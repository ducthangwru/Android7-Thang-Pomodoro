package pomodoro.android7.ducthangwru.testpomodoro.databases.models;

/**
 * Created by DUC THANG on 2/11/2017.
 */

public class TableColor {
    private String color;

    public TableColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "TableColor{" +
                "color='" + color + '\'' +
                '}';
    }
}
