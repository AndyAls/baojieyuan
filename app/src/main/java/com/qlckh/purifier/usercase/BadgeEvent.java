package com.qlckh.purifier.usercase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Andy
 * @date 2018/5/30 11:55
 * Desc:
 */
public class BadgeEvent implements Parcelable {

    private int cunBadge;
    private int caiBadge;

    public int getCunBadge() {
        return cunBadge;
    }

    public void setCunBadge(int cunBadge) {
        this.cunBadge = cunBadge;
    }

    public int getCaiBadge() {
        return caiBadge;
    }

    public void setCaiBadge(int caiBadge) {
        this.caiBadge = caiBadge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cunBadge);
        dest.writeInt(this.caiBadge);
    }

    public BadgeEvent() {
    }

    protected BadgeEvent(Parcel in) {
        this.cunBadge = in.readInt();
        this.caiBadge = in.readInt();
    }

    public static final Parcelable.Creator<BadgeEvent> CREATOR = new Parcelable.Creator<BadgeEvent>() {
        @Override
        public BadgeEvent createFromParcel(Parcel source) {
            return new BadgeEvent(source);
        }

        @Override
        public BadgeEvent[] newArray(int size) {
            return new BadgeEvent[size];
        }
    };
}
