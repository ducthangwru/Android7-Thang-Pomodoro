package pomodoro.android7.ducthangwru.testpomodoro.services;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import pomodoro.android7.ducthangwru.testpomodoro.events.TimerCommand;
import pomodoro.android7.ducthangwru.testpomodoro.events.TimerTickEvent;
import pomodoro.android7.ducthangwru.testpomodoro.events.TimerCommandEvent;

/**
 * Created by DUC THANG on 3/4/2017.
 */

public class PomodoroService extends Service {
    private long total = 100000;
    private long resumeMilis = 100000;
    private CountDownTimer countDownTimer;
    private static final String TAG = "abc";
    private TimerTickEvent event;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCommand(TimerCommandEvent event) {
        if (event.getCommand() == TimerCommand.START) {
            startTimer();
        } else if (event.getCommand() == TimerCommand.PAUSE) {
            pauseTimer();
        } else {
            resumeTimer();
        }
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(total, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resumeMilis = millisUntilFinished;
                event = new TimerTickEvent(millisUntilFinished);
                EventBus.getDefault().post(event);

            }

            @Override
            public void onFinish() {
                event.setTick(0);
                EventBus.getDefault().post(event);
            }

        }.start();
    }

    private void pauseTimer(){
        countDownTimer.cancel();
    }

    private void resumeTimer(){
        countDownTimer = new CountDownTimer(resumeMilis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                event = new TimerTickEvent(millisUntilFinished);
                EventBus.getDefault().post(event);
                resumeMilis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                event.setTick(0);
                EventBus.getDefault().post(event);
            }
        }.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
