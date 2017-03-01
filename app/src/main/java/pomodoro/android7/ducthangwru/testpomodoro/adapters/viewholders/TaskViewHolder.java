package pomodoro.android7.ducthangwru.testpomodoro.adapters.viewholders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.TaskJson;

/**
 * Created by DUC THANG on 2/8/2017.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.v_task_color)
    View vTaskColor;

    @BindView(R.id.tv_task_name)
    TextView tvTaskName;

    @BindView(R.id.ib_start)
    ImageView ibStart;

    @BindView(R.id.iv_done)
    ImageView ivDone;

    public TaskViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(TaskJson task) {
        GradientDrawable gradientDrawable = (GradientDrawable) vTaskColor.getBackground();
        gradientDrawable.setColor(Color.parseColor(task.getColor()));
        tvTaskName.setText(task.getName());

        if(task.isDone()) {
            ivDone.setVisibility(View.VISIBLE);
        } else {
            ivDone.setVisibility(View.INVISIBLE);
        }

    }

    public View getIbStart() {
        return ibStart;
    }

    public View getvTaskColor() {
        return vTaskColor;
    }

    public TextView getTvTaskName() {
        return tvTaskName;
    }

    public ImageView getIvDone() {
        return ivDone;
    }
}
