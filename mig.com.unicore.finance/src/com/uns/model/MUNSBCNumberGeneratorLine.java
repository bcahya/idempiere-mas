package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.compiere.model.MOrg;
import org.compiere.model.PO;
import org.compiere.util.DB;

public class MUNSBCNumberGeneratorLine extends X_UNS_BCNumber_GeneratorLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7226428532940853953L;

	public MUNSBCNumberGeneratorLine(Properties ctx,
			int UNS_BCNumber_GeneratorLine_ID, String trxName) {
		super(ctx, UNS_BCNumber_GeneratorLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSBCNumberGeneratorLine(Properties ctx,
			ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public MUNSBCNumberGenerator getParent(){
		return new MUNSBCNumberGenerator(getCtx(), getUNS_BCNumber_Generator_ID(), get_TrxName());
	}
	
	private void setFromHeader(MUNSBCNumberGenerator parent){
		setAD_Org_ID(parent.getAD_Org_ID());
		setUNS_BCNumber_Generator_ID(parent.get_ID());
	}
	
	private String getBCCode(){
		String code = new X_UNS_BCCode(getCtx(), getParent().getUNS_BCCode_ID(), get_TrxName()).getValue();
		String dept = MOrg.get(getCtx(), getAD_OrgTrx_ID()).getValue();
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(getDateDoc()));
		String sMonth = GeneralCustomization.getRomanNumber(month);
		String sYear = new SimpleDateFormat("yyyy").format(getDateDoc());
		String retVal =  code +"."+ getBCNo()+"/"+dept+"/"+sMonth+"/"+sYear;
		return retVal;
	}
	
	public static String getBCCodeNumber(Properties ctx, int bcCodeID,	int yearID , Timestamp date,
			boolean isDaily, int orgTrxID, PO po, String trxName){
		int bcCodeNumberID = 0;
		String newBCNumber = null;
		String sDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		MUNSBCNumberGenerator bcGenerator = MUNSBCNumberGenerator.newGet(ctx, bcCodeID, yearID, trxName);
		
		if (isDaily){
			String sql = "SELECT UNS_BCNumber_GeneratorLine_ID FROM UNS_BCNumber_GeneratorLine" +
					" WHERE DateDoc = '" + sDate + "' AND " + " AD_OrgTrx_ID = " + orgTrxID + " AND " +
					" UNS_BCNumber_Generator_ID = " + bcGenerator.get_ID(); 
			bcCodeNumberID = DB.getSQLValue(trxName, sql);
			if (bcCodeNumberID == -1)
				bcCodeNumberID = 0;
		} else {
			newBCNumber = MUNSBCNumberGenerator.add(ctx, bcGenerator.get_ID(), trxName);
		}
			
		MUNSBCNumberGeneratorLine line = new MUNSBCNumberGeneratorLine(ctx, bcCodeNumberID, trxName);
		if (bcCodeNumberID==0){
			line.setFromHeader(bcGenerator);
			line.setDateDoc(date);
			line.setAD_OrgTrx_ID(orgTrxID);
			//overwrite Last used number if daily and new day. 12/08/13
			if (isDaily)
				newBCNumber = MUNSBCNumberGenerator.add(ctx, bcGenerator.get_ID(), trxName);
			line.setBCNo(newBCNumber);
			line.setBCCodeNo(line.getBCCode());
			line.saveEx();
		}
		
		MUNSBCNumberTransaction.createTranscation(line, po);

		return line.getBCCodeNo();
	}

}
