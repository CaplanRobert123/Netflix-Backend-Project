package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Serial {
    private String title;
    /**
     * The year the show was released
     */
    private int year;
    /**
     * Show casting
     */
    private ArrayList<String> cast;
    /**
     * Show genres
     */
    private ArrayList<String> genres;
    /**
     * Number of seasons
     */
    private int numberOfSeasons;
    /**
     * Season list
     */
    private ArrayList<Season> seasons;

    public Serial(String title, int year, ArrayList<String> cast, ArrayList<String> genres, int numberOfSeasons, ArrayList<Season> seasons) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

 /*   public Double calcAverage(ArrayList<Season> seasons) {
        double averagePerSerial = 0.0;
        for (int i = 0; i < seasons.size(); i++) {
            double averagePerSeason = 0.0;
            if (seasons.get(i).getRatings().size() == 0) {
            } else {
                for (int j = 0; j < seasons.get(i).getRatings().size(); j++) {
                    averagePerSeason += seasons.get(i).getRatings().get(j);
                }
                if (averagePerSeason != 0) {
                    averagePerSeason /= seasons.get(i).getRatings().size();
                    averagePerSerial += averagePerSeason;
                }
            }
        }
        if (averagePerSerial != 0) {
            averagePerSerial /= seasons.size();
        } else {
            averagePerSerial = 0;
        }
        return averagePerSerial;
    }*/

    public Double calcAverage(List<Season> seasonList) {
        Double average = 0.0;
        for (int i = 0; i < seasonList.size(); i++) {
            average += seasonList.get(i).calcAverage(seasonList.get(i).getRatings());
        }
        if (average != 0) {
            average /= seasonList.size();
        } else {
            average = 0.0;
        }
        return average;
    }

    public int serialDuration(List<Season> seasonList) {
        int sum = 0;
        for (int i = 0; i < seasonList.size(); i++) {
            sum += seasonList.get(i).getDuration();
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Serial{" +
                "title='" + title + '\'' +
                ", seasons=" + seasons +
                '}';
    }
}
