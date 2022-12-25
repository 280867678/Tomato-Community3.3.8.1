package com.tomatolive.library.p136ui.view.widget.guideview;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* renamed from: com.tomatolive.library.ui.view.widget.guideview.Configuration */
/* loaded from: classes4.dex */
public class Configuration implements Parcelable {
    public static final Parcelable.Creator<Configuration> CREATOR = new Parcelable.Creator<Configuration>() { // from class: com.tomatolive.library.ui.view.widget.guideview.Configuration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public Configuration mo6647createFromParcel(Parcel parcel) {
            Configuration configuration = new Configuration();
            configuration.mAlpha = parcel.readInt();
            configuration.mFullingViewId = parcel.readInt();
            configuration.mTargetViewId = parcel.readInt();
            configuration.mFullingColorId = parcel.readInt();
            configuration.mCorner = parcel.readInt();
            configuration.mPadding = parcel.readInt();
            configuration.mPaddingLeft = parcel.readInt();
            configuration.mPaddingTop = parcel.readInt();
            configuration.mPaddingRight = parcel.readInt();
            configuration.mPaddingBottom = parcel.readInt();
            configuration.mGraphStyle = parcel.readInt();
            boolean z = false;
            configuration.mAutoDismiss = parcel.readByte() == 1;
            if (parcel.readByte() == 1) {
                z = true;
            }
            configuration.mOverlayTarget = z;
            return configuration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public Configuration[] mo6648newArray(int i) {
            return new Configuration[i];
        }
    };
    Bitmap mBitmap;
    View mTargetView = null;
    int mPadding = 0;
    int mPaddingLeft = 0;
    int mPaddingTop = 0;
    int mPaddingRight = 0;
    int mPaddingBottom = 0;
    boolean mOutsideTouchable = false;
    int mAlpha = 255;
    int mFullingViewId = -1;
    int mTargetViewId = -1;
    int mCorner = 0;
    int mGraphStyle = 0;
    int mFullingColorId = 17170444;
    boolean mAutoDismiss = true;
    boolean mOverlayTarget = false;
    boolean mShowCloseButton = false;
    int mEnterAnimationId = -1;
    int mExitAnimationId = -1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAlpha);
        parcel.writeInt(this.mFullingViewId);
        parcel.writeInt(this.mTargetViewId);
        parcel.writeInt(this.mFullingColorId);
        parcel.writeInt(this.mCorner);
        parcel.writeInt(this.mPadding);
        parcel.writeInt(this.mPaddingLeft);
        parcel.writeInt(this.mPaddingTop);
        parcel.writeInt(this.mPaddingRight);
        parcel.writeInt(this.mPaddingBottom);
        parcel.writeInt(this.mGraphStyle);
        parcel.writeByte(this.mAutoDismiss ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mOverlayTarget ? (byte) 1 : (byte) 0);
    }
}
