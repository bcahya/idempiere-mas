package com.uns.qad.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.DB;

public class MUNSQALabTest extends X_UNS_QALabTest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5101980377823595611L;
	/**	Cache						*/
	private volatile static CCache<Integer,MUNSQALabTest> s_cache; 
	
	private MUNSQALabTestPCategory[] m_lines;

	public MUNSQALabTest(Properties ctx, int UNS_QALabTest_ID, String trxName) {
		super(ctx, UNS_QALabTest_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSQALabTest(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static MUNSQALabTest getByProduct(Properties ctx, int productID, String trxName) {
		String sql="SELECT UNS_QALabTest_ID FROM UNS_QALabTest WHERE ProductService_ID = ?";
		int retVal = DB.getSQLValue(trxName, sql, productID);
		if(retVal == -1)
			return null;
		return new MUNSQALabTest(ctx, retVal, trxName);
	}
	
	/**
	* @param requery
	* @return MUNSQALabTestPCategory[]
	*/
	protected MUNSQALabTestPCategory[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}

		
		
		List<MUNSQALabTestPCategory> mList = new Query(getCtx(), MUNSQALabTestPCategory.Table_Name
			, MUNSQALabTestPCategory.COLUMNNAME_UNS_QALabTest_ID + " =?", get_TrxName())
			.setParameters(get_ID())
			.setOrderBy(MUNSQALabTestPCategory.COLUMNNAME_UNS_QALabTest_PCategory_ID).list();
			
		m_lines = new MUNSQALabTestPCategory[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
		
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if(getThresHold_Type().equals(THRESHOLD_TYPE_Max))
			setMinValue(BigDecimal.ZERO);
		else if (getThresHold_Type().equals(THRESHOLD_TYPE_Min))
			setMaxValue(BigDecimal.ZERO);
		
		return super.beforeSave(newRecord);
	}

	/**
	 * 
	 * @return MUNSQALabTestPCategory[]
	 */
	public MUNSQALabTestPCategory[] getLines(){
		return getLines(false);
	}
	
	public static boolean checkThresHold(Properties ctx, int id, BigDecimal result){
		MUNSQALabTest lt = MUNSQALabTest.get(ctx, id);
		int RoundingScale = MUNSQALabTest.getRoundingScale(lt);			
		result = result.setScale(RoundingScale, RoundingMode.HALF_UP);
		BigDecimal min = lt.getMinValue().setScale(RoundingScale, RoundingMode.HALF_UP);
		BigDecimal max = lt.getMaxValue().setScale(RoundingScale, RoundingMode.HALF_UP);
		
		if(lt.getThresHold_Type().equals(THRESHOLD_TYPE_Max)){
			if (result.compareTo(min)<=0)
				return true;
		} else if (lt.getThresHold_Type().equals(THRESHOLD_TYPE_Min)){
			if (result.compareTo(max)>=0)
				return true;
		} else {
			if(result.compareTo(min)>=0 && result.compareTo(max)<=0)
				return true;
		}
		
		return false;
	}

	public static MUNSQALabTest get(Properties ctx, int UNS_LabTest_ID) {
		if (s_cache == null)
			s_cache	= new CCache<Integer,MUNSQALabTest>(Table_Name, 20);
		Integer key = new Integer (UNS_LabTest_ID);
		MUNSQALabTest retValue = (MUNSQALabTest) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MUNSQALabTest (ctx, UNS_LabTest_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}
	
	public static int getRoundingScale(MUNSQALabTest lt) {
		if(lt.getDecimalPoint().equals(MUNSQALabTest.DECIMALPOINT_SixDecimalPlaces))
			return 6;
		else if(lt.getDecimalPoint().equals(MUNSQALabTest.DECIMALPOINT_FiveDecimalPlaces))
			return 5;
		else if(lt.getDecimalPoint().equals(MUNSQALabTest.DECIMALPOINT_FourDecimalPlaces))
			return 4;
		else if(lt.getDecimalPoint().equals(MUNSQALabTest.DECIMALPOINT_ThreeDecimalPlaces))
			return 3;
		else if(lt.getDecimalPoint().equals(MUNSQALabTest.DECIMALPOINT_TwoDecimalPlaces))
			return 2;
		else if(lt.getDecimalPoint().equals(MUNSQALabTest.DECIMALPOINT_OneDecimalPlaces))
			return 1;
		else
			return 0;
	}
	

}
