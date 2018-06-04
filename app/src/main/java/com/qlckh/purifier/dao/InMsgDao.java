package com.qlckh.purifier.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Andy
 * @date 2018/5/22 21:04
 * Desc:
 */
public class InMsgDao {

    /**
     * status : 1
     * msg : 查询成功
     * total : 9
     * data : [{"id":290,"fromid":18,"title":"tksk","content":"dsfsdf","createtime":"1527693302","readtime":"","isread":0,"gotoid":"14"},{"id":287,"fromid":18,"title":"tksk","content":"dsfsdf","createtime":"1527693301","readtime":"","isread":0,"gotoid":"14"},{"id":284,"fromid":18,"title":"tksk","content":"dsfsdf","createtime":"1527693299","readtime":"","isread":0,"gotoid":"14"},{"id":281,"fromid":18,"title":"tksk","content":"dsfsdf","createtime":"1527693297","readtime":"","isread":0,"gotoid":"14"}]
     * page : 4
     * pagesize : 0
     */

    private int status;
    private String msg;
    private int total;
    private String page;
    private String pagesize;
    private List<InMsg> data;

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

    public List<InMsg> getData() {
        return data;
    }

    public void setData(List<InMsg> data) {
        this.data = data;
    }

    public static class InMsg implements Parcelable {
        /**
         * id : 290
         * fromid : 18
         * title : tksk
         * content : dsfsdf
         * createtime : 1527693302
         * readtime :
         * isread : 0
         * gotoid : 14
         */

        private int id;
        private int fromid;
        private String title;
        private String content;
        private String createtime;
        private String readtime;
        private int isread;
        private String gotoid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFromid() {
            return fromid;
        }

        public void setFromid(int fromid) {
            this.fromid = fromid;
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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getReadtime() {
            return readtime;
        }

        public void setReadtime(String readtime) {
            this.readtime = readtime;
        }

        public int getIsread() {
            return isread;
        }

        public void setIsread(int isread) {
            this.isread = isread;
        }

        public String getGotoid() {
            return gotoid;
        }

        public void setGotoid(String gotoid) {
            this.gotoid = gotoid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.fromid);
            dest.writeString(this.title);
            dest.writeString(this.content);
            dest.writeString(this.createtime);
            dest.writeString(this.readtime);
            dest.writeInt(this.isread);
            dest.writeString(this.gotoid);
        }

        public InMsg() {
        }

        protected InMsg(Parcel in) {
            this.id = in.readInt();
            this.fromid = in.readInt();
            this.title = in.readString();
            this.content = in.readString();
            this.createtime = in.readString();
            this.readtime = in.readString();
            this.isread = in.readInt();
            this.gotoid = in.readString();
        }

        public static final Parcelable.Creator<InMsg> CREATOR = new Parcelable.Creator<InMsg>() {
            @Override
            public InMsg createFromParcel(Parcel source) {
                return new InMsg(source);
            }

            @Override
            public InMsg[] newArray(int size) {
                return new InMsg[size];
            }
        };
    }
}
