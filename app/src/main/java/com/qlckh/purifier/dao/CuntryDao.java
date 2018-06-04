package com.qlckh.purifier.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andy
 * @date 2018/5/26 13:04
 * Desc:
 */
public class CuntryDao implements Parcelable {
    /**
     * status : 1
     * name : [{"id":34,"name":"青山绿景苑","classid":33,"sort":0},{"id":35,"name":"南苑","classid":33,"sort":0},{"id":36,"name":"新岩村","classid":33,"sort":0},{"id":37,"name":"孟家塘","classid":33,"sort":0},{"id":38,"name":"大联","classid":33,"sort":0},{"id":39,"name":"枫家潭","classid":33,"sort":0},{"id":40,"name":"芦士","classid":33,"sort":0}]
     */

    private int status;
    private List<Cuntry> name;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Cuntry> getName() {
        return name;
    }

    public void setName(List<Cuntry> name) {
        this.name = name;
    }

    public static class Cuntry implements Parcelable {
        /**
         * id : 34
         * name : 青山绿景苑
         * classid : 33
         * sort : 0
         */

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeInt(this.classid);
            dest.writeInt(this.sort);
        }

        public Cuntry() {
        }

        protected Cuntry(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.classid = in.readInt();
            this.sort = in.readInt();
        }

        public static final Creator<Cuntry> CREATOR = new Creator<Cuntry>() {
            @Override
            public Cuntry createFromParcel(Parcel source) {
                return new Cuntry(source);
            }

            @Override
            public Cuntry[] newArray(int size) {
                return new Cuntry[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeList(this.name);
    }

    public CuntryDao() {
    }

    protected CuntryDao(Parcel in) {
        this.status = in.readInt();
        this.name = new ArrayList<Cuntry>();
        in.readList(this.name, Cuntry.class.getClassLoader());
    }

    public static final Parcelable.Creator<CuntryDao> CREATOR = new Parcelable.Creator<CuntryDao>() {
        @Override
        public CuntryDao createFromParcel(Parcel source) {
            return new CuntryDao(source);
        }

        @Override
        public CuntryDao[] newArray(int size) {
            return new CuntryDao[size];
        }
    };
}
