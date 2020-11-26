package action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Action {
    private final int actionId;
    /**
     * Type of action
     */
    private final String actionType;
    /**
     * Used for commands
     */
    private final String type;
    /**
     * Username of user
     */
    private final String username;
    /**
     * The type of object on which the actions will be performed
     */
    private final String objectType;
    /**
     * Sorting type: ascending or descending
     */
    private final String sortType;
    /**
     * The criterion according to which the sorting will be performed
     */
    private final String criteria;
    /**
     * Video title
     */
    private final String title;
    /**
     * Video genre
     */
    private final String genre;
    /**
     * Query limit
     */
    private final int number;
    /**
     * Grade for rating - aka value of the rating
     */
    private final double grade;
    /**
     * Season number
     */
    private final int seasonNumber;
    /**
     * Filters used for selecting videos
     */
    private final List<List<String>> filters = new ArrayList<>();

    public Action(final int actionId,  final String actionType,  final String type,
                  final String username, final String objectType,  final String sortType,
                  final String criteria,  final String title, final String genre,
                  final int number, final double grade, final int seasonNumber) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.type = type;
        this.username = username;
        this.objectType = objectType;
        this.sortType = sortType;
        this.criteria = criteria;
        this.title = title;
        this.genre = genre;
        this.number = number;
        this.grade = grade;
        this.seasonNumber = seasonNumber;
    }

    public Action(final int actionId, final String actionType, final String objectType,
                  final String genre, final String sortType, final String criteria,
                  final String year, final int number, final List<String> words,
                  final List<String> awards) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.objectType = objectType;
        this.sortType = sortType;
        this.criteria = criteria;
        this.number = number;
        this.filters.add(new ArrayList<>(Collections.singleton(year)));
        this.filters.add(new ArrayList<>(Collections.singleton(genre)));
        this.filters.add(words);
        this.filters.add(awards);
        this.title = null;
        this.type = null;
        this.username = null;
        this.genre = null;
        this.grade = 0;
        this.seasonNumber = 0;
    }

    public int getActionId() {
        return actionId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getSortType() {
        return sortType;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getNumber() {
        return number;
    }

    public double getGrade() {
        return grade;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public List<List<String>> getFilters() {
        return filters;
    }
}
