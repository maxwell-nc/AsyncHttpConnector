package pres.nc.maxwell.asynchttp.callback.impl;

import java.io.IOException;
import java.io.InputStream;

import pres.nc.maxwell.asynchttp.callback.ICallback;
import pres.nc.maxwell.asynchttp.io.IOUtils;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 字符串回调
 */
public abstract class StringCallback implements ICallback<String> {

    @Override
    public void onPrepared(Request request) {
    }

    @Override
    public void onSuccess(Response<String> response) {
    }

    @Override
    public void onFailure(Response<String> response) {
    }

    @Override
    public String parseResponseStream(InputStream is) {

        try {
            return new String(IOUtils.inputStream2bytes(is));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String toCache(String data) {
        return data;
    }
}
