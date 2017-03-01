package pomodoro.android7.ducthangwru.testpomodoro.adapters.viewholders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pomodoro.android7.ducthangwru.testpomodoro.R;
/**
 * Created by DUC THANG on 2/11/2017.
 */

public class ColorViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_check)
    ImageView ivCheck;
    @BindView(R.id.v_color)
    View v_color;
    public ColorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void bind(String color){
        //1: Bind color
        GradientDrawable drawable = (GradientDrawable)v_color.getBackground();
        drawable.setColor(Color.parseColor(color));
    }

    public void setCheck(boolean check){
        if(check){
            ivCheck.setVisibility(View.VISIBLE);
        }
        else{
            ivCheck.setVisibility(View.INVISIBLE);
        }
    }
}
