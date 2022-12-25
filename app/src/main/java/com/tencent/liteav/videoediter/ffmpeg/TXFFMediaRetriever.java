package com.tencent.liteav.videoediter.ffmpeg;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videoediter.ffmpeg.jni.FFMediaInfo;
import com.tencent.liteav.videoediter.ffmpeg.jni.TXFFMediaInfoJNI;
import java.io.File;

/* renamed from: com.tencent.liteav.videoediter.ffmpeg.a */
/* loaded from: classes3.dex */
public class TXFFMediaRetriever {

    /* renamed from: a */
    private String f5524a;

    /* renamed from: b */
    private FFMediaInfo f5525b;

    /* renamed from: a */
    public int m498a(String str) {
        if (str == null) {
            TXCLog.m2914e("TXFFMediaRetriever", "setDataSource: path can't be null!");
            return -1;
        } else if (!new File(str).exists()) {
            TXCLog.m2914e("TXFFMediaRetriever", "setDataSource: file isn't exists!");
            return -1;
        } else if (str.equals(this.f5524a)) {
            return 0;
        } else {
            this.f5524a = str;
            this.f5525b = TXFFMediaInfoJNI.getMediaInfo(this.f5524a);
            if (this.f5525b != null) {
                TXCLog.m2913i("TXFFMediaRetriever", "setDataSource: media info = " + this.f5525b.toString());
                return 0;
            }
            TXCLog.m2914e("TXFFMediaRetriever", "setDataSource: get media info fail!");
            return -1;
        }
    }

    /* renamed from: a */
    public int m499a() {
        FFMediaInfo fFMediaInfo;
        if (this.f5524a == null || (fFMediaInfo = this.f5525b) == null) {
            TXCLog.m2914e("TXFFMediaRetriever", "getRotation: you must set path first!");
            return 0;
        }
        return fFMediaInfo.rotation;
    }

    /* renamed from: b */
    public int m497b() {
        FFMediaInfo fFMediaInfo;
        if (this.f5524a == null || (fFMediaInfo = this.f5525b) == null) {
            TXCLog.m2914e("TXFFMediaRetriever", "getVideoWidth: you must set path first!");
            return 0;
        }
        return fFMediaInfo.width;
    }

    /* renamed from: c */
    public int m496c() {
        FFMediaInfo fFMediaInfo;
        if (this.f5524a == null || (fFMediaInfo = this.f5525b) == null) {
            TXCLog.m2914e("TXFFMediaRetriever", "getVideoHeight: you must set path first!");
            return 0;
        }
        return fFMediaInfo.height;
    }

    /* renamed from: d */
    public float m495d() {
        FFMediaInfo fFMediaInfo;
        if (this.f5524a == null || (fFMediaInfo = this.f5525b) == null) {
            TXCLog.m2914e("TXFFMediaRetriever", "getVideoFPS: you must set path first!");
            return 0.0f;
        }
        return fFMediaInfo.fps;
    }

    /* renamed from: e */
    public long m494e() {
        FFMediaInfo fFMediaInfo;
        if (this.f5524a == null || (fFMediaInfo = this.f5525b) == null) {
            TXCLog.m2914e("TXFFMediaRetriever", "getVideoBitrate: you must set path first!");
            return 0L;
        }
        return fFMediaInfo.videoBitrate;
    }

    /* renamed from: f */
    public long m493f() {
        FFMediaInfo fFMediaInfo;
        if (this.f5524a == null || (fFMediaInfo = this.f5525b) == null) {
            TXCLog.m2914e("TXFFMediaRetriever", "getVideoDuration: you must set path first!");
            return 0L;
        }
        return fFMediaInfo.videoDuration;
    }

    /* renamed from: g */
    public int m492g() {
        FFMediaInfo fFMediaInfo;
        if (this.f5524a == null || (fFMediaInfo = this.f5525b) == null) {
            TXCLog.m2914e("TXFFMediaRetriever", "getSampleRate: you must set path first!");
            return 0;
        }
        return fFMediaInfo.sampleRate;
    }

    /* renamed from: h */
    public long m491h() {
        FFMediaInfo fFMediaInfo;
        if (this.f5524a == null || (fFMediaInfo = this.f5525b) == null) {
            TXCLog.m2914e("TXFFMediaRetriever", "getAudioDuration: you must set path first!");
            return 0L;
        }
        return fFMediaInfo.audioDuration;
    }
}
