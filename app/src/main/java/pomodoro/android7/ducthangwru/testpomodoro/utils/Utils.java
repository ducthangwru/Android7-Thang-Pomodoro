package pomodoro.android7.ducthangwru.testpomodoro.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import pomodoro.android7.ducthangwru.testpomodoro.settings.SharePrefs;

/**
 * Created by DUC THANG on 2/15/2017.
 */

public class Utils {
    public static void setSolidColor(View v, String colorString) {
        GradientDrawable drawable = (GradientDrawable)v.getBackground();
        drawable.setColor(Color.parseColor(colorString));
    }

    public static int getTimeBreak() {
        int timeBreak = SharePrefs.getInstance().getSetting().getTimeBreak();
        return timeBreak;
    }
}
