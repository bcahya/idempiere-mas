/**
 * 
 */
package com.uns.qad.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author YAKA
 *
 */
public class MUNSQAStatusPRCLine extends X_UNS_QAStatus_PRCLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3349909121631187746L;
	private MUNSQAStatusNCRecord[] m_NClines;
	private MUNSQAMLabResult[] m_LabLines;

	/**
	 * @param ctx
	 * @param UNS_QAStatus_PRCLine_ID
	 * @param trxName
	 */
	public MUNSQAStatusPRCLine(Properties ctx, int UNS_QAStatus_PRCLine_ID,
			String trxName) {
		super(ctx, UNS_QAStatus_PRCLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSQAStatusPRCLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public MUNSQAStatusPRC getParent() {
		return new MUNSQAStatusPRC(getCtx(), getUNS_QAStatus_PRC_ID(), get_TrxName());
	}
	
	public MUNSQAStatusNCRecord getLastNCRecord(){
		String sql="SELECT UNS_QAStatus_NCRecord_ID FROM UNS_QAStatus_NCRecord " +
				"WHERE UNS_QAStatus_PRCLine_ID = ? ORDER BY UNS_QAStatus_NCRecord_ID DESC";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getUNS_QAStatus_PRCLine_ID());
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
						, MUNSQAStatusNCRecord.COLUMNNAME_UNS_QAStatus_PRCLine_ID + " =?"
						, get_TrxName())
		.setParameters(getUNS_QAStatus_PRCLine_ID())
		.setOrderBy(MUNSQAStatusNCRecord.COLUMNNAME_UNS_QAStatus_PRCLine_ID).list();
		
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

	@Override
	protected boolean beforeSave(boolean newRecord) {
		//TODO QASTATUS
//		if(MStorageOnHand.checkQAStatus(getQAStatus()).equals(QASTATUS_Release)){
//			setRelease(getCodeNo());
//			setReleaseQty(getQty());
//			setOnHold("-");
//			setOnHoldQty(BigDecimal.ZERO);
//			setNC("-");
//			setNCQty(BigDecimal.ZERO);
//		} else if(MStorageOnHand.checkQAStatus(getQAStatus()).equals(QASTATUS_OnHold)){
//			setRelease("-");
//			setReleaseQty(BigDecimal.ZERO);
//			setOnHold(getCodeNo());
//			setOnHoldQty(getQty());
//			setNC("-");
//			setNCQty(BigDecimal.ZERO);
//		} else 
//			setRemark("NC of product " + getM_Product().getName());
		
		BigDecimal sum = getReleaseQty().add(getOnHoldQty().add(getNCQty()));
		if (sum.intValue()>0 && sum.intValue()!=getQty().intValue())
			throw new AdempiereException("Could not save, Result not equal with total quantity.");
				
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) 
	{
//		if((newRecord || is_ValueChanged(COLUMNNAME_QAStatus)) && 
//				MStorageOnHand.checkQAStatus(getQAStatus()).equals(QASTATUS_OnHold))
//		{
//			MStorageOnHand soh = MStorageOnHand.get(getCtx(), getM_Locator_ID(), getM_Product_ID(), getM_AttributeSetInstance_ID(), get_TrxName());
//			if(soh != null)
//			{
//				soh.setQAStatus(getQAStatus());
//				soh.setOnHoldQty(getOnHoldQty());
//				if(!soh.save(get_TrxName()))
//				{
//					log.log(Level.SEVERE, "Could not save Storage On Hand for "
//							+ toString());
//					throw new AdempiereException(log.toString());
//				}
//			}
//			else {
//				log.log(Level.SEVERE, "QA Monitoring of PRC is only for available inventory.");
//				throw new AdempiereException(log.toString());
//			}
//		}
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean beforeDelete() {
		if (!getParent().getDocStatus().equals(MUNSQAStatusPRC.DOCSTATUS_Drafted))
			return false;
		return super.beforeDelete();
	}
	
	 /**
	 * @param requery
	 * @return MUNSQAMLabResult[]
	 */
	protected MUNSQAMLabResult[] getLabLines(boolean requery){
		if (m_LabLines != null	&& !requery){
			set_TrxName(m_LabLines, get_TrxName());
			return m_LabLines;
		}
		
		List<MUNSQAMLabResult> mList = new Query(getCtx(), MUNSQAMLabResult.Table_Name
					, MUNSQAMLabResult.COLUMNNAME_UNS_QAStatus_PRCLine_ID + " =?", get_TrxName())
			.setParameters(getUNS_QAStatus_PRCLine_ID())
			.setOrderBy(MUNSQAMLabResult.COLUMNNAME_UNS_QAMLab_Result_ID).list();
			
		m_LabLines = new MUNSQAMLabResult[mList.size()];
		mList.toArray(m_LabLines);
		return m_LabLines;
	}
		
	/**
	 * 
	 * @return MUNSQAMLabResult[]
	 */
	public MUNSQAMLabResult[] getLabLines()
	{
		return getLabLines(false);
	}
	
	public boolean updateQAStatus(){
//		int releaseCount = 0;
		
//		for (MUNSQAMLabResult lab : getLabLines()){
//			if(null != lab.getRelease() && !"-".equals(lab.getRelease())
//					&& !"".equals(lab.getRelease()))
//				releaseCount++;
//			if(MStorageOnHand.checkQAStatus(lab.getQAStatus()).equals(QASTATUS_Release))
//				releaseCount++;
//		}
//		
//		if (releaseCount==getLabLines().length)
//			setQAStatus(null);
//		else {
//			setQAStatus(QASTATUS_OnHold);
//		}
//		TODO QA STATUS
//		setQAStatus(MStorageOnHand.QASTATUS_QATested);
		return save();
	}
	
}
