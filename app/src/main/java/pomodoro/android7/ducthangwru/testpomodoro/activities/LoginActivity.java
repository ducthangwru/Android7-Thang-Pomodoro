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
import pomodoro.android7.ducthangwru.testpomodoro.networks.NetContext;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.LoginBodyJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.LoginResponseJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.RegisterBodyJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.RegisterResponseJson;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.LoginService;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.RegisterService;
import pomodoro.android7.ducthangwru.testpomodoro.settings.LoginCredendials;
import pomodoro.android7.ducthangwru.testpomodoro.settings.SharePrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.toString();
    private EditText etUsername;
    private EditText etPassword;
    private Button btRegister;
    private Button btLogin;
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;
    private ProgressBar pbLoading;
    private MediaType jsonType = MediaType.parse("application/json");

    private String username;
    private String password;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //2 Create Service
        LoginService loginService = NetContext.instance.getRetrofit().create(LoginService.class);
        //data & format
        String loginJson = (new Gson()).toJson(new LoginBodyJson(username, password));
        RequestBody loginBody = RequestBody.create(jsonType, loginJson);
        //create Call
        Call<LoginResponseJson> loginCall = loginService.login(loginBody);
        loginCall.enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(Call<LoginResponseJson> call, Response<LoginResponseJson> response) {
                LoginResponseJson loginResponseJson = response.body();
                if (loginResponseJson == null) {
                    pbLoading.setVisibility(View.GONE);
                    if(response.code()==401){
                        Toast.makeText(LoginActivity.this, "error password", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    if (response.code() == 200) {
                        Log.d(TAG, String.format("%s", loginResponseJson));
                        token = loginResponseJson.getToken();
                        pbLoading.setVisibility(View.GONE);
                        getDataTasks();
                        onLoginSuccess();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseJson> call, Throwable t) {
                Log.d(TAG, String.format("onFailure %s", t));
                pbLoading.setVisibility(View.GONE);
            }
        });
    }

    private void sentRegister(String username, String password) {
        RegisterService registerSevice = NetContext.instance.getRetrofit().create(RegisterService.class);

        String registerJson = (new Gson()).toJson(new RegisterBodyJson(username, password));
        Log.d(TAG, String.format("%s", registerJson));
        RequestBody registerBody = RequestBody.create(jsonType, registerJson);
        Log.d(TAG, String.format("sendRegister: %s", registerBody));
        registerSevice.register(registerBody)
                .enqueue(new Callback<RegisterResponseJson>() {
                    @Override
                    public void onResponse(Call<RegisterResponseJson> call, Response<RegisterResponseJson> response) {
                        Log.d(TAG, "onResponse Code: " + response.code());
                        //ban dau em de la 400 cung k chay?
                        RegisterResponseJson registerResponse = response.body();
                        Log.d(TAG, String.format("onResponse: %s", registerResponse));
                        if (registerResponse == null) {
                            if (response.code() == 400) {
                                Toast.makeText(LoginActivity.this, "This account has already existed!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "register fail", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (response.code() == 200) {
                                Toast.makeText(LoginActivity.this, "Register success!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponseJson> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Register Fail!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void onLoginSuccess() {
        SharePrefs.getInstance().put(new LoginCredendials(username, password, token));
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

    private void getDataTasks(){
//        DbContext.getInstance().deleteAll();
//        TaskServices getTaskService = NetContext.instance.createTaskSevice();
//        getTaskService.getTasks("JWT "+token).enqueue(new Callback<List<TaskJson>>() {
//            @Override
//            public void onResponse(Call<List<TaskJson>> call, Response<List<TaskJson>> response) {
//                for(TaskJson taskJson : response.body()){
//                    if(taskJson.getColor() != null) {
//                        DbContext.getInstance().addOrUpdate(taskJson);
//
//                    }
//                }
//                for (TaskJson task : DbContext.getInstance().allTasks()){
//                    Log.d(TAG, String.format("TaskJson: %s", task));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<TaskJson>> call, Throwable t) {
//
//            }
//        });
    }


    private void attemptRegister() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if (!username.equals("admin")) {
            sentRegister(username, password);
        }
    }
}

