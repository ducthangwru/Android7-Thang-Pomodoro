package pomodoro.android7.ducthangwru.testpomodoro.networks.services;

import okhttp3.RequestBody;
import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by DUC THANG on 2/21/2017.
 */

public interface AddNewTaskServices {
    @POST("task")
    Call<Task> addTask(@Header("Content-Type") String type, @Header("Authorization") String token, @Body RequestBody requestBody);
}
