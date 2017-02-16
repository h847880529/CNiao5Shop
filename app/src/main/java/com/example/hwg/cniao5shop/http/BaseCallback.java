package com.example.hwg.cniao5shop.http;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hwg on 2017/2/14.
 */

public abstract class BaseCallback<T> {

    Type mType ;
    //为了把T转换成type，获取到T的对象
    static Type getSuperclassTypeParameter(Class<?> subclass){
        Type superclass = subclass.getGenericSuperclass() ;
        if (superclass instanceof Class){
            throw new RuntimeException("Miss type paramter") ;
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]) ;
    }

    public BaseCallback()
    {
        mType = getSuperclassTypeParameter(getClass());
    }


    public abstract void onRequestBefore(Request request) ;

    public abstract void onFailure(Request request , Exception e) ;

    public abstract void onResponse(Response response);

    public abstract void onSuccess(Response response , T t);

    public abstract void onError(Response response , int code ,  Exception e );

}
