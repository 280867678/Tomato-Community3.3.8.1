package com.gen.p059mh.webapp_extensions.matisse.internal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.content.FileProvider;
import com.gen.p059mh.webapp_extensions.activities.TakePhotoActivity;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.CaptureStrategy;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.utils.MediaStoreCompat */
/* loaded from: classes2.dex */
public class MediaStoreCompat {
    private CaptureStrategy mCaptureStrategy;
    private final WeakReference<Activity> mContext;
    private String mCurrentPhotoPath;
    private Uri mCurrentPhotoUri;
    private final WeakReference<Fragment> mFragment;
    private String photoPath;

    public MediaStoreCompat(Activity activity, String str) {
        this.mContext = new WeakReference<>(activity);
        this.mFragment = null;
        this.photoPath = str;
    }

    public MediaStoreCompat(Activity activity, Fragment fragment, String str) {
        this.mContext = new WeakReference<>(activity);
        this.mFragment = new WeakReference<>(fragment);
        this.photoPath = str;
    }

    public static boolean hasCameraFeature(Context context) {
        return context.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    public void setCaptureStrategy(CaptureStrategy captureStrategy) {
        this.mCaptureStrategy = captureStrategy;
    }

    public void dispatchCaptureIntent(Context context, int i) {
        File file;
        Intent intent = new Intent(context, TakePhotoActivity.class);
        try {
            file = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
            file = null;
        }
        if (file != null) {
            this.mCurrentPhotoPath = file.getAbsolutePath();
            intent.putExtra("photo_file_path", this.mCurrentPhotoPath);
            if (this.mCaptureStrategy != null) {
                this.mCurrentPhotoUri = FileProvider.getUriForFile(this.mContext.get(), this.mCaptureStrategy.authority, file);
                intent.putExtra("output", this.mCurrentPhotoUri);
                intent.addFlags(2);
                if (Build.VERSION.SDK_INT < 21) {
                    for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 65536)) {
                        context.grantUriPermission(resolveInfo.activityInfo.packageName, this.mCurrentPhotoUri, 3);
                    }
                }
            }
            WeakReference<Fragment> weakReference = this.mFragment;
            if (weakReference != null) {
                weakReference.get().startActivityForResult(intent, i);
            } else {
                this.mContext.get().startActivityForResult(intent, i);
            }
        }
    }

    private File createImageFile() throws IOException {
        File externalFilesDir;
        String format = String.format("JPEG_%s.jpg", new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()));
        String str = this.photoPath;
        if (str != null) {
            externalFilesDir = new File(str);
            if (!externalFilesDir.exists()) {
                externalFilesDir.mkdirs();
            }
        } else if (this.mCaptureStrategy.isPublic) {
            externalFilesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!externalFilesDir.exists()) {
                externalFilesDir.mkdirs();
            }
        } else {
            externalFilesDir = this.mContext.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        CaptureStrategy captureStrategy = this.mCaptureStrategy;
        if (captureStrategy != null) {
            File file = new File(externalFilesDir, captureStrategy.directory);
            if (!file.exists()) {
                file.mkdirs();
            }
            externalFilesDir = file;
        }
        return new File(externalFilesDir, format);
    }

    public Uri getCurrentPhotoUri() {
        return this.mCurrentPhotoUri;
    }

    public String getCurrentPhotoPath() {
        return this.mCurrentPhotoPath;
    }
}
