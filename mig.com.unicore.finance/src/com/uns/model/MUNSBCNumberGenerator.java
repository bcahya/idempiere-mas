package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.DB;

public class MUNSBCNumberGenerator extends X_UNS_BCNumber_Generator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8323142580129465691L;

	public MUNSBCNumberGenerator(Properties ctx, int UNS_BCNumber_Generator_ID,
			String trxName) {
		super(ctx, UNS_BCNumber_Generator_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSBCNumberGenerator(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MUNSBCNumberGenerator newGet(Properties ctx, int bcCodeID, int yearID, String trxName){
		String query = "SELECT "+MUNSBCNumberGenerator.COLUMNNAME_UNS_BCNumber_Generator_ID
				+ " FROM " + MUNSBCNumberGenerator.Table_Name+" WHERE UNS_BCCode_ID = "
				+ bcCodeID +" AND " + " C_Year_ID = " + yearID;
		int bcNumberID = DB.getSQLValue(trxName, query);
		
		if (bcNumberID==-1)	
			bcNumberID = 0;
		
		MUNSBCNumberGenerator bcGenerator = new MUNSBCNumberGenerator(ctx, bcNumberID, trxName);
		if(bcNumberID==0){
			bcGenerator.setUNS_BCCode_ID(bcCodeID);
			bcGenerator.setC_Year_ID(yearID);
			bcGenerator.setAD_Org_ID(0);
			bcGenerator.setLatestUsedNumber("000000");
		}
		
		bcGenerator.saveEx(trxName);
		
		return bcGenerator;
	}

	public static String add(Properties ctx, int bcNumberGeneratorID, String trxName) {
		MUNSBCNumberGenerator bcGenerator = new MUNSBCNumberGenerator(ctx, bcNumberGeneratorID, trxName);
		
		String newNumber = "";
		int lastNumber = Integer.parseInt(bcGenerator.getLatestUsedNumber())+1;
		String sLastNumber = String.valueOf(lastNumber);
		for(int i=1; i<=6-sLastNumber.length();i++){
			newNumber = newNumber+"0";
		}
		newNumber = newNumber + sLastNumber;
		bcGenerator.setLatestUsedNumber(newNumber);
		bcGenerator.saveEx(trxName);
		
		return bcGenerator.getLatestUsedNumber();
	}
}
