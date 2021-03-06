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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import util.RandomNumberGeneration;
import basics.Job;
import basics.VehicleRoutingProblem;
import basics.route.VehicleRoute;


/**
 * Ruin strategy that ruins current solution randomly. I.e.
 * customer are removed randomly from current solution.
 * 
 * @author stefan schroeder
 * 
 */

final class RuinRandom implements RuinStrategy {

	public static RuinRandom newInstance(VehicleRoutingProblem vrp, double fraction, JobRemover jobRemover, VehicleRouteUpdater routeUpdater){
		return new RuinRandom(vrp, fraction, jobRemover, routeUpdater);
	}
	
	private Logger logger = Logger.getLogger(RuinRandom.class);

	private VehicleRoutingProblem vrp;

	private double fractionOfAllNodes2beRuined;

	private Random random = RandomNumberGeneration.getRandom();

	private JobRemover jobRemover;

	private VehicleRouteUpdater vehicleRouteUpdater;

	public void setRandom(Random random) {
		this.random = random;
	}

	/**
	 * Constructs ruinRandom.
	 * 
	 * @param vrp
	 * @param fraction which is the fraction of total c
	 * @param jobRemover
	 * @param vehicleRouteUpdater
	 */
	public RuinRandom(VehicleRoutingProblem vrp, double fraction, JobRemover jobRemover, VehicleRouteUpdater vehicleRouteUpdater) {
		super();
		this.vrp = vrp;
		this.jobRemover = jobRemover;
		this.vehicleRouteUpdater = vehicleRouteUpdater;
		this.fractionOfAllNodes2beRuined = fraction;
		logger.info("initialise " + this);
		logger.info("done");
	}

	/**
	 * Removes a fraction of jobs from vehicleRoutes. 
	 * 
	 * <p>The number of jobs is calculated as follows: Math.ceil(vrp.getJobs().values().size() * fractionOfAllNodes2beRuined).
	 */
	@Override
	public Collection<Job> ruin(Collection<VehicleRoute> vehicleRoutes) {
		List<Job> unassignedJobs = new ArrayList<Job>();
		int nOfJobs2BeRemoved = selectNuOfJobs2BeRemoved();
		ruin(vehicleRoutes, nOfJobs2BeRemoved, unassignedJobs);
		return unassignedJobs;
	}

	/**
	 * Removes nOfJobs2BeRemoved from vehicleRoutes, including targetJob.
	 */
	@Override
	public Collection<Job> ruin(Collection<VehicleRoute> vehicleRoutes, Job targetJob, int nOfJobs2BeRemoved) {
		List<Job> unassignedJobs = new ArrayList<Job>();
		if(targetJob != null){
			boolean removed = false;
			for (VehicleRoute route : vehicleRoutes) {
				removed = jobRemover.removeJobWithoutTourUpdate(targetJob, route);
				if (removed) {
					nOfJobs2BeRemoved--;
					unassignedJobs.add(targetJob);
					break;
				}
			}
		}
		ruin(vehicleRoutes, nOfJobs2BeRemoved, unassignedJobs);
		return unassignedJobs;
	}

	public void setRuinFraction(double fractionOfAllNodes2beRuined) {
		this.fractionOfAllNodes2beRuined = fractionOfAllNodes2beRuined;
		logger.info("fraction set " + this);
	}

	private void ruin(Collection<VehicleRoute> vehicleRoutes,int nOfJobs2BeRemoved, List<Job> unassignedJobs) {
		LinkedList<Job> availableJobs = new LinkedList<Job>(vrp.getJobs().values());
		for (int i = 0; i < nOfJobs2BeRemoved; i++) {
			Job job = pickRandomJob(availableJobs);
			unassignedJobs.add(job);
			availableJobs.remove(job);
			for (VehicleRoute route : vehicleRoutes) {
				boolean removed = jobRemover.removeJobWithoutTourUpdate(job, route);
				if (removed) break;
			}
		}
		updateRoutes(vehicleRoutes);
	}

	private void updateRoutes(Collection<VehicleRoute> vehicleRoutes) {
		for(VehicleRoute route : vehicleRoutes){
			vehicleRouteUpdater.updateRoute(route);
		}
	}

	
	@Override
	public String toString() {
		return "[name=randomRuin][fraction="+fractionOfAllNodes2beRuined+"]";
	}
	
	private Job pickRandomJob(LinkedList<Job> availableJobs) {
		int randomIndex = random.nextInt(availableJobs.size());
		return availableJobs.get(randomIndex);
	}

	private int selectNuOfJobs2BeRemoved() {
		return (int) Math.ceil(vrp.getJobs().values().size() * fractionOfAllNodes2beRuined);
	}

}
