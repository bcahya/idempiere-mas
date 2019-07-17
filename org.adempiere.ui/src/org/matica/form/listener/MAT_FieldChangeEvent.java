/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Matteo Carminati	                                    * 
 * Copyright (C) Contributors                                         * 
 *                                                                    * 
 * This program is free software; you can redistribute it and/or      * 
 * modify it under the terms of the GNU General Public License        * 
 * as published by the Free Software Foundation; either version 2     * 
 * of the License, or (at your option) any later version.             * 
 *                                                                    * 
 * This program is distributed in the hope that it will be useful,    * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
 * GNU General Public License for more details.                       * 
 *                                                                    * 
 * You should have received a copy of the GNU General Public License  * 
 * along with this program; if not, write to the Free Software        * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
 * MA 02110-1301, USA.                                                * 
 *                                                                    * 
 * Contributors:                                                      * 
 *  - Matteo Carminati (mcarminati@ma-tica.it)                        *
 *                                                                    *
 * Sponsors:                                                          *
 *  - MA-TICA (http://www.ma-tica.it/)                                *
 **********************************************************************/

package org.matica.form.listener;

public class MAT_FieldChangeEvent extends java.util.EventObject 
{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 3335871404350870916L;

    /**
     * name of the field that changed.  May be null, if not known.
     * @serial
     */
    private String fieldName;

    /**
     * New value for property.  May be null if not known.
     * @serial
     */
    private Object newValue;

    /**
     * Previous value for property.  May be null if not known.
     * @serial
     */
    private Object oldValue;

	public MAT_FieldChangeEvent(Object source,String fieldName,
		     Object oldValue, Object newValue) {
		super(source);
		this.fieldName = fieldName;
		this.oldValue = oldValue;
		this.newValue = newValue;
		
	}

    /**
     * Gets the programmatic name of the property that was changed.
     *
     * @return  The programmatic name of the property that was changed.
     *		May be null if multiple properties have changed.
     */
    public String getFieldName() {
    	return fieldName;
    }
    
    /**
     * Gets the new value for the property, expressed as an Object.
     *
     * @return  The new value for the property, expressed as an Object.
     *		May be null if multiple properties have changed.
     */
    public Object getNewValue() {
    	return newValue;
    }

    /**
     * Gets the old value for the property, expressed as an Object.
     *
     * @return  The old value for the property, expressed as an Object.
     *		May be null if multiple properties have changed.
     */
    public Object getOldValue() {
    	return oldValue;
    }



}
