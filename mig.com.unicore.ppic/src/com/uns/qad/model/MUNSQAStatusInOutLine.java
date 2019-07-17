/**
 * 
 */
package com.uns.qad.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author YAKA
 *
 */
public class MUNSQAStatusInOutLine extends X_UNS_QAStatus_InOutLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4136616919823699976L;
	private MUNSQAStatusAttributeTest[] m_lines;
	private MUNSQAStatusNCRecord[] m_NClines;

	/**
	 * @param ctx
	 * @param UNS_QAStatus_InOutLine_ID
	 * @param trxName
	 */
	public MUNSQAStatusInOutLine(Properties ctx, int UNS_QAStatus_InOutLine_ID,
			String trxName) {
		super(ctx, UNS_QAStatus_InOutLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSQAStatusInOutLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	 /**
	 * @param requery
	 * @return MUNSQAStatusContainer[]
	 */
	protected MUNSQAStatusAttributeTest[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQAStatusAttributeTest> mList =
				new Query(getCtx(), MUNSQAStatusAttributeTest.Table_Name
						, MUNSQAStatusAttributeTest.COLUMNNAME_UNS_QAStatus_InOutLine_ID + " =?"
						, get_TrxName())
		.setParameters(getUNS_QAStatus_InOutLine_ID())
		.setOrderBy(MUNSQAStatusAttributeTest.COLUMNNAME_UNS_QAStatus_InOutLine_ID).list();
		
		m_lines = new MUNSQAStatusAttributeTest[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * 
	 * @return MUNSQAStatusContainer[]
	 */
	public MUNSQAStatusAttributeTest[] getLines()
	{
		return getLines(false);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		//Initialize
		if(getConfirmedQty().intValue()==0)
			setConfirmedQty(getTargetQty());
		if(getQAStatus() == null)
			setQAStatus(QASTATUS_PendingInspectionLabTest);
		String msg = "Illegal Quantity, ";
		BigDecimal sum = getNCQty().add(getOnHoldQty()).add(getReleaseQty());
//TODO QA Status
//		if(MStorageOnHand.checkQAStatus(getQAStatus()).equals(QASTATUS_OnHold)){
//			setOnHoldQty(getConfirmedQty());
//			setReleaseQty(Env.ZERO);
//			setNCQty(Env.ZERO);
//		} else if (MStorageOnHand.checkQAStatus(getQAStatus()).equals(QASTATUS_Release)){
//			setOnHoldQty(Env.ZERO);
//			setReleaseQty(getConfirmedQty());
//			setNCQty(Env.ZERO);
//		}
		
		//condition for NC
		if (getQAStatus().equals(QASTATUS_NonConformance)){
			if(getRemark()==null)
				setRemark("NC for Product "+getM_Product().getName() 
					+ ", nc Qty:"+getNCQty().setScale(2, RoundingMode.HALF_UP));
			if(getOnHoldQty().intValue()!=0)
				throw new AdempiereUserError(msg + "Set OnHold Qty to Zero.");
		}
		
		if (getSampleQty().compareTo(getConfirmedQty())>0)
			throw new AdempiereUserError(msg + "Sample Quantity can't bigger than Confirmed Quantity.");
		else if (sum.compareTo(getConfirmedQty())>0)
			throw new AdempiereUserError(msg + "Total of result can't bigger than Confirmed Quantity.");
		else if (sum.compareTo(Env.ZERO)>0 && sum.compareTo(getConfirmedQty())!=0)
			throw new AdempiereUserError(msg + "Total of result must same with Confirmed Quantity.");
		
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (getUNS_QAStatus_Type_ID()!=0 && is_ValueChanged(COLUMNNAME_UNS_QAStatus_Type_ID)){
			if(!createListAttributeTest())
				throw new AdempiereException("Error when create list atribute test.");
		}
			
		return super.afterSave(newRecord, success);
	}
	
	@Override
	protected boolean beforeDelete() {
		return false;
	}
	

	protected boolean createListAttributeTest(){
		for(MUNSQAStatusAttributeTest test : getLines()){
			test.delete(false);
		}
		
		MUNSQAStatusType type = new MUNSQAStatusType(getCtx(), getUNS_QAStatus_Type_ID(), get_TrxName());
		for (MUNSQAMonitoringAttribut attribut : type.getLines()){
			MUNSQAStatusAttributeTest new_sat = new MUNSQAStatusAttributeTest(getCtx(), 0, get_TrxName());
			new_sat.setAD_Org_ID(getAD_Org_ID());
			new_sat.setUNS_QAAttributeTest_ID(attribut.getUNS_QAAttributeTest_ID());
			new_sat.setUNS_QAStatus_InOutLine_ID(get_ID());
			new_sat.setName("(None)");
			if (!new_sat.save())
				return false;
		}
		return true;
	}	
	
	public MUNSQAStatusInOut getParent(){
		return new MUNSQAStatusInOut(getCtx(), getUNS_QAStatus_InOut_ID(), get_TrxName());
	}

	public static int get(int id, String columnName){
		String sql="SELECT UNS_QAStatus_InOutLine_ID FROM UNS_QAStatus_InOutLine WHERE "+columnName+"=?";
		int retVal = DB.getSQLValue(null, sql, id);
		if(retVal == -1)
			return 0;
		return retVal;
	}
	
	public static MUNSQAStatusInOutLine get(Properties ctx, int ID, String COLUMNNAME, String trxName){
		MUNSQAStatusInOutLine qasInOutLine = null;
		if(ID<0)
			return qasInOutLine;
		
		if(COLUMNNAME == null)
			qasInOutLine = new MUNSQAStatusInOutLine(ctx, ID, trxName);
		else 
		{
			int id = get(ID,COLUMNNAME);
			if (id > 0)
				qasInOutLine= new MUNSQAStatusInOutLine(ctx, get(ID,COLUMNNAME), trxName);
		}
		return qasInOutLine;
	}
	
	public MUNSQAStatusNCRecord getLastNCRecord(){
		String sql="SELECT UNS_QAStatus_NCRecord_ID FROM UNS_QAStatus_NCRecord " +
				"WHERE UNS_QAStatus_InOutLine_ID = ? ORDER BY UNS_QAStatus_NCRecord_ID DESC";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getUNS_QAStatus_InOutLine_ID());
		if(retVal == -1)
			return null;
		return new MUNSQAStatusNCRecord(getCtx(), retVal, get_TrxName());
	}

	 /**
	 * @param requery
	 * @return MUNSQAStatusNCRecord[]
	 */
	protected MUNSQAStatusNCRecord[] getNCLines(boolean requery){
		if (m_NClines != null	&& !requery){
			set_TrxName(m_NClines, get_TrxName());
			return m_NClines;
		}
		
		List<MUNSQAStatusNCRecord> mList =
				new Query(getCtx(), MUNSQAStatusNCRecord.Table_Name
						, MUNSQAStatusNCRecord.COLUMNNAME_UNS_QAStatus_InOutLine_ID + " =?"
						, get_TrxName())
		.setParameters(getUNS_QAStatus_InOutLine_ID())
		.setOrderBy(MUNSQAStatusNCRecord.COLUMNNAME_UNS_QAStatus_InOutLine_ID).list();
		
		m_NClines = new MUNSQAStatusNCRecord[mList.size()];
		mList.toArray(m_NClines);
		return m_NClines;
	}
	
	/**
	 * 
	 * @return MUNSQAStatusNCRecord[]
	 */
	public MUNSQAStatusNCRecord[] getNCLines()
	{
		return getNCLines(false);
	}	
	
}
