package br.com.rf.ramdomusercodetest.users;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import br.com.rf.ramdomusercodetest.data.source.UsersDataSource;
import br.com.rf.ramdomusercodetest.data.source.UsersRepository;
import br.com.rf.ramdomusercodetest.model.User;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigoferreira on 11/06/2017.
 */

public class UsersPresenter implements UsersContract.Presenter {

    private final UsersRepository mUsersRepository;

    private final UsersContract.View mUsersView;

    private boolean mFirstLoad = true;

    public UsersPresenter(@NonNull UsersRepository usersRepository, @NonNull UsersContract.View usersView) {
        mUsersRepository = checkNotNull(usersRepository, "usersRepository cannot be null");
        mUsersView = checkNotNull(usersView, "usersView cannot be null!");

        mUsersView.setPresenter(this);
    }

    @Override
    public void start() {
        loadUsers();
    }

    @Override
    public void loadUsers() {
        loadUsers(true);
        mFirstLoad = false;
    }

    @Override
    public void loadMoreUsers() {
        mUsersRepository.getMoreUsers(new UsersDataSource.LoadUsersCallback() {
            @Override
            public void onUsersLoaded(TreeSet<User> users) {

                mUsersView.showMoreUsers(users);
            }

            @Override
            public void onDataNotAvailable() {

                mUsersView.showLoadingMoreUsersError();
            }
        });
    }

    private void loadUsers(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mUsersView.setLoadingIndicator(true);
        }

        mUsersRepository.getUsers(new UsersDataSource.LoadUsersCallback() {
            @Override
            public void onUsersLoaded(TreeSet<User> users) {

                if (showLoadingUI) {
                    mUsersView.setLoadingIndicator(false);
                }
                mUsersView.showUsers(users);
            }

            @Override
            public void onDataNotAvailable() {

                mUsersView.showLoadingUsersError();
            }
        });
    }

    @Override
    public void openUserDetails(@NonNull User user) {
        mUsersView.showUserDetailsUi(user);
    }

    @Override
    public void deleteUser(@NonNull User user) {
        mUsersRepository.deleteUser(user);
        mUsersView.showDeleteUserComplete(user);
    }

    @Override
    public void setFiltering(String filter, List<User> users) {
        List<User> filteredList = getFilteredUsers(filter, users);

        if (filteredList.size() > 0) {
            mUsersView.showFilteredResults(filteredList);
        } else {
            mUsersView.showFilterNoResult();
        }
    }

    public static List<User> getFilteredUsers(String filter, List<User> users) {
        List<User> filteredList = new ArrayList<>();
        for (User user : users) {
            if (user.getFullname() != null && user.getFullname().length() > 0 && user.getFullname().toLowerCase().contains(filter.toLowerCase())
                    || user.getEmail() != null && user.getEmail().length() > 0 && user.getEmail().toLowerCase().contains(filter.toLowerCase())) {
                filteredList.add(user);
            }
        }
        return filteredList;
    }

    @Override
    public void showFilter() {
        mUsersView.showFilter();
    }

    @Override
    public void hideFilter() {
        mUsersView.hideFilter();
    }
}
