/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PartType;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.compiere.model.MTable;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.uns.util.DBUtils;
import com.uns.util.MessageBox;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;


/**
 * @author AzHaidar
 * 
 */
public class MUNSResourceInOut extends X_UNS_Resource_InOut implements Comparable<MUNSResourceInOut> {

	private MUNSResource m_Parent = null;
	private int m_Product_BOM_ID = 0;
	// private static int m_KBA_ID = UNSApps.getRef(UNSApps.PRD_KBA);
	// private static int m_KBB_ID = UNSApps.getRef(UNSApps.PRD_KBB);
	// private int m_UOM_HOUR = UNSApps.getRefAsInt(UNSApps.UOM_HR);
	/**
	 * 
	 */
	private static final long serialVersionUID = -5327860355390591980L;

	/**
	 * @param ctx
	 * @param UNS_Resource_InOut_ID
	 * @param trxName
	 */
	public MUNSResourceInOut(Properties ctx, int UNS_Resource_InOut_ID, String trxName) {
		super(ctx, UNS_Resource_InOut_ID, trxName);
	}

	public MUNSResource getParent() {
		if (null == m_Parent)
			m_Parent = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());

		return m_Parent;
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSResourceInOut(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public static String getOutputType(Properties ctx, int UNS_Resource_InOut_ID, String trxName) {
		MUNSResourceInOut rscInOut = new MUNSResourceInOut(ctx, UNS_Resource_InOut_ID, trxName);
		return rscInOut.getOutputType();
	}

	/**
	 * 
	 * @return
	 */
	public MProductBOM getBOM_Me() {
		MProductBOM[] BOMS = MProductBOM.getBOMLines(getCtx(), getM_Product_ID(), get_TrxName());
		for (int i = 0; i < BOMS.length; i++)
		{
			MProductBOM BOM = BOMS[i];
			if (BOM.getM_ProductBOM().getM_Product_ID() == m_Product_BOM_ID)
				return BOM;
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Vector<Integer> getBOMSProduct() {
		Vector<Integer> bOMSProduct = new Vector<Integer>();
		MProductBOM[] BOMS = MProductBOM.getBOMLines(getCtx(), getM_Product_ID(), get_TrxName());
		for (MProductBOM BOM : BOMS)
		{
			int Product = BOM.getM_ProductBOM().getM_Product_ID();
			bOMSProduct.add(Product);
		}
		return bOMSProduct;
	}

	/**
	 * 
	 * @param M_Product_BOM_ID
	 */
	public void setM_Product_BOM_ID(int M_Product_BOM_ID) {
		m_Product_BOM_ID = M_Product_BOM_ID;
	}

	/**
	 * Indicates if this output is optional.
	 * 
	 * @return
	 */
	public boolean isOptional() {
		return (getInOutType().equals(INOUTTYPE_Output) && getOutputType().equals(OUTPUTTYPE_Optional));
	}

	/**
	 * Indicates if this output is single.
	 * 
	 * @return
	 */
	public boolean isSingle() {
		return (getInOutType().equals(INOUTTYPE_Output) && getOutputType().equals(OUTPUTTYPE_Single));
	}

	/**
	 * Indicates if this output is multi.
	 * 
	 * @return
	 */
	public boolean isMulti() {
		return (getInOutType().equals(INOUTTYPE_Output) && getOutputType().equals(OUTPUTTYPE_Multi));
	}

	/**
	 * Indicates if this output is multioptional.
	 * @return
	 */
	public boolean isMultiOptional() {
		return (getInOutType().equals(INOUTTYPE_Output) && getOutputType().equals(OUTPUTTYPE_MultiOptional));
	}

	/**
	 * 
	 * @param day
	 * @param ST
	 * @return
	 */
	public BigDecimal getOptCaps(int day, MUNSSlotType ST) {
		BigDecimal WorkTime = ST.getWorkTime(day);
		BigDecimal Qty = getOptimumCaps();
		/*
		 * if (getUOMTime_ID() != m_UOM_HOUR) { Qty = MUOMConversion.convertProductTo(getCtx(), 1,
		 * m_UOM_HOUR, Qty); }
		 */
		BigDecimal optCaps = Qty.multiply(WorkTime);
		return optCaps;
	}


	/**
	 * 
	 * @param day
	 * @param ST
	 * @return max caps of resource
	 */
	public BigDecimal getMaxCapsDay(int day, MUNSSlotType ST) {
		BigDecimal WorkTime = ST.getWorkTime(day);
		BigDecimal Qty = getMaxCaps();
		/*
		 * if (getUOMTime_ID() != m_UOM_HOUR) { Qty = MUOMConversion.convertProductTo(getCtx(),
		 * getM_Product_ID(), m_UOM_HOUR, Qty); }
		 */
		BigDecimal maxCaps = Qty.multiply(WorkTime);
		return maxCaps;
	}


	/**
	 * 
	 * @param month
	 * @param slotType
	 * @return
	 */
	public BigDecimal getMaxCapsMonthly(int month, int year, MUNSSlotType slotType) {
		BigDecimal avgTimeProductionDay = getDailyAvgProductionHours();
		BigDecimal MaxCapsDay = avgTimeProductionDay.multiply(getMaxCaps());
		int workDay = slotType.getWorkDay(year, month);
		BigDecimal maxcapsMonth = MaxCapsDay.multiply(new BigDecimal(workDay));
		return maxcapsMonth;
	}

	/**
	 * 
	 */
	public void setAllDaysTotalProductionHours() {
		MUNSSlotType slotType = ((MUNSResource) getUNS_Resource()).getSlotType();
		for (int i = 1; i <= 7; i++)
		{
			set_Value("Day" + i + "ProductionHours", slotType.getWorkTime(i));
		}
	}



	/**
	 * 
	 * @return
	 */
	public I_UNS_Resource getUNS_Resource() {
		return (I_UNS_Resource) MTable.get(getCtx(), I_UNS_Resource.Table_Name).getPO(getUNS_Resource_ID(),
				get_TrxName());
	}

	/**
	 * 
	 * @param output
	 * @param input
	 * @param outputQty
	 * @return Qty OF BOM
	 */
	public static BigDecimal getBOMQty(int output, int input, String trxName) {
		/*
		 * BigDecimal qtyTmp = BigDecimal.ZERO; MProductBOM[] BOMS =
		 * MProductBOM.getBOMLines(Env.getCtx(), output, trxName); for (MProductBOM BOM : BOMS) { if
		 * (BOM.getM_ProductBOM().getM_Product_ID() == input) { //qtyTmp =
		 * qty.multiply(BOM.getBOMQty()); qtyTmp = BOM.getBOMQty(); return qtyTmp; } else { output =
		 * BOM.getM_ProductBOM().getM_Product_ID(); //qtyTmp = qty.multiply(BOM.getBOMQty()); //
		 * continue recursive if only the output is not KBA or KBB, since they // are recursively
		 * dependent. if (output != m_KBA_ID && output != m_KBB_ID) qtyTmp = getBOMQty(output,
		 * input, trxName); BigDecimal qtyBOM = BOM.getBOMQty(); if
		 * (qtyTmp.compareTo(BigDecimal.ZERO) > 0 ) return qtyTmp.multiply(qtyBOM); } } return
		 * qtyTmp;
		 */
		// return getBOMQty(-1, output, input, trxName);
		BigDecimal bomQty = DB.getSQLValueBD(trxName, "SELECT GetBOMQty(-1,?,?)", output, input);
		return bomQty;
	}

	/**
	 * 
	 * @param output
	 * @param input
	 * @param outputQty
	 * @return Qty OF BOM
	 */
	public static BigDecimal getNonOptionalBOMQty(int output, int input, String trxName) {
		BigDecimal bomQty =
				DB.getSQLValueBD(trxName, "SELECT GetNonOptionalBOMQty(?,?,?,?)", -1, output, input);
		return bomQty;
	}

	/**
	 * 
	 * @param prevOutput
	 * @param output
	 * @param input
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getBOMQty(int prevOutput, int output, int input, String trxName) {
		BigDecimal qtyTmp = BigDecimal.ZERO;
		MProductBOM[] BOMS = MProductBOM.getBOMLines(Env.getCtx(), output, trxName);
		for (MProductBOM BOM : BOMS)
		{
			if (BOM.getM_ProductBOM_ID() == input)
			{
				// qtyTmp = qty.multiply(BOM.getBOMQty());
				qtyTmp = BOM.getBOMQty();
				return qtyTmp;
			}
			else
			{
				// Jika prevOuput == BOM, maka langsung keluar dg nol krn akan terjadi infinite
				// loop.
				if (prevOutput == BOM.getM_ProductBOM_ID())
					return qtyTmp;

				prevOutput = output;
				output = BOM.getM_ProductBOM().getM_Product_ID();
				// qtyTmp = qty.multiply(BOM.getBOMQty());
				// continue recursive if only the output is not KBA or KBB, since they
				// are recursively dependent.
				// if (output != m_KBA_ID && output != m_KBB_ID)
				qtyTmp = getBOMQty(output, input, trxName);
				BigDecimal qtyBOM = BOM.getBOMQty();
				if (qtyTmp.compareTo(BigDecimal.ZERO) > 0)
					return qtyTmp.multiply(qtyBOM);
			}
		}
		return qtyTmp;
	}

	/**
	 * 
	 * @param input
	 * @return Qty OF Explosive BOM
	 */
	public BigDecimal getBOMQty(int input, BigDecimal outputQty, String trxName) {
		return getBOMQty(getM_Product_ID(), input, trxName).multiply(outputQty);
	}

	/**
	 * public BigDecimal getInputRequirement() { return getInputRequirement((MUNSResource)
	 * getUNS_Resource()); }
	 */

	/**
	 * 
	 * @param cal
	 * @param dayProductionHours
	 * @param trxName
	 * @return
	 */
	public static double getTotalProductionHoursInMonthOf(Calendar cal, double[] dayProductionHours,
			int Org_ID, String trxName) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(cal.getTimeInMillis());
		double total = 0.0;
		for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			calendar.set(Calendar.DATE, i);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

			if (MNonBusinessDay.isNonBusinessDay(Env.getCtx(), new Timestamp(calendar.getTimeInMillis()),					
					Org_ID, trxName))
				continue;
			total = total + dayProductionHours[dayOfWeek - 1];
		}
		return total;
	}

	/**
	 * 
	 * @param cal
	 * @param allProductPH
	 * @param trxName
	 * @return
	 */
	public static double getTotalAllOutputProductionHoursInMonth(Calendar cal,
			Hashtable<MUNSResourceInOut, double[]> allProductPH, String trxName) {
		double totalPH = 0.0;
		int multiCount = 0;
		int optionalCount = 0;
		for (MUNSResourceInOut output : allProductPH.keySet())
		{
			if (multiCount > 0 && output.getOutputType().equals(MUNSResourceInOut.OUTPUTTYPE_Multi))
				continue;
			if (optionalCount > 0 && output.getOutputType().equals(MUNSResourceInOut.OUTPUTTYPE_Optional))
				continue;

			// for (double[] productionHours : allProductPH.values())
			// {
			totalPH += getTotalProductionHoursInMonthOf(cal, allProductPH.get(output), output.getAD_Org_ID(), trxName);
			// }
			if (output.getOutputType().equals(MUNSResourceInOut.OUTPUTTYPE_Multi))
				multiCount++;
			if (output.getOutputType().equals(MUNSResourceInOut.OUTPUTTYPE_Optional))
				optionalCount++;
		}
		return totalPH;
	}


	/**
	 * 
	 * @param cals
	 * @return
	 */
	public BigDecimal getTotalProductProductionHoursInYear(Calendar cal, int Org_ID) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(cal.getTimeInMillis());
		BigDecimal total = BigDecimal.ZERO;
		for (int i = 0; i < 12; i++)
		{
			calendar.set(Calendar.MONTH, i);
			total = total.add(getTotalProductionHoursInMonth(calendar, Org_ID));
		}

		return total;
	}

	/**
	 * 
	 * @param cal
	 * @return
	 */
	public BigDecimal getTotalProductionHoursInMonth(Calendar cal, int Org_ID) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(cal.getTimeInMillis());
		BigDecimal total = BigDecimal.ZERO;
		for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			calendar.set(Calendar.DATE, i);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

			if (MNonBusinessDay.isNonBusinessDay(getCtx(), new Timestamp(calendar.getTimeInMillis()),
					Org_ID, get_TrxName()))
				continue;
			total = total.add(getDayProductionHours(dayOfWeek));
		}
		return total;
	}

	/**
	 * 
	 * @param cal
	 * @param qtyPerHour
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getTotalProductionHoursInMonth(Calendar cal, BigDecimal qtyPerHour,
			int Org_ID, String trxName) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(cal.getTimeInMillis());
		BigDecimal total = BigDecimal.ZERO;
		for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			calendar.set(Calendar.DATE, i);

			if (MNonBusinessDay.isNonBusinessDay(Env.getCtx(), new Timestamp(calendar.getTimeInMillis()),
					Org_ID, trxName))
				continue;
			total = total.add(qtyPerHour);
		}
		return total;
	}

	/**
	 * Total production hours in a week without considering no-work day (defined in the Calendar,
	 * Year & Period).
	 * 
	 * @return
	 */
	public BigDecimal getTotalWeeklyProductionHours() {
		BigDecimal totalHours =
				getDay1ProductionHours().add(getDay2ProductionHours()).add(getDay3ProductionHours())
						.add(getDay4ProductionHours()).add(getDay5ProductionHours())
						.add(getDay6ProductionHours()).add(getDay7ProductionHours());

		return totalHours;
	}

	/**
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public BigDecimal getDayProductionHours(int dayOfWeek) {
		BigDecimal total = BigDecimal.ZERO;
		switch (dayOfWeek)
		{
			case 1:
				total = getDay1ProductionHours();
				break;
			case 2:
				total = getDay2ProductionHours();
				break;
			case 3:
				total = getDay3ProductionHours();
				break;
			case 4:
				total = getDay4ProductionHours();
				break;
			case 5:
				total = getDay5ProductionHours();
				break;
			case 6:
				total = getDay6ProductionHours();
				break;
			case 7:
				total = getDay7ProductionHours();
				break;
		}
		return total;
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (isSingle() || isOptional())
			setIsPrimary(true);
		
		if (newRecord)
		{
			setResourceName(getParent().getName());
			
			if (getUNS_OutputLink_ID() > 0)
			{
				StringBuilder sql = new StringBuilder("SELECT p.Value FROM M_Product p ")
						.append("WHERE p.M_Product_ID=(SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio")
						.append(" WHERE rio.UNS_Resource_InOut_ID=?)");
				
				String outputLinkName =
						DB.getSQLValueStringEx(get_TrxName(), sql.toString(), getUNS_OutputLink_ID());
				setOutputLinkName(outputLinkName);
			}
			
			int count = 0;
			count = DB.getSQLValue(get_TrxName(), 
					"SELECT COUNT(*) FROM " + Table_Name + " WHERE UNS_Resource_ID=" + getUNS_Resource_ID());
			
			setLine((count+1) * 10);
		}
		
		if (newRecord || is_ValueChanged(COLUMNNAME_M_Product_ID))
		{
			MProduct product = MProduct.get(getCtx(), getM_Product_ID());
			setValue(product.getValue());
		}
		
		return true;
	}

	boolean m_isSynchronizeOutput = false;

	/**
	 * 
	 * @param isSync
	 */
	public void setIsNeedToSyncOutput(boolean isSync) {
		m_isSynchronizeOutput = isSync;
	}

	@Override
	protected boolean afterSave(boolean success, boolean newRecord) {
		if (m_isSynchronizeOutput)
			return true;

		// if (getInOutType().equals(INOUTTYPE_Input))
		// return true;
		// if (OUTPUTTYPE_Multi.equals(getOutputType()))
		// return synchronizeMultiMaxCaps(this);
		// //else if (OUTPUTTYPE_Optional.equals(getOutputType()))
		// // return synchronizeOptionalMaxCaps(null, this);
		// else if (OUTPUTTYPE_Single.equals(getOutputType()))
		// return validateSingleMaxCaps(this);
		// else if (!INOUTTYPE_Input.equals(getInOutType()))
		// throw new AdempiereUserError ("Sorry, we have not implement the output type of [" +
		// getOutputType() + "] yet.");

		return true;
	}

	/**
	 * If the given baseOutput is primary then set the other output's capacities with this base max
	 * caps. Otherwise, then set the given baseOutput's max caps settings with the primary's output
	 * max caps.
	 * 
	 * @param baseOutput
	 * @return
	 */
	public static boolean synchronizeMultiMaxCaps(MUNSResourceInOut baseOutput) {
		MUNSResource rsc = baseOutput.getParent();
		if (!rsc.isWorker() && !rsc.isMachine())
			return true;

		MUNSResourceInOut primaryOutput = null;
		MUNSResourceInOut[] outputLines = rsc.getOutputLines();

		if (!baseOutput.isPrimary() && baseOutput.is_new())
		{
			for (MUNSResourceInOut output : outputLines)
				if (output.isPrimary())
					primaryOutput = output;
			

			if (null == primaryOutput)
			{
				String msg = Msg.getMsg(baseOutput.getCtx(), "MultiOutputSetPrimaryMsg");
				String title = Msg.getMsg(baseOutput.getCtx(), "MultiOutputSetPrimaryTitle");
				int setAsPrimary =
						MessageBox
								.showMsg(baseOutput, 
										baseOutput.getCtx(),
										msg,
										title, MessageBox.YESNOCANCEL,
										MessageBox.ICONQUESTION);
				if (MessageBox.RETURN_YES == setAsPrimary)
					primaryOutput = baseOutput;
				else if (MessageBox.RETURN_CANCEL == setAsPrimary)
					return false;
				else
				// (null == primaryOutput)
				{ // it means user decided not to set a primary output yet. Then just set the actual
					// max caps with it's max caps.
					baseOutput.setActualMaxCaps(baseOutput.getMaxCaps());
					baseOutput.setActualMaxCapsMT(baseOutput.getMaxCapsMT());
					return true;
				}
			}
		}
		else if (baseOutput.isPrimary())
		{
			// in this position the isPrimary flag is set to true. then check if it is a proper
			// selection.
			if (X_UNS_Resource_InOut.OUTPUTTYPE_Optional.equals(baseOutput.getOutputType()))
				throw new AdempiereUserError("You can only set optional output as non-primary.");

			// check if there's already an output set as primary.
			for (MUNSResourceInOut output : outputLines)
			{
				if (output.getM_Product_ID() == baseOutput.getM_Product_ID())
					continue;
				if (output.isPrimary())
					throw new AdempiereUserError("You already set product ["
							+ output.getM_Product().getValue() + "] as primary.",
							"Please change the product as non-primary first.");
			}
			primaryOutput = baseOutput;
			// Make sure this Actual Max Caps is set the same value with the primaryOutput's Max
			// Caps value.
			baseOutput.setActualMaxCaps(baseOutput.getMaxCaps());
			baseOutput.setActualMaxCapsMT(baseOutput.getMaxCapsMT());
		}
		else
			return true;

		// Get the primaryOutput's BOM Qty.
		BigDecimal bomQty = getMainPartBOMQtyOf(primaryOutput);
		if (bomQty.compareTo(BigDecimal.ZERO) == 0)
			throw new AdempiereUserError("You have not defined any Main Part of a BOM for product:'"
					+ primaryOutput.getM_Product().getValue() + "'");

		// Get the actual input based on the primaryOutput.
		BigDecimal realInputMax = primaryOutput.getMaxCaps().multiply(bomQty);
		BigDecimal realInputMaxMT =
				primaryOutput.getMaxCapsMT().multiply(bomQty)
						.multiply(primaryOutput.getM_Product().getWeight());
		BigDecimal realInputOptimum = primaryOutput.getOptimumCaps().multiply(bomQty);
		BigDecimal realInputOptimumMT =
				primaryOutput.getOptimumCapsMT().multiply(bomQty)
						.multiply(primaryOutput.getM_Product().getWeight());
		BigDecimal realActualInput = primaryOutput.getActualMaxCaps().multiply(bomQty);
		BigDecimal realActualInputMT =
				primaryOutput.getActualMaxCapsMT().multiply(bomQty)
						.multiply(primaryOutput.getM_Product().getWeight());

		if (primaryOutput.getM_Product_ID() != baseOutput.getM_Product_ID())
		{
			// Yang menjadi primary bukanlah baseOutput, jadi set baseOutput's max caps dg
			// primaryOutput's max caps.
			bomQty = getMainPartBOMQtyOf(baseOutput);
			if (bomQty.compareTo(BigDecimal.ZERO) == 0)
				throw new AdempiereUserError("You have not defined any Main Part of a BOM for product:'"
						+ baseOutput.getM_Product().getValue() + "'");

			baseOutput.setMaxCaps(realInputMax.divide(bomQty, 4, RoundingMode.HALF_UP));
			baseOutput.setMaxCapsMT(realInputMaxMT.divide(bomQty, 4, RoundingMode.HALF_UP).multiply(
					baseOutput.getM_Product().getWeight()));
			baseOutput.setOptimumCaps(realInputOptimum.divide(bomQty, 4, RoundingMode.HALF_UP));
			baseOutput.setOptimumCapsMT(realInputOptimumMT.divide(bomQty, 4, RoundingMode.HALF_UP).multiply(
					baseOutput.getM_Product().getWeight()));
			baseOutput.setActualMaxCaps(realActualInput.divide(bomQty, 4, RoundingMode.HALF_UP));
			baseOutput.setActualMaxCapsMT(realActualInputMT.divide(bomQty, 4, RoundingMode.HALF_UP).multiply(
					baseOutput.getM_Product().getWeight()));
		}
		// record baru dan bukan primary, cukup sampai setting capacity nya dengan yang primary
		// saja.
		if (!baseOutput.isPrimary() && baseOutput.is_new())
			return true;

		// Set all the other output's max caps with the primaryOutput's max caps.
		for (MUNSResourceInOut output : outputLines)
		{
			if (output.getM_Product_ID() == primaryOutput.getM_Product_ID())
				continue;
			if (output.get_ID() == baseOutput.get_ID())
				continue;

			bomQty = getMainPartBOMQtyOf(output);

			if (bomQty.compareTo(BigDecimal.ZERO) == 0)
				throw new AdempiereUserError("You have not defined any Main Part of a BOM for product:'"
						+ baseOutput.getM_Product().getValue() + "'");

			output.setMaxCaps(realInputMax.divide(bomQty, 4, RoundingMode.HALF_UP));
			output.setMaxCapsMT(realInputMaxMT.divide(bomQty, 4, RoundingMode.HALF_UP).multiply(
					output.getM_Product().getWeight()));
			output.setOptimumCaps(realInputOptimum.divide(bomQty, 4, RoundingMode.HALF_UP));
			output.setOptimumCapsMT(realInputOptimumMT.divide(bomQty, 4, RoundingMode.HALF_UP).multiply(
					output.getM_Product().getWeight()));
			output.setActualMaxCaps(realActualInput.divide(bomQty, 4, RoundingMode.HALF_UP));
			output.setActualMaxCapsMT(realActualInputMT.divide(bomQty, 4, RoundingMode.HALF_UP).multiply(
					output.getM_Product().getWeight()));
			output.save();
		}
		return true;
	}

	/**
	 * You call this method to synchronize all other outputs with the given base-output Max Caps.
	 * 
	 * @param baseOutput
	 * @return true if no error occurs.
	 */
	public static boolean synchronizeOptionalMaxCaps(MUNSResource rsc, MUNSResourceInOut theSaveRIO) {
		if (rsc == null && theSaveRIO != null)
			rsc = (MUNSResource) theSaveRIO.getUNS_Resource();

		if (!rsc.isWorker() && !rsc.isMachine())
			return true;
		MUNSResourceInOut[] outputLines = rsc.getOutputLines();
		if (outputLines == null || outputLines.length <= 0)
			return true;
		// Get all different productBOMIDs of this resource's outputs.
		/*
		 * String sql = "SELECT DISTINCT bom.M_ProductBOM_ID FROM M_Product_BOM bom " +
		 * "INNER JOIN M_Product pbom ON bom.M_ProductBOM_ID=pbom.M_Product_ID " +
		 * "INNER JOIN M_PartType partType ON partType.M_PartType_ID=pbom.M_PartType_ID " +
		 * "WHERE bom.IsActive='Y' AND bom.M_Product_ID IN " +
		 * "	(SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio " +
		 * "	 WHERE rio.IsActive='Y' AND rio.UNS_Resource_ID=? AND rio.InOutType='O') " +
		 * " AND partType.name=?";
		 */
		String sql =
				"SELECT DISTINCT bom.M_ProductBOM_ID FROM M_Product_BOM bom "
						+ "WHERE bom.IsActive='Y' AND bom.M_Product_ID IN "
						+ "	(SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio "
						+ "	 WHERE rio.IsActive='Y' AND rio.UNS_Resource_ID=? AND rio.InOutType='O') "
						+ " AND bom.M_PartType_ID=(SELECT parttype.M_PartType_ID FROM M_PartType parttype "
						+ "						 WHERE parttype.name=?)";

		int[] listOfBaseInput = DB.getIDsEx(rsc.get_TrxName(), sql, rsc.get_ID(), "Main Part");

		if (listOfBaseInput == null || listOfBaseInput.length == 0)
		{
			throw new AdempiereUserError(
					"You have not defined any Main Part of a BOM for ResourceInOut of: '" + rsc.getName()
							+ "'");
		}
		for (int baseInputID : listOfBaseInput)
		{
			Hashtable<Integer, BigDecimal> rioBOMMap = new Hashtable<Integer, BigDecimal>();
			BigDecimal maxInputQty = Env.ZERO;
			BigDecimal optimumInputQty = Env.ZERO;

			for (MUNSResourceInOut rio : outputLines)
			{
				sql =
						"SELECT BOMQty FROM M_Product_BOM WHERE IsActive='Y' AND M_Product_ID=? AND M_ProductBOM_ID=?";
				BigDecimal rioBOMQty =
						DB.getSQLValueBD(rsc.get_TrxName(), sql, rio.getM_Product_ID(), baseInputID);
				if (null == rioBOMQty)
					continue; // it means the InOut is not the same base input.
				rioBOMMap.put(rio.get_ID(), rioBOMQty); // store the BOMQty to be used later.

				BigDecimal rioMaxInputQty = rioBOMQty.multiply(rio.getMaxCaps());
				if (rioMaxInputQty.compareTo(maxInputQty) > 0)
					maxInputQty = rioMaxInputQty;

				BigDecimal rioOptimumInputQty = rioBOMQty.multiply(rio.getOptimumCaps());
				if (rioOptimumInputQty.compareTo(optimumInputQty) > 0)
					optimumInputQty = rioOptimumInputQty;
			}

			// Re-loop the output lines to set all caps based on the maxInputQty.
			for (MUNSResourceInOut rio : outputLines)
			{
				BigDecimal rioBOMQty = rioBOMMap.get(rio.get_ID());
				if (rioBOMQty == null)
					continue; // If not exist, then just continue the loop.

				if (theSaveRIO != null && rio.getM_Product_ID() == theSaveRIO.getM_Product_ID())
					rio = theSaveRIO;
				rio.m_isSynchronizeOutput = true;
				rio.setMaxCaps(maxInputQty.divide(rioBOMQty, 4, BigDecimal.ROUND_HALF_UP));
				rio.setMaxCapsMT(rio.getMaxCaps().multiply(rio.getM_Product().getWeight())
						.multiply(new BigDecimal(0.001)));
				rio.setOptimumCaps(optimumInputQty.divide(rioBOMQty, 4, BigDecimal.ROUND_HALF_UP));
				rio.setOptimumCapsMT(rio.getOptimumCaps().multiply(rio.getM_Product().getWeight())
						.multiply(new BigDecimal(0.001)));
				rio.setActualMaxCaps(rio.getMaxCaps());
				rio.setActualMaxCapsMT(rio.getMaxCapsMT());
				if (!rio.saveUpdate())
					throw new AdempiereException("Cannot update resource's output capacities");
			}
		}
		return true;
	}

	/**
	 * You call this method to synchronize all other outputs with the given base-output Max Caps.
	 * 
	 * @param baseOutput
	 * @return true if no error occurs.
	 */
	public static boolean synchronizeOptionalMaxCaps_OLD(MUNSResourceInOut baseOutput) {
		MUNSResource rsc = baseOutput.getParent();
		if (!rsc.isWorker() && !rsc.isMachine())
			return true;

		MProductBOM[] boms = MProductBOM.getBOMLines((MProduct) baseOutput.getM_Product());
		BigDecimal bomQty = BigDecimal.ZERO;
		int baseInputID = 0;

		for (MProductBOM bom : boms)
		{
			if (bom.getM_PartType_ID() == 0)
				continue;

			I_M_PartType partType = bom.getM_PartType();

			if (null != partType && "Main Part".equals(partType.getName()))
			{
				bomQty = bom.getBOMQty();
				baseInputID = bom.getM_ProductBOM_ID();
				break;
			}
		}
		if (bomQty.compareTo(BigDecimal.ZERO) == 0)
			throw new AdempiereUserError("You have not defined any Main Part of a BOM for product:'"
					+ baseOutput.getM_Product().getValue() + "'");

		// Make sure this Actual Max Caps is set the same value with it's Max Caps value.
		baseOutput.setActualMaxCaps(baseOutput.getMaxCaps());
		baseOutput.setActualMaxCapsMT(baseOutput.getMaxCapsMT());

		BigDecimal realInputMax = baseOutput.getMaxCaps().multiply(bomQty);
		BigDecimal realInputMaxMT =
				baseOutput.getMaxCapsMT().multiply(bomQty).multiply(baseOutput.getM_Product().getWeight());
		BigDecimal realInputOptimum = baseOutput.getOptimumCaps().multiply(bomQty);
		BigDecimal realInputOptimumMT =
				baseOutput.getOptimumCapsMT().multiply(bomQty)
						.multiply(baseOutput.getM_Product().getWeight());
		BigDecimal realActualInput = baseOutput.getActualMaxCaps().multiply(bomQty);
		BigDecimal realActualInputMT =
				baseOutput.getActualMaxCapsMT().multiply(bomQty)
						.multiply(baseOutput.getM_Product().getWeight());

		MUNSResourceInOut[] outputLines = rsc.getOutputLines();

		for (MUNSResourceInOut output : outputLines)
		{
			if (output.getM_Product_ID() == baseOutput.getM_Product_ID())
				continue;

			boms = MProductBOM.getBOMLines((MProduct) output.getM_Product());
			bomQty = BigDecimal.ZERO;
			int inputOfCurrentOutputID = 0;
			for (MProductBOM bom : boms)
			{
				if (bom.getM_ProductBOM().getM_PartType_ID() == 0)
					continue;
				// I_M_PartType partType = bom.getM_ProductBOM().getM_PartType();
				I_M_PartType partType = bom.getM_PartType();

				if (null != partType && partType.getName().equals("Main Part"))
				{
					if (bom.getM_ProductBOM_ID() == baseInputID)
					{
						bomQty = bom.getBOMQty();
						inputOfCurrentOutputID = bom.getM_ProductBOM_ID();
						break;
					}
				}
			}
			if (inputOfCurrentOutputID != baseInputID)
				continue;

			if (bomQty.compareTo(BigDecimal.ZERO) == 0)
				throw new AdempiereUserError("You have not defined any Main Part of a BOM for product:'"
						+ baseOutput.getM_Product().getValue() + "'");

			output.setMaxCaps(realInputMax.divide(bomQty, 4, RoundingMode.HALF_UP));
			output.setMaxCapsMT(realInputMaxMT.divide(bomQty, 4, RoundingMode.HALF_UP).multiply(
					output.getM_Product().getWeight()));
			output.setOptimumCaps(realInputOptimum.divide(bomQty, 4, RoundingMode.HALF_UP));
			output.setOptimumCapsMT(realInputOptimumMT.divide(bomQty, 4, RoundingMode.HALF_UP).multiply(
					output.getM_Product().getWeight()));
			output.setActualMaxCaps(realActualInput.divide(bomQty, 4, RoundingMode.HALF_UP));
			output.setActualMaxCapsMT(realActualInputMT.divide(bomQty, 4, RoundingMode.HALF_UP).multiply(
					output.getM_Product().getWeight()));
			output.saveUpdate();
		}
		return true;
	}

	/**
	 * Call this method to validate the output Max Caps settings.
	 * 
	 * @param output
	 * @return true if no error occurs.
	 */
	public static boolean validateSingleMaxCaps(MUNSResourceInOut output) {
		MUNSResource rsc = output.getParent();
		if (!rsc.isWorker() && !rsc.isMachine())
			return true;

		// Make sure this Actual Max Caps is set the same value with it's Max Caps value.
		output.setActualMaxCaps(output.getMaxCaps());
		output.setActualMaxCapsMT(output.getMaxCapsMT());

		return true;
	}

	/**
	 * Compare this Actual Max Caps with the otherOutput's Actual Max Caps.
	 * 
	 * @return -1, 0, or 1 as this Actual Max Caps less, equals, or greater than the otherOutput's
	 *         Actual Max Caps.
	 */
	public int compareTo(MUNSResourceInOut otherOutput) {
		if (getActualMaxCaps().compareTo(otherOutput.getActualMaxCaps()) < 0)
			return -1;
		if (getActualMaxCaps().compareTo(otherOutput.getActualMaxCaps()) > 0)
			return 1;
		else
			return 0;
	}

	/**
	 * 
	 * @param inout
	 * @return
	 */
	public static BigDecimal getMainPartBOMQtyOf(MUNSResourceInOut inout) {
		MProductBOM[] boms = MProductBOM.getBOMLines((MProduct) inout.getM_Product());
		BigDecimal bomQty = BigDecimal.ZERO;
		for (MProductBOM bom : boms)
		{
			if (bom.getM_ProductBOM().getM_PartType_ID() == 0)
				continue;
			// I_M_PartType partType = bom.getM_ProductBOM().getM_PartType();
			I_M_PartType partType = bom.getM_PartType();

			if (null != partType && partType.getM_PartType_ID() > 0 && partType.getName().equals("Main Part"))
			{
				bomQty = bom.getBOMQty();
				break;
			}
		}
		return bomQty;
	}

	/**
	 * 
	 * @param inout
	 * @return
	 */
	public static BigDecimal getMainPartBOMQtyOf(MProduct product, String bomType) {
		//MProductBOM[] boms = MProductBOM.getBOMLines(product);
		//BigDecimal bomQty = BigDecimal.ZERO;
		
		/*
		for (MProductBOM bom : boms)
		{
			if (bom.getM_ProductBOM().getM_PartType_ID() == 0)
				continue;
			// I_M_PartType partType = bom.getM_ProductBOM().getM_PartType();
			I_M_PartType partType = bom.getM_PartType();

			if (null != partType && partType.getName().equals("Main Part")
					&& bom.getBOMType().equals(bomType))
			{
				bomQty = bom.getBOMQty();
				break;
			}
		}
		*/
		
		StringBuilder sql = 
				new StringBuilder ("SELECT BOMQty FROM M_Product_BOM WHERE M_Product_ID=? AND BOMType=?")
				.append("AND M_PartType_ID=(SELECT M_PartType_ID FROM M_PartType WHERE UPPER(Name)=?)");
		BigDecimal bomQty = 
				DB.getSQLValueBDEx(product.get_TrxName(), sql.toString(), product.get_ID(), bomType, "MAIN PART");
		
		if (bomQty == null)
			bomQty = Env.ZERO;

		return bomQty;
	}

	/**
	 * To check if a product as a resource is need to be checked by QA department.
	 * 
	 * @param ctx
	 * @param prod
	 * @param M_Product_ID
	 * @return
	 */
	public static boolean checkQAMonitoring(Properties ctx, MUNSProduction prod, int M_Product_ID) {
		MUNSResource rsc = MUNSProduction.getResource(ctx, prod);
		MUNSResourceInOut rio = rsc.getResourceOutput(M_Product_ID);
		return rio.isQAMonitoring();
	}

	/**
	 * To check if the product of this output is consider as primary output. If it is one of product
	 * of a multi output, then simply return the flag stated by isPrimary method of this super
	 * class. If it is an optional output then check if it's main part of BOM is a primary output.
	 * 
	 * @return
	 */
	public boolean isPrimaryOutput() {
		if (OUTPUTTYPE_Multi.equals(getOutputType()))
			return isPrimary();

		boolean isPrimary = isPrimary();

		Integer[] mainParts = getMainPartBOMs(getM_Product_ID());

		if (null == mainParts)
			return isPrimary;

		for (int mainPartBOM : mainParts)
		{
			MUNSResourceInOut rio =
					Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSResourceInOut.Table_Name,
							"M_Product_ID=? AND InOutType=?", get_TrxName())
							.setParameters(mainPartBOM, INOUTTYPE_Output).setOnlyActiveRecords(true).first();
			if (rio == null)
				continue;
			if (OUTPUTTYPE_Multi.equals(rio.getOutputType()))
				return rio.isPrimary();
			else
				isPrimary = rio.isPrimaryOutput();
			if (isPrimary)
				break;
		}

		/*
		 * Vector<MUNSResource> prevRscList =
		 * ((MUNSResource)getUNS_Resource()).getPrevResource(false);
		 * 
		 * if (prevRscList.size() == 0) return isPrimary;
		 * 
		 * for (MUNSResource prevRsc : prevRscList) { MUNSResourceInOut[] rscOuts =
		 * prevRsc.getOutputLines(); for (MUNSResourceInOut rio : rscOuts) { BigDecimal BOMQty =
		 * getBOMQty(getM_Product_ID(), rio.getM_Product_ID(), get_TrxName()); if
		 * (BOMQty.compareTo(Env.ZERO) <= 0) continue;
		 * 
		 * if (OUTPUTTYPE_Multi.equals(rio.getOutputType())) return rio.isPrimary(); else isPrimary
		 * = rio.isPrimaryOutput(); } }
		 */

		// MUNSResourceInOut[] BOMInOuts = getBOMResourceInOut();
		//
		// for (MUNSResourceInOut BOMInOut : BOMInOuts)
		// {
		// if (OUTPUTTYPE_Multi.equals(BOMInOut.getOutputType()))
		// isPrimary = BOMInOut.isPrimary();
		// else
		// isPrimary = BOMInOut.isPrimaryOutput();
		//
		// if (isPrimary)
		// break;
		// //else if (OUTPUTTYPE_Multi.equals(BOMInOut.getOutputType()))
		// // isPrimary = BOMInOut.isPrimary();
		// }
		//
		return isPrimary;
	}

	/**
	 * 
	 * @param m_product_id
	 * @return
	 */
	public Integer[] getMainPartBOMs(int m_product_id) {
		Integer[] mainParts;

		/*
		 * String sql =
		 * "SELECT bom.M_ProductBOM_ID FROM M_Product_BOM bom, M_Product p, M_PartType pt " +
		 * "WHERE bom.IsActive='Y' AND bom.M_ProductBOM_ID=p.M_Product_ID AND p.M_PartType_ID=pt.M_PartType_ID "
		 * + "  AND pt.Name='Main Part' AND bom.M_Product_ID=? AND bom.BomType=?";
		 */
		String sql =
				"SELECT bom.M_ProductBOM_ID FROM M_Product_BOM bom, M_PartType pt "
						+ "WHERE bom.IsActive='Y' AND bom.M_PartType_ID=pt.M_PartType_ID "
						+ "  AND pt.Name='Main Part' AND bom.M_Product_ID=? AND bom.BomType=?";

		mainParts =
				DBUtils.getIntValues(get_TrxName(), sql, getM_Product_ID(), MProductBOM.BOMTYPE_StandardPart);

		return mainParts;
	}

	/**
	 * Get this output's product BOM listed as the previous UNS_Resource_InOut.
	 * 
	 * @return The output's product BOM listed as the previous UNS_Resource_InOut.
	 */
	public MUNSResourceInOut[] getBOMResourceInOut() {
		MUNSResourceInOut[] BOMInOuts = null;

		String whereClause =
				"M_Product_ID IN (SELECT bom.M_ProductBOM_ID FROM M_Product_BOM bom "
						+ " 		WHERE bom.IsActive='Y' AND bom.M_Product_ID=?) "
						+ " AND InOutType=? AND UNS_Resource_ID IN (SELECT rsc.UNS_Resource_ID FROM UNS_Resource rsc "
						+ "		WHERE rsc.IsActive='Y' AND rsc.ResourceParent_ID=?)";

		List<MUNSResourceInOut> list =
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name, whereClause,
						get_TrxName()).setParameters(getM_Product_ID(), INOUTTYPE_Output, getM_Product_ID())
						.setOnlyActiveRecords(true).list();
		if (list.size() > 0)
		{
			BOMInOuts = new MUNSResourceInOut[list.size()];
			list.toArray(BOMInOuts);
		}
		else
		{
			whereClause =
					"M_Product_ID IN (SELECT bom.M_ProductBOM_ID FROM M_Product_BOM bom "
							+ "		WHERE bom.IsActive='Y' AND bom.M_Product_ID=?) "
							+ " AND InOutType=? AND "
							+ " UNS_Resource_ID IN (SELECT trans.NextResource_ID FROM UNS_Resource_Transition trans "
							+ "					  WHERE trans.IsActive='Y' AND trans.UNS_Resource_ID=?)";

			list =
					Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name, whereClause,
							get_TrxName())
							.setParameters(getM_Product_ID(), INOUTTYPE_Output, getM_Product_ID())
							.setOnlyActiveRecords(true).list();
		}

		return BOMInOuts;
	}

	/**
	 * 
	 */
	public String toString() {
		MProduct product = MProduct.get(getCtx(), getM_Product_ID());
		return super.toString() + "[" + product.toString() + "]";
	}

	public boolean isLinkedProduct(int M_Product_ID) {
		if (getUNS_OutputLink_ID() > 0)
		{
			if (getUNS_OutputLink().getM_Product_ID() == M_Product_ID)
				return true;
		}

		return false;
	}
	
	/**
	 * Get the Primary Output (Product) ID of this non-primary output.
	 * 
	 * @return
	 */
	public int getOutputLinkProductID()
	{
		if (this.isPrimary() || this.getOutputType().equals(OUTPUTTYPE_Single) 
				|| this.getOutputType().equals(OUTPUTTYPE_Optional))
			return -1;
		
		String sql = "SELECT M_Product_ID FROM UNS_Resource_InOut WHERE UNS_Resource_InOut_ID=" 
					+ getUNS_OutputLink_ID();
		
		int primaryOutputID = DB.getSQLValueEx(get_TrxName(), sql);
		
		return primaryOutputID;
	}
}
