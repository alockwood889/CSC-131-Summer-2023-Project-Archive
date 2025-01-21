/**
 * Implementation for the TripPlanServiceImpl class.
 * 
 * @authors Alyssa Lockwood, Jalen Grant Hall
 * @date 08/15/2023
 */


package edu.csus.csc131.transit.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.csus.csc131.transit.data.Route;
import edu.csus.csc131.transit.data.StopTime;
import edu.csus.csc131.transit.data.Trip;
import edu.csus.csc131.transit.model.TripPlan;
import edu.csus.csc131.transit.model.TripStep;
import edu.csus.csc131.transit.repository.RouteRepository;
import edu.csus.csc131.transit.repository.StopTimeRepository;
import edu.csus.csc131.transit.repository.TripRepository;

@Service
public class TripPlanServiceImpl implements TripPlanService {
    // Repositories
    @Autowired
    private RouteRepository routeRepo;
    @Autowired
    private TripRepository tripRepo;
    @Autowired
    private StopTimeRepository stopTimeRepo;

    @Override
    public TripPlan getPlan(String fromStopId, String toStopId, ZonedDateTime departTime) {
        TripPlan tripPlan = new TripPlan();
        tripPlan.setFromStopId(fromStopId);
        tripPlan.setToStopId(toStopId);
        tripPlan.setDepartTime(departTime);

        // Fetch data from repositories and calculate trip steps
        List<TripStep> tripSteps = getTripSteps(fromStopId, toStopId, departTime);
        tripPlan.setTripSteps(tripSteps);


        return tripPlan;
    }

    private List<TripStep> getTripSteps(String fromStopId, String toStopId, ZonedDateTime departTime) {
        List<TripStep> tripSteps = new ArrayList<>();
        StopTime validStopTime = null;

        int index = 0;
        String compare = "";

        
        
        System.out.println("From Stop ID: " + fromStopId);
        System.out.println("To Stop ID: " + toStopId);
        
        
        List<StopTime> stopTimes = stopTimeRepo.findByStopId(fromStopId);
        validStopTime = findStopTime(fromStopId, toStopId, departTime);
        
        while(validStopTime == null) {
        	System.out.println("Finding StopTimes");
        	validStopTime = findStopTime(fromStopId, toStopId, departTime.plusHours(1).withMinute(0));
        	index++;
        	
        	if(index >= 10) {
        		validStopTime = new StopTime();
        		return tripSteps;
        	}
        }
       
        
       
        index = validStopTime.getStopSequence();  
        
        stopTimes = stopTimeRepo.findByTripId(validStopTime.getTripId());
        
        index = validStopTime.getStopSequence();
        
        if(!stopTimes.get(index).getStopId().equals(fromStopId)) {
        	index--;
        	System.out.println(index);
        	System.out.println(stopTimes.get(index).toString());
        }
        

        while(!compare.equals(toStopId) && (index < stopTimes.size()-1)) {
        	
        	if(index >= stopTimes.size()) {
            	System.out.println("Stop time not found");
            	break;
            }

        	
        	Trip trip = tripRepo.findById(stopTimes.get(index).getTripId()).get();
        	Route route = routeRepo.findById(trip.getRouteId()).get();
        	compare = stopTimes.get(index).getStopId();
        	String[] dTime = stopTimes.get(index).getDepartureTime().split(":");
        	String[] aTime = stopTimes.get(index+1).getArrivalTime().split(":");
        	
        	ZonedDateTime dZDT = ZonedDateTime.of(departTime.getYear(), departTime.getMonth().getValue() , departTime.getDayOfMonth(),
        						Integer.parseInt(dTime[0]), Integer.parseInt(dTime[1]), Integer.parseInt(dTime[2]),
        						0, departTime.getZone());
        	
        	ZonedDateTime aZDT = ZonedDateTime.of(departTime.getYear(), departTime.getMonth().getValue() , departTime.getDayOfMonth(),
					Integer.parseInt(aTime[0]), Integer.parseInt(aTime[1]), Integer.parseInt(aTime[2]),
					0, departTime.getZone());

        	if(compare.equals(toStopId)) {
        		System.out.println("Plan complete");
        		break;
        	}
        	
        	System.out.println("StopId:" + compare);
        	System.out.println("TripId:" + trip.getId());
        	System.out.println(trip.toString());
        	System.out.println(route.toString());
        	
        	TripStep tripStep = new TripStep();
            tripStep.setRouteId(route.getId());
            tripStep.setRouteName(route.getShortName());
            tripStep.setFromStopId(compare);
            tripStep.setDepartTime(dZDT);
            tripStep.setToStopId(stopTimes.get(index+1).getStopId());
            tripStep.setArrivalTime(aZDT);
            tripSteps.add(tripStep); 
            
            index++;
        }
        
      

        //Debug logs to check the data
        System.out.println("Number of Trip Steps: " + tripSteps.size());
        for (TripStep step : tripSteps) {
            System.out.println("Trip Step: " + step);
        }

        return tripSteps;
    }
    
    private StopTime findStopTime(String stopId, String finalStopId, ZonedDateTime departTime) {
    	List<StopTime> stopTimes = stopTimeRepo.findByStopId(stopId);
        StopTime validStopTime = null;
    	boolean vTime = true;

    	
        	for(StopTime stopTime : stopTimes) {
        		Optional<Trip> tripOptional = tripRepo.findById(stopTime.getTripId());
        	
        		String[] time = stopTime.getDepartureTime().split(":");
        		
        		if(((Integer.parseInt(time[0]) == departTime.getHour()) 
        			&& (Integer.parseInt(time[1]) > (departTime.getMinute() + 10)))) {
        			
        			List<StopTime> finalStopTimes = stopTimeRepo.findByTripIdAndStopId(stopTime.getTripId(), finalStopId);
        			List<StopTime> validStopTimes = stopTimeRepo.findByTripIdAndStopId(stopTime.getTripId(), stopTime.getStopId());
        			
        			if(finalStopTimes.size() <= 0) {
        				continue;
        			}
        		
        			if(validStopTimes.get(0).getStopSequence() > finalStopTimes.get(0).getStopSequence()) {
        				continue;
        			}
        			
        			if(validStopTime != null) {
        				String[] validTime = validStopTime.getDepartureTime().split(":");
        				vTime = false;
        				//System.out.println("Checking Time");
        			
        			
        				if((Integer.parseInt(time[1]) < Integer.parseInt(validTime[1]))) {
        					vTime = true;
        				}
        			}
        		
        			if(vTime == false) {
        				continue;
        			}
        		
        		
        			if(departTime.getDayOfWeek().getValue() == 6 &&
            			tripOptional.get().getServiceId().contains("Saturday")) {
        				validStopTime = stopTime;
        				//System.out.println("Saturday");
            		
            		
        			}else if(departTime.getDayOfWeek().getValue() == 7 &&
            				tripOptional.get().getServiceId().contains("Sunday")) {
        				validStopTime = stopTime;
        				//System.out.println("Sunday");
            		
            		
        			}else if((departTime.getDayOfWeek().getValue() > 0 && departTime.getDayOfWeek().getValue() < 6)
            				&& tripOptional.get().getServiceId().contains("Weekday")){
        				validStopTime = stopTime;
        				//System.out.println("Weekday");
            			
        			}
            	
        		}
        		
        	}
        
        //Debug	
        //System.out.println(validStopTime.toString());	
        return validStopTime;	
    } 
    
}