# OkHttpPlus

A extension for okhttp

# Feature

1、Pure OkHttp code

2、support json with gson（you can replace it with other json parser）

3、support callback with onStart() and onFinish()

4、 callback in UIThread

5、 callback can bind activity or fragment 's lifecycle

6、 more simple get or post method

7、auto check network_error and response error

# Usage
```java
      OkHttpUtil.enqueue(new SimpleRequest(SimpleRequest.METHOD_GET, "http://httpbin.org/get", null), 
         new TextCallback(this){

            @Override
            public void onSuccess(Response response,String result) {
                Log.i("MainActivity", result);
            }

            @Override
            public void onFailed(Response response, Exception e) {
                Log.i("MainActivity", response.code() + "");
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }
        });
        
      OkHttpUtil.get("http://httpbin.org/get", null, new TextCallback(this) {
            @Override
            public void onSuccess(Response response, String result) {
                Log.i("MainActivity", result + Thread.currentThread().getId());
            }

            @Override
            public void onFailed(Response response, Exception e) {
                Log.i("MainActivity", response.code() + e.toString());
            }

            @Override
            public void onStart() {
                Log.i("MainActivity", "start:" + Thread.currentThread().getId());
            }

            @Override
            public void onFinish() {

            }
        });        
```

```java
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.MasonLiuChn:OkHttpPlus:342.280.0'
}
```


-----
Blog:http://blog.csdn.net/masonblog

Email:MasonLiuChn@gmail.com
