package edu.csus.csc131.transit.service;

import java.time.ZonedDateTime;

import edu.csus.csc131.transit.model.TripPlan;

public interface TripPlanService {
	public TripPlan getPlan(String fromStopId, String toStopId, ZonedDateTime departTime);

}
