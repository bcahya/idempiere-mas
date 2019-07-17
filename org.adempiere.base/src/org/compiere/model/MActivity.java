/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.CCache;
import org.compiere.util.DB;


/**
 *	Activity Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MActivity.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2736867 ] Add caching support to MActivity
 */
public class MActivity extends X_C_Activity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3014706648686670575L;
	
	/** Static Cache */
	private static CCache<Integer, MActivity> s_cache = new CCache<Integer, MActivity>(Table_Name, 30);

	/**
	 * Get/Load Activity [CACHED]
	 * @param ctx context
	 * @param C_Activity_ID
	 * @return activity or null
	 */
	public static MActivity get(Properties ctx, int C_Activity_ID)
	{
		if (C_Activity_ID <= 0)
		{
			return null;
		}
		// Try cache
		MActivity activity = s_cache.get(C_Activity_ID);
		if (activity != null)
		{
			return activity;
		}
		// Load from DB
		activity = new MActivity(ctx, C_Activity_ID, null);
		if (activity.get_ID() == C_Activity_ID)
		{
			s_cache.put(C_Activity_ID, activity);
		}
		else
		{
			activity = null;
		}
		return activity;
	}

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_Activity_ID id
	 *	@param trxName transaction
	 */
	public MActivity (Properties ctx, int C_Activity_ID, String trxName)
	{
		super (ctx, C_Activity_ID, trxName);
	}	//	MActivity

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MActivity (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MActivity
	
	
	/**
	 * 	After Save.
	 * 	Insert
	 * 	- create tree
	 *	@param newRecord insert
	 *	@param success save success
	 *	@return true if saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if (newRecord)
			insert_Tree(MTree_Base.TREETYPE_Activity);
		if (newRecord || is_ValueChanged(COLUMNNAME_Value))
			update_Tree(MTree_Base.TREETYPE_Activity);
		//	Value/Name change
		if (!newRecord && (is_ValueChanged("Value") || is_ValueChanged("Name"))){
			StringBuilder msguvd = new StringBuilder("C_Activity_ID=").append(getC_Activity_ID());
			MAccount.updateValueDescription(getCtx(), msguvd.toString(), get_TrxName());
		}	
		return true;
	}	//	afterSave
	
	/**
	 * 	After Delete
	 *	@param success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		if (success)
			delete_Tree(MTree_Base.TREETYPE_Activity);
		return success;
	}	//	afterDelete

	//TAMBAHAN
	/**
	 * Get the activity id of a search key of 'value'.
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static int getIDOf(Properties ctx, String value, String trxName)
	{
		int existingID = DB.getSQLValue(trxName, "SELECT C_Activity_ID FROM C_Activity WHERE value=?", value);
		
		return existingID;
	}
	
	/**
	 * It will first try to get it by the search key of 'value', it doesn't exist it will create it.
	 * 
	 * @param ctx
	 * @param value
	 * @param Name
	 * @param trxName
	 * @return
	 */
	public static MActivity create(Properties ctx, String value, String Name, String trxName)
	{
		MActivity retAct = get(ctx, value, trxName);
		
		if (retAct != null)
			return retAct;
		
		retAct = new MActivity (ctx, 0, trxName);
		retAct.setValue(value);
		retAct.setName(Name);
		retAct.setDescription("***Auto generated***");
		
		retAct.saveEx();
		
		return retAct;
	}
	
	/**
	 * Get an activity based on it's search key.
	 * @param ctx
	 * @param searchKey
	 * @param trxName The instance of the activity if found or null otherwise.
	 * @return
	 */
	public static MActivity get (Properties ctx, String searchKey, String trxName)
	{
		MActivity retAct = null;
		
		int existingID = DB.getSQLValue(trxName, "SELECT C_Activity_ID FROM C_Activity WHERE value=?", searchKey);
		
		if (existingID > 0)
			retAct = new MActivity(ctx, existingID, trxName);
		
		return retAct;
	}
}	//	MActivity
