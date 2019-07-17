package com.unicore.importer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Properties;
import jxl.Sheet;
import com.uns.model.process.SimpleImportXLS;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.Adempiere;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.model.MPayment;
import org.compiere.process.DocAction;
import com.uns.importer.ImporterValidation;

/**
 * 
 * @author Menjagan
 * @see www.untasoft.com
 */
public class InvoiceSettlementImporterLogic implements ImporterValidation {
	
	private final String 				COLUMN_REFERENCE_NO = "Reference No";
	private final String 				COLUMN_OPEN_INVOICE = "Open Amount";
	private final String 				TENDER_TYPE = "X";
	//Offset initial balance patern OIB+Depo/Region
	private final String 				BANK_ACCOUNT_PREVIX_KEY = "OIB-";
	private final String 				BPARTNER_PREVIX_KEY = "BPARTNER-";
	private final String				REG_PREVIX = "REG-";
	private final int					DT_AR_CMEMO = MDocType.getDocType(
			MDocType.DOCBASETYPE_ARCreditMemo);
	private final int					DT_AP_CMEMO = MDocType.getDocType(
			MDocType.DOCBASETYPE_APCreditMemo);
	private final String				m_description = 
			"::Auto created from import. "+ "Importer logic = " 
					+ getClass().getName();
	private Properties 					m_ctx;
	private Sheet 						m_sheet;
	private String 						m_trxName;
	private Hashtable<String, MPayment> m_mapPayment;
	private Hashtable<String, Object> 	m_mapTemp;
	private StringBuilder 				m_bufferInvNotExists;
	private StringBuilder 				m_bufferInvOverAllocation;
	private IProcessUI 					m_pUI;

	public InvoiceSettlementImporterLogic() 
	{
		super ();
	}
	
	/**
	 * 
	 * @param ctx
	 * @param sheet
	 * @param trxName
	 */
	public InvoiceSettlementImporterLogic (Properties ctx, Sheet sheet, 
			String trxName)
	{
		this ();
		m_ctx = ctx;
		m_sheet = sheet;
		m_trxName = trxName;
		m_mapPayment = new Hashtable<>();
		m_mapTemp = new Hashtable<>();
		m_bufferInvNotExists = new StringBuilder();
		m_bufferInvOverAllocation = new StringBuilder();
		m_pUI = Env.getProcessUI(getCtx());
	}
	
	
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		String referenceNo = (String) freeColVals.get(COLUMN_REFERENCE_NO);
		BigDecimal openAmount = (BigDecimal)
				freeColVals.get(COLUMN_OPEN_INVOICE);
		
		String sql = " SELECT C_Invoice_ID, AD_Client_ID, AD_Org_ID, "
				+ " DocumentNo, C_BPartner_ID, C_DocType_ID, IsSOTrx, "
				+ " DateInvoiced, InvoiceOpen (C_Invoice_ID, 0) AS InvOpen, "
				+ " (SELECT COALESCE (SUM (InvoiceOpen(C_Invoice_ID, 0)), 0) "
				+ " FROM C_Invoice WHERE ReferenceNo = ?) AS TotalOpen FROM "
				+ " C_Invoice WHERE ReferenceNo = ? ";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		int totalInvoices = 0;
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setString(1, referenceNo);
			st.setString(2, referenceNo);
			rs = st.executeQuery();
			BigDecimal allocatedPaid =  Env.ZERO;
			
			while (rs.next())
			{
				totalInvoices++;
				BigDecimal totSysOpen = rs.getBigDecimal("TotalOpen");
				if (totalInvoices == 1)
				{
					allocatedPaid = totSysOpen.subtract(openAmount);
				}
				int documentType_ID = rs.getInt("C_DocType_ID");
				boolean isCN = documentType_ID == DT_AP_CMEMO
						|| documentType_ID == DT_AR_CMEMO;
				int comparator = isCN ? 1 : -1;
				
				if(allocatedPaid.signum() == comparator)
				{
					int bufferCharLength = m_bufferInvOverAllocation.length();
					
					if (bufferCharLength > 0)
					{
						m_bufferInvOverAllocation.append(",");
					}
					
					m_bufferInvOverAllocation.append("'").append(referenceNo)
					.append("'\n");
					
					break;
				}
				
				if(allocatedPaid.signum() == 0)
				{
					break;
				}
				
				BigDecimal sysOpen = rs.getBigDecimal("InvOpen");
				BigDecimal paidAmt = Env.ZERO;
				
				if ((sysOpen.compareTo(allocatedPaid) == comparator) == false)
				{
					paidAmt = allocatedPaid;
					allocatedPaid = Env.ZERO;
				}
				else
				{
					allocatedPaid = allocatedPaid.subtract(sysOpen);
					paidAmt = sysOpen;
				}
				
				if(paidAmt.signum() == 0)
				{
					continue;
				}
				
				crerateAllocation(rs.getInt("C_Invoice_ID"),
						rs.getInt("AD_Org_ID"), 
						rs.getString("IsSOTrx").equals("Y"), 
						rs.getInt("C_BPartner_ID"), 
						rs.getTimestamp("DateInvoiced"), sysOpen, paidAmt);
			}
			
			if(totalInvoices == 0)
			{
				int bufferCharLength = m_bufferInvNotExists.length();
				
				if (bufferCharLength > 0)
				{
					m_bufferInvNotExists.append(",");
				}
				
				m_bufferInvNotExists.append("'").append(referenceNo).append("'\n");
			}
		}
		catch (SQLException e)
		{
			return e.getMessage();
		}
		finally
		{
			DB.close(rs, st);
		}		

		return SimpleImportXLS.DONT_SAVE;
	}

	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		return null;
	}

	@Override
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) 
	{
		int total = m_mapPayment.size();
		int count = 1;
		for (MPayment payment : m_mapPayment.values())
		{
			m_pUI.statusUpdate("Completing payment " + payment.getDocumentNo()
					+ " | " + count++ + " of " + total);
			completePayment(payment);
		}
		
		String toWrite = "";
		String invNotFound = m_bufferInvNotExists.toString();

		if (!Util.isEmpty(invNotFound, true))
		{
			toWrite += "Invoice Not Exists : \n";
			toWrite += invNotFound;
		}
		
		String invOverAllocation = m_bufferInvOverAllocation.toString();
	
		if (!Util.isEmpty(invOverAllocation, true))
		{
			toWrite += "Over Pay : \n";
			toWrite += invOverAllocation;
		}
		
		if (!Util.isEmpty(toWrite, true))
		{
			createLog(toWrite);
		}
		
		return null;
	}

	@Override
	public Hashtable<String, PO> getPOReferenceMap() 
	{
		return null;
	}
	
	public void setCtx (Properties ctx)
	{
		m_ctx = ctx;
	}
	
	public Properties getCtx ()
	{
		return m_ctx;
	}
	
	public void setSheet (Sheet sheet)
	{
		m_sheet = sheet;
	}
	
	public Sheet getSheet ()
	{
		return m_sheet;
	}

	@Override
	public void setTrxName(String trxName) 
	{
		m_trxName = trxName;
	}
	
	public String get_TrxName ()
	{
		return m_trxName;
	}
	
	
	/**
	 * Create Payment
	 * @param C_Invoice_ID
	 * @param AD_Org_ID
	 * @param isSoTrx
	 * @param C_BPartner_ID
	 * @param date
	 * @param openAmt
	 * @param paidAmt
	 */
	private void crerateAllocation (int C_Invoice_ID, int AD_Org_ID, 
			boolean isSoTrx, int C_BPartner_ID, Timestamp date, 
			BigDecimal openAmt, BigDecimal paidAmt)
	{
		int bankAccount_ID = getBankAccount_ID(AD_Org_ID);
		String keyPayment = generateKeyPayment(bankAccount_ID, 
				C_BPartner_ID, isSoTrx);
		
		MPayment payment = m_mapPayment.get(keyPayment);
		if (null == payment)
		{
			payment = getPayment(bankAccount_ID, C_BPartner_ID, isSoTrx);
			if (null == payment)
			{
				payment = new MPayment(getCtx(), 0, get_TrxName());
				payment.setDocumentNo(keyPayment);
				m_mapPayment.put(keyPayment, payment);
			}
			
			payment.setDateTrx(date);
			payment.setDateAcct(date);
			payment.setAD_Org_ID(getBankOrg(bankAccount_ID));
			payment.setC_BPartner_ID(C_BPartner_ID);
			payment.setC_BankAccount_ID(bankAccount_ID);
			payment.setTenderType(TENDER_TYPE);
			payment.setIsReceipt(isSoTrx);
			payment.setC_DocType_ID(payment.isReceipt());
			payment.setC_Currency_ID(303);
		}
		
		if (date.after(payment.getDateTrx()))
		{
			payment.setDateTrx(date);
			payment.setDateAcct(date);
		}
		
		payment.saveEx();
		
		createAllocation(payment, C_Invoice_ID, AD_Org_ID, openAmt, paidAmt);
	}
	
	/**
	 * Complete Payment
	 * @param payment
	 * @return
	 */
	String completePayment(MPayment payment)
	{
		if (payment.isComplete())
			return null;
		
		MPaymentAllocate paList[] = MPaymentAllocate.get(payment);
		
		if (paList.length == 0)
			return null;
		
		MAllocationHdr alloc = new MAllocationHdr(getCtx(), false, 
				payment.getDateTrx(), payment.getC_Currency_ID(), 
					Msg.translate(getCtx(), "C_Payment_ID")	+ ": " 
					+ payment.getDocumentNo(), payment.get_TrxName());
		alloc.setAD_Org_ID(payment.getAD_Org_ID());
		alloc.setDateAcct(payment.getDateAcct());
		alloc.disableModelValidation();
		if (!alloc.save()) {
			return "Payment Allocation header not created.";
		}
			
		MBPartner bpartner = new MBPartner (getCtx(), 
				payment.getC_BPartner_ID(), payment.get_TrxName());
		
		for (MPaymentAllocate pa : paList)
		{
			MAllocationLine aLine = null;
			if (payment.isReceipt())
				aLine = new MAllocationLine (alloc, pa.getAmount(), 
					pa.getDiscountAmt(), pa.getWriteOffAmt(), 
					pa.getOverUnderAmt());
			else
				aLine = new MAllocationLine (alloc, pa.getAmount().negate(), 
					pa.getDiscountAmt().negate(), pa.getWriteOffAmt().negate(), 
					pa.getOverUnderAmt().negate());
			
			MInvoice invoice = (MInvoice) pa.getC_Invoice();
		
			aLine.setDocInfo(invoice.getC_BPartner_ID(), 0, 
					pa.getC_Invoice_ID());
			aLine.setPaymentInfo(payment.getC_Payment_ID(), 0);
			aLine.disableModelValidation();
			if (!aLine.save())
				throw new AdempiereException("P.Allocations - line not saved");
			else {
				pa.setC_AllocationLine_ID(aLine.getC_AllocationLine_ID());
				pa.disableModelValidation();
				pa.saveEx();
			}
			
			invoice.testAllocation();
			invoice.disableModelValidation();
			invoice.saveEx();
			
			//Update BPartner Balance.
			int C_Payment_ID = aLine.getC_Payment_ID();
			int C_BPartner_ID = aLine.getC_BPartner_ID();
			int M_Invoice_ID = aLine.getC_Invoice_ID();

			if ((C_BPartner_ID == 0) || ((M_Invoice_ID == 0) 
					&& (C_Payment_ID == 0)))
				continue;

			boolean isSOTrxInvoice = false;
			if (M_Invoice_ID > 0)
				isSOTrxInvoice = invoice.isSOTrx();
			
			DB.getDatabase().forUpdate(bpartner, 0);

			BigDecimal openBalanceDiff = Env.ZERO;
			
			boolean paymentProcessed = false;
			boolean paymentIsReceipt = false;
			
			int convTypeID = 0;
			Timestamp paymentDate = null;
			
			convTypeID = payment.getC_ConversionType_ID();
			paymentDate = payment.getDateAcct();
			paymentIsReceipt = payment.isReceipt();
					
			// Adjust open amount with allocated amount. 
			if (invoice != null)
			{
				// If payment is already processed, only adjust open 
				//balance by discount and write off amounts.
				BigDecimal amt = MConversionRate.convertBase(getCtx(), 
						aLine.getWriteOffAmt().add(aLine.getDiscountAmt()),
						alloc.getC_Currency_ID(), paymentDate, 
						convTypeID, payment.getAD_Client_ID(), 
						alloc.getAD_Org_ID());
				if (amt == null)
				{
					return "Could not convert allocation C_Currency_ID=" 
							+ alloc.getC_Currency_ID()
					+ " to base C_Currency_ID=" + MClient.get(getCtx())
					.getC_Currency_ID() + ", C_ConversionType_ID=" + convTypeID
					+ ", conversion date= " + paymentDate;
				}
				openBalanceDiff = openBalanceDiff.add(amt);
			}
			
			//	Total Balance
			BigDecimal newBalance = bpartner.getTotalOpenBalance();
			if (newBalance == null)
				newBalance = Env.ZERO;
			
			BigDecimal originalBalance = new BigDecimal(newBalance.toString());

			if (openBalanceDiff.signum() != 0) {
				newBalance = newBalance.subtract(openBalanceDiff);
			}

			// Update BP Credit Used only for Customer Invoices and for 
			// payment-to-payment allocations.
			BigDecimal newCreditAmt = Env.ZERO;
			if (isSOTrxInvoice || (invoice == null && paymentIsReceipt 
					&& paymentProcessed))
			{
				if (invoice == null)
					openBalanceDiff = openBalanceDiff.negate();

				newCreditAmt = bpartner.getSO_CreditUsed();

				if (newCreditAmt == null)
					newCreditAmt = openBalanceDiff.negate();
				else
					newCreditAmt = newCreditAmt.subtract(openBalanceDiff);

				bpartner.setSO_CreditUsed(newCreditAmt);
			}
			else
			{
				System.out.println("TotalOpenBalance=" + bpartner
						.getTotalOpenBalance() + "(" + openBalanceDiff
						+ ", Balance=" + bpartner.getTotalOpenBalance() 
						+ " -> " + newBalance);
			}

			if (newBalance.compareTo(originalBalance) != 0)
				bpartner.setTotalOpenBalance(newBalance);
			
			bpartner.setSOCreditStatus();
		}

		payment.setDocStatus(DocAction.STATUS_Completed);
		payment.setDocAction(DocAction.ACTION_Close);
		payment.setProcessed(true);
		payment.disableModelValidation();
		payment.saveEx();
		
		alloc.disableModelValidation();
		alloc.setDocStatus(DocAction.STATUS_Completed);
		alloc.setDocAction(DocAction.ACTION_Close);
		alloc.setProcessed(true);
		if (!alloc.save()) {
			return "Payment Allocation header not created.";
		}
			
		
		if (!bpartner.save())
		{
			return "Could not update Business Partner";
		}
 
		return null;
	}
	
	/**
	 * Create Invoice allocation
	 * @param payment
	 * @param C_Invoice_ID
	 * @param AD_Org_ID
	 * @param openAmt
	 * @param paidAmt
	 */
	private void createAllocation (MPayment payment, int C_Invoice_ID, 
			int AD_Org_ID, BigDecimal openAmt, BigDecimal paidAmt)
	{
		try
		{
			MPaymentAllocate allocation = getAllocation(payment.get_ID(), 
					C_Invoice_ID);
			if (null == allocation)
			{
				allocation = new MPaymentAllocate(getCtx(), 0, get_TrxName());
				allocation.setAD_Org_ID(AD_Org_ID);
				allocation.setC_Payment_ID(payment.get_ID());
				allocation.setC_Invoice_ID(C_Invoice_ID);
				allocation.setDescription(m_description);
			}
			
			BigDecimal overUnder = openAmt.subtract(paidAmt);
			allocation.setInvoiceAmt(openAmt);
			allocation.setPayToOverUnderAmount(paidAmt);
			allocation.setWriteOffAmt(Env.ZERO);
			allocation.setAmount(paidAmt);
			allocation.setOverUnderAmt(overUnder);
			allocation.saveEx();
		}
		catch (Exception ex)
		{
			throw new AdempiereException(ex.getMessage());
		}
	}
	
	private MPaymentAllocate getAllocation (int C_Payment_ID, int C_Invoice_ID)
	{
		return new Query(getCtx(), MPaymentAllocate.Table_Name, 
				MPaymentAllocate.COLUMNNAME_C_Payment_ID + " = ? AND " 
				+ MPaymentAllocate.COLUMNNAME_C_Invoice_ID + " = ? ", 
				get_TrxName()).setParameters(C_Payment_ID, 
						C_Invoice_ID).first();
	}
	
	
	private String generateKeyPayment (int C_BankAccount_ID, int C_BPartner_ID,
			boolean isSOTrx)
	{
		String bankAccountName = getBankAccountName(C_BankAccount_ID);
		String partnerName = getBPartnerName(C_BPartner_ID);
		return (new StringBuilder(bankAccountName).append(partnerName)
				.append(isSOTrx ? "Y" : "N")).toString();
	}
	
	private String getBankAccountName (int C_BankAccount_ID)
	{
		String value = (String) m_mapTemp.get(
				BANK_ACCOUNT_PREVIX_KEY + C_BankAccount_ID);
		if (null == value)
		{
			value = "SELECT Value FROM C_BankAccount WHERE C_BankAccount_ID = ?";
			value = DB.getSQLValueString(
					get_TrxName(), value, C_BankAccount_ID);
			if (null == value)
			{
				throw new AdempiereUserError("No Bank Account with ID " 
							+ C_BankAccount_ID);
			}
			m_mapTemp.put(BANK_ACCOUNT_PREVIX_KEY + C_BankAccount_ID, value);
		}
		
		return value;
	}
	
	private String getBPartnerName (int C_BPartner_ID)
	{
		String value = (String) m_mapTemp.get(
				BPARTNER_PREVIX_KEY + C_BPartner_ID);
		if (null == value)
		{
			value = "SELECT Value FROM C_BPartner WHERE C_BPartner_ID = ?";
			value = DB.getSQLValueString(
					get_TrxName(), value, C_BPartner_ID);
			if (null == value)
			{
				throw new AdempiereUserError("No Bank Account with ID " 
							+ C_BPartner_ID);
			}
			m_mapTemp.put(BPARTNER_PREVIX_KEY + C_BPartner_ID, value);
		}
		
		return value;
	}
	
	private MPayment getPayment (int C_BankAccount_ID, int C_BPartner_ID, 
			boolean isSOTrx)
	{
		return new Query(getCtx(), MPayment.Table_Name, 
				"C_BankAccount_ID = ? AND C_BPartner_ID = ? AND DocStatus "
				+ " IN ('DR') AND IsReceipt = ? ", get_TrxName()).
				setParameters(C_BankAccount_ID, C_BPartner_ID, 
						isSOTrx ? "Y" : "N").first();
	}
	
	private int getBankAccount_ID (int AD_Org_ID)
	{
		String region = getOrgRegionValue(AD_Org_ID);
		String key = BANK_ACCOUNT_PREVIX_KEY.concat(region);
		Integer id = (Integer) 
				m_mapTemp.get(key);
		if (null == id)
		{
			String sql = "SELECT C_BankAccount_ID FROM C_BankAccount "
					+ " WHERE Value = ?";
			id = DB.getSQLValue(get_TrxName(), sql, key);
			m_mapTemp.put(key, id);
		}
		
		return id;
	}
	
	private int getBankOrg (int C_BankAccount_ID)
	{
		String previx = "BANK-";
		String key = previx + C_BankAccount_ID;
		Integer retVal = (Integer) m_mapTemp.get(key);
		if (null == retVal)
		{
			retVal = DB.getSQLValue(get_TrxName(), "SELECT AD_Org_ID "
					+ " FROM C_BankAccount WHERE C_BankAccount_ID = ? ", 
					C_BankAccount_ID);
			m_mapTemp.put(key, retVal);
		}
		
		return retVal.intValue();
	}
	
	/**
	 * 
	 * @param AD_Org_ID
	 * @return
	 */
	private String getOrgRegionValue (int AD_Org_ID)
	{
		String key = REG_PREVIX + AD_Org_ID;
		String value = (String) m_mapTemp.get(key);
		
		if (null == value)
		{
			String sql = "SELECT Value FROM C_SalesRegion WHERE "
					+ " C_SalesRegion_ID = (SELECT C_SalesRegion_ID FROM "
					+ " AD_Org WHERE AD_Org_ID = ?)";
			
			value = DB.getSQLValueString(get_TrxName(), sql, AD_Org_ID);
			m_mapTemp.put(key, value);
		}
		
		return value;
	}
	
	private void createLog (String log)
	{
		String dir = Adempiere.getAdempiereHome();
		
		File iFile = new File(dir + "/" + getClass().getName() + "_"
				+ System.currentTimeMillis() + ".txt");
		iFile.setWritable(true);
		try 
		{
			FileWriter writer = new FileWriter(iFile.getAbsoluteFile());
			BufferedWriter buffer = new BufferedWriter(writer);
			buffer.write(log);
			buffer.close();
		}
		catch (IOException e)
		{
			throw new AdempiereException(e.getMessage());
		}

		System.out.println("Created log " + dir + "/" + iFile.getName());
	}
}