/**
 * 
 */
package com.unicore.base.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.model.MDocType;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MProject;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTable;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

import com.unicore.model.MUNSBPCanvasser;
import com.uns.base.model.Query;

import com.unicore.model.MUNSBonusClaim;
import com.unicore.model.MUNSCvsLimit;
import com.unicore.model.MUNSDiscountTrx;
import com.unicore.model.MUNSShipping;
import com.unicore.model.SimplePrice;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UNTA-Andy
 * 
 */
public class MOrder extends org.compiere.model.MOrder {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6005360228731964841L;
    private MOrderLine[] m_lines;
    private MUNSBPCanvasser m_canvasserInfo = null;
    private BigDecimal m_tonase = null;
    private BigDecimal m_volume = null;
    private BigDecimal m_cibu	= new BigDecimal(1000);

    /**
     * @param ctx
     * @param C_Order_ID
     * @param trxName
     */
    public MOrder(Properties ctx, int C_Order_ID, String trxName) {
        super(ctx, C_Order_ID, trxName);
        if(getC_BPartner_ID() > 0)
        	m_canvasserInfo = MUNSBPCanvasser.getOf(getC_BPartner_ID(), get_TrxName());
    }

    /**
     * @param project
     * @param IsSOTrx
     * @param DocSubTypeSO
     */
    public MOrder(MProject project, boolean IsSOTrx, String DocSubTypeSO) {
        super(project, IsSOTrx, DocSubTypeSO);
        if(getC_BPartner_ID() > 0)
        	m_canvasserInfo = MUNSBPCanvasser.getOf(getC_BPartner_ID(), get_TrxName());
    }

    /**
     * @param ctx
     * @param rs
     * @param trxName
     */
    public MOrder(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
        // TODO Auto-generated constructor stub
    }

    /**************************************************************************
     * Get Lines of Order
     * 
     * @param whereClause where clause or null (starting with AND)
     * @param orderClause order clause
     * @return lines
     */
    public MOrderLine[] getLines(String whereClause, String orderClause) {
        // red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
        StringBuilder whereClauseFinal = new StringBuilder(MOrderLine.COLUMNNAME_C_Order_ID + "=? ");
        if (!Util.isEmpty(whereClause, true))
            whereClauseFinal.append(whereClause);
        if (orderClause.length() == 0)
            orderClause = MOrderLine.COLUMNNAME_Line;
        //
        List<MOrderLine> list =
                Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MOrderLine.Table_Name,
                        whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID())
                        .setOrderBy(orderClause).list();

        return list.toArray(new MOrderLine[list.size()]);
    } // getLines

    /**
     * Get Lines of Order
     * 
     * @param requery requery
     * @param orderBy optional order by column
     * @return lines
     */
    public MOrderLine[] getLines(boolean requery, String orderBy) {
        if (m_lines != null && !requery)
        {
            set_TrxName(m_lines, get_TrxName());
            return m_lines;
        }
        //
        String orderClause = "";
        if (orderBy != null && orderBy.length() > 0)
            orderClause += orderBy;
        else
            orderClause += "Line";
        m_lines = getLines(null, orderClause);
        return m_lines;
    } // getLines

    /**
     * Get Lines of Order. (used by web store)
     * 
     * @return lines
     */
    public MOrderLine[] getLines() {
        return getLines(false, null);
    } // getLines
   
	/**
	 * Get Purchase Order or Sales order of Business Partner in Discount/Bonus Claim.
	 * @param claim
	 * @return null if no order data or array of order if any data.
	 */
	public static MOrder[] getOrderBPClaim(MUNSBonusClaim claim)
	{
		ArrayList<MOrder> list = new ArrayList<MOrder>();
		
		StringBuilder PrepareSQL = new StringBuilder("SELECT * FROM ")
		.append(MOrder.Table_Name).append(" WHERE ")
		.append(MOrder.COLUMNNAME_C_BPartner_ID).append("=? ")
		.append(" AND ").append(MOrder.COLUMNNAME_DateOrdered)
		.append(" BETWEEN ? AND ?");
		
		String sql = PrepareSQL.toString();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(sql, claim.get_TrxName());
			st.setInt(1, claim.getC_BPartner_ID());
			st.setTimestamp(2, claim.getDateFrom());
			st.setTimestamp(3, claim.getDateTo());
			rs = st.executeQuery();
			while (rs.next()) {
				MOrder order = new MOrder(claim.getCtx(), rs, claim.get_TrxName());
				list.add(order);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new AdempiereException("Error SQL on get order data.");
		} finally {
			DB.close(rs, st);
		}
		
		MOrder[] orders = new MOrder[list.size()];
		list.toArray(orders);
		
		return orders;
	}

    private MUNSDiscountTrx[] m_discountsTrx = null;

    public MUNSDiscountTrx[] getDiscountsTrx(boolean requery) {
        if (null != m_discountsTrx && !requery)
        {
            set_TrxName(m_discountsTrx, get_TrxName());
            return m_discountsTrx;
        }

        List<MUNSDiscountTrx> list =
                Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSDiscountTrx.Table_Name,
                        MUNSDiscountTrx.COLUMNNAME_C_Order_ID + "=?", get_TrxName())
                        .setParameters(getC_Order_ID()).list();

        m_discountsTrx = new MUNSDiscountTrx[list.size()];
        list.toArray(m_discountsTrx);

        return m_discountsTrx;
    }

    @Override
    public String prepareIt() {
    	String docSubType = getC_DocTypeTarget().getDocSubTypeSO();
    	//We can process order with doc sub type CO (Canvas Order).
    	if(null == docSubType || !docSubType.equals("CO"))    	
    		return super.prepareIt();
    	
    	if(m_canvasserInfo == null)
    	{
    		super.m_processMsg = "Canvasser info of canvasser is not set.";
    		return DocAction.STATUS_Invalid;
    	}
    	
    	m_processMsg = checkPrevOrder();
    	if(!Util.isEmpty(m_processMsg, true))
    	{
    		super.m_processMsg = m_processMsg;
    		return DocAction.STATUS_Invalid;
    	}
    	
    	int allowedOrderNotReported = m_canvasserInfo.getAllowedOrderNotReported();
    	Timestamp lastReport = m_canvasserInfo.getLastReport();
    	if(null == lastReport)
    		lastReport = new Timestamp(System.currentTimeMillis());
    	
    	int totalOrderNotReported =
    			DB.getSQLValue(get_TrxName(), "SELECT COUNT(C_Order_ID) FROM C_Order WHERE DateOrdered > ? AND C_BPartner_ID = ? AND DocStatus IN ('CO','CL')"
    					+ " AND C_Order_ID<>? AND C_DocTypeTarget_ID=1000092"
    					, lastReport, getC_BPartner_ID(),  get_ID());    	
    	if(totalOrderNotReported > allowedOrderNotReported)
    	{
    		super.m_processMsg = "...Please create report for previous canvas order.";
    		return DocAction.STATUS_Invalid;
    	}
    	
    	MStorageOnHand[] storages = MStorageOnHand.getOfLocator(
    			getCtx(), m_canvasserInfo.getM_Locator_ID(), get_TrxName());
    	
    	Hashtable<Integer, Hashtable<BigDecimal, BigDecimal>> mapping = new Hashtable<>();
    	if(getDocStatus().equals(DOCSTATUS_Drafted))
    	{
    		for(MStorageOnHand storage : storages)
        	{
        		SimplePrice pricing = new SimplePrice(
        				getC_BPartner_ID(), storage.getM_Product_ID(), get_TrxName());
        		
        		Hashtable<BigDecimal, BigDecimal> childMap = mapping.get(storage.getM_Product_ID());
        		if(null == childMap)
        		{
        			childMap = new Hashtable<>();
        			mapping.put(storage.getM_Product_ID(), childMap);
        		}
        		
        		BigDecimal tmp = childMap.get(pricing.getPriceActual());
        		if(null == tmp)
        		{
        			tmp = Env.ZERO;
        		}
        		
        		tmp = tmp.add(storage.getQtyOnHand());
        		childMap.put(pricing.getPriceActual(), tmp);
        	}
    	}
    	
    	MOrderLine[] lines = getLines();
    	for(int i=0; i<lines.length; i++)
    	{
    		MOrderLine line = lines[i];
    		Hashtable<BigDecimal, BigDecimal> childMap = mapping.get(line.getM_Product_ID());
    		if(null == childMap)
    		{
    			childMap = new Hashtable<>();
    			mapping.put(line.getM_Product_ID(), childMap);
    		}
    		
    		BigDecimal tmp = childMap.get(line.getPriceActual());
    		if(null == tmp)
    		{
    			tmp = Env.ZERO;
    		}
    		
    		tmp = tmp.add(line.getQtyEntered());
    		childMap.put(line.getPriceActual(), tmp);
    	}
    	
    	BigDecimal grandTotal = Env.ZERO;
    	for(Integer product : mapping.keySet())
    	{
    		Hashtable<BigDecimal, BigDecimal> childMap = mapping.get(product);
    		BigDecimal totalQty = Env.ZERO;
    		BigDecimal totalLines = Env.ZERO;
    		for(BigDecimal price : childMap.keySet())
    		{
    			BigDecimal qty = childMap.get(price);
    			totalQty = totalQty.add(qty);
    			BigDecimal lineNetAmt = price.multiply(qty);
    			totalLines = totalLines.add(lineNetAmt);
    		}
    		
    		boolean isOverLimit = MUNSCvsLimit.isOverQtyOrAmt(
    				getAD_Org_ID(), m_canvasserInfo.getCanvasType(), getDateOrdered(), product
    				, totalQty, totalLines, get_TrxName());
    		
    		if(isOverLimit)
    		{
    			super.m_processMsg = "@Over Limit@";
    			return DocAction.STATUS_Invalid;
    		}
    		
    		grandTotal = grandTotal.add(totalLines);
    	}
    	
    	boolean isOverLimit = MUNSCvsLimit.isOverGrandTotal(
    			getAD_Org_ID(), m_canvasserInfo.getCanvasType(), getDateOrdered()
    			, grandTotal, get_TrxName());
    	
    	if(isOverLimit)
    	{
    		super.m_processMsg = "Over Limit Grand Total";
    		return DocAction.STATUS_Invalid;
    	}
    	
    	if(!m_canvasserInfo.isMustReturnRemainingItems())
    	{
    		if(m_canvasserInfo.getLastPhysical() == null)
    		{
    			m_canvasserInfo.setLastPhysical(getDateOrdered());;
        		m_canvasserInfo.saveEx();
    		}
    		
    		int daysPhysical = m_canvasserInfo.getDaysPhysical();
        	StringBuilder sb = new StringBuilder("SELECT TIMESTAMP '")
        		.append(m_canvasserInfo.getLastPhysical())
        		.append("' + INTERVAL '1 DAY' * " ).append(daysPhysical);
        	
        	String sql = sb.toString();
        	
        	Timestamp dateMustPhysical = DB.getSQLValueTS(
        			get_TrxName(), sql);
        	
        	if(getDateOrdered().compareTo(dateMustPhysical) >= 0)
        	{
        		super.m_processMsg = "Can't Commplete process document. please create physical First";
        		return DocAction.STATUS_Invalid;
        	}
    	}
    	
    	boolean exists = false;
    	if(m_canvasserInfo.getLastReport() == null)
    	{
    		String sql = "SELECT COUNT(*) FROM C_Order WHERE C_BPartner_ID = ? AND C_Order_ID <> ? "
    				+ " AND IsSOTrx='Y' AND C_DocTypeTarget_ID = (SELECT C_DocType_ID "
    				+ " FROM C_DocType WHERE DocSubTypeSO = 'CO') AND (DocStatus = 'CO' OR DocStatus = 'CL')";
    		exists = DB.getSQLValue(
    				get_TrxName(), sql, getC_BPartner_ID(), getC_Order_ID())
    				> m_canvasserInfo.getMaxDaysNotReport();
    	}
    	else
    	{
    		String sql = "SELECT COUNT(*) FROM C_Order WHERE C_BPartner_ID = ? AND C_Order_ID <> ? AND IsSOTrx = 'Y'"
    				+ " AND C_DocTypeTarget_ID = (SELECT C_DocType_ID FROM C_DocType WHERE "
    				+ " DocSubTypeSO = 'CO') AND DateOrdered > ? AND DocStatus IN ('CO','CL') AND AD_Org_ID=?";
    		exists = DB.getSQLValue(
    				get_TrxName(), sql, getC_BPartner_ID(), getC_Order_ID(),m_canvasserInfo.getLastReport(), getAD_Org_ID())
    				> m_canvasserInfo.getMaxDaysNotReport();
    	}
    	
    	if(exists)
		{
			super.m_processMsg = "Please create canvas report from previous Canvas Order.";
			return DocAction.STATUS_Invalid;
		}
    	
    	super.m_processMsg = onCanvasOrder(DocAction.ACTION_Prepare);
    	if(!Util.isEmpty(m_processMsg, true))
    	{
    		return DocAction.STATUS_InProgress;
    	}
    	
        return super.prepareIt();
    }


    /**
     * Get Order Not shipped/receipt
     * @param ctx
     * @param BP_SalesRep_ID
     * @param dateFrom
     * @param dateTo
     * @param SOTrx
     * @param trxName
     * @return
     */
    public static List<MOrder> getNotShipBySalesAndDate(Properties ctx, int BP_SalesRep_ID, int UNS_Rayon_ID,
    		Timestamp dateFrom, Timestamp dateTo, String SOTrx, String trxName) 
    {
        StringBuilder buildWhereClause = new StringBuilder(" SalesRep_ID = ").append(BP_SalesRep_ID)
                .append(" AND isSOTrx = '").append(SOTrx).append("' ");
        buildWhereClause.append(" AND ").append(COLUMNNAME_C_Order_ID).append(" IN ")
        .append("(SELECT ").append(COLUMNNAME_C_Order_ID).append( " FROM ")
        .append(MOrderLine.Table_Name).append(" WHERE ").append(MOrderLine.COLUMNNAME_QtyDelivered)
        .append(" < ").append(MOrderLine.COLUMNNAME_QtyOrdered).append(") AND ")
        .append(" (DocStatus = 'CO' OR DocStatus='CL')");
        
        if(dateFrom != null && dateTo != null)
        {
        	buildWhereClause.append(" AND ").append(COLUMNNAME_DateOrdered).append(" BETWEEN '")
        	.append(new SimpleDateFormat("yyy-MM-dd").format(dateFrom)).append("' AND '")
        	.append(new SimpleDateFormat("yyy-MM-dd").format(dateTo)).append("'");
        }
        else if(dateFrom != null)
        {
        	buildWhereClause.append(" AND DateOrdered >= '").append(
        			new SimpleDateFormat("yyyy-MM-dd").format(dateFrom)).append("'");
        }
        else if(dateTo != null)
        {
        	buildWhereClause.append(" AND DateOrdered <= '").append(
        			new SimpleDateFormat("yyyy-MM-dd").format(dateTo)).append("'");
        }
        
        if(UNS_Rayon_ID > 0)
        {
        	buildWhereClause.append(" AND ").append(MOrder.COLUMNNAME_C_BPartner_ID)
        	.append(" IN (SELECT C_BPartner_ID FROM C_BPartner_Location WHERE UNS_Rayon_ID = ")
        	.append(UNS_Rayon_ID).append(")");
        }
        
        String whereClause = buildWhereClause.toString();
        return get(ctx, whereClause, trxName);
    }

    /**
     * CCD kang
    public static List<MOrder> getBySalesAndDate(Properties ctx, int BP_SalesRep_ID, Timestamp dateFrom,
            Timestamp dateTo, String SOTrx, String trxName) {
    	//lo masukin parameter SalesRep_ID dari yang manggil method ini tapi lu mau buat instance BPartner
    	//di sini bakal error. udh gitu gak lu pake. salesRep.getSalesRep(). terus mau di apain ???? gak ada yang nampung
//        MBPartner salesRep = MBPartner.get(ctx, BP_SalesRep_ID);
//        salesRep.getSalesRep();
//    	kita cuma butuh order yang belum dikirim kaleeee :(. yang bener lah.

        StringBuilder buildWhereClause = new StringBuilder(" SalesRep_ID = ").append(BP_SalesRep_ID)
                .append(" AND isSOTrx = '").append(SOTrx).append("' ");
        if(dateTo != null)
            buildWhereClause.append(" AND DateOrdered <= '").append(new SimpleDateFormat("yyyy-MM-dd").format(dateTo))
            .append("'").append(" AND ").append(" (DocStatus = 'CO' OR DocStatus='CL')");
        String whereClause = buildWhereClause.toString();
        return get(ctx, whereClause, trxName);
    }
*/
    /**
     * 
     * @param ctx
     * @param whereClause
     * @param trxName
     * @return
     */
    public static List<MOrder> get(Properties ctx, String whereClause, String trxName) {
        List<MOrder> list =
                Query.get(ctx, UNSOrderModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName).list();

        return list;
    }


    public void addLines(MOrderLine line) {
        if (null == m_lines)
            return;

        MOrderLine[] tmp = Arrays.copyOf(m_lines, m_lines.length + 1);
        tmp[m_lines.length] = line;
        m_lines = tmp;
        set_TrxName(m_lines, get_TrxName());
    }

    @Override
    public boolean beforeSave(boolean newRecord) {
        if (is_ValueChanged(COLUMNNAME_DiscountAmt) || is_ValueChanged(COLUMNNAME_Discount))
        {
            BigDecimal totalLines =
                    DB.getSQLValueBD(get_TrxName(),
                            "SELECT COALESCE(SUM(LinenetAmt), 0) FROM C_OrderLine WHERE C_Order_ID =?",
                            getC_Order_ID());

            BigDecimal grandTotal = totalLines.subtract(getDiscountAmt());
            setGrandTotal(grandTotal);
        }
        
        String docSubTypeSo = getC_DocTypeTarget().getDocSubTypeSO();
        if(docSubTypeSo != null && docSubTypeSo.equals("CO"))
        {
        	String sql = "SELECT AD_User_ID FROM AD_User WHERE C_BPartner_ID = ?";
        	int salesRep = DB.getSQLValue(get_TrxName(), sql, getC_BPartner_ID());
        	if (salesRep <= 0)
        	{
        		throw new AdempiereUserError("The Partner is not have User"
        				, "Please create user for this partner first.");
        	}
        	setSalesRep_ID(salesRep);
        }
        return super.beforeSave(newRecord);
    }
    
    public boolean afterSave(boolean newRecord, boolean success)
    {
    	if(!success)
    		return false;
    	
//    	if(!newRecord && getUNS_OrderGroup_ID() > 0 && !isProcessed())
//    	{
//    		MUNSOrderGroup group = new MUNSOrderGroup(getCtx(), getUNS_OrderGroup_ID(), get_TrxName());
//    		if(!group.upHeaderGroup())
//    		{
//    			log.saveError("Error", "Failed when trying update header group");
//    			return false;
//    		}
//    	}
    	
    	return super.afterSave(newRecord, success);
    }
    
    /**
     * 
     * @param M_OrderLine_ID
     * @return
     */
    public MOrderLine getLineOf(int M_OrderLine_ID)
    {
    	MOrderLine line = null;
    	MOrderLine[] lines = getLines();
    	for(int i=0; i<lines.length; i++)
    	{
    		if(lines[i].get_ID() != M_OrderLine_ID)
    			continue;
    		
    		line = lines[i];
    		break;
    	}
    	
    	return line;
    }
    
    private String onCanvasOrder(String event)
    {
    	String message = null;

    	if(event.equals(DocAction.ACTION_Complete))
    	{
    		message = onCanvasOrderComplete();
    	}
    	else if(event.equals(DocAction.ACTION_Prepare))
    	{
    		message = onCanvasOrderPrepare();
    	}
    	else if (event.equals(DocAction.ACTION_Void) && !isForce())
    	{
    		message = onCanvasVoid();
    	}
    	
    	return message;
    }
    
    /**
     * 
     * @return
     */
    private String onCanvasOrderPrepare()
    {
		MMovement move = getCanvasMovement();
		
		if(null == move)
			createCanvasMovement();
    	
    	return null;
    }
    
    private boolean m_force = false;
    
    /**
     * 
     * @return
     */
    private String onCanvasOrderComplete()
    {
    	MMovement move = getCanvasMovement();
    	
    	if(null == move)
    		move = createCanvasMovement();
    	
    	String moveStatus = move.getDocStatus();
    	
    	if(!moveStatus.equals(DOCSTATUS_Completed) && !moveStatus.equals(DOCSTATUS_Closed)
    			&& !isForce())
    		return "Please complete Canvas Confirmation --> " .concat(move.getDocumentNo());
    	
    	String errorMsg = synchronizeMovement();
    	if(!Util.isEmpty(errorMsg, true))
    		return errorMsg;
    	
    	return null;
    }
    
    /**
     * Synchronize quantity on canvas order line and movement line. find unallocated movement line.
     * @return {@link String} Error Message / ""
     */
    public String synchronizeMovement()
    {
    	MMovement move = getCanvasMovement();
    	MOrderLine[] orderLines = getLines();
    	StringBuilder errorMsg = new StringBuilder();
   
    	Hashtable<Integer, BigDecimal> mapingProductQty = getMappingProductQtyMove(move);
    	
    	for(int j=0; j<orderLines.length; j++)
		{
			MOrderLine orderLine = orderLines[j];
			BigDecimal remaining = mapingProductQty.get(orderLine.getM_Product_ID());
			
			if(null  == remaining)
			{
				errorMsg.append("Not full fill Canvas Line " + orderLine.getLine());
				continue;
			}
			
			remaining = remaining.subtract(orderLine.getQtyOrdered());
			
			if(remaining.signum() == -1)
			{
				errorMsg.append("Not full fill Canvas Line " + orderLine.getLine());
				continue;
			}
			else
			{
				mapingProductQty.put(orderLine.getM_Product_ID(), remaining);
			}
    	}
    	
    	for(BigDecimal remaining : mapingProductQty.values())
    	{
    		if(remaining.signum() == 0)
    			continue;
    		
    		errorMsg.append("Some Movement Line is unallocated");	
    	}
    	
    	String retVal = errorMsg.toString();
    	return retVal;
    }
    
    
    /**
     * Create Movement From Canvas Order
     * @return
     */
    public MMovement createCanvasMovement()
    {
    	MWarehouse whsFrom = (MWarehouse) getM_Warehouse();
    	MMovement move = new MMovement(getCtx(), 0, get_TrxName());
    	move.setC_Order_ID(get_ID());
    	move.setAD_Org_ID(whsFrom.getAD_Org_ID());
    	move.setAD_OrgTrx_ID(whsFrom.getAD_Org_ID());
    	move.setAD_User_ID(getAD_User_ID());
    	move.setC_BPartner_ID(getC_BPartner_ID());
    	move.setC_BPartner_Location_ID(getC_BPartner_Location_ID());
    	move.setC_Charge_ID(getC_Charge_ID());
    	move.setC_Activity_ID(getC_Activity_ID());
    	move.setC_Campaign_ID(getC_Campaign_ID());
    	move.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_MaterialMovement));
    	move.setC_Project_ID(getC_Project_ID());
    	move.setChargeAmt(getChargeAmt());
    	move.setDateReceived(getDateOrdered());
    	move.setDeliveryRule(getDeliveryRule());
    	move.setDeliveryViaRule(getDeliveryViaRule());
    	move.setDescription(getDescription());
    	move.setDestinationWarehouse_ID(m_canvasserInfo.getM_Locator().getM_Warehouse_ID());
    	move.setDocAction(DocAction.ACTION_Prepare);
    	move.setDocStatus(DOCSTATUS_Drafted);
    	move.setFreightAmt(getFreightAmt());
    	move.setFreightCostRule(getFreightCostRule());
    	move.setIsActive(true);
    	move.setIsApproved(false);
    	move.setIsInternalWarehouseConfirm(true);
    	move.setIsInTransit(false);
    	move.setM_Shipper_ID(getM_Shipper_ID());
    	move.setMovementDate(getDateOrdered());
    	move.setPOReference(getPOReference());
    	move.setPosted(false);
    	move.setPriorityRule(getPriorityRule());
    	move.setProcessed(false);
    	move.setSalesRep_ID(getSalesRep_ID());
    	move.setUser1_ID(getUser1_ID());
    	move.setUser2_ID(getUser2_ID());
    	try
    	{
        	move.saveEx();
        	createMoveLines(move);
    	}
    	catch (Exception e)
    	{
    		throw new AdempiereException("Failed on create movement. " + e);
    	}
    	return move;
    }
    
    /**
     * Create Movement Line from Order Line. set locator to default locator of warehouse in order line.
     * @param move
     */
    private void createMoveLines(MMovement move)
    {
    	MOrderLine[] lines = getLines();
    	for(int i=0; i<lines.length; i++)
    	{
    		MMovementLine mLine = new MMovementLine(move);
    		mLine.setDescription(lines[i].getDescription());
    		mLine.setIsActive(true);
    		mLine.setM_Locator_ID(lines[i].getDefaultLocator().get_ID());
    		mLine.setM_LocatorTo_ID(m_canvasserInfo.getM_Locator_ID());
    		mLine.setM_Product_ID(lines[i].getM_Product_ID());
    		mLine.setMovementQty(lines[i].getQtyOrdered());
    		mLine.setProcessed(false);
    		mLine.saveEx();
    	}
    }
    
    /**
     * 
     * @return
     */
    public MMovement getCanvasMovement()
    {
    	String sql = "SELECT * FROM M_Movement WHERE C_Order_ID = ?";
    	MMovement movement = null;
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	try
    	{
    		st = DB.prepareStatement(sql, get_TrxName());
    		st.setInt(1, get_ID());
    		rs = st.executeQuery();
    		if (rs.next())
    			movement = new MMovement(getCtx(), rs, get_TrxName());
    	}
    	catch (SQLException e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		DB.close(rs, st);
    	}
    	
    	return movement;
    }
    
    @Override
    public String completeIt()
    {
    	String docSubType = getC_DocType().getDocSubTypeSO();
    	
    	if(null == docSubType || !docSubType.equals("CO"))
    		return super.completeIt();
    	

    	super.m_processMsg = onCanvasOrder(DocAction.ACTION_Complete);	
    	if(!Util.isEmpty(m_processMsg, true))
    	{
    		return DocAction.STATUS_InProgress;
    	}
    	if(m_canvasserInfo.isMandatoryShipping())
    	{
    		MUNSShipping shipping = MUNSShipping.getShipping(this);
        	if(null == shipping)
        	{
        		super.m_processMsg = "Please Create shipping document first";
        		return DocAction.STATUS_InProgress;
        	}
        	if(!shipping.getDocStatus().equals(DOCSTATUS_Completed)
        			&& !shipping.getDocStatus().equals(DOCSTATUS_Closed))
        	{
        		super.m_processMsg = "Please complete shipping document first";
        		return DocAction.STATUS_InProgress;
        	}
    	}
    
    	return super.completeIt();
    }
    
    private  Hashtable<Integer, BigDecimal> getMappingProductQtyMove(MMovement move)
    {
    	Hashtable<Integer, BigDecimal> myMap = new Hashtable<>();
    	MMovementLine[] records = move.getLines(false);
    	for(int i=0; i<records.length; i++)
    	{
    		BigDecimal tmp = myMap.get(records[i].getM_Product_ID());
    		
    		if(null == tmp)
    			tmp = Env.ZERO;
    		
    		tmp = tmp.add(records[i].getMovementQty());
    		myMap.put(records[i].getM_Product_ID(), tmp);
    	}
    	return myMap;
    }
    
    /**
     * 
     * @return
     */
    public BigDecimal getTonase()
    {
    	if(null == m_tonase)
    		initialVolumeAndTonase();
    	
    	return m_tonase;
    }
    
    /**
     * 
     */
    public BigDecimal getVolume()
    {
    	if(null == m_volume)
    		initialVolumeAndTonase();
    	
    	return m_volume;
    }
    
    private void initialVolumeAndTonase()
    {
    	m_tonase = Env.ZERO;
    	m_volume = Env.ZERO;
    	MStorageOnHand[] storages = MStorageOnHand.getOfLocator(
    			getCtx(), m_canvasserInfo.getM_Locator_ID(), get_TrxName());
    	
    	for(MStorageOnHand storage : storages)
    	{
    		I_M_Product p = storage.getM_Product();
    		BigDecimal weight = p.getWeight();
    		BigDecimal volume = p.getVolume();
    		BigDecimal totalWeight = storage.getQtyOnHand().multiply(weight);
    		BigDecimal totalVolume = storage.getQtyOnHand().multiply(volume);
    		m_tonase = m_tonase.add(totalWeight);
    		m_volume = m_volume.add(totalVolume);
    	}
    	m_tonase = m_tonase.divide(m_cibu, 4, RoundingMode.HALF_DOWN);
    }
    
    public boolean isForce()
    {
    	return this.m_force;
    }
    
    public void setForce(boolean force)
    {
    	this.m_force = force;
    }
    
    public String onCanvasVoid ()
    {
    	MTable table = MTable.get(getCtx(), "M_Movement");
    	table.setExtensionHandler("UniCoreMaterialManagementModelFactory");
    	PO po = table.getPO("C_Order_ID = ?", 
    			new Object[] {getC_Order_ID()}, get_TrxName());
    	String status = po.get_ValueAsString("DocStatus");
    	if (DOCSTATUS_Reversed.equals(status)
    			|| DOCSTATUS_Voided.equals(status))
    	{
    		return null;
    	}
    	try
    	{
    		ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(
    				po, DocAction.ACTION_Void);
    		if (pi.isError())
    		{
    			return pi.getSummary();
    		}
    	}
    	catch (Exception e)
    	{
    		return e.getMessage();
    	}
    	
    	return null;
    }
    
    @Override
    public boolean voidIt ()
    {
    	String docSubType = getC_DocType().getDocSubTypeSO();
    	
    	if(null == docSubType || !docSubType.equals("CO"))
    		return super.voidIt();
    	
    	m_processMsg = onCanvasOrder(DocAction.ACTION_Void);
    	if (!Util.isEmpty(m_processMsg, true))
    		return false;
    	return super.voidIt();
    }
    
    public BigDecimal getWriteOff()
    {
    	if(getC_DocTypeTarget_ID() != 1000097)
    		return Env.ZERO;
    	
    	BigDecimal writeOff = getGrandTotal().subtract(getPaidAmt());
    	
    	return writeOff;
    }
    
    private String checkPrevOrder()
    {
    	String sql = "SELECT MAX(o.DateOrdered) FROM C_Order o WHERE o.C_BPartner_ID = ?"
    			+ " AND o.DocStatus IN ('CO', 'CL') AND o.C_DocTypeTarget_ID IN ("
    			+ " SELECT dt.C_DocType_ID FROM C_DocType dt WHERE dt.DocSubTypeSO = 'CO')"
    			+ " AND o.C_Order_ID <> ? AND o.AD_Org_ID = ?";
    	Timestamp prevDateOrder = DB.getSQLValueTS(get_TrxName(), sql, getC_BPartner_ID(), get_ID(), getAD_Org_ID());
    	if(prevDateOrder == null)
    		return "";
    	
    	sql = "SELECT MAX(iv.DateInvoiced) FROM C_Invoice iv WHERE iv.C_BPartner_ID = ?"
    			+ " AND iv.DocStatus IN ('CO', 'CL') AND iv.IsPaid = 'Y' AND"
    			+ " EXISTS (SELECT 1 FROM UNS_Cvs_Rpt rpt WHERE rpt.C_Invoice_ID = iv.C_Invoice_ID"
    			+ " AND rpt.DocStatus IN ('CO', 'CL') AND rpt.AD_Org_ID = ?)";
    	Timestamp prevDateInvoice = DB.getSQLValueTS(get_TrxName(), sql, getC_BPartner_ID(), getAD_Org_ID());
    	
    	if(prevDateInvoice == null || prevDateInvoice.before(prevDateOrder))
    		return "Please create report for previous order";
    	
    	return "";
    }
}