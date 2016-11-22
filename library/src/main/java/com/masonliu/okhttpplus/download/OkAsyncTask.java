package com.masonliu.okhttpplus.download;

import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.Executors;

/**
 * Created by liumeng on 7/22/15.
 */
public abstract class OkAsyncTask<PA, PR, R> extends AsyncTask<PA, PR, R> {

    public void executeOnMyExecutor(PA... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            this.executeOnExecutor(Executors.newCachedThreadPool(), params);
        } else {
            this.execute(params);
        }
    }

    @Override
    protected abstract R doInBackground(PA... params);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(R aVoid) {
        super.onPostExecute(aVoid);
    }
}
