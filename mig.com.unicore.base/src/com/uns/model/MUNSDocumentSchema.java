/**
 * 
 */
package com.uns.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import com.uns.base.model.Query;

/**
 * @author Burhani Adam
 *
 */
public class MUNSDocumentSchema extends X_UNS_DocumentSchema {

	private StringBuilder m_SQLSelect = new StringBuilder();
	private StringBuilder m_SQLWhere = new StringBuilder();
	/**
	 * 
	 */
	private static final long serialVersionUID = -6255130774359366203L;

	/**
	 * @param ctx
	 * @param UNS_DocumentSchema_ID
	 * @param trxName
	 */
	public MUNSDocumentSchema(Properties ctx, int UNS_DocumentSchema_ID,
			String trxName) {
		super(ctx, UNS_DocumentSchema_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDocumentSchema(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected MUNSDocSchemaMapCol[] getMapCols()
	{
		List<MUNSDocSchemaMapCol> list = new Query(getCtx(), MUNSDocSchemaMapCol.Table_Name, COLUMNNAME_UNS_DocumentSchema_ID + "=?",
				get_TrxName()).setParameters(get_ID()).setOrderBy(X_UNS_DocSchema_MapCol.COLUMNNAME_Line).list();

		return list.toArray(new MUNSDocSchemaMapCol[list.size()]);
	} // getMapCols
	
	protected MUNSDocSchemaCondition[] getConditions()
	{
		List<MUNSDocSchemaMapCol> list = new Query(getCtx(), MUNSDocSchemaCondition.Table_Name, COLUMNNAME_UNS_DocumentSchema_ID + "=?",
				get_TrxName()).setParameters(get_ID()).setOrderBy(X_UNS_DocSchema_Condition.COLUMNNAME_Line).list();

		return list.toArray(new MUNSDocSchemaCondition[list.size()]);
	} // getConditions
	
	protected String generateDocLines(MUNSDocChecking dc)
	{
		int params = 1;
		String m_Msg = null;
		String whereRayon = " C_BPartner_ID IN (SELECT C_BPartner_ID FROM C_BPartner_Location WHERE"
				+ " UNS_Rayon_ID=?)";
		String whereSalesRep = " SalesRep_ID=?";
		initSQLSelect();
		initSQLWhere();
		MUNSDocSchemaMapCol[] mc = getMapCols();
		StringBuilder sql = new StringBuilder();
		sql.append(m_SQLSelect).append(m_SQLWhere);
		sql.append(" AND (").append(getAD_Column().getColumnName()).append(" BETWEEN ")
		.append("?").append(" AND ").append("?").append(")");
		
		if(dc.getUNS_Rayon_ID() > 0)
			sql.append(" AND ").append(whereRayon);
		if(dc.getSalesRep_ID() > 0)
			sql.append(" AND").append(whereSalesRep);

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.prepareStatement(sql.toString(), get_TrxName());
			stmt.setTimestamp(params++, dc.getDateFrom());
			stmt.setTimestamp(params++, dc.getDateTo());
			if(dc.getUNS_Rayon_ID() > 0)
				stmt.setInt(params++, dc.getUNS_Rayon_ID());
			if(dc.getSalesRep_ID() > 0)
				stmt.setInt(params++, dc.getSalesRep_ID());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				MUNSDocCheckingLine cl = new MUNSDocCheckingLine(dc);
				for(int i = 0; i < mc.length; i++)
				{
					cl.set_ValueOfColumn(mc[i].getColumnReferer_ID(),
							rs.getObject(mc[i].getAD_Column().getColumnName()));
				}
				if(!cl.save())
					throw new AdempiereException("Failed while trying create checking line");
			}
		}
		catch(SQLException e)
		{
			throw new AdempiereException(e.toString());
		}
		
		return m_Msg;
	}
	
	private void initSQLSelect()
	{
		m_SQLSelect = new StringBuilder();
		m_SQLSelect.append("SELECT ");
		MUNSDocSchemaMapCol[] mc = getMapCols();
		
		for(int i = 0; i < mc.length; i++)
		{
			if(i!=0)
				m_SQLSelect.append(", ").append(mc[i].getAD_Column().getColumnName());
			else
				m_SQLSelect.append(mc[i].getAD_Column().getColumnName());
		}
		
		m_SQLSelect.append(" FROM ").append(getAD_Table().getTableName());	
	}
	
	private void initSQLWhere()
	{
		String AndOr = null;
		StringBuilder sqlStatement = new StringBuilder();
		String operation = null;
		m_SQLWhere = new StringBuilder();
		m_SQLWhere.append(" WHERE ");
		MUNSDocSchemaCondition[] c = getConditions();
		
		for(int i = 0; i < c.length; i++)
		{
			switch (c[i].getOperation()) {
			case X_UNS_DocSchema_Condition.OPERATION_Eq:
				operation = "=";
				break;
			case X_UNS_DocSchema_Condition.OPERATION_Gt:
				operation = ">";
				break;
			case X_UNS_DocSchema_Condition.OPERATION_GtEq:
				operation = ">=";
				break;
			case X_UNS_DocSchema_Condition.OPERATION_Le:
				operation = "<";
				break;
			case X_UNS_DocSchema_Condition.OPERATION_LeEq:
				operation = "<=";
				break;
			case X_UNS_DocSchema_Condition.OPERATION_Like:
				operation = "%";
				break;
			case X_UNS_DocSchema_Condition.OPERATION_NotEq:
				operation = "<>";
				break;
			case X_UNS_DocSchema_Condition.OPERATION_X:
				operation = "OR";
				break;
			}
			AndOr = c[i].getAndOr().equals(X_UNS_DocSchema_Condition.ANDOR_And) ? "AND" : "OR";
			if(c[i].getOperation().equals(X_UNS_DocSchema_Condition.OPERATION_Sql))
				sqlStatement.append(c[i].getSQLStatement());
			else
				sqlStatement.append(c[i].getAD_Column().getColumnName()).append(operation).append(c[i].getValueFrom());
					
			if(i!=0)
				m_SQLWhere.append(AndOr).append(" ").append(sqlStatement);
			else
				m_SQLWhere.append(sqlStatement);
		}
	}
}