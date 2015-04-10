package net.masonliu.okhttpplus.extension;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SimpleRequest {
    private Request request;
    private RequestBody mRequestBody;
    public Request.Builder mRequestBuilder = new Request.Builder();

    public static final int METHOD_GET = 1;
    public static final int METHOD_POST = 2;
    private int method = METHOD_GET;

    public HashMap<String, String> mHeaders = new HashMap<String, String>();
    public HashMap<String, String> mParams = new HashMap<String, String>();
    public HashMap<String, Pair<String, File>> mFiles = new HashMap<String, Pair<String, File>>();
    public String mUrl;

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
            FormEncodingBuilder builder = new FormEncodingBuilder();
            for (Map.Entry<String, String> map : mParams.entrySet()) {
                builder.add(map.getKey(), map.getValue());
            }
            mRequestBody = builder.build();
        } else {
            MultipartBuilder builder = new MultipartBuilder();
            builder.type(MultipartBuilder.FORM);
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
        if(sb.toString().length()>0){
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
