package edu.csus.csc131.transit.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.csus.csc131.transit.controller.common.ResourceNotFoundException;
import edu.csus.csc131.transit.data.Trip;
import edu.csus.csc131.transit.repository.TripRepository;

/*
 * Controller class for Trip
 * Author: Nancy Zhu
 */
@RestController
@RequestMapping(value = "/trips")

public class TripController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private TripRepository tripRepository;
	private TripModelAssembler tripAssembler;
	
	public TripController(TripRepository tripRepository, TripModelAssembler tripModelAssembler) {
		this.tripRepository = tripRepository;
		this.tripAssembler = tripModelAssembler;
	}
	
	@GetMapping
	public CollectionModel<EntityModel<Trip>> getAllTrips(
			@RequestParam(required = false) String routeId, @RequestParam(required = false) String serviceId) {
		log.info("Getting all Trips.");
		
		List<Trip> trips = null;
		if((routeId == null || routeId.isBlank()) && (serviceId == null || serviceId.isBlank())) {
			trips = tripRepository.findAll();
		} else if (routeId == null || routeId.isBlank()) {
			trips = tripRepository.findByRouteId(routeId);
		} else if (serviceId == null || serviceId.isBlank()) {
			trips = tripRepository.findByRouteId(routeId);
		} else {
			trips = tripRepository.findByRouteIdAndServiceId(routeId, serviceId);
		}
			
		log.info("Returning {} Trips.", trips.size());
		
		// Java Aggregate Operations Tutorial: https://docs.oracle.com/javase/tutorial/collections/streams/index.html
		List<EntityModel<Trip>> tripList = trips.stream() 
				.map(tripAssembler :: toModel) 
				.toList();
				
		
		return CollectionModel.of(tripList,
				linkTo(methodOn(TripController.class).getAllTrips(routeId, serviceId)).withSelfRel());
		
	}
	@GetMapping(value = "/{id}")
	public EntityModel<Trip> getTrip(@PathVariable String id) {
		log.info("Getting a Trip by Id: {}.", id);
		Trip trip = tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip", id));
		return tripAssembler.toModel(trip);
		
	}
	
	
}