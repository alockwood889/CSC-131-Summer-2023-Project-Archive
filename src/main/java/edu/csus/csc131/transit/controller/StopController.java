/**
 * REST controller for handling transit stops.
 * Provides end points to retrieve all stops and stops by their ID.
 * Uses HATEOAS to generate links for related resources and handles HTTP GET requests for data retrieval.
 *  
 * @author Jalen Grant Hall
 * @date 07/27/2023
 */
package edu.csus.csc131.transit.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.csus.csc131.transit.controller.common.ResourceNotFoundException;
import edu.csus.csc131.transit.data.Stop;
import edu.csus.csc131.transit.repository.StopRepository;

@RestController
@RequestMapping(value = "/stops")
public class StopController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private StopRepository stopRepository;
	private StopModelAssembler stopModelAssembler;

	public StopController(StopRepository stopRepository, StopModelAssembler stopModelAssembler) {
		this.stopRepository = stopRepository;
		this.stopModelAssembler = stopModelAssembler;
	}

	@GetMapping
	public CollectionModel<EntityModel<Stop>> getAllStops() {
		log.info("Getting all stops.");

		List<Stop> stops = stopRepository.findAll();
		log.info("Returning {} stops.", stops.size());

		List<EntityModel<Stop>> stopList = stops.stream().map(stopModelAssembler::toModel).toList();

		return CollectionModel.of(stopList, linkTo(methodOn(StopController.class).getAllStops()).withSelfRel());
	}

	@GetMapping(value = "/{id}")
	public EntityModel<Stop> getStop(@PathVariable String id) {
		log.info("Getting a stop by ID: {}.", id);
		Stop stop = stopRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stop", id));
		return stopModelAssembler.toModel(stop);
	}
}