package com.masonliu.okhttpplus.download;

import com.masonliu.okhttpplus.OkHttpUtil;
import com.masonliu.okhttpplus.callback.DownloadCallback;
import com.masonliu.okhttpplus.request.SimpleRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Response;

/**
 * Created by liumeng on 16/11/22.
 */

public class DownloadFileTask extends OkAsyncTask<Void, Void, File> {
    private String url;
    private String path;
    private DownloadCallback callback;

    public DownloadFileTask(String url, String path, DownloadCallback downloadCallback) {
        this.url = url;
        this.path = path;
        this.callback = downloadCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (callback == null) {
            return;
        }
        callback.onDownloadStart();
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (callback == null) {
            return;
        }
        callback.onDownloadFinish();
        if (file.exists()) {
            callback.onDownloadSuccess(file);
        } else {
            callback.onDownloadFailed();
        }
    }

    @Override
    protected File doInBackground(Void... params) {
        File file = new File(path);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            file.getParentFile().mkdirs();
            file.delete();
            Response response = OkHttpUtil.execute(new SimpleRequest(SimpleRequest.METHOD_GET, url, null));
            inputStream = response.body().byteStream();
            outputStream = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                file.delete();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
        return file;
    }
}
