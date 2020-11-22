package action;

import entertainment.Movie;
import entertainment.Serial;
import jdk.jshell.execution.Util;
import user.User;
import utils.Utils;

import java.util.*;

public class Recommendation {
    public String doStandard(User user, List<Movie> movieList, List<Serial> serialList) {
        for (int i = 0; i < movieList.size(); i++) {
            if(!user.getHistory().containsKey(movieList.get(i).getTitle()))
                return "StandardRecommendation result: " + movieList.get(i).getTitle();
        }
        for (int i = 0; i < serialList.size(); i++) {
            if(!user.getHistory().containsKey(serialList.get(i).getTitle()))
                return "StandardRecommendation result: " + serialList.get(i).getTitle();
        }
        return "StandardRecommendation cannot be applied!";
    }

    public String doBestUnseen(User user, List<Movie> movieList, List<Serial> serialList) {
        Map<String, Double> videosMap = new HashMap<>();
        if (user.getHistory().size() != 0) {
            for (int i = 0; i < movieList.size(); i++) {
                if (!user.getHistory().containsKey(movieList.get(i).getTitle())) {
                    videosMap.put(movieList.get(i).getTitle(), movieList.get(i).calcAverage(movieList.get(i).getRatings()));
                }
            }
            for (int i = 0; i < serialList.size(); i++) {
                if (!user.getHistory().containsKey(serialList.get(i).getTitle())) {
                    videosMap.put(serialList.get(i).getTitle(), serialList.get(i).calcAverage(serialList.get(i).getSeasons()));
                }
            }
        }
        videosMap = Utils.sortByValueForRatings(videosMap, false);
        Set<String> keySet = videosMap.keySet();
        List<String> listOfKeys = new ArrayList<>(keySet);
        return "BestRatedUnseenRecommendation result: " + listOfKeys;
    }
}
