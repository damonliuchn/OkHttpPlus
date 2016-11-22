package com.masonliu.okhttpplus.request;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SimpleRequest {
    public static final int METHOD_GET = 1;
    public static final int METHOD_POST = 2;
    public Request.Builder mRequestBuilder = new Request.Builder();
    public HashMap<String, String> mHeaders = new HashMap<String, String>();
    public HashMap<String, String> mParams = new HashMap<String, String>();
    public HashMap<String, Pair<String, File>> mFiles = new HashMap<String, Pair<String, File>>();
    public String mUrl;
    private Request request;
    private RequestBody mRequestBody;
    private int method = METHOD_GET;

    public SimpleRequest(int method, String url, Map<String, String> params) {
        mUrl = url;
        if (params != null) {
            mParams = (HashMap<String, String>) params;
        }
        this.method = method;
    }

    public Request getRequest() {
        switch (method) {
            case METHOD_POST:
                initPostRequest();
                break;
            default:
                initGetRequest();
                break;
        }
        return request;
    }

    private void prepareHeaders() {
        for (Map.Entry<String, String> map : mHeaders.entrySet()) {
            mRequestBuilder.header(map.getKey(), map.getValue());
        }

    }

    private void prepareParamsForPost() {

        if (mFiles.size() == 0) {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> map : mParams.entrySet()) {
                builder.add(map.getKey(), map.getValue());
            }
            mRequestBody = builder.build();
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (Map.Entry<String, String> map : mParams.entrySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + map.getKey() + "\""), RequestBody.create(null, map.getValue()));
            }
            for (Map.Entry<String, Pair<String, File>> map : mFiles.entrySet()) {
                Pair<String, File> filePair = map.getValue();
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + map.getKey() + "\"; filename=\"" + map.getKey() + "\""), RequestBody.create(MediaType.parse(filePair.getFirst()), filePair.getSecond()));
            }
            mRequestBody = builder.build();
        }

    }

    private void prepareParamsForGet() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : mParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey()),
                    urlEncodeUTF8(entry.getValue())));
        }
        if (sb.toString().length() > 0) {
            if (mUrl.contains("?")) {
                mUrl = mUrl + "&";
            } else {
                mUrl = mUrl + "?";
            }
            mUrl = mUrl + sb.toString();
        }
    }

    private String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public void initPostRequest() {

        prepareHeaders();
        prepareParamsForPost();

        mRequestBuilder.url(mUrl);
        mRequestBuilder.post(mRequestBody);
        request = mRequestBuilder.build();

    }

    public void initGetRequest() {

        prepareHeaders();
        prepareParamsForGet();

        mRequestBuilder.url(mUrl);
        mRequestBuilder.get();
        request = mRequestBuilder.build();

    }

}
