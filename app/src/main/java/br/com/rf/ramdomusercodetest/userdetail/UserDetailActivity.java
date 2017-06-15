package br.com.rf.ramdomusercodetest.userdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.rf.ramdomusercodetest.R;
import br.com.rf.ramdomusercodetest.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserDetailActivity extends AppCompatActivity implements UserDetailContract.View {

    UserDetailPresenter mUserDetailPresenter;
    UserDetailContract.Presenter mPresenter;

    @BindView(R.id.user_detail_name)
    TextView mTextName;
    @BindView(R.id.user_detail_gender)
    TextView mTextGender;
    @BindView(R.id.user_detail_email)
    TextView mTextEmail;
    @BindView(R.id.user_detail_phone)
    TextView mTextPhone;
    @BindView(R.id.user_detail_address)
    TextView mTextAddress;
    @BindView(R.id.user_detail_img)
    ImageView mImgUser;

    @BindView(R.id.toolbar)
    Toolbar mTollbar;

    public static final String EXTRA_USER = "EXTRA_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mTollbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Detail");

        User user = (User) getIntent().getSerializableExtra(EXTRA_USER);
        // Create the presenter
        mUserDetailPresenter = new UserDetailPresenter(user, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(UserDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showUserDetails(@NonNull User user) {

        mTextName.setText(getString(R.string.lbl_name, user.getFullname()));
        mTextGender.setText(getString(R.string.lbl_genger, user.getGender()));
        mTextEmail.setText(getString(R.string.lbl_email, user.getEmail()));
        mTextPhone.setText(getString(R.string.lbl_phone, user.getPhone()));
        mTextAddress.setText(getString(R.string.lbl_address, user.getFullAddress()));
        if (user.getPicture() != null && !TextUtils.isEmpty(user.getPicture().getLarge())) {
            Glide.with(this)
                    .load(user.getPicture().getLarge())
                    .dontAnimate()
                    .into(mImgUser);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
