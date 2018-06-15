package com.qlckh.purifier.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/6/12 13:05
 * Desc:
 */
public class CunListDao {

    private int status;
    private String msg;
    private List<Cundao> data;

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

    public List<Cundao> getData() {
        return data;
    }

    public void setData(List<Cundao> data) {
        this.data = data;
    }

    public static class Cundao {

        private int id;
        private String name;
        private int classid;
        private int sort;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getClassid() {
            return classid;
        }

        public void setClassid(int classid) {
            this.classid = classid;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
