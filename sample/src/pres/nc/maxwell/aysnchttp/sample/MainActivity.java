package pres.nc.maxwell.aysnchttp.sample;

import java.util.HashMap;

import pres.nc.maxwell.aysnchttp.HttpConnector;
import pres.nc.maxwell.aysnchttp.handler.ResponseHandler;
import pres.nc.maxwell.aysnchttplibtest.R;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		test();
	}

	public void test() {
		
		//参数
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("params", "value");

		//发送GET请求
		HttpConnector.easyGet("https://www.baidu.com", null,
				new ResponseHandler() {

					@Override
					public void onSuccess(byte[] data) {
						System.out.println(new String(data));
					}

					@Override
					public void onFailure(int statusCode) {
						System.out.println("error,status code:" + statusCode);
					}
					
				});
	}

}
