package actor;

import fileio.ActorInputData;
import fileio.Input;

import java.util.List;

public class Actor {

    public List<ActorInputData> getActorsList(Input input) {
        return input.getActors();
    }
}
