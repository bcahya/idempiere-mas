/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSysConfig;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MProduct;
import com.uns.util.UNTPair;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.I_DiscountModel;
import com.unicore.model.I_DiscountModelLine;
import com.unicore.model.MDiscountSchema;
import com.unicore.model.MDiscountSchemaBreak;
import com.unicore.model.MUNSBonusClaimLine;
import com.unicore.model.MUNSDSBreakLine;
import com.unicore.model.MUNSDiscountBonus;
import com.unicore.model.MUNSDiscountProduct;
import com.unicore.model.MUNSDiscountTrx;
import com.unicore.model.UNSDiscountBonus;

/**
 * @author Menjangan@Untasoft
 * @see www.untasoft.com
 */
public class CalculateDiscount 
{
	private I_DiscountModel m_model = null;
	private List<MDiscountSchema> m_AllDiscountSchema;
	private boolean m_reset = true;
	private boolean m_justUpdate = false;
	private Hashtable<Integer, BigDecimal> tmpQtyBonus = 
			new Hashtable<Integer, BigDecimal>();
	private boolean m_hasPrepared = false;
	private Properties ctx = null;
	private String trxName = null;
	private BigDecimal m_divider = Env.ONE;
	private BigDecimal m_multiplicand = Env.ONE;
	private final int m_precission = 5;
	private boolean m_isInvoice = true;
	private int m_Parent_ID = 0;

	/**
	 * 
	 * @param model
	 */
	public CalculateDiscount(I_DiscountModel model, boolean isInvoice, int Parent_ID)
	{
		m_Parent_ID = Parent_ID;
		m_isInvoice = isInvoice;
		m_model = model;
		setTrxName(m_model.get_TrxName());
		setCtx(m_model.getCtx());
	}
	
	public CalculateDiscount()
	{
		super();
	}
	
	public void setIsUpdate (boolean isUpdate)
	{
		m_justUpdate = isUpdate;
	}

	protected void prepare()
	{		
		m_AllDiscountSchema = MDiscountSchema.getComplete(m_model);		
		checkAppliedDiscount();
		checkPPNCalculation();
		m_hasPrepared = true;
	}
	
	private void checkPPNCalculation ()
	{
		if(m_model.isSOTrx())
		{
			boolean isIncludeTaxOnCalculation = MSysConfig.
					getBooleanValue(MSysConfig.
							INCLUDE_TAX_ON_DISCOUNT_CALCULATION, true);
			if (!isIncludeTaxOnCalculation)
			{
				return;
			}
			
			String pkp = DB.getSQLValueString(trxName, "SELECT " 
					.concat(MBPartner.COLUMNNAME_IsPKP) .concat(" FROM ") 
					.concat(MBPartner.Table_Name) .concat(" WHERE ") 
					.concat(MBPartner.COLUMNNAME_C_BPartner_ID)
					.concat(" = ?"), m_model.getBPartner().get_ID());

			if(null == pkp)	
			{	
				pkp = "N";
			}
			
			boolean isPKP = "Y" .equals(pkp);
			
			int taxID = DB.getSQLValue(
					trxName, "SELECT " .concat(MOrgInfo.COLUMNNAME_OrgTax_ID)
					.concat(" FROM ").concat(MOrgInfo.Table_Name)
					.concat(" WHERE ").concat(MOrgInfo.COLUMNNAME_AD_Org_ID)
					.concat(" = ? "), m_model.getModel()
					.getAD_Org_ID());
			
			int PPNTax_ID = DB.getSQLValue (trxName, "SELECT C_Tax_ID " + 
					"FROM C_Tax WHERE " + " UPPER(Name) = ?", "PPN");
			
			if (PPNTax_ID != taxID && isPKP)	
			{
				m_multiplicand = new BigDecimal(1.1);
			}
			else if (PPNTax_ID == taxID && !isPKP)
			{	
				m_divider = new BigDecimal(1.1);
			}
		}
	}
	
	public void run()
	{
		try {
			doIt();
		} catch (Exception ex) {
			throw new AdempiereException("Error on calculate discount " +
						ex.toString());
		}
	}

	protected String doIt() throws Exception
	{
		String returnMsg = null;
		
		if(!m_isInvoice && m_Parent_ID > 0)
		{
			String tableParent = m_isInvoice ? "C_Invoice" : "C_Order";
			StringBuilder delExisting = new StringBuilder();
			delExisting.append("DELETE FROM UNS_DiscountTrx WHERE ").append(tableParent).append("_ID=?")
			.append(" OR ").append(tableParent).append("Line_ID IN ").append("(SELECT C_OrderLine_ID FROM C_OrderLine WHERE C_Order_ID=?)");
			int count = DB.executeUpdate(delExisting.toString(), new Object[]{m_Parent_ID, m_Parent_ID}, false, trxName);
			if(count < 0)
				throw new AdempiereException("Failed when trying up re check status discount");
		}
		
		if(!m_hasPrepared)
		{	
			prepare();
		}
		if (m_AllDiscountSchema == null || m_AllDiscountSchema.size() == 0)
		{
			return null;
		}
		
		boolean flagResetDocument = false;
		boolean flagResetLine = false;
		m_reset = true;
		
		if(m_reset)
		{
			flagResetDocument = true;
			flagResetLine = true;
		}

		for (int x=0; x<m_AllDiscountSchema.size(); x++)
		{
			MDiscountSchema schema = m_AllDiscountSchema.get(x);
			if(flagResetDocument && schema.isCumulativeDocument())
			{
				m_model.setDiscountAmt(BigDecimal.ZERO);
				m_model.getModel().saveEx();
				flagResetDocument = false;
			}
			if(flagResetLine && schema.isCumulativeLine())
			{
				List<I_DiscountModelLine> lines = m_model.getLines(false);
				
				for(int i=0; i<lines.size(); i++)
				{
					if(lines.get(i).isProductBonuses())
					{
						continue;
					}
					if (!lines.get(i).getModel().isActive()) {
						String sql = "DELETE FROM UNS_DiscountTrx WHERE " + lines.get(i).getModel().get_TableName() + "_ID = ?";
						int result = DB.executeUpdate(sql, lines.get(i).getModel().get_ID(), false, lines.get(i).getModel().get_TrxName());
						if (result == -1)
							 throw new AdempiereException("Failed when try to delete discount trx");
					}
					lines.get(i).setDiscountAmt(BigDecimal.ZERO);
					lines.get(i).setDiscount(Env.ZERO);
					lines.get(i).setPrice(lines.get(i).getPriceList());
					lines.get(i).setQtyMerge(lines.get(i).getQtyEntered());
					lines.get(i).setQtyBonuses(Env.ZERO);
					lines.get(i).setLineNetAmt(lines.get(i).getQtyEntered().
							multiply(lines.get(i).getPriceList()));
					lines.get(i).getModel().saveEx();
				}
				
				flagResetLine = false;
			}
			
			if (schema.getDiscountType().equals(
					MDiscountSchema.DISCOUNTTYPE_Flat))
			{	
				calculateFlat(schema);
				continue;
			}

			List<MDiscountSchemaBreak> dsBreaks = schema.getListBreaks(false);
			for (int o=0; o<dsBreaks.size(); o++)
			{
				MDiscountSchemaBreak dsBreak = dsBreaks.get(o);
				if(!dsBreak.isDiscountEveryPOSO())
				{
					continue;
				}
				else if(dsBreak.isBirthdayDiscount() && ! m_model.isBirthday())
				{
					continue;
				}
				else if(dsBreak.isMixRequired())
				{
					calculateMixRequirement(dsBreak);
				}
				else if(dsBreak.isMix())
				{
					calculateMix(dsBreak);
				}
				else
				{
					calculateBreak(dsBreak);
				}
			}
		}
		
		return returnMsg;
	}

	public BigDecimal setDiscountPercentByShema(UNSDiscountBonus discountBonus, 
			MDiscountSchema discountSchema,BigDecimal discountValue, 
			MUNSDiscountTrx discountTrx)
	{
		BigDecimal amount = discountBonus.getLineNetAmount();
		if(!discountSchema.isBudgetAvailable(discountBonus))
		{
			if (discountTrx != null)
				discountTrx.deleteEx(true);
			return Env.ZERO;
		}
		
		BigDecimal percent1 = Env.ZERO;
		BigDecimal percent2 = Env.ZERO;
		BigDecimal percent3 = Env.ZERO;
		BigDecimal percent4 = Env.ZERO;
		BigDecimal percent5 = Env.ZERO;
		
		if(m_justUpdate)
		{
			percent1 = discountTrx.getFirstDiscount();
			percent2 = discountTrx.getSecondDiscount();
			percent3 = discountTrx.getThirdDiscount();
			percent4 = discountTrx.getFourthDiscount();
			percent5 = discountTrx.getFifthDiscount();
		}
		else
		{
			percent1 = discountSchema.getFlatDiscount();
			percent2 = discountSchema.getSecondDiscount();
			percent3 = discountSchema.getThirdDiscount();
			percent4 = discountSchema.getFourthDiscount();
			percent5 = discountSchema.getFifthDiscount();
		}
		
		if(percent1.compareTo(Env.ZERO) > 0)
		{
			discountValue = amount.multiply(percent1.divide(
					Env.ONEHUNDRED, m_precission, RoundingMode.HALF_EVEN));
			discountTrx.setDiscountValue1st(discountValue);
			discountTrx.setFirstDiscount(percent1);
		}
		
		if (percent2.compareTo(Env.ZERO) > 0)
		{
			BigDecimal discountAmt = amount.subtract(discountValue).
					multiply(percent2.divide(Env.ONEHUNDRED, m_precission,
							RoundingMode.HALF_EVEN));
			discountValue = discountValue.add(discountAmt);
			discountTrx.setDiscountValue2nd(discountAmt);
			discountTrx.setSecondDiscount(percent2);
		}
		
		if (percent3.compareTo(Env.ZERO) > 0)
		{
			BigDecimal discountAmt = amount.subtract(discountValue).multiply(
					percent3.divide(Env.ONEHUNDRED, m_precission,
							RoundingMode.HALF_EVEN));
			discountValue = discountValue.add(discountAmt);
			discountTrx.setDiscountValue3rd(discountAmt);
			discountTrx.setThirdDiscount(percent3);
		}
		
		if (percent4.compareTo(Env.ZERO) > 0)
		{
			BigDecimal discountAmt = amount.subtract(discountValue).
					multiply(percent4.divide(Env.ONEHUNDRED, m_precission,
							RoundingMode.HALF_EVEN));
			discountValue = discountValue.add(discountAmt);
			discountTrx.setDiscountValue4th(discountAmt);
			discountTrx.setFourthDiscount(percent4);
		}
		
		if (percent5.compareTo(Env.ZERO) > 0)
		{
			BigDecimal discountAmt = amount.subtract(discountValue)
					.multiply(percent5.divide(Env.ONEHUNDRED, m_precission, 
							RoundingMode.HALF_EVEN));
			discountValue = discountValue.add(discountAmt);
			discountTrx.setDiscountValue5th(discountAmt);
			discountTrx.setFifthDiscount(percent5);
		}
		
		discountTrx.setDiscountType(MUNSDiscountTrx.DISCOUNTTYPE_Percent);
		discountTrx.setQtyValDiscounted(amount);
		discountTrx.setDiscountedAmt(amount);
		discountTrx.save();
		
		return discountValue;
	}
	
	/**
	 * 
	 * @param dsBreak
	 * @param discountBonus
	 * @param isFlat
	 * @param isQty
	 * @return
	 */
	public UNSDiscountBonus checkByDiscountType(MDiscountSchemaBreak dsBreak,
			UNSDiscountBonus discountBonus, boolean isFlat, boolean isQty)
	{
		discountBonus.setDiscountBonus(Env.ZERO);
		if (dsBreak.getAD_Org_ID() != 0 && discountBonus.getOrgTrx_ID() != dsBreak.getAD_Org_ID())
			return discountBonus;
		if (isFlat && isQty)
		{
			BigDecimal comparator = dsBreak.isMix() ? 
					discountBonus.getConversionMixQty() 
					: discountBonus.getConversionQty();
			if (comparator.compareTo(dsBreak.getBreakValue()) < 0 && 
					discountBonus.getTable_ID() != MUNSBonusClaimLine.Table_ID)
			{
				return discountBonus;
			}
		}
		else if (isFlat && !isQty)
		{
			BigDecimal comparator = dsBreak.isMix() ? 
					discountBonus.getLinenetAmtMix().multiply(m_divider)
					.divide(m_multiplicand, m_precission, RoundingMode.HALF_EVEN)
					: discountBonus.getLineNetAmount();
			
			if (comparator.compareTo(dsBreak.getBreakValue()) < 0 && 
					discountBonus.getTable_ID() != MUNSBonusClaimLine.Table_ID)
			{
				return discountBonus;
			}
		}
		discountBonus.setDiscountBonus(
				setDiscount(dsBreak, discountBonus, isFlat, isQty));
		return discountBonus;
	}

	/**
	 * 
	 * @param dsBreak
	 * @param discountBonus
	 * @param isFlat
	 * @param isQty
	 * @return
	 */
	private BigDecimal setDiscount(MDiscountSchemaBreak dsBreak, 
			UNSDiscountBonus discountBonus,boolean isFlat, boolean isQty)
	{
		if (isFlat)
		{
			int multiples = dsBreak.getNofMultiples().intValue();
			if (multiples == -1)
			{
				multiples = Integer.MAX_VALUE;
			}
			
			BigDecimal multiply = dsBreak.getBreakValue();
			BigDecimal discount = Env.ZERO;
			BigDecimal qtyMultiplyPrice = isQty ? dsBreak.getBreakValue().
					multiply(discountBonus.getPrice()) 
					:dsBreak.getBreakValue();
;
			
			BigDecimal qtyValDiscounted = isQty ? discountBonus.getConversionQty()
					: discountBonus.getLineNetAmount();

			BigDecimal checkValue = qtyValDiscounted;
				
			MUNSDiscountTrx discountTrx = MUNSDiscountTrx.get(
					discountBonus, dsBreak);
			if(null == discountTrx)
			{
				discountTrx = new MUNSDiscountTrx(discountBonus);
				discountTrx.setDiscountTrx(null, dsBreak, discountBonus);
				discountTrx.setM_DiscountSchemaBreak_ID(dsBreak.get_ID());
				discountTrx.setQtyValDiscounted(qtyValDiscounted);
				discountTrx.setDiscountedAmt(discountBonus.getLineNetAmount());
				discountTrx.setName(dsBreak.getName());
			}

			if (!MDiscountSchemaBreak.BUDGETCALCULATION_AmountOfDiscount.equals(dsBreak.getBudgetCalculation())
					&& !dsBreak.isBudgetAvailable(discountBonus)) {
				if (discountTrx.get_ID() > 0)
				{
					discountTrx.deleteEx(true);
				}
				
				return Env.ZERO;
			}
			
			int i = 1;
			if (dsBreak.getDiscountType().equals(MDiscountSchemaBreak.
					DISCOUNTTYPE_PercentValueDiscount))
			{			
				BigDecimal percent1 = Env.ZERO;
				BigDecimal percent2 = Env.ZERO;
				BigDecimal percent3 = Env.ZERO;
				BigDecimal percent4 = Env.ZERO;
				BigDecimal percent5 = Env.ZERO;
				BigDecimal accumulated1 = Env.ZERO;
				BigDecimal accumulated2 = Env.ZERO;
				BigDecimal accumulated3 = Env.ZERO;
				BigDecimal accumulated4 = Env.ZERO;
				BigDecimal accumulated5 = Env.ZERO;
				
				if(m_justUpdate)
				{
					percent1 = discountTrx.getFirstDiscount();
					percent2 = discountTrx.getSecondDiscount();
					percent3 = discountTrx.getThirdDiscount();
					percent4 = discountTrx.getFourthDiscount();
					percent5 = discountTrx.getFifthDiscount();
				}
				else
				{
					percent1 = dsBreak.getBreakDiscount();
					percent2 = dsBreak.getSecondDiscount();
					percent3 = dsBreak.getThirdDiscount();
					percent4 = dsBreak.getFourthDiscount();
					percent5 = dsBreak.getFifthDiscount();
					discountTrx.setZeroValueDiscount();
				}
				do
				{
					BigDecimal discountValue = Env.ZERO;
					BigDecimal discountTemp = Env.ZERO;
				
					if(percent1.compareTo(Env.ZERO) > 0)
					{
						discountTemp = 
								getDiscountValueFlatPrecentDiscountQtyValue(
										dsBreak, percent1, 
										discountBonus.getPrice(), 
										isQty);
						discountValue = discountValue.add(discountTemp);
						accumulated1 = accumulated1.add(discountTemp);
					}
					if (percent2.compareTo(Env.ZERO) > 0)
					{
						qtyMultiplyPrice = qtyMultiplyPrice.
								subtract(discountValue);
						discountTemp = 
								getDiscountValueFlatPrecentDiscountQtyValue(
										dsBreak, percent2, qtyMultiplyPrice,
										isQty);
						discountValue = discountValue.add(discountTemp);
						accumulated2 = accumulated2.add(discountTemp);
					}
					if (percent3.compareTo(Env.ZERO) > 0)
					{
						qtyMultiplyPrice = qtyMultiplyPrice.
								subtract(discountValue);
						discountTemp =
								getDiscountValueFlatPrecentDiscountQtyValue(
										dsBreak, percent3, qtyMultiplyPrice, 
										isQty );
						discountValue = discountValue.add(discountTemp);
						accumulated3 = accumulated3.add(discountTemp);
					}
					if (percent4.compareTo(Env.ZERO) > 0)
					{
						qtyMultiplyPrice = qtyMultiplyPrice.
								subtract(discountValue);
						discountTemp =
								getDiscountValueFlatPrecentDiscountQtyValue(
										dsBreak, percent4, qtyMultiplyPrice, 
										isQty);
						discountValue = discountValue.add(discountTemp);
						accumulated4 = accumulated4.add(discountTemp);
					}
					if (percent5.compareTo(Env.ZERO) > 0)
					{
						qtyMultiplyPrice = qtyMultiplyPrice.
								subtract(discountValue);
						discountTemp =
								getDiscountValueFlatPrecentDiscountQtyValue(
										dsBreak, percent5, qtyMultiplyPrice,
										isQty);
						discountValue = discountValue.add(discountTemp);
						accumulated5 = accumulated5.add(discountTemp);
					}

					discount = discount.add(discountValue);
					multiply = multiply.add(dsBreak.getBreakValue());
					boolean next = multiply.compareTo(checkValue) <= 0
							&& !m_justUpdate;
					if (!next)
					{
						break;
					}
					i++;

				} while (i <= multiples);
				
				if (!MDiscountSchemaBreak.BUDGETCALCULATION_AmountOfDiscount.equals(dsBreak.getBudgetCalculation())
						&& !dsBreak.isBudgetAvailable(discountBonus)) {
					discountBonus.setDiscountBonus(Env.ZERO);
					if (discountTrx != null)
						discountTrx.deleteEx(true);
					return Env.ZERO;
				}
				discountBonus.setDiscountBonus(discount);
				discountTrx.setDiscountValue1st(accumulated1);
				discountTrx.setDiscountValue2nd(accumulated2);
				discountTrx.setDiscountValue3rd(accumulated3);
				discountTrx.setDiscountValue4th(accumulated4);
				discountTrx.setDiscountValue5th(accumulated5);
				BigDecimal tmpPrice = discountBonus.getPrice();
				BigDecimal real1 = accumulated1.divide(tmpPrice, m_precission, 
						RoundingMode.HALF_DOWN);
				tmpPrice = tmpPrice.subtract(accumulated1);  
				BigDecimal real2 = accumulated2.divide(discountBonus.getPrice()
						, m_precission, RoundingMode.HALF_DOWN);
				tmpPrice = tmpPrice.subtract(accumulated2);
				BigDecimal real3 = accumulated3.divide(tmpPrice, m_precission, 
						RoundingMode.HALF_DOWN);
				tmpPrice = tmpPrice.subtract(accumulated3);
				BigDecimal real4 = accumulated4.divide(tmpPrice, m_precission, 
						RoundingMode.HALF_DOWN);
				tmpPrice = tmpPrice.subtract(accumulated4);
				BigDecimal real5 = accumulated5.divide(tmpPrice, m_precission, 
						RoundingMode.HALF_DOWN);
				
				discountTrx.setFirstDiscount(real1);
				discountTrx.setSecondDiscount(real2);
				discountTrx.setThirdDiscount(real3);
				discountTrx.setFourthDiscount(real4);
				discountTrx.setFifthDiscount(real5);
				discountTrx.setDiscountType(MUNSDiscountTrx.
						DISCOUNTTYPE_Percent);
				
				discountTrx.setQtyValDiscounted(discountBonus.getQty());
				discountTrx.setIsNeedRecalculate(false);
				discountTrx.saveEx();
			}
			// Flat Value Discount
			else if (dsBreak.getDiscountType().equals(
					MDiscountSchemaBreak.DISCOUNTTYPE_FlatValueDiscount))
			{
				do
				{
					BigDecimal discountval = Env.ZERO;
					
					if(m_justUpdate)
					{
						discountval = discountTrx.getFlatValueDiscount();
					}
					else
					{
						discountval = dsBreak.getBreakDiscount();
						discountval = discountval.multiply(m_multiplicand)
								.divide(m_divider, m_precission, 
										RoundingMode.HALF_EVEN);
					}
					
					discount = discount.add(discountval);
					multiply = multiply.add(dsBreak.getBreakValue());
					boolean next = multiply.compareTo(checkValue) <= 0
							&& !m_justUpdate;
					if (!next)
					{
						break;
					}
					i++;

				} while (i <= multiples);
				
				if (!MDiscountSchemaBreak.BUDGETCALCULATION_AmountOfDiscount.equals(dsBreak.getBudgetCalculation())
						&& !dsBreak.isBudgetAvailable(discountBonus)) {
					discountBonus.setDiscountBonus(Env.ZERO);
					if (discountTrx != null)
						discountTrx.deleteEx(true);
					return Env.ZERO;
				}
				
				discountBonus.setDiscountBonus(discount);
				BigDecimal percent = discount.divide(
							qtyMultiplyPrice, m_precission,
							RoundingMode.HALF_DOWN).multiply(Env.ONEHUNDRED);

				discountTrx.setFlatValueDiscount(discount);
				discountTrx.setFlatPercentDiscount(percent);
				discountTrx.setQtyValDiscounted(discountBonus.getQty());
				discountTrx.setDiscountType(MUNSDiscountTrx.DISCOUNTTYPE_Value);
				discountTrx.setIsNeedRecalculate(false);
				discountTrx.saveEx();
			}
			// Bonuses Product
			else if (dsBreak.getDiscountType().equals(
					MDiscountSchemaBreak.DISCOUNTTYPE_PercentProductBonuses)
					|| dsBreak.getDiscountType().equals(
							MDiscountSchemaBreak.
							DISCOUNTTYPE_FlatProductBonuses))
			{
				m_reset = true;
				BigDecimal discountQty = Env.ZERO;
				
				BigDecimal discountValue =
						getNewLineFlatBreakBonus(discountBonus, dsBreak, 
								isQty);
				
				BigDecimal bonusesQty = Env.ZERO;
				
				if (m_justUpdate)
				{
					bonusesQty = discountTrx.getQtyBonuses();						
				}
				else if (discountBonus.getM_Product_ID() > 0)
				{
					bonusesQty = dsBreak.getDiscountType().equals(
							MDiscountSchemaBreak.
							DISCOUNTTYPE_PercentProductBonuses) 
							? dsBreak.getBreakValue().
							multiply(bonusesQty).divide(Env.ONEHUNDRED, 
									m_precission, 
									RoundingMode.HALF_EVEN) 
									: dsBreak.getBreakDiscount();
							
					MProduct product = new MProduct(discountBonus.getCtx(), 
							discountBonus.getM_Product_ID(), 
							discountBonus.getTrxName());
					if(dsBreak.getC_UOM_ID() > 0 && (dsBreak.getC_UOM_ID() 
							!= product.getC_UOM_ID()))
					{
						bonusesQty = product.convertTo(
								dsBreak.getC_UOM_ID(),product.getC_UOM_ID(), 
								bonusesQty);
					}
				}
				
				discountValue = discountValue.add(
						bonusesQty.multiply(
								discountBonus.getPrice()));
				discountQty = discountQty.add(bonusesQty);
				discount = discount.add(discountValue);
				multiply = multiply.add(dsBreak.getBreakValue());
				m_reset = false;
				
				discountBonus.setM_ProductBonus_ID(
						discountBonus.getM_Product_ID());
				discountBonus.setC_UOMBonus_ID(discountBonus.getC_UOM_ID());
				discountBonus.setDiscountBonus(discountBonus.getDiscountBonus()
						.add(discount));
				discountBonus.setQtyBonus(discountBonus.getQtyBonus().
						add(discountQty));

				if (discountQty.signum() == 1 
						&& discountBonus.getM_Product_ID() > 0)
				{
					discountTrx.setQtyBonuses(discountBonus.getQtyBonus());
					discountTrx.setQtyValDiscounted(discountBonus.getQty());
					discountTrx.setBonusesPrice(analyzeBonusesPrice(discountBonus, discountBonus.getM_Product_ID()));
					discountTrx.setDiscountType(MUNSDiscountTrx.
							DISCOUNTTYPE_Bonus);
					discountTrx.setIsNeedRecalculate(false);
					discountTrx.saveEx();
				}
				else if (discountTrx.get_ID() > 0)
				{
					discountTrx.deleteEx(true);
				}
			}
			
			if(checkValue.compareTo(dsBreak.getBreakValue()) < 0 && 
					discountBonus.getTable_ID() == MUNSBonusClaimLine.Table_ID)
			{
				discountBonus.setNeedConfirmation(true);
			}
		}
		// multiple break
		if (!isFlat)
		{
			if (dsBreak.getDiscountType().equals(MDiscountSchemaBreak.
					DISCOUNTTYPE_PercentValueDiscount))
			{
				discountBonus = calculateMultipleBreakPercentDiscount(
						dsBreak, discountBonus, isQty);
			}
			else if (dsBreak.getDiscountType()
					.equals(MDiscountSchemaBreak.DISCOUNTTYPE_FlatValueDiscount)
					|| dsBreak.getDiscountType().equals(MDiscountSchemaBreak.
							DISCOUNTTYPE_MultipleValueDiscount))
			{
				discountBonus = calculateDiscountMultipleValueDiscount(
						dsBreak, discountBonus, isQty);
			}
			else if (dsBreak.getDiscountType().equals(
					MDiscountSchemaBreak.DISCOUNTTYPE_PercentProductBonuses))
			{
				discountBonus = calculateDiscountMultipleBreakProduct(
						dsBreak, discountBonus, isQty);
			}
			else if (dsBreak.getDiscountType().equals(
					MDiscountSchemaBreak.DISCOUNTTYPE_FlatProductBonuses)
					|| MDiscountSchemaBreak.DISCOUNTTYPE_MultipleFlatProductBonuses.equals(dsBreak.getDiscountType()))
			{
				discountBonus = calculateDiscountMultipleBreakProduct(
						dsBreak, discountBonus, isQty);
			}
		}
		return discountBonus.getDiscountBonus();
	}

	/**
	 * 
	 * @param dsBreak
	 * @param precentDiscount
	 * @param priceEntered
	 * @param isQty
	 * @param discount
	 * @return
	 */
	private BigDecimal getDiscountValueFlatPrecentDiscountQtyValue(
			MDiscountSchemaBreak dsBreak, BigDecimal precentDiscount, 
			BigDecimal priceEntered, boolean isQty)
	{
		BigDecimal discountValue = dsBreak.getBreakValue().
				multiply(precentDiscount).divide(Env.ONEHUNDRED, m_precission, 
						RoundingMode.HALF_EVEN);
		return discountValue;
	}

	/**
	 * 
	 * @param dsBreak
	 * @param discountBonus
	 * @param isQty
	 * @return
	 */
	private UNSDiscountBonus calculateMultipleBreakPercentDiscount(
			MDiscountSchemaBreak dsBreak,UNSDiscountBonus discountBonus, 
			boolean isQty)
	{
		MUNSDSBreakLine[] breaklines = dsBreak.getBreakLines(true);
		int n = breaklines.length - 1;
		BigDecimal discountValue = Env.ZERO;
		boolean first = true;

		// back step looping
		while (n >= 0)
		{
			MUNSDSBreakLine breakLine = breaklines[n];
			BigDecimal qtyValDiscounted = isQty ? discountBonus.getConversionQty()
					: discountBonus.getLineNetAmount();
			BigDecimal comparator = isQty ? qtyValDiscounted 
					: qtyValDiscounted.multiply(m_divider)
					.divide(m_multiplicand, m_precission, RoundingMode.HALF_EVEN);
			
			if(comparator.compareTo(breakLine.getBreakValue()) == -1 && 
					! dsBreak.isMix() && !dsBreak.isMixRequired())
			{
				n--;
				continue;
			}
			else if(dsBreak.isMix() || dsBreak.isMixRequired())
			{
				comparator = isQty ? discountBonus.getConversionMixQty() 
						: discountBonus.getLinenetAmtMix()
						.multiply(m_divider).divide(m_multiplicand, m_precission, 
								RoundingMode.HALF_EVEN);
				if(comparator.compareTo(breakLine.getBreakValue()) == -1)
				{
					n--;
					continue;
				}
			}
			
			if (dsBreak.isStrictStrata() && comparator.compareTo(breakLine.getBreakValueTo()) == 1)
				break;
			
			boolean isBudgetAvailable = false;
			
			if (!dsBreak.getBudgetCalculation().equals(MDiscountSchemaBreak.BUDGETCALCULATION_AmountOfDiscount)) {
				if (!dsBreak.isStrataBudget())
					isBudgetAvailable = breakLine.isBudgetAvailable(discountBonus);
				else
					isBudgetAvailable = dsBreak.isBudgetAvailable(discountBonus);
			}
			
			if(!isBudgetAvailable)
			{
				n--;
				continue;
			}
			
			if (comparator.compareTo(breaklines[n].getBreakValueTo()) >= 0)
			{
				discountValue = calculateBreakValueTo(
						breaklines[n], discountBonus, isQty, 
						dsBreak.isOnlyCountMaxRange(), discountValue, 
						n == 0, first);
				first = false;
	
				if(qtyValDiscounted.compareTo(dsBreak.getBreakValue()) < 0 && 
						discountBonus.getTable_ID() == 
						MUNSBonusClaimLine.Table_ID)
				{
					discountBonus.setNeedConfirmation(true);
				}
				
				if (dsBreak.isOnlyCountMaxRange())
				{
					break;
				}
			}
			// only pass 1 times
			else if (comparator.compareTo(breaklines[n].getBreakValue()) >= 0)
			{
				discountValue = calculateBreakValue(
						breaklines[n], discountBonus, isQty, 
						dsBreak.isOnlyCountMaxRange(),discountValue, 
						n == 0, first);
				first = false;
				
				if(comparator.compareTo(dsBreak.getBreakValue()) < 0 && 
						discountBonus.getTable_ID() == 
						MUNSBonusClaimLine.Table_ID)
				{
					discountBonus.setNeedConfirmation(true);
				}
				
				if (dsBreak.isOnlyCountMaxRange() && discountValue.signum() == 1)
				{
					break;
				}
			}
			n--;
		}

		discountBonus.setDiscountBonus(discountValue);

		return discountBonus;

	}

	/*
	 * This Method for calculate Multiple Break -> Percent Discount in case checkValue > BreakValueTo
	 * (First Condition)
	 */
	private BigDecimal calculateBreakValueTo(MUNSDSBreakLine breakLine, 
			UNSDiscountBonus discountBonus, boolean isQty, 
			boolean isOnlyCountMaxRange, BigDecimal discountValue, boolean last, 
			boolean first)
	{
		BigDecimal qtyValue = breakLine.getBreakValueTo();
		if (breakLine.getParent().isMix())
		{
			qtyValue = isQty ? discountBonus.getConversionQty() 
					: discountBonus.getLineNetAmount();
			if (qtyValue.compareTo(breakLine.getBreakValueTo()) == 1)
			{
				qtyValue = breakLine.getBreakValueTo();
			}
		}
		if (!isOnlyCountMaxRange)
		{
			qtyValue = breakLine.getBreakValueTo().
					subtract(breakLine.getBreakValue().subtract(Env.ONE));
		}

		return discountValue.add(calculateMultipleBreakPrecentDiscountValue(
				qtyValue, breakLine, discountBonus, discountBonus.getPrice(), 
				isQty, first));
	}

	/*
	 * This Method for calculate Multiple Break -> Percent Discount in case checkValue > BreakValue
	 * (Second Condition)
	 */
	private BigDecimal calculateBreakValue(MUNSDSBreakLine breakline, 
			UNSDiscountBonus discountBonus, boolean isQty, 
			boolean isOnlyCountMaxRange, BigDecimal discountValue, boolean last,
			boolean first)
	{
		BigDecimal qtyValue = isQty ? discountBonus.getConversionQty()
				: discountBonus.getLineNetAmount();

		if(!isOnlyCountMaxRange)
		{
			qtyValue = qtyValue.subtract(breakline.getBreakValue().
					subtract(Env.ONE));
		}

		return discountValue.add(calculateMultipleBreakPrecentDiscountValue(
				qtyValue, breakline,discountBonus, discountBonus.getPrice(), 
				isQty, first));
	}

	/*
	 * This Method for calculate Multiple Break -> Percent Discount
	 * 
	 * @return DiscountValue
	 */
	private BigDecimal calculateMultipleBreakPrecentDiscountValue(
			BigDecimal valueQty, MUNSDSBreakLine breakLine, 
			UNSDiscountBonus discountBonus, BigDecimal price, boolean isQty,
			boolean first)
	{
		BigDecimal discountValue = Env.ZERO;
		BigDecimal discountTemp = Env.ZERO;
		BigDecimal qtyMultiplyPrice = Env.ZERO;
		if (isQty)
		{
			qtyMultiplyPrice = valueQty.multiply(discountBonus.getPrice());
		}
		else
		{
			qtyMultiplyPrice = valueQty;
		}
		
		MUNSDiscountTrx discountTrx = MUNSDiscountTrx.get(
				discountBonus, breakLine);
		if(null == discountTrx && !m_justUpdate)
		{
			discountTrx = new MUNSDiscountTrx(discountBonus);
			discountTrx.setDiscountTrx(breakLine, null, discountBonus);
			discountTrx.setUNS_DSBreakLine_ID(breakLine.get_ID());
			discountTrx.setName(breakLine.getName());
		}
		if (null == discountTrx)
		{
			return Env.ZERO;
		}
		if (first && !m_justUpdate)
		{
			discountTrx.setZeroValueDiscount();
		}

		BigDecimal percent1 = Env.ZERO;
		BigDecimal percent2 = Env.ZERO;
		BigDecimal percent3 = Env.ZERO;
		BigDecimal percent4 = Env.ZERO;
		BigDecimal percent5 = Env.ZERO;

		if(m_justUpdate)
		{
			percent1 = discountTrx.getFirstDiscount();
			percent2 = discountTrx.getSecondDiscount();
			percent3 = discountTrx.getThirdDiscount();
			percent4 = discountTrx.getFourthDiscount();
			percent5 = discountTrx.getFifthDiscount();
		}
		else
		{
			percent1 = breakLine.getBreakDiscount();
			percent2 = breakLine.getSecondDiscount();
			percent3 = breakLine.getThirdDiscount();
			percent4 = breakLine.getFourthDiscount();
			percent5 = breakLine.getFifthDiscount();
		}
		
		
		if(percent1.compareTo(Env.ZERO) > 0)
		{
			discountTemp = getDiscountValueMultipleQtyValue(
					percent1, isQty, qtyMultiplyPrice);
			discountValue = discountValue.add(discountTemp);
			discountTrx.setDiscountValue1st(discountTemp);
			discountTrx.setFirstDiscount(percent1);
		}
		
		if (percent2.compareTo(Env.ZERO) > 0)
		{
			discountTemp =
					getDiscountValueMultipleQtyValue(percent2, isQty,
							qtyMultiplyPrice.subtract(discountValue));
			discountValue = discountValue.add(discountTemp);
			discountTrx.setDiscountValue2nd(discountTemp);
			discountTrx.setSecondDiscount(percent2);
		}
		if (percent3.compareTo(Env.ZERO) > 0)
		{
			discountTemp =
					getDiscountValueMultipleQtyValue(percent3, isQty,
							qtyMultiplyPrice.subtract(discountValue));
			discountValue = discountValue.add(discountTemp);
			discountTrx.setDiscountValue3rd(discountTemp);
			discountTrx.setThirdDiscount(percent3);
		}
		if (percent4.compareTo(Env.ZERO) > 0)
		{
			discountTemp =
					getDiscountValueMultipleQtyValue(percent4, isQty,
							qtyMultiplyPrice.subtract(discountValue));
			discountValue = discountValue.add(discountTemp);
			discountTrx.setDiscountValue4th(discountTemp);
			discountTrx.setFourthDiscount(percent4);
		}
		if (percent5.compareTo(Env.ZERO) > 0)
		{
			discountTemp =
					getDiscountValueMultipleQtyValue(percent5, isQty,
							qtyMultiplyPrice.subtract(discountValue));
			discountValue = discountValue.add(discountTemp);
			discountTrx.setDiscountValue5th(discountTemp);
			discountTrx.setFifthDiscount(percent5);
		}
		
		if (!MDiscountSchemaBreak.BUDGETCALCULATION_AmountOfDiscount.equals(breakLine.getParent().getBudgetCalculation())) {
			if (breakLine.getParent().isStrataBudget()) {
				if (!breakLine.isBudgetAvailable(discountBonus)) {
					discountBonus.setDiscountBonus(Env.ZERO);
					if (discountTrx != null)
						discountTrx.deleteEx(true);
					return Env.ZERO;
				}
			} else  {
				if (!breakLine.getParent().isBudgetAvailable(discountBonus)) {
					discountBonus.setDiscountBonus(Env.ZERO);
					if (discountTrx != null)
						discountTrx.deleteEx(true);
					return Env.ZERO;
				}
			}
		}
			
		discountTrx.setDiscountType(MUNSDiscountTrx.DISCOUNTTYPE_Percent);
		discountTrx.setQtyValDiscounted(valueQty);
		discountTrx.setDiscountedAmt(discountBonus.getLineNetAmount());
		discountTrx.setIsNeedRecalculate(false);
		discountTrx.saveEx();

		return discountValue;
	}

	/**
	 * 
	 * @param breakDiscount
	 * @param isQty
	 * @param discount
	 * @return
	 */
	private BigDecimal getDiscountValueMultipleQtyValue(
			BigDecimal breakDiscount, boolean isQty, BigDecimal discount)
	{
		BigDecimal discountValue = discount.multiply(breakDiscount).
				divide(Env.ONEHUNDRED, m_precission, RoundingMode.HALF_EVEN);
		return discountValue;
	}

	/**
	 * 
	 * @param dsBreak
	 * @param discountBonus
	 * @param isQty
	 * @return
	 */
	private UNSDiscountBonus calculateDiscountMultipleValueDiscount(
			MDiscountSchemaBreak dsBreak, UNSDiscountBonus discountBonus, 
			boolean isQty)
	{
		MUNSDSBreakLine[] breaklines = dsBreak.getBreakLines(true);
		int n = breaklines.length - 1;
		BigDecimal discountValue = Env.ZERO;

		// back step looping
		while (n >= 0)
		{
			BigDecimal qtyValDiscounted = isQty ? discountBonus.getConversionQty()
					: discountBonus.getLineNetAmount();
			BigDecimal comparator = isQty ? qtyValDiscounted 
					: qtyValDiscounted.multiply(m_divider)
					.divide(m_multiplicand, m_precission, RoundingMode.HALF_EVEN);
			
			
			if(comparator.compareTo(breaklines[n].getBreakValue()) == -1 && 
					! dsBreak.isMix() && !dsBreak.isMixRequired())
			{
				n--;
				continue;
			}
			else if(dsBreak.isMix() || dsBreak.isMixRequired())
			{
				comparator = isQty ? discountBonus.getConversionMixQty()
						: discountBonus.getLinenetAmtMix().multiply(m_divider)
						.divide(m_multiplicand, m_precission, RoundingMode.HALF_EVEN);
				if(comparator.compareTo(breaklines[n].getBreakValue()) == -1)
				{
					n--;
					continue;
				}
			}

			if (dsBreak.isStrictStrata() && comparator.compareTo(breaklines[n].getBreakValueTo()) == 1)
				break;
			
			boolean isBudgetAvailable = false;
			if (!dsBreak.getBudgetCalculation().equals(MDiscountSchemaBreak.BUDGETCALCULATION_AmountOfDiscount)) {
				if (dsBreak.isStrataBudget()) {
					isBudgetAvailable = breaklines[n].isBudgetAvailable(discountBonus);
				} else {
					isBudgetAvailable = dsBreak.isBudgetAvailable(discountBonus);
				}
			}
			
			if (!isBudgetAvailable)
			{
				n--;
				continue;
			}
			
			MUNSDiscountTrx discountTrx = MUNSDiscountTrx.get(
					discountBonus, breaklines[n]);
			if(null == discountTrx && !m_justUpdate)
			{
				discountTrx = new MUNSDiscountTrx(discountBonus);
				discountTrx.setDiscountTrx(breaklines[n], null, discountBonus);
				discountTrx.setIsActive(true);
				discountTrx.setName(dsBreak.getName());
			}
				
			if(null == discountTrx)
			{
				continue;
			}
			
			BigDecimal discountBreak = Env.ZERO;
			
			if(m_justUpdate)
			{
				discountBreak = discountTrx.getFlatValueDiscount();
			}
			else
			{
				discountBreak = breaklines[n].getBreakDiscount();
				discountBreak = discountBreak.multiply(m_multiplicand)
						.divide(m_divider, m_precission, 
						RoundingMode.HALF_EVEN);
			}
			
			if(dsBreak.getDiscountType().equals(MDiscountSchemaBreak.
					DISCOUNTTYPE_MultipleValueDiscount)
					&&  !m_justUpdate)
			{
				BigDecimal pembagi = breaklines[n].getNofMultiples();
				if(pembagi.signum() == 0)
				{
					throw new AdempiereUserError(
							"Invalid no of multiple on line break " + 
									breaklines[n].getSeqNo());
				}
				
				BigDecimal pengali = qtyValDiscounted.divide(pembagi, 
						m_precission, RoundingMode.HALF_EVEN);
				discountBreak = discountBreak.multiply(pengali);
			}
			boolean allowed = true;
			if (dsBreak.getBudgetCalculation().equals(MDiscountSchemaBreak.BUDGETCALCULATION_AmountOfDiscount)) {
				if (dsBreak.isStrataBudget()) {
					allowed = breaklines[n].isBudgetAvailable(discountBonus);
				} else {
					allowed = dsBreak.isBudgetAvailable(discountBonus);
				}
			}
			if (comparator.compareTo(breaklines[n].getBreakValue()) >= 0 && allowed)
			{
				discountValue = discountValue.add(discountBreak);
				discountTrx.setFlatValueDiscount(discountBreak);
				BigDecimal percent = discountBreak.divide(
						!isQty ? qtyValDiscounted : qtyValDiscounted.multiply(
								discountBonus.getPrice()), m_precission, 
								RoundingMode.HALF_DOWN).multiply(Env.ONEHUNDRED);
				discountTrx.setQtyValDiscounted(qtyValDiscounted);
				discountTrx.setDiscountedAmt(discountBonus.getLineNetAmount());
				discountTrx.setFlatPercentDiscount(percent);
				discountTrx.setDiscountType(MUNSDiscountTrx.DISCOUNTTYPE_Value);
				discountTrx.setIsNeedRecalculate(false);
				discountTrx.saveEx();
				
				if(qtyValDiscounted.compareTo(dsBreak.getBreakValue()) < 0
						&& discountBonus.getTable_ID() == 
						MUNSBonusClaimLine.Table_ID)
				{
					discountBonus.setNeedConfirmation(true);
				}
				
				if (dsBreak.isOnlyCountMaxRange())
				{
					break;
				}
			}
			else if (discountTrx.get_ID() > 0)
			{
				discountTrx.deleteEx(true);
			}
			
			n--;
		}

		discountBonus.setDiscountBonus(discountValue);
		return discountBonus;
	}

	/**
	 * 
	 * @param dsBreak
	 * @param discountBonus
	 * @param isQty
	 * @return
	 */
	private UNSDiscountBonus calculateDiscountMultipleBreakProduct(
			MDiscountSchemaBreak dsBreak, UNSDiscountBonus discountBonus, 
			boolean isQty)
	{
		MUNSDSBreakLine[] breaklines = dsBreak.getBreakLines(true);
		int n = breaklines.length - 1;
		m_reset = true;
		// back step looping
		while (n >= 0)
		{
			BigDecimal qtyValDiscounted = isQty ? discountBonus.getConversionQty()
					: discountBonus.getLineNetAmount();
			BigDecimal comparator = isQty ? qtyValDiscounted 
					: qtyValDiscounted.multiply(m_divider)
					.divide(m_multiplicand, m_precission, 
					RoundingMode.HALF_EVEN);
			
			
			
			if(comparator.compareTo(breaklines[n].getBreakValue()) == -1 && 
					! dsBreak.isMix() && !dsBreak.isMixRequired())
			{
				n--;
				continue;
			}
			else if(dsBreak.isMix() || dsBreak.isMixRequired())
			{
				comparator = isQty ? discountBonus.getConversionMixQty()
						: discountBonus.getLinenetAmtMix()
						.multiply(m_divider)
						.divide(m_multiplicand, m_precission, 
						RoundingMode.HALF_EVEN);
				if(comparator.compareTo(breaklines[n].getBreakValue()) == -1)
				{
					n--;
					continue;
				}
			}
			
			if (dsBreak.isStrictStrata() && comparator.compareTo(breaklines[n].getBreakValueTo()) == 1)
				break;
			
			boolean isBudgetAvailable = false;
			boolean budgetedByDiscount = MDiscountSchemaBreak.BUDGETCALCULATION_AmountOfDiscount.equals(dsBreak.getBudgetCalculation()); 
			if (!budgetedByDiscount) {
				isBudgetAvailable = breaklines[n].isBudgetAvailable(discountBonus);
			} else {
				throw new AdempiereException("Budget Amount of Discount is not implemented for product bonuses.");
			}
			
			if (qtyValDiscounted.compareTo(breaklines[n].getBreakValueTo()) == 1)
				qtyValDiscounted = breaklines[n].getBreakValueTo();
			if (!dsBreak.isOnlyCountMaxRange())
				qtyValDiscounted = qtyValDiscounted.subtract(breaklines[n].getBreakValue()).add(Env.ONE);
			
			MUNSDiscountTrx discountTrx = MUNSDiscountTrx.get(
					discountBonus, breaklines[n]);
			if(null == discountTrx && !m_justUpdate && isBudgetAvailable)
			{
				discountTrx = new MUNSDiscountTrx(discountBonus);
				discountTrx.setDiscountTrx(breaklines[n], null, discountBonus);
				discountTrx.setUNS_DSBreakLine_ID(breaklines[n].get_ID());
				discountTrx.setName(breaklines[n].getName());
			}
			
			if(!isBudgetAvailable)
			{
				if (discountTrx != null)
				{
					discountTrx.deleteEx(true);
				}
				n--;
				continue;
			}
			

			BigDecimal discountValue = Env.ZERO;
			BigDecimal qtyBonus = Env.ZERO;
			
			if (comparator.compareTo(breaklines[n].getBreakValue()) >= 0)
			{
				if(m_justUpdate)
				{
					qtyBonus = discountTrx.getQtyBonuses();
				}
				else if (discountBonus.getM_Product_ID() > 0)
				{
					qtyBonus = breaklines[n].getBreakDiscount();
					MProduct product = new MProduct(discountBonus.getCtx(), 
							discountBonus.getM_Product_ID(),
							discountBonus.getTrxName());
					if(dsBreak.getC_UOM_ID() > 0 && 
							(product.getC_UOM_ID() != dsBreak.getC_UOM_ID()))
					{
						qtyBonus = product.convertTo(dsBreak.getC_UOM_ID(), 
								product.getC_UOM_ID(), qtyBonus);
					}
					
					if(dsBreak.getDiscountType().equals(MDiscountSchemaBreak.
							DISCOUNTTYPE_MultipleFlatProductBonuses))
					{
						BigDecimal pembagi = breaklines[n].getNofMultiples();
						if(pembagi.signum() == 0)
						{
							throw new AdempiereUserError(
									"Invalid no of multiple on line break " + 
											breaklines[n].getSeqNo());
						}
						
						BigDecimal pengali = qtyValDiscounted.divide(pembagi, 
								0, RoundingMode.DOWN);
						qtyBonus = qtyBonus.multiply(pengali);
					}
				}
				discountValue = getNewLineMultipleBonus(discountBonus, 
						breaklines[n], false, qtyValDiscounted);	

				if (dsBreak.getDiscountType().equals(MDiscountSchemaBreak.
						DISCOUNTTYPE_PercentProductBonuses) && !m_justUpdate)
				{
					qtyBonus = qtyValDiscounted.multiply(qtyBonus).
							divide(Env.ONEHUNDRED, m_precission, 
									RoundingMode.HALF_EVEN);
				}
				
				discountValue = discountValue.add(qtyBonus.
						multiply(discountBonus.getPrice()));
			}
			else
			{
				discountTrx.deleteEx(true);
				n--;
				continue;
			}
			
			discountBonus.setQtyBonus(discountBonus.getQtyBonus().
					add(qtyBonus));
			discountBonus.setM_ProductBonus_ID(
					discountBonus.getM_Product_ID());
			discountBonus.setC_UOMBonus_ID(discountBonus.getC_UOM_ID());
			discountBonus.setDiscountBonus(discountBonus.
					getDiscountBonus().add(discountValue));
			if (qtyBonus.signum() > 0)
			{
				discountTrx.setQtyValDiscounted(qtyValDiscounted);
				discountTrx.setDiscountedAmt(discountBonus.getLineNetAmount());
				discountTrx.setQtyBonuses(qtyBonus);
				discountTrx.setProductBonus_ID(discountBonus.getM_Product_ID());
				discountTrx.setBonusesPrice(analyzeBonusesPrice(discountBonus, discountBonus.getM_Product_ID()));
				discountTrx.setIsNeedRecalculate(false);
				discountTrx.saveEx();
			}
			else
				discountTrx.deleteEx(true);
				
			
			m_reset = false;
		
			if (dsBreak.isOnlyCountMaxRange())
			{
				break;
			}
			
			n--;
		}

		return discountBonus;

	}

	private BigDecimal getNewLineMultipleBonus(UNSDiscountBonus discountBonus,
			MUNSDSBreakLine breakLine, boolean isPrecent, BigDecimal breakValue)
	{
		BigDecimal sum = Env.ZERO;
		if (discountBonus.getTable_ID() == MOrderLine.Table_ID
				|| discountBonus.getTable_ID() == MOrder.Table_ID
				|| discountBonus.getTable_ID() == MInvoiceLine.Table_ID
				|| discountBonus.getTable_ID() == MInvoice.Table_ID
				)
		{
			I_DiscountModelLine line = null;
			BigDecimal noOfMultiples = breakLine.getNofMultiples();
			BigDecimal pengali = Env.ONE;
			if (noOfMultiples.signum() > 0)
				pengali = breakValue.divide(noOfMultiples, 0, RoundingMode.DOWN);
			
			MUNSDiscountBonus[] bonuses = MUNSDiscountBonus.getBonus(breakLine);
			for (MUNSDiscountBonus bonus : bonuses)
			{				
				MUNSDiscountTrx discountTrx = MUNSDiscountTrx.getCreate(
						discountBonus, breakLine, bonus);
				
				BigDecimal bonusesQty = Env.ZERO;
				if(m_justUpdate)
				{
					bonusesQty = discountTrx.getQtyBonuses();
				}
				else
				{
					bonusesQty = bonus.getBreakDiscount();
					MProduct product = new MProduct(discountBonus.getCtx(), 
							bonus.getM_Product_ID(), 
							discountBonus.getTrxName());
					if(bonus.getC_UOM_ID() != product.getC_UOM_ID())
					{
						bonusesQty = product.convertTo(bonus.getC_UOM_ID(), 
								product.getC_UOM_ID(), bonusesQty);
					}
					
					bonusesQty = bonusesQty.multiply(pengali);
					bonusesQty = bonusesQty.setScale(m_precission, RoundingMode.DOWN);
				}
				
				line = setOrderLineBonus(discountBonus.getRecord_ID(), 
						bonus.getM_Product_ID(), bonusesQty, 
						breakLine.getParent().getParent().
						isCumulativeDocument(), breakLine.getParent().isDiscountedBonus());
				
				discountTrx.setProductBonus_ID(line.getProduct().get_ID());
				discountTrx.setQtyBonuses(bonusesQty);
				discountTrx.setQtyValDiscounted(breakValue);
				discountTrx.setDiscountedAmt(breakValue.multiply(discountBonus.getPrice()));
				discountTrx.setDiscountType(MUNSDiscountTrx.DISCOUNTTYPE_Bonus);
				discountTrx.setBonusesPrice(analyzeBonusesPrice(discountBonus, line.getProduct().get_ID()));
				discountTrx.setIsNeedRecalculate(false);
				discountTrx.saveEx();
				
				
				if(line.isProductBonuses())
				{
					if(!breakLine.getParent().isDiscountedBonus())
					{
						line.setPrice();
						sum = sum.add(line.getPriceEntered().
								multiply(bonusesQty));
					}
					else
					{
//						line.setPriceList(Env.ZERO);
//						line.setPriceLimit(Env.ZERO);
//						line.setPrice(Env.ZERO);
						line.setPrice();
						line.setPriceEntered(Env.ZERO);
						line.setPriceActual(Env.ZERO);
						line.setDiscount(Env.ONEHUNDRED);
					}
				}
				else
				{
					sum = sum.add(line.getPriceEntered().
							multiply(bonusesQty));
				}
				
				line.getModel().saveEx();

			}
		}
		else if (discountBonus.getTable_ID() == MUNSBonusClaimLine.Table_ID)
		{
			MUNSBonusClaimLine line = null;
			MUNSDiscountBonus[] bonuses = MUNSDiscountBonus.getBonus(breakLine);
			int prevRecord_ID = discountBonus.getRecord_ID();
			for (MUNSDiscountBonus bonus : bonuses)
			{
				BigDecimal bonusesQty = bonus.getBreakDiscount();
				MProduct product = new MProduct(discountBonus.getCtx(), 
						bonus.getM_Product_ID(), discountBonus.getTrxName());
				if(bonus.getC_UOM_ID() != product.getC_UOM_ID())
				{
					bonusesQty = product.convertTo(bonus.getC_UOM_ID(), 
							product.getC_UOM_ID(), bonusesQty);
				}
				line = setBonusClaimLine(discountBonus, bonus.getM_Product_ID(), 
						bonusesQty);
				discountBonus.setRecord_ID(line.get_ID());
				
				MUNSDiscountTrx discountTrx = MUNSDiscountTrx.get(
						discountBonus, breakLine);
				if(null == discountTrx)
				{
					discountTrx = new MUNSDiscountTrx(discountBonus);
					discountTrx.setDiscountTrx(null, null, discountBonus);
					discountTrx.setUNS_DSBreakLine_ID(breakLine.get_ID());
					discountTrx.setUNS_BonusClaimLine_ID(line.get_ID());
					discountTrx.setName(breakLine.getName());
					discountTrx.setIsNeedRecalculate(false);
					discountTrx.saveEx();
					
				}
				if(!breakLine.getParent().isDiscountedBonus())
				{
					line.setAmtClaimed(line.getAmtClaimed().add(
							line.getQtyClaimed().multiply(
									discountBonus.getPriceBonus())));
				}
			}
			discountBonus.setRecord_ID(prevRecord_ID);
		}
		return sum;
	}

	private BigDecimal getNewLineFlatBreakBonus(UNSDiscountBonus discountBonus,
			MDiscountSchemaBreak dsBreak, boolean isQty)
	{
		BigDecimal sum = Env.ZERO;
		if (discountBonus.getTable_ID() == MOrderLine.Table_ID
				|| discountBonus.getTable_ID() == MOrder.Table_ID
				|| discountBonus.getTable_ID() == MInvoice.Table_ID
				|| discountBonus.getTable_ID() == MInvoiceLine.Table_ID)
		{
			I_DiscountModelLine line = null;
			MUNSDiscountBonus[] bonuses = MUNSDiscountBonus.getBonus(dsBreak);
			for (MUNSDiscountBonus bonus : bonuses)
			{
				MUNSDiscountTrx discountTrx = MUNSDiscountTrx.getCreate(
						discountBonus, dsBreak, bonus);
				BigDecimal bonusesQty = Env.ZERO;
				if(m_justUpdate)
				{
					bonusesQty = discountTrx.getQtyBonuses();
				}
				else
				{
					bonusesQty = bonus.getBreakDiscount();
					BigDecimal multiplicand = discountBonus.getConversionQty();
					if (dsBreak.getNofMultiples().signum() == 1 && 
							multiplicand.compareTo(dsBreak.getNofMultiples()) == 1) {
						multiplicand = dsBreak.getNofMultiples();
					}
					
					multiplicand = multiplicand.divide(dsBreak.getBreakValue(), 
							0, RoundingMode.DOWN);
					bonusesQty = bonusesQty.multiply(multiplicand);
					MProduct product = new MProduct(discountBonus.getCtx(), 
							bonus.getM_Product_ID(), 
							discountBonus.getTrxName());
					if(bonus.getC_UOM_ID() != product.getC_UOM_ID())
					{
						bonusesQty = product.convertTo(bonus.getC_UOM_ID(), 
								product.getC_UOM_ID(), bonusesQty);
					}
				}

				line = setOrderLineBonus(discountBonus.getRecord_ID(), 
						bonus.getM_Product_ID(), bonusesQty, 
						dsBreak.getParent().isCumulativeDocument(), dsBreak.isDiscountedBonus());
				if (m_reset)
				{
					discountTrx.setQtyBonuses(Env.ZERO);
				}
				discountTrx.setProductBonus_ID(line.getProduct().get_ID());
				discountTrx.setQtyBonuses(discountTrx.getQtyBonuses().
						add(bonusesQty));
				BigDecimal qtyValDiscounted = isQty ? discountBonus.getQty() : discountBonus.getLineNetAmount();
				discountTrx.setQtyValDiscounted(qtyValDiscounted);
				discountTrx.setDiscountedAmt(discountBonus.getLineNetAmount());
				discountTrx.setBonusesPrice(analyzeBonusesPrice(discountBonus, line.getProduct().get_ID()));
				discountTrx.setDiscountType(MUNSDiscountTrx.
						DISCOUNTTYPE_Bonus);
				discountTrx.setIsNeedRecalculate(false);
				discountTrx.saveEx();
				
				if(line.isProductBonuses())
				{
					if(!dsBreak.isDiscountedBonus())
					{
						line.setPrice();
						sum = sum.add(line.getPriceEntered().
								multiply(bonusesQty));
					}
					else
					{
//						line.setPriceList(Env.ZERO);
//						line.setPriceLimit(Env.ZERO);
//						line.setPrice(Env.ZERO);
						line.setPrice();
						line.setPriceEntered(Env.ZERO);
						line.setPriceActual(Env.ZERO);
						line.setDiscount(Env.ONEHUNDRED);
					}
				}
				else
				{
					sum = sum.add(line.getPriceEntered().
							multiply(bonusesQty));
				}

				line.getModel().saveEx();
			}
		}
		else if (discountBonus.getTable_ID() == MUNSBonusClaimLine.Table_ID)
		{
			MUNSBonusClaimLine line = null;
			MUNSDiscountBonus[] bonuses = MUNSDiscountBonus.getBonus(dsBreak);
			for (MUNSDiscountBonus bonus : bonuses)
			{
				BigDecimal bonusesQty = bonus.getBreakDiscount();
				MProduct product = new MProduct(discountBonus.getCtx(), 
						bonus.getM_Product_ID(), discountBonus.getTrxName());
				if(bonus.getC_UOM_ID() != product.getC_UOM_ID())
				{
					bonusesQty = product.convertTo(bonus.getC_UOM_ID(), 
							product.getC_UOM_ID(), bonusesQty);
				}
				line = setBonusClaimLine(discountBonus, 
						bonus.getM_Product_ID(), bonusesQty);
				MUNSDiscountTrx discountTrx = MUNSDiscountTrx.get(
						discountBonus, dsBreak);
				if(null == discountTrx)
				{
					discountTrx = new MUNSDiscountTrx(discountBonus);
					discountTrx.setDiscountTrx(null, null, discountBonus);
					discountTrx.setM_DiscountSchemaBreak_ID(dsBreak.get_ID());
					discountTrx.setUNS_BonusClaimLine_ID(line.get_ID());
					discountTrx.setName(dsBreak.getName());
					discountTrx.setIsNeedRecalculate(false);
					discountTrx.saveEx();
					
				}
				if(!dsBreak.isDiscountedBonus())
				{
					line.setAmtClaimed(line.getAmtClaimed().add(
							line.getQtyClaimed().multiply(discountBonus.
									getPriceBonus())));
				}
			}
		}

		return sum;
	}

	/**
	 * 
	 * @param discountBonus
	 * @param schema
	 * @param isQty
	 * @return
	 */
	private BigDecimal getNewLineFlatBonus(UNSDiscountBonus discountBonus, 
			MDiscountSchema schema, boolean isQty)
	{
		BigDecimal sum = Env.ZERO;
		if (discountBonus.getTable_ID() == MOrderLine.Table_ID
				|| discountBonus.getTable_ID() == MOrder.Table_ID
				|| discountBonus.getTable_ID() == MInvoiceLine.Table_ID
				|| discountBonus.getTable_ID() == MInvoice.Table_ID)
		{
			I_DiscountModelLine line = null;
			MUNSDiscountBonus[] bonuses = MUNSDiscountBonus.getBonus(schema);
			for (MUNSDiscountBonus bonus : bonuses)
			{
				MUNSDiscountTrx discountTrx = MUNSDiscountTrx.getCreate(
						discountBonus, schema, bonus);
				
				BigDecimal bonusesQty = Env.ZERO;
				
				if(m_justUpdate)
				{
					bonusesQty = discountTrx.getQtyBonuses();
				}
				else
				{
					bonusesQty = bonus.getBreakDiscount();
					MProduct product = new MProduct(discountBonus.getCtx(), 
							bonus.getM_Product_ID(), 
							discountBonus.getTrxName());
					if(bonus.getC_UOM_ID() != product.getC_UOM_ID())
					{
						bonusesQty = product.convertTo(bonus.getC_UOM_ID(), 
								product.getC_UOM_ID(), bonusesQty);
					}
				}
				line = setOrderLineBonus(discountBonus.getRecord_ID(), 
						bonus.getM_Product_ID(), bonusesQty, 
						schema.isCumulativeDocument(), true);
				discountBonus.setQtyBonus(discountBonus.getQtyBonus().
						add(bonusesQty));
				discountTrx.setProductBonus_ID(line.getProduct().get_ID());
				discountTrx.setQtyBonuses(discountBonus.getQtyBonus());
				BigDecimal qtyValDiscounted = isQty ? discountBonus.getQty() : discountBonus.getLineNetAmount();
				discountTrx.setQtyValDiscounted(qtyValDiscounted);
				discountTrx.setDiscountedAmt(discountBonus.getLineNetAmount());
				discountTrx.setBonusesPrice(analyzeBonusesPrice(discountBonus, line.getProduct().get_ID()));
				discountTrx.setDiscountType(MUNSDiscountTrx.DISCOUNTTYPE_Bonus);
				discountTrx.setIsNeedRecalculate(false);
				discountTrx.saveEx();

				if(line.isProductBonuses())
				{
					line.setPriceList(Env.ZERO);
					line.setPrice(Env.ZERO);
					line.setPriceLimit(Env.ZERO);
					line.setPriceCost(Env.ZERO);
				}
				else
				{
					line.setPrice();
					sum = sum.add(line.getPriceEntered().multiply(bonusesQty));
				}

				line.getModel().saveEx();
			}
		}
		else if (discountBonus.getTable_ID() == MUNSBonusClaimLine.Table_ID)
		{
			MUNSBonusClaimLine line = null;
			MUNSDiscountBonus[] bonuses = MUNSDiscountBonus.getBonus(schema);
			for (MUNSDiscountBonus bonus : bonuses)
			{
				BigDecimal bonusesQty = bonus.getBreakDiscount();
				MProduct product = new MProduct(discountBonus.getCtx(), 
						bonus.getM_Product_ID(), discountBonus.getTrxName());
				if(bonus.getC_UOM_ID() != product.getC_UOM_ID())
				{
					bonusesQty = product.convertTo(bonus.getC_UOM_ID(), 
							product.getC_UOM_ID(), bonusesQty);
				}

				discountBonus.setQtyBonus(discountBonus.getQtyBonus().
						add(bonusesQty));
				line = setBonusClaimLine(discountBonus, bonus.getM_Product_ID(), 
						bonusesQty);
				MUNSDiscountTrx discountTrx = MUNSDiscountTrx.get(
						discountBonus, schema);
				if(null == discountTrx)
				{
					discountTrx = new MUNSDiscountTrx(discountBonus);
					discountTrx.setDiscountTrx(null, null, discountBonus);
					discountTrx.setM_DiscountSchema_ID(schema.get_ID());
					discountTrx.setUNS_BonusClaimLine_ID(line.get_ID());
					discountTrx.setName(schema.getName());
					discountTrx.setIsNeedRecalculate(false);
					discountTrx.saveEx();
				}
			}
		}
		return sum;
	}


	/**
	 * 
	 * @param discountBonus
	 * @param ProductBonus_ID
	 * @param calculateQty
	 * @return
	 */
	private MUNSBonusClaimLine setBonusClaimLine(UNSDiscountBonus discountBonus, 
			int ProductBonus_ID, BigDecimal calculateQty)
	{
		String SQL =
				"SELECT UNS_BonusClaimLine_ID FROM UNS_BonusClaimLine " +
						" WHERE M_Product_ID=? AND ProductBonus_ID=?";
		int record = DB.getSQLValue(trxName, SQL, 
				discountBonus.getM_Product_ID(), ProductBonus_ID);
		
		if(record < 0)
		{
			record = 0;
		}

		MUNSBonusClaimLine line = new MUNSBonusClaimLine(
				Env.getCtx(), record, trxName);
		line.setAD_Org_ID(discountBonus.getOrgTrx_ID());
		line.setUNS_BonusClaim_ID(Env.getContextAsInt(
				Env.getCtx(), "UNS_BonusClaim_ID"));
		line.setDataValue(discountBonus);
		String sql = "SELECT C_Tax_ID FROM C_Tax WHERE name LIKE 'Standard'";
		int C_Tax_ID = DB.getSQLValue(trxName, sql);
		line.setC_Tax_ID(C_Tax_ID);
		line.setProductBonus_ID(ProductBonus_ID);
		line.setUOMBonus_ID(line.getProductBonus().getC_UOM_ID());
		line.setQtyClaimed(line.getQtyClaimed().add(calculateQty));
		line.saveEx();
		
		return line;
	}

	/**
	 * 
	 * @param record_ID
	 * @param pBonus_ID
	 * @param bonus
	 * @param isCumulativeDocument
	 * @return
	 */
	private I_DiscountModelLine setOrderLineBonus(int record_ID, int pBonus_ID, 
			BigDecimal bonus, boolean isCumulativeDocument, boolean isDiscountedToBonueses)
	{
		I_DiscountModelLine line = m_model.getByProduct(pBonus_ID, 
				isCumulativeDocument ? -1 : record_ID, isDiscountedToBonueses);
		
		if(isCumulativeDocument)
		{
			if(!line.isProductBonuses())
			{
				if (m_reset)
				{
					BigDecimal tmpQty = tmpQtyBonus.get(pBonus_ID);
					if (null != tmpQty)
						bonus = bonus.add(tmpQty);
					tmpQtyBonus.put(pBonus_ID, bonus);
					line.setQtyBonuses(bonus);
				}
				else
				{
					line.setQtyBonuses(line.getQtyBonuses().add(bonus));
				}
			}
			else
			{
				if (m_reset)
				{
					BigDecimal tmpQty = tmpQtyBonus.get(pBonus_ID);
					if (null != tmpQty)
						bonus = bonus.add(tmpQty);
					tmpQtyBonus.put(pBonus_ID, bonus);
					
					line.setQty(bonus);
				}
				else
				{
					line.setQty(line.getQtyEntered().add(bonus));
				}
				line.setIsProductBonus(true);
			}
		}
		else
		{
			if (line.getModel().get_ID() == record_ID)
			{
				if (m_reset)
				{					
					line.setQtyBonuses(bonus);
				}
				else
				{
					line.setQtyBonuses(line.getQtyBonuses().add(bonus));
				}
			}
			else
			{
				if (m_reset)
				{
					line.setQty(bonus);
				}
				else
				{
					line.setQty(line.getQtyEntered().add(bonus));
				}
				line.setIsProductBonus(true);
				line.setRefLine_ID(record_ID);
				line.setQtyBonuses(Env.ZERO);
			}
		}
		return line;
	}
	
	
	/**
	 * check applied discount
	 * remove from list discount if not applied
	 */
	public void checkAppliedDiscount()
	{
		ArrayList<MDiscountSchema> afters = new ArrayList<>();
		ArrayList<MDiscountSchema> befores = new ArrayList<>();
		ArrayList<MDiscountSchema> doncit = new ArrayList<>();
		for(int i=0; i<m_AllDiscountSchema.size(); i++)
		{
			System.out.println("validate discount schema " 
					+ m_AllDiscountSchema.get(i).toString());
			if((m_AllDiscountSchema.get(i).isPickup() && !m_model.isPickup())
					&& (m_AllDiscountSchema.get(i).isCashPayment() 
							&& !m_model.isCash()))
			{
				System.out.println("Removed from list, discount schema " 
						+ m_AllDiscountSchema.get(i).toString());
				m_AllDiscountSchema.remove(i);
				i--;
				continue;
			}
			
			if(m_AllDiscountSchema.get(i).isOrgBased())
			{
				if (m_AllDiscountSchema.get(i).getCumulativeLevel().
						equals(MDiscountSchema.CUMULATIVELEVEL_Document)) 
				{
					doncit.add(m_AllDiscountSchema.get(i));
					m_AllDiscountSchema.remove(i);
					i--;
					continue;
				}
			}
			else
			{
				if(m_AllDiscountSchema.get(i).getorganizationaleffectiveness().
						equals(MDiscountSchema.
				ORGANIZATIONALEFFECTIVENESS_OrganizationSchemaWillBeEditeAfter))
				{
					afters.add(m_AllDiscountSchema.get(i));
				}
				else if (m_AllDiscountSchema.get(i).
						getorganizationaleffectiveness().equals(MDiscountSchema.
			ORGANIZATIONALEFFECTIVENESS_OrganizationSchemaWillBeEditeBefore))
				{
					befores.add(m_AllDiscountSchema.get(i));
				}
				else if (m_AllDiscountSchema.get(i).
						getorganizationaleffectiveness().equals(MDiscountSchema.
				ORGANIZATIONALEFFECTIVENESS_OverwrittenWithOrganizationSchema))
				{
					doncit.add(m_AllDiscountSchema.get(i));
				}
				
				m_AllDiscountSchema.remove(i);
				i--;
			}
		} 
		
		if(befores.size() > 0)
		{
			m_AllDiscountSchema.addAll(befores);
		}
		if(afters.size() > 0)
		{
			afters.addAll(m_AllDiscountSchema);
			m_AllDiscountSchema = afters;
		}
		if (doncit.size() > 0)
		{
			m_AllDiscountSchema.addAll(doncit);
		}
	}
	
	/**
	 * 
	 * @param ctx
	 */
	public void setCtx(Properties ctx)
	{
		this.ctx = ctx;
	}
	
	/**
	 * 
	 * @param trxname
	 */
	public void setTrxName(String trxname)
	{
		this.trxName = trxname;
	}
	
	/**
	 * 
	 * @param dsBreak
	 * @return
	 */
	public String calculateMix(MDiscountSchemaBreak dsBreak)
	{
		BigDecimal totalQtyMix = Env.ZERO;
		BigDecimal totalAmtMix = Env.ZERO;
		List<I_DiscountModelLine> lines = m_model.getLines(false);
		BigDecimal conversionRate = Env.ZERO;
		for(int i=0; i<lines.size(); i++)
		{
			I_DiscountModelLine line = lines.get(i);
			if (!line.getModel().isActive())
				continue;
			if(line.isProductBonuses())
				continue;
			if (line.getProduct() == null)
				continue;
			UNTPair<Integer, Integer> pair = dsBreak.getSelectedProductUOM(line.getProduct().get_ID());
			if (pair == null)
				continue;
			
			BigDecimal qty = line.getQtyEntered();
			conversionRate = line.getProduct().getConvertionRate(
					line.getProduct().getC_UOM_ID(), pair.getY());
			qty = qty.multiply(conversionRate);
			totalQtyMix = totalQtyMix.add(qty);
			totalAmtMix = totalAmtMix.add(line.getLineNetAmt());
		}
		
		if(totalAmtMix.signum() ==1 && totalQtyMix.signum() == 1)
		{
			if(dsBreak.getParent().getCumulativeLevel().equals(
					MDiscountSchema.CUMULATIVELEVEL_Document))
			{
				UNSDiscountBonus discountBonus = new UNSDiscountBonus(
						ctx, m_model.getModel().get_Table_ID(), 
						m_model.getModel().get_ID(), trxName);

				BigDecimal price = totalAmtMix.divide(
						totalQtyMix, m_precission, RoundingMode.HALF_EVEN);
				discountBonus.setDataValue(0, 0,
						totalQtyMix, price, totalAmtMix, false);
				discountBonus.setConversionRate(conversionRate);
				discountBonus.setConversionMixQty(totalQtyMix);
				discountBonus.setConversionQty(totalQtyMix);
				discountBonus.setLineNetAmtMix(totalAmtMix);
				discountBonus.setOrgTrx_ID(m_model.getModel().getAD_Org_ID());

				discountBonus = checkByDiscountType(dsBreak, discountBonus, 
						dsBreak.getBreakType().equals(MDiscountSchemaBreak.
								BREAKTYPE_FlatDiscount), 
						dsBreak.getCalculationType().equals(
								MDiscountSchemaBreak.CALCULATIONTYPE_Qty));

				m_model.setDiscountAmt(m_model.getDiscountAmt().
						add(discountBonus.getDiscountBonus()));
				m_model.getModel().saveEx();
			}
			else if(dsBreak.getParent().getCumulativeLevel().equals(
					MDiscountSchema.CUMULATIVELEVEL_Line))
			{
				for (int i=0; i<lines.size(); i++)
				{
					I_DiscountModelLine line = lines.get(i);
					if (!line.getModel().isActive())
						continue;
					if(line.isProductBonuses())
					{
						continue;
					}
					if(!dsBreak.isInMySelectionProducts(
							line.getProduct().get_ID()))
					{
						continue;
					}
					
					BigDecimal comparator = line.getQtyEntered();
					BigDecimal price = line.getPriceActual();
					
					comparator = comparator.multiply(conversionRate);
					price = line.getLineNetAmt().divide(comparator, 
							m_precission, RoundingMode.HALF_DOWN);

					UNSDiscountBonus discountBonus = new UNSDiscountBonus(
							ctx, line.getModel().get_Table_ID(), 
							line.getModel().get_ID(), trxName);

					discountBonus.setDataValue(line.getProduct().get_ID(), 
							line.getProduct().getC_UOM_ID(),
							comparator, price, line.getLineNetAmt(), false);
					discountBonus.setOrgTrx_ID(line.getModel().getAD_Org_ID());
					discountBonus.setConversionRate(conversionRate);
					discountBonus.setConversionMixQty(totalQtyMix);
					discountBonus.setConversionQty(comparator);
					discountBonus.setLineNetAmtMix(totalAmtMix);			
				
					discountBonus = checkByDiscountType(dsBreak, discountBonus, 
							dsBreak.getBreakType().equals(MDiscountSchemaBreak.
									BREAKTYPE_FlatDiscount),
							dsBreak.getCalculationType().equals(
									MDiscountSchemaBreak.CALCULATIONTYPE_Qty));

					line.setQtyBonuses(line.getQtyBonuses().add(
							discountBonus.getQtyBonus()));
					line.setQtyMerge(line.getQtyMerge().add(
							line.getQtyBonuses()));
					line.setDiscountAmt(line.getDiscountAmt().add(
							discountBonus.getDiscountBonus()));
					BigDecimal qty = line.getQtyEntered().add(
							line.getQtyBonuses());
					BigDecimal actualPrice = line.getPriceList();
					
					if(qty != null && qty.signum() == 1)
					{
						actualPrice = line.getPriceList().subtract((
								line.getDiscountAmt()).divide(qty, 
								line.getPrecision(), RoundingMode.HALF_UP));
					}
					
					line.setPrice(actualPrice);
					line.getModel().saveEx();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param dsBreak
	 * @return
	 */
	public String calculateMixRequirement(MDiscountSchemaBreak dsBreak)
	{
		Hashtable<Integer, BigDecimal> mapSyaratQty = new Hashtable<>();
		BigDecimal totalQtyMix = Env.ZERO;
		BigDecimal totalAmtMix = Env.ZERO;
		MUNSDiscountProduct[] products = dsBreak.getSelectionProducts(false);
		List<I_DiscountModelLine> lines = m_model.getLines(false);
		int uom_ID = 0;
		BigDecimal conversionRate = Env.ONE;
		
		for(int i=0; i<products.length; i++)
		{
			if(dsBreak.getProductSelection().equals(MDiscountSchemaBreak.
					PRODUCTSELECTION_IncludedSelectedProduct) ||
					dsBreak.getProductSelection().equals(MDiscountSchemaBreak.
							PRODUCTSELECTION_IncludedSelectedProductByVendor))
			{
				mapSyaratQty.put(products[i].getM_Product_ID(), 
						products[i].getQtyRequiered());
			}
			else if(dsBreak.getProductSelection().equals(MDiscountSchemaBreak.
					PRODUCTSELECTION_IncludedSelectedProductCategory))
			{
				mapSyaratQty.put(products[i].getM_Product_Category_ID(), 
						products[i].getQtyRequiered());
			}
			else
			{
				System.out.println("Not Falid Discount Configuration");
			}
		}
		
		for(int i=0; i<lines.size(); i++)
		{
			I_DiscountModelLine line = lines.get(i);
			if (!lines.get(i).getModel().isActive())
				continue;
			if(line.isProductBonuses())
			{
				continue;
			}
			MProduct product = line.getProduct();
			UNTPair<Integer, Integer> pair = dsBreak.getSelectedProductUOM(product.get_ID());
			if(pair == null)
			{
				continue;
			}
			
			BigDecimal qtyComparator = line.getQtyEntered();
			if (conversionRate.signum() == 0)
			{
				conversionRate = line.getProduct().getConvertionRate(
						line.getProduct().getC_UOM_ID(), pair.getY());
			}
			qtyComparator = qtyComparator.multiply(conversionRate);
			
			if (uom_ID == 0)
			{
				uom_ID = line.getProduct().getC_UOM_ID();
			}
			
			if(dsBreak.getProductSelection().equals(MDiscountSchemaBreak.
					PRODUCTSELECTION_IncludedSelectedProduct))
			{
				BigDecimal sisa = mapSyaratQty.get(product.get_ID());
				if(null == sisa)
				{
					continue;
				}
				sisa = sisa.subtract(qtyComparator);
				mapSyaratQty.put(product.getM_Product_ID(), sisa);
				totalQtyMix = totalQtyMix.add(qtyComparator);
				totalAmtMix = totalAmtMix.add(line.getLineNetAmt());
			}
			else if(dsBreak.getProductSelection().equals(MDiscountSchemaBreak.
					PRODUCTSELECTION_IncludedSelectedProductCategory))
			{
				BigDecimal sisa = mapSyaratQty.get(
						product.getM_Product_Category_ID());
				if(null == sisa)
				{
					continue;
				}
				sisa = sisa.subtract(qtyComparator);
				mapSyaratQty.put(product.getM_Product_Category_ID(), sisa);
				totalQtyMix = totalQtyMix.add(qtyComparator);
				totalAmtMix = totalAmtMix.add(line.getLineNetAmt());
			}
		}
		
		for(BigDecimal sisa : mapSyaratQty.values())
		{
			if(sisa.signum() > 0)
			{
				return "Can't meet requirement";
			}
		}
		
		if(totalAmtMix.signum() == 1 && totalQtyMix.signum() == 1)
		{
			if(dsBreak.getParent().getCumulativeLevel().equals(
					MDiscountSchema.CUMULATIVELEVEL_Document))
			{
				UNSDiscountBonus discountBonus = new UNSDiscountBonus(
						ctx, m_model.getModel().get_Table_ID(), 
						m_model.getModel().get_ID(),trxName);
				
				BigDecimal price = totalAmtMix.divide(totalQtyMix, m_precission, 
						RoundingMode.HALF_EVEN);
				
				discountBonus.setDataValue(0, uom_ID,
						Env.ZERO, price, totalAmtMix, false);
				discountBonus.setConversionRate(conversionRate);
				discountBonus.setConversionMixQty(totalQtyMix);
				discountBonus.setLineNetAmtMix(totalAmtMix);
				
				discountBonus.setOrgTrx_ID(m_model.getModel().getAD_Org_ID());

				discountBonus =
						checkByDiscountType(dsBreak, discountBonus,
								dsBreak.getBreakType().equals(
										MDiscountSchemaBreak.
										BREAKTYPE_FlatDiscount),
								dsBreak.getCalculationType().equals(
										MDiscountSchemaBreak.
										CALCULATIONTYPE_Qty));

				m_model.setDiscountAmt(m_model.getDiscountAmt().
						add(discountBonus.getDiscountBonus()));
				m_model.getModel().saveEx();
			}
			else if(dsBreak.getParent().getCumulativeLevel().equals(
					MDiscountSchema.CUMULATIVELEVEL_Line))
			{
				for (I_DiscountModelLine line : m_model.getLines(false))
				{
					if(line.isProductBonuses())
					{
						continue;
					}
					
					if(!dsBreak.isInMySelectionProducts(line.getProduct().
							getM_Product_ID()))
					{
						continue;
					}
					
					BigDecimal comparator = line.getQtyEntered();
					BigDecimal price = line.getPriceActual();
					
					comparator = comparator.multiply(conversionRate);
					price = line.getLineNetAmt().divide(comparator, 
							m_precission, RoundingMode.HALF_DOWN);
				
					
					UNSDiscountBonus discountBonus = new UNSDiscountBonus(
							ctx, line.getModel().get_Table_ID(), 
							line.getModel().get_ID(),trxName);

					discountBonus.setDataValue(line.getProduct().get_ID(), 
							dsBreak.getC_UOM_ID(), comparator, price, 
							line.getLineNetAmt(), false);

					discountBonus.setConversionRate(conversionRate);
					discountBonus.setOrgTrx_ID(line.getModel().getAD_Org_ID());
					discountBonus.setConversionQty(comparator);
					discountBonus.setConversionMixQty(totalQtyMix);
					discountBonus.setLineNetAmtMix(totalAmtMix);				
				
					discountBonus = checkByDiscountType(dsBreak, discountBonus,
							dsBreak.getBreakType().equals(MDiscountSchemaBreak.
									BREAKTYPE_FlatDiscount),
							dsBreak.getCalculationType().equals(
									MDiscountSchemaBreak.CALCULATIONTYPE_Qty));

					line.setQtyBonuses(line.getQtyBonuses().
							add(discountBonus.getQtyBonus()));
					line.setQtyMerge(line.getQtyMerge().
							add(line.getQtyBonuses()));
					line.setDiscountAmt(line.getDiscountAmt().
							add(discountBonus.getDiscountBonus()));
					BigDecimal qty = line.getQtyEntered().
							add(line.getQtyBonuses());
					BigDecimal actualPrice = line.getPriceList();
					
					if (qty != null && qty.signum() == 1)
					{
						actualPrice =  line.getPriceList().subtract((
								line.getDiscountAmt()).divide(qty, line.
										getPrecision(), RoundingMode.HALF_UP));
					}
							
					line.setPrice(actualPrice);
					line.getModel().saveEx();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param dsBreak
	 */
	private void calculateBreak(MDiscountSchemaBreak dsBreak)
	{
		if(dsBreak.getParent().isCumulativeDocument())
		{					
			UNSDiscountBonus discountBonus = new UNSDiscountBonus(
					ctx, m_model.getModel().get_Table_ID(), m_model.
					getModel().get_ID(),trxName);

			discountBonus.setDataValue(0, dsBreak.getC_UOM_ID(), Env.ONE, 
					m_model.getTotalLines(), 
					m_model.getTotalLines(), false);
			discountBonus.setOrgTrx_ID(m_model.getModel().getAD_Org_ID());

			discountBonus = checkByDiscountType(dsBreak, discountBonus, 
					dsBreak.getBreakType().equals(MDiscountSchemaBreak.
							BREAKTYPE_FlatDiscount),
					dsBreak.getCalculationType().equals(MDiscountSchemaBreak.
							CALCULATIONTYPE_Qty));

			m_model.setDiscountAmt(m_model.getDiscountAmt().
					add(discountBonus.getDiscountBonus()));
			m_model.getModel().saveEx();
		}
		else
		{
			List<I_DiscountModelLine> lines = m_model.getLines(false);
			for (int i=0; i<lines.size(); i++)
			{
				I_DiscountModelLine line = lines.get(i);
				MProduct product = line.getProduct();
				if (!lines.get(i).getModel().isActive())
					continue;
				if(!dsBreak.isInMySelectionProducts(product.get_ID()))
				{
					continue;
				}
				if(line.isProductBonuses())
				{
					continue;
				}
				
				BigDecimal price = line.getPriceActual();
				BigDecimal comparator = line.getQtyEntered();
				BigDecimal conversionRate = line.getProduct().getConvertionRate(
						line.getProduct().getC_UOM_ID(), dsBreak.getC_UOM_ID());
				
				comparator = comparator.multiply(conversionRate);
				if(comparator.compareTo(Env.ZERO) == 0)
					price = Env.ZERO;
				else
				price = line.getLineNetAmt().divide(comparator, 
						m_precission, RoundingMode.HALF_DOWN);

				
				UNSDiscountBonus discountBonus = new UNSDiscountBonus(
						ctx, line.getModel().get_Table_ID(), 
						line.getModel().get_ID(), trxName);

				discountBonus.setDataValue(line.getProduct().get_ID(), 
						dsBreak.getC_UOM_ID(), line.getQtyEntered(), price, 
						line.getLineNetAmt(), false);
				discountBonus.setOrgTrx_ID(line.getModel().getAD_Org_ID());
				discountBonus.setConversionRate(conversionRate);
				discountBonus = checkByDiscountType(dsBreak, discountBonus,
						dsBreak.getBreakType().equals(MDiscountSchemaBreak.
								BREAKTYPE_FlatDiscount),
						dsBreak.getCalculationType().equals(
								MDiscountSchemaBreak.CALCULATIONTYPE_Qty));

				line.setQtyBonuses(line.getQtyBonuses().add(
						discountBonus.getQtyBonus()));
				line.setQtyMerge(line.getQtyEntered().add(line.getQtyBonuses()));
				line.setDiscountAmt(line.getDiscountAmt().add(
						discountBonus.getDiscountBonus()));
				
				BigDecimal qty = line.getQtyEntered().add(line.getQtyBonuses());
				BigDecimal actualPrice = line.getPriceList();
				
				if(qty != null && qty.signum() == 1)
				{
					actualPrice = line.getPriceList().subtract(
									(line.getDiscountAmt()).divide(qty, 
									line.getPrecision(), RoundingMode.HALF_UP));
				}
				
				line.setPrice(actualPrice);
				line.getModel().saveEx();
			}
		}
	}
	
	private void calculateFlat(MDiscountSchema discountSchema)
	{
		if(discountSchema.isBirthdayDiscount() && ! m_model.isBirthday())
		{
			return;
		}
		
		if(discountSchema.isCumulativeDocument())
		{
			UNSDiscountBonus discountBonus = new UNSDiscountBonus(
					ctx, MOrder.Table_ID, m_model.getModel().get_ID(), trxName);
			discountBonus.setOrgTrx_ID(m_model.getModel().getAD_Org_ID());
			discountBonus.setDataValue(0, 0, Env.ZERO, Env.ZERO, 
					m_model.getTotalLines(), false);
			
			MUNSDiscountTrx discountTrx = MUNSDiscountTrx.get(
					discountBonus, discountSchema);
			if(null == discountTrx && !discountSchema.getFlatDiscountType().
					equals(MDiscountSchema.FLATDISCOUNTTYPE_ProductBonuses))
			{
				discountTrx = new MUNSDiscountTrx(discountBonus);
				discountTrx.setM_DiscountSchema_ID(discountSchema.get_ID());
				discountTrx.setDiscountTrx(null, null, discountBonus);
				discountTrx.setName(discountSchema.getName());
			}
			
			if(discountSchema.isFlatDiscountTypePercent())
			{
				BigDecimal discountValue = setDiscountPercentByShema(
						discountBonus, discountSchema, m_model.getDiscountAmt(), 
						discountTrx);
				BigDecimal newDiscountAmt = m_model.getDiscountAmt().
						add(discountValue);
				m_model.setDiscountAmt(newDiscountAmt.setScale(
						m_precission, RoundingMode.HALF_EVEN));
				m_model.getModel().saveEx();
			}
			
			else if(discountSchema.isFlatDiscountTypeValueCurrency())
			{
				if(!discountSchema.isBudgetAvailable(discountBonus))
				{
					if (discountTrx != null)
						discountTrx.deleteEx(true);
					return;
				}
				BigDecimal budget = discountBonus.getLineNetAmount();
				BigDecimal discountVal = Env.ZERO;
				if(m_justUpdate)
				{
					discountVal = discountTrx.getFlatValueDiscount();
				}
				else
				{
					discountVal = discountSchema.getFlatDiscount();
					discountVal = discountVal.multiply(m_multiplicand)
							.divide(m_divider, m_precission, 
									RoundingMode.HALF_EVEN);
				} 
				discountTrx.setFlatValueDiscount(discountVal);
				BigDecimal percent = discountVal.divide(
						m_model.getTotalLines(), m_precission, 
						RoundingMode.HALF_DOWN).
						multiply(Env.ONEHUNDRED);
				discountTrx.setDiscountType(MUNSDiscountTrx.DISCOUNTTYPE_Value);
				discountTrx.setFlatPercentDiscount(percent);
				discountTrx.setQtyValDiscounted(budget);
				discountTrx.setDiscountedAmt(budget);
				discountTrx.setIsNeedRecalculate(false);
				discountTrx.saveEx();
				
				BigDecimal tempDiscount = m_model.getDiscountAmt().
						add(discountVal);
				m_model.setDiscountAmt(tempDiscount);
				m_model.getModel().saveEx();
			}
			else
			{
				if(!discountSchema.isBudgetAvailable(discountBonus))
				{
					if (discountTrx != null)
						discountTrx.deleteEx(true);
					return;
				}
				BigDecimal discountValue = getNewLineFlatBonus(
						discountBonus, discountSchema, false);
				BigDecimal tempDiscount = m_model.getDiscountAmt().
						add(discountValue);
				m_model.setDiscountAmt(tempDiscount);
				m_model.getModel().saveEx();
			}
		}
		else
		{
			List<I_DiscountModelLine> lines = m_model.getLines(false);
			for (int i=0; i<lines.size(); i++)
			{
				I_DiscountModelLine line = lines.get(i);
				if (line.isProductBonuses())
				{
					continue;
				}
				
				UNSDiscountBonus discountBonus = new UNSDiscountBonus(
						ctx, MOrderLine.Table_ID, line.getModel().get_ID(), 
						trxName);
				discountBonus.setOrgTrx_ID(line.getModel().getAD_Org_ID());
				discountBonus.setDataValue(line.getProduct().get_ID(), 
						line.getProduct().getC_UOM_ID(), line.getQtyEntered(), 
						line.getPriceEntered(), line.getLineNetAmt(), false);

				MUNSDiscountTrx discountTrx = MUNSDiscountTrx.get(
						discountBonus, discountSchema);
				if(null == discountTrx && !discountSchema.getFlatDiscountType().
						equals(MDiscountSchema.FLATDISCOUNTTYPE_ProductBonuses))
				{
					discountTrx = new MUNSDiscountTrx(discountBonus);
					discountTrx.setM_DiscountSchema_ID(discountSchema.get_ID());
					discountTrx.setDiscountTrx(null, null, discountBonus);
					discountTrx.setName(discountSchema.getName());
				}
				
				if (discountSchema.getFlatDiscountType().equals(
						MDiscountSchema.FLATDISCOUNTTYPE_Percent))
				{
					BigDecimal discountValue = setDiscountPercentByShema(
							discountBonus, discountSchema, Env.ZERO, 
							discountTrx);						
					line.setDiscountAmt(line.getDiscountAmt().
							add(discountValue));
				}
				else if (discountSchema.getFlatDiscountType().equals(
						MDiscountSchema.FLATDISCOUNTTYPE_ValueCurrency))
				{
					if(discountSchema.isBudgetAvailable(discountBonus))
					{
						if (discountTrx != null)
							discountTrx.deleteEx(true);
						continue;
					}
					
					BigDecimal discountValue = Env.ZERO;
					if(m_justUpdate)
					{
						discountValue = discountTrx.getFlatValueDiscount();
					}
					else
					{
						discountValue = discountSchema.getFlatDiscount();
						discountValue = discountValue.multiply(m_multiplicand)
								.divide(m_divider, m_precission, 
										RoundingMode.HALF_EVEN);
					}
					discountTrx.setFlatValueDiscount(discountValue);
					
					BigDecimal percent = discountValue.divide(
							line.getLineNetAmt(), m_precission, 
							RoundingMode.HALF_DOWN).multiply(Env.ONEHUNDRED);
					
					discountTrx.setDiscountType(MUNSDiscountTrx.
							DISCOUNTTYPE_Value);
					discountTrx.setFlatPercentDiscount(percent);
					discountTrx.setQtyValDiscounted(discountBonus.getLineNetAmount());
					discountTrx.save();
					line.setDiscountAmt(line.getDiscountAmt().
							add(discountSchema.getFlatDiscount()));
				}
				else
				{
					if(!discountSchema.isBudgetAvailable(discountBonus))
					{
						if (discountTrx != null)
							discountTrx.deleteEx(true);
						return;
					}
					BigDecimal discountVaule = getNewLineFlatBonus(
							discountBonus, discountSchema, false);
					line.setDiscountAmt(line.getDiscountAmt().
							add(discountVaule));
					
				}

				BigDecimal qty = line.getQtyEntered().add(line.getQtyBonuses());
				BigDecimal actualPrice = line.getPriceList();
				
				if (qty != null && qty.signum() == 1)
				{
					actualPrice = line.getPriceList().subtract(
							line.getDiscountAmt().divide(line.getQtyEntered(), 
									line.getPrecision(), RoundingMode.HALF_UP));
				}
				
				line.setPrice(actualPrice);
				line.getModel().saveEx();
			}
		}
	}
	
	private BigDecimal analyzeBonusesPrice(UNSDiscountBonus discBonus, int M_Product_ID)
	{
		BigDecimal price = Env.ZERO;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CASE WHEN IsProductBonuses = 'Y' THEN PriceList ELSE PriceActual END FROM ");
		String column = null;
		
		if(discBonus.getTable_ID() == MOrder.Table_ID
				|| discBonus.getTable_ID() == MInvoice.Table_ID)
		{
			column = discBonus.getTable_ID() == MOrder.Table_ID ? "C_Order" : "C_Invoice";
			sql.append(column).append("Line").append(" WHERE ").append(column).append("_ID=").append(discBonus.getRecord_ID())
			.append(" AND ").append("M_Product_ID=").append(M_Product_ID);
			
			price = DB.getSQLValueBD(discBonus.getTrxName(), sql.toString());
			
//			if(price == null || price.compareTo(Env.ZERO) == 0)
//			{
//				String getPriceList = "SELECT M_PriceList_ID FROM " + column
//						+ " WHERE " + column + "_ID=?";
//				int priceList = DB.getSQLValue(discBonus.getTrxName(), getPriceList, discBonus.getRecord_ID());
//				if(priceList > 0)
//				{
//					
//					String getPriceStd = "SELECT pp.PriceStd FROM M_ProductPrice pp WHERE pp.M_Product_ID = ?"
//							+ " AND EXISTS (SELECT 1 FROM M_PriceList_Version pv WHERE pv.M_PriceList_Version_ID"
//							+ " = pp.M_PriceList_Version_ID AND ValidFrom >=  ";
//				}
//			}
		}
		else if(discBonus.getTable_ID() == MOrderLine.Table_ID
				|| discBonus.getTable_ID() == MInvoiceLine.Table_ID)
		{
			column = discBonus.getTable_ID() == MOrderLine.Table_ID ? "C_Order" : "C_Invoice";
			sql.append(column).append("Line").append(" WHERE ").append(column).append("_ID=").append("(SELECT ").append(column)
			.append("_ID").append(" FROM ").append(column).append("Line").append(" WHERE ").append(column).append("Line_ID=")
			.append(discBonus.getRecord_ID()).append(") AND ").append("M_Product_ID=").append(M_Product_ID).append(" ORDER BY")
			.append(" IsProductBonuses DESC");
			price = DB.getSQLValueBD(discBonus.getTrxName(), sql.toString());
		}
		
		if(price == null)
			price = Env.ZERO;
		
		return price;
	}
}
