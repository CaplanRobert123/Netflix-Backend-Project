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
    User user = new User();

//    private final int actionId;
//    /**
//     * Type of action
//     */
//    private final String actionType;
//    /**
//     * Used for commands
//     */
//    private final String type;
//    /**
//     * Username of user
//     */
//    private final String username;
//    /**
//     * The type of object on which the actions will be performed
//     */
//    private final String objectType;
//    /**
//     * Sorting type: ascending or descending
//     */
//    private final String sortType;
//    /**
//     * The criterion according to which the sorting will be performed
//     */
//    private final String criteria;
//    /**
//     * Video title
//     */
//    private final String title;
//    /**
//     * Video genre
//     */
//    private final String genre;
//    /**
//     * Query limit
//     */
//    private final int number;
//    /**
//     * Grade for rating - aka value of the rating
//     */
//    private final double grade;
//    /**
//     * Season number
//     */
//    private final int seasonNumber;
//    /**
//     * Filters used for selecting videos
//     */
//    private final List<List<String>> filters = new ArrayList<>();
//
//    public Action(int actionId, String actionType, String type,
//                  String username, String objectType, String sortType,
//                  String criteria, String title, String genre,
//                  int number, double grade, int seasonNumber) {
//        this.actionId = actionId;
//        this.actionType = actionType;
//        this.type = type;
//        this.username = username;
//        this.objectType = objectType;
//        this.sortType = sortType;
//        this.criteria = criteria;
//        this.title = title;
//        this.genre = genre;
//        this.number = number;
//        this.grade = grade;
//        this.seasonNumber = seasonNumber;
//    }

    public List<ActionInputData> getActionsList(Input input) {
        return input.getCommands();
    }

//    public boolean checkCommand(ActionInputData actionInputData) {
//        if(actionInputData.getActionType().equals(Constants.COMMAND))
//            return true;
//        return false;
//    }

    public void doCommand(ActionInputData actionInputData) {
        if(command.checkCommand(actionInputData))
            if(command.checkType(actionInputData).equals(Constants.FAVORITE))
                user.checkFavorite(actionInputData.getUsername(), actionInputData); //FIX THIS WITH THE ONE IN ACTION CLASS
    }
}
