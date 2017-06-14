package br.com.rf.ramdomusercodetest.model;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by rodrigoferreira on 11/06/2017.
 */

public class UserWrapper {

   public TreeSet<User> results;

    public UserWrapper() {
    }

    public UserWrapper(TreeSet<User> users) {
        this.results = users;
    }
}
