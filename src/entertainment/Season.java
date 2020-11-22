package entertainment;

import action.Action;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    private Map<String, Double> ratingList = new HashMap<>();

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    public Map<String, Double> getRatingList() {
        return ratingList;
    }

    public Double calcAverage(List<Double> ratings) {
        Double average = 0.0;
        if(ratings.size() == 0)
            return average;
        for (int i = 0; i < ratings.size(); i++) {
            average += ratings.get(i);
        }
        average /= ratings.size();
        return average;
    }

/*    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }*/

    @Override
    public String toString() {
        return "Season{" +
                "currentSeason=" + currentSeason +
                ", ratings=" + ratings +
                '}';
    }

    public boolean checkIfAlreadyRated(User user, Action action) {
        if(user.checkIfViewed(user, action)) {
            return !ratingList.containsKey(action.getUsername());
        }
        return false;
    }
}

