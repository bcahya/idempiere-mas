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

package org.matica.form;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class VMATListElementItem 
{
	private int m_rowpos = 0;
	private int m_colpos = 0;
	private int m_rowspan = 1;
	private int m_colspan = 1;
	private boolean m_isVisible = true;
	private Component m_component = null;
	private int m_fill = GridBagConstraints.NONE;
	private int m_align = GridBagConstraints.NORTHWEST;
	private Insets m_insets = new Insets(3, 3, 3, 3);
	
	public VMATListElementItem(Component component, int rowpos, int colpos)
	{
		m_component = component;
		m_rowpos = rowpos;
		m_colpos = colpos;
	}
	
	public String getKey()
	{
		return "" + m_rowpos + ":" + m_colpos;
	}
	
	public int getRowpos() {
		return m_rowpos;
	}
	public void setRowpos(int m_rowpos) {
		this.m_rowpos = m_rowpos;
	}
	public int getColpos() {
		return m_colpos;
	}
	public void setColpos(int m_colpos) {
		this.m_colpos = m_colpos;
	}
	public int getRowspan() {
		return m_rowspan;
	}
	public void setRowspan(int m_rowspan) {
		this.m_rowspan = m_rowspan;
	}
	public int getColspan() {
		return m_colspan;
	}
	public void setColspan(int colpan) {
		this.m_colspan = colpan;
	}
	public boolean getVisible() {
		return m_isVisible;
	}
	public void setVisible(boolean m_isVisible) {
		this.m_isVisible = m_isVisible;
	}
	public Component getComponent() {
		return m_component;
	}
	public int getFill() {
		return m_fill;
	}
	public void setFill(int m_fill) {
		this.m_fill = m_fill;
	}
	public int getAlign() {
		return m_align;
	}
	public void setAlign(int m_align) {
		this.m_align = m_align;
	}
	
	public Insets getInsets() {
		return m_insets;
	}
	public void setInsets(Insets insets) {
		m_insets = insets;
	}

}
