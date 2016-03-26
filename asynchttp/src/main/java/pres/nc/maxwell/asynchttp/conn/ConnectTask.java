package pres.nc.maxwell.asynchttp.conn;

import java.io.InputStream;
import java.net.HttpURLConnection;

import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;
import pres.nc.maxwell.asynchttp.handler.ResultHandler;

import android.os.AsyncTask;

/**
 * 连接任务
 */
public class ConnectTask extends AsyncTask<Request, Void, Boolean> {

    /**
     * 结果处理器
     */
    private ResultHandler resultHandler;

    /**
     * 响应信息
     */
    private Response response = new Response();

    /**
     * 创建连接任务
     *
     * @param connHandler 结果处理器
     */
    public ConnectTask(ResultHandler connHandler) {
        this.resultHandler = connHandler;
    }

    @Override
    protected void onPreExecute() {//主线程
        resultHandler.onPrepared();
    }

    @Override
    protected Boolean doInBackground(Request... params) {// 子线程

        HttpURLConnection urlConnection = null;

        try {

            urlConnection = URLConnection.getURLConnection(params[0]);

            if (urlConnection == null) {
                return false;
            }

            urlConnection.connect();

            response.setResponseCode(urlConnection.getResponseCode());
            if (response.getResponseCode() == 200) {//成功接收

                InputStream inputStream = urlConnection.getInputStream();

                // 调用监听器
                if (resultHandler != null) {
                    response.setResponseMsg(urlConnection.getResponseMessage());
                    response.setResponseData(resultHandler.parseResponseStream(inputStream));
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

        if (resultHandler != null) {

            if (result) {// 成功接收
                resultHandler.onSuccess(response);
            } else {// 接收失败
                resultHandler.onFailure(response);
            }

        }

    }

}
