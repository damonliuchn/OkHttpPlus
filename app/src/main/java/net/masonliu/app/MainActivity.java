package net.masonliu.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Response;

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
