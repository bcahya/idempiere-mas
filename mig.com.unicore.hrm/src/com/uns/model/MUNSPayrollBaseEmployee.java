/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.util.DB;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSPayrollBaseEmployee extends X_UNS_PayrollBase_Employee {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1188035197642766104L;

	/**
	 * @param ctx
	 * @param UNS_PayrollBase_Employee_ID
	 * @param trxName
	 */
	public MUNSPayrollBaseEmployee(Properties ctx,
			int UNS_PayrollBase_Employee_ID, String trxName) {
		super(ctx, UNS_PayrollBase_Employee_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPayrollBaseEmployee(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSPayrollBaseEmployee(MUNSContractRecommendation cr)
	{
		super(cr.getCtx(), 0, cr.get_TrxName());
		setUNS_Employee_ID(cr.getUNS_Employee_ID());
		setUNS_Contract_Recommendation_ID(cr.get_ID());
		setAD_Org_ID(cr.getNewDept_ID());
//		setPayrollType(cr.getNextPayrollType());
		setA_L1(cr.getNew_A_L1());
		setA_L2(cr.getNew_A_L2());
		setA_L3(cr.getNew_A_L3());
		setA_LemburJamPertama(cr.getNewLeburJamPertama());
		setA_LemburJamBerikutnya(cr.getNewLeburJamBerikutnya());
		setA_Other(cr.getNew_A_Lain2());
		setA_Premi(cr.getNew_A_Premi());
		setA_Rapel(BigDecimal.ZERO);
		setG_T_Jabatan(cr.getNew_T_Jabatan());
		setG_T_Kesejahteraan(cr.getNew_T_Kesejahteraan());
		setG_T_Khusus(BigDecimal.ZERO);
		setG_T_Lembur(cr.getNew_T_Lembur());
		setGPokok(cr.getNew_G_Pokok());
		setP_Koperasi(BigDecimal.ZERO);
		setP_Label(cr.getNew_P_Label());
		setP_ListrikAir(BigDecimal.ZERO);
		setP_Mangkir(cr.getNew_P_Mangkir());
		setP_Obat(BigDecimal.ZERO);
		setP_Other(cr.getNew_P_Lain2());
		setP_PinjamanKaryawan(BigDecimal.ZERO);
		setP_SPTP(cr.getNew_P_SPTP());
		setValidFrom(cr.getDateContractStart());
		setPayrollLevel(cr.getNewPayrollLevel());
		setValidTo(cr.getDateContractEnd());
		setIsJHTApplyed(cr.isJHTApplyed());
		setIsJKApplyed(cr.isJKApplyed());
		setIsJKKApplyed(cr.isJKKApplyed());
		setIsJPApplied(cr.isJPApplied());
		setIsJPKApplyed(cr.isJPKApplyed());
	}
	
	public static MUNSPayrollBaseEmployee get(
			Properties ctx, int UNS_Contract_Recommendation_ID, String trxName)
	{
		return Query.get(
				ctx, 
				UNSHRMModelFactory.getExtensionID(), 
				Table_Name, 
				COLUMNNAME_UNS_Contract_Recommendation_ID + " = " 
				+ UNS_Contract_Recommendation_ID, trxName).setOrderBy("IsActive DESC").firstOnly();
	}
	
	/**
	 * 
	 * @param ctx
	 * @param payrollLevel
	 * @param date
	 * @param UNS_Employee_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSPayrollBaseEmployee getPrev(
			Properties ctx, int UNS_Employee_ID, String trxName)
	{
		MUNSPayrollBaseEmployee pbEmploye = null;
		String sql = "SELECT * FROM " + Table_Name
				+ " WHERE " + COLUMNNAME_ValidTo + " = " +
						"(SELECT MAX(" + COLUMNNAME_ValidTo + ") FROM " + Table_Name + " "
								+ " WHERE " + COLUMNNAME_UNS_Employee_ID
								+ " = " + UNS_Employee_ID + " AND " + COLUMNNAME_IsActive + " ='Y')" 
								+ " AND " + COLUMNNAME_IsActive + " = 'Y' AND " 
								+ COLUMNNAME_UNS_Employee_ID
								+ " = " + UNS_Employee_ID;
		
		PreparedStatement stm = null;
		ResultSet rs = null;
		try
		{
			stm = DB.prepareStatement(sql,trxName);
			rs = stm.executeQuery();
			if (rs.next())
				pbEmploye = new MUNSPayrollBaseEmployee(ctx, rs, trxName);
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			DB.close(rs, stm);
		}
		
		return pbEmploye;
	}
	
	/** modified by ITD-Andy, get DocType of Payroll */
	public static MDocType getDocType(Properties ctx, boolean isEmployee) {
		try {
			for (MDocType doc : MDocType.getOfDocBaseType(ctx, "PRE")) {
				if(doc.isSOTrx()!= isEmployee)
					return doc;
				else
					return doc;
			}
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		return null;
	}
}
