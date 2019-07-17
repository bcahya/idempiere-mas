/**
 * 
 */
package org.uns.matica.form.listener;

import org.matica.form.listener.MAT_I_BusinessLogic;

/**
 * @author Haryadi
 *
 */
public abstract class UNS_I_BusinessLogic extends MAT_I_BusinessLogic{

	/**
	 * Check if the transaction of current business logic need to be committed.
	 * @return
	 */
	public abstract boolean needCommitTrx();
	
	/**
	 * Get the AD_Table_ID of a fieldName.
	 * @param fieldName
	 * @return the AD_Table_ID of fieldName.
	 */
	public abstract int getTableIDOf(String fieldName);
}
