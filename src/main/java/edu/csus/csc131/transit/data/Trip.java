package edu.csus.csc131.transit.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Data class for Trip with setter and getter methods.
 * Author: Nancy Zhu
 */

@Document
public class Trip {
	@Id
	private String id;
	private String serviceId;
	private String routeId;
	private int directionId;
	
	
	public Trip() {
		super();
	}
	
	public void setId(String id) {
		this.id = id;
	}	
	
	public String getId() {
		return id;
	}
	
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getServiceId() {
		return serviceId;
	}
	
	public void setRouteId(String routeId) {
		this.routeId= routeId;
	}
	
	public String getRouteId() {
		return routeId;
	}
	
	
	public void setDirectionId(int directionId) {
		this.directionId = directionId;
	}
	
	public int getDirectionId() {
		return directionId;
	}

	@Override
	public String toString() {
		return "Trip [id=" + id + ", routeId=" + routeId + ", directionId" + directionId + "]";
	}

}