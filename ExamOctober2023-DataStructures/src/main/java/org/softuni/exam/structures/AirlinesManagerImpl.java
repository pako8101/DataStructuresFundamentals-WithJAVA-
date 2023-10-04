package org.softuni.exam.structures;

import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Flight;

import java.util.*;
import java.util.stream.Collectors;

public class AirlinesManagerImpl implements AirlinesManager {
    private Map<String, Airline> airlinesById;
    private Map<String, Flight> flightsById;
    private  Map<String,Flight> completed = new LinkedHashMap<>();
    private Map<String, List<Flight>> flightsByAirline;

    public AirlinesManagerImpl() {
        this.airlinesById = new LinkedHashMap<>();
        this.flightsById = new LinkedHashMap<>();
        this.flightsByAirline = new LinkedHashMap<>();
    }

    @Override
    public void addAirline(Airline airline) {
        airlinesById.put(airline.getId(), airline);
        flightsByAirline.put(airline.getId(), new ArrayList<>());
    }

    @Override
    public void addFlight(Airline airline, Flight flight) {
        if (!contains(airline)) throw new IllegalArgumentException();

        flightsById.put(flight.getId(), flight);
        flightsByAirline.get(airline.getId()).add(flight);
        if (flight.isCompleted()){
            completed.put(flight.getId(), flight);
        }

    }

    @Override
    public boolean contains(Airline airline) {
        return airlinesById.containsKey(airline);
    }

    @Override
    public boolean contains(Flight flight) {
        return flightsById.containsKey(flight);
    }

    @Override
    public void deleteAirline(Airline airline) throws IllegalArgumentException {
        if (!contains(airline)) throw new IllegalArgumentException();

        airlinesById.remove(airline.getId());
        List<Flight> airlineToRemove = flightsByAirline.get(airline.getId());
        for (Flight flight : airlineToRemove) {
            flightsById.remove(flight.getId());
            completed.remove(flight.getId());
        }
    }

    @Override
    public Iterable<Flight> getAllFlights() {
        return flightsById.values();
    }

    @Override
    public Flight performFlight(Airline airline, Flight flight) throws IllegalArgumentException {
        if (!contains(airline) || !contains(flight)) throw new IllegalArgumentException();

        Flight flightsPerform = flightsById.get(flight.getId());
        flightsPerform.setCompleted(true);
completed.put(flightsPerform.getId(),flightsPerform);

        return flightsPerform;
    }

    @Override
    public Iterable<Flight> getCompletedFlights() {
        return completed.values();
    }

    @Override
    public Iterable<Flight> getFlightsOrderedByNumberThenByCompletion() {
        return flightsById.values().stream()
                .sorted(Comparator.comparing(Flight::isCompleted)
                        .thenComparing(Flight::getNumber))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesOrderedByRatingThenByCountOfFlightsThenByName() {

        return airlinesById.values().stream()
                .sorted(Comparator.comparing(Airline::getRating)
                        .thenComparing(a-> flightsByAirline.get(a.getId()).size()).reversed()
                        .thenComparing(Airline::getName))
                .collect(Collectors.toList());


    }

    @Override
    public Iterable<Airline> getAirlinesWithFlightsFromOriginToDestination(String origin, String destination) {
        return airlinesById.values()
                .stream()
                .filter(a -> flightsByAirline.get(a.getId()).stream().anyMatch(f->!f.isCompleted()
                && f.getOrigin().equals(origin) && f.getDestination().equals(destination)))
                .collect(Collectors.toList());
    }
}
