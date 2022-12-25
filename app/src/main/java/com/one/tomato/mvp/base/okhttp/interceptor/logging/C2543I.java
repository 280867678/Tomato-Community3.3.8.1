package com.one.tomato.mvp.base.okhttp.interceptor.logging;

/* renamed from: com.one.tomato.mvp.base.okhttp.interceptor.logging.I */
/* loaded from: classes3.dex */
class C2543I {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void log(int i, String str, String str2) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(str);
        if (i == 4) {
            logger.log(java.util.logging.Level.INFO, str2);
        } else {
            logger.log(java.util.logging.Level.WARNING, str2);
        }
    }
}
