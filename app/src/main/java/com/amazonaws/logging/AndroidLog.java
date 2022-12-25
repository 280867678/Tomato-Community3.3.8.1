package com.amazonaws.logging;

/* loaded from: classes2.dex */
public class AndroidLog implements Log {
    private final String tag;

    public AndroidLog(String str) {
        this.tag = str;
    }

    @Override // com.amazonaws.logging.Log
    public boolean isDebugEnabled() {
        return android.util.Log.isLoggable(this.tag, 3);
    }

    @Override // com.amazonaws.logging.Log
    public boolean isErrorEnabled() {
        return android.util.Log.isLoggable(this.tag, 6);
    }

    @Override // com.amazonaws.logging.Log
    public boolean isInfoEnabled() {
        return android.util.Log.isLoggable(this.tag, 4);
    }

    @Override // com.amazonaws.logging.Log
    public void trace(Object obj) {
        android.util.Log.v(this.tag, obj.toString());
    }

    @Override // com.amazonaws.logging.Log
    public void debug(Object obj) {
        android.util.Log.d(this.tag, obj.toString());
    }

    @Override // com.amazonaws.logging.Log
    public void debug(Object obj, Throwable th) {
        android.util.Log.d(this.tag, obj.toString(), th);
    }

    @Override // com.amazonaws.logging.Log
    public void info(Object obj) {
        android.util.Log.i(this.tag, obj.toString());
    }

    @Override // com.amazonaws.logging.Log
    public void warn(Object obj) {
        android.util.Log.w(this.tag, obj.toString());
    }

    @Override // com.amazonaws.logging.Log
    public void warn(Object obj, Throwable th) {
        android.util.Log.w(this.tag, obj.toString(), th);
    }

    @Override // com.amazonaws.logging.Log
    public void error(Object obj) {
        android.util.Log.e(this.tag, obj.toString());
    }

    @Override // com.amazonaws.logging.Log
    public void error(Object obj, Throwable th) {
        android.util.Log.e(this.tag, obj.toString(), th);
    }
}
