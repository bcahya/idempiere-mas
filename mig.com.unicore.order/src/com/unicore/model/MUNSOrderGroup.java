/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_Payment;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

import com.uns.util.MessageBox;

import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;

/**
 * @author Burhani Adam
 *
 */
public class MUNSOrderGroup extends X_UNS_OrderGroup implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9072532694352142109L;

	/**
	 * @param ctx
	 * @param UNS_OrderGroup_ID
	 * @param trxName
	 */
	public MUNSOrderGroup(Properties ctx, int UNS_OrderGroup_ID, String trxName) {
		super(ctx, UNS_OrderGroup_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSOrderGroup(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		if(!newRecord &&
				(is_ValueChanged(COLUMNNAME_C_BPartner_ID) || is_ValueChanged(COLUMNNAME_C_BPartner_Location_ID)))
		{
			for(org.compiere.model.MOrder order : getOrders())
			{
				order.setC_BPartner_ID(getC_BPartner_ID());
				order.setC_BPartner_Location_ID(getC_BPartner_Location_ID());
				order.saveEx();
			}
		}
		
		if(!newRecord && is_ValueChanged(COLUMNNAME_DateOrdered))
		{
			for(org.compiere.model.MOrder order : getOrders())
			{
				order.setDateOrdered(getDateOrdered());
				order.setDatePromised(getDateOrdered());
				order.saveEx();
			}
		}
		
		if(!newRecord && is_ValueChanged(COLUMNNAME_C_DocType_ID))
		{
			for(org.compiere.model.MOrder order : getOrders())
			{
				order.setC_DocTypeTarget_ID(getC_DocType_ID());
				order.saveEx();
			}
		}
		
		if(!newRecord && is_ValueChanged(COLUMNNAME_PaymentRule))
		{
			for(org.compiere.model.MOrder order : getOrders())
			{
				order.setPaymentRule(getPaymentRule());
				order.saveEx();
			}
		}
		
		if(!newRecord && is_ValueChanged(COLUMNNAME_PaidAmt))
		{
			if(getC_DocType_ID() == 1000097)
				setWriteOffAmt(getGrandTotal().subtract(getPaidAmt()));
		}
		return true;
	}
	
	protected boolean beforeDelete()
	{
		for(org.compiere.model.MOrder order: getOrders())
		{
			order.delete(false, get_TrxName());
		}
		
		return true;
	}
	
	public MOrder[] getOrders()
	{
		List<MOrder> list = new ArrayList<>();
		String sql = "SELECT * FROM C_Order WHERE UNS_OrderGroup_ID = ?";
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, get_ID());
			rs = stmt.executeQuery();
			while(rs.next())
			{
				list.add(new MOrder(getCtx(), rs, get_TrxName()));
			}
		}
		catch(SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		return list.toArray(new MOrder[list.size()]);
	}
	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	process
	
	/**	Process Message 			*/
	private String			m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean 		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success 
	 */
	public boolean unlockIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
		setProcessed(false);
		return true;
	}	//	unlockIt
	
	/**
	 * 	Invalidate Document
	 * 	@return true if success 
	 */
	public boolean invalidateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("invalidateIt - " + toString());
		return true;
	}	//	invalidateIt
	
	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	public String prepareIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(getOrders().length == 0)
		{
			m_processMsg = "No Orders";
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}	//	prepareIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	
	public String completeIt()
	{
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		for(org.compiere.model.MOrder order : getOrders())
		{
			if(order.isComplete() || order.getDocStatus().equals(DOCSTATUS_WaitingPayment))
			{
//				if(order.getC_Payment_ID() <= 0 && !createPayment())
//				{
//					m_processMsg = "Failed when trying create payment";
//					return DocAction.STATUS_Invalid;
//				}
				continue;
			}
			
			try {
				order.processIt(DocAction.ACTION_Complete);
				order.saveEx();
			} catch (Exception e) {
				throw new AdempiereException(e.getLocalizedMessage());
			}
		}
		
		String sql = "SELECT COUNT(*) FROM C_Order WHERE UNS_OrderGroup_ID = ? AND DocStatus NOT IN ('CO', 'CL', 'VO', 'RE')";
		if(DB.getSQLValue(get_TrxName(), sql, get_ID()) > 0)
		{
			MessageBox.showMsg(this, getCtx(), "Please complete all orders", "Information",
					MessageBox.OK, MessageBox.ICONINFORMATION);
			m_processMsg = "Please complete all orders";
			setProcessed(true);
			return DocAction.STATUS_InProgress;
		}
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}		
		//
		setProcessed(true);
		setDocAction(ACTION_Close);
		setIsApproved(true);
		return DocAction.STATUS_Completed;
	}	//	completeIt

	/**
	 * 	Void Document.
	 * 	Same as Close.
	 * 	@return true if success 
	 */
	public boolean voidIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("voidIt - " + toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		for(org.compiere.model.MOrder order : getOrders())
		{
			if(!DOCSTATUS_Reversed.equals(order.getDocStatus())
					&& !DOCSTATUS_Voided.equals(order.getDocStatus()))
			{
				try {
					order.processIt(DocAction.ACTION_Void);
					order.saveEx();
				} catch (Exception e) {
					throw new AdempiereException(e.getLocalizedMessage());
				}
			}
		}
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		setDocStatus(DOCSTATUS_Voided);
		setProcessed(true);
		setDocAction(DocAction.ACTION_None);
		return true;
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	Cancel not delivered Quantities
	 * 	@return true if success 
	 */
	public boolean closeIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("closeIt - " + toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		
		setDocStatus(DOCSTATUS_Closed);
		setDocAction(DocAction.ACTION_None);
		setProcessed(true);
		
		return true;
	}	//	closeIt
	
	/**
	 * 	Reverse Correction
	 * 	@return true if success 
	 */
	public boolean reverseCorrectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reverseCorrectIt - " + toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return voidIt();
	}	//	reverseCorrectionIt
	
	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success 
	 */
	public boolean reverseAccrualIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reverseAccrualIt - " + toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;				
		
		return voidIt();
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		for(org.compiere.model.MOrder order : getOrders())
		{
			if(!DOCSTATUS_Reversed.equals(order.getDocStatus())
					&& !DOCSTATUS_Voided.equals(order.getDocStatus())
						&& !DOCSTATUS_Closed.equals(order.getDocStatus()))
			{
				if(order.getC_Payment_ID() > 0)
				{
					MPayment pay = new MPayment(getCtx(), order.getC_Payment_ID(), get_TrxName());
					if(!pay.processIt(ACTION_Reverse_Correct) && !pay.save())
					{
						m_processMsg = pay.getProcessMsg();
						return false;
					}
				}
				try {
					order.processIt(DocAction.ACTION_ReActivate);
					order.saveEx();
				} catch (Exception e) {
					throw new AdempiereException(e.getLocalizedMessage());
				}
			}
			else
			{
				m_processMsg = "order " + order.getDocumentNo() + " cannot reactivate";
				return false;
			}
		}
		
		setProcessed(false);
		setDocAction(DOCACTION_Prepare);
		setDocStatus(DOCSTATUS_Drafted);
		saveEx();
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	reActivateIt

	@Override
	public String getSummary() {
		return getDocumentInfo();
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return getPaidAmt();
	}

	@Override
	public boolean approveIt() {
		setIsApproved(true);
		setProcessed(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		setIsApproved(false);
		setProcessed(false);
		return true;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
				String orderType, String isSOTrx, int AD_Table_ID,
					String[] docAction, String[] options, int index)
	{
		if (docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_ReActivate;
			options[index++] = DocAction.ACTION_Void;
		}	
		return index;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isComplete()
	{
		return getDocStatus().equals("CO") || getDocStatus().equals("CL");
	}
	
	public boolean isVoided()
	{
		return getDocStatus().equals("RE") || getDocStatus().equals("VO");
	}
	
	public boolean createPayment()
	{
		MOrder[] orders = getOrders();
		for(int i = 0; i < orders.length; i++)
		{
			if(orders[i].getC_Payment_ID() > 0)
				continue;
			MPayment pay = new MPayment(getCtx(), 0, get_TrxName());
			pay.setAD_Org_ID(orders[i].getAD_Org_ID());
			pay.setC_BankAccount_ID(MBankAccount.getDefault(getCtx(), orders[i].getAD_Org_ID(), get_TrxName()).get_ID());
			pay.setIsReceipt(true);
			pay.setC_BPartner_ID(getC_BPartner_ID());
			pay.setDateAcct(getDateOrdered());
			pay.setDateTrx(getDateOrdered());
			pay.setTenderType(X_C_Payment.TENDERTYPE_Cash);
			pay.setSalesRep_ID(orders[i].getSalesRep_ID());
			if(!pay.save())
				return false;
			
			MPaymentAllocate allocate = new MPaymentAllocate(getCtx(), 0, get_TrxName());
			allocate.setC_Payment_ID(pay.get_ID());
			allocate.setC_Order_ID(orders[i].getAD_Org_ID());
			allocate.setAD_Org_ID(orders[i].getAD_Org_ID());
			allocate.setInvoiceAmt(orders[i].getGrandTotal());
			allocate.setAmount(orders[i].getPaidAmt());
			allocate.setPayToOverUnderAmount(orders[i].getPaidAmt());
			allocate.setWriteOffAmt(orders[i].getWriteOff());
			if(!allocate.save())
				return false;
			
			if(!pay.processIt(DocAction.ACTION_Complete) && !pay.save())
				return false;
			
			orders[i].setC_Payment_ID(pay.get_ID());
			if(!orders[i].save())
				return false;
		}
		
		return true;
	}
	
	public boolean createPackingList()
	{
		MUNSPackingList pl = new MUNSPackingList(getCtx(), 0, get_TrxName());
		pl.setAD_Org_ID(getAD_Org_ID());
		pl.setName("Create from Order Group " + getDocumentNo());
		pl.setDateDoc(getDateOrdered());
		pl.setIsPickup(isPickup());
		if(!pl.save())
			return false;
		MOrder[] orders = getOrders();
		for(int i = 0; i < orders.length; i++)
		{
			MUNSPackingListOrder plo = new MUNSPackingListOrder(orders[i]);
			if(!plo.save())
				return false;
			
			MOrderLine[] lines = orders[i].getLines();
			for(int j = 0; j < lines.length; j++)
			{
				MUNSPackingListLine pll = new MUNSPackingListLine(lines[i]);
				if(!pll.save())
					return false;
			}
		}
		
		if(!pl.processIt(DocAction.ACTION_Complete) && !pl.save())
			return false;
		
		setUNS_PackingList_ID(pl.get_ID());
		if(!save())
			return false;
		
		return true;
	}
	
//	 public boolean upHeaderGroup()
//	 {
//    	String sql = "SELECT SUM(PaidAmt) FROM C_Order WHERE UNS_OrderGroup_ID = ?";
//    	BigDecimal paidAmt = DB.getSQLValueBD(get_TrxName(), sql, getUNS_OrderGroup_ID());
//    	if(paidAmt == null)
//    		paidAmt = Env.ZERO;
//    	
//    	sql = "SELECT SUM(GrandTotal) FROM C_Order WHERE UNS_OrderGroup_ID = ?";
//    	BigDecimal grandTotal = DB.getSQLValueBD(get_TrxName(), sql, getUNS_OrderGroup_ID());
//    	if(grandTotal == null)
//    		grandTotal = Env.ZERO;
//    	
//    	sql = "SELECT C_Order_ID FROM C_Order WHERE UNS_OrderGroup_ID = ?";
//    	int a = DB.getSQLValue(get_TrxName(), sql, get_ID());
//    	MOrder order = new MOrder(getCtx(), a, get_TrxName());
//    	BigDecimal b = order.getGrandTotal();
//    	
//    	setPaidAmt(paidAmt);
//    	setGrandTotal(grandTotal);
//    	
//    	return save();
//    }
}