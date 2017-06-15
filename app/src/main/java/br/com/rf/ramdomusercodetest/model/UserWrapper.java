package br.com.rf.ramdomusercodetest.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by rodrigoferreira on 11/06/2017.
 */

public class UserWrapper {

    private static String TAG = UserWrapper.class.getSimpleName();

    public TreeSet<User> results;

    public List<User> getNoDeletedUsers() {
        return getNoDeletedUsers(results);
    }

    public static List<User> getNoDeletedUsers(TreeSet<User> users) {
        List<User> activeUsers = new ArrayList<>();
        for (User user : users) {
            if (!user.getDeleted()) {
                activeUsers.add(user);
            }
        }
        Log.d(TAG, "users: " + users.size());
        return activeUsers;
    }

    public UserWrapper() {
    }

    public UserWrapper(TreeSet<User> users) {
        this.results = new TreeSet<>(users);
    }

    public UserWrapper(List<User> users) {
        this.results = new TreeSet<>(users);
    }
}
