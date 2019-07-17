/**
 * 
 */
package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author YAKA
 *
 */
public class MUNSQAStatusNC extends X_UNS_QAStatus_NC {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5230752279359051743L;

	/**
	 * @param ctx
	 * @param UNS_QAStatus_NC_ID
	 * @param trxName
	 */
	public MUNSQAStatusNC(Properties ctx, int UNS_QAStatus_NC_ID, String trxName) {
		super(ctx, UNS_QAStatus_NC_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSQAStatusNC(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean createStatusNC(MUNSQAStatusContainer sc){
		setClientOrg(sc);
		setUNS_QAStatusContainer_ID(sc.get_ID());
		
		return save(get_TrxName());
	}

	public String checkField(){
		if (getAccByDT()==null || getAccByName()==null)
			return "Please input Accepted By. ";
		if (getVrfByDT()==null || getVrfByName()==null)
			return "Please input Verified By. ";
		if (getActByDT()==null || getActByName()==null)
			return "Please input Action By. ";
		if (getActByDT()==null || getActByName()==null)
			return "Please input Action By. ";
		
		return null;
	}

	private void processIt(String msg, boolean release){
		if (msg==null){
			MUNSQAStatusContainer sc = getParent();
			MUNSQAContainerInspection ci = sc.getParent();
			if (release){
				sc.setQAStatus(X_UNS_QAStatusContainer.QASTATUS_Release);
				msg = msg + ci.completeIt();
			} else {
				sc.setQAStatus(X_UNS_QAStatusContainer.QASTATUS_Reject);
			}
			sc.saveEx(get_TrxName());
			
			setProcessed(true);
			saveEx(get_TrxName());
		}
	}
	
	public String completeIt(){
		String msg = checkField();

		if (getDescription()==null || getProposedCorectiveAction()==null || getQADisposition()==null)
			msg = msg +" Please input field Decription, QA Disposition, and Proposed Corective Action.";

		processIt(msg, true);
		return msg;
	}
	
	public String rejectIt(){
		String msg = checkField();

		if (getDescription()==null || getProposedCorectiveAction()==null || getQADisposition()==null)
			msg = " Please input field Decription, QA Disposition, and Proposed Corective Action.";

		processIt(msg, false);
		return msg;
	}
	
	public MUNSQAStatusContainer getParent(){
		return new MUNSQAStatusContainer(getCtx(), getUNS_QAStatusContainer_ID(), get_TrxName());
	}
}
