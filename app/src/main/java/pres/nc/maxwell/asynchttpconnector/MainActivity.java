package pres.nc.maxwell.asynchttpconnector;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import pres.nc.maxwell.asynchttp.HttpConnector;
import pres.nc.maxwell.asynchttp.callback.impl.FileCallback;
import pres.nc.maxwell.asynchttp.callback.impl.StringCallback;
import pres.nc.maxwell.asynchttp.response.Response;

public class MainActivity extends AppCompatActivity {

    String url = "https://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void get(View view) {

        HttpConnector.get(url)
                .addParams("params", "value")//参数
                .log("tag")//打印日志的标记，不设置则不打印
                //.cache()//使用缓存,使用这个方法必须配置过全局属性
                //.cache(3600000L)//使用缓存,使用这个方法必须配置过全局属性,独立的缓存时间
                .cache(this, 3600000L)//使用缓存,独立的缓存时间
                //.setConnectTimeout(10000)//独立的连接超时时间
                //.setReadTimeout(15000)//独立的读取超时时间
                .callback(new StringCallback() {

                    @Override
                    public void onAfter(Response<String> response) {
                        Log.i("Response Data:", response.getResponseData());
                    }

                })
                .load();

    }

    public void download(View view) {

        HttpConnector.get("http://pic32.nipic.com/20130829/12906030_124355855000_2.png")
                .log("file")
                .setConnectTimeout(20000)
                .setReadTimeout(30000)
                .callback(new FileCallback(Environment.getExternalStorageDirectory().getPath() + "/test.png") {

                    @Override
                    public void onSuccess(Response<File> response) {
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownProgress(float progress, int fileSize) {
                        Log.e("onDownProgress", "当前进度：" + progress * 100 + "% 总大小为：" + fileSize);
                    }

                })
                .load();

    }

    public void set(View view) {

        HttpConnector.globalConfig(this)
                .setDefaultConnectTimeout(10000)//默认连接超时时间（毫秒），这里是10秒
                .setDefaultReadTimeout(15000)//默认读取超时时间（毫秒），这里是15秒
                .setDefaultCacheTime(3600000L)//默认缓存时间（毫秒），这里是1小时
                //.onlyCache()//只读取缓存，不请求网络
                .config();

    }

    public void clearSome(View view) {
        HttpConnector.clearCache(this, url);//清除指定缓存
    }

    public void clearAll(View view) {
        HttpConnector.clearCache(this);//清除所有缓存
    }

    public void cancel(View view) {
        //待实现
        Toast.makeText(MainActivity.this, "待实现", Toast.LENGTH_SHORT).show();
    }
}

