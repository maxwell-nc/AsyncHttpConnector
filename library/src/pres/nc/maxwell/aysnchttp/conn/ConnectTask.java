package pres.nc.maxwell.aysnchttp.conn;

import java.io.InputStream;
import java.net.HttpURLConnection;

import pres.nc.maxwell.aysnchttp.domain.ConnectInfo;
import pres.nc.maxwell.aysnchttp.handler.ResponseHandler;
import android.os.AsyncTask;

/**
 * 连接任务
 */
public class ConnectTask extends AsyncTask<ConnectInfo, Void, Boolean> {

	/**
	 * 结果处理器
	 */
	private ResponseHandler connHandler;
	
	/**
	 * 结果
	 */
	private byte[] responseData;
	
	/**
	 * 状态码
	 */
	private int responseCode;

	/**
	 * 创建连接任务
	 * @param connHandler 结果处理器
	 */
	public ConnectTask(ResponseHandler connHandler) {
		this.connHandler = connHandler;
	}

	@Override
	protected Boolean doInBackground(ConnectInfo... params) {// 子线程

		HttpURLConnection urlConnection = null;

		try {

			urlConnection = URLConnection.getURLConnection(params[0]);

			urlConnection.connect();
			
			responseCode = urlConnection.getResponseCode();
			if (responseCode == 200) {//成功接收

				InputStream inputStream = urlConnection.getInputStream();

				// 调用监听器
				if (connHandler != null) {
					responseData = connHandler.dealResponseStream(inputStream);
				}
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (urlConnection != null) {
				urlConnection.disconnect();// 断开
			}

		}

		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {// 主线程

		if (connHandler != null) {

			if (result.booleanValue()) {// 成功接收
				connHandler.onSuccess(responseData);
			} else {// 接收失败
				connHandler.onFailure(responseCode);
			}

		}

	}

}
