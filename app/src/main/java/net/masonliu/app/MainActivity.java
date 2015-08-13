package net.masonliu.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Response;

import net.masonliu.okhttpplus.BaseCallback;
import net.masonliu.okhttpplus.OkHttpUtil;
import net.masonliu.okhttpplus.SimpleRequest;
import net.masonliu.okhttpplus.TextCallback;

/**
 * Created by liumeng on 4/9/15.
 */
public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView testTextView = (TextView) findViewById(R.id.test_tv);
        OkHttpUtil.enqueue(new SimpleRequest(SimpleRequest.METHOD_GET, "http://httpbin.org/get", null), new TextCallback(this) {

            @Override
            public void onSuccess(Response response, String result) {
                Log.i("MainActivity", result + Thread.currentThread().getId());
                Toast.makeText(MainActivity.this, "ddd", Toast.LENGTH_LONG).show();
                testTextView.setText("ddddddd");
            }

            @Override
            public void onFailed(Response response, Exception e) {
                Log.i("MainActivity", response.code() + e.toString());
                if (response.code() == BaseCallback.NETWORK_ERROR) {
                    //没有网络
                } else if (response.code() == BaseCallback.NO_RESPONSE) {
                    //有网络，但连不上服务器
                } else if (response.code() >= 200 && response.code() < 300) {
                    //内部错误，如 json 解析出错
                } else {
                    //有网络，也连上了服务器，但是正常标识的 error httpcode
                }
            }

            @Override
            public void onStart() {
                Log.i("MainActivity", "start:" + Thread.currentThread().getId());
            }

            @Override
            public void onFinish() {

            }
        });
        //more simple method
//        OkHttpUtil.get("http://httpbin.org/get", null, new TextCallback(this) {
//
//            @Override
//            public void onSuccess(Response response, String result) {
//                Log.i("MainActivity", result + Thread.currentThread().getId());
//                Toast.makeText(MainActivity.this, "ddd", Toast.LENGTH_LONG).show();
//                testTextView.setText("ddddddd");
//            }
//
//            @Override
//            public void onFailed(Response response, Exception e) {
//                Log.i("MainActivity", response.code() + e.toString());
//            }
//
//            @Override
//            public void onStart() {
//                Log.i("MainActivity", "start:" + Thread.currentThread().getId());
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
    }
}
