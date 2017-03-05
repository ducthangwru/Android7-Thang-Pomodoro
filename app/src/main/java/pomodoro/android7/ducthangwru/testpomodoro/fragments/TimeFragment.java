package pomodoro.android7.ducthangwru.testpomodoro.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.lzyzsd.circleprogress.DonutProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.activities.TaskActivity;
import pomodoro.android7.ducthangwru.testpomodoro.events.TimerCommand;
import pomodoro.android7.ducthangwru.testpomodoro.events.TimerCommandEvent;
import pomodoro.android7.ducthangwru.testpomodoro.events.TimerTickEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeFragment extends Fragment {
    private static final String TAG = "TimerFragment";
    @BindView(R.id.pb_time)
    DonutProgress pbTime;
    @BindView(R.id.ib_stop)
    ImageButton ibStop;

    @BindView(R.id.ib_play)
    ImageButton ibPlay;


    public TimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        setupUI(view);
        pbTime.setMax(100000);

        ibStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerCommandEvent timerCommandEvent = new TimerCommandEvent(TimerCommand.PAUSE);
                EventBus.getDefault().post(timerCommandEvent);
            }
        });

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerCommandEvent timerCommandEvent = new TimerCommandEvent(TimerCommand.RESUME);
                EventBus.getDefault().post(timerCommandEvent);
            }
        });
        return view;
    }

    public void setupUI(View view) {
        ButterKnife.bind(this, view);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onTick(TimerTickEvent tickEvent) {
        pbTime.setText("" + tickEvent.getTick() / 1000);
        pbTime.setProgress(tickEvent.getTick());
        if (tickEvent.getTick() == 0) {
            pbTime.setText("Done");
            pbTime.setProgress(0);
        }
    }
}