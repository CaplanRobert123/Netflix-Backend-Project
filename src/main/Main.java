package main;

import action.Action;
import action.Command;
import action.Query;
import action.Recommendation;
import actor.Actor;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import entertainment.Movie;
import entertainment.Serial;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        Command command = new Command();
        Query query = new Query();
        Recommendation recommendation = new Recommendation();
        List<Movie> movieList = new ArrayList<>();
        List<Serial> serialList = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        List<Actor> actorList = new ArrayList<>();
        for (int i = 0; i < input.getMovies().size(); i++) {
            Movie movie = new Movie(input.getMovies().get(i).getTitle(),
                    input.getMovies().get(i).getYear(),
                    input.getMovies().get(i).getCast(),
                    input.getMovies().get(i).getGenres(),
                    input.getMovies().get(i).getDuration());
            movieList.add(movie);
        }
        for (int i = 0; i < input.getCommands().size(); i++) {
            Action action;
            if (input.getCommands().get(i).getFilters().size() != 0) {
                action = new Action(input.getCommands().get(i).getActionId(),
                        input.getCommands().get(i).getActionType(),
                        input.getCommands().get(i).getObjectType(),
                        input.getCommands().get(i).getFilters().get(1).get(0),
                        input.getCommands().get(i).getSortType(),
                        input.getCommands().get(i).getCriteria(),
                        input.getCommands().get(i).getFilters().get(0).get(0),
                        input.getCommands().get(i).getNumber(),
                        input.getCommands().get(i).getFilters().get(2),
                        input.getCommands().get(i).getFilters().get(3));
            } else {
                action = new Action(input.getCommands().get(i).getActionId(),
                        input.getCommands().get(i).getActionType(),
                        input.getCommands().get(i).getType(),
                        input.getCommands().get(i).getUsername(),
                        input.getCommands().get(i).getObjectType(),
                        input.getCommands().get(i).getSortType(),
                        input.getCommands().get(i).getCriteria(),
                        input.getCommands().get(i).getTitle(),
                        input.getCommands().get(i).getGenre(),
                        input.getCommands().get(i).getNumber(),
                        input.getCommands().get(i).getGrade(),
                        input.getCommands().get(i).getSeasonNumber());
            }
            actionList.add(action);
        }
        for (int i = 0; i < input.getUsers().size(); i++) {
            User user = new User(input.getUsers().get(i).getUsername(),
                    input.getUsers().get(i).getSubscriptionType(),
                    input.getUsers().get(i).getHistory(),
                    input.getUsers().get(i).getFavoriteMovies());
            userList.add(user);
        }
        for (int i = 0; i < input.getActors().size(); i++) {
            Actor actor = new Actor(input.getActors().get(i).getName(),
                    input.getActors().get(i).getCareerDescription(),
                    input.getActors().get(i).getFilmography(),
                    input.getActors().get(i).getAwards());
            actorList.add(actor);
        }
        for (int i = 0; i < input.getSerials().size(); i++) {
            Serial serial = new Serial(input.getSerials().get(i).getTitle(),
                    input.getSerials().get(i).getYear(),
                    input.getSerials().get(i).getCast(),
                    input.getSerials().get(i).getGenres(),
                    input.getSerials().get(i).getNumberSeason(),
                    input.getSerials().get(i).getSeasons());
            serialList.add(serial);
        }

        for (int i = 0; i < actionList.size(); i++) {
            if (actionList.get(i).getActionType().equals(Constants.COMMAND)) {
                for (User user : userList) {
                    if (actionList.get(i).getUsername().equals(user.getUsername())) {
                        if (actionList.get(i).getType().equals(Constants.VIEW)) {
                            arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                    command.doView(user, actionList.get(i))));
                        }
                        if (actionList.get(i).getType().equals(Constants.FAVORITE)) {
                            arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                    command.doFavorite(user, actionList.get(i))));
                        }
                        if (actionList.get(i).getType().equals(Constants.RATING)) {
                            arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                    command.doRating(user, actionList.get(i), movieList, serialList)));
                        }
                    }
                }

            } else if (actionList.get(i).getActionType().equals(Constants.QUERY)) {
                if (actionList.get(i).getObjectType().equals(Constants.ACTORS)) {
                    if (actionList.get(i).getCriteria().equals("average")) {
                        arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                query.doAverage(actorList, movieList, serialList,
                                        actionList.get(i).getNumber(), actionList.get(i).getSortType())));
                    }
                    else if (actionList.get(i).getCriteria().equals("awards")) {
                        arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                query.doAwards(actorList, actionList.get(i), actionList.get(i).getSortType())));
                    }
                    else if (actionList.get(i).getCriteria().equals("filter_description")) {
                        arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                query.doFilterDescription(actorList, actionList.get(i), actionList.get(i).getSortType())));
                    }
                } else if (actionList.get(i).getCriteria().equals("ratings")) {
                    arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                            query.doRating(movieList, serialList, actionList.get(i),
                                    actionList.get(i).getNumber(), actionList.get(i).getSortType())));

                } else if (actionList.get(i).getCriteria().equals("favorite")) {
                    arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                            query.doFavorite(userList, movieList, serialList, actionList.get(i), actionList.get(i).getNumber(), actionList.get(i).getSortType())));

                } else if (actionList.get(i).getCriteria().equals("longest")) {
                    arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                            query.doLongest(movieList, serialList, actionList.get(i), actionList.get(i).getNumber(), actionList.get(i).getSortType())));
                } else if (actionList.get(i).getCriteria().equals("most_viewed")) {
                    arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                            query.doMostViewed(userList, movieList, serialList, actionList.get(i), actionList.get(i).getNumber(), actionList.get(i).getSortType())));
                } else if (actionList.get(i).getCriteria().equals("num_ratings")) {
                    arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                            query.doNumRatings(userList, actionList.get(i).getNumber(),
                                    actionList.get(i).getSortType())));
                }
            } else if (actionList.get(i).getActionType().equals(Constants.RECOMMENDATION)) {
                for (User user : userList) {
                    if (actionList.get(i).getUsername().equals(user.getUsername())) {
                        if (actionList.get(i).getType().equals("standard")) {
                            arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                    recommendation.doStandard(user, movieList, serialList)));
                        }
                        else if (actionList.get(i).getType().equals("best_unseen")) {
                            arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                    recommendation.doBestUnseen(user, movieList, serialList)));
                        }
                        else if (actionList.get(i).getType().equals("popular")) {
                            if (user.getSubscriptionType().equals("BASIC")) {
                                arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                        "PopularRecommendation cannot be applied!"));
                            } else {
                                arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                        recommendation.doPopular(user, movieList, serialList)));
                            }
                        }
                        else if (actionList.get(i).getType().equals("favorite")) {
                            if (user.getSubscriptionType().equals("BASIC")) {
                                arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                        "FavoriteRecommendation cannot be applied!"));
                            } else {
                                arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                        recommendation.doFavorite(userList, movieList, serialList, user)));
                            }
                        }
                        else if (actionList.get(i).getType().equals("search")) {
                            if (user.getSubscriptionType().equals("BASIC")) {
                                arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                        "SearchRecommendation cannot be applied!"));
                            } else {
                                arrayResult.add(fileWriter.writeFile(actionList.get(i).getActionId(), "",
                                        recommendation.doSearch(movieList, serialList, user, actionList.get(i))));
                            }
                        }
                    }
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
