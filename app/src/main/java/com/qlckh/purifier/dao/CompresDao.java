package com.qlckh.purifier.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Andy
 * @date 2018/5/26 12:06
 * Desc:
 */
public class CompresDao implements Parcelable {


    private String imgPaths;

    public String getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(String imgPaths) {
        this.imgPaths = imgPaths;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public static Creator<CompresDao> getCREATOR() {
        return CREATOR;
    }

    private boolean isDone;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgPaths);
        dest.writeByte(this.isDone ? (byte) 1 : (byte) 0);
    }

    public CompresDao() {
    }

    protected CompresDao(Parcel in) {
        this.imgPaths = in.readString();
        this.isDone = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CompresDao> CREATOR = new Parcelable.Creator<CompresDao>() {
        @Override
        public CompresDao createFromParcel(Parcel source) {
            return new CompresDao(source);
        }

        @Override
        public CompresDao[] newArray(int size) {
            return new CompresDao[size];
        }
    };
}
