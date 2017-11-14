package com.example.aidlexample.warehouse.chemical;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.aidlexample.warehouse.ContainerFeature;

public class Ammonia extends Chemical implements Parcelable {
    Ammonia(int size, ContainerFeature containerFeature) {
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

    protected Ammonia(Parcel in) {
        this.volume = in.readInt();
        int tmpContainerFeature = in.readInt();
        this.containerFeature = tmpContainerFeature == -1 ? null : ContainerFeature.values()[tmpContainerFeature];
    }

    public static final Parcelable.Creator<Ammonia> CREATOR = new Parcelable.Creator<Ammonia>() {
        @Override
        public Ammonia createFromParcel(Parcel source) {
            return new Ammonia(source);
        }

        @Override
        public Ammonia[] newArray(int size) {
            return new Ammonia[size];
        }
    };
}
