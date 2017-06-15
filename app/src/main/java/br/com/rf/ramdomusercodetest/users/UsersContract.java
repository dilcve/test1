package br.com.rf.ramdomusercodetest.users;

/**
 * Created by rodrigoferreira on 11/06/2017.
 */

import android.support.annotation.NonNull;

import java.util.List;
import java.util.TreeSet;

import br.com.rf.ramdomusercodetest.BasePresenter;
import br.com.rf.ramdomusercodetest.BaseView;
import br.com.rf.ramdomusercodetest.model.User;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface UsersContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showUsers(TreeSet<User> users);

        void showMoreUsers(TreeSet<User> users);

        void showUserDetailsUi(User user);

        void showDeleteUserComplete(User user);

        void showLoadingUsersError();

        void showLoadingMoreUsersError();

        void showFilter();

        void showFilterNoResult();

        void hideFilter();

        void showFilteredResults(List<User> users);

    }

    interface Presenter extends BasePresenter {

        void loadUsers();

        void loadMoreUsers();

        void openUserDetails(@NonNull User user);

        void deleteUser(@NonNull User user);

        void setFiltering(String arg, List<User> users);

        void showFilter();

        void hideFilter();


    }
}
