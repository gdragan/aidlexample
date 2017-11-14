package com.example.aidlexample.warehouse.chemical;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.aidlexample.warehouse.ContainerFeature;

public class Sand extends Chemical implements Parcelable {
    Sand(int size, ContainerFeature containerFeature) {
        super(size, containerFeature);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.volume);
        dest.writeInt(this.containerFeature == null ? -1 : this.containerFeature.ordinal());
    }

    protected Sand(Parcel in) {
        this.volume = in.readInt();
        int tmpContainerFeature = in.readInt();
        this.containerFeature = tmpContainerFeature == -1 ? null : ContainerFeature.values()[tmpContainerFeature];
    }

    public static final Parcelable.Creator<Sand> CREATOR = new Parcelable.Creator<Sand>() {
        @Override
        public Sand createFromParcel(Parcel source) {
            return new Sand(source);
        }

        @Override
        public Sand[] newArray(int size) {
            return new Sand[size];
        }
    };
}
