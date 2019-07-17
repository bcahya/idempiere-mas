package com.uns.model.process;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.model.MTransaction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/******************************************************************************
 * Reinitialize M_Transcation. 
 * Re-initialize the entire transaction data based on the table 
 * referenced by m_transaction. Only the data with complete or 
 * closed status to be calculated in this process.
 * @author Menjangan :: endang.nurseha@untasoft.com
 * @see www.untasoft.com
 ******************************************************************************/
public class UNSReinitialTransaction extends SvrProcess 
{
	private boolean m_reset = false;
	private Timestamp m_dateFrom = null;
	private Timestamp m_dateTo = null;
	private IProcessUI m_ui = null;
	
	private final String[] m_methods = new String[] 
	{
		"doInitializeInOut",
		"doInitializeMovement",
		"doInitializeInventory",
		"doInitializeProduction",
		"doInitializeProject",
		"doInitializePackingList"
	};
	
	public UNSReinitialTransaction() 
	{
		super ();
	}

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] params = getParameter();
		for (int i=0; i<params.length; i++)
		{
			ProcessInfoParameter param = params[i];
			String name = param.getParameterName();
			if ("Reset".equals(name))
			{
				m_reset = param.getParameterAsBoolean();
			}
			else if ("DateFrom".equals(name))
			{
				m_dateFrom = param.getParameterAsTimestamp();
			}
			else if ("DateTo".equals(name))
			{
				m_dateTo = param.getParameterAsTimestamp();
			}
			else
			{
				log.info("Parameter not used [" + name + "]");
			}
		}
	}
	

	@Override
	protected String doIt() throws Exception 
	{
		log.info("Initialize Process UI");
		m_ui = Env.getProcessUI(getCtx());
		
		if (m_reset)
		{
			log.info("Preparing to delete transactions");
			String sql = "DELETE FROM M_Transaction";
			m_ui.statusUpdate("Deleting M_Transaction....");
			int count = DB.executeUpdate(sql, get_TrxName());
			log.info(count + " transaction deleted");
		}
		
		for (int i=0; i<m_methods.length; i++)
		{
			Method method = null;
			String methodName = m_methods[i];
			try 
			{
				method = this.getClass().getMethod(methodName);
			} 
			catch (NoSuchMethodException | SecurityException e)
			{
				e.printStackTrace();
				throw new Exception(e);
			}
			
			try 
			{
				method.invoke(this);
			}
			catch (IllegalAccessException e) 
			{
				throw new Exception(e);
			}
			catch (IllegalArgumentException e) 
			{
				throw new Exception(e);
			}
			catch (InvocationTargetException e) 
			{
				throw new Exception(e);
			}
		}
		
		return null;
	}
	
	/**
	 * Reinitialize inout transaction
	 */
	public void doInitializeInOut ()
	{
		m_ui.statusUpdate("Processing M_InOutLine...");
		String sqlFrom = " M_InOutLineMA ma INNER JOIN M_InOutLine iol ON "
				+ " iol.M_InOutLine_ID = ma.M_InOutLine_ID INNER JOIN "
				+ " M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID ";
		String sqlWc = " io.DocStatus IN (?, ?) AND io.IsActive = ? "
				+ " AND iol.IsActive = ? AND ma.IsActive = ?";
		
		if (m_dateFrom != null && m_dateTo != null)
		{
			sqlWc += " AND io.MovementDate BETWEEN '" + m_dateFrom + "' AND '"
					+ m_dateTo + "' ";
		}
		else if (m_dateFrom != null)
		{
			sqlWc += " AND io.MovementDate >= '" + m_dateFrom + "' ";
		}
		else if (m_dateTo != null)
		{
			sqlWc += " AND io.MovementDate <= '" + m_dateTo + "' "; 
		}
		
		String sql = "SELECT COUNT (*) FROM " + sqlFrom + " WHERE " + sqlWc;
		int total = DB.getSQLValue(get_TrxName(), sql, "CO", "CL", 
				"Y", "Y", "Y");
		log.info(total + " record will be processed...");
		sql = "SELECT iol.M_InOutLine_ID, iol.AD_Org_ID, iol.M_Locator_ID, "
				+ " iol.M_Product_ID, io.MovementDate, ma.MovementQty, "
				+ " ma.M_AttributeSetInstance_ID, MovementType " 
				+ sqlFrom + " WHERE " + sqlWc;
		
		int count = 1;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setString(1, "CO");
			st.setString(2, "CL");
			st.setString(3, "Y");
			st.setString(4, "Y");
			st.setString(5, "Y");
			rs = st.executeQuery();
			while (rs.next())
			{
				m_ui.statusUpdate("Processing M_InOutLineMA [" + count 
							+ " to " + total);
				int M_InOutLine_ID = rs.getInt(1);
				int AD_Org_ID = rs.getInt(2);
				int M_Locator_ID = rs.getInt(3);
				int M_Product_id = rs.getInt(4);
				Timestamp movementDate = rs.getTimestamp(5);
				BigDecimal movementQty = rs.getBigDecimal(6);
				int M_AttributeSetInstance_ID = rs.getInt(7);
				String movementType = rs.getString(8);
				boolean isConvertToNegate = movementType.contains("-");
				
				if (null == movementQty || movementQty.signum() == 0)
				{
					continue;
				}
				else if (isConvertToNegate)
				{
					movementQty = movementQty.negate();
				}
				
				MTransaction trx = null;
				if (!m_reset)
				{
					log.info("Update M_Transaction {M_InOutLine_ID[" 
							+ M_InOutLine_ID + "], M_AttributeSetInstance_ID[" 
							+ M_AttributeSetInstance_ID + "]}");
					trx = getTransaction("M_InOutLine_ID", M_InOutLine_ID, 
							M_AttributeSetInstance_ID, movementType);
					trx.setMovementQty(movementQty);
					trx.setMovementType(movementType);
					trx.setM_Locator_ID(M_Locator_ID);
				}
				if (null == trx)
				{
					trx = new MTransaction(getCtx(), AD_Org_ID, 
							movementType, M_Locator_ID, M_Product_id, 
							M_AttributeSetInstance_ID, movementQty, 
							movementDate, get_TrxName());
					trx.setM_InOutLine_ID(M_InOutLine_ID);
				}
				trx.saveEx();
			}
		}
		catch (SQLException ex)
		{
			throw new AdempiereException(ex.getMessage());
		}
		catch (Exception ex)
		{
			throw new AdempiereException(ex.getMessage());
		}
		finally
		{
			DB.close(rs, st);
		}
	}
	
	/**
	 * Reinitialize movement transaction
	 */
	public void doInitializeMovement ()
	{
		m_ui.statusUpdate("Processing M_MovementLine...");
		String sqlFrom = " M_MovementLineMA ma INNER JOIN M_MovementLine ml "
				+ " ON ml.M_MovementLine_ID = ma.M_MovementLine_ID INNER JOIN "
				+ " M_Movement m ON m.M_Movement_ID = ml.M_Movement_ID ";
		String sqlWc = " m.DocStatus IN (?, ?) AND m.IsActive = ? "
				+ " AND ml.IsActive = ? AND ma.IsActive = ?";
		
		if (m_dateFrom != null && m_dateTo != null)
		{
			sqlWc += " AND m.MovementDate BETWEEN '" + m_dateFrom + "' AND '"
					+ m_dateTo + "' ";
		}
		else if (m_dateFrom != null)
		{
			sqlWc += " AND m.MovementDate >= '" + m_dateFrom + "' ";
		}
		else if (m_dateTo != null)
		{
			sqlWc += " AND m.MovementDate <= '" + m_dateTo + "' "; 
		}
		
		String sql = "SELECT COUNT (*) FROM " + sqlFrom + " WHERE " + sqlWc;
		int total = DB.getSQLValue(get_TrxName(), sql, "CO", "CL", 
				"Y", "Y", "Y");
		log.info(total + " record will be processed...");
		sql = "SELECT ml.M_MovementLine_ID, ml.AD_Org_ID, ml.M_Locator_ID, "
				+ " ml.M_LocatorTo_ID, ml.M_Product_ID, m.MovementDate, "
				+ " ma.MovementQty, ma.M_AttributeSetInstance_ID " 
				+ sqlFrom + " WHERE " + sqlWc;
		
		int count = 1;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setString(1, "CO");
			st.setString(2, "CL");
			st.setString(3, "Y");
			st.setString(4, "Y");
			st.setString(5, "Y");
			rs = st.executeQuery();
			while (rs.next())
			{
				m_ui.statusUpdate("Processing M_InOutLineMA [" + count 
							+ " to " + total);
				int M_MovementLine_ID = rs.getInt(1);
				int AD_Org_ID = rs.getInt(2);
				int M_Locator_ID = rs.getInt(3);
				int M_LocatorTo_ID = rs.getInt(4);
				int M_Product_id = rs.getInt(5);
				Timestamp movementDate = rs.getTimestamp(6);
				BigDecimal movementQty = rs.getBigDecimal(7);
				int M_AttributeSetInstance_ID = rs.getInt(8);
				
				if (null == movementQty || movementQty.signum() == 0)
				{
					continue;
				}
				
				MTransaction trxFrom = null;
				MTransaction trxTo = null;
				if (!m_reset)
				{
					log.info("Update M_Transaction {M_MovementLine_ID[" 
							+ M_MovementLine_ID + "], M_AttributeSetInstance_ID[" 
							+ M_AttributeSetInstance_ID + "]}");
					
					trxFrom = getTransaction("M_MovementLine_ID", 
							M_MovementLine_ID, M_AttributeSetInstance_ID,
							"M-");
					trxFrom.setMovementQty(movementQty.negate());
					trxFrom.setMovementType("M-");
					trxFrom.setM_Locator_ID(M_Locator_ID);
					
					trxTo = getTransaction("M_MovementLine_ID", 
							M_MovementLine_ID, M_AttributeSetInstance_ID,
							"M+");
					trxTo.setMovementQty(movementQty);
					trxTo.setMovementType("M+");
					trxTo.setM_Locator_ID(M_LocatorTo_ID);
				}
				if (null == trxFrom)
				{
					trxFrom = new MTransaction(getCtx(), AD_Org_ID, 
							"M-", M_Locator_ID, M_Product_id, 
							M_AttributeSetInstance_ID, movementQty, 
							movementDate, get_TrxName());
					trxFrom.setM_MovementLine_ID(M_MovementLine_ID);
					
					trxTo = new MTransaction(getCtx(), AD_Org_ID, 
							"M+", M_LocatorTo_ID, M_Product_id, 
							M_AttributeSetInstance_ID, movementQty, 
							movementDate, get_TrxName());
					trxTo.setM_MovementLine_ID(M_MovementLine_ID);
				}
				
				trxFrom.saveEx();
				trxTo.saveEx();
			}
		}
		catch (SQLException ex)
		{
			throw new AdempiereException(ex.getMessage());
		}
		catch (Exception ex)
		{
			throw new AdempiereException(ex.getMessage());
		}
		finally
		{
			DB.close(rs, st);
		}
	}
	
	/**
	 * 
	 * @param columnRef
	 * @param key
	 * @param attributeSetInsance_ID
	 * @param transactionType
	 * @return
	 */
	private MTransaction getTransaction (String columnRef, int key, 
			int attributeSetInsance_ID, String transactionType)
	{
		String sql = "SELECT * FROM M_Transaction WHERE " + columnRef
				+ " = ? AND M_AttributeSetInstance_ID = ? AND "
				+ " TransactionType = ? ";
		
		MTransaction trx = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setInt(1, key);
			st.setInt(2, attributeSetInsance_ID);
			st.setString(3, transactionType);
			rs = st.executeQuery();
			if (rs.next())
			{
				trx = new MTransaction(getCtx(), rs, get_TrxName());
			}
		}
		catch (SQLException ex)
		{
			throw new AdempiereException(ex.getMessage());
		}
		finally
		{
			DB.close(rs, st);
		}
		return trx;
	}
}