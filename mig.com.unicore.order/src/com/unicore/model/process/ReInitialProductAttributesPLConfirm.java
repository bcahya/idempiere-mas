package com.unicore.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.base.model.MInOut;
import com.unicore.model.MUNSPLConfirmMA;
import com.unicore.model.MUNSPLConfirmProduct;

public class ReInitialProductAttributesPLConfirm extends SvrProcess {

	private IProcessUI m_ui = null;
	public ReInitialProductAttributesPLConfirm() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare()
	{
		
	}

	@Override
	protected String doIt() throws Exception
	{
		m_ui = Env.getProcessUI(getCtx());
		int count = 0;
		String sql = "SELECT io.M_InOut_ID, iol.M_InOutLine_ID, ioma.M_AttributeSetInstance_ID, ioma.MovementQty,"
				+ " ioma.DateMaterialPolicy, cp.UNS_PL_ConfirmProduct_ID FROM M_InOutLineMA ioma"
				+ " INNER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = ioma.M_InOutLine_ID"
				+ " INNER JOIN UNS_PackingList_Line pll ON pll.M_InOutLine_ID = iol.M_InOutLine_ID"
				+ " INNER JOIN UNS_PL_ConfirmLine cl ON cl.UNS_PackingList_Line_ID = pll.UNS_PackingList_Line_ID"
				+ " INNER JOIN UNS_PL_ConfirmProduct cp ON cp.UNS_PL_ConfirmProduct_ID = cl.UNS_PL_ConfirmProduct_ID"
				+ " INNER JOIN UNS_PL_Confirm plc ON plc.UNS_PL_Confirm_ID = cp.UNS_PL_Confirm_ID"
				+ " INNER JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID"
				+ " WHERE NOT EXISTS (SELECT maa.UNS_PL_ConfirmProduct_ID FROM UNS_PL_ConfirmMA maa"
				+ " WHERE maa.UNS_PL_ConfirmProduct_ID = cp.UNS_PL_ConfirmProduct_ID)"
				+ " ORDER BY iol.M_InOutLine_ID DESC";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			rs = stmt.executeQuery();
			String key = null;
			
			Hashtable<String, MUNSPLConfirmMA> mapASI = new Hashtable<>();
			while (rs.next())
			{
				MInOut io = new MInOut(getCtx(), rs.getInt(1), get_TrxName());
				if(io.getDocStatus().equals("VO") || io.getDocStatus().equals("RE"))
				{
					count ++;
					continue;
				}
				key = String.valueOf(rs.getInt(6)) + " - " + String.valueOf(rs.getInt(3)) + " - " + String.valueOf(rs.getTime(5));
				
				MUNSPLConfirmMA ma = mapASI.get(key);
				if(ma == null)
				{
					MUNSPLConfirmProduct cp = new MUNSPLConfirmProduct(getCtx(), rs.getInt(6), get_TrxName());
					ma = new MUNSPLConfirmMA(cp);
					ma.setM_AttributeSetInstance_ID(rs.getInt(3));
					ma.setDateMaterialPolicy(rs.getTimestamp(5));
					ma.setMovementQty(rs.getBigDecimal(4));
					mapASI.put(key, ma);
				}
				else
				{
					ma.setMovementQty(ma.getMovementQty().add(rs.getBigDecimal(4)));
				}
				ma.saveEx();
				count ++;
				m_ui.statusUpdate("Data ke " + count);
			}
		} 
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
		return null;
	}

}
