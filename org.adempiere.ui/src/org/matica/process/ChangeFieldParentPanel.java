package org.matica.process;

import java.util.logging.Level;


import org.compiere.model.MMAT3FormField;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

public class ChangeFieldParentPanel extends SvrProcess {

	private int m_mat3formfield_id = -1;
	private int m_mat3formcontainer_id = -1;
	private boolean m_copy = true;
	
	
	@Override
	protected void prepare() {
	
		loadParameters();
	}

	@Override
	protected String doIt() throws Exception {
		
		
		MMAT3FormField field = new MMAT3FormField(getCtx(), m_mat3formfield_id, get_TrxName());
		
		if (m_copy == false){	
			field.setMAT3_FormContainer_ID(m_mat3formcontainer_id);
			if (field.save(get_TrxName())) {
				return "";
			}else{
				return "Error executing process 'ChangeFieldParentPanel'";
			}
		}else{
			MMAT3FormField newfield = new MMAT3FormField(getCtx(), 0, get_TrxName());
			PO.copyValues(field, newfield);
			newfield.setMAT3_FormContainer_ID(m_mat3formcontainer_id);
			if (newfield.save(get_TrxName())) {
				return "";
			}else{
				return "Error executing process 'ChangeFieldParentPanel'";
			}
			
			
		}
		
	}
	
	
	private void loadParameters(){		
		for (ProcessInfoParameter para : getParameter())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("NewContainerID")){
				m_mat3formcontainer_id = para.getParameterAsInt();
			}
			else if (name.equals("MAT3_FormField_ID")){
				m_mat3formfield_id = para.getParameterAsInt();
			}
			else if (name.equals("MAT3_Copy")){
				m_copy = para.getParameterAsBoolean();
			}
			else {
				log.log(Level.INFO, "Unknown Parameter: " + name);
			}
		}
	}

}
