/**
 * 
 */
package com.uns.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author menjangan
 *
 */
public class MUNSMRPWeekly extends X_UNS_MRPWeekly implements Comparable<MUNSMRPWeekly>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_MRPWeekly_ID
	 * @param trxName
	 */
	public MUNSMRPWeekly(Properties ctx, int UNS_MRPWeekly_ID, String trxName) {
		super(ctx, UNS_MRPWeekly_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMRPWeekly(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param UNS_MRP_ID
	 * @param WeekNo
	 * @return MUNSMRPWeekly
	 */
	public static MUNSMRPWeekly get(int UNS_MRP_ID, int WeekNo, String trxName)
	{
		MUNSMRPWeekly M = null;
		String sql = "SELECT * FROM UNS_MRPWeekly WHERE UNS_MRP_ID =? AND WeekNo =?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = DB.prepareStatement(sql, trxName);
			stm.setInt(1, UNS_MRP_ID);
			stm.setInt(2, WeekNo);
			rs = stm.executeQuery();
			if (rs.next())
				M = new MUNSMRPWeekly(Env.getCtx(), rs, trxName);
		} catch (Exception ex) {
			ex.fillInStackTrace();
		}finally {
			DB.close(rs,stm);
		}
		
		return M;
	}
	
	
	/**
	 * 
	 * @param trxName
	 * @param WeekNoFrom
	 * @param WeekNoTo
	 * @param processed
	 * @return
	 */
	public static MUNSMRPWeekly[] get(Properties ctx, 
									  int WeekNoFrom, 
									  int WeekNoTo,
									  boolean nullRequisition, 
									  boolean nullPO, 
									  String orderByClause,
									  String trxName)
	{
		MUNSMRPWeekly[] mrpWeekly = null;
		List<X_UNS_MRPWeekly> listOfWeekly = new ArrayList<X_UNS_MRPWeekly>();

		String sqlWhereClause = " SOR=0 ";
		
		if (WeekNoFrom > 0)
			sqlWhereClause += "  AND WeekNo >= " + WeekNoFrom + " AND WeekNo <= " + WeekNoTo;
		
		if (nullRequisition)
			sqlWhereClause += " AND (RequisitionRef_ID IS NULL OR RequisitionRef_ID=0) ";
		
		if (nullPO)
			sqlWhereClause += " AND (PORef_ID IS NULL OR PORef_ID=0) ";
		
		String sql = "SELECT mw.* FROM " + MUNSMRPWeekly.Table_Name + " mw INNER JOIN "
				+ MUNSMRP.Table_Name + " m ON m." + MUNSMRP.COLUMNNAME_UNS_MRP_ID
				+ " = mw." + MUNSMRPWeekly.COLUMNNAME_UNS_MRP_ID 
				+ " AND m." + MUNSMRP.COLUMNNAME_UNS_MPSHeader_ID + " IS NOT NULL OR m."
				+ MUNSMRP.COLUMNNAME_UNS_MPSHeader_ID + " > 0 "
				+ " WHERE " + sqlWhereClause 
				+ " " + orderByClause;
		
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			st = DB.prepareStatement(sql, trxName);
			rs = st.executeQuery();
			while (rs.next()) {
				MUNSMRPWeekly mrpWeeklyTolist = new MUNSMRPWeekly(ctx, rs, trxName);
				listOfWeekly.add(mrpWeeklyTolist);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, st);
		}
		
		mrpWeekly = new MUNSMRPWeekly[listOfWeekly.size()];
		listOfWeekly.toArray(mrpWeekly);
		
		return mrpWeekly;
	}
	

	/**
	 * 
	 * @param From
	 *
	public void copyFrom(MUNSMRPWeekly From)
	{
		setAD_Org_ID(From.getAD_Org_ID());
		setEndDate(From.getEndDate());
		setgrosrequirement(From.getgrosrequirement());
		setGRManufacturing(getgrosrequirement());
		setStartDate(From.getStartDate());
		setWeekNo(From.getWeekNo());
	}
	
	public void setScaleAllQty()
	{
		setgrosrequirement(getgrosrequirement().setScale(2, RoundingMode.HALF_UP));
		setNetRequirement(getNetRequirement().setScale(2, RoundingMode.HALF_UP));
		setProjectedAvailable(getProjectedAvailable().setScale(2, RoundingMode.HALF_UP));
		setProjectedOnHand(getProjectedOnHand().setScale(2, RoundingMode.HALF_UP));
		setAATP(getAATP().setScale(2, RoundingMode.HALF_UP));
		setAROH(getAROH().setScale(2, RoundingMode.HALF_UP));
		setAORE(getAORE().setScale(2, RoundingMode.HALF_UP));
		setPOR(getPOR().setScale(2, RoundingMode.HALF_UP));
		setPORE(getPORE().setScale(2, RoundingMode.HALF_UP));
		setScheduleReceipt(getPOR().setScale(2, RoundingMode.HALF_UP));
		setGRManufacturing(getgrosrequirement().setScale(2, RoundingMode.HALF_UP));
	}
	*/
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		return super.beforeSave(newRecord);
	}

	@Override
	public int compareTo(MUNSMRPWeekly o) {
		// TODO Auto-generated method stub
		if (getStartDate().getTime() < o.getStartDate().getTime())
			return -1;
		else if (getStartDate().getTime() > o.getStartDate().getTime())
			return 1;
		else
			return 0;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		/*
		MUNSMRP parent = getParent();
		I_M_Product product = parent.getM_Product();
		double weight = product.getWeight().doubleValue();
		BigDecimal oldValueGrossReq = (BigDecimal)get_ValueOld(COLUMNNAME_grosrequirement);
		BigDecimal oldValueGrossManf = (BigDecimal)get_ValueOld(COLUMNNAME_GRManufacturing);
		if(null == oldValueGrossManf)
			oldValueGrossManf = BigDecimal.ZERO;
		if(null == oldValueGrossReq)
			oldValueGrossReq = BigDecimal.ZERO;
		
		double oldvalueGrossManfMT = oldValueGrossManf.doubleValue() * weight / 1000;
		double oldvalueGrossReqMT = oldValueGrossReq.doubleValue() * weight / 1000;
		
		double oldTotalGrosReq = parent.getGrandTotalUOM().doubleValue();
		double oldTotalGrosManf = parent.getTotalGrossManufacturUOM().doubleValue();
		
		double oldTotalGrosReqMT = parent.getGrandTotalMt().doubleValue();
		double oldTotalGrosManfMT = parent.getTotalGrossManufacturMT().doubleValue();
		
		double newGrosManf = getGRManufacturing().doubleValue();
		double newGrossReq = getgrosrequirement().doubleValue();
		double newGrosManfMT = newGrosManf * weight / 1000;
		double newGrosReqMT = newGrossReq * weight / 1000;
		
		oldTotalGrosReq -= oldValueGrossReq.doubleValue();
		oldTotalGrosReqMT -= oldvalueGrossReqMT;
		oldTotalGrosManf -= oldValueGrossManf.doubleValue();
		oldTotalGrosManfMT -= oldvalueGrossManfMT;
		double newTotalGrosmanfMT = oldTotalGrosManfMT + newGrosManfMT;
		double newTotalGrosManf = oldTotalGrosManf + newGrosManf;
		double newTotalGrosReqMT = oldTotalGrosReqMT + newGrosReqMT;
		double newTotalGrosReq = oldTotalGrosReq + newGrossReq;
		
		parent.setTotalGrossManufacturMT(new BigDecimal(newTotalGrosmanfMT));
		parent.setTotalGrossManufacturUOM(new BigDecimal(newTotalGrosManf));
		parent.setGrandTotalMt(new BigDecimal(newTotalGrosReqMT));
		parent.setGrandTotalUOM(new BigDecimal(newTotalGrosReq));
		parent.save();
		*/
		return super.afterSave(newRecord, success);
	}
	
	public MUNSMRP getParent()
	{
		return (MUNSMRP)getUNS_MRP();
	}

}
