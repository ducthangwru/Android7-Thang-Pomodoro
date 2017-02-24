package pomodoro.android7.ducthangwru.testpomodoro.settings;

/**
 * Created by DUC THANG on 1/14/2017.
 */

public class LoginCredendials {
    private String username;
    private String password;
    private String accessToken;

    public LoginCredendials(String username, String password, String accessToken) {
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginCredendials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
