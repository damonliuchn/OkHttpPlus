package net.masonliu.okhttpplus.extension;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class BaseCallback implements Callback {

    @Override
    public void onFailure(Request request, IOException e) {
        onBaseFailed(null,e);
        onFinish();
    }

    @Override
    public void onResponse(Response response) throws IOException {
        onBaseSuccess(response);
        onFinish();
    }

    public abstract void onBaseSuccess(Response response);
    public abstract void onBaseFailed(Response response,Exception e);
    public abstract void onStart();
    public abstract void onFinish();

}