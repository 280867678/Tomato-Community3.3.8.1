package p007b.p025d.p026a.p032e;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.UUID;

/* renamed from: b.d.a.e.b */
/* loaded from: classes2.dex */
public class C0783b {

    /* renamed from: a */
    public static String f609a = null;

    /* renamed from: b */
    public static String f610b = "unique_id";

    /* renamed from: c */
    public static String f611c = Environment.getExternalStorageDirectory().getAbsolutePath();

    /* renamed from: d */
    public static String f612d = "unique.txt";

    /* renamed from: a */
    public static void m5034a() {
        if (!TextUtils.isEmpty(f609a)) {
            return;
        }
        String str = Build.SERIAL;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        f609a = str;
        Log.e("UniqueIDUtils", "getUniqueID: SNID获取成功" + f609a);
    }

    /* renamed from: a */
    public static void m5033a(Context context) {
        FileOutputStream fileOutputStream;
        if (!TextUtils.isEmpty(f609a)) {
            return;
        }
        f609a = UUID.randomUUID().toString();
        Log.e("UniqueIDUtils", "getUniqueID: UUID生成成功" + f609a);
        File file = new File(f611c + File.separator + context.getApplicationContext().getPackageName());
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(file, f612d);
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file2);
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return;
                }
            } catch (FileNotFoundException e3) {
                e = e3;
            } catch (IOException e4) {
                e = e4;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileOutputStream.write(f609a.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e5) {
            e = e5;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            if (fileOutputStream2 == null) {
                return;
            }
            fileOutputStream2.flush();
            fileOutputStream2.close();
        } catch (IOException e6) {
            e = e6;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            if (fileOutputStream2 == null) {
                return;
            }
            fileOutputStream2.flush();
            fileOutputStream2.close();
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }

    /* renamed from: b */
    public static void m5032b(Context context) {
        if (!TextUtils.isEmpty(f609a)) {
            return;
        }
        try {
            String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
            if (TextUtils.isEmpty(string)) {
                return;
            }
            if ("9774d56d682e549c".equals(string)) {
                return;
            }
            f609a = string;
            Log.e("UniqueIDUtils", "getUniqueID: AndroidID获取成功" + f609a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint({"MissingPermission"})
    /* renamed from: c */
    public static void m5031c(Context context) {
        if (TextUtils.isEmpty(f609a) && Build.VERSION.SDK_INT <= 27) {
            try {
                String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                if (TextUtils.isEmpty(deviceId)) {
                    return;
                }
                if ("unknown".equals(deviceId)) {
                    return;
                }
                f609a = deviceId;
                Log.e("UniqueIDUtils", "getUniqueID: DeviceId获取成功" + f609a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: d */
    public static String m5030d(Context context) {
        StringBuilder sb;
        String str;
        WeakReference weakReference = new WeakReference(context);
        f611c = (Build.VERSION.SDK_INT >= 19 ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) : Environment.getExternalStorageDirectory()).getAbsolutePath();
        if (!TextUtils.isEmpty(f609a)) {
            sb = new StringBuilder();
            str = "getUniqueID: 内存中获取";
        } else {
            f609a = PreferenceManager.getDefaultSharedPreferences((Context) weakReference.get()).getString(f610b, "");
            if (!TextUtils.isEmpty(f609a)) {
                sb = new StringBuilder();
                str = "getUniqueID: SP中获取";
            } else {
                m5029e((Context) weakReference.get());
                if (TextUtils.isEmpty(f609a)) {
                    m5031c((Context) weakReference.get());
                    m5032b((Context) weakReference.get());
                    m5034a();
                    m5033a((Context) weakReference.get());
                    PreferenceManager.getDefaultSharedPreferences((Context) weakReference.get()).edit().putString(f610b, f609a);
                    return f609a;
                }
                sb = new StringBuilder();
                str = "getUniqueID: 外部存储中获取";
            }
        }
        sb.append(str);
        sb.append(f609a);
        Log.e("UniqueIDUtils", sb.toString());
        return f609a;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0061, code lost:
        if (r0 == null) goto L13;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: e */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m5029e(Context context) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        File file = new File(new File(f611c + File.separator + context.getApplicationContext().getPackageName()), f612d);
        if (file.exists()) {
            FileInputStream fileInputStream3 = null;
            fileInputStream3 = null;
            fileInputStream3 = null;
            fileInputStream3 = null;
            try {
                try {
                    try {
                        fileInputStream2 = new FileInputStream(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e2) {
                    e = e2;
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                byte[] bArr = new byte[(int) file.length()];
                fileInputStream2.read(bArr);
                String str = new String(bArr);
                f609a = str;
                fileInputStream2.close();
                fileInputStream3 = str;
            } catch (FileNotFoundException e4) {
                e = e4;
                fileInputStream3 = fileInputStream2;
                e.printStackTrace();
                fileInputStream = fileInputStream3;
            } catch (IOException e5) {
                e = e5;
                fileInputStream3 = fileInputStream2;
                e.printStackTrace();
                if (fileInputStream3 != null) {
                    fileInputStream = fileInputStream3;
                    fileInputStream.close();
                    fileInputStream3 = fileInputStream;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream3 = fileInputStream2;
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }
}
