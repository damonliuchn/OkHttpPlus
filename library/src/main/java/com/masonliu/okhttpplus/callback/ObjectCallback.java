package com.masonliu.okhttpplus.callback;

import android.content.Context;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

public abstract class ObjectCallback<T> extends TextCallback {

    protected Class<T> tClass;

    public ObjectCallback(Context context) {
        super(context, false);
    }

    @Override
    public void onSuccess(final Response response, String responseString) {
        try {
            tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            final T t = new Gson().fromJson(responseString, tClass);
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (!needBindVerify || verify()) {
                        onSuccess(response, t);
                    }
                }
            });
        } catch (Exception e) {
            failedAndFinish(response, e);
        }
    }

    public abstract void onSuccess(Response response, T result);

}