package pomodoro.android7.ducthangwru.testpomodoro.networks;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DUC THANG on 1/18/2017.
 */

public class NetContext {
    private Retrofit retrofit;

    public NetContext() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://a-task.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}