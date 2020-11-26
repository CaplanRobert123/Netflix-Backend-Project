package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Serial {
    private final String title;
    /**
     * The year the show was released
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
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public Serial(final String title, final int year, final ArrayList<String> cast,
                  final ArrayList<String> genres, final int numberOfSeasons,
                  final ArrayList<Season> seasons) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
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

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }
    /**
     * List of ratings for each movie
     */
    public Double calcAverage(final List<Season> seasonList) {
        double average = 0.0;
        for (Season season : seasonList) {
            average += season.calcAverage(season.getRatings());
        }
        if (average != 0) {
            average /= seasonList.size();
        } else {
            average = 0.0;
        }
        return average;
    }
    /**
     * List of ratings for each movie
     */
    public int serialDuration(final List<Season> seasonList) {
        int sum = 0;
        for (Season season : seasonList) {
            sum += season.getDuration();
        }
        return sum;
    }
    /**
     * List of ratings for each movie
     */
    @Override
    public String toString() {
        return "Serial{" +
                "title='" + title + '\''
                + ", seasons=" + seasons + '}';
    }
}
