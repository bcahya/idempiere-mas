package com.unicore.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.PO;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.Query;

public class MUNSShippingFreight extends X_UNS_ShippingFreight {

	private static final long serialVersionUID = -4993367358730883566L;
	private MUNSShipping m_parent  = null;
	private PO m_reference = null;
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_ShippingFreight_ID
	 * @param trxName
	 */
	public MUNSShippingFreight(Properties ctx, int UNS_ShippingFreight_ID,
			String trxName) 
	{
		super(ctx, UNS_ShippingFreight_ID, trxName);
	}

	/**
	 * 
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSShippingFreight(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}

	/**
	 * 
	 * @param parent
	 */
	public MUNSShippingFreight (MUNSShipping parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setUNS_Shipping_ID(parent.get_ID());
		setClientOrg(parent);
		this.m_parent = parent;
	}
	
	/**
	 * 
	 * @param parent
	 * @param poReference
	 */
	public MUNSShippingFreight (MUNSShipping parent, PO poReference)
	{
		this (parent);
		set_Value(poReference.get_TableName() + "_ID", poReference.get_ID());
		m_reference = poReference;
		switch (m_reference.get_TableName())
		{
			case MUNSPackingList.Table_Name :
				setFreightType(FREIGHTTYPE_PackingList);
				break;
			case MUNSExpedition.Table_Name :
				setFreightType(FREIGHTTYPE_Expedition);
				break;
			case MOrder.Table_Name :
				setFreightType(FREIGHTTYPE_Canvas);
				break;
			case MMovement.Table_Name :
				setFreightType(FREIGHTTYPE_Movement);
				break;
			case MInOut.Table_Name :
				setFreightType(FREIGHTTYPE_MaterialReceipt);
				break;
			default :
				throw new AdempiereException("Unhandled table name " + 
						m_reference.get_TableName());
		}
	}
	
	/**
	 * Get Parent
	 * @return {@link MUNSShipping}
	 */
	public MUNSShipping getParent ()
	{
		if (null == m_parent)
		{
			m_parent = new MUNSShipping(
					getCtx(), getUNS_Shipping_ID(), get_TrxName());
		}
		
		return m_parent;
	}
	
	public static MUNSShippingFreight getByParentAndReference(Properties ctx, int Parent_ID, PO poReference,
			String trxName)
	{
		MUNSShippingFreight sf = null;
		String whereClause = poReference.get_TableName() + "_ID=?";
		if(Parent_ID > 0)
		{
			whereClause += " AND UNS_Shipping_ID = " + Parent_ID;
		}
		else
		{
			whereClause += " AND EXISTS (SELECT 1 FROM UNS_Shipping WHERE UNS_Shipping.UNS_Shipping_ID"
					+ " = UNS_ShippingFreight.UNS_Shipping_ID AND UNS_Shipping.DocStatus IN ('CO', 'RE'))";
		}
		sf = new Query(ctx, Table_Name, whereClause, trxName).setParameters(poReference.get_ID()).first();
		
		return sf;
	}
	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		initVolumeAndTonase();
		validateReference();
		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 */
	private void validateReference ()
	{
		switch (getFreightType())
		{
			case FREIGHTTYPE_Canvas :
				setUNS_Expedition_ID(-1);
				setM_InOut_ID(-1);
				setM_Movement_ID(-1);
				setUNS_PackingList_ID(-1);
				break;
			case FREIGHTTYPE_Expedition :
				setC_Order_ID(-1);
				setM_InOut_ID(-1);
				setM_Movement_ID(-1);
				setUNS_PackingList_ID(-1);
				break;
			case FREIGHTTYPE_MaterialReceipt :
				setC_Order_ID(-1);
				setUNS_Expedition_ID(-1);
				setM_Movement_ID(-1);
				setUNS_PackingList_ID(-1);
				break;
			case FREIGHTTYPE_Movement :
				setC_Order_ID(-1);
				setUNS_Expedition_ID(-1);
				setM_InOut_ID(-1);
				setUNS_PackingList_ID(-1);
				break;
			case FREIGHTTYPE_PackingList :
				setC_Order_ID(-1);
				setUNS_Expedition_ID(-1);
				setM_InOut_ID(-1);
				setM_Movement_ID(-1);
				break;
			default : break;
		}
		
		boolean ok = getUNS_Expedition_ID() > 0 || getUNS_PackingList_ID() > 0
				|| getM_InOut_ID() > 0 || getM_Movement_ID() > 0 
				|| getC_Order_ID() > 0;
		if (!ok)
		{
			throw new AdempiereUserError("No Reference");
		}
	}
	
	private void initVolumeAndTonase ()
	{
		if (getUNS_Expedition_ID() > 0)
		{
			initVolumeAndTonaseFromExpedition();
		}
		else if (getUNS_PackingList_ID() > 0)
		{
			initVolumeAndTonaseFromPackingList();
		}
		else if (getM_Movement_ID() > 0)
		{
			initVolumeAndTonaseFromMovement();
		}
		else if (getM_InOut_ID() > 0)
		{
			initVolumeAndTonaseFromInOut();
		}
		else if (getC_Order_ID() > 0)
		{
			initVolumeAndTonaseFromCanvas();
		}
		else
		{
			throw new AdempiereUserError("No document reference");
		}
	}
	
	private void initVolumeAndTonaseFromPackingList ()
	{
		MUNSPackingList pl = new MUNSPackingList(
				getCtx(), getUNS_PackingList_ID(), get_TrxName());
		BigDecimal tonase = pl.getTonase();
		BigDecimal volume = pl.getVolume();
		setTonase(tonase);
		setVolume(volume);
	}
	
	private void initVolumeAndTonaseFromMovement ()
	{
		MMovement move = (MMovement) getM_Movement();
		MMovementLine[] lines = move.getLines(false);
		BigDecimal volume = Env.ZERO;
		BigDecimal tonase = Env.ZERO;
		for (int i=0; i<lines.length; i++)
		{
			MMovementLine line = lines[i];
			MProduct p = (MProduct) line.getM_Product();
			BigDecimal w = p.getGrossWeight();
			BigDecimal v = p.getVolume();
			w = w.multiply(line.getMovementQty());
			w = w.divide(new BigDecimal(1000), 5, RoundingMode.HALF_UP);
			v = v.multiply(line.getMovementQty());
			volume = volume.add(v);
			tonase = tonase.add(w);
		}
		setTonase(tonase);
		setVolume(volume);
	}
	
	private void initVolumeAndTonaseFromCanvas ()
	{
		MOrder o = (MOrder) getC_Order();
		MOrderLine[] lines = o.getLines();
		BigDecimal volume = Env.ZERO;
		BigDecimal tonase = Env.ZERO;
		for (int i=0; i<lines.length; i++)
		{
			MOrderLine line = lines[i];
			MProduct p = (MProduct) line.getM_Product();
			BigDecimal w = p.getGrossWeight();
			BigDecimal v = p.getVolume();
			w = w.multiply(line.getQtyOrdered());
			w = w.divide(new BigDecimal(1000), 5, RoundingMode.HALF_UP);
			v = v.multiply(line.getQtyOrdered());
			volume = volume.add(v);
			tonase = tonase.add(w);
		}
		
		setTonase(tonase);
		setVolume(volume);
	}
	
	private void initVolumeAndTonaseFromInOut ()
	{
		MInOut io = (MInOut) getM_InOut();
		MInOutLine[] lines = io.getLines(false);
		BigDecimal volume = Env.ZERO;
		BigDecimal tonase = Env.ZERO;
		for (int i=0; i<lines.length; i++)
		{
			MInOutLine line = lines[i];
			MProduct p = (MProduct) line.getM_Product();
			BigDecimal w = p.getGrossWeight();
			BigDecimal v = p.getVolume();
			w = w.multiply(line.getMovementQty());
			w = w.divide(new BigDecimal(1000), 5, RoundingMode.HALF_UP);
			v = v.multiply(line.getMovementQty());
			volume = volume.add(v);
			tonase = tonase.add(w);
		}
		setTonase(tonase);
		setVolume(volume);
	}
	
	private void initVolumeAndTonaseFromExpedition ()
	{
		MUNSExpedition exp = new MUNSExpedition (
				getCtx(), getUNS_Expedition_ID(), get_TrxName());
		BigDecimal tonase = exp.getTonase();
		BigDecimal volume = exp.getVolume();
		setTonase(tonase);
		setVolume(volume);
	}
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		updateHeader();
		return super.afterSave(newRecord, success);
	}
	
	private void updateHeader ()
	{
		MUNSShipping parent = getParent();
		MUNSShippingFreight[] freights = parent.getFreights(false);
		BigDecimal tv = Env.ZERO;
		BigDecimal tw = Env.ZERO;
		for (int i=0; i<freights.length; i++)
		{
			MUNSShippingFreight f = freights[i];
			BigDecimal v = f.getVolume();
			BigDecimal w = f.getTonase();
			tv = tv.add(v);
			tw = tw.add(w);
		}
		
		parent.setTonase(tw);
		parent.setVolume(tv);
		parent.saveEx();
	}
}
