/**
 * 
 */
package com.uns.base.acct;

import java.sql.ResultSet;

import com.uns.base.acct.DocLine;
import com.uns.qad.model.MUNSQAMLabResult;
import com.uns.qad.model.MUNSQAMLabResultInfo;

import org.compiere.model.MAcctSchema;

/**
 * @author YAKA
 *
 */
public abstract class Doc extends org.compiere.acct.Doc {

	/**
	 * 
	 */
	protected Doc (MAcctSchema as, Class<?> clazz, ResultSet rs, String defaultDocumentType, String trxName)
	{
		super(as, clazz, rs, defaultDocumentType, trxName);
	}
	
	/** UNS Production    */
	public static final String	DOCTYPE_UNSProduction	= "PCO";
	/** UNS Quality Assurance    */
	public static final String	DOCTYPE_UNSQualityAssurance	= "QAD";
	
	/**	Contained Doc Lines			*/
	protected DocLine[]			p_lines;
	protected MUNSQAMLabResult m_LabResult = null;
	
	
	/**
	 *  Get DocLine with ID
	 *  @param Record_ID Record ID
	 *  @return DocLine
	 */
	public DocLine getDocLine (int Record_ID)
	{
		if (p_lines == null || p_lines.length == 0 || Record_ID == 0)
			return null;

		for (int i = 0; i < p_lines.length; i++)
		{
			if (p_lines[i].get_ID() == Record_ID)
				return p_lines[i];
		}
		return null;
	}   //  getDocLine


	@Override
	public String getDocumentNo() {
		String retval = null;
		if (getPO().get_Table_ID()==MUNSQAMLabResult.Table_ID){
			int index = p_po.get_ColumnIndex("ReferenceNo");
			if (index == -1)
				return super.getDocumentNo();
			retval = (String)p_po.get_Value(index);
			
			return retval;
		} else if (getPO().get_Table_ID()==MUNSQAMLabResultInfo.Table_ID){
			retval = getMUNSQAMLabResult().getReferenceNo();
			
			int index = p_po.get_ColumnIndex("LineMachine");
			if (index == -1)
				return super.getDocumentNo();
			
			retval = retval.concat("-").concat((String)p_po.get_Value(index));
			
			return retval;
		}
		
		return super.getDocumentNo();
	}
	
	public MUNSQAMLabResult getMUNSQAMLabResult() {
		if (getPO().get_Table_ID()==MUNSQAMLabResult.Table_ID
				|| getPO().get_Table_ID()==MUNSQAMLabResultInfo.Table_ID){
			
			if (m_LabResult!=null)
				return m_LabResult;
			
			int index = p_po.get_ColumnIndex("UNS_QAMLab_Result_ID");
			
			int ID = (Integer) p_po.get_Value(index);
			
			m_LabResult  = new MUNSQAMLabResult(getCtx(), ID, getTrxName());
			
			return m_LabResult;
		} 
		
		return null;
	}
	
}
