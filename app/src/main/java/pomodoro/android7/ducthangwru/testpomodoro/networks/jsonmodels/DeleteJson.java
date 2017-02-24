package pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DUC THANG on 2/24/2017.
 */


public class DeleteJson {
    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private int code;

    public DeleteJson(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DeleteJson{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}

