package com.qlckh.purifier.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Andy
 * @date 2018/5/16 11:42
 * Desc:
 */
public class UseDo implements Parcelable {
    private int status;
    private String msg;
    private UserInfo data;

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

    @Override
    public String toString() {
        return "UseDo{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", info=" + data +
                '}';
    }

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.msg);
        dest.writeParcelable(this.data, flags);
    }

    public UseDo() {
    }

    protected UseDo(Parcel in) {
        this.status = in.readInt();
        this.msg = in.readString();
        this.data = in.readParcelable(UserInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<UseDo> CREATOR = new Parcelable.Creator<UseDo>() {
        @Override
        public UseDo createFromParcel(Parcel source) {
            return new UseDo(source);
        }

        @Override
        public UseDo[] newArray(int size) {
            return new UseDo[size];
        }
    };
}
