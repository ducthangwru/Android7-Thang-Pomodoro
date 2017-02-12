package pomodoro.android7.ducthangwru.testpomodoro.adapters.viewholders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;

/**
 * Created by DUC THANG on 2/8/2017.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.v_task_color)
    View vTaskColor;

    @BindView(R.id.tv_task_name)
    TextView tvTaskName;

    public TaskViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(Task task) {
        //1: Bind Color
        //vTaskColor.setBackgroundColor(Color.parseColor(task.getColor()));
        GradientDrawable gradientDrawable = (GradientDrawable) vTaskColor.getBackground();
        gradientDrawable.setColor(Color.parseColor(task.getColor()));
        //2: Bind Task Name
        tvTaskName.setText(task.getName());
    }
}
