package pomodoro.android7.ducthangwru.testpomodoro.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.activities.TaskActivity;
import pomodoro.android7.ducthangwru.testpomodoro.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeFragment extends Fragment {
    @BindView(R.id.pb_time)
    ProgressBar pbTime;
    @BindView(R.id.tv_countdown)
    TextView tvCountdown;
    @BindView(R.id.ib_stop)
    ImageButton ibStop;

    private boolean isStop;
    private CountDownTimer countDownTimer;
    private long timeUntilFinished;
    FragmentListenner listener;
    private String tittle;

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public TimeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        startTimer(Utils.getTimeBreak() * 60 * 1000 + 1000, 1000);

        ibStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.onFinish();
                countDownTimer.cancel();
                isStop = true;
            }
        });

        if (getActivity() instanceof TaskActivity) {
            ((TaskActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        }
    }

    private void startTimer(long millisInFuture, long countDownInterval) {
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                long secondRemaining = millisUntilFinished / 1000;
                int minute = (int) (secondRemaining / 60);
                int second = (int) (secondRemaining - (minute * 60));
                timeUntilFinished = millisUntilFinished;
                tvCountdown.setText(String.format("%02d:%02d", minute, second));
                pbTime.setMax(Utils.getTimeBreak() * 60);
                pbTime.setProgress((int) secondRemaining);
            }

            public void onFinish() {
                countDownTimer.cancel();
            }

        };
        countDownTimer.start();
    }
}