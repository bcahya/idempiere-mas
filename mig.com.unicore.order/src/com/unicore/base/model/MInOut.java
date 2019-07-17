/**
 * 
 */
package com.unicore.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.MUNSBonusClaim;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UNTA-Andy
 * 
 */
public class MInOut extends org.compiere.model.MInOut {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6132125295464291402L;

	/**
	 * @param ctx
	 * @param M_InOut_ID
	 * @param trxName
	 */
	public MInOut(Properties ctx, int M_InOut_ID, String trxName) {
		super(ctx, M_InOut_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInOut(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MInOut(MUNSBonusClaim bonusClaim) {
		super(bonusClaim.getCtx(), 0, bonusClaim.get_TrxName());
		setAD_Org_ID(bonusClaim.getAD_Org_ID());
		setAD_OrgTrx_ID(bonusClaim.getAD_Org_ID());
		setC_BPartner_ID(bonusClaim.getC_BPartner_ID());
		setC_BPartner_Location_ID(bonusClaim.getC_BPartner_Location_ID());
		setIsSOTrx(bonusClaim.isSOTrx());
		setC_DocType_ID();
		setDateAcct(bonusClaim.getDateAcct());
		setDateOrdered(bonusClaim.getDateDoc());
		setDateReceived(bonusClaim.getDateDoc());
		setPriorityRule(PRIORITYRULE_Low);
		setMovementType(isSOTrx() ? MOVEMENTTYPE_CustomerShipment
				: MOVEMENTTYPE_VendorReceipts);
		setDeliveryRule(DELIVERYRULE_Availability);
		setDeliveryViaRule(DELIVERYVIARULE_Delivery);
		setDescription("::Auto generate from bonus claim "
				+ bonusClaim.getDocumentNo());
	}

	/**
	 * @param order
	 * @param C_DocTypeShipment_ID
	 * @param movementDate
	 */
	public MInOut(MOrder order, int C_DocTypeShipment_ID, Timestamp movementDate) {
		super(order, C_DocTypeShipment_ID, movementDate);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param invoice
	 * @param C_DocTypeShipment_ID
	 * @param movementDate
	 * @param M_Warehouse_ID
	 */
	public MInOut(MInvoice invoice, int C_DocTypeShipment_ID,
			Timestamp movementDate, int M_Warehouse_ID) {
		super(invoice, C_DocTypeShipment_ID, movementDate, M_Warehouse_ID);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param original
	 * @param C_DocTypeShipment_ID
	 * @param movementDate
	 */
	public MInOut(org.compiere.model.MInOut original, int C_DocTypeShipment_ID,
			Timestamp movementDate) {
		super(original, C_DocTypeShipment_ID, movementDate);
		// TODO Auto-generated constructor stub
	}

	public static MInOut[] getInOutByDate(Properties ctx, Timestamp dateFrom,
			Timestamp dateTo, boolean isSoTrx, String trxName) {
		String dateCheck;
		if (isSoTrx) {
			dateCheck = MInOut.COLUMNNAME_ShipDate;
		} else {
			dateCheck = MInOut.COLUMNNAME_DateReceived;
		}

		StringBuilder whereClauseFinal = new StringBuilder(dateCheck)
				.append(" BETWEEN '").append(dateFrom).append("' AND '")
				.append(dateTo).append("'");

		List<MInOut> list = Query
				.get(ctx, UNSOrderModelFactory.EXTENSION_ID, MInOut.Table_Name,
						whereClauseFinal.toString(), trxName)
				.setOrderBy(dateCheck).list();

		//
		return list.toArray(new MInOut[list.size()]);
	}

	/**
	 * 
	 * @param isDiscountAfterTrx
	 * @return
	 */
	public BigDecimal getAmtOrder(boolean isDiscountAfterTrx) {
		BigDecimal amtOrder = Env.ZERO;
		if (getC_Order_ID() <= 0)
			return amtOrder;

		for (MInOutLine line : getLines(false)) {
			amtOrder = amtOrder.add(line.getAmtOrder(isDiscountAfterTrx));
		}
		return amtOrder;
	}

	private MInOutLine[] m_lines = null;

	public MInOutLine[] getLines(boolean requery) {
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		List<MInOutLine> list = Query
				.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID,
						MInOutLine.Table_Name, "M_InOut_ID=?", get_TrxName())
				.setParameters(getM_InOut_ID())
				.setOrderBy(MInOutLine.COLUMNNAME_Line).list();
		//
		m_lines = new MInOutLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
	} // getMInOutLines

	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	public MInOutLine getByProduct(int M_Product_ID) {
		getLines(false);
		for (int i = 0; i < m_lines.length; i++) {
			if (m_lines[i].getM_Product_ID() == M_Product_ID) {
				return m_lines[i];
			}
		}

		return null;
	}

	/**
	 * 
	 * @param line
	 */
	public void addLines(MInOutLine line) {
		if (null == m_lines) {
			m_lines = new MInOutLine[1];
		} else {
			m_lines = Arrays.copyOf(m_lines, m_lines.length + 1);
		}
		m_lines[m_lines.length - 1] = line;
		set_TrxName(m_lines, get_TrxName());
	}
}
