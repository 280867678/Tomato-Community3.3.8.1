package com.tencent.liteav.basic.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.iceteck.silicompressorr.videocompression.MediaController;
import com.tencent.avroom.TXCAVRoomConstants;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.CropRect;
import com.tencent.ugc.TXRecordCommon;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.UUID;

/* renamed from: com.tencent.liteav.basic.util.b */
/* loaded from: classes3.dex */
public class TXCSystemUtil {

    /* renamed from: a */
    public static long f2736a = 0;

    /* renamed from: b */
    private static float f2737b = 0.0f;

    /* renamed from: c */
    private static float f2738c = 0.0f;

    /* renamed from: d */
    private static float f2739d = 0.0f;

    /* renamed from: e */
    private static float f2740e = 0.0f;

    /* renamed from: f */
    private static float f2741f = 0.0f;

    /* renamed from: g */
    private static float f2742g = 0.0f;

    /* renamed from: h */
    private static boolean f2743h = true;

    /* renamed from: j */
    private static long f2745j = 0;

    /* renamed from: k */
    private static String f2746k = "";

    /* renamed from: l */
    private static int f2747l;

    /* renamed from: m */
    private static long f2748m;

    /* renamed from: o */
    private static boolean f2750o;

    /* renamed from: i */
    private static int[] f2744i = new int[2];

    /* renamed from: n */
    private static final Object f2749n = new Object();

    /* renamed from: p */
    private static int[] f2751p = {96000, 88200, 64000, TXRecordCommon.AUDIO_SAMPLERATE_48000, TXRecordCommon.AUDIO_SAMPLERATE_44100, TXRecordCommon.AUDIO_SAMPLERATE_32000, 24000, 22050, TXRecordCommon.AUDIO_SAMPLERATE_16000, 12000, 11025, 8000, 7350};

    /* renamed from: h */
    private static long m2870h() {
        try {
            int myPid = Process.myPid();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + myPid + "/stat")), 1000);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            String[] split = readLine.split(ConstantUtils.PLACEHOLDER_STR_ONE);
            if (split != null && !TextUtils.isEmpty(split[13])) {
                return Long.parseLong(split[13]) + Long.parseLong(split[14]) + Long.parseLong(split[15]) + Long.parseLong(split[16]);
            }
            return 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x008c  */
    /* renamed from: i */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void m2869i() {
        long j;
        String[] split;
        long j2 = 0;
        if (Build.VERSION.SDK_INT < 26) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1000);
                String readLine = bufferedReader.readLine();
                bufferedReader.close();
                split = readLine.split(ConstantUtils.PLACEHOLDER_STR_ONE);
            } catch (Exception e) {
                e = e;
                j = 0;
            }
            if (split != null && split.length >= 9) {
                j = Long.parseLong(split[2]) + Long.parseLong(split[3]) + Long.parseLong(split[4]) + Long.parseLong(split[6]) + Long.parseLong(split[5]) + Long.parseLong(split[7]) + Long.parseLong(split[8]);
                try {
                    j2 = Long.parseLong(split[5]) + Long.parseLong(split[6]);
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    if (f2743h) {
                    }
                }
                if (f2743h) {
                    f2737b = (float) j;
                    f2741f = (float) j2;
                    return;
                }
                f2738c = (float) j;
                f2742g = (float) j2;
                return;
            }
        }
        j = 0;
        if (f2743h) {
        }
    }

    /* renamed from: a */
    public static int[] m2894a() {
        float f;
        if (f2745j != 0 && TXCTimeUtil.getTimeTick() - f2745j < 2000) {
            return f2744i;
        }
        int[] iArr = new int[2];
        if (f2743h) {
            f2739d = (float) m2870h();
            m2869i();
            f2743h = false;
            iArr[0] = 0;
            iArr[1] = 0;
            return iArr;
        }
        f2740e = (float) m2870h();
        m2869i();
        float f2 = f2738c;
        float f3 = f2737b;
        float f4 = 0.0f;
        if (f2 != f3) {
            f4 = ((f2740e - f2739d) * 100.0f) / (f2 - f3);
            f = (((f2 - f3) - (f2742g - f2741f)) * 100.0f) / (f2 - f3);
        } else {
            f = 0.0f;
        }
        f2737b = f2738c;
        f2739d = f2740e;
        f2741f = f2742g;
        iArr[0] = (int) (f4 * 10.0f);
        iArr[1] = (int) (f * 10.0f);
        int[] iArr2 = f2744i;
        iArr2[0] = iArr[0];
        iArr2[1] = iArr[1];
        f2745j = TXCTimeUtil.getTimeTick();
        return iArr;
    }

    /* renamed from: b */
    public static int m2881b() {
        if (f2748m != 0 && TXCTimeUtil.getTimeTick() - f2748m < 10000) {
            return f2747l;
        }
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        int totalPss = memoryInfo.getTotalPss();
        f2748m = TXCTimeUtil.getTimeTick();
        f2747l = totalPss / 1024;
        return f2747l;
    }

    /* renamed from: a */
    public static String m2890a(Context context) {
        return TXCDRApi.getSimulateIDFA(context);
    }

    /* renamed from: b */
    public static String m2880b(Context context) {
        if (context != null) {
            try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /* renamed from: c */
    public static int m2876c(Context context) {
        if (context == null) {
            return 255;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return 255;
        }
        if (activeNetworkInfo.getType() == 9) {
            return 5;
        }
        if (activeNetworkInfo.getType() == 1) {
            return 1;
        }
        if (activeNetworkInfo.getType() != 0) {
            return 255;
        }
        switch (telephonyManager.getNetworkType()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 4;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 3;
            case 13:
            default:
                return 2;
        }
    }

    /* renamed from: c */
    public static String m2877c() {
        return Build.MODEL;
    }

    /* renamed from: d */
    public static String m2875d() {
        return UUID.randomUUID().toString();
    }

    /* renamed from: d */
    public static String m2874d(Context context) {
        return TXCDRApi.getDevUUID(context, TXCDRApi.getSimulateIDFA(context));
    }

    /* renamed from: a */
    public static void m2883a(WeakReference<TXINotifyListener> weakReference, long j, int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putLong(TXCAVRoomConstants.EVT_USERID, j);
        bundle.putInt(TXCAVRoomConstants.EVT_ID, i);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (str != null) {
            bundle.putCharSequence("EVT_MSG", str);
        }
        m2886a(weakReference, i, bundle);
    }

    /* renamed from: a */
    public static void m2885a(WeakReference<TXINotifyListener> weakReference, int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt(TXCAVRoomConstants.EVT_ID, i);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (str != null) {
            bundle.putCharSequence("EVT_MSG", str);
        }
        m2886a(weakReference, i, bundle);
    }

    /* renamed from: a */
    public static void m2886a(WeakReference<TXINotifyListener> weakReference, int i, Bundle bundle) {
        TXINotifyListener tXINotifyListener;
        if (weakReference == null || (tXINotifyListener = weakReference.get()) == null) {
            return;
        }
        tXINotifyListener.onNotifyEvent(i, bundle);
    }

    /* renamed from: a */
    public static void m2884a(WeakReference<TXINotifyListener> weakReference, long j, int i, Bundle bundle) {
        TXINotifyListener tXINotifyListener;
        if (weakReference == null || (tXINotifyListener = weakReference.get()) == null) {
            return;
        }
        bundle.putLong(TXCAVRoomConstants.EVT_USERID, j);
        tXINotifyListener.onNotifyEvent(i, bundle);
    }

    /* renamed from: a */
    public static CropRect m2891a(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = i * i4;
        int i8 = i2 * i3;
        if (i7 >= i8) {
            i6 = i8 / i4;
            i5 = i2;
        } else {
            i5 = i7 / i3;
            i6 = i;
        }
        int i9 = 0;
        int i10 = i > i6 ? (i - i6) >> 1 : 0;
        if (i2 > i5) {
            i9 = (i2 - i5) >> 1;
        }
        return new CropRect(i10, i9, i6, i5);
    }

    /* renamed from: e */
    public static void m2873e() {
        synchronized (f2749n) {
            if (!f2750o) {
                m2889a("stlport_shared");
                m2889a("saturn");
                m2889a("txffmpeg");
                m2889a("liteavsdk");
                f2750o = true;
            }
        }
    }

    /* renamed from: a */
    public static void m2889a(String str) {
        try {
            System.loadLibrary(str);
        } catch (Error e) {
            Log.d("NativeLoad", "load library : " + e.toString());
            m2878b(f2746k, str);
        } catch (Exception e2) {
            Log.d("NativeLoad", "load library : " + e2.toString());
            m2878b(f2746k, str);
        }
    }

    /* renamed from: b */
    private static void m2878b(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            System.load(str + "/lib" + str2 + ".so");
        } catch (Error e) {
            Log.d("NativeLoad", "load library : " + e.toString());
        } catch (Exception e2) {
            Log.d("NativeLoad", "load library : " + e2.toString());
        }
    }

    /* renamed from: b */
    public static void m2879b(String str) {
        f2746k = str;
    }

    /* renamed from: f */
    public static String m2872f() {
        return f2746k;
    }

    /* renamed from: a */
    public static int m2893a(int i) {
        int i2 = 0;
        while (true) {
            int[] iArr = f2751p;
            if (i2 >= iArr.length || iArr[i2] == i) {
                break;
            }
            i2++;
        }
        if (i2 >= f2751p.length) {
            return -1;
        }
        return i2;
    }

    @TargetApi(16)
    /* renamed from: a */
    public static MediaFormat m2892a(int i, int i2, int i3) {
        int m2893a = m2893a(i);
        ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.put(0, (byte) ((i3 << 3) | (m2893a >> 1)));
        allocate.put(1, (byte) (((m2893a & 1) << 7) | (i2 << 3)));
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", i, i2);
        createAudioFormat.setInteger("channel-count", i2);
        createAudioFormat.setInteger("sample-rate", i);
        createAudioFormat.setByteBuffer("csd-0", allocate);
        return createAudioFormat;
    }

    /* renamed from: a */
    public static boolean m2887a(String str, String str2) {
        MediaMetadataRetriever mediaMetadataRetriever;
        Bitmap frameAtTime;
        FileOutputStream fileOutputStream;
        if (str == null || str2 == null) {
            return false;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            if (!new File(str).exists()) {
                return false;
            }
            mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                try {
                    mediaMetadataRetriever.setDataSource(str);
                    frameAtTime = mediaMetadataRetriever.getFrameAtTime();
                    File file = new File(str2);
                    if (file.exists()) {
                        file.delete();
                    }
                    fileOutputStream = new FileOutputStream(file);
                } catch (Exception e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                frameAtTime.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                try {
                    fileOutputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                mediaMetadataRetriever.release();
                return true;
            } catch (Exception e3) {
                e = e3;
                fileOutputStream2 = fileOutputStream;
                e.printStackTrace();
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                if (mediaMetadataRetriever != null) {
                    mediaMetadataRetriever.release();
                }
                return false;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (mediaMetadataRetriever != null) {
                    mediaMetadataRetriever.release();
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            mediaMetadataRetriever = null;
        } catch (Throwable th3) {
            th = th3;
            mediaMetadataRetriever = null;
        }
    }

    /* renamed from: g */
    public static boolean m2871g() {
        return Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") && Build.MODEL.equalsIgnoreCase("NEM-L22");
    }

    /* renamed from: a */
    private static void m2888a(String str, MediaFormat mediaFormat, byte[] bArr, int i, int i2) {
        int i3 = i2 - i;
        ByteBuffer allocate = ByteBuffer.allocate(i3);
        allocate.put(bArr, i, i3);
        allocate.position(0);
        mediaFormat.setByteBuffer(str, allocate);
    }

    @TargetApi(16)
    /* renamed from: a */
    public static MediaFormat m2882a(byte[] bArr, int i, int i2) {
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(MediaController.MIME_TYPE, i, i2);
        int i3 = 0;
        int i4 = 0;
        boolean z = false;
        boolean z2 = false;
        int i5 = 0;
        while (true) {
            int i6 = i3 + 3;
            if (i6 < bArr.length) {
                int i7 = (bArr[i3] == 0 && bArr[i3 + 1] == 0 && bArr[i3 + 2] == 1) ? 3 : 0;
                if (bArr[i3] == 0 && bArr[i3 + 1] == 0 && bArr[i3 + 2] == 0 && bArr[i6] == 1) {
                    i7 = 4;
                }
                if (i7 > 0) {
                    if (i4 == 0) {
                        i3 += i7;
                        i4 = i7;
                    } else {
                        int i8 = bArr[i4] & 31;
                        if (i8 == 7) {
                            m2888a("csd-0", createVideoFormat, bArr, i4, i3);
                            z = true;
                        } else if (i8 == 8) {
                            m2888a("csd-1", createVideoFormat, bArr, i4, i3);
                            z2 = true;
                        }
                        i4 = i3 + i7;
                        if (z && z2) {
                            return createVideoFormat;
                        }
                        i5 = i3;
                        i3 = i4;
                    }
                }
                i3++;
            } else {
                int i9 = bArr[i4] & 31;
                if (z && i9 == 8) {
                    m2888a("csd-1", createVideoFormat, bArr, i4, i5);
                    return createVideoFormat;
                } else if (!z2 || i9 != 7) {
                    return null;
                } else {
                    m2888a("csd-0", createVideoFormat, bArr, i4, i5);
                    return createVideoFormat;
                }
            }
        }
    }
}
