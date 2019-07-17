/**
 * 
 */
package org.compiere.report;

/**
 * @author Haryadi
 *
 */
public interface IReportPeriod 
{

	/**
	 * Is it a Period type?
	 * @return true if it is a Period Amount Type.
	 */
	public boolean isPeriod();

	/**
	 * Is it a Year type?
	 * @return true if it is a Year Amount Type.
	 */
	public boolean isYear();

	/**
	 * Is it a Total type?
	 * @return true if it is a Total Amount Type.
	 */
	public boolean isTotal();
	
	/**
	 * Is it natural balance ?
	 * Natural balance means year balance for profit and loss a/c, total balance for balance sheet account
	 * @return true if Natural Balance Amount Type
	 */
	public boolean isNatural();
	
	
	/**
	 * Is it natural balance, year, or total period type?
	 * @return true if it is a natural, year or total period.
	 */
	public boolean isYearlyPeriod();
}
