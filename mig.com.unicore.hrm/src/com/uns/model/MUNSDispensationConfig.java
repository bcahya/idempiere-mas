/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/**
 * @author eko
 *
 */
public class MUNSDispensationConfig extends X_UNS_DispensationConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_DispensationConfig_ID
	 * @param trxName
	 */
	public MUNSDispensationConfig(Properties ctx,
			int UNS_DispensationConfig_ID, String trxName) {
		super(ctx, UNS_DispensationConfig_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDispensationConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static int getIDOf(String value, String trxName)
	{
		String sql = "SELECT UNS_DispensationConfig_ID FROM UNS_DispensationConfig WHERE Value=?";
		
		int retID = DB.getSQLValue(trxName, sql, value);
		return retID;
	}
	
	/**
	 *
	 * @param contractType
	 * @return
	 */
	public boolean isPayableFor(String contractType)
	{
		if(MUNSContractRecommendation.NEXTCONTRACTTYPE_Borongan.equals(contractType))
			return isBorongan();
		else if(MUNSContractRecommendation.NEXTCONTRACTTYPE_Contract1.equals(contractType))
			return isContract1();
		else if(MUNSContractRecommendation.NEXTCONTRACTTYPE_Contract2.equals(contractType))
			return isContract2();
		else if(MUNSContractRecommendation.NEXTCONTRACTTYPE_Permanen.equals(contractType))
			return isPermanent();
		else if(MUNSContractRecommendation.NEXTCONTRACTTYPE_Recontract1.equals(contractType))
			return isRecontract1();
		else if(MUNSContractRecommendation.NEXTCONTRACTTYPE_Recontract2.equals(contractType))
			return isRecontract2();
		else if(MUNSContractRecommendation.NEXTCONTRACTTYPE_SquenceContract.equals(contractType))
			return isSequenceContract();
		else
			throw new AdempiereException("Unhandled Contract Type : " + contractType);
	}


	/**
	 * 
	 * @param dispensationCode
	 * @return true if it is of "keguguran", false otherwise.
	 */
	public boolean isKeguguran()
	{	
		return isKeguguran(getValue()); 
	}
	
	/**
	 * 
	 * @param dispensationCode
	 * @return
	 */
	public static boolean isKeguguran(String dispensationCode)
	{
		return dispensationCode != null && !dispensationCode.isEmpty()? dispensationCode.equals("KGR") : false; 
	}
	
	/**
	 * 
	 * @param config
	 * @return
	 */
	public static boolean isKeguguran(I_UNS_DispensationConfig config)
	{
		if (config == null)
			return false;
		
		return isKeguguran(config.getValue());
	}
}
