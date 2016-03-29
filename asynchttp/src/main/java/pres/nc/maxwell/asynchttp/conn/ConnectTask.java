package pres.nc.maxwell.asynchttp.conn;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;

import pres.nc.maxwell.asynchttp.callback.ResultCallback;
import pres.nc.maxwell.asynchttp.log.LogUtils;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 连接任务
 */
public class ConnectTask extends AsyncTask<Void, Void, Boolean> {

    /**
     * 日志标记，为null则不打印日志
     */
    private String logTag;

    /**
     * 请求信息
     */
    private final Request request;

    /**
     * 结果处理器
     */
    private ResultCallback resultCallback;

    /**
     * 响应信息
     */
    private Response response = new Response();


    /**
     * 创建连接任务
     *
     * @param resultCallback 结果回调
     * @param request
     * @param logTag
     */
    public ConnectTask(ResultCallback resultCallback, Request request, String logTag) {
        this.resultCallback = resultCallback;
        this.request = request;
        this.logTag = logTag;
    }

    @Override
    protected void onPreExecute() {//主线程

        //TODO:可以取消任务

        if (logTag != null) {
            LogUtils.log()
                    .tag(logTag)
                    .priority(Log.INFO)
                    .addMsg("Request: " + request.toString())
                    .addMsg("RequestMethod: "+ request.getRequestMethod())
                    .build()
                    .execute();
        }

        resultCallback.onPrepared(request);
    }

    @Override
    protected Boolean doInBackground(Void... params) {// 子线程

        HttpURLConnection urlConnection = null;

        try {

            urlConnection = URLConnection.getURLConnection(request);

            if (urlConnection == null) {
                return false;
            }

            urlConnection.connect();

            response.setResponseCode(urlConnection.getResponseCode());
            if (response.getResponseCode() == 200) {//成功接收

                InputStream inputStream = urlConnection.getInputStream();

                // 调用监听器
                if (resultCallback != null) {
                    response.setResponseMsg(urlConnection.getResponseMessage());
                    response.setResponseData(resultCallback.parseResponseStream(inputStream));
                }
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();// 断开
            }

        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {// 主线程

        if (resultCallback != null) {

            if (result) {// 成功接收

                if (logTag != null) {
                    LogUtils.log()
                            .tag(logTag)
                            .priority(Log.INFO)
                            .addMsg("Request Success")
                            .addMsg("Response Message: " + response.getResponseMsg())
                            .build()
                            .execute();
                }
                resultCallback.onSuccess(response);

            } else {// 接收失败

                if (logTag != null) {
                    LogUtils.log()
                            .tag(logTag)
                            .priority(Log.WARN)
                            .addMsg("Request Failed")
                            .addMsg("Error code: " + response.getResponseCode())
                            .addMsg("Error Msg: " + response.getResponseMsg())
                            .build()
                            .execute();
                }
                resultCallback.onFailure(response);
            }

        }

    }

}
