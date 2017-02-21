package pomodoro.android7.ducthangwru.testpomodoro.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.activities.TaskActivity;
import pomodoro.android7.ducthangwru.testpomodoro.adapters.TaskAdapter;
import pomodoro.android7.ducthangwru.testpomodoro.databases.DbContext;
import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;

public class TaskFragment extends Fragment{
    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    TaskAdapter taskAdapter;

    FragmentListenner fragmentListenner;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_task, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        taskAdapter = new TaskAdapter();
        rvTask.setAdapter(taskAdapter);
        setHasOptionsMenu(true);
        taskAdapter.setTaskItemClickListener(new TaskAdapter.TaskItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
                taskDetailFragment.setTitle("Edit Task");
                taskDetailFragment.setTask(task);
                taskDetailFragment.setPosition(DbContext.instance.getPosition(task));
                fragmentListenner.replaceFragment(taskDetailFragment, true);
                setReplace(fragmentListenner);
            }
        });

        taskAdapter.setButtonPlayClickListener(new TaskAdapter.ButtonPlayClickListener() {
            @Override
            public void onButtonClick(Task task) {
                TimeFragment timeFragment = new TimeFragment();
                timeFragment.setTittle("Timer");
                fragmentListenner.replaceFragment(timeFragment, true);
                setReplace(fragmentListenner);
            }
        });

        if(getActivity() instanceof TaskActivity) {
            ((TaskActivity) getActivity()).getSupportActionBar().setTitle("Tasks");
        }

        rvTask.setLayoutManager(new LinearLayoutManager(this.getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rvTask.addItemDecoration(dividerItemDecoration);
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        taskDetailFragment.setTitle("Add new task");
        fragmentListenner.replaceFragment(taskDetailFragment, true);
        setReplace(fragmentListenner);
    }

    public void setReplace(FragmentListenner fragmentListenner) {
        this.fragmentListenner = fragmentListenner;
    }
}
