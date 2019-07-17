/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MTree;
import org.compiere.model.MTree_NodeBP;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.unicore.model.X_UNS_Outlet_Grade;
import com.uns.util.MessageBox;

import com.unicore.model.MDiscountSchema;
import com.unicore.model.MDiscountSchemaBreak;
import com.unicore.model.MUNSDSBreakLine;
import com.unicore.model.MUNSSalesBudget;

/**
 * @author menjangan
 *
 */
public class GenerateSalesBudget extends SvrProcess {

	/**
	 * 
	 */
	public GenerateSalesBudget() {
		super();
	}
	
	private MDiscountSchema m_discountSchema;
	private int m_BPTree_ID = 0;
	

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] pi = getParameter();
		for(ProcessInfoParameter p : pi)
		{
			String pName = p.getParameterName();
			if(null == pName)
				continue;
		}
		m_discountSchema = new MDiscountSchema(getCtx(), getRecord_ID(), get_TrxName());
		m_BPTree_ID = MTree.getDefaultAD_Tree_ID(
				getAD_Client_ID(), MUNSSalesBudget.COLUMNNAME_C_BPartner_ID);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if(m_discountSchema.getDiscountType().equals(MDiscountSchema.DISCOUNTTYPE_Flat))
			calculateFlatSalesBudget();
		else if(m_discountSchema.getDiscountType().equals(MDiscountSchema.DISCOUNTTYPE_Breaks))
			calculateBreakSalesBudget();
		else
			throw new AdempiereException("UNHANDLED DISCOUNT TYPE " + m_discountSchema.getDiscountType());

		return null;
	}
	
	private void calculateSalesBudget(MUNSSalesBudget[] listSalesBudget, double allocatedBudget, int percission, boolean customerBudget)
	{
		if(customerBudget)
		{
			double rate = allocatedBudget / listSalesBudget.length;
			for(int i=0; i<listSalesBudget.length; i++)
			{
				listSalesBudget[i].setQtyAllocated(new BigDecimal(rate).setScale(percission, RoundingMode.HALF_DOWN));
				listSalesBudget[i].saveEx();
			}
			return;
		}
		Hashtable<Integer, SalesBudgetProportion> mapSales = initializeSalesBudgetProportion(listSalesBudget);
	
		for(Integer key : mapSales.keySet()) 
		{	
			SalesBudgetProportion salesBudgetProp = mapSales.get(key);
			
			double budget = (allocatedBudget * salesBudgetProp.getProportion()) / 100;
			double devider = salesBudgetProp.getListOfSalesBudgets().size();
			double proportionBudget = budget / devider;
			BigDecimal value = new BigDecimal(proportionBudget).setScale(percission, RoundingMode.HALF_DOWN);
			
			for(MUNSSalesBudget salesBudget : salesBudgetProp.getListOfSalesBudgets())
			{
				salesBudget.setQtyAllocated(value);
				salesBudget.saveEx();
			}
		}
	}
	
	private void loadCustomerBudget(PO po)
	{
		MBPartner[] partners = null;
		if(po instanceof MDiscountSchema)
		{
			MDiscountSchema discount = (MDiscountSchema) po;
			partners = discount.getIncludedPartners(false);
		}
		else if(po instanceof MDiscountSchemaBreak)
		{
			MDiscountSchemaBreak discount = (MDiscountSchemaBreak) po;
			partners = discount.getIncludedPartners(false);
		}
		else if(po instanceof MUNSDSBreakLine)
		{
			MUNSDSBreakLine discount = (MUNSDSBreakLine) po;
			partners = discount.getIncludedPartners(false);
		}
		
		if(null == partners)
			return;
		
		for(int i=0; i<partners.length; i++)
		{
			MUNSSalesBudget salesBudget = new MUNSSalesBudget(po, Env.ZERO);
			salesBudget.setC_BPartner_ID(partners[i].get_ID());
			salesBudget.saveEx();
		}
	}
	
	private void loadCustomerGradeBudget(PO po)
	{
		X_UNS_Outlet_Grade[] ogs = null;
		if(po instanceof MDiscountSchema)
		{
			MDiscountSchema discount = (MDiscountSchema) po;
			ogs = discount.getIncludedGrades(false);
		}
		else if(po instanceof MDiscountSchemaBreak)
		{
			MDiscountSchemaBreak discount = (MDiscountSchemaBreak) po;
			ogs = discount.getIncludedGrades(false);
		}
		else if(po instanceof MUNSDSBreakLine)
		{
			MUNSDSBreakLine discount = (MUNSDSBreakLine) po;
			ogs = discount.getIncludedGrades(false);
		}
		
		if(null == ogs)
			return;
		
		for(X_UNS_Outlet_Grade og : ogs)
		{
			MUNSSalesBudget salesBudget = new MUNSSalesBudget(po, Env.ZERO);
			salesBudget.setUNS_Outlet_Grade_ID(og.get_ID());
			salesBudget.saveEx();
		}
	}
	
	private void loadCustomerGroupBudget(PO po)
	{
		MBPGroup[] groups =  null;
		if(po instanceof MDiscountSchema)
		{
			MDiscountSchema discount = (MDiscountSchema) po;
			groups = discount.getIncludedGroups(false);
		}
		else if(po instanceof MDiscountSchemaBreak)
		{
			MDiscountSchemaBreak discount = (MDiscountSchemaBreak) po;
			groups = discount.getIncludedGroups(false);
		}
		else if(po instanceof MUNSDSBreakLine)
		{
			MUNSDSBreakLine discount = (MUNSDSBreakLine) po;
			groups = discount.getIncludedGroups(false);
		}
		
		if(null == groups)
			return;
		
		for(MBPGroup group : groups)
		{
			MUNSSalesBudget salesBudget = new MUNSSalesBudget(po, Env.ZERO);
			salesBudget.setC_BP_Group_ID(group.get_ID());
			salesBudget.saveEx();
		}
	}
	
	private void loadSalesBudget(PO po)
	{
		MBPartner[] partners = null;
		if(po instanceof MDiscountSchema)
		{
			MDiscountSchema discount = (MDiscountSchema)po;
			partners = discount.getIncludedSaleses(true);
		}
		else if(po instanceof MDiscountSchemaBreak)
		{
			MDiscountSchemaBreak discount = (MDiscountSchemaBreak) po;
			partners = discount.getIncludedSaleses(true);
		}
		else if(po instanceof MUNSDSBreakLine)
		{
			MUNSDSBreakLine discount = (MUNSDSBreakLine) po;
			partners = discount.getIncludedSaleses(true);
		}
		
		if(null == partners)
			return ;
		
		for(int i=0; i<partners.length; i++)
		{
			MUNSSalesBudget salesBudget = new MUNSSalesBudget(po, Env.ZERO);
			salesBudget.setC_BPartner_ID(partners[i].get_ID());
			salesBudget.saveEx();
		}
	}
	
	private void calculateFlatSalesBudget()
	{
		MUNSSalesBudget[] listSalesBudget = m_discountSchema.getSalesBudgetLines(false);
		if(null != listSalesBudget && listSalesBudget.length > 0)
		{
			String msg = Msg.getMsg(getCtx(), "DeletePreviousRecrordMsg");
			String title = Msg.getMsg(getCtx(), "DeletePreviousRecrordTitle");
			int retVal = MessageBox.showMsg(
					getCtx(), getProcessInfo(), msg, title, MessageBox.YESNO, MessageBox.ICONWARNING);
			if(retVal == MessageBox.RETURN_NO)
				return;
			
			String sql = "DELETE FROM UNS_SalesBudget WHERE M_DiscountSchema_ID = ?";
			retVal = DB.executeUpdate(sql, m_discountSchema.get_ID(), get_TrxName());
			if(retVal <= 0)
				throw new AdempiereException("Could not delete Sales/Customer Budget Lines of Discount Schema ");
		}
		if(m_discountSchema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerBudget))
		{
			loadCustomerBudget(m_discountSchema);
		}
		else if(m_discountSchema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
		{
			loadSalesBudget(m_discountSchema);
		}
		else if(m_discountSchema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerGradeBudget))
		{
			loadCustomerGradeBudget(m_discountSchema);
		}
		else if(m_discountSchema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerGroupBudget))
		{
			loadCustomerGroupBudget(m_discountSchema);
		}
		else 
		{
			return;
		}
		
		listSalesBudget = m_discountSchema.getSalesBudgetLines(true);		
		double allocatedBudget = m_discountSchema.getQtyAllocated().doubleValue();
		calculateSalesBudget(
				listSalesBudget, allocatedBudget, 0, 
				!m_discountSchema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget));
	}
	
	private void calculateBreakSalesBudget()
	{
		MDiscountSchemaBreak[] dsBreaks = m_discountSchema.getBreaks(false);
		for(MDiscountSchemaBreak dsBreak : dsBreaks)
		{
			if(dsBreak.getBreakType().equals(MDiscountSchemaBreak.BREAKTYPE_MultipleDiscount))
			{
				calculateBreakLinesSalesBudget(dsBreak);
				continue;
			}
			MUNSSalesBudget[] listSalesBudget = dsBreak.getSalesBudgetLines(false);
			if(null != listSalesBudget && listSalesBudget.length > 0)
			{
				String msg = Msg.getMsg(getCtx(), "DeletePreviousRecrordMsg");
				String title = Msg.getMsg(getCtx(), "DeletePreviousRecrordTitle");
				int retVal = MessageBox.showMsg(getCtx(), getProcessInfo(), msg, title, MessageBox.YESNO, MessageBox.ICONWARNING);
				if(retVal == MessageBox.RETURN_NO)
					return;
				
				String sql = "DELETE FROM UNS_SalesBudget WHERE M_DiscountSchemaBreak_ID = ?";
				retVal = DB.executeUpdate(sql, dsBreak.get_ID(), get_TrxName());
				if(retVal <= 0)
					throw new AdempiereException("Could not delete Sales/Customer Budget Lines of Discount Schema ");
			}			
			if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerBudget))
			{
				loadCustomerBudget(dsBreak);
			}
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
			{
				loadSalesBudget(dsBreak);
			}
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerGradeBudget))
			{
				loadCustomerGradeBudget(dsBreak);
			}
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerGroupBudget))
			{
				loadCustomerGroupBudget(dsBreak);
			}
			else 
			{
				continue;
			}
			
			int percission = 0;
			if(dsBreak.isCalculationQty())
			{
				I_M_Product product = dsBreak.getM_Product();
				I_C_UOM uom = product.getC_UOM();
				percission = uom.getStdPrecision();
			}
			
			listSalesBudget = dsBreak.getSalesBudgetLines(true);
			double allocatedBudget = dsBreak.getQtyAllocated().doubleValue();
			calculateSalesBudget(
					listSalesBudget, allocatedBudget, percission
					, !dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_SalesBudget));
		}
	}
	
	private void calculateBreakLinesSalesBudget(MDiscountSchemaBreak dsBreak)
	{
		MUNSDSBreakLine[] breaks = dsBreak.getBreakLines(false);
		for(MUNSDSBreakLine l : breaks)
		{
			MUNSSalesBudget[] budgets = l.getSalesBudgetLines();
			if(budgets != null && budgets.length > 0)
			{
				String msg = Msg.getMsg(getCtx(), "DeletePreviousRecrordMsg");
				String title = Msg.getMsg(getCtx(), "DeletePreviousRecrordTitle");
				int retVal = MessageBox.showMsg(getCtx(), getProcessInfo(), msg, title, MessageBox.YESNO, MessageBox.ICONWARNING);
				if(retVal == MessageBox.RETURN_NO)
					return;
				
				String sql = "DELETE FROM UNS_SalesBudget WHERE UNS_DSBreakLine_ID = ?";
				retVal = DB.executeUpdate(sql, l.get_ID(), get_TrxName());
				if(retVal <= 0)
					throw new AdempiereException("Could not delete Sales/Customer Budget Lines of Discount Schema ");
			}
			
			if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerBudget))
			{
				loadCustomerBudget(l);
			}
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
			{
				loadSalesBudget(l);
			}
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerGradeBudget))
			{
				loadCustomerGradeBudget(l);
			}
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerGroupBudget))
			{
				loadCustomerGroupBudget(l);
			}
			else 
			{
				continue;
			}
			
			int percission = 0;
			if(dsBreak.isCalculationQty())
			{
				I_M_Product product = dsBreak.getM_Product();
				I_C_UOM uom = product.getC_UOM();
				percission = uom.getStdPrecision();
			}
			
			budgets = l.getSalesBudgetLines(true);
			double allocatedBudget = l.getQtyAllocated().doubleValue();
			calculateSalesBudget(
					budgets, allocatedBudget, percission
					, !dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_SalesBudget));
		}
	}
	
	/**
	 * 
	 * @param salesBudgets
	 * @return
	 */
	private Hashtable<Integer, SalesBudgetProportion> initializeSalesBudgetProportion(
			MUNSSalesBudget[] salesBudgets)
	{
		Hashtable<Integer, SalesBudgetProportion> mapSales = new Hashtable<Integer, SalesBudgetProportion>();
		
		int totalProportion = 0;
		for(MUNSSalesBudget salesBudget : salesBudgets)
		{
			int salesRep_ID = salesBudget.getC_BPartner_ID();
			MTree_NodeBP node = MTree_NodeBP.get(
					getCtx(), m_BPTree_ID, salesRep_ID, get_TrxName());
			int level = node.getLevel();
			
			SalesBudgetProportion salesBudgetProp = mapSales.get(level);
			if(null == salesBudgetProp)
			{
				salesBudgetProp = new SalesBudgetProportion();
				mapSales.put(level, salesBudgetProp);
				totalProportion += level;
			}
			salesBudgetProp.addSalesBudget(salesBudget);
		}
		
		double newTotal = 0;
		for(Integer key : mapSales.keySet())
		{
			SalesBudgetProportion salesBudgetProp = mapSales.get(key);
			double prop = totalProportion / key * salesBudgetProp.getListOfSalesBudgets().size();
			salesBudgetProp.setProportion(prop);
			newTotal += prop;
		}
		
		for(SalesBudgetProportion salesBudget : mapSales.values())
		{
			double newProp = salesBudget.getProportion() / newTotal * 100;
			salesBudget.setProportion(newProp);
		}
		
		return mapSales;
	}
}

class SalesBudgetProportion
{
	private double m_proportion = 0;
	private List<MUNSSalesBudget> m_salesesBudget;
	
	public SalesBudgetProportion() {
		this.m_salesesBudget = new ArrayList<MUNSSalesBudget>();
	}
	
	public void setProportion(double proportion)
	{
		this.m_proportion = proportion;
	}
	
	public double getProportion()
	{
		return this.m_proportion;
	}
	
	public List<MUNSSalesBudget> getListOfSalesBudgets()
	{
		return this.m_salesesBudget;
	}
	
	public void addSalesBudget(MUNSSalesBudget salesBudget)
	{
		this.m_salesesBudget.add(salesBudget);
	}
}