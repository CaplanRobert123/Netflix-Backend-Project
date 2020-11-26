package action;

import actor.Actor;
import entertainment.Movie;
import entertainment.Serial;
import user.User;
import utils.Utils;

import java.util.*;

public class Query {
    private final int words = 3;

    /**
     * for coding style
     */
    public String doAverage(final List<Actor> actorList, final List<Movie> movieList,
                            final List<Serial> serialList, final int n, final String sortType) {
        Map<String, Double> actorGrades = new HashMap<>();
        List<String> queriedActors = new ArrayList<>();
        for (Actor actor : actorList) {
            Map<String, Double> ratings = new HashMap<>();
            double average = 0;
            int cnt = 0;
            for (int j = 0; j < actor.getFilmography().size(); j++) {
                for (Movie movie : movieList) {
                    if (actor.getFilmography().get(j).equals(movie.getTitle())
                            && movie.calcAverage(movie.getRatings()) != 0) {
                        ratings.put(movie.getTitle(),
                                movie.calcAverage(movie.getRatings()));
                    }
                }
                for (Serial serial : serialList) {
                    if (actor.getFilmography().get(j).equals(serial.getTitle())
                            && serial.calcAverage(serial.getSeasons()) != 0) {
                        ratings.put(serial.getTitle(),
                                serial.calcAverage(serial.getSeasons()));
                    }
                }
            }
            Collection<Double> values = ratings.values();
            ArrayList<Double> listOfValues = new ArrayList<>(values);

            if (listOfValues.size() != 0) {
                for (Double listOfValue : listOfValues) {
                    if (listOfValue != 0.0) {
                        average += listOfValue;
                        cnt++;
                    }
                }
                average = average / cnt;
                actor.setAverageGrade(average);
                actorGrades.put(actor.getName(), average);
            }
        }
        if (sortType.equals("asc")) {
            actorGrades = Utils.sortByValueForRatings(actorGrades, true);
        } else {
            actorGrades = Utils.sortByValueForRatings(actorGrades, false);
        }
        Set<String> keySet = actorGrades.keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);
        if (n > listOfKeys.size()) {
            queriedActors = listOfKeys;
        } else {
            for (int i = 0; i < n; i++) {
                queriedActors.add(listOfKeys.get(i));
            }
        }
        return "Query result: " + queriedActors.toString();
    }

    /**
     * for coding style
     */
    public String doNumRatings(final List<User> userList, final double n, final String sortType) {
        Map<String, Integer> userRatings = new HashMap<>();
        List<String> queriedUsers = new ArrayList<>();
        for (User user : userList) {
            if (user.getNumberOfRatings() != 0) {
                userRatings.put(user.getUsername(), user.getNumberOfRatings());
            }
        }
        if (sortType.equals("asc")) {
            userRatings = Utils.sortByValue(userRatings, true);
        } else {
            userRatings = Utils.sortByValue(userRatings, false);
        }
        Set<String> keySet = userRatings.keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);
        if (n > listOfKeys.size()) {
            queriedUsers = listOfKeys;
        } else {
            for (int i = 0; i < n; i++) {
                queriedUsers.add(listOfKeys.get(i));
            }
        }
        return "Query result: " + queriedUsers.toString();
    }

    /**
     * for coding style
     */
    public String doAwards(final List<Actor> actorList, final Action action,
                           final String sortType) {
        Map<String, Integer> queriedActors = new HashMap<>();
        for (Actor actor : actorList) {
            int cnt = 0;
            for (int j = 0; j < action.getFilters().get(words).size(); j++) {
                if (actor.getAwards().containsKey(Utils
                        .stringToAwards(action.getFilters().get(words).get(j)))) {
                    cnt++;
                }
            }
            if (cnt == action.getFilters().get(words).size()) {
                queriedActors.put(actor.getName(), actor.calcTotalAwards());
            }
        }
        if (sortType.equals("asc")) {
            queriedActors = Utils.sortByValue(queriedActors, true);
        } else {
            queriedActors = Utils.sortByValue(queriedActors, false);
        }
        List<String> queriedActorsKeys = new ArrayList<>(queriedActors.keySet());
        return "Query result: " + queriedActorsKeys.toString();
    }

    /**
     * for coding style
     */
    public String doFilterDescription(final List<Actor> actorList, final Action action,
                                      final String sortType) {
        List<String> queriedActors = new ArrayList<>();
        for (Actor actor : actorList) {
            int cnt = 0;
            for (int j = 0; j < action.getFilters().get(2).size(); j++) {
                boolean found = Arrays.asList(actor.getCareerDescription().toLowerCase()
                        .split("[ ,.-]")).contains(action.getFilters().get(2).get(j));
                if (found) {
                    cnt++;
                }
            }
            if (cnt == action.getFilters().get(2).size()) {
                queriedActors.add(actor.getName());
            }
        }
        if (sortType.equals("asc")) {
            queriedActors.sort(Comparator.naturalOrder());
        } else {
            queriedActors.sort(Comparator.reverseOrder());
        }
        return "Query result: " + queriedActors.toString();
    }

    /**
     * for coding style
     */
    public String doRating(final List<Movie> movieList, final List<Serial> serialList,
                           final Action action, final int n, final String sortType) {
        Map<String, Double> queriedVideos = Utils
                .putVideosWhoRespectConditionDouble(movieList, serialList, action);
        List<String> listOfVideos = new ArrayList<>();
        if (action.getObjectType().equals("movies")) {
            for (Movie movie : movieList) {
                if (queriedVideos.containsKey(movie.getTitle())) {
                    if (movie.calcAverage(movie.getRatings()) != 0) {
                        queriedVideos.put(movie.getTitle(),
                                movie.calcAverage(movie.getRatings()));
                    }
                }
            }
        }
        if (action.getObjectType().equals("shows")) {
            for (Serial serial : serialList) {
                if (queriedVideos.containsKey(serial.getTitle())) {
                    if (serial.calcAverage(serial.getSeasons()) != 0) {
                        queriedVideos.put(serial.getTitle(),
                                serial.calcAverage(serial.getSeasons()));
                    }
                }
            }
        }
        if (Utils.checkSortType(sortType)) {
            queriedVideos = Utils.sortByValueForRatings(queriedVideos, true);
        } else {
            queriedVideos = Utils.sortByValueForRatings(queriedVideos, false);
        }
        Set<String> keySet = queriedVideos.keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);
        if (n > listOfKeys.size()) {
            return "Query result: " + listOfKeys.toString();
        } else {
            for (int i = 0; i < n; i++) {
                listOfVideos.add(listOfKeys.get(i));
            }
            return "Query result: " + listOfVideos.toString();
        }
    }

    /**
     * for coding style
     */
    public String doFavorite(final List<User> userList, final List<Movie> movieList,
                             final List<Serial> serialList, final Action action,
                             final int n, final String sortType) {
        List<String> listOfVideos = new ArrayList<>();
        Map<String, Integer> queriedVideos = Utils
                .putVideosWhoRespectCondition(movieList, serialList, action);
        if (queriedVideos.size() == 0) {
            return "Query result: " + listOfVideos.toString();
        }
        for (User user : userList) {
            for (int j = 0; j < user.getFavoriteMovies().size(); j++) {
                if (queriedVideos.containsKey(user.getFavoriteMovies().get(j))) {
                    int nrOfUsersWhoFavorite = queriedVideos.get(user.getFavoriteMovies().get(j));
                    nrOfUsersWhoFavorite++;
                    queriedVideos.replace(user.getFavoriteMovies().get(j), nrOfUsersWhoFavorite);
                }
            }
        }
        if (sortType.equals("asc")) {
            queriedVideos = Utils.sortByValue(queriedVideos, true);
        } else {
            queriedVideos = Utils.sortByValue(queriedVideos, false);
        }
        queriedVideos.values().removeIf(val -> 0 == val);
        Set<String> keySet = queriedVideos.keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);
        if (n > listOfKeys.size()) {
            return "Query result: " + listOfKeys.toString();
        } else {
            for (int i = 0; i < n; i++) {
                listOfVideos.add(listOfKeys.get(i));
            }
            return "Query result: " + listOfVideos.toString();
        }
    }

    /**
     * for coding style
     */
    public String doLongest(final List<Movie> movieList, final List<Serial> serialList,
                            final Action action, final int n, final String sortType) {
        Map<String, Integer> queriedVideos = Utils
                .putVideosWhoRespectCondition(movieList, serialList, action);
        List<String> videosDuration = new ArrayList<>();
        if (action.getObjectType().equals("movies")) {
            for (Movie movie : movieList) {
                if (queriedVideos.containsKey(movie.getTitle())) {
                    queriedVideos.put(movie.getTitle(), movie.getDuration());
                }
            }
        }
        if (action.getObjectType().equals("shows")) {
            for (Serial serial : serialList) {
                if (queriedVideos.containsKey(serial.getTitle())) {
                    queriedVideos.put(serial.getTitle(),
                            serial.serialDuration(serial.getSeasons()));
                }
            }
        }
        if (sortType.equals("asc")) {
            queriedVideos = Utils.sortByValue(queriedVideos, true);
        } else {
            queriedVideos = Utils.sortByValue(queriedVideos, false);
        }
        Set<String> keySet = queriedVideos.keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);
        if (n > listOfKeys.size()) {
            videosDuration = listOfKeys;
        } else {
            for (int i = 0; i < n; i++) {
                videosDuration.add(listOfKeys.get(i));
            }
        }
        return "Query result: " + videosDuration.toString();
    }

    /**
     * for coding style
     */
    public String doMostViewed(final List<User> userList, final List<Movie> movieList,
                               final List<Serial> serialList, final Action action,
                               final int n, final String sortType) {
        List<String> listOfVideos = new ArrayList<>();
        Map<String, Integer> queriedVideos = Utils
                .putVideosWhoRespectCondition(movieList, serialList, action);
        if (queriedVideos.size() == 0) {
            return "Query result: " + listOfVideos.toString();
        }
        for (User user : userList) {
            if (user.getHistory().size() != 0) {
                for (Map.Entry<String, Integer> userHistory : user.getHistory().entrySet()) {
                    String historyKey = userHistory.getKey();
                    Integer historyValue = userHistory.getValue();
                    for (Map.Entry<String, Integer> videosWhoRespectCondition
                            : queriedVideos.entrySet()) {
                        String keyOfVideos = videosWhoRespectCondition.getKey();
                        Integer valueOfVideos = videosWhoRespectCondition.getValue();
                        if (historyKey.equals(keyOfVideos)) {
                            int timesViewed = valueOfVideos;
                            timesViewed += historyValue;
                            queriedVideos.replace(historyKey, timesViewed);
                        }
                    }
                }
            }
        }
        if (sortType.equals("asc")) {
            queriedVideos = Utils.sortByValue(queriedVideos, true);
        } else {
            queriedVideos = Utils.sortByValue(queriedVideos, false);
        }
        queriedVideos.values().removeIf(val -> 0 == val);
        List<String> listOfKeys = Utils.mapKeysToList(queriedVideos);
        if (n > listOfKeys.size()) {
            return "Query result: " + listOfKeys.toString();
        } else {
            for (int i = 0; i < n; i++) {
                listOfVideos.add(listOfKeys.get(i));
            }
            return "Query result: " + listOfVideos.toString();
        }
    }
}
