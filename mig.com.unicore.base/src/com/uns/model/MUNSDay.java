package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;

public class MUNSDay extends X_UNS_Day implements DocAction, DocOptions {

	/**
	 *@author (Ahong) 
	 */
	private static final long serialVersionUID = 1L;

	public MUNSDay(Properties ctx, int UNS_Day_ID, String trxName) {
		super(ctx, UNS_Day_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSDay(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDocStatus(String newStatus) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDocStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocAction() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static int getDayDifferent(Timestamp start, Timestamp end) {
		int value = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		int startMonth = calendar.get(Calendar.MONTH);
		int startDay = calendar.get(Calendar.DAY_OF_MONTH);
		int lastMonthDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int startYear = calendar.get(Calendar.YEAR);
	
		Calendar phaseEnd = Calendar.getInstance();
		phaseEnd.setTime(end);
		int finishMonth = phaseEnd.get(Calendar.MONTH);
		int finishYear = phaseEnd.get(Calendar.YEAR);
		int finishDay = phaseEnd.get(Calendar.DAY_OF_MONTH);
		int yearDif = (finishYear - startYear) * 12;
		int realFinishDay = phaseEnd.get(Calendar.DAY_OF_MONTH);
		int monthDif = finishMonth - startMonth;
		monthDif += yearDif;
		
		// Condition where the phase are promised to be finished in next month or more
		if(monthDif > 0) {
			for(int k = 0; k < monthDif+1; ++k) {
				int totalDay = 0;
				if(k == 0) {
					if(monthDif > 0) 
						totalDay = lastMonthDay - startDay + 1;
					else 
						totalDay = finishDay - startDay + 1;
				}
				else if(k == monthDif) 
					totalDay = finishDay;
				else {
					int currentYear = startYear;
					int currentMonth = startMonth + k;
					if(currentMonth > 12) {
						currentYear += (currentMonth/12);
						currentMonth %= 12;
					}
					Calendar current = new GregorianCalendar(currentYear, currentMonth, 1);
					int currentLastMonthDay = current.getActualMaximum(Calendar.DAY_OF_MONTH);
					
					totalDay = currentLastMonthDay;
				}
				value += totalDay;
			}
		}
		else {
			value = realFinishDay - startDay +1;
		}
		return value;
	}
}
