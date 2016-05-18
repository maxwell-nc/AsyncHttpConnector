package pres.nc.maxwell.asynchttp.callback.impl;

import java.io.IOException;
import java.io.InputStream;

import pres.nc.maxwell.asynchttp.callback.ICallback;
import pres.nc.maxwell.asynchttp.io.IOUtils;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 字节数组回调
 */
public abstract class ByteArrayCallback implements ICallback<byte[]> {

    @Override
    public void onPrepared(Request request) {
    }

    @Override
    public void onSuccess(Response<byte[]> response) {
    }

    @Override
    public void onFailure(Response<byte[]> response) {
    }

    public byte[] parseResponseStream(InputStream is) throws IOException {
        return IOUtils.inputStream2bytes(is);
    }

    @Override
    public String toCache(byte[] data) {
        return new String(data);
    }
}
