package edu.csus.csc131.transit.data;

/* Data class for Transfer 
 * Author: Alyssa Lockwood
 * Updated: 7/25/2023
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transfer {	
	//Fields
	@Id
	private String id;
	private String fromStopId;
	private String toStopId;
	private int minTransferTime;
	private String fromRouteId;
	private String toRouteId;
	
	//Constructor
	public Transfer() {
		super();
	}
	
	//Methods
	//id methods
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	//fromStopId methods
	public void setFromStopId(String fromStopId) {
		this.fromStopId = fromStopId;
	}
	
	public String getFromStopId(){
		return fromStopId;
	}
	
	//toStopId methods
	public void setToStopId(String toStopId) {
		this.toStopId = toStopId;
	}
	
	public String getToStopId() {
		return toStopId;
	}
	
	//minTransferTime methods
	public void setMinTransferTime(int minTransferTime) {
		this.minTransferTime = minTransferTime;
	}
	
	public int getMinTransferTime() {
		return minTransferTime;
	}
	
	//fromRouteId methods
	public void setFromRouteId(String fromRouteId) {
		this.fromRouteId = fromRouteId;
	}
	
	public String getFromRouteId() {
		return fromRouteId;
	}
	
	//toRouteId methods
	public void setToRouteId(String toRouteId) {
		this.toRouteId = toRouteId;
	}
	
	public String getToRouteId() {
		return toRouteId;
	}

	
}
