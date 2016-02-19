# 说明
AsyncHttpConnector是一个异步HTTP连接库，使用起来非常简单，只需要一两行代码就可以完成一个请求操作。

##特点
- 支持HTTP和HTTPS
- 支持GET和POST
- 异步处理，回调操作运行在主线程

# 快速使用
以GET请求为例子，带参数的HTTPS请求：

```java
//参数
HashMap<String, String> params = new HashMap<String, String>();
params.put("params", "value");

//发送GET请求
HttpConnector.easyGet("https://www.baidu.com", null,
		new ResponseHandler() {

			@Override
			public void onSuccess(byte[] data) {
				System.out.println(new String(data));
			}

			@Override
			public void onFailure(int statusCode) {
				System.out.println("error,status code:" + statusCode);
			}
			
	});
```
**更多例子可以参考Sample部分**