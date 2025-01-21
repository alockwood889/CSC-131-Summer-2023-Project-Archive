package edu.csus.csc131.transit.repository;

/* Repository interface for Transfer 
 * Author: Alyssa Lockwood
 * Updated: 7/25/2023
 */

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.Transfer;

public interface TransferRepository extends MongoRepository<Transfer, String>{
	List<Transfer> findByFromStopIdAndToStopId(String fromStopId, String toStopId);
}
