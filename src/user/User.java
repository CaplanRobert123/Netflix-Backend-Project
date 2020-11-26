package user;

import action.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class User {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;
    /**
     * Movies rated by user and the grade
     */
    private final Map<String, Double> ratingList = new HashMap<>();

    private int numberOfRatings;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history, final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = favoriteMovies;
        this.numberOfRatings = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public Map<String, Double> getRatingList() {
        return ratingList;
    }

    /**
     * Checks if the user has viewed the movie from the given action.
     */
    public boolean checkIfViewed(final User user, final Action action) {
        Map<String, Integer> map = user.getHistory();
        return map.containsKey(action.getTitle());
    }

    /**
     * Checks if the movie is already in the user's favorite list
     */
    public boolean checkIfFavorite(final User user, final Action action) {
        return user.getFavoriteMovies().contains(action.getTitle());
    }

    /**
     * Returns false if the movie has not been seen or is already rated.
     */
    public boolean checkIfAlreadyRated(final User user, final Action action) {
        if (checkIfViewed(user, action)) {
            return !ratingList.containsKey(action.getTitle());
        }
        return false;
    }

}
