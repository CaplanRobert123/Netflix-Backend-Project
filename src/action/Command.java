package action;

import entertainment.Movie;
import entertainment.Serial;
import user.User;

import java.util.List;
import java.util.Map;

public class Command {
    /**
     * Caut in istoricul de vizionari al user-ului, daca video-ul cautat se afla in lista,
     * incrementez numarul de vizionari al acelui video. Daca nu il gasesc in lista,
     * il adaug cu numarul de vizionari 1
     * @param user for modifying its history list
     * @param action for getting the title of the video and checking if it has been seen already
     */
    public String doView(final User user, final Action action) {
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
     * Verific daca user-ul a vizionat video-ul in primul rand. Daca a fost vizionat,
     * verific daca nu cumva se afla deja in lista de video-uri favorite.
     * Daca nu se afla il adaug si returnez mesaj de succes, altfel returnez mesaj de eroare
     * si nu fac nicio modificare asupra listei.
     * @param user for modifying its favorite list
     * @param action for getting the title of the video and checking if it is already in the
     *               favorite list
     */
    public String doFavorite(final User user, final Action action) {
        if (user.checkIfViewed(user, action)) {
            if (user.checkIfFavorite(user, action)) {
                return "error -> " + action.getTitle() + " is already in favourite list";
            } else {
                user.getFavoriteMovies().add(action.getTitle());
                return "success -> " + action.getTitle() + " was added as favourite";
            }
        }
        return "error -> " + action.getTitle() + " is not seen";
    }

    /**
     * Verific in primul rand daca a fost vazut video-ul. Dupa aceea, verific daca se da rating unui
     * film sau unui serial. Daca se da rating unui serial, iterez prin lista de seriale pana il
     * gasesc pe cel cautat, verific pentru ce sezon trebuie sa dau rating,verific sa nu fi dat deja
     * rating pentru acel sezon si daca totul este ok, adaug in map-ul de ratings numele serialului
     * si nota, si in lista de rating-uri a sezonului nota. Pentru filme procedez similar.
     * De asemenea, incrementez numarul de rating-uri al utilizatorului pentru a-l folosi la Query.
     * @param user for checking if he already rated a video and for adding to its number of ratings
     *             in order to count his activity
     * @param action for getting the required info (title, grade, season number, etc)
     * @param movieList for iterating trough the list of movies
     * @param serialList for iterating trough the list of serials
     */
    public String doRating(final User user, final Action action,
                           final List<Movie> movieList, final List<Serial> serialList) {
        if (!user.checkIfViewed(user, action)) {
            return "error -> " + action.getTitle() + " is not seen";
        }
        if (action.getSeasonNumber() != 0) {
            for (Serial serial : serialList) {
                if (serial.getTitle().equals(action.getTitle())) {
                    for (int j = 0; j < serial.getNumberOfSeasons(); j++) {
                        if (j + 1 == action.getSeasonNumber()) {
                            if (serial.getSeasons().get(j).checkIfAlreadyRated(user, action)) {
                                serial.getSeasons().get(j).getRatings().add(action.getGrade());
                                serial.getSeasons().get(j).getRatingList().put(action.getUsername(),
                                        action.getGrade());
                            } else {
                                return "error -> " + action.getTitle() + " has been already rated";
                            }
                        }
                    }
                }
            }
        } else {
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
        return "success -> " + action.getTitle() + " was rated with "
                + action.getGrade() + " by " + action.getUsername();
    }
}
