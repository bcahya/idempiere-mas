/**
 * 
 */
package com.uns.model.process;

import java.sql.Timestamp;
import java.util.List;

import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.model.X_M_Warehouse;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.unicore.model.MUNSTrxWeeklyStock;

/**
 * @author Burhani Adam
 *
 */
public class CreateTrxWeekly extends SvrProcess {

	/**
	 * 
	 */
	public CreateTrxWeekly() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		int weekNo = 0;
		String sql = "SELECT C_Year_ID FROM C_Year WHERE FiscalYear = (SELECT CAST(EXTRACT(YEAR FROM NOW()) AS Character Varying))";
		int yearID = DB.getSQLValue(get_TrxName(), sql);
		
		String day = "SELECT EXTRACT(DOW FROM now())";
		int dayInt = DB.getSQLValue(get_TrxName(), day);
		if(dayInt != 0)
		{
			for(int i = 1; i <= 7; i++)
			{
				day = "SELECT EXTRACT(DOW FROM now() - ?)";
				dayInt = DB.getSQLValue(get_TrxName(), day, i);
				if(dayInt == 0)
				{
					String week = "SELECT EXTRACT(WEEK FROM now() - ?)";
					weekNo = DB.getSQLValue(get_TrxName(), week, i);
				}
			}
		}
		else if(dayInt == 0)
		{
			String week = "SELECT EXTRACT(WEEK FROM now())";
			weekNo = DB.getSQLValue(get_TrxName(), week);
		}
		
		List<MWarehouse> list = new Query(getCtx(), MWarehouse.Table_Name, null, get_TrxName()).
				setOrderBy(X_M_Warehouse.COLUMNNAME_Value).list();
		
		for(MWarehouse wh : list)
		{
			MUNSTrxWeeklyStock trx = MUNSTrxWeeklyStock.getMaxPeriod(getCtx(), wh.get_ID(), yearID, get_TrxName());
			
			if(trx != null)
			{
				int diff = weekNo - trx.getWeekNo();
				if(diff == 0)
					continue;
				for(int i = 1; i <= diff; i++)
				{
					weekNo = trx.getWeekNo() + i;
					
					Timestamp dateFrom = DB.getSQLValueTS(get_TrxName(), 
							"SELECT TO_DATE((SELECT CAST(EXTRACT(YEAR FROM NOW()) AS Character Varying) || ?), 'yyyyw') + 1"
							, String.valueOf(weekNo));
					
					Timestamp dateTo = DB.getSQLValueTS(get_TrxName(), "SELECT ?::timestamp + 6",
							dateFrom);
					
					if(dateTo.compareTo(new Timestamp (System.currentTimeMillis ())) == 1)
						continue;
					
					MUNSTrxWeeklyStock tws = new MUNSTrxWeeklyStock(getCtx(), 0, get_TrxName());
					tws.setAD_Org_ID(wh.getAD_Org_ID());
					tws.setM_Warehouse_ID(wh.get_ID());
					tws.setC_Year_ID(yearID);
					tws.setWeekNo(trx.getWeekNo() + i);
					tws.setDateFrom(dateFrom);
					tws.setDateTo(dateTo);
					tws.saveEx(get_TrxName());
				}
			}
			else
			{
				String week = "SELECT EXTRACT(WEEK FROM now())";
				weekNo = DB.getSQLValue(get_TrxName(), week);
				
				Timestamp dateFrom = DB.getSQLValueTS(get_TrxName(), "SELECT now() - CAST((CASE WHEN extract(dow from now()) <> 0"
						+ " THEN extract(dow from now()) ELSE 7 END) AS int) + 1");
				Timestamp dateTo = DB.getSQLValueTS(get_TrxName(), "SELECT ?::timestamp + 6", dateFrom);
				
				//just use for first running this process. if you want setting static date and week. if no, you can use week now.
//				dateFrom = Timestamp.valueOf("2017-01-02 00:00:00");
//				dateTo = Timestamp.valueOf("2017-01-08 00:00:00");
//				weekNo = 1;
				
				if(dateTo.compareTo(new Timestamp (System.currentTimeMillis ())) == 1)
					continue;
				
				MUNSTrxWeeklyStock tws = new MUNSTrxWeeklyStock(getCtx(), 0, get_TrxName());
				tws.setAD_Org_ID(wh.getAD_Org_ID());
				tws.setM_Warehouse_ID(wh.get_ID());
				tws.setC_Year_ID(yearID);
				tws.setWeekNo(weekNo);
				tws.setDateFrom(dateFrom);
				tws.setDateTo(dateTo);
				tws.saveEx();
			}
		}
		
		return "Success";
	}

}
