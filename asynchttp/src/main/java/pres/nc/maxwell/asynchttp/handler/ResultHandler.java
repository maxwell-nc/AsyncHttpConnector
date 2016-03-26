package pres.nc.maxwell.asynchttp.handler;

import java.io.InputStream;

import pres.nc.maxwell.asynchttp.response.Response;

/**
 * Response接口模型
 */
public interface ResultHandler<T> {

    void onPrepared();

    //主线程
    void onSuccess(Response<T> response);

    //失败时操作，主线程
    void onFailure(Response<T> response);

    /**
     * 解析响应输入流，子线程
     *
     * @param is 输入流
     * @return 特定类型数据
     */
    T parseResponseStream(InputStream is);
}
