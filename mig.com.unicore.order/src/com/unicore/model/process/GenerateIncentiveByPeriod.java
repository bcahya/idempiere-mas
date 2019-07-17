/**
 * 
 */
package com.unicore.model.process;

import java.util.List;

import org.compiere.model.MAcctSchema;
import org.compiere.process.SvrProcess;

import com.unicore.model.MUNSAcvIncentiveByPeriod;
import com.unicore.model.MUNSIncentiveSchema;

/**
 * @author root
 *
 */
public class GenerateIncentiveByPeriod extends SvrProcess {
	
	private MUNSAcvIncentiveByPeriod m_model = null;
	public List<MUNSIncentiveSchema> m_listIncentiveSchema = null;
	public MAcctSchema m_AcctSchema = null;
	public String m_SQlCost = "SELECT COALESCE(CurrentCostPrice, 0) FROM M_CostDetail WHERE M_AttributeSetInstance_ID =? AND C_AcctSchema_ID=?";

	/**
	 * 
	 */
	public GenerateIncentiveByPeriod() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{	
		m_model = new MUNSAcvIncentiveByPeriod(getCtx(), getRecord_ID(), get_TrxName());
		m_listIncentiveSchema = MUNSIncentiveSchema.get(
				m_model.getCtx(), m_model.getParent().getC_Year_ID()
				, m_model.getParent().getC_BPartner_ID(), get_TrxName());
		MAcctSchema[] schemas = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), get_TrxName());
		m_AcctSchema = schemas[0];
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
//		if(null == m_model)
//			return null;
//		if(null == m_listIncentiveSchema || m_listIncentiveSchema.size() <= 0)
//			return null;
//		
//		for(MUNSIncentiveSchema incentiveSchema : m_listIncentiveSchema)
//		{
//			calculateBillingincentive(incentiveSchema);
//			calculateSalesIncentive(incentiveSchema);
//		}
		return null;
	}
	
//	private void calculateBillingincentive(MUNSIncentiveSchema incentiveSchema)
//	{
//		List<MPaymentAllocate> listAllocate = getListAllocation();
//		for(MPaymentAllocate allocate : listAllocate)
//		{
//			if(allocate.getC_Invoice_ID() <= 0)
//				continue;
//
//			I_C_Invoice invoice = allocate.getC_Invoice();
//			I_C_Payment payment = allocate.getC_Payment();
//			I_C_BPartner bp = allocate.getC_Invoice().getC_BPartner();
//			
//			boolean isNewOutlet = allocate.getC_Invoice().getDateInvoiced().equals(bp.getFirstSale());
//			int UNS_Outlet_Type_ID = bp.getUNS_Outlet_Type_ID();
//			int UNS_Outlet_Grade_ID = bp.getUNS_Outlet_Grade_ID();
//			int top = TimeUtil.getDaysBetween(invoice.getDateInvoiced(), payment.getDateTrx());
//			BigDecimal incentive = Env.ZERO;
//			
//			incentive = incentiveSchema.getBillingIncentive(
//					UNS_Outlet_Grade_ID, UNS_Outlet_Type_ID, top, allocate.getAmount(), isNewOutlet);
//			
//			MUNSAchievedIncentiveLine acvIncentiveLine = new MUNSAchievedIncentiveLine(m_model);
//			acvIncentiveLine.setC_PaymentAllocate_ID(allocate.get_ID());
//			acvIncentiveLine.setAmount(incentive);
//			acvIncentiveLine.saveEx();
//		}
//	}
//	
//	/**
//	 * 
//	 * @param incentiveSchema
//	 */
//	private void calculateSalesIncentive(MUNSIncentiveSchema incentiveSchema)
//	{
//		List<MInvoice> listInvoice = getListInvoice();
//		for(MInvoice invoice : listInvoice)
//		{
//			I_C_BPartner outlet = invoice.getC_BPartner();
//			Timestamp firstSale = outlet.getFirstSale();
//			MInvoiceLine[] lines = invoice.getLines();
//			boolean isReturn = invoice.getC_DocTypeTarget().getDocBaseType().equals(MDocType.DOCBASETYPE_ARCreditMemo);
//			boolean isNewOutLet = invoice.getDateInvoiced().equals(firstSale);
//			
//			if(isReturn)
//			{
//				int C_ARInvoice_ID = getAR_Invoice_ID(invoice.getM_RMA_ID());
//				Timestamp dateAR = DB.getSQLValueTS(get_TrxName(), "SELECT DateInvoiced FROM C_Invoice WHERE C_Invoice_ID = ?", C_ARInvoice_ID);
//				isNewOutLet = dateAR.equals(firstSale);
//				
//				deducBillingIncentive(
//						incentiveSchema, outlet.getUNS_Outlet_Grade_ID(), outlet.getUNS_Outlet_Type_ID()
//						, C_ARInvoice_ID, invoice.getGrandTotal(), isNewOutLet);
//			}
//			
//			for(MInvoiceLine line : lines)
//			{
//				BigDecimal cost = DB.getSQLValueBD(get_TrxName(), m_SQlCost, line.getM_AttributeSetInstance_ID(), m_AcctSchema.get_ID());
//				cost = cost.subtract(cost.multiply(line.getDiscount().divide(Env.ONEHUNDRED, line.getPrecision(), RoundingMode.HALF_DOWN)));
//				cost = cost.multiply(line.getQtyEntered());
//				cost = line.getLineNetAmt().subtract(cost);
//				BigDecimal incentive = incentiveSchema.getSalesIncentive(line.getM_Product_ID(), cost, isNewOutLet);
//				
//				if(isReturn)
//					incentive = incentive.negate();
//				
//				MUNSAchievedIncentiveLine acvIncentiveLine = new MUNSAchievedIncentiveLine(m_model);
//				acvIncentiveLine.setC_InvoiceLine_ID(line.get_ID());
//				acvIncentiveLine.setAmount(incentive);
//				acvIncentiveLine.saveEx();
//			}
//			
//		}
//	}
//	
//	private void calculateOrderIncentive(MUNSIncentiveSchema incentiveSchema)
//	{
//		List<MOrder> listOrder = getListOrder();
//		for(MOrder order : listOrder)
//		{
//			org.compiere.model.MOrderLine[] lines = order.getLines();
//			for(org.compiere.model.MOrderLine line : lines)
//			{
//				
//			}
//		}
//	}
//	
//	private void deducBillingIncentive(
//			MUNSIncentiveSchema incentiveSchema, int UNS_Outlet_Grade_ID, int UNS_Outlet_Type_ID
//			, int C_Invoice_ID, BigDecimal returnAmt, boolean isNewOutlet)
//	{
//		if(C_Invoice_ID <= 0)
//			return;
//		
//		String sql = "SELECT UNS_AchievedIncentive_Line_ID FROM UNS_AchievedIncentive_Line "
//				+ " WHERE C_PaymentAllocate_ID = (SELECT C_PaymentAllocate_ID FROM C_PaymentAllocate "
//				+ " WHERE C_Invoice_ID = ?)";
//		int UNS_IncentiveLine_ID = DB.getSQLValue(get_TrxName(), sql, C_Invoice_ID);
//		if(UNS_IncentiveLine_ID <= 0)
//			return;
//		
//		Timestamp dateInvoiced = DB.getSQLValueTS(
//				get_TrxName(), "SELECT DateInvoiced FROM C_Invoice WHERE C_Invoice_ID = ?"
//				, C_Invoice_ID);
//		Timestamp dateTrx = DB.getSQLValueTS(
//				get_TrxName(), "SELECT DateTrx FROM C_Payment WHERE C_Payment_ID = "
//						+ "(SELECT C_Payment_ID FROM C_PaymentAllocate WHERE C_PaymentAllocate_ID =  "
//						+ "(SELECT C_PaymentAllocate_ID FROM UNS_AchievedIncentive_Line WHERE UNS_AchievedIncentive_Line_ID = ?))",
//				UNS_IncentiveLine_ID);
//		
//		int top = TimeUtil.getDaysBetween(dateInvoiced, dateTrx);
//		BigDecimal amt = incentiveSchema.getBillingIncentive(UNS_Outlet_Grade_ID, UNS_Outlet_Type_ID, top, returnAmt, isNewOutlet);
//		MUNSAchievedIncentiveLine acvLine = new MUNSAchievedIncentiveLine(getCtx(), UNS_IncentiveLine_ID, get_TrxName());
//		
//		MUNSAchievedIncentiveLine acvIncentiveLine = new MUNSAchievedIncentiveLine(m_model);
//		acvIncentiveLine.setC_PaymentAllocate_ID(acvLine.getC_PaymentAllocate_ID());
//		acvIncentiveLine.setAmount(amt);
//		acvIncentiveLine.saveEx();
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	private List<MInvoice> getListInvoice()
//	{
//		Timestamp startDate = m_model.getStartDate();
//		Timestamp endDate = m_model.getEndDate();
//		String sql = "SELECT * FROM C_Invoice WHERE (DocStatus = ? OR DocStatus = ?) "
//				+ " AND SalesRep_ID IN (SELECT AD_User_ID FROM AD_User WHERE C_BPartner_ID = ?) "
//				+ " AND DateInvoiced BETWEEN ? AND ? "
//				+ " AND NOT EXISTS (SELECT 1 FROM C_InvoiceLine WHERE C_InvoiceLine_ID IN "
//				+ " (SELECT C_InvoiceLine_ID FROM UNS_AchievedIncentive_Line"
//				+ " WHERE UNS_AcvIncentiveByPeriod_ID = ?) AND C_Invoice_ID = C_Invoice.C_Invoice_ID)"
//				+ " Order By DateInvoiced ";
//		List<MInvoice> list = new ArrayList<MInvoice>();
//		
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		
//		try {
//			st = DB.prepareStatement(sql, get_TrxName());
//			st.setString(1,MInvoice.DOCSTATUS_Completed);
//			st.setString(2, MInvoice.DOCSTATUS_Closed);
//			st.setInt(3, m_model.getParent().getC_BPartner_ID());
//			st.setTimestamp(4, startDate);
//			st.setTimestamp(5, endDate);
//			st.setInt(6, m_model.get_ID());
//			
//			rs = st.executeQuery();
//			while (rs.next()) {
//				MInvoice invoice = new MInvoice(getCtx(), rs, get_TrxName());
//				list.add(invoice);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return list;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	private List<MPaymentAllocate> getListAllocation()
//	{
//		Timestamp startDate = m_model.getStartDate();
//		Timestamp endDate = m_model.getEndDate();
//		
//		List<MPaymentAllocate> list = new ArrayList<MPaymentAllocate>();
//		String sql = "SELECT * FROM C_PaymentAllocate pa WHERE pa.C_Payment_ID IN "
//				.concat("(SELECT p.C_Payment_ID FROM C_Payment p WHERE p.SalesRep_ID IN ")
//				.concat("(SELECT u.AD_User_ID FROM AD_User u WHERE u.C_BPartner_ID = ?) ")
//				.concat("AND (p.DocStatus = ? or p.DocStatus =?) AND p.IsReceipt = ? ")
//				.concat("AND p.DateTrx BETWEEN ? AND ? ORDER BY p.datetrx)")
//				.concat(" AND NOT EXISTS (SELECT 1 FROM UNS_AchievedIncentive_Line")
//				.concat(" WHERE UNS_AcvIncentiveByPeriod_ID = ? AND C_PaymentAllocate_ID = pa.C_PaymentAllocate_ID)");
//		
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		
//		try {
//			st = DB.prepareStatement(sql, get_TrxName());
//			st.setInt(1, m_model.getParent().getC_BPartner_ID());
//			st.setString(2, MPayment.DOCSTATUS_Completed);
//			st.setString(3, MPayment.DOCSTATUS_Closed);
//			st.setString(4, "Y");
//			st.setTimestamp(5, startDate);
//			st.setTimestamp(6, endDate);
//			st.setInt(9, m_model.get_ID());
//			
//			rs = st.executeQuery();
//			while (rs.next()) {
//				MPaymentAllocate allocate = new MPaymentAllocate(getCtx(), rs, get_TrxName());
//				list.add(allocate);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
//	
//	/**
//	 * 
//	 * @param M_RMA_ID
//	 * @return
//	 */
//	private int getAR_Invoice_ID(int M_RMA_ID)
//	{
//		StringBuilder sql = new StringBuilder("SELECT i.").append(MInvoice.COLUMNNAME_C_Invoice_ID)
//				.append(" FROM ").append(MInvoice.Table_Name).append(" i WHERE i.")
//				.append(MInvoice.COLUMNNAME_C_Order_ID).append("=(SELECT io.")
//				.append(MInOut.COLUMNNAME_C_Order_ID).append(" FROM ")
//				.append(MInOut.Table_Name).append(" io WHERE io.").append(MInOut.COLUMNNAME_M_InOut_ID)
//				.append("= (SELECT Inout_ID FROM M_RMA WHERE M_RMA_ID = ").append(M_RMA_ID).append("))");
//		
//		return DB.getSQLValue(get_TrxName(), sql.toString());
//	}
//	
//	private List<MOrder> getListOrder()
//	{
//		Timestamp startDate = m_model.getStartDate();
//		Timestamp endDate = m_model.getEndDate();
//		
//		String sql = "SELECT * FROM C_Order WHERE (DocStatus = ? OR DocStatus = ?) "
//				+ " AND SalesRep_ID IN (SELECT AD_User_ID FROM AD_User WHERE C_BPartner_ID = ?) "
//				+ " AND DateOrdered BETWEEN ? AND ? "
//				+ " AND NOT EXISTS (SELECT 1 FROM C_OrderLine WHERE C_OrderLine_ID IN "
//				+ " (SELECT C_OrderLine_ID FROM UNS_AchievedIncentive_Line"
//				+ " WHERE UNS_AcvIncentiveByPeriod_ID = ?) AND C_Order_ID = C_Order.C_Order_ID)"
//				+ " Order By DateOrdered ";
//		List<MOrder> list = new ArrayList<MOrder>();
//		
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		
//		try {
//			st = DB.prepareStatement(sql, get_TrxName());
//			st.setString(1,MOrder.DOCSTATUS_Completed);
//			st.setString(2, MOrder.DOCSTATUS_Closed);
//			st.setInt(3, m_model.getParent().getC_BPartner_ID());
//			st.setTimestamp(4, startDate);
//			st.setTimestamp(5, endDate);
//			st.setInt(6, m_model.get_ID());
//			
//			rs = st.executeQuery();
//			while (rs.next()) {
//				MOrder order = new MOrder(getCtx(), rs, get_TrxName());
//				list.add(order);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return list;
//	}
}