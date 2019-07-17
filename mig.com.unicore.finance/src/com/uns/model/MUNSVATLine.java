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
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MInvoiceTax;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.model.MUNSVATDocNo;
import com.unicore.model.X_UNS_VATDocNo;
import com.uns.base.model.MInvoice;

/**
 * @author Burhani Adam
 *
 */
public class MUNSVATLine extends X_UNS_VATLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9103910944548612580L;

	/**
	 * @param ctx
	 * @param UNS_VATLine_ID
	 * @param trxName
	 */
	public MUNSVATLine(Properties ctx, int UNS_VATLine_ID, String trxName) {
		super(ctx, UNS_VATLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSVATLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSVATLine(MUNSVAT parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setUNS_VAT_ID(parent.get_ID());
	}
	
	public void setInvoice(MInvoice inv)
	{
		setAD_Org_ID(inv.getAD_Org_ID());
		setC_Invoice_ID(inv.get_ID());
		setC_BPartner_ID(inv.getC_BPartner_ID());
		MBPartnerLocation loc = new MBPartnerLocation(getCtx(), inv.getC_BPartner_Location_ID(), get_TrxName());
		setC_BPartner_Location_ID(loc.get_ID());
		if(!Util.isEmpty(loc.getTaxName(), true))
			setTaxName(loc.getTaxName());
		else
			setTaxName(inv.getC_BPartner().getName());
		
		if(!Util.isEmpty(loc.getTaxAddress(), true))
			setTaxAddress(loc.getTaxAddress());
		else
		{
			if(loc.getC_Location().getC_City_ID() > 0)
			{
				setTaxAddress(loc.getC_Location().getC_City().getName());
			}
			else
			{
				String sql = "SELECT C_City_ID FROM C_Location WHERE C_Location_ID = "
						+ " (SELECT C_Location_ID FROM AD_OrgInfo WHERE AD_Org_ID = ?)";
				int siti = DB.getSQLValue(get_TrxName(), sql, inv.getAD_Org_ID());
				if(siti <= 0)
					throw new AdempiereException("Please set city in partner location or organization location."
							+ " Invoice " + inv.getDocumentNo());
				else
				{
					org.compiere.model.MCity city = org.compiere.model.MCity.get(getCtx(), siti);
					setTaxAddress(city.getName());
				}
			}
		}			
		
		if(!Util.isEmpty(loc.getTaxSerialNo(), true))
			setTaxSerialNo(inv.getC_BPartner_Location().getTaxSerialNo());
		else
			setTaxSerialNo("000000000000000");
		
		setReferenceNo(inv.getDocumentNo());
		setDateInvoiced(inv.getDateInvoiced());
		setGrandTotal(inv.getGrandTotal());
	}
	
	public void copyFrom(MUNSVAT vat, MUNSVATLine from)
	{
		copyValues(from, this);
		setUNS_VAT_ID(vat.get_ID());
//		setBeforeTaxAmt(from.getRevisionBeforeTaxAmt());
//		setTaxAmt(from.getRevisionTaxAmt());
		setGrandTotal(from.getRevisionAmt());
//		setReference_ID(from.get_ID());
		setBeforeTaxAmt(Env.ZERO);
		setTaxAmt(Env.ZERO);
//		setGrandTotal(Env.ZERO);
		setReference_ID(from.get_ID());
		setDifferenceTaxAmt(Env.ZERO);
		setisReplacement(true);
	}
	
	protected boolean beforeSave(boolean newRecord)
	{		
		if(newRecord)
		{
			String sql = "SELECT COALESCE(Max(LineNo),0) + 10 FROM UNS_VATLine WHERE UNS_VAT_ID = ?";
			int line = DB.getSQLValue(get_TrxName(), sql, getUNS_VAT_ID());
			setLineNo(line);
		}
		
		if(m_ignoreValidate)
			return true;
		
		if(!newRecord)
			setRevisionAmt(getRevisionBeforeTaxAmt().add(getRevisionTaxAmt()));
		
		if(getGrandTotal().signum() == 0)
			setGrandTotal(getBeforeTaxAmt().add(getTaxAmt()));
		
		if(getUNS_VAT().isSOTrx() && !getUNS_VAT().isCreditMemo() &&
				!m_auto && (newRecord || is_ValueChanged(COLUMNNAME_TaxInvoiceNo)))
		{
			MUNSVATDocNo newDn = null;
			MUNSVATDocNo oldDn = null;
			if(get_ValueOld(COLUMNNAME_TaxInvoiceNo) == null)
			{
				newDn = MUNSVATDocNo.getByTaxNo(getCtx(), getUNS_VAT().getAD_Org_ID(), getTaxInvoiceNo().trim(), get_TrxName());
				if(newDn != null && newDn.getUsageStatus().equals("NU"))
				{
					newDn.setUNS_VATLine_ID(get_ID());
					newDn.setUsageStatus("U");
					newDn.setC_Invoice_ID(getC_Invoice_ID());
					if(!newDn.save())
					{
						log.saveError("Error", "failed update VAT Document No");
						return false;
					}
				}
				else
				{
					log.saveError("Error", "Tax Invoice No unregistered or has used / void, please recheck");
					return false;
				}
			}
			else if(!Util.isEmpty(getTaxInvoiceNo(), true))
			{
				newDn = MUNSVATDocNo.getByTaxNo(getCtx(), getUNS_VAT().getAD_Org_ID(), getTaxInvoiceNo().trim(), get_TrxName());
				if(newDn != null && newDn.getUsageStatus().equals("NU"))
				{
					oldDn = MUNSVATDocNo.getByVATLine(getCtx(), get_ID(), get_TrxName());
					if(oldDn != null && newDn.get_ID() != oldDn.get_ID())
					{
						newDn.setUNS_VATLine_ID(get_ID());
						newDn.setUsageStatus("U");
						newDn.setC_Invoice_ID(getC_Invoice_ID());
						oldDn.setUsageStatus("NU");
						oldDn.setC_Invoice_ID(-1);
						oldDn.setUNS_VATLine_ID(-1);
						if(!newDn.save() || !oldDn.save())
						{
							log.saveError("Error", "failed update VAT Document No");
							return false;
						}
					}
				}
				else
				{
					log.saveError("Error", "Tax Invoice No unregistered or has used / void, please recheck");
					return false;
				}
			}
			else
			{
				oldDn = MUNSVATDocNo.getByVATLine(getCtx(), get_ID(), get_TrxName());
				if(oldDn != null)
				{
					oldDn.setUsageStatus("NU");
					oldDn.setC_Invoice_ID(-1);
					oldDn.setUNS_VATLine_ID(-1);
					if(!oldDn.save())
					{
						log.saveError("Error", "failed update VAT Document No");
						return false;
					}
				}
			}
		}
		return true;
	}
	
	protected boolean afterSave(boolean newRecord, boolean success)
	{		
		if(!success)
			return false;
		
		if(m_ignoreValidate)
			return true;
		
		if((isReCreateLines() || newRecord) && getC_Invoice_ID() > 0)
		{
			if(!isReplacement())
			{
				MInvoice inv = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
				MInvoiceTax[] taxs = MInvoiceTax.getByInvoice(getCtx(), inv.get_ID(), get_TrxName());
				BigDecimal rate = Env.ZERO;
				boolean isTaxIncluded = false;
				for(int i=0;i<taxs.length;i++)
				{
					if(taxs[i].getC_Tax_ID() == getUNS_VAT().getC_Tax_ID())
					{
						rate = taxs[i].getC_Tax().getRate();
						isTaxIncluded = taxs[i].isTaxIncluded();
						break;
					}
				}
				org.compiere.model.MInvoiceLine[] lines = inv.getLines(null, I_C_InvoiceLine.COLUMNNAME_LineNetAmt);
				BigDecimal balance = Env.ONE;
				for(int i=0;i<lines.length;i++)
				{
					BigDecimal propotional = Env.ZERO;
					MUNSVATInvLine vil = new MUNSVATInvLine(getCtx(), 0, get_TrxName());
					vil.setUNS_VATLine_ID(get_ID());
					vil.setIncludeTax(isTaxIncluded);
					if((i+1) == lines.length)
						propotional = balance;
					else
					{
						if(lines[i].getLineNetAmt().signum() != 0)
						{
							propotional = lines[i].getLineNetAmt().
											divide(inv.getGrandTotal(), 1, RoundingMode.HALF_DOWN);
							balance = balance.subtract(propotional);
						}
					}
					vil.setInvoiceLine(lines[i], rate, isTaxIncluded);
					vil.setAuto(true);
					if(!vil.save())
					{
						log.saveError("Error", "Failed when trying create lines for invoice " + inv.getDocumentNo() + ".");
						return false;
					}
				}
			}
			else
			{
				MUNSVATInvLine[] lines = MUNSVATInvLine.getByParent(getCtx(), getReference_ID(), null, get_TrxName());
				for(MUNSVATInvLine line : lines)
				{
					MUNSVATInvLine newLine = new MUNSVATInvLine(getCtx(), 0, get_TrxName());
					copyValues(line, newLine);
					newLine.setUNS_VATLine_ID(get_ID());
					newLine.setAD_Org_ID(line.getAD_Org_ID());
					newLine.setBeforeTaxAmt(line.getRevisionBeforeTaxAmt());
					newLine.setDiscountAmt(line.getRevisionDiscAmt());
					newLine.setTaxAmt(line.getRevisionTaxAmt());
					newLine.setLineNetAmt(line.getRevisionAmt());
					if(!newLine.save())
						throw new AdempiereException("Failed when trying create vat invoice lines");
				}
			}
		}
		
		return upHeader(false, newRecord);
	}
	
	protected boolean beforeDelete()
	{
		MUNSVATDocNo dn = MUNSVATDocNo.getByVATLine(getCtx(), get_ID(), get_TrxName());
		if(dn != null)
		{
			dn.setUNS_VATLine_ID(-1);
			dn.setC_Invoice_ID(-1);
			dn.setUsageStatus(X_UNS_VATDocNo.USAGESTATUS_NotUsed);
			dn.saveEx();
		}
		String sql = "DELETE FROM UNS_VATInvLine WHERE UNS_VATLine_ID=?";
		if(DB.executeUpdate(sql, get_ID(), get_TrxName()) < 0)
			return false;
		
		return upHeader(true, false);
	}
	
	public boolean upHeader(boolean isDelete, boolean newRecord)
	{
		MUNSVAT vat = new MUNSVAT(getCtx(), getUNS_VAT_ID(), get_TrxName());
		
		if(isDelete || !newRecord)
		{
			vat.setBeforeTaxAmt(vat.getBeforeTaxAmt()
					.subtract((BigDecimal) get_ValueOld(X_UNS_VATLine.COLUMNNAME_BeforeTaxAmt)));
			vat.setTaxAmt(vat.getTaxAmt()
					.subtract((BigDecimal) get_ValueOld(X_UNS_VATLine.COLUMNNAME_TaxAmt)));
			vat.setGrandTotal(vat.getGrandTotal()
					.subtract((BigDecimal) get_ValueOld(X_UNS_VATLine.COLUMNNAME_GrandTotal)));
			vat.setRevisionBeforeTaxAmt(vat.getRevisionBeforeTaxAmt()
					.subtract((BigDecimal) get_ValueOld(X_UNS_VATLine.COLUMNNAME_RevisionBeforeTaxAmt)));
			vat.setRevisionTaxAmt(vat.getRevisionTaxAmt()
					.subtract((BigDecimal) get_ValueOld(X_UNS_VATLine.COLUMNNAME_RevisionTaxAmt)));
			vat.setRevisionAmt(vat.getRevisionAmt()
					.subtract((BigDecimal) get_ValueOld(X_UNS_VATLine.COLUMNNAME_RevisionAmt)));
			vat.setDifferenceTaxAmt(vat.getDifferenceTaxAmt()
					.subtract((BigDecimal) get_ValueOld(X_UNS_VATLine.COLUMNNAME_DifferenceTaxAmt)));
		}
		if(!isDelete)
		{
			vat.setBeforeTaxAmt(vat.getBeforeTaxAmt().add(getBeforeTaxAmt()));
			vat.setTaxAmt(vat.getTaxAmt().add(getTaxAmt()));
			vat.setGrandTotal(vat.getGrandTotal().add(getGrandTotal()));
			vat.setRevisionBeforeTaxAmt(vat.getRevisionBeforeTaxAmt().add(getRevisionBeforeTaxAmt()));
			vat.setRevisionTaxAmt(vat.getRevisionTaxAmt().add(getRevisionTaxAmt()));
			vat.setRevisionAmt(vat.getRevisionAmt().add(getRevisionAmt()));
			vat.setDifferenceTaxAmt(vat.getDifferenceTaxAmt().add(getDifferenceTaxAmt()));
		}
		
		return vat.save();
	}
	
	public static MUNSVATLine[] getByParent(Properties ctx, int UNS_VAT_ID, String trxName)
	{
		ArrayList<MUNSVATLine> list = new ArrayList<MUNSVATLine>();
		
		String sql = "SELECT vl.UNS_VATLine_ID FROM UNS_VATLine vl"
						+ " INNER JOIN C_Invoice iv ON iv.C_Invoice_ID = vl.C_Invoice_ID"
						+ " WHERE vl.UNS_VAT_ID=?"
						+ " ORDER BY iv.DateInvoiced ASC, iv.DocumentNo ASC";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, UNS_VAT_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MUNSVATLine(ctx, rs.getInt(1),
						trxName));
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException ex) {
			throw new AdempiereException("Unable to load VAT Lines ",
					ex);
		} finally {
			DB.close(rs, pstmt);
		}

		MUNSVATLine[] retValue = new MUNSVATLine[list.size()];
		list.toArray(retValue);
		
		return retValue;
	}
	
	private boolean m_auto = false;
	
	public boolean isAuto()
	{
		return m_auto;
	}
	
	public void setAuto (boolean auto)
	{
		m_auto = auto;
	}
	
	private boolean m_reCreateLines = false;
	
	public boolean isReCreateLines()
	{
		return m_reCreateLines;
	}
	
	public void setReCreateLines(boolean reCreateLines)
	{
		m_reCreateLines = reCreateLines;
	}
	
	private boolean m_ignoreValidate = false;
	public boolean isIgnoreValidate()
	{
		return m_ignoreValidate;
	}
	public void setIgnoreValidation(boolean ignoreValidate)
	{
		m_ignoreValidate = ignoreValidate;
	}
}