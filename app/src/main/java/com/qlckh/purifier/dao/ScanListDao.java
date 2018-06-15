package com.qlckh.purifier.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/6/12 13:07
 * Desc:
 */
public class ScanListDao {

    private String msg;
    private int status;
    private List<ScanList> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ScanList> getData() {
        return data;
    }

    public void setData(List<ScanList> data) {
        this.data = data;
    }

    public static class ScanList {

        private String username;
        private int ws;
        private int yisao;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getWs() {
            return ws;
        }

        public void setWs(int ws) {
            this.ws = ws;
        }

        public int getYisao() {
            return yisao;
        }

        public void setYisao(int yisao) {
            this.yisao = yisao;
        }
    }
}
