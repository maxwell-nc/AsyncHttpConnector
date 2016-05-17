package pres.nc.maxwell.asynchttp;

import android.content.Context;

import java.util.HashMap;

import pres.nc.maxwell.asynchttp.callback.ICallback;
import pres.nc.maxwell.asynchttp.conn.ConnectTask;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.thread.ThreadPoolController;

/**
 * HTTP连接器
 */
public class HttpConnector {

    /**
     * 一小时
     */
    public static final long CACHE_TIME_AN_HOUR = 3600000L;

    /**
     * 构建器
     */
    private Builder mBuilder;

    private HttpConnector(Builder builder) {
        mBuilder = builder;
    }

    /**
     * 执行请求操作
     */
    public void execute() {
        if (mBuilder.request.getURL() == null) {
            return;
        }

        ConnectTask connectTask = new ConnectTask(mBuilder.resultCallback, mBuilder.request, mBuilder.logTag, mBuilder.isCache, mBuilder.context, mBuilder.cacheTime);
        connectTask.executeOnExecutor(ThreadPoolController.getThreadPool());
    }

    /**
     * 发起GET请求
     *
     * @param url 请求地址
     * @return 构建器
     */
    public static Builder get(String url) {
        return new Builder(Request.createGetRequest(url));
    }

    /**
     * 发起POST请求
     *
     * @param url 请求地址
     * @return 构建器
     */
    public static Builder post(String url) {
        return new Builder(Request.createPostRequest(url));
    }

    /**
     * 构建器
     */
    public static class Builder {

        /**
         * 日志标记
         */
        String logTag;

        /**
         * 结果回调
         */
        ICallback resultCallback;

        /**
         * 请求信息
         */
        Request request;

        /**
         * 构建生成的
         */
        HttpConnector httpConnector;

        /**
         * 是否使用缓存
         */
        boolean isCache = false;

        /**
         * 缓存时间，毫秒
         */
        long cacheTime;

        /**
         * 上下文，用于获取缓存目录
         */
        Context context;//TODO:想办法取缔

        Builder(Request request) {
            this.request = request;
        }

        /**
         * 设置参数Map，注意此方法必须在{@link #addParams(String, Object)}之前调用，否则会丢失参数
         *
         * @param params
         * @return
         */
        public Builder setParams(HashMap<String, Object> params) {
            if (params == null)
                return this;

            request.setParams(params);
            return this;
        }

        /**
         * 添加参数
         *
         * @param key   参数名
         * @param value 参数值
         * @return 构建器
         */
        public Builder addParams(String key, Object value) {
            HashMap<String, Object> params = request.getParams();
            if (params == null) {
                params = new HashMap<>();
            }
            params.put(key, value);
            request.setParams(params);//记得设置回去
            return this;
        }

        /**
         * 设置日志信息标记，不设置则不打印日志
         *
         * @param tag 日志标记
         * @return 构建器
         */
        public Builder log(String tag) {
            this.logTag = tag;
            return this;
        }

        /**
         * 设置回调
         *
         * @param resultCallback 结果回调
         * @return 构建器
         */
        public Builder callback(ICallback resultCallback) {
            this.resultCallback = resultCallback;
            return this;
        }

        /**
         * 使用缓存
         *
         * @param context   上下文
         * @param cacheTime 缓存时间，毫秒
         * @return 构建器
         * @see #CACHE_TIME_AN_HOUR
         */
        public Builder cache(Context context, long cacheTime) {
            this.context = context;
            this.cacheTime = cacheTime;
            this.isCache = true;
            return this;
        }

        /**
         * 构建连接器
         *
         * @return 连接器
         */
        public HttpConnector build() {
            httpConnector = new HttpConnector(this);
            return httpConnector;
        }

        /**
         * 加载
         */
        public void load() {
            if (httpConnector != null) {
                httpConnector.execute();
            } else {
                new HttpConnector(this).execute();
            }
        }

    }

}
