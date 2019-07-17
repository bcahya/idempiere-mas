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
import org.compiere.model.MOrder;
import org.compiere.model.MProduct;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.util.Env;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.model.I_DiscountModel;
import com.unicore.model.I_DiscountModelLine;

/**
 * @author root
 *
 */
public class UNSInvoiceDiscountModel implements I_DiscountModel {


	private MBPartner m_SalesRep = null;
	private MBPartner m_BPartner = null;
	private MInvoice m_model = null;
	private List<I_DiscountModelLine> m_lines = null;
	private boolean m_isBirthday = false;
	

	/**
	 * 
	 */
	public UNSInvoiceDiscountModel() {
		super();
	}
	
	public UNSInvoiceDiscountModel(PO po)
	{
		setModel(po);
	}
	

	@Override
	public void setModel(PO po) {
		m_model = (MInvoice) po;
		initialRequirements();
	}

	@Override
	public MInvoice getModel() {
		return m_model;
	}

	@Override
	public List<I_DiscountModelLine> getLines(boolean requery) {
		if(null != m_lines && !requery)
			return m_lines;
		m_lines = new ArrayList<>();
		MInvoiceLine[] lines = getModel().getLines();
		
		for(MInvoiceLine line : lines)
		{
			UNSInvoiceLineDiscountModel l = new UNSInvoiceLineDiscountModel(line);
			m_lines.add(l);
		}
		
		return m_lines;
	}
	
	@Override
	public I_DiscountModelLine getByProduct(int M_Product_ID, int refLine_ID, boolean isDiscountedToBonuses) {
		UNSInvoiceLineDiscountModel retVal = null;
		List<I_DiscountModelLine> list = getLines(false);
		for(int i=0; i< list.size(); i++)
		{
			if(list.get(i).getProduct() == null)
				continue;
			
			if(list.get(i).getProduct().get_ID() != M_Product_ID)
				continue;
			
			if(refLine_ID != -1 && list.get(i).getModel().get_ID() != refLine_ID)
				continue;
			if (isDiscountedToBonuses && !list.get(i).isProductBonuses())
				continue;
			
			retVal = (UNSInvoiceLineDiscountModel) list.get(i);
			break;
		}
		
		if(null == retVal)
		{
			MInvoiceLine line = new MInvoiceLine(getModel());
			line.setProduct(MProduct.get(getModel().getCtx(), M_Product_ID));
			line.setisProductBonuses(true);
			line.setQty(Env.ZERO);
			getModel().addLines(line);
			
			retVal = new UNSInvoiceLineDiscountModel(line);
		}
		
		return retVal;
	}

	@Override
	public void setDiscount(BigDecimal discount) {
		getModel().setAddDiscount(discount);
	}

	@Override
	public BigDecimal getDiscount() {
		return getModel().getAddDiscount();
	}

	@Override
	public void setDiscountAmt(BigDecimal discount) {
		getModel().setAddDiscountAmt(discount);
	}

	@Override
	public BigDecimal getDiscountAmt() {
		return getModel().getAddDiscountAmt();
	}

	@Override
	public void setGrandTotal(BigDecimal GrandTotal) {
		getModel().getGrandTotal();
	}

	@Override
	public BigDecimal getGrandTotal() {
		return getModel().getTotalLines();
	}

	@Override
	public void setTotalLines(BigDecimal TotalLines) {
		getModel().setTotalLines(TotalLines);
	}

	@Override
	public BigDecimal getTotalLines() {
		return getModel().getTotalLines();
	}

	@Override
	public void setBpartner(MBPartner Bpartner) {
		m_BPartner = Bpartner;
	}

	@Override
	public MBPartner getBPartner() {
		return m_BPartner;
	}

	@Override
	public void setSalesRep(MBPartner partner) {
		m_SalesRep = partner;
	}

	@Override
	public MBPartner getSalesRep() {
		return m_SalesRep;
	}

	@Override
	public boolean isBirthday() {
		return m_isBirthday;
	}

	@Override
	public void setIsBirthDay(boolean isBirthday) {
		m_isBirthday = isBirthday;
	}

	@Override
	public Timestamp getDateDiscounted() {
		return getModel().getDateInvoiced();
	}

	@Override
	public void setDateDiscounted(Timestamp dateDiscounted) {
		getModel().setDateInvoiced(dateDiscounted);
	}

	@Override
	public boolean isSOTrx() {
		return getModel().isSOTrx();
	}
	
	@Override
	public void initialRequirements()
	{
		MBPartner partner = new MBPartner(getCtx(), getModel().getC_BPartner_ID(), get_TrxName());
		setBpartner(partner);
		MUser user = new MUser(getCtx(), getModel().getSalesRep_ID(), get_TrxName());
		MBPartner salesRep = (MBPartner) user.getC_BPartner();
		setSalesRep(salesRep);
		
		if(getModel().isSOTrx())
		initializeBirthday();
	}
	
	public void initializeBirthday()
	{
		Timestamp datePromised = getModel().getDateInvoiced();
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
	public String get_TrxName() {
		return getModel().get_TrxName();
	}


	@Override
	public Properties getCtx() {
		return getModel().getCtx();
	}

	@Override
	public boolean isPickup() {
		if(getModel().getC_Order_ID() <= 0)
			return false;
		
		return getModel().getC_Order().getDeliveryViaRule().equals(MOrder.DELIVERYVIARULE_Pickup);
	}

	@Override
	public boolean isCash() {
		if(getModel().getC_Order_ID() <= 0)
			return false;
		
		return getModel().getC_Order().getC_DocType().getDocSubTypeSO()
				.equals(MDocType.DOCSUBTYPESO_CashOrder);
	}

}
