package com.example.hwg.cniao5shop.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hwg on 2017/2/14.
 */

public class OkHttpHelper {

    private static OkHttpClient okHttpClient ;

    private Gson mGson = new Gson() ;

    private Handler handler ;

    private OkHttpHelper(){

        okHttpClient = new OkHttpClient() ;

        mGson = new Gson() ;

        handler = new Handler(Looper.getMainLooper()) ;
    };

    public static OkHttpHelper getInstance(){


        return new OkHttpHelper() ;
    }

    public void get(String url , BaseCallback callback){

        Request request = buildRequest(url , null , HttpMethodType.GET) ;

        doRequest(request , callback);
    }

    public void post(String url , Map<String , String> params , BaseCallback callback){
        Request request = buildRequest(url , params , HttpMethodType.POST) ;

        doRequest(request , callback);
    }

    //执行Call
    public void doRequest(final Request request , final BaseCallback callback){

        callback.onRequestBefore(request);

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(request , e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                callback.onResponse(response);

                if (response.isSuccessful()){
                    String resultStr = response.body().string() ;
                    if (callback.mType == String.class){
                        callbackSuccess(callback , response , resultStr);
                    }
                    else{
                        try {
                            Object object = mGson.fromJson(resultStr , callback.mType) ;
                            callbackSuccess(callback , response , object);
                        }catch (JsonParseException e){
                            callback.onError(response , response.code() , e);
                        }
                    }
                }
                else {
                    callback.onError(response , response.code() , null);
                }
            }
        });
    }


    private Request buildRequest(String url , Map<String , String> params , HttpMethodType methodType){

        Request.Builder builder = new Request.Builder() ;
        builder.url(url) ;

        if (methodType == HttpMethodType.GET){
            builder.get() ;
        }else if (methodType == HttpMethodType.POST){
            RequestBody body = buildFormDate(params) ;
            builder.post(body) ;
        }

        return builder.build() ;
    }

    //post请求体
    private RequestBody buildFormDate(Map<String , String> params){
        FormBody.Builder builder = new FormBody.Builder() ;

        if (params != null){
            for (Map.Entry<String , String> entry : params.entrySet()){
                builder.add(entry.getKey() , entry.getValue()) ;
            }
        }

        return builder.build() ;
    }


    private void callbackSuccess(final BaseCallback callback , final Response response , final Object object){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response , object);
            }
        });
    }

    private void callbackError(final  BaseCallback callback , final Response response, final Exception e ){

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response,response.code(),e);
            }
        });
    }


    enum HttpMethodType{
        GET,
        POST
    }
}
