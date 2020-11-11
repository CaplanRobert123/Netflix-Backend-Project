package action;

import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import user.User;

import java.util.ArrayList;
import java.util.List;

public class Action{
    Command command = new Command();
    Query query = new Query();
    Recommendation recommendation = new Recommendation();
    User user = new User();

    public List<ActionInputData> getActionsList(Input input) {
        return input.getCommands();
    }

//    public boolean checkCommand(ActionInputData actionInputData) {
//        if(actionInputData.getActionType().equals(Constants.COMMAND))
//            return true;
//        return false;
//    }

    public void doAction() {

    }

    public void doCommand(ActionInputData actionInputData) {
        if (command.checkCommand(actionInputData)) {
            if(command.checkType(actionInputData).equals(Constants.FAVORITE)) {
                command.doFavorite();
            }
        }
    }
}
