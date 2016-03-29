package pres.nc.maxwell.asynchttp.callback.impl;

import java.io.IOException;
import java.io.InputStream;

import pres.nc.maxwell.asynchttp.callback.ResultCallback;
import pres.nc.maxwell.asynchttp.io.IOUtils;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 字节数组回调
 */
public abstract class ByteArrayCallback implements ResultCallback<byte[]> {

	@Override
	public void onPrepared(Request request) {}

	@Override
	public void onSuccess(Response<byte[]> response) {}

	@Override
	public void onFailure(Response<byte[]> response) {}

	public byte[] parseResponseStream(InputStream is) {

		try {
			return IOUtils.inputstream2bytes(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
