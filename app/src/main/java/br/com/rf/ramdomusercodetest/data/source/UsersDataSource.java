package br.com.rf.ramdomusercodetest.data.source;

import android.support.annotation.NonNull;

import java.util.TreeSet;

import br.com.rf.ramdomusercodetest.model.User;
import br.com.rf.ramdomusercodetest.model.UserWrapper;

/**
 * Created by rodrigoferreira on 11/06/2017.
 */

public interface UsersDataSource {

    void saveUsers(@NonNull UserWrapper userWrapper);

    void saveUsers(@NonNull TreeSet<User> users);

    void deleteAllUsers();

    void deleteUser(@NonNull User user, @NonNull TreeSet<User> allUsers);

    interface LoadUsersCallback {

        void onUsersLoaded(TreeSet<User> users);

        void onDataNotAvailable();
    }

    void getUsers(@NonNull LoadUsersCallback callback);

}
