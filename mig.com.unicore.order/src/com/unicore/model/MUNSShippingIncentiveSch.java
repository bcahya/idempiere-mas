package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

public class MUNSShippingIncentiveSch extends X_UNS_ShippingIncentiveSch
		implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3089421354512526868L;

	
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;
	
	
	// List<MUNSShippingIncentiveSch> list;

	
	public MUNSShippingIncentiveSch(Properties ctx,
			int UNS_ShippingIncentiveSch_ID, String trxName) {
		super(ctx, UNS_ShippingIncentiveSch_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSShippingIncentiveSch(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSShippingInctvSchLine[] m_Lines = null;
	
	public MUNSShippingInctvSchLine[] getLinesA(boolean requery) {
		if(null != m_Lines && !requery)
		{
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		
		//giri MUNSSHippuinginctvSchLine itu anaknya object ini? i /yclass ini  .. IY mas
		
		String whereClause = COLUMNNAME_UNS_ShippingIncentiveSch_ID
				.concat("=?");

		List<MUNSShippingInctvSchLine> list = Query
				.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID,
						MUNSShippingInctvSchLine.Table_Name, whereClause,
						get_TrxName()).setParameters(getUNS_ShippingIncentiveSch_ID()).list();
		
//		if(null == list || list.size() <=0)
//			return null;
		
		// atau giri check nya pada saat complete
		//if(getLinesA(true) == null || getLinesA(false).length <=0)
		// thrown (apalah apalah);
///udah yah iy makasih mas sama sama
		
		m_Lines = new MUNSShippingInctvSchLine[(list.size())];
		
		// kamu tau gak ini ngapain?
		//tau bikin instance buat ngisi nilainy m_line dari kelas MUNSShippingInctvSchLine
		// jadi nanti isi m_lineny itu list dari kelas MUNSShippingInctvSchLine
		// gak putus ko. ohh iy . terus kalo list nya gak ada / gak dapet tau return dari query.get nya apa?
		// dia return nya list dengan panjang 0
		// dia gak return null;
		// kalo return null maka pas MUNSShippingInctvSchLine[(list.size())]; akan error
		// jadi m_lines intu gak null tapi ada tapi 0
		//ngerti gak. bingung yah bahasa  gw
		// paham jadi itu buat size listny maka pas ngga ada isiny tetep dibuat array dengan value 0 bener gak?
		// [panjang array nya 0
		// terus harus gimana berarti paham gak?
		// iy" baru paham saya jadi nanti nilai m_lineny harusny 0
		// ada 2 solusi yang pertama
		list.toArray(m_Lines);
		return m_Lines;

	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {

		
		
		return super.beforeSave(newRecord);

	}

	@Override
	public boolean processIt(String processAction) {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());

	}

	@Override
	public boolean unlockIt() {
		if (log.isLoggable(Level.INFO))
			log.info("unlockIt - " + toString());
		setProcessed(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		if (log.isLoggable(Level.INFO))
			log.info("invalidateIt - " + toString());
		return true;
	} // invalidateIt

	@Override
	public String prepareIt() {
		if (log.isLoggable(Level.INFO))
			if (log.isLoggable(Level.INFO))
				log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//getLines();
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		if (log.isLoggable(Level.INFO))
			log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		
		
		if (!m_justPrepared) {
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_COMPLETE);
		
		//yang mana?
		
		if(getLinesA(true) == null || getLinesA(false).length <=0)
			throw new AdempiereException("zz");
		
//		if(m_Lines == null)
//			throw new AdempiereException("Line Kosong");
		// harusny begini
		// getLinesA return nya apaan?
		// m_lines. m_lines nya diisi gak di dalam getLines?
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (log.isLoggable(Level.INFO))
			log.info(toString());
		
			// User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null) {
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		StringBuffer sqlUpd = new StringBuffer(
				"UPDATE UNS_ShippingIncentiveSch SET docstatus = 'CL'"
						+ " WHERE crewTYpe = '"+getcrewTYpe()+"' "
						+ " AND AD_Org_ID = "+getAD_Org_ID()+"");
		DB.executeUpdate(sqlUpd.toString(), get_TrxName());

		setProcessed(true);
		setIsApproved(true);
		setDocAction(ACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		if (log.isLoggable(Level.INFO))
			log.info("voidIt - " + toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (!closeIt())
			return false;

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		return true;
	} // voidIt

	@Override
	public boolean closeIt() {
		if (log.isLoggable(Level.INFO))
			log.info("closeIt - " + toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		return true;
	} // closeIt

	@Override
	public boolean reverseCorrectIt() {
		if (log.isLoggable(Level.INFO))
			log.info("reverseCorrectIt - " + toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return false;
	} // reverseCorrectionIt

	@Override
	public boolean reverseAccrualIt() {
		if (log.isLoggable(Level.INFO))
			log.info("reverseAccrualIt - " + toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	} // reverseAccrualIt

	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO))
			log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		setProcessed(false);
		return true;
	} // reActivateIt

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return Msg.getElement(getCtx(), "UNS_ShippingIncentiveSch_ID") + " "
				+ getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index)
	{
		if (docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_ReActivate;
		}
		
		return index;
	}
}
