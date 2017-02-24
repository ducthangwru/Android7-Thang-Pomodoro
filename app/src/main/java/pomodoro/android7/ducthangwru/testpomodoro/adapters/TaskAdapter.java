package pomodoro.android7.ducthangwru.testpomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.adapters.viewholders.TaskViewHolder;
import pomodoro.android7.ducthangwru.testpomodoro.databases.DbContext;
import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;

/**
 * Created by DUC THANG on 2/8/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{
    public int selection;

    public interface TaskItemClickListener {
        void onItemClick(Task task);
    }

    public interface ButtonPlayClickListener{
        void onButtonClick(Task task);
    }

    public interface TaskItemLongClickListener {
        void onItemLongClick(Task task);
    }
    private ButtonPlayClickListener buttonPlayClickListener;

    public void setButtonPlayClickListener(ButtonPlayClickListener buttonPlayClickListener) {
        this.buttonPlayClickListener = buttonPlayClickListener;
    }

    private TaskItemClickListener taskItemClickListener;

    public void setTaskItemClickListener(TaskItemClickListener taskItemClickListener) {
        this.taskItemClickListener = taskItemClickListener;
    }

    private TaskItemLongClickListener taskItemLongClickListener;
    public void setTaskItemLongClickListener(TaskItemLongClickListener taskItemLongClickListener) {
        this.taskItemLongClickListener = taskItemLongClickListener;
    }

    public int getSelection() {
        return selection;
    }


    private void selection(int position) {
        selection = position;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //1: Create View
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_task, parent, false);

        //2: Create ViewHolder
        TaskViewHolder taskViewHolder = new TaskViewHolder(itemView);

        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {
        //1: Get data based on position
        final Task task = DbContext.instance.allTasks().get(position);

        //2: Bind data into view
        holder.bind(task);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskItemClickListener != null) {
                    taskItemClickListener.onItemClick(task);
                }
            }
        });
        holder.getIbStart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonPlayClickListener != null){
                    buttonPlayClickListener.onButtonClick(task);
                }
            }
        });

        holder.vTaskColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.flipDone();
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(taskItemLongClickListener != null) {
                    taskItemLongClickListener.onItemLongClick(task);
                    selection(position);
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return DbContext.instance.allTasks().size();
    }
}