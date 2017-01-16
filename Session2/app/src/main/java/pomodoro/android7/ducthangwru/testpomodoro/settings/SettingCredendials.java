package pomodoro.android7.ducthangwru.testpomodoro.settings;

/**
 * Created by DUC THANG on 1/16/2017.
 */

public class SettingCredendials {
    private int workTime;
    private int timeBreak;
    private int longBreak;
    private int breaks;

    public SettingCredendials(int workTime, int timeBreak, int longBreak, int breaks) {
        this.workTime = workTime;
        this.timeBreak = timeBreak;
        this.longBreak = longBreak;
        this.breaks = breaks;
    }

    public int getWorkTime() {
        return workTime;
    }

    public int getTimeBreak() {
        return timeBreak;
    }

    public int getLongBreak() {
        return longBreak;
    }

    public int getBreaks() {
        return breaks;
    }
}
