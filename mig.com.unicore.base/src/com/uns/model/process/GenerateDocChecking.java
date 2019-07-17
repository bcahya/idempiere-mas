/**
 * 
 */
package com.uns.model.process;

import java.sql.Timestamp;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

import com.uns.model.MUNSDocChecking;


/**
 * @author toriq
 *
 */
public class GenerateDocChecking extends SvrProcess {

	/** tHE Record */
	private int p_Record_ID = 0;
	private int m_UNSRayon_ID = 0;
	private boolean m_DeleteOld = false;
	private int m_SalesRep_ID = 0 ;
	private Timestamp m_DateFrom = null;
	private Timestamp m_DateTo = null;
	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			//if (name.equals("Tabel_ID"))
			//	m_Table_ID = para[i].getParameter_ToAsInt();
//			if (name.equals("SalesRep_ID"))
//				m_SalesRep_ID = para[i].getParameterAsInt();
//			else if (name.equals("UNS_Rayon_ID"))
//				m_UNSRayon_ID = para[i].getParameterAsInt();
//			else if (name.equals("DateFrom"))
//				m_DateFrom = para[i].getParameterAsTimestamp();
//			else if (name.equals("DateTo"))
//				m_DateTo = para[i].getParameterAsTimestamp();
			if (name.equals("DeleteOld"))
				m_DeleteOld = para[i].getParameterAsBoolean();
			else 
				log.log(Level.SEVERE, "Unknown Parameter :" + name);
		}
		p_Record_ID = getRecord_ID();				
	}


	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		int cnt = 0;
		if(p_Record_ID <=0)
		{
			throw new AdempiereException("No Record ID");
		}
		
		MUNSDocChecking docChecking = new MUNSDocChecking(getCtx(), p_Record_ID, get_TrxName());
		m_SalesRep_ID = docChecking.getSalesRep_ID();
		m_UNSRayon_ID = docChecking.getUNS_Rayon_ID();
		m_DateFrom = docChecking.getDateFrom();
		m_DateTo = docChecking.getDateTo();
		
//		if (docChecking.getUNS_DocChecking_ID() == 0)
//			throw new AdempiereUserError("@No@ @DocChecking");
		if (docChecking.isProcessed())
			return "Can't Process Processed Document...";
//		if (m_SalesRep_ID != docChecking.getSalesRep_ID())
//			docChecking.setSalesRep_ID(m_SalesRep_ID);
//		if (m_UNSRayon_ID != docChecking.getUNS_Rayon_ID())
//			docChecking.setUNS_Rayon_ID(m_UNSRayon_ID);
//		if (m_DateFrom != docChecking.getDateFrom())
//			docChecking.setDateDoc(m_DateFrom);
//		if (m_DateTo != docChecking.getDateTo())
//			docChecking.setDateTo(m_DateTo);
		
		cnt = docChecking.recalcDocChecking(m_SalesRep_ID, m_UNSRayon_ID, m_DateFrom, m_DateTo, 
				m_DeleteOld);
		
		if (cnt == -1)
			throw new AdempiereUserError("Error Calculating DocChecking, Please Check Log");
		
		if (docChecking.save())
			return "@Inserted@=" + cnt;
				throw new AdempiereUserError("Error Caculating DocChecking line, Please Check Log");
	}

}