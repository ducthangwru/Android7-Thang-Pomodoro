package pomodoro.android7.ducthangwru.testpomodoro.networks.services;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by DUC THANG on 2/24/2017.
 */

public interface EditTaskServices {
    @PUT("task/{local_id}")
    Call<GetAllTaskServices> editTask(@Path("local_id") String local_id, @Body RequestBody body);
}
