package entertainment;

import fileio.Input;
import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private Double average;
    private List<Double> ratings = new ArrayList<>();

    public Movie(String title) {
        this.title = title;
    }

    public List<MovieInputData> getMoviesList(Input input) {
        return input.getMovies();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> rating) {
        this.ratings = rating;
    }

    public void addToMovieList(ArrayList<Movie> movieList) {
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", average=" + average +
                ", ratings=" + ratings +
                '}';
    }
}
