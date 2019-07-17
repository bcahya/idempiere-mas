/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.compiere.model.MCharge;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

import com.uns.model.I_C_DocType;
import com.uns.model.MUNSMessOccupants;
import com.uns.model.MUNSMessPartition;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSUtilitiesUses;
import com.uns.model.X_UNS_Utilities_Uses;

/**
 * @author menjangan
 *
 */
public class ChargeToUtilitiesUses extends SvrProcess {

	/**
	 * 
	 */
	public ChargeToUtilitiesUses() {
	}
	
	private int m_Product_ID = 0;
	private int m_Period_ID = 0;
	private String m_PayableTo = null;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		for(ProcessInfoParameter param : getParameter())
		{
			String paramName = param.getParameterName();
			if (paramName.equalsIgnoreCase("M_Product_ID"))
			{
				m_Product_ID = param.getParameterAsInt();
			}
			else if (paramName.equalsIgnoreCase("C_Period_ID"))
			{
				m_Period_ID = param.getParameterAsInt();
			}
			else if (paramName.equalsIgnoreCase("PayableTo"))
			{
				m_PayableTo = (String)param.getParameter();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		StringBuilder buildMsg = new StringBuilder("Utilities Uses Created : \n ");
		MPeriod period = MPeriod.get(getCtx(), m_Period_ID);
		
		MUNSPayrollConfiguration payConfig = 
				MUNSPayrollConfiguration.get(getCtx(), period, get_TrxName());
		
		for (MInventoryLine inventoryLine : loadInventoryLines(period))
		{
			MUNSMessPartition mess = MUNSMessPartition.get(
					getCtx(), inventoryLine.getM_Locator_ID(), get_TrxName());
			
			if (null == mess)
				continue;
			
			if (!mess.getPayableTo().equals(m_PayableTo))
				continue;
			
			BigDecimal cost = BigDecimal.ZERO;
			if (inventoryLine.getC_Charge_ID() > 0)
			{
				MCharge charge =(MCharge)inventoryLine.getC_Charge();
				if (null != charge)
					cost = charge.getChargeAmt();
			}
			
		
			if (m_PayableTo.equals(MUNSUtilitiesUses.PAYABLETO_Employee))
			{
				List<MUNSMessOccupants> messOccupants = mess.getOccupantsToChargeElectricityAndWaterUses();
				if (null == messOccupants || messOccupants.size() <=0)
					throw new AdempiereUserError("" +
							"Mess " + mess.getUNS_Mess_BuildingBlock().getName() + " Block Room " 
							+ mess.getBlok_Room_No() + " does not have occupants");
				
				int occupantsToCharge = messOccupants.size();
				
				for(MUNSMessOccupants messOccupant :messOccupants)
				{
					X_UNS_Utilities_Uses utilitiesUse = new X_UNS_Utilities_Uses(getCtx(), 0, get_TrxName());
					utilitiesUse.setAD_Org_ID(mess.getAD_Org_ID());
					utilitiesUse.setC_UOM_ID(inventoryLine.getM_Product().getC_UOM_ID());
					utilitiesUse.setUNS_Mess_Partition_ID(mess.get_ID());
					utilitiesUse.setC_Period_ID(m_Period_ID);
					utilitiesUse.setDescription("AUTO GENERATE");
					utilitiesUse.setM_Locator_ID(inventoryLine.getM_Locator_ID());
					utilitiesUse.setM_Product_ID(inventoryLine.getM_Product_ID());
					utilitiesUse.setPayableTo(m_PayableTo);
					
					BigDecimal qtyPerEmployee = inventoryLine.getQtyInternalUse().divide(
							new BigDecimal(occupantsToCharge), 4, RoundingMode.HALF_UP);
					utilitiesUse.setQty(qtyPerEmployee);


					if (null == messOccupant)
						throw new AdempiereUserError("" +
								"Mess " + mess.getUNS_Mess_BuildingBlock().getName() + " Block Room " 
								+ mess.getBlok_Room_No() + " haven't occupants");
					if (messOccupant.getUNS_Employee_ID() <= 0)
						throw new AdempiereUserError("" +
								"Mess " + mess.getUNS_Mess_BuildingBlock().getName() + " Block Room " 
								+ mess.getBlok_Room_No() + " is payable to employee but not employee has devine");
					
					utilitiesUse.setUNS_Employee_ID(messOccupant.getUNS_Employee_ID());
					if (utilitiesUse.getM_Product().getValue().equals("ELC"))
						cost = utilitiesUse.getQty().multiply(payConfig.getBiayaListrik());
					else if(utilitiesUse.getM_Product().getValue().equals("WTR"))
						cost = utilitiesUse.getQty().multiply(payConfig.getBiayaAir());
										
					utilitiesUse.setCost(cost);
					utilitiesUse.setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_UtilitiesUses));
					if(!utilitiesUse.save())
						throw new AdempiereUserError("can't create Utilities Uses");
					
					buildMsg
					.append(" ").append(utilitiesUse.getDocumentNo())
					.append("_").append(inventoryLine.getM_Product().getName())
					.append("_").append(mess.getBlok_Room_No())
					.append("_").append(messOccupant.getUNS_Employee().getName())
					.append(" ").append(m_PayableTo);
				}
			}
			else
			{
				X_UNS_Utilities_Uses utilitiesUse = new X_UNS_Utilities_Uses(getCtx(), 0, get_TrxName());
				utilitiesUse.setAD_Org_ID(mess.getAD_Org_ID());
				utilitiesUse.setC_UOM_ID(inventoryLine.getM_Product().getC_UOM_ID());
				utilitiesUse.setUNS_Mess_Partition_ID(mess.get_ID());
				utilitiesUse.setDescription("AUTO GENERATE");
				utilitiesUse.setM_Locator_ID(inventoryLine.getM_Locator_ID());
				utilitiesUse.setM_Product_ID(inventoryLine.getM_Product_ID());
				utilitiesUse.setPayableTo(m_PayableTo);
				utilitiesUse.setC_Period_ID(m_Period_ID);
				utilitiesUse.setQty(inventoryLine.getQtyInternalUse());
				
				utilitiesUse.setCost(cost);
				utilitiesUse.setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_UtilitiesUses));
				utilitiesUse.save();
				
				buildMsg
				.append(" ").append(utilitiesUse.getDocumentNo())
				.append("_").append(inventoryLine.getM_Product().getName())
				.append("_").append(mess.getBlok_Room_No())
				.append(" ").append(m_PayableTo);
			}
		}
		
		return buildMsg.toString();
	}
	
	private List<MInventoryLine> loadInventoryLines(MPeriod period)
	{
		Timestamp dateStart = period.getStartDate();
		Timestamp dateEnd = period.getEndDate();
		List<MInventoryLine> list = new ArrayList<MInventoryLine>();
		String sql = "SELECT il.*, '' As UPC, '' As Value FROM " + MInventoryLine.Table_Name + " il " 
				+ " INNER JOIN " + MInventory.Table_Name + " i "
				+ " ON i." + MInventory.COLUMNNAME_M_Inventory_ID
				+ " = il." + MInventoryLine.COLUMNNAME_M_Inventory_ID
				+ " AND i." + MInventory.COLUMNNAME_MovementDate
				+ " BETWEEN '" + dateStart + "' AND '" + dateEnd + "' AND il."
				+ MInventoryLine.COLUMNNAME_M_Product_ID + " = " + m_Product_ID
				+ " AND i." + MInventory.COLUMNNAME_DocStatus + " = 'CO'";
		
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = DB.prepareStatement(sql, get_TrxName());
			rs = stm.executeQuery();
			while (rs.next()) {
				MInventoryLine inventoryLine = new MInventoryLine(getCtx(), rs, get_TrxName());
				list.add(inventoryLine);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			DB.close(rs, stm);
		}
		return list;
	}

}
