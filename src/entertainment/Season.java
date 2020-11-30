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
    private final int duration;
    /**
     * List of ratings for each season
     */
    private final List<Double> ratings;

    private final Map<String, Double> ratingList = new HashMap<>();

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public Map<String, Double> getRatingList() {
        return ratingList;
    }
    /**
     * A method to calculate the average grade for a season
     */
    public Double calcAverage(final List<Double> ratings) {
        Double average = 0.0;
        if (ratings.size() == 0) {
            return average;
        }
        for (Double rating : ratings) {
            average += rating;
        }
        average /= ratings.size();
        return average;
    }
    /**
     * toString
     */
    @Override
    public String toString() {
        return "Season{"
                + "currentSeason=" + currentSeason
                + ", ratings=" + ratings + '}';
    }
    /**
     * A method to check if the given user already rated the given video.
     */
    public boolean checkIfAlreadyRated(final User user, final Action action) {
        if (user.checkIfViewed(user, action)) {
            return !ratingList.containsKey(action.getUsername());
        }
        return false;
    }
}

