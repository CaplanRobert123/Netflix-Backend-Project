package entertainment;

import fileio.Input;
import fileio.SerialInputData;

import java.util.List;

public class Show {
    public List<SerialInputData> getShowsList(Input input) {
        return input.getSerials();
    }
}
