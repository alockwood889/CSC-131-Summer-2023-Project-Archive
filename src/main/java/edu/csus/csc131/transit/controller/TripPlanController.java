package edu.csus.csc131.transit.controller;

import java.time.ZonedDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.csus.csc131.transit.model.TripPlan;
import edu.csus.csc131.transit.service.TripPlanService;

@RestController
@RequestMapping(value = "/tripPlans")
public class TripPlanController {
	
	private TripPlanService tripPlanService;
	
	public TripPlanController(TripPlanService tripPlanService) {
		this.tripPlanService = tripPlanService;
	}
	
	@GetMapping
	public TripPlan getTripPlan(
			@RequestParam(required = true) String fromStopId, 
			@RequestParam(required = true) String toStopId,
			@RequestParam(required = true)  ZonedDateTime departTime) {
		return tripPlanService.getPlan(fromStopId, toStopId, departTime);

	} 

}
