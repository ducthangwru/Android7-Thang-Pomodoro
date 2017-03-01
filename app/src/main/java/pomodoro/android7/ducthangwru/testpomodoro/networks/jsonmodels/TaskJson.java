package pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by DUC THANG on 2/27/2017.
 */

public class TaskJson extends RealmObject{
    @SerializedName("due_date")
    private String dueDate;

    @PrimaryKey
    @SerializedName("local_id")
    private String local_id;

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
                ", localID='" + local_id + '\'' +
                ", color='" + color + '\'' +
                ", id='" + id + '\'' +
                ", isDone=" + isDone +
                ", payment=" + payment +
                ", name='" + name + '\'' +
                '}';
    }

    public TaskJson() {
        local_id = UUID.randomUUID().toString();
    }

    public TaskJson(String name, String color, float payment) {
        this.local_id = UUID.randomUUID().toString();
        this.name = name;
        this.color = color;
        this.payment = payment;
    }

    public TaskJson(String localID, String color, String name, float payment) {
        this.local_id = localID;
        this.color = color;
        this.name = name;
        this.payment = payment;
    }

    public String getLocalID() {
        return local_id;
    }

    public void setLocalID(String localID) {
        this.local_id = localID;
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
        this.local_id = localID;
        this.color = color;
        this.id = id;
        this.isDone = isDone;
        this.payment = payment;
        this.name = name;
    }
}
