/**
 * 
 */
package com.uns.model.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCharge;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MUNSChargeDetail;
import com.uns.model.MUNSChargeRS;
import com.uns.util.UNSApps;

/**
 * @author Menjangan
 * @see www.untasoft.com
 * 
 */
public class Doc_UNSChargeRS extends Doc {

	private int m_reversalID = 0;
	/**
	 * 
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSChargeRS(MAcctSchema as, ResultSet rs, String trxName) {
	    this(as, MUNSChargeRS.class, rs, null, trxName);
	  }

  /**
   * @param as
   * @param clazz
   * @param rs
   * @param defaultDocumentType
   * @param trxName
   */
  public Doc_UNSChargeRS(MAcctSchema as, Class<?> clazz, ResultSet rs, String defaultDocumentType,
      String trxName) {
    super(as, clazz, rs, defaultDocumentType, trxName);
    // TODO Auto-generated constructor stub
  }

  /**
   * Load Specific Document Details
   * 
   * @return error message or null
   */
  protected String loadDocumentDetails() {
	Timestamp date = null;
	m_reversalID = getPO().get_ValueAsInt("Reversal_ID");
	String sql = "SELECT C_BankAccount_ID FROM C_BankStatement WHERE "
			+ " C_BankStatement_ID = (SELECT C_BankStatement_ID FROM "
			+ " UNS_Charge_Confirmation WHERE ";
	
	if(getPO().get_TableName().equals("UNS_Shipping"))
	{
		date = (Timestamp) getPO().get_Value("DateDoc");
		sql += "UNS_Shipping_ID = ?";
	}
	else if(getPO().get_TableName().equals("UNS_Charge_RS"))
	{
		date = (Timestamp) getPO().get_Value("DateTrx");
		sql += "UNS_Charge_RS_ID = ?";
	}
	
	sql += ")";
	int C_BankAccount_ID = DB.getSQLValue(getTrxName(), sql, m_reversalID > 0 ? 
			getPO().get_ValueAsInt("Reversal_ID") : getPO().get_ID());
	setC_BankAccount_ID(C_BankAccount_ID);

    setDateDoc(date);
    setDateAcct(date);
    setC_BPartner_ID(getPO().get_ValueAsInt("C_BPartner_ID"));
    setAmount(Doc.AMTTYPE_Charge, (BigDecimal) getPO().get_Value("GrandTotal"));
    
    p_lines = loadLines();
    return null;
  } 

  /**
   * Load Cost Details 
   * Ignored voucher 
   * @return DocLine Array
   */
  private DocLine[] loadLines() {
    ArrayList<DocLine> list = new ArrayList<DocLine>();
    MUNSChargeDetail[] cds = MUNSChargeDetail.gets(getPO());
    for(int i=0; i<cds.length; i++)
    {
    	if(!cds[i].isSettlement())
    		continue;
    	if (cds[i].getAmountConfirmed().signum() == 0)
    		continue;
    	
    	DocLine dl = new DocLine(cds[i], this);
    	dl.setAmount(cds[i].getAmountConfirmed());
    	dl.setReversalLine_ID(cds[i].getReversalLine_ID());

    	list.add(dl);
    }
    
    DocLine[] dls = new DocLine[list.size()];
    list.toArray(dls);

    return dls;
  } 

  /**************************************************************************
   * Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
   * 
   * @return positive amount, if total invoice is bigger than lines
   */
  public BigDecimal getBalance() {
   return Env.ZERO;
  } // getBalance

  /*
   * (non-Javadoc)
   * 
   * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
   */
  @Override
  public ArrayList<Fact> createFacts(MAcctSchema as) 
  {
	Fact fact = new Fact(this, as, Fact.POST_Actual);
	setC_Currency_ID(as.getC_Currency_ID());
	
	FactLine dr = null;
	FactLine cr = null;
	
	for (int i = 0; i < p_lines.length; i++) 
	{
	  int C_Currency_ID = as.getC_Currency_ID();
	  DocLine line = p_lines[i];
	  BigDecimal cost = line.getAmtSource();
	  
	  int C_Charge_ID = line.getPO().get_ValueAsInt("C_Charge_ID");
	  dr = fact.createLine(line, MCharge.getAccount(C_Charge_ID, as), C_Currency_ID, cost, null);
	  
	  if (dr == null) 
	  {
	    p_Error = "DR not created: " + line;
	    log.log(Level.WARNING, p_Error);
	    return null;
	  }
	  
	  if (isReversal(line))
		{
			if (!dr.updateReverseLine (getPO().get_Table_ID(),
					m_reversalID, line.getReversalLine_ID(),Env.ONE))
			{
				p_Error = "Original Move Confirm not posted yet";
				return null;
			}
		}
	  
	  if(line.getC_BPartner_ID() > 0)
		  dr.setC_BPartner_ID(line.getC_BPartner_ID());
	  
	  MAccount account = null;
	  if(((MUNSChargeDetail) line.getPO()).isVoucher())
	  {
		  account = MCharge.getAccount(UNSApps.getRefAsInt(
				  UNSApps.CHRG_VBBM), as);
	  }
	  else
	  {
		  account = getAccount(Doc.ACCTTYPE_BankInTransit, as);
	  }
	  cr = fact.createLine(line, account, C_Currency_ID, null, cost);
	
	  if (cr == null) 
	  {
	    p_Error = "DR not created: " + line;
	    log.log(Level.WARNING, p_Error);
	    return null;
	  }
	  
	  if (isReversal(line))
		{
			if (!cr.updateReverseLine (getPO().get_Table_ID(),
					m_reversalID, line.getReversalLine_ID(),Env.ONE))
			{
				p_Error = "Original Move Confirm not posted yet";
				return null;
			}
		}
	}
	
	ArrayList<Fact> facts = new ArrayList<Fact>();
	facts.add(fact);
	return facts;
  }
  
  private boolean isReversal(DocLine line) 
  {
		return m_reversalID !=0 && line.getReversalLine_ID() != 0;
  }
  
}
