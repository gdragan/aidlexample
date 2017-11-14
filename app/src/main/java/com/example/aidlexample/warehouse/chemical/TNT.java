package com.example.aidlexample.warehouse.chemical;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.aidlexample.warehouse.ContainerFeature;

public class TNT extends Chemical implements Parcelable {
    public static final Parcelable.Creator<TNT> CREATOR = new Parcelable.Creator<TNT>() {
        @Override
        public TNT createFromParcel(Parcel source) {
            return new TNT(source);
        }

        @Override
        public TNT[] newArray(int size) {
            return new TNT[size];
        }
    };

    public TNT() {
    }

    public TNT(int size, ContainerFeature containerFeature) {
        super(size, containerFeature);
    }

    public TNT(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(volume);
        dest.writeInt(containerFeature == null ? -1 : containerFeature.ordinal());
    }

    public void readFromParcel(Parcel in) {
        volume = in.readInt();
        int tmpContainerFeature = in.readInt();
        containerFeature = tmpContainerFeature == -1 ? null : ContainerFeature.values()[tmpContainerFeature];
    }
}
