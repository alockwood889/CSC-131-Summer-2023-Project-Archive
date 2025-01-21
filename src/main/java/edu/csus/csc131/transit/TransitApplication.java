package edu.csus.csc131.transit;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.csus.csc131.transit.data.StopTime;
import edu.csus.csc131.transit.data.Transfer;
import edu.csus.csc131.transit.data.Trip;
import edu.csus.csc131.transit.data.Route;
import edu.csus.csc131.transit.data.Stop;
import edu.csus.csc131.transit.repository.RouteRepository;
import edu.csus.csc131.transit.repository.StopRepository;
import edu.csus.csc131.transit.repository.StopTimeRepository;
import edu.csus.csc131.transit.repository.TransferRepository;
import edu.csus.csc131.transit.repository.TripRepository;

@SpringBootApplication
public class TransitApplication implements CommandLineRunner {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Value("${dataRoot}")
	private String dataDir;

	@Autowired
	private StopTimeRepository stopTimeRepo;
	
	@Autowired
	private TransferRepository transferRepo;

	@Autowired
	private RouteRepository routeRepository;

	@Autowired
	private TripRepository tripRepo;
	
	@Autowired
	private StopRepository stopRepository;

	public static void main(String[] args) {
		SpringApplication.run(TransitApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createStops();
		createRoutes();
		createTransfers();
		createTrips();
		createStopTimes();
	}

	/**
	 * Reads routes from a file named "stops.txt" and saves them to the database.
	 * 
	 * @author Jalen Grant Hall
	 * @date 07/28/2023
	 */
	private void createStops() {
		stopRepository.deleteAll();
		log.info("Start creating stops");
		Path path = FileSystems.getDefault().getPath(dataDir, "stops.txt");
		int count = 0;
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line = reader.readLine(); // skip the first line
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

				String stopId = tokens[0].replaceAll("^\"|\"$", "").trim();
				String stopName = tokens[2].replaceAll("^\"|\"$", "").trim();
				double latitude = Double.parseDouble(tokens[4].replaceAll("^\"|\"$", "").trim());
				double longitude = Double.parseDouble(tokens[5].replaceAll("^\"|\"$", "").trim());

				Stop stop = new Stop(stopId, stopName, latitude, longitude);
				stop = stopRepository.save(stop);
				count++;
			}
		} catch (IOException x) {
			log.error("IOException: " + x.getMessage(), x);
		} catch (NumberFormatException e) {
			log.error("NumberFormatException: " + e.getMessage(), e);
		}

		log.info("Finished creating {} stops", count);
	}

	/**
	 * Reads routes from a file named "routes.txt" and saves them to the database.
	 * 
	 * @author Jalen Grant Hall
	 * @date 07/24/2023
	 */
	private void createRoutes() {
		routeRepository.deleteAll();
		log.info("Start creating routes");
		Path path = FileSystems.getDefault().getPath(dataDir, "routes.txt");
		int count = 0;
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line = reader.readLine(); // skip the first line
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");

				Route route = new Route();
				route.setId(tokens[0].trim());
				route.setShortName(tokens[1].trim());
				route.setLongName(tokens[2].trim());

				route = routeRepository.save(route);
				count++;
			}
		} catch (IOException x) {
			log.error("IOException: " + x.getMessage(), x);
		}
		
		log.info("Finished creating {} routes", count);
	}

	// ToDo: read transfers from "transfers.txt" and save them to the database
	/* Create method for Transfer 
	 * Author: Alyssa Lockwood
	 * Updated: 7/25/2023
	 */
	private void createTransfers() {
		transferRepo.deleteAll();
		log.info("Start creating transfers");
		Path path = FileSystems.getDefault().getPath(dataDir, "transfers.txt");
		int count = 0;
		
		try(BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)){
			String line = reader.readLine();
			
			while((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				int lastIndex = tokens.length - 1;
				
				Transfer transfer = new Transfer();
				transfer.setFromStopId(tokens[0].trim());
				transfer.setToStopId(tokens[1].trim());
				transfer.setMinTransferTime(Integer.parseInt(tokens[3]));
				transfer.setFromRouteId(tokens[lastIndex-1].trim());
				transfer.setToRouteId(tokens[lastIndex].trim());
				
				transfer = transferRepo.save(transfer);
				count++;
			}
			
		}catch(IOException e) {
			log.error("IO exception: " + e.getMessage(), e);
		}
		
		log.info("Finished creating {} transfers", count);
	}

	// ToDo: read trips from "trips.txt" and save them to the database
	/* 
	 * Author: Nancy Zhu
	 */
	private void createTrips() {

		tripRepo.deleteAll();
		log.info("Start creating Trips");
		Path path = FileSystems.getDefault().getPath(dataDir, "trips.txt");
		int count = 0;
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				int lastIndex = tokens.length - 1;
				
				Trip trip = new Trip();
				trip.setRouteId(tokens[0].trim());
				trip.setServiceId(tokens[1].trim());
				trip.setId(tokens[2].trim());
				trip.setDirectionId(Integer.parseInt(tokens[lastIndex]));
				
				trip = tripRepo.save(trip);
				count++;
				
			}
		} catch (IOException x) {
			log.error("IOException: " + x.getMessage(), x);
		}
		log.info("Finished creating {} Trips", count);
	}
	private void createStopTimes() {
		stopTimeRepo.deleteAll();
		log.info("Start createing stopTimes");
		Path path = FileSystems.getDefault().getPath(dataDir, "stop_times.txt");
		int count = 0;
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line = reader.readLine(); // skip the first line
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				int lastIndex = tokens.length - 1;

				StopTime stopTime = new StopTime();
				stopTime.setTripId(tokens[0].trim());
				stopTime.setArrivalTime(tokens[1].trim());
				stopTime.setDepartureTime(tokens[2].trim());
				stopTime.setStopId(tokens[3].trim());
				stopTime.setStopSequence(Integer.parseInt(tokens[4].trim()));
				stopTime.setDistTraveled(Double.parseDouble(tokens[lastIndex]));

				stopTime = stopTimeRepo.save(stopTime);
				count++;
			}
		} catch (IOException x) {
			log.error("IOException: " + x.getMessage(), x);
		}
		log.info("Finished createing {} stopTimes", count);
	}

}
