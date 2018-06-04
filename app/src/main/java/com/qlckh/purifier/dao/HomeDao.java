package com.qlckh.purifier.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Andy
 * @date 2018/5/19 19:18
 * Desc:
 */
public class HomeDao implements Parcelable {

    /**
     * caiid : 14
     * caiuser : 王菊莲
     * id : 774
     * username : 陈灿军
     * address : 6111
     * sheng : 浙江省
     * shi : 绍兴市
     * xiang : 新昌县
     * cun : 南苑
     * cunid : 35
     */

    private String caiid;
    private String caiuser;
    private String id;
    private String username;
    private String address;
    private String sheng;
    private String shi;
    private String xiang;
    private String cun;
    private String cunid;

    public String getCaiid() {
        return caiid;
    }

    public void setCaiid(String caiid) {
        this.caiid = caiid;
    }

    public String getCaiuser() {
        return caiuser;
    }

    public void setCaiuser(String caiuser) {
        this.caiuser = caiuser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getXiang() {
        return xiang;
    }

    public void setXiang(String xiang) {
        this.xiang = xiang;
    }

    public String getCun() {
        return cun;
    }

    public void setCun(String cun) {
        this.cun = cun;
    }

    public String getCunid() {
        return cunid;
    }

    public void setCunid(String cunid) {
        this.cunid = cunid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.caiid);
        dest.writeString(this.caiuser);
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.address);
        dest.writeString(this.sheng);
        dest.writeString(this.shi);
        dest.writeString(this.xiang);
        dest.writeString(this.cun);
        dest.writeString(this.cunid);
    }

    public HomeDao() {
    }

    protected HomeDao(Parcel in) {
        this.caiid = in.readString();
        this.caiuser = in.readString();
        this.id = in.readString();
        this.username = in.readString();
        this.address = in.readString();
        this.sheng = in.readString();
        this.shi = in.readString();
        this.xiang = in.readString();
        this.cun = in.readString();
        this.cunid = in.readString();
    }

    public static final Parcelable.Creator<HomeDao> CREATOR = new Parcelable.Creator<HomeDao>() {
        @Override
        public HomeDao createFromParcel(Parcel source) {
            return new HomeDao(source);
        }

        @Override
        public HomeDao[] newArray(int size) {
            return new HomeDao[size];
        }
    };
}
