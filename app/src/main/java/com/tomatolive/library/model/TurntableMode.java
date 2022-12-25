package com.tomatolive.library.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class TurntableMode implements Parcelable {
    public static final Parcelable.Creator<TurntableMode> CREATOR = new Parcelable.Creator<TurntableMode>() { // from class: com.tomatolive.library.model.TurntableMode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public TurntableMode mo6588createFromParcel(Parcel parcel) {
            return new TurntableMode(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public TurntableMode[] mo6589newArray(int i) {
            return new TurntableMode[i];
        }
    };
    public Integer[] bgColors;
    public List<Bitmap> bitmaps;
    public List<Integer> codes;
    public String[] prizeNames;
    public String[] prizeNum;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TurntableMode() {
    }

    public TurntableMode(Integer[] numArr, String[] strArr, List<Bitmap> list) {
        this.bgColors = numArr;
        this.prizeNames = strArr;
        this.bitmaps = list;
    }

    public String toString() {
        return "TurntableMode{bgColors=" + Arrays.toString(this.bgColors) + ", prizeNames=" + Arrays.toString(this.prizeNames) + ", bitmaps.size()=" + this.bitmaps.size() + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeArray(this.bgColors);
        parcel.writeStringArray(this.prizeNames);
        parcel.writeStringArray(this.prizeNum);
        parcel.writeTypedList(this.bitmaps);
        parcel.writeList(this.codes);
    }

    protected TurntableMode(Parcel parcel) {
        this.bgColors = (Integer[]) parcel.readArray(Integer[].class.getClassLoader());
        this.prizeNames = parcel.createStringArray();
        this.prizeNum = parcel.createStringArray();
        this.bitmaps = parcel.createTypedArrayList(Bitmap.CREATOR);
        this.codes = new ArrayList();
        parcel.readList(this.codes, Integer.class.getClassLoader());
    }
}
