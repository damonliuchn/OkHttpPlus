package net.masonliu.okhttpplus.extension;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class ObjectsCallback<T> extends TextCallback {

    protected Class<T> tClass;

    @Override
    public void onSuccess(Response response,String responseString) {
        try {
            tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Type type = com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, ArrayList.class, tClass);
            List<T> ts = new Gson().fromJson(responseString, type);
            onSuccess(response,ts);
        } catch (Exception e) {
            onObjectFailed(null, e);
        }
    }

    @Override
    public void onFailed(Response response,Exception e) {
        onObjectFailed(response,e);
    }

    public abstract void onSuccess(Response response,List<T> result);
    public abstract void onObjectFailed(Response response,Exception e);

}