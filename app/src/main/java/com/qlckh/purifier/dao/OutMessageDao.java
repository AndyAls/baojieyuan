package com.qlckh.purifier.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/22 21:07
 * Desc:
 */
public class OutMessageDao {

    /**
     * status : 1
     * msg : 查询成功
     * total : 2
     * data : [{"content":"<p>这里是内容<br/><\/p>","id":4268,"usertel":"15355859616","newsid":258,"createtime":"2018-05-30 14:39:11","isread":1,"readtime":"2018-05-31 12:44:24","newstitle":"标题","newsadduser":"admin","status":0,"newstype":1},{"content":"<p>这里是内容<br/><\/p>","id":4261,"usertel":"15355859616","newsid":258,"createtime":"2018-05-30 14:39:11","isread":1,"readtime":"2018-05-31 12:02:53","newstitle":"标题","newsadduser":"admin","status":0,"newstype":1}]
     * page : 20
     * pagesize : 0
     */

    private int status;
    private String msg;
    private int total;
    private String page;
    private String pagesize;
    private List<OutMessage> data;

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

    public List<OutMessage> getData() {
        return data;
    }

    public void setData(List<OutMessage> data) {
        this.data = data;
    }

    public static class OutMessage implements Parcelable {
        /**
         * content : <p>这里是内容<br/></p>
         * id : 4268
         * usertel : 15355859616
         * newsid : 258
         * createtime : 2018-05-30 14:39:11
         * isread : 1
         * readtime : 2018-05-31 12:44:24
         * newstitle : 标题
         * newsadduser : admin
         * status : 0
         * newstype : 1
         */

        private String content;
        private int id;
        private String usertel;
        private int newsid;
        private String createtime;
        private int isread;
        private String readtime;
        private String newstitle;
        private String newsadduser;
        private int status;
        private int newstype;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsertel() {
            return usertel;
        }

        public void setUsertel(String usertel) {
            this.usertel = usertel;
        }

        public int getNewsid() {
            return newsid;
        }

        public void setNewsid(int newsid) {
            this.newsid = newsid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getIsread() {
            return isread;
        }

        public void setIsread(int isread) {
            this.isread = isread;
        }

        public String getReadtime() {
            return readtime;
        }

        public void setReadtime(String readtime) {
            this.readtime = readtime;
        }

        public String getNewstitle() {
            return newstitle;
        }

        public void setNewstitle(String newstitle) {
            this.newstitle = newstitle;
        }

        public String getNewsadduser() {
            return newsadduser;
        }

        public void setNewsadduser(String newsadduser) {
            this.newsadduser = newsadduser;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getNewstype() {
            return newstype;
        }

        public void setNewstype(int newstype) {
            this.newstype = newstype;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.content);
            dest.writeInt(this.id);
            dest.writeString(this.usertel);
            dest.writeInt(this.newsid);
            dest.writeString(this.createtime);
            dest.writeInt(this.isread);
            dest.writeString(this.readtime);
            dest.writeString(this.newstitle);
            dest.writeString(this.newsadduser);
            dest.writeInt(this.status);
            dest.writeInt(this.newstype);
        }

        public OutMessage() {
        }

        protected OutMessage(Parcel in) {
            this.content = in.readString();
            this.id = in.readInt();
            this.usertel = in.readString();
            this.newsid = in.readInt();
            this.createtime = in.readString();
            this.isread = in.readInt();
            this.readtime = in.readString();
            this.newstitle = in.readString();
            this.newsadduser = in.readString();
            this.status = in.readInt();
            this.newstype = in.readInt();
        }

        public static final Parcelable.Creator<OutMessage> CREATOR = new Parcelable.Creator<OutMessage>() {
            @Override
            public OutMessage createFromParcel(Parcel source) {
                return new OutMessage(source);
            }

            @Override
            public OutMessage[] newArray(int size) {
                return new OutMessage[size];
            }
        };
    }
}
