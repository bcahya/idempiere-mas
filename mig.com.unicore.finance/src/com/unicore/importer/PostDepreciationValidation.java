/**
 * 
 */
package com.unicore.importer;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import jxl.Sheet;
import com.unicore.base.model.MPaymentAllocate;
import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.MInvoice;
import com.uns.base.model.Query;
import com.uns.model.process.SimpleImportXLS;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDepreciationEntry;
import org.compiere.model.MPayment;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.jfree.util.Log;

import com.uns.importer.ImporterValidation;

/**
 * @author root
 *
 */
public class PostDepreciationValidation implements ImporterValidation {
	
	private static CLogger log = CLogger.getCLogger(PostDepreciationValidation.class);
	
	private Properties m_Ctx = null;
	//private Sheet m_Sheet = null;
	private String m_trxName = null;
	private final static String ACTION = "Action";
	
	public PostDepreciationValidation(Properties ctx, Sheet sheet, String trxName)
	{
		m_Ctx = ctx;
		//m_Sheet = sheet;
		m_trxName = trxName;
	}
	
	
	/**
	 * 
	 */
	public PostDepreciationValidation() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable, int)
	 */
	@Override
	public String beforeSave(
			Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable, int)
	 */
	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		if (freeColVals.get(ACTION) == null)
			return null;
		
		String action = (String) freeColVals.get(ACTION);
		
		if (!action.equals(DocAction.ACTION_Complete)
				&& !action.equals(DocAction.ACTION_Close)
				&& !action.equals(DocAction.ACTION_Prepare))
			return null;
		
		MDepreciationEntry deprEntry = (MDepreciationEntry) po;
		
		if (!deprEntry.processIt(action))
		{
			log.log(log.getLevel(), "Error while doing action of the Depreciation Entry.");
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveAllRow(java.util.Hashtable, org.compiere.model.PO[])
	 */
	@Override
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#getPOReferenceMap()
	 */
	@Override
	public Hashtable<String, PO> getPOReferenceMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
		
	}
}
