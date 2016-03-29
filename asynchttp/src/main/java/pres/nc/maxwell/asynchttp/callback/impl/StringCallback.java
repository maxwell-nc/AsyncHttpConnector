package pres.nc.maxwell.asynchttp.callback.impl;

import java.io.IOException;
import java.io.InputStream;

import pres.nc.maxwell.asynchttp.callback.ResultCallback;
import pres.nc.maxwell.asynchttp.io.IOUtils;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 字符串回调
 */
public abstract  class StringCallback implements ResultCallback<String> {

    @Override
    public void onPrepared(Request request) {}

    @Override
    public void onSuccess(Response<String> response) {}

    @Override
    public void onFailure(Response<String> response) {}

    public String parseResponseStream(InputStream is) {

        try {
            return new String(IOUtils.inputstream2bytes(is));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
