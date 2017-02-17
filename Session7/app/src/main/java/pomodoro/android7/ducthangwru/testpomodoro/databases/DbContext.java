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
    ArrayList<Task> tasks;

    public List<Task> allTasks() {
        //1 create array list
        if (tasks == null) {
            tasks = new ArrayList<>();

            //2 Add some task and return
            tasks.add(new Task("Study RecycleView", colors[0], 2.3f));
            tasks.add(new Task("Study RecycleView 1", colors[1], 0.9f));
            tasks.add(new Task("Study RecycleView 2", colors[2], 1.2f));
            tasks.add(new Task("Study RecycleView 3", colors[3], 2.5f));
            tasks.add(new Task("Study RecycleView 4", colors[4], 3.4f));
        }
        return tasks;
    }

    public static String[] colors = {"#F44336", "#E91E63", "#9C27B0", "#673AB7",
            "#3F51B5", "#2196F3", "#03A9F4", "#009688"};

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void editTask(Task task, int position) {
            tasks.get(position).setName(task.getName());
            tasks.get(position).setColor(task.getColor());
            tasks.get(position).setPaymentPerHour(task.getPaymentPerHour());
    }

    public int getPosition(Task task){
        for(int i=0;i<tasks.size();i++){
            if(task.getColor().equals(tasks.get(i).getColor())
                    && task.getName().equals(tasks.get(i).getName())
                    && task.getPaymentPerHour() == tasks.get(i).getPaymentPerHour()){
                return i;
            }
        }
        return 0;
    }
}
