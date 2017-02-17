package pomodoro.android7.ducthangwru.testpomodoro.databases.models;

/**
 * Created by DUC THANG on 2/8/2017.
 */

public class Task {
    private String name;
    private String color;
    private float paymentPerHour;

    public Task(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Task(String name, String color, float paymentPerHour) {
        this.name = name;
        this.color = color;
        this.paymentPerHour = paymentPerHour;
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

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", paymentPerHour=" + paymentPerHour +
                '}';
    }
}
