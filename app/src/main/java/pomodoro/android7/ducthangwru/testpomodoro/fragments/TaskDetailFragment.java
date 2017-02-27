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
import pomodoro.android7.ducthangwru.testpomodoro.actions.TaskAction;
import pomodoro.android7.ducthangwru.testpomodoro.activities.TaskActivity;
import pomodoro.android7.ducthangwru.testpomodoro.adapters.ColorAdapter;
import pomodoro.android7.ducthangwru.testpomodoro.databases.DbContext;
import pomodoro.android7.ducthangwru.testpomodoro.decorations.TaskColorDecor;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.TaskJson;

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

    public TaskAction getTaskAction() {
        return taskAction;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View v) {
        ButterKnife.bind(this, v);
        taskColorDecor = new TaskColorDecor();
        rv_color.addItemDecoration(new TaskColorDecor());
        rv_color.setAdapter(colorAdapter);
        rv_color.setLayoutManager(new GridLayoutManager(getActivity(), 3));
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
            //1: get data from UI
            String taskName;
            float payment;
            String color;
            taskName = etTaskName.getText().toString();
            color = colorAdapter.getSelectionColor();
            try {
                payment = Float.parseFloat(etPayment.getText().toString());
            } catch (Exception e) {
                payment = 0.0f;
            }
            //2: Create new  Task;
            TaskJson newTask = new TaskJson(taskName, color, payment);

            if (task != null) {//EDIT
                newTask.setLocalID(task.getLocalID());
                DbContext.instance.editTask(newTask, position);
                taskAction.toDo(newTask);
            } else {
                //ADD
                newTask.setLocalID(UUID.randomUUID().toString());
                taskAction.toDo(newTask);

            }
            getActivity().onBackPressed();
        }

        return false;
    }
}
