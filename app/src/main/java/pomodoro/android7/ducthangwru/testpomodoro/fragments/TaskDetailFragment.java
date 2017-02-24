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

import com.google.gson.Gson;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.activities.TaskActivity;
import pomodoro.android7.ducthangwru.testpomodoro.adapters.ColorAdapter;
import pomodoro.android7.ducthangwru.testpomodoro.databases.DbContext;
import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;
import pomodoro.android7.ducthangwru.testpomodoro.decorations.TaskColorDecor;
import pomodoro.android7.ducthangwru.testpomodoro.networks.NetContext;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.AddNewTaskServices;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.EditTaskServices;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.GetAllTaskServices;
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
    private Task task;
    private int position;
    private String uuid;
    private TaskAction taskAction;
    ColorAdapter colorAdapter = new ColorAdapter();

    public interface TaskAction {
        void edit();
    }

    public class EditTask implements TaskAction {

        @Override
        public void edit() {
            String taskName = etTaskName.getText().toString();
            float payment = Float.parseFloat(etPayment.getText().toString());
            String color = colorAdapter.getSelectionColor();
            final Task newTask = new Task(taskName, color, payment, uuid);
            EditTaskServices editTaskServices = NetContext.instance.getRetrofit().create(EditTaskServices.class);
            String type = "application/json";
            String taskRequest = (new Gson()).toJson(newTask);
            final RequestBody requestBody = RequestBody.create(MediaType.parse(type), taskRequest);
            editTaskServices.editTask(task.getLocal_id(),requestBody).enqueue(new Callback<GetAllTaskServices>() {

                @Override
                public void onResponse(Call<GetAllTaskServices> call, Response<GetAllTaskServices> response) {
                    DbContext.instance.editTask(newTask, position);
                    Log.d(TAG, String.format("onResponse: %s", response.body()));
                    getActivity().onBackPressed();
                }

                @Override
                public void onFailure(Call<GetAllTaskServices> call, Throwable t) {

                }
            });
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTaskAction(TaskAction taskAction) {
        this.taskAction = taskAction;
    }

    public TaskDetailFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        setupUI(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_task, menu);
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        rv_color.setAdapter(colorAdapter);
        rv_color.setLayoutManager(new GridLayoutManager(this.getContext(), 4));

        if (getActivity() instanceof TaskActivity) {
            ((TaskActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
        rv_color.addItemDecoration(new TaskColorDecor());

        if (task != null) {
            //Edit
            etTaskName.setText(task.getName());
            etPayment.setText(String.format("%s", task.getPaymentPerHour()));
            colorAdapter.setSelectedColor(task.getColor());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //1: Get data from UI
        if (item.getItemId() == R.id.it_ok && task == null) {
            String taskName = etTaskName.getText().toString();
            float paymentPerHour = Float.parseFloat(etPayment.getText().toString());
            String color = colorAdapter.getSelectionColor();
            uuid = UUID.randomUUID().toString();
            Log.d(TAG, String.format("UUIDaaa  %s", uuid));
            //Create new task
            Task newTask = new Task(taskName, color, paymentPerHour, uuid);
            String type = "application/json";
            String taskRequest = (new Gson()).toJson(newTask);
            AddNewTaskServices addNewTask = NetContext.instance.getRetrofit().create(AddNewTaskServices.class);
            MediaType jsonType = MediaType.parse("application/json");
            final RequestBody requestBody = RequestBody.create(jsonType, taskRequest);
            addNewTask.addTask(type, "JWT " + SharePrefs.getInstance().getAccessToken(), requestBody).enqueue(new Callback<Task>() {
                @Override
                public void onResponse(Call<Task> call, Response<Task> response) {
                    Log.d("abcd", "onResponse: " + response.code());
                    Log.d("abcd", "onResponse: %s" + requestBody.toString());

                }

                @Override
                public void onFailure(Call<Task> call, Throwable t) {

                }
            });
            //3: add to data
            DbContext.instance.addTask(newTask);
            getActivity().onBackPressed();
        } else {
            taskAction = new EditTask();
            taskAction.edit();
        }
        return false;
    }
}
