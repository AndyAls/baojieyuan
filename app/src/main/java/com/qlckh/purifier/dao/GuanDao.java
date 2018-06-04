package com.qlckh.purifier.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/28 13:58
 * Desc:
 */
public class GuanDao {

    /**
     * status : 1
     * msg : 查询成功
     * data : [{"id":602,"username":"","company":"南苑","puttime":"2018-05-09 20:59:01","address":"南苑","tel":"13819560726","ucode":null,"imgpath":"","fullname":"杨凯","topflag":4,"adduser":"13605850277","items":"11,31,32,33,35","sex":"男","pwd":"e10adc3949ba59abbe56e057f20f883e","jifen":0,"huiCode":0,"jicode":0,"dangId":0}]
     */

    private int status;
    private String msg;
    private List<Guan> data;

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

    public List<Guan> getData() {
        return data;
    }

    public void setData(List<Guan> data) {
        this.data = data;
    }

    public static class Guan {
        /**
         * id : 602
         * username :
         * company : 南苑
         * puttime : 2018-05-09 20:59:01
         * address : 南苑
         * tel : 13819560726
         * ucode : null
         * imgpath :
         * fullname : 杨凯
         * topflag : 4
         * adduser : 13605850277
         * items : 11,31,32,33,35
         * sex : 男
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * jifen : 0
         * huiCode : 0
         * jicode : 0
         * dangId : 0
         */

        private int id;
        private String username;
        private String company;
        private String puttime;
        private String address;
        private String tel;
        private Object ucode;
        private String imgpath;
        private String fullname;
        private int topflag;
        private String adduser;
        private String items;
        private String sex;
        private String pwd;
        private int jifen;
        private int huiCode;
        private int jicode;
        private int dangId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPuttime() {
            return puttime;
        }

        public void setPuttime(String puttime) {
            this.puttime = puttime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public Object getUcode() {
            return ucode;
        }

        public void setUcode(Object ucode) {
            this.ucode = ucode;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public int getTopflag() {
            return topflag;
        }

        public void setTopflag(int topflag) {
            this.topflag = topflag;
        }

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
        }

        public String getItems() {
            return items;
        }

        public void setItems(String items) {
            this.items = items;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public int getJifen() {
            return jifen;
        }

        public void setJifen(int jifen) {
            this.jifen = jifen;
        }

        public int getHuiCode() {
            return huiCode;
        }

        public void setHuiCode(int huiCode) {
            this.huiCode = huiCode;
        }

        public int getJicode() {
            return jicode;
        }

        public void setJicode(int jicode) {
            this.jicode = jicode;
        }

        public int getDangId() {
            return dangId;
        }

        public void setDangId(int dangId) {
            this.dangId = dangId;
        }
    }
}
