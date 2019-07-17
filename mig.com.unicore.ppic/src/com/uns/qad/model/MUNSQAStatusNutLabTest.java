package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;

import com.uns.model.GeneralCustomization;

public class MUNSQAStatusNutLabTest extends X_UNS_QAStatus_NutLabTest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7924682062803891835L;
	private MUNSQAStatusNutLTLine[] m_lines;

	public MUNSQAStatusNutLabTest(Properties ctx,
			int UNS_QAStatus_NutLabTest_ID, String trxName) {
		super(ctx, UNS_QAStatus_NutLabTest_ID, trxName);
		if (UNS_QAStatus_NutLabTest_ID==0)
			setAD_Org_ID(GeneralCustomization.getDept_ID(get_TrxName(), "QAD"));
	}

	public MUNSQAStatusNutLabTest(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public String getDocumentInfo(){
		String retVal = "RMM "+ getCoconutType()+" : " +getDocumentNo();
		return retVal;
	}
	
	public String completeIt(){
		if (getDocStatus().equals(DOCSTATUS_Drafted) && calcuateRMM()){
			setDocAction(DOCACTION_Complete);
			setDocStatus(DOCSTATUS_InProgress);
			return "Please update result for lab test, "+ getDocumentInfo();
		} else if (getDocStatus().equals(DOCSTATUS_InProgress)){
			calculateLabTest();
			setDocAction(DOCACTION_Re_Activate);
			setDocStatus(DOCSTATUS_Completed);
		} else
			throw new AdempiereException("Invalid condition");
		
		return null;
	}

	private void calculateLabTest() {
		throw new AdempiereException("Calculation result error.");
	}

	public boolean calcuateRMM(){
		if (getCalculationType().equals("Y")){
			return true;
		} else {
			for(MUNSQAStatusNutLTLine ltLine : getLines(false)){
				if(!ltLine.checkInput())
					throw new AdempiereException("Please fill all field on Lines.");
				if(ltLine.isCalculated()){
					ltLine.updateCalculation();
					if(!ltLine.save(get_TrxName()))
						throw new AdempiereException("Calculation error.");;
				}
			}
			setCalculationType("Y");
			return true;
		}
	}	

	/**
	 * 
	 * @param requery
	 * @return MUNSQAStatusNutLTLine[]
	 */
	protected MUNSQAStatusNutLTLine[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQAStatusNutLTLine> mList =
				new Query(getCtx(), MUNSQAStatusNutLTLine.Table_Name
						, MUNSQAStatusNutLTLine.COLUMNNAME_UNS_QAStatus_NutLabTest_ID + " =?"
						, get_TrxName())
		.setParameters(getUNS_QAStatus_NutLabTest_ID())
		.setOrderBy(MUNSQAStatusNutLTLine.COLUMNNAME_UNS_QAStatus_NutLTLine_ID).list();
		
		m_lines = new MUNSQAStatusNutLTLine[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * 
	 * @return MUNSQAStatusNutLTLine[]
	 */
	public MUNSQAStatusNutLTLine[] getLines()
	{
		return getLines(false);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		if(is_ValueChanged(COLUMNNAME_UNS_QAStatus_Type_ID)){
			deleteAndNewLines();
		}
		return super.beforeSave(newRecord);
	}

	private void deleteAndNewLines() {
		for (MUNSQAStatusNutLTLine line : getLines()){
			if(line.delete(false)){
				MUNSQAStatusType type = new MUNSQAStatusType(getCtx(), getUNS_QAStatus_Type_ID(), get_TrxName());
				for (MUNSQAMonitoringAttribut attribut : type.getLines()){
					MUNSQAStatusNutLTLine new_line = new MUNSQAStatusNutLTLine(getCtx(), 0, get_TrxName());
					new_line.setAD_Org_ID(getAD_Org_ID());
					new_line.setUNS_QAAttributeTest_ID(attribut.getUNS_QAAttributeTest_ID());
					new_line.setIsCalculated(attribut.isCalculated());
					if (attribut.getC_UOM_ID()==0)
						throw new AdempiereException("error at configuration Attribute Type.");
					else
						new_line.setC_UOM_ID(attribut.getC_UOM_ID());
					if (!new_line.save())
						throw new AdempiereException("can't update lines.");
				}
			} else
				throw new AdempiereException("can't update lines.");
		}
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean beforeDelete() {
		for (MUNSQAStatusNutLTLine line : getLines()){
			line.delete(false);
		}
		
		return super.beforeDelete();
	}

	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		return super.afterDelete(success);
	}
	
}
