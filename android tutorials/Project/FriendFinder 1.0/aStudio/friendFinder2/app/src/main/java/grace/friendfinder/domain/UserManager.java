package grace.friendfinder.domain;

import java.util.ArrayList;

/**
 * Created by grace on 05/08/17.
 */

public class UserManager {

    private ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
