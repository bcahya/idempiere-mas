package testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import org.adempiere.util.Callback;
import org.compiere.util.Env;

import com.uns.util.UNSTimeUtil;

public class TestingPurposes {

	public TestingPurposes() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException
	{
		//test1();
		//test2();
		//testingDate();
		//testBasicMath();
		//testingBasicString();
		//testingFileAccess();
		//testChoices();
		//testingRegex();
		testingMultiThread();
	}
	
	static void testingMultiThread()
	{
		final TmpClass tmpClass = new TmpClass();
		final TmpClass tmpClass2 = new TmpClass();
		
		final Callback<TmpClass> callback = new Callback<TmpClass>() {

			@Override
			public void onCallback(TmpClass tmpClass) {
				synchronized (tmpClass) {
					tmpClass.value += 3;
					//tmpClass.notify();
				}
				synchronized (tmpClass2) {
					tmpClass2.notify();
				}
			}			
		};
		
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				testingRunnableStep1(callback, tmpClass, tmpClass2);
			}
		};
		
		Thread t1 = new Thread(r1);
		t1.start();
		
		tmpClass.value += 3;
		
		System.out.println("TmpClass value : " + tmpClass.value);
		
		synchronized (tmpClass2) {
			try {
				tmpClass2.wait();
			}
			catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}
		
		synchronized (tmpClass) {
			tmpClass.notify();
		}
		
		System.out.println("TmpClass value : " + tmpClass.value);
	}
	
	private static class TmpClass{
		int value = 0;
	};
	
	static void testingRunnableStep1(Callback<TmpClass> callback, TmpClass tmpClass, TmpClass tmpClass2) {
		System.out.println("testing runable step-1.");
		testingInterruptRunnable1(callback, tmpClass, tmpClass2);
	}
	
	static void testingInterruptRunnable1(Callback<TmpClass> callback, TmpClass tmpClass, TmpClass tmpClass2) 
	{
		synchronized (tmpClass) {
			try {
				callback.onCallback(tmpClass);
				System.out.println("Runnable1 is waiting.");
				tmpClass.wait();
			}
			catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Runnable1 running again.");
	}
	
	static void testingRegex()
	{
		StringBuilder strBuilder = new StringBuilder("TD15010001GDO");
		String str = strBuilder.toString();
		
		String[] splitted = str.split("[a-zA-Z]+[0-9]+[a-zA-Z]+");
		String newStr = str.replaceAll("[a-zA-Z]+[0-9]+", "");
		System.out.println(str);
		System.out.println(newStr);
		
		System.out.println(splitted[0]);
		
		for (String s : splitted)
		{
			System.out.println(s);
		}
	}
	
	@SuppressWarnings("resource")
	static void testChoices() throws IOException
	{
		Scanner scanner;
		scanner = new Scanner(System.in);
		String[][] dataMhs = new String[50][3];
		int numberOfData = 0;
		
		while (true) 
		{
			System.out.println("1. Insert Data.");
			System.out.println("2. View Data.");
			System.out.println("3. Search Data");
			System.out.println("4. Exit.");
			System.out.println("Enter your choice: ");
			int answer = scanner.nextInt();
			//DataInputStream input = new DataInputStream(System.in);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			switch (answer)
			{
				case 1 :
					String reinput = "Y";
					while ((reinput.equals("Y") || reinput.equals("y")) && (numberOfData < 50))
					{
						System.out.print("Name: ");
						dataMhs[numberOfData][0] = reader.readLine();//scanner.nextLine();
						
						System.out.print("Address: ");
						dataMhs[numberOfData][1] = reader.readLine();//scanner.nextLine();
						
						System.out.print("Telp.: ");
						dataMhs[numberOfData][2] = reader.readLine();//scanner.nextLine();
						
						numberOfData++;

						System.out.println("More data input?(Y/N): ");
						reinput = scanner.next();
					}
					//try {
						//Runtime.getRuntime().exec("clear");
						System.out.flush();
//					}
//					catch (IOException io)
//					{
//						io.printStackTrace();
//					}
					break;
				case 2 :
					System.out.printf("%-6s", "No.");
					System.out.printf("%-32s", "Name");
					System.out.printf("%-64s", "Address");
					System.out.printf("%16s", "Telphone");
					System.out.println("\n====================================================================================================================");
					for (int i=0; i < dataMhs.length; i++)
					{
						if (dataMhs[i][0] == null && dataMhs[i][1] == null && dataMhs[i][2] == null)
							break;
						
						System.out.printf("%-6.6s", (i+1) + ".");
						
						System.out.printf("%-32.32s", dataMhs[i][0]);

						System.out.printf("%-64.64s", dataMhs[i][1]);

						System.out.printf("%16.16s", dataMhs[i][2]);
						System.out.println();
					}
					break;
				case 3 :
					break;
				case 4 :
					System.exit(0);
				default :
					System.out.println("Invalid input!");
			}
		}
	}
	
	public static void testingBasicString()
	{
		String str1 = "-1234";
		char c2 = '-';
		if (str1.charAt(0) == c2)
			System.out.println("str1.charAt(0) return '-' is comparable.");
		
		String csv = "1, 2, 3, 4, 5, 6";
		
		String[] st = csv.split(",");
		
		for (int i=0; i < st.length; i++)
		{
			int theInt = Integer.valueOf(st[i].trim());
			System.out.println(i + "." + st[i] + " the integer=" + theInt);
		}
	}
	
	public static void testingFileAccess()
	{
		File f = new File(".");
		System.out.println(f.getAbsolutePath());
	}
	
	public static void testBasicMath()
	{
		BigDecimal zero = Env.ZERO;
		System.out.println("The negate of zero : " + zero.negate());
		BigDecimal bd = new BigDecimal(Double.valueOf("-101.00000000002"));
		
		System.out.println("BigDecimal.scale : " + bd.scale());
		System.out.println("BigDecimal.toString : " + bd.toString());
		
		int cekInt = 100;
		
		for (int i=0; i < 10; i++)
		{
			cekInt--;
			System.out.println(cekInt);
		}
	}
	
	public static void testingDate()
	{
		Calendar cal = Calendar.getInstance(Locale.ENGLISH);
		cal.set(2014, 11, 31);
		System.out.println("The week of month of " + cal.getTime() + " is " + cal.get(Calendar.WEEK_OF_MONTH));
		cal.set(2015, 0, 1);
		System.out.println("The week of month of " + cal.getTime() + " is " + cal.get(Calendar.WEEK_OF_MONTH));
		cal.set(2015, 0, 31);
		System.out.println("The week of month of " + cal.getTime() + " is " + cal.get(Calendar.WEEK_OF_MONTH));
		cal.set(2015, 1, 1);
		System.out.println("The week of month of " + cal.getTime() + " is " + cal.get(Calendar.WEEK_OF_MONTH));
		cal.set(2015, 2, 28);
		System.out.println("The day name of " + cal.getTime() + " is " + cal.get(Calendar.DAY_OF_WEEK));
		System.out.println("The day number of SUNDAY" + cal.getTime() + " is " + Calendar.SUNDAY);
	}
	
	public static void testBinarySearchOfArray()
	{
		int[] toBeSearched = new int[]{10, 20, 30, 40};
		
		System.out.println("Ix of 20 = " + Arrays.binarySearch(toBeSearched, 20));
		System.out.println("Ix of 1 = " + Arrays.binarySearch(toBeSearched, 1));
		System.out.println("Ix of 50 = " + Arrays.binarySearch(toBeSearched, 1));
	}
	
	public static void test1()
	{
		Properties prop1 = new Properties();
		prop1.setProperty("key1", "value1");
		
		Properties prop2 = new Properties();
		prop2.setProperty("key1", "value1");

		if (prop1 == prop2) 
			System.out.println("Dianggap sama");
		else 
			System.out.println("Dianggap beda");
		
		String[] fruits = {"Jambu", "Jamblang", "Juwet"};
		
		for (int i=0; i < fruits.length; ++i)
			System.out.println(i + ". " + fruits[i]);
		
		for (int i=0; i < fruits.length; i++)
			System.out.println(i + ". " + fruits[i]);
		
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("Jengkol");
		testList.add("Pete");
		testList.add("Kelor");
		testList.add("Sengon");
		System.out.println("============ Testing removing list while looping =============");
		/*
		 * concurrent error message.
		 */
//		ArrayList<String> testListTmp = testList;
//		int i = 0;
//		for (String test : testListTmp)
//		{
//			System.out.println(i + ". " + test);
//			testList.remove(i++);
//		}
		/*
		for (int i=0; i < testList.size(); i++) 
		{
			System.out.println(i + ". " + testList.get(i));
			testList.remove(i);
		}
		*/
		boolean firstCondition = true;
		boolean secondCondition = true;
		if (firstCondition == secondCondition)
			System.out.println("firstCondition == secondCondition");
		else 
			System.out.println("firstCondition != secondCondition");
		
		Calendar cal = Calendar.getInstance();
		System.out.println("Current week = " + cal.get(Calendar.WEEK_OF_YEAR));
		cal.set(2013, 0, 1);
		System.out.println("First week = " + cal.get(Calendar.WEEK_OF_YEAR));
		cal.set(2013, 11, 29);
		System.out.println("Last week = " + cal.get(Calendar.WEEK_OF_YEAR));
		System.out.println("Day of year 29-12-2013 = " + cal.get(Calendar.DAY_OF_YEAR));
		
		System.out.println("String [01 ]= Integer of " + Integer.valueOf("01"));
		
		cal = Calendar.getInstance();
		System.out.println("Day of Week = " + cal.get(Calendar.DAY_OF_WEEK));
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - cal.get(Calendar.DAY_OF_WEEK) + 1);
		
		System.out.println("Period Start = " + cal.getTime());
		
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 6);
		
		System.out.println("Period End = " + cal.getTime());
		
		String[] splittedString = "@123@@456@DCE= @ABC@ AND FGH=@IJK@".split("@");
		
		for (int i=0; i < splittedString.length; i++) {
			System.out.println("Token-" + i + "=" + splittedString[i]);
		}
		//String testWild = ""
		System.out.println("=============================================");
		for (char ch='A'; ch < 'B' + 'C'; ch++)
		{
			System.out.println("Char: " + ch);
		}
		
		cal = Calendar.getInstance(new Locale("ID", "ID"));
		/*
		cal.set(2013, 8, 1);
		int productionWeekNo = UNSTimeUtil.getProductionWeekNo(new Timestamp(cal.getTimeInMillis()));
		System.out.println("Production weekno: " + productionWeekNo);
		System.out.println("Week Start Date: " + UNSTimeUtil.getProductionWeekStartDate(2013, productionWeekNo));
		
		System.out.println("==========================");
		cal.set(2013, 0, 6);
		System.out.println("the date : " + new Timestamp(cal.getTimeInMillis()));
		productionWeekNo = cal.get(Calendar.WEEK_OF_YEAR);//UNSTimeUtil.getProductionWeekNo(new Timestamp(cal.getTimeInMillis()));
		System.out.println("Production weekno: " + productionWeekNo);
		System.out.println("The day is : " + cal.get(Calendar.DAY_OF_WEEK));		
		System.out.println("==========================");
		System.out.println("==========================");
		cal.set(2013, 0, 7);
		System.out.println("the date : " + new Timestamp(cal.getTimeInMillis()));
		productionWeekNo = cal.get(Calendar.WEEK_OF_YEAR);//UNSTimeUtil.getProductionWeekNo(new Timestamp(cal.getTimeInMillis()));
		System.out.println("Production weekno: " + productionWeekNo);
		System.out.println("The day is : " + cal.get(Calendar.DAY_OF_WEEK));		
		System.out.println("==========================");
		System.out.println("==========================");
		cal.set(2013, 0, 12);
		System.out.println("the date : " + new Timestamp(cal.getTimeInMillis()));
		//productionWeekNo = UNSTimeUtil.getProductionWeekNo(new Timestamp(cal.getTimeInMillis()));
		productionWeekNo = cal.get(Calendar.WEEK_OF_YEAR);
		System.out.println("Production weekno: " + productionWeekNo);
		System.out.println("Week Start Date: " + UNSTimeUtil.getProductionWeekStartDate(2013, productionWeekNo));		
		System.out.println("==========================");
		System.out.println("==========================");
		cal.set(2013, 0, 13);
		System.out.println("the date : " + new Timestamp(cal.getTimeInMillis()));
		//productionWeekNo = UNSTimeUtil.getProductionWeekNo(new Timestamp(cal.getTimeInMillis()));
		productionWeekNo = cal.get(Calendar.WEEK_OF_YEAR);
		System.out.println("Production weekno: " + productionWeekNo);
		System.out.println("Week Start Date: " + UNSTimeUtil.getProductionWeekStartDate(2013, productionWeekNo));		
		System.out.println("==========================");
		*/
		
		cal.set(2013, 0, 1);
		Timestamp ts = new Timestamp(cal.getTimeInMillis());
		System.out.println("=== The date : " + ts + " ====");
		int productionWeekNo = UNSTimeUtil.getProductionWeekNo(new Timestamp(cal.getTimeInMillis()));
		System.out.println("Production weekno: " + productionWeekNo);
		for (int i=productionWeekNo; i < 37; i++)
		{
			cal.add(Calendar.DATE, 7);
			ts = new Timestamp(cal.getTimeInMillis());
			System.out.println("=== The date : " + ts + " ====");
			productionWeekNo = UNSTimeUtil.getProductionWeekNo(new Timestamp(cal.getTimeInMillis()));
			System.out.println("Production weekno: " + productionWeekNo);
			System.out.print("Week Range: " + UNSTimeUtil.getProductionWeekStartDate(2013, productionWeekNo));
			System.out.println(" => " + UNSTimeUtil.getProductionWeekEndDate(2013, productionWeekNo));
		}
	}
}
