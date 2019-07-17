package com.uns.qad.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

public class MUNSQAStatusNutLTLine extends X_UNS_QAStatus_NutLTLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 512574666091401079L;
	MUNSQAStatusNutLabTest m_parent = null;

	public MUNSQAStatusNutLTLine(Properties ctx, int UNS_QAStatus_NutLTLine_ID,
			String trxName) {
		super(ctx, UNS_QAStatus_NutLTLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSQAStatusNutLTLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public boolean checkInput() {
		if(getQty().intValue()==0)
			return false;
		return true;
	}

	public void updateCalculation() {
		getParent();
		if (isCalculated()){
			setAverage(getQty().divide(m_parent.getPickedQty(), 2, RoundingMode.HALF_EVEN));
			setPercentage((getQty().divide(m_parent.getWeight(), 4, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(100)));
		}
	}
	
	public MUNSQAStatusNutLabTest getParent(){
		if (m_parent == null)
			m_parent = new MUNSQAStatusNutLabTest(getCtx(), getUNS_QAStatus_NutLabTest_ID(), get_TrxName());
		return m_parent;
	}

}
