package br.com.rf.ramdomusercodetest.users;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import br.com.rf.ramdomusercodetest.R;
import br.com.rf.ramdomusercodetest.data.source.UsersRepository;
import br.com.rf.ramdomusercodetest.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class UsersActivity extends AppCompatActivity implements UsersContract.View {

    UsersPresenter mUserPresenter;
    UsersContract.Presenter mPresenter;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    UsersAdapter mAdapter;

    @BindView(R.id.users_loading)
    View mLoadingView;
    @BindView(R.id.users_no_data)
    View mNoDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

        // Create the presenter
        mUserPresenter = new UsersPresenter(UsersRepository.provideUsersRepository(this), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(UsersContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mNoDataView.setVisibility(View.GONE);
        if (active) {
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            mLoadingView.setVisibility(View.GONE);
        }

    }

    @Override
    public void showUsers(TreeSet<User> users) {
        mAdapter = new UsersAdapter(new ArrayList<User>(users), mItemListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showUserDetailsUi(User user) {
        Toast.makeText(this, "show details of: " + user.getFullname(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteUserComplete() {
        Toast.makeText(this, "show delete complete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingUsersError() {
        mNoDataView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUsersFilter(String arg) {

    }

    /**
     * Listener for clicks on tasks in the ListView.
     */
    UsersAdapter.UserItemListener mItemListener = new UsersAdapter.UserItemListener() {

        @Override
        public void onUserImageClick(User clickedUser) {
            mPresenter.openUserDetails(clickedUser);
        }

        @Override
        public void onDelteClick(User deletedUser) {
            mPresenter.deleteUser(deletedUser);
        }
    };

}
