/*******************************************************************************
 * Copyright (C) 2013  Stefan Schroeder
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 * Contributors:
 *     Stefan Schroeder - initial API and implementation
 ******************************************************************************/
package basics.route;

import basics.Service;
import basics.route.TourActivity.JobActivity;

public class ServiceActivity implements JobActivity<Service>{
	
	public static int counter = 0;
	
	public double arrTime;
	
	public double endTime;
	
	/**
	 * @return the arrTime
	 */
	public double getArrTime() {
		return arrTime;
	}

	/**
	 * @param arrTime the arrTime to set
	 */
	public void setArrTime(double arrTime) {
		this.arrTime = arrTime;
	}

	/**
	 * @return the endTime
	 */
	public double getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}

	public static ServiceActivity copyOf(ServiceActivity serviceActivity){
		return new ServiceActivity(serviceActivity);
	}
	
	public static ServiceActivity newInstance(Service service){
		return new ServiceActivity(service);
	}
	
	private final Service service;
			
	private ServiceActivity(Service service) {
		counter++;
		this.service = service;
	}

	private ServiceActivity(ServiceActivity serviceActivity) {
		counter++;
		this.service = serviceActivity.getJob();
		this.arrTime = serviceActivity.getArrTime();
		this.endTime = serviceActivity.getEndTime();
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceActivity other = (ServiceActivity) obj;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		return true;
	}

	public double getTheoreticalEarliestOperationStartTime() {
		return service.getTimeWindow().getStart();
	}

	public double getTheoreticalLatestOperationStartTime() {
		return service.getTimeWindow().getEnd();
	}

	@Override
	public int getCapacityDemand() {
		return service.getCapacityDemand();
	}

	@Override
	public double getOperationTime() {
		return service.getServiceDuration();
	}

	@Override
	public String getLocationId() {
		return service.getLocationId();
	}

	
	@Override
	public Service getJob() {
		return service;
	}

	public String toString() {
		return "[type="+getName()+"][service="+this.service+"]";
	}

	@Override
	public String getName() {
		return service.getType();
	}

	@Override
	public TourActivity duplicate() {
		return new ServiceActivity(this);
	}
	
	

}
