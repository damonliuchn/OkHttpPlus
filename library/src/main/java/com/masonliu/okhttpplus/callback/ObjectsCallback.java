package com.masonliu.okhttpplus.callback;

import android.content.Context;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public abstract class ObjectsCallback<T> extends TextCallback {

    protected Class<T> tClass;

    public ObjectsCallback(Context context) {
        super(context, false);
    }

    @Override
    public void onSuccess(final Response response, String responseString) {
        try {
            tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Type type = com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, ArrayList.class, tClass);
            final List<T> ts = new Gson().fromJson(responseString, type);
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (!needBindVerify || verify()) {
                        onSuccess(response, ts);
                    }
                }
            });
        } catch (Exception e) {
            failedAndFinish(response, e);
        }
    }

    public abstract void onSuccess(Response response, List<T> result);

}