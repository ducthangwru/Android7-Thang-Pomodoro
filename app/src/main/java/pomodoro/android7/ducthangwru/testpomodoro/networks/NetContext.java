package pomodoro.android7.ducthangwru.testpomodoro.networks;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.TaskServices;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DUC THANG on 2/21/2017.
 */

public class NetContext {
    private Retrofit retrofit;
    private NetContext() {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(new LoggerInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://a-task.herokuapp.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static final NetContext instance = new NetContext();


    class LoggerInterceptor implements Interceptor{
        private static final String TAG = "LoggerInterceptor";
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            RequestBody body = request.body();
            Log.d(TAG, String.format("intercept: %s", body ));
            if(body != null){
                Log.d(TAG, String.format("intercept: %s", body.toString() ));
            }
            okhttp3.Headers header = request.headers();

            if(header != null){
                Log.d(TAG, String.format("header: %s", header));
            }

            Response response =  chain.proceed(request);
            Log.d(TAG, String.format("intercept: %s", request ));

            Log.d(TAG, String.format("intercept: %s", response.toString()));
            Log.d(TAG, String.format("intercept: %s", getResponseString(response)));
            return response;
        }
    }

    private String getResponseString(okhttp3.Response response) {
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        return buffer.clone().readString(Charset.forName("UTF-8"));
    }

    public TaskServices createTaskSevice(){
        return retrofit.create(TaskServices.class);
    }
}
