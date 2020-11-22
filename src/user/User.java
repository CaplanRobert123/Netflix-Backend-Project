package user;

import action.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class User {
    /**
     * User's username
     */
    private String username;
    /**
     * Subscription Type
     */
    private String subscriptionType;
    /**
     * The history of the movies seen
     */
    private Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private ArrayList<String> favoriteMovies;
    /**
     * Movies rated by user and the grade
     */
    private Map<String, Double> ratingList = new HashMap<>();

    private int numberOfRatings;

    private double average;

    public User(String username, String subscriptionType, Map<String, Integer> history, ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = favoriteMovies;
        this.numberOfRatings = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Integer> history) {
        this.history = history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(ArrayList<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public Map<String, Double> getRatingList() {
        return ratingList;
    }

    public void setRatingList(Map<String, Double> ratingList) {
        this.ratingList = ratingList;
    }

    /**
     * Checks if the user has viewed the movie from the given action.
     */
    public boolean checkIfViewed(User user, Action action) {
        Map<String, Integer> map = user.getHistory();
        return map.containsKey(action.getTitle());
    }

    /**
     * Checks if the movie is already in the user's favorite list
     */
    public boolean checkIfFavorite(User user, Action action) {
        return user.getFavoriteMovies().contains(action.getTitle());
    }

    /**
     * Returns false if the movie has not been seen or is already rated.
     */
    public boolean checkIfAlreadyRated(User user, Action action) {
        if(checkIfViewed(user, action)) {
            return !ratingList.containsKey(action.getTitle());
        }
        return false;
    }

}
