package pomodoro.android7.ducthangwru.testpomodoro.databases.models;

import com.google.gson.annotations.SerializedName;

import butterknife.BindView;

/**
 * Created by DUC THANG on 2/8/2017.
 */

public class Task {
    @SerializedName("name")
    private String name;

    @SerializedName("payment_per_hour")
    private float paymentPerHour;

    @SerializedName("color")
    private String color;

    @SerializedName("done")
    private boolean done;

    @SerializedName("local_id")
    private String localID;

    public float getPaymentPerHour() {
        return paymentPerHour;
    }

    public void setPaymentPerHour(float paymentPerHour) {
        this.paymentPerHour = paymentPerHour;
    }

    public Task(String name, String color, float paymentPerHour) {

        this.name = name;
        this.color = color;
        this.paymentPerHour = paymentPerHour;
    }

    public Task(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public boolean isCheck() {
        return done;
    }

    public void setCheck(boolean done) {
        done = done;
    }

    public Task(String name, String color, float paymentPerHour, boolean isDone) {

        this.name = name;
        this.color = color;
        this.paymentPerHour = paymentPerHour;
        this.done = isDone;
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

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", paymentPerHour=" + paymentPerHour +
                ", color='" + color + '\'' +
                ", done=" + done +
                '}';
    }

    public String getLocalID() {
        return localID;
    }
}
