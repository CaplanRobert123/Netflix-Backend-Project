package entertainment;

import fileio.Input;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.List;

public class Serial {
    private String title;
    private int numberOfSeasons;
    private List<Season> seasons = new ArrayList<>();

    public Serial(String title) {
        this.title = title;
//        this.numberOfSeasons = numberOfSeasons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public List<SerialInputData> getSerialsList(Input input) {
        return input.getSerials();
    }

    @Override
    public String toString() {
        return "Serial{" +
                "title='" + title + '\'' +
                ", numberOfSeasons=" + numberOfSeasons +
                ", seasons=" + seasons +
                '}';
    }
}
