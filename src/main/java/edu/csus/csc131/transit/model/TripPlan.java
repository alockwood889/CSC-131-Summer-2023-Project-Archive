package edu.csus.csc131.transit.model;

import java.time.ZonedDateTime;
import java.util.List;

public class TripPlan {
	private String fromStopId;
	private String toStopId;
	private ZonedDateTime departTime;
	private List<TripStep> tripSteps; // trip result steps
	
	public TripPlan() {
		super();
	}

	public String getFromStopId() {
		return fromStopId;
	}

	public void setFromStopId(String fromStopId) {
		this.fromStopId = fromStopId;
	}

	public String getToStopId() {
		return toStopId;
	}

	public void setToStopId(String toStopId) {
		this.toStopId = toStopId;
	}

	public ZonedDateTime getDepartTime() {
		return departTime;
	}

	public void setDepartTime(ZonedDateTime departTime) {
		this.departTime = departTime;
	}

	public List<TripStep> getTripSteps() {
		return tripSteps;
	}

	public void setTripSteps(List<TripStep> tripSteps) {
		this.tripSteps = tripSteps;
	}

}
