/**
 * 
 */
package com.uns.model.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;

import com.uns.model.I_UNS_Employee;
import com.uns.model.MUNSHalfPeriodPresence;
import com.uns.model.MUNSPayrollConfiguration;

/**
 * @author menjangan
 *
 */
public class Doc_UNSHalfPeriodPresence extends Doc {

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSHalfPeriodPresence(MAcctSchema as, Class<?> clazz,
			ResultSet rs, String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
		// TODO Auto-generated constructor stub
	}
	
	private DocLine m_DocLineGaji = null;
	private DocLine m_DocLinePremiKerajinan = null;
	private DocLine m_DocLinePremiReceivable = null;
	private DocLine m_DocLineJHT = null;
	private DocLine m_DocLineJPK = null;
	private DocLine m_DocLineJKK = null;
	private DocLine m_DocLineJK = null;
	private DocLine m_DocLineSPTP = null;
	private DocLine m_DocLinePLabel = null;
	private DocLine m_PPH21 = null;
	
	/**
	 * 
	 * @param as
	 * @param rs
	 * @param trxName
	 */
	public Doc_UNSHalfPeriodPresence(MAcctSchema as,ResultSet rs,String trxName)
	{
		super(as,com.uns.model.override.MUNSHalfPeriodPresence.class,rs,null,trxName);
	}
	
	private MUNSHalfPeriodPresence m_HalfPeriodPresence = null;

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		// TODO Auto-generated method stub
		m_HalfPeriodPresence = (MUNSHalfPeriodPresence)getPO();
		setDateDoc(m_HalfPeriodPresence.getUpdated());
		setDateAcct(m_HalfPeriodPresence.getUpdated());
		loadLines();
		return null;
	}
	
	protected void loadLines()
	{
		if(m_HalfPeriodPresence.isSwitchTodaily())
		{
			if(m_HalfPeriodPresence.getPaidDaily().compareTo(BigDecimal.ZERO) > 0)
			{
				m_DocLineGaji = new DocLine(m_HalfPeriodPresence, this);
				m_DocLineGaji.setAmount(m_HalfPeriodPresence.getPaidDaily());
			}
		}
		else
		{
			if (null != m_HalfPeriodPresence.getTotalReceivableAmt() 
					&& m_HalfPeriodPresence.getTotalReceivableAmt().compareTo(BigDecimal.ZERO) > 0)
			{
				m_DocLineGaji = new DocLine(m_HalfPeriodPresence, this);
				m_DocLineGaji.setAmount(m_HalfPeriodPresence.getTotalReceivableAmt());
			}

			if (null != m_HalfPeriodPresence.getPremiKerajinanReceivable()
					&& m_HalfPeriodPresence.getPremiKerajinanReceivable().compareTo(BigDecimal.ZERO) > 0)
			{
				m_DocLinePremiReceivable = new DocLine(m_HalfPeriodPresence, this);
				m_DocLinePremiReceivable.setAmount(m_HalfPeriodPresence.getPremiKerajinanReceivable());
			}
			
			if (null != m_HalfPeriodPresence.getPremiTargetReceivable()
					&& m_HalfPeriodPresence.getPremiTargetReceivable().compareTo(BigDecimal.ZERO) > 0)
			{
				m_DocLinePremiKerajinan = new DocLine(m_HalfPeriodPresence, this);
				m_DocLinePremiKerajinan.setAmount(m_HalfPeriodPresence.getPremiTargetReceivable());
			}
		}
		
		if (null != m_HalfPeriodPresence.getA_JHT()
				&& m_HalfPeriodPresence.getA_JHT().compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJHT = new DocLine(m_HalfPeriodPresence, this);
			m_DocLineJHT.setAmount(m_HalfPeriodPresence.getA_JHT());
		}
		
		if (null != m_HalfPeriodPresence.getA_JK()
				&& m_HalfPeriodPresence.getA_JK().compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJK = new DocLine(m_HalfPeriodPresence, this);
			m_DocLineJK.setAmount(m_HalfPeriodPresence.getA_JK());
		}
		
		if (null != m_HalfPeriodPresence.getA_JKK()
				&& m_HalfPeriodPresence.getA_JKK().compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJKK = new DocLine(m_HalfPeriodPresence, this);
			m_DocLineJKK.setAmount(m_HalfPeriodPresence.getA_JKK());
		}
		
		if (null != m_HalfPeriodPresence.getA_JPK()
				&& m_HalfPeriodPresence.getA_JPK().compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJPK = new DocLine(m_HalfPeriodPresence, this);
			m_DocLineJPK.setAmount(m_HalfPeriodPresence.getA_JPK());
		}
		
		if (null != m_HalfPeriodPresence.getP_SPTP()
				&& m_HalfPeriodPresence.getP_SPTP().compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineSPTP= new DocLine(m_HalfPeriodPresence, this);
			m_DocLineSPTP.setAmount(m_HalfPeriodPresence.getP_SPTP());
		}
		
		if (null != m_HalfPeriodPresence.getP_Label()
				&& m_HalfPeriodPresence.getP_Label().compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLinePLabel = new DocLine(m_HalfPeriodPresence, this);
			m_DocLinePLabel.setAmount(m_HalfPeriodPresence.getP_Label());
		}
		if(null != m_HalfPeriodPresence.getPPH21() 
				&& m_HalfPeriodPresence.getPPH21().compareTo(BigDecimal.ZERO) > 0)
		{
			m_PPH21 = new DocLine(m_HalfPeriodPresence, this);
			m_PPH21.setAmount(m_HalfPeriodPresence.getPPH21());
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		// TODO Auto-generated method stub
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		// TODO Auto-generated method stub
		ArrayList<Fact> facts = new ArrayList<Fact>();
		I_UNS_Employee employee = m_HalfPeriodPresence.getUNS_Employee();
		MUNSPayrollConfiguration payConfig = MUNSPayrollConfiguration.get(
				getCtx(), m_HalfPeriodPresence.getStartDate(), getTrxName());
		
		int BiayaGajiBuruhValidAcct = payConfig.getUpahBuruhDirectAcct_ID();
		int HutangGajiBuruhValidAcct = payConfig.getHutangUpahBuruhDirectAcct_ID();
		int premiValidAcct = payConfig.getUpahBuruhInDirectAcct_ID();
		int jhtAcct = payConfig.getB_JHTAcct_ID();
		int jpkAcct = payConfig.getB_JPKAcct_ID();
		int jkkAcct = payConfig.getB_JKKAcct_ID();
		int jkAcct = payConfig.getB_JKAcct_ID();
		int sptpAcct = payConfig.getP_SPTPAcct_ID();
		int pLabelAcct = payConfig.getP_LabelAcct_ID();
		int pph21Acct = payConfig.getPPH21_Acct_ID();
		
//		int vendor_ID = employee.getVendor_ID();
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID (as.getC_Currency_ID());
		FactLine dr = null;
		FactLine cr = null;
		
		if(null != m_DocLineGaji)
		{
			MAccount accountDebit = MAccount.get(getCtx(), HutangGajiBuruhValidAcct);
			
			dr = fact.createLine(m_DocLineGaji, accountDebit,
					as.getC_Currency_ID(), m_DocLineGaji.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineGaji;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), BiayaGajiBuruhValidAcct);
				
			cr = fact.createLine(m_DocLineGaji, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineGaji.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineGaji;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		
		if(null != m_DocLinePremiKerajinan)
		{
			MAccount accountDebit = MAccount.get(getCtx(), premiValidAcct);
			
			dr = fact.createLine(m_DocLinePremiKerajinan, accountDebit,
					as.getC_Currency_ID(), m_DocLinePremiKerajinan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLinePremiKerajinan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), BiayaGajiBuruhValidAcct);
				
			cr = fact.createLine(m_DocLinePremiKerajinan, accountCredit,
					as.getC_Currency_ID(), null, m_DocLinePremiKerajinan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLinePremiKerajinan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		if(null != m_DocLinePremiReceivable)
		{
			MAccount accountDebit = MAccount.get(getCtx(), premiValidAcct);
			
			dr = fact.createLine(m_DocLinePremiReceivable, accountDebit,
					as.getC_Currency_ID(), m_DocLinePremiReceivable.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLinePremiReceivable;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), BiayaGajiBuruhValidAcct);
				
			cr = fact.createLine(m_DocLinePremiReceivable, accountCredit,
					as.getC_Currency_ID(), null, m_DocLinePremiReceivable.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLinePremiReceivable;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		if(null != m_DocLineJHT)
		{
			MAccount accountDebit = MAccount.get(getCtx(), jhtAcct);
			
			dr = fact.createLine(m_DocLineJHT, accountDebit,
					as.getC_Currency_ID(), m_DocLineJHT.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJHT;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), BiayaGajiBuruhValidAcct);
				
			cr = fact.createLine(m_DocLineJHT, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineJHT.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJHT;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		if(null != m_DocLineJK)
		{
			MAccount accountDebit = MAccount.get(getCtx(), jkAcct);
			
			dr = fact.createLine(m_DocLineJK, accountDebit,
					as.getC_Currency_ID(), m_DocLineJK.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJHT;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), BiayaGajiBuruhValidAcct);
				
			cr = fact.createLine(m_DocLineJK, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineJK.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJK;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		if(null != m_DocLineJKK)
		{
			MAccount accountDebit = MAccount.get(getCtx(), jkkAcct);
			
			dr = fact.createLine(m_DocLineJKK, accountDebit,
					as.getC_Currency_ID(), m_DocLineJKK.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJKK;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), BiayaGajiBuruhValidAcct);
				
			cr = fact.createLine(m_DocLineJKK, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineJKK.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJKK;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		if(null != m_DocLineJPK)
		{
			MAccount accountDebit = MAccount.get(getCtx(), jpkAcct);
			
			dr = fact.createLine(m_DocLineJPK, accountDebit,
					as.getC_Currency_ID(), m_DocLineJPK.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJPK;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), BiayaGajiBuruhValidAcct);
				
			cr = fact.createLine(m_DocLineJPK, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineJPK.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJPK;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		if(null != m_DocLineSPTP)
		{
			MAccount accountDebit = MAccount.get(getCtx(), sptpAcct);
			
			dr = fact.createLine(m_DocLineSPTP, accountDebit,
					as.getC_Currency_ID(), m_DocLineSPTP.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineSPTP;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), BiayaGajiBuruhValidAcct);
				
			cr = fact.createLine(m_DocLineSPTP, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineSPTP.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineSPTP;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		if(null != m_DocLinePLabel)
		{
			MAccount accountDebit = MAccount.get(getCtx(), pLabelAcct);
			
			dr = fact.createLine(m_DocLinePLabel, accountDebit,
					as.getC_Currency_ID(), m_DocLinePLabel.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLinePLabel;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), BiayaGajiBuruhValidAcct);
				
			cr = fact.createLine(m_DocLinePLabel, accountCredit,
					as.getC_Currency_ID(), null, m_DocLinePLabel.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLinePLabel;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		
		if(null != m_PPH21)
		{
			MAccount accountDebit = MAccount.get(getCtx(), pph21Acct);
			
			dr = fact.createLine(m_PPH21, accountDebit,
					as.getC_Currency_ID(), m_PPH21.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_PPH21;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), BiayaGajiBuruhValidAcct);
				
			cr = fact.createLine(m_PPH21, accountCredit,
					as.getC_Currency_ID(), null, m_PPH21.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_PPH21;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		
		facts.add(fact);
		return facts;
	}

}
