package com.tomatolive.library.utils;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes4.dex */
public class HandlerUtils {
    private Handler ioHandler;
    private HandlerThread ioHandlerThread;

    private HandlerUtils() {
    }

    /* loaded from: classes4.dex */
    private static class SingletonHolder {
        private static final HandlerUtils INSTANCE = new HandlerUtils();

        private SingletonHolder() {
        }
    }

    public static HandlerUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Handler startIOThread(String str, Handler.Callback callback) {
        HandlerThread handlerThread = this.ioHandlerThread;
        if (handlerThread == null || !handlerThread.isAlive()) {
            this.ioHandlerThread = new HandlerThread(str);
            this.ioHandlerThread.start();
            this.ioHandler = new Handler(this.ioHandlerThread.getLooper(), callback);
            return this.ioHandler;
        }
        return null;
    }

    public void stopIOThread() {
        Handler handler = this.ioHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.ioHandler = null;
        }
        HandlerThread handlerThread = this.ioHandlerThread;
        if (handlerThread != null) {
            try {
                try {
                    if (Build.VERSION.SDK_INT >= 18) {
                        handlerThread.quitSafely();
                    } else {
                        handlerThread.quit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                this.ioHandlerThread = null;
            }
        }
    }
}
