package com.tomato.ucrop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.tomato.ucrop.model.CutInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class UCropMulti {
    private Intent mCropIntent = new Intent();
    private Bundle mCropOptionsBundle = new Bundle();

    /* renamed from: of */
    public static UCropMulti m243of(@NonNull Uri uri, @NonNull Uri uri2) {
        return new UCropMulti(uri, uri2);
    }

    private UCropMulti(@NonNull Uri uri, @NonNull Uri uri2) {
        this.mCropOptionsBundle.putParcelable("com.one.tomato.ucrop.InputUri", uri);
        this.mCropOptionsBundle.putParcelable("com.one.tomato.ucrop.OutputUri", uri2);
    }

    public UCropMulti withAspectRatio(float f, float f2) {
        this.mCropOptionsBundle.putFloat("com.one.tomato.ucrop.AspectRatioX", f);
        this.mCropOptionsBundle.putFloat("com.one.tomato.ucrop.AspectRatioY", f2);
        return this;
    }

    public UCropMulti withMaxResultSize(@IntRange(from = 100) int i, @IntRange(from = 100) int i2) {
        this.mCropOptionsBundle.putInt("com.one.tomato.ucrop.MaxSizeX", i);
        this.mCropOptionsBundle.putInt("com.one.tomato.ucrop.MaxSizeY", i2);
        return this;
    }

    public UCropMulti withOptions(@NonNull Options options) {
        this.mCropOptionsBundle.putAll(options.getOptionBundle());
        return this;
    }

    public void start(@NonNull Activity activity) {
        start(activity, 609);
    }

    public void start(@NonNull Activity activity, int i) {
        activity.startActivityForResult(getIntent(activity), i);
    }

    public Intent getIntent(@NonNull Context context) {
        this.mCropIntent.setClass(context, PictureMultiCuttingActivity.class);
        this.mCropIntent.putExtras(this.mCropOptionsBundle);
        return this.mCropIntent;
    }

    @Nullable
    public static List<CutInfo> getOutput(@NonNull Intent intent) {
        return (List) intent.getSerializableExtra("com.one.tomato.ucrop.OutputUriList");
    }

    /* loaded from: classes3.dex */
    public static class Options {
        private final Bundle mOptionBundle = new Bundle();

        @NonNull
        public Bundle getOptionBundle() {
            return this.mOptionBundle;
        }

        public void setCompressionQuality(@IntRange(from = 0) int i) {
            this.mOptionBundle.putInt("com.one.tomato.ucrop.CompressionQuality", i);
        }

        public void setCircleDimmedLayer(boolean z) {
            this.mOptionBundle.putBoolean("com.one.tomato.ucrop.CircleDimmedLayer", z);
        }

        public void setShowCropFrame(boolean z) {
            this.mOptionBundle.putBoolean("com.one.tomato.ucrop.ShowCropFrame", z);
        }

        public void setShowCropGrid(boolean z) {
            this.mOptionBundle.putBoolean("com.one.tomato.ucrop.ShowCropGrid", z);
        }

        public void setScaleEnabled(boolean z) {
            this.mOptionBundle.putBoolean("com.one.tomato.ucrop.scale", z);
        }

        public void setRotateEnabled(boolean z) {
            this.mOptionBundle.putBoolean("com.one.tomato.ucrop.rotate", z);
        }

        public void setDragFrameEnabled(boolean z) {
            this.mOptionBundle.putBoolean("com.one.tomato.ucrop.DragCropFrame", z);
        }

        public void setToolbarColor(@ColorInt int i) {
            this.mOptionBundle.putInt("com.one.tomato.ucrop.ToolbarColor", i);
        }

        public void setStatusBarColor(@ColorInt int i) {
            this.mOptionBundle.putInt("com.one.tomato.ucrop.StatusBarColor", i);
        }

        public void setToolbarWidgetColor(@ColorInt int i) {
            this.mOptionBundle.putInt("com.one.tomato.ucrop.UcropToolbarWidgetColor", i);
        }

        public void setHideBottomControls(boolean z) {
            this.mOptionBundle.putBoolean("com.one.tomato.ucrop.HideBottomControls", z);
        }

        public void setCutListData(ArrayList<String> arrayList) {
            this.mOptionBundle.putStringArrayList("com.one.tomato.ucrop.cuts", arrayList);
        }

        public void setFreeStyleCropEnabled(boolean z) {
            this.mOptionBundle.putBoolean("com.one.tomato.ucrop.FreeStyleCrop", z);
        }
    }
}
