package pomodoro.android7.ducthangwru.testpomodoro.networks.services;

import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.DeleteJson;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

/**
 * Created by DUC THANG on 2/24/2017.
 */

public interface DeleteTaskServices {
    @DELETE("task/{local_id}")
    Call<DeleteJson> deleteTask(@Path("local_id") String local_id);
}
