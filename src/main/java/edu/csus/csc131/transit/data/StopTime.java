package edu.csus.csc131.transit.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StopTime {
	@Id
	private String id;
	private String tripId;
	private String arrivalTime;
	private String departureTime;
	private String stopId;
	private int stopSequence;
	private double distTraveled;
	
	public StopTime() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getStopId() {
		return stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
	}

	public int getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(int stopSequence) {
		this.stopSequence = stopSequence;
	}

	public double getDistTraveled() {
		return distTraveled;
	}

	public void setDistTraveled(double distTraveled) {
		this.distTraveled = distTraveled;
	}

	@Override
	public String toString() {
		return "StopTime [id=" + id + ", tripId=" + tripId + ", arrivalTime=" + arrivalTime + ", departureTime="
				+ departureTime + ", stopId=" + stopId + ", stopSequence=" + stopSequence + ", distTraveled="
				+ distTraveled + "]";
	}

}
