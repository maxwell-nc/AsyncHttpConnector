package pres.nc.maxwell.aysnchttp.conn;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import pres.nc.maxwell.aysnchttp.domain.ConnectInfo;

public class URLParser {

	/**
	 * 非法地址
	 */
	public static final int TYPE_INVALID = 0;

	/**
	 * HTTP类型地址
	 */
	public static final int TYPE_HTTP = 1;

	/**
	 * HTTPS类型地址
	 */
	public static final int TYPE_HTTPS = 2;

	/**
	 * 解析地址类型
	 * 
	 * @param URL
	 *            地址
	 * @return 地址类型
	 */
	public static int parseType(String URL) {

		// 默认地址不合法
		int type = TYPE_INVALID;

		// HTTPS类型
		if (URL.startsWith("https://")) {
			type = TYPE_HTTPS;
		}

		// HTTP类型
		else if (URL.startsWith("http://")) {
			type = TYPE_HTTP;
		}

		// HTTP类型，补全http://
		else if (URL.startsWith("www.")) {
			type = TYPE_HTTP;
			URL = "http://" + URL;
		}

		return type;
	}

	/**
	 * 获得参数字符串
	 * 
	 * @param connInfo
	 *            连接信息
	 * @return 参数字符串
	 */
	public static String getParamsString(ConnectInfo connInfo) {

		String paramsString = "";

		HashMap<String, String> params = connInfo.params;

		for (Entry<String, String> entry : params.entrySet()) {

			try {
				paramsString += entry.getKey() + "="
						+ URLEncoder.encode(entry.getValue(), "UTF-8") + "&";

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}

		// 去掉最后一个&
		paramsString = paramsString.substring(0, paramsString.length() - 1);

		return paramsString;
	}

	/**
	 * 合并地址和参数，GET请求时才有效
	 * 
	 * @param connInfo
	 *            连接信息
	 * @return 合并后的地址
	 */
	public static String mergeParams(ConnectInfo connInfo) {

		String URL = connInfo.URL;

		// 只有GET才拼写地址
		if (connInfo.requestMethod.equals(ConnectInfo.METHOD_GET)) {
			URL = URL + "?" + getParamsString(connInfo);
		}

		return URL;
	}

}
