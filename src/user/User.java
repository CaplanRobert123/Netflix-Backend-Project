package user;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private Map<String, Double> ratingList = new HashMap<>();
    public List<UserInputData> getUserList(Input input) {
        return input.getUsers();
    }

    /**
     * Checks if the movie is already in the user's favorite list
     */
    public boolean checkIfFavorite(UserInputData userInputData, ActionInputData actionInputData) {
        return userInputData.getFavoriteMovies().contains(actionInputData.getTitle());
    }

    /**
     * Checks if the user has viewed the movie from the given action.
     */
    public boolean checkIfViewed(UserInputData userInputData, ActionInputData actionInputData) {
        Map<String, Integer> map = userInputData.getHistory();
        return map.containsKey(actionInputData.getTitle());
    }

    /**
     * Returns false if the movie has not been seen or is already rated.
     */
    public boolean checkIfAlreadyRated(ActionInputData actionInputData, UserInputData userInputData) {
        if(checkIfViewed(userInputData, actionInputData)) {
            return !ratingList.containsKey(actionInputData.getTitle());
        }
        return false;
    }

    public Map<String, Double> getRatingList() {
        return ratingList;
    }

    public void setRatingList(Map<String, Double> ratingList) {
        this.ratingList = ratingList;
    }
}