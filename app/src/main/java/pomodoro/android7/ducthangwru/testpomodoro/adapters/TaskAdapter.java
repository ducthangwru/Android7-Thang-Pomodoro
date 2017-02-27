package pomodoro.android7.ducthangwru.testpomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.adapters.viewholders.TaskViewHolder;
import pomodoro.android7.ducthangwru.testpomodoro.databases.DbContext;
import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.TaskJson;

/**
 * Created by DUC THANG on 2/8/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private static final String TAG = "TaskAdapter";
    private ButtonPlayClickListener buttonPlayClickListener;
    private TaskItemClickListener taskItemClickListener;
    private DeleteTaskListener deleteTaskListener;

    public void setDeleteTaskListener(DeleteTaskListener deleteTaskListener) {
        this.deleteTaskListener = deleteTaskListener;
    }

    public interface DeleteTaskListener {
        void deleteTask(int position);
    }

    public interface TaskItemClickListener {
        void onItemClick(TaskJson task);
    }


    public void setTaskItemClickListener(TaskItemClickListener taskItemClickListener) {
        this.taskItemClickListener = taskItemClickListener;
    }

    public interface ButtonPlayClickListener {
        void onButtonClick(TaskJson task);
    }


    public void setButtonPlayClickListener(ButtonPlayClickListener buttonPlayClickListener) {
        this.buttonPlayClickListener = buttonPlayClickListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_task, parent, false);
        //Create ViewHolder
        TaskViewHolder taskViewholder = new TaskViewHolder(itemView);
        return taskViewholder;
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        //get Data on position
        final TaskJson task = DbContext.instance.allTasks().get(position);
        //Bind data to View
        holder.bind(task);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send event to outside
                if (taskItemClickListener != null) {
                    taskItemClickListener.onItemClick(task);
                }
            }
        });
        holder.getIbStart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonPlayClickListener != null) {
                    buttonPlayClickListener.onButtonClick(task);
                }
            }
        });
        holder.getvTaskColor().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.setCheck(!task.isCheck());
                task.setDone(!task.isDone());
                TaskAdapter.this.notifyItemChanged(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick: ");
                deleteTaskListener.deleteTask(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return DbContext.instance.allTasks().size();
    }
}