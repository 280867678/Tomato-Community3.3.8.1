package com.gen.p059mh.webapp_extensions.views.camera.smartCamera;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.p002v4.util.SparseArrayCompat;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.AspectRatio */
/* loaded from: classes2.dex */
public class AspectRatio implements Parcelable, Comparable<AspectRatio> {

    /* renamed from: mX */
    private final int f1294mX;

    /* renamed from: mY */
    private final int f1295mY;
    private static final SparseArrayCompat<SparseArrayCompat<AspectRatio>> sCache = new SparseArrayCompat<>(16);
    public static final Parcelable.Creator<AspectRatio> CREATOR = new Parcelable.Creator<AspectRatio>() { // from class: com.gen.mh.webapp_extensions.views.camera.smartCamera.AspectRatio.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public AspectRatio mo6184createFromParcel(Parcel parcel) {
            return AspectRatio.m4118of(parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public AspectRatio[] mo6185newArray(int i) {
            return new AspectRatio[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* renamed from: of */
    public static AspectRatio m4118of(int i, int i2) {
        int gcd = gcd(i, i2);
        int i3 = i / gcd;
        int i4 = i2 / gcd;
        SparseArrayCompat<AspectRatio> sparseArrayCompat = sCache.get(i3);
        if (sparseArrayCompat == null) {
            AspectRatio aspectRatio = new AspectRatio(i3, i4);
            SparseArrayCompat<AspectRatio> sparseArrayCompat2 = new SparseArrayCompat<>();
            sparseArrayCompat2.put(i4, aspectRatio);
            sCache.put(i3, sparseArrayCompat2);
            return aspectRatio;
        }
        AspectRatio aspectRatio2 = sparseArrayCompat.get(i4);
        if (aspectRatio2 != null) {
            return aspectRatio2;
        }
        AspectRatio aspectRatio3 = new AspectRatio(i3, i4);
        sparseArrayCompat.put(i4, aspectRatio3);
        return aspectRatio3;
    }

    public static AspectRatio parse(String str) {
        int indexOf = str.indexOf(58);
        if (indexOf == -1) {
            throw new IllegalArgumentException("Malformed aspect ratio: " + str);
        }
        try {
            return m4118of(Integer.parseInt(str.substring(0, indexOf)), Integer.parseInt(str.substring(indexOf + 1)));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Malformed aspect ratio: " + str, e);
        }
    }

    private AspectRatio(int i, int i2) {
        this.f1294mX = i;
        this.f1295mY = i2;
    }

    public int getX() {
        return this.f1294mX;
    }

    public int getY() {
        return this.f1295mY;
    }

    public boolean matches(Size size) {
        int gcd = gcd(size.getWidth(), size.getHeight());
        return this.f1294mX == size.getWidth() / gcd && this.f1295mY == size.getHeight() / gcd;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AspectRatio)) {
            return false;
        }
        AspectRatio aspectRatio = (AspectRatio) obj;
        return this.f1294mX == aspectRatio.f1294mX && this.f1295mY == aspectRatio.f1295mY;
    }

    public String toString() {
        return this.f1294mX + ":" + this.f1295mY;
    }

    public float toFloat() {
        return this.f1294mX / this.f1295mY;
    }

    public int hashCode() {
        int i = this.f1295mY;
        int i2 = this.f1294mX;
        return i ^ ((i2 >>> 16) | (i2 << 16));
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull AspectRatio aspectRatio) {
        if (equals(aspectRatio)) {
            return 0;
        }
        return toFloat() - aspectRatio.toFloat() > 0.0f ? 1 : -1;
    }

    public AspectRatio inverse() {
        return m4118of(this.f1295mY, this.f1294mX);
    }

    private static int gcd(int i, int i2) {
        while (true) {
            int i3 = i2;
            int i4 = i;
            i = i3;
            if (i != 0) {
                i2 = i4 % i;
            } else {
                return i4;
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1294mX);
        parcel.writeInt(this.f1295mY);
    }
}
