/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.base.model.MInOut;
import com.unicore.base.model.MInOutLine;
import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.MDiscountSchemaBreak;
import com.unicore.model.MUNSBonusClaim;
import com.unicore.model.MUNSBonusClaimLine;
import com.unicore.model.MUNSDiscountTrx;
import com.unicore.model.UNSDiscountBonus;

/**
 * @author UNTA-Andy, Menjangan
 * @see www.untasoft.com
 * 
 */
public class CalculateClaimDiscount extends SvrProcess
{

	private int p_UNS_BonusClaim;
	private MUNSBonusClaim m_UNSBonusClaim;
	private MDiscountSchemaBreak m_DiscountSchemaBreak;
	Hashtable<Integer, UNSDiscountBonus> m_mapDiscountBonus = 
					new Hashtable<Integer, UNSDiscountBonus>();
	

	/**
	 * 
	 */
	public CalculateClaimDiscount()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_UNS_BonusClaim = getRecord_ID();
		m_UNSBonusClaim = new MUNSBonusClaim(
				getCtx(), p_UNS_BonusClaim, get_TrxName());

		m_DiscountSchemaBreak =
				new MDiscountSchemaBreak(
						getCtx(), m_UNSBonusClaim.getM_DiscountSchemaBreak_ID(),
						get_TrxName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		if(m_DiscountSchemaBreak.getParent().isSOTrx()
				!= m_UNSBonusClaim.isSOTrx())
		{
			return "Not match discount";
		}
		
		if (m_UNSBonusClaim.getLines().length > 0)
		{
			m_UNSBonusClaim.removeLines();
		}
		
		if(m_UNSBonusClaim.getC_DocType().getDocBaseType().equals(
				MDocType.DOCBASETYPE_DiscountAfterTransaction))
		{
			calculteDiscountAfterTrx();
		}
		else if(m_UNSBonusClaim.getC_DocType().getDocBaseType().equals(
						MDocType.DOCBASETYPE_DiscountTargetBonus))
		{
			calculateDiscountTargetBonus();
		}
		
		return "success";
	}
	
	/**
	 * Calculate Vendor Cash Back...
	 * @param breakSchema
	 */
	private void calculateCashBack()
	{
		MUNSDiscountTrx[] myTransactions = m_DiscountSchemaBreak
				.getMyTransactions(true);
		BigDecimal achieved = Env.ZERO;
		BigDecimal claimed = Env.ZERO;
		int param_ID = 0;
		Vector<Object[]> trxs = new Vector<>();
		
		for(MUNSDiscountTrx transaction : myTransactions)
		{
			String sql = null;
			if(transaction.getC_Invoice_ID() == 0 
					&& transaction.getC_InvoiceLine_ID() == 0)
			{
				continue;
			}
			
			if(transaction.getC_Invoice_ID() > 0)
			{
				sql = "SELECT DocStatus FROM C_Invoice WHERE C_Invoice_ID = ?";
				param_ID = transaction.getC_Invoice_ID();
			}
			else if(transaction.getC_InvoiceLine_ID() > 0)
			{
				sql = "SELECT DocStatus FROM C_Invoice WHERE C_Invoice_ID = "
						+ "(SELECT C_Invoice_ID FROM C_InvoiceLine WHERE "
						+ "C_InvoiceLine_ID = ?)";
				
				param_ID = transaction.getC_InvoiceLine_ID();
			}
			
			if(null == sql)
				continue;
			
			String retVal = DB.getSQLValueString(get_TrxName(), sql, param_ID);
			if(null != retVal && !retVal.isEmpty() && !retVal.equals("CO")
					&& !retVal.equals("CL"))
				continue;
			
			achieved = achieved.add(transaction.getQtyValDiscounted());
			claimed = claimed.add(transaction.getDiscountValue1st().add(
					transaction.getDiscountValue2nd()
					.add(transaction.getDiscountValue3rd().add(
							transaction.getDiscountValue4th()
							.add(transaction.getDiscountValue5th().add(
									transaction.getQtyBonuses()))))));
			
			Object[] obj = new Object[1];
			obj[0] = transaction.get_ID();
			trxs.add(obj);
		}
		
		if(achieved.compareTo(m_DiscountSchemaBreak.getQtyAllocated()) < 0)
		{
			m_UNSBonusClaim.setIsNeedConfirm(true);
			m_UNSBonusClaim.saveEx();
		}
		
		I_M_Product product = m_DiscountSchemaBreak.getM_Product();
	
		BigDecimal qty = m_DiscountSchemaBreak.isCalculationQty() 
				? achieved : Env.ZERO;
		BigDecimal net = m_DiscountSchemaBreak.isCalculationValue() 
				? achieved : Env.ZERO;
		
		
		UNSDiscountBonus discountBonus = new UNSDiscountBonus(
				getCtx(), MUNSBonusClaimLine.Table_ID, 0, get_TrxName());
		discountBonus.setDataValue(product.getM_Product_ID(), 
				product.getC_UOM_ID(), qty, Env.ZERO, net, false);
		discountBonus.setOrgTrx_ID(m_UNSBonusClaim.getAD_Org_ID());
		discountBonus.setDiscountBonus(claimed);


		MUNSBonusClaimLine claimLine = 
				new MUNSBonusClaimLine(getCtx(), 0, get_TrxName());
		claimLine.setDataValue(discountBonus);
		claimLine.setUNS_BonusClaim_ID(m_UNSBonusClaim.get_ID());
		String sqlTax = "SELECT C_Tax_ID FROM C_Tax WHERE name LIKE 'Standard'";
		int C_Tax_ID = DB.getSQLValue(get_TrxName(), sqlTax);
		claimLine.setAD_Org_ID(m_UNSBonusClaim.getAD_Org_ID());
		claimLine.setC_Tax_ID(C_Tax_ID);
		claimLine.saveEx();
		
		for (Object[] obj : trxs) {
			if (null == obj) {
				continue;
			}
			MUNSDiscountTrx discTrx = new MUNSDiscountTrx(getCtx(),  ((int) obj[0]), get_TrxName());
			discTrx.setUNS_BonusClaimLine_ID(claimLine.get_ID());
			discTrx.saveEx();
		}
	}
	
	private void calculteDiscountAfterTrx()
	{
		if(m_DiscountSchemaBreak.getParent().getVendor_ID()
				!= m_UNSBonusClaim.getC_BPartner_ID())
			throw new AdempiereException("Not match Business Partner");
			
		if(!m_DiscountSchemaBreak.isVendorCashback())
				return;
		calculateCashBack();
	}
	
	private void calculateDiscountTargetBonus()
	{
		if(!m_DiscountSchemaBreak.isValidCustomerDiscount(
				(MBPartner) m_UNSBonusClaim.getC_BPartner()))
		{
			throw new AdempiereException("Not match discount schema");
		}
		else if(m_DiscountSchemaBreak.isDiscountEveryPOSO())
		{
			return;
		}
		
		if (m_UNSBonusClaim.getDocSourceType().equals(
				MUNSBonusClaim.DOCSOURCETYPE_PurchaseOrder)
				|| m_UNSBonusClaim.getDocSourceType().equals(
						MUNSBonusClaim.DOCSOURCETYPE_SalesOrder))
		{
			MOrder[] listOrder = MOrder.getOrderBPClaim(m_UNSBonusClaim);
		
			if(null == listOrder || listOrder.length == 0)
			{
				throw new AdempiereUserError("No order found for "
						+ " business partner "
						+ m_UNSBonusClaim.getC_BPartner().getName());
			}
				
			for (MOrder order : listOrder)
			{			
				if(order.isSOTrx() != m_UNSBonusClaim.isSOTrx())
					continue;
				
				if(m_DiscountSchemaBreak.getParent().isCumulativeDocument())
				{
					BigDecimal tempAmt = Env.ZERO;
					
					if(m_DiscountSchemaBreak.isTragetBeforeDiscount())
						tempAmt = order.getTotalLines();
					else
						tempAmt = order.getGrandTotal();
					
					
					UNSDiscountBonus discountBonus = m_mapDiscountBonus.get(
							m_DiscountSchemaBreak.get_ID());
					if(null == discountBonus)
					{
						discountBonus =
								new UNSDiscountBonus(getCtx(), 
										MUNSBonusClaimLine.Table_ID, 0, 
										get_TrxName());							
						discountBonus.setOrgTrx_ID(m_UNSBonusClaim.getAD_Org_ID());	
						m_mapDiscountBonus.put(
								m_DiscountSchemaBreak.get_ID(), discountBonus);
						discountBonus.setDataValue(
								0, 0, Env.ZERO,Env.ZERO, Env.ZERO, false);
					}
						
					tempAmt = tempAmt.add(discountBonus.getLineNetAmount());
					discountBonus.setLineNetAmount(tempAmt);
					continue;
				}
				
				for (MOrderLine line : order.getLines())
				{
					if (line.getRef_OrderLine_ID() > 0)
						continue;

					BigDecimal tempLineNetAmt	= Env.ZERO;
					BigDecimal tempQty			= Env.ZERO;
					BigDecimal tempPrice		= Env.ZERO;
					
					if (m_DiscountSchemaBreak.isInMySelectionProducts(
							line.getM_Product_ID())
							&& !line.isProductBonuses())
					{
						if(m_DiscountSchemaBreak.isTragetBeforeDiscount())
						{
							tempQty			= line.getQtyEntered()
									.subtract(line.getQtyBonuses());
							tempPrice		= line.getPriceList();
							tempLineNetAmt	= tempPrice.multiply(tempQty);
						}
						else
						{
							tempQty			= line.getQtyEntered();
							tempPrice		= line.getPriceEntered();
							tempLineNetAmt	= line.getLineNetAmt();
						}
						
						UNSDiscountBonus discountBonus = m_mapDiscountBonus.
								get(line.getM_Product_ID());
						if(null == discountBonus)
						{
							discountBonus =
									new UNSDiscountBonus(getCtx(), 
											MUNSBonusClaimLine.Table_ID, 0, 
											get_TrxName());
							discountBonus.setDataValue(
									line.getM_Product_ID(), line.getC_UOM_ID(), 
									Env.ZERO, Env.ZERO, Env.ZERO, false);

							discountBonus.setOrgTrx_ID(
									m_UNSBonusClaim.getAD_Org_ID());
							m_mapDiscountBonus.put(
									line.getM_Product_ID(), discountBonus);
						}
						tempLineNetAmt	= tempLineNetAmt.add(
								discountBonus.getLineNetAmount());
						tempQty			= tempQty.add(discountBonus.getQty());
						tempPrice	= tempLineNetAmt.divide(
								tempQty, 4, RoundingMode.HALF_DOWN);
						
						discountBonus.setLineNetAmount(tempLineNetAmt);
						discountBonus.setQty(tempQty);
						discountBonus.setPrice(tempPrice);
					}
				}
			}
		}
		else if (m_UNSBonusClaim.getDocSourceType()
				.equals(MUNSBonusClaim.DOCSOURCETYPE_MaterialReceipt)
				|| m_UNSBonusClaim.getDocSourceType().equals(
						MUNSBonusClaim.DOCSOURCETYPE_MaterialShipment))
		{
			MInOut[] listInOut =
					MInOut.getInOutByDate(
						getCtx(),
						m_UNSBonusClaim.getDateFrom(),
						m_UNSBonusClaim.getDateTo(),
						m_UNSBonusClaim.getDocSourceType().equals(
								MUNSBonusClaim.DOCSOURCETYPE_MaterialShipment),
								get_TrxName());
					
			for (MInOut inOut : listInOut)
			{			
				if(inOut.isSOTrx() != m_UNSBonusClaim.isSOTrx())
					continue;
				
				if(m_DiscountSchemaBreak.getParent().isCumulativeDocument())
				{
					BigDecimal tempAmt = Env.ZERO;
					
					tempAmt = inOut.getAmtOrder(
							m_DiscountSchemaBreak.isTragetBeforeDiscount());				
					
					UNSDiscountBonus discountBonus = m_mapDiscountBonus.get(
							m_DiscountSchemaBreak.get_ID());
					if(null == discountBonus)
					{
						discountBonus =
								new UNSDiscountBonus(getCtx(), 
										MUNSBonusClaimLine.Table_ID, 0, 
										get_TrxName());							
						discountBonus.setOrgTrx_ID(
								m_UNSBonusClaim.getAD_Org_ID());	
						m_mapDiscountBonus.put(m_DiscountSchemaBreak.get_ID(), 
								discountBonus);
						discountBonus.setDataValue(0, 0, Env.ZERO,Env.ZERO, 
								Env.ZERO, false);
					}
						
					tempAmt = tempAmt.add(discountBonus.getLineNetAmount());
					discountBonus.setLineNetAmount(tempAmt);
					continue;
				}
				
				for (MInOutLine line : inOut.getLines(false))
				{
					if (line.getRef_InOutLine_ID() > 0)
						continue;

					BigDecimal tempLineNetAmt	= Env.ZERO;
					BigDecimal tempQty			= Env.ZERO;
					BigDecimal tempPrice		= Env.ZERO;
					
					if (m_DiscountSchemaBreak.isInMySelectionProducts(
							line.getM_Product_ID()))
					{
						
						tempQty			= line.getMovementQty();
						tempPrice		= line.getPriceOrder(
								m_DiscountSchemaBreak.isTragetBeforeDiscount());
						tempLineNetAmt	= tempPrice.multiply(tempQty);

						
						UNSDiscountBonus discountBonus = m_mapDiscountBonus.
								get(line.getM_Product_ID());
						if(null == discountBonus)
						{
							discountBonus =
									new UNSDiscountBonus(getCtx(), 
											MUNSBonusClaimLine.Table_ID, 0,
											get_TrxName());
							discountBonus.setDataValue(
									line.getM_Product_ID(), line.getC_UOM_ID(), 
									Env.ZERO, Env.ZERO, Env.ZERO, false);

							discountBonus.setOrgTrx_ID(m_UNSBonusClaim.getAD_Org_ID());
							m_mapDiscountBonus.put(line.getM_Product_ID(), discountBonus);
						}
						tempLineNetAmt	= tempLineNetAmt.add(discountBonus.getLineNetAmount());
						tempQty			= tempQty.add(discountBonus.getQty());
						tempPrice	= tempLineNetAmt.divide(tempQty, 4, RoundingMode.HALF_DOWN);
						
						discountBonus.setLineNetAmount(tempLineNetAmt);
						discountBonus.setQty(tempQty);
						discountBonus.setPrice(tempPrice);
					}
				}
			}
		}
		else if(MUNSBonusClaim.DOCSOURCETYPE_APInvoice.equals(m_UNSBonusClaim.getDocSourceType())
				|| MUNSBonusClaim.DOCSOURCETYPE_ARInvoice.equals(m_UNSBonusClaim.getDocSourceType()))
		{			
			MInvoice[] listInvoice = MInvoice.getInvoiceByDate(
					getCtx(), m_UNSBonusClaim.getDateFrom(), m_UNSBonusClaim.getDateTo(), get_TrxName() );
			
			if(null == listInvoice || listInvoice.length == 0)
			{
				throw new AdempiereUserError("No order found for business partner "
						+ m_UNSBonusClaim.getC_BPartner().getName());
			}
			
			for (MInvoice invoice : listInvoice)
			{			
				if(invoice.isSOTrx() != m_UNSBonusClaim.isSOTrx())
					continue;
				
				if(m_DiscountSchemaBreak.getParent().isCumulativeDocument())
				{
					BigDecimal tempAmt = Env.ZERO;
					
					if(m_DiscountSchemaBreak.isTragetBeforeDiscount())
						tempAmt = invoice.getTotalLines();
					else
						tempAmt = invoice.getGrandTotal();
					
					
					UNSDiscountBonus discountBonus = m_mapDiscountBonus.get(m_DiscountSchemaBreak.get_ID());
					if(null == discountBonus)
					{
						discountBonus =
								new UNSDiscountBonus(getCtx(), MUNSBonusClaimLine.Table_ID, 0, get_TrxName());							
						discountBonus.setOrgTrx_ID(m_UNSBonusClaim.getAD_Org_ID());	
						m_mapDiscountBonus.put(m_DiscountSchemaBreak.get_ID(), discountBonus);
						discountBonus.setDataValue(0, 0, Env.ZERO,Env.ZERO, Env.ZERO, false);
					}
						
					tempAmt = tempAmt.add(discountBonus.getLineNetAmount());
					discountBonus.setLineNetAmount(tempAmt);
					continue;
				}
				
				for (MInvoiceLine line : invoice.getLines(false))
				{
					if (line.getRef_InvoiceLine_ID() > 0)
						continue;

					BigDecimal tempLineNetAmt	= Env.ZERO;
					BigDecimal tempQty			= Env.ZERO;
					BigDecimal tempPrice		= Env.ZERO;
					
					if (m_DiscountSchemaBreak.isInMySelectionProducts(line.getM_Product_ID())
							&& !line.isProductBonuses())
					{
						if(m_DiscountSchemaBreak.isTragetBeforeDiscount())
						{
							tempQty			= line.getQtyEntered().subtract(line.getQtyBonuses());
							tempPrice		= line.getPriceList();
							tempLineNetAmt	= tempPrice.multiply(tempQty);
						}
						else
						{
							tempQty			= line.getQtyEntered();
							tempPrice		= line.getPriceEntered();
							tempLineNetAmt	= line.getLineNetAmt();
						}
						
						UNSDiscountBonus discountBonus = m_mapDiscountBonus.get(line.getM_Product_ID());
						if(null == discountBonus)
						{
							discountBonus =
									new UNSDiscountBonus(getCtx(), MUNSBonusClaimLine.Table_ID, 0, get_TrxName());
							discountBonus.setDataValue(
									line.getM_Product_ID(), line.getC_UOM_ID(), Env.ZERO
									, Env.ZERO, Env.ZERO, false);

							discountBonus.setOrgTrx_ID(m_UNSBonusClaim.getAD_Org_ID());
							m_mapDiscountBonus.put(line.getM_Product_ID(), discountBonus);
						}
						tempLineNetAmt	= tempLineNetAmt.add(discountBonus.getLineNetAmount());
						tempQty			= tempQty.add(discountBonus.getQty());
						tempPrice	= tempLineNetAmt.divide(tempQty, 4, RoundingMode.HALF_DOWN);
						
						discountBonus.setLineNetAmount(tempLineNetAmt);
						discountBonus.setQty(tempQty);
						discountBonus.setPrice(tempPrice);
					}
				}
			}
		}

		boolean isNeedConfirm = false;
		
		for (UNSDiscountBonus discountBonus : m_mapDiscountBonus.values())
		{
			CalculateDiscount calcDiscount = new CalculateDiscount();
			calcDiscount.setCtx(getCtx());
			calcDiscount.setTrxName(get_TrxName());
			Env.setContext(getCtx(), "UNS_BonusClaim_ID", m_UNSBonusClaim.get_ID());

			MUNSBonusClaimLine claimLine = new MUNSBonusClaimLine(getCtx(), 0, get_TrxName());
			claimLine.setDataValue(discountBonus);
			claimLine.setUNS_BonusClaim_ID(m_UNSBonusClaim.get_ID());
			String sql = "SELECT C_Tax_ID FROM C_Tax WHERE name LIKE 'Standard'";
			int C_Tax_ID = DB.getSQLValue(get_TrxName(), sql);
			claimLine.setAD_Org_ID(m_UNSBonusClaim.getAD_Org_ID());
			claimLine.setC_Tax_ID(C_Tax_ID);
			claimLine.saveEx();
			
			discountBonus.setRecord_ID(claimLine.get_ID());
			discountBonus.setOrgTrx_ID(claimLine.getAD_Org_ID());
			
			discountBonus =
					calcDiscount.checkByDiscountType(m_DiscountSchemaBreak, discountBonus, m_DiscountSchemaBreak
							.getBreakType().equals(MDiscountSchemaBreak.BREAKTYPE_FlatDiscount), m_DiscountSchemaBreak
							.getCalculationType().equals(MDiscountSchemaBreak.CALCULATIONTYPE_Qty));
			if(!isNeedConfirm)
			{
				isNeedConfirm = discountBonus.isNeedConfirmation();
			}
			if(discountBonus.getQtyBonus().signum()== 0 && discountBonus.getDiscountBonus().signum() == 0)
			{
				claimLine.deleteEx(true);
				continue;
			}
			claimLine.setDataValue(discountBonus);
			claimLine.setAmtClaimed(Env.ZERO);
			claimLine.saveEx();
		}
		
		m_UNSBonusClaim.setIsNeedConfirm(isNeedConfirm);
		m_UNSBonusClaim.saveEx();
	}
}