package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Movie {
    private String title;
    /**
     * The year the movie was released
     */
    private int year;
    /**
     * Show casting
     */
    private ArrayList<String> cast;
    /**
     * Show genres
     */
    private ArrayList<String> genres;
    /**
     * Duration in minutes of a movie
     */
    private int duration;
    /**
     * List of ratings for each movie
     */
    private List<Double> ratings = new ArrayList<>();

    public Movie(String title, int year, ArrayList<String> cast, ArrayList<String> genres, int duration) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }

    public Double calcAverage(List<Double> ratings) {
        Double average = 0.0;
        for (int i = 0; i < ratings.size(); i++) {
            average += ratings.get(i);
        }
        if (average != 0) {
            average /= ratings.size();
        } else {
            average = 0.0;
        }
        return average;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", ratings=" + ratings +
                '}';
    }
}
