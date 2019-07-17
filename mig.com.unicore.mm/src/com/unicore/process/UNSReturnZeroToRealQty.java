package com.unicore.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.model.PO;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.base.model.MMovement;
import com.unicore.base.model.MMovementLine;

public class UNSReturnZeroToRealQty extends SvrProcess {
	
	final String previx = "Auto set to zero prev qty = ";

	public UNSReturnZeroToRealQty() {
		super();
	}

	@Override
	protected void prepare() {

	}

	@Override
	protected String doIt() throws Exception {
		IProcessUI info = Env.getProcessUI(getCtx());
		List<MMovementLine> mLines = getRecords();
		Hashtable<String, MMovement> mapMove = new Hashtable<>();
		int total = mLines.size();
		int count = 0;
		for (MMovementLine line : mLines)
		{
			info.statusUpdate("Processing " + ++ count + " of " + total);
			MMovement move = new MMovement(getCtx(), line.getM_Movement_ID(), get_TrxName());
			String key = move.getDescription() + move.getMovementDate() + move.getDestinationWarehouse_ID();
			MMovementLine newLine = new MMovementLine(getCtx(), 0, get_TrxName());
			PO.copyValues(line, newLine);
			newLine.setAD_Org_ID(line.getAD_Org_ID());
			newLine.setProcessed(false);
			analyzeQty(newLine, line.getDescription());
			MMovement newMove = mapMove.get(key);
			if(null == newMove)
			{
				newMove = new MMovement(getCtx(), 0, get_TrxName());
				PO.copyValues(move, newMove);
				newMove.setProcessed(false);
				newMove.setDocAction("CO");
				newMove.setDocStatus("DR");
				newMove.setAD_Org_ID(move.getAD_Org_ID());
				newMove.setDescription("Auto Crated By Menjangan. Copying completed zero movement to this movement prev move [" + move.getDocumentNo() + " - " + move.getDescription() + "]");
				newMove.saveEx();
				mapMove.put(key, newMove);
			}
			newLine.setM_Movement_ID(newMove.get_ID());
			newLine.saveEx();
		}
		return null;
	}

	private List<MMovementLine> getRecords()
	{
		List<MMovementLine> list = new ArrayList<>();
		String sql = "SELECT * FROM M_MovementLine WHERE Description Like "
				+ "'Auto set to zero prev qty =%' AND EXISTS (SELECT * FROM M_Movement "
				+ " WHERE Docstatus = 'CO' AND M_Movement_ID = M_MovementLine.M_Movement_ID)"
				+ " ORDER BY M_Movement_ID";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			rs = st.executeQuery();
			while(rs.next())
			{
				list.add(new MMovementLine(getCtx(), rs, get_TrxName()));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs,st);
		}
		return list;
	}
	
	void analyzeQty(MMovementLine line, String Desc)
	{
		if(Util.isEmpty(Desc, true))
			return;
		
		Desc = Desc.replace(previx, "");
		try
		{
			BigDecimal qty = new BigDecimal(Desc);
			line.setMovementQty(qty);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
	}
}
