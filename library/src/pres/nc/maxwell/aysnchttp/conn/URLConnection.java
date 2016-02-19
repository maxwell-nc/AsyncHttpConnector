package pres.nc.maxwell.aysnchttp.conn;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import pres.nc.maxwell.aysnchttp.domain.ConnectInfo;

/**
 * URL连接管理
 */
public class URLConnection {

	/**
	 * 自动判断HTTP还是HTTPS并获得HttpURLConnection,设置参数
	 * 
	 * @param URL
	 *            地址
	 * @return 如果地址非法则返回null
	 */
	public static HttpURLConnection getURLConnection(ConnectInfo connInfo)
			throws MalformedURLException, IOException {

		String URL = connInfo.URL;

		int type = URLParser.parseType(URL);

		// 非法类型
		if (type == URLParser.TYPE_INVALID) {
			return null;
		}

		if (connInfo.params != null) {
			// GET方法时合并地址和参数
			URL = URLParser.mergeParams(connInfo);
		}

		HttpURLConnection connection = null;

		switch (type) {
			case URLParser.TYPE_HTTP :// 创建HttpURLConnection

				connection = (HttpURLConnection) new URL(URL).openConnection();

				break;
			case URLParser.TYPE_HTTPS :// 创建HttpsURLConnection

				// 信任所有HTTPS连接
				HttpsURLConnection
						.setDefaultHostnameVerifier(new HostnameVerifier() {
							public boolean verify(String string, SSLSession ssls) {
								return true;
							}
						});

				connection = (HttpsURLConnection) new URL(URL).openConnection();
				break;
		}

		// 连接超时时间
		connection.setConnectTimeout(connInfo.connectTimeout);

		// 读取超时时间
		connection.setReadTimeout(connInfo.readTimeout);

		// User-Agent
		connection.setRequestProperty("User-Agent", connInfo.userAgent);

		// 请求方法
		connection.setRequestMethod(connInfo.requestMethod);

		// 处理POST方法的参数
		if (connInfo.requestMethod.equals(ConnectInfo.METHOD_POST) && connInfo.params != null) {
			String paramsString = URLParser.getParamsString(connInfo);
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					String.valueOf(paramsString.length()));

			connection.setDoOutput(true);

			// 提交参数
			OutputStream os = connection.getOutputStream();
			os.write(paramsString.getBytes());
		}

		return connection;

	}

}
