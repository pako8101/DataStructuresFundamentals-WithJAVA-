package org.softuni.exam.entities;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private String id;

    private String name;

    private int age;
private List<Movie>movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Actor(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.movies = new ArrayList<>();
    }

    public double getMaxMovieBudget() {
        return movies.stream()
                .mapToDouble(Movie::getBudget)
                .max()
                .orElse(0);
    }

    public int getMovieCount() {
        return movies.size();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
