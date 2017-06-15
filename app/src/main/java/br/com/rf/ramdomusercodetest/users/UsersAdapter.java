package br.com.rf.ramdomusercodetest.users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.rf.ramdomusercodetest.R;
import br.com.rf.ramdomusercodetest.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rodrigoferreira on 14/06/2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> users;
    private UserItemListener mItemListener;
    private ProgressBar mProgressBar;

    public UsersAdapter(List<User> users, UserItemListener itemListener) {
        this.users = new ArrayList<>(users);
        this.mItemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Context context = holder.itemView.getContext();
        final User user = users.get(position);
        holder.mTextUserName.setText(user.getFullname());
        holder.mTextEmail.setText(user.getEmail());
        holder.mTextPhone.setText(user.getPhone());

        if (user.getPicture() != null && !TextUtils.isEmpty(user.getPicture().getMedium())) {
            Glide.with(context)
                    .load(user.getPicture().getMedium())
                    .dontAnimate()
                    .into(holder.mImgUser);
        }

        holder.mImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onUserImageClick(user);
            }
        });

        holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onDelteClick(user);
            }
        });
        holder.itemView.setClickable(true);


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_user_img)
        ImageView mImgUser;
        @BindView(R.id.item_user_name)
        TextView mTextUserName;
        @BindView(R.id.item_user_email)
        TextView mTextEmail;
        @BindView(R.id.item_user_phone)
        TextView mTextPhone;
        @BindView(R.id.item_user_btn_delete)
        Button mBtnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_user;
    }

    public List<User> getUserList() {
        return users;
    }

    public void appendItems(List<User> items) {
        int count = getItemCount();
        users.addAll(items);
        notifyItemRangeInserted(count, items.size());
    }

    public void removeAnItem(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        users.clear();
    }

    public void replaceContent(List<User> users) {
        clear();
        this.users = users;
        notifyDataSetChanged();
    }

    public void showLoading() {

    }

    public interface UserItemListener {

        void onUserImageClick(User clickedUser);

        void onDelteClick(User deletedUser);

    }
}
