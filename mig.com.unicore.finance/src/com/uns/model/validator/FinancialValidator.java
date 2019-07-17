/**
 * 
 */
package com.uns.model.validator;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalLine;
import org.compiere.model.MOrder;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MTax;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.uns.util.MessageBox;

import com.unicore.model.MUNSPRAllocation;
import com.unicore.model.MUNSVATDocInOut;

/**
 * @author Haryadi
 *
 */
public class FinancialValidator implements ModelValidator 
{

	/**
	 * 
	 */
	private int m_AD_Client_ID;
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ChequebookValidator.class);
	
	
	/**
	 * 
	 */
	public FinancialValidator() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#initialize(org.compiere.model.ModelValidationEngine, org.compiere.model.MClient)
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client) 
	{
		if (client != null) {	
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing global validator: "+this.toString());
		}
		engine.addModelChange(MJournalLine.Table_Name, this);
		engine.addDocValidate(MJournal.Table_Name, this);
		engine.addDocValidate(MPayment.Table_Name, this);
		engine.addDocValidate(MBankStatement.Table_Name, this);
		engine.addModelChange(MPayment.Table_Name, this);
		engine.addModelChange(MBankStatement.Table_Name, this);
		engine.addModelChange(MPaymentAllocate.Table_Name, this);
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#getAD_Client_ID()
	 */
	@Override
	public int getAD_Client_ID() {
		return m_AD_Client_ID;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#login(int, int, int)
	 */
	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#modelChange(org.compiere.model.PO, int)
	 */
	@Override
	public String modelChange(PO po, int type) throws Exception 
	{
		//menghandel biaya transfer ditanggung perusahaan
		//pembayaran akan dikurangi dengan biaya transfer
		String msg = "";
		if(po.get_Table_ID() == MPayment.Table_ID)
		{
			if(type != TYPE_BEFORE_CHANGE && type != TYPE_BEFORE_NEW)
				return null;
			MPayment payment = (MPayment) po;
			if(payment.getC_Charge_ID() == 0)
				payment.setChargeAmt(Env.ZERO);
//			else if(!payment.isReceipt())
//				payment.setChargeAmt(Env.ZERO);
//			else if(MPaymentAllocate.get(payment).length == 0)
//				payment.setChargeAmt(Env.ZERO);
			if (payment.getC_Charge_ID() == 0)
				payment.setChargeAmt(Env.ZERO);
			BigDecimal chargeAmt = payment.getChargeAmt();

//			BigDecimal payAmt = payment.getPayAmt();
			
//			if(chargeAmt.signum() != 0 && chargeAmt.compareTo(payAmt) == 1)
//				return "Charge amount can't be more than pay amount";
			
			BigDecimal allocatedAmt = payment.getPayAmt();
			
			if ((payment.getC_Order_ID() == 0 
					&& payment.getC_Invoice_ID() == 0 
					&& payment.getC_Charge_ID() > 0) || payment.is_ValueChanged("C_Charge_ID")) 
			{
				String sql = "SELECT SUM(Amount) " +
							" FROM C_PaymentAllocate WHERE C_Payment_ID=" + payment.get_ID();
				allocatedAmt = DB.getSQLValueBDEx(payment.get_TrxName(), sql);
				
				if (allocatedAmt == null)
					allocatedAmt = payment.getPayAmt();
				
				payment.setTotalAmt(allocatedAmt);
				payment.setPayAmt(allocatedAmt.subtract(chargeAmt));
				if (chargeAmt.signum()< 0)
					payment.setTotalAmt(payment.getPayAmt());
			}
			return msg;
			
//			BigDecimal totalAmt = payment.getTotalAmt();
//			if(totalAmt.signum() == 0)
//			{
//				totalAmt = payAmt;
//			}
//			else if(po.is_ValueChanged(MPayment.COLUMNNAME_PayAmt))
//			{
//				totalAmt = payAmt;
//			}
//			
//			payAmt = totalAmt.subtract(chargeAmt);
//			payment.setPayAmt(payAmt);
//			payment.setTotalAmt(totalAmt);
		}
		
		//hanya bisa buka 1 bank/cash statement untuk akun yang sama dalam waktu bersamaan.
		//apri
		if(po.get_Table_ID() == MBankStatement.Table_ID && type == TYPE_AFTER_NEW)
		{
			if(type != TYPE_BEFORE_CHANGE && type != TYPE_BEFORE_NEW)
				return null;
			String sql = "SELECT 1 FROM C_BankStatement WHERE DocStatus = 'DR'"
					+ " AND C_BankAccount_ID = " + po.get_ValueAsInt("C_BankAccount_ID")
					+ " AND C_BankStatement_ID <> " + po.get_ID();
			 
			 if (DB.getSQLValue(po.get_TrxName(), sql) >= 1)
				msg = "Failed: Duplicate data is found."
				 		+ " Account Bank/Cash Statement cannot used. \n"
				 		+ "Please be sure you are completed previouse data before create new.";
		}
//		sampai disini 
		
		if(po.get_Table_ID() == MPaymentAllocate.Table_ID)
		{
			if(type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)
			{
				msg = allowedPaidAmount(po);
				if(Util.isEmpty(msg, true))
					msg = checkWriteOff(po);
			}
		}
		
		//TODO JURNAL Cross balance
//		if (po.get_TableName().equals(MJournalLine.Table_Name)
//			&& (TYPE_BEFORE_NEW == type || TYPE_BEFORE_CHANGE == type))
//		{
//			MJournalLine jLine = (MJournalLine) po;
//			MJournal journal = jLine.getParent();
//			
//			if(!MJournal.JOURNALTYPE_VendorCrossBalance.equals(journal.getJournalType()))
//				return errMsg;
//			MJournalLine[] jLines = journal.getLines(false);
//
//			if (jLines == null)
//				return null;
//			
//			if (TYPE_BEFORE_NEW == type)
//			{
//				MJournalLine[] jLineTmps = new MJournalLine[jLines.length + 1];
//				for (int i=0; i < jLines.length; i++)
//					jLineTmps[i] = jLines[i];
//				
//				jLineTmps[jLineTmps.length - 1] = jLine;
//				jLines = jLineTmps;
//			}
//			return isValidBPartnerInJournalLines(jLines);
//		}
		return msg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#docValidate(org.compiere.model.PO, int)
	 */
	@Override
	public String docValidate(PO po, int timing) 
	{
		String errMsg = null;
		//TODO Vendor Cross Balance
//		if (po.get_TableName().equals(MJournal.Table_Name) && (TIMING_BEFORE_COMPLETE == timing))
//		{
//			MJournal journal = (MJournal) po;
//			MJournalLine[] jLines = journal.getLines(false);
//			
//			errMsg = isValidBPartnerInJournalLines(jLines);
//			if (errMsg != null)
//				return errMsg;
//			
//			for (MJournalLine jLine : jLines)
//			{
//				BigDecimal amtDr = Env.ZERO;
//				if (jLine.getAmtAcctDr().signum() > 0)
//					amtDr = jLine.getAmtAcctDr();
//				else if (jLine.getAmtAcctCr().signum() < 0)
//					amtDr = jLine.getAmtAcctCr().abs();
//				
//				BigDecimal amtCr = Env.ZERO;
//				if (jLine.getAmtAcctCr().signum() > 0)
//					amtCr = jLine.getAmtAcctCr();
//				else if (jLine.getAmtAcctDr().signum() < 0)
//					amtCr = jLine.getAmtAcctDr().abs();
//				
//				MBPartner bp = (MBPartner) jLine.getC_BPartner();
//				
//				if (amtDr.signum() > 0)
//				{
//					if (MJournal.JOURNALTYPE_VendorCrossBalance.equals(journal.getJournalType()))
//					{
//						if (journal.getReversal_ID() == 0)
//							bp.setTotalOpenBalance(bp.getTotalOpenBalance().add(amtDr));
//						else
//							bp.setTotalOpenBalance(bp.getTotalOpenBalance().subtract(amtDr));
//					}
//					else if (MJournal.JOURNALTYPE_CustomerCrossBalance.equals(journal.getJournalType()))
//					{
//						if (journal.getReversal_ID() == 0)
//							bp.setTotalOpenBalance(bp.getTotalOpenBalance().add(amtDr));
//						else 
//							bp.setTotalOpenBalance(bp.getTotalOpenBalance().subtract(amtDr));
//					}
//				}
//				else if (amtCr.signum() > 0)
//				{
//					if (MJournal.JOURNALTYPE_VendorCrossBalance.equals(journal.getJournalType()))
//					{
//						if (journal.getReversal_ID() == 0)
//							bp.setTotalOpenBalance(bp.getTotalOpenBalance().subtract(amtCr));
//						else
//							bp.setTotalOpenBalance(bp.getTotalOpenBalance().add(amtCr));
//					}
//					else if (MJournal.JOURNALTYPE_CustomerCrossBalance.equals(journal.getJournalType()))
//					{
//						if (journal.getReversal_ID() == 0)
//							bp.setTotalOpenBalance(bp.getTotalOpenBalance().subtract(amtCr));
//						else
//							bp.setTotalOpenBalance(bp.getTotalOpenBalance().add(amtCr));
//					}
//				}
//				bp.saveEx();
//			}
//		}
//		else 
		if (po.get_TableName().equals(MPayment.Table_Name) && (TIMING_AFTER_COMPLETE == timing))
		{
			MPayment thePayment = (MPayment) po;
			if (thePayment.getC_Order_ID() > 0 
				&& thePayment.getPayAmt().signum() > 0 
				&& thePayment.getC_BPartner_ID() > 0)
			{
				X_C_Order theOrder = (X_C_Order) thePayment.getC_Order();
				
				String prevDesc = theOrder.getDescription() == null? "" : theOrder.getDescription();
				
				String theAmt = 
						NumberFormat.getInstance(new Locale("ID")).format(thePayment.getPayAmt().doubleValue());
				
				String theTrxDate = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM)
						.format(thePayment.getDateTrx());
				
				theOrder.setDescription("*** Prepaid by finance for amount of " + 
										thePayment.getC_Currency().getISO_Code() + " " + 
										theAmt + " @" + theTrxDate + " ***\n" + prevDesc);
				theOrder.saveEx();
			}
			
			errMsg = createStatementOfPayment(thePayment);
		}
		else if(po.get_TableName().equals(MPayment.Table_Name) && TIMING_BEFORE_PREPARE == timing)
		{
			MPayment payment = (MPayment) po;
			if(payment.getTenderType().equals(MPayment.TENDERTYPE_Check)
					&& payment.getCheckNo() == null)
			{
				return "Please define check no...";
			}
			
			MPaymentAllocate[] allocates = MPaymentAllocate.get(payment);
			for(int i=0; i<allocates.length; i++)
			{
				MPaymentAllocate allocate = allocates[i];
				if(allocate.getC_Order_ID() == 0)
					continue;
				
				MOrder order = (MOrder) allocate.getC_Order();
				if(order.getDateOrdered().after(payment.getDateTrx()))
				{
					errMsg = "Wrong allocation date.";
					break;
				}
				
				order.setC_Payment_ID(payment.get_ID());
				String docSubTypeSo = order.getC_DocType().getDocSubTypeSO();
				if(!docSubTypeSo.equals(MDocType.DOCSUBTYPESO_CashOrder)
						&& !docSubTypeSo.equals(MDocType.DOCSUBTYPESO_PrepayOrder))
				{
					errMsg = "Can't process Order document. It is not cash / pre pay order.";
					break;
				}
				
				BigDecimal amount = allocate.getPayToOverUnderAmount()
						.add(allocate.getWriteOffAmt());
				if(amount.signum() == 0)
				{
					errMsg = "Invalid amount.";
					break;
				}
				if(order.getGrandTotal().compareTo(amount) == 1
						&& !docSubTypeSo.equals(MDocType.DOCSUBTYPESO_PrepayOrder))
				{
					errMsg = "Can't complete document. Grand Total of Order must less than or equal to payment amount";
					break;
				}
				
				if(!order.isComplete())
				{
					boolean ok = order.processIt(DocAction.ACTION_Complete);
					if(!ok || !order.isComplete())
					{
						errMsg = "Can't complete Order Document " + order.getProcessMsg();
						break;
					}
				}
				
				MInvoice invoice = null;
				int C_Invoice_ID = allocate.getC_Invoice_ID();
				if(C_Invoice_ID == 0)
				{
					C_Invoice_ID = order.getC_Invoice_ID();
				}
				if(C_Invoice_ID <= 0)
				{
					invoice = order.createInvoice (
							(MDocType) order.getC_DocType(), null, order.getDatePromised());
					if (invoice == null)
					{
						errMsg = "Can't create invoice.";
					}
					else
					{
						C_Invoice_ID = invoice.get_ID();
					}
					errMsg = invoice.getProcessMsg();
					if(!Util.isEmpty(errMsg, true))
					{
						break;
					}
				}
				else
				{
					invoice = new MInvoice(po.getCtx(), C_Invoice_ID, po.get_TrxName());
				}
				
				if(!invoice.isComplete())
				{
					boolean ok = invoice.processIt(DocAction.ACTION_Complete);
					if(!ok)
					{
						errMsg = "Can't complete Invoice Document " + invoice.getProcessMsg();
						break;
					}
				}
				allocate.setC_Invoice_ID(C_Invoice_ID);
				allocate.saveEx();
			}
		}
		else if (po.get_Table_ID() == MPayment.Table_ID && timing == TIMING_AFTER_COMPLETE)
		{
			boolean isReceipt = po.get_ValueAsBoolean("IsReceipt");
			if (!isReceipt)
			{
				int ar_ID = po.get_ValueAsInt("Reference_ID");
				if (ar_ID > 0)
				{
					MPayment payment = new MPayment(po.getCtx(), ar_ID, 
							po.get_TrxName());
					payment.m_force = true;
					payment.setGridTab(po.getGridTab());
					boolean ok = payment.processIt(DocAction.ACTION_Complete);
					if (!ok)
					{
						return payment.getProcessMsg();
					}
					payment.saveEx();
				}
			}
		}
		else if (po.get_TableName().equals(MPayment.Table_Name) && timing == TIMING_AFTER_COMPLETE
				&& ((MPayment) po).getUNS_PR_Allocation_ID() > 0)
		{
			MUNSPRAllocation receiptAllocation = new MUNSPRAllocation(
					po.getCtx(), po.get_ValueAsInt("UNS_PR_Allocation_ID"), po.get_TrxName());
			MPayment[] paymentList = receiptAllocation.getLines();
			boolean fullyCompleted = true;
			for(int i=0; i<paymentList.length; i++)
			{
				if(paymentList[i].get_ID() == po.get_ID())
				{
					continue;
				}
				else if(paymentList[i].isComplete())
				{
					continue;
				}
				fullyCompleted = false;
			}
			
			if(!fullyCompleted)
			{
				return errMsg;
			}
			
			if(receiptAllocation.getDocStatus().equals("DR")
					|| receiptAllocation.getDocStatus().equals("IP")
					|| receiptAllocation.getDocStatus().equals("IN"))
			{
				receiptAllocation.m_force = true;
				boolean ok = receiptAllocation.processIt(DocAction.ACTION_Complete);
				if(!ok) 
				{
					errMsg = receiptAllocation.getProcessMsg();
				}
			}
		}
		else if (MPayment.Table_ID == po.get_Table_ID()
				&& (timing == TIMING_AFTER_REVERSEACCRUAL
				|| timing == TIMING_AFTER_REVERSECORRECT)
				|| timing == TIMING_AFTER_VOID)
		{
			String sql = " SELECT C_Order_ID, C_Invoice_ID FROM "
					+ " C_PaymentAllocate WHERE C_Order_ID IS NOT NULL "
					+ " AND C_Payment_ID = ?";
			PreparedStatement st = null;
			ResultSet rs = null;
			try
			{
				st = DB.prepareStatement(sql, po.get_TrxName());
				st.setInt(1, po.get_ID());
				rs = st.executeQuery();
				while (rs.next())
				{
					po.saveEx();
					int C_Order_ID = rs.getInt("C_Order_ID");
					int C_Invoice_ID = rs.getInt("C_Invoice_ID");
					MOrder order = new MOrder(po.getCtx(), C_Order_ID, 
							po.get_TrxName());
					boolean ok = order.processIt(DocAction.ACTION_Void);
					if (!ok)
					{
						errMsg = order.getProcessMsg();
						break;
					}
					order.saveEx();
					
					MInvoice invoice = new MInvoice(po.getCtx(), C_Invoice_ID, 
							po.get_TrxName());
					ok = invoice.processIt(DocAction.ACTION_Void);
					if (!ok)
					{
						errMsg = invoice.getProcessMsg();
						break;
					}
					invoice.saveEx();
				}
			}
			catch (SQLException ex)
			{
				errMsg = ex.getMessage();
			}
			catch (Exception ex) 
			{
				errMsg = ex.getMessage();
			}
			finally
			{
				DB.close(rs, st);
			}
			
			if (!Util.isEmpty(errMsg, true))
				return errMsg;
			
			errMsg = doCancelPayment((MPayment) po);
		}
		
		else if(po.get_Table_ID() == MBankStatement.Table_ID && timing == TIMING_BEFORE_REACTIVATE)
		{
			if(!checkOtherStatement(po))
				errMsg = "Transaction has cancelled";
		}
//		else if(po.get_Table_ID() == MPayment.Table_ID && (timing == TIMING_BEFORE_REVERSEACCRUAL
//				|| timing == TIMING_BEFORE_REVERSECORRECT || timing == TIMING_BEFORE_VOID))
//		{
//			MPayment pay = (MPayment) po;
//			for(MPaymentAllocate alloc : MPaymentAllocate.get(pay))
//			{
//				MInvoice inv = MInvoice.get(pay.getCtx(), alloc.getC_Invoice_ID());
//				if(inv.isPaid() && inv.isReconciled())
//				{
//					inv.setIsReconciled(false);
//					inv.saveEx();
//				}
//			}
//		}
		
		return errMsg;
	}
	
//	/**
//	 * 
//	 * @param journal
//	 * @return
//	 */
//	private String isValidBPartnerInJournalLines(MJournalLine[] jLines)
//	{
//		String errMsg = null;
//		
//		//MJournalLine[] jLines = journal.getLines(false);
//		
//		for (MJournalLine jLine : jLines)
//		{
//			int vFromID = jLine.getC_BPartner_ID();
//			if (vFromID == 0)
//				errMsg += "BPartner is not selected in line no:" + jLine.getLine() + " \n";
//			
//			if (jLine.getAmtSourceDr().signum() < 0 || jLine.getAmtSourceCr().signum() > 0)
//			{ // this is the line of vendor to reduce the AP.
//				for (MJournalLine jLineTo : jLines)
//				{
//					int vToID = jLineTo.getC_BPartner_ID();
//					if (jLine.getAmtSourceCr().signum() < 0 || jLine.getAmtSourceDr().signum() > 0)
//					{
//						if (vToID == 0)
//							errMsg += "BPartner is not selected in line no:" + jLineTo.getLine() + " \n";
//						else if (vToID == vFromID)
//							errMsg += "Cannot cross balance to the same BPartner in line no:" + jLine.getLine() + 
//							" with line no: " + jLineTo.getLine() + "\n";
//					}
//				}
//			}
//		}
//		
//		return errMsg;
//	}
	
	/**
	 * Please only call this on reverse/void.
	 * @param payment
	 * @return
	 */
	private String doCancelPayment (MPayment payment)
	{
		String msg = null;
		//Transver Balance
//		String tb_status = DB.getSQLValueString(payment.get_TrxName(), 
//				" SELECT DocStatus FROM UNS_TransferBalance_Confirm " +
//				" WHERE PaymentFrom_ID = ?", payment.get_ID());
//		if (null != tb_status && (!tb_status.equals("VO") || !tb_status.equals("RE"))
//				&& !payment.m_force)
//		{
//			msg = "Please reverse/void Petty Cash Request first";
//		}
		if (payment.isReceipt())
		{
			if (payment.get_ValueAsInt("Reference_ID") <= 0)
			{
				return msg;
			}
			
			MPayment apPayment = new MPayment(payment.getCtx(), 
					payment.get_ValueAsInt("Reference_ID"), 
					payment.get_TrxName());
			String apStatus = apPayment.getDocStatus();
			if (!"VO".equals(apStatus) && !"RE".equals(apStatus) 
					&& !payment.m_force)
			{
				msg = "Please reverse or void payment from AP reference Doc.";
			}
		}
		else
		{
			int ar_id = payment.get_ValueAsInt("Reference_ID");
			if (ar_id <= 0)
			{
				return msg;
			}
			
			MPayment ar = new MPayment(payment.getCtx(), ar_id,
					payment.get_TrxName());
			ar.setGridTab(payment.getGridTab());
			ar.m_force = true;
			boolean ok = ar.processIt(DocAction.ACTION_Void);
			if (!ok)
			{
				msg = "Failed when complete AR Receipt ";
			}
			ar.saveEx();
		}
		return msg;
	}
	
	
	private String createStatementOfPayment(MPayment payment)
	{
//		if (!payment.isAutoCreateStatement())
//			return null;
		
		try
		{
			String sql = " SELECT 1 FROM C_BankStatementLine WHERE "
					+ " C_Payment_ID = ? AND EXISTS (SELECT * FROM "
					+ " C_BankStatement WHERE C_BankStatement_ID = "
					+ " C_BankStatementLine.C_BankStatement_ID AND "
					+ " DocStatus NOT IN ('RE','VO')) ";
			int exists = DB.getSQLValueEx(payment.get_TrxName(), sql, 
					payment.get_ID());
			boolean isExists = exists > 0;
			if (isExists)
				return null;
			
			MBankStatement bStmt = MBankStatement.getOpen(
					payment.getC_BankAccount_ID(), 
					payment.get_TrxName(), true);
			
			MBankStatementLine bStmtL = new MBankStatementLine(bStmt);
			bStmtL.setPayment(payment);
			bStmtL.setDateAcct(payment.getDateAcct());
			bStmtL.saveEx();
		}
		catch (Exception ex)
		{
			return ex.getMessage();
		}
		
		return null;
	}
	
	private String allowedPaidAmount(PO po)
	{
		if(po == null)
			return null;
		MPaymentAllocate pa = null;
		
		if(po instanceof MPaymentAllocate)
			pa = (MPaymentAllocate) po;
		else
			return null;
		
		if(pa.getC_Invoice_ID() <= 0)
			return null;
		
		MInvoice inv = MInvoice.get(pa.getCtx(), pa.getC_Invoice_ID());
		if(inv.getC_Order_ID() > 0)
		{
			
		}
		MInvoiceTax[] taxs = MInvoiceTax.getByInvoice(pa.getCtx(), inv.get_ID(), pa.get_TrxName());
		BigDecimal amtTaxUnReceivedDoc = Env.ZERO;
		
		for(MInvoiceTax tax : taxs)
		{
			MTax taxRate = MTax.get(pa.getCtx(), tax.getC_Tax_ID());
			String trxType = taxRate.getRequiresDocOnTrx();
			if(Util.isEmpty(trxType, true))
				continue;
			
			if((trxType.equals(MTax.REQUIRESDOCONTRX_PurchaseTax) && !inv.isSOTrx())
					|| (trxType.equals(MTax.REQUIRESDOCONTRX_SalesTax) && inv.isSOTrx())
						|| trxType.equals(MTax.REQUIRESDOCONTRX_Both))
			{
				if(null == MUNSVATDocInOut.getByInvoiceTax(pa.getCtx(), inv.getC_Invoice_ID(),
						tax.get_ID(), pa.get_TrxName()))
					amtTaxUnReceivedDoc = amtTaxUnReceivedDoc.add(tax.getTaxAmt());
			}
		}
		
		if(amtTaxUnReceivedDoc.signum() > 0)
		{
			if(amtTaxUnReceivedDoc.compareTo(pa.getOverUnderAmt()) == 1)
			{
				return "Over amount pay. Amount tax unreceived document :: " + amtTaxUnReceivedDoc;
			}
		}
		return null;
	}
	
	private String checkWriteOff(PO po)
	{
		if(po.get_ValueAsInt("C_Invoice_ID") <= 0)
			return null;
		
		String sql = "SELECT dt.DocBaseType, pa.WriteOffAmt, (CASE WHEN iv.IsSOTrx = 'Y'"
				+ " THEN oi.MaxWriteOffSales ELSE oi.MaxWriteOffPurchasing END) FROM C_PaymentAllocate pa"
				+ " INNER JOIN C_Invoice iv ON iv.C_Invoice_ID = pa.C_Invoice_ID"
				+ " INNER JOIN C_DocType dt ON dt.C_DocType_ID = iv.C_DocType_ID"
				+ " INNER JOIN AD_OrgInfo oi ON oi.AD_Org_ID = iv.AD_Org_ID"
				+ " WHERE pa.C_PaymentAllocate_ID = ?";
		List<List<Object>> columns = DB.getSQLArrayObjectsEx(po.get_TrxName(), sql, po.get_ID());
		String dt = (String) columns.get(0).get(0);
		BigDecimal writeOff = (BigDecimal) columns.get(0).get(1);
		BigDecimal maxWriteOff = (BigDecimal) columns.get(0).get(2);
		
		if(writeOff.signum() == 0)
			return null;
		String msg = "Allowed write off amount is " + maxWriteOff.setScale(2) + " or lower"; 
		
		if(dt.equals(MDocType.DOCBASETYPE_ARInvoice) || dt.equals(MDocType.DOCBASETYPE_ARCreditMemo))
		{
			if(writeOff.signum() < 0)
				return null;
			else
			{
				if(maxWriteOff.compareTo(writeOff) == -1)
					return msg;
			}
		}
		else if(dt.equals(MDocType.DOCBASETYPE_APInvoice) || dt.equals(MDocType.DOCBASETYPE_APCreditMemo))
		{
			if(writeOff.signum() > 0)
				return null;
			else
			{
				writeOff = writeOff.abs();
				if(maxWriteOff.compareTo(writeOff) == -1)
					return msg;
			}
		}
		
		return null;
	}
	
	private boolean checkOtherStatement(PO po)
	{
		if(!(po instanceof MBankStatement))
			return true;
		
		MBankStatement stm  = (MBankStatement) po;
		String sql = "SELECT COUNT(*) FROM C_BankStatement "
				+ "WHERE C_BankAccount_ID=? AND DocStatus NOT IN ('CO', 'CL', 'VO', 'RE') AND C_BankStatement_ID <> ?";

		int count = DB.getSQLValue(stm.get_TrxName(), sql, stm.getC_BankAccount_ID(), stm.get_ID());
		
		if (count > 0) {
			String msg = "You still have " + count + " Bank/Cash Statement opened (not completed/closed)."
					+ " Are you sure to continue this action?";
			int retval = MessageBox.showMsg(
									stm, stm.getCtx(), msg, "Reactivate Confirmation.",
										MessageBox.YESNO, MessageBox.ICONQUESTION);
			return retval == MessageBox.RETURN_OK || MessageBox.RETURN_YES == retval;
		}
		
		return true;
	}
}