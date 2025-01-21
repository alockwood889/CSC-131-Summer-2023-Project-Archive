/**
 * REST controller for handling transit routes.
 * Provides end points to retrieve all routes, routes by their short name, long name, or ID.
 * Uses HATEOAS to generate links for related resources and handles HTTP GET requests for data retrieval.
 * 
 * @author Jalen Grant Hall
 * @date 07/24/2023
 */

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.csus.csc131.transit.controller.common.ResourceNotFoundException;
import edu.csus.csc131.transit.data.Route;
import edu.csus.csc131.transit.repository.RouteRepository;

@RestController
@RequestMapping(value = "/routes")
public class RouteController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private RouteRepository routeRepository;
	private RouteModelAssembler routeModelAssembler;

	public RouteController(RouteRepository routeRepository, RouteModelAssembler routeModelAssembler) {
		this.routeRepository = routeRepository;
		this.routeModelAssembler = routeModelAssembler;
	}

	@GetMapping
	public CollectionModel<EntityModel<Route>> getAllRoutes(@RequestParam(required = false) String shortName,
			@RequestParam(required = false) String longName) {
		log.info("Getting all routes.");

		List<Route> routes = null;
		if ((shortName == null || shortName.isBlank()) && (longName == null || longName.isBlank())) {
			routes = routeRepository.findAll();
		} else if (shortName == null || shortName.isBlank()) {
			routes = routeRepository.findByLongName(longName);
		} else if (longName == null || longName.isBlank()) {
			routes = routeRepository.findByShortName(shortName);
		} else {
			routes = routeRepository.findByShortNameAndLongName(shortName, longName);
		}
		log.info("Returning {} routes.", routes.size());

		// Java Aggregate Operations Tutorial:
		// https://docs.oracle.com/javase/tutorial/collections/streams/index.html
		List<EntityModel<Route>> routeList = routes.stream() //
				.map(routeModelAssembler::toModel) //
				.toList();
		return CollectionModel.of(routeList,
				linkTo(methodOn(RouteController.class).getAllRoutes(shortName, longName)).withSelfRel());
	}

	@GetMapping(value = "/{id}")
    public EntityModel<Route> getRoute(@PathVariable String id) { // Remove "/routes" from the @PathVariable
        log.info("Getting a route by Id: {}.", id);
        Route route = routeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Route", id));
        return routeModelAssembler.toModel(route);
	}
}