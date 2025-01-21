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
import edu.csus.csc131.transit.data.StopTime;
import edu.csus.csc131.transit.repository.StopTimeRepository;

@RestController
@RequestMapping(value = "/stopTimes")
public class StopTimeController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private StopTimeRepository stopTimeRepository;
	private StopTimeModelAssembler stopTimeAssembler;
	
	public StopTimeController(StopTimeRepository stopTimeRepository, StopTimeModelAssembler stopTimeModelAssembler) {
		this.stopTimeRepository = stopTimeRepository;
		this.stopTimeAssembler = stopTimeModelAssembler;
	}
	
	@GetMapping
	public CollectionModel<EntityModel<StopTime>> getAllStopTimes(
			@RequestParam(required = false) String tripId, @RequestParam(required = false) String stopId) {
		log.info("Getting all stopTimes.");
		
		List<StopTime> stopTimes = null;
		if ((tripId == null || tripId.isBlank()) && (stopId == null || stopId.isBlank())) {
			stopTimes = stopTimeRepository.findAll();
		} else if (tripId == null || tripId.isBlank()) {
			stopTimes = stopTimeRepository.findByStopId(stopId);
		} else if (stopId == null || stopId.isBlank()) {
			stopTimes = stopTimeRepository.findByTripId(tripId);
		} else {
			stopTimes = stopTimeRepository.findByTripIdAndStopId(tripId, stopId);
		}
		log.info("Returning {} stopTimes.", stopTimes.size());
		
		// Java Aggregate Operations Tutorial: https://docs.oracle.com/javase/tutorial/collections/streams/index.html
		List<EntityModel<StopTime>> stopTimeList = stopTimes.stream() //
				.map(stopTimeAssembler::toModel) //
				.toList();
		return CollectionModel.of(stopTimeList,
				linkTo(methodOn(StopTimeController.class).getAllStopTimes(tripId, stopId)).withSelfRel());
	}
	
	@GetMapping(value = "/{id}")
	public EntityModel<StopTime> getStopTime(@PathVariable String id) {
		log.info("Getting a stopTime by Id: {}.", id);
		StopTime stopTime = stopTimeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("StopTime", id));
		return stopTimeAssembler.toModel(stopTime);
	}
	
}
