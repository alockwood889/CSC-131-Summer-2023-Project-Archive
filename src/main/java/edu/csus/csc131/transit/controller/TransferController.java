package edu.csus.csc131.transit.controller;

/* Controller class for Transfer 
 * Author: Alyssa Lockwood
 * Updated: 7/25/2023
 */

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
import edu.csus.csc131.transit.repository.TransferRepository;
import edu.csus.csc131.transit.data.Transfer;

@RestController
@RequestMapping(value = "/transfers")
public class TransferController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	private TransferRepository transferRepository;
	private TransferModelAssembler transferAssembler;
	
	//Constructor
	public TransferController(TransferRepository transferRepository, TransferModelAssembler transferAssembler) {
		this.transferRepository = transferRepository;
		this.transferAssembler = transferAssembler;
	}
	
	@GetMapping
	public CollectionModel<EntityModel<Transfer>> getAllTransfers(
			@RequestParam(required = false) String fromStopId, 
			@RequestParam(required = false) String toStopId){
		log.info("Getting all transfers");
		
		List<Transfer> transfers = null;
		if((fromStopId == null || fromStopId.isBlank()) && (toStopId == null || toStopId.isBlank())) {
			transfers = transferRepository.findAll();
		}else {
			transfers = transferRepository.findByFromStopIdAndToStopId(fromStopId, toStopId);
		} 
		log.info("Returning {} transfers.", transfers.size());
		
		List<EntityModel<Transfer>> transferList = transfers.stream()
				.map(transferAssembler::toModel)
				.toList();
		
		return CollectionModel.of(transferList,
				linkTo(methodOn(TransferController.class).getAllTransfers(fromStopId, toStopId)).withSelfRel());
	}
	
	@GetMapping(value = "/{id}")
	public EntityModel<Transfer> getTransfer(@PathVariable String id){
		log.info("Getting transfer by ID: ", id);
		Transfer transfer = transferRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transfer", id));
		
		return transferAssembler.toModel(transfer);
	}
}
