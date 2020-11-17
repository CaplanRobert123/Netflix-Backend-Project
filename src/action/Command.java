package action;

import common.Constants;
import entertainment.Movie;
import entertainment.Serial;
import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Command{
    User user = new User();
    List<Movie> movieList = new ArrayList<>();
    List<Serial> serialList = new ArrayList<>();

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
    public String doRating(UserInputData userInputData, ActionInputData actionInputData, Input input) {
        if(user.checkIfAlreadyRated(actionInputData, userInputData)) {
            if(actionInputData.getSeasonNumber() != 0) {
                int ok = 0;
                int j = 0;
                Serial serial = new Serial(actionInputData.getTitle());
                for (int i = 0; i < serial.getSerialsList(input).size(); i++) {
                    if(serial.getSerialsList(input).get(i).getTitle().equals(actionInputData.getTitle())) {
                        serial.setNumberOfSeasons(serial.getSerialsList(input).get(i).getNumberSeason());
                        serial.setSeasons(serial.getSerialsList(input).get(i).getSeasons());
                    }
                }
                for (int i = 0; i < serialList.size(); i++) {
                    if(serialList.get(i).getTitle().equals(serial.getTitle())) {
                        ok = 1;
                        j = i;
                    }
                }
                if( ok != 1 ){
                    serialList.add(serial);
                    serialList.get(serialList.indexOf(serial)).getSeasons().
                            get(actionInputData.getSeasonNumber()-1).getRatings().add(actionInputData.getGrade());
                    System.out.println(serial.toString());
                }
                if(ok == 1) {
                    serialList.get(j).getSeasons().
                            get(actionInputData.getSeasonNumber() - 1).getRatings().add(actionInputData.getGrade());
                    System.out.println(serial.toString());
                }
            }
            else {
                int ok = 0;
                int j = 0;
                Movie movie = new Movie(actionInputData.getTitle());
                for (int i = 0; i < movieList.size(); i++) {
                    if(movieList.get(i).getTitle().equals(movie.getTitle())) {
                        ok = 1;
                        j = i;
                    }
                }
                if( ok != 1 ) {
                    movieList.add(movie);
                    movieList.get(movieList.indexOf(movie)).getRatings().add(actionInputData.getGrade());
                }
                if(ok == 1)
                    movieList.get(j).getRatings().add(actionInputData.getGrade());
            }
            return "success -> " + actionInputData.getTitle() + " was rated with " +
                    actionInputData.getGrade() + " by " + actionInputData.getUsername();
        }
        return "error -> " + actionInputData.getTitle() + " is not seen";
    }
}
