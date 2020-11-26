package actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Actor {
    private final String name;
    /**
     * description of the actor's career
     */
    private final String careerDescription;
    /**
     * videos starring actor
     */
    private final ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;

    private double averageGrade;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public final String getName() {
        return name;
    }

    public final String getCareerDescription() {
        return careerDescription;
    }

    public final ArrayList<String> getFilmography() {
        return filmography;
    }

    public final Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    /**
     * Sorts a HashMap by value
     */
    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    /**
     * Sorts a HashMap by value
     */
    public int calcTotalAwards() {
        int totalAwards = 0;
        List<Integer> valuesList = new ArrayList<>(awards.values());
        for (Integer integer : valuesList) {
            totalAwards += integer;
        }
        return totalAwards;
    }
    /**
     * Sorts a HashMap by value
     */
    @Override
    public String toString() {
        return "Actor{"
                + "name='" + name + '\''
                + ", filmography=" + filmography
                + ", averageGrade=" + averageGrade
                + '}';
    }
}
