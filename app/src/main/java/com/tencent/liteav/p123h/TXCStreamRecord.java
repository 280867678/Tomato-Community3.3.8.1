package com.tencent.liteav.p123h;

import android.content.Context;
import android.media.MediaFormat;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.liteav.audio.TXCAudioRecorder;
import com.tencent.liteav.audio.TXIAudioRecordListener;
import com.tencent.liteav.audio.impl.Record.TXCAudioHWEncoder;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.muxer.TXCMP4Muxer;
import com.tencent.liteav.videoencoder.TXCHWVideoEncoder;
import com.tencent.liteav.videoencoder.TXIVideoEncoderListener;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.h.a */
/* loaded from: classes3.dex */
public class TXCStreamRecord implements TXIAudioRecordListener, TXIVideoEncoderListener {

    /* renamed from: c */
    private TXCMP4Muxer f4321c;

    /* renamed from: d */
    private C3509a f4322d;

    /* renamed from: e */
    private AbstractC3510b f4323e;

    /* renamed from: f */
    private long f4324f = 0;

    /* renamed from: g */
    private long f4325g = -1;

    /* renamed from: h */
    private boolean f4326h = false;

    /* renamed from: i */
    private Handler f4327i = new Handler(Looper.getMainLooper()) { // from class: com.tencent.liteav.h.a.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (TXCStreamRecord.this.f4323e != null) {
                int i = message.what;
                if (i == 1) {
                    TXCStreamRecord.this.f4323e.mo1453a(((Long) message.obj).longValue());
                } else if (i != 2) {
                } else {
                    TXCLog.m2915d("TXCStreamRecord", "record complete. errcode = " + message.arg1 + ", errmsg = " + ((String) message.obj) + ", outputPath = " + TXCStreamRecord.this.f4322d.f4334f + ", coverImage = " + TXCStreamRecord.this.f4322d.f4335g);
                    if (message.arg1 == 0 && TXCStreamRecord.this.f4322d.f4335g != null && !TXCStreamRecord.this.f4322d.f4335g.isEmpty() && !TXCSystemUtil.m2887a(TXCStreamRecord.this.f4322d.f4334f, TXCStreamRecord.this.f4322d.f4335g)) {
                        TXCLog.m2914e("TXCStreamRecord", "saveVideoThumb error. sourcePath = " + TXCStreamRecord.this.f4322d.f4334f + ", coverImagePath = " + TXCStreamRecord.this.f4322d.f4335g);
                    }
                    if (message.arg1 != 0) {
                        try {
                            File file = new File(TXCStreamRecord.this.f4322d.f4334f);
                            if (file.exists()) {
                                file.delete();
                            }
                        } catch (Exception unused) {
                        }
                    }
                    TXCStreamRecord.this.f4323e.mo1454a(message.arg1, (String) message.obj, TXCStreamRecord.this.f4322d.f4334f, TXCStreamRecord.this.f4322d.f4335g);
                }
            }
        }
    };

    /* renamed from: a */
    private TXCAudioHWEncoder f4319a = new TXCAudioHWEncoder();

    /* renamed from: b */
    private TXCHWVideoEncoder f4320b = new TXCHWVideoEncoder();

    /* compiled from: TXCStreamRecord.java */
    /* renamed from: com.tencent.liteav.h.a$b */
    /* loaded from: classes3.dex */
    public interface AbstractC3510b {
        /* renamed from: a */
        void mo1454a(int i, String str, String str2, String str3);

        /* renamed from: a */
        void mo1453a(long j);
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordError(int i, String str) {
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordPcmData(byte[] bArr, long j, int i, int i2, int i3) {
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordRawPcmData(byte[] bArr, long j, int i, int i2, int i3, boolean z) {
    }

    /* compiled from: TXCStreamRecord.java */
    /* renamed from: com.tencent.liteav.h.a$a */
    /* loaded from: classes3.dex */
    public static class C3509a {

        /* renamed from: e */
        public EGLContext f4333e;

        /* renamed from: f */
        public String f4334f;

        /* renamed from: g */
        public String f4335g;

        /* renamed from: a */
        public int f4329a = 544;

        /* renamed from: b */
        public int f4330b = 960;

        /* renamed from: c */
        public int f4331c = 20;

        /* renamed from: d */
        public int f4332d = 1000;

        /* renamed from: h */
        public int f4336h = 0;

        /* renamed from: i */
        public int f4337i = 0;

        /* renamed from: j */
        public int f4338j = 16;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("TXCStreamRecordParams: [width=" + this.f4329a);
            sb.append("; height=" + this.f4330b);
            sb.append("; fps=" + this.f4331c);
            sb.append("; bitrate=" + this.f4332d);
            sb.append("; channels=" + this.f4336h);
            sb.append("; samplerate=" + this.f4337i);
            sb.append("; bits=" + this.f4338j);
            sb.append("; EGLContext=" + this.f4333e);
            sb.append("; coveriamge=" + this.f4335g);
            sb.append("; outputpath=" + this.f4334f + "]");
            return sb.toString();
        }
    }

    public TXCStreamRecord(Context context) {
        this.f4321c = new TXCMP4Muxer(context, 2);
    }

    /* renamed from: a */
    public void m1458a(AbstractC3510b abstractC3510b) {
        this.f4323e = abstractC3510b;
    }

    /* renamed from: a */
    public void m1459a(C3509a c3509a) {
        int i;
        int i2;
        this.f4322d = c3509a;
        this.f4324f = 0L;
        this.f4325g = -1L;
        this.f4321c.mo1231a(this.f4322d.f4334f);
        int i3 = c3509a.f4336h;
        if (i3 > 0 && (i = c3509a.f4337i) > 0 && (i2 = c3509a.f4338j) > 0) {
            this.f4319a.m3392a(TXCAudioRecorder.f2047f, i, i3, i2, new WeakReference<>(this));
            C3509a c3509a2 = this.f4322d;
            this.f4321c.mo1226b(TXCSystemUtil.m2892a(c3509a2.f4337i, c3509a2.f4336h, 2));
            this.f4326h = true;
        }
        this.f4320b.setListener(this);
        TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
        C3509a c3509a3 = this.f4322d;
        tXSVideoEncoderParam.width = c3509a3.f4329a;
        tXSVideoEncoderParam.height = c3509a3.f4330b;
        tXSVideoEncoderParam.fps = c3509a3.f4331c;
        tXSVideoEncoderParam.glContext = c3509a3.f4333e;
        tXSVideoEncoderParam.annexb = true;
        tXSVideoEncoderParam.appendSpsPps = false;
        this.f4320b.setBitrate(c3509a3.f4332d);
        this.f4320b.start(tXSVideoEncoderParam);
    }

    /* renamed from: a */
    public void m1464a() {
        this.f4326h = false;
        this.f4319a.m3393a();
        this.f4320b.stop();
        if (this.f4321c.mo1227b() < 0) {
            Handler handler = this.f4327i;
            handler.sendMessage(Message.obtain(handler, 2, 1, 0, "mp4合成失败"));
            return;
        }
        Handler handler2 = this.f4327i;
        handler2.sendMessage(Message.obtain(handler2, 2, 0, 0, ""));
    }

    /* renamed from: a */
    public void m1462a(int i, long j) {
        TXCHWVideoEncoder tXCHWVideoEncoder = this.f4320b;
        C3509a c3509a = this.f4322d;
        tXCHWVideoEncoder.pushVideoFrame(i, c3509a.f4329a, c3509a.f4330b, j);
    }

    /* renamed from: a */
    public void m1456a(byte[] bArr, long j) {
        if (this.f4326h) {
            this.f4319a.m3389a(bArr, j);
        } else {
            TXCLog.m2914e("TXCStreamRecord", "drainAudio fail because of not init yet!");
        }
    }

    /* renamed from: a */
    public static String m1460a(Context context, String str) {
        if (context == null) {
            return null;
        }
        try {
            String valueOf = String.valueOf(System.currentTimeMillis() / 1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(valueOf + "000").longValue()));
            String m1461a = m1461a(context);
            return new File(m1461a, String.format("TXUGC_%s" + str, format)).getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    private static String m1461a(Context context) {
        if ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath();
        }
        return context.getFilesDir().getPath();
    }

    /* renamed from: a */
    private String m1463a(int i) {
        String str;
        switch (i) {
            case 10000002:
                str = "未启动视频编码器";
                break;
            case 10000003:
                str = "非法视频输入参数";
                break;
            case 10000004:
                str = "视频编码初始化失败";
                break;
            case 10000005:
                str = "视频编码失败";
                break;
            default:
                str = "";
                break;
        }
        Handler handler = this.f4327i;
        handler.sendMessage(Message.obtain(handler, 2, 1, 0, str));
        return str;
    }

    @Override // com.tencent.liteav.audio.TXIAudioRecordListener
    public void onRecordEncData(byte[] bArr, long j, int i, int i2, int i3) {
        this.f4321c.mo1228a(bArr, 0, bArr.length, j * 1000, 1);
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
    public void onEncodeNAL(TXSNALPacket tXSNALPacket, int i) {
        if (i == 0) {
            TXCMP4Muxer tXCMP4Muxer = this.f4321c;
            byte[] bArr = tXSNALPacket.nalData;
            tXCMP4Muxer.mo1224b(bArr, 0, bArr.length, tXSNALPacket.pts * 1000, tXSNALPacket.info.flags);
            if (this.f4325g < 0) {
                this.f4325g = tXSNALPacket.pts;
            }
            long j = tXSNALPacket.pts;
            if (j <= this.f4324f + 500) {
                return;
            }
            Handler handler = this.f4327i;
            handler.sendMessage(Message.obtain(handler, 1, new Long(j - this.f4325g)));
            this.f4324f = tXSNALPacket.pts;
            return;
        }
        String m1463a = m1463a(i);
        TXCLog.m2914e("TXCStreamRecord", "video encode error! errmsg: " + m1463a);
    }

    @Override // com.tencent.liteav.videoencoder.TXIVideoEncoderListener
    public void onEncodeFormat(MediaFormat mediaFormat) {
        this.f4321c.mo1232a(mediaFormat);
        if (!this.f4321c.mo1223c() || this.f4321c.mo1235a() >= 0) {
            return;
        }
        Handler handler = this.f4327i;
        handler.sendMessage(Message.obtain(handler, 2, 1, 0, "mp4封装器启动失败"));
    }
}
