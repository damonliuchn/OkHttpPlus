package com.masonliu.okhttpplus;


import com.masonliu.okhttpplus.callback.BaseCallback;
import com.masonliu.okhttpplus.callback.DownloadCallback;
import com.masonliu.okhttpplus.download.DownloadFileTask;
import com.masonliu.okhttpplus.request.SimpleRequest;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by liumeng on 4/9/15.
 */
public class OkHttpUtil {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();

    private OkHttpUtil() {

    }

    public static Response execute(SimpleRequest request) throws IOException {
        return mOkHttpClient.newCall(request.getRequest()).execute();
    }

    public static void enqueue(SimpleRequest request, BaseCallback responseCallback) {
        responseCallback.onStart();
        mOkHttpClient.newCall(request.getRequest()).enqueue(responseCallback);
    }

    public static void enqueue(SimpleRequest request) {
        mOkHttpClient.newCall(request.getRequest()).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public static void get(String url, Map<String, String> params, BaseCallback responseCallback) {
        OkHttpUtil.enqueue(new SimpleRequest(SimpleRequest.METHOD_GET, url, params), responseCallback);
    }

    public static void post(String url, Map<String, String> params, BaseCallback responseCallback) {
        OkHttpUtil.enqueue(new SimpleRequest(SimpleRequest.METHOD_POST, url, params), responseCallback);
    }

    public static void download(String url, String path, DownloadCallback downloadCallback) {
        new DownloadFileTask(url, path, downloadCallback).executeOnMyExecutor();
    }
}
