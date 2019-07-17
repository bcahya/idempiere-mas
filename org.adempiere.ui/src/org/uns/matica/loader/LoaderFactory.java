/**
 * 
 */
package org.uns.matica.loader;

import org.adempiere.base.equinox.EquinoxExtensionLocator;
import org.matica.form.listener.MAT_I_BusinessLogic;

/**
 * @author Haryadi
 * This file is part of Unisoft (was Untasoft) customization to iDempiere and MATICA RAD Form.
 *
 * Copyright (C) AzHaidar (harry.haryadi@Untasoft.com).
 *                                                                          
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * UNTASOFT (UNISOFT) will only provide WARRANTY to the party by whose untasoft (unisoft) 
 * have an agreement but on the convergence of time agreement only.
 */
public class LoaderFactory 
{
	public static MAT_I_BusinessLogic getBusinessLogic(String businessLogicID)
	{
		MAT_I_BusinessLogic logic = 
				EquinoxExtensionLocator.instance().locate(
						MAT_I_BusinessLogic.class, "org.uns.matica.BusinessLogic", businessLogicID, null)
						.getExtension();
		return logic;
	}
}
