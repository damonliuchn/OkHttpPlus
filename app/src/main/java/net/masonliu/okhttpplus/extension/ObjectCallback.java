package net.masonliu.okhttpplus.extension;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;

import java.lang.reflect.ParameterizedType;

public abstract class ObjectCallback<T> extends TextCallback {

    protected Class<T> tClass;

    @Override
    public void onSuccess(Response response,String responseString) {
        try {
            tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            T t = new Gson().fromJson(responseString, tClass);
            onSuccess(response,t);
        } catch (Exception e) {
            onObjectFailed(null, e);
        }
    }

    @Override
    public void onFailed(Response response,Exception e) {
        onObjectFailed(response,e);
    }

    public abstract void onSuccess(Response response,T result);
    public abstract void onObjectFailed(Response response,Exception e);

}