package android.databinding;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class ObservableParcelable<T extends Parcelable> extends ObservableField<T> implements Parcelable, Serializable {
    public static final Parcelable.Creator<ObservableParcelable> CREATOR = new Parcelable.Creator<ObservableParcelable>() { // from class: android.databinding.ObservableParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public ObservableParcelable mo5611createFromParcel(Parcel parcel) {
            return new ObservableParcelable(parcel.readParcelable(C00161.class.getClassLoader()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public ObservableParcelable[] mo5612newArray(int i) {
            return new ObservableParcelable[i];
        }
    };
    static final long serialVersionUID = 1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ObservableParcelable(T t) {
        super(t);
    }

    public ObservableParcelable() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable((Parcelable) get(), 0);
    }
}
