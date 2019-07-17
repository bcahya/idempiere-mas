package com.uns.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import org.compiere.util.TimeUtil;

public class UNSTimeUtil
  extends TimeUtil
{
  public static Locale DEFAULT_LOCALE = Locale.UK;
  
  public static Timestamp getProductionWeekStartDate(int year, int weekNo)
  {
    Calendar cal = Calendar.getInstance(DEFAULT_LOCALE);
    Timestamp theDate = null;
    if (weekNo == 1)
    {
      cal.set(cal.get(1), 0, 1);
    }
    else
    {
      if (weekNo == 53)
      {
        cal.set(cal.get(1), 11, 31);
        int day = cal.get(7);
        int addBy = day > 1 ? (day - 2) * -1 : -6;
        theDate = addDays(new Timestamp(cal.getTimeInMillis()), addBy);
        return theDate;
      }
      cal.set(cal.get(1), 0, 1);
      int weekNoTmp = cal.get(3);
      if (weekNoTmp == 53) {
        weekNo--;
      }
      cal.set(3, weekNo);
      int day = cal.get(7);
      int addBy = day > 1 ? (day - 2) * -1 : -6;
      theDate = addDays(new Timestamp(cal.getTimeInMillis()), addBy);
      return theDate;
    }
    theDate = new Timestamp(cal.getTimeInMillis());
    return theDate;
  }
  
  public static Timestamp getProductionWeekEndDate(int year, int weekNo)
  {
    Calendar cal = Calendar.getInstance(DEFAULT_LOCALE);
    Timestamp theDate = null;
    if (weekNo == 1)
    {
      cal.set(cal.get(1), 0, 1);
      int day = cal.get(7);
      int addBy = day > 1 ? 8 - day : 0;
      theDate = addDays(new Timestamp(cal.getTimeInMillis()), addBy);
      return theDate;
    }
    if (weekNo == 53)
    {
      cal.set(cal.get(1), 11, 31);
    }
    else
    {
      cal.set(cal.get(1), 0, 1);
      int weekNoTmp = cal.get(3);
      if (weekNoTmp == 53) {
        weekNo--;
      }
      cal.set(3, weekNo);
      int day = cal.get(7);
      int addBy = day > 1 ? 8 - day : 0;
      theDate = addDays(new Timestamp(cal.getTimeInMillis()), addBy);
      return theDate;
    }
    theDate = new Timestamp(cal.getTimeInMillis());
    return theDate;
  }
  
  public static int getProductionWeekNo(Timestamp ts)
  {
    Calendar cal = Calendar.getInstance(DEFAULT_LOCALE);
    cal.setTimeInMillis(ts.getTime());
    
    int weekNo = cal.get(3);
    int month = cal.get(2);
    if ((weekNo == 1) && (month == 11))
    {
      weekNo = 53;
    }
    else if ((weekNo == 53) && (month == 0))
    {
      weekNo = 1;
    }
    else
    {
      cal.set(2, 0);
      cal.set(5, 1);
      ts = new Timestamp(cal.getTimeInMillis());
      int weekNoTmp = cal.get(3);
      if (weekNoTmp == 53)
      {
        if (weekNo == 53) {
          weekNo = 1;
        } else {
          weekNo++;
        }
      }
      else if (weekNo == 54) {
        weekNo--;
      }
    }
    return weekNo;
  }
  
  public static Timestamp getNextCutOffWeekDay(Timestamp date, int cutOffWeekDay)
  {
    Timestamp theNextCutOffWeekDay = date;
    
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    
    int currDay = cal.get(7);
    if (currDay == cutOffWeekDay) {
      return theNextCutOffWeekDay;
    }
    int currToNextCutOffWeekDay = 0;
    if (currDay < cutOffWeekDay) {
      currToNextCutOffWeekDay = cutOffWeekDay - currDay;
    } else {
      currToNextCutOffWeekDay = 7 - (currDay - cutOffWeekDay);
    }
    cal.add(5, currToNextCutOffWeekDay);
    
    theNextCutOffWeekDay = new Timestamp(cal.getTimeInMillis());
    
    return theNextCutOffWeekDay;
  }
}
