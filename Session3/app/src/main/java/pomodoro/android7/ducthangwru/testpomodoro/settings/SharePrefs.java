package pomodoro.android7.ducthangwru.testpomodoro.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Set;

/**
 * Created by DUC THANG on 1/14/2017.
 */

public class SharePrefs {
    private static final String SHARED_PREFS_NAME = "SP";
    private static final String LOGIN_KEY = "login";
    private static final String SETTING_KEY = "setting";
    private static SharedPreferences sharedPreferences;

    private static SharePrefs instance;

    public static SharePrefs getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new SharePrefs(context);
    }

    public SharePrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void put(LoginCredendials loginCredendials) {
        String json = (new Gson().toJson(loginCredendials));

        sharedPreferences.edit().putString(LOGIN_KEY, json).commit();
    }

    public void putSetting(SettingCredendials settingCredendials) {
        String json = (new Gson().toJson(settingCredendials));
        sharedPreferences.edit().putString(SETTING_KEY, json).commit();
    }

    public LoginCredendials get() {
        String json = this.sharedPreferences.getString(LOGIN_KEY, null);
        if(json == null)
            return null;
        LoginCredendials login = new Gson().fromJson(json, LoginCredendials.class);
        return login;
    }

    public SettingCredendials getSetting() {
        String json = sharedPreferences.getString(SETTING_KEY, null);
        if(json == null)
            return null;
        SettingCredendials setting = new Gson().fromJson(json, SettingCredendials.class);
        return setting;
    }
}
