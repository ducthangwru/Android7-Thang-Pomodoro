package pomodoro.android7.ducthangwru.testpomodoro.networks.services;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.RegisterRespoinseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by DUC THANG on 1/19/2017.
 */

public interface RegisterService {
    @POST("register")
    Call<RegisterRespoinseJson> register (@Body RequestBody body);
}
