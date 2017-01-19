package pomodoro.android7.ducthangwru.testpomodoro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import pomodoro.android7.ducthangwru.testpomodoro.R;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.LoginBodyJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.LoginRespoinseJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.RegisterBodyJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.RegisterRespoinseJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.LoginService;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.RegisterService;
import pomodoro.android7.ducthangwru.testpomodoro.settings.LoginCredendials;
import pomodoro.android7.ducthangwru.testpomodoro.settings.SharePrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.toString();
    private EditText etUsername;
    private EditText etPassword;
    private Button btRegister;
    private Button btLogin;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //skipLoginPossible();
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

    private void sendLogin(String username, String password) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://a-task.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginService loginService = retrofit.create(LoginService.class);
        //data & format
        //format ==> MediaType
        //data ==> json

        MediaType jsonType = MediaType.parse("application/json");
        String loginJson = (new Gson()).toJson(new LoginBodyJson(username, password));

        final RequestBody loginBody = RequestBody.create(jsonType, loginJson);

        loginService.login(loginBody).enqueue(new Callback<LoginRespoinseJson>() {
            @Override
            public void onResponse(Call<LoginRespoinseJson> call, Response<LoginRespoinseJson> response) {
                LoginRespoinseJson loginRespoinseJson = response.body();
                if(loginRespoinseJson == null) {
                    Log.d(TAG, "onResponse: Could not parse body");
                } else {
                    Log.d(TAG, String.format("onResponse: =))))  %s", loginRespoinseJson));
                    if(response.code() == 200) {
                        onLoginSuccess();
                    }
                }
                Log.d(TAG, "onResponse");
            }

            @Override
            public void onFailure(Call<LoginRespoinseJson> call, Throwable t) {
                Log.d(TAG, String.format("onFailure: %s", t));
            }
        });
    }

    private void sentRegister(String username, String password) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://a-task.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterService registerService = retrofit.create(RegisterService.class);

        MediaType jsonType = MediaType.parse("application/json");
        String registerJson = (new Gson()).toJson(new RegisterBodyJson(username, password));

        final RequestBody registerBody = RequestBody.create(jsonType, registerJson);

        registerService.register(registerBody).enqueue(new Callback<RegisterRespoinseJson>() {

            @Override
            public void onResponse(Call<RegisterRespoinseJson> call, Response<RegisterRespoinseJson> response) {
                RegisterRespoinseJson registerRespoinseJson = response.body();
                if(registerRespoinseJson == null) {
                    Log.d(TAG, "onResponse: Could not parse body");
                    Toast.makeText(LoginActivity.this, "Register Failed!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, String.format("onResponse: Register Success %s", registerRespoinseJson));
                    Toast.makeText(LoginActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterRespoinseJson> call, Throwable t) {
                Log.d(TAG, "onResponse: not Connected!!");
                Toast.makeText(LoginActivity.this, "Register Failed! Not Connected!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String username;
    private String password;

    private void onLoginSuccess() {
        SharePrefs.getInstance().put(new LoginCredendials(username, password));
        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
        gotoTaskActivity();
    }

    private void attemptLogin() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        sendLogin(username, password);
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
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if(!username.equals("admin")) {
            sentRegister(username, password);
        }
    }
}

