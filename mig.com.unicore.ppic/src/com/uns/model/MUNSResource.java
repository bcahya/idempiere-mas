/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProductBOM;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.uns.model.MProduct;
import com.uns.util.MessageBox;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author AzHaidar
 * 
 */
public class MUNSResource extends X_UNS_Resource implements DocAction,
		DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -95398467042793722L;
	
	public static final String EXTENSION_ID = UNSPPICModelFactory.getExtensionID();

	private List<MUNSResourceWorkerLine> m_ListOfWorkerLines = new ArrayList<MUNSResourceWorkerLine>();
	
	/**
	 * Child of this resource.
	 */
	private MUNSResource[] m_lines = null;
	
	/**
	 * Output list of this resource.
	 */
	private MUNSResourceInOut[] m_outputLines = null;
	
	/**
	 * resource transition list of this resource
	 */
	private MUNSResourceTransition[] m_resourceTransition =null;

	/**
	 * resource transition list of this resource
	 */
	private MUNSResourceWorkerLine[] m_resourceWorker =null;
	
	/**
	 * Preveous resource From Reource
	 */
	private MUNSResourceTransition[] PrevResource = null;

	private MUNSResourceLocator[] m_resourceLocator = null;

	/**
	 * @param ctx
	 * @param UNS_Resource_ID
	 * @param trxName
	 */
	public MUNSResource(Properties ctx, int UNS_Resource_ID, String trxName) {
		super(ctx, UNS_Resource_ID, trxName);

	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSResource(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);

	}
	
	/**
	 * Before save.
	 */
	protected boolean beforeSave(boolean newRecord) {
		if (!isValidStartNode()) {
			String msg = Msg.getMsg(getCtx(), "NotSetBasedStartNodeMsg");
			String title = Msg.getMsg(getCtx(), "NotSetBasedStartNodeTitle");
			MessageBox.showMsg(this, getCtx(), msg, title, MessageBox.OK,
					MessageBox.ICONERROR);
			return false;
		}
		if (!isValidEndNode()) {
			String msg = Msg.getMsg(getCtx(), "NotSetBasedEndNodeMsg");
			String title = Msg.getMsg(getCtx(), "NotSetBasedEndNodeTitle");
			MessageBox.showMsg(this, getCtx(), msg, title, MessageBox.OK,
					MessageBox.ICONERROR);
			return false;
		}
		
		if("WO".equals(getResourceType())){
			String sql = "UPDATE UNS_Resource SET IsWorkerBase='Y' WHERE UNS_Resource_ID="
					+ getResourceParent_ID();
			int count = DB.executeUpdate(sql, get_TrxName());
			if (getResourceParent_ID() > 0)
				if (count<1)
					throw new AdempiereException("Cannot change worker base.");
			
			if (!newRecord && is_ValueChanged(COLUMNNAME_PayrollTerm)) {
				sql = "UPDATE UNS_Resource_WorkerLine SET PayrollTerm=?";
				DB.executeUpdateEx(sql, new Object[]{getPayrollTerm()}, get_TrxName());
			}
		}
		
		return super.beforeSave(newRecord);
	}
	
	/**
	 * Before delete.
	 */
	protected boolean beforeDelete() {
		if (RESOURCETYPE_5Worker.equals(getResourceType())){
			String sql = "SELECT COUNT(*) FROM UNS_Resource WHERE ResourceType='"
				+RESOURCETYPE_5Worker+"' AND ResourceParent_ID=" + getUNS_Resource_ID();
			int count = DB.getSQLValue(get_TrxName(), sql);
		
			if(count==0)
				setIsWorkerBase(false);
			else if (count<1)
				throw new AdempiereException("Cannot change worker base.");
		}
		return super.beforeDelete();
	}

	/**
	 * IsStartNode allowed for this resource. It is allowed to be start node if
	 * only this resource is not used as the transition node of a resource.
	 * 
	 * @return
	 */
	boolean isValidStartNode() {
		if (!isParentStartNode()) {
			return true;
		}
		String sql = "SELECT count(*) FROM UNS_Resource_Transition urt, UNS_Resource ur "
				+ "	WHERE 		urt.UNS_Resource_ID = ur.UNS_Resource_ID "
				+ "			AND urt.NextResource_ID="
				+ getUNS_Resource_ID()
				+ " 			AND ur.ResourceParent_ID=" + getResourceParent_ID();
		int countNext = DB.getSQLValue(get_TrxName(), sql);

		if (countNext > 0) {
			return false;
		}
		return true;
	}

	/**
	 * IsEndNode allowed for this resource. It is allowed to be start node if
	 * only this resource doesn't have any next node in the transition of it's
	 * parent.
	 * 
	 * @return
	 */
	boolean isValidEndNode() {
		if (!isParentEndNode()) {
			return true;
		}
		String sql = "SELECT count(*) FROM UNS_Resource_Transition "
				+ "	WHERE UNS_Resource_ID=" + getUNS_Resource_ID();
		int countTransition = DB.getSQLValue(get_TrxName(), sql);

		if (countTransition > 0) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.compiere.process.DocOptions#customizeValidActions(java.lang.String,
	 * java.lang.Object, java.lang.String, java.lang.String, int,
	 * java.lang.String[], java.lang.String[], int)
	 */
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#setDocStatus(java.lang.String)
	 */
	@Override
	public void setDocStatus(String newStatus) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getDocStatus()
	 */
	@Override
	public String getDocStatus() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getDocumentNo()
	 */
	@Override
	public String getDocumentNo() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getDocAction()
	 */
	@Override
	public String getDocAction() {

		return null;
	}
	
	/**
	 * Get childs of this resource.
	 * @param requery
	 * @return
	 */
	public MUNSResource[] getLines(boolean requery) 
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}

		List<MUNSResource> list = Query
				.get(getCtx(), MUNSResource.EXTENSION_ID,
						MUNSResource.Table_Name, "ResourceParent_ID=? ",
						get_TrxName())
				.setParameters(new Object[] { getUNS_Resource_ID() })
				.setOrderBy(MUNSResource.COLUMNNAME_UNS_Resource_ID)
				.setOnlyActiveRecords(true)
				.list();

		m_lines = new MUNSResource[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * Load current resource's child or get it from cache.
	 * @return
	 */
	public MUNSResource[] getLines()
	{
		return getLines(false);
	}
	
	public boolean hasChild()
	{
		return ((getLines() != null) && (m_lines.length > 0)); 
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public Vector<MUNSResource> getPrevResource(boolean requery)
	{
		Vector<MUNSResource> PrevResources = new Vector<MUNSResource>();
		if (PrevResource != null && !requery)
		{
			set_TrxName(PrevResource, get_TrxName());
			for (MUNSResourceTransition resourceTransition : PrevResource)
			{
				PrevResources.add(resourceTransition.getResource());
			}
			return PrevResources;
		}
		
		final String whereClause = X_UNS_Resource_Transition.COLUMNNAME_NextResource_ID + " =?";
		List<MUNSResourceTransition> list = Query.get(
				getCtx(), EXTENSION_ID, X_UNS_Resource_Transition.Table_Name, whereClause, get_TrxName())
				.setParameters(getUNS_Resource_ID())
				.setOrderBy(X_UNS_Resource_Transition.COLUMNNAME_UNS_Resource_Transition_ID)
				.setOnlyActiveRecords(true)
				.list();
		
		PrevResource = new MUNSResourceTransition[list.size()];
		list.toArray(PrevResource);
		
		for (MUNSResourceTransition resourceTransition : PrevResource)
		{
			PrevResources.add(resourceTransition.getResource());
		}
		
		return PrevResources;
	}
	

	/**
	 * Get this resource outputs. 
	 * @param requery
	 * @return
	 */
	public MUNSResourceInOut[] getOutputLines(boolean requery) 
	{
		if (m_outputLines != null && !requery) {
			set_TrxName(m_outputLines, get_TrxName());
			return m_outputLines;
		}

		String whereClauseFinal = "UNS_Resource_ID=? AND InOutType=? AND UNS_Resource_Transition_ID IS NULL";
		List<X_UNS_Resource_InOut> list = Query
				.get(getCtx(), MUNSResource.EXTENSION_ID,
						X_UNS_Resource_InOut.Table_Name, whereClauseFinal,
						get_TrxName())
				.setParameters(new Object[] { getUNS_Resource_ID(), MUNSResourceInOut.INOUTTYPE_Output })
				.setOrderBy(X_UNS_Resource_InOut.COLUMNNAME_Line)
				.setOnlyActiveRecords(true)
				.list();

		m_outputLines = new MUNSResourceInOut[list.size()];
		list.toArray(m_outputLines);

		return m_outputLines;
	}
	
	/**
	 * Get this resource outputs for the given Output_ID. If the resource's output type is Multi Optional,
	 * then it will also try return the non-primary output of the Output_ID.
	 * 
	 * @param Output_ID
	 * @return
	 */
	public MUNSResourceInOut[] getOutputLinesForProductOf(int Output_ID) 
	{
		if (Output_ID <=0 ) {
			return null;
		}

		String whereClauseFinal = "UNS_Resource_ID=? AND InOutType='O' AND UNS_Resource_Transition_ID IS NULL "
				+ " AND (M_Product_ID=? OR UNS_OutputLink_ID=("
				+ "		SELECT rio.UNS_Resource_InOut_ID FROM UNS_Resource_InOut rio "
				+ "		WHERE rio.UNS_Resource_ID=? AND rio.InOutType='O' AND rio.M_Product_ID=?))";
		
		List<X_UNS_Resource_InOut> list = Query
				.get(getCtx(), MUNSResource.EXTENSION_ID,
						X_UNS_Resource_InOut.Table_Name, whereClauseFinal,
						get_TrxName())
				.setParameters(get_ID(), Output_ID, get_ID(), Output_ID)
				.setOrderBy(X_UNS_Resource_InOut.COLUMNNAME_Line)
				.setOnlyActiveRecords(true)
				.list();

		m_outputLines = new MUNSResourceInOut[list.size()];
		list.toArray(m_outputLines);

		return m_outputLines;
	}
	
	/**
	 * Get resource inout yg product nya merupakan keluaran dari inputID, berdasarkan BOM yang
	 * telah didefinisikan.
	 * @param inputID
	 * @return
	 */
	public MUNSResourceInOut[] getOutputLinesForInputOf(int inputID) 
	{
		String whereClauseFinal = 
				"UNS_Resource_ID=? AND InOutType=? " +
				"AND (UNS_Resource_Transition_ID IS NULL OR UNS_Resource_Transition_ID=0) " +
				"AND M_Product_ID IN (SELECT bom.M_Product_ID FROM M_Product_BOM bom " +
				"		WHERE bom.M_ProductBOM_ID=?)";
		List<X_UNS_Resource_InOut> list = Query
				.get(getCtx(), MUNSResource.EXTENSION_ID,
						X_UNS_Resource_InOut.Table_Name, whereClauseFinal,
						get_TrxName())
				.setParameters(get_ID(), MUNSResourceInOut.INOUTTYPE_Output, inputID)
				.setOrderBy(X_UNS_Resource_InOut.COLUMNNAME_UNS_Resource_ID)
				.setOnlyActiveRecords(true)
				.list();

		m_outputLines = new MUNSResourceInOut[list.size()];
		list.toArray(m_outputLines);

		return m_outputLines;
	}
	
	public static Vector<MUNSResource> getVectorPrevResource(int nexResource, String trxName)
	{
		Vector<MUNSResource> VectorResource = new Vector<MUNSResource>();
		String sql = "SELECT * FROM UNS_Resource r INNER JOIN UNS_Resource_Transition t"
				+ " ON t.UNS_Resource_ID = r.UNS_Resource_ID AND t.NextResource_ID = ? "
				+ " AND t.IsActive='Y' AND r.IsActive='Y' ";
		try {
			PreparedStatement stm = DB.prepareStatement(sql, trxName);
			stm.setInt(1, nexResource);
			ResultSet rs = stm.executeQuery();
			while (rs.next())
			{
				MUNSResource resource = new MUNSResource(Env.getCtx(), rs, trxName);
				VectorResource.add(resource);
			}
			DB.close(rs, stm);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return VectorResource;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSResourceTransition[] getTransitionLines(boolean requery)
	{
		if (m_resourceTransition != null
				&& !requery)
		{
			set_TrxName(m_resourceTransition, get_TrxName());
			return m_resourceTransition;
		}
		
		String whereClauseFinal = COLUMNNAME_UNS_Resource_ID + "=?";
		
		List<X_UNS_Resource_Transition> list =
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						MUNSResourceTransition.Table_Name, whereClauseFinal, get_TrxName())
						.setParameters(getUNS_Resource_ID()).setOrderBy(
								MUNSResourceTransition.COLUMNNAME_UNS_Resource_Transition_ID)
						.setOnlyActiveRecords(true)
						.list();
		
		m_resourceTransition = new MUNSResourceTransition[list.size()];
		list.toArray(m_resourceTransition);
		
		return m_resourceTransition;
	}
	
	
	/**
	 * 
	 * @param requery
	 * @return
	 * 
	 */
	public MUNSResourceWorkerLine[] getWorkerLines(boolean requery)
	{
		if (m_resourceWorker != null
				&& !requery)
		{
			set_TrxName(m_resourceWorker, get_TrxName());
			return m_resourceWorker;
		}
		
		String whereClauseFinal = COLUMNNAME_UNS_Resource_ID + "=?";
		
		List<MUNSResourceWorkerLine> list =
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						MUNSResourceWorkerLine.Table_Name, whereClauseFinal, get_TrxName())
				.setParameters(getUNS_Resource_ID()).setOrderBy(
								MUNSResourceWorkerLine.COLUMNNAME_UNS_Resource_WorkerLine_ID)
				.setOnlyActiveRecords(true)
				.list();
		
		m_resourceWorker = new MUNSResourceWorkerLine[list.size()];
		list.toArray(m_resourceWorker);
		
		return m_resourceWorker;
	}
	
	/**
	 * Get this resource locator. 
	 * @return
	 */
	public MUNSResourceLocator[] getLocatorLines(boolean requery) 
	{
		if (m_resourceLocator != null
				&& !requery)
		{
			set_TrxName(m_resourceLocator, get_TrxName());
			return m_resourceLocator;
		}
		
		String whereClauseFinal = COLUMNNAME_UNS_Resource_ID + "=?";
		
		List<X_UNS_Resource_Locator> list =
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						MUNSResourceLocator.Table_Name, whereClauseFinal, get_TrxName())
				.setParameters(getUNS_Resource_ID()).setOrderBy(
								MUNSResourceLocator.COLUMNNAME_UNS_Resource_Locator_ID)
				.setOnlyActiveRecords(true)
				.list();
		
		m_resourceLocator = new MUNSResourceLocator[list.size()];
		list.toArray(m_resourceLocator);
		
		return m_resourceLocator;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSResourceWorkerLine[] getWorkerLines()
	{
		return getWorkerLines(false);
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSResourceTransition[] getTransitionLines()
	{
		return getTransitionLines(false);
	}
	
	/**
	 * Get this resource outputs. 
	 * @return
	 */
	public MUNSResourceInOut[] getOutputLines() 
	{
		return getOutputLines(false);
	}
	
	/**
	 * Get this resource outputs. 
	 * @return
	 */
	public MUNSResourceLocator[] getLocatorLines() 
	{
		return getLocatorLines(false);
	}
	
	public boolean isWorkStation() {
		return RESOURCETYPE_4WorkStation.equals(getResourceType());
	}
	
	public boolean isWorker() {
		return RESOURCETYPE_5Worker.equals(getResourceType());
	}
	
	public boolean isMachine() {
		return RESOURCETYPE_6Machine.equals(getResourceType());
	}
	

	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	public MUNSResourceInOut getInout(int M_Product_BOM_ID)
	{
		MUNSResourceInOut[] InOuts = getOutputLines();
		MUNSResourceInOut InOut = null;
		for (int i=0; i<InOuts.length; i++)
		{
			InOut = InOuts[i];
			Iterator<Integer> listProductBOM = InOut.getBOMSProduct().iterator();
			{
				Integer BOM = listProductBOM.next();
				if (M_Product_BOM_ID == BOM)
				{
					InOut.setM_Product_BOM_ID(BOM);
					break;
				}
			}
		}
		
		return InOut;
	}
	
	/**
	 * 
	 * @param UNS_Resource_ID
	 * @return
	 */
	public boolean isValidNextResource()
	{
		MUNSResourceTransition[] Ts = getTransitionLines();
		for (MUNSResourceTransition transition : Ts)
		{
			if (transition.getNextResource_ID() == getUNS_Resource_ID());
				return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public MUNSSlotType getSlotType()
	{
		MUNSSlotType SlotType = new MUNSSlotType(getCtx(), getUNS_SlotType_ID(), get_TrxName());
		
		return SlotType;
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @param M_Product_BOM_ID
	 * @param qty
	 * @return Qty OF BOM
	 *
	private static BigDecimal getBOMQty(int M_Product_ID, int M_Product_BOM_ID, BigDecimal qty, String trxName){
		BigDecimal qtyTmp = BigDecimal.ZERO;
		MProductBOM[] BOMS = MProductBOM.getBOMLines(Env.getCtx(), M_Product_ID, trxName);
		for (MProductBOM BOM : BOMS){
			if (BOM.getM_ProductBOM().getM_Product_ID() == M_Product_BOM_ID){
				qtyTmp = qty.multiply(BOM.getBOMQty());
				return qtyTmp;
			}
			else{
				M_Product_ID = BOM.getM_ProductBOM().getM_Product_ID();
				qtyTmp = qty.multiply(BOM.getBOMQty());
				return getBOMQty(M_Product_ID, M_Product_BOM_ID, qtyTmp, trxName);
			}
		}
		return qtyTmp;
	}
	*/
	
	/**
	 * 
	 * @param M_Product_ID
	 * @param M_Product_BOM_ID
	 * @return Qty OF BOM
	 */
	public static BigDecimal getBOMQty(int M_Product_ID, int M_Product_BOM_ID, String trxName)
	{
		/*
		BigDecimal qty = BigDecimal.ZERO;
		MProductBOM[] BOMS = MProductBOM.getBOMLines(Env.getCtx(), M_Product_ID, trxName);
		for (MProductBOM BOM : BOMS){
			if (BOM.getM_ProductBOM().getM_Product_ID() == M_Product_BOM_ID){
				return BOM.getBOMQty();
			}
			else {
				M_Product_ID = BOM.getM_ProductBOM().getM_Product_ID();
				qty = BOM.getBOMQty();
				qty = getBOMQty(M_Product_ID, M_Product_BOM_ID, qty, trxName);
				if (qty.compareTo(new BigDecimal(0))>0){
					return qty;
				}
			}
		}
		return BigDecimal.ZERO;
		*/
		BigDecimal bomQty = DB.getSQLValueBDEx(trxName, "SELECT GetBOMQty(?,?,?)", -1, M_Product_ID, M_Product_BOM_ID);
		return bomQty;
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @param M_Product_BOM_ID
	 * @param trxName
	 * @return
	 */
	public static MProductBOM getBOM(int M_Product_ID, int M_ProductBOM_ID, String trxName)
	{
		int BOMID = DB.getSQLValue(trxName, "SELECT GetBOMID(-1, ?, ?)", M_Product_ID, M_ProductBOM_ID);
		MProductBOM bom = null;
		if (BOMID > 0)
			bom = new MProductBOM(Env.getCtx(), BOMID, trxName);
		return bom;
		/*
		MProductBOM[] BOMS = MProductBOM.getBOMLines(Env.getCtx(), M_Product_ID, trxName);
		MProductBOM BOM = null;
		for (int i=0; i<BOMS.length; i++){
			BOM = BOMS[i];
			M_Product_ID = BOM.getM_ProductBOM().getM_Product_ID();
			if (M_Product_ID == M_ProductBOM_ID){
				return BOM;
			}
			else{
				BOM = getBOM(M_Product_ID, M_ProductBOM_ID,trxName);
				if (null != BOM){
					return BOM;
				}
			}
		}	
		return null;
		*/
	}
	
	/**
	 * 
	 * @param ctx
	 * @param trxName
	 * @param AD_Org_ID
	 * @param M_Product_ID
	 * @return
	 */
	public static MUNSResource getChildOf(
			Properties ctx, String trxName, int AD_Org_ID, int M_Product_ID, int ResourceParent_ID)
	{
		String sql="SELECT UNS_Resource_ID FROM UNS_Resource rsc " +
				" 	INNER JOIN UNS_Resource_InOut rio ON rsc.UNS_Resource_ID=rio.UNS_Resource_ID " +
				"WHERE AD_Org_ID=? AND rio.M_Product_ID=? AND rsc.ResourceParent_ID=? " +
				"	   AND rio.InOutType=? AND rsc.IsActive='Y'";
		
		int id = DB.getSQLValue(trxName, sql, 
				AD_Org_ID, M_Product_ID, ResourceParent_ID, MUNSResourceInOut.INOUTTYPE_Output);
		if (id > 0)
			return new MUNSResource(ctx, id, trxName);
		else
			return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param trxName
	 * @param AD_Org_ID
	 * @return
	 */
	public static MUNSResource getWSFromDept(Properties ctx, String trxName, int AD_Org_ID){
		String sql="SELECT UNS_Resource_ID FROM UNS_Resource WHERE ResourceType='WS' AND AD_Org_ID=?";
		int id = DB.getSQLValue(trxName, sql, AD_Org_ID);
		return new MUNSResource(ctx, id, trxName);
	}
	
	public static MUNSResource getWSFromProduct(Properties ctx, String trxName, int M_Product_ID){
		String sql = "SELECT UNS_Resource_InOut_ID FROM UNS_Resource_InOut INNER JOIN UNS_Resource ON " +
					"UNS_Resource.UNS_Resource_ID=UNS_Resource_InOut.UNS_Resource_ID WHERE UNS_Resource.ResourceType='WS' " +
					"AND UNS_Resource_InOut.M_Product_ID=?";
		int id = DB.getSQLValue(trxName, sql, M_Product_ID);
		if (id==-1)
			return null;
			
		MUNSResourceInOut rio = new MUNSResourceInOut(ctx, id, trxName);
		return rio.getParent();
	}
	
	public boolean isOptionalAndSameResource(int M_ProductBOM_ID) {
		MUNSResourceInOut[] inouts = getOutputLines();
		int i = 0;
		for (MUNSResourceInOut inout : inouts) {
			MProductBOM bom = getBOM(inout.getM_Product_ID(), M_ProductBOM_ID, get_TrxName());
			if (null != bom && inout.getOutputType().equals(MUNSResourceInOut.OUTPUTTYPE_Optional)) {
				i++;
			}
		}
		if (i>0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean deleteOutputs()
	{
		/*
		int[] rioIDs = DB.getIDsEx(get_TrxName(), 
				"SELECT UNS_Resource_InOut_ID FROM UNS_Resource_InOut " +
				"WHERE InOutType='O' AND UNS_Resource_ID=?", 
				get_ID());;
		*/
		String sql = "DELETE FROM UNS_Resource_InOut WHERE InOutType='O' AND UNS_Resource_ID=" + get_ID();

		//int count = -1;
		int count = DB.executeUpdateEx(sql, get_TrxName());
		/*
		PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName());
		try {
			count = pstmt.executeUpdate();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		rioIDs = DB.getIDsEx(get_TrxName(), 
				"SELECT UNS_Resource_InOut_ID FROM UNS_Resource_InOut " +
				"WHERE InOutType='O' AND UNS_Resource_ID=?", 
				get_ID());;
		*/
		return count > 0;
	}

	/**
	 * Check if the M_Product_ID is set as one of resource's transition Out-In product.
	 * @param resource
	 * @param M_Product_ID
	 * @return
	 */
	public boolean isConsideredAsParentOutput(int M_Product_ID)
	{
		boolean isSold = MProduct.get(getCtx(), M_Product_ID).isSold();
		
		boolean isOutputOfResource = false;
		for (MUNSResourceInOut rio : getOutputLines())
		{
			if (rio.getM_Product_ID() == M_Product_ID) {
				isOutputOfResource = true;
				break;
			}
		}
		if (!isOutputOfResource)
			return false;
		
		return isSold || !isOutputSetAsTransitionOutIn(M_Product_ID);
	}
	
	/**
	 * Check if the M_Product_ID is set as one of resource's transition Out-In product.
	 * @param resource
	 * @param M_Product_ID
	 * @return
	 */
	public boolean isOutputSetAsTransitionOutIn(int M_Product_ID)
	{
		String sql = "SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio, UNS_Resource_Transition rt" +
				" WHERE rio.UNS_Resource_Transition_ID=rt.UNS_Resource_Transition_ID AND " +
				" 		rt.UNS_Resource_ID=? AND rio.M_Product_ID=? AND rio.IsActive='Y' AND rt.IsActive='Y'";
		int iCount = DB.getSQLValue(get_TrxName(), sql, new Object[]{getUNS_Resource_ID(), M_Product_ID});
		
		if (iCount != -1)
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getMaxOutput()
	{
		String sql = "SELECT MAX(" + X_UNS_Resource_InOut.COLUMNNAME_MaxCaps + ") FROM " 
				+ X_UNS_Resource_InOut.Table_Name
				+ " WHERE " + COLUMNNAME_UNS_Resource_ID + " =? AND IsActive='Y'";
		BigDecimal maxCaps = DB.getSQLValueBD(get_TrxName(), sql, getUNS_Resource_ID());
		return maxCaps;
	}
	
	/**
	 * getLines start nodes
	 * @return
	 */
	public MUNSResource[] getStartNodes()
	{
		return getStartNodes(false);
	}
	
	private MUNSResource[] m_startNodeLines = null;
	
	/**
	 * getLines start nodes
	 * @return
	 */
	public MUNSResource[] getStartNodes(boolean requery)
	{
		if (m_startNodeLines != null && !requery) {
			set_TrxName(m_startNodeLines, get_TrxName());
			return m_startNodeLines;
		}
		List<MUNSResource> list = Query
				.get(getCtx(), MUNSResource.EXTENSION_ID,
						MUNSResource.Table_Name, "ResourceParent_ID=? AND IsParentStartNode ='Y'",
						get_TrxName())
				.setParameters(new Object[] { getUNS_Resource_ID() })
				.setOrderBy(MUNSResource.COLUMNNAME_UNS_Resource_ID)
				.setOnlyActiveRecords(true)
				.list();

		m_startNodeLines = new MUNSResource[list.size()];
		list.toArray(m_startNodeLines);
		return m_startNodeLines;
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	public MUNSResourceInOut getResourceOutput(int M_Product_ID)
	{
		return  Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), 
				X_UNS_Resource_InOut.Table_Name, COLUMNNAME_UNS_Resource_ID + "=?" 
				+ " AND " + X_UNS_Resource_InOut.COLUMNNAME_M_Product_ID + "=?"
				+ " AND InoutType = 'O'",get_TrxName())
				.setParameters(getUNS_Resource_ID(), M_Product_ID)
				.setOnlyActiveRecords(true)
				.firstOnly();
	}
	
	/**
	 * 
	 * @return true if resource type is production line
	 */
	public boolean isPlant()
	{
		return RESOURCETYPE_1Plant.equals(getResourceType());
	}
	
	/**
	 * 
	 * @return true if resource type is production line
	 */
	public boolean isProductionLine()
	{
		return RESOURCETYPE_2ProductionLine.equals(getResourceType());
	}
	
	/**
	 * 
	 * @return true if resource type is work center
	 */
	public boolean isWorkCenter()
	{
		return RESOURCETYPE_3WorkCenter.equals(getResourceType());
	}
	
	
	/**
	 * Mendapatkan output dari sebuah resource dengan maxcaps terbesar
	 * @return
	 */
	private MUNSResourceInOut getOutputWithMaxCaps()
	{
		MUNSResourceInOut outputPrimary = null;
		for (MUNSResourceInOut output : getOutputLines())
		{
			outputPrimary = output;
			if ( outputPrimary.getActualMaxCapsMT().compareTo(output.getActualMaxCapsMT()) < 0)
				outputPrimary = output;
		}
		return outputPrimary;
	}
	
	/**
	 * get primary output if output is multy
	 * @return
	 */
	public MUNSResourceInOut getPrimaryOutput()
	{
		for (MUNSResourceInOut output : getOutputLines())
		{
			if (output.isPrimary())
				return output;
		}
		
		return getOutputWithMaxCaps();
	}
	
	/**
	 * 
	 * @return resource End Nodes
	 */
	public MUNSResource[] getEndNodes()
	{
		MUNSResource[] nodes = null;
		List<X_UNS_Resource> list = new ArrayList<X_UNS_Resource>();
		for (MUNSResource resource : getLines())
		{
			if (resource.isParentEndNode())
			{
				list.add(resource);
			}
		}
		nodes = new MUNSResource[list.size()];
		list.toArray(nodes);
		return nodes;
	}
	
	public String toString()
	{
		return super.toString() + "[" + getValue() + "]" + "[" + getName() + "]";
	}
	
	/**
	 * Ambil maksimum kebutuhan input dari actual kapasitas output2 yang membutuhkan input tersebut.
	 * Dan yang diambil adalah total kebutuhan input dari actual kapasitas maksimum terbesar dari 
	 * kemungkinan output yang ada.
	 * @param inputID
	 * @return
	 */
	public double getMaxRequirementPerJamOf(int inputID)
	{
		double maxReq = 0.0;
		for (MUNSResourceInOut output : getOutputLines())
		{
//			I_M_Product outputProduct = output.getM_Product();
			//MProductBOM bom = getBOM(M_Product_ID, M_ProductBOM_ID, trxName);
			BigDecimal bomQty = output.getBOMQty(inputID, BigDecimal.ONE, get_TrxName());
			if (bomQty.compareTo(BigDecimal.ZERO) <= 0)
				continue;
			
			double actualOutputMaxCap = output.getActualMaxCaps().doubleValue();
			actualOutputMaxCap = actualOutputMaxCap  * bomQty.doubleValue();
			if (actualOutputMaxCap > maxReq)
				maxReq = actualOutputMaxCap;
			//if (output.getOutputType().equals(MUNSResourceInOut.OUTPUTTYPE_Multi)
			//	|| output.getOutputType().equals(MUNSResourceInOut.OUTPUTTYPE_Single))
			//	break;
		}
		return maxReq;
		// di PSG semua 1 input di process secara sequensial menjadi 1 atau beberapa output, sehingga algoritma
		// dibawah ini masih kurang relevan. Jika ingin dibuat seperti dibawah ini yang sangat memungkinkan 
		// sebuah resource mengolah 1 input menjadi beberapa tipe product berbeda2 maka perlu algoritma yang
		// benar2 dapat memeriksa apakah output2 sebuah resource itu memang berbeda2 pengolahan atau sama.
		// oleh karena itu utk case PSG kita hanya perlu mencari output terbesar berdasarkan BOMQty output dari 
		// input yang dimaksud (algoritma di atas).
		/*
		double actualMaxReqOfSingle = 0.0;
		double actualMaxReqOfMulti = 0.0;
		double actualMaxReqOfOptional = 0.0;
		for (MUNSResourceInOut output : getOutputLines())
		{
//			I_M_Product outputProduct = output.getM_Product();
			BigDecimal bomQty = output.getBOMQty(inputID, BigDecimal.ONE, get_TrxName());
			if (bomQty.compareTo(BigDecimal.ZERO) <= 0)
				continue;
			
			double actualOutputMaxCap = output.getActualMaxCapsMT().doubleValue() * bomQty.doubleValue();
			if (output.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Single))
			{
				actualMaxReqOfSingle += actualOutputMaxCap;
				continue;
			}
			if (output.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Multi) && output.isPrimary())
			{
				actualMaxReqOfMulti += actualOutputMaxCap;
			}
			if (output.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Optional))
			{
				actualMaxReqOfOptional = actualOutputMaxCap;
			}
		}
		
		return actualMaxReqOfMulti+actualMaxReqOfOptional+actualMaxReqOfSingle;
		*/
	}
	
	/**
	 * 
	 * @param outputID
	 * @return
	 */
	public double getAvgProportionOf(String allocationMethod, int outputID, int inputID)
	{
		double actualMaxCapsOfSingle = 0.0;
		double actualMaxCapsOfMulti = 0.0;
		double actualMaxCapsOfOptional = 0.0;
		double inReqOfTheOutput = 0.0;
		//int optionalCount = 0;
		//int multiCount = 0;
		MUNSResourceInOut theOutput = null;
		String typeOfOutput = null;
		
		for (MUNSResourceInOut output : getOutputLines())
		{
			//MProductBOM bom = getBOM(output.getM_Product_ID(), inputID, get_TrxName());
			BigDecimal bomQty = getBOMQty(output.getM_Product_ID(), inputID, get_TrxName());
			if (bomQty == null || bomQty.signum() == 0)
				continue;
			
			//TODO FOR GENRATE FORECAST
//			org.compiere.model.MProduct prdOfOutput = MProduct.get(getCtx(), output.getM_Product_ID());
//
//			if (prdOfOutput.isGeneralProduct() && output.isOptional())
//			{
//				if (allocationMethod.equals(GenerateForecast.INPUTALLOCATION_ExcludeGeneral))
//					continue;
//			}
//			else if (allocationMethod.equals(GenerateForecast.INPUTALLOCATION_ExcludePerBuyer)
//					&& output.isOptional())
//					continue;
				
			double actualInReqOfOutputMaxCap = output.getActualMaxCaps().multiply(bomQty).doubleValue();
			
			if (output.getM_Product_ID() == outputID)
			{
				theOutput = output;
				inReqOfTheOutput = actualInReqOfOutputMaxCap;
				typeOfOutput = output.getOutputType();
			}
			if (output.isSingle() && output.getM_Product_ID() == outputID)
			{
				actualMaxCapsOfSingle += actualInReqOfOutputMaxCap;
				continue;
			}
			if (output.isMulti() && output.isPrimary())//&& multiCount == 0 && output.getM_Product_ID() == outputID)
			{
				actualMaxCapsOfMulti = actualInReqOfOutputMaxCap;
				//multiCount++;
			}
			if (output.isOptional())
					//&& output.getM_Product_ID() == outputID)
			{
				actualMaxCapsOfOptional += actualInReqOfOutputMaxCap;
				//optionalCount++;
			}
			/*
			if (optionalCount == 0 
					&& output.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Optional)
					&& output.getM_Product_ID() == outputID)
			{
				actualMaxCapsOfOptional = actualInReqOfOutputMaxCap;
				optionalCount++;
			}
			*/
		}
		
		if (null == theOutput)
			return 0;
		double totalInReqByOuputCaps = 0.0; 
		/*
		if (fromDifferentWSButSameBOMChain(Multi, Single, Optional)){
			// TODO mungkin saja terjadi. Fikirkan ...!!!
			// double totalInReqByOuputCaps = actualMaxCapsOfMulti + actualMaxCapsOfOptional + actualMaxCapsOfSingle;
		}
		*/
		if (MUNSResourceInOut.OUTPUTTYPE_Multi.equals(typeOfOutput))
			totalInReqByOuputCaps = actualMaxCapsOfMulti;
		else if (MUNSResourceInOut.OUTPUTTYPE_Single.equals(typeOfOutput))
			totalInReqByOuputCaps = actualMaxCapsOfSingle;
		else 
			totalInReqByOuputCaps = actualMaxCapsOfOptional;
		/*
		if (actualMaxCapsOfMulti > actualMaxCapsOfOptional
			&& actualMaxCapsOfMulti > actualMaxCapsOfSingle)
			totalInReqByOuputCaps = actualMaxCapsOfMulti + actualMaxCapsOfOptional + actualMaxCapsOfSingle;
		*/
		/**
		if (theOutput.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Single))
		{
			return theOutput.getActualMaxCapsMT().doubleValue() / totalInReqOuputCaps;
		}
		if (theOutput.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Multi))
		{
			return theOutput.getActualMaxCapsMT().doubleValue() / totalInReqOuputCaps;
		}
		if (theOutput.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Optional))
		{
			return (actualMaxCapsOfOptional/optionalCount) / totalInReqOuputCaps;
		}
		*/
		if (totalInReqByOuputCaps <= 0)
			return 0;
		return inReqOfTheOutput/totalInReqByOuputCaps;
	
	}
	
	/**
	 * Get list of resources which is set as next resources of this resource transition for the given outinID
	 * @param fromOutToIn_ID
	 * @return MUNSResource[]
	 */
	public MUNSResource[] getNextResourcesOf(int fromOutToIn_ID)
	{
		MUNSResource[] resources = null;
		ArrayList<MUNSResource> rscList = new ArrayList<MUNSResource>();
		
		String sql = "SELECT rsc.* FROM UNS_Resource rsc INNER JOIN UNS_Resource_Transition rscTrans " +
				" ON rsc.UNS_Resource_ID = rscTrans.NextResource_ID INNER JOIN UNS_Resource_InOut io " +
				" ON rscTrans.UNS_Resource_Transition_ID = io.UNS_Resource_Transition_ID " +
				" AND io.M_Product_ID = " + fromOutToIn_ID +
				" WHERE rscTrans.UNS_Resource_ID = " + getUNS_Resource_ID() +
				" AND rsc.IsActive='Y' AND rscTrans.IsActive='Y' AND io.IsActive='Y'";
		PreparedStatement st = DB.prepareStatement(sql, get_TrxName());
		try {
			ResultSet rs = st.executeQuery();
			while (rs.next())
			{
				rscList.add(new MUNSResource(getCtx(), rs, get_TrxName()));
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new AdempiereException(e.getMessage());
		} finally {
			st = null;
		}
		resources = new MUNSResource[rscList.size()];
		rscList.toArray(resources);
		
		return resources;
	}
	
	public Hashtable<Integer, double[]> getAllProductProductionHours()
	{
		Hashtable<Integer, double[]> allProductProductionHours = new Hashtable<Integer, double[]>();
		String sql = "SELECT io.M_Product_ID, SUM(io.Day1ProductionHours), SUM(io.Day2ProductionHours), " +
				" SUM(io.Day3ProductionHours), SUM(io.Day4ProductionHours), SUM(io.Day5ProductionHours), " +
				"SUM(io.Day6ProductionHours), SUM(io.Day7ProductionHours)" +
				" FROM UNS_Resource_InOut io INNER JOIN UNS_Resource rsc " +
				"ON rsc.UNS_Resource_ID = io.UNS_Resource_ID  AND rsc.ResourceParent_ID = " + getUNS_Resource_ID() +
				" WHERE io.InOutType = 'O' AND io.IsActive='Y' AND rsc.IsActive='Y' " +
				" GROUP BY io.M_Product_ID, io.Day1ProductionHours, io.Day2ProductionHours, " +
				"io.Day3ProductionHours, io.Day4ProductionHours, io.Day5ProductionHours, " +
				"io.Day6ProductionHours, io.Day7ProductionHours ";
		
		PreparedStatement st = DB.prepareStatement(sql, get_TrxName());
		try {
			ResultSet rs = st.executeQuery();
			while (rs.next())
			{
				double[] productionHours = new double[7];
				productionHours[0] = rs.getDouble(2);
				productionHours[1] = rs.getDouble(3);
				productionHours[2] = rs.getDouble(4);
				productionHours[3] = rs.getDouble(5);
				productionHours[4] = rs.getDouble(6);
				productionHours[5] = rs.getDouble(7);
				productionHours[6] = rs.getDouble(8);
				allProductProductionHours.put(rs.getInt(1), productionHours);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			throw new AdempiereException(e.getMessage());
		}
		return allProductProductionHours;
	}

	/**
	 * Key map nya adalah key UNS_Resource_InOut_ID.
	 * @return
	 */
	public Hashtable<MUNSResourceInOut, double[]> getAllOutputProductionHours()
	{
		Hashtable<MUNSResourceInOut, double[]> allProductProductionHours = new Hashtable<MUNSResourceInOut, double[]>();
		String sql = "SELECT io.UNS_Resource_InOut_ID, SUM(io.Day1ProductionHours), SUM(io.Day2ProductionHours), " +
				" SUM(io.Day3ProductionHours), SUM(io.Day4ProductionHours), SUM(io.Day5ProductionHours), " +
				"SUM(io.Day6ProductionHours), SUM(io.Day7ProductionHours)" +
				" FROM UNS_Resource_InOut io INNER JOIN UNS_Resource rsc " +
				"ON rsc.UNS_Resource_ID = io.UNS_Resource_ID  AND rsc.ResourceParent_ID = " + getUNS_Resource_ID() +
				" WHERE io.InOutType = 'O' AND io.IsActive='Y' AND rsc.IsActive='Y'" +
				" GROUP BY io.UNS_Resource_InOut_ID, io.Day1ProductionHours, io.Day2ProductionHours, " +
				"io.Day3ProductionHours, io.Day4ProductionHours, io.Day5ProductionHours, " +
				"io.Day6ProductionHours, io.Day7ProductionHours ";
		
		PreparedStatement st = DB.prepareStatement(sql, get_TrxName());
		try {
			ResultSet rs = st.executeQuery();
			while (rs.next())
			{
				double[] productionHours = new double[7];
				productionHours[0] = rs.getDouble(2);
				productionHours[1] = rs.getDouble(3);
				productionHours[2] = rs.getDouble(4);
				productionHours[3] = rs.getDouble(5);
				productionHours[4] = rs.getDouble(6);
				productionHours[5] = rs.getDouble(7);
				productionHours[6] = rs.getDouble(8);
				allProductProductionHours.put(new MUNSResourceInOut(getCtx(), rs.getInt(1), get_TrxName()), productionHours);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			throw new AdempiereException(e.getMessage());
		}
		return allProductProductionHours;
	}
	
	/**
	 * Get resources of product
	 * @param M_Product_ID
	 * @return MUNSResource[]
	 */
	public MUNSResource[] getResourcesOf(int M_Product_ID)
	{
		MUNSResource[] resourcesOfProduct = null;
		List<MUNSResource> list = new ArrayList<MUNSResource>();
		for (MUNSResource child : getLines())
		{
			for (MUNSResourceInOut output : child.getOutputLines())
			{
				if (output.getM_Product_ID() == M_Product_ID)
					list.add(child);
			}
		}
		resourcesOfProduct = new MUNSResource[list.size()];
		list.toArray(resourcesOfProduct);
		return resourcesOfProduct;
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	public BigDecimal getTotalActualMaxCapsOf(int M_Product_ID)
	{
		BigDecimal maximumQty = BigDecimal.ZERO;
		for (MUNSResourceInOut outputLines : getOutputLines())
		{
			if (outputLines.getM_Product_ID() != M_Product_ID)
				continue;
			maximumQty = maximumQty.add(outputLines.getActualMaxCaps());
		}
		return maximumQty;
	}
	
	public BigDecimal getTotalActualMaxCapsMTOf(int M_Product_ID)
	{
		BigDecimal maximumQty = BigDecimal.ZERO;
		for (MUNSResourceInOut outputLines : getOutputLines())
		{
			if (outputLines.getM_Product_ID() != M_Product_ID)
				continue;
			maximumQty = maximumQty.add(outputLines.getActualMaxCapsMT());
		}
		return maximumQty;
	}

	/**
	 * 
	 * @param M_Product_ID
	 * @param year
	 * @return
	 */
	public BigDecimal getTotalProductProductionHours(int M_Product_ID, int year)
	{
		BigDecimal total = BigDecimal.ZERO;
		Calendar cal = Calendar.getInstance();
		for (MUNSResourceInOut output : getOutputLines())
		{
			if (output.getM_Product_ID() != output.getM_Product_ID())
				continue;
			
			total = total.add(output.getTotalProductProductionHoursInYear(cal, output.getAD_Org_ID()));
		}
		
		return total;
	}
	
	/**
	 * 
	 * @param labor_ID
	 * @return
	 */
	public MUNSResourceWorkerLine getWorker(int labor_ID)
	{
		for (MUNSResourceWorkerLine worker : getListOfWorker())
		{
			if (labor_ID == worker.getLabor_ID())
				return worker;
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<MUNSResourceWorkerLine> getListOfWorker()
	{
		if (m_ListOfWorkerLines != null && m_ListOfWorkerLines.size() >0)
			return m_ListOfWorkerLines;
		
		return getWorkerListOf(this);
	}
	
	/**
	 * 
	 * @param resource
	 * @return
	 */
	public List<MUNSResourceWorkerLine> getWorkerListOf( MUNSResource resource)
	{
		if (resource.isWorker())
		{
			for (MUNSResourceWorkerLine workerLine : resource.getWorkerLines(false))
			{
				m_ListOfWorkerLines.add(workerLine);
			}
		}
		for (MUNSResource child : resource.getLines())
		{
			getWorkerListOf(child);
		}
		return m_ListOfWorkerLines;
	}
	
	public List<MUNSResource> getResourceWorker()
	{
		m_listResourceWorker = new ArrayList<MUNSResource>();
		return getResourceWorker(this);
	}
	
	private List<MUNSResource> m_listResourceWorker = null;
	
	/**
	 * 
	 * @param resource
	 * @return
	 */
	public List<MUNSResource> getResourceWorker(MUNSResource resource)
	{
		if (resource.isWorker())
			m_listResourceWorker.add(resource);
		
		else
			for(MUNSResource child : resource.getLines())
				getResourceWorker(child);
		
		return m_listResourceWorker;
	}
	
	public MUNSResource getResourceChildOf(int M_Product_ID)
	{
		for(MUNSResource child : getLines())
		{
			MUNSResourceInOut output = child.getResourceOutput(M_Product_ID);
			if(null == output)
				continue;
			return child;
		}
		return null;
	}
	
	public MUNSResource getParent()
	{
		return (MUNSResource)getResourceParent();
	}
	
	/**
	 * Reset it's node state either it is a parent's start node or end node.
	 * 
	 * @return
	 */
	public boolean resetNodeState()
	{
		String sql = "SELECT COUNT(*) FROM UNS_Resource_Transition rt WHERE rt.NextResource_ID=?";
		int prevCount = DB.getSQLValue(get_TrxName(), sql, get_ID());
		
		boolean isParentStartNode = (prevCount <= 0);
		
		setIsParentStartNode(isParentStartNode);
		
		sql = "SELECT COUNT(*) FROM UNS_Resource_Transition rt WHERE rt.UNS_Resource_ID=?";
		int nextCount = DB.getSQLValue(get_TrxName(), sql, get_ID());
		
		boolean isParentEndNode = (nextCount <= 0);
		
		setIsParentEndNode(isParentEndNode);
		
		return save();
	}
	
	/**
	 * Reset all of the given's resource's child node state recursively.

	 * @param rsc
	 */
	public static boolean resetNodeStateRecursively(MUNSResource rsc, String trxName) 
	{
		boolean success = true;
		
		for (MUNSResource child : rsc.getLines())
		{
			success = child.resetNodeState();
			
			success = resetNodeStateRecursively(child, trxName);
		}
		return success;
	} 
	
	/**
	 * 
	 * @param M_Product_ID
	 * 
	 * @return true if the M_Product_ID is set as this previous resource (in transition)'s output.
	 */
	public boolean isPreviousResourceOutput(int M_Product_ID)
	{
		String sql = "SELECT count(*) FROM UNS_Resource_InOut rio WHERE rio.M_Product_ID=? " +
				" AND rio.IsActive='Y' AND rio.UNS_Resource_ID IN " +
				"    (SELECT rscTrans.UNS_Resource_ID FROM UNS_Resource_Transition rscTrans " +
				"  	  WHERE rscTrans.NextResource_ID=? AND rscTrans.IsActive='Y')";
		int count = DB.getSQLValue(get_TrxName(), sql, M_Product_ID, get_ID());
		
		return count > 0;
	}

	/**
	 * To check apakah parameter input_ID merupakan input (BOM part) dari workstation-start node dari resource ini.
	 * Method ini hanya untuk melakukan pengecekan pada resource type PL, WC, dan WS. 
	 * @param input_ID
	 * @return
	 */
	public boolean isRequiredInput(int input_ID)
	{
		String sql = "";
		if (RESOURCETYPE_2ProductionLine.equals(getResourceType()))
			sql = "SELECT count(*) FROM M_Product_BOM bom " +
				" WHERE bom.IsActive='Y' AND bom.M_Product_ID IN (SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio " +
				"	 WHERE rio.IsActive='Y' AND rio.UNS_Resource_ID IN (SELECT ws.UNS_Resource_ID FROM UNS_Resource ws " +
				"		WHERE ws.IsActive='Y' AND ws.isParentStartNode='Y' AND " +
				"			ws.ResourceParent_ID IN (SELECT wc.UNS_Resource_ID FROM UNS_Resource wc " +
				"			WHERE wc.IsActive='Y' AND wc.isParentStartNode='Y' AND wc.ResourceParent_ID=" + get_ID() + ")))" +
				"  AND bom.BomType='P' AND bom.M_ProductBOM_ID=" + input_ID;
		else if (RESOURCETYPE_3WorkCenter.equals(getResourceType()))
			sql = "SELECT count(*) FROM M_Product_BOM bom " +
					" WHERE bom.IsActive='Y' AND bom.M_Product_ID IN (SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio " +
					"	 WHERE rio.IsActive='Y' AND rio.UNS_Resource_ID IN (SELECT ws.UNS_Resource_ID FROM UNS_Resource ws " +
					"		WHERE ws.IsActive='Y' AND ws.isParentStartNode='Y' AND " +
					"			ws.ResourceParent_ID =" + get_ID() + "))" +
					" AND bom.BomType='P' AND bom.M_ProductBOM_ID=" + input_ID;
		else if (RESOURCETYPE_4WorkStation.equals(getResourceType()))
			sql = "SELECT count(*) FROM M_Product_BOM bom " +
					" WHERE bom.IsActive='Y' AND bom.M_Product_ID IN (SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio " +
					"	 WHERE rio.IsActive='Y' AND rio.UNS_Resource_ID =" + get_ID() + ")" +
					" AND bom.BomType='P' AND bom.M_ProductBOM_ID=" + input_ID;
		if ("".equals(sql))
			return false;
		
		int existingID = DB.getSQLValue(get_TrxName(), sql);
		
		return existingID > 0;
	}

	/**
	 * 
	 * @param ctx
	 * @param employeeID
	 * @param trxName
	 * @return
	 */
	public boolean isAdditionalWorker(int workerId)
	{
		MUNSResourceWorkerLine theWorker = getWorker(workerId);
		
		return (theWorker == null || theWorker.isAdditional());
	}
	
	public static MUNSResource get (Properties ctx, int Resource_ID){
		return new MUNSResource(ctx, Resource_ID, null);
	}
	
	public static MUNSSlotType[] getSlotType (MUNSEmployee labor)
	{
		String sql = "SELECT * FROM UNS_SlotType st WHERE EXISTS ("
				+ " SELECT 1 FROM UNS_Resource rs WHERE "
				+ " rs.UNS_SloType_ID = st.UNS_SlotType_ID"
				+ " AND EXISTS (SELECT 1 FROM "
				+ " UNS_Resource_WorkerLine rwl WHERE rwl.UNS_Resource_ID = rs.UNS_Resource_ID"
				+ " rwl.Labor_ID = ? AND rwl.IsActive = 'Y') AND rs.ResourceType = ? AND "
				+ " rs.IsActive = 'Y') AND st.IsActive = 'Y'";
		
		List<MUNSSlotType> list = new ArrayList<MUNSSlotType>();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, labor.get_TrxName());
			st.setInt(1, labor.get_ID());
			st.setString(2, RESOURCETYPE_5Worker);
			rs = st.executeQuery();
			while (rs.next())
			{
				list.add(new MUNSSlotType(
						labor.getCtx(), rs, labor.get_TrxName()));
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		finally 
		{
			DB.close(rs, st);
		}
		
		MUNSSlotType[] slottype = new MUNSSlotType[list.size()];
		list.toArray(slottype);
		
		return slottype;
	}
	
	public MUNSResourceInOut getInOut (int M_Product_ID) {
		return  Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), 
				X_UNS_Resource_InOut.Table_Name, COLUMNNAME_UNS_Resource_ID + "=?" 
				+ " AND " + X_UNS_Resource_InOut.COLUMNNAME_M_Product_ID + "=?"
				,get_TrxName()). setParameters(getUNS_Resource_ID(), 
						M_Product_ID)
				.setOnlyActiveRecords(true)
				.firstOnly();
	}
	
	public static int getSlotType_ID (MUNSEmployee employee)
	{
		String sql = "SELECT UNS_SlotType_ID FROM UNS_Resource WHERE "
				+ " UNS_Resource_ID IN (SELECT UNS_Resource_ID FROM "
				+ " UNS_Resource_WorkerLine WHERE Labor_ID = ? "
				+ " AND IsActive = 'Y') AND ResourceType IN (?) AND "
				+ " IsActive = 'Y'";
		
		return DB.getSQLValue(employee.get_TrxName(), sql, employee.get_ID(), RESOURCETYPE_5Worker);
	}
	
	public static MUNSResource getByEmployee(Properties ctx, int Employee_ID, String trxName)
	{
		MUNSResource rs = null;
		String whereClause = "EXISTS"
								+ " (SELECT 1 FROM UNS_Resource_WorkerLine wl"
								+ " WHERE wl.UNS_Resource_ID = UNS_Resource.UNS_Resource_ID"
								+ " AND wl.IsActive = 'Y' AND wl.Labor_ID = ?)"
								+ " AND C_DocType_ID IN (SELECT dt.C_DocType_ID"
								+ " FROM C_DocType dt WHERE dt.DocBaseType IN ('EMP'))";
		rs = new Query(ctx, Table_Name, 
							whereClause, trxName)
								.setParameters(Employee_ID).setOnlyActiveRecords(true).first();
		return rs;
	}
}
