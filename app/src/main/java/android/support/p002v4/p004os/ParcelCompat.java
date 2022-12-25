package android.support.p002v4.p004os;

import android.os.Parcel;

/* renamed from: android.support.v4.os.ParcelCompat */
/* loaded from: classes2.dex */
public final class ParcelCompat {
    public static boolean readBoolean(Parcel parcel) {
        return parcel.readInt() != 0;
    }

    public static void writeBoolean(Parcel parcel, boolean z) {
        parcel.writeInt(z ? 1 : 0);
    }

    private ParcelCompat() {
    }
}
