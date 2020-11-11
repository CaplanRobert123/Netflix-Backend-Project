package action;

import common.Constants;
import entertainment.Movie;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.Writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Command {

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

    public String doView(UserInputData userInputData, ActionInputData actionInputData) {
        Map<String, Integer> map = userInputData.getHistory();
        Integer nrOfViews = map.get(actionInputData.getTitle());
//        if (map.containsKey(actionInputData.getTitle())) {
//            nrOfViews = map.get(actionInputData.getTitle());
//            nrOfViews++;
//        }
        nrOfViews++;
        return nrOfViews.toString();
    }

    public void doFavorite() {

    }
}
