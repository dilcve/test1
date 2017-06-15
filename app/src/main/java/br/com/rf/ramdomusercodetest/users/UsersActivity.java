package br.com.rf.ramdomusercodetest.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import br.com.rf.ramdomusercodetest.R;
import br.com.rf.ramdomusercodetest.data.source.UsersRepository;
import br.com.rf.ramdomusercodetest.model.User;
import br.com.rf.ramdomusercodetest.model.UserWrapper;
import br.com.rf.ramdomusercodetest.userdetail.UserDetailActivity;
import br.com.rf.ramdomusercodetest.util.GUIUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

public class UsersActivity extends AppCompatActivity implements UsersContract.View {

    UsersPresenter mUserPresenter;
    UsersContract.Presenter mPresenter;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    UsersAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.users_loading)
    View mLoadingView;
    @BindView(R.id.users_no_data)
    View mNoDataView;
    @BindView(R.id.users_no_data_msg)
    TextView mNoDataMsg;

    @BindView(R.id.toolbar)
    Toolbar mTollbar;
    @BindView(R.id.toolbar_edit_search)
    EditText mEditSearch;
    @BindView(R.id.toolbar_title)
    TextView mTextTitle;
    @BindView(R.id.toolbar_btn_search_close)
    ImageButton mBtnSearchClose;

    EndlessRecyclerViewAdapter mEndlessRecyclerViewAdapter;

    private boolean loadingMore = true;

    private List<User> mUsersList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

        setSupportActionBar(mTollbar);
        mTextTitle.setText(R.string.app_name);

        // Create the presenter
        mUserPresenter = new UsersPresenter(UsersRepository.provideUsersRepository(this), this);

        loadListener();

        mPresenter.start();
    }

    public void loadListener() {
        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (v.getText().toString().trim().length() != 0) {
                        mPresenter.setFiltering(v.getText().toString().trim(), mUsersList);
                    } else {
                        mPresenter.loadUsers();
                    }
                    return true;
                }
                return false;
            }
        });
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
        mUsersList = UserWrapper.getNoDeletedUsers(users);
        mAdapter = new UsersAdapter(mUsersList, mItemListener);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mEndlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(mAdapter, new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMoreUsers();
            }
        });

        mRecyclerView.setAdapter(mEndlessRecyclerViewAdapter);
    }

    @Override
    public void showMoreUsers(TreeSet<User> users) {
        TreeSet<User> usersSet = new TreeSet<>(mUsersList);
        usersSet.addAll(users);
        mUsersList = UserWrapper.getNoDeletedUsers(usersSet);
        mAdapter.replaceContent(mUsersList);
        mEndlessRecyclerViewAdapter.onDataReady(true);
        loadingMore = true;
    }

    @Override
    public void showUserDetailsUi(User user) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra(UserDetailActivity.EXTRA_USER, user);
        startActivity(intent);
    }

    @Override
    public void showDeleteUserComplete(User user) {
        mPresenter.loadUsers();
        hideFilter();
        Toast.makeText(this, getString(R.string.msg_user_deleted, user.getFullname()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingMoreUsersError() {
        mEndlessRecyclerViewAdapter.onDataReady(true);
        loadingMore = true;
        Toast.makeText(this, R.string.lbl_load_more_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingUsersError() {
        mNoDataMsg.setText(R.string.lbl_no_data_available);
        mNoDataView.setVisibility(View.VISIBLE);
    }

    public void hideNoResultView() {
        mNoDataView.setVisibility(View.GONE);
    }

    @Override
    public void showFilterNoResult() {
        mNoDataMsg.setText(R.string.lbl_filter_no_resut);
        mNoDataView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFilter() {
        mTextTitle.setVisibility(View.GONE);
        mEditSearch.setVisibility(View.VISIBLE);
        GUIUtil.showKeyboard(this, mEditSearch);
        mBtnSearchClose.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        mEndlessRecyclerViewAdapter.onDataReady(false);
        loadingMore = false;
    }

    @Override
    public void hideFilter() {
        mTextTitle.setVisibility(View.VISIBLE);
        mEditSearch.setVisibility(View.GONE);
        mEditSearch.setText("");
        mBtnSearchClose.setImageResource(android.R.drawable.ic_menu_search);
        GUIUtil.hideKeyboard(this);
        mEndlessRecyclerViewAdapter.onDataReady(true);
        loadingMore = true;
        mPresenter.loadUsers();

    }

    @OnClick(R.id.toolbar_btn_search_close)
    public void clickSearchClose() {
        if (mEditSearch.getVisibility() == View.VISIBLE) {
            mPresenter.hideFilter();
        } else {
            mPresenter.showFilter();
        }
    }

    @Override
    public void showFilteredResults(List<User> users) {
        hideNoResultView();
        GUIUtil.hideKeyboard(this);
        mAdapter.replaceContent(users);
    }

    /**
     * Listener for clicks on users in the RecyclerView.
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
