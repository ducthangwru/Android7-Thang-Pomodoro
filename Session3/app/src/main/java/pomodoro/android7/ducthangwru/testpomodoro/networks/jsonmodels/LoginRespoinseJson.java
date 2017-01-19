package pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DUC THANG on 1/18/2017.
 */

public class LoginRespoinseJson {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("$token")
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
