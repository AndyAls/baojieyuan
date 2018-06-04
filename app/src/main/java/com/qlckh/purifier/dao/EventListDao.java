package com.qlckh.purifier.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/29 11:50
 * Desc:
 */
public class EventListDao {

    /**
     * status : 1
     * msg : 查询成功
     * total : 3
     * data : [{"id":3,"baojie":77,"jiedao":1,"cunguan":100,"image1":"","sj_time":"","status":0,"image2":"","title":"","content":"","content2":"","sj_time2":""}]
     * page : 2
     * pagesize : 1
     */

    private int status;
    private String msg;
    private int total;
    private String page;
    private String pagesize;
    private List<EventDao> data;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public List<EventDao> getData() {
        return data;
    }

    public void setData(List<EventDao> data) {
        this.data = data;
    }

    public static class EventDao implements Parcelable {
        /**
         * id : 3
         * baojie : 77
         * jiedao : 1
         * cunguan : 100
         * image1 :
         * sj_time :
         * status : 0
         * image2 :
         * title :
         * content :
         * content2 :
         * sj_time2 :
         */

        private int id;
        private int baojie;
        private int jiedao;
        private int cunguan;
        private String image1;
        private String sj_time;
        private int status;
        private String image2;
        private String title;
        private String content;
        private String content2;
        private String sj_time2;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBaojie() {
            return baojie;
        }

        public void setBaojie(int baojie) {
            this.baojie = baojie;
        }

        public int getJiedao() {
            return jiedao;
        }

        public void setJiedao(int jiedao) {
            this.jiedao = jiedao;
        }

        public int getCunguan() {
            return cunguan;
        }

        public void setCunguan(int cunguan) {
            this.cunguan = cunguan;
        }

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getSj_time() {
            return sj_time;
        }

        public void setSj_time(String sj_time) {
            this.sj_time = sj_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent2() {
            return content2;
        }

        public void setContent2(String content2) {
            this.content2 = content2;
        }

        public String getSj_time2() {
            return sj_time2;
        }

        public void setSj_time2(String sj_time2) {
            this.sj_time2 = sj_time2;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.baojie);
            dest.writeInt(this.jiedao);
            dest.writeInt(this.cunguan);
            dest.writeString(this.image1);
            dest.writeString(this.sj_time);
            dest.writeInt(this.status);
            dest.writeString(this.image2);
            dest.writeString(this.title);
            dest.writeString(this.content);
            dest.writeString(this.content2);
            dest.writeString(this.sj_time2);
        }

        public EventDao() {
        }

        protected EventDao(Parcel in) {
            this.id = in.readInt();
            this.baojie = in.readInt();
            this.jiedao = in.readInt();
            this.cunguan = in.readInt();
            this.image1 = in.readString();
            this.sj_time = in.readString();
            this.status = in.readInt();
            this.image2 = in.readString();
            this.title = in.readString();
            this.content = in.readString();
            this.content2 = in.readString();
            this.sj_time2 = in.readString();
        }

        public static final Parcelable.Creator<EventDao> CREATOR = new Parcelable.Creator<EventDao>() {
            @Override
            public EventDao createFromParcel(Parcel source) {
                return new EventDao(source);
            }

            @Override
            public EventDao[] newArray(int size) {
                return new EventDao[size];
            }
        };
    }
}

