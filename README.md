# OkHttpPlus

A extention for okhttp

# Feature

1、Pure OkHttp code

2、support json with gson（you can replace it with other json parser）

3、support callback with onStart() and onFinish()

# Usage
```java
OkHttpUtil.enqueue(new SimpleRequest(SimpleRequest.METHOD_GET, "http://httpbin.org/get", null), new TextCallback() {

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
```

-----
Blog:http://blog.csdn.net/masonblog

Email:MasonLiuChn@gmail.com
