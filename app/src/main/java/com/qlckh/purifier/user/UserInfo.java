package com.qlckh.purifier.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.qlckh.purifier.dao.BaseDo;

/**
 * @author Andy
 * @date 2018/5/15 14:45
 * Desc: 登录用户信息
 */
public class UserInfo implements Parcelable {


    private int id;
    private String fullname;
    private int topflag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.fullname);
        dest.writeInt(this.topflag);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.id = in.readInt();
        this.fullname = in.readString();
        this.topflag = in.readInt();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", topflag=" + topflag +
                '}';
    }

}
