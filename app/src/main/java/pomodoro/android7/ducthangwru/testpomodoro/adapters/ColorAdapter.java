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
    private int selectedPossition;

    public String getSelectionColor() {
        return DbContext.colors[selectedPossition];
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.item_color,parent,false);
        return new ColorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, final int position) {
        String s = DbContext.colors[position];
        holder.bind(s);

        if(selectedPossition == position) {
            holder.setCheck(true);
        } else {
            holder.setCheck(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPossition = position;

                notifyDataSetChanged();
            }
        });
    }

    public void setSelectedColor(String color) {
        for(int colorIndex = 0; colorIndex < DbContext.colors.length; colorIndex++) {
            if(DbContext.colors[colorIndex].equalsIgnoreCase(color)) {
                selectedPossition = colorIndex;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return DbContext.colors.length;
    }
}
