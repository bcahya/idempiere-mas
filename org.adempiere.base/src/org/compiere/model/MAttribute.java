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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 *  Product Attribute
 *
 *	@author Jorg Janke
 *	@version $Id: MAttribute.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MAttribute extends X_M_Attribute
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7869800574413317999L;


	/**
	 * 	Get Attributes Of Client
	 *	@param ctx Properties
	 *	@param onlyProductAttributes only Product Attributes
	 *	@param onlyListAttributes only List Attributes
	 *	@return array of attributes
	 */
	public static MAttribute[] getOfClient(Properties ctx, 
		boolean onlyProductAttributes, boolean onlyListAttributes)
	{
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		String sql = "";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(AD_Client_ID);
		if (onlyProductAttributes)
			{
				sql += " AND IsInstanceAttribute=?";
				params.add(false);
			}
		if (onlyListAttributes)
			{
				sql += " AND AttributeValueType=?";
				params.add(MAttribute.ATTRIBUTEVALUETYPE_List);
			}
		StringBuilder whereClause = new StringBuilder("AD_Client_ID=?").append(sql);
		
		List<MAttribute>list = new Query(ctx,I_M_Attribute.Table_Name,whereClause.toString(),null)
		.setParameters(params)
		.setOnlyActiveRecords(true)
		.setOrderBy("Name")
		.list();

		MAttribute[] retValue = new MAttribute[list.size ()];
		list.toArray (retValue);
		if (s_log.isLoggable(Level.FINE)) s_log.fine("AD_Client_ID=" + AD_Client_ID + " - #" + list.size());
		return retValue;
	}	//	getOfClient
	
	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (MAttribute.class);

	
	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_Attribute_ID id
	 *	@param trxName transaction
	 */
	public MAttribute (Properties ctx, int M_Attribute_ID, String trxName)
	{
		super (ctx, M_Attribute_ID, trxName);
		if (M_Attribute_ID == 0)
		{
			setAttributeValueType(ATTRIBUTEVALUETYPE_StringMax40);
			setIsInstanceAttribute (false);
			setIsMandatory (false);
		}
	}	//	MAttribute

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAttribute (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAttribute

	/**	Values						*/
	private MAttributeValue[]		m_values = null;

	/**
	 *	Get Values if List
	 *	@return Values or null if not list
	 */
	public MAttributeValue[] getMAttributeValues()
	{
		if (m_values == null && ATTRIBUTEVALUETYPE_List.equals(getAttributeValueType()))
		{
			final String whereClause = I_M_AttributeValue.COLUMNNAME_M_Attribute_ID+"=?";
			List<MAttributeValue> list = new ArrayList<MAttributeValue>();
			if (!isMandatory())
				list.add (null);
			list = new Query(getCtx(),I_M_AttributeValue.Table_Name,whereClause,null)
			.setParameters(getM_Attribute_ID())
			.setOrderBy("Value")
			.list();
			m_values = new MAttributeValue[list.size()];
			list.toArray(m_values);
		}
		return m_values;
	}	//	getValues

	
	/**************************************************************************
	 * 	Get Attribute Instance
	 *	@param M_AttributeSetInstance_ID attribute set instance
	 *	@return Attribute Instance or null
	 */
	public MAttributeInstance getMAttributeInstance (int M_AttributeSetInstance_ID)
	{
		final String whereClause = I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID+"=? AND "+I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID+"=?";
		MAttributeInstance retValue = new Query(getCtx(),I_M_AttributeInstance.Table_Name,whereClause,get_TrxName())
		.setParameters(getM_Attribute_ID(),M_AttributeSetInstance_ID)
		.first();

		return retValue;
	}	//	getAttributeInstance

	/**
	 * 	Set Attribute Instance
	 * 	@param value value
	 * 	@param M_AttributeSetInstance_ID id
	 */
	public void setMAttributeInstance (int M_AttributeSetInstance_ID, MAttributeValue value)
	{
		MAttributeInstance instance = getMAttributeInstance(M_AttributeSetInstance_ID);
		if (instance == null)
		{
			if (value != null)
				instance = new MAttributeInstance (getCtx (), getM_Attribute_ID (),
					M_AttributeSetInstance_ID, value.getM_AttributeValue_ID (),
					value.getName (), get_TrxName()); 					//	Cached !!
			else
				instance = new MAttributeInstance (getCtx(), getM_Attribute_ID(),
					M_AttributeSetInstance_ID, 0, null, get_TrxName());
		}
		else
		{
			if (value != null)
			{
				instance.setM_AttributeValue_ID (value.getM_AttributeValue_ID ());
				instance.setValue (value.getName()); 	//	Cached !!
			}
			else
			{
				instance.setM_AttributeValue_ID (0);
				instance.setValue (null);
			}
		}
		instance.saveEx();
	}	//	setAttributeInstance

	/**
	 * 	Set Attribute Instance
	 * 	@param value string value
	 * 	@param M_AttributeSetInstance_ID id
	 */
	public void setMAttributeInstance (int M_AttributeSetInstance_ID, String value)
	{
		MAttributeInstance instance = getMAttributeInstance(M_AttributeSetInstance_ID);
		if (instance == null)
			instance = new MAttributeInstance (getCtx(), getM_Attribute_ID(), 
				M_AttributeSetInstance_ID, value, get_TrxName());
		else
			instance.setValue(value);
		instance.saveEx();
	}	//	setAttributeInstance

	/**
	 * 	Set Attribute Instance
	 * 	@param value number value
	 * 	@param M_AttributeSetInstance_ID id
	 */
	public void setMAttributeInstance (int M_AttributeSetInstance_ID, BigDecimal value)
	{
		MAttributeInstance instance = getMAttributeInstance(M_AttributeSetInstance_ID);
		if (instance == null)
			instance = new MAttributeInstance (getCtx(), getM_Attribute_ID(), 
				M_AttributeSetInstance_ID, value, get_TrxName());
		else
			instance.setValueNumber(value);
		instance.saveEx();
	}	//	setAttributeInstance
	
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MAttribute[");
		sb.append (get_ID()).append ("-").append (getName())
			.append(",Type=").append(getAttributeValueType())
			.append(",Instance=").append(isInstanceAttribute())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	AfterSave
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		//	Changed to Instance Attribute
		if (!newRecord && is_ValueChanged("IsInstanceAttribute") && isInstanceAttribute())
		{
			StringBuilder sql = new StringBuilder("UPDATE M_AttributeSet mas ")
				.append("SET IsInstanceAttribute='Y' ")
				.append("WHERE IsInstanceAttribute='N'")
				.append(" AND EXISTS (SELECT * FROM M_AttributeUse mau ")
					.append("WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID")
					.append(" AND mau.M_Attribute_ID=").append(getM_Attribute_ID()).append(")");
			int no = DB.executeUpdate(sql.toString(), get_TrxName());
			if (log.isLoggable(Level.FINE)) log.fine("AttributeSet Instance set #" + no);
		}
		return success;
	}	//	afterSave
	
	//TAMBAHAN
	/**
	 * 
	 * @param M_AttributeInstance
	 * @param po
	 * @param ColumnName
	 * @param trxName
	 */
	public void resetInstanceValue (MAttributeInstance M_AttributeInstance, PO po, String trxName)
	{
		if ((po == null) || (getAD_Table_ID() <= 0))
			return;
		I_AD_Column adColumn = getAD_Column();
		String columnName = adColumn.getColumnName();
		
//		if (isUPC())
//		{
//			String upcVal = po.get_ValueAsString("UPC");
//			M_AttributeInstance.setValue(upcVal);
//			M_AttributeInstance.saveEx();
//		}
		if (getAD_Table_ID() == po.get_Table_ID())
		{
			I_AD_Reference adRef = adColumn.getAD_Reference();
			I_AD_Reference adRefValue = adColumn.getAD_Reference_Value();
			//if (X_AD_Reference.VALIDATIONTYPE_DataType.equals(adRef.getValidationType()))
			if ("Table Direct".equals(adRef.getName()) 
					|| ("Table".equals(adRef.getName()) && (adRefValue != null)
							&& X_AD_Reference.VALIDATIONTYPE_TableValidation.equals(adRefValue.getValidationType()))
					|| ("Table".equals(adRef.getName()) && (adRefValue == null))
					|| "ID".equals(adRef.getName())
					|| "Locator (WH)".equals(adRef.getName())
					|| "Search".equals(adRef.getName()))
			{
				//if (columnName.substring(columnName.length()-3).equals("_ID")) 
				//{
					String tableName = getTableName(columnName.substring(0,columnName.length()-3), trxName);
					
					initInstanceValue(M_AttributeInstance, tableName, columnName, po, trxName);
				//}
			}
			else if ((adRefValue != null) && X_AD_Reference.VALIDATIONTYPE_ListValidation.equals(adRefValue.getValidationType()))
			{
				if (X_M_Attribute.COLUMNVALUE_Name.equals(getColumnValue()))
				{
					//X_AD_Ref_List refList = new X_AD_Ref_List(getCtx(), getAD_Column().getAD_Reference_Value_ID(), trxName);
					String sql = "SELECT name FROM AD_Ref_List WHERE AD_Reference_ID=? AND Value=?";
					String listValueName = 
							DB.getSQLValueString(get_TrxName(), sql, 
									new Object[]{adRefValue.getAD_Reference_ID(), po.get_ValueAsString(columnName)});
					M_AttributeInstance.setValue(listValueName);
				}
				else 
				{
					M_AttributeInstance.setValue(po.get_ValueAsString(columnName));
				}
			}
			else 
			{				
				M_AttributeInstance.setValue(po.get_ValueAsString(columnName));
			}
			M_AttributeInstance.save();
		}
		else if ((po.getParent() != null)) 
		{
			resetInstanceValue (M_AttributeInstance, po.getParent(), trxName);
		}
	}
	
	/**
	 * 
	 * @param possibleTableName
	 * @param trxName
	 * @return
	 */
	private String getTableName(String possibleTableName, String trxName)
	{
		String tableName = null;
		String sql = "SELECT " + X_AD_Table.COLUMNNAME_TableName + " FROM "
				+ X_AD_Table.Table_Name + " WHERE " + X_AD_Table.COLUMNNAME_TableName + "=?";
		tableName = DB.getSQLValueString(trxName, sql, possibleTableName);
		
		if (null != tableName && !"".equals(tableName))
			return tableName;
		
		// The column to be search is probably set as a table reference.
		//I_AD_Reference addRefValue = getAD_Column().getAD_Reference_Value();
		int addRefValId = getAD_Column().getAD_Reference_Value_ID();
		
		if (addRefValId <= 0)
			return null;
		I_AD_Reference adRefValue = getAD_Column().getAD_Reference_Value();
		if (X_AD_Reference.VALIDATIONTYPE_TableValidation.equals(adRefValue.getValidationType())) 
		{
			I_AD_Ref_Table refTable = new X_AD_Ref_Table(getCtx(), addRefValId, get_TrxName());
			
			tableName = refTable.getAD_Table().getTableName();
		}
		/**
		else if (X_AD_Reference.VALIDATIONTYPE_ListValidation.equals(adReff.getValidationType()))
		{
			I_AD_Ref_List refList = new X_AD_Ref_List(getCtx(), addRefValId, get_TrxName());
			tableName = refList.getName();
		}
		*/
		return tableName;
	}
	
	/**
	 * 
	 * @param M_AttributeInstance
	 * @param columnName
	 * @param po
	 * @param trxName
	 */
	private void initInstanceValue(MAttributeInstance M_AttributeInstance, String tableName, 
									String columnName, PO po, String trxName)
	{
		String instanceValue = null;
		String columnValue = null;
		String whereClause = null;
		
		//String tableName = getTableName(columnName.substring(0,columnName.length()-3), trxName);
		
		if (null != tableName)
		{
			String colWhereValue = po.get_ValueAsString(columnName);
			
			if (Util.isEmpty(colWhereValue, true) && this.isMandatory()) {
				throw new AdempiereException("Mandatory attribute of " + this.getName() + 
						" cannot be set with null value for column " + columnName);
			}
			else {
				colWhereValue = "0";
			}
			
			whereClause = tableName + "."+tableName+"_ID=" + colWhereValue;
			
			if (MAttribute.COLUMNVALUE_Name.equals(getColumnValue())) 
			{
				columnValue = tableName+".Name ";
			}
			else if(MAttribute.COLUMNVALUE_Value.equals(getColumnValue()))
			{
				columnValue = tableName+".Value ";
			}
			else 
			{
				columnValue = tableName+"."+columnName+" ";
			}
		}
		if (null != tableName && null != columnValue)
		{
			instanceValue = getInstanceValue(tableName, columnValue, whereClause);
		}
		else 
		{
			instanceValue = po.get_ValueAsString(columnName);
		}
		M_AttributeInstance.setValue(instanceValue);
		//M_AttributeInstance.save();
	}
	
	/**
	 * 
	 * @param tableName
	 * @param columName
	 * @param whereClause
	 * @return
	 */
	private String getInstanceValue(String tableName,String columName, String whereClause) 
	{
		String instanceValue = null;
		instanceValue = DB.getSQLValueString(
				get_TrxName(), "SELECT " + columName + " FROM " + tableName + " WHERE " + whereClause);
		
		return instanceValue;
	}

	
}	//	MAttribute
