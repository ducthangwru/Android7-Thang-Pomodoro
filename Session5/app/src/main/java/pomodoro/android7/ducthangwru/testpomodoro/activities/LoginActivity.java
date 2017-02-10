package pomodoro.android7.ducthangwru.testpomodoro.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;
    private ProgressBar pbLoading;

    Retrofit retrofit;
    private String username;
    private String password;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //skipLoginPossible();
        setContentView(R.layout.activity_login);

        etUsername = (EditText) this.findViewById(R.id.et_username);
        etPassword = (EditText) this.findViewById(R.id.et_password);
        btRegister = (Button) this.findViewById(R.id.bt_register);
        btLogin = (Button) this.findViewById(R.id.bt_login);
        tilUsername = (TextInputLayout) this.findViewById(R.id.et_error_username);
        tilPassword = (TextInputLayout) this.findViewById(R.id.et_error_password);
        pbLoading = (ProgressBar) this.findViewById(R.id.pb_loading);

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

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    private void sendLogin(String username, String password) {
        pbLoading.setVisibility(View.VISIBLE);
        //1. Create retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://a-task.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //2 Create Service
        LoginService loginService = retrofit.create(LoginService.class);
        //data & format
        //format ==> MediaType
        //data ==> json


        MediaType jsonType = MediaType.parse("application/json");
        String loginJson = (new Gson()).toJson(new LoginBodyJson(username, password));
        final RequestBody loginBody = RequestBody.create(jsonType, loginJson);

        //3. Create Call
        Call<LoginRespoinseJson> loginCall = loginService.login(loginBody);
        loginCall.enqueue(new Callback<LoginRespoinseJson>() {
            @Override
            public void onResponse(Call<LoginRespoinseJson> call, Response<LoginRespoinseJson> response) {
                LoginRespoinseJson loginRespoinseJson = response.body();
                if (loginRespoinseJson == null) {
                    Log.d(TAG, "onResponse: Could not parse body");
                } else {
                    Log.d(TAG, String.format("onResponse: =))))  %s", loginRespoinseJson));
                    if (response.code() == 200) {
                        token = loginRespoinseJson.getAccessToken();
                        onLoginSuccess();
                    }
                }
                Log.d(TAG, "onResponse");
                pbLoading.setVisibility(View.GONE);
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
                if (registerRespoinseJson == null) {
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

    private void onLoginSuccess() {
        SharePrefs.getInstance().put(new LoginCredendials(username, password));
        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
        gotoTaskActivity();
    }

    private void attemptLogin() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if(checkLogin()) {
            sendLogin(username, password);
        }
    }

    private boolean checkLogin() {
        if (username.isEmpty()) {
            tilUsername.setError(getString(R.string.error_username));
        } else if (password.isEmpty()) {
            tilPassword.setError(getString(R.string.error_password));
        } else if (password.length() < 5) {
            tilPassword.setError(getString(R.string.error_password_lenght));
        } else if (password.length() >= 5 && !username.isEmpty()) {
            return true;
        }

        return false;
    }

    private void gotoTaskActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void skipLoginPossible() {
        if (SharePrefs.getInstance().get() != null) {
            gotoTaskActivity();
        }
    }


    private void attemptRegister() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if (!username.equals("admin")) {
            sentRegister(username, password);
        }
    }
}

