package edu.csus.csc131.transit.repository;
/*
 * Trip Repository
 * Author: Nancy Zhu
 */

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.Trip;

public interface TripRepository extends MongoRepository<Trip, String>  {
	List<Trip> findByRouteId(String routeId);
	List<Trip> findByServiceId(String serviceId);
	List<Trip> findByRouteIdAndServiceId(String routeId, String serviceId);
}