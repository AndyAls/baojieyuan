package com.qlckh.purifier.dao;

/**
 * @author Andy
 * @date 2018/6/12 9:53
 * Desc:
 */
public class ScanCount {

    private int status;
    private String msg;
    private ScanDao data;

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

    public ScanDao getData() {
        return data;
    }

    public void setData(ScanDao data) {
        this.data = data;
    }

    public static class ScanDao {
        private int yisao;
        private int weisao;

        public int getYisao() {
            return yisao;
        }

        public void setYisao(int yisao) {
            this.yisao = yisao;
        }

        public int getWeisao() {
            return weisao;
        }

        public void setWeisao(int weisao) {
            this.weisao = weisao;
        }
    }
}
