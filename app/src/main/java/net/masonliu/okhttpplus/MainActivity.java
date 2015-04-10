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

        String aa="imei:359776056763222 serial:7335ba3a android_id:fe2f16cfba33be59 brand:samsung model:GT-I9300I networkOperator:46000 macAddress:68_05_71_35_8b_8b netType:WIFI simSerialNumber:898602 simState:5 latitude:31.2350012 longitude:121.3770899 cid:6689 lac:6169 wifiList:94_b4_0f_3c_49_10,94_b4_0f_3c_59_75,94_b4_0f_3c_59_73,94_b4_0f_3c_59_76,94_b4_0f_3c_59_72,94_b4_0f_3c_59_74,94_b4_0f_3c_49_16,94_b4_0f_3c_49_12,94_b4_0f_3c_49_13,94_b4_0f_3c_49_14 haveBluetooth:true track_id: memory:1015 energy_percent:85 first_open:1428374305 last_open:1428572627 net_type:wifi hardware_id:8d2036103cd3a214fc6e54e8dbe5019b";

        String aaad =  Base64.encodeToString(aa.toString().getBytes(), Base64.DEFAULT);
        System.out.println("aaaa"+aaad.length()+"-"+aaad);
        System.out.println("aaaa"+aa.length() +"-"+aa.toString());


    }
}
