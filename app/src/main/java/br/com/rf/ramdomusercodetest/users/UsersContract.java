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

        void showUserDetailsUi(User user);

        void showDeleteUserComplete();

        void showLoadingUsersError();

        void showUsersFilter(String arg);

    }

    interface Presenter extends BasePresenter {

        void loadUsers();

        void openUserDetails(@NonNull User user);

        void deleteUser(@NonNull User user);

        void setFiltering(String arg);

    }
}
