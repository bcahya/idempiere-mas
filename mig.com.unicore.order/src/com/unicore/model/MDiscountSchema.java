/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.unicore.model.X_UNS_Outlet_Grade;
import com.uns.base.model.Query;
import com.uns.util.MessageBox;

import com.unicore.base.model.MInvoiceLine;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.MDiscountSchemaBreak;
import com.unicore.model.factory.UNSOrderModelFactory;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MDiscountSchemaLine;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MOrg;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.compiere.util.Util;

/**
 * @author UNTA-Andy
 * 
 */
public class MDiscountSchema 
	extends X_M_DiscountSchema
	implements DocAction, DocOptions
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5215858169294943843L;
	public static final String DocType_DiscountTargetBonus = "DTB";
	public static final String DocType_DiscountAfterTrx = "DAT";
	private MUNSDiscountCustomer[] m_listCustomer = null;
	/** Breaks */
	private MDiscountSchemaBreak[] m_breaks = null;
	/** Lines */
	private MDiscountSchemaLine[] m_lines = null;	
	/** Cache */
	private static CCache<Integer, MDiscountSchema> s_cache = new CCache<Integer, MDiscountSchema>(
			Table_Name, 20);
	private static Logger s_log;
	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;
	private MUNSSalesBudget[] m_salesBudgets;

	private List<MDiscountSchemaBreak> m_listBreaks = null;

	private MBPartner[] m_includedPartners = null;
	private X_UNS_Outlet_Grade[] m_includedGrades = null;
	private MBPGroup[] m_includedGroups = null;
	private MUNSDiscountOrg[] m_orgs = null;
		

	/**
	 * Get Discount Schema from Cache
	 * 
	 * @param ctx context
	 * @param M_DiscountSchema_ID id
	 * @return MDiscountSchema
	 */
	public static MDiscountSchema get(Properties ctx, int M_DiscountSchema_ID)
	{
		Integer key = new Integer(M_DiscountSchema_ID);
		MDiscountSchema retValue = (MDiscountSchema) s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MDiscountSchema(ctx, M_DiscountSchema_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	} // get

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param M_DiscountSchema_ID id
	 * @param trxName transaction
	 */
	public MDiscountSchema(Properties ctx, int M_DiscountSchema_ID, String trxName)
	{
		super(ctx, M_DiscountSchema_ID, trxName);
		if (M_DiscountSchema_ID == 0)
		{
			// setName();
			setDiscountType(DISCOUNTTYPE_Flat);
			setFlatDiscount(Env.ZERO);
			setIsBPartnerFlatDiscount(false);
			setIsQuantityBased(true); // Y
			setCumulativeLevel(CUMULATIVELEVEL_Line);
			// setValidFrom (new Timestamp(System.currentTimeMillis()));
		}
	} // MDiscountSchema

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MDiscountSchema(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	} // MDiscountSchema

	/**
	 * Method getDiscountSchemabyBPDate get list {@link MDiscountSchema} by BPartner and DateOrdered
	 * 
	 * @param order
	 * @return
	 */
	public static MDiscountSchema[] getDiscountSchemabyBPDate(MOrder order)
	{
		ArrayList<MDiscountSchema> list = new ArrayList<MDiscountSchema>();

		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append(MDiscountSchema.Table_Name).append("_ID FROM ").append(MDiscountSchema.Table_Name)
				.append(" WHERE ").append(MDiscountSchema.COLUMNNAME_C_BPartner_ID).append("=")
				.append(order.getC_BPartner_ID()).append(" AND ")
				.append(MDiscountSchema.COLUMNNAME_DocStatus).append("='")
				.append(MDiscountSchema.DOCSTATUS_Completed).append("' AND '")
				.append(new SimpleDateFormat("yyyy-MM-dd").format(order.getDatePromised()))
				.append("' BETWEEN ").append(MDiscountSchema.COLUMNNAME_ValidFrom).append(" AND ")
				.append(" COALESCE(").append(MDiscountSchema.COLUMNNAME_ValidTo)
				.append(", now() + INTERVAL '365 days')").append(" ORDER BY ")
				.append(MDiscountSchema.COLUMNNAME_ValidFrom);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), order.get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MDiscountSchema(order.getCtx(), rs.getInt(1), order.get_TrxName()));
		}
		catch (SQLException e)
		{
			s_log.log(Level.SEVERE, sql.toString(), e);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (list.size() == 0)
			return null;

		// Convert to array
		MDiscountSchema[] retValue = new MDiscountSchema[list.size()];
		list.toArray(retValue);

		return retValue;
	}

	/**
	 * Method getStandartDiscountSchemabyDate get list {@link MDiscountSchema} by DateOrdered
	 * 
	 * @param order
	 * @return
	 */
	public static MDiscountSchema[] getDiscountSchemabyDate(MOrder order)
	{
		ArrayList<MDiscountSchema> list = new ArrayList<MDiscountSchema>();

		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append(MDiscountSchema.Table_Name).append("_ID FROM ").append(MDiscountSchema.Table_Name)
				.append(" WHERE ").append(MDiscountSchema.COLUMNNAME_C_BPartner_ID).append(" IS NULL AND ")
				.append(MDiscountSchema.COLUMNNAME_DocStatus).append("='")
				.append(MDiscountSchema.DOCSTATUS_Completed).append("' AND '")
				.append(new SimpleDateFormat("yyyy-MM-dd").format(order.getDatePromised()))
				.append("' BETWEEN ").append(MDiscountSchema.COLUMNNAME_ValidFrom).append(" AND ")
				.append(" COALESCE(").append(MDiscountSchema.COLUMNNAME_ValidTo)
				.append(", now() + INTERVAL '365 days')");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), order.get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MDiscountSchema(order.getCtx(), rs.getInt(1), order.get_TrxName()));
			}
		}
		catch (SQLException e)
		{
			s_log.log(Level.SEVERE, sql.toString(), e);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (list.size() == 0)
			return null;

		// Convert to array
		MDiscountSchema[] retValue = new MDiscountSchema[list.size()];
		list.toArray(retValue);

		return retValue;
	}

	/**
	 * Get Breaks
	 * 
	 * @param reload reload
	 * @return breaks
	 */
	public MDiscountSchemaBreak[] getBreaks(boolean reload)
	{
		if (m_breaks != null && !reload)
			return m_breaks;

		String sql = "SELECT * FROM M_DiscountSchemaBreak WHERE M_DiscountSchema_ID=? ORDER BY SeqNo";
		ArrayList<MDiscountSchemaBreak> list = new ArrayList<MDiscountSchemaBreak>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getM_DiscountSchema_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MDiscountSchemaBreak(getCtx(), rs, get_TrxName()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		m_breaks = new MDiscountSchemaBreak[list.size()];
		list.toArray(m_breaks);
		return m_breaks;
	} // getBreaks

	/**
	 * Get Lines
	 * 
	 * @param reload reload
	 * @return lines
	 */
	public MDiscountSchemaLine[] getLines(boolean reload)
	{
		if (m_lines != null && !reload)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}

		String sql = "SELECT * FROM M_DiscountSchemaLine WHERE M_DiscountSchema_ID=? ORDER BY SeqNo";
		ArrayList<MDiscountSchemaLine> list = new ArrayList<MDiscountSchemaLine>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getM_DiscountSchema_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MDiscountSchemaLine(getCtx(), rs, get_TrxName()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		m_lines = new MDiscountSchemaLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
	} // getBreaks

	/**
	 * Calculate Discounted Price
	 * 
	 * @param Qty quantity
	 * @param Price price
	 * @param M_Product_ID product
	 * @param M_Product_Category_ID category
	 * @param BPartnerFlatDiscount flat discount
	 * @return discount or zero
	 */
	public BigDecimal calculatePrice(BigDecimal Qty, BigDecimal Price, int M_Product_ID,
			int M_Product_Category_ID, BigDecimal BPartnerFlatDiscount)
	{
		if (log.isLoggable(Level.FINE))
			log.fine("Price=" + Price + ",Qty=" + Qty);
		if (Price == null || Env.ZERO.compareTo(Price) == 0)
			return Price;
		//
		BigDecimal discount =
				calculateDiscount(Qty, Price, M_Product_ID, M_Product_Category_ID, BPartnerFlatDiscount);
		// nothing to calculate
		if (discount == null || discount.signum() == 0)
			return Price;
		//
		BigDecimal onehundred = Env.ONEHUNDRED;
		BigDecimal multiplier = (onehundred).subtract(discount);
		multiplier = multiplier.divide(onehundred, 6, BigDecimal.ROUND_HALF_UP);
		BigDecimal newPrice = Price.multiply(multiplier);
		if (log.isLoggable(Level.FINE))
			log.fine("=>" + newPrice);
		return newPrice;
	} // calculatePrice

	/**
	 * Calculate Discount Percentage
	 * 
	 * @param Qty quantity
	 * @param Price price
	 * @param M_Product_ID product
	 * @param M_Product_Category_ID category
	 * @param BPartnerFlatDiscount flat discount
	 * @return discount or zero
	 */
	public BigDecimal calculateDiscount(BigDecimal Qty, BigDecimal Price, int M_Product_ID,
			int M_Product_Category_ID, BigDecimal BPartnerFlatDiscount)
	{
		if (BPartnerFlatDiscount == null)
			BPartnerFlatDiscount = Env.ZERO;

		//
		if (DISCOUNTTYPE_Flat.equals(getDiscountType()))
		{
			if (isBPartnerFlatDiscount())
				return BPartnerFlatDiscount;
			return getFlatDiscount();
		}
		// Not supported
		else if (DISCOUNTTYPE_Formula.equals(getDiscountType())
				|| DISCOUNTTYPE_Pricelist.equals(getDiscountType()))
		{
			if (log.isLoggable(Level.INFO))
				log.info("Not supported (yet) DiscountType=" + getDiscountType());
			return Env.ZERO;
		}

		// Price Breaks
		getBreaks(false);
		BigDecimal Amt = Price.multiply(Qty);
		if (isQuantityBased())
		{
			if (log.isLoggable(Level.FINER))
				log.finer("Qty=" + Qty + ",M_Product_ID=" + M_Product_ID + ",M_Product_Category_ID="
						+ M_Product_Category_ID);
		}
		else
		{
			if (log.isLoggable(Level.FINER))
				log.finer("Amt=" + Amt + ",M_Product_ID=" + M_Product_ID + ",M_Product_Category_ID="
						+ M_Product_Category_ID);
		}
		for (int i = 0; i < m_breaks.length; i++)
		{
			MDiscountSchemaBreak br = m_breaks[i];
			if (!br.isActive())
				continue;

			if (isQuantityBased())
			{
				if (!br.applies(Qty, M_Product_ID, M_Product_Category_ID))
				{
					if (log.isLoggable(Level.FINER))
						log.finer("No: " + br);
					continue;
				}
				if (log.isLoggable(Level.FINER))
					log.finer("Yes: " + br);
			}
			else
			{
				if (!br.applies(Amt, M_Product_ID, M_Product_Category_ID))
				{
					if (log.isLoggable(Level.FINER))
						log.finer("No: " + br);
					continue;
				}
				if (log.isLoggable(Level.FINER))
					log.finer("Yes: " + br);
			}

			// Line applies
			BigDecimal discount = null;
			if (br.isBPartnerFlatDiscount())
				discount = BPartnerFlatDiscount;
			else
				discount = br.getBreakDiscount();
			if (log.isLoggable(Level.FINE))
				log.fine("Discount=>" + discount);
			return discount;
		} // for all breaks

		return Env.ZERO;
	} // calculateDiscount


	/**
	 * Before Save
	 * 
	 * @param newRecord new
	 * @return true
	 */
	protected boolean beforeSave(boolean newRecord)
	{
		if (getValidFrom() == null)
			setValidFrom(TimeUtil.getDay(null));
		if(getValidTo() != null && getValidFrom().after(getValidTo()))
			throw new AdempiereException("Valid to must be after valid from.");
		
		if(getDiscountType().equals(DISCOUNTTYPE_Breaks))
		{
			setBudgetType(BUDGETTYPE_NonBudgeted);
		}
		else
		{
			deleteBreaks();
		}
		
		if(!isSOTrx())
		{
			setSalesLevel(null);
			setSalesType(null);
			setUNS_Outlet_Grade_ID(-1);
		}
		
		if(getBudgetType()!= null && getBudgetType().equals(BUDGETTYPE_NonBudgeted))
		{
			setQtyAllocated(Env.ZERO);
			deleteBudgetList();
		}
		
		if(is_ValueChanged(COLUMNNAME_SelectionType))
		{
			deleteCustomerList();				
		}
		
		if((is_ValueChanged(COLUMNNAME_SalesType) && getSalesType() != null)
				|| (is_ValueChanged(COLUMNNAME_SalesLevel) && getSalesLevel() != null))
		{
			String sql = "UPDATE M_DiscountSchemaBreak SET ";
			
			if(getSalesType() != null)
				sql += COLUMNNAME_SalesType . concat(" = '") . concat(getSalesType() . concat("'"));
			
			if(getSalesLevel() != null)
			{
				if(getSalesLevel() != null)
					sql  += ", ";
				sql += COLUMNNAME_SalesLevel . concat(" = '") .concat(getSalesLevel() . concat("'"));
			}
			
			sql = sql . concat(" WHERE M_DiscountSchema_ID = ?");
			
			DB.executeUpdate(sql, getM_DiscountSchema_ID(), get_TrxName());
		}
		
		if (!isComplete())
		{
			validateSelection();
			checkDuplicate();	
		}
		if (Util.isEmpty(getDescription(),true)){
			String name=getName();
			setDescription(name);
		}
		return true;
		
	} // beforeSave	
	
	private void validateSelection()
	{
		if(null == getSelectionType())
		{
			setC_BPartner_ID(-1);
			setC_BP_Group_ID(-1);
			setUNS_Outlet_Grade_ID(-1);
			setBudgetType(BUDGETTYPE_NonBudgeted);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				|| getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			setC_BPartner_ID(-1);
			setC_BP_Group_ID(-1);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomer)
				|| getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomer)
				|| getSelectionType().equals(SELECTIONTYPE_Vendor))
		{
			setC_BP_Group_ID(-1);
			setUNS_Outlet_Grade_ID(-1);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				|| getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				|| getSelectionType().equals(SELECTIONTYPE_VendorGroup))
		{
			setC_BPartner_ID(-1);
			setUNS_Outlet_Grade_ID(-1);
		}
		else
		{
			setC_BPartner_ID(-1);
			setC_BP_Group_ID(-1);
			setUNS_Outlet_Grade_ID(-1);
		}
	}
	
	
	private void deleteBonuses()
	{
		try {
			for (MUNSDiscountBonus discountBonus: MUNSDiscountBonus.getBonus(this))
			{
				discountBonus.deleteEx(true);
			}
		} catch (Exception ex) {
			throw new AdempiereException("Failed to delete bonus lines " + ex.getMessage());
		}
	}
	
	private void deleteBreaks()
	{
		try {
			for (MDiscountSchemaBreak schemaBreak: getBreaks(true))
			{
				schemaBreak.deleteEx(true);
			}
		} catch (Exception ex) {
			throw new AdempiereException("Failed to delete breaks " + ex.getMessage());
		}
	}
	
	private void deleteCustomerList()
	{
		for(MUNSDiscountCustomer dc : getListCustomer(false))
		{
			dc.deleteEx(true);
		}
	}
	
	private void deleteBudgetList()
	{
		for(MUNSSalesBudget budget : getSalesBudgetLines(false))
		{
			budget.deleteEx(true);
		}
	}
	
	private void deleteOrgList()
	{
		for(MUNSDiscountOrg org : getSelectionOrgs())
		{
			org.deleteEx(true);
		}
	}
	
	/**
	 * Before Save
	 * 
	 * @param newRecord new
	 * @return true
	 */
	protected boolean beforeDelete()
	{
		deleteBreaks();
		deleteBonuses();
		deleteCustomerList();
		deleteOrgList();
		
		return super.beforeDelete();
	} // beforeSave

	/**
	 * Renumber
	 * 
	 * @return lines updated
	 */
	public int reSeq()
	{
		int count = 0;
		// Lines
		MDiscountSchemaLine[] lines = getLines(true);
		for (int i = 0; i < lines.length; i++)
		{
			int line = (i + 1) * 10;
			if (line != lines[i].getSeqNo())
			{
				lines[i].setSeqNo(line);
				if (lines[i].save())
					count++;
			}
		}
		m_lines = null;

		// Breaks
		MDiscountSchemaBreak[] breaks = getBreaks(true);
		for (int i = 0; i < breaks.length; i++)
		{
			int line = (i + 1) * 10;
			if (line != breaks[i].getSeqNo())
			{
				breaks[i].setSeqNo(line);
				if (breaks[i].save())
					count++;
			}
		}
		m_breaks = null;
		return count;
	} // reSeq
	
	/**
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public boolean validDate(Timestamp dateFrom, Timestamp dateTo)
	{
		if (getValidFrom().after(dateFrom))
			return false;
		
		if(null == getValidTo())
			return true;
		
		if (getValidTo().before(dateTo))
			return false;

//		if (dateTo.after(new Timestamp(System.currentTimeMillis())))
//			return false;

		return true;
	}


	/**
	 *	String representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MDiscountSchema[")
			.append (get_ID()).append("-").append(getDocumentNo())
			.append(",DocStatus=").append(getDocStatus())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF()
	{
		try
		{
			StringBuilder msgfile = new StringBuilder().append(get_TableName()).append(get_ID()).append("_");
			File temp = File.createTempFile(msgfile.toString(), ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
		ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.ORDER, getM_DiscountSchema_ID(), get_TrxName());
		if (re == null)
			return null;
		MPrintFormat format = re.getPrintFormat();
		// We have a Jasper Print Format
		// ==============================
		if(format.getJasperProcess_ID() > 0)	
		{
			ProcessInfo pi = new ProcessInfo ("", format.getJasperProcess_ID());
			pi.setRecord_ID ( getM_DiscountSchema_ID() );
			pi.setIsBatch(true);
			
			ServerProcessCtl.process(pi, null);
			
			return pi.getPDFReport();
		}
		// Standard Print Format (Non-Jasper)
		// ==================================
		return re.getPDF(file);
	}	//	createPDF

	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	process

	/**
	 * 	Unlock Document.
	 * 	@return true if success
	 */
	public boolean unlockIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setProcessing(false);
		return true;
	}	//	unlockIt

	/**
	 * 	Invalidate Document
	 * 	@return true if success
	 */
	public boolean invalidateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	//	invalidateIt

	@Override
	public String prepareIt() 
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if (getDiscountType().equals(DISCOUNTTYPE_Breaks))
		{
			X_M_DiscountSchemaBreak[] breaks = getBreaks(true);
			if (breaks.length == 0)
			{
				m_processMsg = "You have to define the Discount Schema Breaks for \"Breaks\" discount type.";
				return DocAction.STATUS_Invalid;
			}
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}	//	prepareIt

	/**
	 * 	Approve Document
	 * 	@return true if success
	 */
	public boolean  approveIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setIsApproved(true);
		return true;
	}	//	approveIt

	/**
	 * 	Reject Approval
	 * 	@return true if success
	 */
	public boolean rejectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt

	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		MDiscountSchemaBreak[] dsBreaks = getBreaks(false);
		for(MDiscountSchemaBreak dsBreak : dsBreaks)
		{
			dsBreak.setProcessed(true);
			dsBreak.saveEx();
		}
		
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt

	/**
	 * 	Void Document.
	 * 	@return true if success
	 */
	public boolean voidIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());		

		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}

		//	Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
			|| DOCSTATUS_Invalid.equals(getDocStatus())
			|| DOCSTATUS_InProgress.equals(getDocStatus())
			|| DOCSTATUS_Approved.equals(getDocStatus())
			|| DOCSTATUS_NotApproved.equals(getDocStatus()) )
		{
			// Before Void
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
			if (m_processMsg != null)
				return false;
			
			//	Set lines to 0

			//
			// Void Confirmations
			setDocStatus(DOCSTATUS_Voided); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
			saveEx();
		}
		else
		{
		}

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	//	voidIt

	/**
	 * 	Close Document.
	 * 	@return true if success
	 */
	public boolean closeIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	}	//	closeIt

	/**
	 * 	Reverse Correction - same date
	 * 	@return true if success
	 */
	public boolean reverseCorrectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);		//	 may come from void
		setDocAction(DOCACTION_None);
		return true;
	}	//	reverseCorrectionIt

	/**
	 * 	Reverse Accrual - none
	 * 	@return false
	 */
	public boolean reverseAccrualIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);		//	 may come from void
		setDocAction(DOCACTION_None);
		return true;
	}	//	reverseAccrualIt

	/**
	 * 	Re-activate
	 * 	@return false
	 */
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setProcessed(false);
		String sql = "UPDATE M_DiscountSchemaBreak SET Processed = 'N' WHERE "
				+ " M_DiscountSchema_ID = ?";
		int ok = DB.executeUpdate(sql, get_ID(), get_TrxName());
		if (ok == -1)
			m_processMsg = "Failed when update discount break.";
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocStatus(DOCSTATUS_InProgress);
		setDocAction(DocAction.ACTION_Complete);
		return true;
	}	//	reActivateIt

	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	public String getSummary()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(":")
			.append(" (Breaks#").append(getBreaks(false).length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	//	getSummary

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg

	/**
	 * 	Get Document Owner (Responsible)
	 *	@return AD_User_ID
	 */
	public int getDoc_User_ID()
	{
		return getCreatedBy();
	}	//	getDoc_User_ID

	/**
	 * 	Get Document Approval Amount
	 *	@return amount
	 */
	public BigDecimal getApprovalAmt()
	{
		return Env.ZERO;
	}	//	getApprovalAmt

	/**
	 * 	Get C_Currency_ID
	 *	@return Accounting Currency
	 */
	public int getC_Currency_ID ()
	{
		return Env.getContextAsInt(getCtx(),"$C_Currency_ID");
	}	//	getC_Currency_ID

	/**
	 * 	Document Status is Complete or Closed
	 *	@return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
			|| DOCSTATUS_Closed.equals(ds)
			|| DOCSTATUS_Reversed.equals(ds);
	}	//	isComplete

	/**
	 * 	Get Document Info
	 *	@return document info (untranslated)
	 */
	public String getDocumentInfo()
	{
		StringBuilder msgreturn = new StringBuilder().append(getDocumentNo()).append(getC_BPartner().getName());
		return msgreturn.toString();
	}	//	getDocumentInfo
	
	
	public boolean isCumulativeDocument()
	{
		String cummulativeType = getCumulativeLevel();
		if(null == cummulativeType)
			return false;
		
		return cummulativeType.equals(CUMULATIVELEVEL_Document);
	}
	

	public boolean isCumulativeLine()
	{
		String cummulativeType = getCumulativeLevel();
		if(null == cummulativeType)
			return false;
		
		return cummulativeType.equals(CUMULATIVELEVEL_Line);
	}
	
	public boolean isFlatDiscountTypePercent()
	{
		String flatDiscountType = getFlatDiscountType();
		if(null == flatDiscountType)
			return false;
		
		return flatDiscountType.equals(FLATDISCOUNTTYPE_Percent);
	}
	
	public boolean isFlatDiscountTypeProductBonus()
	{
		String flatDiscountType = getFlatDiscountType();
		if(null == flatDiscountType)
			return false;
		
		return flatDiscountType.equals(FLATDISCOUNTTYPE_ProductBonuses);
	}
	
	public boolean isFlatDiscountTypeValueCurrency()
	{
		String flatDiscountType = getFlatDiscountType();
		if(null == flatDiscountType)
			return false;
		
		return flatDiscountType.equals(FLATDISCOUNTTYPE_ValueCurrency);
	}
	
	
	public BigDecimal validateBudgetQtyOrValOfOrder(BigDecimal qtyOrVal)
	{
		if(qtyOrVal.compareTo(getAllowedOrder()) > 0)
			qtyOrVal = getAllowedOrder();
		
		return qtyOrVal;
	}
	
	public BigDecimal getAllowedOrder()
	{
		return getQtyAllocated().subtract(getQtyReserved());
	}
	
	public BigDecimal getAllowedShipReceipt()
	{
		return getQtyAllocated().subtract(getMovementQty());
	}
	
	public BigDecimal getAllowedInvoiced()
	{
		return getQtyAllocated().subtract(getQtyInvoiced());
	}
	
	public MUNSSalesBudget[] getSalesBudgetLines(boolean requery)
	{
		if(null != m_salesBudgets && !requery)
		{
			set_TrxName(m_salesBudgets, get_TrxName());
			return m_salesBudgets;
		}
		
		List<MUNSSalesBudget> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID
				, MUNSSalesBudget.Table_Name
				, MUNSSalesBudget.COLUMNNAME_M_DiscountSchema_ID + "=? AND M_DiscountSchemaBreak_ID "
						+ "IS NULL AND UNS_DSBreakLine_ID IS NULL"
				, get_TrxName())
				.setParameters(getM_DiscountSchema_ID()).list();
		
		m_salesBudgets = new MUNSSalesBudget[list.size()];
		list.toArray(m_salesBudgets);
		
		return m_salesBudgets;
	}
	
	/**
	 * Get all completed discount 
	 * @param ctx
	 * @param date
	 * @param AD_Org_ID
	 * @param trxName
	 * @return {@link List<MDiscountSchema>}
	 */
	public static List<MDiscountSchema> getAllComplete(
			Properties ctx, Timestamp date, int AD_Org_ID, String trxName)
	{
		StringBuilder prepareWC = new StringBuilder("(")
		.append(COLUMNNAME_AD_Org_ID).append(" = ? OR ")
		.append(COLUMNNAME_AD_Org_ID).append(" = ?) AND ")
		.append(COLUMNNAME_DocStatus).append(" = ? ")
		.append(" AND '").append(new SimpleDateFormat("yyyy-MM-dd").format(date))
		.append("' BETWEEN ")
		.append(MDiscountSchema.COLUMNNAME_ValidFrom).append(" AND ")
		.append(" COALESCE(").append(MDiscountSchema.COLUMNNAME_ValidTo)
		.append(", now() + INTERVAL '365 days')");
		
		String whereClause = prepareWC.toString();
		List<Object> params = new ArrayList<Object>();
		
		params.add(AD_Org_ID);
		params.add(0);
		params.add(DOCSTATUS_Completed);
		
		List<MDiscountSchema> list = Query.get(
				ctx, UNSOrderModelFactory.EXTENSION_ID, Table_Name
				, whereClause, trxName).setParameters(params)
				.setOrderBy(COLUMNNAME_AD_Org_ID).list();
		
		return list;
	}
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	public static List<MDiscountSchema> getAllCompleteByDateAndOrg(MOrder order)
	{
		StringBuilder prepareWC = new StringBuilder("(")
		.append(COLUMNNAME_AD_Org_ID).append(" = ? OR ")
		.append(COLUMNNAME_AD_Org_ID).append(" = ?) AND ")
		.append(COLUMNNAME_DocStatus).append(" = ? ")
		.append(" AND '").append(new SimpleDateFormat("yyyy-MM-dd").format(order.getDatePromised()))
		.append("' BETWEEN ")
		.append(MDiscountSchema.COLUMNNAME_ValidFrom).append(" AND ")
		.append(" COALESCE(").append(MDiscountSchema.COLUMNNAME_ValidTo)
		.append(", now() + INTERVAL '365 days')");
		
		String whereClause = prepareWC.toString();
		List<Object> params = new ArrayList<Object>();
		
		params.add(order.getAD_Org_ID());
		params.add(0);
		params.add(DOCSTATUS_Completed);
		
		List<MDiscountSchema> list = Query.get(
				order.getCtx(), UNSOrderModelFactory.EXTENSION_ID, Table_Name
				, whereClause, order.get_TrxName()).setParameters(params)
				.setOrderBy(COLUMNNAME_AD_Org_ID).list();
		
		return list;
	}
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	public static List<MDiscountSchema> getAllDiscountSchemabyDate(MOrder order)
	{
		ArrayList<MDiscountSchema> list = new ArrayList<MDiscountSchema>();

		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append(MDiscountSchema.Table_Name).append("_ID FROM ").append(MDiscountSchema.Table_Name)
				.append(" WHERE ").append(MDiscountSchema.COLUMNNAME_DocStatus).append("='")
				.append(MDiscountSchema.DOCSTATUS_Completed).append("' AND '")
				.append(new SimpleDateFormat("yyyy-MM-dd").format(order.getDatePromised()))
				.append("' BETWEEN ").append(MDiscountSchema.COLUMNNAME_ValidFrom).append(" AND ")
				.append(" COALESCE(").append(MDiscountSchema.COLUMNNAME_ValidTo)
				.append(", now() + INTERVAL '365 days')");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), order.get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MDiscountSchema(order.getCtx(), rs.getInt(1), order.get_TrxName()));
			}
		}
		catch (SQLException e)
		{
			s_log.log(Level.SEVERE, sql.toString(), e);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return list;
	}
	
	
	public boolean isBudgetAvailable (UNSDiscountBonus discountBonus) {
		
		if (getBudgetType() == null || MDiscountSchemaBreak.BUDGETTYPE_NonBudgeted.equals(getBudgetType()))
			return true;
		BigDecimal comparator = discountBonus.getLineNetAmount();

		BigDecimal availableBudget = Env.ZERO;
		if (MDiscountSchemaBreak.BUDGETTYPE_GeneralBudget.equals(getBudgetType())) {
			if (MOrder.Table_ID == discountBonus.getTable_ID() || MOrderLine.Table_ID == discountBonus.getTable_ID()) {
				availableBudget = getAllowedOrder();
			} else if (MInvoice.Table_ID == discountBonus.getTable_ID() || MInvoiceLine.Table_ID == discountBonus.getTable_ID()) {
				availableBudget = getAllowedInvoiced();
			} else if (MInOut.Table_ID == discountBonus.getTable_ID() || MInOutLine.Table_ID == discountBonus.getTable_ID()) {
				availableBudget = getAllowedShipReceipt();
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
	
	public List<MDiscountSchemaBreak> getListBreaks(boolean reload)
	{
		if (m_listBreaks != null && !reload)
			return m_listBreaks;

		String sql = "SELECT * FROM M_DiscountSchemaBreak WHERE M_DiscountSchema_ID=? AND IsActive='Y' ORDER BY SeqNo";
		m_listBreaks = new ArrayList<MDiscountSchemaBreak>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getM_DiscountSchema_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				m_listBreaks.add(new MDiscountSchemaBreak(getCtx(), rs, get_TrxName()));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return m_listBreaks;
	} // getBreaks
	
	public MDiscountSchema[] getForAnalyzeDuplicate ()
	{
		List<Object> params = new ArrayList<Object>();
		StringBuilder prepareWC = new StringBuilder(
				COLUMNNAME_M_DiscountSchema_ID).append(" <> ? AND ")
			.append(COLUMNNAME_DocStatus).append(" = ? AND ")
			.append(COLUMNNAME_CumulativeLevel).append(" = ? ");
		params.add(get_ID());
		params.add(DOCSTATUS_Completed);
		params.add(getCumulativeLevel());
		if (isPickup())
		{
			prepareWC.append(" AND ").append(COLUMNNAME_IsPickup)
			.append(" = ? ");
			params.add(isPickup() ? "Y" : "N");
		}
		if (isCashPayment())
		{
			prepareWC.append(" AND ").append(COLUMNNAME_IsCashPayment)
			.append(" = ? ");
			params.add(isCashPayment() ? "Y" : "N");
		}
		
		if (getAD_Org_ID() == 0)
		{
			String orgEv = getorganizationaleffectiveness();
			if (orgEv.equals(
					ORGANIZATIONALEFFECTIVENESS_ExcludeSelectedOrganization))
			{
				//Exclude AND Exclude
				prepareWC
				.append(" AND CASE WHEN ").append(COLUMNNAME_AD_Org_ID)
				.append(" > 0 THEN ").append(COLUMNNAME_AD_Org_ID)
				.append(" NOT IN (SELECT ").append(MUNSDiscountOrg.
						COLUMNNAME_AD_OrgTrx_ID)
				.append(" FROM ").append(MUNSDiscountOrg.Table_Name)
				.append(" WHERE ").append(MUNSDiscountOrg.
						COLUMNNAME_M_DiscountSchema_ID).append(" = ")
				.append(get_ID()).append(")")
				
				//Exclude And Exclude
				.append(" WHEN ").append(COLUMNNAME_organizationaleffectiveness)
				.append(" = '").append(
						ORGANIZATIONALEFFECTIVENESS_ExcludeSelectedOrganization)
				.append("' THEN EXISTS (SELECT * " ).append(" FROM ")
				.append(MOrg.Table_Name).append(" WHERE ")
				.append(MOrg.COLUMNNAME_AD_Org_ID).append(" NOT IN ")
				.append("(SELECT ").append(MUNSDiscountOrg.
						COLUMNNAME_AD_OrgTrx_ID)
				.append(" FROM ").append(MUNSDiscountOrg.Table_Name)
				.append(" WHERE ").append(MUNSDiscountOrg.
						COLUMNNAME_M_DiscountSchema_ID)
				.append(" = ").append(get_ID()).append(") AND ").append(MOrg.
						COLUMNNAME_AD_Org_ID)
				.append(" NOT IN (SELECT ").append(MUNSDiscountOrg.
						COLUMNNAME_AD_OrgTrx_ID)
				.append(" FROM ").append(MUNSDiscountOrg.Table_Name)
				.append(" WHERE ").append(MUNSDiscountOrg.
						COLUMNNAME_M_DiscountSchema_ID)
				.append(" = ").append(Table_Name).append(".")
				.append(COLUMNNAME_M_DiscountSchema_ID).append("))")
				
				//Include And Exclude
				.append(" WHEN ").append(COLUMNNAME_organizationaleffectiveness)
				.append(" = '").append(
						ORGANIZATIONALEFFECTIVENESS_IncludedSelectedOrganization)
				.append("' THEN EXISTS (SELECT * " ).append(" FROM ")
				.append(MUNSDiscountOrg.Table_Name).append(" WHERE ")
				.append(MUNSDiscountOrg.COLUMNNAME_AD_OrgTrx_ID)
				.append(" NOT IN ").append("(SELECT ").append(MUNSDiscountOrg.
						COLUMNNAME_AD_OrgTrx_ID)
				.append(" FROM ").append(MUNSDiscountOrg.Table_Name)
				.append(" WHERE ").append(MUNSDiscountOrg.
						COLUMNNAME_M_DiscountSchema_ID)
				.append(" = ").append(get_ID()).append(") AND ")
				.append(MUNSDiscountOrg.COLUMNNAME_M_DiscountSchema_ID)
				.append(" = ").append(Table_Name).append(".")
				.append(COLUMNNAME_M_DiscountSchema_ID)
				.append(") ELSE 1=2 END ");
				
			}
			else if (orgEv.equals(
					ORGANIZATIONALEFFECTIVENESS_IncludedSelectedOrganization))
			{
				prepareWC
				.append(" AND CASE WHEN ").append(COLUMNNAME_AD_Org_ID)
				.append(" > 0 THEN ").append(COLUMNNAME_AD_Org_ID)
				.append(" IN (SELECT ").append(MUNSDiscountOrg.
						COLUMNNAME_AD_OrgTrx_ID)
				.append(" FROM ").append(MUNSDiscountOrg.Table_Name)
				.append(" WHERE ").append(MUNSDiscountOrg.
						COLUMNNAME_M_DiscountSchema_ID).append(" = ")
				.append(get_ID()).append(") ")
				
				//Include and Exclude
				.append(" WHEN ").append(COLUMNNAME_organizationaleffectiveness)
				.append(" = '").append(
						ORGANIZATIONALEFFECTIVENESS_ExcludeSelectedOrganization)
				.append("' THEN EXISTS (SELECT * " ).append(" FROM ")
				.append(MUNSDiscountOrg.Table_Name).append(" WHERE ")
				.append(MUNSDiscountOrg.COLUMNNAME_AD_OrgTrx_ID)
				.append(" NOT IN ").append("(SELECT ").append(MUNSDiscountOrg.
						COLUMNNAME_AD_OrgTrx_ID)
				.append(" FROM ").append(MUNSDiscountOrg.Table_Name)
				.append(" WHERE ").append(MUNSDiscountOrg.
						COLUMNNAME_M_DiscountSchema_ID)
				.append(" = ").append(Table_Name).append(".")
				.append(COLUMNNAME_M_DiscountSchema_ID).append(") AND ")
				.append(MUNSDiscountOrg.COLUMNNAME_M_DiscountSchema_ID)
				.append(" = ").append(get_ID()).append(")")
				
				//Include And Included
				.append(" WHEN ").append(COLUMNNAME_organizationaleffectiveness)
				.append(" = '").append(
						ORGANIZATIONALEFFECTIVENESS_IncludedSelectedOrganization)
				.append("' THEN EXISTS (SELECT * " ).append(" FROM ")
				.append(MUNSDiscountOrg.Table_Name).append(" WHERE ")
				.append(MUNSDiscountOrg.COLUMNNAME_AD_OrgTrx_ID)
				.append(" IN ").append("(SELECT ").append(MUNSDiscountOrg.
						COLUMNNAME_AD_OrgTrx_ID)
				.append(" FROM ").append(MUNSDiscountOrg.Table_Name)
				.append(" WHERE ").append(MUNSDiscountOrg.
						COLUMNNAME_M_DiscountSchema_ID)
				.append(" = ").append(Table_Name).append(".")
				.append(COLUMNNAME_M_DiscountSchema_ID).append(") AND ")
				.append(MUNSDiscountOrg.COLUMNNAME_M_DiscountSchema_ID)
				.append(" = ").append(get_ID()).append(" ) ELSE 1=2 END ");
			}
			else
			{
				prepareWC.append(" AND 1=2 ");
			}
		}
		else
		{
			prepareWC
			.append(" AND CASE WHEN ").append(COLUMNNAME_AD_Org_ID)
			.append(" > 0 THEN ").append(COLUMNNAME_AD_Org_ID)
			.append(" = ").append(getAD_Org_ID())
			
			//Include and Exclude
			.append(" WHEN ").append(COLUMNNAME_organizationaleffectiveness)
			.append(" = '").append(
					ORGANIZATIONALEFFECTIVENESS_ExcludeSelectedOrganization)
			.append("' THEN NOT EXISTS (SELECT * " ).append(" FROM ")
			.append(MUNSDiscountOrg.Table_Name).append(" WHERE ")
			.append(MUNSDiscountOrg.COLUMNNAME_AD_OrgTrx_ID)
			.append(" = ").append(getAD_Org_ID()).append(" AND ")
			.append(MUNSDiscountOrg.COLUMNNAME_M_DiscountSchema_ID)
			.append(" = ").append(Table_Name).append(".")
			.append(COLUMNNAME_M_DiscountSchema_ID).append(")")
			
			//Include And Included
			.append(" WHEN ").append(COLUMNNAME_organizationaleffectiveness)
			.append(" = '").append(
					ORGANIZATIONALEFFECTIVENESS_IncludedSelectedOrganization)
			.append("' THEN EXISTS (SELECT * " ).append(" FROM ")
			.append(MUNSDiscountOrg.Table_Name).append(" WHERE ")
			.append(MUNSDiscountOrg.COLUMNNAME_AD_OrgTrx_ID)
			.append(" = ").append(getAD_Org_ID())
			.append(" AND ").append(MUNSDiscountOrg.
					COLUMNNAME_M_DiscountSchema_ID)
			.append(" = ").append(Table_Name).append(".")
			.append(COLUMNNAME_M_DiscountSchema_ID).append(" ) ELSE 1=2 END ");
		}
		
		prepareWC.append(" AND ").append(COLUMNNAME_IsSOTrx).append(" = ? ");
		params.add(isSOTrx() ? "Y" : "N");
		
		if (getValidFrom() != null && getValidTo() != null)
		{
			prepareWC.append(" AND (").append(COLUMNNAME_ValidFrom)
			.append(" BETWEEN ? AND ?  OR ").append(COLUMNNAME_ValidTo)
			.append(" BETWEEN ? AND ? OR ? BETWEEN ")
			.append(COLUMNNAME_ValidFrom).append(" AND ")
			.append(COLUMNNAME_ValidTo).append(" OR ? BETWEEN ")
			.append(COLUMNNAME_ValidFrom).append(" AND ")
			.append(COLUMNNAME_ValidTo).append(") ");
			
			params.add(getValidFrom());
			params.add(getValidTo());
			params.add(getValidFrom());
			params.add(getValidTo());
			params.add(getValidFrom());
			params.add(getValidTo());
		}
		else if (getValidFrom() != null)
		{
			prepareWC.append(" AND ").append(COLUMNNAME_ValidTo).append(" >= ?");
			params.add(getValidFrom());
		}
		
		String whereClause = prepareWC.toString();
		List<MDiscountSchema> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, Table_Name, 
				whereClause, get_TrxName()).setParameters(params).
				setOrderBy(COLUMNNAME_AD_Org_ID).list();
		
		MDiscountSchema[] schemas = new MDiscountSchema[list.size()];
		list.toArray(schemas);
		
		return schemas;
	}
	
	
	/**
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @param trxName
	 * @return
	 */
	public static MDiscountSchema[] getAllCompletedOfOrg(
			Properties ctx, Timestamp dateFrom, Timestamp dateTo, 
			int AD_Org_ID, String trxName)
	{
		StringBuilder prepareWC = new StringBuilder("(")
		.append(COLUMNNAME_AD_Org_ID).append(" = ? OR ")
		.append(COLUMNNAME_AD_Org_ID).append(" = ?) AND ")
		.append(COLUMNNAME_DocStatus).append(" = ? AND ")
		.append(COLUMNNAME_IsSOTrx).append(" = ?");
		
		if (dateFrom != null && dateTo != null)
		{
			prepareWC.append(" AND ").append(COLUMNNAME_ValidFrom)
			.append(" BETWEEN '").append(dateFrom).append("' AND '")
			.append(dateTo).append("'");
		}
		else if (dateFrom != null)
		{
			prepareWC.append(" AND ").append(COLUMNNAME_ValidFrom)
			.append(" >= '").append(dateFrom).append("'");
		}
		
		String whereClause = prepareWC.toString();
		List<Object> params = new ArrayList<Object>();
		params.add(AD_Org_ID);
		params.add(0);
		params.add(DOCSTATUS_Completed);
		params.add("N");
		
		List<MDiscountSchema> list = Query.get(
				ctx, UNSOrderModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName)
				.setParameters(params).setOrderBy(COLUMNNAME_AD_Org_ID).list();
		
		MDiscountSchema[] schemas = new MDiscountSchema[list.size()];
		list.toArray(schemas);
		
		return schemas;
	}
	
	public String compareWithMyBreaks(MDiscountSchema schema)
	{
		StringBuilder duplicateMsg = new StringBuilder();
		for(MDiscountSchemaBreak schemaBreak : getBreaks(true))
		{
			if(!isPartnerConflict(schemaBreak))
				continue;
			
			boolean duplicateBySales = isDuplicateBySales(schemaBreak.getSalesLevel(), schemaBreak.getSalesType());
			if(!duplicateBySales)
				continue;
			
			if(schema.getDiscountType().equals(DISCOUNTTYPE_Flat)
					&& schema.getFlatDiscountType().equals(FLATDISCOUNTTYPE_ProductBonuses)
					&& (schemaBreak.getDiscountType().equals(MDiscountSchemaBreak.DISCOUNTTYPE_FlatProductBonuses)
							|| schemaBreak.getDiscountType().equals(MDiscountSchemaBreak.DISCOUNTTYPE_PercentProductBonuses)))
			{
				duplicateMsg.append("Duplicate Discount. Discount Schema ").append(schema.getDocumentNo())
				.append(" & Discount schema ").append(getDocumentNo()).append("on line break ")
				.append(schemaBreak.getSeqNo()).append("\n").append(schema.getDocumentNo());
			}
			
			if(schema.getDiscountType().equals(DISCOUNTTYPE_Breaks))
			{
				duplicateMsg.append(schema.compareWithMyBreaks(schemaBreak));
			}
		}
		
		return duplicateMsg.toString();
	}
	
	/**
	 * 
	 * @param dsBreak
	 * @return
	 */
	public String compareWithMyBreaks(MDiscountSchemaBreak dsBreak)
	{
		StringBuilder prepare = new StringBuilder();
		MDiscountSchemaBreak[] breaks = getBreaks(true);
		for(MDiscountSchemaBreak myBreak : breaks)
		{
			prepare.append(myBreak.compareWithMe(dsBreak));
		}
		
		return prepare.toString();
	}
	
	/**
	 * 
	 * @param qtyVal
	 * @param C_BPartner_ID
	 */
	public void updateReserveQtyVal(BigDecimal qtyVal, int C_BPartner_ID)
	{
		if(getBudgetType().equals(BUDGETTYPE_NonBudgeted))
			return;
		
		if( C_BPartner_ID > 0)
		{
			int retVal = updateReserveSalesBudget(C_BPartner_ID, qtyVal);
			if(retVal > 0)
				return;
		}
		
		BigDecimal tmpReserve = getQtyReserved();
		tmpReserve = tmpReserve.add(qtyVal);
		setQtyReserved(tmpReserve);
		saveEx();
	}
	
	/**
	 * 
	 * @param qtyVal
	 * @param C_BPartner_ID
	 */
	public void updateInvoicedQtyVal(BigDecimal qtyVal, int C_BPartner_ID)
	{
		if(getBudgetType().equals(BUDGETTYPE_NonBudgeted))
			return;
		
		if(C_BPartner_ID > 0)
		{
			int retVal = updateInvoicedSalesBudget(C_BPartner_ID, qtyVal);
			if(retVal > 0)
				return;
		}
		BigDecimal tmpReserve = getQtyInvoiced();
		tmpReserve = tmpReserve.add(qtyVal);
		setQtyInvoiced(tmpReserve);
		saveEx();
	}
	
	/**
	 * 
	 * @param qtyVal
	 * @param C_BPartner_ID
	 */
	public void updateShipReceiptQtyVal(BigDecimal qtyVal, int C_BPartner_ID)
	{
		if(getBudgetType().equals(BUDGETTYPE_NonBudgeted))
			return;
		
		if(C_BPartner_ID > 0)
		{
			int retVal = updateShipReceiptSalesBudget(C_BPartner_ID, qtyVal);
			if(retVal > 0)
				return;
		}
		
		BigDecimal tmpReserve = getMovementQty();
		tmpReserve = tmpReserve.add(qtyVal);
		setMovementQty(tmpReserve);
		saveEx();
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @param qtyVal
	 */
	public int updateReserveSalesBudget(int C_BPartner_ID, BigDecimal qtyVal)
	{
		MUNSSalesBudget[] salesBudgets = getSalesBudgetLines(true);
		for(int i=0; i<salesBudgets.length; i++)
		{
			MUNSSalesBudget salesBudget = salesBudgets[i];
			if(getBudgetType().equals(BUDGETTYPE_CustomerBudget)
					|| getBudgetType().equals(BUDGETTYPE_SalesBudget))
			{
				if(C_BPartner_ID != salesBudget.getC_BPartner_ID())
				continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGradeBudget))
			{
				String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getUNS_Outlet_Grade_ID())
					continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGroupBudget))
			{
				String sql = "SELECT C_BP_Group_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getC_BP_Group_ID())
					continue;
			}
			else if (getBudgetType().equals("OB") && salesBudget.getAD_Org_ID() != C_BPartner_ID) {
				continue;
			}
			
			BigDecimal reservedQty = salesBudget.getQtyReserved().add(qtyVal);
			salesBudget.setQtyReserved(reservedQty);
			salesBudget.saveEx();
			return salesBudgets.length;
		}
		
		if(!isIncludingSubOrdinate())
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
			if(getBudgetType().equals(BUDGETTYPE_CustomerBudget)
					|| getBudgetType().equals(BUDGETTYPE_SalesBudget))
			{
				if(C_BPartner_ID != salesBudget.getC_BPartner_ID())
				continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGradeBudget))
			{
				String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getUNS_Outlet_Grade_ID())
					continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGroupBudget))
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
		
		if(!isIncludingSubOrdinate())
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
			if(getBudgetType().equals(BUDGETTYPE_CustomerBudget)
					|| getBudgetType().equals(BUDGETTYPE_SalesBudget))
			{
				if(C_BPartner_ID != salesBudget.getC_BPartner_ID())
				continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGradeBudget))
			{
				String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
				int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
				if(retVal != salesBudget.getUNS_Outlet_Grade_ID())
					continue;
			}
			else if(getBudgetType().equals(BUDGETTYPE_CustomerGroupBudget))
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
		
		if(!isIncludingSubOrdinate())
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
	 * 
	 * @return
	 */
	public boolean isDiscountMustSameWithSchema()
	{
		if(null == getRequirementType())
			return false;
		
		boolean isTrue = getRequirementType().equals(REQUIREMENTTYPE_MustSameWithThisSchema);
		return isTrue;
	}
	
	/**
	 * 
	 * @param preveouseSchema
	 * @return
	 */
	public String copyBreaksFrom(MDiscountSchema preveouseSchema)
	{
		int currentBreaksLengt = getBreaks(true).length;
		MUNSDiscountBonus[] bonuses = MUNSDiscountBonus.getBonus(this);
		int currentProductBonusLength = bonuses.length;
		boolean deletedCurrent = false;
		boolean aborted = false;
		
		if(currentBreaksLengt > 0 || currentProductBonusLength > 0)
		{
			String question = Msg.getMsg(getCtx(), "DeleteBonusConfirmMsg");
			String title = Msg.getMsg(getCtx(), "DeleteBonusConfirmTitle");
			int retVal = MessageBox.showMsg(this, getCtx(), question, title, MessageBox.YESNOCANCEL, MessageBox.ICONQUESTION);
			if(retVal == MessageBox.RETURN_OK)
			{
				deletedCurrent = true;
			}
			else if(retVal == MessageBox.RETURN_CANCEL)
			{
				aborted = true;
			}
		}
		
		if(aborted)
		{
			Trx trx = Trx.get(get_TrxName(), false);
			trx.rollback();
			return "Process Aborted";
		}
		
		if(deletedCurrent)
		{
			if(currentBreaksLengt > 0)
			{
				for(MDiscountSchemaBreak dsBreak : getBreaks(false))
				{
					dsBreak.deleteEx(true);
				}
			}
			if(currentProductBonusLength > 0)
			{
				for(MUNSDiscountBonus bonus : bonuses)
				{
					bonus.deleteEx(true);
				}
			}
			
		}
		
		if(!preveouseSchema.getDocStatus().equals(DOCSTATUS_Closed))
		{
			String q = Msg.getMsg(getCtx(), "ClosePreviousSchemaMsg");
			String t = Msg.getMsg(getCtx(), "ClosePreviousSchemaTitle");
			int retVal = MessageBox.showMsg(this, getCtx(), q, t, MessageBox.YESNOCANCEL, MessageBox.ICONQUESTION);
			if(retVal == MessageBox.RETURN_YES)
			{
				preveouseSchema.processIt(DOCACTION_Close);
				preveouseSchema.saveEx();
			}
			else if (MessageBox.RETURN_CANCEL == retVal) 
			{
				Trx trx = Trx.get(get_TrxName(), false);
				trx.rollback();
				return "Process Aborted";
			}
		}
		
		for(MDiscountSchemaBreak dsBreak : preveouseSchema.getBreaks(true))
		{
			MDiscountSchemaBreak currentBreak = new MDiscountSchemaBreak(this);
			currentBreak.copyValuesFrom(dsBreak);
		}
		
		for(MUNSDiscountBonus bonus : MUNSDiscountBonus.getBonus(preveouseSchema))
		{
			MUNSDiscountBonus newBonus = new MUNSDiscountBonus(preveouseSchema);
			newBonus.copyValuesOf(bonus);
			newBonus.saveEx();
		}
		
		return null;
	}
	
	public String copyFromPrevDiscount()
	{
		MDiscountSchema preveousSchema = new MDiscountSchema(getCtx(), getPreviousSchema_ID(), get_TrxName());
		PO.copyValues(preveousSchema, this);
		setAD_Org_ID(preveousSchema.getAD_Org_ID());
		saveEx();
		
		if(preveousSchema.getDiscountType().equals(MDiscountSchema.DISCOUNTTYPE_Breaks))
		{
			copyBreaksFrom(preveousSchema);
		}
		
		if(isSOTrx())
		{
			copyCustomerList(preveousSchema);
		}
		
		if(null != preveousSchema.getBudgetType()
				&& !preveousSchema.getBudgetType().equals(BUDGETTYPE_NonBudgeted)
				&& !preveousSchema.getBudgetType().equals(BUDGETTYPE_GeneralBudget))
		{
			MUNSSalesBudget[] salesesBudget = preveousSchema.getSalesBudgetLines(false);
			for(MUNSSalesBudget budget : salesesBudget)
			{
				MUNSSalesBudget newBudget = new MUNSSalesBudget(this, budget.getQtyAllocated());
				PO.copyValues(budget, newBudget);
				
				newBudget.saveEx();
			}
		}
		
		if(null != preveousSchema.getorganizationaleffectiveness()
				&& (preveousSchema.getorganizationaleffectiveness().equals(ORGANIZATIONALEFFECTIVENESS_ExcludeSelectedOrganization)
						|| preveousSchema.getorganizationaleffectiveness().equals(ORGANIZATIONALEFFECTIVENESS_IncludedSelectedOrganization)))
		{
			MUNSDiscountOrg[] prevOrgs = preveousSchema.getSelectionOrgs();
			for(MUNSDiscountOrg prevOrg : prevOrgs)
			{
				MUNSDiscountOrg newOrg = new MUNSDiscountOrg(this);
				PO.copyValues(prevOrg, newOrg);
				newOrg.saveEx();
			}
		}
		
		return null;
	}
	
	public void copyCustomerList(MDiscountSchema preveous)
	{
		MUNSDiscountCustomer[] prevCustomerList = preveous.getListCustomer(true);
		
		if(null == prevCustomerList || prevCustomerList.length == 0)
			return;
		
		String question = Msg.getMsg(getCtx(), "DeletePreviousCustomerListMsg");
		String title = Msg.getMsg(getCtx(), "DeletePreviousCustomerListTitle");
		
		if(getListCustomer(true).length > 0)
		{
			int retVal = MessageBox.showMsg(this,
					getCtx(), question, title, MessageBox.YESNO, MessageBox.ICONQUESTION);
			if(retVal == MessageBox.RETURN_YES)
			{
				for(MUNSDiscountCustomer customer : getListCustomer(false))
				{
					customer.delete(true);
				}
			}
			
			for(MUNSDiscountCustomer prevCustomer : prevCustomerList)
			{
				MUNSDiscountCustomer current = new MUNSDiscountCustomer(this);
				current.setC_BPartner_ID(prevCustomer.getC_BPartner_ID());
				current.setC_BP_Group_ID(prevCustomer.getC_BP_Group_ID());
				current.setIsActive(prevCustomer.isActive());
				current.saveEx();
			}
		}
	}
	
	public void checkMaxValue(MUNSDiscountTrx discountTrx)
	{
		StringBuilder errorMsg = new StringBuilder();
		if(discountTrx.getFlatValueDiscount().compareTo(getFlatDiscount()) > 0)
		{
			errorMsg.append("Flat value discount [").append(discountTrx.getFlatValueDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ")
			.append("can't bigger than schema flat value [").append(getFlatDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]");
		}
		else if(discountTrx.getFirstDiscount().compareTo(getFlatDiscount()) > 0)
		{
			errorMsg.append("Discount 1st % [").append(discountTrx.getFirstDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than % value of schema [").append(getFlatDiscount()
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
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSDiscountCustomer[] getListCustomer(boolean requery)
	{
		if(null != m_listCustomer && !requery)
		{
			set_TrxName(m_listCustomer, get_TrxName());
			return m_listCustomer;
		}
		
		List<MUNSDiscountCustomer> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSDiscountCustomer.Table_Name
				, MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchema_ID + "=?"
				, get_TrxName()).setParameters(get_ID()).list();
		
		m_listCustomer = new MUNSDiscountCustomer[list.size()];
		list.toArray(m_listCustomer);
		
		return m_listCustomer;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @param isSOTrx
	 * @return
	 */
	public boolean isValidCustomerDiscount(MBPartner partner)
	{
		if(null == getSelectionType())
		{
			return true;
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedAll))
		{
			return true;
		}
		else if(getSelectionType().equals(SELECTIONTYPE_Vendor))
		{
			return getC_BPartner_ID() == partner.get_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_VendorGroup))
		{
			return getC_BP_Group_ID() == partner.getC_BP_Group_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return partner.get_ID() == getC_BPartner_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return partner.getUNS_Outlet_Grade_ID() == getUNS_Outlet_Grade_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return partner.getC_BP_Group_ID() == getC_BP_Group_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return isSelected(partner.get_ID(), MUNSDiscountCustomer.COLUMNNAME_C_BPartner_ID);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return isSelected(partner.getUNS_Outlet_Grade_ID(), MUNSDiscountCustomer.COLUMNNAME_UNS_Outlet_Grade_ID);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return isSelected(partner.getC_BP_Group_ID(), MUNSDiscountCustomer.COLUMNNAME_C_BP_Group_ID);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludedOnceCustomerGrade))
		{
			return partner.getUNS_Outlet_Grade_ID() != getUNS_Outlet_Grade_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			return partner.getC_BPartner_ID() != getC_BPartner_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			return partner.getC_BP_Group_ID() != getC_BP_Group_ID();
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return !isSelected(partner.get_ID(), MUNSDiscountCustomer.COLUMNNAME_C_BPartner_ID);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return !isSelected(partner.getUNS_Outlet_Grade_ID(), MUNSDiscountCustomer.COLUMNNAME_UNS_Outlet_Grade_ID);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return !isSelected(partner.getC_BP_Group_ID(), MUNSDiscountCustomer.COLUMNNAME_C_BP_Group_ID);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 
	 * @param m_Order
	 * @return
	 */
	public boolean isValidCustomerDiscount(I_DiscountModel model)
	{
		if(!getBudgetType().equals(BUDGETTYPE_NonBudgeted) && getAllowedOrder().signum() <= 0)
		{
			return false;
		}
		else if(model.isSOTrx() != isSOTrx())
		{
			return false;
		}
		else if(!isValidCustomerDiscount(model.getBPartner()))
		{
			return false;
		}
		else if(isSOTrx())
		{
			if(getSalesLevel() == null && getSalesType() == null)
			{
				return true;
			}
			else if(model.getSalesRep() == null)
			{
				return false;
			}
			else if(getSalesType() != null && !getSalesType().equals(model.getSalesRep().getSalesType()))
			{
				return false;
			}
			else if(getSalesLevel() != null && !getSalesLevel().equals(model.getSalesRep().getSalesLevel()))
			{
				if(!isIncludingSubOrdinate())
				{
					return false;
				}
				else if(!model.getSalesRep().myParentLevelIs(getSalesLevel()))
				{
					return false;
				}
				else
				{
					return true;
				}
			}
			else
			{
				return true;
			}
		}
		else
		{
			return true;
		}
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{		
		return  super.afterSave(newRecord, success);
	}
	
	/**
	 * 
	 * @param parameter
	 * @param columnName
	 * @return
	 */
	public boolean isSelected(int parameter, String columnName)
	{
		String sql = "SELECT 1 FROM UNS_Discount_Customer WHERE "
				.concat(columnName).concat("=? AND ")
				.concat(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchema_ID)
				.concat("=?");
		int val = DB.getSQLValue(get_TrxName(), sql, parameter, get_ID());
		return val > 0;
	}
	
	
	/**
	 * Get Included Business Partner Group
	 * @param requery
	 * @return
	 */
	public MBPGroup[] getIncludedGroups(boolean requery)
	{
		if(null != m_includedGroups && !requery)
		{
			set_TrxName(m_includedGroups, get_TrxName());
			return m_includedGroups;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			WhereClause.append(MBPGroup.COLUMNNAME_C_BP_Group_ID)
			.append(" NOT IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			WhereClause.append(MBPGroup.COLUMNNAME_C_BP_Group_ID)
			.append(" NOT IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchema_ID).append(" = ")
			.append(get_ID()).append(")");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			WhereClause.append(MBPGroup.COLUMNNAME_C_BP_Group_ID)
			.append(" IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(")");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			WhereClause.append(MBPGroup.COLUMNNAME_C_BP_Group_ID)
			.append(" IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_C_BP_Group_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchema_ID).append(" = ")
			.append(get_ID()).append(")");
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
		if(null != m_includedGrades && !requery)
		{
			set_TrxName(m_includedGrades, get_TrxName());
			return m_includedGrades;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		if(getSelectionType().equals(SELECTIONTYPE_ExcludedOnceCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" NOT IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") ");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" NOT IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchema_ID).append(" = ")
			.append(get_ID()).append(")");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(")");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			WhereClause.append(MBPartner.COLUMNNAME_UNS_Outlet_Grade_ID)
			.append(" IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_UNS_Outlet_Grade_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchema_ID).append(" = ")
			.append(get_ID()).append(")");
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
		if(null != m_includedPartners && !requery)
		{
			set_TrxName(m_includedPartners, get_TrxName());
			return m_includedPartners;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		if(getBudgetType().equals(BUDGETTYPE_SalesBudget))
		{
			if(getSalesType() != null)
			{
				WhereClause.append(MBPartner.COLUMNNAME_SalesType)
				.append("='").append(getSalesType()).append("'");
			}
			else if(getSalesLevel() != null)
			{
				if(getSalesType() != null)
					WhereClause.append(" AND ");
				
				WhereClause.append(MBPartner.COLUMNNAME_SalesLevel)
				.append("='").append(getSalesLevel()).append("'");
			}
			
			if(getSalesLevel() != null || getSalesType() != null)
				WhereClause.append( " AND ");
		}
		
		WhereClause.append(MBPartner.COLUMNNAME_IsSalesRep)
		.append("='Y'");;	
		
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
	 * Get Included Business Partner.
	 * @param requery
	 * @return
	 */
	public MBPartner[] getIncludedPartners(boolean requery)
	{
		if(null != m_includedPartners && !requery)
		{
			set_TrxName(m_includedPartners, get_TrxName());
			return m_includedPartners;
		}
		
		StringBuilder WhereClause = new StringBuilder();
		
		if(getSelectionType().equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" NOT IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") AND ")
			.append(MBPartner.COLUMNNAME_IsCustomer).append("='Y'");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" NOT IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchema_ID).append(" = ")
			.append(get_ID()).append(") AND ").append(MBPartner.COLUMNNAME_IsCustomer)
			.append("='Y'");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedAll))
		{
			WhereClause.append(MBPartner.COLUMNNAME_IsCustomer).append("='Y'");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" IN (SELECT ").append(Table_Name).append(".")
			.append(COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(Table_Name).append(" WHERE ").append(Table_Name).append(".")
			.append(Table_Name).append("_ID = ").append(get_ID()).append(") AND ")
			.append(MBPartner.COLUMNNAME_IsCustomer).append("='Y'");
		}
		else if(getSelectionType().equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			WhereClause.append(MBPartner.COLUMNNAME_C_BPartner_ID)
			.append(" IN (SELECT ").append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(COLUMNNAME_C_BPartner_ID).append(" FROM ")
			.append(MUNSDiscountCustomer.Table_Name).append(" WHERE ")
			.append(MUNSDiscountCustomer.Table_Name).append(".")
			.append(MUNSDiscountCustomer.COLUMNNAME_M_DiscountSchema_ID).append(" = ")
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
	 * @param C_Bpartner_ID
	 * @return
	 */
	private boolean selectedBPartnerIsMyBPartner(int C_Bpartner_ID)
	{
		return getC_BPartner_ID() == C_Bpartner_ID;
	}
	
	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	private boolean selectedGroupIsMyGroup(int C_BP_Group_ID)
	{
		return getC_BP_Group_ID() == C_BP_Group_ID;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean selectedGradeIsMyGrade(int UNS_Outlet_Grade_ID)
	{
		return getUNS_Outlet_Grade_ID() == UNS_Outlet_Grade_ID;
	}

	/**
	 * Chekc selected business partner is in this Grade
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean selectedBPartnerIsInMyGrade(int C_BPartner_ID)
	{
		String sql = "SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
		return retVal == getUNS_Outlet_Grade_ID();
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean selectedBPartnerIsInMySelectedGrade(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID IN ("
				+ " SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ get_TableName() + "_ID = ?) AND C_BPartner_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), C_BPartner_ID);
		return retVal > 0;
	}
	
	/**
	 * Ceck selected buseness partner is in this group.
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean selectedBPartnerIsInMyGroup(int C_BPartner_ID)
	{
		String sql = "SELECT C_BP_Group_ID FROM C_BPartner WHERE C_BPartner_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID);
		return retVal == getC_BP_Group_ID();
	}
	
	public boolean selectedBPartnerIsInMySelectedGroup(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID IN ("
				+ "SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE " + get_TableName()
				+ "_ID = ?) AND C_BPartner_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), C_BPartner_ID);
		return retVal > 0;
	}
	
	public boolean selectedBPartnerIsInMySelectedBPartner(int C_BPartner_ID)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BPartner_ID = ?"
				+ " AND C_BPartner_ID IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
		//unselected 
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsInMyGrade(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsInMySelectedGrade(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ?"
				+ " AND UNS_Outlet_Grade_ID IN (SELECT UNS_Outlet_Grade_ID FROM "
				+ " UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsInMyGroup(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsInMySelectedGroup(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ?"
				+ " AND C_BP_Group_ID IN (SELECT C_BP_Group_ID FROM "
				+ " UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsInMySelectedBPartner(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? "
				+ " AND C_BPartner_ID IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql,C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean selectedGradeIsInMySelectedGrade(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Discount_Customer "
				+ " WHERE " + get_TableName() + "_ID = ? AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), UNS_Outlet_Grade_ID);
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean selectedGroupIsInMySelectedGroup(int C_BP_Group_ID)
	{
		String sql = "SELECT C_BP_Group_ID FROM UNS_Discount_Customer "
				+ " WHERE " + get_TableName() + "_ID = ? AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), C_BP_Group_ID);
		return retVal > 0;
	}

	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean someUnselectedGroupIsInMySelectedGroup(int C_BP_Group_ID)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_Group WHERE C_BP_Group_ID "
				+ " IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer "
				+ " WHERE " + get_TableName() + "_ID = ?) AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), C_BP_Group_ID);
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean someUnselectedGroupIsNotInMySelectedGroup(int C_BP_Group_ID)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_Group WHERE C_BP_Group_ID "
				+ " NOT IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer "
				+ " WHERE " + get_TableName() + "_ID = ?) AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), C_BP_Group_ID);
		return retVal > 0;
	}
	
	/**
	 * Check some business partner on a selected grade is in this business partner group
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean someBPartnerOnGradeIsInMyGroup(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID = ? AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getC_BP_Group_ID(), UNS_Outlet_Grade_ID);
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedGradeIsInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID "
				+ " NOT IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) " + " AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedGradeIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID "
				+ " NOT IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) " + " AND C_BP_Group_ID IN (SELECT "
				+ " C_BP_Group_ID FROM UNS_DIscountCustomer WHERE " + Table_Name + "_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerNotOnSelectedGradeIsNotInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID "
				+ " NOT IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) " + " AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerOnSelectedBPartnerIsInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerOnSelectedBPartnerIsInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGradeOnSelectedGradeIsInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade WHERE UNS_Outlet_Grade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGradeNotOnSelectedGradeIsNotInMySelectedGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade WHERE UNS_Outlet_Grade_ID NOT IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID NOT IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ Table_Name + "_ID =?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGradeNotOnSelectedGradeIsInMySelectedGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade WHERE UNS_Outlet_Grade_ID NOT IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ Table_Name + "_ID =?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}

	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsNotInMySelectedGrade(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ?"
				+ " AND UNS_Outlet_Grade_ID NOT IN (SELECT UNS_Outlet_Grade_ID FROM "
				+ " UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsNotInMySelectedGroup(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ?"
				+ " AND C_BP_Group_ID NOT IN (SELECT C_BP_Group_ID FROM "
				+ " UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_Bpartner_ID
	 * @return
	 */
	private boolean someUnselectedBPartnerIsNotInMyGrade(int C_Bpartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_Bpartner_ID, getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsNotInMyGroup(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BPartner_ID, getC_BP_Group_ID());
		return retVal > 0;
	}	
	

	
	/**
	 * Check some business partner on a selected group is not in this grade.
	 * @param C_BP_Group_ID
	 * @return
	 */
	private boolean someBPartnerOnGroupIsNotInMyGrade(int C_BP_Group_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID = ? AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BP_Group_ID, getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	private boolean someBPartnerOnGradeIsNotInMyGroup(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID <> ? AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getC_BP_Group_ID(), UNS_Outlet_Grade_ID);
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	public boolean someBPartnerNotOnGradeIsNotInMyGroup(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID <> ? AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getC_BP_Group_ID(), UNS_Outlet_Grade_ID);
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	private boolean someBPartnerOnGradeIsNotInMySelectedGroup(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID "
				+ " NOT IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ Table_Name + "_ID = ?) AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), UNS_Outlet_Grade_ID);
		return retVal > 0;
	}	
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean someUnselectedBPartnerIsNotInMySelectedBPartner(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? "
				+ " AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql,C_BPartner_ID, get_ID());
		return retVal > 0;
	}
	
	/**
	 * check some business partner on selected business partner of discount schema is not in this grade
	 * @param M_DiscountSchema_ID
	 * @return
	 */
	private boolean someBPartnerOnSelectedBPartnerIsNotInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	private boolean someBPartnerOnSelectedBPartnerIsNotInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerOnSelectedGroupIsInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID IN "
				+ " (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerOnSelectedGroupIsNotInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID IN "
				+ " (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerNotOnSelectedGroupIsNotInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID NOT IN "
				+ " (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someGradeOnSelectedGradeIsNotInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade WHERE UNS_Outlet_Grade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someGradeOnUnselectedGradeIsNotMyGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade WHERE UNS_Outlet_Grade_ID NOT IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}	
	
	/**
	 * 
	 * @param UNS_Outlet_Grade_ID
	 * @return
	 */
	private boolean someUnselectedGradeIsNotInMyGrade(int UNS_Outlet_Grade_ID)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Outlet_Grade "
				+ " WHERE UNS_Outlet_Grade_ID <> ? AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, UNS_Outlet_Grade_ID, getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	

	private boolean someUnselectedBPartnerIsNotInMyBPartner(int C_BPartner_ID)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID <> ? AND C_BPartner_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getC_BPartner_ID(), C_BPartner_ID);
		return retVal > 0;
	}

	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerOnSelectedBPartnerIsInMySelectedBPartner(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM UNS_Discount_Customer WHERE " 
				+ Table_Name + "_ID = ? AND  C_BPartner_ID IN(SELECT C_BPartner_ID FROM "
				+ " UNS_Discount_Customer WHERE " + po.get_TableName() + "_ID =? )";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerOnSelectedBPartnerIsNotInMySelectedGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID "
				+ " NOT IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE " 
				+ Table_Name + "_ID = ?) AND  C_BPartner_ID IN (SELECT C_BPartner_ID FROM "
				+ " UNS_Discount_Customer WHERE " + po.get_TableName() + "_ID =? )";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsInMySelectedGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID "
				+ " IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE " 
				+ Table_Name + "_ID = ?) AND  C_BPartner_ID IN (SELECT C_BPartner_ID FROM "
				+ " UNS_Discount_Customer WHERE " + po.get_TableName() + "_ID =? )";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsNotInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID "
				+ " NOT IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE " 
				+ Table_Name + "_ID = ?) AND  C_BPartner_ID IN (SELECT C_BPartner_ID FROM "
				+ " UNS_Discount_Customer WHERE " + po.get_TableName() + "_ID =? )";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnSelectedBPartnerIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BP_Group_ID "
				+ " IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE " 
				+ Table_Name + "_ID = ?) AND  C_BPartner_ID IN (SELECT C_BPartner_ID FROM "
				+ " UNS_Discount_Customer WHERE " + po.get_TableName() + "_ID =? )";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean someUnselectedGroupIsInMyGroup(int C_BP_Group_ID)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_GROUP WHERE "
				+ " C_BP_Group_ID <> ? AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BP_Group_ID, getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param C_BP_Group_ID
	 * @return
	 */
	public boolean someUnselectedGroupIsNotInMyGroup(int C_BP_Group_ID)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_GROUP WHERE "
				+ " C_BP_Group_ID <> ? AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, C_BP_Group_ID, getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerNotOnSelectedBPartnerIsNotInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID "
				+ " NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerNotOnSelectedBPartnerIsNotInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID "
				+ " NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	public boolean someBPartnerNotOnSelectedBPartnerIsInMyGrade(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID "
				+ " NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND UNS_Outlet_Grade_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getUNS_Outlet_Grade_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE C_BPartner_ID "
				+ " NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerOnSelectedGradeIsNotInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_OutletGrade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Outlet_Grade WHERE " + po.get_TableName()
				+ "_ID = ?) AND C_BP_Group_ID <> ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	private boolean someBPartnerOnSelectedGradeIsInMyGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_OutletGrade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Outlet_Grade WHERE " + po.get_TableName()
				+ "_ID = ?) AND C_BP_Group_ID == ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), getC_BP_Group_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsInMySelectedBPartner(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BPartner_ID IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBpartnerIsInMySelectedGrade(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE UNS_Outlet_Grade_ID IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBpartnerIsNotInMySelectedGrade(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE UNS_Outlet_Grade_ID NOT IN "
				+ " (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BP_Group_ID IN "
				+ " (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnSelectedBPartnerIsNotInMySelectedGroup(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BP_Group_ID NOT IN "
				+ " (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private boolean someBPartnerNotOnSelectedBPartnerIsNotInMySelectedBPartner(PO po)
	{
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BPartner_ID NOT IN "
				+ " (SELECT C_BPartner_ID FROM UNS_Discount_Customer WHERE " + Table_Name
				+ "_ID =?) AND C_BPartner_ID NOT IN (SELECT C_BPartner_ID FROM UNS_Discount_Customer "
				+ " WHERE " + po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerNotOnselectedGradeIsNotInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID"
				+ " NOT IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID NOT IN (SELECT C_BP_Group_ID "
				+ " FROM UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	private boolean someBPartnerOnSelectedGradeIsInMySelectedGrade(PO po)
	{
		String sql = "SELECT COUNT(UNS_Outlet_Grade_ID) FROM UNS_Discount_Customer WHERE "
				+ Table_Name + "_ID = ? AND UNS_Outlet_Grade_ID IN ("
				+ " SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	private boolean someBPartnerOnSelectedGroupIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM UNS_Discount_Customer WHERE "
				+ Table_Name + "_ID = ? AND C_BP_Group_ID IN ("
				+ " SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, get_ID(), po.get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnselectedGradeIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID"
				+ " IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID IN (SELECT C_BP_Group_ID "
				+ " FROM UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someBPartnerOnselectedGradeIsNotInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE UNS_Outlet_Grade_ID"
				+ " IN (SELECT UNS_Outlet_Grade_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID NOT IN (SELECT C_BP_Group_ID "
				+ " FROM UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}

	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGroupNotOnSelectedGroupIsInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_Group WHERE C_BP_Group_ID "
				+ " NOT IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID IN (SELECT C_BP_Group_ID "
				+ " FROM UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public boolean someGroupNotOnSelectedGroupIsNotInMySelectedGroup(PO po)
	{
		String sql = "SELECT COUNT(C_BP_Group_ID) FROM C_BP_Group WHERE C_BP_Group_ID "
				+ " NOT IN (SELECT C_BP_Group_ID FROM UNS_Discount_Customer WHERE "
				+ po.get_TableName() + "_ID = ?) AND C_BP_Group_ID NOT IN (SELECT C_BP_Group_ID "
				+ " FROM UNS_Discount_Customer WHERE " + Table_Name + "_ID = ?)";
		int retVal = DB.getSQLValue(get_TrxName(), sql, po.get_ID(), get_ID());
		return retVal > 0;
	}
	
	
	/**
	 * Check Duplicate of Discount Schema. Compare all completed discount
	 * schema with current active discount schema. While duplicate founded and 
	 * the comparator discount type is break we will be compare current active 
	 * discount with break schema of comparator. While current active discount 
	 * is discount with type break, break schema of current active discount 
	 * will be compare to comparator and break of comparator
	 * Throw Exception if duplicate is found.
	 */
	public void checkDuplicate()
	{
		if(!getDocStatus().equals(DOCSTATUS_Drafted)
		&& !getDocStatus().equals(DOCSTATUS_InProgress)
		&& !getDocStatus().equals(DOCSTATUS_Invalid))
		{
			return;
		}
		
		MDiscountSchema[] arraySchema = getForAnalyzeDuplicate();
		
		StringBuilder duplicateMessage = new StringBuilder();
		
		for(MDiscountSchema schema : arraySchema)
		{
//			if(get_ID() == schema.get_ID())
//			{
//				continue;
//			}
//			if(isSOTrx() != schema.isSOTrx())
//			{
//				continue;
//			}
//			if(schema.getDocStatus().equals(DOCSTATUS_Closed)
//					|| schema.getDocStatus().equals(DOCSTATUS_Reversed)
//					|| schema.getDocStatus().equals(DOCSTATUS_Voided))
//				continue;
//			if(!getDocStatus().equals(DOCSTATUS_Drafted)
//					&& !getDocStatus().equals(DOCSTATUS_InProgress)
//					&& !getDocStatus().equals(DOCSTATUS_Invalid))
//				return;
//			if(!isOrgConflict(schema))
//			{
//				continue;
//			}
//			if(isPickup() != schema.isPickup() && isCashPayment() != schema.isCashPayment())
//			{
//				continue;
//			}
//			if((schema.getValidTo() != null && getValidFrom().after(schema.getValidTo()))
//					|| (getValidTo() != null && schema.getValidFrom().after(getValidTo())))
//			{
//				continue;
//			}
//			if(!getCumulativeLevel().equals(schema.getCumulativeLevel()))
//			{
//				continue;
//			}
			if(getDiscountType().equals(DISCOUNTTYPE_Flat) 
					&& schema.getDiscountType().equals(DISCOUNTTYPE_Flat) 
					&& !getFlatDiscountType().equals(schema.getFlatDiscountType()))
			{
				continue;
			}
			if(!isPartnerConflict(schema))
			{
				continue;
			}
			boolean duplicateBySales = isDuplicateBySales(schema.getSalesLevel(), schema.getSalesType());
			if(!duplicateBySales)
				continue;
			
			if(schema.getDiscountType().equals(DISCOUNTTYPE_Breaks))
			{
				duplicateMessage.append(schema.compareWithMyBreaks(this));
			}
			else
			{
				duplicateMessage.append("Duplicate discount ").append(getDocumentNo())
				.append("_").append(getName()).append(" with ").append(schema.getDocumentNo())
				.append("_").append(schema.getName());
			}
		}
		
		String msg = duplicateMessage.toString();
		
		if(!Util.isEmpty(msg, true))
		{
			throw new AdempiereException(msg);
		}
	}
	
	
	
	/**
	 * 
	 * @param salesLevel
	 * @param SalesType
	 * @return
	 */
	public boolean isDuplicateBySales(String salesLevel, String SalesType)
	{
		return someSalesRepOnSalesTypeIsInMySalesLevel(SalesType) 
				|| salesTypeIsMySalesType(SalesType)
				|| salesLevelIsMySalesLevel(salesLevel);
	}
	
	/**
	 * 
	 * @param salesType
	 * @return
	 */
	public boolean someSalesRepOnSalesTypeIsInMySalesLevel(String salesType)
	{
		if(null == salesType)
			return true;
		
		String sql = "SELECT COUNT(C_BPartner_ID) FROM C_BPartner WHERE SalesType = ? AND SalesLevel = ?";
		int retVal = DB.getSQLValue(get_TrxName(), sql, salesType, getSalesLevel());
		return retVal > 0;
	}
	
	/**
	 * 
	 * @param SalesType
	 * @return
	 */
	public boolean salesTypeIsMySalesType(String SalesType)
	{
		if(null == SalesType)
			return true;
		else if(null == getSalesType())
			return true;
		
		return getSalesType().equals(SalesType);
	}
	
	/**
	 * 
	 * @param SalesLevel
	 * @return
	 */
	public boolean salesLevelIsMySalesLevel(String SalesLevel)
	{
		if(null == SalesLevel)
			return true;
		else if(null == getSalesLevel())
			return true;
		
		return getSalesLevel().equals(SalesLevel);
	}
	
	/**
	 * Get selection Organization
	 * @return
	 */
	public MUNSDiscountOrg[] getSelectionOrgs()
	{
		return getSelectionOrgs(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSDiscountOrg[] getSelectionOrgs(boolean requery)
	{
		if(null != m_orgs && !requery)
		{
			set_TrxName(m_orgs, get_TrxName());
			return m_orgs;
		}
		
		List<MUNSDiscountOrg> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSDiscountOrg.Table_Name, Table_Name + "_ID = ?"
				, get_TrxName()).setParameters(get_ID()).list();
		
		m_orgs = new MUNSDiscountOrg[list.size()];
		list.toArray(m_orgs);
		
		return m_orgs;
	}
	
	/**
	 * 
	 * @param AD_Org_ID
	 * @return
	 */
	public boolean isInMySelectedOrgs(int AD_Org_ID)
	{
		getSelectionOrgs(false);
		for(MUNSDiscountOrg org : m_orgs)
		{
			if(org.getAD_OrgTrx_ID() != AD_Org_ID)
				continue;
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param schema
	 * @return
	 */
	public boolean selectedOrgsIsInMySelectedOrgs(MDiscountSchema schema)
	{
		MUNSDiscountOrg[] orgs = schema.getSelectionOrgs();
		boolean retValue = false;
		for(MUNSDiscountOrg org : orgs)
		{
			retValue = isInMySelectedOrgs(org.getAD_OrgTrx_ID());
			if(retValue)
				return true;
		}
		
		return retValue;
	}
	
	/**
	 * 
	 * @param schema
	 * @return
	 */
	private boolean unselectedOrgsIsNotInMySelectedOrgs(MDiscountSchema schema)
	{
		String sql = "SELECT COUNT(AD_Org_ID) FROM AD_Org WHERE AD_Org_ID NOT IN"
				+ " (SELECT COALESCE(AD_OrgTrx_ID,-1) FROM UNS_Discount_Org WHERE M_DiscountSchema_ID = ? )"
				+ " AND AD_Org_ID NOT IN"
				+ " (SELECT COALESCE(AD_OrgTrx_ID,-1) FROM UNS_Discount_Org WHERE M_DiscountSchema_ID = ? )";
		int count = DB.getSQLValue(get_TrxName(), sql,  getM_DiscountSchema_ID(), schema.getM_DiscountSchema_ID());
		return count > 0;
	}
	
	/**
	 * 
	 * @param schema
	 * @return
	 */
	private boolean selectedOrgsNotInMySelectedOrgs(MDiscountSchema schema)
	{
		MUNSDiscountOrg[] orgs = schema.getSelectionOrgs();
		int counter = 0;
		for(MUNSDiscountOrg org : orgs)
		{
			if(!isInMySelectedOrgs(org.getAD_OrgTrx_ID()))
				counter++;
		}
		
		return counter > 0;
	}
	
	public boolean isOrgBased()
	{
		return getAD_Org_ID() > 0 || (getorganizationaleffectiveness() != null
				&& (getorganizationaleffectiveness().equals(ORGANIZATIONALEFFECTIVENESS_ExcludeSelectedOrganization)
						|| getorganizationaleffectiveness().equals(ORGANIZATIONALEFFECTIVENESS_IncludedSelectedOrganization)));
	}
	
	/**
	 * 
	 * @param schema
	 * @return
	 */
	public boolean isPartnerConflict(MDiscountSchema schema)
	{
		if(getSelectionType() == null || schema.getSelectionType() == null)
			return true;
		
		String thisSelection = getSelectionType();
		String schemaSelection = schema.getSelectionType();
		
		if (thisSelection.equals(SELECTIONTYPE_IncludedAll) || schemaSelection.equals(SELECTIONTYPE_IncludedAll))
		{
			return true;
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return !selectedGradeIsMyGrade(getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMyGrade(schema.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return someBPartnerOnGroupIsNotInMyGrade(schema.getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMyGrade(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someGradeOnSelectedGradeIsNotInMyGrade(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsNotInMyGrade(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade))
		{
			return someUnselectedGradeIsNotInMyGrade(schema.getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			return someUnselectedBPartnerIsNotInMyGrade(schema.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			
			return schema.someBPartnerNotOnGradeIsNotInMyGroup(getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{	
			return someBPartnerNotOnSelectedBPartnerIsNotInMyGrade(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{		
			return someGradeOnUnselectedGradeIsNotMyGrade(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return someBPartnerNotOnSelectedGroupIsNotInMyGrade(schema);
		}
		//end Execlude One Grade
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schema.someUnselectedBPartnerIsInMyGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsMyBPartner(schema.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.someUnselectedBPartnerIsInMyGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schema.someUnselectedBPartnerIsInMySelectedBPartner(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.someUnselectedBPartnerIsInMySelectedGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someUnselectedBPartnerIsInMySelectedGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			return someUnselectedBPartnerIsNotInMyBPartner(schema.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			return schema.someUnselectedBPartnerIsNotInMyGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return schema.someUnselectedBPartnerIsNotInMySelectedBPartner(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			 return schema.someUnselectedBPartnerIsNotInMySelectedGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schema.someUnselectedBPartnerIsNotInMySelectedGroup(getC_BPartner_ID());
		}
		//END Execlude Once Customer
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return someBPartnerOnGradeIsNotInMyGroup(schema.getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMyGroup(schema.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.someUnselectedGroupIsInMyGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMyGroup(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsNotInMyGroup(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someUnselectedGroupIsInMySelectedGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			return schema.someUnselectedGroupIsNotInMyGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return someBPartnerNotOnSelectedBPartnerIsNotInMyGroup(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return someBPartnerNotOnSelectedGradeIsNotInMyGroup(schema);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schema.someUnselectedGroupIsNotInMySelectedGroup(getC_BP_Group_ID());
		}
		//End Execlude Once Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schema.someBPartnerNotOnSelectedBPartnerIsInMyGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedBPartner(schema.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.someBPartnerNotOnSelectedBPartnerIsInMyGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schema.someBPartnerNotOnSelectedBPartnerIsInMySelectedBPartner(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.someBPartnerNotOnSelectedBpartnerIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someBPartnerNotOnSelectedBPartnerIsInMySelectedGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return someBPartnerNotOnSelectedBPartnerIsNotInMySelectedBPartner(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return schema.someBPartnerNotOnSelectedBpartnerIsNotInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schema.someBPartnerNotOnSelectedBPartnerIsNotInMySelectedGroup(this);
		}
		//End Execlude Selected Customer
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schema.someGradeOnSelectedGradeIsInMyGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedGrade(schema.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.someBPartnerNotOnSelectedGradeIsInMyGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMySelectedGrade(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.someGradeNotOnSelectedGradeIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someBPartnerNotOnSelectedGradeIsInMySelectedGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return someGradeNotOnSelectedGradeIsNotInMySelectedGrade(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schema.someBPartnerNotOnselectedGradeIsNotInMySelectedGroup(this);
		}
		//End Exclude Selected Customer Grade
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return someBPartnerOnGradeIsNotInMySelectedGroup(schema.getUNS_Outlet_Grade_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedGroup(schema.getC_BPartner_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return !selectedGroupIsInMySelectedGroup(schema.getC_BP_Group_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMySelectedGroup(schema);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnselectedGradeIsNotInMySelectedGroup(schema);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someGroupNotOnSelectedGroupIsInMySelectedGroup(this);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return someGroupNotOnSelectedGroupIsNotInMySelectedGroup(schema);
		}
		//End Execlude Selected Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return selectedBPartnerIsMyBPartner(schema.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schema.selectedBPartnerIsInMyGrade(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.selectedBPartnerIsInMyGroup(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schema.selectedBPartnerIsInMySelectedBPartner(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.selectedBPartnerIsInMySelectedGrade(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.selectedBPartnerIsInMySelectedGroup(getC_BPartner_ID());
		}
		//End Included Once Customer
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return selectedGradeIsMyGrade(schema.getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schema.someBPartnerOnGradeIsInMyGroup(getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMyGrade(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.selectedGradeIsInMySelectedGrade(getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsInMyGrade(schema);
		}
		//End Included Once Customer Grade
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return selectedGroupIsMyGroup(schema.getC_BP_Group_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMyGroup(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsInMyGroup(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.selectedGroupIsInMySelectedGroup(getC_BP_Group_ID());
		}
		//End Include Once Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMySelectedBPartner(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schema.someBPartnerOnSelectedBPartnerIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someBPartnerOnSelectedBPartnerIsInMySelectedGroup(this);
		}
		//End Include Selected Customer
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsInMySelectedGrade(schema);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schema.someBPartnerOnselectedGradeIsInMySelectedGroup(this);
		}
		//END Include Selected Customer Grade
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsInMySelectedGroup(schema);
		}
		//End Include Selected Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_Vendor))
		{
			if(schemaSelection.equals(SELECTIONTYPE_Vendor)
					&& getC_BPartner_ID() != schema.getC_BPartner_ID())
			{
				return false;
			}
			else if(schemaSelection.equals(SELECTIONTYPE_VendorGroup)
				&& !schema.selectedBPartnerIsInMyGroup(getC_BP_Group_ID()))
			{
				return false;
			}
		}
		else if(getSelectionType().equals(SELECTIONTYPE_VendorGroup))
		{
			if(schemaSelection.equals(SELECTIONTYPE_Vendor)
					&& !selectedBPartnerIsInMyGroup(schema.getC_BPartner_ID()))
			{
				return false;
			}
			else if(schemaSelection.equals(SELECTIONTYPE_VendorGroup)
					&& getC_BP_Group_ID() != schema.getC_BP_Group_ID())
			{
				return false;
			}
		}
		else
		{
			return schema.isPartnerConflict(this);
		}
		
		return true;
	}
	
	
	/**
	 * 
	 * @param schemaBreak
	 * @return
	 */
	public boolean isPartnerConflict(MDiscountSchemaBreak schemaBreak)
	{
		if(getSelectionType() == null || schemaBreak.getSelectionType() == null)
			return true;
		
		String thisSelection = getSelectionType();
		String schemaSelection = schemaBreak.getSelectionType();
		
		if (thisSelection.equals(SELECTIONTYPE_IncludedAll) || schemaSelection.equals(SELECTIONTYPE_IncludedAll))
		{
			return true;
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return !selectedGradeIsMyGrade(getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMyGrade(schemaBreak.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return someBPartnerOnGroupIsNotInMyGrade(schemaBreak.getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMyGrade(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someGradeOnSelectedGradeIsNotInMyGrade(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsNotInMyGrade(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade))
		{
			return someUnselectedGradeIsNotInMyGrade(schemaBreak.getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			return someUnselectedBPartnerIsNotInMyGrade(schemaBreak.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			
			return schemaBreak.someBPartnerNotOnGradeIsNotInMyGroup(getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{	
			return someBPartnerNotOnSelectedBPartnerIsNotInMyGrade(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{		
			return someGradeOnUnselectedGradeIsNotMyGrade(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return someBPartnerNotOnSelectedGroupIsNotInMyGrade(schemaBreak);
		}
		//end Execlude One Grade
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schemaBreak.someUnselectedBPartnerIsInMyGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsMyBPartner(schemaBreak.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.someUnselectedBPartnerIsInMyGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schemaBreak.someUnselectedBPartnerIsInMySelectedBPartner(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.someUnselectedBPartnerIsInMySelectedGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someUnselectedBPartnerIsInMySelectedGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer))
		{
			return someUnselectedBPartnerIsNotInMyBPartner(schemaBreak.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			return schemaBreak.someUnselectedBPartnerIsNotInMyGroup(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return schemaBreak.someUnselectedBPartnerIsNotInMySelectedBPartner(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			 return schemaBreak.someUnselectedBPartnerIsNotInMySelectedGrade(getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schemaBreak.someUnselectedBPartnerIsNotInMySelectedGroup(getC_BPartner_ID());
		}
		//END Execlude Once Customer
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return someBPartnerOnGradeIsNotInMyGroup(schemaBreak.getUNS_Outlet_Grade_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMyGroup(schemaBreak.getC_BPartner_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.someUnselectedGroupIsInMyGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMyGroup(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsNotInMyGroup(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someUnselectedGroupIsInMySelectedGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup))
		{
			return schemaBreak.someUnselectedGroupIsNotInMyGroup(getC_BP_Group_ID());
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return someBPartnerNotOnSelectedBPartnerIsNotInMyGroup(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return someBPartnerNotOnSelectedGradeIsNotInMyGroup(schemaBreak);
		}
		else if (thisSelection.equals(SELECTIONTYPE_ExcludeOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schemaBreak.someUnselectedGroupIsNotInMySelectedGroup(getC_BP_Group_ID());
		}
		//End Execlude Once Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schemaBreak.someBPartnerNotOnSelectedBPartnerIsInMyGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedBPartner(schemaBreak.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnSelectedBPartnerIsInMyGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schemaBreak.someBPartnerNotOnSelectedBPartnerIsInMySelectedBPartner(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.someBPartnerNotOnSelectedBpartnerIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnSelectedBPartnerIsInMySelectedGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer))
		{
			return someBPartnerNotOnSelectedBPartnerIsNotInMySelectedBPartner(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return schemaBreak.someBPartnerNotOnSelectedBpartnerIsNotInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnSelectedBPartnerIsNotInMySelectedGroup(this);
		}
		//End Execlude Selected Customer
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schemaBreak.someGradeOnSelectedGradeIsInMyGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedGrade(schemaBreak.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnSelectedGradeIsInMyGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMySelectedGrade(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.someGradeNotOnSelectedGradeIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnSelectedGradeIsInMySelectedGroup(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade))
		{
			return someGradeNotOnSelectedGradeIsNotInMySelectedGrade(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerNotOnselectedGradeIsNotInMySelectedGroup(this);
		}
		//End Exclude Selected Customer Grade
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return someBPartnerOnGradeIsNotInMySelectedGroup(schemaBreak.getUNS_Outlet_Grade_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return !selectedBPartnerIsInMySelectedGroup(schemaBreak.getC_BPartner_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return !selectedGroupIsInMySelectedGroup(schemaBreak.getC_BP_Group_ID());
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsNotInMySelectedGroup(schemaBreak);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnselectedGradeIsNotInMySelectedGroup(schemaBreak);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someGroupNotOnSelectedGroupIsInMySelectedGroup(this);
		}
		else if(getSelectionType().equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_ExcludeSelectedCustomerGroup))
		{
			return someGroupNotOnSelectedGroupIsNotInMySelectedGroup(schemaBreak);
		}
		//End Execlude Selected Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomer))
		{
			return selectedBPartnerIsMyBPartner(schemaBreak.getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return schemaBreak.selectedBPartnerIsInMyGrade(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.selectedBPartnerIsInMyGroup(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return schemaBreak.selectedBPartnerIsInMySelectedBPartner(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.selectedBPartnerIsInMySelectedGrade(getC_BPartner_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.selectedBPartnerIsInMySelectedGroup(getC_BPartner_ID());
		}
		//End Included Once Customer
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade))
		{
			return selectedGradeIsMyGrade(schemaBreak.getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return schemaBreak.someBPartnerOnGradeIsInMyGroup(getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMyGrade(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.selectedGradeIsInMySelectedGrade(getUNS_Outlet_Grade_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsInMyGrade(schemaBreak);
		}
		//End Included Once Customer Grade
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup))
		{
			return selectedGroupIsMyGroup(schemaBreak.getC_BP_Group_ID());
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMyGroup(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsInMyGroup(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedOnceCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.selectedGroupIsInMySelectedGroup(getC_BP_Group_ID());
		}
		//End Include Once Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer))
		{
			return someBPartnerOnSelectedBPartnerIsInMySelectedBPartner(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return schemaBreak.someBPartnerOnSelectedBPartnerIsInMySelectedGrade(this);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomer)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerOnSelectedBPartnerIsInMySelectedGroup(this);
		}
		//End Include Selected Customer
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade))
		{
			return someBPartnerOnSelectedGradeIsInMySelectedGrade(schemaBreak);
		}
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGrade)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return schemaBreak.someBPartnerOnselectedGradeIsInMySelectedGroup(this);
		}
		//END Include Selected Customer Grade
		else if(thisSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup)
				&& schemaSelection.equals(SELECTIONTYPE_IncludedSelectedCustomerGroup))
		{
			return someBPartnerOnSelectedGroupIsInMySelectedGroup(schemaBreak);
		}
		//End Include Selected Customer Group
		else if(thisSelection.equals(SELECTIONTYPE_Vendor))
		{
			if(schemaSelection.equals(SELECTIONTYPE_Vendor)
					&& getC_BPartner_ID() != schemaBreak.getC_BPartner_ID())
			{
				return false;
			}
			else if(schemaSelection.equals(SELECTIONTYPE_VendorGroup)
				&& !schemaBreak.selectedBPartnerIsInMyGroup(getC_BP_Group_ID()))
			{
				return false;
			}
		}
		else if(getSelectionType().equals(SELECTIONTYPE_VendorGroup))
		{
			if(schemaSelection.equals(SELECTIONTYPE_Vendor)
					&& !selectedBPartnerIsInMyGroup(schemaBreak.getC_BPartner_ID()))
			{
				return false;
			}
			else if(schemaSelection.equals(SELECTIONTYPE_VendorGroup)
					&& getC_BP_Group_ID() != schemaBreak.getC_BP_Group_ID())
			{
				return false;
			}
		}
		else
		{
			return schemaBreak.isPartnerConflict(this);
		}
		
		return true;
	}
	
	
	
	/**
	 * 
	 * @param schema
	 * @return
	 */
	public boolean isOrgConflict(MDiscountSchema schema)
	{
		if(isOrgBased() && schema.isOrgBased())
		{
			if(schema.getAD_Org_ID() > 0 && this.getAD_Org_ID() > 0
					&& schema.getAD_Org_ID() == this.getAD_Org_ID())
			{
				return true;
			}
			else if(getAD_Org_ID() > 0 && schema.getAD_Org_ID() == 0)
			{
				String schemaOrgEffectiveness = schema.getorganizationaleffectiveness();
				if(schemaOrgEffectiveness.equals(ORGANIZATIONALEFFECTIVENESS_IncludedSelectedOrganization))
				{
					return schema.isInMySelectedOrgs(getAD_Org_ID());
				}
				else if (schemaOrgEffectiveness.equals(ORGANIZATIONALEFFECTIVENESS_ExcludeSelectedOrganization))
				{
					return !isInMySelectedOrgs(getAD_Org_ID());
				}
			}
			else if(schema.getAD_Org_ID() > 0 && getAD_Org_ID() == 0)
			{
				return schema.isOrgConflict(this);
			}
			else
			{
				return compareTwoSelection(schema);
			}
			
		}
		else if(!isOrgBased() && ! schema.isOrgBased()
				&& getorganizationaleffectiveness().equals(schema.getorganizationaleffectiveness()))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param schema
	 * @return
	 */
	private boolean compareTwoSelection(MDiscountSchema schema)
	{
		String thisOrgEffectiveness = getorganizationaleffectiveness();
		String schemaOrgEffectiveness = schema.getorganizationaleffectiveness();
		if(thisOrgEffectiveness.equals(ORGANIZATIONALEFFECTIVENESS_ExcludeSelectedOrganization)
				&& schemaOrgEffectiveness.equals(ORGANIZATIONALEFFECTIVENESS_IncludedSelectedOrganization))
		{
			return selectedOrgsNotInMySelectedOrgs(schema);
		}
		else if(thisOrgEffectiveness.equals(schemaOrgEffectiveness))
		{
			if(thisOrgEffectiveness.equals(ORGANIZATIONALEFFECTIVENESS_ExcludeSelectedOrganization))
			{
				return unselectedOrgsIsNotInMySelectedOrgs(schema);
			}
			else if(thisOrgEffectiveness.equals(ORGANIZATIONALEFFECTIVENESS_IncludedSelectedOrganization))
			{
				return selectedOrgsIsInMySelectedOrgs(schema);
			}
		}
		return schema.compareTwoSelection(this);
	}
	
	public static List<MDiscountSchema> getComplete(
			I_DiscountModel model)
	{
		StringBuilder wc = new StringBuilder(" IsSOTrx = '")
				.append(model.isSOTrx() ? "Y" : "N")
				.append("' AND CASE WHEN AD_Org_ID > ")
				.append("0 THEN AD_Org_ID = ")
				.append(model.getModel().getAD_Org_ID())
				.append(" WHEN organizationaleffectiveness ")
				.append("= 'ESO' THEN NOT EXISTS (SELECT * FROM ")
				.append("uns_discount_org WHERE uns_discount_org.ad_orgtrx_id = ")
				.append(model.getModel().getAD_Org_ID())
				.append("AND uns_discount_org.m_discountschema_id = ")
				.append("m_discountschema.m_discountschema_id) ")
				.append("WHEN m_discountschema.organizationaleffectiveness = ")
				.append("'ISO' THEN EXISTS (SELECT * FROM uns_discount_org ")
				.append("WHERE uns_discount_org.ad_orgtrx_id = ")
				.append(model.getModel().getAD_Org_ID())
				.append("AND uns_discount_org.m_discountschema_id = ")
				.append("m_discountschema.m_discountschema_id) ")
				.append("WHEN m_discountschema.organizationaleffectiveness IN ")
				.append("('WEA','WEB','WOS') THEN 1=1 ELSE 1=2 END ")
				.append("AND DocStatus = 'CO' AND '")
				.append(model.getDateDiscounted()).append("' BETWEEN ")
				.append("ValidFrom AND COALESCE(ValidTo, now() ")
				.append("+ INTERVAL '365 days') AND CASE WHEN ")
				.append("m_discountschema.selectiontype IS NULL THEN 1=1 ")
				.append("WHEN m_discountschema.selectiontype = 'EOC' ")
				.append("THEN C_BPartner_ID <> ").append(model.getBPartner().get_ID())
				.append("WHEN m_discountschema.selectiontype = 'EOCG' ")
				.append("THEN c_bp_group_id <> (SELECT c_bp_group_id FROM ")
				.append("C_BPartner WHERE C_BPartner_ID = ")
				.append(model.getBPartner().get_ID()).append(") ")
				.append("WHEN m_discountschema.selectiontype = 'EOOG' ")
				.append("THEN uns_outlet_grade_id <> (SELECT c_bpartner")
				.append(".uns_outlet_grade_id FROM C_BPartner ")
				.append("WHERE C_BPartner_ID = ")
				.append(model.getBPartner().get_ID()).append(") ")
				.append("WHEN m_discountschema.selectiontype = 'ESC' ")
				.append("THEN NOT EXISTS (SELECT * from uns_discount_customer ")
				.append("where uns_discount_customer.c_bpartner_id = ")
				.append(model.getBPartner().get_ID())
				.append(" AND uns_discount_customer.m_discountschema_id ")
				.append("= m_discountschema.m_discountschema_id) ")
				.append("WHEN m_discountschema.selectiontype = 'ESCG' ")
				.append("THEN NOT EXISTS (SELECT * from uns_discount_customer ")
				.append("where uns_discount_customer.c_bp_group_id = ")
				.append("(SELECT C_BP_Group_ID from c_bpartner where ")
				.append("c_bpartner_id = ").append(model.getBPartner().get_ID())
				.append(") AND uns_discount_customer.m_discountschema_id")
				.append(" = m_discountschema.m_discountschema_id) ")
				.append(" WHEN m_discountschema.selectiontype = 'ESOG' THEN ")
				.append(" NOT EXISTS (SELECT * from uns_discount_customer ")
				.append("where uns_discount_customer.uns_outlet_grade_id = ")
				.append("(SELECT uns_outlet_grade_id from c_bpartner where ")
				.append("c_bpartner_id = ").append(model.getBPartner().get_ID())
				.append(") AND uns_discount_customer")
				.append(".m_discountschema_id = m_discountschema.")
				.append("m_discountschema_id) WHEN m_discountschema.")
				.append("selectiontype = 'IA' THEN 1=1 WHEN m_discountschema")
				.append(".selectiontype = 'IOC' THEN C_BPartner_ID = ")
				.append(model.getBPartner().get_ID())
				.append("WHEN m_discountschema.selectiontype = 'IOCG' THEN ")
				.append("c_bp_group_id = (SELECT c_bp_group_id FROM C_BPartner ")
				.append("WHERE C_BPartner_ID = ")
				.append(model.getBPartner().get_ID())
				.append(") WHEN m_discountschema.")
				.append("selectiontype = 'IOOG' THEN uns_outlet_grade_id ")
				.append("= (SELECT c_bpartner.uns_outlet_grade_id FROM ")
				.append("C_BPartner WHERE C_BPartner_ID = ")
				.append(model.getBPartner().get_ID())
				.append(")  WHEN m_discountschema.selectiontype = 'ISC' ")
				.append("THEN EXISTS (SELECT * from uns_discount_customer ")
				.append("where uns_discount_customer.c_bpartner_id = ")
				.append(model.getBPartner().get_ID())
				.append(" AND uns_discount_customer.m_discountschema_id ")
				.append("= m_discountschema.m_discountschema_id)")
				.append(" WHEN m_discountschema.selectiontype = 'ISCG' ")
				.append("THEN EXISTS (SELECT * from uns_discount_customer ")
				.append("where uns_discount_customer.c_bp_group_id = ")
				.append("(SELECT C_BP_Group_ID from c_bpartner where ")
				.append("c_bpartner_id = ").append(model.getBPartner().get_ID())
				.append(") AND uns_discount_customer.")
				.append("m_discountschema_id = m_discountschema.")
				.append("m_discountschema_id) WHEN m_discountschema.")
				.append("selectiontype = 'ISOG' THEN EXISTS (SELECT * from ")
				.append("uns_discount_customer where uns_discount_customer.")
				.append("uns_outlet_grade_id = (SELECT uns_outlet_grade_id ")
				.append("from c_bpartner where c_bpartner_id = ")
				.append(model.getBPartner().get_ID())
				.append(") AND uns_discount_customer.m_discountschema_id = ")
				.append("m_discountschema.m_discountschema_id) ")
				.append("WHEN m_discountschema.selectiontype = 'V' ")
				.append("THEN C_BPartner_ID = ")
				.append(model.getBPartner().get_ID())
				.append("WHEN m_discountschema.selectiontype = 'VG' ")
				.append("THEN C_BP_Group_ID = (SELECT C_BP_Group_ID FROM ")
				.append("C_BPartner WHERE C_BPartner_ID = ").append(model.getBPartner().get_ID())
				.append(") ELSE 1=2 END AND CASE WHEN m_discountschema.")
				.append("discounttype = 'F' THEN 1=1 WHEN m_discountschema.")
				.append("discounttype = 'B' THEN EXISTS (SELECT * FROM ")
				.append("m_discountschemabreak WHERE m_discountschemabreak.")
				.append("m_discountschema_id = m_discountschema.")
				.append("m_discountschema_id AND CASE WHEN ")
				.append("m_discountschemabreak.selectiontype IS NULL ")
				.append("AND m_discountschema.selectiontype IS NULL THEN 1=2 ")
				.append("WHEN m_discountschema.selectiontype IS NULL AND  ")
				.append("m_discountschemabreak.selectiontype = 'EOC' ")
				.append("THEN C_BPartner_ID <> ")
				.append(model.getBPartner().get_ID())
				.append(" WHEN m_discountschema.selectiontype IS NULL AND  ")
				.append("m_discountschemabreak.selectiontype = 'EOCG' ")
				.append("THEN c_bp_group_id <> (SELECT c_bp_group_id FROM ")
				.append("C_BPartner WHERE C_BPartner_ID = ")
				.append(model.getBPartner().get_ID()).append(") ")
				.append("WHEN m_discountschema.selectiontype IS NULL AND  ")
				.append("m_discountschemabreak.selectiontype = 'EOOG' ")
				.append("THEN uns_outlet_grade_id <> (SELECT c_bpartner.")
				.append("uns_outlet_grade_id FROM C_BPartner WHERE ")
				.append("C_BPartner_ID = ")
				.append(model.getBPartner().get_ID()).append(") WHEN m_discountschema.")
				.append("selectiontype IS NULL AND  m_discountschemabreak.")
				.append("selectiontype = 'ESC' THEN NOT EXISTS (SELECT * ")
				.append("from uns_discount_customer where ")
				.append("uns_discount_customer.c_bpartner_id = ")
				.append(model.getBPartner().get_ID())
				.append("AND uns_discount_customer.m_discountschemabreak_id ")
				.append("= m_discountschemabreak.m_discountschemabreak_id) ")
				.append("WHEN m_discountschema.selectiontype IS NULL AND  ")
				.append("m_discountschemabreak.selectiontype = 'ESCG' THEN ")
				.append("NOT EXISTS (SELECT * from uns_discount_customer ")
				.append("where uns_discount_customer.c_bp_group_id = ")
				.append("(SELECT C_BP_Group_ID from c_bpartner where ")
				.append("c_bpartner_id = ").append(model.getBPartner().get_ID())
				.append(") AND uns_discount_customer")
				.append(".m_discountschemabreak_id = m_discountschemabreak.")
				.append("m_discountschemabreak_id) WHEN m_discountschema.")
				.append("selectiontype IS NULL AND  m_discountschemabreak.")
				.append("selectiontype = 'ESOG' THEN NOT EXISTS (SELECT * ")
				.append("from uns_discount_customer where uns_discount_customer")
				.append(".uns_outlet_grade_id = (SELECT uns_outlet_grade_id ")
				.append("from c_bpartner where c_bpartner_id = ")
				.append(model.getBPartner().get_ID()).append(") AND ")
				.append("uns_discount_customer.m_discountschemabreak_id = " )
				.append("m_discountschemabreak.m_discountschemabreak_id)")
				.append("WHEN m_discountschema.selectiontype IS NULL AND  ")
				.append("m_discountschemabreak.selectiontype = 'IA' THEN 1=1 ")
				.append("WHEN m_discountschema.selectiontype IS NULL AND  ")
				.append("m_discountschemabreak.selectiontype = 'IOC' THEN ")
				.append("C_BPartner_ID = ").append(model.getBPartner().get_ID())
				.append(" WHEN m_discountschema.")
				.append("selectiontype IS NULL AND  m_discountschemabreak.")
				.append("selectiontype = 'IOCG' THEN c_bp_group_id = ")
				.append("(SELECT c_bp_group_id FROM C_BPartner WHERE ")
				.append("C_BPartner_ID = ").append(model.getBPartner().get_ID())
				.append(") WHEN m_discountschema.")
				.append("selectiontype IS NULL AND  m_discountschemabreak.")
				.append("selectiontype = 'IOOG' THEN uns_outlet_grade_id = ")
				.append("(SELECT c_bpartner.uns_outlet_grade_id FROM ")
				.append("C_BPartner WHERE C_BPartner_ID = ")
				.append(model.getBPartner().get_ID()).append(") ")
				.append("WHEN m_discountschema.selectiontype IS NULL AND  ")
				.append("m_discountschemabreak.selectiontype = 'ISC' ")
				.append("THEN EXISTS (SELECT * from uns_discount_customer ")
				.append("where uns_discount_customer.c_bpartner_id = ")
				.append(model.getBPartner().get_ID())
				.append(" AND uns_discount_customer.")
				.append("m_discountschemabreak_id = m_discountschemabreak.")
				.append("m_discountschemabreak_id) WHEN m_discountschema.")
				.append("selectiontype IS NULL AND  m_discountschemabreak.")
				.append("selectiontype = 'ISCG' THEN EXISTS (SELECT * ")
				.append("from uns_discount_customer where ")
				.append("uns_discount_customer.c_bp_group_id = (SELECT ")
				.append("C_BP_Group_ID from c_bpartner where c_bpartner_id =")
				.append(model.getBPartner().get_ID())
				.append(") AND uns_discount_customer.")
				.append("m_discountschemabreak_id = m_discountschemabreak.")
				.append("m_discountschemabreak_id) WHEN m_discountschema.")
				.append("selectiontype IS NULL AND  m_discountschemabreak.")
				.append("selectiontype = 'ISOG' THEN EXISTS (SELECT * ")
				.append("from uns_discount_customer where ")
				.append("uns_discount_customer.uns_outlet_grade_id = (SELECT ")
				.append("uns_outlet_grade_id from c_bpartner where ")
				.append("c_bpartner_id = ").append(model.getBPartner().get_ID())
				.append(") AND uns_discount_customer.")
				.append("m_discountschemabreak_id = m_discountschemabreak.")
				.append("m_discountschemabreak_id) WHEN m_discountschema.")
				.append("selectiontype IS NULL AND  m_discountschemabreak.")
				.append("selectiontype = 'V' THEN C_BPartner_ID = ")
				.append(model.getBPartner().get_ID())
				.append("WHEN m_discountschema.selectiontype IS NULL AND  ")
				.append("m_discountschemabreak.selectiontype = 'VG' ")
				.append("THEN C_BP_Group_ID = (SELECT C_BP_Group_ID FROM ")
				.append("C_BPartner WHERE C_BPartner_ID = ")
				.append(model.getBPartner().get_ID()).append(") ")
				.append("ELSE 1=1 END AND CASE WHEN m_discountschemabreak.")
				.append("productselection = 'IAP' THEN 1=1 WHEN ");
				
		if (model.getModel().get_TableName().equals(MOrder.Table_Name))
		{
			wc
			.append("m_discountschemabreak.productselection = 'IOP' ")
			.append("THEN m_discountschemabreak.m_product_id IN (SELECT ")
			.append("M_Product_ID FROM C_OrderLine WHERE C_Order_ID = ")
			.append(model.getModel().get_ID())
			.append(" AND IsActive = 'Y') WHEN ")
			.append("m_discountschemabreak.productselection = 'IOPC' ")
			.append("THEN m_discountschemabreak.m_product_category_id IN ")
			.append("(SELECT m_product_category_id from m_product where ")
			.append("M_Product_ID IN (SELECT M_Product_ID FROM C_OrderLine ")
			.append("WHERE C_Order_ID = ")
			.append(model.getModel().get_ID()).append(" AND IsActive = 'Y')) ")
			.append("WHEN m_discountschemabreak.productselection = 'ISP' ")
			.append("THEN EXISTS (SELECT * FROM uns_discount_product where ")
			.append("uns_discount_product.m_discountschemabreak_id = ")
			.append("m_discountschemabreak.m_discountschemabreak_id AND  ")
			.append("m_product_id IN (SELECT M_Product_ID FROM ")
			.append("C_OrderLine WHERE C_Order_ID = ")
			.append(model.getModel().get_ID()).append(" AND IsActive = ")
			.append("'Y')) WHEN m_discountschemabreak.productselection = ")
			.append("'ISPC' THEN EXISTS (SELECT * FROM uns_discount_product ")
			.append("where uns_discount_product.m_discountschemabreak_id = ")
			.append("m_discountschemabreak.m_discountschemabreak_id AND  ")
			.append("m_product_category_id IN (SELECT m_product_category_id ")
			.append("from m_product where m_product_id IN (SELECT ")
			.append("M_Product_ID FROM C_OrderLine WHERE C_Order_ID = ")
			.append(model.getModel().get_ID())
			.append(" AND IsActive = 'Y'))) ELSE 1=2 END ");
		}
		else if (model.getModel().get_TableName().equals(MInvoice.Table_Name))
		{
			wc
			.append("m_discountschemabreak.productselection = 'IOP' ")
			.append("THEN m_discountschemabreak.m_product_id IN (SELECT ")
			.append("M_Product_ID FROM C_InvoiceLine WHERE C_Invoice_ID = ")
			.append(model.getModel().get_ID())
			.append(" AND IsActive = 'Y') WHEN ")
			.append("m_discountschemabreak.productselection = 'IOPC' ")
			.append("THEN m_discountschemabreak.m_product_category_id IN ")
			.append("(SELECT m_product_category_id from m_product where ")
			.append("M_Product_ID IN (SELECT M_Product_ID FROM C_InvoiceLine ")
			.append("WHERE C_Invoice_ID = ")
			.append(model.getModel().get_ID()).append(" AND IsActive = 'Y')) ")
			.append("WHEN m_discountschemabreak.productselection = 'ISP' ")
			.append("THEN EXISTS (SELECT * FROM uns_discount_product where ")
			.append("uns_discount_product.m_discountschemabreak_id = ")
			.append("m_discountschemabreak.m_discountschemabreak_id AND  ")
			.append("m_product_id IN (SELECT M_Product_ID FROM ")
			.append("C_InvoiceLine WHERE C_Invoice_ID = ")
			.append(model.getModel().get_ID()).append(" AND IsActive = ")
			.append("'Y')) WHEN m_discountschemabreak.productselection = ")
			.append("'ISPC' THEN EXISTS (SELECT * FROM uns_discount_product ")
			.append("where uns_discount_product.m_discountschemabreak_id = ")
			.append("m_discountschemabreak.m_discountschemabreak_id AND  ")
			.append("m_product_category_id IN (SELECT m_product_category_id ")
			.append("from m_product where m_product_id IN (SELECT ")
			.append("M_Product_ID FROM C_InvoiceLine WHERE C_Invoice_ID = ")
			.append(model.getModel().get_ID())
			.append(" AND IsActive = 'Y'))) ELSE 1=2 END ");
		}
		wc.append(") ELSE 1=2 END ");
		
		List<MDiscountSchema> list = Query.get(
				Env.getCtx(), UNSOrderModelFactory.EXTENSION_ID, 
				Table_Name, wc.toString(), model.get_TrxName()).
				setOrderBy(COLUMNNAME_AD_Org_ID).list();
		
		return list;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) 
	{
		if ("CO".equals(docStatus))
		{
			options[index++] = DocAction.ACTION_ReActivate;
		}
		return index;
	}
}
