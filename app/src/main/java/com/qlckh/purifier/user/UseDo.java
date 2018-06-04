package com.qlckh.purifier.user;

/**
 * @author Andy
 * @date 2018/5/16 11:42
 * Desc:
 */
public class UseDo {
    private int status;
    private String msg;
    private UserInfo data;

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

    @Override
    public String toString() {
        return "UseDo{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", info=" + data +
                '}';
    }

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }
}
