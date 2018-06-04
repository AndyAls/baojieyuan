package com.qlckh.purifier.dao;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/28 14:15
 * Desc:
 */
public class BaojieDao {
    /**
     * status : 1
     * msg : 查询成功
     * data : [{"id":14,"username":"15355859616","puttime":"2018-05-10 10:30:02","address":"南苑","tel":"15355859616","ucode":"330624195211211143","imgpath":"","fullname":"王菊莲","adduser":"13819560726","sex":"女","pwd":"e10adc3949ba59abbe56e057f20f883e","user_type":0,"district":"南苑","pingallscore":0,"pingusercount":0}]
     */

    private int status;
    private String msg;
    private List<BaoJie> data;

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

    public List<BaoJie> getData() {
        return data;
    }

    public void setData(List<BaoJie> data) {
        this.data = data;
    }

    public static class BaoJie {
        /**
         * id : 14
         * username : 15355859616
         * puttime : 2018-05-10 10:30:02
         * address : 南苑
         * tel : 15355859616
         * ucode : 330624195211211143
         * imgpath :
         * fullname : 王菊莲
         * adduser : 13819560726
         * sex : 女
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * user_type : 0
         * district : 南苑
         * pingallscore : 0
         * pingusercount : 0
         */

        private int id;
        private String username;
        private String puttime;
        private String address;
        private String tel;
        private String ucode;
        private String imgpath;
        private String fullname;
        private String adduser;
        private String sex;
        private String pwd;
        private int user_type;
        private String district;
        private int pingallscore;
        private int pingusercount;

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

        public String getUcode() {
            return ucode;
        }

        public void setUcode(String ucode) {
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

        public String getAdduser() {
            return adduser;
        }

        public void setAdduser(String adduser) {
            this.adduser = adduser;
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

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public int getPingallscore() {
            return pingallscore;
        }

        public void setPingallscore(int pingallscore) {
            this.pingallscore = pingallscore;
        }

        public int getPingusercount() {
            return pingusercount;
        }

        public void setPingusercount(int pingusercount) {
            this.pingusercount = pingusercount;
        }
    }
}
