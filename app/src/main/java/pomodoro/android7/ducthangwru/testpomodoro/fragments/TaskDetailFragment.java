package pomodoro.android7.ducthangwru.testpomodoro.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.actions.TaskAction;
import pomodoro.android7.ducthangwru.testpomodoro.activities.TaskActivity;
import pomodoro.android7.ducthangwru.testpomodoro.adapters.ColorAdapter;
import pomodoro.android7.ducthangwru.testpomodoro.databases.DbContext;
import pomodoro.android7.ducthangwru.testpomodoro.decorations.TaskColorDecor;
import pomodoro.android7.ducthangwru.testpomodoro.networks.NetContext;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.TaskJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.TaskServices;
import pomodoro.android7.ducthangwru.testpomodoro.settings.SharePrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {
    private static final String TAG = TaskDetailFragment.class.toString();
    @BindView(R.id.rv_color)
    RecyclerView rv_color;
    @BindView(R.id.et_name_task)
    EditText etTaskName;
    @BindView(R.id.et_payment)
    EditText etPayment;
    private String title;
    private TaskJson task;
    private int position;
    TaskColorDecor taskColorDecor;
    private TaskAction taskAction;
    ColorAdapter colorAdapter = new ColorAdapter();

    public void setTaskAction(TaskAction taskAction) {
        this.taskAction = taskAction;
    }

    public TaskDetailFragment() {
        setHasOptionsMenu(true);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTask(TaskJson task) {
        this.task = task;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View v) {
        ButterKnife.bind(this, v);
        taskColorDecor = new TaskColorDecor();
        rv_color.addItemDecoration(new TaskColorDecor());
        rv_color.setAdapter(colorAdapter);
        rv_color.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        if (getActivity() instanceof TaskActivity) {
            ((TaskActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
        if (task != null) {
            etTaskName.setText(task.getName());
            etPayment.setText(String.format("%.1f", task.getPayment()));
            colorAdapter.setSelectedColor(task.getColor());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.it_ok) {
            String taskName;
            float payment;
            String color;
            taskName = etTaskName.getText().toString();
            color = colorAdapter.getSelectedColor();
            try {
                payment = Float.parseFloat(etPayment.getText().toString());
            } catch (Exception e) {
                payment = 0;
            }
            if (task != null) {
                TaskJson newTask = new TaskJson(task.getLocalID(), color, taskName, payment);
                editTask(newTask);
            } else {
                TaskJson newTask = new TaskJson(taskName, color, payment);
                addNewTask(newTask);
            }
        }

        return false;
    }

    public void addNewTask(final TaskJson newTask){
        String type = "application/json";
        String taskRequest = (new Gson()).toJson(newTask);
        TaskServices taskService = NetContext.instance.createTaskSevice();
        MediaType jsonType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonType, taskRequest);
        taskService.addTask(type, "JWT " + SharePrefs.getInstance().getAccessToken(), requestBody).enqueue(new Callback<TaskJson>() {
            @Override
            public void onResponse(Call<TaskJson> call, Response<TaskJson> response) {
                Log.d(TAG, String.format("onResponse: %s", response.body()));
                DbContext.getInstance().addTask(newTask);
                getActivity().onBackPressed();
            }
            @Override
            public void onFailure(Call<TaskJson> call, Throwable t) {
                Toast.makeText(TaskDetailFragment.this.getContext(), R.string.cant_add_task, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editTask(final TaskJson newTask){
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
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<TaskJson> call, Throwable t) {
                        Toast.makeText(TaskDetailFragment.this.getContext(), R.string.cant_edit_task, Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
