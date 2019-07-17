package com.unicore.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

import com.uns.base.model.Query;

import com.unicore.model.MUNSShipping;
import com.unicore.model.MUNSShippingCrewIncentive;
import com.unicore.model.MUNSShippingCrewInctvLine;
import com.unicore.model.MUNSShippingInctvSchLine;
import com.unicore.model.factory.UNSOrderModelFactory;

public class CalculateShippingIncentive extends SvrProcess {

	private int m_shippingorg_id = 0;
	
	private int m_period_id = 0;
	
	MUNSShippingCrewInctvLine crewIncentive = null;
	
	public MUNSShipping[] m_LinesShipDrv = null;
	
	public MUNSShippingInctvSchLine[] m_Lines = null;
	
	public MUNSShippingCrewInctvLine[] m_LinesCrew = null;
	
	public CalculateShippingIncentive() {
		super();
	}

	@Override
	protected void prepare() {

		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params) {

			if (param.getParameterName().equals(
					MUNSShipping.COLUMNNAME_AD_Org_ID)) {
				m_shippingorg_id = param.getParameterAsInt();
			}

			if (param.getParameterName().equals(MPeriod.COLUMNNAME_C_Period_ID)) {
				m_period_id = param.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {

		getCalculateIncentive();
		
		return null;
	}

	public java.sql.Timestamp startDate() {

		StringBuffer sql = new StringBuffer(
				"SELECT startDate FROM C_Period WHERE C_Period_ID = "
						+ m_period_id + "");

		java.sql.Timestamp timeStart = DB.getSQLValueTS(get_TrxName(),
				sql.toString());

		return timeStart;

	}

	public java.sql.Timestamp endDate() {

		StringBuffer sql = new StringBuffer(
				"SELECT endDate FROM C_Period WHERE C_Period_ID = "
						+ m_period_id + "");

		java.sql.Timestamp timeEnd = DB.getSQLValueTS(get_TrxName(),
				sql.toString());

		return timeEnd;

	}

	public int getShippingSchemaID(String crewType) {

		StringBuffer sql = new StringBuffer(
				"SELECT UNS_ShippingIncentiveSch_ID FROM UNS_ShippingIncentiveSch WHERE docStatus = 'CO' "
						+ " AND crewType = '"
						+ crewType
						+ "' AND AD_Org_ID = "
						+ m_shippingorg_id + " ");

		int timeStart = DB.getSQLValue(get_TrxName(), sql.toString());

		return timeStart;

	}

	public int getShippingCrewIncentiveID() {

		StringBuffer sql = new StringBuffer(
				"SELECT uns_shippingcrewinctv_line_ID FROM  uns_shippingcrewinctv_line ");

		int timeStart = DB.getSQLValue(get_TrxName(), sql.toString());

		return timeStart;

	}

	// using
	public MUNSShipping[] getShippingBeforeCalculate() {

		String whereClause = " docStatus = 'CO'  AND dateDoc BETWEEN '"
				+ startDate()
				+ "' AND '"
				+ endDate()
				+ "' AND AD_Org_ID = "
				+ m_shippingorg_id
				+ " AND UNS_Shipping_ID NOT IN (SELECT UNS_Shipping_ID FROM uns_shippingcrewinctv_line)";

		List<MUNSShipping> list = Query.get(getCtx(),
				UNSOrderModelFactory.EXTENSION_ID, MUNSShipping.Table_Name,
				whereClause, get_TrxName()).list();

		m_LinesShipDrv = new MUNSShipping[(list.size())];
		list.toArray(m_LinesShipDrv);

		return m_LinesShipDrv;

	}


	// using
	public int getStatus() {

		StringBuffer sql = new StringBuffer(
				"SELECT UNS_Shipping_ID FROM UNS_Shipping WHERE Status = 'RS'");

		int employeeID = DB.getSQLValue(get_TrxName(), sql.toString());
		return employeeID;

	}

	// using
	public int getDriverRitase(java.sql.Timestamp timestamp) {

		StringBuffer sql = new StringBuffer(
				"SELECT MAX(ritDriver) FROM UNS_Shipping WHERE dateDoc = '"
						+ timestamp + "' GROUP BY UNS_Employee_ID ");

		int maxRit = DB.getSQLValue(get_TrxName(), sql.toString());
		return maxRit;

	}

	// using
	public int getDriverDelivery(int employeeID, java.sql.Timestamp timestamp) {

		StringBuffer sql = new StringBuffer(
				"SELECT COUNT(UNS_Shipping_ID) FROM UNS_Shipping WHERE UNS_Employee_ID = "
						+ employeeID + " AND dateDoc = '" + timestamp
						+ "' AND Status = 'DV' ");

		int maxRit = DB.getSQLValue(get_TrxName(), sql.toString());
		return maxRit;

	}

	// using
	public int getHelper1Ritase(java.sql.Timestamp timestamp) {

		StringBuffer sql = new StringBuffer(
				"SELECT MAX(ritHelper1) FROM UNS_Shipping WHERE dateDoc = '"
						+ timestamp + "' GROUP BY helper1_ID ");

		int maxRit = DB.getSQLValue(get_TrxName(), sql.toString());
		return maxRit;

	}

	// using
	public int getHelper2Ritase(java.sql.Timestamp timestamp) {

		StringBuffer sql = new StringBuffer(
				"SELECT MAX(ritHelper2) FROM UNS_Shipping WHERE dateDoc = '"
						+ timestamp + "' GROUP BY helper2_ID ");

		int maxRit = DB.getSQLValue(get_TrxName(), sql.toString());
		return maxRit;

	}

	// using
	public int getHelper3Ritase(java.sql.Timestamp timestamp) {

		StringBuffer sql = new StringBuffer(
				"SELECT MAX(ritHelper3) FROM UNS_Shipping WHERE dateDoc = '"
						+ timestamp + "' GROUP BY helper3_ID ");

		int maxRit = DB.getSQLValue(get_TrxName(), sql.toString());
		return maxRit;

	}

	// using
	public MUNSShippingInctvSchLine[] getLinesDL(boolean requery,
			String setCrewType) {

		String whereClause = MUNSShippingInctvSchLine.COLUMNNAME_UNS_ShippingIncentiveSch_ID
				.concat("=?").concat(" AND incentiveType = 'DL' ");

		List<MUNSShippingInctvSchLine> list = Query
				.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID,
						MUNSShippingInctvSchLine.Table_Name, whereClause,
						get_TrxName())
				.setParameters(getShippingSchemaID(setCrewType)).list();

		m_Lines = new MUNSShippingInctvSchLine[(list.size())];
		list.toArray(m_Lines);
		return m_Lines;

	}

	// using
	public MUNSShippingInctvSchLine[] getLinesOT(boolean requery,
			String setCrewType) {

		String whereClause = MUNSShippingInctvSchLine.COLUMNNAME_UNS_ShippingIncentiveSch_ID
				.concat("=?").concat(" AND incentiveType = 'OT' ");

		List<MUNSShippingInctvSchLine> list = Query
				.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID,
						MUNSShippingInctvSchLine.Table_Name, whereClause,
						get_TrxName())
				.setParameters(getShippingSchemaID(setCrewType)).list();

		m_Lines = new MUNSShippingInctvSchLine[(list.size())];
		list.toArray(m_Lines);
		return m_Lines;

	}

	// using
	public MUNSShippingInctvSchLine[] getLinesRT(boolean requery,
			String setCrewType) {

		String whereClause = MUNSShippingInctvSchLine.COLUMNNAME_UNS_ShippingIncentiveSch_ID
				.concat("=?").concat(" AND incentiveType = 'RT' ");

		List<MUNSShippingInctvSchLine> list = Query
				.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID,
						MUNSShippingInctvSchLine.Table_Name, whereClause,
						get_TrxName())
				.setParameters(getShippingSchemaID(setCrewType)).list();

		m_Lines = new MUNSShippingInctvSchLine[(list.size())];
		list.toArray(m_Lines);
		return m_Lines;

	}

	// using
	public MUNSShippingInctvSchLine[] getLinesDD(String setCrewType) {

		String whereClause = MUNSShippingInctvSchLine.COLUMNNAME_UNS_ShippingIncentiveSch_ID
				.concat("=?").concat(" AND incentiveType = 'DD' ");

		List<MUNSShippingInctvSchLine> list = Query
				.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID,
						MUNSShippingInctvSchLine.Table_Name, whereClause,
						get_TrxName())
				.setParameters(getShippingSchemaID(setCrewType)).list();

		m_Lines = new MUNSShippingInctvSchLine[(list.size())];
		list.toArray(m_Lines);
		return m_Lines;

	}

	// using
	public int getShippingCrewID() {

		StringBuffer sql = new StringBuffer(
				" SELECT uns_shippingcrewinctv_line_id "
						+ "FROM uns_shippingcrewinctv_line ORDER BY uns_shippingcrewinctv_line_id DESC ");

		int shippingCrewID = DB.getSQLValue(get_TrxName(), sql.toString());
		return shippingCrewID;

	}

	public int getCrewID(int employeeID, java.sql.Timestamp date) {

		StringBuffer sql = new StringBuffer(
				" SELECT uns_shippingcrewincentive_id "
						+ "FROM uns_shippingcrewincentive WHERE uns_employee_id = "
						+ employeeID + " AND datedoc = '" + date + "'  ");

		int shippingCrewID = DB.getSQLValue(get_TrxName(), sql.toString());
		return shippingCrewID;

	}

	// using
	public int getPLReturnID(int plID) {

		StringBuffer sql = new StringBuffer(" SELECT uns_pl_return_id "
				+ "FROM uns_pl_return where uns_packinglist_id = " + plID
				+ "  ");

		int plReturnID = DB.getSQLValue(get_TrxName(), sql.toString());
		return plReturnID;

	}

	// using
	public String getReason(int plReturnID) {

		StringBuffer sql = new StringBuffer(" SELECT Reason "
				+ "FROM uns_pl_returnorder where uns_pl_return_id = "
				+ plReturnID + " " + "AND isCancelled = 'Y'  ");

		String reason = DB.getSQLValueString(get_TrxName(), sql.toString());
		return reason;

	}

	// using
	public int getShippingCrewIDCek() {

		StringBuffer sql = new StringBuffer(
				" SELECT uns_shippingcrewinctv_line_id "
						+ "FROM uns_shippingcrewinctv_line ORDER BY uns_shippingcrewinctv_line_id ASC ");

		int shippingCrewID = DB.getSQLValue(get_TrxName(), sql.toString());
		return shippingCrewID;

	}

	

	public int getCrewIncentiveID(int employeeID, java.sql.Timestamp date) {

		StringBuffer sql = new StringBuffer(
				" SELECT uns_shippingcrewincentive_id "
						+ "FROM uns_shippingcrewincentive WHERE uns_employee_id = "
						+ employeeID + " AND datedoc = '" + date + "' ");

		int shippingCrewID = DB.getSQLValue(get_TrxName(), sql.toString());
		return shippingCrewID;

	}

	

	/**************************************************************************************************/
	public void getCalculateIncentive() {

		
		if (m_shippingorg_id <= 0)
			throw new AdempiereUserError("Field Mandatory Organization.");
		else if (m_period_id <= 0)
			throw new AdempiereUserError("");

		for (MUNSShipping ship : getShippingBeforeCalculate()) {

			String setCrewType = null;
			int maxRitase = 0;
			java.util.Date date = new java.util.Date();
			int minRitase = 0;
			int amountRitase = 0;
			int amountDeduction = 0;
			String reason = "TODO";
			String descString = "You are getting incentive type :";
			String descTypes1 = null;

			int schemaID = 0;
			BigDecimal totalAmount = null;
			StringBuilder description;
		
			

			if (ship.getUNS_Employee_ID() > 0) {

				setCrewType = "DRV";

				if (getCrewID(ship.getUNS_Employee_ID(), ship.getDateDoc()) <= -1) {
					MUNSShippingCrewIncentive sci = new MUNSShippingCrewIncentive(
							getCtx(), 0, get_TrxName());
					sci.setAD_Org_ID(ship.getAD_Org_ID());
					sci.setUNS_Employee_ID(ship.getUNS_Employee_ID());
					sci.setDateProcessed(new Timestamp(date.getTime()));
					sci.setDateDoc(ship.getDateDoc());
					sci.saveEx();
				}

				if (getCrewIncentiveID(ship.getUNS_Employee_ID(),
						ship.getDateDoc()) >= 1) {

					maxRitase = getDriverRitase(ship.getDateDoc());

					for (MUNSShippingInctvSchLine driveLine : getLinesDL(true,
							setCrewType)) {

						minRitase = Integer.valueOf(driveLine.getminRitase());
						schemaID = driveLine.getUNS_ShippingIncentiveSch_ID();

						if (maxRitase >= minRitase) {
							amountRitase += driveLine.getIncentiveValue()
									/ maxRitase;
							totalAmount = BigDecimal.valueOf(amountRitase);
							descTypes1 = "\n - Daily Incentive";
							description = new StringBuilder(descString)
									.append(descTypes1);

							MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
									getCtx(), 0, get_TrxName());

							sciLine.setAD_Org_ID(ship.getAD_Org_ID());
							sciLine.setC_Period_ID(m_period_id);
							sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
									ship.getUNS_Employee_ID(),
									ship.getDateDoc()));
							sciLine.setcrewTYpe(setCrewType);
							sciLine.setDateDoc(ship.getDateDoc());
							sciLine.setdateShipping(ship.getDateDoc());
							sciLine.setUNS_Employee_ID(ship.getUNS_Employee_ID());
							sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
							sciLine.setIncentiveType("DL");
							sciLine.setAmount(totalAmount);
							sciLine.setshippingDesc(description.toString());
							sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);

							sciLine.saveEx();

							descTypes1 = null;
							amountRitase = 0;
							totalAmount = null;
							maxRitase = 0;
						}
					}

				}

				if (ship.getStatus().equals("DV")) {
					for (MUNSShippingInctvSchLine driveLineOT : getLinesOT(
							true, setCrewType)) {

						amountRitase += driveLineOT.getIncentiveValue();
						totalAmount = BigDecimal.valueOf(amountRitase);

					}

					for (MUNSShippingInctvSchLine driveLineRT : getLinesRT(
							true, setCrewType)) {

						amountRitase += driveLineRT.getIncentiveValue();
						totalAmount = BigDecimal.valueOf(amountRitase);

					}

					descTypes1 = "\n - Otlet and Ritase Incentive";
					description = new StringBuilder(descString)
							.append(descTypes1);

					MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
							getCtx(), 0, get_TrxName());

					sciLine.setAD_Org_ID(ship.getAD_Org_ID());
					sciLine.setC_Period_ID(m_period_id);
					sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
							ship.getUNS_Employee_ID(), ship.getDateDoc()));
					sciLine.setcrewTYpe(setCrewType);
					sciLine.setDateDoc(ship.getDateDoc());
					sciLine.setdateShipping(ship.getDateDoc());
					sciLine.setUNS_Employee_ID(ship.getUNS_Employee_ID());
					sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
					sciLine.setIncentiveType("OT");
					sciLine.setAmount(totalAmount);
					sciLine.setshippingDesc(description.toString());
					sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
					sciLine.saveEx();

					descTypes1 = null;
					amountRitase = 0;
					totalAmount = null;

				}

//				reason = getReason(getPLReturnID(ship.getUNS_PackingList_ID()));

				if (ship.getStatus().equals("RS")) {
					for (MUNSShippingInctvSchLine ddLines : getLinesDD(setCrewType)) {

						if (reason.equals(ddLines.getReason())) {

							amountDeduction -= ddLines.getIncentiveValue();
							schemaID = ddLines.getUNS_ShippingIncentiveSch_ID();
							totalAmount = BigDecimal.valueOf(amountDeduction);

							if (reason.equals("NA")) {
								descTypes1 = "\n - Other Reason";
							}

							if (reason.equals("TP")) {
								descTypes1 = "\n - Toko Tutup";
							}

							if (reason == "TK") {
								descTypes1 = "\n - Toko Tidak Ditemukan";
							}

							if (reason == "TR") {
								descTypes1 = "\n - Macet";
							}

						}

						description = new StringBuilder(descString)
								.append(descTypes1);

						MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
								getCtx(), 0, get_TrxName());

						sciLine.setAD_Org_ID(ship.getAD_Org_ID());
						sciLine.setC_Period_ID(m_period_id);
						sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
								ship.getUNS_Employee_ID(), ship.getDateDoc()));
						sciLine.setcrewTYpe(setCrewType);
						sciLine.setDateDoc(ship.getDateDoc());
						sciLine.setdateShipping(ship.getDateDoc());
						sciLine.setUNS_Employee_ID(ship.getUNS_Employee_ID());
						sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
						sciLine.setIncentiveType("DD");
						sciLine.setAmount(totalAmount);
						sciLine.setshippingDesc(description.toString());
						sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
						sciLine.saveEx();

						descTypes1 = null;
						amountDeduction = 0;
						totalAmount = null;

					}

				}

			

			}

			if (ship.getHelper1_ID() > 0) {

				setCrewType = "HPR";

				if (getCrewID(ship.getHelper1_ID(), ship.getDateDoc()) <= -1) {
					MUNSShippingCrewIncentive sci = new MUNSShippingCrewIncentive(
							getCtx(), 0, get_TrxName());
					sci.setAD_Org_ID(ship.getAD_Org_ID());
					sci.setUNS_Employee_ID(ship.getHelper1_ID());
					sci.setDateProcessed(new Timestamp(date.getTime()));
					sci.setDateDoc(ship.getDateDoc());
					sci.saveEx();
				}
				maxRitase = getHelper1Ritase(ship.getDateDoc());

				for (MUNSShippingInctvSchLine hp1Line : getLinesDL(true,
						setCrewType)) {

					minRitase = Integer.valueOf(hp1Line.getminRitase());
					schemaID = hp1Line.getUNS_ShippingIncentiveSch_ID();

					if (maxRitase >= minRitase) {

						amountRitase += hp1Line.getIncentiveValue() / maxRitase;
						totalAmount = BigDecimal.valueOf(amountRitase);
						descTypes1 = "\n - Daily Incentive";
						description = new StringBuilder(descString)
								.append(descTypes1);

						MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
								getCtx(), 0, get_TrxName());

						sciLine.setAD_Org_ID(ship.getAD_Org_ID());
						sciLine.setC_Period_ID(m_period_id);
						sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
								ship.getHelper1_ID(), ship.getDateDoc()));
						sciLine.setcrewTYpe(setCrewType);
						sciLine.setDateDoc(ship.getDateDoc());
						sciLine.setdateShipping(ship.getDateDoc());
						sciLine.setUNS_Employee_ID(ship.getHelper1_ID());
						sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
						sciLine.setIncentiveType("DL");
						sciLine.setAmount(totalAmount);
						sciLine.setshippingDesc(description.toString());
						sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
						sciLine.saveEx();

						descTypes1 = null;
						amountRitase = 0;
						totalAmount = null;
						maxRitase = 0;

					}

				}

				if (ship.getStatus().equals("DV")) {

					for (MUNSShippingInctvSchLine hp1LineOT : getLinesOT(true,
							setCrewType)) {

						amountRitase += hp1LineOT.getIncentiveValue();
						totalAmount = BigDecimal.valueOf(amountRitase);

					}

					for (MUNSShippingInctvSchLine hp1LineRT : getLinesRT(true,
							setCrewType)) {

						amountRitase += hp1LineRT.getIncentiveValue();
						totalAmount = BigDecimal.valueOf(amountRitase);

						descTypes1 = "\n - Otlet and Ritase Incentive";
						description = new StringBuilder(descString)
								.append(descTypes1);

						MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
								getCtx(), 0, get_TrxName());

						sciLine.setAD_Org_ID(ship.getAD_Org_ID());
						sciLine.setC_Period_ID(m_period_id);
						sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
								ship.getHelper1_ID(), ship.getDateDoc()));
						sciLine.setcrewTYpe(setCrewType);
						sciLine.setDateDoc(ship.getDateDoc());
						sciLine.setdateShipping(ship.getDateDoc());
						sciLine.setUNS_Employee_ID(ship.getHelper1_ID());
						sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
						sciLine.setIncentiveType("OT");
						sciLine.setAmount(totalAmount);
						sciLine.setshippingDesc(description.toString());
						sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
						sciLine.saveEx();

						descTypes1 = null;
						amountRitase = 0;
						totalAmount = null;

					}

				}

//				reason = getReason(getPLReturnID(ship.getUNS_PackingList_ID()));

				if (ship.getStatus().equals("RS")) {
					for (MUNSShippingInctvSchLine ddLines : getLinesDD(setCrewType)) {

						if (reason.equals(ddLines.getReason())) {

							amountDeduction -= ddLines.getIncentiveValue();
							schemaID = ddLines.getUNS_ShippingIncentiveSch_ID();
							totalAmount = BigDecimal.valueOf(amountDeduction);

							if (reason.equals("NA")) {
								descTypes1 = "\n - Other Reason";
							}

							if (reason.equals("TP")) {
								descTypes1 = "\n - Toko Tutup";
							}

							if (reason == "TK") {
								descTypes1 = "\n - Toko Tidak Ditemukan";
							}

							if (reason == "TR") {
								descTypes1 = "\n - Macet";
							}

						}

						description = new StringBuilder(descString)
								.append(descTypes1);

						MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
								getCtx(), 0, get_TrxName());

						sciLine.setAD_Org_ID(ship.getAD_Org_ID());
						sciLine.setC_Period_ID(m_period_id);
						sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
								ship.getHelper1_ID(), ship.getDateDoc()));
						sciLine.setcrewTYpe(setCrewType);
						sciLine.setDateDoc(ship.getDateDoc());
						sciLine.setdateShipping(ship.getDateDoc());
						sciLine.setUNS_Employee_ID(ship.getHelper1_ID());
						sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
						sciLine.setIncentiveType("DD");
						sciLine.setAmount(totalAmount);
						sciLine.setshippingDesc(description.toString());
						sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
						sciLine.saveEx();

						descTypes1 = null;
						amountDeduction = 0;
						totalAmount = null;

					}

				}

				

				if (ship.getHelper1_ID() <= 0) {
					continue;
				}

			}

			if (ship.getHelper1_ID() <= 0) {

			}

			if (ship.getHelper2_ID() > 0) {

				setCrewType = "HPR";

				if (getCrewID(ship.getHelper2_ID(), ship.getDateDoc()) <= -1) {
					MUNSShippingCrewIncentive sci = new MUNSShippingCrewIncentive(
							getCtx(), 0, get_TrxName());
					sci.setAD_Org_ID(ship.getAD_Org_ID());
					sci.setUNS_Employee_ID(ship.getHelper2_ID());
					sci.setDateProcessed(new Timestamp(date.getTime()));
					sci.setDateDoc(ship.getDateDoc());
					sci.saveEx();
				}

				maxRitase = getHelper2Ritase(ship.getDateDoc());

				for (MUNSShippingInctvSchLine hp2Line : getLinesDL(true,
						setCrewType)) {

					minRitase = Integer.valueOf(hp2Line.getminRitase());
					schemaID = hp2Line.getUNS_ShippingIncentiveSch_ID();

					if (maxRitase >= minRitase) {

						amountRitase += hp2Line.getIncentiveValue() / maxRitase;
						totalAmount = BigDecimal.valueOf(amountRitase);
						descTypes1 = "\n - Daily Incentive";
						description = new StringBuilder(descString)
								.append(descTypes1);

						MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
								getCtx(), 0, get_TrxName());

						sciLine.setAD_Org_ID(ship.getAD_Org_ID());
						sciLine.setC_Period_ID(m_period_id);
						sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
								ship.getHelper2_ID(), ship.getDateDoc()));
						sciLine.setcrewTYpe(setCrewType);
						sciLine.setDateDoc(ship.getDateDoc());
						sciLine.setdateShipping(ship.getDateDoc());
						sciLine.setUNS_Employee_ID(ship.getHelper2_ID());
						sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
						sciLine.setIncentiveType("DL");
						sciLine.setAmount(totalAmount);
						sciLine.setshippingDesc(description.toString());
						sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
						sciLine.saveEx();

						descTypes1 = null;
						amountRitase = 0;
						totalAmount = null;
						maxRitase = 0;

					}

				}

				if (ship.getStatus().equals("DV")) {

					for (MUNSShippingInctvSchLine hp2LineOT : getLinesOT(true,
							setCrewType)) {

						amountRitase += hp2LineOT.getIncentiveValue();
						totalAmount = BigDecimal.valueOf(amountRitase);

					}

					for (MUNSShippingInctvSchLine hp2LineRT : getLinesRT(true,
							setCrewType)) {

						amountRitase += hp2LineRT.getIncentiveValue();
						totalAmount = BigDecimal.valueOf(amountRitase);

						descTypes1 = "\n - Otlet and Ritase Incentive";
						description = new StringBuilder(descString)
								.append(descTypes1);

						MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
								getCtx(), 0, get_TrxName());

						sciLine.setAD_Org_ID(ship.getAD_Org_ID());
						sciLine.setC_Period_ID(m_period_id);
						sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
								ship.getHelper2_ID(), ship.getDateDoc()));
						sciLine.setcrewTYpe(setCrewType);
						sciLine.setDateDoc(ship.getDateDoc());
						sciLine.setdateShipping(ship.getDateDoc());
						sciLine.setUNS_Employee_ID(ship.getHelper2_ID());
						sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
						sciLine.setIncentiveType("OT");
						sciLine.setAmount(totalAmount);
						sciLine.setshippingDesc(description.toString());
						sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
						sciLine.saveEx();

						descTypes1 = null;
						amountRitase = 0;
						totalAmount = null;

					}

//					reason = getReason(getPLReturnID(ship
//							.getUNS_PackingList_ID()));

					if (ship.getStatus().equals("RS")) {
						for (MUNSShippingInctvSchLine ddLines : getLinesDD(setCrewType)) {

							if (reason.equals(ddLines.getReason())) {

								amountDeduction -= ddLines.getIncentiveValue();
								schemaID = ddLines
										.getUNS_ShippingIncentiveSch_ID();
								totalAmount = BigDecimal.valueOf(amountDeduction);

								if (reason.equals("NA")) {
									descTypes1 = "\n - Other Reason";
								}

								if (reason.equals("TP")) {
									descTypes1 = "\n - Toko Tutup";
								}

								if (reason == "TK") {
									descTypes1 = "\n - Toko Tidak Ditemukan";
								}

								if (reason == "TR") {
									descTypes1 = "\n - Macet";
								}

							}

							description = new StringBuilder(descString)
									.append(descTypes1);

							MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
									getCtx(), 0, get_TrxName());

							sciLine.setAD_Org_ID(ship.getAD_Org_ID());
							sciLine.setC_Period_ID(m_period_id);
							sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
									ship.getHelper2_ID(), ship.getDateDoc()));
							sciLine.setcrewTYpe(setCrewType);
							sciLine.setDateDoc(ship.getDateDoc());
							sciLine.setdateShipping(ship.getDateDoc());
							sciLine.setUNS_Employee_ID(ship.getHelper2_ID());
							sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
							sciLine.setIncentiveType("DD");
							sciLine.setAmount(totalAmount);
							sciLine.setshippingDesc(description.toString());
							sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
							sciLine.saveEx();

							descTypes1 = null;
							amountDeduction = 0;
							totalAmount = null;

						}

					}

				}

				

				if (ship.getHelper1_ID() <= 0) {
					continue;
				}

			}

			if (ship.getHelper2_ID() <= 0) {

			}

			if (ship.getHelper3_ID() > 0) {

				setCrewType = "HPR";

				if (getCrewID(ship.getHelper3_ID(), ship.getDateDoc()) <= -1) {
					MUNSShippingCrewIncentive sci = new MUNSShippingCrewIncentive(
							getCtx(), 0, get_TrxName());
					sci.setAD_Org_ID(ship.getAD_Org_ID());
					sci.setUNS_Employee_ID(ship.getHelper3_ID());
					sci.setDateProcessed(new Timestamp(date.getTime()));
					sci.setDateDoc(ship.getDateDoc());
					sci.saveEx();
				}

				maxRitase = getHelper3Ritase(ship.getDateDoc());

				for (MUNSShippingInctvSchLine hp3Line : getLinesDL(true,
						setCrewType)) {

					minRitase = Integer.valueOf(hp3Line.getminRitase());
					schemaID = hp3Line.getUNS_ShippingIncentiveSch_ID();

					if (maxRitase >= minRitase) {

						amountRitase += hp3Line.getIncentiveValue() / maxRitase;
						totalAmount = BigDecimal.valueOf(amountRitase);
						descTypes1 = "\n - Daily Incentive";
						description = new StringBuilder(descString)
								.append(descTypes1);

						MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
								getCtx(), 0, get_TrxName());

						sciLine.setAD_Org_ID(ship.getAD_Org_ID());
						sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
								ship.getHelper3_ID(), ship.getDateDoc()));
						sciLine.setC_Period_ID(m_period_id);
						sciLine.setcrewTYpe(setCrewType);
						sciLine.setDateDoc(ship.getDateDoc());
						sciLine.setdateShipping(ship.getDateDoc());
						sciLine.setUNS_Employee_ID(ship.getHelper2_ID());
						sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
						sciLine.setIncentiveType("DL");
						sciLine.setAmount(totalAmount);
						sciLine.setshippingDesc(description.toString());
						sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
						sciLine.saveEx();

						descTypes1 = null;
						amountRitase = 0;
						totalAmount = null;
						maxRitase = 0;

					}

				}

				if (ship.getStatus().equals("DV")) {

					for (MUNSShippingInctvSchLine hp3LineOT : getLinesOT(true,
							setCrewType)) {

						amountRitase += hp3LineOT.getIncentiveValue();
						totalAmount = BigDecimal.valueOf(amountRitase);

					}

					for (MUNSShippingInctvSchLine hp3LineRT : getLinesRT(true,
							setCrewType)) {

						amountRitase += hp3LineRT.getIncentiveValue();
						totalAmount = BigDecimal.valueOf(amountRitase);

						descTypes1 = "\n - Otlet and Ritase Incentive";
						description = new StringBuilder(descString)
								.append(descTypes1);

						MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
								getCtx(), 0, get_TrxName());

						sciLine.setAD_Org_ID(ship.getAD_Org_ID());
						sciLine.setC_Period_ID(m_period_id);
						sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
								ship.getHelper3_ID(), ship.getDateDoc()));
						sciLine.setcrewTYpe(setCrewType);
						sciLine.setDateDoc(ship.getDateDoc());
						sciLine.setdateShipping(ship.getDateDoc());
						sciLine.setUNS_Employee_ID(ship.getHelper3_ID());
						sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
						sciLine.setIncentiveType("OT");
						sciLine.setAmount(totalAmount);
						sciLine.setshippingDesc(description.toString());
						sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
						sciLine.saveEx();

						descTypes1 = null;
						amountRitase = 0;
						totalAmount = null;

					}

//					reason = getReason(getPLReturnID(ship
//							.getUNS_PackingList_ID()));

					if (ship.getStatus().equals("RS")) {
						for (MUNSShippingInctvSchLine ddLines : getLinesDD(setCrewType)) {

							if (reason.equals(ddLines.getReason())) {

								amountDeduction -= ddLines.getIncentiveValue();
								schemaID = ddLines
										.getUNS_ShippingIncentiveSch_ID();
								totalAmount = BigDecimal.valueOf(amountDeduction);

								if (reason.equals("NA")) {
									descTypes1 = "\n - Other Reason";
								}

								if (reason.equals("TP")) {
									descTypes1 = "\n - Toko Tutup";
								}

								if (reason == "TK") {
									descTypes1 = "\n - Toko Tidak Ditemukan";
								}

								if (reason == "TR") {
									descTypes1 = "\n - Macet";
								}

							}

							description = new StringBuilder(descString)
									.append(descTypes1);

							MUNSShippingCrewInctvLine sciLine = new MUNSShippingCrewInctvLine(
									getCtx(), 0, get_TrxName());

							sciLine.setAD_Org_ID(ship.getAD_Org_ID());
							sciLine.setC_Period_ID(m_period_id);
							sciLine.setUNS_ShippingCrewIncentive_ID(getCrewID(
									ship.getHelper3_ID(), ship.getDateDoc()));
							sciLine.setcrewTYpe(setCrewType);
							sciLine.setDateDoc(ship.getDateDoc());
							sciLine.setdateShipping(ship.getDateDoc());
							sciLine.setUNS_Employee_ID(ship.getHelper3_ID());
							sciLine.setUNS_Shipping_ID(ship.getUNS_Shipping_ID());
							sciLine.setIncentiveType("DD");
							sciLine.setAmount(totalAmount);
							sciLine.setshippingDesc(description.toString());
							sciLine.setUNS_ShippingIncentiveSch_ID(schemaID);
							sciLine.saveEx();

							descTypes1 = null;
							amountDeduction = 0;
							totalAmount = null;

						}

					}

				}

				

				if (ship.getHelper1_ID() <= 0) {
					continue;
				}

			}

			if (ship.getHelper3_ID() <= 0) {

			}

		}
		
	}

	/******************************************************************************************************/

	
}
