/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSPayRollLine extends X_UNS_PayRollLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3964867934061268469L;
	private MUNSPayRollPremiTarget[] m_lines = null;

	/**
	 * @param ctx
	 * @param UNS_PayRollLine_ID
	 * @param trxName
	 */
	public MUNSPayRollLine(Properties ctx, int UNS_PayRollLine_ID,
			String trxName) {
		super(ctx, UNS_PayRollLine_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPayRollLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param requery
	 * @return MUNSPayRollPremiTarget[]
	 */
	public MUNSPayRollPremiTarget[] getLines(boolean requery)
	{
		if (null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		String whereClause = X_UNS_PayRollPremiTarget.COLUMNNAME_UNS_PayRollLine_ID + "=?";
		List<MUNSPayRollPremiTarget> list = 
				Query.get( getCtx(), UNSHRMModelFactory.EXTENSION_ID, X_UNS_PayRollPremiTarget.Table_Name, 
						whereClause, get_TrxName())
				.setParameters(getUNS_PayRollLine_ID())
				.setOrderBy(MUNSPayRollPremiTarget.COLUMNNAME_TargetMin + " ASC")
				.list();
		m_lines = new MUNSPayRollPremiTarget[list.size()];
		return list.toArray(m_lines);
	}
	
	/**
	 * 
	 * @param totalAbsence
	 * @param absenceType
	 * @return
	 */
	public MUNSPayRollPremiTarget getCriteriaTarget(BigDecimal totalAbsence, String absenceType)
	{
		for(MUNSPayRollPremiTarget pp : getLines(false))
		{
			if(!absenceType.equals(pp.getAbsenType())
					&& !MUNSPayRollPremiTarget.ABSENTYPE_AllAbsenceType.equals(pp.getAbsenType()))
				continue;
			
			if(pp.getTargetMax().compareTo(BigDecimal.ZERO) <= 0)
				pp.setTargetMax(new BigDecimal(999999999999.99));
			
			if(totalAbsence.compareTo(pp.getTargetMin()) >=0
					&& totalAbsence.compareTo(pp.getTargetMax()) <=0)
				return pp;
			/*
			if(totalAbsence >= pp.getTargetMin().intValue()
					&& totalAbsence <= pp.getTargetMax().intValue())
			*/
		}
		return null;
	}
	
	/**
	 * 
	 * @param qty
	 * @param date
	 * @return
	 * 
	 */
	public MUNSPayRollPremiTarget getCriteriaTarget(BigDecimal qty)
	{
		for (MUNSPayRollPremiTarget ppTarget : getLines(false))
		{
			if (ppTarget.getTargetMax().compareTo(BigDecimal.ZERO)<=0)
				ppTarget.setTargetMax(new BigDecimal(99999999999999999999999999999999999999.999999999));
			if (qty.compareTo(ppTarget.getTargetMin())>=0
					&& qty.compareTo(ppTarget.getTargetMax()) <= 0)
				return ppTarget;
		}
		return null;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (CRITERIATYPE_Daily.equals(getCriteriaType()) && newRecord)
		{
			String sql = "SELECT UNS_PayRollLine_ID FROM UNS_PayRollLine " +
					"WHERE UNS_Job_Role_ID=? AND payrollTerm=? AND CriteriaType=?";
			int count = DB.getSQLValue(get_TrxName(), sql, 
									   getUNS_Job_Role_ID(), getPayrollTerm(), getCriteriaType());
			
			if (count > 0) 
			{
				throw new AdempiereException("Daily payroll definition for Job-Role " 
											 + getUNS_Job_Role().getName() + " with Payroll Term of "
											 + getPayrollTerm() + " already exist.");
			}
		}
		
		for (MUNSPayRollPremiTarget payPremiTarget : getLines(false))
		{
			payPremiTarget.setIsActive(isActive());
			payPremiTarget.save();
		}
		if(null == getName())
		{
			setName("Criteria Line " + getM_Product().getName());
		}
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean beforeDelete()
	{
		for (MUNSPayRollPremiTarget payPremiTarget : getLines(false))
		{
			if (! payPremiTarget.delete(true))
				ErrorMsg.setErrorMsg(getCtx(), "Delete Failed", "Failde to delete payroll premi target");
		}
		return true;
	}
	
	public boolean isAccomplishement()
	{
		return getCriteriaType().equals(CRITERIATYPE_Accomplishment);
	}
	
	

}
