package pres.nc.maxwell.asynchttp.handler.impl;

import java.io.IOException;
import java.io.InputStream;

import pres.nc.maxwell.asynchttp.handler.ResultHandler;
import pres.nc.maxwell.asynchttp.io.IOUtils;

/**
 * 字节数组结果处理器
 */
public abstract class ByteArrayHandler implements ResultHandler<byte[]> {

	public byte[] parseResponseStream(InputStream is) {

		try {
			return IOUtils.inputstream2bytes(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
