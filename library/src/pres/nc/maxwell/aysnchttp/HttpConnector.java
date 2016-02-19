package pres.nc.maxwell.aysnchttp;

import java.util.HashMap;

import pres.nc.maxwell.aysnchttp.conn.ConnectTask;
import pres.nc.maxwell.aysnchttp.domain.ConnectInfo;
import pres.nc.maxwell.aysnchttp.handler.ResponseHandler;
import pres.nc.maxwell.aysnchttp.thread.ThreadPoolController;

/**
 * HTTP连接器
 */
public class HttpConnector {

	/**
	 * 快捷GET请求
	 * @param URL 请求地址
	 * @param params 请求参数
	 * @param connHandler 结果处理器
	 */
	public static void easyGet(String URL,HashMap<String, String> params,ResponseHandler connHandler){
		
		ConnectInfo connInfo = new ConnectInfo(URL, params);
		//connInfo.requestMethod = ConnectInfo.METHOD_GET;//默认无需设置
		
		ConnectTask connectTask = new ConnectTask(connHandler);
		connectTask.executeOnExecutor(ThreadPoolController.getThreadPool(), connInfo);
	}
	
	/**
	 * 快捷POST请求
	 * @param URL 请求地址
	 * @param params 请求参数
	 * @param connHandler 结果处理器
	 */
	public static void easyPost(String URL,HashMap<String, String> params,ResponseHandler connHandler){
		
		ConnectInfo connInfo = new ConnectInfo(URL, params);
		connInfo.requestMethod = ConnectInfo.METHOD_POST;
		
		ConnectTask connectTask = new ConnectTask(connHandler);
		connectTask.executeOnExecutor(ThreadPoolController.getThreadPool(), connInfo);
	}
	
}
