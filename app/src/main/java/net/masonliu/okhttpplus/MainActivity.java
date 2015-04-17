package net.masonliu.okhttpplus;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;

import com.squareup.okhttp.Response;

import net.masonliu.okhttpplus.extension.OkHttpUtil;
import net.masonliu.okhttpplus.extension.SimpleRequest;
import net.masonliu.okhttpplus.extension.TextCallback;

/**
 * Created by liumeng on 4/9/15.
 */
public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpUtil.enqueue(new SimpleRequest(SimpleRequest.METHOD_GET, "http://httpbin.org/get", null), new TextCallback() {

            @Override
            public void onSuccess(Response response,String result) {
                Log.i("MainActivity", result);
            }

            @Override
            public void onFailed(Response response, Exception e) {
                Log.i("MainActivity", response.code() + "");
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
