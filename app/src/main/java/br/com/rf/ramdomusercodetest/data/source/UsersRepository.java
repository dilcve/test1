package br.com.rf.ramdomusercodetest.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.TreeSet;

import br.com.rf.ramdomusercodetest.data.source.local.UsersLocalDataSource;
import br.com.rf.ramdomusercodetest.data.source.remote.UsersRemoteDataSource;
import br.com.rf.ramdomusercodetest.model.User;
import br.com.rf.ramdomusercodetest.model.UserWrapper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigoferreira on 11/06/2017.
 */

public class UsersRepository implements UsersDataSource {

    private static UsersRepository INSTANCE = null;

    private final UsersDataSource mUsersRemoteDataSource;
    private final UsersDataSource mUsersLocalDataSource;

    // Prevent direct instantiation.
    private UsersRepository(@NonNull UsersDataSource usersRemoteDataSource, @NonNull UsersDataSource usersLocalDataSource) {
        mUsersRemoteDataSource = checkNotNull(usersRemoteDataSource);
        mUsersLocalDataSource = checkNotNull(usersLocalDataSource);
    }

    public static UsersRepository getInstance(UsersDataSource usersRemoteDataSource, UsersDataSource usersLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new UsersRepository(usersRemoteDataSource, usersLocalDataSource);
        }
        return INSTANCE;
    }

    public static UsersRepository provideUsersRepository(@NonNull Context context) {
        checkNotNull(context);
        return UsersRepository.getInstance(UsersRemoteDataSource.getInstance(),
                UsersLocalDataSource.getInstance(context));
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getUsers(@NonNull final LoadUsersCallback callback) {
        checkNotNull(callback);
        getUsersFromLocalDataSource(callback);
    }

    @Override
    public void getMoreUsers(@NonNull LoadUsersCallback callback) {
        checkNotNull(callback);
        getUsersFromRemoteDataSource(callback);
    }

    @Override
    public void saveUsers(@NonNull UserWrapper userWrapper) {
        mUsersLocalDataSource.saveUsers(userWrapper);
    }

    @Override
    public void saveUsers(@NonNull TreeSet<User> users) {
        mUsersLocalDataSource.saveUsers(users);
    }

    @Override
    public void deleteAllUsers() {
        mUsersLocalDataSource.deleteAllUsers();
    }

    @Override
    public void deleteUser(@NonNull User user) {
        mUsersLocalDataSource.deleteUser(user);
    }

    private void getUsersFromRemoteDataSource(@NonNull final LoadUsersCallback callback) {
        mUsersRemoteDataSource.getUsers(new LoadUsersCallback() {
            @Override
            public void onUsersLoaded(TreeSet<User> users) {
                saveUsers(users);
                callback.onUsersLoaded(users);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getUsersFromLocalDataSource(@NonNull final LoadUsersCallback callback) {
        mUsersLocalDataSource.getUsers(new LoadUsersCallback() {
            @Override
            public void onUsersLoaded(TreeSet<User> users) {
                callback.onUsersLoaded(users);
            }

            @Override
            public void onDataNotAvailable() {
                getUsersFromRemoteDataSource(callback);
            }
        });
    }
}
