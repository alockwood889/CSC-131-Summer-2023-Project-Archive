package edu.csus.csc131.transit.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.transit.data.StopTime;

@Component
public class StopTimeModelAssembler implements RepresentationModelAssembler<StopTime, EntityModel<StopTime>> {

	@Override
	public EntityModel<StopTime> toModel(StopTime stopTime) {
		return EntityModel.of(stopTime, //
				linkTo(methodOn(StopTimeController.class).getStopTime(stopTime.getId())).withSelfRel(),
				linkTo(methodOn(StopTimeController.class).getAllStopTimes(null, null)).withRel("stopTimes"));
	}

}

