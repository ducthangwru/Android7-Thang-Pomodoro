package pomodoro.android7.ducthangwru.testpomodoro.databases;

import android.content.Context;
import java.util.List;

import io.realm.Realm;
import pomodoro.android7.ducthangwru.testpomodoro.networks.jsonmodels.TaskJson;

/**
 * Created by DUC THANG on 2/8/2017.
 */

public class DbContext {
    private Realm realm;

    private DbContext(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    private static DbContext instance;

    public static DbContext getInstance() {
        return instance;
    }

    public static void setInstance(Context context) {
        instance = new DbContext(context);
    }

    public void addTask(TaskJson task) {
        realm.beginTransaction();
        realm.copyToRealm(task);
        realm.commitTransaction();
    }

    public List<TaskJson> allTasks() {
        return realm.where(TaskJson.class).findAll();
    }

    public void addOrUpdate(TaskJson task) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(task);
        realm.commitTransaction();
    }

    public void delete(String idFind) {
        realm.beginTransaction();
        realm.where(TaskJson.class).equalTo("local_id", idFind).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }

    public void deleteAll() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public static String[] colors = {"#F44336", "#E91E63", "#9C27B0", "#673AB7",
           "#3F51B5", "#2196F3", "#03A9F4", "#009688"};
}

