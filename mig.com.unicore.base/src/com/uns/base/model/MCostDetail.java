/**
 * 
 */
package com.uns.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.MAcctSchema;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

import com.uns.base.model.Query;


/**
 * @author YAKA
 *
 */
public class MCostDetail extends org.compiere.model.MCostDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2374624128071819296L;

	protected boolean m_isMaterialCost = true;
	
	protected boolean m_isAdjustment = false;
	
	/**
	 * 
	 * @param ctx
	 * @param C_Order_ID
	 * @param trxName
	 */
	public MCostDetail(Properties ctx, int M_CostDetail_ID, String trxName) {
		super(ctx, M_CostDetail_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MCostDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MCostDetail(MAcctSchema as, int aD_Org_ID, int m_Product_ID,
			int m_AttributeSetInstance_ID, int m_CostElement_ID,
			BigDecimal amt, BigDecimal qty, String description, String trxName) {
		super(as, aD_Org_ID, m_Product_ID, m_AttributeSetInstance_ID, m_CostElement_ID, amt,
				qty, description, trxName);
		// TODO Auto-generated constructor stub
	}

	/**	Logger	*/
	private static CLogger 	s_log = CLogger.getCLogger (MCostDetail.class);
	
	/**
	 * 	Create New Order Cost Detail for Production.
	 * 	Called from Doc_Production
	 *	@param as accounting schema
	 *	@param AD_Org_ID org
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param M_ProductionLine_ID production line
	 *	@param M_CostElement_ID optional cost element
	 *	@param Amt amt total amount
	 *	@param Qty qty
	 *	@param Description optional description
	 *	@param trxName transaction
	 *	@return true if no error
	 */
	public static boolean createProduction (MAcctSchema as, int AD_Org_ID, String extensionID,
		int M_Product_ID, int M_AttributeSetInstance_ID,
		int UNS_Production_Detail_ID, int M_CostElement_ID, 
		BigDecimal Amt, BigDecimal Qty,
		String Description, String trxName)
	{
		//	Delete Unprocessed zero Differences
		StringBuilder sql = new StringBuilder("DELETE M_CostDetail ")
			.append("WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0")
			.append(" AND UNS_Production_Detail_ID=").append(UNS_Production_Detail_ID)
			.append(" AND C_AcctSchema_ID =").append(as.getC_AcctSchema_ID())
			.append(" AND M_AttributeSetInstance_ID=").append(M_AttributeSetInstance_ID);
		int no = DB.executeUpdate(sql.toString(), trxName);
		if (no != 0)
			s_log.config("Deleted #" + no);
		MCostDetail cd = get (as.getCtx(), "UNS_Production_Detail_ID=?" 
			, extensionID, UNS_Production_Detail_ID
			, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trxName);
			cd.setUNS_Production_Detail_ID(UNS_Production_Detail_ID);
		}
		else
		{
			// MZ Goodwill
			// set deltaAmt=Amt, deltaQty=qty, and set Cost Detail for Amt and Qty	 
			cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
			cd.setDeltaQty(Qty.subtract(cd.getQty()));
			if (cd.isDelta())
			{
				cd.setProcessed(false);
				cd.setAmt(Amt);
				cd.setQty(Qty);
			}
			// end MZ
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			ok = cd.process();
		}
		s_log.config("(" + ok + ") " + cd);
		return ok;
	}	//	createProduction
	
	/**************************************************************************
	 * 	Get Cost Detail
	 *	@param ctx context
	 *	@param whereClause where clause
	 *	@param ID 1st parameter
	 *  @param M_AttributeSetInstance_ID ASI
	 *	@param trxName trx
	 *	@return cost detail
	 */
	public static MCostDetail get (Properties ctx, String whereClause, String extensionID, 
		int ID, int M_AttributeSetInstance_ID, int C_AcctSchema_ID, String trxName)
	{
		StringBuilder localWhereClause = new StringBuilder(whereClause)
			.append(" AND M_AttributeSetInstance_ID=?")
			.append(" AND C_AcctSchema_ID=?");
		
		MCostDetail retValue = Query.get(ctx, extensionID, MCostDetail.Table_Name, localWhereClause.toString(), trxName)
		.setParameters(ID,M_AttributeSetInstance_ID,C_AcctSchema_ID)
		.first();
		return retValue;
	}	//	get
	
	
	public static boolean createProductionWorker (MAcctSchema as, int AD_Org_ID, String extensionID,
			int M_Product_ID, int M_AttributeSetInstance_ID,
			int UNS_Production_ID, int C_BPartner_ID, int M_CostElement_ID, 
			BigDecimal Amt, BigDecimal Qty,
			String Description, String trxName)
	{
		//	Delete Unprocessed zero Differences
		StringBuilder sql = new StringBuilder("DELETE M_CostDetail ")
			.append("WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0")
			.append(" AND UNS_Production_ID =").append(UNS_Production_ID)
			.append(" AND C_Bpartner_ID =").append(C_BPartner_ID)
			.append(" AND C_AcctSchema_ID =").append(as.getC_AcctSchema_ID())
			.append(" AND M_AttributeSetInstance_ID =").append(M_AttributeSetInstance_ID);
		int no = DB.executeUpdate(sql.toString(), trxName);
		if (no != 0)
			s_log.config("Deleted #" + no);
		MCostDetail cd = get (as.getCtx(), "UNS_Production_ID=" + UNS_Production_ID + " AND C_BPartner_ID =?"
				, extensionID, C_BPartner_ID
				, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trxName);
			cd.setC_BPartner_ID(C_BPartner_ID);
			cd.setUNS_Production_ID(UNS_Production_ID);
			cd.m_isAdjustment = true;
			cd.m_isMaterialCost = false;
		}
		else
		{
			// MZ Goodwill
			// set deltaAmt=Amt, deltaQty=qty, and set Cost Detail for Amt and Qty	 
			cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
			cd.setDeltaQty(Qty.subtract(cd.getQty()));
			if (cd.isDelta())
			{
				cd.setProcessed(false);
				cd.setAmt(Amt);
				cd.setQty(Qty);
			}
			// end MZ
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			ok = cd.process();
		}
		s_log.config("(" + ok + ") " + cd);
		return ok;
	}	//	createProduction
	
	
	/**
	 * 
	 * @param as
	 * @param AD_Org_ID
	 * @param M_Product_ID
	 * @param M_AttributeSetInstance_ID
	 * @param UNS_Utilities_Uses_ID
	 * @param M_CostElement_ID
	 * @param Amt
	 * @param Qty
	 * @param Description
	 * @param trxName
	 * @return
	 */
	public static boolean createUtilitiesUses (MAcctSchema as, int AD_Org_ID, String extensionID,
			int M_Product_ID, int M_AttributeSetInstance_ID,
			int UNS_Utilities_Uses_ID, int M_CostElement_ID, 
			BigDecimal Amt, BigDecimal Qty,
			String Description, String trxName)
	{
		//	Delete Unprocessed zero Differences
		StringBuilder sql = new StringBuilder("DELETE M_CostDetail ")
			.append("WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0")
			.append(" AND UNS_Utilities_Uses_ID =").append(UNS_Utilities_Uses_ID)
			.append(" AND C_AcctSchema_ID =").append(as.getC_AcctSchema_ID())
			.append(" AND M_AttributeSetInstance_ID =").append(M_AttributeSetInstance_ID);
		int no = DB.executeUpdate(sql.toString(), trxName);
		if (no != 0)
			s_log.config("Deleted #" + no);
		MCostDetail cd = get (as.getCtx(), "UNS_Utilities_Uses_ID =?"
				, extensionID, UNS_Utilities_Uses_ID
				, M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trxName);
			cd.setUNS_Utilities_Uses_ID(UNS_Utilities_Uses_ID);
		}
		else
		{
			// MZ Goodwill
			// set deltaAmt=Amt, deltaQty=qty, and set Cost Detail for Amt and Qty	 
			cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
			cd.setDeltaQty(Qty.subtract(cd.getQty()));
			if (cd.isDelta())
			{
				cd.setProcessed(false);
				cd.setAmt(Amt);
				cd.setQty(Qty);
			}
			// end MZ
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			ok = cd.process();
		}
		s_log.config("(" + ok + ") " + cd);
		return ok;
	}	//	createUtilitiesUses

	/**
	 * 
	 * @param as
	 * @param AD_Org_ID
	 * @param M_Product_ID
	 * @param M_AttributeSetInstance_ID
	 * @param UNS_ActualCostItem_ID
	 * @param M_CostElement_ID
	 * @param Amt
	 * @param Qty
	 * @param Description
	 * @param trxName
	 * @return
	 */
	public static boolean createUnloadingAdjustment (MAcctSchema as, int AD_Org_ID, 
			int M_Product_ID, int M_AttributeSetInstance_ID,
			int UNS_BongkarMuatLine_ID, int M_CostElement_ID, 
			BigDecimal Amt, BigDecimal Qty,
			String Description, String trxName)
	{
		//	Delete Unprocessed zero Differences
		StringBuilder sql = new StringBuilder("DELETE M_CostDetail ")
			.append("WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0")
			.append(" AND UNS_BongkarMuatLine_ID=").append(UNS_BongkarMuatLine_ID)
			.append(" AND C_AcctSchema_ID =").append(as.getC_AcctSchema_ID())
			.append(" AND M_AttributeSetInstance_ID=").append(M_AttributeSetInstance_ID);
		int no = DB.executeUpdate(sql.toString(), trxName);
		if (no != 0)
			s_log.config("Deleted #" + no);
		org.compiere.model.MCostDetail cd = get (as.getCtx(), "UNS_BongkarMuatLine_ID=?", UNS_BongkarMuatLine_ID, 
				M_AttributeSetInstance_ID, as.getC_AcctSchema_ID(), trxName);
		//
		if (cd == null)		//	createNew
		{
			cd = new MCostDetail (as, AD_Org_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, 
				M_CostElement_ID, 
				Amt, Qty, Description, trxName);
			cd.setUNS_BongkarMuatLine_ID(UNS_BongkarMuatLine_ID);
			//cd.setAsProductCostAdjustment(true);
		}
		else
		{
			cd.setDeltaAmt(Amt.subtract(cd.getAmt()));
			cd.setDeltaQty(Qty.subtract(cd.getQty()));
			if (cd.isDelta())
			{
				cd.setProcessed(false);
				cd.setAmt(Amt);
				cd.setQty(Qty);
			}
			// end MZ
			else
				return true;	//	nothing to do
		}
		boolean ok = cd.save();
		if (ok && !cd.isProcessed())
		{
			ok = cd.process();
		}
		s_log.config("(" + ok + ") " + cd);
		return ok;
	}	//	createUnloadingAdjustment
}
