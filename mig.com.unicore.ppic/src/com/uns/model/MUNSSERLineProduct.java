/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.Query;
import org.compiere.util.DB;
import com.uns.util.ErrorMsg;

/**
 * @author menjangan
 *
 */
public class MUNSSERLineProduct extends X_UNS_SERLineProduct {
	
	private MUNSSERLineBuyer m_parent = null;
	private static final int UOM_MT = 0;//UNSApps.getRefAsInt(UNSApps.UOM_MT);
	private String sMsg = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_SERPacks_ID
	 * @param trxName
	 */
	public MUNSSERLineProduct(Properties ctx, int UNS_SERLineProduct_ID, String trxName) {
		super(ctx, UNS_SERLineProduct_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSERLineProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return
	 */
	public MUNSSERLineBuyer getParent()
	{
		if (m_parent == null)
		{
			m_parent = new MUNSSERLineBuyer(getCtx(), getUNS_SERLineBuyer_ID()
					, get_TrxName());
		}
		return m_parent;
	}
	
	private MUNSSERLineProduct[] get()
	{
		MUNSSERLineProduct[] mLines = null;
		List<MUNSSERLineProduct> list =
				new Query(getCtx(), Table_Name, COLUMNNAME_UNS_SERLineBuyer_ID + " =?"
				, get_TrxName()).setParameters(getParent().getUNS_SERLineBuyer_ID())
				.setOrderBy(COLUMNNAME_Line).list();
		
		mLines = new MUNSSERLineProduct[list.size()];
		list.toArray(mLines);
		return mLines;
	}
	
	/**
	 * 
	 * @param description
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}
	
	@Override
	public boolean beforeSave(boolean newRecord)
	{
		if (getLine() == 0)
		{
			String SQL = "Select Coalesce(MAX(line),0) + 10 From " 
					+Table_Name + " WHERE " + COLUMNNAME_UNS_SERLineBuyer_ID + " =?";
			int ii = DB.getSQLValueEx(get_TrxName(), SQL, getUNS_SERLineBuyer_ID());
			setLine(ii);
		}
		
		String Msg = cekProduct();
		if (Msg != null)
		{
			ErrorMsg.setErrorMsg(getCtx(), "SaveError", Msg);
			return false;
		}
		
		BigDecimal totalAmt = getQty().multiply(getPrice());
		setLineNetAmt(totalAmt.setScale(2, BigDecimal.ROUND_HALF_UP));
		
		//set sales total FOB GTN/MT multiply Weight(MT)
		BigDecimal SalesTotal = BigDecimal.ZERO;
		if ((getWeight() != null && getWeight().compareTo(BigDecimal.ZERO) >0)
				&& (getFOBGtnToMT() != null && getFOBGtnToMT().compareTo(BigDecimal.ZERO) >0))
		{
			SalesTotal = getFOBGtnToMT().multiply(getWeight());
		}
		else
		{
			SalesTotal = getQty().multiply(getPrice());
		}
		setSalesTotal(SalesTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
		
		/** ---------------- **/
		
		return true;
	}
	
	@Override
	public boolean afterSave(boolean newRecord, boolean sucess)
	{
		if (!updateHeader() || !sucess)
			return false;
		
		return true;
	}
	
	@Override
	public boolean afterDelete(boolean sucess)
	{
		if (!updateHeader() && !sucess)
			return false;
		
		return true;
	}
	
	public static final BigDecimal MAX_TEUS_VOLUME = new BigDecimal(33);
	public static final BigDecimal MAX_TEUS_PAY_LOAD = new BigDecimal(22.1);
	
	public static final BigDecimal MAX_FEUS_VOLUME = new BigDecimal(67.3);
	public static final BigDecimal MAX_FEUS_PAY_LOAD = new BigDecimal(27.396);

	/**
	 * 
	 * @return
	 */
	public boolean updateHeader()
	{
		MUNSSERLineBuyer parent = getParent();
		parent.setTotalLines(getTotalLines());
		parent.setVolume(getTotalVolumeM3());
		parent.setSalesTotal(getTotalLineSalesTotal());
		parent.setWeight(getTotalWeightMT());
		BigDecimal teusQtyMT = 
				parent.getWeight().divide(MAX_TEUS_PAY_LOAD, BigDecimal.ROUND_HALF_UP)
				.setScale(4, BigDecimal.ROUND_HALF_UP);
		setParentUOMAndQty();
		parent.setTEU(teusQtyMT);
		if (sMsg != null)
		{
			ErrorMsg.setErrorMsg(getCtx(), "SaveError", sMsg);
			return false;
		}
		
		if (!getParent().save(get_TrxName()))
			return false;
		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	protected BigDecimal getTotalLines()
	{
		BigDecimal total = BigDecimal.ZERO;
		MUNSSERLineProduct[] mLines = get();
		
		for (int i=0; i<mLines.length; i++)
		{
			MUNSSERLineProduct mLine = mLines[i];
			total = total.add(mLine.getLineNetAmt());
		}
		
		return total;
	}
	
	/**
	 * 
	 * @return
	 */
	protected BigDecimal getTotalVolumeM3()
	{
		BigDecimal total = BigDecimal.ZERO;
		MUNSSERLineProduct[] mLines = get();
		
		for (MUNSSERLineProduct mLine : mLines)
		{
			total = total.add(mLine.getVolume());
		}
		return total;
	}
	
	/**
	 * 
	 * @return
	 */
	protected BigDecimal getTotalWeightMT()
	{
		BigDecimal total =  BigDecimal.ZERO;
		MUNSSERLineProduct[] mLines = get();
		
		for (int i=0; i<mLines.length; i++)
		{
			MUNSSERLineProduct mLine = mLines[i];
			total = total.add(mLine.getWeight());
		}
		
		return total;
	}
	
	/**
	 * 
	 * @return
	 */
	private String cekProduct()
	{
		String Msg = null;
		MUNSSERLineProduct[] mLines = get();
		
		for (int i=0; i<mLines.length; i++)
		{
			MUNSSERLineProduct mLine = mLines[i];
			if (mLine.getM_Product_ID() == getM_Product_ID()
					&& mLine.getUNS_SERLineProduct_ID() != getUNS_SERLineProduct_ID())
			{
				Msg = "Duplicate Product";
			}
		}
		
		return Msg;
	}
	
	/**
	 * 
	 * @return
	 */
	protected BigDecimal getTotalLineSalesTotal()
	{
		BigDecimal total = BigDecimal.ZERO;
		MUNSSERLineProduct[] mLines = get();
		
		for (int i=0; i<mLines.length; i++)
		{
			MUNSSERLineProduct mLine = mLines[i];
			total = total.add(mLine.getSalesTotal());
		}
		return total;
	}
	
	/**
	 * 
	 */
	public void setParentUOMAndQty()
	{
		int C_UOM_ID = 0;
		int count = 0;
		BigDecimal TotalQty = BigDecimal.ZERO;
		BigDecimal qtyAvailable = BigDecimal.ZERO;
		
		MUOM UOM = MUOM.get(getCtx(), UOM_MT);
		MUNSSERLineProduct[] mLines = get();
		
		for (int i=0; i<mLines.length; i++)
		{
			MUNSSERLineProduct mLine = mLines[i];
			if (getC_UOM_ID() != mLine.getC_UOM_ID())
			{				
				count ++;
			}
		}
		
		if (count != 0)
		{
			for (int i=0; i<mLines.length; i++)
			{
				MUNSSERLineProduct mLine = mLines[i];
				if (mLine.getC_UOM_ID() != UOM_MT)
				{
					if (mLine.isVolumeOrWeight())
					{
						BigDecimal conv = 
								MUOMConversion.convertProductTo(
										getCtx(), mLine.getM_Product_ID(), UOM_MT, mLine.getQty());
						BigDecimal convQtyAvailable = 
								MUOMConversion.convertProductTo(
										getCtx(), mLine.getM_Product_ID(), UOM_MT, mLine.getQty());
						
						if (null != convQtyAvailable)
						{
							qtyAvailable = qtyAvailable.add(convQtyAvailable);
						}
						
						if (null != conv)
						{
							TotalQty = TotalQty.add(conv);
						}
						else
						{
							sMsg = "No Conversion From " + mLine.getC_UOM().getName() 
									+ " TO " + UOM.getName() + " - " + mLine.getM_Product()
								.getName();
								
							break;
						}
					}
					else
					{
						if (null != mLine.getWeight() || mLine.getWeight().signum() > 0)
						{
							TotalQty = TotalQty.add(mLine.getWeight());
							qtyAvailable = qtyAvailable.add(mLine.getWeight());
						}
						else
						{
							sMsg = "Undefined Weight in Product " + mLine.getM_Product().getName();
							
							break;
						}
					}
				}
			}
			
			C_UOM_ID = UOM_MT;
		}
		else
		{
			C_UOM_ID = getC_UOM_ID();
			for (int i=0; i<mLines.length; i++)
			{
				MUNSSERLineProduct mLine = mLines[i];
				TotalQty = TotalQty.add(mLine.getQty());
			}
		}
		
		getParent().setQtyAvailable(qtyAvailable);
		getParent().setQty(TotalQty);
		getParent().setC_UOM_ID(C_UOM_ID);
	}
	
}
