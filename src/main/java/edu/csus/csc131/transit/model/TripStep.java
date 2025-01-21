package edu.csus.csc131.transit.model;

import java.time.ZonedDateTime;

public class TripStep {
	private String routeId;
	private String routeName; // route short name
	private String fromStopId;
	private ZonedDateTime departTime;
	private String toStopId;
	private ZonedDateTime arrivalTime;
	
	public TripStep() {
		super();
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getFromStopId() {
		return fromStopId;
	}

	public void setFromStopId(String fromStopId) {
		this.fromStopId = fromStopId;
	}

	public ZonedDateTime getDepartTime() {
		return departTime;
	}

	public void setDepartTime(ZonedDateTime departTime) {
		this.departTime = departTime;
	}

	public String getToStopId() {
		return toStopId;
	}

	public void setToStopId(String toStopId) {
		this.toStopId = toStopId;
	}

	public ZonedDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(ZonedDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}


}
