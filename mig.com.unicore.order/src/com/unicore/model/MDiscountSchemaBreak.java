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
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MProductCategory;
import org.compiere.model.PO;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.model.X_UNS_Outlet_Grade;
import com.uns.base.model.Query;
import com.uns.util.UNTPair;

import com.unicore.base.model.MInvoiceLine;
import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UNTA-Andy
 * 
 */
public class MDiscountSchemaBreak extends com.unicore.model.X_M_DiscountSchemaBreak
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3801671053888666767L;
	private MUNSDSBreakLine[] m_breaksLine;
	private MDiscountSchema m_parent;
	private static final int PLUSH_HALFYEAR =  6;
	private static final int PLUSH_MONTH = 1;
	private static final int PLUSH_QUARTED = 4;
	private static final int PLUSH_YEAR = 12;
	private MUNSDiscountCustomer[] m_listCustomer = null;
	private MBPartner[] m_includedPartners = null;
	private X_UNS_Outlet_Grade[] m_includedGrades = null;
	private MBPGroup[] m_includedGroups = null;
	private MUNSDiscountProduct[] m_products = null;
	

	/**
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param M_DiscountSchemaBreak_ID id
	 * @param trxName transaction
	 */
	public MDiscountSchemaBreak(Properties ctx, int M_DiscountSchemaBreak_ID, String trxName)
	{
		super(ctx, M_DiscountSchemaBreak_ID, trxName);
	} // MDiscountSchemaBreak

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MDiscountSchemaBreak(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	} // MDiscountSchemaBreak


	/**
	 * Criteria apply
	 * 
	 * @param Value amt or qty
	 * @param M_Product_ID product
	 * @param M_Product_Category_ID category
	 * @return true if criteria met
	 */
	public boolean applies(BigDecimal Value, int M_Product_ID, int M_Product_Category_ID)
	{
		if (!isActive())
			return false;

		// below break value
		if (Value.compareTo(getBreakValue()) < 0)
			return false;

		// No Product / Category
		if (getM_Product_ID() == 0 && getM_Product_Category_ID() == 0)
			return true;

		// Product
		if (getM_Product_ID() == M_Product_ID)
			return true;

		// Category
		if (M_Product_Category_ID != 0)
			return getM_Product_Category_ID() == M_Product_Category_ID;

		// Look up Category of Product
		return MProductCategory.isCategory(getM_Product_Category_ID(), M_Product_ID);
	} // applies

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getSeqNo() <= 0)
		{
			String sql = "SELECT COALESCE(MAX(seqNo), 0) + 10 FROM " + Table_Name + " WHERE " 
							+ COLUMNNAME_M_DiscountSchema_ID + " =?";
			int value = DB.getSQLValue(get_TrxName(), sql, getM_DiscountSchema_ID());
			setSeqNo(value);
		}
		// If M_Product_ID and M_Product_Category_ID is set, M_Product_ID has priority - teo_sarca [
		// 2012439 ]
		if (getM_Product_ID() > 0 && getM_Product_Category_ID() > 0)
			setM_Product_Category_ID(I_ZERO);
		
		
		if(isDiscountByPeriodic())
		{
			String targetPeriod = getTargetPeriodic();
			if(null == targetPeriod)
			{
				throw new AdempiereUserError("You must define Target Period if you choose " +
						"Discount By Periodic as Target Break.");
			}
			if(null != getParent().getValidTo())
			{
				if(targetPeriod.equals(TARGETPERIODIC_HalfYear))
					validateValidDate(PLUSH_HALFYEAR);
				else if(targetPeriod.equals(TARGETPERIODIC_Monthly))
					validateValidDate(PLUSH_MONTH);
				else if(targetPeriod.equals(TARGETPERIODIC_QuarterYear))
					validateValidDate(PLUSH_QUARTED);
				else if(targetPeriod.equals(TARGETPERIODIC_Year))
					validateValidDate(PLUSH_YEAR);
				else
					throw new AdempiereException("Unhandled Target Periodic " + targetPeriod);
			}			
		}
		
		if(getProductSelection().equals(PRODUCTSELECTION_IncludedAllProduct))
		{
			setC_UOM_ID(-1);
		}
		
		if(getParent().isCumulativeDocument())
		{
			 if(isCalculationQty() && (!isMix() || isMixRequired()))
			 {
				 throw new AdempiereUserError("Calculate type Qty is not permite" +
							". Because Discount schema cumulative level is Document"); 
			 }
			 else if(getM_Product_ID() > 0 || getM_Product_Category_ID() > 0)
			 {
				 throw new AdempiereUserError("Please set Product Category and Product to null" +
							". Because Discount schema cumulative level is Document");
			 }
		}
		
		if(is_ValueChanged(COLUMNNAME_SelectionType))
		{
			deleteCustomerList();
		}
		
		if(getBudgetType() != null && getBudgetType().equals(BUDGETTYPE_NonBudgeted))
		{
			setQtyAllocated(Env.ZERO);
			deleteBudgetList();
		}
		
		if(getParent().getVendor_ID() <= 0 && isVendorCashback())
		{
			throw new AdempiereException("Please define vendor at the header document.");
		}
		
		if(getParent().getSalesType() != null)
		{
			if(null == getSalesType())
				setSalesType(getParent().getSalesType());
			else
				if(!getParent().getSalesType().equals(getSalesType()))
					getParent().setSalesType(null);
		}
		if(getParent().getSalesLevel() != null)
		{
			if(null == getSalesLevel())
				setSalesLevel(getParent().getSalesLevel());
			else
				if(!getParent().getSalesLevel().equals(getSalesLevel()))
					getParent().setSalesLevel(null);
		}
		validateSelection();
		checkSequence();
		getParent().saveEx();
		if(!getDiscountType().equals(DISCOUNTTYPE_FlatProductBonuses)
						&& !getDiscountType().equals(DISCOUNTTYPE_PercentProductBonuses)
						&& !getDiscountType().equals(DISCOUNTTYPE_MultipleFlatProductBonuses)
						&& !isProcessed())
		{
			deleteLines();
		}
		//
		return true;
	}
	
	private void deleteLines()
	{
		try 
		{
			for (MUNSDiscountBonus discountBonus: MUNSDiscountBonus.getBonus(this))
			{
				discountBonus.deleteEx(true);
			}
		}
		catch (Exception ex) 
		{
			throw new AdempiereException("Failed to delete bonus Lines " + ex.getMessage());
		}
	}
	
	private void deleteBonuses()
	{
		try {
			for (MUNSDSBreakLine breakLine: getBreakLines(true))
			{
				breakLine.deleteEx(true);
			}
		}catch (Exception ex) {
			throw new AdempiereException("Failed to delete Break LInes " + ex.getMessage());
		}	
	}
	
	private void deleteCustomerList()
	{
		try 
		{
			for(MUNSDiscountCustomer dc : getListCustomer(false))
			{
				dc.deleteEx(true);
			} 
			
		}
		catch (Exception ex) 
		{
				throw new AdempiereException("Failed to delete Customer List" + ex.getMessage());
		}
	}
	
	private void deleteBudgetList()
	{
		try
		{
			for(MUNSSalesBudget budget : getSalesBudgetLines(false))
			{
				budget.deleteEx(true);
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException("Failed to delete Budget List : " + e.getMessage());
		}
	}
	
	/**
	 * Before Delete
	 * 
	 * @return true
	 */
	protected boolean beforeDelete()
	{		
		deleteLines();
		deleteBonuses();
		deleteCustomerList();
		deleteBudgetList();
		
		return super.beforeDelete();
	} // beforeDelete

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MDiscountSchemaBreak[");
		sb.append(get_ID()).append("-Seq=").append(getSeqNo());
		if (getM_Product_Category_ID() != 0)
			sb.append(",M_Product_Category_ID=").append(getM_Product_Category_ID());
		if (getM_Product_ID() != 0)
			sb.append(",M_Product_ID=").append(getM_Product_ID());
		sb.append(",Break=").append(getBreakValue());
		if (isBPartnerFlatDiscount())
			sb.append(",FlatDiscount");
		else
			sb.append(",Discount=").append(getBreakDiscount());
		sb.append("]");
		return sb.toString();
	} // toString

	public BigDecimal getCalculateQty(BigDecimal qty, BigDecimal price)
	{
		BigDecimal retValue = Env.ZERO;
		if (getDiscountType().equals(DISCOUNTTYPE_PercentProductBonuses))
		{
			if (getCalculationType().equals(CALCULATIONTYPE_Value))
			{
				retValue =
						getBreakValue().divide(price, 0, RoundingMode.DOWN).multiply(qty)
								.divide(Env.ONEHUNDRED, 0, RoundingMode.HALF_UP);
			}
			else
			{
				retValue = getBreakValue().multiply(qty).divide(Env.ONEHUNDRED, 0, RoundingMode.HALF_UP);
			}
		}
		else if (getDiscountType().equals(DISCOUNTTYPE_FlatProductBonuses))
		{
			retValue = qty;
		}
		
		return retValue;
	}

	/**
	 * Get BreakLines
	 * 
	 * @param boolean reload
	 * @return MUNSDSBreakLine
	 */
	public MUNSDSBreakLine[] getBreakLines(boolean reload)
	{
		if (m_breaksLine != null && !reload)
		{
			set_TrxName(m_breaksLine, get_TrxName());
			return m_breaksLine;
		}
		
		List<MUNSDSBreakLine> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, 
				MUNSDSBreakLine.Table_Name, MUNSDSBreakLine.COLUMNNAME_M_DiscountSchemaBreak_ID + "=?", 
				get_TrxName()).setParameters(get_ID()).
				setOrderBy(COLUMNNAME_SeqNo).list();

		m_breaksLine = new MUNSDSBreakLine[list.size()];
		list.toArray(m_breaksLine);
		
		return m_breaksLine;
	} // getBreaksLines
	
	public MDiscountSchema getParent()
	{
		if (m_parent == null)
			m_parent = new MDiscountSchema(getCtx(), getM_DiscountSchema_ID(), get_TrxName());
		
		return m_parent;
	}
	
	public boolean isDiscountEveryPOSO()
	{
		boolean yes = false;
		if(null != getTargetBreak())
			yes = TARGETBREAK_DiscountEveryPOSO.equals(getTargetBreak());
		return yes;
	}
	

	public boolean isDiscountByPeriodic()
	{
		boolean yesNo = false;
		if(null != getTargetBreak())
			yesNo = TARGETBREAK_DiscountByPeriodic.equals(getTargetBreak());
		return yesNo;
	}
	
	private void validateValidDate(int staticValidTo)
	{
		Timestamp dateFrom = getParent().getValidFrom();
		Timestamp dateto = getParent().getValidTo();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dateFrom.getTime());
		cal.add(Calendar.MONTH, staticValidTo);
		cal.add(Calendar.DATE, -1);
		Timestamp calDate = new Timestamp(cal.getTimeInMillis());
		if(calDate.after(dateto))
		{
			throw new AdempiereUserError("Target periodic is out of range valid from and valid to.");
		}
	}
	
	public boolean isCalculationQty()
	{
		String calculateType = getCalculationType();
		if(null == calculateType)
			return false;
		
		return calculateType.equals(CALCULATIONTYPE_Qty);		
	}
	
	public boolean isCalculationValue()
	{
		String calculateType = getCalculationType();
		if(null == calculateType)
			return false;
		
		return calculateType.equals(CALCULATIONTYPE_Value);
	}
	
	public BigDecimal validateBudgetQtyOrValOfOrder(BigDecimal qtyOrVal)
	{
		if(qtyOrVal.compareTo(getAllowedOrder()) > 0)
			qtyOrVal = getAllowedOrder();
		
		return qtyOrVal;
	}
	
	public BigDecimal getAllowedInOut()
	{
		return getQtyAllocated().subtract(getMovementQty());
	}
	
	public BigDecimal getAllowedInvoice()
	{
		return getQtyAllocated().subtract(getQtyInvoiced());
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getAllowedOrder()
	{
		return getQtyAllocated().subtract(getQtyReserved());
	}
	
	
	/**
	 * 
	 */
	private MUNSSalesBudget[] m_salesBudgets;
	public MUNSSalesBudget[] getSalesBudgetLines(boolean requery)
	{
		if(null != m_salesBudgets && !requery)
		{
			set_TrxName(m_salesBudgets, get_TrxName());
			return m_salesBudgets;
		}
		
		List<MUNSSalesBudget> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID
				, MUNSSalesBudget.Table_Name
				, MUNSSalesBudget.COLUMNNAME_M_DiscountSchemaBreak_ID + "=? AND UNS_DSBreakLine_ID IS NULL"
				, get_TrxName())
				.setParameters(getM_DiscountSchemaBreak_ID()).list();
		
		m_salesBudgets = new MUNSSalesBudget[list.size()];
		list.toArray(m_salesBudgets);
		
		return m_salesBudgets;
	}


	public boolean isBudgetAvailable (UNSDiscountBonus discountBonus) {
		BigDecimal comparator = Env.ZERO;
		if (getBudgetType() == null || BUDGETTYPE_NonBudgeted.equals(getBudgetType()))
			return true;
		if (BUDGETCALCULATION_Quantity.equals(getBudgetCalculation())) {
			if (isMix() || isMixRequired()) {
				comparator = discountBonus.getConversionMixQty();
			} else
				comparator = discountBonus.getConversionQty();
		} else if (BUDGETCALCULATION_Amount.equals(getBudgetCalculation())) {
			if (isMix() || isMixRequired()) {
				comparator = discountBonus.getLinenetAmtMix();
			} else
				comparator = discountBonus.getLineNetAmount();
		} else {
			comparator = discountBonus.getDiscountBonus();
		}

		BigDecimal availableBudget = Env.ZERO;
		if (BUDGETTYPE_GeneralBudget.equals(getBudgetType())) {
			if (MOrder.Table_ID == discountBonus.getTable_ID() || MOrderLine.Table_ID == discountBonus.getTable_ID()) {
				availableBudget = getAllowedOrder();
			} else if (MInvoice.Table_ID == discountBonus.getTable_ID() || MInvoiceLine.Table_ID == discountBonus.getTable_ID()) {
				availableBudget = getAllowedInvoice();
			} else if (MInOut.Table_ID == discountBonus.getTable_ID() || MInOutLine.Table_ID == discountBonus.getTable_ID()) {
				availableBudget = getAllowedInOut();
			}
		} else if (BUDGETTYPE_SalesBudget.equals(getBudgetType())) {
			StringBuilder sb = null;
			if(discountBonus.getTable_ID() == MOrder.Table_ID)
			{
				sb = new StringBuilder("SELECT C_BPartner_ID FROM AD_User")
				.append(" WHERE AD_User_ID = (SELECT SalesRep_ID FROM C_Order WHERE ")
				.append(" C_Order_ID = ?)");
			}
			else if(discountBonus.getTable_ID() == MInvoice.Table_ID)
			{
				sb = new StringBuilder("SELECT C_BPartner_ID FROM AD_User")
				.append(" WHERE AD_User_ID = (SELECT SalesRep_ID FROM C_Invoice WHERE ")
				.append(" C_Invoice_ID = ?)");
			}
			else if(discountBonus.getTable_ID() == MOrderLine.Table_ID)
			{
				sb = new StringBuilder("SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID ")
						.append("= (SELECT SalesRep_ID FROM C_Order WHERE C_Order_ID = (")
						.append("SELECT C_Order_ID FROM C_OrderLine")
						.append(" WHERE C_OrderLine_ID = ?))");
			}
			else if(discountBonus.getTable_ID() == MInvoiceLine.Table_ID)
			{
				sb = new StringBuilder("SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID ")
				.append("= (SELECT SalesRep_ID FROM C_Invoice WHERE C_Invoice_ID = (")
				.append("SELECT C_Invoice_ID FROM C_InvoiceLine")
				.append(" WHERE C_InvoiceLine_ID = ?))");
			}
			
			int C_BPartner_ID = DB.getSQLValue(
					get_TrxName(), sb.toString(), discountBonus.getRecord_ID());
			
			MUNSSalesBudget[] budgets = getSalesBudgetLines(true);
			for (int i=0; i<budgets.length; i++) {
				if (budgets[i].getC_BPartner_ID() == C_BPartner_ID || isIncludingSubOrdinate() && budgets[i].isParentOf(C_BPartner_ID)) {
					if (MOrder.Table_ID == discountBonus.getTable_ID() || MOrderLine.Table_ID == discountBonus.getTable_ID()) {
						availableBudget = budgets[i].getAllowedOrder();
					} else if (MInvoice.Table_ID == discountBonus.getTable_ID() || MInvoiceLine.Table_ID == discountBonus.getTable_ID()) {
						availableBudget = budgets[i].getAllowedInvoiced();
					} else if (MInOut.Table_ID == discountBonus.getTable_ID() || MInOutLine.Table_ID == discountBonus.getTable_ID()) {
						availableBudget = budgets[i].getAllowedInOut();
					}
					break;
				}
			}
		} else if (BUDGETTYPE_OrganizationBudget.equals(getBudgetType())){
			MUNSSalesBudget[] budgets = getSalesBudgetLines(false);
			for (int i=0; i<budgets.length; i++) {
				if (budgets[i].getAD_Org_ID() == discountBonus.getOrgTrx_ID()) {
					if (MOrder.Table_ID == discountBonus.getTable_ID() || MOrderLine.Table_ID == discountBonus.getTable_ID()) {
						availableBudget = budgets[i].getAllowedOrder();
					} else if (MInvoice.Table_ID == discountBonus.getTable_ID() || MInvoiceLine.Table_ID == discountBonus.getTable_ID()) {
						availableBudget = budgets[i].getAllowedInvoiced();
					} else if (MInOut.Table_ID == discountBonus.getTable_ID() || MInOutLine.Table_ID == discountBonus.getTable_ID()) {
						availableBudget = budgets[i].getAllowedInOut();
					}
					break;
				}
			}
		} else {

			StringBuilder sb = null;
			if(discountBonus.getTable_ID() == MOrder.Table_ID)
			{
				sb = new StringBuilder("SELECT C_BPartner_ID FROM ")
				.append(MOrder.Table_Name).append(" WHERE ").append(MOrder.Table_Name)
				.append("_ID").append("=?");
			}
			else if(discountBonus.getTable_ID() == MInvoice.Table_ID)
			{
				sb = new StringBuilder("SELECT C_BPartner_ID FROM ")
				.append(MInvoice.Table_Name).append(" WHERE ").append(MInvoice.Table_Name)
				.append("_ID").append("=?");
			}
			else if(discountBonus.getTable_ID() == MOrderLine.Table_ID)
			{
				sb = new StringBuilder("SELECT C_BPartner_ID FROM C_Order").append(" WHERE ")
						.append("C_Order_ID = (").append("SELECT C_Order_ID FROM C_OrderLine")
						.append(" WHERE C_OrderLine_ID = ?)");
			}
			else if(discountBonus.getTable_ID() == MInvoiceLine.Table_ID)
			{
				sb = new StringBuilder("SELECT C_BPartner_ID FROM C_Invoice").append(" WHERE ")
						.append("C_Invoice_ID = (").append("SELECT C_Invoice_ID FROM C_InvoiceLine")
						.append(" WHERE C_InvoiceLine_ID = ?)");
			}
			
			int C_BPartner_ID = DB.getSQLValue(
					get_TrxName(), sb.toString(), discountBonus.getRecord_ID());
			
			MUNSSalesBudget[] budgets = getSalesBudgetLines(false);
			for(int i=0; i<budgets.length; i++)
			{
				boolean match = false;
				
				if(getBudgetType().equals(BUDGETTYPE_CustomerBudget))
					match = budgets[i].getC_BPartner_ID() == C_BPartner_ID;
				else if(getBudgetType().equals(BUDGETTYPE_CustomerGradeBudget))
					match = budgets[i].isInMyOutletGrade(C_BPartner_ID);
				else if(getBudgetType().equals(BUDGETTYPE_CustomerGroupBudget))
					match = budgets[i].isInMyBPGroup(C_BPartner_ID);
				
				if(match) {
					if (MOrder.Table_ID == discountBonus.getTable_ID() || MOrderLine.Table_ID == discountBonus.getTable_ID()) {
						availableBudget = budgets[i].getAllowedOrder();
					} else if (MInvoice.Table_ID == discountBonus.getTable_ID() || MInvoiceLine.Table_ID == discountBonus.getTable_ID()) {
						availableBudget = budgets[i].getAllowedInvoiced();
					} else if (MInOut.Table_ID == discountBonus.getTable_ID() || MInOutLine.Table_ID == discountBonus.getTable_ID()) {
						availableBudget = budgets[i].getAllowedInOut();
					}
					break;
				}
			}
		}
		
		boolean result = availableBudget.compareTo(comparator) >= 0;
		return result;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean IsInMyBPGroup(int C_BPartner_ID)
	{
		if(getC_BP_Group_ID() <= 0)
			return false;
		else if(C_BPartner_ID <= 0)
			return false;
		
		MBPartner bp = MBPartner.get(getCtx(), C_BPartner_ID);
		return bp.getC_BP_Group_ID() == getC_BP_Group_ID();
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean isInMyCustomerType(int C_BPartner_ID)
	{
		if(getUNS_Outlet_Grade_ID() <= 0)
			return false;
		else if(C_BPartner_ID <= 0)
			return false;
		
		MBPartner bp = MBPartner.get(getCtx(), C_BPartner_ID);
		return bp.getUNS_Outlet_Grade_ID() == getUNS_Outlet_Grade_ID();
	}
//	
//	/**
//	 * 
//	 * @param M_Product_ID
//	 * @return
//	 */
//	public boolean isInMyProductCategory(int M_Product_ID)
//	{
//		if(getM_Product_Category_ID() <= 0 )
//			return false;
//		else if(M_Product_ID <= 0)
//			return false;
//		
//		MProduct product = new MProduct(getCtx(), M_Product_ID, get_TrxName());
//		boolean ok = product.getM_Product_Category_ID() 
//				== getM_Product_Category_ID();
//		if (ok && getC_UOM_ID() > 0)
//		{
//			ok = product.isMyUOM(getC_UOM_ID());
//		}
//		
//		return ok;
//	}
	
	/**
	public void checkDuplicate()
	{
		MDiscountSchema[] allCompleteSchema = MDiscountSchema.getAllCompletedOfOrg(
				getCtx(), getAD_Org_ID(), get_TrxName());
		
		StringBuilder duplicateMsg = new StringBuilder();
		for(MDiscountSchema discountSchema : allCompleteSchema)
		{
			if(getAD_Org_ID() != discountSchema.getAD_Org_ID())
				continue;
			
			duplicateMsg.append(compareWithMe(discountSchema));
		}
		
		String msg = duplicateMsg.toString();
		if(msg != null && !msg.isEmpty())
		{
			throw new AdempiereUserError(msg);
		}
	}
	*/
	
	public String compareWithMe(MDiscountSchema schema)
	{
		StringBuilder duplicateMsg = new StringBuilder();
		duplicateMsg.append(schema.compareWithMyBreaks(this));
		for(MDiscountSchemaBreak dsBreak : schema.getBreaks(true))
		{
			dsBreak.compareWithMe(this);
		}
		return duplicateMsg.toString();
	}
	
	public String compareWithMe(MDiscountSchemaBreak dsBreak)
	{
		StringBuilder duplicateMsg = new StringBuilder();
		int duplicateCount = 0;
		if(get_ID() == dsBreak.get_ID()) {
			return "";
		} else if(!isPartnerConflict(dsBreak)) {
			return "";
		} else if (!isDuplicateBySales(dsBreak.getSalesLevel(), dsBreak.getSalesType())) {
			return "";
		} else if (PRODUCTSELECTION_IncludedAllProduct.equals(dsBreak.getProductSelection())
				|| PRODUCTSELECTION_IncludedAllProduct.equals(getProductSelection())) {
			duplicateCount++;
		}
		if (duplicateCount == 0) {
			List<UNTPair<Integer, Integer>> list = dsBreak.getSelectedProduct(false);
			for (int i=0; i<list.size(); i++) {
				if (isInMySelectionProducts((int)list.get(i).getX()))
					duplicateCount++;
			}
		}
		
		if (duplicateCount == 0)
			return "";
	
		if(getDiscountType().equals(dsBreak.getDiscountType()))
		{
			duplicateMsg.append("Duplicate breaks in Discount Schema ").append(getParent().getDocumentNo())
			.append(" on line break ").append(getSeqNo()).append(" & Discount Schema ")
			.append(dsBreak.getParent().getDocumentNo()).append(" on line break")
			.append(dsBreak.getSeqNo()).append("\n");
		}
		
		else if((getDiscountType().equals(MDiscountSchemaBreak.DISCOUNTTYPE_FlatProductBonuses)
				||getDiscountType().equals(MDiscountSchemaBreak.DISCOUNTTYPE_PercentProductBonuses))
				&& getDiscountType().equals(dsBreak.getDiscountType()))
				
		{
			duplicateMsg.append("Duplicate breaks in Discount Schema ").append(getParent().getDocumentNo())
			.append("on line break ").append(getSeqNo()).append(" & Discount Schema ")
			.append(dsBreak.getParent().getDocumentNo()).append(" on line break")
			.append(dsBreak.getSeqNo()).append("\n");
		}
		return duplicateMsg.toString();
	}
	
	
	public void checkSequence()
	{
		MDiscountSchemaBreak[] breaks = getParent().getBreaks(true);
		for(MDiscountSchemaBreak dsBreak : breaks)
		{
			if(get_ID() == dsBreak.get_ID())
				continue;
			
			if(getSeqNo() != dsBreak.getSeqNo())
				continue;
			
			throw new AdempiereUserError("Duplicate number of sequence " + getSeqNo());
		}
	}
	
	public void updateReserveQtyVal(BigDecimal qtyVal, int C_BPartner_ID)
	{
		if(getBudgetType().equals(BUDGETTYPE_NonBudgeted))
			return;
		if(C_BPartner_ID > 0)
		{
			int retVal = updateReserveSalesBudget(C_BPartner_ID, qtyVal);
			if(retVal > 0)
				return;
		}
		
		BigDecimal tmpReserve = getQtyReserved();
		tmpReserve = tmpReserve.add(qtyVal);
		setQtyReserved(tmpReserve);
		saveEx();
	}
	
	public void updateInvoicedQtyVal(BigDecimal qtyVal, int C_BPartner_ID)
	{
		if(getBudgetType().equals(BUDGETTYPE_NonBudgeted))
			return;
		
		if(C_BPartner_ID > 0)
		{
			int retVal = updateInvoicedSalesBudget(C_BPartner_ID, qtyVal);
			if(retVal > 0)
				return;
		}
		
		BigDecimal tmpReserve = getQtyInvoiced();
		tmpReserve = tmpReserve.add(qtyVal);
		setQtyInvoiced(tmpReserve);
		saveEx();
	}
	
	public void updateShipReceiptQtyVal(BigDecimal qtyVal, int C_BPartner_ID)
	{
		if(getBudgetType().equals(BUDGETTYPE_NonBudgeted))
			return;
		
		if(C_BPartner_ID > 0)
		{
			int retVal = updateShipReceiptSalesBudget(C_BPartner_ID, qtyVal);
			if(retVal > 0)
				return;
		}
		
		BigDecimal tmpReserve = getMovementQty();
		tmpReserve = tmpReserve.add(qtyVal);
		setMovementQty(tmpReserve);
		saveEx();
	}
	
	public int updateReserveSalesBudget(int C_BPartner_ID, BigDecimal qtyVal)
	{
		MUNSSalesBudget[] salesBudgets = getSalesBudgetLines(true);
		for(int i=0; i<salesBudgets.length; i++)
		{
			MUNSSalesBudget salesBudget = salesBudgets[i];
			if(getBudgetType().equals(BUDGETTYPE_CustomerBudget)
					|| getBudgetType().equals(BUDGETTYPE_SalesBudget))
			{
				if(C_BPartner_ID != salesBudget.getC_BPartner_ID())
				continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGradeBudget))
			{
				String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getUNS_Outlet_Grade_ID())
					continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGroupBudget))
			{
				String sql = "SELECT C_BP_Group_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getC_BP_Group_ID())
					continue;
			}
			else if (getBudgetType().equals("OB") && C_BPartner_ID != salesBudget.getAD_Org_ID())
				continue;
			
			BigDecimal reservedQty = salesBudget.getQtyReserved().add(qtyVal);
			salesBudget.setQtyReserved(reservedQty);
			salesBudget.saveEx();
			return salesBudgets.length;
		}
		
		if(!isIncludingSubOrdinate())
			return salesBudgets.length;
		
		for(MUNSSalesBudget salesBudget : salesBudgets)
		{
			if(!salesBudget.isParentOf(C_BPartner_ID))
				continue;
			
			BigDecimal reservedQty = salesBudget.getQtyReserved().add(qtyVal);
			salesBudget.setQtyReserved(reservedQty);
			salesBudget.saveEx();
			return salesBudgets.length;
		}
		
		return salesBudgets.length;
	}
	
	public int updateInvoicedSalesBudget(int C_BPartner_ID, BigDecimal qtyVal)
	{
		MUNSSalesBudget[] salesBudgets = getSalesBudgetLines(true);
		for(int i=0; i<salesBudgets.length; i++)
		{
			MUNSSalesBudget salesBudget = salesBudgets[i];
			if(getBudgetType().equals(BUDGETTYPE_CustomerBudget)
					|| getBudgetType().equals(BUDGETTYPE_SalesBudget))
			{
				if(C_BPartner_ID != salesBudget.getC_BPartner_ID())
				continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGradeBudget))
			{
				String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getUNS_Outlet_Grade_ID())
					continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGroupBudget))
			{
				String sql = "SELECT C_BP_Group_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getC_BP_Group_ID())
					continue;
			}
			
			BigDecimal reservedQty = salesBudget.getQtyInvoiced().add(qtyVal);
			salesBudget.setQtyInvoiced(reservedQty);
			salesBudget.saveEx();
			return salesBudgets.length;
		}
		
		if(!isIncludingSubOrdinate())
			return salesBudgets.length;
		
		for(MUNSSalesBudget salesBudget : salesBudgets)
		{
			if(!salesBudget.isParentOf(C_BPartner_ID))
				continue;
			
			BigDecimal reservedQty = salesBudget.getQtyInvoiced().add(qtyVal);
			salesBudget.setQtyInvoiced(reservedQty);
			salesBudget.saveEx();
			return salesBudgets.length;
		}
		
		return salesBudgets.length;
	}
	
	public int updateShipReceiptSalesBudget(int C_BPartner_ID, BigDecimal qtyVal)
	{
		MUNSSalesBudget[] salesBudgets = getSalesBudgetLines(true);
		for(int i=0; i<salesBudgets.length; i++)
		{
			MUNSSalesBudget salesBudget = salesBudgets[i];
			if(getBudgetType().equals(BUDGETTYPE_CustomerBudget)
					|| getBudgetType().equals(BUDGETTYPE_SalesBudget))
			{
				if(C_BPartner_ID != salesBudget.getC_BPartner_ID())
				continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGradeBudget))
			{
				String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getUNS_Outlet_Grade_ID())
					continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGroupBudget))
			{
				String sql = "SELECT C_BP_Group_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getC_BP_Group_ID())
					continue;
			}
			
			BigDecimal reservedQty = salesBudget.getMovementQty().add(qtyVal);
			salesBudget.setMovementQty(reservedQty);
			salesBudget.saveEx();
			return salesBudgets.length;
		}
		
		if(!isIncludingSubOrdinate())
			return salesBudgets.length;
		
		for(MUNSSalesBudget salesBudget : salesBudgets)
		{
			if(!salesBudget.isParentOf(C_BPartner_ID))
				continue;
			
			BigDecimal reservedQty = salesBudget.getMovementQty().add(qtyVal);
			salesBudget.setMovementQty(reservedQty);
			salesBudget.saveEx();
			return salesBudgets.length;
		}
		
		return salesBudgets.length;
	}
	
	private MUNSDiscountTrx[] m_transaction = null;
	public MUNSDiscountTrx[] getMyTransactions(boolean requery)
	{
		if(null != m_transaction && !requery)
		{
			set_TrxName(m_transaction, get_TrxName());
			return m_transaction;
		}
		
		List<MUNSDiscountTrx> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSDiscountTrx.Table_Name
				, "(" + MUNSDiscountTrx.COLUMNNAME_M_DiscountSchemaBreak_ID + "=?"
				+ " OR UNS_DSBreakLine_ID IN (SELECT UNS_DSBreakLine_ID FROM UNS_DSBreakLine "
						+ " WHERE M_DiscountSchemaBreak_ID = ?))"
				, get_TrxName()).setParameters(get_ID(), get_ID()).list();
		
		m_transaction = new MUNSDiscountTrx[list.size()];
		list.toArray(m_transaction);
		
		return m_transaction;
	}

	public boolean isDiscountMustSameWithSchema()
	{
		if(null == getRequirementType())
			return false;
		
		boolean isTrue = getRequirementType().equals(REQUIREMENTTYPE_MustSameWithThisSchema);
		return isTrue;
	}
	
	public MDiscountSchemaBreak(MDiscountSchema parent)
	{
		super(parent.getCtx(), 0, parent.get_TrxName());
		m_parent = parent;
		setAD_Org_ID(parent.getAD_Org_ID());
		setM_DiscountSchema_ID(parent.get_ID());
	}
	
	/**
	 * 
	 * @param prevBreak
	 * @return
	 */
	public String copyValuesFrom(MDiscountSchemaBreak prevBreak)
	{
		setAD_Org_ID(prevBreak.getAD_Org_ID());
		PO.copyValues(prevBreak, this);
		saveEx();
		
		for(MUNSDSBreakLine breakLine : prevBreak.getBreakLines(true))
		{
			MUNSDSBreakLine line = new MUNSDSBreakLine(this);
			line.copyValuesOf(breakLine);
			line.saveEx();
			
			MUNSSalesBudget[] sbs = breakLine.getSalesBudgetLines();
			for(MUNSSalesBudget sb : sbs)
			{
				MUNSSalesBudget newSB = new MUNSSalesBudget(line, sb.getQtyAllocated());
				newSB.setAD_Org_ID(sb.getAD_Org_ID());
				PO.copyValues(sb, newSB);
				newSB.saveEx();
			}
			for(MUNSDiscountBonus bonus : MUNSDiscountBonus.getBonus(breakLine))
			{
				MUNSDiscountBonus newBonus = new MUNSDiscountBonus(line);
				newBonus.copyValuesOf(bonus);
				newBonus.saveEx();
			}
		}
		
		for(MUNSDiscountBonus bonus : MUNSDiscountBonus.getBonus(prevBreak))
		{
			MUNSDiscountBonus newBonus = new MUNSDiscountBonus(this);
			newBonus.copyValuesOf(bonus);
			newBonus.saveEx();
		}
		
		for(MUNSSalesBudget budget : prevBreak.getSalesBudgetLines(false))
		{
			MUNSSalesBudget newBudget = new MUNSSalesBudget(this, budget.getQtyAllocated());
			PO.copyValues(budget, newBudget);
			newBudget.saveEx();
		}
		
		for(MUNSDiscountProduct product : prevBreak.getSelectionProducts(false))
		{
			MUNSDiscountProduct newProduct = new MUNSDiscountProduct(this);
			PO.copyValues(product, newProduct);
			newProduct.saveEx();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param discountTrx
	 */
	public void checkMaxValue(MUNSDiscountTrx discountTrx)
	{
		StringBuilder errorMsg = new StringBuilder();
		BigDecimal pengali = discountTrx.getQtyValDiscounted().divide(getBreakValue(), 0, RoundingMode.DOWN);
		if(pengali.compareTo(getNofMultiples()) == 1)
			pengali= getNofMultiples();
		
		if(discountTrx.getFlatValueDiscount().signum() > 0 && pengali.signum() > 0
				&& discountTrx.getFlatValueDiscount().compareTo(getBreakDiscount().multiply(pengali)) > 0)
		{
			errorMsg.append("Flat value discount [").append(discountTrx.getFlatValueDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ")
			.append("can't bigger than schema flat value [").append(getBreakDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]");
		}
		else if(discountTrx.getQtyBonuses().signum() > 0 && pengali.signum() > 0
				&& discountTrx.getQtyBonuses().compareTo(
						getDiscountType().equals(MDiscountSchemaBreak.DISCOUNTTYPE_PercentProductBonuses) ? 
								getBreakDiscount().multiply(getBreakValue())
						.divide(Env.ONEHUNDRED).multiply(pengali) : getBreakDiscount().multiply(pengali)) 
						> 0 && discountTrx.getUNS_DiscountBonus_ID() <= 0)
		{
			errorMsg.append("Discount bonus Qty [").append(discountTrx.getQtyBonuses()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than Qty of schema [").append(getBreakDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		else if(discountTrx.getFirstDiscount().signum() > 0 && pengali.signum() > 0 
				&& discountTrx.getFirstDiscount().compareTo(getBreakDiscount()) > 0)
		{
			errorMsg.append("Discount 1st % [").append(discountTrx.getFirstDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than % value of schema [").append(getBreakDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		else if(discountTrx.getSecondDiscount().signum() > 0 && pengali.signum() > 0
				&& discountTrx.getSecondDiscount().compareTo(getBreakDiscount()) > 0)
		{
			errorMsg.append("Discount 2nd % [").append(discountTrx.getSecondDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than % value of schema [").append(getSecondDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		else if(discountTrx.getThirdDiscount().signum() > 0 && pengali.signum() > 0 
				&& discountTrx.getThirdDiscount().compareTo(getBreakDiscount()) > 0)
		{
			errorMsg.append("Discount 3rd % [").append(discountTrx.getThirdDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than % value of schema [").append(getThirdDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		else if(discountTrx.getFourthDiscount().signum() > 0 && pengali.signum() > 0
				&& discountTrx.getFourthDiscount().compareTo(getBreakDiscount()) > 0)
		{
			errorMsg.append("Discount 4th % [").append(discountTrx.getFourthDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than % value of schema [").append(getFourthDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		else if(discountTrx.getFifthDiscount().signum() > 0 && pengali.signum() > 0
				&& discountTrx.getFifthDiscount().compareTo(getBreakDiscount()) > 0)
		{
			errorMsg.append("Discount 5th % [").append(discountTrx.getFifthDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than % value of schema [").append(getFifthDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		
		String msg = errorMsg.toString();
		
		if(null != msg && !msg.isEmpty())
		{
			throw new AdempiereException(msg);
		}
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSDiscountCustomer[] getListCustomer(boolean requery)
	{
		if(null != m_listCustomer && !requery)
		{
			set_TrxName(m_listCustomer, get_TrxName());
			return m_listCustomer;
		}
		
		List<MUNSDiscountCustomer> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSDiscountCustomer.Table_Name
				, MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID + "=?"
				, get_TrxName()).setParameters(get_ID()).list();
		
		m_listCustomer = new MUNSDiscountCustomer[list.size()];
		list.toArray(m_listCustomer);
		
		return m_listCustomer;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @param isSOTrx
	 * @return
	 */
	public boolean isValidCustomerDiscount(MBPartner partner)
	{
		if(getSelectionType() == null)
		{
			return true;
		}
		if(getSelectionType().equals(SELECTIONTYPE_IncludedAll))
		{
			return true;
		}
		else if(getSelectionType().equals(SELECTIONTYPE_Vendor))
		{
			return getC_BPartner_ID() == partner.get_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_VendorGroup))
		{
			return getC_BP_Group_ID() ==  getC_BP_Group_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return partner.get_ID() == getC_BPartner_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return partner.getUNS_Outlet_Grade_ID() == getUNS_Outlet_Grade_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return partner.getC_BP_Group_ID() == getC_BP_Group_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return isSelected(partner.get_ID(), MUNSDiscountCustomer.COLUMNNAME_C_BPartner_ID);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return isSelected(partner.getUNS_Outlet_Grade_ID(), MUNSDiscountCustomer.COLUMNNAME_UNS_Outlet_Grade_ID);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return isSelected(partner.getC_BP_Group_ID(), MUNSDiscountCustomer.COLUMNNAME_C_BP_Group_ID);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludedOnceCustomerGrade))
		{
			return partner.getUNS_Outlet_Grade_ID() != getUNS_Outlet_Grade_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			return partner.getC_BPartner_ID() != getC_BPartner_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			return partner.getC_BP_Group_ID() != getC_BP_Group_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return !isSelected(partner.get_ID(), MUNSDiscountCustomer.COLUMNNAME_C_BPartner_ID);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return !isSelected(partner.getUNS_Outlet_Grade_ID(), MUNSDiscountCustomer.COLUMNNAME_UNS_Outlet_Grade_ID);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return !isSelected(partner.getC_BP_Group_ID(), MUNSDiscountCustomer.COLUMNNAME_C_BP_Group_ID);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 
	 * @param m_Order
	 * @return
	 */
	public boolean isValidCustomerDiscount(I_DiscountModel model)
	{
		if(!getBudgetType().equals(BUDGETTYPE_NonBudgeted) && getAllowedOrder().signum() <= 0)
		{
			return false;
		}
		else if(!isValidCustomerDiscount(model.getBPartner()))
		{
			return false;
		}
		else if(getParent().isSOTrx())
		{
			if(getSalesLevel() == null && getSalesType() == null)
			{
				return true;
			}
			else if(null == model.getSalesRep())
			{
				return false;
			}
			else if(getSalesType() != null && !getSalesType().equals(model.getSalesRep().getSalesType()))
			{
				return false;
			}
			else if(getSalesLevel() != null && !getSalesLevel().equals(model.getSalesRep().getSalesLevel()))
			{
				if(!isIncludingSubOrdinate())
				{
					return false;
				}
				if(!model.getSalesRep().myParentLevelIs(getSalesLevel()))
				{
					return false;
				}
				else
				{
					return true;
				}
			}
			else
			{
				return true;
			}
		} // END (schema.isSOTrx() && (dsBreak.getSalesLevel() != null || dsBreak.getSalesType() != null))
		
		int founded = 0;
		for(I_DiscountModelLine line : model.getLines(false))
		{
			if(line.isProductBonuses())
				continue;
			else if(getParent().isCumulativeDocument())
			{
				//no Action
			}
			else if(getM_Product_ID() > 0 && getM_Product_ID() != line.getProduct().get_ID())
			{
				continue;
			}
			else if(getM_Product_Category_ID() > 0 && getM_Product_Category_ID() != line.getProduct().getM_Product_Category_ID())
			{
				continue;
			}
			founded++;
		}
		
		if(founded == 0)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param parameter
	 * @param columnName
	 * @return
	 */
	public boolean isSelected(int parameter, String columnName)
	{
		String sql = "SELECT 1 FROM UNS_Discount_Customer WHERE "
				.concat(columnName).concat("=? AND ")
				.concat(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID)
				.concat("=?");
		int val = DB.getSQLValue(get_TrxName(), sql, parameter, get_ID());
		return val > 0;
	}
	
	/**
	 * Get Included Business Partner Group
	 * @param requery
	 * @return
	 */
	public MBPGroup[] getIncludedGroups(boolean requery)
	{
		if(null == getSelectionType())
		{
			MDiscountSchema parent = new MDiscountSchema(getCtx(), getM_DiscountSchema_ID(), get_TrxName());
			return parent.getIncludedGroups(requery);
		}
		if(null != m_includedGroups && !requery)
		{
			set_TrxName(m_includedGroups, get_TrxName());
			return m_includedGroups;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BP_Group_ID)
			.append(" NOT IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") ");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BP_Group_ID)
			.append(" NOT IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append(get_ID()).append(") ");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BP_Group_ID)
			.append(" IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") ");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BP_Group_ID)
			.append(" IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append(get_ID()).append(") ");
		}
		
		String whereClause = WhereClause.toString();
		if(Util.isEmpty(whereClause, true))
			whereClause = null;
		
		List<MBPGroup> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MBPGroup.Table_Name
				, whereClause, get_TrxName()).list();
		
		m_includedGroups = new MBPGroup[list.size()];
		list.toArray(m_includedGroups);
		
		return m_includedGroups;
	}
	
	/**
	 * Get Included Customer Grade
	 * @param requery
	 * @return
	 */
	public X_UNS_Outlet_Grade[] getIncludedGrades(boolean requery)
	{
		if(null == getSelectionType())
		{
			MDiscountSchema parent = new MDiscountSchema(getCtx(), getM_DiscountSchema_ID(), get_TrxName());
			return parent.getIncludedGrades(requery);
		}
		if(null != m_includedGrades && !requery)
		{
			set_TrxName(m_includedGrades, get_TrxName());
			return m_includedGrades;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		if(getSelectionType().equals(SELECTIONTYPE_ExcludedOnceCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" NOT IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") ");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" NOT IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append(get_ID()).append(")");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") ");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append(get_ID()).append(") ");
		}
		
		String whereClause = WhereClause.toString();
		if(Util.isEmpty(whereClause, true))
			whereClause = null;
		
		List<X_UNS_Outlet_Grade> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, X_UNS_Outlet_Grade.Table_Name
				, whereClause, get_TrxName()).list();
		
		m_includedGrades = new X_UNS_Outlet_Grade[list.size()];
		list.toArray(m_includedGrades);
		
		return m_includedGrades;
	}
	
	/**
	 * Get Included Business Partner.
	 * @param requery
	 * @return
	 */
	public MBPartner[] getIncludedPartners(boolean requery)
	{
		if(null == getSelectionType())
		{
			MDiscountSchema parent = new MDiscountSchema(getCtx(), getM_DiscountSchema_ID(), get_TrxName());
			return parent.getIncludedPartners(requery);
		}
		if(null != m_includedPartners && !requery)
		{
			set_TrxName(m_includedPartners, get_TrxName());
			return m_includedPartners;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		
		if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" NOT IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") AND ")
			.append(MBPartner.COLUMNNAME_IsCustomer).append("='Y'");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" NOT IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append(get_ID()).append(") AND ").append(MBPartner.COLUMNNAME_IsCustomer)
			.append("='Y'");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedAll))
		{
			WhereClause.append(MBPartner.COLUMNNAME_IsCustomer).append("='Y'");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") AND ")
			.append(MBPartner.COLUMNNAME_IsCustomer).append("='Y'");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append(get_ID()).append(") AND ").append(MBPartner.COLUMNNAME_IsCustomer)
			.append("='Y'");
		}
		else 
		{
			return m_includedPartners;
		}
		
		String whereClause = WhereClause.toString();
		if(Util.isEmpty(whereClause, true))
			whereClause = null;
		
		List<MBPartner> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MBPartner.Table_Name
				, whereClause, get_TrxName()).list();
		
		m_includedPartners = new MBPartner[list.size()];
		list.toArray(m_includedPartners);
		
		return m_includedPartners;
	}
	
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MBPartner[] getIncludedSaleses(boolean requery)
	{
		if(null == getSalesLevel() && null == getSalesType())
		{
			MDiscountSchema parent = new MDiscountSchema(getCtx(), getM_DiscountSchema_ID(), get_TrxName());
			return parent.getIncludedSaleses(requery);
		}
		
		if(null != m_includedPartners && !requery)
		{
			set_TrxName(m_includedPartners, get_TrxName());
			return m_includedPartners;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		if(getBudgetType().equals(BUDGETTYPE_SalesBudget))
		{
			if(getSalesType() != null)
			{
				WhereClause.append(MBPartner.COLUMNNAME_SalesType)
				.append("='").append(getSalesType()).append("'");
			}
			else if(getSalesLevel() != null)
			{
				if(getSalesType() != null)
					WhereClause.append(" AND ");
				
				WhereClause.append(MBPartner.COLUMNNAME_SalesLevel)
				.append("='").append(getSalesLevel()).append("'");
			}
			
			if(getSalesLevel() != null || getSalesType() != null)
				WhereClause.append( " AND ");
			
			WhereClause.append(MBPartner.COLUMNNAME_IsSalesRep)
			.append("='Y'");;	
		}
		else 
		{
			return m_includedPartners;
		}
		
		String whereClause = WhereClause.toString();
		if(Util.isEmpty(whereClause, true))
			whereClause = null;
		
		List<MBPartner> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MBPartner.Table_Name
				, whereClause, get_TrxName()).list();
		
		m_includedPartners = new MBPartner[list.size()];
		list.toArray(m_includedPartners);
		
		return m_includedPartners;
	}
	
	/**
	 * 
	 * @param salesLevel
	 * @param SalesType
	 * @return
	 */
	public boolean isDuplicateBySales(String salesLevel, String SalesType)
	{
		return someSalesRepOnSalesTypeIsInMySalesLevel(SalesType) 
				|| salesTypeIsMySalesType(SalesType)
				|| salesLevelIsMySalesLevel(salesLevel);
	}
	
	/**
	 * 
	 * @param salesType
	 * @return
	 */
	public boolean someSalesRepOnSalesTypeIsInMySalesLevel(String salesType)
	{
		if(salesType == null)
			return true;
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE SalesType = ? AND SalesLevel = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, salesType, getSalesLevel());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param SalesType
	 * @return
	 */
	public boolean salesTypeIsMySalesType(String SalesType)
	{
		if(null == SalesType)
			return true;
		else if(null == getSalesType())
			return true;
		
		return getSalesType().equals(SalesType);
	}
	
	/**
	 * 
	 * @param SalesLevel
	 * @return
	 */
	public boolean salesLevelIsMySalesLevel(String SalesLevel)
	{
		if(null == SalesLevel)
			return true;
		else if(null == getSalesLevel())
			return true;
		
		return getSalesLevel().equals(SalesLevel);
	}
	
	
	private void validateSelection()
	{
		if(getParent().getSelectionType() != null)
		{
			setSelectionType(null);
		}
		else if( getParent().getSelectionType() == null && getSelectionType() == null)
		{
			throw new AdempiereUserError("Field Mandatory  Selection Type");
		}
		if(getSelectionType() == null)
		{
			setC_BP_Group_ID(-1);
			setC_BPartner_ID(-1);
			setUNS_Outlet_Grade_ID(-1);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				|| getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			setC_BPartner_ID(-1);
			setC_BP_Group_ID(-1);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomer)
				|| getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomer)
				|| getSelectionType().equals(SELECTIONTYPE_Vendor))
		{
			setC_BP_Group_ID(-1);
			setUNS_Outlet_Grade_ID(-1);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				|| getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				|| getSelectionType().equals(SELECTIONTYPE_VendorGroup))
		{
			setC_BPartner_ID(-1);
			setUNS_Outlet_Grade_ID(-1);
		}
		else
		{
			setC_BPartner_ID(-1);
			setC_BP_Group_ID(-1);
			setUNS_Outlet_Grade_ID(-1);
		}
	}
	
	
	/**
	 * Get Product Selection
	 * @return
	 */
//	public MUNSDiscountProduct[] getSelectionProducts()
//	{
//		return getSelectionProducts(false);
//	}
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSDiscountProduct[] getSelectionProducts(boolean requery)
	{
		if(null != m_products && !requery)
		{
			set_TrxName(m_products, get_TrxName());
			return m_products;
		}
		
		List<MUNSDiscountProduct> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID
				, MUNSDiscountProduct.Table_Name, Table_Name + "_ID = ?"
				, get_TrxName())
				.setParameters(get_ID()).list();
		
		m_products = new MUNSDiscountProduct[list.size()];
		list.toArray(m_products);
		
		return m_products;
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	public boolean isInMySelectionProducts(int M_Product_ID)
	{
		if(getProductSelection().equals(PRODUCTSELECTION_IncludedAllProduct))
			return true;
		UNTPair<Integer, Integer> p = getSelectedProductUOM(M_Product_ID);
		return p != null;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!getParent().isComplete())
		{
			getParent().checkDuplicate();
		}
		return super.afterSave(newRecord, success);
	}
	
	
	
	public boolean isPartnerConflict(MDiscountSchema schema)
	{
		if(getSelectionType() == null || schema.getSelectionType() == null)
			return true;
		
		String thisSelection = getSelectionType();
		String schemaSelection = schema.getSelectionType();
		
		if (thisSelection.equals(SELECTIONTYPE_IncludedAll) || schemaSelection.equals(SELECTIONTYPE_IncludedAll))
		{
			return true;
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return !selectedGradeIsMyGrade(getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMyGrade(schema.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return someBPartnerOnGroupIsNotInMyGrade(schema.getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMyGrade(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someGradeOnSelectedGradeIsNotInMyGrade(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsNotInMyGrade(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade))
		{
			return someUnselectedGradeIsNotInMyGrade(schema.getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			return someUnselectedBPartnerIsNotInMyGrade(schema.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			
			return schema.someBPartnerNotOnGradeIsNotInMyGroup(getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{	
			return someBPartnerNotOnSelectedBPartnerIsNotInMyGrade(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{		
			return someGradeOnUnselectedGradeIsNotMyGrade(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return someBPartnerNotOnSelectedGroupIsNotInMyGrade(schema);
		}
		//end Execlude One Grade
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schema.someUnselectedBPartnerIsInMyGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsMyBPartner(schema.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.someUnselectedBPartnerIsInMyGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schema.someUnselectedBPartnerIsInMySelectedBPartner(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.someUnselectedBPartnerIsInMySelectedGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someUnselectedBPartnerIsInMySelectedGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			return someUnselectedBPartnerIsNotInMyBPartner(schema.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			return schema.someUnselectedBPartnerIsNotInMyGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return schema.someUnselectedBPartnerIsNotInMySelectedBPartner(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			 return schema.someUnselectedBPartnerIsNotInMySelectedGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schema.someUnselectedBPartnerIsNotInMySelectedGroup(getC_BPartner_ID());
		}
		//END Execlude Once Customer
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return someBPartnerOnGradeIsNotInMyGroup(schema.getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMyGroup(schema.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.someUnselectedGroupIsInMyGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMyGroup(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsNotInMyGroup(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someUnselectedGroupIsInMySelectedGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			return schema.someUnselectedGroupIsNotInMyGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return someBPartnerNotOnSelectedBPartnerIsNotInMyGroup(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return someBPartnerNotOnSelectedGradeIsNotInMyGroup(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schema.someUnselectedGroupIsNotInMySelectedGroup(getC_BP_Group_ID());
		}
		//End Execlude Once Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schema.someBPartnerNotOnSelectedBPartnerIsInMyGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedBPartner(schema.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.someBPartnerNotOnSelectedBPartnerIsInMyGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schema.someBPartnerNotOnSelectedBPartnerIsInMySelectedBPartner(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.someBPartnerNotOnSelectedBpartnerIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someBPartnerNotOnSelectedBPartnerIsInMySelectedGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return someBPartnerNotOnSelectedBPartnerIsNotInMySelectedBPartner(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return schema.someBPartnerNotOnSelectedBpartnerIsNotInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schema.someBPartnerNotOnSelectedBPartnerIsNotInMySelectedGroup(this);
		}
		//End Execlude Selected Customer
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schema.someGradeOnSelectedGradeIsInMyGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedGrade(schema.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.someBPartnerNotOnSelectedGradeIsInMyGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMySelectedGrade(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.someGradeNotOnSelectedGradeIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someBPartnerNotOnSelectedGradeIsInMySelectedGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return someGradeNotOnSelectedGradeIsNotInMySelectedGrade(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schema.someBPartnerNotOnselectedGradeIsNotInMySelectedGroup(this);
		}
		//End Exclude Selected Customer Grade
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return someBPartnerOnGradeIsNotInMySelectedGroup(schema.getUNS_Outlet_Grade_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedGroup(schema.getC_BPartner_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return !selectedGroupIsInMySelectedGroup(schema.getC_BP_Group_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMySelectedGroup(schema);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnselectedGradeIsNotInMySelectedGroup(schema);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someGroupNotOnSelectedGroupIsInMySelectedGroup(this);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return someGroupNotOnSelectedGroupIsNotInMySelectedGroup(schema);
		}
		//End Execlude Selected Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return selectedBPartnerIsMyBPartner(schema.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schema.selectedBPartnerIsInMyGrade(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.selectedBPartnerIsInMyGroup(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schema.selectedBPartnerIsInMySelectedBPartner(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.selectedBPartnerIsInMySelectedGrade(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.selectedBPartnerIsInMySelectedGroup(getC_BPartner_ID());
		}
		//End Included Once Customer
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return selectedGradeIsMyGrade(schema.getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.someBPartnerOnGradeIsInMyGroup(getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMyGrade(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.selectedGradeIsInMySelectedGrade(getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsInMyGrade(schema);
		}
		//End Included Once Customer Grade
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return selectedGroupIsMyGroup(schema.getC_BP_Group_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMyGroup(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsInMyGroup(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.selectedGroupIsInMySelectedGroup(getC_BP_Group_ID());
		}
		//End Include Once Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMySelectedBPartner(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.someBPartnerOnSelectedBPartnerIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someBPartnerOnSelectedBPartnerIsInMySelectedGroup(this);
		}
		//End Include Selected Customer
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsInMySelectedGrade(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someBPartnerOnselectedGradeIsInMySelectedGroup(this);
		}
		//END Include Selected Customer Grade
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsInMySelectedGroup(schema);
		}
		//End Include Selected Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_Vendor))
		{
			if(schemaSelection.equals(SELECTIONTYPE_Vendor)
					&& getC_BPartner_ID() != schema.getC_BPartner_ID())
			{
				return false;
			}
			else if(schemaSelection.equals(SELECTIONTYPE_VendorGroup)
				&& !schema.selectedBPartnerIsInMyGroup(getC_BP_Group_ID()))
			{
				return false;
			}
		}
		else if(getSelectionType().equals(SELECTIONTYPE_VendorGroup))
		{
			if(schemaSelection.equals(SELECTIONTYPE_Vendor)
					&& !selectedBPartnerIsInMyGroup(schema.getC_BPartner_ID()))
			{
				return false;
			}
			else if(schemaSelection.equals(SELECTIONTYPE_VendorGroup)
					&& getC_BP_Group_ID() != schema.getC_BP_Group_ID())
			{
				return false;
			}
		}
		else
		{
			return schema.isPartnerConflict(this);
		}
		
		return true;
	}
	
	
	
	public boolean isPartnerConflict(MDiscountSchemaBreak schemaBreak)
	{
		if(getSelectionType() == null && schemaBreak.getSelectionType() == null)
		{
			return true;
		}
		else if (getSelectionType() != null && schemaBreak.getSelectionType() == null
				&& schemaBreak.getParent().getSelectionType() != null)
		{
			return schemaBreak.getParent().isPartnerConflict(this);
		}
		else if(getSelectionType() == null && schemaBreak.getSelectionType()!= null
				&& getParent().getSelectionType() != null)
		{
			return getParent().isPartnerConflict(schemaBreak);
		}
		
		String thisSelection = getSelectionType();
		String schemaSelection = schemaBreak.getSelectionType();
		
		if (thisSelection.equals(SELECTIONTYPE_IncludedAll) || schemaSelection.equals(SELECTIONTYPE_IncludedAll))
		{
			return true;
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return !selectedGradeIsMyGrade(getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMyGrade(schemaBreak.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return someBPartnerOnGroupIsNotInMyGrade(schemaBreak.getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMyGrade(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someGradeOnSelectedGradeIsNotInMyGrade(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsNotInMyGrade(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade))
		{
			return someUnselectedGradeIsNotInMyGrade(schemaBreak.getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			return someUnselectedBPartnerIsNotInMyGrade(schemaBreak.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			
			return schemaBreak.someBPartnerNotOnGradeIsNotInMyGroup(getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{	
			return someBPartnerNotOnSelectedBPartnerIsNotInMyGrade(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{		
			return someGradeOnUnselectedGradeIsNotMyGrade(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return someBPartnerNotOnSelectedGroupIsNotInMyGrade(schemaBreak);
		}
		//end Execlude One Grade
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schemaBreak.someUnselectedBPartnerIsInMyGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsMyBPartner(schemaBreak.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.someUnselectedBPartnerIsInMyGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schemaBreak.someUnselectedBPartnerIsInMySelectedBPartner(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.someUnselectedBPartnerIsInMySelectedGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someUnselectedBPartnerIsInMySelectedGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			return someUnselectedBPartnerIsNotInMyBPartner(schemaBreak.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			return schemaBreak.someUnselectedBPartnerIsNotInMyGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return schemaBreak.someUnselectedBPartnerIsNotInMySelectedBPartner(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			 return schemaBreak.someUnselectedBPartnerIsNotInMySelectedGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schemaBreak.someUnselectedBPartnerIsNotInMySelectedGroup(getC_BPartner_ID());
		}
		//END Execlude Once Customer
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return someBPartnerOnGradeIsNotInMyGroup(schemaBreak.getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMyGroup(schemaBreak.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.someUnselectedGroupIsInMyGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMyGroup(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsNotInMyGroup(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someUnselectedGroupIsInMySelectedGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			return schemaBreak.someUnselectedGroupIsNotInMyGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return someBPartnerNotOnSelectedBPartnerIsNotInMyGroup(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return someBPartnerNotOnSelectedGradeIsNotInMyGroup(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schemaBreak.someUnselectedGroupIsNotInMySelectedGroup(getC_BP_Group_ID());
		}
		//End Execlude Once Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schemaBreak.someBPartnerNotOnSelectedBPartnerIsInMyGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedBPartner(schemaBreak.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnSelectedBPartnerIsInMyGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schemaBreak.someBPartnerNotOnSelectedBPartnerIsInMySelectedBPartner(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.someBPartnerNotOnSelectedBpartnerIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnSelectedBPartnerIsInMySelectedGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return someBPartnerNotOnSelectedBPartnerIsNotInMySelectedBPartner(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return schemaBreak.someBPartnerNotOnSelectedBpartnerIsNotInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnSelectedBPartnerIsNotInMySelectedGroup(this);
		}
		//End Execlude Selected Customer
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schemaBreak.someGradeOnSelectedGradeIsInMyGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedGrade(schemaBreak.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnSelectedGradeIsInMyGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMySelectedGrade(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.someGradeNotOnSelectedGradeIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnSelectedGradeIsInMySelectedGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return someGradeNotOnSelectedGradeIsNotInMySelectedGrade(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnselectedGradeIsNotInMySelectedGroup(this);
		}
		//End Exclude Selected Customer Grade
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return someBPartnerOnGradeIsNotInMySelectedGroup(schemaBreak.getUNS_Outlet_Grade_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedGroup(schemaBreak.getC_BPartner_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return !selectedGroupIsInMySelectedGroup(schemaBreak.getC_BP_Group_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMySelectedGroup(schemaBreak);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnselectedGradeIsNotInMySelectedGroup(schemaBreak);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someGroupNotOnSelectedGroupIsInMySelectedGroup(this);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return someGroupNotOnSelectedGroupIsNotInMySelectedGroup(schemaBreak);
		}
		//End Execlude Selected Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return selectedBPartnerIsMyBPartner(schemaBreak.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schemaBreak.selectedBPartnerIsInMyGrade(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.selectedBPartnerIsInMyGroup(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schemaBreak.selectedBPartnerIsInMySelectedBPartner(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.selectedBPartnerIsInMySelectedGrade(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.selectedBPartnerIsInMySelectedGroup(getC_BPartner_ID());
		}
		//End Included Once Customer
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return selectedGradeIsMyGrade(schemaBreak.getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.someBPartnerOnGradeIsInMyGroup(getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMyGrade(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.selectedGradeIsInMySelectedGrade(getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsInMyGrade(schemaBreak);
		}
		//End Included Once Customer Grade
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return selectedGroupIsMyGroup(schemaBreak.getC_BP_Group_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMyGroup(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsInMyGroup(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.selectedGroupIsInMySelectedGroup(getC_BP_Group_ID());
		}
		//End Include Once Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMySelectedBPartner(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.someBPartnerOnSelectedBPartnerIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerOnSelectedBPartnerIsInMySelectedGroup(this);
		}
		//End Include Selected Customer
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsInMySelectedGrade(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerOnselectedGradeIsInMySelectedGroup(this);
		}
		//END Include Selected Customer Grade
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsInMySelectedGroup(schemaBreak);
		}
		//End Include Selected Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_Vendor))
		{
			if(schemaSelection.equals(SELECTIONTYPE_Vendor)
					&& getC_BPartner_ID() != schemaBreak.getC_BPartner_ID())
			{
				return false;
			}
			else if(schemaSelection.equals(SELECTIONTYPE_VendorGroup)
				&& !schemaBreak.selectedBPartnerIsInMyGroup(getC_BP_Group_ID()))
			{
				return false;
			}
		}
		else if(getSelectionType().equals(SELECTIONTYPE_VendorGroup))
		{
			if(schemaSelection.equals(SELECTIONTYPE_Vendor)
					&& !selectedBPartnerIsInMyGroup(schemaBreak.getC_BPartner_ID()))
			{
				return false;
			}
			else if(schemaSelection.equals(SELECTIONTYPE_VendorGroup)
					&& getC_BP_Group_ID() != schemaBreak.getC_BP_Group_ID())
			{
				return false;
			}
		}
		else
		{
			return schemaBreak.isPartnerConflict(this);
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param C_Bpartner_ID
	 * @return
	 */
	public boolean selectedBPartnerIsMyBPartner(int C_Bpartner_ID)
	{
		return getC_BPartner_ID() == C_Bpartner_ID;
	}
	
	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean selectedGroupIsMyGroup(int C_BP_Group_ID)
	{
		return getC_BP_Group_ID() == C_BP_Group_ID;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean selectedGradeIsMyGrade(int UNS_Outlet_Grade_ID)
	{
		return getUNS_Outlet_Grade_ID() == UNS_Outlet_Grade_ID;
	}

	/**
	 * Chekc selected business partner is in this Grade
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean selectedBPartnerIsInMyGrade(int C_BPartner_ID)
	{
		String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
		return retVal == getUNS_Outlet_Grade_ID();
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean selectedBPartnerIsInMySelectedGrade(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID IN ("
				+ " SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ get_TableName() + "_ID = ?) AND C_BPartner_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), C_BPartner_ID);
		return retVal > 0;
	}
	
	/**
	 * Ceck selected buseness partner is in this group.
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean selectedBPartnerIsInMyGroup(int C_BPartner_ID)
	{
		String sql = "SELECT C_BP_Group_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
		return retVal == getC_BP_Group_ID();
	}
	
	public boolean selectedBPartnerIsInMySelectedGroup(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID IN ("
				+ "SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE " + get_TableName()
				+ "_ID = ?) AND C_BPartner_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), C_BPartner_ID);
		return retVal > 0;
	}
	
	public boolean selectedBPartnerIsInMySelectedBPartner(int C_BPartner_ID)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BPartner_ID = ?"
				+ " AND C_BPartner_ID IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
		//unselected 
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsInMyGrade(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsInMySelectedGrade(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ?"
				+ " AND UNS_Outlet_Grade_ID IN (SELECT UNS_Outlet_Grade_ID FROM "
				+ " UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsInMyGroup(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsInMySelectedGroup(int C_BPartner_ID)
	{
		String sql = "SELEC COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ?"
				+ " AND UNS_BP_Group_ID IN (SELECT C_BP_Group_ID FROM "
				+ " UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsInMySelectedBPartner(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? "
				+ " AND C_BPartner_ID IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql,C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean selectedGradeIsInMySelectedGrade(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Discount_Customer "
				+ " WHERE " + get_TableName() + "_ID = ? AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), UNS_Outlet_Grade_ID);
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean selectedGroupIsInMySelectedGroup(int C_BP_Group_ID)
	{
		String sql = "SELECT C_BP_Group_ID FROM UNS_Discount_Customer "
				+ " WHERE " + get_TableName() + "_ID = ? AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), C_BP_Group_ID);
		return retVal > 0;
	}

	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean someUnselectedGroupIsInMySelectedGroup(int C_BP_Group_ID)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_Group WHERE C_BP_Group_ID "
				+ " IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer "
				+ " WHERE " + get_TableName() + "_ID = ?) AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), C_BP_Group_ID);
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean someUnselectedGroupIsNotInMySelectedGroup(int C_BP_Group_ID)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_Group WHERE C_BP_Group_ID "
				+ " NOT IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer "
				+ " WHERE " + get_TableName() + "_ID = ?) AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), C_BP_Group_ID);
		return retVal > 0;
	}
	
	/**
	 * Check some business partner on a selected grade is in this business partner group
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean someBPartnerOnGradeIsInMyGroup(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID = ? AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getC_BP_Group_ID(), UNS_Outlet_Grade_ID);
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedGradeIsInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID "
				+ " NOT IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) " + " AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedGradeIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID "
				+ " NOT IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) " + " AND C_BP_Group_ID IN (SELECT "
				+ " C_BP_Group_ID FROM UNS_DIscountCustomer WHERE " + Table_Name + "_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedGradeIsNotInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID "
				+ " NOT IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) " + " AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGradeOnSelectedGradeIsInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade WHERE UNS_Outlet_Grade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGradeNotOnSelectedGradeIsNotInMySelectedGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade WHERE UNS_Outlet_Grade_ID NOT IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID NOT IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ Table_Name + "_ID =?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGradeNotOnSelectedGradeIsInMySelectedGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade WHERE UNS_Outlet_Grade_ID NOT IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ Table_Name + "_ID =?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}

	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsNotInMySelectedGrade(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ?"
				+ " AND UNS_Outlet_Grade_ID NOT IN (SELECT UNS_Outlet_Grade_ID FROM "
				+ " UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsNotInMySelectedGroup(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ?"
				+ " AND C_BP_Group_ID NOT IN (SELECT C_BP_Group_ID FROM "
				+ " UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_Bpartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsNotInMyGrade(int C_Bpartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_Bpartner_ID, getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsNotInMyGroup(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, getC_BP_Group_ID());
		return retVal > 0;
	}	
	

	
	/**
	 * Check some business partner on a selected group is not in this grade.
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean someBPartnerOnGroupIsNotInMyGrade(int C_BP_Group_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID = ? AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BP_Group_ID, getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean someBPartnerOnGradeIsNotInMyGroup(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID <> ? AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getC_BP_Group_ID(), UNS_Outlet_Grade_ID);
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean someBPartnerNotOnGradeIsNotInMyGroup(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID <> ? AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getC_BP_Group_ID(), UNS_Outlet_Grade_ID);
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean someBPartnerOnGradeIsNotInMySelectedGroup(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID "
				+ " NOT IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ Table_Name + "_ID = ?) AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), UNS_Outlet_Grade_ID);
		return retVal > 0;
	}	
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsNotInMySelectedBPartner(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? "
				+ " AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql,C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * check some business partner on selected business partner of discount schema is not in this grade
	 * @param M_DiscountSchema_ID
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsNotInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	public boolean someBPartnerOnSelectedBPartnerIsNotInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedGroupIsInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID IN "
				+ " (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedGroupIsNotInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID IN "
				+ " (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedGroupIsNotInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID NOT IN "
				+ " (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGradeOnSelectedGradeIsNotInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade WHERE UNS_Outlet_Grade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGradeOnUnselectedGradeIsNotMyGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade WHERE UNS_Outlet_Grade_ID NOT IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}	
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean someUnselectedGradeIsNotInMyGrade(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade "
				+ " WHERE UNS_Outlet_Grade_ID <> ? AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, UNS_Outlet_Grade_ID, getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	

	public boolean someUnselectedBPartnerIsNotInMyBPartner(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? AND C_BPartner_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getC_BPartner_ID(), C_BPartner_ID);
		return retVal > 0;
	}

	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsInMySelectedBPartner(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM UNS_Discount_Customer WHERE " 
				+ Table_Name + "_ID = ? AND  C_BPartner_ID IN(SELECT C_BPartner_ID FROM "
				+ " UNS_Discount_Customer WHERE " + po.get_TableName() + "_ID =? )";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsNotInMySelectedGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID "
				+ " NOT IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE " 
				+ Table_Name + "_ID = ?) AND  C_BPartner_ID IN (SELECT C_BPartner_ID FROM "
				+ " UNS_Discount_Customer WHERE " + po.get_TableName() + "_ID =? )";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsInMySelectedGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID "
				+ " IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE " 
				+ Table_Name + "_ID = ?) AND  C_BPartner_ID IN (SELECT C_BPartner_ID FROM "
				+ " UNS_Discount_Customer WHERE " + po.get_TableName() + "_ID =? )";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsNotInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID "
				+ " NOT IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE " 
				+ Table_Name + "_ID = ?) AND  C_BPartner_ID IN (SELECT C_BPartner_ID FROM "
				+ " UNS_Discount_Customer WHERE " + po.get_TableName() + "_ID =? )";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID "
				+ " IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE " 
				+ Table_Name + "_ID = ?) AND  C_BPartner_ID IN (SELECT C_BPartner_ID FROM "
				+ " UNS_Discount_Customer WHERE " + po.get_TableName() + "_ID =? )";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean someUnselectedGroupIsInMyGroup(int C_BP_Group_ID)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_GROUP WHERE "
				+ " C_BP_Group_ID <> ? AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BP_Group_ID, getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean someUnselectedGroupIsNotInMyGroup(int C_BP_Group_ID)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_GROUP WHERE "
				+ " C_BP_Group_ID <> ? AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BP_Group_ID, getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsNotInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID "
				+ " NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsNotInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID "
				+ " NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	public boolean someBPartnerNotOnSelectedBPartnerIsInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID "
				+ " NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID "
				+ " NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedGradeIsNotInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_OutletGrade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Outlet_Grade WHERE " + po.get_TableName()
				+ "_ID = ?) AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	public boolean someBPartnerOnSelectedGradeIsInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_OutletGrade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Outlet_Grade WHERE " + po.get_TableName()
				+ "_ID = ?) AND C_BP_Group_ID == ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsInMySelectedBPartner(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BPartner_ID IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBpartnerIsInMySelectedGrade(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE UNS_Outlet_Grade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBpartnerIsNotInMySelectedGrade(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE UNS_Outlet_Grade_ID NOT IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BP_Group_ID IN "
				+ " (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsNotInMySelectedGroup(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BP_Group_ID NOT IN "
				+ " (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsNotInMySelectedBPartner(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BPartner_ID NOT IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnselectedGradeIsNotInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID"
				+ " NOT IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID NOT IN (SELECT C_BP_Group_ID "
				+ " FROM UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	public boolean someBPartnerOnSelectedGradeIsInMySelectedGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Discount_Customer WHERE"
				+ Table_Name + "_ID = ? AND UNS_Outlet_Grade_ID IN ("
				+ " SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	public boolean someBPartnerOnSelectedGroupIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM UNS_Discount_Customer WHERE"
				+ Table_Name + "_ID = ? AND C_BP_Group_ID IN ("
				+ " SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnselectedGradeIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID"
				+ " IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID IN (SELECT C_BP_Group_ID "
				+ " FROM UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnselectedGradeIsNotInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID"
				+ " IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID NOT IN (SELECT C_BP_Group_ID "
				+ " FROM UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}

	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGroupNotOnSelectedGroupIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_Group WHERE C_BP_Group_ID "
				+ " NOT IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID IN (SELECT C_BP_Group_ID "
				+ " FROM UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGroupNotOnSelectedGroupIsNotInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_Group WHERE C_BP_Group_ID "
				+ " NOT IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID NOT IN (SELECT C_BP_Group_ID "
				+ " FROM UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	private List<UNTPair<Integer,Integer>> m_productSelections = null;
	
	public UNTPair<Integer, Integer> getSelectedProductUOM (int M_Product_ID) {
		getSelectedProduct(false);
		for (int i=0; i<m_productSelections.size(); i++) {
			if (m_productSelections.get(i).getX() == M_Product_ID)
				return m_productSelections.get(i);
		}
		return null;
	}
	
	public List<UNTPair<Integer, Integer>> getSelectedProduct (boolean requery) {
		if (m_productSelections != null && !requery)
			return m_productSelections;
		m_productSelections = new ArrayList<>();
		
		if (PRODUCTSELECTION_IncludedOnceProduct.equals(getProductSelection())) {
			m_productSelections.add(new UNTPair<Integer, Integer>(getM_Product_ID(), getC_UOM_ID()));
		} else if (PRODUCTSELECTION_IncludedOnceProductCategory.equals(getProductSelection())) {
			m_productSelections.addAll(getProductOnCategory(getM_Product_Category_ID(), getC_UOM_ID()));
		}
		else {
			MUNSDiscountProduct[] products = getSelectionProducts(requery);
			int uomID = getC_UOM_ID();
			for (int i=0; i<products.length; i++) {
				if (uomID <= 0)
					uomID = products[i].get_ValueAsInt("C_UOM_ID");
				if (PRODUCTSELECTION_IncludedSelectedProduct.equals(getProductSelection())
						|| PRODUCTSELECTION_IncludedSelectedProductByVendor.equals(getProductSelection())) {
					m_productSelections.add(new UNTPair<Integer, Integer>(products[i].getM_Product_ID(), uomID));
				} else if (PRODUCTSELECTION_IncludedSelectedProductCategory.equals(getProductSelection())) {
					m_productSelections.addAll(getProductOnCategory(products[i].getM_Product_Category_ID(), uomID));
				}
			}
		}
		return m_productSelections;
	}
	
	private List<UNTPair<Integer, Integer>> getProductOnCategory (int M_Product_Category_ID, int C_UOM_ID) {
		List<UNTPair<Integer, Integer>> list = new ArrayList<>();
		String sql = "SELECT M_Product_ID, C_UOM_ID FROM M_Product WHERE M_Product_Category_ID = ?";
		if (C_UOM_ID > 0) {
			sql += " AND (UOMConversionL1_ID = ? OR UOMConversionL2_ID = ? OR "
					+ "UOMConversionL3_ID = ? OR UOMConversionL4_ID = ?)";
		}
				
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(sql, get_TrxName());
			int idx = 0;
			st.setInt(++idx, M_Product_Category_ID);
			if (C_UOM_ID > 0) {
				st.setInt(++idx, C_UOM_ID);
				st.setInt(++idx, C_UOM_ID);
				st.setInt(++idx, C_UOM_ID);
				st.setInt(++idx, C_UOM_ID);	
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DB.close(rs, st);
		}
		return list;
	}
}