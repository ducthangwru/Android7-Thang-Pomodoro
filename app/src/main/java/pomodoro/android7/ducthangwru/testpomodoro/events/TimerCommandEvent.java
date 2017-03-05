package pomodoro.android7.ducthangwru.testpomodoro.events;

/**
 * Created by DUC THANG on 3/4/2017.
 */



public class TimerCommandEvent {
    private TimerCommand command;

    public TimerCommandEvent(TimerCommand command) {
        this.command = command;
    }

    public TimerCommand getCommand() {
        return command;
    }
}
