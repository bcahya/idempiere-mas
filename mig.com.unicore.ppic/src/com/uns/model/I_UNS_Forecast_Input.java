/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package com.uns.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_Forecast_Input
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_Forecast_Input 
{

    /** TableName=UNS_Forecast_Input */
    public static final String Table_Name = "UNS_Forecast_Input";

    /** AD_Table_ID=1000119 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name ForecastMarketingAgt */
    public static final String COLUMNNAME_ForecastMarketingAgt = "ForecastMarketingAgt";

	/** Set Forecast Marketing Agt	  */
	public void setForecastMarketingAgt (BigDecimal ForecastMarketingAgt);

	/** Get Forecast Marketing Agt	  */
	public BigDecimal getForecastMarketingAgt();

    /** Column name ForecastMarketingAgtMT */
    public static final String COLUMNNAME_ForecastMarketingAgtMT = "ForecastMarketingAgtMT";

	/** Set Forecast Marketing Agt (MT)	  */
	public void setForecastMarketingAgtMT (BigDecimal ForecastMarketingAgtMT);

	/** Get Forecast Marketing Agt (MT)	  */
	public BigDecimal getForecastMarketingAgtMT();

    /** Column name ForecastMarketingApr */
    public static final String COLUMNNAME_ForecastMarketingApr = "ForecastMarketingApr";

	/** Set Forecast Marketing Apr	  */
	public void setForecastMarketingApr (BigDecimal ForecastMarketingApr);

	/** Get Forecast Marketing Apr	  */
	public BigDecimal getForecastMarketingApr();

    /** Column name ForecastMarketingAprMT */
    public static final String COLUMNNAME_ForecastMarketingAprMT = "ForecastMarketingAprMT";

	/** Set Forecast Marketing Apr (MT)	  */
	public void setForecastMarketingAprMT (BigDecimal ForecastMarketingAprMT);

	/** Get Forecast Marketing Apr (MT)	  */
	public BigDecimal getForecastMarketingAprMT();

    /** Column name ForecastMarketingDec */
    public static final String COLUMNNAME_ForecastMarketingDec = "ForecastMarketingDec";

	/** Set Forecast Marketing Dec	  */
	public void setForecastMarketingDec (BigDecimal ForecastMarketingDec);

	/** Get Forecast Marketing Dec	  */
	public BigDecimal getForecastMarketingDec();

    /** Column name ForecastMarketingDecMT */
    public static final String COLUMNNAME_ForecastMarketingDecMT = "ForecastMarketingDecMT";

	/** Set Forecast Marketing Dec (MT)	  */
	public void setForecastMarketingDecMT (BigDecimal ForecastMarketingDecMT);

	/** Get Forecast Marketing Dec (MT)	  */
	public BigDecimal getForecastMarketingDecMT();

    /** Column name ForecastMarketingFeb */
    public static final String COLUMNNAME_ForecastMarketingFeb = "ForecastMarketingFeb";

	/** Set Forecast Marketing Feb	  */
	public void setForecastMarketingFeb (BigDecimal ForecastMarketingFeb);

	/** Get Forecast Marketing Feb	  */
	public BigDecimal getForecastMarketingFeb();

    /** Column name ForecastMarketingFebMT */
    public static final String COLUMNNAME_ForecastMarketingFebMT = "ForecastMarketingFebMT";

	/** Set Forecast Marketing Feb (MT)	  */
	public void setForecastMarketingFebMT (BigDecimal ForecastMarketingFebMT);

	/** Get Forecast Marketing Feb (MT)	  */
	public BigDecimal getForecastMarketingFebMT();

    /** Column name ForecastMarketingJan */
    public static final String COLUMNNAME_ForecastMarketingJan = "ForecastMarketingJan";

	/** Set Forecast Marketing Jan	  */
	public void setForecastMarketingJan (BigDecimal ForecastMarketingJan);

	/** Get Forecast Marketing Jan	  */
	public BigDecimal getForecastMarketingJan();

    /** Column name ForecastMarketingJanMT */
    public static final String COLUMNNAME_ForecastMarketingJanMT = "ForecastMarketingJanMT";

	/** Set Forecast Marketing Jan (MT)	  */
	public void setForecastMarketingJanMT (BigDecimal ForecastMarketingJanMT);

	/** Get Forecast Marketing Jan (MT)	  */
	public BigDecimal getForecastMarketingJanMT();

    /** Column name ForecastMarketingJul */
    public static final String COLUMNNAME_ForecastMarketingJul = "ForecastMarketingJul";

	/** Set Forecast Marketing Jul	  */
	public void setForecastMarketingJul (BigDecimal ForecastMarketingJul);

	/** Get Forecast Marketing Jul	  */
	public BigDecimal getForecastMarketingJul();

    /** Column name ForecastMarketingJulMT */
    public static final String COLUMNNAME_ForecastMarketingJulMT = "ForecastMarketingJulMT";

	/** Set Forecast Marketing Jul (MT)	  */
	public void setForecastMarketingJulMT (BigDecimal ForecastMarketingJulMT);

	/** Get Forecast Marketing Jul (MT)	  */
	public BigDecimal getForecastMarketingJulMT();

    /** Column name ForecastMarketingJun */
    public static final String COLUMNNAME_ForecastMarketingJun = "ForecastMarketingJun";

	/** Set Forecast Marketing Jun	  */
	public void setForecastMarketingJun (BigDecimal ForecastMarketingJun);

	/** Get Forecast Marketing Jun	  */
	public BigDecimal getForecastMarketingJun();

    /** Column name ForecastMarketingJunMT */
    public static final String COLUMNNAME_ForecastMarketingJunMT = "ForecastMarketingJunMT";

	/** Set Forecast Marketing Jun (MT)	  */
	public void setForecastMarketingJunMT (BigDecimal ForecastMarketingJunMT);

	/** Get Forecast Marketing Jun (MT)	  */
	public BigDecimal getForecastMarketingJunMT();

    /** Column name ForecastMarketingMar */
    public static final String COLUMNNAME_ForecastMarketingMar = "ForecastMarketingMar";

	/** Set Forecast Marketing Mar	  */
	public void setForecastMarketingMar (BigDecimal ForecastMarketingMar);

	/** Get Forecast Marketing Mar	  */
	public BigDecimal getForecastMarketingMar();

    /** Column name ForecastMarketingMarMT */
    public static final String COLUMNNAME_ForecastMarketingMarMT = "ForecastMarketingMarMT";

	/** Set Forecast Marketing Mar (MT)	  */
	public void setForecastMarketingMarMT (BigDecimal ForecastMarketingMarMT);

	/** Get Forecast Marketing Mar (MT)	  */
	public BigDecimal getForecastMarketingMarMT();

    /** Column name ForecastMarketingMay */
    public static final String COLUMNNAME_ForecastMarketingMay = "ForecastMarketingMay";

	/** Set Forecast Marketing May	  */
	public void setForecastMarketingMay (BigDecimal ForecastMarketingMay);

	/** Get Forecast Marketing May	  */
	public BigDecimal getForecastMarketingMay();

    /** Column name ForecastMarketingMayMT */
    public static final String COLUMNNAME_ForecastMarketingMayMT = "ForecastMarketingMayMT";

	/** Set Forecast Marketing May (MT)	  */
	public void setForecastMarketingMayMT (BigDecimal ForecastMarketingMayMT);

	/** Get Forecast Marketing May (MT)	  */
	public BigDecimal getForecastMarketingMayMT();

    /** Column name ForecastMarketingNov */
    public static final String COLUMNNAME_ForecastMarketingNov = "ForecastMarketingNov";

	/** Set Forecast Marketing Nov	  */
	public void setForecastMarketingNov (BigDecimal ForecastMarketingNov);

	/** Get Forecast Marketing Nov	  */
	public BigDecimal getForecastMarketingNov();

    /** Column name ForecastMarketingNovMT */
    public static final String COLUMNNAME_ForecastMarketingNovMT = "ForecastMarketingNovMT";

	/** Set Forecast Marketing Nov (MT)	  */
	public void setForecastMarketingNovMT (BigDecimal ForecastMarketingNovMT);

	/** Get Forecast Marketing Nov (MT)	  */
	public BigDecimal getForecastMarketingNovMT();

    /** Column name ForecastMarketingOct */
    public static final String COLUMNNAME_ForecastMarketingOct = "ForecastMarketingOct";

	/** Set Forecast Marketing Oct	  */
	public void setForecastMarketingOct (BigDecimal ForecastMarketingOct);

	/** Get Forecast Marketing Oct	  */
	public BigDecimal getForecastMarketingOct();

    /** Column name ForecastMarketingOctMT */
    public static final String COLUMNNAME_ForecastMarketingOctMT = "ForecastMarketingOctMT";

	/** Set Forecast Marketing Oct (MT)	  */
	public void setForecastMarketingOctMT (BigDecimal ForecastMarketingOctMT);

	/** Get Forecast Marketing Oct (MT)	  */
	public BigDecimal getForecastMarketingOctMT();

    /** Column name ForecastMarketingSep */
    public static final String COLUMNNAME_ForecastMarketingSep = "ForecastMarketingSep";

	/** Set Forecast Marketing Sep	  */
	public void setForecastMarketingSep (BigDecimal ForecastMarketingSep);

	/** Get Forecast Marketing Sep	  */
	public BigDecimal getForecastMarketingSep();

    /** Column name ForecastMarketingSepMT */
    public static final String COLUMNNAME_ForecastMarketingSepMT = "ForecastMarketingSepMT";

	/** Set Forecast Marketing Sep (MT)	  */
	public void setForecastMarketingSepMT (BigDecimal ForecastMarketingSepMT);

	/** Get Forecast Marketing Sep (MT)	  */
	public BigDecimal getForecastMarketingSepMT();

    /** Column name ForecastRMPAgt */
    public static final String COLUMNNAME_ForecastRMPAgt = "ForecastRMPAgt";

	/** Set Forecast RMP Agt	  */
	public void setForecastRMPAgt (BigDecimal ForecastRMPAgt);

	/** Get Forecast RMP Agt	  */
	public BigDecimal getForecastRMPAgt();

    /** Column name ForecastRMPAgtMT */
    public static final String COLUMNNAME_ForecastRMPAgtMT = "ForecastRMPAgtMT";

	/** Set Forecast RMP Agt (MT)	  */
	public void setForecastRMPAgtMT (BigDecimal ForecastRMPAgtMT);

	/** Get Forecast RMP Agt (MT)	  */
	public BigDecimal getForecastRMPAgtMT();

    /** Column name ForecastRMPApr */
    public static final String COLUMNNAME_ForecastRMPApr = "ForecastRMPApr";

	/** Set Forecast RMP Apr	  */
	public void setForecastRMPApr (BigDecimal ForecastRMPApr);

	/** Get Forecast RMP Apr	  */
	public BigDecimal getForecastRMPApr();

    /** Column name ForecastRMPAprMT */
    public static final String COLUMNNAME_ForecastRMPAprMT = "ForecastRMPAprMT";

	/** Set Forecast RMP Apr (MT)	  */
	public void setForecastRMPAprMT (BigDecimal ForecastRMPAprMT);

	/** Get Forecast RMP Apr (MT)	  */
	public BigDecimal getForecastRMPAprMT();

    /** Column name ForecastRMPDec */
    public static final String COLUMNNAME_ForecastRMPDec = "ForecastRMPDec";

	/** Set Forecast RMP Dec	  */
	public void setForecastRMPDec (BigDecimal ForecastRMPDec);

	/** Get Forecast RMP Dec	  */
	public BigDecimal getForecastRMPDec();

    /** Column name ForecastRMPDecMT */
    public static final String COLUMNNAME_ForecastRMPDecMT = "ForecastRMPDecMT";

	/** Set Forecast RMP Dec (MT)	  */
	public void setForecastRMPDecMT (BigDecimal ForecastRMPDecMT);

	/** Get Forecast RMP Dec (MT)	  */
	public BigDecimal getForecastRMPDecMT();

    /** Column name ForecastRMPFeb */
    public static final String COLUMNNAME_ForecastRMPFeb = "ForecastRMPFeb";

	/** Set Forecast RMP Feb	  */
	public void setForecastRMPFeb (BigDecimal ForecastRMPFeb);

	/** Get Forecast RMP Feb	  */
	public BigDecimal getForecastRMPFeb();

    /** Column name ForecastRMPFebMT */
    public static final String COLUMNNAME_ForecastRMPFebMT = "ForecastRMPFebMT";

	/** Set Forecast RMP Feb (MT)	  */
	public void setForecastRMPFebMT (BigDecimal ForecastRMPFebMT);

	/** Get Forecast RMP Feb (MT)	  */
	public BigDecimal getForecastRMPFebMT();

    /** Column name ForecastRMPJan */
    public static final String COLUMNNAME_ForecastRMPJan = "ForecastRMPJan";

	/** Set Forecast RMP Jan	  */
	public void setForecastRMPJan (BigDecimal ForecastRMPJan);

	/** Get Forecast RMP Jan	  */
	public BigDecimal getForecastRMPJan();

    /** Column name ForecastRMPJanMT */
    public static final String COLUMNNAME_ForecastRMPJanMT = "ForecastRMPJanMT";

	/** Set Forecast RMP Jan (MT)	  */
	public void setForecastRMPJanMT (BigDecimal ForecastRMPJanMT);

	/** Get Forecast RMP Jan (MT)	  */
	public BigDecimal getForecastRMPJanMT();

    /** Column name ForecastRMPJul */
    public static final String COLUMNNAME_ForecastRMPJul = "ForecastRMPJul";

	/** Set Forecast RMP Jul	  */
	public void setForecastRMPJul (BigDecimal ForecastRMPJul);

	/** Get Forecast RMP Jul	  */
	public BigDecimal getForecastRMPJul();

    /** Column name ForecastRMPJulMT */
    public static final String COLUMNNAME_ForecastRMPJulMT = "ForecastRMPJulMT";

	/** Set Forecast RMP Jul (MT)	  */
	public void setForecastRMPJulMT (BigDecimal ForecastRMPJulMT);

	/** Get Forecast RMP Jul (MT)	  */
	public BigDecimal getForecastRMPJulMT();

    /** Column name ForecastRMPJun */
    public static final String COLUMNNAME_ForecastRMPJun = "ForecastRMPJun";

	/** Set Forecast RMP Jun	  */
	public void setForecastRMPJun (BigDecimal ForecastRMPJun);

	/** Get Forecast RMP Jun	  */
	public BigDecimal getForecastRMPJun();

    /** Column name ForecastRMPJunMT */
    public static final String COLUMNNAME_ForecastRMPJunMT = "ForecastRMPJunMT";

	/** Set Forecast RMP Jun (MT)	  */
	public void setForecastRMPJunMT (BigDecimal ForecastRMPJunMT);

	/** Get Forecast RMP Jun (MT)	  */
	public BigDecimal getForecastRMPJunMT();

    /** Column name ForecastRMPMar */
    public static final String COLUMNNAME_ForecastRMPMar = "ForecastRMPMar";

	/** Set Forecast RMP Mar	  */
	public void setForecastRMPMar (BigDecimal ForecastRMPMar);

	/** Get Forecast RMP Mar	  */
	public BigDecimal getForecastRMPMar();

    /** Column name ForecastRMPMarMT */
    public static final String COLUMNNAME_ForecastRMPMarMT = "ForecastRMPMarMT";

	/** Set Forecast RMP Mar (MT)	  */
	public void setForecastRMPMarMT (BigDecimal ForecastRMPMarMT);

	/** Get Forecast RMP Mar (MT)	  */
	public BigDecimal getForecastRMPMarMT();

    /** Column name ForecastRMPMay */
    public static final String COLUMNNAME_ForecastRMPMay = "ForecastRMPMay";

	/** Set Forecast RMP May	  */
	public void setForecastRMPMay (BigDecimal ForecastRMPMay);

	/** Get Forecast RMP May	  */
	public BigDecimal getForecastRMPMay();

    /** Column name ForecastRMPMayMT */
    public static final String COLUMNNAME_ForecastRMPMayMT = "ForecastRMPMayMT";

	/** Set Forecast RMP May (MT)	  */
	public void setForecastRMPMayMT (BigDecimal ForecastRMPMayMT);

	/** Get Forecast RMP May (MT)	  */
	public BigDecimal getForecastRMPMayMT();

    /** Column name ForecastRMPNov */
    public static final String COLUMNNAME_ForecastRMPNov = "ForecastRMPNov";

	/** Set Forecast RMP Nov	  */
	public void setForecastRMPNov (BigDecimal ForecastRMPNov);

	/** Get Forecast RMP Nov	  */
	public BigDecimal getForecastRMPNov();

    /** Column name ForecastRMPNovMT */
    public static final String COLUMNNAME_ForecastRMPNovMT = "ForecastRMPNovMT";

	/** Set Forecast RMP Nov (MT)	  */
	public void setForecastRMPNovMT (BigDecimal ForecastRMPNovMT);

	/** Get Forecast RMP Nov (MT)	  */
	public BigDecimal getForecastRMPNovMT();

    /** Column name ForecastRMPOct */
    public static final String COLUMNNAME_ForecastRMPOct = "ForecastRMPOct";

	/** Set Forecast RMP Oct	  */
	public void setForecastRMPOct (BigDecimal ForecastRMPOct);

	/** Get Forecast RMP Oct	  */
	public BigDecimal getForecastRMPOct();

    /** Column name ForecastRMPOctMT */
    public static final String COLUMNNAME_ForecastRMPOctMT = "ForecastRMPOctMT";

	/** Set Forecast RMP Oct (MT)	  */
	public void setForecastRMPOctMT (BigDecimal ForecastRMPOctMT);

	/** Get Forecast RMP Oct (MT)	  */
	public BigDecimal getForecastRMPOctMT();

    /** Column name ForecastRMPSep */
    public static final String COLUMNNAME_ForecastRMPSep = "ForecastRMPSep";

	/** Set Forecast RMP Sep	  */
	public void setForecastRMPSep (BigDecimal ForecastRMPSep);

	/** Get Forecast RMP Sep	  */
	public BigDecimal getForecastRMPSep();

    /** Column name ForecastRMPSepMT */
    public static final String COLUMNNAME_ForecastRMPSepMT = "ForecastRMPSepMT";

	/** Set Forecast RMP Sep (MT)	  */
	public void setForecastRMPSepMT (BigDecimal ForecastRMPSepMT);

	/** Get Forecast RMP Sep (MT)	  */
	public BigDecimal getForecastRMPSepMT();

    /** Column name ForecastTargetedAgt */
    public static final String COLUMNNAME_ForecastTargetedAgt = "ForecastTargetedAgt";

	/** Set Forecast Targeted Agt.
	  * Forecast Targeted August
	  */
	public void setForecastTargetedAgt (BigDecimal ForecastTargetedAgt);

	/** Get Forecast Targeted Agt.
	  * Forecast Targeted August
	  */
	public BigDecimal getForecastTargetedAgt();

    /** Column name ForecastTargetedAgtMT */
    public static final String COLUMNNAME_ForecastTargetedAgtMT = "ForecastTargetedAgtMT";

	/** Set Forecast Targeted Agt (MT).
	  * Forecast Targeted August
	  */
	public void setForecastTargetedAgtMT (BigDecimal ForecastTargetedAgtMT);

	/** Get Forecast Targeted Agt (MT).
	  * Forecast Targeted August
	  */
	public BigDecimal getForecastTargetedAgtMT();

    /** Column name ForecastTargetedApr */
    public static final String COLUMNNAME_ForecastTargetedApr = "ForecastTargetedApr";

	/** Set Forecast Targeted Apr.
	  * Forecast Targeted April
	  */
	public void setForecastTargetedApr (BigDecimal ForecastTargetedApr);

	/** Get Forecast Targeted Apr.
	  * Forecast Targeted April
	  */
	public BigDecimal getForecastTargetedApr();

    /** Column name ForecastTargetedAprMT */
    public static final String COLUMNNAME_ForecastTargetedAprMT = "ForecastTargetedAprMT";

	/** Set Forecast Targeted Apr (MT).
	  * Forecast Targeted April
	  */
	public void setForecastTargetedAprMT (BigDecimal ForecastTargetedAprMT);

	/** Get Forecast Targeted Apr (MT).
	  * Forecast Targeted April
	  */
	public BigDecimal getForecastTargetedAprMT();

    /** Column name ForecastTargetedDec */
    public static final String COLUMNNAME_ForecastTargetedDec = "ForecastTargetedDec";

	/** Set Forecast Targeted Dec.
	  * Forecast Targeted December
	  */
	public void setForecastTargetedDec (BigDecimal ForecastTargetedDec);

	/** Get Forecast Targeted Dec.
	  * Forecast Targeted December
	  */
	public BigDecimal getForecastTargetedDec();

    /** Column name ForecastTargetedDecMT */
    public static final String COLUMNNAME_ForecastTargetedDecMT = "ForecastTargetedDecMT";

	/** Set Forecast Targeted Dec (MT).
	  * Forecast Targeted December
	  */
	public void setForecastTargetedDecMT (BigDecimal ForecastTargetedDecMT);

	/** Get Forecast Targeted Dec (MT).
	  * Forecast Targeted December
	  */
	public BigDecimal getForecastTargetedDecMT();

    /** Column name ForecastTargetedFeb */
    public static final String COLUMNNAME_ForecastTargetedFeb = "ForecastTargetedFeb";

	/** Set Forecast Targeted Feb.
	  * Forecast Targeted February
	  */
	public void setForecastTargetedFeb (BigDecimal ForecastTargetedFeb);

	/** Get Forecast Targeted Feb.
	  * Forecast Targeted February
	  */
	public BigDecimal getForecastTargetedFeb();

    /** Column name ForecastTargetedFebMT */
    public static final String COLUMNNAME_ForecastTargetedFebMT = "ForecastTargetedFebMT";

	/** Set Forecast Targeted Feb (MT).
	  * Forecast Targeted February
	  */
	public void setForecastTargetedFebMT (BigDecimal ForecastTargetedFebMT);

	/** Get Forecast Targeted Feb (MT).
	  * Forecast Targeted February
	  */
	public BigDecimal getForecastTargetedFebMT();

    /** Column name ForecastTargetedJan */
    public static final String COLUMNNAME_ForecastTargetedJan = "ForecastTargetedJan";

	/** Set Forecast Targeted Jan.
	  * Forecast Targeted January
	  */
	public void setForecastTargetedJan (BigDecimal ForecastTargetedJan);

	/** Get Forecast Targeted Jan.
	  * Forecast Targeted January
	  */
	public BigDecimal getForecastTargetedJan();

    /** Column name ForecastTargetedJanMT */
    public static final String COLUMNNAME_ForecastTargetedJanMT = "ForecastTargetedJanMT";

	/** Set Forecast Targeted Jan (MT).
	  * Forecast Targeted January
	  */
	public void setForecastTargetedJanMT (BigDecimal ForecastTargetedJanMT);

	/** Get Forecast Targeted Jan (MT).
	  * Forecast Targeted January
	  */
	public BigDecimal getForecastTargetedJanMT();

    /** Column name ForecastTargetedJul */
    public static final String COLUMNNAME_ForecastTargetedJul = "ForecastTargetedJul";

	/** Set Forecast Targeted Jul.
	  * Forecast Targeted July
	  */
	public void setForecastTargetedJul (BigDecimal ForecastTargetedJul);

	/** Get Forecast Targeted Jul.
	  * Forecast Targeted July
	  */
	public BigDecimal getForecastTargetedJul();

    /** Column name ForecastTargetedJulMT */
    public static final String COLUMNNAME_ForecastTargetedJulMT = "ForecastTargetedJulMT";

	/** Set Forecast Targeted Jul (MT).
	  * Forecast Targeted July
	  */
	public void setForecastTargetedJulMT (BigDecimal ForecastTargetedJulMT);

	/** Get Forecast Targeted Jul (MT).
	  * Forecast Targeted July
	  */
	public BigDecimal getForecastTargetedJulMT();

    /** Column name ForecastTargetedJun */
    public static final String COLUMNNAME_ForecastTargetedJun = "ForecastTargetedJun";

	/** Set Forecast Targeted Jun.
	  * Forecast Targeted Juny
	  */
	public void setForecastTargetedJun (BigDecimal ForecastTargetedJun);

	/** Get Forecast Targeted Jun.
	  * Forecast Targeted Juny
	  */
	public BigDecimal getForecastTargetedJun();

    /** Column name ForecastTargetedJunMT */
    public static final String COLUMNNAME_ForecastTargetedJunMT = "ForecastTargetedJunMT";

	/** Set Forecast Targeted Jun (MT).
	  * Forecast Targeted Juny
	  */
	public void setForecastTargetedJunMT (BigDecimal ForecastTargetedJunMT);

	/** Get Forecast Targeted Jun (MT).
	  * Forecast Targeted Juny
	  */
	public BigDecimal getForecastTargetedJunMT();

    /** Column name ForecastTargetedMar */
    public static final String COLUMNNAME_ForecastTargetedMar = "ForecastTargetedMar";

	/** Set Forecast Targeted Mar.
	  * Forecast Targeted March
	  */
	public void setForecastTargetedMar (BigDecimal ForecastTargetedMar);

	/** Get Forecast Targeted Mar.
	  * Forecast Targeted March
	  */
	public BigDecimal getForecastTargetedMar();

    /** Column name ForecastTargetedMarMT */
    public static final String COLUMNNAME_ForecastTargetedMarMT = "ForecastTargetedMarMT";

	/** Set Forecast Targeted Mar (MT).
	  * Forecast Targeted March
	  */
	public void setForecastTargetedMarMT (BigDecimal ForecastTargetedMarMT);

	/** Get Forecast Targeted Mar (MT).
	  * Forecast Targeted March
	  */
	public BigDecimal getForecastTargetedMarMT();

    /** Column name ForecastTargetedMay */
    public static final String COLUMNNAME_ForecastTargetedMay = "ForecastTargetedMay";

	/** Set Forecast Targeted May.
	  * Forecast Targeted May
	  */
	public void setForecastTargetedMay (BigDecimal ForecastTargetedMay);

	/** Get Forecast Targeted May.
	  * Forecast Targeted May
	  */
	public BigDecimal getForecastTargetedMay();

    /** Column name ForecastTargetedMayMT */
    public static final String COLUMNNAME_ForecastTargetedMayMT = "ForecastTargetedMayMT";

	/** Set Forecast Targeted May (MT).
	  * Forecast Targeted May
	  */
	public void setForecastTargetedMayMT (BigDecimal ForecastTargetedMayMT);

	/** Get Forecast Targeted May (MT).
	  * Forecast Targeted May
	  */
	public BigDecimal getForecastTargetedMayMT();

    /** Column name ForecastTargetedNov */
    public static final String COLUMNNAME_ForecastTargetedNov = "ForecastTargetedNov";

	/** Set Forecast Targeted Nov.
	  * Forecast Targeted November
	  */
	public void setForecastTargetedNov (BigDecimal ForecastTargetedNov);

	/** Get Forecast Targeted Nov.
	  * Forecast Targeted November
	  */
	public BigDecimal getForecastTargetedNov();

    /** Column name ForecastTargetedNovMT */
    public static final String COLUMNNAME_ForecastTargetedNovMT = "ForecastTargetedNovMT";

	/** Set Forecast Targeted Nov (MT).
	  * Forecast Targeted November
	  */
	public void setForecastTargetedNovMT (BigDecimal ForecastTargetedNovMT);

	/** Get Forecast Targeted Nov (MT).
	  * Forecast Targeted November
	  */
	public BigDecimal getForecastTargetedNovMT();

    /** Column name ForecastTargetedOct */
    public static final String COLUMNNAME_ForecastTargetedOct = "ForecastTargetedOct";

	/** Set Forecast Targeted Oct.
	  * Forecast Targeted October
	  */
	public void setForecastTargetedOct (BigDecimal ForecastTargetedOct);

	/** Get Forecast Targeted Oct.
	  * Forecast Targeted October
	  */
	public BigDecimal getForecastTargetedOct();

    /** Column name ForecastTargetedOctMT */
    public static final String COLUMNNAME_ForecastTargetedOctMT = "ForecastTargetedOctMT";

	/** Set Forecast Targeted Oct (MT).
	  * Forecast Targeted October
	  */
	public void setForecastTargetedOctMT (BigDecimal ForecastTargetedOctMT);

	/** Get Forecast Targeted Oct (MT).
	  * Forecast Targeted October
	  */
	public BigDecimal getForecastTargetedOctMT();

    /** Column name ForecastTargetedSep */
    public static final String COLUMNNAME_ForecastTargetedSep = "ForecastTargetedSep";

	/** Set Forecast Targeted Sep.
	  * Forecast Targeted September
	  */
	public void setForecastTargetedSep (BigDecimal ForecastTargetedSep);

	/** Get Forecast Targeted Sep.
	  * Forecast Targeted September
	  */
	public BigDecimal getForecastTargetedSep();

    /** Column name ForecastTargetedSepMT */
    public static final String COLUMNNAME_ForecastTargetedSepMT = "ForecastTargetedSepMT";

	/** Set Forecast Targeted Sep (MT).
	  * Forecast Targeted September
	  */
	public void setForecastTargetedSepMT (BigDecimal ForecastTargetedSepMT);

	/** Get Forecast Targeted Sep (MT).
	  * Forecast Targeted September
	  */
	public BigDecimal getForecastTargetedSepMT();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name UNS_Forecast_Header_ID */
    public static final String COLUMNNAME_UNS_Forecast_Header_ID = "UNS_Forecast_Header_ID";

	/** Set Manufacturing Forecast	  */
	public void setUNS_Forecast_Header_ID (int UNS_Forecast_Header_ID);

	/** Get Manufacturing Forecast	  */
	public int getUNS_Forecast_Header_ID();

	public com.uns.model.I_UNS_Forecast_Header getUNS_Forecast_Header() throws RuntimeException;

    /** Column name UNS_Forecast_Input_ID */
    public static final String COLUMNNAME_UNS_Forecast_Input_ID = "UNS_Forecast_Input_ID";

	/** Set Forecast Input	  */
	public void setUNS_Forecast_Input_ID (int UNS_Forecast_Input_ID);

	/** Get Forecast Input	  */
	public int getUNS_Forecast_Input_ID();

    /** Column name UNS_Forecast_Input_UU */
    public static final String COLUMNNAME_UNS_Forecast_Input_UU = "UNS_Forecast_Input_UU";

	/** Set Forecast Input UU	  */
	public void setUNS_Forecast_Input_UU (String UNS_Forecast_Input_UU);

	/** Get Forecast Input UU	  */
	public String getUNS_Forecast_Input_UU();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
