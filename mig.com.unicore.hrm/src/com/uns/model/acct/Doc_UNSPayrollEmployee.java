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
import org.compiere.model.MPeriod;

import com.uns.model.I_UNS_Contract_Recommendation;
import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSPayrollEmployee;

/**
 * @author menjangan
 *
 */
public class Doc_UNSPayrollEmployee extends Doc {

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSPayrollEmployee(MAcctSchema as, Class<?> clazz,
			ResultSet rs, String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public Doc_UNSPayrollEmployee(MAcctSchema as,ResultSet rs,String trxName)
	{
		super(as,MUNSPayrollEmployee.class,rs,null,trxName);
	}

	
	private MUNSPayrollEmployee m_PayEmployee = null;
	private DocLine m_DocLineJHTDibayarPerusahaan = null;
	private DocLine m_DocLineJKDibayarPerusahaan = null;
	private DocLine m_DocLineJKKDibayarPerusahaan = null;
	private DocLine m_DocLineJPKDibayarPerusahaan = null;
	private DocLine m_DocLineTunjanganJabatan = null;
	private DocLine m_DocLineTunjanganKesejahteraan = null;
	private DocLine m_DocLineTunjanganKhusus = null;
	private DocLine m_DocLineTunjanganLembur = null;
	private DocLine m_DocLineJHTDibayarKaryawan = null;
	private DocLine m_DocLineJKDibayarKaryawan = null;
	private DocLine m_DocLineJKKDibayarKaryawan = null;
	private DocLine m_DocLineJPKDibayarKaryawan = null;
	private DocLine m_DocLineALembur = null;
	private DocLine m_DocLineAL1 = null;
	private DocLine m_DocLineAL2 = null;
	private DocLine m_DocLineAL3 = null;
	private DocLine m_DocLinePremi = null;
	private DocLine m_DocLineRapel = null;
	private DocLine m_DocLineAdditionalOther = null;
	private DocLine m_DocLinePotonganOther = null;
	private DocLine m_DocLinePotonganLabel = null;
	private DocLine m_DocLinePotonganListrikAir = null;
	private DocLine m_DocLinePotonganMangkir = null;
	private DocLine m_DocLineSptp = null;
	private DocLine m_DocLineBiayaGaji = null;
	private DocLine m_DocLinePPH21 = null;
	private DocLine m_DocLineBayarUtang = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		// TODO Auto-generated method stub
		m_PayEmployee = (MUNSPayrollEmployee)getPO();
		setDateDoc(m_PayEmployee.getUpdated());
		setDateAcct(m_PayEmployee.getUpdated());
		loadLines();
		return null;
	}
	
	
	protected void loadLines()
	{
		/** Biaya Jamsostek Dibayarkan perusahaan */
		BigDecimal JHT = m_PayEmployee.getA_JHT();
		BigDecimal JK = m_PayEmployee.getA_JK();
		BigDecimal JKK = m_PayEmployee.getA_JKK();
		BigDecimal JPK = m_PayEmployee.getA_JPK();
		
		if (null != JHT && JHT.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJHTDibayarPerusahaan = new DocLine(m_PayEmployee, this);
			m_DocLineJHTDibayarPerusahaan.setAmount(JHT);
		}
		if (null != JK && JK.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJKDibayarPerusahaan = new DocLine(m_PayEmployee, this);
			m_DocLineJKDibayarPerusahaan.setAmount(JK);
		}
		if(null != JKK && JKK.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJKKDibayarPerusahaan = new DocLine(m_PayEmployee, this);
			m_DocLineJKKDibayarPerusahaan.setAmount(JKK);
		}
		if (null != JPK && JPK.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJPKDibayarPerusahaan = new DocLine(m_PayEmployee, this);
			m_DocLineJPKDibayarPerusahaan.setAmount(JPK);
		}
		
		
		/** Biaya Tunjangan */
		BigDecimal tunjanganJabatan = m_PayEmployee.getG_T_Jabatan();
		BigDecimal tunjanganKesejahteraan = m_PayEmployee.getG_T_Kesejahteraan();
		BigDecimal tunjanganKhusus = m_PayEmployee.getG_T_Khusus();
		BigDecimal tunjanganLembur = m_PayEmployee.getG_T_Lembur();
		if (null != tunjanganJabatan && tunjanganJabatan.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineTunjanganJabatan = new DocLine(m_PayEmployee, this);
			m_DocLineTunjanganJabatan.setAmount(tunjanganJabatan);
		}
		if (null != tunjanganKesejahteraan && tunjanganKesejahteraan.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineTunjanganKesejahteraan = new DocLine(m_PayEmployee, this);
			m_DocLineTunjanganKesejahteraan.setAmount(tunjanganKesejahteraan);
		}
		if (null != tunjanganKhusus && tunjanganKhusus.compareTo(BigDecimal.ZERO) > 0 )
		{
			m_DocLineTunjanganKhusus = new DocLine(m_PayEmployee, this);
			m_DocLineTunjanganKhusus.setAmount(tunjanganKhusus);
		}
		if (null != tunjanganLembur && tunjanganLembur.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineTunjanganLembur = new DocLine(m_PayEmployee, this);
			m_DocLineTunjanganLembur.setAmount(tunjanganLembur);
		}
		
		
		/** PPH21 */
		BigDecimal pph21 = m_PayEmployee.getPPH21();
		if (null != pph21 && pph21.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLinePPH21 = new DocLine(m_PayEmployee, this);
			m_DocLinePPH21.setAmount(pph21);
		}
		
		/**potongan Jamsostek */		
		BigDecimal P_JHT = m_PayEmployee.getP_JHT();
		BigDecimal P_JK = m_PayEmployee.getP_JK();
		BigDecimal P_JKK = m_PayEmployee.getP_JKK();
		BigDecimal P_JPK = m_PayEmployee.getP_JPK();
		if (null != P_JHT && P_JHT.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJHTDibayarKaryawan = new DocLine(m_PayEmployee, this);
			m_DocLineJHTDibayarKaryawan.setAmount(P_JHT);
		}
		if (null != P_JK && P_JK.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJKDibayarKaryawan = new DocLine(m_PayEmployee, this);
			m_DocLineJKDibayarKaryawan.setAmount(P_JK);
		}
		if (null != P_JKK && P_JKK.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJKKDibayarKaryawan = new DocLine(m_PayEmployee, this);
			m_DocLineJKKDibayarKaryawan.setAmount(P_JKK);
		}
		if (null != P_JPK && P_JPK.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineJPKDibayarKaryawan = new DocLine(m_PayEmployee, this);
			m_DocLineJPKDibayarKaryawan.setAmount(P_JPK);
		}
		
		
		/** Biaya Lembur */
		BigDecimal A_Lembur = m_PayEmployee.getA_LemburJamPertama();
		BigDecimal A_L1 = m_PayEmployee.getA_L1();
		BigDecimal A_L2 = m_PayEmployee.getA_L2();
		BigDecimal A_L3 = m_PayEmployee.getA_L3();
		if (null != A_Lembur && A_Lembur.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineALembur = new DocLine(m_PayEmployee, this);
			m_DocLineALembur.setAmount(A_Lembur);
		}
		if (null != A_L1 && A_L1.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineAL1 = new DocLine(m_PayEmployee, this);
			m_DocLineAL1.setAmount(A_L1);
		}
		if (null != A_L2 && A_L2.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineAL2 = new DocLine(m_PayEmployee, this);
			m_DocLineAL2.setAmount(A_L2);
		}
		if (null != A_L3 && A_L3.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineAL3 = new DocLine(m_PayEmployee, this);
			m_DocLineAL3.setAmount(A_L3);
		}
		
		BigDecimal premi = m_PayEmployee.getA_Premi();
		if (null != premi && premi.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLinePremi = new DocLine(m_PayEmployee, this);
			m_DocLinePremi.setAmount(premi);
		}
		
		BigDecimal rapel = m_PayEmployee.getA_Rapel();
		if (null != rapel && rapel.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineRapel = new DocLine(m_PayEmployee, this);
			m_DocLineRapel.setAmount(rapel);
		}
		
		BigDecimal additionalOther = m_PayEmployee.getA_Other();
		if (null != additionalOther && additionalOther.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineAdditionalOther = new DocLine(m_PayEmployee, this);
			m_DocLineAdditionalOther.setAmount(additionalOther);
		}
		
		BigDecimal potonganOther = m_PayEmployee.getP_Other();
		if (null != potonganOther && potonganOther.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLinePotonganOther = new DocLine(m_PayEmployee, this);;
			m_DocLinePotonganOther.setAmount(potonganOther);
		}

		BigDecimal potonganLabel = m_PayEmployee.getP_Label();
		if (null != potonganLabel && potonganLabel.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLinePotonganLabel = new DocLine(m_PayEmployee, this);
			m_DocLinePotonganLabel.setAmount(potonganLabel);
		}
		
		BigDecimal potonganListrik = m_PayEmployee.getP_ListrikAir();
		if (null != potonganListrik && potonganListrik.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLinePotonganListrikAir = new DocLine(m_PayEmployee, this);
			m_DocLinePotonganListrikAir.setAmount(potonganListrik);
		}
		
		BigDecimal potonganMangkir = m_PayEmployee.getP_Mangkir();
		if (null != potonganMangkir && potonganMangkir.compareTo(BigDecimal.ZERO) > 0) 
		{
			m_DocLinePotonganMangkir = new DocLine(m_PayEmployee, this);
			m_DocLinePotonganMangkir.setAmount(potonganMangkir);
		}
		
		BigDecimal sptp = m_PayEmployee.getP_SPTP();
		if (null != sptp && sptp.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineSptp = new DocLine(m_PayEmployee, this);
			m_DocLineSptp.setAmount(sptp);
		}
		
		BigDecimal biayaGaji = m_PayEmployee.getGPokok();
		if (null != biayaGaji && biayaGaji.compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineBiayaGaji = new DocLine(m_PayEmployee, this);
			m_DocLineBiayaGaji.setAmount(biayaGaji);
		}
		
		if(null != m_PayEmployee.getP_PinjamanKaryawan() 
				&& m_PayEmployee.getP_PinjamanKaryawan().compareTo(BigDecimal.ZERO) > 0)
		{
			m_DocLineBayarUtang = new DocLine(m_PayEmployee, this);
			m_DocLineBayarUtang.setAmount(m_PayEmployee.getP_PinjamanKaryawan());
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		// TODO Auto-generated method stub
		return BigDecimal.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		// TODO Auto-generated method stub
		ArrayList<Fact> facts = new ArrayList<Fact>();
		
		MUNSEmployee employee = (MUNSEmployee)m_PayEmployee.getUNS_Employee();
		
		MUNSPayrollConfiguration payConfig =
				MUNSPayrollConfiguration.get(
						getCtx(), (MPeriod)m_PayEmployee.getC_Period(), getTrxName());
		
		
		/** Account */
		int BiayaGajiAcct = payConfig.getBiayaGajiBulananAcct_ID();
		int HutangGajiAcct = payConfig.getHutangGajiBulananAcct_ID();
		
		I_UNS_Contract_Recommendation contract = employee.getUNS_Contract_Recommendation();
		
		if(MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarian.equals(
				contract.getNextContractType())
				|| MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarianCV.equals(
						contract.getNextContractType()))
		{
			BiayaGajiAcct = payConfig.getBiayaGajiHarianAcct_ID();
			HutangGajiAcct = payConfig.getHutangGajiHarianAcct_ID();
		}
		int TunjanganJabatanAcct = payConfig.getTunjanganJabatanAcct_ID();
		int TunjanganKesejahteraanAcct = payConfig.getTunjanganKesejahteraanAcct_ID();
		int TunjanganKhususAcct = payConfig.getTunjanganKhususAcct_ID();
		int TunjanganLemburAcct = payConfig.getTunjanganLemburAcct_ID();
		int pph21Acct = payConfig.getPPH21_Acct_ID();
		int B_JHTAcct = payConfig.getB_JHTAcct_ID();
		int B_JPKAcct = payConfig.getB_JPKAcct_ID();
		int B_JKKAcct = payConfig.getB_JKKAcct_ID();
		int B_JKAcct = payConfig.getB_JKAcct_ID();
		int H_JHTAcct = payConfig.getH_JHT_Acct_ID();
		int H_JPKAcct = payConfig.getH_JPK_Acct_ID();
		int H_JKKAcct = payConfig.getH_JKK_Acct_ID();
		int H_JKAcct = payConfig.getH_JK_Acct_ID();
		int A_LemburAcct = payConfig.getA_Lembur_Acct_ID();
		int A_L1Acct = payConfig.getA_L1_Acct_ID();
		int A_L2Acct = payConfig.getA_L2_Acct_ID();
		int A_L3Acct = payConfig.getA_L3_Acct_ID();
		int A_otherAcct = payConfig.getA_OtherAcct_ID();
		int A_PremiAcct = payConfig.getA_PremiAcct_ID();
		int A_RapelAcct = payConfig.getA_RapelAcct_ID();
		int P_LabelAcct = payConfig.getP_LabelAcct_ID();
		int p_ListrikAirAcct = payConfig.getP_ListrikAirAcct_ID();
		int P_mangkirAcct = payConfig.getP_MangkirAcct_ID();
		int P_OtherAcct = payConfig.getP_OtherAcct_ID();
		int P_SPTPAcct = payConfig.getP_SPTPAcct_ID();
		int P_BayarUtang = payConfig.getP_Employee_Loan_Acct();
		
//		int sectionOfDepartment = employee.getC_BPartner_ID();
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID (as.getC_Currency_ID());
		FactLine dr = null;
		FactLine cr = null;
		
		
		if (null != m_DocLineBiayaGaji)
		{
			MAccount accountDebit = MAccount.get(getCtx(), BiayaGajiAcct);
			
			dr = fact.createLine(m_DocLineBiayaGaji, accountDebit,
					as.getC_Currency_ID(), m_DocLineBiayaGaji.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineBiayaGaji;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineBiayaGaji.getAmtSource());
				

				MAccount accountCredit = MAccount.get(getCtx(), HutangGajiAcct);
				
			cr = fact.createLine(m_DocLineBiayaGaji, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineBiayaGaji.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineBiayaGaji;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineBiayaGaji.getAmtSource());
		}
		
		if (null != m_DocLineAdditionalOther)
		{	
			MAccount accountDebit = MAccount.get(getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineAdditionalOther, accountDebit,
					as.getC_Currency_ID(), m_DocLineAdditionalOther.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineAdditionalOther;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineAdditionalOther.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), A_otherAcct);
				
			cr = fact.createLine(m_DocLineAdditionalOther, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineAdditionalOther.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineAdditionalOther;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineAdditionalOther.getAmtSource());
		}
		
		if (null != m_DocLineAL1)
		{
			MAccount accountDebit = MAccount.get(getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineAL1, accountDebit,
					as.getC_Currency_ID(), m_DocLineAL1.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineAL1;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineAL1.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), A_L1Acct);
				
			cr = fact.createLine(m_DocLineAL1, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineAL1.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineAL1;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineAL1.getAmtSource());
		}
		
		if (null != m_DocLineAL2)
		{
			MAccount accountDebit = MAccount.get(getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineAL2, accountDebit,
					as.getC_Currency_ID(), m_DocLineAL2.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineAL2;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineAL2.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), A_L2Acct);
				
			cr = fact.createLine(m_DocLineAL2, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineAL2.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineAL2;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineAL2.getAmtSource());
		}
		
		if (null != m_DocLineAL3)
		{
			MAccount accountDebit = MAccount.get(getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineAL3, accountDebit,
					as.getC_Currency_ID(), m_DocLineAL3.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineAL3;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineAL3.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), A_L3Acct);
				
			cr = fact.createLine(m_DocLineAL3, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineAL3.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineAL3;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineAL3.getAmtSource());
		}
		
		if (null != m_DocLineALembur)
		{
			MAccount accountDebit = MAccount.get(getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineALembur, accountDebit,
					as.getC_Currency_ID(), m_DocLineALembur.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineALembur;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineALembur.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), A_LemburAcct);
				
			cr = fact.createLine(m_DocLineALembur, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineALembur.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineALembur;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineALembur.getAmtSource());
		}
		
		
		/** Ini lagi dibuat */
		
		if (null != m_DocLineJHTDibayarKaryawan)
		{
			MAccount accountDebit = MAccount.get(getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineJHTDibayarKaryawan, accountDebit,
					as.getC_Currency_ID(), m_DocLineJHTDibayarKaryawan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJHTDibayarKaryawan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineJHTDibayarKaryawan.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), H_JHTAcct);
				
			cr = fact.createLine(m_DocLineJHTDibayarKaryawan, accountCredit,
					as.getC_Currency_ID(), null,  m_DocLineJHTDibayarKaryawan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJHTDibayarKaryawan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineJHTDibayarKaryawan.getAmtSource());
		}
		
		if (null != m_DocLineJHTDibayarPerusahaan)
		{
			MAccount accountDebit = MAccount.get(getCtx(), B_JHTAcct);
			
			dr = fact.createLine(m_DocLineJHTDibayarPerusahaan, accountDebit,
					as.getC_Currency_ID(), m_DocLineJHTDibayarPerusahaan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJHTDibayarPerusahaan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineJHTDibayarPerusahaan.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), H_JHTAcct);
				
			cr = fact.createLine(m_DocLineJHTDibayarPerusahaan, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineJHTDibayarPerusahaan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJHTDibayarPerusahaan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineJHTDibayarPerusahaan.getAmtSource());
		}
		
		if (null != m_DocLineJKDibayarKaryawan)
		{
			MAccount accountDebit = MAccount.get(getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineJKDibayarKaryawan, accountDebit,
					as.getC_Currency_ID(), m_DocLineJKDibayarKaryawan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJKDibayarKaryawan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineJKDibayarKaryawan.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), H_JKAcct);
				
			cr = fact.createLine(m_DocLineJKDibayarKaryawan, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineJKDibayarKaryawan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJKDibayarKaryawan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineJKDibayarKaryawan.getAmtSource());
		}
		
		if (null != m_DocLineJKDibayarPerusahaan)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), B_JKAcct);
			
			dr = fact.createLine(m_DocLineJKDibayarPerusahaan, accountDebit,
					as.getC_Currency_ID(), m_DocLineJKDibayarPerusahaan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJKDibayarPerusahaan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineJKDibayarPerusahaan.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), H_JKAcct);
				
			cr = fact.createLine(m_DocLineJKDibayarPerusahaan, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineJKDibayarPerusahaan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJKDibayarPerusahaan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineJKDibayarPerusahaan.getAmtSource());
		}
		
		if (null != m_DocLineJKKDibayarPerusahaan)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), B_JKKAcct);
			
			dr = fact.createLine(m_DocLineJKKDibayarPerusahaan, accountDebit,
					as.getC_Currency_ID(), m_DocLineJKKDibayarPerusahaan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJKKDibayarPerusahaan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineJKKDibayarPerusahaan.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), H_JKKAcct);
				
			cr = fact.createLine(m_DocLineJKKDibayarPerusahaan, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineJKKDibayarPerusahaan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJKKDibayarPerusahaan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineJKKDibayarPerusahaan.getAmtSource());
		}
		
		if (null != m_DocLineJKKDibayarKaryawan)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineJKKDibayarKaryawan, accountDebit,
					as.getC_Currency_ID(), m_DocLineJKKDibayarKaryawan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJKKDibayarKaryawan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineJKKDibayarKaryawan.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), H_JKKAcct);
				
			cr = fact.createLine(m_DocLineJKKDibayarKaryawan, accountCredit,
					as.getC_Currency_ID(),null,  m_DocLineJKKDibayarKaryawan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJKKDibayarKaryawan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineJKKDibayarKaryawan.getAmtSource());
		}
		
		if (null != m_DocLineJPKDibayarKaryawan)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineJPKDibayarKaryawan, accountDebit,
					as.getC_Currency_ID(), m_DocLineJPKDibayarKaryawan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJPKDibayarKaryawan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineJPKDibayarKaryawan.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), H_JPKAcct);
				
			cr = fact.createLine(m_DocLineJPKDibayarKaryawan, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineJPKDibayarKaryawan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJPKDibayarKaryawan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineJPKDibayarKaryawan.getAmtSource());
		}
		
		if (null != m_DocLineJPKDibayarPerusahaan)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), B_JPKAcct);
			
			dr = fact.createLine(m_DocLineJPKDibayarPerusahaan, accountDebit,
					as.getC_Currency_ID(), m_DocLineJPKDibayarPerusahaan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineJPKDibayarPerusahaan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineJPKDibayarPerusahaan.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), H_JPKAcct);
				
			cr = fact.createLine(m_DocLineJPKDibayarPerusahaan, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineJPKDibayarPerusahaan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineJPKDibayarPerusahaan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineJPKDibayarPerusahaan.getAmtSource());
		}
		
		if (null != m_DocLinePotonganLabel)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLinePotonganLabel, accountDebit,
					as.getC_Currency_ID(), m_DocLinePotonganLabel.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLinePotonganLabel;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLinePotonganLabel.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), P_LabelAcct);
				
			cr = fact.createLine(m_DocLinePotonganLabel, accountCredit,
					as.getC_Currency_ID(), null, m_DocLinePotonganLabel.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLinePotonganLabel;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLinePotonganLabel.getAmtSource());
		}
		
		if (null != m_DocLinePotonganListrikAir)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLinePotonganListrikAir, accountDebit,
					as.getC_Currency_ID(), m_DocLinePotonganListrikAir.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLinePotonganListrikAir;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLinePotonganListrikAir.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), p_ListrikAirAcct);
				
			cr = fact.createLine(m_DocLinePotonganListrikAir, accountCredit,
					as.getC_Currency_ID(), null, m_DocLinePotonganListrikAir.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLinePotonganListrikAir;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLinePotonganListrikAir.getAmtSource());
		}
		
		if (null != m_DocLinePotonganMangkir)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLinePotonganMangkir, accountDebit,
					as.getC_Currency_ID(), m_DocLinePotonganMangkir.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLinePotonganMangkir;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLinePotonganMangkir.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), P_mangkirAcct);
				
			cr = fact.createLine(m_DocLinePotonganMangkir, accountCredit,
					as.getC_Currency_ID(), null, m_DocLinePotonganMangkir.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLinePotonganMangkir;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLinePotonganMangkir.getAmtSource());
		}
		
		if (null != m_DocLinePotonganOther)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLinePotonganOther, accountDebit,
					as.getC_Currency_ID(), m_DocLinePotonganOther.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLinePotonganOther;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLinePotonganOther.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), P_OtherAcct);
				
			cr = fact.createLine(m_DocLinePotonganOther, accountCredit,
					as.getC_Currency_ID(), null, m_DocLinePotonganOther.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLinePotonganOther;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLinePotonganOther.getAmtSource());
		}
		
		if (null != m_DocLinePPH21)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLinePPH21, accountDebit,
					as.getC_Currency_ID(), m_DocLinePPH21.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLinePPH21;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLinePPH21.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(),pph21Acct);
				
			cr = fact.createLine(m_DocLinePPH21, accountCredit,
					as.getC_Currency_ID(), null, m_DocLinePPH21.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLinePPH21;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLinePPH21.getAmtSource());
		}
		
		if (null != m_DocLinePremi)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLinePremi, accountDebit,
					as.getC_Currency_ID(), m_DocLinePremi.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLinePremi;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLinePremi.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), A_PremiAcct);
				
			cr = fact.createLine(m_DocLinePremi, accountCredit,
					as.getC_Currency_ID(), null,  m_DocLinePremi.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLinePremi;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLinePremi.getAmtSource());
		}
		
		if (null != m_DocLineRapel)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineRapel, accountDebit,
					as.getC_Currency_ID(), m_DocLineRapel.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineRapel;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineRapel.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), A_RapelAcct);
				
			cr = fact.createLine(m_DocLineRapel, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineRapel.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineRapel;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineRapel.getAmtSource());
		}
		
		if (null != m_DocLineSptp)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineSptp, accountDebit,
					as.getC_Currency_ID(), m_DocLineSptp.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineSptp;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineSptp.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), P_SPTPAcct);
				
			cr = fact.createLine(m_DocLineSptp, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineSptp.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineSptp;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineSptp.getAmtSource());
		}
		
		if (null != m_DocLineTunjanganJabatan)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineTunjanganJabatan, accountDebit,
					as.getC_Currency_ID(), m_DocLineTunjanganJabatan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineTunjanganJabatan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineTunjanganJabatan.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(getCtx(), TunjanganJabatanAcct);
				
			cr = fact.createLine(m_DocLineTunjanganJabatan, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineTunjanganJabatan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineTunjanganJabatan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineTunjanganJabatan.getAmtSource());
		}
		
		if (null != m_DocLineTunjanganKesejahteraan)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineTunjanganKesejahteraan, accountDebit,
					as.getC_Currency_ID(), m_DocLineTunjanganKesejahteraan.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineTunjanganKesejahteraan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineTunjanganKesejahteraan.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), TunjanganKesejahteraanAcct);
				
			cr = fact.createLine(m_DocLineTunjanganKesejahteraan, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineTunjanganKesejahteraan.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineTunjanganKesejahteraan;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineTunjanganKesejahteraan.getAmtSource());
		}
		
		if (null != m_DocLineTunjanganKhusus)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineTunjanganKhusus, accountDebit,
					as.getC_Currency_ID(), m_DocLineTunjanganKhusus.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineTunjanganKhusus;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineTunjanganKhusus.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), TunjanganKhususAcct);
				
			cr = fact.createLine(m_DocLineTunjanganKhusus, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineTunjanganKhusus.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineTunjanganKhusus;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineTunjanganKhusus.getAmtSource());
		}
		
		if (null != m_DocLineTunjanganLembur)
		{
			MAccount accountDebit = MAccount.get(
					getCtx(), HutangGajiAcct);
			
			dr = fact.createLine(m_DocLineTunjanganLembur, accountDebit,
					as.getC_Currency_ID(), m_DocLineTunjanganLembur.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineTunjanganLembur;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				dr.setAmtSourceDr(m_DocLineTunjanganLembur.getAmtSource());
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(),TunjanganLemburAcct);
				
			cr = fact.createLine(m_DocLineTunjanganLembur, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineTunjanganLembur.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineTunjanganLembur;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
//				cr.setAmtSourceDr(m_DocLineTunjanganLembur.getAmtSource());
		}
		
		if(null != m_DocLineBayarUtang)
		{
			MAccount acctDebit = MAccount.get(getCtx(), HutangGajiAcct);
			dr = fact.createLine(m_DocLineBayarUtang, acctDebit,
					as.getC_Currency_ID(), m_DocLineBayarUtang.getAmtSource(), null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + m_DocLineBayarUtang;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(employee.getC_BPartner_ID());
				
			MAccount accountCredit = MAccount.get(
						getCtx(),P_BayarUtang);
				
			cr = fact.createLine(m_DocLineBayarUtang, accountCredit,
					as.getC_Currency_ID(), null, m_DocLineBayarUtang.getAmtSource());
					//
				if (cr == null)
				{
					p_Error = "DR not created: " + m_DocLineBayarUtang;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
		}
		
		
		facts.add(fact);
		return facts;
	}

}
