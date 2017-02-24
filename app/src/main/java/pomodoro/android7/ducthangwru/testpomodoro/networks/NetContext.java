package pomodoro.android7.ducthangwru.testpomodoro.networks;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import pomodoro.android7.ducthangwru.testpomodoro.networks.services.LoginService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DUC THANG on 2/21/2017.
 */

public class NetContext {
    private static final String TAG = NetContext.class.toString();
    private Retrofit retrofit;
    private NetContext() {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggerInterceptor()).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://a-task.herokuapp.com/api/").client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public LoginService createLoginService() {
        return retrofit.create(LoginService.class);
    }

    class LoggerInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //1: Get Request
            Request request = chain.request();
            //2: Process request
            RequestBody body = request.body();
            if(body != null) {
                Log.d("NetContext", String.format("%s", body));
            }

            Headers headers = request.headers();

            if(headers != null) {
                Log.d("NetContext", String.format("%s", headers));
            }

            //3: Send
            Response response = chain.proceed(request);
            Log.d(TAG, String.format("%s", response.toString()));
            Log.d(TAG, String.format("%s", getResponseString(response)));
            return response;
        }
    }
    public Retrofit getRetrofit() {
        return retrofit;
    }

    private String getResponseString(okhttp3.Response response) {
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        return buffer.clone().readString(Charset.forName("UTF-8"));
    }

    public static final NetContext instance = new NetContext();
}
