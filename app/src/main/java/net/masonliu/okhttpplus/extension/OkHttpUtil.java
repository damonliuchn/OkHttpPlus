package net.masonliu.okhttpplus.extension;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by liumeng on 4/9/15.
 */
public class OkHttpUtil {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    static{
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    public static Response execute(SimpleRequest request) throws IOException{
        return mOkHttpClient.newCall(request.getRequest()).execute();
    }

    public static void enqueue(SimpleRequest request, BaseCallback responseCallback){
        responseCallback.onStart();
        mOkHttpClient.newCall(request.getRequest()).enqueue(responseCallback);
    }

    public static void enqueue(SimpleRequest request){
        mOkHttpClient.newCall(request.getRequest()).enqueue(new Callback() {

            @Override
            public void onResponse(Response arg0) throws IOException {

            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {

            }
        });
    }
}
