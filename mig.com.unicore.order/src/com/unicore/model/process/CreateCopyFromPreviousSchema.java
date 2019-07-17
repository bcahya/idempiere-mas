package com.unicore.model.process;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;

import com.unicore.model.MDiscountSchema;

public class CreateCopyFromPreviousSchema extends SvrProcess {

	private int m_record_id = 0;
	private String m_trxName = null;
	private Properties m_ctx = null;
	
	public CreateCopyFromPreviousSchema() {
		super();
	}

	@Override
	protected void prepare() {
		m_record_id = getRecord_ID();
		m_ctx = getCtx();
		m_trxName = get_TrxName();
	}

	@Override
	protected String doIt() throws Exception {
		MDiscountSchema currentSchema = new MDiscountSchema(m_ctx, m_record_id, m_trxName);
		if(currentSchema.getPreviousSchema_ID() <= 0)
		{
			throw new AdempiereException("No preveouse discount.");
		}
		currentSchema.setProcessInfo(getProcessInfo());
		return currentSchema.copyFromPrevDiscount();
	}

}
