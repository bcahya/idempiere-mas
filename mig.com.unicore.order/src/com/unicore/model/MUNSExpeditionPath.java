/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MLocation;
import org.compiere.model.MProductPricing;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;

/**
 * @author ALBURHANY
 *
 */
public class MUNSExpeditionPath extends X_UNS_ExpeditionPath {

	private MLocation m_LocationOrigin;
	private MLocation m_LocationDest;
	private MUNSExpedition m_Exp;
	public BigDecimal costprice = Env.ZERO;
	private int m_UOM;
	private MCurrency m_Currency = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6170794642944888124L;

	/**
	 * @param ctx
	 * @param UNS_ExpeditionPath_ID
	 * @param trxName
	 */
	public MUNSExpeditionPath(Properties ctx, int UNS_ExpeditionPath_ID,
			String trxName) {
		super(ctx, UNS_ExpeditionPath_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSExpeditionPath(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	
	public boolean beforeSave (boolean newRecord)
	{
		m_LocationOrigin = new MLocation(getCtx(), getOrigin_ID(), get_TrxName());
		m_LocationDest = new MLocation(getCtx(), getDestination_ID(), get_TrxName());
		m_Exp = new MUNSExpedition(getCtx(), getUNS_Expedition_ID(), get_TrxName());
		
		MProductPricing pp = new MProductPricing(m_LocationOrigin, m_LocationDest);
		if(m_Exp.isWholeSale())
			pp.setM_PriceList_ID(m_Exp.getM_PriceList_ID());
		else
			pp.setM_PriceList_ID(getM_PriceList_ID());
		pp.setPriceDate(m_Exp.getDateDoc());
		
		if(newRecord || is_ValueChanged(COLUMNNAME_Destination_ID) || is_ValueChanged(COLUMNNAME_Origin_ID))
		{
			if(m_Exp.isWholeSale())
				pp.setM_PriceList_ID(m_Exp.getM_PriceList_ID());
			else
				pp.setM_PriceList_ID(getM_PriceList_ID());
			
//			if(!pp.calculatePrice())
//				throw new AdempiereException(pp.getMessage());
			
			setDistance(pp.getDistance().setScale(pp.getPrecision(), 
					RoundingMode.HALF_UP));
			setPriceActual(pp.getPriceStd().setScale(pp.getPrecision(), 
					RoundingMode.HALF_UP));
			setPriceList(pp.getPriceList().setScale(pp.getPrecision(), 
					RoundingMode.HALF_UP));
			setPriceLimit(pp.getPriceLimit().setScale(pp.getPrecision(), 
					RoundingMode.HALF_UP));
			setTotalAmt(pp.getPriceStd().setScale(pp.getPrecision()));
		}
		
		if(is_ValueChanged(COLUMNNAME_PriceActual))
			setTotalAmt(getPriceActual());
		
		return true;
	}
	
	public boolean afterSave (boolean newRecord, boolean success)
	{
		if(newRecord || is_ValueChanged(COLUMNNAME_TotalAmt) || is_ValueChanged(COLUMNNAME_IsActive))
			updateHeaderAmout();
		if(newRecord || is_ValueChanged(COLUMNNAME_Tonase) || is_ValueChanged(COLUMNNAME_Volume))
			updateHeaderTonVol();
		
		return true;
	}
	
	public boolean afterDelete(boolean success)
	{
		updateHeaderAmout();
		updateHeaderTonVol();
		
		return super.afterDelete(success);
	}
	
	private boolean updateHeaderAmout()
	{		
		String up = "UPDATE UNS_Expedition SET TotalAmt = (SELECT COALESCE(SUM(TotalAmt), 0) FROM UNS_ExpeditionPath WHERE "
			+ " UNS_Expedition_ID = " + getUNS_Expedition_ID() + ") WHERE UNS_Expedition_ID = " + getUNS_Expedition_ID();
		DB.executeUpdate(up, get_TrxName());
		
		return true;
	}
	
	private boolean updateHeaderTonVol()
	{
		int idExp = getUNS_Expedition_ID();
		String up = "UPDATE UNS_Expedition SET Tonase = (SELECT COALESCE(SUM(Tonase),0) FROM UNS_ExpeditionPath"
						+ " WHERE UNS_Expedition_ID = " + idExp + ")"
						+ ", Volume = (SELECT COALESCE(SUM(Volume), 0) FROM UNS_ExpeditionPath"
						+ " WHERE UNS_Expedition_ID = " + idExp + ")"
						+ " WHERE UNS_Expedition_ID = " + idExp;
		DB.executeUpdate(up, get_TrxName());
		
		return true;
	}
	
	/**
	 * 
	 * @param UNS_ExpeditionPath_ID
	 * @return
	 */
	public MUNSExpeditionDetail[] getDetail (int UNS_ExpeditionPath_ID)
	{
		List<MUNSExpeditionDetail> expDetail = new Query(getCtx(), MUNSExpeditionDetail.Table_Name,
				COLUMNNAME_UNS_ExpeditionPath_ID + "=?", get_TrxName()).setParameters(UNS_ExpeditionPath_ID).list();
		
		return expDetail.toArray(new MUNSExpeditionDetail[expDetail.size()]);
	}
	
	public boolean createInvfromPath(Hashtable<Integer, MInvoice> mapInv)
	{
		m_UOM = DB.getSQLValue(get_TrxName(), "SELECT C_UOM_ID FROM C_UOM WHERE Name = 'Each'");
		
		m_Exp = new MUNSExpedition(getCtx(), getUNS_Expedition_ID(), get_TrxName());
		MInvoice inv = mapInv.get(getM_PriceList_ID());
		String invoiceType = null;
		if(null == inv)
		{
			inv = new MInvoice(getCtx(), 0, get_TrxName());
			
			inv.setAD_Org_ID(getAD_Org_ID());
			inv.setDocumentNo("EXP-" + m_Exp.getDocumentNo()+"_"+get_ID());

			String desc = (new StringBuilder("Tonase : ").append(getTonase()
					.setScale(2, RoundingMode.HALF_DOWN)).append(" - Volume : ")
					.append(getVolume().setScale(2, RoundingMode.HALF_DOWN))
					.append(" #").append(getDescription() == null ? "" 
							: getDescription()).append("#")).toString();
			inv.setDescription(desc);
			if(m_Exp.getC_DocType_ID()==1000099)
			{
				invoiceType = MDocType.DOCBASETYPE_ARInvoice;
			}
			else
			{
				invoiceType = MDocType.DOCBASETYPE_APInvoice;
			}
			inv.setC_DocTypeTarget_ID(invoiceType);
			inv.setDateAcct(m_Exp.getDateDoc());
			inv.setDateInvoiced(m_Exp.getDateDoc());
			inv.setC_BPartner_ID(m_Exp.getC_BPartner_ID());
			inv.setC_BPartner_Location_ID(m_Exp.getC_BPartner_Location_ID());
			inv.setM_PriceList_ID(getM_PriceList_ID());
			inv.setC_Currency_ID(m_Exp.getC_Currency_ID());			
			inv.setPaymentRule(m_Exp.getPaymentRule());
			inv.setPaymentRule("Immediate");
			inv.saveEx();
			
			mapInv.put(inv.getM_PriceList_ID(), inv);
		}
		
		setC_Invoice_ID(inv.get_ID());
		saveEx();
		
		for (MUNSExpeditionDetail expDetail : getDetail(get_ID()))
		{
			MInvoiceLine invLine = new MInvoiceLine(inv);
			
			if(expDetail.getC_Charge_ID() > 0)
				invLine.setC_Charge_ID(expDetail.getC_Charge_ID());
			else
				invLine.setM_Product_ID(expDetail.getM_Product_ID());
			invLine.setC_UOM_ID(m_UOM);
			invLine.setQty(Env.ONE);
			invLine.setQtyInvoiced(Env.ONE);
			invLine.setPriceEntered(expDetail.getLineNetAmt());
			invLine.setPriceActual(expDetail.getLineNetAmt());
			invLine.setPriceList(expDetail.getLineNetAmt());
			invLine.setPriceLimit(expDetail.getLineNetAmt());

			String desc = (new StringBuilder("Tonase : ").append(getTonase()
					.setScale(2,RoundingMode.HALF_DOWN)).append(
							" - Volume : ").append(getVolume().setScale(
									2, RoundingMode.HALF_DOWN))
					.append(" #").append(expDetail.getItemDescription() 
							== null ? "" : expDetail.getItemDescription())
							.append("#")).toString();
			invLine.setDescription(desc);
			invLine.saveEx();
		}
		
		return true;
	}
	
	@Override
	public void setTotalAmt(BigDecimal TotalAmt)
	{
		int precision = getCurrency().getStdPrecision();
		BigDecimal roundValue = TotalAmt.setScale(precision, RoundingMode.HALF_UP);
		
		super.setTotalAmt(roundValue);
	}
	
	public MCurrency getCurrency()
	{
		if(m_Currency != null)
			return m_Currency;
		m_Exp = new MUNSExpedition(getCtx(), getUNS_Expedition_ID(), get_TrxName());
		m_Currency = (MCurrency) m_Exp.getCurrency();
		
		return m_Currency;
	}
}
