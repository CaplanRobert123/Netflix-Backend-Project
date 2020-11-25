package action;

import actor.Actor;
import entertainment.Movie;
import entertainment.Serial;
import jdk.jshell.execution.Util;
import user.User;
import utils.Utils;

import java.util.*;

public class Query {
    public String doAverage(List<Actor> actorList, List<Movie> movieList,
                            List<Serial> serialList, int n, String sortType) {
        Map<String, Double> actorGrades = new HashMap<>();
        List<String> queriedActors = new ArrayList<>();
        for (int i = 0; i < actorList.size(); i++) {
            Map<String, Double> ratings = new HashMap<>();
            double average = 0;
            int cnt = 0;
            for (int j = 0; j < actorList.get(i).getFilmography().size(); j++) {
                for (int k = 0; k < movieList.size(); k++) {
                    if(actorList.get(i).getFilmography().get(j).equals(movieList.get(k).getTitle()) &&
                            movieList.get(k).calcAverage(movieList.get(k).getRatings()) != 0) {
                        ratings.put(movieList.get(k).getTitle(),
                                movieList.get(k).calcAverage(movieList.get(k).getRatings()));
                    }
                }
                for (int k = 0; k < serialList.size(); k++) {
                    if(actorList.get(i).getFilmography().get(j).equals(serialList.get(k).getTitle()) &&
                            serialList.get(k).calcAverage(serialList.get(k).getSeasons()) != 0) {
                        ratings.put(serialList.get(k).getTitle(),
                                serialList.get(k).calcAverage(serialList.get(k).getSeasons()));
                    }
                }
            }
            Collection<Double> values = ratings.values();
            ArrayList<Double> listOfValues = new ArrayList<>(values);

            if(listOfValues.size() == 0){

            } else {
                for (int j = 0; j < listOfValues.size(); j++) {
                    if (listOfValues.get(j) != 0.0) {
                        average += listOfValues.get(j);
                        cnt++;
                    }
                }
                average = average / cnt;
                actorList.get(i).setAverageGrade(average);
                actorGrades.put(actorList.get(i).getName(), average);
            }
        }
        if(sortType.equals("asc")) {
            actorGrades = Utils.sortByValueForRatings(actorGrades, true);
        }
        else {
            actorGrades = Utils.sortByValueForRatings(actorGrades, false);
        }
        Set<String> keySet = actorGrades.keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);
        if(n > listOfKeys.size()) {
            queriedActors = listOfKeys;
        } else {
            for (int i = 0; i < n; i++) {
                queriedActors.add(listOfKeys.get(i));
            }
        }
        return "Query result: " + queriedActors.toString();
    }

    public String doNumRatings(List<User> userList, double n, String sortType) {
        Map<String, Integer> userRatings = new HashMap<>();
        List<String> queriedUsers = new ArrayList<>();
        for (User user : userList) {
            if (user.getNumberOfRatings() != 0)
                userRatings.put(user.getUsername(), user.getNumberOfRatings());
        }
        if(sortType.equals("asc")) {
            userRatings = Utils.sortByValue(userRatings, true);
        }
        else {
            userRatings = Utils.sortByValue(userRatings, false);
        }
        Set<String> keySet = userRatings.keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);
        if(n > listOfKeys.size()) {
            queriedUsers = listOfKeys;
        } else {
            for (int i = 0; i < n; i++) {
                queriedUsers.add(listOfKeys.get(i));
            }
        }
        return "Query result: " + queriedUsers.toString();
    }

    public String doAwards(List<Actor> actorList, Action action, String sortType) {
        Map<String, Integer> queriedActors = new HashMap<>();
        for (int i = 0; i < actorList.size(); i++) {
            int cnt = 0;
            for (int j = 0; j < action.getFilters().get(3).size(); j++) {
                if (actorList.get(i).getAwards().containsKey(Utils.stringToAwards(action.getFilters().get(3).get(j))))
                    cnt++;
            }
            if (cnt == action.getFilters().get(3).size())
                queriedActors.put(actorList.get(i).getName(), actorList.get(i).calcTotalAwards());
        }
        if(sortType.equals("asc")) {
            queriedActors = Utils.sortByValue(queriedActors, true);
        } else {
            queriedActors = Utils.sortByValue(queriedActors, false);
        }
        List<String> queriedActorsKeys = new ArrayList<>(queriedActors.keySet());
        return "Query result: " + queriedActorsKeys.toString();
    }

    public String doFilterDescription(List<Actor> actorList, Action action, String sortType) {
        List<String> queriedActors = new ArrayList<>();
        for (Actor actor : actorList) {
            int cnt = 0;
            for (int j = 0; j < action.getFilters().get(2).size(); j++) {
                boolean found = Arrays.asList(actor.getCareerDescription().toLowerCase().split("[ ,.-]")).contains(action.getFilters().get(2).get(j));
                if (found) {
                    cnt++;
                }
            }
            if (cnt == action.getFilters().get(2).size())
                queriedActors.add(actor.getName());
        }
        if(sortType.equals("asc")) {
            queriedActors.sort(Comparator.naturalOrder());
        } else {
            queriedActors.sort(Comparator.reverseOrder());
        }
        return "Query result: " + queriedActors.toString();
    }

    public String doRating(List<Movie> movieList, List<Serial> serialList, Action action, int n, String sortType) {
//        Map<String, Double> queriedVideos = new HashMap<>();
        Map<String, Double> queriedVideos = Utils.putVideosWhoRespectConditionDouble(movieList, serialList, action);
        List<String> listOfVideos = new ArrayList<>();
        if (action.getObjectType().equals("movies")) {
            for (int i = 0; i < movieList.size(); i++) {
                if (queriedVideos.containsKey(movieList.get(i).getTitle())) {
                    if (movieList.get(i).calcAverage(movieList.get(i).getRatings()) != 0) {
                        queriedVideos.put(movieList.get(i).getTitle(), movieList.get(i).calcAverage(movieList.get(i).getRatings()));
                    }
                }
            }
        }
        if (action.getObjectType().equals("shows")) {
            for (int i = 0; i < serialList.size(); i++) {
                if (queriedVideos.containsKey(serialList.get(i).getTitle())) {
                    if (serialList.get(i).calcAverage(serialList.get(i).getSeasons()) != 0) {
                        queriedVideos.put(serialList.get(i).getTitle(), serialList.get(i).calcAverage(serialList.get(i).getSeasons()));
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

    public String doFavorite(List<User> userList, List<Movie> movieList, List<Serial> serialList, Action action, int n, String sortType) {
        List<String> listOfVideos = new ArrayList<>();
        Map<String, Integer> queriedVideos = Utils.putVideosWhoRespectCondition(movieList, serialList, action);
        if (queriedVideos.size() == 0) {
            return "Query result: " + listOfVideos.toString();
        }
        for (int i = 0; i < userList.size(); i++) {
            for (int j = 0; j < userList.get(i).getFavoriteMovies().size(); j++) {
                if (queriedVideos.containsKey(userList.get(i).getFavoriteMovies().get(j))) {
                    int nrOfUsersWhoFavorite = queriedVideos.get(userList.get(i).getFavoriteMovies().get(j));
                    nrOfUsersWhoFavorite++;
                    queriedVideos.replace(userList.get(i).getFavoriteMovies().get(j), nrOfUsersWhoFavorite);
                }
            }
        }
        if(sortType.equals("asc")) {
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

    public String doLongest(List<Movie> movieList, List<Serial> serialList, Action action, int n, String sortType) {
//        Map<String, Integer> queriedVideos = new HashMap<>();
        Map<String, Integer> queriedVideos = Utils.putVideosWhoRespectCondition(movieList, serialList, action);
        List<String> videosDuration = new ArrayList<>();
        if (action.getObjectType().equals("movies")) {
            for (int i = 0; i < movieList.size(); i++) {
                if (queriedVideos.containsKey(movieList.get(i).getTitle())) {
                    queriedVideos.put(movieList.get(i).getTitle(), movieList.get(i).getDuration());
                }
            }
        }
        if (action.getObjectType().equals("shows")) {
            for (int i = 0; i < serialList.size(); i++) {
                if (queriedVideos.containsKey(serialList.get(i).getTitle())) {
                    queriedVideos.put(serialList.get(i).getTitle(), serialList.get(i).serialDuration(serialList.get(i).getSeasons()));
                }
            }
        }
        if(sortType.equals("asc")) {
            queriedVideos = Utils.sortByValue(queriedVideos, true);
        } else {
            queriedVideos = Utils.sortByValue(queriedVideos, false);
        }
        Set<String> keySet = queriedVideos.keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);
        if(n > listOfKeys.size()) {
            videosDuration = listOfKeys;
        } else {
            for (int i = 0; i < n; i++) {
                videosDuration.add(listOfKeys.get(i));
            }
        }
        return "Query result: " + videosDuration.toString();
    }

    public String doMostViewed(List<User> userList, List<Movie> movieList, List<Serial> serialList, Action action, int n, String sortType) {
        List<String> listOfVideos = new ArrayList<>();
        Map<String, Integer> queriedVideos = Utils.putVideosWhoRespectCondition(movieList, serialList, action);
        if (queriedVideos.size() == 0) {
            return "Query result: " + listOfVideos.toString();
        }
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getHistory().size() != 0) {
                for (Map.Entry<String, Integer> userHistory : userList.get(i).getHistory().entrySet()) {
                    String historyKey = userHistory.getKey();
                    Integer historyValue = userHistory.getValue();
                    for (Map.Entry<String, Integer> videosWhoRespectCondition : queriedVideos.entrySet()) {
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
        if(sortType.equals("asc")) {
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
