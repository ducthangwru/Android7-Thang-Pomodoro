package pomodoro.android7.ducthangwru.testpomodoro.settings;

/**
 * Created by DUC THANG on 1/14/2017.
 */

public class LoginCredendials {
    private String username;
    private String password;

    public LoginCredendials(String username, String password) {
        this.username = username;
        this.password = password;
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
                '}';
    }
}
