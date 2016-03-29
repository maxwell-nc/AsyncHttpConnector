package pres.nc.maxwell.asynchttpconnector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import pres.nc.maxwell.asynchttp.HttpConnector;
import pres.nc.maxwell.asynchttp.callback.impl.StringCallback;
import pres.nc.maxwell.asynchttp.response.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    private void test() {

        HttpConnector
                .get()
                .url("https://www.baidu.com")
                .addParams("params", "value")//参数
                .log("YourLogTag")//打印日志的标记，不设置则不打印
                .callback(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("Response Data:", response.getResponseData());
                    }

                })
                .build()
                .execute();

    }
}

