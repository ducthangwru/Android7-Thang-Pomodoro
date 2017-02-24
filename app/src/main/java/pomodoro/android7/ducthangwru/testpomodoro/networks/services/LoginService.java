package pomodoro.android7.ducthangwru.testpomodoro.networks.services;

import okhttp3.RequestBody;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.LoginResponseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by DUC THANG on 1/18/2017.
 */

public interface LoginService {
    @POST("login")
    Call<LoginResponseJson> login(@Body RequestBody body);
}
