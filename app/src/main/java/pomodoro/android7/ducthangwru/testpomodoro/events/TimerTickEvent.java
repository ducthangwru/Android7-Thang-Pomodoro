package pomodoro.android7.ducthangwru.testpomodoro.events;

/**
 * Created by DUC THANG on 3/4/2017.
 */

public class TimerTickEvent {
    private long tick;

    public TimerTickEvent(long tick) {
        this.tick = tick;
    }

    public long getTick() {
        return tick;
    }

    public void setTick(long tick) {
        this.tick = tick;
    }
}
