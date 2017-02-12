package pomodoro.android7.ducthangwru.testpomodoro;

import android.app.Application;
import android.util.Log;

import pomodoro.android7.ducthangwru.testpomodoro.settings.SharePrefs;

/**
 * Created by DUC THANG on 1/14/2017.
 */

public class PomodoroApplication extends Application {
    private static final String TAG = "PomodoroApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "OnCreate!");
        SharePrefs.init(this);
    }
}