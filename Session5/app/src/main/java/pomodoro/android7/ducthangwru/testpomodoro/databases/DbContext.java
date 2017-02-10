package pomodoro.android7.ducthangwru.testpomodoro.databases;

import java.util.ArrayList;
import java.util.List;

import pomodoro.android7.ducthangwru.testpomodoro.databases.models.TableColor;
import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;

/**
 * Created by DUC THANG on 2/8/2017.
 */

public class DbContext {
    public static final DbContext instance = new DbContext();
    public List<Task> allTasks() {
        //1 create array list
        ArrayList<Task> tasks = new ArrayList<>();

        //2 Add some task and return
        tasks.add(new Task("Study RecycleView", "#EF5350"));
        tasks.add(new Task("Study RecycleView 1", "#F44336"));
        tasks.add(new Task("Study RecycleView 2", "#BA68C8"));
        tasks.add(new Task("Study RecycleView 3", "#4DD0E1"));
        tasks.add(new Task("Study RecycleView 4", "#009688"));
        return tasks;
    }

    public static String[] colors={"#F44336","#E91E63","#9C27B0","#673AB7",
            "#3F51B5","#2196F3","#03A9F4","#009688"};
}
