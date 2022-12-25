package com.security.sdk;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.security.sdk.p093a.C3078a;
import com.security.sdk.p095c.C3085b;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.UUID;

/* renamed from: com.security.sdk.c */
/* loaded from: classes3.dex */
public class C3083c {

    /* renamed from: a */
    private static String f1864a;

    /* renamed from: b */
    private static String f1865b;

    /* renamed from: c */
    private static String f1866c;

    /* renamed from: d */
    private static String f1867d;

    /* renamed from: e */
    private static String f1868e;

    /* renamed from: f */
    private static String f1869f;

    /* renamed from: g */
    private static String f1870g;

    /* renamed from: a */
    public static String m3697a() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            NetworkInterface byName = NetworkInterface.getByName("eth1");
            if (byName == null) {
                byName = NetworkInterface.getByName("wlan0");
            }
            if (byName == null) {
                f1867d = "02:00:00:00:00:00";
            } else {
                byte[] hardwareAddress = byName.getHardwareAddress();
                int length = hardwareAddress.length;
                for (int i = 0; i < length; i++) {
                    stringBuffer.append(String.format("%02X:", Byte.valueOf(hardwareAddress[i])));
                }
                if (stringBuffer.length() > 0) {
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                }
                f1867d = stringBuffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            f1867d = "02:00:00:00:00:00";
        }
        return f1867d;
    }

    /* renamed from: a */
    public static String m3696a(Context context) {
        try {
            String str = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.DISPLAY.length() % 10) + (Build.HOST.length() % 10) + (Build.ID.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10) + (Build.TAGS.length() % 10) + (Build.TYPE.length() % 10) + (Build.USER.length() % 10);
            try {
                f1864a = new UUID(str.hashCode(), Build.class.getField("SERIAL").get(null).toString().hashCode()).toString();
            } catch (Exception e) {
                e.printStackTrace();
                f1864a = new UUID(str.hashCode(), Settings.System.getString(context.getContentResolver(), "android_id").hashCode()).toString();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return f1864a;
    }

    /* renamed from: b */
    public static String m3695b() {
        Object obj;
        Object invoke;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            Field declaredField = BluetoothAdapter.class.getDeclaredField("mService");
            declaredField.setAccessible(true);
            obj = declaredField.get(defaultAdapter);
        } catch (Exception unused) {
            f1869f = "02:00:00:00:00:00";
        }
        if (obj == null) {
            return null;
        }
        Method method = obj.getClass().getMethod("getAddress", new Class[0]);
        if (method != null && (invoke = method.invoke(obj, new Object[0])) != null) {
            f1869f = invoke.toString();
        }
        return f1869f;
    }

    /* renamed from: b */
    public static String m3694b(Context context) {
        try {
            f1865b = Build.VERSION.SDK_INT >= 23 ? context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0 ? ((TelephonyManager) context.getSystemService("phone")).getDeviceId() : "0" : ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f1865b;
    }

    /* renamed from: c */
    public static String m3693c(Context context) {
        try {
            f1866c = Build.VERSION.SDK_INT >= 23 ? context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0 ? ((TelephonyManager) context.getSystemService("phone")).getSubscriberId() : "0" : ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f1865b;
    }

    /* renamed from: d */
    public static String m3692d(Context context) {
        try {
            f1868e = Settings.System.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f1868e;
    }

    /* renamed from: e */
    public static String m3691e(Context context) {
        try {
            int m3687b = C3085b.m3687b(context);
            if (m3687b != 0) {
                if (m3687b == 1) {
                    try {
                        f1870g = ((WifiManager) context.getSystemService(AopConstants.WIFI)).getConnectionInfo().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (m3687b == 2 || m3687b == 3 || m3687b == 4) {
                    C3078a c3078a = new C3078a();
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                    String networkOperator = telephonyManager.getNetworkOperator();
                    c3078a.m3725c(networkOperator.substring(0, 3));
                    c3078a.m3723d(networkOperator.substring(3));
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
                            c3078a.m3729a("0");
                            c3078a.m3727b("0");
                        } else if (telephonyManager.getPhoneType() == 2) {
                            try {
                                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) telephonyManager.getCellLocation();
                                c3078a.m3729a(cdmaCellLocation.getBaseStationId() + "");
                                c3078a.m3727b(cdmaCellLocation.getNetworkId() + "");
                            } catch (Exception e2) {
                                e = e2;
                                e.printStackTrace();
                                f1870g = c3078a.m3722e();
                                return f1870g;
                            }
                        } else if (telephonyManager.getPhoneType() == 1) {
                            try {
                                GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
                                if (gsmCellLocation != null) {
                                    c3078a.m3729a(gsmCellLocation.getCid() + "");
                                    c3078a.m3727b(gsmCellLocation.getLac() + "");
                                }
                            } catch (Exception e3) {
                                e = e3;
                                e.printStackTrace();
                                f1870g = c3078a.m3722e();
                                return f1870g;
                            }
                        }
                    }
                    f1870g = c3078a.m3722e();
                }
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return f1870g;
    }
}
