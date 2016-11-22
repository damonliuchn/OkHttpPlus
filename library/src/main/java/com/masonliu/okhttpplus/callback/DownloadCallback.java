package com.masonliu.okhttpplus.callback;

import java.io.File;

/**
 * Created by liumeng on 16/11/22.
 */

public interface DownloadCallback {
    void onDownloadStart();
    void onDownloadFinish();
    void onDownloadSuccess(File file);
    void onDownloadFailed();
}
