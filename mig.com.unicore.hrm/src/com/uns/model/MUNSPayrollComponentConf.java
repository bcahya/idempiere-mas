/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.PO;
import org.compiere.util.DB;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSPayrollComponentConf extends X_UNS_Payroll_Component_Conf {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1367060548578622719L;

	/**
	 * @param ctx
	 * @param UNS_Payroll_Component_Conf_ID
	 * @param trxName
	 */
	public MUNSPayrollComponentConf(Properties ctx,
			int UNS_Payroll_Component_Conf_ID, String trxName) {
		super(ctx, UNS_Payroll_Component_Conf_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPayrollComponentConf(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSPayrollComponentConf (MUNSContractRecommendation contract, MUNSPayrollComponentConf conf)
	{
		this (contract.getCtx(), 0, contract.get_TrxName());
		copyValues(conf, this);
		setClientOrg(contract);
		setUNS_Contract_Recommendation_ID(contract.get_ID());
		setUNS_PayrollLevel_Config_ID(-1);
	}
	
	public static MUNSPayrollComponentConf[] get (PO po)
	{
		String wc = po.get_TableName() + "_ID = ?";
		MUNSPayrollComponentConf[] comps = null;
		List<MUNSPayrollComponentConf> list = Query.get(
				po.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				wc, po.get_TrxName()).setParameters(po.get_ID()).setOrderBy("SeqNo").list();
		comps = new MUNSPayrollComponentConf[list.size()];
		list.toArray(comps);
		return comps;
	}
	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord && getSeqNo() == 0)
		{
			StringBuilder sqlB = new StringBuilder("SELECT MAX (").append(COLUMNNAME_SeqNo)
					.append(") FROM ").append(Table_Name).append(" WHERE ");
			List<Object> params = new ArrayList<>();
			if (getUNS_Contract_Recommendation_ID() > 0) {
				sqlB.append(COLUMNNAME_UNS_Contract_Recommendation_ID).append(" =? ");
				params.add(getUNS_Contract_Recommendation_ID());
			} else {
				sqlB.append(COLUMNNAME_UNS_PayrollLevel_Config_ID).append(" = ? ");
				params.add(getUNS_PayrollLevel_Config_ID());
			}
			
			int seqNo = DB.getSQLValue(get_TrxName(), sqlB.toString(), params);
			seqNo += 10;
			setSeqNo(seqNo);
		}
		return super.beforeSave(newRecord);
	}
}
