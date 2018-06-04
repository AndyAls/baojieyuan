package com.qlckh.purifier.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/15 15:23
 * Desc:
 */
public class BaseListDo<T> {

    private String code;
    private String message;
    private List<T> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
