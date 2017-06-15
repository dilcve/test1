package br.com.rf.ramdomusercodetest.userdetail;

/**
 * Created by rodrigoferreira on 14/06/2017.
 */

import android.support.annotation.NonNull;

import br.com.rf.ramdomusercodetest.BasePresenter;
import br.com.rf.ramdomusercodetest.BaseView;
import br.com.rf.ramdomusercodetest.model.User;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface UserDetailContract {

    interface View extends BaseView<Presenter> {

        void showUserDetails(@NonNull User user);
    }

    interface Presenter extends BasePresenter {

        void showUserDetail(@NonNull User user);

    }
}
