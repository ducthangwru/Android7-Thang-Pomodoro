package pomodoro.android7.ducthangwru.testpomodoro.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.adapters.viewholders.ColorViewHolder;
import pomodoro.android7.ducthangwru.testpomodoro.databases.DbContext;

/**
 * Created by DUC THANG on 2/11/2017.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorViewHolder>{
    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.item_color,parent,false);
        return new ColorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) {
        String s= DbContext.colors[position];
        holder.bind(s);
    }

    @Override
    public int getItemCount() {
        return DbContext.colors.length;
    }
}
