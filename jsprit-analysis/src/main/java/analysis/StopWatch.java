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
package analysis;

import java.util.Collection;

import org.apache.log4j.Logger;

import basics.VehicleRoutingAlgorithm;
import basics.VehicleRoutingProblem;
import basics.VehicleRoutingProblemSolution;
import basics.algo.AlgorithmEndsListener;
import basics.algo.AlgorithmStartsListener;

public class StopWatch implements AlgorithmStartsListener, AlgorithmEndsListener{
	
	private static Logger log = Logger.getLogger(StopWatch.class);
	
	private double ran;
	
	private double startTime;
	
	
	@Override
	public void informAlgorithmStarts(VehicleRoutingProblem problem, VehicleRoutingAlgorithm algorithm, Collection<VehicleRoutingProblemSolution> solutions) {
		reset();
		start();
	}
	
	public double getCompTimeInSeconds(){
		return (ran)/1000.0;
	}

	@Override
	public void informAlgorithmEnds(VehicleRoutingProblem problem, Collection<VehicleRoutingProblemSolution> solutions) {
		stop();
		log.info("computation time [in sec]: " + getCompTimeInSeconds());
	}
	
	public void stop(){
		ran += System.currentTimeMillis() - startTime;
	}
	
	public void start(){
		startTime = System.currentTimeMillis();
	}
	
	public void reset(){
		startTime = 0;
		ran = 0;
	}
	
	@Override
	public String toString() {
		return "stopWatch: " + getCompTimeInSeconds() + " sec";
	}

	public double getCurrTimeInSeconds() {
		return (System.currentTimeMillis()-startTime)/1000.0;
	}
	
}
