package net.masonliu.okhttpplus;

import com.squareup.okhttp.Response;

public abstract class TextCallback extends BaseCallback {


    @Override
    public void onBaseFailed(Response response, Exception e) {
        onFailed(response,e);
    }

    @Override
    public void onBaseSuccess(Response response) {
        if (response.isSuccessful()) {
            try{
                String responseStr = response.body().string();
                onSuccess(response,responseStr);
            }catch(Exception e){
                onFailed(response,e);
            }
        } else {
            onFailed(response,null);
        }
    }

    public abstract void onSuccess(Response response,String result);
    public abstract void onFailed(Response response,Exception e);

}