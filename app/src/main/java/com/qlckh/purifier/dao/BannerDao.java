package com.qlckh.purifier.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/17 11:47
 * Desc:
 */
public class BannerDao {


    private int status;
    private String msg;
    private List<ImageDao> data;

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

    public List<ImageDao> getData() {
        return data;
    }

    public void setData(List<ImageDao> data) {
        this.data = data;
    }

    public static class ImageDao {

        private int id;
        private String title;
        private String links;
        private int flag;
        private int classid;
        private String adduser;
        private String imgpath;
        private String puttime;
        private String classname;
        private int topid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLinks() {
            return links;
        }

        public void setLinks(String links) {
            this.links = links;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getClassid() {
            return classid;
        }

        public void setClassid(int classid) {
            this.classid = classid;
        }

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public String getPuttime() {
            return puttime;
        }

        public void setPuttime(String puttime) {
            this.puttime = puttime;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public int getTopid() {
            return topid;
        }

        public void setTopid(int topid) {
            this.topid = topid;
        }
    }
}
