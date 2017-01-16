package pomodoro.android7.ducthangwru.testpomodoro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.settings.LoginCredendials;
import pomodoro.android7.ducthangwru.testpomodoro.settings.SharePrefs;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.toString();
    private EditText etUsername;
    private EditText etPassword;
    private Button btRegister;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skipLoginPossible();
        setContentView(R.layout.activity_login);

        etUsername = (EditText) this.findViewById(R.id.et_username);
        etPassword = (EditText) this.findViewById(R.id.et_password);
        btRegister = (Button) this.findViewById(R.id.bt_register);
        btLogin = (Button) this.findViewById(R.id.bt_login);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        SharePrefs sharePrefs = new SharePrefs(this);
        sharePrefs.put(new LoginCredendials("thang", "hiuhi"));
        Log.d(TAG, String.format("%s", sharePrefs.get().toString()));
    }

    private void attemptLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(username.equals("admin") && password.equals("admin")) {
            SharePrefs.getInstance().put(new LoginCredendials(username, password));
            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
            gotoTaskActivity();
        }
        else {
            Toast.makeText(this, "Logged Failled!", Toast.LENGTH_SHORT).show();
        }
    }


    private void gotoTaskActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }

    private void skipLoginPossible() {
        if(SharePrefs.getInstance().get() != null) {
            gotoTaskActivity();
        }
    }
    private void attemptRegister() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        
        if((username.equals("admin") || username.isEmpty() || password.isEmpty())) {
            Toast.makeText(this, "Register Failled!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show();
    }
}

