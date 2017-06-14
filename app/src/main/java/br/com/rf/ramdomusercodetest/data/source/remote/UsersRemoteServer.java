package br.com.rf.ramdomusercodetest.data.source.remote;

import br.com.rf.ramdomusercodetest.model.UserWrapper;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by rodrigoferreira on 13/06/2017.
 */


public interface UsersRemoteServer {

    public static final String USER_BASE_API = "http://api.randomuser.me";

    @GET("/?results=40")
    Call<UserWrapper> getUsers();
}
