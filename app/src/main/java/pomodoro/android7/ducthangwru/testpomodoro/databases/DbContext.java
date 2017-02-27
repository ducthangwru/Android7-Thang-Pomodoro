package pomodoro.android7.ducthangwru.testpomodoro.databases;

import java.util.ArrayList;
import java.util.List;

import pomodoro.android7.ducthangwru.testpomodoro.databases.models.TableColor;
import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.TaskJson;

/**
 * Created by DUC THANG on 2/8/2017.
 */

public class DbContext {
    private List<TaskJson> tasks;
    private DbContext() {
        tasks = new ArrayList<>();
    }

    public static final DbContext instance = new DbContext();
    public List<TaskJson> allTasks(){
        return tasks;
    }

    public static String[] colors = {"#F44336", "#E91E63", "#9C27B0", "#673AB7",
            "#3F51B5", "#2196F3", "#03A9F4", "#009688"};


    public void editTask(TaskJson task,int position){
        tasks.get(position).setName(task.getName());
        tasks.get(position).setColor(task.getColor());
        tasks.get(position).setPayment(task.getPayment());
    }
    public int getPosition(TaskJson task){
        for(int i=0;i<tasks.size();i++){
            if(task.getColor().equals(tasks.get(i).getColor())
                    && task.getName().equals(tasks.get(i).getName())
                    && task.getPayment() == tasks.get(i).getPayment()
                    && task.getLocalID() == tasks.get(i).getLocalID()){
                return i;
            }
        }
        return 0;
    }

    public void addTask(TaskJson task) {
        tasks.add(task);
    }
    public void clearAllTasks() {
        tasks.clear();
    }
}

