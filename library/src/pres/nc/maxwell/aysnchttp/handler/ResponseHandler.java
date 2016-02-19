package pres.nc.maxwell.aysnchttp.handler;

import java.io.IOException;
import java.io.InputStream;

import pres.nc.maxwell.aysnchttp.io.IOUtils;

/**
 * 响应处理器
 */
public abstract class ResponseHandler {

	/**
	 * 成功时操作，主线程
	 * 
	 * @param data
	 *            数据
	 */
	public abstract void onSuccess(byte[] data);

	/**
	 * 失败时操作，主线程
	 * 
	 * @param statusCode
	 *            状态码
	 */
	public abstract void onFailure(int statusCode);

	/**
	 * 处理响应输入流，子线程
	 * 
	 * @param is
	 *            输入流
	 * @return byte数组
	 */
	public byte[] dealResponseStream(InputStream is) {

		try {
			return IOUtils.inputstream2bytes(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
