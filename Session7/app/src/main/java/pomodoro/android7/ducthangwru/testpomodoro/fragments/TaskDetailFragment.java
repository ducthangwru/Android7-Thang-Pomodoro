package pomodoro.android7.ducthangwru.testpomodoro.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.activities.TaskActivity;
import pomodoro.android7.ducthangwru.testpomodoro.adapters.ColorAdapter;
import pomodoro.android7.ducthangwru.testpomodoro.databases.DbContext;
import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;
import pomodoro.android7.ducthangwru.testpomodoro.decorations.TaskColorDecor;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {
    @BindView(R.id.rv_color)
    RecyclerView rv_color;
    @BindView(R.id.et_name_task)
    EditText etTaskName;
    @BindView(R.id.et_payment)
    EditText etPayment;
    private String title;
    private Task task;
    private int position;
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
            Task newTask = new Task(taskName, color, payment);
            DbContext.instance.editTask(newTask, position);
            getActivity().onBackPressed();
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

            //Create new task
            Task task = new Task(taskName, color, paymentPerHour);
            DbContext.instance.addTask(task);

            getActivity().onBackPressed();
        }
        else {
            taskAction = new EditTask();
            taskAction.edit();
        }
        return false;
    }
}
