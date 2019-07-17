/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSFinanceDocumentFactory;

/**
 * @author Burhani Adam
 *
 */
public class MUNSVATInvLine extends X_UNS_VATInvLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4986322805702176433L;

	/**
	 * @param ctx
	 * @param UNS_VATInvLine_ID
	 * @param trxName
	 */
	public MUNSVATInvLine(Properties ctx, int UNS_VATInvLine_ID, String trxName) {
		super(ctx, UNS_VATInvLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSVATInvLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		if(m_ignoreValidate)
			return true;
		
		if(!isAuto())
		{
			BigDecimal taxRate = Env.ZERO;
			if(getC_InvoiceLine_ID() > 0)
				taxRate = getC_InvoiceLine().getC_Tax().getRate();
			else
				taxRate = getUNS_VATLine().getUNS_VAT().getC_Tax().getRate();
				
			if(newRecord ||
					is_ValueChanged(COLUMNNAME_RevisionBeforeTaxAmt) 
							|| is_ValueChanged(COLUMNNAME_RevisionDiscAmt))
			{
				BigDecimal revBefTaxAmt = getRevisionBeforeTaxAmt().multiply(getQtyInvoiced());
				revBefTaxAmt = revBefTaxAmt.subtract(getRevisionDiscAmt());
				BigDecimal revTaxAmt = Env.ZERO;
				if(taxRate.signum() != 0)
					revTaxAmt = revBefTaxAmt.divide(taxRate);
				else
					revTaxAmt = revBefTaxAmt;
				
				setRevisionTaxAmt(revTaxAmt.setScale(0, RoundingMode.HALF_UP));
				setRevisionAmt(revBefTaxAmt);
				if(getTaxAmt().signum() == 0)
					setTaxAmt(revTaxAmt.setScale(0, RoundingMode.HALF_UP));
				if(getLineNetAmt().signum() == 0)
					setLineNetAmt(revBefTaxAmt);
			}
		}
		setDifferenceTaxAmt(getTaxAmt().subtract(getRevisionTaxAmt()));
		return true;
	}
	
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if(m_ignoreValidate)
			return true;
		
		return upHeader(newRecord);
	}
	
//	public void setInvoiceLine(org.compiere.model.MInvoiceLine line, 
//			BigDecimal taxBaseAmt, BigDecimal tax, BigDecimal propotional, BigDecimal rate,
//			boolean includeTax)
//	{
//		setC_InvoiceLine_ID(line.get_ID());
//		setAD_Org_ID(line.getAD_Org_ID());
//		setLineNo(line.getLine());
//		if(line.getM_Product_ID() > 0)
//			setM_Product_ID(line.getM_Product_ID());
//		else
//			setC_Charge_ID(line.getC_Charge_ID());
//		rate = rate.divide(Env.ONEHUNDRED);
//		setDescription(line.getDescription());
//		setQtyInvoiced(line.getQtyInvoiced());
//		setC_UOM_ID(line.getC_UOM_ID());
//		setC_Tax_ID(line.getC_Tax_ID());
//		if(line.getQtyInvoiced().compareTo(Env.ZERO) == 0)
//		{
//			if(includeTax)
//			{
//				BigDecimal price = line.getPriceList().divide((rate.add(Env.ONE)), 2, RoundingMode.HALF_DOWN);
//				setBeforeTaxAmt(price);
//				setRevisionBeforeTaxAmt(price);
//			}
//			else
//			{
//				setBeforeTaxAmt(line.getPriceList());
//				setRevisionBeforeTaxAmt(line.getPriceList());
//			}
//			setToZero();
//		}
//		else
//		{
//			if(includeTax && line.getDiscountAmt().compareTo(Env.ZERO) != 0)
//			{
//				BigDecimal discAmt = line.getDiscountAmt().divide((rate.add(Env.ONE)), 2, RoundingMode.HALF_DOWN);
//				setDiscountAmt(discAmt);
//				setRevisionDiscAmt(discAmt);
//			}
//			else
//			{
//				setDiscountAmt(line.getDiscountAmt());
//				setRevisionDiscAmt(line.getDiscountAmt());
//			}
//			BigDecimal taxLine = tax.multiply(propotional);
//			setTaxAmt(taxLine);
//			setRevisionTaxAmt(taxLine);
//			BigDecimal dpp = taxLine.divide(rate);
//			setLineNetAmt(dpp);
//			setRevisionAmt(dpp);
//			if(line.getDiscountAmt().compareTo(Env.ZERO) != 0)
//				dpp = dpp.add(getDiscountAmt());
//			BigDecimal price = dpp.divide(line.getQtyInvoiced(), 2, RoundingMode.HALF_DOWN);
//			setBeforeTaxAmt(price);
//			setRevisionBeforeTaxAmt(price);
//		}
//	}
	
	public void setInvoiceLine(org.compiere.model.MInvoiceLine line, BigDecimal rate, boolean includeTax)
	{
		setC_InvoiceLine_ID(line.get_ID());
		setAD_Org_ID(line.getAD_Org_ID());
		setLineNo(line.getLine());
		if(line.getM_Product_ID() > 0)
			setM_Product_ID(line.getM_Product_ID());
		else
			setC_Charge_ID(line.getC_Charge_ID());
		BigDecimal dividen = rate.divide(Env.ONEHUNDRED);
		dividen = dividen.add(Env.ONE);
		setDescription(line.getDescription());
		setQtyInvoiced(line.getQtyInvoiced());
		setC_UOM_ID(line.getC_UOM_ID());
		setC_Tax_ID(line.getC_Tax_ID());
		if(line.getQtyInvoiced().compareTo(Env.ZERO) == 0)
		{
			if(includeTax)
			{
				BigDecimal price = line.getPriceList().divide(dividen, 2, RoundingMode.HALF_DOWN);
				setBeforeTaxAmt(price);
				setRevisionBeforeTaxAmt(price);
			}
			else
			{
				setBeforeTaxAmt(line.getPriceList());
				setRevisionBeforeTaxAmt(line.getPriceList());
			}
			setToZero();
		}
		else
		{
			BigDecimal discAmt = Env.ZERO;
			if(includeTax && line.getDiscountAmt().compareTo(Env.ZERO) != 0)
			{
				discAmt = line.getDiscountAmt().divide(dividen, 2, RoundingMode.HALF_DOWN);
				setDiscountAmt(discAmt);
				setRevisionDiscAmt(discAmt);
			}
			else
			{
				setDiscountAmt(line.getDiscountAmt());
				setRevisionDiscAmt(line.getDiscountAmt());
			}
			BigDecimal dpp = Env.ZERO;
			if(isIncludeTax())
				dpp = line.getLineNetAmt().divide(dividen, 0, RoundingMode.HALF_UP);
			else
				dpp = line.getLineNetAmt();
			setLineNetAmt(dpp);
			setRevisionAmt(dpp);
			BigDecimal taxLine = dpp.divide(rate, 0, RoundingMode.HALF_DOWN);
			setTaxAmt(taxLine);
			setRevisionTaxAmt(taxLine);
			if(line.getDiscountAmt().compareTo(Env.ZERO) != 0)
				dpp = dpp.add(getDiscountAmt());
			BigDecimal price = dpp.divide(line.getQtyInvoiced(), 2, RoundingMode.HALF_UP);
			setBeforeTaxAmt(price);
			setRevisionBeforeTaxAmt(price);
		}
	}
	
	private void setToZero()
	{
		BigDecimal nol = Env.ZERO;
		setTaxAmt(nol);
		setRevisionTaxAmt(nol);
		setLineNetAmt(nol);
		setRevisionAmt(nol);
	}
	
	public static MUNSVATInvLine[] getByParent(Properties ctx, int UNS_VATLine_ID,
			String whereClause, String trxName)
	{
		ArrayList<MUNSVATInvLine> list = new ArrayList<MUNSVATInvLine>();
		
		String sql = "SELECT vl.UNS_VATInvLine_ID FROM UNS_VATInvLine vl"
						+ " WHERE vl.UNS_VATLine_ID=?";
		if(!Util.isEmpty(whereClause, true))
			sql += " AND " + whereClause;
		
		sql += " ORDER BY vl.LineNo ASC";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, UNS_VATLine_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MUNSVATInvLine(ctx, rs.getInt(1),
						trxName));
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException ex) {
			throw new AdempiereException("Unable to load VAT Invoice Lines ",
					ex);
		} finally {
			DB.close(rs, pstmt);
		}

		MUNSVATInvLine[] retValue = new MUNSVATInvLine[list.size()];
		list.toArray(retValue);
		
		return retValue;
	}
	
	private boolean upHeader(boolean newRecord)
	{
		MUNSVATLine line = new MUNSVATLine(getCtx(), getUNS_VATLine_ID(), get_TrxName());
		if(newRecord)
		{
			line.setBeforeTaxAmt(line.getBeforeTaxAmt().add(getLineNetAmt()));
			line.setTaxAmt(line.getTaxAmt().add(getTaxAmt()));
			line.setRevisionBeforeTaxAmt(line.getBeforeTaxAmt());
			line.setRevisionTaxAmt(line.getTaxAmt());
			line.setRevisionAmt(line.getGrandTotal());
			line.setDifferenceTaxAmt(line.getDifferenceTaxAmt().add(getDifferenceTaxAmt()));
		}
		else
		{
			BigDecimal oldRevBeforeTaxAmt = ((BigDecimal) get_ValueOld(COLUMNNAME_RevisionAmt));
			BigDecimal oldRevTaxAmt = (BigDecimal) get_ValueOld(COLUMNNAME_RevisionTaxAmt);
			BigDecimal oldDiffTaxAmt = (BigDecimal) get_ValueOld(COLUMNNAME_DifferenceTaxAmt);
			line.setRevisionBeforeTaxAmt((line.getRevisionBeforeTaxAmt().subtract(oldRevBeforeTaxAmt))
					.add(getRevisionAmt()));
			line.setRevisionTaxAmt((line.getRevisionTaxAmt().subtract(oldRevTaxAmt))
					.add(getRevisionTaxAmt()));
			line.setDifferenceTaxAmt((line.getDifferenceTaxAmt().subtract(oldDiffTaxAmt))
					.add(getDifferenceTaxAmt()));
		}
		
		if(!m_auto)
			line.setIsManual(true);
		
		return line.save();
	}
	
	private boolean m_auto = false;
	
	public boolean isAuto()
	{
		return m_auto;
	}
	
	public void setAuto(boolean auto)
	{
		m_auto = auto;
	}
	
	public BigDecimal AddDiscAmt()
	{
		BigDecimal addDisctAmt = Env.ZERO;
		
		String sql = "SELECT COALESCE(AddDiscountAmt,0) FROM C_Invoice WHERE C_Invoice_ID = ?";
		BigDecimal discOnInv = DB.getSQLValueBD(get_TrxName(), sql, getC_InvoiceLine().getC_Invoice_ID());
		if(discOnInv.signum() == 0)
			return addDisctAmt;
		
		BigDecimal line = getC_InvoiceLine().getLineNetAmt();
		if(line.compareTo(Env.ZERO) <= 0)
			return addDisctAmt;
		BigDecimal grandTotal = getC_InvoiceLine().getC_Invoice().getTotalLines();
		
		BigDecimal propotional = line.divide(grandTotal, 5, RoundingMode.HALF_DOWN);
		addDisctAmt = propotional.multiply(discOnInv);
		
		return addDisctAmt;
	}

	private boolean m_includeTax = false;
	
	public boolean isIncludeTax()
	{
		return m_includeTax;
	}
	
	public void setIncludeTax(boolean includeTax)
	{
		m_includeTax = includeTax;
	}
	
	private boolean m_ignoreValidate = false;
	
	public boolean isIgnoreValidate()
	{
		return m_ignoreValidate;
	}
	
	public void setIgnoreValidate(boolean ignoreValidate)
	{
		m_ignoreValidate = ignoreValidate;
	}
	
	public static MUNSVATInvLine getOfProductParent(Properties ctx, int Parent_ID, int Product_ID, String trxName)
	{
		MUNSVATInvLine invLine = Query.get(ctx, UNSFinanceDocumentFactory.getExtensionID(), Table_Name,
				COLUMNNAME_UNS_VATLine_ID + "=? AND " + COLUMNNAME_M_Product_ID + "=?", trxName)
					.setParameters(Parent_ID, Product_ID).firstOnly();
		
		return invLine;
	}
}