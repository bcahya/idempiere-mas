/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MProduct;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.util.Env;

import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;

/**
 * @author root
 *
 */
public class UNSOrderDiscountModel implements I_DiscountModel {
	
	private MOrder m_model = null;
	private List<I_DiscountModelLine> m_lines = null;
	private MBPartner m_BPartner = null;
	private MBPartner m_SalesRep = null;
	private boolean m_isBirthday = false;

	/**
	 * 
	 */
	public UNSOrderDiscountModel() {
		super();
	}
	
	public UNSOrderDiscountModel(PO po)
	{
		setModel(po);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#setModel(org.compiere.model.PO)
	 */
	@Override
	public void setModel(PO po) {
		m_model = (MOrder) po;
		initialRequirements();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getModel()
	 */
	@Override
	public MOrder getModel() {
		return m_model;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getLines(boolean)
	 */
	@Override
	public List<I_DiscountModelLine> getLines(boolean requery) {
		if(null != m_lines && ! requery)
		{
			return m_lines;
		}
		
		MOrderLine[] list = getModel().getLines();
		
		m_lines = new ArrayList<>();
		
		for(MOrderLine line : list)
		{
			UNSOrderLineDiscountModel lineCalcDiscount = new UNSOrderLineDiscountModel(line);
			m_lines.add(lineCalcDiscount);
		}
		
		return m_lines;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getByProduct(int)
	 */
	@Override
	public I_DiscountModelLine getByProduct(int M_Product_ID, int refLine_ID, boolean isDiscountedToBonuses) {
		UNSOrderLineDiscountModel retVal = null;
		List<I_DiscountModelLine> list = getLines(true);
		for(int i=0; i< list.size(); i++)
		{
			if(list.get(i).getProduct() == null)
				continue;
			
			if(list.get(i).getProduct().get_ID() != M_Product_ID)
				continue;
			
			if(refLine_ID != -1 && list.get(i).getRefLine_ID() != refLine_ID)
				continue;
			if(isDiscountedToBonuses && !list.get(i).isProductBonuses())
				continue;
			
			retVal = (UNSOrderLineDiscountModel) list.get(i);
			break;
		}
		
		if(retVal == null)
		{
			MOrderLine line = new MOrderLine(getModel());
			line.setProduct(MProduct.get(getModel().getCtx(), M_Product_ID));
			line.setisProductBonuses(true);
			line.setQty(Env.ZERO);
			getModel().addLines(line);
			retVal = new UNSOrderLineDiscountModel(line);
		}

		retVal.setHeaderInfo(getModel());
		return retVal;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#setDiscount(java.math.BigDecimal)
	 */
	@Override
	public void setDiscount(BigDecimal discount) {
		getModel().setDiscount(discount);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getDiscount()
	 */
	@Override
	public BigDecimal getDiscount() {
		return getModel().getDiscount();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#setDiscountAmt(java.math.BigDecimal)
	 */
	@Override
	public void setDiscountAmt(BigDecimal discount) {
		getModel().setDiscountAmt(discount);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getDiscountAmt()
	 */
	@Override
	public BigDecimal getDiscountAmt() {
		return getModel().getDiscountAmt();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#setGrandTotal(java.math.BigDecimal)
	 */
	@Override
	public void setGrandTotal(BigDecimal GrandTotal) {
		getModel().setGrandTotal(GrandTotal);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getGrandTotal()
	 */
	@Override
	public BigDecimal getGrandTotal() {
		return getModel().getGrandTotal();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#setTotalLines(java.math.BigDecimal)
	 */
	@Override
	public void setTotalLines(BigDecimal TotalLines) {
		getModel().setTotalLines(TotalLines);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getTotalLines()
	 */
	@Override
	public BigDecimal getTotalLines() {
		return getModel().getTotalLines();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#setBpartner(org.compiere.model.MBPartner)
	 */
	@Override
	public void setBpartner(MBPartner Bpartner) {
		m_BPartner = Bpartner;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getBPartner()
	 */
	@Override
	public MBPartner getBPartner() {
		return m_BPartner;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#setSalesRep(org.compiere.model.MBPartner)
	 */
	@Override
	public void setSalesRep(MBPartner partner) {
		 m_SalesRep = partner;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getSalesRep()
	 */
	@Override
	public MBPartner getSalesRep() {
		return m_SalesRep;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#isBirthday()
	 */
	@Override
	public boolean isBirthday() {
		return m_isBirthday;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#setIsBirthDay(boolean)
	 */
	@Override
	public void setIsBirthDay(boolean isBirthday) {
		m_isBirthday = isBirthday;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#get_TrxName()
	 */
	@Override
	public String get_TrxName() {
		return getModel().get_TrxName();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getCtx()
	 */
	@Override
	public Properties getCtx() {
		return getModel().getCtx();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#getDateDiscounted()
	 */
	@Override
	public Timestamp getDateDiscounted() {
		return getModel().getDatePromised();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#setDateDiscounted(java.sql.Timestamp)
	 */
	@Override
	public void setDateDiscounted(Timestamp dateDiscounted) {
		getModel().setDatePromised(dateDiscounted);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModel#isSOTrx()
	 */
	@Override
	public boolean isSOTrx() {
		return getModel().isSOTrx();
	}
	
	@Override
	public void initialRequirements()
	{
		MBPartner partner = new MBPartner(getCtx(), getModel().getC_BPartner_ID(), get_TrxName());
		setBpartner(partner);
		if(getModel().getSalesRep_ID() > 0)
		{
			MUser user = new MUser(getCtx(), getModel().getSalesRep_ID(), get_TrxName());
			MBPartner salesRep = (MBPartner) user.getC_BPartner();
			setSalesRep(salesRep);
		}
		if(getModel().isSOTrx())
		initializeBirthday();
	}
	
	private void initializeBirthday()
	{
		Timestamp datePromised = getModel().getDatePromised();
		Timestamp dateOfBirth = getBPartner().getBirthday();
		
		if(null == dateOfBirth)
		{
			setIsBirthDay(false);
			return;
		}
		
		Calendar calPromised = Calendar.getInstance();
		Calendar calBirthday = Calendar.getInstance();
		
		calPromised.setTimeInMillis(datePromised.getTime());
		calBirthday.setTimeInMillis(dateOfBirth.getTime());
		
		int datePromInt = calPromised.get(Calendar.DATE);
		int dateBirthInt = calBirthday.get(Calendar.DATE);
		int monthPromised = calPromised.get(Calendar.MONTH);
		int monthBirthday = calBirthday.get(Calendar.MONTH);
		
		boolean isSameDate = datePromInt == dateBirthInt;
		boolean isSameMonth = monthPromised == monthBirthday;
		
		if(isSameDate && isSameMonth)
			setIsBirthDay(true);
		else
			setIsBirthDay(false);
	}

	@Override
	public boolean isPickup() {
		return getModel().getDeliveryViaRule().equals(MOrder.DELIVERYVIARULE_Pickup);
	}


	@Override
	public boolean isCash() {
		return getModel().getC_DocTypeTarget().getDocSubTypeSO().equals(MDocType.DOCSUBTYPESO_CashOrder);
	}


}
