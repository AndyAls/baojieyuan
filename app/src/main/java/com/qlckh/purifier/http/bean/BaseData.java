package com.qlckh.purifier.http.bean;

/**
 * @author Andy
 * @date   2018/5/15 18:40
 * Desc:    BaseData.java
 */
public class BaseData<T> {
    /**
     * 错误码
     */
    private int status;
    /**
     * 错误描述
     */
    private String msg;

    /**
     * 数据
     */

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
