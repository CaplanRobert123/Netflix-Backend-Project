package entertainment;

import fileio.Input;
import fileio.MovieInputData;

import java.util.List;

public class Movie {
    public List<MovieInputData> getMoviesList(Input input) {
        return input.getMovies();
    }
}
