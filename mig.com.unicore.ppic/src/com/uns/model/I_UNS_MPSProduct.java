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

/** Generated Interface for UNS_MPSProduct
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_MPSProduct 
{

    /** TableName=UNS_MPSProduct */
    public static final String Table_Name = "UNS_MPSProduct";

    /** AD_Table_ID=1000334 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name ActualManufacturedAprMT */
    public static final String COLUMNNAME_ActualManufacturedAprMT = "ActualManufacturedAprMT";

	/** Set Manufactured-Apr MT	  */
	public void setActualManufacturedAprMT (BigDecimal ActualManufacturedAprMT);

	/** Get Manufactured-Apr MT	  */
	public BigDecimal getActualManufacturedAprMT();

    /** Column name ActualManufacturedAprUOM */
    public static final String COLUMNNAME_ActualManufacturedAprUOM = "ActualManufacturedAprUOM";

	/** Set Manufactured-Apr	  */
	public void setActualManufacturedAprUOM (BigDecimal ActualManufacturedAprUOM);

	/** Get Manufactured-Apr	  */
	public BigDecimal getActualManufacturedAprUOM();

    /** Column name ActualManufacturedAugMT */
    public static final String COLUMNNAME_ActualManufacturedAugMT = "ActualManufacturedAugMT";

	/** Set Manufactured-Aug MT	  */
	public void setActualManufacturedAugMT (BigDecimal ActualManufacturedAugMT);

	/** Get Manufactured-Aug MT	  */
	public BigDecimal getActualManufacturedAugMT();

    /** Column name ActualManufacturedAugUOM */
    public static final String COLUMNNAME_ActualManufacturedAugUOM = "ActualManufacturedAugUOM";

	/** Set Manufactured-Aug	  */
	public void setActualManufacturedAugUOM (BigDecimal ActualManufacturedAugUOM);

	/** Get Manufactured-Aug	  */
	public BigDecimal getActualManufacturedAugUOM();

    /** Column name ActualManufacturedDecMT */
    public static final String COLUMNNAME_ActualManufacturedDecMT = "ActualManufacturedDecMT";

	/** Set Manufactured-Dec MT	  */
	public void setActualManufacturedDecMT (BigDecimal ActualManufacturedDecMT);

	/** Get Manufactured-Dec MT	  */
	public BigDecimal getActualManufacturedDecMT();

    /** Column name ActualManufacturedDecUOM */
    public static final String COLUMNNAME_ActualManufacturedDecUOM = "ActualManufacturedDecUOM";

	/** Set Manufactured-Dec	  */
	public void setActualManufacturedDecUOM (BigDecimal ActualManufacturedDecUOM);

	/** Get Manufactured-Dec	  */
	public BigDecimal getActualManufacturedDecUOM();

    /** Column name ActualManufacturedFebMT */
    public static final String COLUMNNAME_ActualManufacturedFebMT = "ActualManufacturedFebMT";

	/** Set Manufactured-Feb MT	  */
	public void setActualManufacturedFebMT (BigDecimal ActualManufacturedFebMT);

	/** Get Manufactured-Feb MT	  */
	public BigDecimal getActualManufacturedFebMT();

    /** Column name ActualManufacturedFebUOM */
    public static final String COLUMNNAME_ActualManufacturedFebUOM = "ActualManufacturedFebUOM";

	/** Set Manufactured-Feb	  */
	public void setActualManufacturedFebUOM (BigDecimal ActualManufacturedFebUOM);

	/** Get Manufactured-Feb	  */
	public BigDecimal getActualManufacturedFebUOM();

    /** Column name ActualManufacturedJanMT */
    public static final String COLUMNNAME_ActualManufacturedJanMT = "ActualManufacturedJanMT";

	/** Set Manufactured-Jan MT	  */
	public void setActualManufacturedJanMT (BigDecimal ActualManufacturedJanMT);

	/** Get Manufactured-Jan MT	  */
	public BigDecimal getActualManufacturedJanMT();

    /** Column name ActualManufacturedJanUOM */
    public static final String COLUMNNAME_ActualManufacturedJanUOM = "ActualManufacturedJanUOM";

	/** Set Manufactured-Jan	  */
	public void setActualManufacturedJanUOM (BigDecimal ActualManufacturedJanUOM);

	/** Get Manufactured-Jan	  */
	public BigDecimal getActualManufacturedJanUOM();

    /** Column name ActualManufacturedJulMT */
    public static final String COLUMNNAME_ActualManufacturedJulMT = "ActualManufacturedJulMT";

	/** Set Manufactured-Jul MT	  */
	public void setActualManufacturedJulMT (BigDecimal ActualManufacturedJulMT);

	/** Get Manufactured-Jul MT	  */
	public BigDecimal getActualManufacturedJulMT();

    /** Column name ActualManufacturedJulUOM */
    public static final String COLUMNNAME_ActualManufacturedJulUOM = "ActualManufacturedJulUOM";

	/** Set Manufactured-Jul	  */
	public void setActualManufacturedJulUOM (BigDecimal ActualManufacturedJulUOM);

	/** Get Manufactured-Jul	  */
	public BigDecimal getActualManufacturedJulUOM();

    /** Column name ActualManufacturedJunMT */
    public static final String COLUMNNAME_ActualManufacturedJunMT = "ActualManufacturedJunMT";

	/** Set Manufactured-Jun MT	  */
	public void setActualManufacturedJunMT (BigDecimal ActualManufacturedJunMT);

	/** Get Manufactured-Jun MT	  */
	public BigDecimal getActualManufacturedJunMT();

    /** Column name ActualManufacturedJunUOM */
    public static final String COLUMNNAME_ActualManufacturedJunUOM = "ActualManufacturedJunUOM";

	/** Set Manufactured-Jun	  */
	public void setActualManufacturedJunUOM (BigDecimal ActualManufacturedJunUOM);

	/** Get Manufactured-Jun	  */
	public BigDecimal getActualManufacturedJunUOM();

    /** Column name ActualManufacturedMarMT */
    public static final String COLUMNNAME_ActualManufacturedMarMT = "ActualManufacturedMarMT";

	/** Set Manufactured-Mar MT	  */
	public void setActualManufacturedMarMT (BigDecimal ActualManufacturedMarMT);

	/** Get Manufactured-Mar MT	  */
	public BigDecimal getActualManufacturedMarMT();

    /** Column name ActualManufacturedMarUOM */
    public static final String COLUMNNAME_ActualManufacturedMarUOM = "ActualManufacturedMarUOM";

	/** Set Manufactured-Mar	  */
	public void setActualManufacturedMarUOM (BigDecimal ActualManufacturedMarUOM);

	/** Get Manufactured-Mar	  */
	public BigDecimal getActualManufacturedMarUOM();

    /** Column name ActualManufacturedMayMT */
    public static final String COLUMNNAME_ActualManufacturedMayMT = "ActualManufacturedMayMT";

	/** Set Manufactured-May MT	  */
	public void setActualManufacturedMayMT (BigDecimal ActualManufacturedMayMT);

	/** Get Manufactured-May MT	  */
	public BigDecimal getActualManufacturedMayMT();

    /** Column name ActualManufacturedMayUOM */
    public static final String COLUMNNAME_ActualManufacturedMayUOM = "ActualManufacturedMayUOM";

	/** Set Manufactured-May	  */
	public void setActualManufacturedMayUOM (BigDecimal ActualManufacturedMayUOM);

	/** Get Manufactured-May	  */
	public BigDecimal getActualManufacturedMayUOM();

    /** Column name ActualManufacturedNovMT */
    public static final String COLUMNNAME_ActualManufacturedNovMT = "ActualManufacturedNovMT";

	/** Set Manufactured-Nov MT	  */
	public void setActualManufacturedNovMT (BigDecimal ActualManufacturedNovMT);

	/** Get Manufactured-Nov MT	  */
	public BigDecimal getActualManufacturedNovMT();

    /** Column name ActualManufacturedNovUOM */
    public static final String COLUMNNAME_ActualManufacturedNovUOM = "ActualManufacturedNovUOM";

	/** Set Manufactured-Nov	  */
	public void setActualManufacturedNovUOM (BigDecimal ActualManufacturedNovUOM);

	/** Get Manufactured-Nov	  */
	public BigDecimal getActualManufacturedNovUOM();

    /** Column name ActualManufacturedOctMT */
    public static final String COLUMNNAME_ActualManufacturedOctMT = "ActualManufacturedOctMT";

	/** Set Manufactured-Oct MT	  */
	public void setActualManufacturedOctMT (BigDecimal ActualManufacturedOctMT);

	/** Get Manufactured-Oct MT	  */
	public BigDecimal getActualManufacturedOctMT();

    /** Column name ActualManufacturedOctUOM */
    public static final String COLUMNNAME_ActualManufacturedOctUOM = "ActualManufacturedOctUOM";

	/** Set Manufactured-Oct	  */
	public void setActualManufacturedOctUOM (BigDecimal ActualManufacturedOctUOM);

	/** Get Manufactured-Oct	  */
	public BigDecimal getActualManufacturedOctUOM();

    /** Column name ActualManufacturedSepMT */
    public static final String COLUMNNAME_ActualManufacturedSepMT = "ActualManufacturedSepMT";

	/** Set Manufactured-Sep MT	  */
	public void setActualManufacturedSepMT (BigDecimal ActualManufacturedSepMT);

	/** Get Manufactured-Sep MT	  */
	public BigDecimal getActualManufacturedSepMT();

    /** Column name ActualManufacturedSepUOM */
    public static final String COLUMNNAME_ActualManufacturedSepUOM = "ActualManufacturedSepUOM";

	/** Set Manufactured-Sep	  */
	public void setActualManufacturedSepUOM (BigDecimal ActualManufacturedSepUOM);

	/** Get Manufactured-Sep	  */
	public BigDecimal getActualManufacturedSepUOM();

    /** Column name ActualProduced */
    public static final String COLUMNNAME_ActualProduced = "ActualProduced";

	/** Set Total Manufactured.
	  * Latest amount actually manufactured in UOM quantity
	  */
	public void setActualProduced (BigDecimal ActualProduced);

	/** Get Total Manufactured.
	  * Latest amount actually manufactured in UOM quantity
	  */
	public BigDecimal getActualProduced();

    /** Column name ActualProducedMT */
    public static final String COLUMNNAME_ActualProducedMT = "ActualProducedMT";

	/** Set T. Manufactured MT	  */
	public void setActualProducedMT (BigDecimal ActualProducedMT);

	/** Get T. Manufactured MT	  */
	public BigDecimal getActualProducedMT();

    /** Column name ActualScheduledAprMT */
    public static final String COLUMNNAME_ActualScheduledAprMT = "ActualScheduledAprMT";

	/** Set Scheduled-Apr MT	  */
	public void setActualScheduledAprMT (BigDecimal ActualScheduledAprMT);

	/** Get Scheduled-Apr MT	  */
	public BigDecimal getActualScheduledAprMT();

    /** Column name ActualScheduledAprUOM */
    public static final String COLUMNNAME_ActualScheduledAprUOM = "ActualScheduledAprUOM";

	/** Set Scheduled-Apr	  */
	public void setActualScheduledAprUOM (BigDecimal ActualScheduledAprUOM);

	/** Get Scheduled-Apr	  */
	public BigDecimal getActualScheduledAprUOM();

    /** Column name ActualScheduledAugMT */
    public static final String COLUMNNAME_ActualScheduledAugMT = "ActualScheduledAugMT";

	/** Set Scheduled-Aug MT	  */
	public void setActualScheduledAugMT (BigDecimal ActualScheduledAugMT);

	/** Get Scheduled-Aug MT	  */
	public BigDecimal getActualScheduledAugMT();

    /** Column name ActualScheduledAugUOM */
    public static final String COLUMNNAME_ActualScheduledAugUOM = "ActualScheduledAugUOM";

	/** Set Scheduled-Aug	  */
	public void setActualScheduledAugUOM (BigDecimal ActualScheduledAugUOM);

	/** Get Scheduled-Aug	  */
	public BigDecimal getActualScheduledAugUOM();

    /** Column name ActualScheduledDecMT */
    public static final String COLUMNNAME_ActualScheduledDecMT = "ActualScheduledDecMT";

	/** Set Scheduled-Dec MT	  */
	public void setActualScheduledDecMT (BigDecimal ActualScheduledDecMT);

	/** Get Scheduled-Dec MT	  */
	public BigDecimal getActualScheduledDecMT();

    /** Column name ActualScheduledDecUOM */
    public static final String COLUMNNAME_ActualScheduledDecUOM = "ActualScheduledDecUOM";

	/** Set Scheduled-Dec	  */
	public void setActualScheduledDecUOM (BigDecimal ActualScheduledDecUOM);

	/** Get Scheduled-Dec	  */
	public BigDecimal getActualScheduledDecUOM();

    /** Column name ActualScheduledFebMT */
    public static final String COLUMNNAME_ActualScheduledFebMT = "ActualScheduledFebMT";

	/** Set Scheduled-Feb MT	  */
	public void setActualScheduledFebMT (BigDecimal ActualScheduledFebMT);

	/** Get Scheduled-Feb MT	  */
	public BigDecimal getActualScheduledFebMT();

    /** Column name ActualScheduledFebUOM */
    public static final String COLUMNNAME_ActualScheduledFebUOM = "ActualScheduledFebUOM";

	/** Set Scheduled-Feb	  */
	public void setActualScheduledFebUOM (BigDecimal ActualScheduledFebUOM);

	/** Get Scheduled-Feb	  */
	public BigDecimal getActualScheduledFebUOM();

    /** Column name ActualScheduledJanMT */
    public static final String COLUMNNAME_ActualScheduledJanMT = "ActualScheduledJanMT";

	/** Set Scheduled-Jan MT	  */
	public void setActualScheduledJanMT (BigDecimal ActualScheduledJanMT);

	/** Get Scheduled-Jan MT	  */
	public BigDecimal getActualScheduledJanMT();

    /** Column name ActualScheduledJanUOM */
    public static final String COLUMNNAME_ActualScheduledJanUOM = "ActualScheduledJanUOM";

	/** Set Scheduled-Jan	  */
	public void setActualScheduledJanUOM (BigDecimal ActualScheduledJanUOM);

	/** Get Scheduled-Jan	  */
	public BigDecimal getActualScheduledJanUOM();

    /** Column name ActualScheduledJulMT */
    public static final String COLUMNNAME_ActualScheduledJulMT = "ActualScheduledJulMT";

	/** Set Scheduled-Jul MT	  */
	public void setActualScheduledJulMT (BigDecimal ActualScheduledJulMT);

	/** Get Scheduled-Jul MT	  */
	public BigDecimal getActualScheduledJulMT();

    /** Column name ActualScheduledJulUOM */
    public static final String COLUMNNAME_ActualScheduledJulUOM = "ActualScheduledJulUOM";

	/** Set Scheduled-Jul	  */
	public void setActualScheduledJulUOM (BigDecimal ActualScheduledJulUOM);

	/** Get Scheduled-Jul	  */
	public BigDecimal getActualScheduledJulUOM();

    /** Column name ActualScheduledJunMT */
    public static final String COLUMNNAME_ActualScheduledJunMT = "ActualScheduledJunMT";

	/** Set Scheduled-Jun MT	  */
	public void setActualScheduledJunMT (BigDecimal ActualScheduledJunMT);

	/** Get Scheduled-Jun MT	  */
	public BigDecimal getActualScheduledJunMT();

    /** Column name ActualScheduledJunUOM */
    public static final String COLUMNNAME_ActualScheduledJunUOM = "ActualScheduledJunUOM";

	/** Set Scheduled-Jun	  */
	public void setActualScheduledJunUOM (BigDecimal ActualScheduledJunUOM);

	/** Get Scheduled-Jun	  */
	public BigDecimal getActualScheduledJunUOM();

    /** Column name ActualScheduledMarMT */
    public static final String COLUMNNAME_ActualScheduledMarMT = "ActualScheduledMarMT";

	/** Set Scheduled-Mar MT	  */
	public void setActualScheduledMarMT (BigDecimal ActualScheduledMarMT);

	/** Get Scheduled-Mar MT	  */
	public BigDecimal getActualScheduledMarMT();

    /** Column name ActualScheduledMarUOM */
    public static final String COLUMNNAME_ActualScheduledMarUOM = "ActualScheduledMarUOM";

	/** Set Scheduled-Mar	  */
	public void setActualScheduledMarUOM (BigDecimal ActualScheduledMarUOM);

	/** Get Scheduled-Mar	  */
	public BigDecimal getActualScheduledMarUOM();

    /** Column name ActualScheduledMayMT */
    public static final String COLUMNNAME_ActualScheduledMayMT = "ActualScheduledMayMT";

	/** Set Scheduled-May MT	  */
	public void setActualScheduledMayMT (BigDecimal ActualScheduledMayMT);

	/** Get Scheduled-May MT	  */
	public BigDecimal getActualScheduledMayMT();

    /** Column name ActualScheduledMayUOM */
    public static final String COLUMNNAME_ActualScheduledMayUOM = "ActualScheduledMayUOM";

	/** Set Scheduled-May	  */
	public void setActualScheduledMayUOM (BigDecimal ActualScheduledMayUOM);

	/** Get Scheduled-May	  */
	public BigDecimal getActualScheduledMayUOM();

    /** Column name ActualScheduledMT */
    public static final String COLUMNNAME_ActualScheduledMT = "ActualScheduledMT";

	/** Set T. Scheduled (MT).
	  * Total Actual Scheduled (MT)
	  */
	public void setActualScheduledMT (BigDecimal ActualScheduledMT);

	/** Get T. Scheduled (MT).
	  * Total Actual Scheduled (MT)
	  */
	public BigDecimal getActualScheduledMT();

    /** Column name ActualScheduledNovMT */
    public static final String COLUMNNAME_ActualScheduledNovMT = "ActualScheduledNovMT";

	/** Set Scheduled-Nov MT	  */
	public void setActualScheduledNovMT (BigDecimal ActualScheduledNovMT);

	/** Get Scheduled-Nov MT	  */
	public BigDecimal getActualScheduledNovMT();

    /** Column name ActualScheduledNovUOM */
    public static final String COLUMNNAME_ActualScheduledNovUOM = "ActualScheduledNovUOM";

	/** Set Scheduled-Nov	  */
	public void setActualScheduledNovUOM (BigDecimal ActualScheduledNovUOM);

	/** Get Scheduled-Nov	  */
	public BigDecimal getActualScheduledNovUOM();

    /** Column name ActualScheduledOctMT */
    public static final String COLUMNNAME_ActualScheduledOctMT = "ActualScheduledOctMT";

	/** Set Scheduled-Oct MT	  */
	public void setActualScheduledOctMT (BigDecimal ActualScheduledOctMT);

	/** Get Scheduled-Oct MT	  */
	public BigDecimal getActualScheduledOctMT();

    /** Column name ActualScheduledOctUOM */
    public static final String COLUMNNAME_ActualScheduledOctUOM = "ActualScheduledOctUOM";

	/** Set Scheduled-Oct	  */
	public void setActualScheduledOctUOM (BigDecimal ActualScheduledOctUOM);

	/** Get Scheduled-Oct	  */
	public BigDecimal getActualScheduledOctUOM();

    /** Column name ActualScheduledSepMT */
    public static final String COLUMNNAME_ActualScheduledSepMT = "ActualScheduledSepMT";

	/** Set Scheduled-Sep MT	  */
	public void setActualScheduledSepMT (BigDecimal ActualScheduledSepMT);

	/** Get Scheduled-Sep MT	  */
	public BigDecimal getActualScheduledSepMT();

    /** Column name ActualScheduledSepUOM */
    public static final String COLUMNNAME_ActualScheduledSepUOM = "ActualScheduledSepUOM";

	/** Set Scheduled-Sep	  */
	public void setActualScheduledSepUOM (BigDecimal ActualScheduledSepUOM);

	/** Get Scheduled-Sep	  */
	public BigDecimal getActualScheduledSepUOM();

    /** Column name ActualScheduledUOM */
    public static final String COLUMNNAME_ActualScheduledUOM = "ActualScheduledUOM";

	/** Set Total Scheduled.
	  * Total Actual Scheduled (UOM)
	  */
	public void setActualScheduledUOM (BigDecimal ActualScheduledUOM);

	/** Get Total Scheduled.
	  * Total Actual Scheduled (UOM)
	  */
	public BigDecimal getActualScheduledUOM();

    /** Column name ActualToOrderAprMT */
    public static final String COLUMNNAME_ActualToOrderAprMT = "ActualToOrderAprMT";

	/** Set Actual Order-Apr.
	  * Actual To Order Apr (MT)
	  */
	public void setActualToOrderAprMT (BigDecimal ActualToOrderAprMT);

	/** Get Actual Order-Apr.
	  * Actual To Order Apr (MT)
	  */
	public BigDecimal getActualToOrderAprMT();

    /** Column name ActualToOrderAprUOM */
    public static final String COLUMNNAME_ActualToOrderAprUOM = "ActualToOrderAprUOM";

	/** Set Actual Order-Apr.
	  * Actual To Order Apr (UOM)
	  */
	public void setActualToOrderAprUOM (BigDecimal ActualToOrderAprUOM);

	/** Get Actual Order-Apr.
	  * Actual To Order Apr (UOM)
	  */
	public BigDecimal getActualToOrderAprUOM();

    /** Column name ActualToOrderAugMT */
    public static final String COLUMNNAME_ActualToOrderAugMT = "ActualToOrderAugMT";

	/** Set Actual Order-Aug.
	  * Actual To Order Aug (MT)
	  */
	public void setActualToOrderAugMT (BigDecimal ActualToOrderAugMT);

	/** Get Actual Order-Aug.
	  * Actual To Order Aug (MT)
	  */
	public BigDecimal getActualToOrderAugMT();

    /** Column name ActualToOrderAugUOM */
    public static final String COLUMNNAME_ActualToOrderAugUOM = "ActualToOrderAugUOM";

	/** Set Actual Order-Aug.
	  * Actual To Order Aug (UOM)
	  */
	public void setActualToOrderAugUOM (BigDecimal ActualToOrderAugUOM);

	/** Get Actual Order-Aug.
	  * Actual To Order Aug (UOM)
	  */
	public BigDecimal getActualToOrderAugUOM();

    /** Column name ActualToOrderDecMT */
    public static final String COLUMNNAME_ActualToOrderDecMT = "ActualToOrderDecMT";

	/** Set Actual Order-Dec.
	  * Actual To Order Dec (MT)
	  */
	public void setActualToOrderDecMT (BigDecimal ActualToOrderDecMT);

	/** Get Actual Order-Dec.
	  * Actual To Order Dec (MT)
	  */
	public BigDecimal getActualToOrderDecMT();

    /** Column name ActualToOrderDecUOM */
    public static final String COLUMNNAME_ActualToOrderDecUOM = "ActualToOrderDecUOM";

	/** Set Actual Order-Dec.
	  * Actual To Order Dec (UOM)
	  */
	public void setActualToOrderDecUOM (BigDecimal ActualToOrderDecUOM);

	/** Get Actual Order-Dec.
	  * Actual To Order Dec (UOM)
	  */
	public BigDecimal getActualToOrderDecUOM();

    /** Column name ActualToOrderFebMT */
    public static final String COLUMNNAME_ActualToOrderFebMT = "ActualToOrderFebMT";

	/** Set Actual Order-Feb.
	  * Actual To Order Feb (MT)
	  */
	public void setActualToOrderFebMT (BigDecimal ActualToOrderFebMT);

	/** Get Actual Order-Feb.
	  * Actual To Order Feb (MT)
	  */
	public BigDecimal getActualToOrderFebMT();

    /** Column name ActualToOrderFebUOM */
    public static final String COLUMNNAME_ActualToOrderFebUOM = "ActualToOrderFebUOM";

	/** Set Actual Order-Feb.
	  * Actual To Order Feb (UOM)
	  */
	public void setActualToOrderFebUOM (BigDecimal ActualToOrderFebUOM);

	/** Get Actual Order-Feb.
	  * Actual To Order Feb (UOM)
	  */
	public BigDecimal getActualToOrderFebUOM();

    /** Column name ActualToOrderJanMT */
    public static final String COLUMNNAME_ActualToOrderJanMT = "ActualToOrderJanMT";

	/** Set Actual Order-Jan.
	  * Actual To Order Jan (MT)
	  */
	public void setActualToOrderJanMT (BigDecimal ActualToOrderJanMT);

	/** Get Actual Order-Jan.
	  * Actual To Order Jan (MT)
	  */
	public BigDecimal getActualToOrderJanMT();

    /** Column name ActualToOrderJanUOM */
    public static final String COLUMNNAME_ActualToOrderJanUOM = "ActualToOrderJanUOM";

	/** Set Actual Order-Jan.
	  * Actual To Order Jan (UOM)
	  */
	public void setActualToOrderJanUOM (BigDecimal ActualToOrderJanUOM);

	/** Get Actual Order-Jan.
	  * Actual To Order Jan (UOM)
	  */
	public BigDecimal getActualToOrderJanUOM();

    /** Column name ActualToOrderJulMT */
    public static final String COLUMNNAME_ActualToOrderJulMT = "ActualToOrderJulMT";

	/** Set Actual Order-Jul.
	  * Actual To Order Jul (MT)
	  */
	public void setActualToOrderJulMT (BigDecimal ActualToOrderJulMT);

	/** Get Actual Order-Jul.
	  * Actual To Order Jul (MT)
	  */
	public BigDecimal getActualToOrderJulMT();

    /** Column name ActualToOrderJulUOM */
    public static final String COLUMNNAME_ActualToOrderJulUOM = "ActualToOrderJulUOM";

	/** Set Actual Order-Jul.
	  * Actual To Order Jul (UOM)
	  */
	public void setActualToOrderJulUOM (BigDecimal ActualToOrderJulUOM);

	/** Get Actual Order-Jul.
	  * Actual To Order Jul (UOM)
	  */
	public BigDecimal getActualToOrderJulUOM();

    /** Column name ActualToOrderJunMT */
    public static final String COLUMNNAME_ActualToOrderJunMT = "ActualToOrderJunMT";

	/** Set Actual Order-Jun.
	  * Actual To Order Jun (MT)
	  */
	public void setActualToOrderJunMT (BigDecimal ActualToOrderJunMT);

	/** Get Actual Order-Jun.
	  * Actual To Order Jun (MT)
	  */
	public BigDecimal getActualToOrderJunMT();

    /** Column name ActualToOrderJunUOM */
    public static final String COLUMNNAME_ActualToOrderJunUOM = "ActualToOrderJunUOM";

	/** Set Actual Order-Jun.
	  * Actual To Order Jun (UOM)
	  */
	public void setActualToOrderJunUOM (BigDecimal ActualToOrderJunUOM);

	/** Get Actual Order-Jun.
	  * Actual To Order Jun (UOM)
	  */
	public BigDecimal getActualToOrderJunUOM();

    /** Column name ActualToOrderMarMT */
    public static final String COLUMNNAME_ActualToOrderMarMT = "ActualToOrderMarMT";

	/** Set Actual Order-Mar.
	  * Actual To Order Mar (MT)
	  */
	public void setActualToOrderMarMT (BigDecimal ActualToOrderMarMT);

	/** Get Actual Order-Mar.
	  * Actual To Order Mar (MT)
	  */
	public BigDecimal getActualToOrderMarMT();

    /** Column name ActualToOrderMarUOM */
    public static final String COLUMNNAME_ActualToOrderMarUOM = "ActualToOrderMarUOM";

	/** Set Actual Order-Mar.
	  * Actual To Order Mar (UOM)
	  */
	public void setActualToOrderMarUOM (BigDecimal ActualToOrderMarUOM);

	/** Get Actual Order-Mar.
	  * Actual To Order Mar (UOM)
	  */
	public BigDecimal getActualToOrderMarUOM();

    /** Column name ActualToOrderMayMT */
    public static final String COLUMNNAME_ActualToOrderMayMT = "ActualToOrderMayMT";

	/** Set Actual Order-May.
	  * Actual To Order May (MT)
	  */
	public void setActualToOrderMayMT (BigDecimal ActualToOrderMayMT);

	/** Get Actual Order-May.
	  * Actual To Order May (MT)
	  */
	public BigDecimal getActualToOrderMayMT();

    /** Column name ActualToOrderMayUOM */
    public static final String COLUMNNAME_ActualToOrderMayUOM = "ActualToOrderMayUOM";

	/** Set Actual Order-May.
	  * Actual To Order May (UOM)
	  */
	public void setActualToOrderMayUOM (BigDecimal ActualToOrderMayUOM);

	/** Get Actual Order-May.
	  * Actual To Order May (UOM)
	  */
	public BigDecimal getActualToOrderMayUOM();

    /** Column name ActualToOrderNovMT */
    public static final String COLUMNNAME_ActualToOrderNovMT = "ActualToOrderNovMT";

	/** Set Actual Order-Nov.
	  * Actual To Order Nov (MT)
	  */
	public void setActualToOrderNovMT (BigDecimal ActualToOrderNovMT);

	/** Get Actual Order-Nov.
	  * Actual To Order Nov (MT)
	  */
	public BigDecimal getActualToOrderNovMT();

    /** Column name ActualToOrderNovUOM */
    public static final String COLUMNNAME_ActualToOrderNovUOM = "ActualToOrderNovUOM";

	/** Set Actual Order-Nov.
	  * Actual To Order Nov (UOM)
	  */
	public void setActualToOrderNovUOM (BigDecimal ActualToOrderNovUOM);

	/** Get Actual Order-Nov.
	  * Actual To Order Nov (UOM)
	  */
	public BigDecimal getActualToOrderNovUOM();

    /** Column name ActualToOrderOctMT */
    public static final String COLUMNNAME_ActualToOrderOctMT = "ActualToOrderOctMT";

	/** Set Actual Order-Oct.
	  * Actual To Order Oct (MT)
	  */
	public void setActualToOrderOctMT (BigDecimal ActualToOrderOctMT);

	/** Get Actual Order-Oct.
	  * Actual To Order Oct (MT)
	  */
	public BigDecimal getActualToOrderOctMT();

    /** Column name ActualToOrderOctUOM */
    public static final String COLUMNNAME_ActualToOrderOctUOM = "ActualToOrderOctUOM";

	/** Set Actual Order-Oct.
	  * Actual To Order Oct (UOM)
	  */
	public void setActualToOrderOctUOM (BigDecimal ActualToOrderOctUOM);

	/** Get Actual Order-Oct.
	  * Actual To Order Oct (UOM)
	  */
	public BigDecimal getActualToOrderOctUOM();

    /** Column name ActualToOrderSepMT */
    public static final String COLUMNNAME_ActualToOrderSepMT = "ActualToOrderSepMT";

	/** Set Actual Order-Sep.
	  * Actual To Order Sep (MT)
	  */
	public void setActualToOrderSepMT (BigDecimal ActualToOrderSepMT);

	/** Get Actual Order-Sep.
	  * Actual To Order Sep (MT)
	  */
	public BigDecimal getActualToOrderSepMT();

    /** Column name ActualToOrderSepUOM */
    public static final String COLUMNNAME_ActualToOrderSepUOM = "ActualToOrderSepUOM";

	/** Set Actual Order-Sep.
	  * Actual To Order Sep (UOM)
	  */
	public void setActualToOrderSepUOM (BigDecimal ActualToOrderSepUOM);

	/** Get Actual Order-Sep.
	  * Actual To Order Sep (UOM)
	  */
	public BigDecimal getActualToOrderSepUOM();

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

    /** Column name IncubationDays */
    public static final String COLUMNNAME_IncubationDays = "IncubationDays";

	/** Set Incubation Days.
	  * Incubation period of product in days
	  */
	public void setIncubationDays (int IncubationDays);

	/** Get Incubation Days.
	  * Incubation period of product in days
	  */
	public int getIncubationDays();

    /** Column name InitialProjectedStock_OnHand */
    public static final String COLUMNNAME_InitialProjectedStock_OnHand = "InitialProjectedStock_OnHand";

	/** Set Initial Stock On Hand	  */
	public void setInitialProjectedStock_OnHand (BigDecimal InitialProjectedStock_OnHand);

	/** Get Initial Stock On Hand	  */
	public BigDecimal getInitialProjectedStock_OnHand();

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

    /** Column name IsGenerate */
    public static final String COLUMNNAME_IsGenerate = "IsGenerate";

	/** Set Generated	  */
	public void setIsGenerate (boolean IsGenerate);

	/** Get Generated	  */
	public boolean isGenerate();

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

    /** Column name QtyMT_Agt */
    public static final String COLUMNNAME_QtyMT_Agt = "QtyMT_Agt";

	/** Set F. Demand-Aug.
	  * Quantity In Matric Ton On August
	  */
	public void setQtyMT_Agt (BigDecimal QtyMT_Agt);

	/** Get F. Demand-Aug.
	  * Quantity In Matric Ton On August
	  */
	public BigDecimal getQtyMT_Agt();

    /** Column name QtyMT_Apr */
    public static final String COLUMNNAME_QtyMT_Apr = "QtyMT_Apr";

	/** Set F. Demand-Apr.
	  * Quantity In Matric Ton On April
	  */
	public void setQtyMT_Apr (BigDecimal QtyMT_Apr);

	/** Get F. Demand-Apr.
	  * Quantity In Matric Ton On April
	  */
	public BigDecimal getQtyMT_Apr();

    /** Column name QtyMT_Dec */
    public static final String COLUMNNAME_QtyMT_Dec = "QtyMT_Dec";

	/** Set F. Demand-Dec.
	  * Quantity In Matric Ton On December
	  */
	public void setQtyMT_Dec (BigDecimal QtyMT_Dec);

	/** Get F. Demand-Dec.
	  * Quantity In Matric Ton On December
	  */
	public BigDecimal getQtyMT_Dec();

    /** Column name QtyMT_Feb */
    public static final String COLUMNNAME_QtyMT_Feb = "QtyMT_Feb";

	/** Set F. Demand-Feb.
	  * Quantity In Matric Ton On February
	  */
	public void setQtyMT_Feb (BigDecimal QtyMT_Feb);

	/** Get F. Demand-Feb.
	  * Quantity In Matric Ton On February
	  */
	public BigDecimal getQtyMT_Feb();

    /** Column name QtyMT_Jan */
    public static final String COLUMNNAME_QtyMT_Jan = "QtyMT_Jan";

	/** Set F. Demand-Jan.
	  * Quantity In Matric Ton On January
	  */
	public void setQtyMT_Jan (BigDecimal QtyMT_Jan);

	/** Get F. Demand-Jan.
	  * Quantity In Matric Ton On January
	  */
	public BigDecimal getQtyMT_Jan();

    /** Column name QtyMT_Jul */
    public static final String COLUMNNAME_QtyMT_Jul = "QtyMT_Jul";

	/** Set F. Demand-Jul.
	  * Quantity In Matric Ton On July
	  */
	public void setQtyMT_Jul (BigDecimal QtyMT_Jul);

	/** Get F. Demand-Jul.
	  * Quantity In Matric Ton On July
	  */
	public BigDecimal getQtyMT_Jul();

    /** Column name QtyMT_Jun */
    public static final String COLUMNNAME_QtyMT_Jun = "QtyMT_Jun";

	/** Set F. Demand-Jun.
	  * Quantity In Matric Ton On Juny
	  */
	public void setQtyMT_Jun (BigDecimal QtyMT_Jun);

	/** Get F. Demand-Jun.
	  * Quantity In Matric Ton On Juny
	  */
	public BigDecimal getQtyMT_Jun();

    /** Column name QtyMT_Mar */
    public static final String COLUMNNAME_QtyMT_Mar = "QtyMT_Mar";

	/** Set F. Demand-Mar.
	  * Quantity In Matric Ton On March
	  */
	public void setQtyMT_Mar (BigDecimal QtyMT_Mar);

	/** Get F. Demand-Mar.
	  * Quantity In Matric Ton On March
	  */
	public BigDecimal getQtyMT_Mar();

    /** Column name QtyMT_May */
    public static final String COLUMNNAME_QtyMT_May = "QtyMT_May";

	/** Set F. Demand-May.
	  * Quantity In Matric Ton On May
	  */
	public void setQtyMT_May (BigDecimal QtyMT_May);

	/** Get F. Demand-May.
	  * Quantity In Matric Ton On May
	  */
	public BigDecimal getQtyMT_May();

    /** Column name QtyMT_Nov */
    public static final String COLUMNNAME_QtyMT_Nov = "QtyMT_Nov";

	/** Set F. Demand-Nov.
	  * Quantity In Matric Ton On November
	  */
	public void setQtyMT_Nov (BigDecimal QtyMT_Nov);

	/** Get F. Demand-Nov.
	  * Quantity In Matric Ton On November
	  */
	public BigDecimal getQtyMT_Nov();

    /** Column name QtyMT_Oct */
    public static final String COLUMNNAME_QtyMT_Oct = "QtyMT_Oct";

	/** Set F. Demand-Oct.
	  * Quantity In Matric Ton On October
	  */
	public void setQtyMT_Oct (BigDecimal QtyMT_Oct);

	/** Get F. Demand-Oct.
	  * Quantity In Matric Ton On October
	  */
	public BigDecimal getQtyMT_Oct();

    /** Column name QtyMT_Sep */
    public static final String COLUMNNAME_QtyMT_Sep = "QtyMT_Sep";

	/** Set F. Demand-Sep.
	  * Quantity In Matric Ton On September
	  */
	public void setQtyMT_Sep (BigDecimal QtyMT_Sep);

	/** Get F. Demand-Sep.
	  * Quantity In Matric Ton On September
	  */
	public BigDecimal getQtyMT_Sep();

    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/** Set On Hand Quantity.
	  * Actual On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand);

	/** Get On Hand Quantity.
	  * Actual On Hand Quantity
	  */
	public BigDecimal getQtyOnHand();

    /** Column name QtyUOM_Agt */
    public static final String COLUMNNAME_QtyUOM_Agt = "QtyUOM_Agt";

	/** Set F. Demand-Aug	  */
	public void setQtyUOM_Agt (BigDecimal QtyUOM_Agt);

	/** Get F. Demand-Aug	  */
	public BigDecimal getQtyUOM_Agt();

    /** Column name QtyUOM_Apr */
    public static final String COLUMNNAME_QtyUOM_Apr = "QtyUOM_Apr";

	/** Set F. Demand-Apr	  */
	public void setQtyUOM_Apr (BigDecimal QtyUOM_Apr);

	/** Get F. Demand-Apr	  */
	public BigDecimal getQtyUOM_Apr();

    /** Column name QtyUOM_Dec */
    public static final String COLUMNNAME_QtyUOM_Dec = "QtyUOM_Dec";

	/** Set F. Demand-Dec	  */
	public void setQtyUOM_Dec (BigDecimal QtyUOM_Dec);

	/** Get F. Demand-Dec	  */
	public BigDecimal getQtyUOM_Dec();

    /** Column name QtyUOM_Feb */
    public static final String COLUMNNAME_QtyUOM_Feb = "QtyUOM_Feb";

	/** Set F. Demand-Feb	  */
	public void setQtyUOM_Feb (BigDecimal QtyUOM_Feb);

	/** Get F. Demand-Feb	  */
	public BigDecimal getQtyUOM_Feb();

    /** Column name QtyUOM_Jan */
    public static final String COLUMNNAME_QtyUOM_Jan = "QtyUOM_Jan";

	/** Set F. Demand-Jan	  */
	public void setQtyUOM_Jan (BigDecimal QtyUOM_Jan);

	/** Get F. Demand-Jan	  */
	public BigDecimal getQtyUOM_Jan();

    /** Column name QtyUOM_Jul */
    public static final String COLUMNNAME_QtyUOM_Jul = "QtyUOM_Jul";

	/** Set F. Demand-Jul	  */
	public void setQtyUOM_Jul (BigDecimal QtyUOM_Jul);

	/** Get F. Demand-Jul	  */
	public BigDecimal getQtyUOM_Jul();

    /** Column name QtyUOM_Jun */
    public static final String COLUMNNAME_QtyUOM_Jun = "QtyUOM_Jun";

	/** Set F. Demand-Jun	  */
	public void setQtyUOM_Jun (BigDecimal QtyUOM_Jun);

	/** Get F. Demand-Jun	  */
	public BigDecimal getQtyUOM_Jun();

    /** Column name QtyUOM_Mar */
    public static final String COLUMNNAME_QtyUOM_Mar = "QtyUOM_Mar";

	/** Set F. Demand-Mar	  */
	public void setQtyUOM_Mar (BigDecimal QtyUOM_Mar);

	/** Get F. Demand-Mar	  */
	public BigDecimal getQtyUOM_Mar();

    /** Column name QtyUOM_May */
    public static final String COLUMNNAME_QtyUOM_May = "QtyUOM_May";

	/** Set F. Demand-May	  */
	public void setQtyUOM_May (BigDecimal QtyUOM_May);

	/** Get F. Demand-May	  */
	public BigDecimal getQtyUOM_May();

    /** Column name QtyUOM_Nov */
    public static final String COLUMNNAME_QtyUOM_Nov = "QtyUOM_Nov";

	/** Set F. Demand-Nov	  */
	public void setQtyUOM_Nov (BigDecimal QtyUOM_Nov);

	/** Get F. Demand-Nov	  */
	public BigDecimal getQtyUOM_Nov();

    /** Column name QtyUOM_Oct */
    public static final String COLUMNNAME_QtyUOM_Oct = "QtyUOM_Oct";

	/** Set F. Demand-Oct	  */
	public void setQtyUOM_Oct (BigDecimal QtyUOM_Oct);

	/** Get F. Demand-Oct	  */
	public BigDecimal getQtyUOM_Oct();

    /** Column name QtyUOM_Sep */
    public static final String COLUMNNAME_QtyUOM_Sep = "QtyUOM_Sep";

	/** Set F. Demand-Sep	  */
	public void setQtyUOM_Sep (BigDecimal QtyUOM_Sep);

	/** Get F. Demand-Sep	  */
	public BigDecimal getQtyUOM_Sep();

    /** Column name SafetyStock */
    public static final String COLUMNNAME_SafetyStock = "SafetyStock";

	/** Set Safety Stock Qty.
	  * Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public void setSafetyStock (BigDecimal SafetyStock);

	/** Get Safety Stock Qty.
	  * Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public BigDecimal getSafetyStock();

    /** Column name SOH */
    public static final String COLUMNNAME_SOH = "SOH";

	/** Set SOH.
	  * Scheduled (Stock) On Hand
	  */
	public void setSOH (BigDecimal SOH);

	/** Get SOH.
	  * Scheduled (Stock) On Hand
	  */
	public BigDecimal getSOH();

    /** Column name TotalActualToOrderMT */
    public static final String COLUMNNAME_TotalActualToOrderMT = "TotalActualToOrderMT";

	/** Set T. Actual Order (MT).
	  * Total Actual To Order 
	  */
	public void setTotalActualToOrderMT (BigDecimal TotalActualToOrderMT);

	/** Get T. Actual Order (MT).
	  * Total Actual To Order 
	  */
	public BigDecimal getTotalActualToOrderMT();

    /** Column name TotalActualToOrderUOM */
    public static final String COLUMNNAME_TotalActualToOrderUOM = "TotalActualToOrderUOM";

	/** Set T. Actual Order.
	  * Total Actual To Order 
	  */
	public void setTotalActualToOrderUOM (BigDecimal TotalActualToOrderUOM);

	/** Get T. Actual Order.
	  * Total Actual To Order 
	  */
	public BigDecimal getTotalActualToOrderUOM();

    /** Column name TotalActualToStockMT */
    public static final String COLUMNNAME_TotalActualToStockMT = "TotalActualToStockMT";

	/** Set Total F. Demand (MT)	  */
	public void setTotalActualToStockMT (BigDecimal TotalActualToStockMT);

	/** Get Total F. Demand (MT)	  */
	public BigDecimal getTotalActualToStockMT();

    /** Column name TotalActualToStockUOM */
    public static final String COLUMNNAME_TotalActualToStockUOM = "TotalActualToStockUOM";

	/** Set Total F. Demand	  */
	public void setTotalActualToStockUOM (BigDecimal TotalActualToStockUOM);

	/** Get Total F. Demand	  */
	public BigDecimal getTotalActualToStockUOM();

    /** Column name UNS_MPSHeader_ID */
    public static final String COLUMNNAME_UNS_MPSHeader_ID = "UNS_MPSHeader_ID";

	/** Set MPS	  */
	public void setUNS_MPSHeader_ID (int UNS_MPSHeader_ID);

	/** Get MPS	  */
	public int getUNS_MPSHeader_ID();

	public com.uns.model.I_UNS_MPSHeader getUNS_MPSHeader() throws RuntimeException;

    /** Column name UNS_MPSProduct_ID */
    public static final String COLUMNNAME_UNS_MPSProduct_ID = "UNS_MPSProduct_ID";

	/** Set MPS Product	  */
	public void setUNS_MPSProduct_ID (int UNS_MPSProduct_ID);

	/** Get MPS Product	  */
	public int getUNS_MPSProduct_ID();

    /** Column name UNS_MPSProduct_UU */
    public static final String COLUMNNAME_UNS_MPSProduct_UU = "UNS_MPSProduct_UU";

	/** Set UNS_MPSProduct_UU	  */
	public void setUNS_MPSProduct_UU (String UNS_MPSProduct_UU);

	/** Get UNS_MPSProduct_UU	  */
	public String getUNS_MPSProduct_UU();

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

    /** Column name WeekToBeUpdated */
    public static final String COLUMNNAME_WeekToBeUpdated = "WeekToBeUpdated";

	/** Set Week To Be Updated.
	  * Week To Be Updated
	  */
	public void setWeekToBeUpdated (int WeekToBeUpdated);

	/** Get Week To Be Updated.
	  * Week To Be Updated
	  */
	public int getWeekToBeUpdated();
}
