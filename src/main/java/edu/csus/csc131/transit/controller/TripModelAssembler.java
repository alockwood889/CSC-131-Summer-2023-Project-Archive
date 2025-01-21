package edu.csus.csc131.transit.controller;
/*
 * Trip Assembler for Trip
 * Author: Nancy Zhu
 */

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.transit.data.Trip;

@Component
public class TripModelAssembler implements RepresentationModelAssembler<Trip, EntityModel<Trip>> {

	@Override
	public EntityModel<Trip> toModel(Trip trip) {
		return EntityModel.of(trip,
				linkTo(methodOn(TripController.class).getTrip(trip.getId())).withSelfRel(),
				linkTo(methodOn(TripController.class).getAllTrips(null, null)).withRel("Trips"));
			
	}


}