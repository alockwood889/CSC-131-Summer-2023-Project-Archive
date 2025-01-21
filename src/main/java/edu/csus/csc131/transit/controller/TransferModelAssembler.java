package edu.csus.csc131.transit.controller;

/* Model Assembler class for Transfer 
 * Author: Alyssa Lockwood
 * Updated: 7/25/2023
 */

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.csus.csc131.transit.data.Transfer;

@Component
public class TransferModelAssembler implements RepresentationModelAssembler<Transfer, EntityModel<Transfer>>{
	
	@Override
	public EntityModel<Transfer> toModel(Transfer transfer){
		return EntityModel.of(transfer,
				linkTo(methodOn(TransferController.class).getTransfer(transfer.getId())).withSelfRel(),
				linkTo(methodOn(TransferController.class).getAllTransfers(null, null)).withRel("Transfers")
				);
	}
}
