package user;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;

import java.util.List;

public class User {
    public List<UserInputData> getUserList(Input input) {
        return input.getUsers();
    }

    public boolean checkFavorite(String username, ActionInputData actionInputData) {
        return actionInputData.getTitle().equals(username); //FIX THIS
    }
}