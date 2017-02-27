package pomodoro.android7.ducthangwru.testpomodoro.networks.services;

import java.util.List;

import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.TaskJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by DUC THANG on 2/21/2017.
 */

public interface GetAllTaskServices {
    @GET("task")
    Call<List<TaskJson>> getTasks(@Header("Authorization") String token);
}
