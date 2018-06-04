package com.qlckh.purifier.usercase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Andy
 * @date 2018/6/1 11:04
 * Desc:
 */
public class MessageEvent implements Parcelable {

    private int unRead;

    public int getUnRead() {
        return unRead;
    }

    public void setUnRead(int unRead) {
        this.unRead = unRead;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.unRead);
    }

    public MessageEvent() {
    }

    protected MessageEvent(Parcel in) {
        this.unRead = in.readInt();
    }

    public static final Parcelable.Creator<MessageEvent> CREATOR = new Parcelable.Creator<MessageEvent>() {
        @Override
        public MessageEvent createFromParcel(Parcel source) {
            return new MessageEvent(source);
        }

        @Override
        public MessageEvent[] newArray(int size) {
            return new MessageEvent[size];
        }
    };
}
