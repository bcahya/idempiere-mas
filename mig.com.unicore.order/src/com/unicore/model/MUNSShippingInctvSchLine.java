package com.unicore.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

public class MUNSShippingInctvSchLine extends X_UNS_ShippingInctvSch_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MUNSShippingInctvSchLine(Properties ctx,
			int UNS_ShippingInctvSch_Line_ID, String trxName) {
		super(ctx, UNS_ShippingInctvSch_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSShippingInctvSchLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSShippingInctvSchLine[] m_Lines = null;
	
	@Override
	protected boolean beforeSave(boolean newRecord) {

		//looping line yang udah terbuat.
		for(MUNSShippingInctvSchLine line : getLines(true)){
			
			if(get_ID() == line.get_ID())
				continue;

			
			if (getIncentiveType().equals("DD") && line.getIncentiveType().equals("DD")
					&& line.getReason().equals(getReason())) {
				throw new AdempiereException("Duplicated Reason");
			}

			if (line.getIncentiveType().equals("DL") && getIncentiveType().equals("DL")) {
				throw new AdempiereException("Duplicated Minimum Ritiase");
			}

			
			if (line.getIncentiveType().equals("RT")
					&& getIncentiveType().equals("RT")) {
				throw new AdempiereException("Duplicated Incentive Type Ritase");
			}

			if (line.getIncentiveType().equals("OT")
					&& getIncentiveType().equals("OT")) {
				throw new AdempiereException("Duplicated Incentive Type Outlet");
			}
			
			
		}
		
		return super.beforeSave(newRecord);

	}
	
	public MUNSShippingInctvSchLine[] getLines(boolean requery) {

		String whereClause = MUNSShippingInctvSchLine.COLUMNNAME_UNS_ShippingIncentiveSch_ID
				.concat("=?");

		List<MUNSShippingInctvSchLine> list = Query
				.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID,
						MUNSShippingInctvSchLine.Table_Name, whereClause,
						get_TrxName()).setParameters(getUNS_ShippingIncentiveSch_ID()).list();

		m_Lines = new MUNSShippingInctvSchLine[(list.size())];
		list.toArray(m_Lines);
		return m_Lines;

	}
}
