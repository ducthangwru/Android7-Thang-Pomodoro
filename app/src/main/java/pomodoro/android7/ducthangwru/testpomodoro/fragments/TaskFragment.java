package pomodoro.android7.ducthangwru.testpomodoro.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.actions.AddAction;
import pomodoro.android7.ducthangwru.testpomodoro.actions.EditAction;
import pomodoro.android7.ducthangwru.testpomodoro.adapters.TaskAdapter;
import pomodoro.android7.ducthangwru.testpomodoro.databases.DbContext;
import pomodoro.android7.ducthangwru.testpomodoro.networks.NetContext;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.DeleteJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.TaskJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.TaskServices;
import pomodoro.android7.ducthangwru.testpomodoro.settings.SharePrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskFragment extends Fragment {
    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    @BindView(R.id.pb_loadtask)
    ProgressBar pbLoadTask;

    private TaskAdapter taskAdapter = new TaskAdapter();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        downloadTasks();
        setupUI(view);
        return view;
    }

    private void downloadTasks() {
        TaskServices getTaskService = NetContext.instance.createTaskSevice();
        pbLoadTask.setVisibility(View.VISIBLE);
        getTaskService.getTasks("JWT "+ SharePrefs.getInstance().getAccessToken()).enqueue(new Callback<List<TaskJson>>() {
            @Override
            public void onResponse(Call<List<TaskJson>> call, Response<List<TaskJson>> response) {
                List<TaskJson> tasks = response.body();
                for(TaskJson taskJson : tasks){
                    if(taskJson.getColor() != null) {
                        DbContext.getInstance().addOrUpdate(taskJson);
                    }
                }
                pbLoadTask.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<TaskJson>> call, Throwable t) {
                Toast.makeText(TaskFragment.this.getContext(), getString(R.string.cant_get_task), Toast.LENGTH_SHORT).show();
                pbLoadTask.setVisibility(View.GONE);
            }
        });
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        rvTask.setAdapter(taskAdapter);
        rvTask.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tasks");
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rvTask.addItemDecoration(dividerItemDecoration);
        setHasOptionsMenu(true);

        taskAdapter.setTaskItemClickListener(new TaskAdapter.TaskItemClickListener() {
            @Override
            public void onItemClick(TaskJson taskJson) {
                TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
                taskDetailFragment.setTaskAction(new EditAction());
                taskDetailFragment.setTitle(getString(R.string.edit_task));
                taskDetailFragment.setTask(taskJson);
                (new SceneFragment(getActivity().getSupportFragmentManager(), R.id.fl_main)).replaceFragment(taskDetailFragment, true);
            }
        });
        taskAdapter.setButtonPlayClickListener(new TaskAdapter.ButtonPlayClickListener() {
            @Override
            public void onButtonClick(TaskJson task) {
                TimeFragment timerFragment = new TimeFragment();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(task.getName() + " Timer");
                (new SceneFragment(getActivity().getSupportFragmentManager(), R.id.fl_main)).replaceFragment(timerFragment, true);
            }
        });

        taskAdapter.setDeleteTaskListener(new TaskAdapter.DeleteTaskListener() {
            @Override
            public void deleteTask(TaskJson taskJson) {
                showDialog(taskJson);
            }
        });
    }

    private void showDialog(final TaskJson task) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("DELETE");
        dialog.setMessage(R.string.are_you_sure);
        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(task);
            }
        });

        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert11 = dialog.create();
        alert11.show();
    }

    private void delete(final TaskJson task) {
        TaskServices taskServices = NetContext.instance.createTaskSevice();

        taskServices.deleteTask("http://a-task.herokuapp.com/api/task/" + task.getLocalID(),
                "JWT " + SharePrefs.getInstance().getAccessToken())
                .enqueue(new Callback<DeleteJson>() {
                    @Override
                    public void onResponse(Call<DeleteJson> call, retrofit2.Response<DeleteJson> response) {
                        DbContext.getInstance().delete(task.getLocalID());
                        taskAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<DeleteJson> call, Throwable t) {
                        Toast.makeText(TaskFragment.this.getContext(), R.string.cant_delete, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        taskDetailFragment.setTaskAction(new AddAction());
        taskDetailFragment.setTitle("Add new Task");
        (new SceneFragment(getActivity().getSupportFragmentManager(), R.id.fl_main)).replaceFragment(taskDetailFragment, true);
    }
}