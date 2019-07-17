/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.compiere.model.PO;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author menjangan
 * @author ALBURHANY
 */
public class MUNSDocumentValidator extends X_UNS_DocumentValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean m_isTobeValidate = false;
	private PO m_po = null;

	/**
	 * @param ctx
	 * @param UNS_DocumentValidator_ID
	 * @param trxName
	 */
	public MUNSDocumentValidator(Properties ctx, int UNS_DocumentValidator_ID,
			String trxName) {
		super(ctx, UNS_DocumentValidator_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDocumentValidator(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	public boolean isMandatoryDescription()
	{
		if(!isNeedCheckMandatoryField())
			return false;
		
		return super.isMandatoryDescription();
	}
	
	@Override
	public boolean isMandatoryAttachment()
	{
		if(!isNeedCheckMandatoryField())
			return false;
		
		return super.isMandatoryAttachment();
	}
	
	public boolean isNeedCheckMandatoryField()
	{
		if(!this.m_isTobeValidate)
			runCheckQuery();
		
		return this.m_isTobeValidate;
	}
	
	public void setValidationPO(PO po)
	{
		this.m_po = po;
	}
	
	public PO getValidationPO()
	{
		return this.m_po;
	}
	
	public void setIsNeedCheckMandatoryField(boolean mandatory)
	{
		this.m_isTobeValidate = mandatory;
	}
	
	private void checkWindow()
	{
		if(getAD_Window_ID() <= 0)
			return;
		
		m_isTobeValidate = getAD_Window_ID() == getValidationPO().getMyWindow_ID();			
	}
	
	@Override
	public boolean isAlwaysError()
	{
		if(!m_isTobeValidate)
			runCheckQuery();
		
		return m_isTobeValidate ? super.isAlwaysError() : false;
	}
	
	
	public void runCheckQuery()
	{		
		String sql = getQuery();
		sql = parserPOContext(sql);
		
		if(null == sql || sql.length() == 0 || sql.isEmpty())
			return;
		
		m_isTobeValidate = DB.getSQLValue(get_TrxName(), sql) > 0;
		if(m_isTobeValidate)
			checkWindow();
	}
	
	public static MUNSDocumentValidator[] getAll(Properties ctx, String trxName)
	{
		int[] allID = getAllIDs(Table_Name, null, trxName);
		List<MUNSDocumentValidator> list = new ArrayList<MUNSDocumentValidator>();
		
		for(int id : allID)
		{
			MUNSDocumentValidator docValidate = 
					new MUNSDocumentValidator(ctx, id, trxName);
			if (!docValidate.isActive())
			{
				continue;
			}
			list.add(docValidate);
		}
		
		MUNSDocumentValidator[] docs = new MUNSDocumentValidator[list.size()];
		list.toArray(docs);
		
		return docs;
	}
	
	public String parserPOContext(String sql)
	{
		if (sql == null || sql.length() == 0)
			return "";

		String token;
		String inStr = new String(sql);
		StringBuilder outStr = new StringBuilder();
		int i = inStr.indexOf('&');
		
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));
			inStr = inStr.substring(i+1, inStr.length());

			int j = inStr.indexOf('&');						
			if (j < 0)
			{
				throw new AdempiereUserError("Invalid context");
			}
			token = inStr.substring(0, j);
			outStr.append(getValidationPO().get_Value(token));
			inStr = inStr.substring(j+1, inStr.length());
			i = inStr.indexOf('&');
		}
		
		outStr.append(inStr);
		sql = outStr.toString();
		
		return Env.parseContext(getCtx(), getValidationPO().getMyWindowNo(), 
				getValidationPO().getMyTabNo(), sql, false, false);
	}
	
	public int getTiming(String OnCondition, boolean isModelChange)
	{
		int timing = 0;

		if(OnCondition.equals(ONCONDITION_All_TypeORTiming))
			return timing = 0;
		
		if(isModelChange)
		{
			if(OnCondition.equals(ONCONDITION_TYPE_BEFORE_NEW))
				timing = 1;
			else if(OnCondition.equals(ONCONDITION_TYPE_NEW))
				timing = 1;
			else if(OnCondition.equals(ONCONDITION_TYPE_AFTER_NEW))
				timing = 4;
			else if(OnCondition.equals(ONCONDITION_TYPE_AFTER_NEW_REPLICATION))
				timing = 7;
			else if(OnCondition.equals(ONCONDITION_TYPE_BEFORE_CHANGE))
				timing = 2;
			else if(OnCondition.equals(ONCONDITION_TYPE_CHANGE))
				timing = 2;
			else if(OnCondition.equals(ONCONDITION_TYPE_AFTER_CHANGE))
				timing = 5;
			else if(OnCondition.equals(ONCONDITION_TYPE_AFTER_CHANGE_REPLICATION))
				timing = 8;
			else if(OnCondition.equals(ONCONDITION_TYPE_BEFORE_DELETE))
				timing = 3;
			else if(OnCondition.equals(ONCONDITION_TYPE_DELETE))
				timing = 3;
			else if(OnCondition.equals(ONCONDITION_TYPE_AFTER_DELETE))
				timing = 6;
			else if(OnCondition.equals(ONCONDITION_TYPE_BEFORE_DELETE_REPLICATION))
				timing = 9;
		}
		
		else if(!isModelChange)
		{
			if(OnCondition.equals(ONCONDITION_TIMING_BEFORE_PREPARE))
				timing = 1;
			else if(OnCondition.equals(ONCONDITION_TIMING_BEFORE_VOID))
				timing = 2;
			else if(OnCondition.equals(ONCONDITION_TIMING_BEFORE_CLOSE))
				timing = 3;
			else if(OnCondition.equals(ONCONDITION_TIMING_BEFORE_REACTIVATE))
				timing = 4;
			else if(OnCondition.equals(ONCONDITION_TIMING_BEFORE_REVERSECORRECT))
				timing = 5;
			else if(OnCondition.equals(ONCONDITION_TIMING_BEFORE_REVERSEACCRUAL))
				timing = 6;
			else if(OnCondition.equals(ONCONDITION_TIMING_BEFORE_COMPLETE))
				timing = 7;
			else if(OnCondition.equals(ONCONDITION_TIMING_AFTER_PREPARE))
				timing = 8;
			else if(OnCondition.equals(ONCONDITION_TIMING_AFTER_VOID))
				timing = 9;
			else if(OnCondition.equals(ONCONDITION_TIMING_AFTER_CLOSE))
				timing = 10;
			else if(OnCondition.equals(ONCONDITION_TIMING_AFTER_REACTIVATE))
				timing = 11;
			else if(OnCondition.equals(ONCONDITION_TIMING_AFTER_REVERSECORRECT))
				timing = 12;
			else if(OnCondition.equals(ONCONDITION_TIMING_AFTER_REVERSEACCRUAL))
				timing = 13;
			else if(OnCondition.equals(ONCONDITION_TIMING_AFTER_COMPLETE))
				timing = 14;
			else if(OnCondition.equals(ONCONDITION_TIMING_BEFORE_POST))
				timing = 15;
			else if(OnCondition.equals(ONCONDITION_TIMING_AFTER_POST))
				timing = 16;
		}
		return timing;
	}
}
