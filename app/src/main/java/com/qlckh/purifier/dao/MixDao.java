package com.qlckh.purifier.dao;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author Andy
 * @date 2018/6/1 15:10
 * Desc:
 */

public class MixDao implements Parcelable,Comparable<MixDao>{

    private int id;
    private String address;
    private String tel;
    private String fullName;
    private String adduser;
    private int topFlag;
    private boolean isSelect;
    private int itemType;
    private boolean isHeader;
    private String headName;

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public static Creator<MixDao> getCREATOR() {
        return CREATOR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public int getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(int topFlag) {
        this.topFlag = topFlag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.address);
        dest.writeString(this.tel);
        dest.writeString(this.fullName);
        dest.writeString(this.adduser);
        dest.writeInt(this.topFlag);
    }

    public MixDao() {
    }

    protected MixDao(Parcel in) {
        this.id = in.readInt();
        this.address = in.readString();
        this.tel = in.readString();
        this.fullName = in.readString();
        this.adduser = in.readString();
        this.topFlag = in.readInt();
    }

    public static final Parcelable.Creator<MixDao> CREATOR = new Parcelable.Creator<MixDao>() {
        @Override
        public MixDao createFromParcel(Parcel source) {
            return new MixDao(source);
        }

        @Override
        public MixDao[] newArray(int size) {
            return new MixDao[size];
        }
    };

    @Override
    public int compareTo(@NonNull MixDao o) {
        return this.getItemType()-o.getItemType();
    }
}
