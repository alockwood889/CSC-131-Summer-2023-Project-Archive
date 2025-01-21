/**
 * Provides data access methods for querying transit routes in the MongoDB database.
 * Supports querying stops by name.
 * 
 * @author Jalen Grant Hall
 * @date 07/26/2023
 */
package edu.csus.csc131.transit.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.Stop;

public interface StopRepository extends MongoRepository<Stop, String> {
    List<Stop> findByName(String name);
}