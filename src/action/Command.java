package action;

import entertainment.Movie;
import entertainment.Serial;
import user.User;

import java.util.List;
import java.util.Map;

public class Command {
    /**
     * Implements the "view" command of an action.
     */
    public String doView(User user, Action action) {
        Map<String, Integer> map = user.getHistory();
        int nrOfViews;
        if (user.checkIfViewed(user, action)) {
            nrOfViews = map.get(action.getTitle());
            nrOfViews++;
        } else {
            nrOfViews = 1;
        }
        map.put(action.getTitle(), nrOfViews);
        return "success -> " + action.getTitle() + " was viewed with total views of " + nrOfViews;
    }

    /**
     * Implements the "favorite" command of an action.
     */
    public String doFavorite(User user, Action action) {
        if (user.checkIfViewed(user, action)) {
            if(user.checkIfFavorite(user, action)) {
                return "error -> " + action.getTitle() + " is already in favourite list";
            } else {
                user.getFavoriteMovies().add(action.getTitle());
                return "success -> " + action.getTitle() + " was added as favourite";
            }
        }
        return "error -> " + action.getTitle() + " is not seen";
    }

    /**
     * Implements the "rating" command of an action.
     */
    public String doRating(User user, Action action, List<Movie> movieList, List<Serial> serialList) {
        if (!user.checkIfViewed(user, action)) {
            return "error -> " + action.getTitle() + " is not seen";
        }
        if(action.getSeasonNumber() != 0) {
            for (Serial serial : serialList) {
                if (serial.getTitle().equals(action.getTitle())) {
                    for (int j = 0; j < serial.getNumberOfSeasons(); j++) {
                        if (j + 1 == action.getSeasonNumber()) {
                            if (serial.getSeasons().get(j).checkIfAlreadyRated(user, action)) {
                                serial.getSeasons().get(j).getRatings().add(action.getGrade());
                                serial.getSeasons().get(j).getRatingList().put(action.getUsername(), action.getGrade());
                            } else {
                                return "error -> " + action.getTitle() + " has been already rated";
                            }
                        }
                    }
                }
            }
        }
        else {
            if (user.checkIfAlreadyRated(user, action)) {
                for (Movie movie : movieList) {
                    if (movie.getTitle().equals(action.getTitle())) {
                        movie.getRatings().add(action.getGrade());
                    }
                }
                user.getRatingList().put(action.getTitle(), action.getGrade());
            } else {
                return "error -> " + action.getTitle() + " has been already rated";
            }
        }
        user.setNumberOfRatings(user.getNumberOfRatings() + 1);
        return "success -> " + action.getTitle() + " was rated with " +
                action.getGrade() + " by " + action.getUsername();
    }
}
