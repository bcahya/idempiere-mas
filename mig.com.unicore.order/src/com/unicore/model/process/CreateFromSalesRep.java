/**
 * 
 */
package com.unicore.model.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.unicore.model.MUNSPackingList;

/**
 * @author setyaka
 *
 */
public class CreateFromSalesRep extends SvrProcess implements ProcessCall {

    private MUNSPackingList m_packingList;
    private int p_salesRep_ID = 0;
    private int p_UNS_Rayon_ID = 0;
    private Timestamp p_dateFrom = null;

    /**
     * 
     */
    public CreateFromSalesRep() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.compiere.process.SvrProcess#prepare()
     */
    @Override
    protected void prepare() {
        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++)
        {
            String name = para[i].getParameterName();
            if (name.equals("SalesRep_ID"))
                p_salesRep_ID = para[i].getParameterAsInt();
            else if(name.equals("DateFrom"))
            	p_dateFrom = para[i].getParameterAsTimestamp();
            else if(name.equals("UNS_Rayon_ID"))
            	p_UNS_Rayon_ID = para[i].getParameterAsInt();
            else
            	log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        
        m_packingList = new MUNSPackingList(getCtx(), getRecord_ID(), get_TrxName());
    }

    /* (non-Javadoc)
     * @see org.compiere.process.SvrProcess#doIt()
     */
    @Override
    protected String doIt() throws Exception {
        
    	if(m_packingList.isProcessed())
    	{
    		return "Can't process processed document.";
    	}
        return m_packingList.createOrderList(p_salesRep_ID, p_dateFrom, p_UNS_Rayon_ID);
    }

}
