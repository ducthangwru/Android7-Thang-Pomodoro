package pomodoro.android7.ducthangwru.testpomodoro.databases.models;

import com.google.gson.annotations.SerializedName;

import butterknife.BindView;

/**
 * Created by DUC THANG on 2/8/2017.
 */

public class Task {
    private String name;
    private String color;
    private float paymentPerHour;
    private boolean isDone;
    @SerializedName("local_id")
    private String local_id;

    public Task(String name, String color, float paymentPerHour, String id) {
        this.name = name;
        this.color = color;
        this.paymentPerHour = paymentPerHour;
        this.local_id = id;
        this.isDone = false;
    }

    public float getPaymentPerHour() {
        return paymentPerHour;
    }

    public void setPaymentPerHour(float paymentPerHour) {
        this.paymentPerHour = paymentPerHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", paymentPerHour=" + paymentPerHour +
                ", isDone=" + isDone +
                ", local_id='" + local_id + '\'' +
                '}';
    }

    public void flipDone() {
        isDone = !isDone;
    }
}
