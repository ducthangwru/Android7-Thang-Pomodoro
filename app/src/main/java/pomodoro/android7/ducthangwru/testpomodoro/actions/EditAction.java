package pomodoro.android7.ducthangwru.testpomodoro.actions;

import android.util.Log;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import pomodoro.android7.ducthangwru.testpomodoro.databases.DbContext;
import pomodoro.android7.ducthangwru.testpomodoro.networks.NetContext;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.TaskJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.TaskServices;
import pomodoro.android7.ducthangwru.testpomodoro.settings.SharePrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUC THANG on 2/27/2017.
 */

public class EditAction implements TaskAction {
    private static final String TAG = "EditAction";

    @Override
    public void toDo(final TaskJson newTask) {
        String type = "application/json";
        String request = (new Gson()).toJson(newTask);
        MediaType mediaType = MediaType.parse(request);
        RequestBody requestBody = RequestBody.create(mediaType, request);
        TaskServices edit = NetContext.instance.createTaskSevice();
        edit.editTask("http://a-task.herokuapp.com/api/task/" + newTask.getLocalID(), type,
                "JWT " + SharePrefs.getInstance().getAccessToken(), requestBody)
                .enqueue(new Callback<TaskJson>() {
                    @Override
                    public void onResponse(Call<TaskJson> call, Response<TaskJson> response) {
                        Log.d(TAG, String.format("onResponse: %s", response.body()));
                        DbContext.getInstance().addOrUpdate(newTask);
                    }

                    @Override
                    public void onFailure(Call<TaskJson> call, Throwable t) {
                    }
                });
    }
}
