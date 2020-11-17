package action;

import actor.Actor;
import common.Constants;
import fileio.ActionInputData;
import fileio.MovieInputData;
import user.User;

import java.util.Map;

public class Query {
    public boolean checkQuery(ActionInputData actionInputData) {
        return actionInputData.getActionType().equals(Constants.QUERY);
    }

    public void checkType(ActionInputData actionInputData) {
        if (checkQuery(actionInputData)) {
            if (actionInputData.getObjectType().equals(Constants.ACTORS)) {
                if(actionInputData.getCriteria().equals(Constants.AVERAGE)) {
//                    doAverage();
                }
            }
        }
    }

    public void doAverage(User user, ActionInputData actionInputData, MovieInputData movieInputData) {
        Double average;
        Map<String, Double> map = user.getRatingList();


    }
}
