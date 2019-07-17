/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DB;

/**
 * @author YAKA
 *
 */
public class MUNSSOShipment extends X_UNS_SOShipment {

	/**
	 * 
	 */
	private static final long serialVersionUID = -11047695803161936L;

	/**
	 * @param ctx
	 * @param UNS_SOShipment_ID
	 * @param trxName
	 */
	public MUNSSOShipment(Properties ctx, int UNS_SOShipment_ID, String trxName) {
		super(ctx, UNS_SOShipment_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSOShipment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSPSSOAllocation getSOAllocation(){
		String sql="SELECT UNS_PSSOAllocation_ID FROM UNS_PSSOAllocation WHERE C_OrderLine_ID=?";
		int id = DB.getSQLValue(get_TrxName(), sql, getC_OrderLine_ID());
		if(id == -1)
			return null;
		return new MUNSPSSOAllocation(getCtx(), id, get_TrxName());
	}
	
	public int getLocator(){
		MUNSPSSOAllocation psa = getSOAllocation();
		if(null == psa)
			throw new AdempiereException("SO Allocation not found");
		MUNSProductionSchedule ps =  psa.getParent();
		MUNSPSRealization[] psr = ps.getLinesReal();
		MUNSProductionDetail pd = new MUNSProductionDetail(getCtx(), psr[0].getUNS_Production_Detail_ID(), get_TrxName());
		
		return pd.getM_Locator_ID();
	}
	
	public static int get(int id, String columnName){
		String sql="SELECT UNS_SOShipment_ID FROM UNS_SOShipment WHERE "+columnName+"=?";
		int retVal = DB.getSQLValue(null, sql, id);
		if(retVal == -1)
			return 0;
		return retVal;
	}
	
	public static MUNSSOShipment get(Properties ctx, int ID, String COLUMNNAME, String trxName){
		MUNSSOShipment sos = null;
		if(ID<0)
			return sos;
		
		if(COLUMNNAME == null)
			sos = new MUNSSOShipment(ctx, ID, trxName);
		else 
		{
			int id = get(ID,COLUMNNAME);
			if (id > 0)
				sos= new MUNSSOShipment(ctx, get(ID,COLUMNNAME), trxName);
		}
		return sos;
	}
	
	@Override
	public boolean beforeSave(boolean isNew)
	{
		if (getM_AttributeSetInstance_ID() == 0 && getM_InOutLine_ID()!=0)
		{
			//create new attribute instance related to this product shipment.
			I_M_InOutLine inOutLine = getM_InOutLine();
			if (inOutLine.getM_AttributeSetInstance_ID() > 0)
			{
				setM_AttributeSetInstance_ID(inOutLine.getM_AttributeSetInstance_ID());
			}
		}
		return true;
	}
}
