package com.example.screenhotlibrary;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.p002v4.content.ContextCompat;

/* loaded from: classes2.dex */
public class XRScreenHot {
    private static final String[] KEYWORDS = {"screenshot", "screen_shot", "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", "screen-cap", "screen cap"};
    private static final String[] MEDIA_PROJECTIONS = {"_data", "datetaken"};
    private static XRScreenHot mInstance;
    private Handler handler = new Handler();
    private Context mContext;
    private MediaContentObserver mExternalObserver;
    private Handler mHandler;
    private MediaContentObserver mInternalObserver;

    private XRScreenHot(Context context) {
        this.mContext = context;
    }

    public static XRScreenHot with(Context context) {
        if (mInstance == null) {
            mInstance = new XRScreenHot(context);
        }
        return mInstance;
    }

    public void start(ScreenHotListener screenHotListener) {
        HandlerThread handlerThread = new HandlerThread("Screenshot_Observer");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
        this.mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, this.mHandler, screenHotListener);
        this.mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.mHandler, screenHotListener);
        this.mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, false, this.mInternalObserver);
        this.mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, this.mExternalObserver);
    }

    public void recycle() {
        if (this.mInternalObserver == null || this.mExternalObserver == null) {
            return;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mInternalObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mExternalObserver);
    }

    /* loaded from: classes2.dex */
    private class MediaContentObserver extends ContentObserver {
        private Uri mContentUri;
        private ScreenHotListener mListener;

        public MediaContentObserver(Uri uri, Handler handler, ScreenHotListener screenHotListener) {
            super(handler);
            this.mContentUri = uri;
            this.mListener = screenHotListener;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            handleMediaContentChange(this.mContentUri);
        }

        private void handleMediaContentChange(Uri uri) {
            Cursor query;
            if (ContextCompat.checkSelfPermission(XRScreenHot.this.mContext, "android.permission.READ_EXTERNAL_STORAGE") != 0 || (query = XRScreenHot.this.mContext.getContentResolver().query(uri, XRScreenHot.MEDIA_PROJECTIONS, null, null, "date_added desc limit 1")) == null || !query.moveToFirst()) {
                return;
            }
            final String string = query.getString(query.getColumnIndex("_data"));
            final long j = query.getLong(query.getColumnIndex("datetaken"));
            if (!checkScreenHot(string)) {
                return;
            }
            XRScreenHot.this.handler.post(new Runnable() { // from class: com.example.screenhotlibrary.XRScreenHot.MediaContentObserver.1
                @Override // java.lang.Runnable
                public void run() {
                    MediaContentObserver.this.mListener.onScreenHotSuccess(string, j);
                }
            });
        }

        private boolean checkScreenHot(String str) {
            for (String str2 : XRScreenHot.KEYWORDS) {
                if (str.toLowerCase().contains(str2)) {
                    return true;
                }
            }
            return false;
        }
    }
}
