package br.com.rf.ramdomusercodetest.userdetail;

import android.support.annotation.NonNull;

import br.com.rf.ramdomusercodetest.model.User;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigoferreira on 14/06/2017.
 */

public class UserDetailPresenter implements UserDetailContract.Presenter {

    private final UserDetailContract.View mUserDetailView;

    User mUser;

    public UserDetailPresenter(@NonNull User user, @NonNull UserDetailContract.View usersView) {
        mUserDetailView = checkNotNull(usersView, "usersView cannot be null!");
        mUserDetailView.setPresenter(this);
        mUser = user;
    }

    @Override
    public void start() {
        checkNotNull(mUser);
        showUserDetail(mUser);
    }

    @Override
    public void showUserDetail(@NonNull User user) {
          mUserDetailView.showUserDetails(user);
    }
}
