/**
 * 
 */
package com.unicore.model.process;

import java.io.File;
import java.util.logging.Level;
import jxl.Workbook;
import com.unicore.model.MUNSImportSimpleTable;
import com.uns.model.process.SimpleImportXLS;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MBankStatement;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

/**
 * @author menjangan
 *
 */
public class ImportBankStatement extends SvrProcess {

	/**
	 * 
	 */
	public ImportBankStatement() {
	}
	
	private int m_importSimpleTable_iD = 0;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		for(ProcessInfoParameter pi : getParameter())
		{
			String paramName = pi.getParameterName();
			if(null == paramName)
				continue;
			else if(paramName.equals("UNS_ImportSimpleTable_ID"))
				m_importSimpleTable_iD = pi.getParameterAsInt();
			else
				log.log(Level.WARNING, "Parameter tidak teridentifikasi " + paramName);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		if(getRecord_ID() <= 0)
			throw new AdempiereException("No Record ID");
		
		MBankStatement bs = new MBankStatement(getCtx(), getRecord_ID(), get_TrxName());
		MAttachment attachment = bs.getAttachment();
		if(null == attachment)
		{
			throw new AdempiereException(
					"Tolong isi attachment dengan file Xls yang mau di import. Ingat file Xls jangan yang lain!!!");
		}
		
		for(MAttachmentEntry entry : attachment.getEntries())
		{
			Workbook wb = null;
			try {
				File file = entry.getFile();
				wb = Workbook.getWorkbook(file);
			} catch (Exception e) {
				throw new AdempiereException("Kesalahan terjadi saat buka file. ".concat(e.getMessage()));
			}
			
			if(wb == null)
				return "Tidak menemukan Work Book";
			
			MUNSImportSimpleTable tableImporter = new MUNSImportSimpleTable(getCtx(), m_importSimpleTable_iD, get_TrxName());
			
			Env.setContext(getCtx(), MBankStatement.COLUMNNAME_C_BankStatement_ID
					, bs.getC_BankStatement_ID());
			
			SimpleImportXLS simpleImport = new SimpleImportXLS();
			simpleImport.setTrxName1(get_TrxName());
			simpleImport.setCtx(getCtx());

			if(!simpleImport.importSimpleTable(wb, tableImporter))
				throw new AdempiereException("Import dibatalkan karena suatu kesalahan :(");
		}
		
		return null;
	}

}
