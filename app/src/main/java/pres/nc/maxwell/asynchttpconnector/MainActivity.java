package pres.nc.maxwell.asynchttpconnector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;

import pres.nc.maxwell.asynchttp.HttpConnector;
import pres.nc.maxwell.asynchttp.handler.ResultHandler;
import pres.nc.maxwell.asynchttp.handler.impl.ByteArrayHandler;
import pres.nc.maxwell.asynchttp.handler.impl.StringHandler;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;
import pres.nc.maxwell.asynchttp.utils.Constant;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    private void test() {
        //参数
        HashMap<String, Object> params = new HashMap<>();
        params.put("params", "value");

        final Request request = new Request("https://www.baidu.com",params);
        //request.setRequestMethod(Constant.METHOD_POST);//默认为GET，可手动设置为POST

        HttpConnector.request(request, new StringHandler() {
            @Override
            public void onPrepared() {
                Log.i("Request:", request.toString());
            }

            @Override
            public void onSuccess(Response<String> response) {
                Log.i("Response Message:", response.getResponseMsg());
                Log.i("Response Data:", response.getResponseData());
            }

            @Override
            public void onFailure(Response<String> response) {
                Log.i("Error code:", String.valueOf(response.getResponseCode()));
            }
        });

    }
}

