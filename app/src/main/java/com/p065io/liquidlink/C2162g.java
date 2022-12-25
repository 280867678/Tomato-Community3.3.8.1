package com.p065io.liquidlink;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import com.p065io.liquidlink.p072g.C2163a;
import com.p065io.liquidlink.p072g.C2164b;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.IdentityHashMap;
import java.util.Locale;

/* renamed from: com.io.liquidlink.g */
/* loaded from: classes3.dex */
public class C2162g {

    /* renamed from: n */
    private static C2162g f1451n;

    /* renamed from: o */
    private static final Object f1452o = new Object();

    /* renamed from: a */
    private final Context f1453a;

    /* renamed from: b */
    private final String f1454b;

    /* renamed from: d */
    private final Integer f1456d;

    /* renamed from: e */
    private final String f1457e;

    /* renamed from: f */
    private final String f1458f;

    /* renamed from: g */
    private final String f1459g;

    /* renamed from: h */
    private final String f1460h;

    /* renamed from: i */
    private final String f1461i;

    /* renamed from: j */
    private final String f1462j;

    /* renamed from: k */
    private final String f1463k;

    /* renamed from: l */
    private final String f1464l;

    /* renamed from: m */
    private final IdentityHashMap f1465m = new IdentityHashMap();

    /* renamed from: c */
    private final String f1455c = Build.VERSION.RELEASE;

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004e, code lost:
        r2 = r0.nextElement().getInetAddresses();
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x005c, code lost:
        if (r2.hasMoreElements() == false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x005e, code lost:
        r3 = r2.nextElement();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0068, code lost:
        if (r3.isLoopbackAddress() != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006a, code lost:
        r6.f1465m.put(new java.lang.String("localIP"), r3.getHostAddress());
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0046, code lost:
        if (r0 != null) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x004c, code lost:
        if (r0.hasMoreElements() == false) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public C2162g(Context context) {
        Integer num;
        String str;
        String str2;
        this.f1453a = context;
        this.f1454b = context.getPackageName();
        String str3 = null;
        try {
            num = Integer.valueOf(this.f1453a.getPackageManager().getPackageInfo(this.f1453a.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException unused) {
            num = null;
        }
        this.f1456d = num;
        this.f1457e = Build.MODEL;
        this.f1458f = Build.ID;
        this.f1459g = Build.DISPLAY;
        this.f1460h = Build.BRAND;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException unused2) {
        }
        this.f1462j = str2;
        if (Build.VERSION.SDK_INT < 26) {
            str3 = Build.SERIAL;
        } else {
            try {
                str3 = Build.getSerial();
            } catch (SecurityException unused3) {
            }
        }
        this.f1463k = str3;
        this.f1464l = Settings.Secure.getString(context.getContentResolver(), "android_id");
        this.f1461i = str;
        try {
            str2 = ((WifiManager) context.getSystemService(AopConstants.WIFI)).getConnectionInfo().getMacAddress();
        } catch (SecurityException unused4) {
            str2 = null;
        }
        this.f1462j = str2;
        if (Build.VERSION.SDK_INT < 26) {
        }
        this.f1463k = str3;
        this.f1464l = Settings.Secure.getString(context.getContentResolver(), "android_id");
        try {
            str = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (SecurityException unused5) {
            str = null;
        }
        this.f1461i = str;
        str2 = ((WifiManager) context.getSystemService(AopConstants.WIFI)).getConnectionInfo().getMacAddress();
        this.f1462j = str2;
        if (Build.VERSION.SDK_INT < 26) {
        }
        this.f1463k = str3;
        this.f1464l = Settings.Secure.getString(context.getContentResolver(), "android_id");
        this.f1463k = str3;
        this.f1464l = Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    /* renamed from: a */
    public static C2162g m3970a(Context context) {
        synchronized (f1452o) {
            if (f1451n == null) {
                f1451n = new C2162g(context.getApplicationContext());
            }
        }
        return f1451n;
    }

    /* renamed from: a */
    public String m3971a() {
        Throwable th;
        FileChannel fileChannel;
        try {
            fileChannel = new RandomAccessFile(this.f1453a.getApplicationInfo().sourceDir, "r").getChannel();
            try {
                C2163a m3951a = C2164b.m3951a(fileChannel);
                if (m3951a == null) {
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException unused) {
                        }
                    }
                    return "";
                }
                byte[] m3953c = m3951a.m3953c();
                if (m3953c == null) {
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return "";
                }
                String str = new String(Base64.encode(m3953c, 10), "UTF-8");
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException unused3) {
                    }
                }
                return str;
            } catch (FileNotFoundException unused4) {
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException unused5) {
                    }
                }
                return null;
            } catch (IOException unused6) {
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException unused7) {
                    }
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException unused8) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException unused9) {
            fileChannel = null;
        } catch (IOException unused10) {
            fileChannel = null;
        } catch (Throwable th3) {
            th = th3;
            fileChannel = null;
        }
    }

    /* renamed from: b */
    public String m3969b() {
        return this.f1454b;
    }

    /* renamed from: c */
    public String m3968c() {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(this.f1453a.getPackageManager().getPackageInfo(this.f1453a.getPackageName(), 64).signatures[0].toByteArray());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String upperCase = Integer.toHexString(b & 255).toUpperCase(Locale.US);
                if (upperCase.length() == 1) {
                    sb.append("0");
                }
                sb.append(upperCase);
                sb.append(":");
            }
            String sb2 = sb.toString();
            return sb2.substring(0, sb2.length() - 1);
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException unused) {
            return null;
        }
    }

    /* renamed from: d */
    public String m3967d() {
        return this.f1455c;
    }

    /* renamed from: e */
    public Integer m3966e() {
        return this.f1456d;
    }

    /* renamed from: f */
    public String m3965f() {
        return this.f1457e;
    }

    /* renamed from: g */
    public String m3964g() {
        return this.f1458f;
    }

    /* renamed from: h */
    public String m3963h() {
        return this.f1459g;
    }

    /* renamed from: i */
    public String m3962i() {
        return this.f1460h;
    }

    /* renamed from: j */
    public String m3961j() {
        return this.f1461i;
    }

    /* renamed from: k */
    public String m3960k() {
        return this.f1462j;
    }

    /* renamed from: l */
    public String m3959l() {
        return this.f1463k;
    }

    /* renamed from: m */
    public String m3958m() {
        return this.f1464l;
    }

    /* renamed from: n */
    public IdentityHashMap m3957n() {
        return this.f1465m;
    }
}
