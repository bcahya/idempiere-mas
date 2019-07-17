/**
 * @author Menjangan.....
 * Generate Price List Schema 
 * Combine Outlet Grade, Outlet Type, and Cluster Area.
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDiscountSchema;
import org.compiere.model.MDiscountSchemaLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.model.X_UNS_Cluster;
import com.unicore.model.X_UNS_Outlet_Grade;
import com.unicore.model.X_UNS_Outlet_Type;

/**
 * @author root
 *
 */
public class GeneratePriceListSchema extends SvrProcess {

	/**
	 * 
	 */
	public GeneratePriceListSchema() {
		super();
	}
	
	private boolean					m_deleteOld = true;
	private MDiscountSchema			m_schema = null;
	List<PriceListSchemaHelper>		m_Grade = null;
	List<PriceListSchemaHelper>		m_Type = null;
	List<PriceListSchemaHelper>		m_Cluster = null;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		for(int i=0; i < params.length; i++)
		{
			String paramName = params[i].getParameterName();
			if(null == paramName)
				continue;
			else if(paramName.equals("DeleteOld"))
				m_deleteOld = params[i].getParameterAsBoolean();
			else
				log.log(Level.SEVERE, "Undefined parameter " . concat(paramName));
		}
		
		m_schema = new MDiscountSchema(getCtx(), getRecord_ID(), get_TrxName());
		m_Grade = initialOutletGrade();
		m_Type = initialOutletType();
		m_Cluster = initialCluster();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if(!m_schema.getDiscountType().equals("U"))
			return "";
		if(m_deleteOld && !deleteOld())
			throw new AdempiereException("Failed on delete lines.");
		if(m_schema.getLines(true).length > 0)
			throw new AdempiereException("Schema has ben generated.");
		if(m_Grade == null || m_Grade.size() == 0)
			return "0 record has ben created. no defined Outlet Grade.";
		if(m_Type == null || m_Type.size() == 0)
			return "0 record has ben created. no defined Outlet Type.";
		if(m_Cluster == null || m_Cluster.size() == 0)
			return "0 record has ben created. no defined Cluster Area.";
		
		return createRecord(0, 0, 0, 0). toString() . concat(" Record success fully created");
	}

	private boolean deleteOld()
	{
		String sql = "DELETE FROM M_DiscountSchemaLine WHERE M_DiscountSchema_ID = ?";
		int retVal = DB.executeUpdate(sql, m_schema.get_ID(), false, get_TrxName());
		return retVal != -1;
	}
	
	private List<PriceListSchemaHelper> createHelpers(String tableName)
	{
		StringBuilder sb = new StringBuilder("SELECT ").append(tableName).append("_ID, ")
				.append("AddType, AddPrice ").append(" FROM ").append(tableName);
		
		List<PriceListSchemaHelper> list = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = sb.toString();
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setString(1, "Y");
			rs = st.executeQuery();
			while (rs.next())
			{
				list.add(new PriceListSchemaHelper(
						rs.getInt(1), rs.getString(2).equals("Y"), rs.getBigDecimal(3)));
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, e.getMessage());
		}
		finally
		{
			DB.close(rs, st);
		}
		return list;
	}
	
	private List<PriceListSchemaHelper> initialOutletGrade()
	{
		return createHelpers(X_UNS_Outlet_Grade.Table_Name);
	}
	
	private List<PriceListSchemaHelper> initialOutletType()
	{
		return createHelpers(X_UNS_Outlet_Type.Table_Name);
	}
	
	private List<PriceListSchemaHelper> initialCluster()
	{
		return createHelpers(X_UNS_Cluster.Table_Name);
	}
	
	/**
	 * Create recursive new schema line. combine  by Outlet Grade, Outlet Type, And Cluster Area
	 * @param gradeCount
	 * @param typeCount
	 * @param clusterCount
	 * @param recordCreated
	 * @return {@link Integer} of record created
	 */
	private Integer createRecord(int gradeCount, int typeCount, int clusterCount, int recordCreated)
	{
		BigDecimal addAmt = m_Cluster.get(clusterCount).getAddValue().add(m_Grade.get(gradeCount).getAddValue()
				.add(m_Type.get(typeCount).getAddValue())).divide(new BigDecimal(3), 4, RoundingMode.HALF_DOWN);
		
		log.info("Add ammount : " + addAmt);
		
		MDiscountSchemaLine schemaLine = new MDiscountSchemaLine(getCtx(), 0, get_TrxName());
		schemaLine.setAD_Org_ID(m_schema.getAD_Org_ID());
		schemaLine.setC_BPartner_ID(-1);
		schemaLine.setC_ConversionType_ID(-1);
		schemaLine.setClassification(null);
		schemaLine.setConversionDate(null);
		schemaLine.setGroup1(null);
		schemaLine.setGroup2(null);
		schemaLine.setIsActive(true);
		schemaLine.setLimit_AddAmt(addAmt);
		schemaLine.setLimit_Base(null);
		schemaLine.setLimit_Discount(Env.ZERO);
		schemaLine.setLimit_Fixed(Env.ZERO);
		schemaLine.setLimit_MaxAmt(Env.ZERO);
		schemaLine.setLimit_MinAmt(Env.ZERO);
		schemaLine.setLimit_Rounding(null);
		schemaLine.setList_AddAmt(addAmt);
		schemaLine.setList_Base(null);
		schemaLine.setList_Discount(Env.ZERO);
		schemaLine.setList_Fixed(Env.ZERO);
		schemaLine.setList_MaxAmt(Env.ZERO);
		schemaLine.setList_Rounding(null);
		schemaLine.setM_DiscountSchema_ID(m_schema.get_ID());
		schemaLine.setM_Product_Category_ID(-1);
		schemaLine.setM_Product_ID(-1);
		schemaLine.setStd_AddAmt(addAmt);
		schemaLine.setStd_Base(null);
		schemaLine.setStd_Discount(Env.ZERO);
		schemaLine.setStd_Fixed(Env.ZERO);
		schemaLine.setStd_MaxAmt(Env.ZERO);
		schemaLine.setStd_MinAmt(Env.ZERO);
		schemaLine.setStd_Rounding(null);
		schemaLine.setUNS_Cluster_ID(m_Cluster.get(clusterCount).getID());
		schemaLine.setUNS_Outlet_Grade_ID(m_Grade.get(gradeCount).getID());
		schemaLine.setUNS_Outlet_Type_ID(m_Type.get(typeCount).getID());
		schemaLine.saveEx();
		
		log.log(Level.INFO, "COMBINE......................");
		log.log(Level.INFO, "Outlet Grade Index : " + gradeCount);
		log.log(Level.INFO, "Outlet Type Index : " + typeCount);
		log.log(Level.INFO, "Cluster Are Index : " + clusterCount);
		log.log(Level.INFO, (++recordCreated) + " Record Created : " + schemaLine.toString());
		
		if(gradeCount == m_Grade.size() - 1 && typeCount == m_Type.size() - 1 && clusterCount == m_Cluster.size() - 1)
		{
			return recordCreated;
		}
		else if(typeCount == m_Type.size() - 1 && clusterCount == m_Cluster.size() - 1)
		{
			gradeCount++;
			typeCount = 0;
			clusterCount = -1;
		}
		else if(clusterCount == m_Cluster.size() - 1)
		{
			typeCount++;
			clusterCount = -1;
		}
		
		return createRecord(gradeCount, typeCount, ++clusterCount, recordCreated);
	}
}

/**
 * 
 * @author root
 *
 */
class PriceListSchemaHelper 
{
	private int m_ID = 0;
	private boolean m_isPercent = false;
	private BigDecimal m_addValue = Env.ZERO;
	
	public PriceListSchemaHelper(int ID, boolean isPercent, BigDecimal value)
	{
		setID(ID); setIsPercent(isPercent); setAddValue(value);
	}
	
	public void setID(int ID)
	{
		this.m_ID = ID;
	}
	
	public void setIsPercent(boolean isPercent)
	{
		this.m_isPercent = isPercent;
	}
	
	public void setAddValue(BigDecimal value)
	{
		this.m_addValue = value;
	}
	
	public int getID()
	{
		return this.m_ID;
	}
	
	public boolean isPercent()
	{
		return this.m_isPercent;
	}
	
	public BigDecimal getAddValue()
	{
		return this.m_addValue;
	}
}
