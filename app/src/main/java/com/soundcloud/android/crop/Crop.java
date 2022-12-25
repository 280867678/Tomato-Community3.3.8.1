package com.soundcloud.android.crop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/* loaded from: classes3.dex */
public class Crop {
    private Intent cropIntent = new Intent();

    /* renamed from: of */
    public static Crop m3671of(Uri uri, Uri uri2) {
        return new Crop(uri, uri2);
    }

    private Crop(Uri uri, Uri uri2) {
        this.cropIntent.setData(uri);
        this.cropIntent.putExtra("output", uri2);
    }

    public Crop asSquare() {
        this.cropIntent.putExtra("aspect_x", 1);
        this.cropIntent.putExtra("aspect_y", 1);
        return this;
    }

    public void start(Activity activity) {
        start(activity, 6709);
    }

    public void start(Activity activity, int i) {
        activity.startActivityForResult(getIntent(activity), i);
    }

    public Intent getIntent(Context context) {
        this.cropIntent.setClass(context, CropImageActivity.class);
        return this.cropIntent;
    }
}
