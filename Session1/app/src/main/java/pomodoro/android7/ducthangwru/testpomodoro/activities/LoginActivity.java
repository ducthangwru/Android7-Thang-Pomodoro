package pomodoro.android7.ducthangwru.testpomodoro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pomodoro.android7.ducthangwru.testpomodoro.R;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btRegister;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    private void attemptLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(username.equals("admin") && password.equals("admin")) {
            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Logged Failled!", Toast.LENGTH_SHORT).show();
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

