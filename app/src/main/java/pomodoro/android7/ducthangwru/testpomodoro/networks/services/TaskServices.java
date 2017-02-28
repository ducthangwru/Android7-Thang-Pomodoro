package pomodoro.android7.ducthangwru.testpomodoro.networks.services;

import java.util.List;

import okhttp3.RequestBody;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.DeleteJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.TaskJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * Created by DUC THANG on 2/28/2017.
 */

public interface TaskServices {
    @POST("task")
    Call<TaskJson> addTask(@Header("Content-Type") String type, @Header("Authorization") String token,
                           @Body RequestBody requestBody);
    @PUT
    Call<TaskJson> editTask(@Url String url, @Header("Content-Type") String type, @Header("Authorization") String token,
                            @Body RequestBody requestBody);
    @DELETE
    Call<DeleteJson> deleteTask(@Url String url, @Header("Authorization") String token);
    @GET("task")
    Call<List<TaskJson>> getTasks(@Header("Authorization") String token);
}
