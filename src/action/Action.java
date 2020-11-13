package action;

import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import user.User;

import java.util.List;

public class Action {
    Command command = new Command();
    Query query = new Query();
    Recommendation recommendation = new Recommendation();
    User user = new User();

    public List<ActionInputData> getActionsList(Input input) {
        return input.getCommands();
    }

    public void doAction() {

    }

    /**
     * If the action type is a command, it checks what type of command it is and executes it
     */
    public String doCommand(UserInputData userInputData, ActionInputData actionInputData) {
        if (command.checkCommand(actionInputData)) {
            if (command.checkType(actionInputData).equals(Constants.VIEW)) {
                return command.doView(userInputData, actionInputData);
            }
            if (command.checkType(actionInputData).equals(Constants.FAVORITE)) {
                return command.doFavorite(userInputData, actionInputData);
            }
            if (command.checkType(actionInputData).equals(Constants.RATING)) {
                return command.doRating(userInputData, actionInputData);
            }
        }
        return "Didnt do anything";
    }
}
