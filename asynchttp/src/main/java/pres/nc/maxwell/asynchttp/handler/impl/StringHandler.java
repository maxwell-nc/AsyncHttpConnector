package pres.nc.maxwell.asynchttp.handler.impl;

import java.io.IOException;
import java.io.InputStream;

import pres.nc.maxwell.asynchttp.handler.ResultHandler;
import pres.nc.maxwell.asynchttp.io.IOUtils;

/**
 * 字符串结果处理器
 */
public abstract  class StringHandler implements ResultHandler<String> {

    public String parseResponseStream(InputStream is) {

        try {
            return new String(IOUtils.inputstream2bytes(is));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
