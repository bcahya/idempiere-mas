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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;

/**
 *  Product Attribute Set
 *
 *	@author Jorg Janke
 *	@version $Id: MAttributeInstance.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MAttributeInstance extends X_M_AttributeInstance
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6154044437449512042L;


	/**
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param ignored ignored
	 *	@param trxName transaction
	 */
	public MAttributeInstance (Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
	}	//	MAttributeInstance

	/**
	 * 	Load Cosntructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAttributeInstance (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAttributeInstance

	/**
	 * 	String Value Constructior
	 *	@param ctx context
	 *	@param M_Attribute_ID attribute
	 *	@param M_AttributeSetInstance_ID instance
	 *	@param Value string value
	 *	@param trxName transaction
	 */
	public MAttributeInstance (Properties ctx, int M_Attribute_ID, 
		int M_AttributeSetInstance_ID, String Value, String trxName)
	{
		super(ctx, 0, trxName);
		setM_Attribute_ID (M_Attribute_ID);
		setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
		setValue (Value);
	}	//	MAttributeInstance
	
	/**
	 * 	Number Value Constructior
	 *	@param ctx context
	 *	@param M_Attribute_ID attribute
	 *	@param M_AttributeSetInstance_ID instance
	 *	@param BDValue number value
	 *	@param trxName transaction
	 */
	public MAttributeInstance (Properties ctx, int M_Attribute_ID, 
		int M_AttributeSetInstance_ID, BigDecimal BDValue, String trxName)
	{
		super(ctx, 0, trxName);
		setM_Attribute_ID (M_Attribute_ID);
		setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
		setValueNumber(BDValue);
	}	//	MAttributeInstance

	/**
	 * 	Selection Value Constructior
	 *	@param ctx context
	 *	@param M_Attribute_ID attribute
	 *	@param M_AttributeSetInstance_ID instance
	 *	@param M_AttributeValue_ID selection
	 * 	@param Value String representation for fast display
	 *	@param trxName transaction
	 */
	public MAttributeInstance (Properties ctx, int M_Attribute_ID, 
		int M_AttributeSetInstance_ID, int M_AttributeValue_ID, String Value, String trxName)
	{
		super(ctx, 0, trxName);
		setM_Attribute_ID (M_Attribute_ID);
		setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
		setM_AttributeValue_ID (M_AttributeValue_ID);
		setValue (Value);
	}	//	MAttributeInstance

	
	/**
	 * 	Set ValueNumber
	 *	@param ValueNumber number
	 */
	public void setValueNumber (BigDecimal ValueNumber)
	{
		super.setValueNumber (ValueNumber);
		if (ValueNumber == null)
		{
			setValue(null);
			return;
		}
		if (ValueNumber.signum() == 0)
		{
			setValue("0");
			return;
		}
		//	Display number w/o decimal 0
		char[] chars = ValueNumber.toString().toCharArray();
		StringBuilder display = new StringBuilder();
		boolean add = false;
		for (int i = chars.length-1; i >= 0; i--)
		{
			char c = chars[i];
			if (add)
				display.insert(0, c);
			else
			{
				if (c == '0')
					continue;
				else if (c == '.')	//	decimal point
					add = true;
				else
				{
					display.insert(0, c);
					add = true;
				}
			}
		}			
		setValue(display.toString());
	}	//	setValueNumber
	
	
	/**
	 *	String Representation
	 * 	@return info
	 */
	public String toString()
	{
		return getValue();
	}	//	toString

	//TAMBAHAN
	/**
	 * 
	 * @param ctx
	 * @param M_AttributeSetInstance_ID
	 * @param attrId
	 * @param trxName
	 * @return
	 */
	public static MAttributeInstance getNoSave(Properties ctx, int M_AttributeSetInstance_ID, int M_Attribute_ID, String trxName)
	{
		MAttributeInstance ai = findIdByParentId(ctx, M_AttributeSetInstance_ID, M_Attribute_ID, trxName);

		if (null == ai) 
		{
			ai = new MAttributeInstance(ctx, 0, trxName);
			ai.setM_Attribute_ID(M_Attribute_ID);
			ai.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
			ai.saveEx();
		}
		return ai;
	}
	
	/**
	 * Find attribute instance's id based on AttributeSetInstanceID and AttributeID.
	 * @param ctx
	 * @param attrSetInstanceId
	 * @param attrId
	 * @return return M_AttributeInstance_ID, or 0 if not found.
	 */
	public static MAttributeInstance findIdByParentId (Properties ctx, int M_AttributeSetInstance_ID, int M_Attribute_ID, String trxName)
	{ 
		MAttributeInstance ai = null;
		String sql = "SELECT * FROM M_AttributeInstance "
				+ " WHERE M_AttributeSetInstance_ID = ? AND M_Attribute_ID =?";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(sql, trxName);
			st.setInt(1, M_AttributeSetInstance_ID);
			st.setInt(2, M_Attribute_ID);
			rs = st.executeQuery();
			if (rs.next()) {
				ai = new MAttributeInstance(ctx, rs, trxName);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DB.close(rs, st);
		}
		return ai;
	}
	
	public static MAttributeInstance[] getAttributeInstance(Properties ctx, String trxName, int AttributeSetInstance_ID){
		String whereClauseFinal = MAttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID+"=? ";

		List<MAttributeInstance> list = new Query(ctx,
				MAttributeInstance.Table_Name, whereClauseFinal, trxName)
				.setParameters(new Object[] { AttributeSetInstance_ID })
				.setOrderBy(MAttributeInstance.COLUMNNAME_M_Attribute_ID).list();

		return list.toArray(new MAttributeInstance[list.size()]);
	}
	
}	//	MAttributeInstance
