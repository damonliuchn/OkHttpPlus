package net.masonliu.okhttpplus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class BaseCallback implements Callback {
    public final static int NETWORK_ERROR = 10000;
    public final static int NO_RESPONSE = 10001;

    protected Context context;
    protected Handler mainHandler;
    protected Activity activity;
    protected Fragment fragment;
    protected boolean needBindVerify;

    public BaseCallback(Context context) {
        this.context = context;
        mainHandler = new Handler(context.getMainLooper());
    }

    public BaseCallback(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        mainHandler = new Handler(context.getMainLooper());
        needBindVerify = true;
    }

    public BaseCallback(Fragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getActivity().getApplicationContext();
        mainHandler = new Handler(context.getMainLooper());
        needBindVerify = true;
    }

    @Override
    public void onFailure(Request request, IOException e) {
        Response response = null;
        if (getCurrentNetType(context) == 0) {
            response = new Response.Builder()
                    .request(request)
                    .code(NETWORK_ERROR)
                    .message("NETWORK_ERROR")
                    .protocol(Protocol.HTTP_1_0)
                    .build();
        } else {
            response = new Response.Builder()
                    .request(request)
                    .code(NO_RESPONSE)
                    .message("NO_RESPONSE")
                    .protocol(Protocol.HTTP_1_0)
                    .build();
        }
        failedAndFinish(response, e);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        onBaseSuccess(response);
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!needBindVerify || verify()) {
                    onFinish();
                }
            }
        });
    }

    public void failedAndFinish(final Response response, final Exception e) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!needBindVerify || verify()) {
                    onFailed(response, e);
                    onFinish();
                }
            }
        });
    }

    public abstract void onBaseSuccess(Response response);

    public abstract void onFailed(Response response, Exception e);

    public abstract void onStart();

    public abstract void onFinish();

    private int getCurrentNetType(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                // 判断当前网络是否已经连接
                if (info != null && info.isConnected()) {
                    return 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected boolean verify() {
        if (needBindVerify && activity != null && !activity.isFinishing()) {
            return true;
        }
        if (needBindVerify && fragment != null && fragment.isAdded() && !fragment.getActivity().isFinishing()) {
            return true;
        }
        return false;
    }

}