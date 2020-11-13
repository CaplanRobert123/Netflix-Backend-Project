package action;

import common.Constants;
import fileio.ActionInputData;
import fileio.UserInputData;
import user.User;

import java.util.Map;

public class Command {
    User user = new User();

    /**
     * Method that checks if the received action needs to do a command.
     */
    public boolean checkCommand(ActionInputData actionInputData) {
        return actionInputData.getActionType().equals(Constants.COMMAND);
    }

    /**
     * Method that checks the type of the command.
     */
    public String checkType(ActionInputData actionInputData) {
        return switch (actionInputData.getType()) {
            case Constants.VIEW -> Constants.VIEW;
            case Constants.FAVORITE -> Constants.FAVORITE;
            case Constants.RATING -> Constants.RATING;
            default -> "";
        };
    }


    /**
     * Implements the "view" command of an action.
     */
    public String doView(UserInputData userInputData, ActionInputData actionInputData) {
        Map<String, Integer> map = userInputData.getHistory();
        Integer nrOfViews;
        if (user.checkIfViewed(userInputData, actionInputData)) {
            nrOfViews = map.get(actionInputData.getTitle());
            nrOfViews++;
            return "success -> " + actionInputData.getTitle() + " was viewed with total views of " + nrOfViews;
        }
        else {
            nrOfViews = 1;
            map.put(actionInputData.getTitle(), nrOfViews);
            return "success -> " + actionInputData.getTitle() + " was viewed with total views of " + nrOfViews;
        }
    }

    /**
     * Implements the "favorite" command of an action.
     */
    public String doFavorite(UserInputData userInputData, ActionInputData actionInputData) {
        if (user.checkIfViewed(userInputData, actionInputData)) {
//            if (userInputData.getFavoriteMovies().contains(actionInputData.getTitle())) {
            if(user.checkIfFavorite(userInputData, actionInputData)) {
                return "error -> " + actionInputData.getTitle() + " is already in favourite list";
            } else {
                userInputData.getFavoriteMovies().add(actionInputData.getTitle());
                return "success -> " + actionInputData.getTitle() + " was added as favourite";
            }
        }
        return "error -> " + actionInputData.getTitle() + " is not seen";
    }

    /**
     * Implements the "rating" command of an action.
     */
    public String doRating(UserInputData userInputData, ActionInputData actionInputData) {
        if(user.checkIfAlreadyRated(actionInputData, userInputData)) {
            user.getRatingList().put(actionInputData.getTitle(), actionInputData.getGrade());
            return "success -> " + actionInputData.getTitle() + " was rated with " +
                    actionInputData.getGrade() + " by " + actionInputData.getUsername();
        }
        return "error -> " + actionInputData.getTitle() + " is not seen";
    }
}
