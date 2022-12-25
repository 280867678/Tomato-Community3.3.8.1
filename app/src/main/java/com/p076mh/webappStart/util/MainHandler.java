package com.p076mh.webappStart.util;

import android.os.Handler;
import android.os.Looper;

/* renamed from: com.mh.webappStart.util.MainHandler */
/* loaded from: classes3.dex */
public class MainHandler extends Handler {
    private static volatile MainHandler instance;

    public static MainHandler getInstance() {
        if (instance == null) {
            synchronized (MainHandler.class) {
                if (instance == null) {
                    instance = new MainHandler();
                }
            }
        }
        return instance;
    }

    private MainHandler() {
        super(Looper.getMainLooper());
    }
}
