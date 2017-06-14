package br.com.rf.ramdomusercodetest.data.source.remote;

import android.support.annotation.NonNull;

import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import br.com.rf.ramdomusercodetest.data.source.UsersDataSource;
import br.com.rf.ramdomusercodetest.model.User;
import br.com.rf.ramdomusercodetest.model.UserWrapper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigoferreira on 11/06/2017.
 */

public class UsersRemoteDataSource implements UsersDataSource {

    private static UsersRemoteDataSource INSTANCE;

    public static UsersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsersRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private UsersRemoteDataSource() {
    }

    @Override
    public void getUsers(@NonNull final LoadUsersCallback callback) {
        checkNotNull(callback);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.interceptors().add(logging);  //---> debug


        client.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UsersRemoteServer.USER_BASE_API)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final UsersRemoteServer usersService = retrofit.create(UsersRemoteServer.class);
        Call<UserWrapper> call = usersService.getUsers();
        call.enqueue(new Callback<UserWrapper>() {
            @Override
            public void onResponse(Call<UserWrapper> User, Response<UserWrapper> response) {
                UserWrapper userWrapper = response.body();
                if (userWrapper != null) {
                    callback.onUsersLoaded(userWrapper.results);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<UserWrapper> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void saveUsers(@NonNull UserWrapper userWrapper) {
        //nothing to do on remote
    }

    @Override
    public void saveUsers(@NonNull TreeSet<User> users) {
        //nothing to do on remote
    }

    @Override
    public void deleteAllUsers() {
        //nothing to do on remote
    }

    @Override
    public void deleteUser(@NonNull User user, @NonNull TreeSet<User> allUsers) {
        //nothing to do on remote

    }


}
