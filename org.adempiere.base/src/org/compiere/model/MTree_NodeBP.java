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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	(Disk) Tree Node Model BPartner
 *	
 *  @author Jorg Janke
 *  @version $Id: MTree_NodeBP.java,v 1.3 2006/07/30 00:58:38 jjanke Exp $
 */
public class MTree_NodeBP extends X_AD_TreeNodeBP
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5103486471442008006L;

	/**
	 * 	Get Tree Node
	 *	@param tree tree
	 *	@param Node_ID node
	 *	@return node or null
	 */
	public static MTree_NodeBP get (MTree_Base tree, int Node_ID)
	{
		MTree_NodeBP retValue = null;
		String sql = "SELECT * FROM AD_TreeNodeBP WHERE AD_Tree_ID=? AND Node_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, tree.get_TrxName());
			pstmt.setInt (1, tree.getAD_Tree_ID());
			pstmt.setInt (2, Node_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = new MTree_NodeBP (tree.getCtx(), rs, tree.get_TrxName());
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, "get", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return retValue;
	}	//	get

	//UNS Customization
		/**
		 * 
		 * @param ctx
		 * @param AD_Tree_ID
		 * @param Node_ID
		 * @param trxName
		 * @return
		 */
		public static MTree_NodeBP get (Properties ctx,int AD_Tree_ID, int Node_ID, String trxName)
		{
			if(AD_Tree_ID <= 0)
			{
				AD_Tree_ID = MTree.getDefaultAD_Tree_ID(Env.getContextAsInt(ctx, "#AD_Client_ID")
						, MBPartner.COLUMNNAME_C_BPartner_ID);
			}
			
			MTree_NodeBP retValue = null;
			String sql = "SELECT * FROM AD_TreeNodeBP WHERE AD_Tree_ID=? AND Node_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement (sql, trxName);
				pstmt.setInt (1, AD_Tree_ID);
				pstmt.setInt (2, Node_ID);
				rs = pstmt.executeQuery ();
				if (rs.next ())
					retValue = new MTree_NodeBP (ctx, rs, trxName);
			}
			catch (Exception e)
			{
				s_log.log(Level.SEVERE, "get", e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			return retValue;
		}	//	get
	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MTree_NodeBP.class);

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MTree_NodeBP(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MTree_NodeBP

	/**
	 * 	Full Constructor
	 *	@param tree tree
	 *	@param Node_ID node
	 */
	public MTree_NodeBP (MTree_Base tree, int Node_ID)
	{
		super (tree.getCtx(), 0, tree.get_TrxName());
		setClientOrg(tree);
		setAD_Tree_ID (tree.getAD_Tree_ID());
		setNode_ID(Node_ID);
		//	Add to root
		setParent_ID(0);
		setSeqNo (0);
	}	//	MTree_NodeBP

	//TAMBAHAN
	public int getLevel()
	{
		int level = 1;
		
		if(getParent_ID() > 0) {
			return getLevel(getParent_ID(), level);
		}
		
		return level;
	}
	
	private int getLevel(int parent_id, int childLevel)
	{
		int level = ++childLevel;
		MTree_NodeBP parentNode = MTree_NodeBP.get(
				getCtx(), getAD_Tree_ID(), parent_id, get_TrxName());
		
		if(parentNode.getParent_ID() > 0) {
			return parentNode.getLevel(parentNode.getParent_ID(), childLevel);
		}
		
		return level;
	}
	
	//TAMBAHAN
	public boolean myParentLevelIs(String salesLevel)
	{
		if(getParent_ID() <= 0)
			return false;
		
		MTree_NodeBP treeNode = MTree_NodeBP.get(getCtx(), 0, getParent_ID(), get_TrxName());
		MBPartner bp = MBPartner.get(getCtx(), treeNode.getNode_ID());
		if(bp.getSalesLevel() != null && bp.getSalesLevel().equals(salesLevel))
			return true;
		
		return treeNode.myParentLevelIs(salesLevel);
	}

	public boolean isParentOf(int node_ID)
	{
		for(MTree_NodeBP treeNode : getChilds(true))
		{
			if(treeNode.get_ID() == node_ID)
				return true;
			
			treeNode.isParentOf(node_ID);
		}
		return false;
	}
	
	private MTree_NodeBP[] m_childs;
	
	public MTree_NodeBP[] getChilds(boolean requery)
	{
		if(null != m_childs && !requery)
			return m_childs;
		
		String whereClause = COLUMNNAME_Parent_ID + "=?";
		List<MTree_NodeBP> list = new Query(
				getCtx(), Table_Name, whereClause, get_TrxName())
		.setParameters(getNode_ID()).list();
		if(list.size() == 0)
			return null;
		
		m_childs = new MTree_NodeBP[list.size()];
		list.toArray(m_childs);
		
		return m_childs;
	}
	
}	//	MTree_NodeBP
