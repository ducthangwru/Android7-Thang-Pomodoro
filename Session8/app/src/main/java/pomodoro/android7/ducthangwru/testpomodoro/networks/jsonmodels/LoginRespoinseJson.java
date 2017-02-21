package pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DUC THANG on 1/18/2017.
 */

public class LoginRespoinseJson {
    @SerializedName("access_token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
