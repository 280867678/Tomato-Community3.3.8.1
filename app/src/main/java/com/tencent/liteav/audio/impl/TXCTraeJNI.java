package com.tencent.liteav.audio.impl;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.tencent.liteav.audio.TXCLiveBGMPlayer;
import com.tencent.liteav.audio.TXIAudioRecordListener;
import com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.io.File;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class TXCTraeJNI {
    private static Context mContext;
    private static WeakReference<TXIAudioRecordListener> mTraeRecordListener;

    public static native void nativeAppendLibraryPath(String str);

    public static native void nativeCacheClassForNative();

    public static native void nativeSetAudioMode(int i);

    public static native void nativeSetTraeConfig(String str);

    public static native boolean nativeTraeIsPlaying();

    public static native boolean nativeTraeIsRecording();

    public static native void nativeTraeSetChangerType(int i, int i2);

    public static native void nativeTraeSetRecordMute(boolean z);

    public static native void nativeTraeSetRecordReverb(int i);

    public static native void nativeTraeSetVolume(float f);

    public static native void nativeTraeStartPlay(Context context);

    public static native void nativeTraeStartRecord(Context context, int i, int i2, int i3);

    public static native void nativeTraeStopPlay();

    public static native void nativeTraeStopRecord(boolean z);

    static {
        TXCSystemUtil.m2873e();
        nativeCacheClassForNative();
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static void setTraeRecordListener(WeakReference<TXIAudioRecordListener> weakReference) {
        mTraeRecordListener = weakReference;
    }

    public static void onRecordRawPcmData(byte[] bArr, int i, int i2, int i3) {
        WeakReference<TXIAudioRecordListener> weakReference = mTraeRecordListener;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        mTraeRecordListener.get().onRecordRawPcmData(bArr, TXCTimeUtil.getTimeTick(), i, i2, i3, false);
    }

    public static void onRecordPcmData(byte[] bArr, int i, int i2, int i3) {
        WeakReference<TXIAudioRecordListener> weakReference = mTraeRecordListener;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        mTraeRecordListener.get().onRecordPcmData(bArr, TXCTimeUtil.getTimeTick(), i, i2, i3);
    }

    public static void onRecordEncData(byte[] bArr, long j, int i, int i2, int i3) {
        WeakReference<TXIAudioRecordListener> weakReference = mTraeRecordListener;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        mTraeRecordListener.get().onRecordEncData(bArr, j, i, i2, i3);
    }

    public static void onRecordError(int i, String str) {
        WeakReference<TXIAudioRecordListener> weakReference = mTraeRecordListener;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        mTraeRecordListener.get().onRecordError(i, str);
    }

    public static void SetAudioMode(int i) {
        TXCAudioRouteMgr.m3371a().m3370a(i);
    }

    public static void InitTraeEngineLibrary(Context context) {
        ApplicationInfo applicationInfo;
        if (context == null) {
            TXCLog.m2914e("TXCAudioJNI", "nativeInitTraeEngine failed, context is null!");
            return;
        }
        try {
            String str = context.getApplicationInfo().nativeLibraryDir;
            String str2 = applicationInfo.dataDir + "/lib";
            nativeAppendLibraryPath("add_libpath:" + str);
            nativeAppendLibraryPath("add_libpath:" + str2);
            nativeAppendLibraryPath("add_libpath:" + ("/data/data/" + applicationInfo.packageName + "/lib"));
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public static boolean nativeCheckTraeEngine(Context context) {
        ApplicationInfo applicationInfo;
        if (context == null) {
            TXCLog.m2914e("TXCAudioJNI", "nativeCheckTraeEngine failed, context is null!");
            return false;
        }
        String str = context.getApplicationInfo().nativeLibraryDir;
        String str2 = applicationInfo.dataDir + "/lib";
        String str3 = "/data/data/" + applicationInfo.packageName + "/lib";
        String m2872f = TXCSystemUtil.m2872f();
        if (m2872f == null) {
            m2872f = "";
        }
        if (new File(str + "/libtraeimp-rtmp-armeabi-v7a.so").exists()) {
            return true;
        }
        if (new File(str2 + "/libtraeimp-rtmp-armeabi-v7a.so").exists()) {
            return true;
        }
        if (new File(str3 + "/libtraeimp-rtmp-armeabi-v7a.so").exists()) {
            return true;
        }
        if (new File(m2872f + "/libtraeimp-rtmp-armeabi-v7a.so").exists()) {
            return true;
        }
        if (new File(str + "/libtraeimp-rtmp-armeabi.so").exists()) {
            return true;
        }
        if (new File(str2 + "/libtraeimp-rtmp-armeabi.so").exists()) {
            return true;
        }
        if (new File(str3 + "/libtraeimp-rtmp-armeabi.so").exists()) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(m2872f);
        sb.append("/libtraeimp-rtmp-armeabi.so");
        return new File(sb.toString()).exists();
    }

    public static boolean traeStartPlay(Context context) {
        if (!TXCAudioBasePlayController.nativeIsTracksEmpty() || TXCLiveBGMPlayer.getInstance().isPlaying()) {
            InitTraeEngineLibrary(context);
            nativeTraeStartPlay(context);
            return true;
        }
        return false;
    }

    public static boolean traeStopPlay() {
        if (!TXCAudioBasePlayController.nativeIsTracksEmpty() || TXCLiveBGMPlayer.getInstance().isPlaying()) {
            return false;
        }
        nativeTraeStopPlay();
        return true;
    }
}
