package action;

import entertainment.Genre;
import entertainment.Movie;
import entertainment.Serial;
import user.User;
import utils.Utils;

import java.util.*;

public class Recommendation {
    /**
     * for coding style
     */
    public String doStandard(final User user, final List<Movie> movieList,
                             final List<Serial> serialList) {
        for (Movie movie : movieList) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                return "StandardRecommendation result: " + movie.getTitle();
            }
        }
        for (Serial serial : serialList) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                return "StandardRecommendation result: " + serial.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }
    /**
     * for coding style
     */
    public String doBestUnseen(final User user, final List<Movie> movieList,
                               final List<Serial> serialList) {
        String bestVideoTitle = "";
        double bestVideoGrade = -1.0;
        for (Movie movie : movieList) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                if (Double.compare(movie.calcAverage(movie.getRatings()), bestVideoGrade) > 0) {
                    bestVideoTitle = movie.getTitle();
                    bestVideoGrade = movie.calcAverage(movie.getRatings());
                }
            }
        }
        for (Serial serial : serialList) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                if (!user.getHistory().containsKey(serial.getTitle())) {
                    if (Double.compare(serial.calcAverage(serial.getSeasons()),
                            bestVideoGrade) > 0) {
                        bestVideoTitle = serial.getTitle();
                        bestVideoGrade = serial.calcAverage(serial.getSeasons());
                    }
                }
            }
        }
        if (bestVideoGrade == -1.0) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }
        return "BestRatedUnseenRecommendation result: " + bestVideoTitle;
    }
    /**
     * for coding style
     */
    public String doPopular(final User user, final List<Movie> movieList,
                            final List<Serial> serialList) {
        List<String> mostPopularGenre = Utils.mostPopularGenre(movieList, serialList);
        for (String s : mostPopularGenre) {
            for (Movie movie : movieList) {
                for (int k = 0; k < movie.getGenres().size(); k++) {
                    if (movie.getGenres().get(k).equals(s)
                            && !user.getHistory().containsKey(movie.getTitle())) {
                        return "PopularRecommendation result: " + movie.getTitle();
                    }
                }
            }
            for (int j = 0; j < serialList.size(); j++) {
                for (int k = 0; k < serialList.get(j).getGenres().size(); k++) {
                    if (serialList.get(j).getGenres().get(k).equals(mostPopularGenre.get(j))
                            && !user.getHistory().containsKey(serialList.get(j).getTitle())) {
                        return "PopularRecommendation result: " + serialList.get(j).getTitle();
                    }
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }
    /**
     * for coding style
     */
    public String doFavorite(final List<User> userList, final List<Movie> movieList,
                             final List<Serial> serialList, final User user) {
        Map<String, Integer> favoriteVideos = new HashMap<>();
        for (Movie movie : movieList) {
            int cnt = 0;
            for (User user1 : userList) {
                if (user1.getFavoriteMovies().contains(movie.getTitle())) {
                    cnt++;
                }
            }
            favoriteVideos.put(movie.getTitle(), cnt);
        }
        for (Serial serial : serialList) {
            int cnt = 0;
            for (User user1 : userList) {
                if (user1.getFavoriteMovies().contains(serial.getTitle())) {
                    cnt++;
                }
            }
            favoriteVideos.put(serial.getTitle(), cnt);
        }
        String video = "";
        int favorite = 0;
        for (Map.Entry<String, Integer> entry : favoriteVideos.entrySet()) {
            String movieTitle = entry.getKey();
            Integer nrOfFavorites = entry.getValue();
            if (nrOfFavorites > favorite && !user.getHistory().containsKey(movieTitle)) {
                favorite = nrOfFavorites;
                video = movieTitle;
            }
        }
        if (video.equals("")) {
            return "FavoriteRecommendation cannot be applied!";
        }
        return "FavoriteRecommendation result: " + video;
    }
    /**
     * for coding style
     */
    public String doSearch(final List<Movie> movieList, final List<Serial> serialList,
                           final User user, final Action action) {
        List<Genre> genres = Arrays.asList(Genre.values());
        int check = 0;
        for (Genre genre : genres) {
            if (genre.equals(Utils.stringToGenre(action.getGenre()))) {
                check++;
            }
        }
        if (check == 0) {
            return "SearchRecommendation cannot be applied!";
        }
        Map<String, Double> searchedVideos = new HashMap<>();
        for (Movie movie : movieList) {
            if (!user.getHistory().containsKey(movie.getTitle())
                    && movie.getGenres().contains(action.getGenre())) {
                searchedVideos.put(movie.getTitle(),
                        movie.calcAverage(movie.getRatings()));
            }
        }
        for (Serial serial : serialList) {
            if (!user.getHistory().containsKey(serial.getTitle())
                    && serial.getGenres().contains(action.getGenre())) {
                searchedVideos.put(serial.getTitle(),
                        serial.calcAverage(serial.getSeasons()));
            }
        }
        searchedVideos = Utils.sortByValueForRatings(searchedVideos, true);
        Set<String> keySet = searchedVideos.keySet();
        List<String> videosList = new ArrayList<>(keySet);
        if (videosList.isEmpty()) {
            return "SearchRecommendation cannot be applied!";
        }
        return "SearchRecommendation result: " + videosList.toString();
    }
}
