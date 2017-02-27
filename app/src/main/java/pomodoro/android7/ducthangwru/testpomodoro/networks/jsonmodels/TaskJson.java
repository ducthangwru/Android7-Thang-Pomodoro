package pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

import pomodoro.android7.ducthangwru.testpomodoro.databases.models.Task;

/**
 * Created by DUC THANG on 2/27/2017.
 */

public class TaskJson{
    @SerializedName("due_date")
    private String dueDate;

    @SerializedName("local_id")
    private String localID;

    @SerializedName("color")
    private String color;

    @SerializedName("id")
    private String id;

    @SerializedName("done")
    private boolean isDone;

    @SerializedName("payment_per_hour")
    private float payment;

    @SerializedName("name")
    private String name;

    @Override
    public String toString() {
        return "TaskJson{" +
                "dueDate='" + dueDate + '\'' +
                ", localID='" + localID + '\'' +
                ", color='" + color + '\'' +
                ", id='" + id + '\'' +
                ", isDone=" + isDone +
                ", payment=" + payment +
                ", name='" + name + '\'' +
                '}';
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public TaskJson(String name, String color, float payment) {
        this.name = name;
        this.color = color;
        this.payment = payment;
    }

    public String getLocalID() {
        return localID;
    }

    public void setLocalID(String localID) {
        this.localID = localID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public float getPayment() {
        return payment;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskJson(String dueDate, String localID, String color, String id, boolean isDone, float payment, String name) {

        this.dueDate = dueDate;
        this.localID = localID;
        this.color = color;
        this.id = id;
        this.isDone = isDone;
        this.payment = payment;
        this.name = name;
    }
}
