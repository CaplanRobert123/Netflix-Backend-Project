package utils;

import action.Action;
import actor.ActorsAwards;
import common.Constants;
import entertainment.Genre;
import entertainment.Movie;
import entertainment.Serial;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The class contains static methods that helps with parsing.
 *
 * We suggest you add your static methods here or in a similar class.
 */
public final class Utils {
    /**
     * for coding style
     */
    private Utils() {
    }

    /**
     * Transforms a string into an enum
     * @param genre of video
     * @return an Genre Enum
     */
    public static Genre stringToGenre(final String genre) {
        return switch (genre.toLowerCase()) {
            case "action" -> Genre.ACTION;
            case "adventure" -> Genre.ADVENTURE;
            case "drama" -> Genre.DRAMA;
            case "comedy" -> Genre.COMEDY;
            case "crime" -> Genre.CRIME;
            case "romance" -> Genre.ROMANCE;
            case "war" -> Genre.WAR;
            case "history" -> Genre.HISTORY;
            case "thriller" -> Genre.THRILLER;
            case "mystery" -> Genre.MYSTERY;
            case "family" -> Genre.FAMILY;
            case "horror" -> Genre.HORROR;
            case "fantasy" -> Genre.FANTASY;
            case "science fiction" -> Genre.SCIENCE_FICTION;
            case "action & adventure" -> Genre.ACTION_ADVENTURE;
            case "sci-fi & fantasy" -> Genre.SCI_FI_FANTASY;
            case "animation" -> Genre.ANIMATION;
            case "kids" -> Genre.KIDS;
            case "western" -> Genre.WESTERN;
            case "tv movie" -> Genre.TV_MOVIE;
            default -> null;
        };
    }

    /**
     * Transforms a string into an enum
     * @param award for actors
     * @return an ActorsAwards Enum
     */
    public static ActorsAwards stringToAwards(final String award) {
        return switch (award) {
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            default -> null;
        };
    }

    /**
     * Transforms an array of JSON's into an array of strings
     * @param array of JSONs
     * @return a list of strings
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Transforms an array of JSON's into a map
     * @param jsonActors array of JSONs
     * @return a map with ActorsAwardsa as key and Integer as value
     */
    public static Map<ActorsAwards, Integer> convertAwards(final JSONArray jsonActors) {
        Map<ActorsAwards, Integer> awards = new LinkedHashMap<>();

        for (Object iterator : jsonActors) {
            awards.put(stringToAwards((String) ((JSONObject) iterator).get(Constants.AWARD_TYPE)),
                    Integer.parseInt(((JSONObject) iterator).get(Constants.NUMBER_OF_AWARDS)
                            .toString()));
        }

        return awards;
    }

    /**
     * Transforms an array of JSON's into a map
     * @param movies array of JSONs
     * @return a map with String as key and Integer as value
     */
    public static Map<String, Integer> watchedMovie(final JSONArray movies) {
        Map<String, Integer> mapVideos = new LinkedHashMap<>();

        if (movies != null) {
            for (Object movie : movies) {
                mapVideos.put((String) ((JSONObject) movie).get(Constants.NAME),
                        Integer.parseInt(((JSONObject) movie).get(Constants.NUMBER_VIEWS)
                                .toString()));
            }
        } else {
            System.out.println("NU ESTE VIZIONAT NICIUN FILM");
        }

        return mapVideos;
    }

    /**
     * Sorts a HashMap by value
     */
    public static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, final boolean order)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue())
                : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey,
                Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

    public static Map<String, Double> sortByValueForRatings(Map<String, Double> unsortMap, final boolean order)
    {
        List<Map.Entry<String, Double>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue())
                : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey,
                Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

    public static boolean checkSortType(String sortType) {
        if(sortType.equals("asc")) {
            return true;
        } else {
            return false;
        }
    }

    public static Map<String, Integer> putVideosWhoRespectCondition(List<Movie> movieList, List<Serial> serialList, Action action) {
        Map<String, Integer> listOfVideosWhoRespectCondition = new HashMap<>();
        if (action.getObjectType().equals("movies")) {
            for (Movie movie : movieList) {
                if (action.getFilters().get(0).get(0) != null) {
                    if (String.valueOf(movie.getYear()).equals(action.getFilters().get(0).get(0))) {
                        if (action.getFilters().get(1).get(0) != null) {
                            if (movie.getGenres().contains(action.getFilters().get(1).get(0))) {
                                listOfVideosWhoRespectCondition.put(movie.getTitle(), 0);
                            }
                        } else {
                            listOfVideosWhoRespectCondition.put(movie.getTitle(), 0);
                        }
                    }
                } else if (action.getFilters().get(1).get(0) != null) {
                    if (movie.getGenres().contains(action.getFilters().get(1).get(0))) {
                        listOfVideosWhoRespectCondition.put(movie.getTitle(), 0);
                    }
                } else {
                    listOfVideosWhoRespectCondition.put(movie.getTitle(), 0);
                }
            }
        }
        if (action.getObjectType().equals("shows")) {
            for (Serial serial : serialList) {
                if (action.getFilters().get(0).get(0) != null) {
                    if (String.valueOf(serial.getYear()).equals(action.getFilters().get(0).get(0))) {
                        if (action.getFilters().get(1).get(0) != null) {
                            if (serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                                listOfVideosWhoRespectCondition.put(serial.getTitle(), 0);
                            }
                        } else {
                            listOfVideosWhoRespectCondition.put(serial.getTitle(), 0);
                        }
                    }
                } else if (action.getFilters().get(1).get(0) != null) {
                    if (serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                        listOfVideosWhoRespectCondition.put(serial.getTitle(), 0);
                    }
                } else {
                    listOfVideosWhoRespectCondition.put(serial.getTitle(), 0);
                }
            }
        }
        return listOfVideosWhoRespectCondition;
    }

    public static Map<String, Double> putVideosWhoRespectConditionDouble(List<Movie> movieList, List<Serial> serialList, Action action) {
        Map<String, Double> listOfVideosWhoRespectCondition = new HashMap<>();
        if (action.getObjectType().equals("movies")) {
            for (Movie movie : movieList) {
                if (action.getFilters().get(0).get(0) != null) {
                    if (String.valueOf(movie.getYear()).equals(action.getFilters().get(0).get(0))) {
                        if (action.getFilters().get(1).get(0) != null) {
                            if (movie.getGenres().contains(action.getFilters().get(1).get(0))) {
                                if (movie.calcAverage(movie.getRatings()) != 0) {
                                    listOfVideosWhoRespectCondition.put(movie.getTitle(), 0.0);
                                }
                            }
                        } else {
                            if (movie.calcAverage(movie.getRatings()) != 0) {
                                listOfVideosWhoRespectCondition.put(movie.getTitle(), 0.0);
                            }
                        }
                    }
                } else if (action.getFilters().get(1).get(0) != null) {
                    if (movie.getGenres().contains(action.getFilters().get(1).get(0))) {
                        if (movie.calcAverage(movie.getRatings()) != 0) {
                            listOfVideosWhoRespectCondition.put(movie.getTitle(), 0.0);
                        }
                    }
                } else {
                    if (movie.calcAverage(movie.getRatings()) != 0) {
                        listOfVideosWhoRespectCondition.put(movie.getTitle(), 0.0);
                    }
                }
            }
        }
        if (action.getObjectType().equals("shows")) {
            for (Serial serial : serialList) {
                if (action.getFilters().get(0).get(0) != null) {
                    if (String.valueOf(serial.getYear()).equals(action.getFilters().get(0).get(0))) {
                        if (action.getFilters().get(1).get(0) != null) {
                            if (serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                                if (serial.calcAverage(serial.getSeasons()) != 0) {
                                    listOfVideosWhoRespectCondition.put(serial.getTitle(), 0.0);
                                }
                            }
                        } else {
                            if (serial.calcAverage(serial.getSeasons()) != 0) {
                                listOfVideosWhoRespectCondition.put(serial.getTitle(), 0.0);
                            }
                        }
                    }
                } else if (action.getFilters().get(1).get(0) != null) {
                    if (serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                        if (serial.calcAverage(serial.getSeasons()) != 0) {
                            listOfVideosWhoRespectCondition.put(serial.getTitle(), 0.0);
                        }
                    }
                } else {
                    if (serial.calcAverage(serial.getSeasons()) != 0) {
                        listOfVideosWhoRespectCondition.put(serial.getTitle(), 0.0);
                    }
                }
            }
        }
        return listOfVideosWhoRespectCondition;
    }

    public static List<String> mapKeysToList(Map<String, Integer> map) {
        Set<String> keySet = map.keySet();
        return new ArrayList<>(keySet);
    }

    public static List<String> mostPopularGenre(List<Movie> movieList, List<Serial> serialList) {
        Map<String, Integer> genrePopularity = new HashMap<>();
        for (Movie movie : movieList) {
            for (int j = 0; j < movie.getGenres().size(); j++) {
                if (genrePopularity.containsKey(movie.getGenres().get(j))) {
                    int popularity = genrePopularity.get(movie.getGenres().get(j));
                    popularity++;
                    genrePopularity.replace(movie.getGenres().get(j), popularity);
                } else {
                    genrePopularity.put(movie.getGenres().get(j), 1);
                }
            }
        }
        for (Serial serial : serialList) {
            for (int j = 0; j < serial.getGenres().size(); j++) {
                if (genrePopularity.containsKey(serial.getGenres().get(j))) {
                    int popularity = genrePopularity.get(serial.getGenres().get(j));
                    popularity++;
                    genrePopularity.replace(serial.getGenres().get(j), popularity);
                } else {
                    genrePopularity.put(serial.getGenres().get(j), 1);
                }
            }
        }
        genrePopularity = sortByValue(genrePopularity, false);
        List<String> popularGenres = mapKeysToList(genrePopularity);
        return popularGenres;
    }
}
