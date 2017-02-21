package pomodoro.android7.ducthangwru.testpomodoro.networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DUC THANG on 2/21/2017.
 */

public class NetRetrofit {
    private Retrofit retrofit;
    private NetRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://a-task.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static final NetRetrofit instance = new NetRetrofit();
}
