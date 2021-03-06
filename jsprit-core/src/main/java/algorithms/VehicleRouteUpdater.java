/*******************************************************************************
 * Copyright (c) 2011 Stefan Schroeder.
 * eMail: stefan.schroeder@kit.edu
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Stefan Schroeder - initial API and implementation
 ******************************************************************************/
package algorithms;

import basics.route.VehicleRoute;


/**
 * Updater that updates a vehicleRoute, e.g. the total costs or the time-windows.
 * 
 * @author stefan schroeder
 *
 */

interface VehicleRouteUpdater {
	
	public boolean updateRoute(VehicleRoute vehicleRoute);
	
}
