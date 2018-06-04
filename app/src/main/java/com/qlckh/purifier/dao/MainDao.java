package com.qlckh.purifier.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Andy
 * @date 2018/5/17 16:30
 * Desc:
 */
public class MainDao implements Parcelable {
    private String title;
    private int icon;
    private int id;

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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.icon);
        dest.writeInt(this.id);
    }

    public MainDao() {
    }

    private MainDao(Parcel in) {
        this.title = in.readString();
        this.icon = in.readInt();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<MainDao> CREATOR = new Parcelable.Creator<MainDao>() {
        @Override
        public MainDao createFromParcel(Parcel source) {
            return new MainDao(source);
        }

        @Override
        public MainDao[] newArray(int size) {
            return new MainDao[size];
        }
    };

    @Override
    public String toString() {
        return "MainDao{" +
                "title='" + title + '\'' +
                ", icon=" + icon +
                ", id=" + id +
                '}';
    }
}
