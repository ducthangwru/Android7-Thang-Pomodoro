package pomodoro.android7.ducthangwru.testpomodoro.decorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by DUC THANG on 2/15/2017.
 */

public class TaskColorDecor extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = 16;
        outRect.right = 16;
    }
}
