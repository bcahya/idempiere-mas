/**
 * Mengubah QA Status Pada M_StorageOnHand dari Incubation ke Pending Inspection/Labtest
 * Perubahan Status dilakukan jika QA status Storage = Incubation dan incubation remain days = 0
 * juga merubah QA status pada palet yang digunakan di storage ini
 * 
 */
package com.uns.model.process;

import org.compiere.process.SvrProcess;

/**
 * @author MENJANGAN
 *
 */
public class UpdateQAStatusFromICToPL extends SvrProcess {

	
	public UpdateQAStatusFromICToPL() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		// No action

	}
	

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		StringBuilder returnMsg = new StringBuilder();
		int countProductInspection = 0;
		int countStorage = 0;
		int countPallet = 0;
		//TODO QA STATUS
//		String QAStatus = MStorageOnHand.QASTATUS_Incubation;
//		String sql = "SELECT * FROM M_Storage s " +
//						"WHERE s.QAStatus = ? AND s.IncubationDaysRemain <= 0";
//		PreparedStatement stm = null;
//		ResultSet rs = null;
//		try
//		{
//			stm = DB.prepareStatement(sql, get_TrxName());
//			stm.setString(1, QAStatus);
//			rs = stm.executeQuery();
//			while (rs.next())
//			{
//				int locator_ID = rs.getInt("M_Locator_ID");
//				int product_ID = rs.getInt("M_Product_ID");
//				int attributeSetInstance_ID = rs.getInt("M_AttributeSetInstance_ID");
//				String QAStatusTo = MStorageOnHand.QASTATUS_PendingInspectionLabTest;
//				//update product release cetificate
//				String whereClausePrcLine = MUNSQAStatusPRCLine.COLUMNNAME_M_Product_ID 
//												+ " = " + product_ID + " AND " 
//											+ MUNSQAStatusPRCLine.COLUMNNAME_M_Locator_ID 
//												+ " = " + locator_ID + " AND "  
//											+ MUNSQAStatusPRCLine.COLUMNNAME_M_AttributeSetInstance_ID 
//												+ " = " + attributeSetInstance_ID + " AND " 
//											+ MUNSQAStatusPRCLine.COLUMNNAME_QAStatus
//												+ " = '" + QAStatus +"'";
//				
//				int[] prcLines = MUNSQAStatusPRCLine.getAllIDs(
//						MUNSQAStatusPRCLine.Table_Name, 
//						whereClausePrcLine, get_TrxName());
//				for(int prcID : prcLines)
//				{
//					MUNSQAStatusPRCLine prcLine = new MUNSQAStatusPRCLine(
//														getCtx(), prcID, get_TrxName());
//					prcLine.setQAStatus(QAStatusTo);
//					prcLine.save();
//					countProductInspection++;
//				}
//				
//				
//				/** Update Storage */
//				MStorageOnHand soh = MStorageOnHand.get(
//						getCtx(), 
//						locator_ID, 
//						product_ID, 
//						attributeSetInstance_ID, 
//						get_TrxName());
//				soh.setQAStatus(QAStatusTo);
//				soh.setQtyQAStatus(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
//				if(soh.save())
//					countStorage++;
				
				// update pallet
//				MProduct product = new MProduct(getCtx(), rs.getInt("M_Product_ID"), get_TrxName());
//				X_UNS_Pallet[] pallets = PalletHelper.getPallets(
//						getCtx(),
//						product, 
//						locator_ID, 
//						attributeSetInstance_ID, 
//						get_TrxName());
//				for (int i=0; i< pallets.length; i++)
//				{
//					pallets[i].setQAStatus(QAStatusTo);
//					pallets[i].setNCQty(BigDecimal.ZERO);
//					pallets[i].setReleaseQty(BigDecimal.ZERO);
//					pallets[i].setOnHoldQty(BigDecimal.ZERO);
//					pallets[i].setNCPackage(null);
//					pallets[i].setReleasedPackage(null);
//					pallets[i].setUnreleasedPackage(null);
//					pallets[i].save();
//					countPallet++;
//				}
//			}
//		} catch (Exception ex)
//		{
//			returnMsg.append(ex.getMessage());
//		} finally
//		{
//			DB.close(rs, stm);
//		}
		if(returnMsg.length() <=0)
		{
			returnMsg.append("Success fully update ");
			if(countStorage > 0)
				returnMsg.append(countStorage)
						.append(" Storage ");
			if(countPallet > 0)
				returnMsg.append(countPallet)
						.append(" Pallet ");
			if(countProductInspection > 0)
				returnMsg.append(countProductInspection)
						.append(" Product Inspection");
		}
		return returnMsg.toString();
	}

}
