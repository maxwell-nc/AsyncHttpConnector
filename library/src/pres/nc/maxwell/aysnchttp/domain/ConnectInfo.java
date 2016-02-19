package pres.nc.maxwell.aysnchttp.domain;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 连接信息
 */
public class ConnectInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认的User-Agent
	 */
	public static final String USERAGENT_DEFALUT = "Mozilla/5.0 (Linux; U; Android ;) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

	/**
	 * GET请求
	 */
	public static final String METHOD_GET = "GET";

	/**
	 * POST请求
	 */
	public static final String METHOD_POST = "POST";

	/**
	 * 网址
	 */
	public String URL;

	/**
	 * 参数
	 */
	public HashMap<String, String> params;
	
	/**
	 * 请求方法，默认为GET
	 * 
	 * @see #METHOD_GET
	 * @see #METHOD_POST
	 */
	public String requestMethod = METHOD_GET;

	/**
	 * 连接超时时间,默认为2000ms
	 */
	public int connectTimeout = 2000;

	/**
	 * 读取超时时间,默认为10000ms
	 */
	public int readTimeout = 10000;

	/**
	 * 用户代理
	 * 
	 * @see #USERAGENT_DEFALUT
	 */
	public String userAgent = USERAGENT_DEFALUT;

	/**
	 * 创建连接信息
	 * @param URL 地址
	 * @param params 参数
	 */
	public ConnectInfo(String URL,HashMap<String, String> params){
		this.URL = URL;
		this.params = params;
	}
}
