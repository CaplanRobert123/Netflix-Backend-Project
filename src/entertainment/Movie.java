package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Movie {
    private final String title;
    /**
     * The year the movie was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    /**
     * Duration in minutes of a movie
     */
    private final int duration;
    /**
     * List of ratings for each movie
     */
    private final List<Double> ratings = new ArrayList<>();

    public Movie(final String title, final int year, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int duration) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public int getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    /**
     * A method to calculate the average grade for a movie
     */
    public Double calcAverage(final List<Double> ratings) {
        Double average = 0.0;
        for (Double rating : ratings) {
            average += rating;
        }
        if (average != 0) {
            average /= ratings.size();
        } else {
            average = 0.0;
        }
        return average;
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        return "Movie{"
                + "title='" + title + '\''
                + ", ratings=" + ratings + '}';
    }
}
