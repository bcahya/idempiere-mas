/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPrice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import com.uns.model.MUNSPallet;

/**
 * @author Toriq
 * 
 */
public class CopyPrice extends SvrProcess {
	
	private int m_PriceListFrom = 0;
	private int m_PriceListTo = 0;
	private IProcessUI m_ui =null;
	private String m_priceListName = null;
	private String message = null;
	
	protected void prepare(){
		ProcessInfoParameter[] para = getParameter();
		for (int i=0 ; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("PriceListFrom_ID"))
				m_PriceListFrom = para[i].getParameterAsInt();
			else if (name.equals("PriceListTo_ID"))
				m_PriceListTo = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unkwon Parameter : "+ name);
		}
		m_priceListName = DB.getSQLValueString(get_TrxName(), "SELECT Name FROM M_PriceLIst WHERE M_PriceLIst_ID=?",m_PriceListFrom);
	}
	
	protected String doIt() throws Exception{
		// proses version
		String sqld="select now()";
		Timestamp now = DB.getSQLValueTS(get_TrxName(), sqld);

		int checkVerTo = DB.getSQLValue(get_TrxName(), "SELECT m_pricelist_version_id from"
				+ " m_pricelist_version where m_pricelist_id = "+getRecord_ID()+""
						+ " and validfrom >= now()");
		
		if(checkVerTo < 0)
		{
			MPriceListVersion mv= new MPriceListVersion(getCtx(),0,get_TrxName());
			mv.setM_PriceList_ID(m_PriceListTo);
			mv.setName(now.toString());
			mv.setValidFrom(now);
			mv.setDateDoc(now);
			mv.saveEx();
			
			
			//proses created product price
			m_ui = Env.getProcessUI(getCtx());
			String sql ="select p.m_product_id,mpp.pricelist,mpp.pricestd,mpp.pricelimit,p.name from ( "
						+"select max(mpp.m_pricelist_version_id) m_pricelist_version_id ,mpp.m_product_id as m_product_id from M_PriceList mp "
						+"left join M_PriceList_Version mv on mv.m_pricelist_id=mp.m_pricelist_id " 
						+"left join m_productprice mpp on mpp.m_pricelist_version_id=mv.m_pricelist_version_id " 
						+"where mp.M_PriceList_ID=? "
						+"group by mpp.m_product_id) as data "
						+"left join m_productprice mpp on data.m_pricelist_version_id=mpp.m_pricelist_version_id and mpp.m_product_id=data.m_product_id "
						+"left join m_product p on p.m_product_id=mpp.m_product_id"; 
			
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			int count = 0;
			try {
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, m_PriceListFrom);
				rs = pstmt.executeQuery();
				while (rs.next()){
					MProductPrice pp=new MProductPrice(getCtx(), 0,0, get_TrxName());
					pp.setM_PriceList_Version_ID(mv.get_ID());
					pp.setM_Product_ID(rs.getInt(1));
					pp.setPriceList(rs.getBigDecimal(2));
					pp.setPriceStd(rs.getBigDecimal(3));
					pp.setPriceLimit(rs.getBigDecimal(4));
					pp.saveEx();
					
					message ="Created record --> "+rs.getString(5)  ;
					m_ui.statusUpdate(message);
					count ++;
				}
			}catch (SQLException e)
			{
				throw new AdempiereException(e.getMessage());
			}catch (Exception e) 
			{
				// TODO: handle exception
				throw new AdempiereException(e.getMessage());
			}
			finally {
				DB.close(rs,pstmt);
			}
			message = count +" Record Created.";
			return message;
		}
		return "Process Complite";
	}

	/**
	 * 
	 */
	public CopyPrice() {
		// TODO Auto-generated constructor stub
		super();
	}	

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
}
