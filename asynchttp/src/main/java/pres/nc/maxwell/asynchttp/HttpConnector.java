package pres.nc.maxwell.asynchttp;

import java.util.HashMap;

import pres.nc.maxwell.asynchttp.conn.ConnectTask;
import pres.nc.maxwell.asynchttp.handler.ResultHandler;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.handler.impl.ByteArrayHandler;
import pres.nc.maxwell.asynchttp.thread.ThreadPoolController;

/**
 * HTTP连接器
 */
public class HttpConnector {

    /**
     * 发起请求
     * @param request 请求信息
     * @param resultHandler 结果处理
     */
    public static void request(Request request, ResultHandler resultHandler) {

        if (request.getURL() == null) {
            return;
        }

        ConnectTask connectTask = new ConnectTask(resultHandler);
        connectTask.executeOnExecutor(ThreadPoolController.getThreadPool(), request);
    }

}
