/**
 * Creates a user-friendly representation of a transit route.
 * Provides links for easy navigation to specific routes and a collection of all routes.
 *
 * @author Jalen Grant Hall
 * @date 07/24/2023
 */
package edu.csus.csc131.transit.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.transit.data.Route;

@Component
public class RouteModelAssembler implements RepresentationModelAssembler<Route, EntityModel<Route>> {

	@Override
	public EntityModel<Route> toModel(Route route) {
		return EntityModel.of(route, //
				linkTo(methodOn(RouteController.class).getRoute(route.getId())).withSelfRel(),
				linkTo(methodOn(RouteController.class).getAllRoutes(null, null)).withRel("routes"));
	}
}