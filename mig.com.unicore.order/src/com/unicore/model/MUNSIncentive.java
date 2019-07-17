/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MPeriod;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

import com.uns.model.MProduct;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MOrderLine;

/**
 * @author menjangan, BurhaniAdam
 *
 */
public class MUNSIncentive extends X_UNS_Incentive {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSIncentiveSchema m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_Incentive_ID
	 * @param trxName
	 */
	public MUNSIncentive(Properties ctx, int UNS_Incentive_ID, String trxName) {
		super(ctx, UNS_Incentive_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSIncentive(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSIncentiveSchema getParent()
	{
		if(null != m_parent)
			return m_parent;
		
		m_parent = (MUNSIncentiveSchema) getUNS_IncentiveSchema();
		return m_parent;
	}
	
	public boolean isSalesIncentive()
	{
		return getIncentiveType().equals(INCENTIVETYPE_SalesInvoice);
	}
	
	public boolean isBillingIncentive()
	{
		return getIncentiveType().equals(INCENTIVETYPE_Billing);
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	public boolean IsInMyProductCategory(int M_Product_ID)
	{
		if(getM_Product_Category_ID() <= 0)
			return true;
		
		MProduct product = new MProduct(getCtx(), M_Product_ID, get_TrxName());
		boolean isIn = product.getM_Product_Category_ID() == getM_Product_Category_ID();
		
		return isIn;
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
//	public boolean isValidProduct(int M_Product_ID)
//	{
//		boolean validProduct = getM_Product_ID() == 0;
//		if(!validProduct)
//			validProduct = getM_Product_ID() == M_Product_ID;
//		if(!validProduct)
//			validProduct = IsInMyProductCategory(M_Product_ID);
//		return validProduct;
//	}
	
	/**
	 * 
	 * @param outletGrade
	 * @return
	 */
	public boolean IsValidOutletGrade(int outletGrade)
	{
		boolean retval = getUNS_Outlet_Grade_ID() <= 0 || getUNS_Outlet_Grade_ID() == outletGrade;
		return retval;
	}
	
	/**
	 * 
	 * @param outletType
	 * @return
	 */
	public boolean isValidOutletType(int outletType)
	{
		boolean retval = getUNS_Outlet_Type_ID() <= 0 || getUNS_Outlet_Type_ID() == outletType;
		return retval;
	}
	
	public int getPaymentTerm()
	{
		int paymentTerm = 0;
//		if(getC_PaymentTerm_ID() <= 0)
//			return paymentTerm;
		
//		String sql = "SELECT COALESCE(NetDays, 0) FROM C_PaymentTerm WHERE C_PaymentTerm_ID = ?";
//		paymentTerm = DB.getSQLValue(get_TrxName(), sql, getC_PaymentTerm_ID());
		return paymentTerm;
	}
	
	/**
	 * @param type (O = Organization, C = Customer, P = Product)
	 * @return {@link MUNSIncentiveComponent}
	 */
	public ArrayList<MUNSIncentiveComponent> getComponent(String type)
	{
		ArrayList<MUNSIncentiveComponent> list = new ArrayList<>();
		
		String sql = "SELECT * FROM UNS_IncentiveComponent WHERE"
				+ " UNS_Incentive_ID = ?";
		
		if(type != null)
			sql += " AND ComponentType = ?";
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		try{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, get_ID());
			if(type != null)
				stmt.setString(2, type);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new MUNSIncentiveComponent(getCtx(), rs, get_TrxName()));				
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return list;
	}
	
	private boolean isValidOrganization(int AD_Org_ID)
	{
		if(AD_Org_ID <= 0)
			return true;
		boolean tmpboolean = false;
		String orgs = getOrganizationSelection();
		if(!orgs.equals(X_UNS_Incentive.ORGANIZATIONSELECTION_IncludeAllOrganization))
		{
			if(orgs.equals(X_UNS_Incentive.ORGANIZATIONSELECTION_ExcludeOnceOrganization))
			{
				if(getAD_OrgTrx_ID() == AD_Org_ID)
					return false;
			}
			else if(orgs.equals(X_UNS_Incentive.ORGANIZATIONSELECTION_IncludeOnceOrganization))
			{
				if(getAD_OrgTrx_ID() != AD_Org_ID)
					return false;
			}
			else if(orgs.equals(X_UNS_Incentive.ORGANIZATIONSELECTION_ExcludeSelectedOrganization))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Organization))
				{
					if(comp.getAD_OrgTrx_ID() == AD_Org_ID)
						return false;
				}
			}
			else if(orgs.equals(X_UNS_Incentive.ORGANIZATIONSELECTION_IncludeSelectedOrganization))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Organization))
				{
					if(comp.getAD_OrgTrx_ID() == AD_Org_ID)
					{
						tmpboolean = true;
						break;
					}
				}
				if(!tmpboolean)
					return false;
			}
		}
		
		return true;
	}
	
	private boolean isValidCustomer(int C_BPartner_ID)
	{
		if(C_BPartner_ID <= 0)
			return true;
		boolean tmpboolean = false;
		String custs = getCustomerSelection();
		if(!custs.equals(X_UNS_Incentive.CUSTOMERSELECTION_IncludeAllCustomer))
		{
			if(custs.equals(X_UNS_Incentive.CUSTOMERSELECTION_ExcludeOnceCustomer))
			{
				if(getC_BPartner_ID() == C_BPartner_ID)
					return false;
			}
			else if(custs.equals(X_UNS_Incentive.CUSTOMERSELECTION_IncludeOnceCustomer))
			{
				if(getC_BPartner_ID() != C_BPartner_ID)
					return false;
			}
			else if(custs.equals(X_UNS_Incentive.CUSTOMERSELECTION_ExcludeSelectedCustomer))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Customer))
				{
					if(comp.getC_BPartner_ID() == C_BPartner_ID)
						return false;
				}
			}
			else if(custs.equals(X_UNS_Incentive.CUSTOMERSELECTION_IncludeSelectedCustomer))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Customer))
				{
					if(comp.getC_BPartner_ID() == C_BPartner_ID)
					{
						tmpboolean = true;
						break;
					}
				}
				if(!tmpboolean)
					return false;
			}
		}
		return true;
	}
	
	private boolean isValidProduct(int M_Product_ID)
	{
		if(M_Product_ID <= 0)
			return true;
		String prods = getProductSelection();		
		boolean tmpboolean = false;
		
		if(!prods.equals(X_UNS_Incentive.PRODUCTSELECTION_IncludeAllProduct))
		{
			if(prods.equals(X_UNS_Incentive.PRODUCTSELECTION_ExcludeOnceProduct))
			{
				if(getM_Product_ID() == M_Product_ID)
					return false;
			}
			else if(prods.equals(X_UNS_Incentive.PRODUCTSELECTION_IncludeOnceProduct))
			{
				if(getM_Product_ID() != M_Product_ID)
					return false;
			}
			else if(prods.equals(X_UNS_Incentive.PRODUCTSELECTION_ExcludeSelectedProduct))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Product))
				{
					if(comp.getM_Product_ID() == M_Product_ID)
						return false;
				}
			}
			else if(prods.equals(X_UNS_Incentive.PRODUCTSELECTION_IncludeSelectedProduct))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Product))
				{
					if(comp.getM_Product_ID() == M_Product_ID)
					{
						tmpboolean = true;
						break;
					}
				}
				if(!tmpboolean)
					return false;
			}
		}
		return true;
	}
	
	private boolean isValidRayon(int BPLocation_ID)
	{
		if(BPLocation_ID <= 0)
			return true;
		boolean tmpboolean = false;
		int Rayon_ID = DB.getSQLValue(get_TrxName(), "SELECT UNS_Rayon_ID "
				+ "FROM C_BPartner_Location WHERE "
					+ "C_BPartner_Location_ID = ?", BPLocation_ID);
		String rayons = getRayonSelection();
		
		if(!rayons.equals(X_UNS_Incentive.RAYONSELECTION_IncludeAllRayon))
		{
			if(rayons.equals(X_UNS_Incentive.RAYONSELECTION_ExcludeOnceRayon))
			{
				if(getUNS_Rayon_ID() == Rayon_ID)
					return false;
			}
			else if(rayons.equals(X_UNS_Incentive.RAYONSELECTION_IncludeOnceRayon))
			{
				if(getUNS_Rayon_ID() != Rayon_ID)
					return false;
			}
			else if(rayons.equals(X_UNS_Incentive.RAYONSELECTION_ExcludeSelectedRayon))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Rayon))
				{
					if(comp.getUNS_Rayon_ID() == Rayon_ID)
						return false;
				}
			}
			else if(rayons.equals(X_UNS_Incentive.RAYONSELECTION_IncludeSelectedRayon))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Rayon))
				{
					if(comp.getUNS_Rayon_ID() == Rayon_ID)
					{
						tmpboolean = true;
						break;
					}
				}
				if(!tmpboolean)
					return false;
			}
		}
			
		return true;
	}
	
	private boolean isValidBPGroup(int C_BPartner_ID)
	{
		if(C_BPartner_ID <= 0)
			return true;
		boolean tmpboolean = false;
		int BPGroup_ID = DB.getSQLValue(get_TrxName(), "SELECT C_BP_Group_ID "
				+ "FROM C_BPartner WHERE C_BPartner_ID = ?", C_BPartner_ID);
		String groups = getBPGroupSelection();
		
		if(!groups.equals(X_UNS_Incentive.BPGROUPSELECTION_IncludeAllGroup))
		{
			if(groups.equals(X_UNS_Incentive.BPGROUPSELECTION_ExcludeOnceGroup))
			{
				if(getC_BP_Group_ID() == BPGroup_ID)
					return false;
			}
			else if(groups.equals(X_UNS_Incentive.BPGROUPSELECTION_IncludeOnceGroup))
			{
				if(getC_BP_Group_ID() != BPGroup_ID)
					return false;
			}
			else if(groups.equals(X_UNS_Incentive.BPGROUPSELECTION_ExcludeSelectedGroup))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_CustomerGroup))
				{
					if(comp.getC_BP_Group_ID() == BPGroup_ID)
						return false;
				}
			}
			else if(groups.equals(X_UNS_Incentive.BPGROUPSELECTION_IncludeSelectedGroup))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_CustomerGroup))
				{
					if(comp.getC_BP_Group_ID() == BPGroup_ID)
					{
						tmpboolean = true;
						break;
					}
				}
				if(!tmpboolean)
					return false;
			}
		}		
		return true;
	}
	
	private boolean isValidCGrade(int C_BPartner_ID)
	{
		if(C_BPartner_ID <= 0)
			return true;
		boolean tmpboolean = false;
		int Grade_ID = DB.getSQLValue(get_TrxName(), "SELECT UNS_Outlet_Grade_ID "
				+ "FROM C_BPartner WHERE C_BPartner_ID = ?", C_BPartner_ID);
		String grades = getOutletGradeSelection();
		
		if(!grades.equals(X_UNS_Incentive.OUTLETGRADESELECTION_IncludeAllGrade))
		{
			if(grades.equals(X_UNS_Incentive.OUTLETGRADESELECTION_ExcludeOnceGrade))
			{
				if(getUNS_Outlet_Grade_ID() == Grade_ID)
					return false;
			}
			else if(grades.equals(X_UNS_Incentive.OUTLETGRADESELECTION_IncludeOnceGrade))
			{
				if(getUNS_Outlet_Grade_ID() != Grade_ID)
					return false;
			}
			else if(grades.equals(X_UNS_Incentive.OUTLETGRADESELECTION_ExcludeSelectedGrade))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Product))
				{
					if(comp.getUNS_Outlet_Grade_ID() == Grade_ID)
						return false;
				}
			}
			else if(grades.equals(X_UNS_Incentive.OUTLETGRADESELECTION_IncludeSelectedGrade))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Product))
				{
					if(comp.getUNS_Outlet_Grade_ID() == Grade_ID)
					{
						tmpboolean = true;
						break;
					}
				}
				if(!tmpboolean)
					return false;
			}
		}
		
		return true;
	}
	
	private boolean isValidCType(int C_BPartner_ID)
	{
		if(C_BPartner_ID <= 0)
			return true;
		boolean tmpboolean = false;
		int Type_ID = DB.getSQLValue(get_TrxName(), "SELECT UNS_Outlet_Type_ID "
				+ "FROM C_BPartner WHERE C_BPartner_ID = ?", C_BPartner_ID);
		String types = getOutletTypeSelection();
		
		if(!types.equals(X_UNS_Incentive.OUTLETTYPESELECTION_IncludeAllType))
		{
			if(types.equals(X_UNS_Incentive.OUTLETTYPESELECTION_ExcludeOnceType))
			{
				if(getUNS_Outlet_Type_ID() == Type_ID)
					return false;
			}
			else if(types.equals(X_UNS_Incentive.OUTLETTYPESELECTION_IncludeOnceType))
			{
				if(getUNS_Outlet_Type_ID() != Type_ID)
					return false;
			}
			else if(types.equals(X_UNS_Incentive.OUTLETTYPESELECTION_ExcludeSelectedType))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Product))
				{
					if(comp.getUNS_Outlet_Type_ID() == Type_ID)
						return false;
				}
			}
			else if(types.equals(X_UNS_Incentive.OUTLETTYPESELECTION_IncludeSelectedType))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Product))
				{
					if(comp.getUNS_Outlet_Type_ID() == Type_ID)
					{
						tmpboolean = true;
						break;
					}
				}
				if(!tmpboolean)
					return false;
			}
		}
		
		return true;
	}
	
	private boolean isValidPCategory(int M_Product_ID)
	{
		if(M_Product_ID <= 0)
			return true;
		boolean tmpboolean = false;
		int PCategory_ID = DB.getSQLValue(get_TrxName(), "SELECT M_Product_Category_ID"
				+ " FROM M_Product WHERE M_Product_ID = ?", M_Product_ID);
		String categorys = getPCategorySelection();
		
		if(!categorys.equals(X_UNS_Incentive.PCATEGORYSELECTION_IncludeAllCategory))
		{
			if(categorys.equals(X_UNS_Incentive.PCATEGORYSELECTION_ExcludeOnceCategory))
			{
				if(getM_Product_Category_ID() == PCategory_ID)
					return false;
			}
			else if(categorys.equals(X_UNS_Incentive.PCATEGORYSELECTION_IncludeOnceCategory))
			{
				if(getM_Product_Category_ID() != PCategory_ID)
					return false;
			}
			else if(categorys.equals(X_UNS_Incentive.PCATEGORYSELECTION_ExcludeSelectedCategory))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Product))
				{
					if(comp.getM_Product_Category_ID() == PCategory_ID)
						return false;
				}
			}
			else if(categorys.equals(X_UNS_Incentive.PCATEGORYSELECTION_IncludeSelectedCategory))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_Product))
				{
					if(comp.getM_Product_Category_ID() == PCategory_ID)
					{
						tmpboolean = true;
						break;
					}
				}
				if(!tmpboolean)
					return false;
			}
		}
		
		return true;
	}
	
	private boolean isValidTenderType(String TenderType)
	{
		if(Util.isEmpty(TenderType, true))
			return true;
		boolean tmpboolean = false;
		
		String tendertypes = getTenderTypeSelection();
		
		if(!tendertypes.equals(X_UNS_Incentive.TENDERTYPESELECTION_IncludeAllTenderType))
		{
			if(tendertypes.equals(X_UNS_Incentive.TENDERTYPESELECTION_ExcludeOneTenderType))
			{
				if(TenderType.equals(tendertypes))
					return false;
			}
			else if(tendertypes.equals(X_UNS_Incentive.TENDERTYPESELECTION_IncludeOnceTenderType))
			{
				if(!TenderType.equals(tendertypes))
					return false;
			}
			else if(tendertypes.equals(X_UNS_Incentive.TENDERTYPESELECTION_ExcludeSelectedTenderType))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_TenderType))
				{
					if(comp.getTenderType().equals(TenderType))
						return false;
				}
			}
			else if(tendertypes.equals(X_UNS_Incentive.TENDERTYPESELECTION_IncludeSelectedTenderType))
			{
				for(MUNSIncentiveComponent comp : getComponent(X_UNS_IncentiveComponent.COMPONENTTYPE_TenderType))
				{
					if(comp.getTenderType().equals(TenderType))
					{
						tmpboolean = true;
						break;
					}
				}
				if(!tmpboolean)
					return false;
			}
		}
		
		return true;
	}
	
	public boolean isQualify(int AD_Org_ID, int C_BPartner_ID, 
			int BPLocation_ID, int M_Product_ID, String TenderType)
	{			
		return isValidOrganization(AD_Org_ID) && isValidCustomer(C_BPartner_ID)
				&& isValidBPGroup(C_BPartner_ID) && isValidCGrade(C_BPartner_ID)
				&& isValidProduct(M_Product_ID) && isValidPCategory(M_Product_ID)
				&& isValidRayon(BPLocation_ID) && isValidCType(C_BPartner_ID)
				&& isValidTenderType(TenderType);
	}
	
//	public boolean calculateIncentive(PO po, MPeriod period)
//	{
//		boolean isMatch = false;
//		ArrayList<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
//		if(po instanceof MPaymentAllocate)
//		{
//			MPaymentAllocate allocate = (MPaymentAllocate) po;
//			for(int i = 0; i < configs.size(); i++)
//			{
//				isMatch = configs.get(i).analyzePaymentAlloc(allocate, this);
//				if(isMatch)
//				{
//					calcPayment(allocate, (MUNSIncentiveSchemaConfig) configs.get(i));
//					break;
//				}
//			}
//		}
//		else if(po instanceof MInvoiceLine)
//		{
//			MInvoiceLine line = (MInvoiceLine) po;
//			for(int i = 0; i < configs.size(); i++)
//			{
//				isMatch = configs.get(i).analyzeInvoiceLine(line, this);
//				if(isMatch)
//				{
//					calcInvoiceLine(line, (MUNSIncentiveSchemaConfig) configs.get(i));
//					break;
//				}
//			}
//		}
//		else if(po instanceof MInvoice)
//		{
//			for(int i = 0; i < configs.size(); i++)
//			{
//				MInvoice invoice = (MInvoice) po;
//				isMatch = configs.get(i).analyzeInvoice((MInvoice) po, this);
//				if(isMatch)
//				{
//					if(getIncentiveType().equals(INCENTIVETYPE_NewOutlet))
//						calcNewOutlet(invoice, period, configs.get(i));
//					else
//						calcDeductionInvoice(invoice, period, configs.get(i));
//					break;
//				}
//			}
//		}
//		else if(po instanceof MOrderLine)
//		{
//			for(int i = 0; i < configs.size(); i++)
//			{
//				isMatch = configs.get(i).analyzeOrder((MOrderLine) po, this);
//				if(isMatch)
//					break;
//			}
//		}
//		else if(po instanceof MUNSShipping)
//		{
//			for(int i = 0; i < configs.size(); i++)
//			{
//				isMatch = configs.get(i).analyzeShipping((MUNSShipping) po, this);
//				if(isMatch)
//					break;
//			}
//		}
//		else
//		{
//			log.log(Level.FINE, "Not found reference");
//			return false;
//		}
//		
//		return isMatch;
//	}
	

	
	public static ArrayList<MUNSIncentive> getBySalesRep(Properties ctx, MBPartner partner, 
			MPeriod period, String schemaType, String IncentiveType, boolean isSales, String trxName)
	{
		ArrayList<MUNSIncentive> incentive = new ArrayList<>();
		
		String sql = "SELECT i.* FROM UNS_Incentive i"
				+ " INNER JOIN UNS_IncentiveSchema it ON it.UNS_IncentiveSchema_ID = i.UNS_IncentiveSchema_ID"
				+ " WHERE it.ValidFrom <= ? AND (it.ValidTo IS NULL OR it.ValidTo >= ?)";
		if(isSales)
				sql += " AND (CASE WHEN it.C_BPartner_ID > 0 THEN it.C_BPartner_ID = ? ELSE 1=1 END)"
				+ " AND (CASE WHEN it.SalesType <> '' THEN it.SalesType = ? ELSE 1=1 END)"
				+ " AND (CASE WHEN it.SalesLevel <> '' THEN it.SalesLevel = ? ELSE 1=1 END)";
		
		if(!Util.isEmpty(IncentiveType, true))
			sql += " AND i.IncentiveType = '" + IncentiveType + "'";
		
		if(!Util.isEmpty(schemaType, true))
			sql += " AND it.SchemaType = '" + schemaType + "'";
		else if(isSales)
			sql += " AND it.SchemaType <> 'CT'";
		else
			sql += " AND it.SchemaType = 'CT'";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.prepareStatement(sql, trxName);
			stmt.setTimestamp(1, period.getStartDate());
			stmt.setTimestamp(2, period.getStartDate());
			if(isSales)
			{
				stmt.setInt(3, partner.get_ID());
				stmt.setString(4, partner.getSalesType());
				stmt.setString(5, partner.getSalesLevel());
			}
			rs = stmt.executeQuery();
			while (rs.next())
			{
				incentive.add(new MUNSIncentive(ctx, rs, trxName));
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally{DB.close(rs, stmt);}
		
		return incentive;
	}
	
	public String calcInvoiceLine(MInvoiceLine line, MUNSIncentiveSchemaConfig config)
	{		
		String sql = "SELECT pr.IsTaxIncluded, dt.C_DocType_ID,"
				+ " i.DateInvoiced, i.DocumentNo, i.SalesRep_ID"
				+ " FROM C_Invoice i"
				+ " INNER JOIN M_PriceList pr ON pr.M_PriceList_ID = i.M_PriceList_ID"
				+ " INNER JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocType_ID"
				+ " WHERE i.C_Invoice_ID = ?";
		List<List<Object>> sqlColumn = DB.getSQLArrayObjectsEx(get_TrxName(), sql, line.getC_Invoice_ID());
		BigDecimal amt = Env.ZERO;
		BigDecimal baseAmt = Env.ZERO;
		
		if(config.getQtyMultiplier().compareTo(Env.ZERO) == 0)
		{
			if(config.isIncentiveAmount())
			{
				baseAmt = line.getLineNetAmt();
				amt = config.getIncentive();
			}
			else
			{
				if(isIncludedTax())
				{
					baseAmt = line.getLineNetAmt();
					amt = (baseAmt.multiply(config.getIncentive()))
								.divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
				}
				else
				{
					if(sqlColumn.get(0).get(0).equals("Y"))
					{
						if(line.getC_Tax().getRate().compareTo(Env.ZERO) == 0)
						{
							baseAmt = line.getLineNetAmt();
							amt = (baseAmt.multiply(config.getIncentive()))
									.divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
						}
						else
						{
							BigDecimal tax = (line.getLineNetAmt().multiply(line.getC_Tax().getRate()))
													.divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
							baseAmt = line.getLineNetAmt().subtract(tax);
							amt = (baseAmt.multiply(config.getIncentive()))
									.divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
						}
					}
					else
					{
						baseAmt = line.getLineNetAmt();
						amt = (baseAmt.multiply(config.getIncentive()))
								.divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
					}
				}
			}
		}
		else
		{
			BigDecimal multiplier = line.getQtyInvoiced().divide(config.getQtyMultiplier(), 0, RoundingMode.DOWN);
			if(config.isIncentiveAmount())
				baseAmt = config.getIncentive();
			else
			{
				baseAmt = line.getPriceActual().multiply(config.getQtyMultiplier());
				baseAmt = baseAmt.multiply(config.getIncentive()).divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
			}
			baseAmt = line.getPriceActual().multiply(multiplier);
			amt = baseAmt.multiply(multiplier);
		}
		 
		if(isCreditNoteMinusValue() 
				&& Integer.parseInt(sqlColumn.get(0).get(1).toString()) 
					== MDocType.getDocType(MDocType.DOCBASETYPE_ARCreditMemo))
		{
			amt = amt.negate();
			baseAmt = baseAmt.negate();
		}
			
		int sales = Integer.parseInt(sqlColumn.get(0).get(4).toString());
		MUNSAchievedIncentiveLine ref = MUNSAchievedIncentiveLine.getCreate(
				getCtx(), line, getUNS_IncentiveSchema().getSchemaType(),
				Timestamp.valueOf(sqlColumn.get(0).get(2).toString()), 
				sales, sales, baseAmt, amt, X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, 
				sqlColumn.get(0).get(3).toString(), this, 0, 0, get_TrxName());
		if(config.isSupervisorReward())
		{
			ArrayList<MUNSIncentiveConfig> configs = 
					MUNSIncentiveConfig.getSupervisor(getCtx(), line, sales, getIncentiveType(), get_TrxName());
			for(int i = 0; i < configs.size(); i++)
			{
				configs.get(i).runSupervisorTree(line, getIncentiveType(), config.isSupervisorContinuous(), sales, 
						baseAmt, amt, ref.get_ID(), this);
			}
		}
		return "";
	}
	
	public void calcBilling(int AD_User_ID, MPeriod period)
	{
		if(isCalculationOnPaid() && isUseLastSalesTrx() && isUseLastDateTrx())
			calcPaymentOnPaidUseLastSalesLastDateTrx(AD_User_ID, period);
		else if(isCalculationOnPaid() && isUseLastSalesTrx() && !isUseLastDateTrx())
			calcPaymentOnPaidUseLastSales(AD_User_ID, period);
		else
			calcPayment(AD_User_ID, period);
	}
	
	private void calcPaymentOnPaidUseLastSalesLastDateTrx(int AD_User_ID, MPeriod period)
	{
		List<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
		String sql = "SELECT Data.C_Invoice_ID, Data.DateInvoiced, Data.SalesInvoice,"
				+ " Data.Datetrx, Data.Amount, Data.DocumentNo, Data.C_Payment_ID FROM ("
				+ " SELECT iv.C_Invoice_ID, iv.DateInvoiced, iv.SalesRep_ID, Master.C_Payment_ID"
				+ " Master.SalesRep_ID, Master.DateTrx, pa.Amount, Master.DocumentNo, Master.SalesRep_ID"
				+ " ROW_NUMBER() OVER (PARTITION BY iv.C_Invoice_ID ORDER BY Master.DateTrx DESC)"
				+ " AS RowNumber FROM C_Payment Master"
				+ " INNER JOIN C_PaymentAllocate pa ON pa.C_Payment_ID = Master.C_Payment_ID"
				+ " INNER JOIN C_Invoice iv ON iv.C_Invoice_ID = pa.C_Invoice_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = iv.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = iv.C_BPartner_Location_ID"
				+ " INNER JOIN C_InvoiceLine il ON il.C_Invoice_ID = iv.C_Invoice_ID"
				+ " INNER JOIN M_Product mp ON mp.M_Product_ID = il.M_Product_ID"
				+ " WHERE iv.IsSOTrx = 'Y' AND Master.DocStatus IN ('CO', 'CL')"
				+ " AND iv.IsPaid = 'Y' AND Master.DateTrx BETWEEN ? AND ? " + getStdWhereClause()
				+ " ) AS Data"
				+ " WHERE RowNumber = 1 AND Data.SalesRep_ID = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setTimestamp(1, period.getStartDate());
			stmt.setTimestamp(2, period.getEndDate());
			stmt.setInt(3, AD_User_ID);
			rs = stmt.executeQuery();
			int i = 0;
			int row = rs.getRow();
			while(rs.next())
			{
				BigDecimal payAmt = Env.ZERO;
				if(isUseTotalPayAmt())
				{
					String totalPay = "SELECT SUM(pa.Amount) FROM C_PaymentAllocate pa"
							+ " INNER JOIN C_Payment p ON p.C_Payment_ID = pa.C_Payment_ID"
							+ " WHERE p.DocStatus IN ('CO', 'CL') AND pa.C_Invoice_ID = ?";
					payAmt = DB.getSQLValueBD(get_TrxName(), totalPay, rs.getInt(1));
				}
				else
					payAmt = rs.getBigDecimal(5);
				
				if(payAmt == null)
					continue;
					
				Timestamp dateTrx = rs.getTimestamp(4);
				Calendar cal = Calendar.getInstance();
				cal.setTime(rs.getTimestamp(2));
				Timestamp dateInv = new Timestamp(cal.getTimeInMillis());
				int range = TimeUtil.getDaysBetween(dateInv, dateTrx);
				
				for(MUNSIncentiveSchemaConfig config : configs)
				{
					if((config.getAgeTo() == 0 || (range >= config.getAgeFrom() && range <= config.getAgeTo()))
							&& (config.getRangeTo().signum() == 0 || (payAmt.compareTo(config.getRangeFrom()) >= 0
							&& payAmt.compareTo(config.getRangeTo()) <= 0)))
					{
						i++;
						BigDecimal amt = Env.ZERO;
						if(config.isIncentiveAmount())
							amt = config.getIncentive();
						else
							amt = (payAmt.multiply(config.getIncentive())).
										divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
						
						MPayment pay = new MPayment(getCtx(), rs.getInt(7), get_TrxName());
						MUNSAchievedIncentiveLine ref = MUNSAchievedIncentiveLine.getCreate(
								getCtx(), new MPayment(getCtx(), rs.getInt(7), get_TrxName()),
								getUNS_IncentiveSchema().getSchemaType(),
								dateTrx, AD_User_ID, rs.getInt(3), payAmt, amt, 
								X_UNS_AchievedIncentive_Line.SOURCETYPE_Payment, rs.getString(6), 
								this, 0, 0, get_TrxName());
						log.info(new Timestamp(System.currentTimeMillis()) + 
								"Use Last Sales & Last Date Trx " + (i) + " dari " + row);
						if(config.isSupervisorReward())
						{
							ArrayList<MUNSIncentiveConfig> iconfigs = 
									MUNSIncentiveConfig.getSupervisor(getCtx(), pay, pay.getSalesRep_ID(),
											getIncentiveType(), get_TrxName());
							for(int j = 0; j < iconfigs.size(); j++)
							{
								iconfigs.get(j).runSupervisorTree(pay, getIncentiveType(),
										config.isSupervisorContinuous(), pay.getSalesRep_ID(), 
										payAmt, amt, ref.get_ID(), this);
							}
						}
						break;
					}
				}
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
	}
	
	private void calcPaymentOnPaidUseLastSales(int AD_User_ID, MPeriod period)
	{
		List<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
		String sql = "SELECT DISTINCT(Master.C_Payment_ID), iv.C_Invoice_ID, iv.DateInvoiced, iv.SalesRep_ID, Master.DateTrx,"
				+ " Master.SalesRep_ID, pa.Amount, Master.DocumentNo FROM C_Payment Master"
				+ " INNER JOIN C_PaymentAllocate pa ON pa.C_Payment_ID = Master.C_Payment_ID"
				+ " INNER JOIN C_Invoice iv ON iv.C_Invoice_ID = pa.C_Invoice_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = iv.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = iv.C_BPartner_Location_ID"
				+ " INNER JOIN C_InvoiceLine il ON il.C_Invoice_ID = iv.C_Invoice_ID"
				+ " INNER JOIN M_Product mp ON mp.M_Product_ID = il.M_Product_ID"
				+ " WHERE iv.IsSOTrx = 'Y' AND Master.DocStatus IN ('CO', 'CL')"
				+ " AND iv.IsPaid = 'Y' AND Master.DateTrx BETWEEN ? AND ? "
				+ " AND Master.SalesRep_ID = ?";
		sql += getStdWhereClause();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setTimestamp(1, period.getStartDate());
			stmt.setTimestamp(2, period.getEndDate());
			stmt.setInt(3, AD_User_ID);
			rs = stmt.executeQuery();
			int i = 0;
			int row = rs.getRow();
			while(rs.next())
			{
				BigDecimal payAmt = Env.ZERO;
				payAmt = rs.getBigDecimal(7);
				if(payAmt == null)
					continue;
					
				Timestamp dateTrx = rs.getTimestamp(5);
				int range = TimeUtil.getDaysBetween(rs.getTimestamp(3), dateTrx);
				
				for(MUNSIncentiveSchemaConfig config : configs)
				{
					if((config.getAgeTo() == 0 || (range >= config.getAgeFrom() && range <= config.getAgeTo()))
							&& (config.getRangeTo().signum() == 0 || (payAmt.compareTo(config.getRangeFrom()) >= 0
							&& payAmt.compareTo(config.getRangeTo()) <= 0)))
					{
						BigDecimal amt = Env.ZERO;
						if(config.isIncentiveAmount())
							amt = config.getIncentive();
						else
							amt = (payAmt.multiply(config.getIncentive())).
										divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
						
						MPayment pay = new MPayment(getCtx(), rs.getInt(1), get_TrxName());
						MUNSAchievedIncentiveLine ref = MUNSAchievedIncentiveLine.getCreate(
								getCtx(), pay,
								getUNS_IncentiveSchema().getSchemaType(),
								dateTrx, AD_User_ID, rs.getInt(4), payAmt, amt, 
								X_UNS_AchievedIncentive_Line.SOURCETYPE_Payment, rs.getString(8), 
								this, 0, 0, get_TrxName());
						log.info(new Timestamp(System.currentTimeMillis()) + 
								"Use Last Sales" + (i) + " dari " + row);
						if(config.isSupervisorReward())
						{
							ArrayList<MUNSIncentiveConfig> iconfigs = 
									MUNSIncentiveConfig.getSupervisor(getCtx(), pay, pay.getSalesRep_ID(),
											getIncentiveType(), get_TrxName());
							for(int j = 0; j < iconfigs.size(); j++)
							{
								iconfigs.get(j).runSupervisorTree(pay, getIncentiveType(),
										config.isSupervisorContinuous(), pay.getSalesRep_ID(), 
										payAmt, amt, ref.get_ID(), this);
							}
						}
						break;
					}
				}
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
	}
	
	private void calcPayment(int AD_User_ID, MPeriod period)
	{
		List<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
		String sql = "SELECT Master.C_Payment_ID, iv.C_Invoice_ID, iv.DateInvoiced, iv.SalesRep_ID, Master.DateTrx,"
				+ " Master.SalesRep_ID, pa.Amount, Master.DocumentNo FROM C_Payment Master"
				+ " INNER JOIN C_PaymentAllocate pa ON pa.C_Payment_ID = Master.C_Payment_ID"
				+ " INNER JOIN C_Invoice iv ON iv.C_Invoice_ID = pa.C_Invoice_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = iv.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = iv.C_BPartner_Location_ID"
				+ " INNER JOIN C_InvoiceLine il ON il.C_Invoice_ID = iv.C_Invoice_ID"
				+ " INNER JOIN M_Product mp ON mp.M_Product_ID = il.M_Product_ID"
				+ " WHERE iv.IsSOTrx = 'Y' AND Master.DocStatus IN ('CO', 'CL')"
				+ " AND Master.DateTrx BETWEEN ? AND ? "
				+ " AND Master.SalesRep_ID = ?";
		if(isCalculationOnPaid())
			sql += " AND iv.IsPaid = 'Y'";
		
		sql += getStdWhereClause();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setTimestamp(1, period.getStartDate());
			stmt.setTimestamp(2, period.getEndDate());
			stmt.setInt(3, AD_User_ID);
			rs = stmt.executeQuery();
			int i = 0;
			while(rs.next())
			{
				BigDecimal payAmt = Env.ZERO;
				payAmt = rs.getBigDecimal(7);
				if(payAmt == null)
					continue;
				Timestamp dateTrx = null;
				if(isUseLastDateTrx())
				{
					String lastDate = "SELECT MAX(p.DateTrx) FROM C_Payment p"
							+ " INNER JOIN C_PaymentAllocate pa ON pa.C_Payment_ID = p.C_Payment_ID"
							+ " WHERE p.DocStatus IN ('CO', 'CL') AND p.IsReceipt = 'Y'"
							+ " AND pa.C_Invoice_ID = ?";
					dateTrx = DB.getSQLValueTS(get_TrxName(), lastDate, rs.getInt(2));
				}
				else
					dateTrx = rs.getTimestamp(5);
				
				Timestamp dateInv = rs.getTimestamp(3);
				int range = TimeUtil.getDaysBetween(dateInv, dateTrx);
				
				for(MUNSIncentiveSchemaConfig config : configs)
				{
					if((config.getAgeTo() == 0 || (range >= config.getAgeFrom() && range <= config.getAgeTo()))
							&& (config.getRangeTo().signum() == 0 || (payAmt.compareTo(config.getRangeFrom()) >= 0
							&& payAmt.compareTo(config.getRangeTo()) <= 0)))
					{
						i++;
						BigDecimal amt = Env.ZERO;
						if(config.isIncentiveAmount())
							amt = config.getIncentive();
						else
							amt = (payAmt.multiply(config.getIncentive())).
										divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
						MPayment pay = new MPayment(getCtx(), rs.getInt(1), get_TrxName());
						MUNSAchievedIncentiveLine ref = MUNSAchievedIncentiveLine.getCreate(
								getCtx(), pay,
								getUNS_IncentiveSchema().getSchemaType(),
								dateTrx, AD_User_ID, rs.getInt(4), payAmt, amt, 
								X_UNS_AchievedIncentive_Line.SOURCETYPE_Payment, rs.getString(8), 
								this, 0, 0, get_TrxName());
						log.info(new Timestamp(System.currentTimeMillis()) + 
								"Payment " + (i));
						if(config.isSupervisorReward())
						{
							ArrayList<MUNSIncentiveConfig> iconfigs = 
									MUNSIncentiveConfig.getSupervisor(getCtx(), pay, pay.getSalesRep_ID(),
											getIncentiveType(), get_TrxName());
							for(int j = 0; j < iconfigs.size(); j++)
							{
								iconfigs.get(j).runSupervisorTree(pay, getIncentiveType(),
										config.isSupervisorContinuous(), pay.getSalesRep_ID(), 
										payAmt, amt, ref.get_ID(), this);
							}
						}
						break;
					}
				}
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
	}
	
	public String calcOrderLine(org.compiere.model.MOrderLine line, MUNSIncentiveSchemaConfig config)
	{		
		org.compiere.model.MOrder order = (org.compiere.model.MOrder) line.getC_Order();
		BigDecimal amt = Env.ZERO;
		BigDecimal baseAmt = Env.ZERO;
		if(config.getQtyMultiplier().compareTo(Env.ZERO) == 0)
		{
			if(config.isIncentiveAmount())
			{
				baseAmt = line.getLineNetAmt();
				amt = config.getIncentive();
			}
			else
			{
				if(isIncludedTax())
				{
					baseAmt = line.getLineNetAmt();
					amt = (baseAmt.multiply(config.getIncentive()))
								.divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
				}
				else
				{
					if(order.getM_PriceList().isTaxIncluded())
					{
						if(line.getC_Tax().getRate().compareTo(Env.ZERO) == 0)
						{
							baseAmt = line.getLineNetAmt();
							amt = (baseAmt.multiply(config.getIncentive()))
									.divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
						}
						else
						{
							BigDecimal tax = (line.getLineNetAmt().multiply(line.getC_Tax().getRate()))
													.divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
							baseAmt = line.getLineNetAmt().subtract(tax);
							amt = (baseAmt.multiply(config.getIncentive()))
									.divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
						}
					}
					else
					{
						baseAmt = line.getLineNetAmt();
						amt = (baseAmt.multiply(config.getIncentive()))
								.divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
					}
				}
			}
		}
		else
		{
			BigDecimal multiplier = line.getQtyOrdered().divide(config.getQtyMultiplier(), 0, RoundingMode.DOWN);
			if(config.isIncentiveAmount())
			{
				baseAmt = config.getIncentive();
			}
			else
			{
				baseAmt = line.getPriceActual().multiply(config.getQtyMultiplier());
				baseAmt = baseAmt.multiply(config.getIncentive()).divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
			}
				
			amt = baseAmt.multiply(multiplier);
		}
		
		MUNSAchievedIncentiveLine ref = MUNSAchievedIncentiveLine.getCreate(
				getCtx(), line, getUNS_IncentiveSchema().getSchemaType(),
				order.getDateOrdered(), order.getSalesRep_ID(), order.getSalesRep_ID(), baseAmt, amt,
				X_UNS_AchievedIncentive_Line.SOURCETYPE_SalesOrderLine, order.getDocumentNo(),
				this, order.getC_BPartner_ID(), line.getM_Product_ID(), get_TrxName());
		
		if(config.isSupervisorReward())
		{
			ArrayList<MUNSIncentiveConfig> configs = 
					MUNSIncentiveConfig.getSupervisor(getCtx(), line, order.getSalesRep_ID(),
							getIncentiveType(), get_TrxName());
			for(int i = 0; i < configs.size(); i++)
			{
				configs.get(i).runSupervisorTree(line, getIncentiveType(),
						config.isSupervisorContinuous(), order.getSalesRep_ID(), 
						baseAmt, amt, ref.get_ID(), this);
			}
		}
		
		return "";
	}
	
	public String calcNewOutlet(MInvoice invoice, MPeriod period, MUNSIncentiveSchemaConfig config)
	{
		BigDecimal amt = Env.ZERO;
		BigDecimal baseAmt = Env.ZERO;
			baseAmt = invoice.getGrandTotal();
		if(config.isIncentiveAmount())
			amt = config.getIncentive();
		else
			amt = (baseAmt.multiply(config.getIncentive())).divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
		
		MUNSAchievedIncentiveLine ref = MUNSAchievedIncentiveLine.getCreate(
				getCtx(), invoice, getUNS_IncentiveSchema().getSchemaType(),
				invoice.getDateInvoiced(), invoice.getSalesRep_ID(), invoice.getSalesRep_ID(), baseAmt, amt, 
				X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceNewOutlet, invoice.getDocumentNo(),
				this, invoice.getC_BPartner_ID(), 0, get_TrxName());
		if(config.isSupervisorReward())
		{
			ArrayList<MUNSIncentiveConfig> configs = 
					MUNSIncentiveConfig.getSupervisor(getCtx(), invoice, invoice.getSalesRep_ID(),
							getIncentiveType(), get_TrxName());
			for(int i = 0; i < configs.size(); i++)
			{
				configs.get(i).runSupervisorTree(invoice, getIncentiveType(),
						config.isSupervisorContinuous(), invoice.getSalesRep_ID(), 
						baseAmt, amt, ref.get_ID(), this);
			}
		}
		
		return "";
	}
	
	public String calcDeductionInvoice(MInvoice invoice, MPeriod period,
			MUNSIncentiveSchemaConfig config)
	{
		BigDecimal amt = Env.ZERO;
		BigDecimal baseAmt = Env.ZERO;
		
		String sql = "SELECT InvoiceOpenToDate(?,0,?)";
		baseAmt = DB.getSQLValueBD(get_TrxName(), sql, invoice.get_ID(), period.getEndDate());
		
		if(baseAmt == null)
			baseAmt = Env.ZERO;
		
		if(baseAmt.compareTo(Env.ZERO) == 1)
		{
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(invoice.getDateInvoiced());
//			cal.add(Calendar.DAY_OF_MONTH, invoice.getC_PaymentTerm().getNetDays());
//			int age = TimeUtil.getDaysBetween(new Timestamp(cal.getTimeInMillis()), period.getEndDate());
//			if(age > 0 && age <= config.getRangeTo() && age >= config.getRangeFrom())
//			{	
				if(config.isIncentiveAmount())
					amt = config.getIncentive();
				else
					amt = (baseAmt.multiply(config.getIncentive())).divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
				
				MUNSAchievedIncentiveLine.getCreate(
						getCtx(), invoice, getUNS_IncentiveSchema().getSchemaType(),
						invoice.getDateInvoiced(), invoice.getSalesRep_ID(), 
						invoice.getSalesRep_ID(), baseAmt.negate(), amt.negate(),
						X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceOverDue, invoice.getDocumentNo(),
						this, invoice.getC_BPartner_ID(), 0, get_TrxName());
//			}
		}
		
		return "";
	}
	
	private boolean isIncludedTax()
	{
		return MSysConfig.getBooleanValue(MSysConfig.INCLUDE_TAX_ON_INCENTIVE_CALCULATION, false);
	}
	
	private ArrayList<MInvoiceLine> listOfInvoiceLine(int AD_User_ID, MPeriod period, 
			MUNSIncentiveSchemaConfig config)
	{
		ArrayList<MInvoiceLine> lines = new ArrayList<MInvoiceLine>();
		String SQLSelection = "";
		String sql = "SELECT il.* FROM C_InvoiceLine il"
				+ " INNER JOIN C_Invoice iv ON iv.C_Invoice_ID = il.C_Invoice_ID"
				+ " INNER JOIN C_DocType dt ON iv.C_DocType_ID = dt.C_DocType_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = iv.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = iv.C_BPartner_Location_ID"
				+ " INNER JOIN M_Product mp ON mp.M_Product_ID = il.M_Product_ID"
				+ " WHERE iv.DocStatus IN ('CO', 'CL') AND iv.SalesRep_ID = ?"
				+ " AND iv.DateInvoiced BETWEEN ? AND ? AND iv.IsSOTrx = 'Y'";
		
		SQLSelection = getStdWhereClause();
		if(!Util.isEmpty(SQLSelection, true))
			sql += SQLSelection;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, AD_User_ID);
			stmt.setTimestamp(2, period.getStartDate());
			stmt.setTimestamp(3, period.getEndDate());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				lines.add(new MInvoiceLine(getCtx(), rs, get_TrxName()));
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally{DB.close(rs, stmt);}

		return lines;
	}
	
	private ArrayList<MInvoice> listOfInvoice(int AD_User_ID, MPeriod period,
			boolean isNewOutlet, boolean isDeduction, MUNSIncentiveSchemaConfig config)
	{
		ArrayList<MInvoice> lines = new ArrayList<MInvoice>();
		String SQLSelection = "";
		String sql = "SELECT master.* FROM C_Invoice master"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = master.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = master.C_BPartner_Location_ID"
				+ " INNER JOIN C_DocType dt ON dt.C_DocType_ID = master.C_DocType_ID"
				+ " INNER JOIN C_PaymentTerm pt ON pt.C_PaymentTerm_ID = master.C_PaymentTerm_ID"
				+ " WHERE master.DocStatus IN ('CO', 'CL') AND master.SalesRep_ID = ?"
				+ " AND master.DateInvoiced BETWEEN ? AND ? AND master.IsSOTrx = 'Y'"
				+ " AND dt.DocBaseType = 'ARI'";
		
		if(isNewOutlet)
			sql += " AND master.DateInvoiced = bp.FirstSale";
		
		if(isDeduction)
		{
			sql += " AND COALESCE(InvoiceOpenToDate(master.C_Invoice_ID, 0, ?),0) > 0";
			if(config.getAgeFrom() > 0)
				sql += " AND ? - (master.DateInvoiced + pt.NetDays) >= " + config.getRangeFrom();
			if(config.getAgeTo() > 0)
				sql += " AND ? - (master.DateInvoiced + pt.NetDays) <= " + config.getRangeTo();
		}
		
		SQLSelection = getStdWhereClause();
		if(!Util.isEmpty(SQLSelection, true))
			sql += SQLSelection;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, AD_User_ID);
			stmt.setTimestamp(2, period.getStartDate());
			stmt.setTimestamp(3, period.getEndDate());
			if(isDeduction)
			{
				stmt.setTimestamp(4, period.getEndDate());
				stmt.setTimestamp(5, period.getEndDate());
				stmt.setTimestamp(6, period.getEndDate());
			}
			rs = stmt.executeQuery();
			while (rs.next())
			{
				lines.add(new MInvoice(getCtx(), rs, get_TrxName()));
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
		finally{DB.close(rs, stmt);}
		
		return lines;
	}
	
	@SuppressWarnings("unused")
	private ArrayList<MPaymentAllocate> listOfPaymentAllocate(int AD_User_ID, MPeriod period,
			MUNSIncentiveSchemaConfig config)
	{
		ArrayList<MPaymentAllocate> allocates = new ArrayList<MPaymentAllocate>();
		String SQLselection = "";
		String sql = "SELECT master.* FROM C_PaymentAllocate master"
				+ " INNER JOIN C_Payment p ON p.C_Payment_ID = master.C_Payment_ID"
				+ " INNER JOIN C_Invoice inv ON inv.C_Invoice_ID = master.C_Invoice_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = inv.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = inv.C_BPartner_Location_ID"
				+ " INNER JOIN C_PaymentTerm pt ON pt.C_PaymentTerm_ID = inv.C_PaymentTerm_ID"
				+ " WHERE p.SalesRep_ID = ? AND p.DocStatus IN ('CO', 'CL') AND p.IsReceipt = 'Y'"
				+ " AND p.DateTrx BETWEEN ? AND ? AND master.Amount > 0";
		
		if(config.getRangeFrom().intValue() > 0)
			sql += " AND DATE_PART('day', age(p.DateTrx, inv.DateInvoiced)) >= " + config.getRangeFrom();
		if(config.getRangeTo().intValue() > 0)
			sql += " AND DATE_PART('day', age(p.DateTrx, inv.DateInvoiced)) <= " + config.getRangeTo();
		
		SQLselection = getStdWhereClause();
		if(!Util.isEmpty(SQLselection, true))
			sql += SQLselection;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, AD_User_ID);
			stmt.setTimestamp(2, period.getStartDate());
			stmt.setTimestamp(3, period.getEndDate());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				allocates.add(new MPaymentAllocate(getCtx(), rs, get_TrxName()));
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
		finally{DB.close(rs, stmt);}
		
		return allocates;
	}
	
	private ArrayList<MOrderLine> listOfOrderLine(int AD_User_ID, MPeriod period)
	{
		ArrayList<MOrderLine> orders = new ArrayList<MOrderLine>();
		String SQLSelection = "";
		String sql = "SELECT master.* FROM C_OrderLine master"
				+ " INNER JOIN C_Order o ON o.C_Order_ID = master.C_Order_ID"
				+ " INNER JOIN M_Product mp ON mp.M_Product_ID = master.M_Product_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = o.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = o.C_BPartner_Location_ID"
				+ " WHERE o.DocStatus IN ('CO', 'CL') AND o.SalesRep_ID = ?"
				+ " AND o.DateOrdered BETWEEN ? AND ? AND o.IsSOTrx = 'Y'";
		SQLSelection = getStdWhereClause();
		if(!Util.isEmpty(SQLSelection, true))
			sql += SQLSelection;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, AD_User_ID);
			stmt.setTimestamp(2, period.getStartDate());
			stmt.setTimestamp(3, period.getEndDate());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				orders.add(new MOrderLine(getCtx(), rs, get_TrxName()));
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
		finally{DB.close(rs, stmt);}
		
		return orders;
	}
	
	public String calc(String schemeType, MPeriod period, MUser user)
	{
		String incentiveType = getIncentiveType();
		ArrayList<MUNSIncentiveSchemaConfig> configs = 
				MUNSIncentiveSchemaConfig.getOfParent(this);
		if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_SalesIncentive) &&
				incentiveType.equals(X_UNS_Incentive.INCENTIVETYPE_Billing))
		{
			calcBilling(user.get_ID(), period);
		}
		else if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_SalesIncentive) &&
				incentiveType.equals(X_UNS_Incentive.INCENTIVETYPE_SalesInvoice))
		{
			ArrayList<MInvoiceLine> lines = listOfInvoiceLine(user.get_ID(), period, null);
			int linessize = lines.size();
			for(int i = 0; i < linessize; i++)
			{
				for(int j = 0; j < configs.size(); j++)
				{
					calcInvoiceLine(lines.get(i), configs.get(j));
					log.info(new Timestamp(System.currentTimeMillis()) + " Invoice Line ke " + (i+1) + " dari " + linessize);
				}
			}
		}
		else if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_SalesIncentive) &&
				incentiveType.equals(X_UNS_Incentive.INCENTIVETYPE_SalesOrder))
		{
			ArrayList<MOrderLine> lines = listOfOrderLine(user.get_ID(), period);
			int ordersize = lines.size();
			for(int i = 0; i < lines.size(); i++)
			{
				for(int j = 0; j < configs.size(); j++)
				{
					calcOrderLine(lines.get(i), configs.get(j));
					log.info(new Timestamp(System.currentTimeMillis()) + " SO ke " + (i+1) + " dari " + ordersize);
				}
			}
		}
		else if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_SalesIncentive) &&
				incentiveType.equals(X_UNS_Incentive.INCENTIVETYPE_NewOutlet))
		{
			for(int j = 0; j < configs.size(); j++)
			{
				ArrayList<MInvoice> invoices = listOfInvoice(user.get_ID(), period, true, false, configs.get(j));
				int invoicesize = invoices.size();
				for(int i = 0; i < invoices.size(); i++)
				{
					calcNewOutlet(invoices.get(i), period, configs.get(j));
					log.info(new Timestamp(System.currentTimeMillis()) + " Invoice New Outlet ke " 
					+ (i+1) + " dari " + invoicesize);
				}
			}
		}
		else if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_SalesIncentive) &&
				incentiveType.equals(X_UNS_Incentive.INCENTIVETYPE_DeductionInvoice))
		{
			for(int j = 0; j < configs.size(); j++)
			{
				ArrayList<MInvoice> invoices = listOfInvoice(user.get_ID(), period, false, true, configs.get(j));
				int invoicesize = invoices.size();
				for(int i = 0; i < invoicesize; i++)
				{
					calcDeductionInvoice(invoices.get(i), period, configs.get(j));
					log.info(new Timestamp(System.currentTimeMillis()) + " Invoice Deduction ke " 
					+ (i+1) + " dari " + invoicesize);
				}
			}
		}
		else if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_SalesIncentive) &&
				incentiveType.equals(X_UNS_Incentive.INCENTIVETYPE_Delivery))
		{
			//TODO
		}
		else if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_SalesTarget) &&
				incentiveType.equals(X_UNS_Incentive.INCENTIVETYPE_SalesInvoice)
				&& getCumulativeLevel().equals(CUMULATIVELEVEL_Line))
		{
			calcTargetInvoiceCLine(user.get_ID(), period);
		}
		else if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_SalesTarget) &&
				incentiveType.equals(X_UNS_Incentive.INCENTIVETYPE_SalesInvoice)
				&& getCumulativeLevel().equals(CUMULATIVELEVEL_Document))
		{
			calcTargetInvoiceCDoc(user.get_ID(), period);
		}
		else if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_SalesTarget) &&
				incentiveType.equals(X_UNS_Incentive.INCENTIVETYPE_SalesOrder))
		{
			calcTargetOrder(user.get_ID(), period);
		}
		else if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_SalesTarget) &&
				incentiveType.equals(X_UNS_Incentive.INCENTIVETYPE_NewOutlet))
		{
			calcTargetNewOutlet(user.get_ID(), period);
		}
		
		return null;
	}
	
	public String calcCustomerTarget(String schemeType, int C_BPartner_ID, MPeriod period)
	{
		if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_CustomerTarget)
				&& getCumulativeLevel().equals(CUMULATIVELEVEL_Document))
		{
			calcCustTargetDoc(period, C_BPartner_ID);
		}
		else if(schemeType.equals(X_UNS_IncentiveSchema.SCHEMATYPE_CustomerTarget)
				&& getCumulativeLevel().equals(CUMULATIVELEVEL_Line))
		{
			calcCustTargetLine(period, C_BPartner_ID);
		}
		
		return "";
	}
	
	private String getStdWhereClause()
	{
		String sql = "";
		if(getOrganizationSelection().equals(ORGANIZATIONSELECTION_ExcludeOnceOrganization))
			sql += " AND master.AD_Org_ID <> " + getAD_OrgTrx_ID();
		else if(getOrganizationSelection().equals(ORGANIZATIONSELECTION_IncludeOnceOrganization))
			sql += " AND master.AD_Org_ID = " + getAD_OrgTrx_ID();
		else if(getOrganizationSelection().equals(ORGANIZATIONSELECTION_ExcludeSelectedOrganization))
			sql += " AND master.AD_Org_ID NOT IN (SELECT ic.AD_Org_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'O' AND ic.UNS_Incentive_ID = " + get_ID() + ")";
		else if(getOrganizationSelection().equals(ORGANIZATIONSELECTION_IncludeSelectedOrganization))
			sql += " AND master.AD_Org_ID IN (SELECT ic.AD_Org_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'O' AND ic.UNS_Incentive_ID = " + get_ID() + ")";
		
		if(getCustomerSelection().equals(CUSTOMERSELECTION_ExcludeOnceCustomer))
			sql += " AND bp.C_BPartner_ID <> " + getC_BPartner_ID();
		else if(getCustomerSelection().equals(CUSTOMERSELECTION_IncludeOnceCustomer))
			sql += " AND bp.C_BPartner_ID = " + getC_BPartner_ID();
		else if(getCustomerSelection().equals(CUSTOMERSELECTION_ExcludeSelectedCustomer))
			sql += " AND bp.C_BPartner_ID NOT IN (SELECT ic.C_BPartner_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'C' AND ic.UNS_Incentive_ID = " + get_ID() + ")";
		else if(getCustomerSelection().equals(CUSTOMERSELECTION_IncludeSelectedCustomer))
			sql += " AND bp.C_BPartner_ID IN (SELECT ic.C_BPartner_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'C' AND ic.UNS_Incentive_ID = " + get_ID() + ")";
		
		if(getBPGroupSelection().equals(BPGROUPSELECTION_ExcludeOnceGroup))
			sql += " AND (CASE WHEN bp.C_BP_Group_ID > 0 THEN bp.C_BP_Group_ID <> " + getC_BP_Group_ID()
					+ " ELSE 1=1 END)";
		else if(getBPGroupSelection().equals(BPGROUPSELECTION_IncludeOnceGroup))
			sql += " AND (CASE WHEN bp.C_BP_Group_ID > 0 THEN bp.C_BP_Group_ID = " + getC_BP_Group_ID()
					+ " ELSE 1=1 END)";
		else if(getBPGroupSelection().equals(BPGROUPSELECTION_ExcludeSelectedGroup))
			sql += " AND (CASE WHEN bp.C_BP_Group_ID > 0 THEN bp.C_BP_Group_ID NOT IN"
					+ " (SELECT ic.C_BP_Group_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'CG' AND ic.UNS_Incentive_ID = " + get_ID() + ")"
					+ " ELSE 1=1 END)";
		else if(getBPGroupSelection().equals(BPGROUPSELECTION_IncludeSelectedGroup))
			sql += " AND (CASE WHEN bp.C_BP_Group_ID > 0 THEN bp.C_BP_Group_ID IN"
					+ " (SELECT ic.C_BP_Group_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'CG' AND ic.UNS_Incentive_ID = " + get_ID() + ")"
					+ " ELSE 1=1 END)";
		
		if(getRayonSelection().equals(RAYONSELECTION_ExcludeOnceRayon))
			sql += " AND (CASE WHEN bpl.UNS_Rayon_ID > 0 THEN bpl.UNS_Rayon_ID <> " + getUNS_Rayon_ID()
					+ " ELSE 1=1 END)";
		else if(getRayonSelection().equals(RAYONSELECTION_IncludeOnceRayon))
			sql += " AND (CASE WHEN bpl.UNS_Rayon_ID > 0 THEN bpl.UNS_Rayon_ID = " + getUNS_Rayon_ID()
					+ " ELSE 1=1 END)";
		else if(getRayonSelection().equals(RAYONSELECTION_ExcludeSelectedRayon))
			sql += " AND (CASE WHEN bpl.UNS_Rayon_ID > 0 THEN bpl.UNS_Rayon_ID NOT IN"
					+ " (SELECT ic.UNS_Rayon_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'R' AND ic.UNS_Incentive_ID = " + get_ID() + ")"
					+ " ELSE 1=1 END)";
		else if(getRayonSelection().equals(RAYONSELECTION_IncludeSelectedRayon))
			sql += " AND (CASE WHEN bpl.UNS_Rayon_ID > 0 THEN bpl.UNS_Rayon_ID IN"
					+ " (SELECT ic.UNS_Rayon_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'R' AND ic.UNS_Incentive_ID = " + get_ID() + ")"
					+ " ELSE 1=1 END)";
		
		if(getOutletGradeSelection().equals(OUTLETGRADESELECTION_ExcludeOnceGrade))
			sql += " AND (CASE WHEN bp.UNS_Outlet_Grade_ID > 0 THEN bp.UNS_Outlet_Grade_ID <> " + getUNS_Outlet_Grade_ID()
					+ " ELSE 1=1 END)";
		else if(getOutletGradeSelection().equals(OUTLETGRADESELECTION_ExcludeOnceGrade))
			sql += " AND (CASE WHEN bp.UNS_Outlet_Grade_ID > 0 THEN bp.UNS_Outlet_Grade_ID = " + getUNS_Outlet_Grade_ID()
					+ " ELSE 1=1 END)";
		else if(getOutletGradeSelection().equals(OUTLETGRADESELECTION_ExcludeSelectedGrade))
			sql += " AND CASE WHEN bp.UNS_Outlet_Grade_ID > 0 THEN bp.UNS_Outlet_Grade_ID NOT IN"
					+ " (SELECT ic.UNS_Outlet_Grade_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'GC' AND ic.UNS_Incentive_ID = " + get_ID() + ")"
					+ " ELSE 1=1 END)";
		else if(getOutletGradeSelection().equals(OUTLETGRADESELECTION_IncludeSelectedGrade))
			sql += " AND CASE WHEN bp.UNS_Outlet_Grade_ID > 0 THEN bp.UNS_Outlet_Grade_ID IN"
					+ " (SELECT ic.UNS_Outlet_Grade_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'GC' AND ic.UNS_Incentive_ID = " + get_ID() + ")"
					+ " ELSE 1=1 END)";
		
		if(getOutletTypeSelection().equals(OUTLETTYPESELECTION_ExcludeOnceType))
			sql += " AND (CASE WHEN bp.UNS_Outlet_Type_ID > 0 THEN bp.UNS_Outlet_Type_ID <> " + getUNS_Outlet_Type_ID()
					+ " ELSE 1=1 END)";
		else if(getOutletTypeSelection().equals(OUTLETTYPESELECTION_IncludeOnceType))
			sql += " AND (CASE WHEN bp.UNS_Outlet_Type_ID > 0 THEN bp.UNS_Outlet_Type_ID = " + getUNS_Outlet_Type_ID()
			+ " ELSE 1=1 END)";
		else if(getOutletTypeSelection().equals(OUTLETTYPESELECTION_ExcludeSelectedType))
			sql += " AND (CASE WHEN bp.UNS_Outlet_Type_ID > 0 THEN bp.UNS_Outlet_Type_ID NOT IN"
					+ " (SELECT ic.UNS_Outlet_Type_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'CT' AND ic.UNS_Incentive_ID = " + get_ID() + ")"
					+ " ELSE 1=1 END)";
		else if(getOutletTypeSelection().equals(OUTLETTYPESELECTION_IncludeSelectedType))
			sql += " AND (CASE WHEN bp.UNS_Outlet_Type_ID > 0 THEN bp.UNS_Outlet_Type_ID IN"
					+ " (SELECT ic.UNS_Outlet_Type_ID FROM UNS_IncentiveComponent ic"
					+ " WHERE ic.ComponentType = 'CT' AND ic.UNS_Incentive_ID = " + get_ID() + ")"
					+ " ELSE 1=1 END)";
		
		if(getIncentiveType().equals(INCENTIVETYPE_SalesInvoice) ||
				getIncentiveType().equals(INCENTIVETYPE_SalesOrder)
				|| getIncentiveType().equals(INCENTIVETYPE_Billing))
		{
			if(getProductSelection().equals(PRODUCTSELECTION_ExcludeOnceProduct))
				sql += " AND mp.M_Product_ID <> " + getM_Product_ID();
			else if(getProductSelection().equals(PRODUCTSELECTION_IncludeOnceProduct))
				sql += " AND mp.M_Product_ID = " + getM_Product_ID();
			else if(getProductSelection().equals(PRODUCTSELECTION_ExcludeSelectedProduct))
				sql += " AND mp.M_Product_ID NOT IN (SELECT ic.M_Product_ID FROM UNS_IncentiveComponent ic"
						+ " WHERE ic.ComponentType = 'P' AND ic.UNS_Incentive_ID = " + get_ID() + ")";
			else if(getProductSelection().equals(PRODUCTSELECTION_IncludeSelectedProduct))
				sql += " AND mp.M_Product_ID IN (SELECT ic.M_Product_ID FROM UNS_IncentiveComponent ic"
						+ " WHERE ic.ComponentType = 'P' AND ic.UNS_Incentive_ID = " + get_ID() + ")";
			
			if(getPCategorySelection().equals(PCATEGORYSELECTION_ExcludeOnceCategory))
				sql += " AND mp.M_Product_Category_ID <> " + getM_Product_Category_ID();
			else if(getPCategorySelection().equals(PCATEGORYSELECTION_IncludeOnceCategory))
				sql += " AND mp.M_Product_Category_ID = " + getM_Product_Category_ID();
			else if(getPCategorySelection().equals(PCATEGORYSELECTION_ExcludeSelectedCategory))
				sql += " AND mp.M_Product_Category_ID NOT IN (SELECT ic.M_Product_Category_ID"
						+ " FROM UNS_IncentiveComponent ic WHERE ic.ComponentType = 'PG'"
						+ " AND ic.UNS_Incentive_ID = " + get_ID() + ")";
			else if(getPCategorySelection().equals(PCATEGORYSELECTION_IncludeSelectedCategory))
				sql += " AND mp.M_Product_Category_ID IN (SELECT ic.M_Product_Category_ID"
						+ " FROM UNS_IncentiveComponent ic WHERE ic.ComponentType = 'PG'"
						+ " AND ic.UNS_Incentive_ID = " + get_ID() + ")";
		}
		
		if(getIncentiveType().equals(INCENTIVETYPE_Billing))
		{
			if(getTenderTypeSelection().equals(TENDERTYPESELECTION_ExcludeOneTenderType))
				sql += " AND master.TenderType <> '" + getTenderType() + "'";
			else if(getTenderTypeSelection().equals(TENDERTYPESELECTION_IncludeOnceTenderType))
				sql += " AND master.TenderType = '" + getTenderType() +"'";
			else if(getTenderTypeSelection().equals(TENDERTYPESELECTION_ExcludeSelectedTenderType))
				sql += " AND master.TenderType NOT IN (SELECT ic.TenderType FROM UNS_IncentiveComponent ic"
						+ " WHERE ic.ComponentType = 'TT' AND ic.UNS_Incentive_ID = " + get_ID() + ")";
			else if(getTenderTypeSelection().equals(TENDERTYPESELECTION_IncludeSelectedTenderType))
				sql += " AND master.TenderType IN (SELECT ic.TenderType FROM UNS_IncentiveComponent ic"
						+ " WHERE ic.ComponentType = 'TT' AND ic.UNS_Incentive_ID = " + get_ID() + ")";
		}
		
		return sql;
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		if(isCalculationOnPaid() && isUseLastSalesTrx() && !isUseLastDateTrx())
			setUseTotalPayAmt(false);
		if(isCalculationOnPaid() && !isUseLastSalesTrx())
			setUseTotalPayAmt(false);
		if(isMix() && isMixRequired() && !getProductSelection().equals(PRODUCTSELECTION_IncludeSelectedProduct)
				&& !getProductSelection().equals(PRODUCTSELECTION_IncludeOnceProduct))
		{
			setProductSelection(PRODUCTSELECTION_IncludeSelectedProduct);
		}
		
		if(getCumulativeLevel().equals(CUMULATIVELEVEL_Document)
				&& (!getProductSelection().equals(PRODUCTSELECTION_IncludeAllProduct)
				|| !getPCategorySelection().equals(PCATEGORYSELECTION_IncludeAllCategory)))
		{
			log.saveError("Error", "Cannot set product or category with level document");
			return false;
		}
			
		String delete = "";
		if(!getIncentiveType().equals(INCENTIVETYPE_SalesInvoice)
				&& !getIncentiveType().equals(INCENTIVETYPE_SalesOrder)
					&& !getIncentiveType().equals(INCENTIVETYPE_Billing))
		{
			setProductSelection(PRODUCTSELECTION_IncludeAllProduct);
			setPCategorySelection(PCATEGORYSELECTION_IncludeAllCategory);
		}
		
		if(getIncentiveType().equals(TENDERTYPESELECTION_IncludeAllTenderType))
			setTenderType(null);
		
		delete = "DELETE FROM UNS_IncentiveComponent WHERE UNS_Incentive_ID = " + get_ID()
				+ " AND ComponentType IN (";
		if(getOrganizationSelection().equals(ORGANIZATIONSELECTION_IncludeAllOrganization))
		{
			setAD_OrgTrx_ID(-1);
			delete += "'O',";			
		}
		if(getCustomerSelection().equals(CUSTOMERSELECTION_IncludeAllCustomer))
		{
			setC_BPartner_ID(-1);
			delete += "'C',";
		}
		if(getProductSelection().equals(PRODUCTSELECTION_IncludeAllProduct))
		{
			setM_Product_ID(-1);
			delete += "'PG',";
		}
		if(getBPGroupSelection().equals(BPGROUPSELECTION_IncludeAllGroup))
		{
			setC_BP_Group_ID(-1);
			delete += "'CG',";
		}
		if(getRayonSelection().equals(RAYONSELECTION_IncludeAllRayon))
		{
			setUNS_Rayon_ID(-1);
			delete += "'R',";
		}
		if(getOutletGradeSelection().equals(OUTLETGRADESELECTION_IncludeAllGrade))
		{
			setUNS_Outlet_Grade_ID(-1);
			delete += "'GC',";
		}
		if(getOutletTypeSelection().equals(OUTLETTYPESELECTION_IncludeAllType))
		{
			setUNS_Outlet_Type_ID(-1);
			delete += "'CT',";
		}
		if(getTenderTypeSelection().equals(TENDERTYPESELECTION_IncludeAllTenderType))
		{
			setTenderType(null);
			delete += "'TT'";
		}
		
		delete += ")";
		
		delete = delete.replace(",)", ")");
		DB.executeUpdate(delete, get_TrxName());
		
		if(isProcessed())
		{
			List<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
			for(MUNSIncentiveSchemaConfig config : configs)
			{
				config.setProcessed(true);
				config.saveEx();
			}
		}
		
		return true;
	}
	
	protected boolean beforeDelete()
	{
		String sql = "DELETE FROM UNS_IncentiveComponent WHERE UNS_Incentive_ID = ?";
		DB.executeUpdate(sql, get_ID(), get_TrxName());
		sql = "DELETE FROM UNS_IncentiveSchemaConfig WHERE UNS_Incentive_ID = ?";
		DB.executeUpdate(sql, get_ID(), get_TrxName());
		return true;
	}
	
	private void calcTargetInvoiceCLine(int AD_User_ID, MPeriod period)
	{
		BigDecimal total = Env.ZERO;
		boolean isMatch = false;
		BigDecimal incentive = Env.ZERO;
		List<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
		String sql = "SELECT mp.M_Product_ID, SUM(CASE WHEN dt.DocBaseType = 'ARI' THEN Master.QtyInvoiced"
				+ " ELSE (Master.QtyInvoiced * -1) END), SUM(CASE WHEN dt.DocBaseType = 'ARI' THEN"
				+ " Master.LineNetAmt ELSE (Master.LineNetAmt * -1) END) FROM C_InvoiceLine Master"
				+ " INNER JOIN C_Invoice iv ON iv.C_Invoice_ID = Master.C_Invoice_ID"
				+ " INNER JOIN C_DocType dt ON dt.C_DocType_ID = iv.C_DocType_ID"
				+ " INNER JOIN M_Product mp ON mp.M_Product_ID = Master.M_Product_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = iv.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = iv.C_BPartner_Location_ID"
				+ " WHERE iv.IsSOTrx = 'Y' AND iv.DocStatus IN ('CO', 'CL')"
				+ " AND iv.SalesRep_ID = ? AND iv.DateInvoiced BETWEEN ? AND ?";
		if(isCreditNoteMinusValue())
			sql += " AND dt.DocBaseType IN ('ARI', 'ARC')";
		else
			sql += " AND dt.DocBaseType = 'ARI'";
		
		sql += getStdWhereClause();
		
		sql += " GROUP BY mp.M_Product_ID";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, AD_User_ID);
			stmt.setTimestamp(2, period.getStartDate());
			stmt.setTimestamp(3, period.getEndDate());
			rs = stmt.executeQuery();
			if(isMix() && isMixRequired())
			{
				MUNSIncentiveComponent[] cmps = MUNSIncentiveComponent.getByType(getCtx(), get_ID(), 
						X_UNS_IncentiveComponent.COMPONENTTYPE_Product, get_TrxName());
				for(int i=0; i<cmps.length; i++)
				{
					isMatch = false;
					while(rs.next())
					{
						if(rs.getInt(1) == cmps[i].getM_Product_ID())
						{
							if(isQuantityBased())
								total = total.add(rs.getBigDecimal(2));
							else
								total = total.add(rs.getBigDecimal(3));
							isMatch = true;
							break;
						}
					}
					if(!isMatch)
						break;
				}
				
				if(isMatch)
				{
					for(MUNSIncentiveSchemaConfig config : configs)
					{
						if(total.compareTo(config.getRangeFrom()) >= 0 
								&& total.compareTo(config.getRangeTo()) <= 0)
						{
							if(config.getQtyMultiplier().signum() > 0)
							{
								total = total.divide(config.getQtyMultiplier(), RoundingMode.DOWN);
								incentive = total.multiply(incentive);
							}
							else
								incentive = config.getIncentive();
							
							MUNSAchievedIncentiveLine ref = MUNSAchievedIncentiveLine.getCreate(
									getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
									period.getEndDate(), AD_User_ID, 0, total, incentive, 
									X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
									this, 0, 0, get_TrxName());
							if(config.isSupervisorReward())
							{
								ArrayList<MUNSIncentiveConfig> iconfigs = 
										MUNSIncentiveConfig.getSupervisor(getCtx(), null, AD_User_ID,
												getIncentiveType(), get_TrxName());
								for(int j = 0; j < iconfigs.size(); j++)
								{
									iconfigs.get(j).runSupervisorTree(null, getIncentiveType(),
											config.isSupervisorContinuous(), AD_User_ID, 
											total, incentive, ref.get_ID(), this);
								}
							}
							break;
						}
					}
				}
			}
			else if(isMix() && !isMixRequired())
			{
				while(rs.next())
				{
					if(isQuantityBased())
						total = total.add(rs.getBigDecimal(2));
					else
						total = total.add(rs.getBigDecimal(3));
				}
				
				if(total.signum() > 0)
				{
					for(MUNSIncentiveSchemaConfig config : configs)
					{
						if(total.compareTo(config.getRangeFrom()) >= 0 
								&& total.compareTo(config.getRangeTo()) <= 0)
						{
							if(config.getQtyMultiplier().signum() > 0)
							{
								total = total.divide(config.getQtyMultiplier(), RoundingMode.DOWN);
								incentive = total.multiply(incentive);
							}
							else
								incentive = config.getIncentive();
							
							MUNSAchievedIncentiveLine.getCreate(
									getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
									period.getEndDate(), AD_User_ID, 0, total, incentive, 
									X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
									this, 0, 0, get_TrxName());
							break;
						}
					}
				}
			}
			else if(!isMix())
			{
				while(rs.next())
				{
					if(isQuantityBased())
						total = rs.getBigDecimal(2);
					else
						total = rs.getBigDecimal(3);
					
					if(total.signum() > 0)
					{
						for(MUNSIncentiveSchemaConfig config : configs)
						{
							if(total.compareTo(config.getRangeFrom()) >= 0 
									&& total.compareTo(config.getRangeTo()) <= 0)
							{
								if(config.getQtyMultiplier().signum() > 0)
								{
									total = total.divide(config.getQtyMultiplier(), RoundingMode.DOWN);
									incentive = total.multiply(incentive);
								}
								else
									incentive = config.getIncentive();
								
								MUNSAchievedIncentiveLine.getCreate(
										getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
										period.getEndDate(), AD_User_ID, 0, total, incentive, 
										X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
										this, 0, rs.getInt(1), get_TrxName());
								break;
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}
	}
	
	private void calcTargetInvoiceCDoc(int AD_User_ID, MPeriod period)
	{
		BigDecimal total = Env.ZERO;
		BigDecimal incentive = Env.ZERO;
		List<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
		String sql = "SELECT COUNT(*) FROM C_Invoice Master"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = Master.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = Master.C_BPartner_Location_ID"
				+ " WHERE Master.IsSOTrx = 'Y' AND Master.DocStatus IN ('CO', 'CL')"
				+ " AND Master.SalesRep_ID = ? AND Master.DateInvoiced BETWEEN ? AND ?";
		
		sql += getStdWhereClause();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, AD_User_ID);
			stmt.setTimestamp(2, period.getStartDate());
			stmt.setTimestamp(3, period.getEndDate());
			rs = stmt.executeQuery();
			while(rs.next())
			{	
				total = rs.getBigDecimal(1);
				if(total.signum() > 0)
				{
					for(MUNSIncentiveSchemaConfig config : configs)
					{
						if(total.compareTo(config.getRangeFrom()) >= 0 
								&& total.compareTo(config.getRangeTo()) <= 0)
						{
							if(config.getQtyMultiplier().signum() > 0)
							{
								BigDecimal multiple = total.
										divide(config.getQtyMultiplier(), RoundingMode.DOWN);
								incentive = multiple.multiply(config.getIncentive());
							}
							else
								incentive = config.getIncentive();
							
							MUNSAchievedIncentiveLine.getCreate(
									getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
									period.getEndDate(), AD_User_ID, 0, total, incentive, 
									X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
									this, 0, 0, get_TrxName());
							break;
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}
	}

	private void calcTargetOrder(int AD_User_ID, MPeriod period)
	{
		BigDecimal total = Env.ZERO;
		boolean isMatch = false;
		BigDecimal incentive = Env.ZERO;
		List<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
		String sql = "SELECT mp.M_Product_ID, SUM(Master.QtyOrdered), SUM(Master.LineNetAmt)"
				+ " FROM C_OrderLine Master"
				+ " INNER JOIN C_Order od ON od.C_Order_ID = Master.C_Order_ID"
				+ " INNER JOIN M_Product mp ON mp.M_Product_ID = Master.M_Product_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = iv.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = iv.C_BPartner_Location_ID"
				+ " WHERE od.IsSOTrx = 'Y' AND od.DocStatus IN ('CO', 'CL')"
				+ " AND od.SalesRep_ID = ? AND od.DateOrdered BETWEEN ? AND ?";
		
		sql += getStdWhereClause();
		
		sql += " GROUP BY mp.M_Product_ID";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, AD_User_ID);
			stmt.setTimestamp(2, period.getStartDate());
			stmt.setTimestamp(3, period.getEndDate());
			rs = stmt.executeQuery();
			if(isMix() && isMixRequired())
			{
				MUNSIncentiveComponent[] cmps = MUNSIncentiveComponent.getByType(getCtx(), get_ID(), 
						X_UNS_IncentiveComponent.COMPONENTTYPE_Product, get_TrxName());
				for(int i=0; i<cmps.length; i++)
				{
					isMatch = false;
					while(rs.next())
					{
						if(rs.getInt(1) == cmps[i].getM_Product_ID())
						{
							if(isQuantityBased())
								total = total.add(rs.getBigDecimal(2));
							else
								total = total.add(rs.getBigDecimal(3));
							isMatch = true;
							break;
						}
					}
					if(!isMatch)
						break;
				}
				
				if(isMatch)
				{
					for(MUNSIncentiveSchemaConfig config : configs)
					{
						if(total.compareTo(config.getRangeFrom()) >= 0 
								&& total.compareTo(config.getRangeTo()) <= 0)
						{
							if(config.getQtyMultiplier().signum() > 0)
							{
								total = total.divide(config.getQtyMultiplier(), RoundingMode.DOWN);
								incentive = total.multiply(incentive);
							}
							else
								incentive = config.getIncentive();
							
							MUNSAchievedIncentiveLine.getCreate(
									getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
									period.getEndDate(), AD_User_ID, 0, total, incentive, 
									X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
									this, 0, 0, get_TrxName());
							break;
						}
					}
				}
			}
			else if(isMix() && !isMixRequired())
			{
				while(rs.next())
				{
					if(isQuantityBased())
						total = total.add(rs.getBigDecimal(2));
					else
						total = total.add(rs.getBigDecimal(3));
				}
				
				if(total.signum() > 0)
				{
					for(MUNSIncentiveSchemaConfig config : configs)
					{
						if(total.compareTo(config.getRangeFrom()) >= 0 
								&& total.compareTo(config.getRangeTo()) <= 0)
						{
							if(config.getQtyMultiplier().signum() > 0)
							{
								total = total.divide(config.getQtyMultiplier(), RoundingMode.DOWN);
								incentive = total.multiply(incentive);
							}
							else
								incentive = config.getIncentive();
							
							MUNSAchievedIncentiveLine.getCreate(
									getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
									period.getEndDate(), AD_User_ID, 0, total, incentive, 
									X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
									this, 0, 0, get_TrxName());
						}
					}
				}
			}
			else if(!isMix())
			{
				while(rs.next())
				{
					if(isQuantityBased())
						total = rs.getBigDecimal(2);
					else
						total = rs.getBigDecimal(3);
					
					if(total.signum() > 0)
					{
						for(MUNSIncentiveSchemaConfig config : configs)
						{
							if(total.compareTo(config.getRangeFrom()) >= 0 
									&& total.compareTo(config.getRangeTo()) <= 0)
							{
								if(config.getQtyMultiplier().signum() > 0)
								{
									total = total.divide(config.getQtyMultiplier(), RoundingMode.DOWN);
									incentive = total.multiply(incentive);
								}
								else
									incentive = config.getIncentive();
								
								MUNSAchievedIncentiveLine.getCreate(
										getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
										period.getEndDate(), AD_User_ID, 0, total, incentive, 
										X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
										this, 0, rs.getInt(1), get_TrxName());
								break;
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}
	}
	
	private void calcTargetNewOutlet(int AD_User_ID, MPeriod period)
	{
		BigDecimal total = Env.ZERO;
		BigDecimal incentive = Env.ZERO;
		List<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
		String sql = "SELECT COUNT(Master.*), SUM(Master.GrandTotal) FROM C_Invoice Master"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = iv.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = iv.C_BPartner_Location_ID"
				+ " WHERE iv.IsSOTrx = 'Y' AND iv.DocStatus IN ('CO', 'CL')"
				+ " AND iv.SalesRep_ID = ? AND iv.DateInvoiced BETWEEN ? AND ? AND dt.DocBaseType = 'ARI'";
		
		sql += getStdWhereClause();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, AD_User_ID);
			stmt.setTimestamp(2, period.getStartDate());
			stmt.setTimestamp(3, period.getEndDate());
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				if(isQuantityBased())
					total = rs.getBigDecimal(1);
				else
					total = rs.getBigDecimal(2);
				
				if(total.signum() > 0)
				{
					for(MUNSIncentiveSchemaConfig config : configs)
					{
						if(total.compareTo(config.getRangeFrom()) >= 0 
								&& total.compareTo(config.getRangeTo()) <= 0)
						{
							if(!isQuantityBased() && !config.isIncentiveAmount())
								incentive = (total.multiply(config.getIncentive())).divide(Env.ONEHUNDRED);
							else
								incentive = config.getIncentive();
							
							MUNSAchievedIncentiveLine.getCreate(
									getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
									period.getEndDate(), AD_User_ID, 0, total, incentive, 
									X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
									this, 0, 0, get_TrxName());
							break;
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}
	}
	
	private void calcCustTargetDoc(MPeriod period, int C_BPartner_ID)
	{
		List<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
		String sql = "SELECT bp.C_BPartner_ID, SUM(CASE WHEN dt.DocBaseType = 'ARI' THEN"
				+ " Master.GrandTotal ELSE (Master.GrandTotal * -1) END), SUM(CASE WHEN"
				+ " dt.DocBaseType = 'ARI' THEN 1 ELSE -1 END)"
				+ " FROM C_Invoice Master"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = Master.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = Master.C_BPartner_Location_ID"
				+ " INNER JOIN C_DocType dt ON dt.C_DocType_ID = Master.C_DocType_ID"
				+ " WHERE Master.IsSOTrx = 'Y' AND Master.DocStatus IN ('CO', 'CL')"
				+ " AND Master.DateInvoiced BETWEEN ? AND ?";
		if(isCreditNoteMinusValue())
			sql += " AND dt.DocBaseType IN ('ARI', 'ARC')";
		else
			sql += " AND dt.DocBaseType = 'ARI'";
		
		if(C_BPartner_ID > 0)
			sql += " AND bp.C_BPartner_ID = " + C_BPartner_ID;
		
		sql += getStdWhereClause();
		
		sql += " GROUP BY bp.C_BPartner_ID";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setTimestamp(1, period.getStartDate());
			stmt.setTimestamp(2, period.getEndDate());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				BigDecimal baseAmt = Env.ZERO;
				BigDecimal incentive = Env.ZERO;
				for(MUNSIncentiveSchemaConfig config : configs)
				{
					if(baseAmt.compareTo(config.getRangeFrom()) >= 0 
							&& baseAmt.compareTo(config.getRangeTo()) <= 0)
					{
						if(!isQuantityBased() && !config.isIncentiveAmount())
							incentive = (baseAmt.multiply(config.getIncentive())).divide(Env.ONEHUNDRED);
						else
							incentive = config.getIncentive();
						
						MUNSAchievedIncentiveLine.getCreate(
								getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
								period.getEndDate(), 0, 0, baseAmt, incentive, 
								X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
								this, 0, 0, get_TrxName());
					}
				}
			}
			
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
	}
	
	private void calcCustTargetLine(MPeriod period, int C_BPartner_ID)
	{
		List<MUNSIncentiveSchemaConfig> configs = MUNSIncentiveSchemaConfig.getOfParent(this);
		BigDecimal total = Env.ZERO;
		int bpartnerID = 0;
		boolean isMatch = false;
		String sql = "SELECT bp.C_BPartner_ID, SUM(CASE WHEN dt.DocBaseType = 'ARI' THEN"
				+ " il.LineNetAmt ELSE (il.LineNetAmt * -1) END), SUM(CASE WHEN"
				+ " dt.DocBaseType = 'ARI' THEN il.QtyInvoiced ELSE (il.QtyInvoiced * -1) END),"
				+ " il.M_Product_ID"
				+ " FROM C_Invoice Master"
				+ " INNER JOIN C_InvoiceLine il ON il.C_Invoice_ID = Master.C_Invoice_ID"
				+ " INNER JOIN M_Product mp ON mp.M_Product_ID = il.M_Product_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = Master.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = Master.C_BPartner_Location_ID"
				+ " INNER JOIN C_DocType dt ON dt.C_DocType_ID = Master.C_DocType_ID"
				+ " WHERE Master.IsSOTrx = 'Y' AND Master.DocStatus IN ('CO', 'CL')"
				+ " AND Master.DateInvoiced BETWEEN ? AND ?";
		if(isCreditNoteMinusValue())
			sql += " AND dt.DocBaseType IN ('ARI', 'ARC')";
		else
			sql += " AND dt.DocBaseType = 'ARI'";
		
		if(C_BPartner_ID > 0)
			sql += " AND bp.C_BPartner_ID = " + C_BPartner_ID;
		
		sql += getStdWhereClause();
		
		sql += " GROUP BY bp.C_BPartner_ID, il.M_Product_ID ORDER BY bp.C_BPartner_ID";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setTimestamp(1, period.getStartDate());
			stmt.setTimestamp(2, period.getEndDate());
			rs = stmt.executeQuery();
			if(isMix() && isMixRequired())
			{
				MUNSIncentiveComponent[] cmps = MUNSIncentiveComponent.getByType(getCtx(), get_ID(), 
						X_UNS_IncentiveComponent.COMPONENTTYPE_Product, get_TrxName());
				for(int i=0; i<cmps.length; i++)
				{
					isMatch = false;
					while(rs.next())
					{
						if(!rs.first() && bpartnerID != rs.getInt(1))
						{
							if(isMatch)
							{
								for(MUNSIncentiveSchemaConfig config : configs)
								{
									if(total.compareTo(config.getRangeFrom()) >= 0 
											&& total.compareTo(config.getRangeTo()) <= 0)
									{
										if(config.getQtyMultiplier().signum() > 0
												&& config.getQtyMultiplier().compareTo(total) == 1)
											break;
										
										BigDecimal incentive = Env.ZERO;
										if(!isQuantityBased() && !config.isIncentiveAmount())
										{
											if(!config.isIncentiveAmount())
											{
												incentive = (total.multiply(config.getIncentive())).divide(Env.ONEHUNDRED);
											}
											else
											{
												if(config.getQtyMultiplier().signum() > 0)
												{
													BigDecimal multiple = total.divide(
															config.getQtyMultiplier(), RoundingMode.DOWN);
													incentive = multiple.multiply(config.getIncentive());
												}
												else
													incentive = config.getIncentive();
											}
											
											MUNSAchievedIncentiveLine.getCreate(
													getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
													period.getEndDate(), 0, 0, total, incentive, 
													X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
													this, rs.getInt(1), 0, get_TrxName());
										}
									}
								}
							}
						}
						bpartnerID = rs.getInt(1);
						if(rs.getInt(4) == cmps[i].getM_Product_ID())
						{
							if(isQuantityBased())
								total = total.add(rs.getBigDecimal(3));
							else
								total = total.add(rs.getBigDecimal(2));
							isMatch = true;
							break;
						}
					}
					if(!isMatch)
						break;
				}
			}
			else if(isMix() && !isMixRequired())
			{				
				while (rs.next())
				{
					if(isQuantityBased())
						total = total.add(rs.getBigDecimal(3));
					else
						total = total.add(rs.getBigDecimal(2));
					
					for(MUNSIncentiveSchemaConfig config : configs)
					{
						if(total.compareTo(config.getRangeFrom()) >= 0 
								&& total.compareTo(config.getRangeTo()) <= 0)
						{
							BigDecimal incentive = Env.ZERO;
							if(!config.isIncentiveAmount())
							{
								incentive = (total.multiply(config.getIncentive())).divide(Env.ONEHUNDRED);
							}
							else
							{
								if(config.getQtyMultiplier().signum() > 0)
								{
									BigDecimal multiple = total.divide(config.getQtyMultiplier(), RoundingMode.DOWN);
									incentive = multiple.multiply(config.getIncentive());
								}
								else
									incentive = config.getIncentive();
								
								MUNSAchievedIncentiveLine.getCreate(
										getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
										period.getEndDate(), 0, 0, total, incentive, 
										X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
										this, rs.getInt(1), 0, get_TrxName());
							}
						}
					}
				}
			}
			else if(!isMix())
			{
				while(rs.next())
				{
					if(isQuantityBased())
						total = rs.getBigDecimal(3);
					else
						total = rs.getBigDecimal(2);
					
					if(total.signum() > 0)
					{
						for(MUNSIncentiveSchemaConfig config : configs)
						{
							if(total.compareTo(config.getRangeFrom()) >= 0 
									&& total.compareTo(config.getRangeTo()) <= 0)
							{
								BigDecimal incentive = Env.ZERO;
								if(config.getQtyMultiplier().signum() > 0)
								{
									BigDecimal multiple = total.divide(config.getQtyMultiplier(), RoundingMode.DOWN); 
									incentive = multiple.multiply(config.getIncentive());
								}
								else
									incentive = config.getIncentive();
								
								MUNSAchievedIncentiveLine.getCreate(
										getCtx(), null, getUNS_IncentiveSchema().getSchemaType(),
										period.getEndDate(), 0, 0, total, incentive, 
										X_UNS_AchievedIncentive_Line.SOURCETYPE_InvoiceLine, null,
										this, rs.getInt(1), rs.getInt(4), get_TrxName());
								break;
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(CLogger.retrieveErrorString("Error"));
		}
	}
}