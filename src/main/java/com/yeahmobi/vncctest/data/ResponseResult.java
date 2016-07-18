package com.yeahmobi.vncctest.data;

/**
 * Created by steven.liu on 2016/6/23.
 */
public class ResponseResult<T> {

    public String result;
    public T data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
