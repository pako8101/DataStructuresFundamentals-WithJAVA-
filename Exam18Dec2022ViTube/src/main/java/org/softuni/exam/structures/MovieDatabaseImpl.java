package org.softuni.exam.structures;

import org.softuni.exam.entities.Actor;
import org.softuni.exam.entities.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class MovieDatabaseImpl implements MovieDatabase {

    private Map<String, Actor> actors = new LinkedHashMap<>();
    private Map<String, Movie> movies = new LinkedHashMap<>();
    private Map<String, Actor> actorsWithoutMovies = new LinkedHashMap<>();
    private Map<String, List<Movie>> moviesByActor = new LinkedHashMap<>();


    @Override
    public void addActor(Actor actor) {
        this.actors.put(actor.getId(), actor);
        moviesByActor.put(actor.getId(), new ArrayList<>());
    }

    @Override
    public void addMovie(Actor actor, Movie movie) throws IllegalArgumentException {
        if (!contains(actor)) throw new IllegalArgumentException();

        actors.put(actor.getId(), actor);
        moviesByActor.get(actor.getId()).add(movie);
Actor actorNoMovie = actors.get(actor.getId());

    }

    @Override
    public boolean contains(Actor actor) {
        return actors.containsValue(actor);
    }

    @Override
    public boolean contains(Movie movie) {
        return movies.containsValue(movie);
    }

    @Override
    public Iterable<Movie> getAllMovies() {
        return movies.values();
    }

    @Override
    public Iterable<Actor> getNewbieActors() {
//        return actors.values().stream()
//                .filter(actor -> actor.getMovies()
//                        .isEmpty()).collect(Collectors.toList());
        List<Actor> newbieActors = new ArrayList<>();
        for (Actor actor : actors.values()) {
            if (actor.getMovies().isEmpty()) {
                newbieActors.add(actor);
            }
        }
        return newbieActors;
    }

    @Override
    public Iterable<Movie> getMoviesOrderedByBudgetThenByRating() {
        return movies.values().stream()
                .sorted(Comparator.comparing(Movie::getBudget)
                        .reversed().thenComparing(Movie::getRating).reversed()
                        .thenComparing(Movie::getTitle).reversed()).collect(Collectors.toList());
    }

    @Override
    public Iterable<Actor> getActorsOrderedByMaxMovieBudgetThenByMoviesCount() {

        return actors.values().stream()
                .sorted(Comparator.comparing(Actor::getMaxMovieBudget).reversed()
                        .thenComparing(Actor::getMovieCount).reversed()).collect(Collectors.toList());
    }

    @Override
    public Iterable<Movie> getMoviesInRangeOfBudget(double lower, double upper) {
        return movies
                .values().stream()
                .filter(m->m.getBudget()>=lower && m.getBudget()<=upper).collect(Collectors.toList());
    }
}
