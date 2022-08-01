package com.hancher.common.net.bean;

import java.io.Serializable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/6 14:19 <br/>
 * 描述 : http json 转换
 */
public class ResultBean<T> implements IResult, Serializable {
    private int code;
    private T data;
    private String message;

    @Override
    public boolean isOK() {
        return code == 200 || code == 201;
    }

    @Override
    public boolean isEmpty(){
        return data == null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
