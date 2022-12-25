package com.tomatolive.library.p136ui.view.widget.matisse.internal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.content.FileProvider;
import android.support.p002v4.p004os.EnvironmentCompat;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.CaptureStrategy;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.utils.MediaStoreCompat */
/* loaded from: classes4.dex */
public class MediaStoreCompat {
    private CaptureStrategy mCaptureStrategy;
    private final WeakReference<Activity> mContext;
    private String mCurrentPhotoPath;
    private Uri mCurrentPhotoUri;
    private final WeakReference<Fragment> mFragment;

    public MediaStoreCompat(Activity activity) {
        this.mContext = new WeakReference<>(activity);
        this.mFragment = null;
    }

    public MediaStoreCompat(Activity activity, Fragment fragment) {
        this.mContext = new WeakReference<>(activity);
        this.mFragment = new WeakReference<>(fragment);
    }

    public static boolean hasCameraFeature(Context context) {
        return context.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    public void setCaptureStrategy(CaptureStrategy captureStrategy) {
        this.mCaptureStrategy = captureStrategy;
    }

    public void dispatchCaptureIntent(Context context, int i) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            File file = null;
            try {
                file = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file == null) {
                return;
            }
            this.mCurrentPhotoPath = file.getAbsolutePath();
            this.mCurrentPhotoUri = FileProvider.getUriForFile(this.mContext.get(), this.mCaptureStrategy.authority, file);
            intent.putExtra("output", this.mCurrentPhotoUri);
            intent.addFlags(2);
            if (Build.VERSION.SDK_INT < 21) {
                for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 65536)) {
                    context.grantUriPermission(resolveInfo.activityInfo.packageName, this.mCurrentPhotoUri, 3);
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
        if (this.mCaptureStrategy.isPublic) {
            externalFilesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!externalFilesDir.exists()) {
                externalFilesDir.mkdirs();
            }
        } else {
            externalFilesDir = this.mContext.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        String str = this.mCaptureStrategy.directory;
        if (str != null) {
            File file = new File(externalFilesDir, str);
            if (!file.exists()) {
                file.mkdirs();
            }
            externalFilesDir = file;
        }
        File file2 = new File(externalFilesDir, format);
        if (!"mounted".equals(EnvironmentCompat.getStorageState(file2))) {
            return null;
        }
        return file2;
    }

    public Uri getCurrentPhotoUri() {
        return this.mCurrentPhotoUri;
    }

    public String getCurrentPhotoPath() {
        return this.mCurrentPhotoPath;
    }
}
