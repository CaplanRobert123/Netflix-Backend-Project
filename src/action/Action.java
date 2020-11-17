package action;

import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;

import java.util.List;

public class Action {
    private Command command = new Command();
    private Query query = new Query();
    private Recommendation recommendation = new Recommendation();

    public List<ActionInputData> getActionsList(Input input) {
        return input.getCommands();
    }

    /**
     * If the action type is a command, it checks what type of command it is and executes it
     */
    public String doCommand(UserInputData userInputData, ActionInputData actionInputData, Input input) {
        if (command.checkCommand(actionInputData)) {
            if (command.checkType(actionInputData).equals(Constants.VIEW)) {
                return command.doView(userInputData, actionInputData);
            }
            if (command.checkType(actionInputData).equals(Constants.FAVORITE)) {
                return command.doFavorite(userInputData, actionInputData);
            }
            if (command.checkType(actionInputData).equals(Constants.RATING)) {
                return command.doRating(userInputData, actionInputData, input);
            }
        }
        return "Didnt do anything";
    }

//    public String doQuery(ActionInputData actionInputData) {
//        if(query.checkQuery(actionInputData)) {
//            if(query.checkType(actionInputData).equals(Constants.AVERAGE)) {
//                return query.Average();
//            }
//        }
//    }

}
