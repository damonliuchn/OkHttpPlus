package com.masonliu.okhttpplus.callback;

import android.app.Activity;
import android.content.Context;

import okhttp3.Response;


public abstract class TextCallback extends BaseCallback {

    private boolean isOnSuccessInMainThread;

    public TextCallback(Context context) {
        super(context);
        this.isOnSuccessInMainThread = true;
    }

    public TextCallback(Activity context) {
        super(context);
        this.isOnSuccessInMainThread = true;
    }

    public TextCallback(Context context, boolean isOnSuccessInMainThread) {
        super(context);
        this.isOnSuccessInMainThread = isOnSuccessInMainThread;
    }

    @Override
    public void onBaseSuccess(final Response response) {
        if (response.isSuccessful()) {
            try {
                final String responseStr = response.body().string();
                if (isOnSuccessInMainThread) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!needBindVerify || verify()) {
                                onSuccess(response, responseStr);
                            }
                        }
                    });
                } else {
                    if (!needBindVerify || verify()) {
                        onSuccess(response, responseStr);
                    }
                }
            } catch (Exception e) {
                failedAndFinish(response, e);
            }
        } else {
            failedAndFinish(response, new Exception("have response but it is not successful"));
        }
    }

    public abstract void onSuccess(Response response, String result);
}