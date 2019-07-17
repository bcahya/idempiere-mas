/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.model.X_UNS_Outlet_Grade;
import com.uns.base.model.Query;

import com.unicore.base.model.MInvoiceLine;
import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author user
 * 
 */
public class MUNSDSBreakLine extends X_UNS_DSBreakLine
{
	private MDiscountSchemaBreak m_parent = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1416513690973293133L;
	private MDiscountSchemaBreak m_Parent;
	private MUNSSalesBudget[] m_salesBudgets = null;
	private MBPartner[] m_includedPartners = null;
	private X_UNS_Outlet_Grade[] m_includedGrades = null;
	private MBPGroup[] m_includedGroups = null;

	/**
	 * @param ctx
	 * @param UNS_DSBreakLine_ID
	 * @param trxName
	 */
	public MUNSDSBreakLine(Properties ctx, int UNS_DSBreakLine_ID, String trxName)
	{
		super(ctx, UNS_DSBreakLine_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDSBreakLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MDiscountSchemaBreak getParent(boolean reload)
	{
		if (reload || m_Parent == null)
		{
			m_Parent = new MDiscountSchemaBreak(getCtx(), getM_DiscountSchemaBreak_ID(), get_TrxName());
		}
		return m_Parent;
	}

	public BigDecimal getCalculateQty(BigDecimal breakValue, BigDecimal qty, boolean isPercent)
	{
		BigDecimal retValue = Env.ZERO;
		if (isPercent)
		{
			retValue = breakValue.multiply(qty).divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_EVEN);
		}
		else 
		{
			retValue = qty;
		}
		return retValue;
	}
	
	private void deleteBonuses()
	{
		try {
			for (MUNSDiscountBonus discountBonus: MUNSDiscountBonus.getBonus(this)){
				discountBonus.deleteEx(true);
			}
		} catch (Exception ex) {
			throw new AdempiereException("Failed to delete bonus lines " + ex.getMessage());
		}
	}
	
	/**
	 * Before Delete
	 * 
	 * @return true
	 */
	protected boolean beforeDelete()
	{				
		deleteBonuses();
		return super.beforeDelete();
	} // beforeDelete
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getSeqNo() <=0)
		{
			String sql = "SELECT COALESCE(MAX(seqNo), 0) + 10 FROM UNS_DSBreakLine WHERE M_DiscountSchemaBreak_ID =?";
			int value = DB.getSQLValue(get_TrxName(), sql, getM_DiscountSchemaBreak_ID());
			setSeqNo(value);
		}
		checkValidTarget();
		if(getParent().getDiscountType().equals(MDiscountSchemaBreak.DISCOUNTTYPE_MultipleValueDiscount))
		{
			if(getNofMultiples().signum() <= 0)
				throw new AdempiereUserError("NO of multiple is mandatory and must greater than zero.");
		}
		return super.beforeSave(newRecord);
	}
	
	private void checkValidTarget()
	{
		MDiscountSchemaBreak parent = getParent();
		
		if(null == parent)
			throw new AdempiereException("No parent set for this break line" +
					", please contact administrator to fix it.");
		
		if(getBreakValue().compareTo(getBreakValueTo()) > 0)
		{
			throw new AdempiereUserError("Break value from must be lower than break value to");
		}
		
		
		MUNSDSBreakLine[] lines = parent.getBreakLines(true);
		BigDecimal maxTarget = getBreakValueTo();
			
		
		for(MUNSDSBreakLine line : lines)
		{
			if(line.get_ID() == get_ID())
				continue;
			
			if(getBreakValue().compareTo(line.getBreakValue())>= 0
					&& getBreakValue().compareTo(line.getBreakValueTo()) <= 0)
			{
				throw new AdempiereUserError("Invalid break value from");
			}
			else if(getBreakValueTo().compareTo(line.getBreakValueTo()) <= 0
					&& getBreakValueTo().compareTo(line.getBreakValue()) >= 0)
			{
				throw new AdempiereUserError("Invalid break value to");
			}
			
			BigDecimal currentTarget = line.getBreakValueTo();			
			if(currentTarget.compareTo(maxTarget) > 0)
			{
				maxTarget = currentTarget;
			}
		}
	}
	
	public MDiscountSchemaBreak getParent()
	{
		if(m_parent != null)
			return m_parent;
		m_parent = getM_DiscountSchemaBreak_ID() <= 0 ? null 
						: new MDiscountSchemaBreak(getCtx(), getM_DiscountSchemaBreak_ID(), get_TrxName());
		return m_parent;
	}
	
	public MUNSDSBreakLine(MDiscountSchemaBreak parent)
	{
		super(parent.getCtx(), 0, parent.get_TrxName());
		m_parent = parent;
		setM_DiscountSchemaBreak_ID(parent.get_ID());
		setAD_Org_ID(parent.getAD_Org_ID());
	}
	
	public void copyValuesOf(MUNSDSBreakLine prev)
	{
		setAD_Org_ID(prev.getAD_Org_ID());
		setBreakDiscount(prev.getBreakDiscount());
		setBreakValue(prev.getBreakValue());
		setBreakValueTo(prev.getBreakValueTo());
		setFifthDiscount(prev.getFifthDiscount());
		setFourthDiscount(prev.getFourthDiscount());
		setNofMultiples(prev.getNofMultiples());
		setName(prev.getName());
		setSecondDiscount(prev.getSecondDiscount());
		setThirdDiscount(prev.getThirdDiscount());
	}
	
	
	public void checkMaxValue(MUNSDiscountTrx discountTrx)
	{
		StringBuilder errorMsg = new StringBuilder();
		BigDecimal pengali = Env.ONE;
		if(getParent().getDiscountType().equals(MDiscountSchemaBreak.DISCOUNTTYPE_MultipleValueDiscount))
		{
			pengali = discountTrx.getQtyValDiscounted().divide(
					getNofMultiples(), 2, RoundingMode.HALF_EVEN);
		}
		
		if(discountTrx.getFlatValueDiscount().compareTo(getBreakDiscount().multiply(pengali)) > 0)
		{
			errorMsg.append("Flat value discount [").append(discountTrx.getFlatValueDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ")
			.append("can't bigger than schema flat value [").append(getBreakDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]");
		}
		else if(discountTrx.getQtyBonuses().signum() > 0 && discountTrx.getUNS_DiscountBonus_ID() <= 0)
		{
//			BigDecimal percent = getBreakDiscount().multiply(discountTrx.getQtyValDiscounted())
//					.divide(Env.ONEHUNDRED);
//			if(discountTrx.getQtyBonuses().compareTo(percent) > 0 )
//			{
//				errorMsg.append("Discount bonus Qty [").append(discountTrx.getQtyBonuses()
//						.setScale(2, RoundingMode.HALF_DOWN)).append("]")
//				.append("can't bigger than Qty of schema [").append(getBreakDiscount()
//						.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
//			}
		}
		else if(discountTrx.getFirstDiscount().compareTo(getBreakDiscount()) > 0)
		{
			errorMsg.append("Discount 1st % [").append(discountTrx.getFirstDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than % value of schema [").append(getBreakDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		else if(discountTrx.getSecondDiscount().compareTo(getSecondDiscount()) > 0)
		{
			errorMsg.append("Discount 2nd % [").append(discountTrx.getSecondDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than % value of schema [").append(getSecondDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		else if(discountTrx.getThirdDiscount().compareTo(getThirdDiscount()) > 0)
		{
			errorMsg.append("Discount 3rd % [").append(discountTrx.getThirdDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than % value of schema [").append(getThirdDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		else if(discountTrx.getFourthDiscount().compareTo(getFourthDiscount()) > 0)
		{
			errorMsg.append("Discount 4th % [").append(discountTrx.getFourthDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than % value of schema [").append(getFourthDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		else if(discountTrx.getFifthDiscount().compareTo(getFifthDiscount()) > 0)
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
	
	public String getBudgetType () {
		return getParent().getBudgetType();
	}
	
	public String getBudgetCalculation () {
		return getParent().getBudgetCalculation();
	}
	
	public boolean isMix () {
		return getParent().isMix();
	}
	
	public boolean isMixRequired () {
		return getParent().isMixRequired();
	}
	
	public boolean isIncludingSubOrdinate () {
		return getParent().isIncludingSubOrdinate();
	}
	
	public boolean isBudgetAvailable (UNSDiscountBonus discountBonus) {
		if (!getParent().isStrataBudget())
			return getParent().isBudgetAvailable(discountBonus);
		BigDecimal comparator = Env.ZERO;
		if (getBudgetType() == null || MDiscountSchemaBreak.BUDGETTYPE_NonBudgeted.equals(getBudgetType()))
			return true;
		if (MDiscountSchemaBreak.BUDGETCALCULATION_Quantity.equals(getBudgetCalculation())) {
			if (isMix() || isMixRequired()) {
				comparator = discountBonus.getConversionMixQty();
			} else
				comparator = discountBonus.getConversionQty();
		} else if (MDiscountSchemaBreak.BUDGETCALCULATION_Amount.equals(getBudgetCalculation())) {
			if (isMix() || isMixRequired()) {
				comparator = discountBonus.getLinenetAmtMix();
			} else
				comparator = discountBonus.getLineNetAmount();
		} else {
			comparator = discountBonus.getDiscountBonus();
		}

		BigDecimal availableBudget = Env.ZERO;
		if (MDiscountSchemaBreak.BUDGETTYPE_GeneralBudget.equals(getBudgetType())) {
			if (MOrder.Table_ID == discountBonus.getTable_ID() || MOrderLine.Table_ID == discountBonus.getTable_ID()) {
				availableBudget = getAllowedOrder();
			} else if (MInvoice.Table_ID == discountBonus.getTable_ID() || MInvoiceLine.Table_ID == discountBonus.getTable_ID()) {
				availableBudget = getAllowedInvoice();
			} else if (MInOut.Table_ID == discountBonus.getTable_ID() || MInOutLine.Table_ID == discountBonus.getTable_ID()) {
				availableBudget = getAllowedInOut();
			}
		} else if (MDiscountSchemaBreak.BUDGETTYPE_SalesBudget.equals(getBudgetType())) {
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
		} else if (MDiscountSchemaBreak.BUDGETTYPE_OrganizationBudget.equals(getBudgetType())){
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
				
				if(getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget))
					match = budgets[i].getC_BPartner_ID() == C_BPartner_ID;
				else if(getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget))
					match = budgets[i].isInMyOutletGrade(C_BPartner_ID);
				else if(getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
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
	
	public BigDecimal getAllowedOrder()
	{
		BigDecimal val = getQtyAllocated().subtract(getQtyReserved());
		return val;
	}
	
	public BigDecimal getAllowedInvoice()
	{
		BigDecimal val = getQtyAllocated().subtract(getQtyInvoiced());
		return val;
	}
	
	public BigDecimal getAllowedInOut()
	{
		BigDecimal val = getQtyAllocated().subtract(getMovementQty());
		return val;
	}
	
	
	public MUNSSalesBudget[] getSalesBudgetLines()
	{
		return getSalesBudgetLines(false);
	}
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSSalesBudget[] getSalesBudgetLines(boolean requery)
	{
		if(null != m_salesBudgets && !requery)
		{
			set_TrxName(m_salesBudgets, get_TrxName());
			return m_salesBudgets;
		}
		
		List<MUNSSalesBudget> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSSalesBudget.Table_Name, Table_Name + "_ID = ?"
				, get_TrxName()).setParameters(get_ID()).list();
		
		m_salesBudgets = new MUNSSalesBudget[list.size()];
		list.toArray(m_salesBudgets);
		
		return m_salesBudgets;
	}
	
	/**
	 * 
	 * @param qtyVal
	 * @param C_BPartner_ID
	 */
	public void updateReserveQtyVal(BigDecimal qtyVal, int C_BPartner_ID)
	{
		if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_NonBudgeted))
			return;
		if(C_BPartner_ID > 0)
		{
			int retVal = 1;
			if (getParent().isStrataBudget())
				retVal = updateReserveSalesBudget(C_BPartner_ID, qtyVal);
			if(retVal > 0)
			{
				getParent().updateReserveQtyVal(qtyVal, -1);
				return;
			}
		}
		
		BigDecimal tmpReserve = getQtyReserved();
		tmpReserve = tmpReserve.add(qtyVal);
		setQtyReserved(tmpReserve);
		saveEx();
		getParent().updateReserveQtyVal(qtyVal, -1);
	}
	
	/**
	 * 
	 * @param qtyVal
	 * @param C_BPartner_ID
	 */
	public void updateInvoicedQtyVal(BigDecimal qtyVal, int C_BPartner_ID)
	{
		if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_NonBudgeted))
			return;
		
		if(C_BPartner_ID > 0)
		{
			int retVal = updateInvoicedSalesBudget(C_BPartner_ID, qtyVal);
			if(retVal > 0)
			{
				getParent().updateInvoicedQtyVal(qtyVal, -1);
				return;
			}
		}
		
		BigDecimal tmpInvoiced = getQtyInvoiced();
		tmpInvoiced = tmpInvoiced.add(qtyVal);
		setQtyInvoiced(tmpInvoiced);
		saveEx();
		getParent().updateInvoicedQtyVal(qtyVal, -1);
	}
	
	/**
	 * 
	 * @param qtyVal
	 * @param C_BPartner_ID
	 */
	public void updateShipReceiptQtyVal(BigDecimal qtyVal, int C_BPartner_ID)
	{
		if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_NonBudgeted))
			return;
		
		if(C_BPartner_ID > 0)
		{
			int retVal = updateShipReceiptSalesBudget(C_BPartner_ID, qtyVal);
			if(retVal > 0)
			{
				getParent().updateShipReceiptQtyVal(qtyVal, -1);
				return;
			}
		}
		
		BigDecimal tmpShipReceipt = getMovementQty();
		tmpShipReceipt = tmpShipReceipt.add(qtyVal);
		setMovementQty(tmpShipReceipt);
		saveEx();
		getParent().updateShipReceiptQtyVal(qtyVal, -1);
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @param qtyVal
	 * @return
	 */
	public int updateReserveSalesBudget(int C_BPartner_ID, BigDecimal qtyVal)
	{
		MUNSSalesBudget[] salesBudgets = getSalesBudgetLines(true);
		for(int i=0; i<salesBudgets.length; i++)
		{
			MUNSSalesBudget salesBudget = salesBudgets[i];
			if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					|| getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_SalesBudget))
			{
				if(C_BPartner_ID != salesBudget.getC_BPartner_ID())
				continue;
			}
			else if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget))
			{
				String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getUNS_Outlet_Grade_ID())
					continue;
			}
			else if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
			{
				String sql = "SELECT C_BP_Group_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getC_BP_Group_ID())
					continue;
			}
			else if (getParent().getBudgetType().equals("OB") && salesBudget.getAD_Org_ID() != C_BPartner_ID)
				continue;
			
			BigDecimal reservedQty = salesBudget.getQtyReserved().add(qtyVal);
			salesBudget.setQtyReserved(reservedQty);
			salesBudget.saveEx();
			return salesBudgets.length;
		}
		
		if(!getParent().isIncludingSubOrdinate())
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
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @param qtyVal
	 * @return
	 */
	public int updateInvoicedSalesBudget(int C_BPartner_ID, BigDecimal qtyVal)
	{
		MUNSSalesBudget[] salesBudgets = getSalesBudgetLines(true);
		for(int i=0; i<salesBudgets.length; i++)
		{
			MUNSSalesBudget salesBudget = salesBudgets[i];
			if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					|| getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_SalesBudget))
			{
				if(C_BPartner_ID != salesBudget.getC_BPartner_ID())
				continue;
			}
			else if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget))
			{
				String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getUNS_Outlet_Grade_ID())
					continue;
			}
			else if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
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
		
		if(!getParent().isIncludingSubOrdinate())
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
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @param qtyVal
	 * @return
	 */
	public int updateShipReceiptSalesBudget(int C_BPartner_ID, BigDecimal qtyVal)
	{
		MUNSSalesBudget[] salesBudgets = getSalesBudgetLines(true);
		for(int i=0; i<salesBudgets.length; i++)
		{
			MUNSSalesBudget salesBudget = salesBudgets[i];
			if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					|| getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_SalesBudget))
			{
				if(C_BPartner_ID != salesBudget.getC_BPartner_ID())
				continue;
			}
			else if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget))
			{
				String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getUNS_Outlet_Grade_ID())
					continue;
			}
			else if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
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
		
		if(!getParent().isIncludingSubOrdinate())
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
	
	/**
	 * Get Included Business Partner.
	 * @param requery
	 * @return
	 */
	public MBPartner[] getIncludedPartners(boolean requery)
	{
		if(null == getParent().getSelectionType())
		{
			MDiscountSchema parent = new MDiscountSchema(
					getCtx(), getParent().getM_DiscountSchema_ID(), get_TrxName());
			return parent.getIncludedPartners(requery);
		}
		if(null != m_includedPartners && !requery)
		{
			set_TrxName(m_includedPartners, get_TrxName());
			return m_includedPartners;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		
		if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_ExcludeOnceCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" NOT IN (SELECT ").append(Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") AND ")
			.append(MBPartner.COLUMNNAME_IsCustomer).append("='Y'");
		}
		else if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" NOT IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append(get_ID()).append(") AND ").append(MBPartner.COLUMNNAME_IsCustomer)
			.append("='Y'");
		}
		else if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_IncludedAll))
		{
			WhereClause.append(MBPartner.COLUMNNAME_IsCustomer).append("='Y'");
		}
		else if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_IncludedOnceCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" IN (SELECT ").append(Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") AND ")
			.append(MBPartner.COLUMNNAME_IsCustomer).append("='Y'");
		}
		else if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_IncludedSelectedCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_C_BPartner_ID).append(" FROM ")
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
	public MBPGroup[] getIncludedGroups(boolean requery)
	{
		if(null == getParent().getSelectionType())
		{
			MDiscountSchema parent = new MDiscountSchema(
					getCtx(), getParent().getM_DiscountSchema_ID(), get_TrxName());
			return parent.getIncludedGroups(requery);
		}
		if(null != m_includedGroups && !requery)
		{
			set_TrxName(m_includedGroups, get_TrxName());
			return m_includedGroups;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BP_Group_ID)
			.append(" NOT IN (SELECT ").append(Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") ");
		}
		else if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BP_Group_ID)
			.append(" NOT IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append(get_ID()).append(") ");
		}
		else if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BP_Group_ID)
			.append(" IN (SELECT ").append(Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") ");
		}
		else if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BP_Group_ID)
			.append(" IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_C_BP_Group_ID).append(" FROM ")
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
		if(null == getParent().getSelectionType())
		{
			MDiscountSchema parent = new MDiscountSchema(
					getCtx(), getParent().getM_DiscountSchema_ID(), get_TrxName());
			return parent.getIncludedGrades(requery);
		}
		if(null != m_includedGrades && !requery)
		{
			set_TrxName(m_includedGrades, get_TrxName());
			return m_includedGrades;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_ExcludedOnceCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" NOT IN (SELECT ").append(Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") ");
		}
		else if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" NOT IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append(get_ID()).append(")");
		}
		else if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" IN (SELECT ").append(Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") ");
		}
		else if(getParent().getSelectionType().equals(
				MDiscountSchemaBreak.SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MDiscountSchemaBreak.COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
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
	 * 
	 * @param requery
	 * @return
	 */
	public MBPartner[] getIncludedSaleses(boolean requery)
	{
		if(null == getParent().getSalesLevel() && null == getParent().getSalesType())
		{
			MDiscountSchema parent = new MDiscountSchema(
					getCtx(), getParent().getM_DiscountSchema_ID(), get_TrxName());
			return parent.getIncludedSaleses(requery);
		}
		
		if(null != m_includedPartners && !requery)
		{
			set_TrxName(m_includedPartners, get_TrxName());
			return m_includedPartners;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		if(getParent().getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_SalesBudget))
		{
			if(getParent().getSalesType() != null)
			{
				WhereClause.append(MBPartner.COLUMNNAME_SalesType)
				.append("='").append(getParent().getSalesType()).append("'");
			}
			else if(getParent().getSalesLevel() != null)
			{
				if(getParent().getSalesType() != null)
					WhereClause.append(" AND ");
				
				WhereClause.append(MBPartner.COLUMNNAME_SalesLevel)
				.append("='").append(getParent().getSalesLevel()).append("'");
			}
			
			if(getParent().getSalesLevel() != null || getParent().getSalesType() != null)
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

}
