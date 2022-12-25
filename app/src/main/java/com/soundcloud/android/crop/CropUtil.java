package com.soundcloud.android.crop;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.soundcloud.android.crop.MonitoredActivity;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes3.dex */
class CropUtil {
    public static void closeSilently(@Nullable Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Throwable unused) {
        }
    }

    public static int getExifRotation(File file) {
        if (file == null) {
            return 0;
        }
        try {
            int attributeInt = new ExifInterface(file.getAbsolutePath()).getAttributeInt(android.support.media.ExifInterface.TAG_ORIENTATION, 0);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt == 6) {
                return 90;
            }
            return attributeInt != 8 ? 0 : 270;
        } catch (IOException e) {
            Log.m3670e("Error getting Exif data", e);
            return 0;
        }
    }

    public static boolean copyExifRotation(File file, File file2) {
        if (file != null && file2 != null) {
            try {
                ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
                ExifInterface exifInterface2 = new ExifInterface(file2.getAbsolutePath());
                exifInterface2.setAttribute(android.support.media.ExifInterface.TAG_ORIENTATION, exifInterface.getAttribute(android.support.media.ExifInterface.TAG_ORIENTATION));
                exifInterface2.saveAttributes();
                return true;
            } catch (IOException e) {
                Log.m3670e("Error copying Exif data", e);
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0077, code lost:
        if (r3 != null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0080, code lost:
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x007e, code lost:
        if (r3 == null) goto L49;
     */
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static File getFromMediaUri(Context context, ContentResolver contentResolver, Uri uri) {
        Cursor cursor;
        int columnIndex;
        Cursor cursor2 = null;
        if (uri == null) {
            return null;
        }
        if ("file".equals(uri.getScheme())) {
            return new File(uri.getPath());
        }
        if ("content".equals(uri.getScheme())) {
            try {
                try {
                    cursor = contentResolver.query(uri, new String[]{"_data", "_display_name"}, null, null, null);
                    if (cursor != null) {
                        try {
                            if (cursor.moveToFirst()) {
                                if (uri.toString().startsWith("content://com.google.android.gallery3d")) {
                                    columnIndex = cursor.getColumnIndex("_display_name");
                                } else {
                                    columnIndex = cursor.getColumnIndex("_data");
                                }
                                if (columnIndex != -1) {
                                    String string = cursor.getString(columnIndex);
                                    if (!TextUtils.isEmpty(string)) {
                                        File file = new File(string);
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                        return file;
                                    }
                                }
                            }
                        } catch (IllegalArgumentException unused) {
                            cursor2 = cursor;
                            File fromMediaUriPfd = getFromMediaUriPfd(context, contentResolver, uri);
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            return fromMediaUriPfd;
                        } catch (SecurityException unused2) {
                        } catch (Throwable th) {
                            th = th;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor = null;
                }
            } catch (IllegalArgumentException unused3) {
            } catch (SecurityException unused4) {
                cursor = null;
            }
        }
        return null;
    }

    private static String getTempFilename(Context context) throws IOException {
        return File.createTempFile("image", "tmp", context.getCacheDir()).getAbsolutePath();
    }

    @Nullable
    private static File getFromMediaUriPfd(Context context, ContentResolver contentResolver, Uri uri) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        if (uri == null) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(contentResolver.openFileDescriptor(uri, "r").getFileDescriptor());
            try {
                String tempFilename = getTempFilename(context);
                fileOutputStream = new FileOutputStream(tempFilename);
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read != -1) {
                            fileOutputStream.write(bArr, 0, read);
                        } else {
                            File file = new File(tempFilename);
                            closeSilently(fileInputStream);
                            closeSilently(fileOutputStream);
                            return file;
                        }
                    }
                } catch (IOException unused) {
                    closeSilently(fileInputStream);
                    closeSilently(fileOutputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream2 = fileOutputStream;
                    closeSilently(fileInputStream);
                    closeSilently(fileOutputStream2);
                    throw th;
                }
            } catch (IOException unused2) {
                fileOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused3) {
            fileOutputStream = null;
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
        }
    }

    public static void startBackgroundJob(MonitoredActivity monitoredActivity, String str, String str2, Runnable runnable, Handler handler) {
        new Thread(new BackgroundJob(monitoredActivity, runnable, ProgressDialog.show(monitoredActivity, str, str2, true, false), handler)).start();
    }

    /* loaded from: classes3.dex */
    private static class BackgroundJob extends MonitoredActivity.LifeCycleAdapter implements Runnable {
        private final MonitoredActivity activity;
        private final Runnable cleanupRunner = new Runnable() { // from class: com.soundcloud.android.crop.CropUtil.BackgroundJob.1
            @Override // java.lang.Runnable
            public void run() {
                BackgroundJob.this.activity.removeLifeCycleListener(BackgroundJob.this);
                if (BackgroundJob.this.dialog.getWindow() != null) {
                    BackgroundJob.this.dialog.dismiss();
                }
            }
        };
        private final ProgressDialog dialog;
        private final Handler handler;
        private final Runnable job;

        public BackgroundJob(MonitoredActivity monitoredActivity, Runnable runnable, ProgressDialog progressDialog, Handler handler) {
            this.activity = monitoredActivity;
            this.dialog = progressDialog;
            this.job = runnable;
            this.activity.addLifeCycleListener(this);
            this.handler = handler;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.job.run();
            } finally {
                this.handler.post(this.cleanupRunner);
            }
        }

        @Override // com.soundcloud.android.crop.MonitoredActivity.LifeCycleListener
        public void onActivityDestroyed(MonitoredActivity monitoredActivity) {
            this.cleanupRunner.run();
            this.handler.removeCallbacks(this.cleanupRunner);
        }

        @Override // com.soundcloud.android.crop.MonitoredActivity.LifeCycleListener
        public void onActivityStopped(MonitoredActivity monitoredActivity) {
            this.dialog.hide();
        }

        @Override // com.soundcloud.android.crop.MonitoredActivity.LifeCycleListener
        public void onActivityStarted(MonitoredActivity monitoredActivity) {
            this.dialog.show();
        }
    }
}
