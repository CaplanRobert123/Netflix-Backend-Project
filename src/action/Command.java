package action;

import common.Constants;
import fileio.ActionInputData;

public class Command {
    public boolean checkCommand(ActionInputData actionInputData) {
        return actionInputData.getActionType().equals(Constants.COMMAND);
    }

    public String checkType(ActionInputData actionInputData) {
        return switch (actionInputData.getType()) {
            case Constants.VIEW -> Constants.VIEW;
            case Constants.FAVORITE -> Constants.FAVORITE;
            case Constants.RATING -> Constants.RATING;
            default -> "";
        };
    }
}
