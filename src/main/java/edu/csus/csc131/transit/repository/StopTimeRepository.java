package edu.csus.csc131.transit.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.csus.csc131.transit.data.StopTime;

public interface StopTimeRepository extends MongoRepository<StopTime, String> {
	List<StopTime> findByTripId(String tripId);
	List<StopTime> findByStopId(String stopId);
	List<StopTime> findByTripIdAndStopId(String tripId, String stopId);
}