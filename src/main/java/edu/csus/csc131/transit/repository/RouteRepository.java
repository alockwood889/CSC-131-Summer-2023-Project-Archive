/**
 * Provides data access methods for querying transit routes in the MongoDB database.
 * Supports querying routes by short name, long name, and a combination of both.
 *
 * @author Jalen Grant Hall
 * @date 07/24/2023
 */
package edu.csus.csc131.transit.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.Route;

public interface RouteRepository extends MongoRepository<Route, String> {
	List<Route> findByShortName(String shortName);
	List<Route> findByLongName(String longName);
	List<Route> findByShortNameAndLongName(String shortName, String longName);
}


