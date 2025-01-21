/**
 * Creates a user-friendly representation of a transit stop.
 * Provides links for easy navigation to specific routes and a collection of all stops.
 * 
 * @author Jalen Grant Hall
 * @date 07/27/2023
 * 
 */
package edu.csus.csc131.transit.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.transit.data.Stop;

@Component
public class StopModelAssembler implements RepresentationModelAssembler<Stop, EntityModel<Stop>> {

	@Override
	public EntityModel<Stop> toModel(Stop stop) {
		return EntityModel.of(stop, //
				linkTo(methodOn(StopController.class).getStop(stop.getId())).withSelfRel(),
				linkTo(methodOn(StopController.class).getAllStops()).withRel("stops"));
	}
}