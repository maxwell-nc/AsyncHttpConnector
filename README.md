# 说明
AsyncHttpConnector是一个异步HTTP连接库，使用起来非常简单，只需要一两行代码就可以完成一个请求操作，并且轻松处理结果。

##特点
- 支持HTTP和HTTPS
- 支持GET和POST
- 支持GZip响应，减少流量加速加载
- 支持带进度下载文件回调
- 支持带失效时间控制的三级缓存管理
- 分离式配置设置，全局配置+独立配置
- 异步处理，回调操作运行在主线程
- 链式调用，操作简单

##使用方法


基本使用方法：

```java
HttpConnector.get(url)
        .addParams("params", "value")//参数
        .log("tag")//打印日志的标记，不设置则不打印
        //.cache()//使用缓存,使用这个方法必须配置过全局属性
        //.cache(3600000L)//使用缓存,使用这个方法必须配置过全局属性,独立的缓存时间
        .cache(this, 3600000L)//使用缓存,独立的缓存时间
        //.setConnectTimeout(10000)//独立的连接超时时间
        //.setReadTimeout(15000)//独立的读取超时时间
        .callback(new StringCallback() {

            @Override
            public void onAfter(Response<String> response) {
                Log.i("Response Data:", response.getResponseData());
            }

        })
        .load();
```

下载文件例子：

```java
HttpConnector.get("http://pic32.nipic.com/20130829/12906030_124355855000_2.png")
        .log("file")
        .setConnectTimeout(20000)
        .setReadTimeout(30000)
        .callback(new FileCallback(Environment.getExternalStorageDirectory().getPath() + "/test.png") {
            @Override
            public void onSuccess(Response<File> response) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onDownProgress(float progress, int fileSize) {
                Log.e("onDownProgress", "当前进度：" + progress * 100 + "% 总大小为：" + fileSize);
            }
        })
        .load();

```

全局配置：

```java
HttpConnector.globalConfig(this)
        .setDefaultConnectTimeout(10000)//默认连接超时时间（毫秒），这里是10秒
        .setDefaultReadTimeout(15000)//默认读取超时时间（毫秒），这里是15秒
        .setDefaultCacheTime(3600000L)//默认缓存时间（毫秒），这里是1小时
        //.onlyCache()//只读取缓存，不请求网络
        .config();
```

清除缓存：

```java
HttpConnector.clearCache(this, url);//清除指定缓存
HttpConnector.clearCache(this);//清除所有缓存
```