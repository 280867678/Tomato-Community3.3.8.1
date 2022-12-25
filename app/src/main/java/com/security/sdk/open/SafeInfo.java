package com.security.sdk.open;

import android.content.Context;
import com.google.gson.GsonBuilder;
import com.security.sdk.C3079b;
import com.security.sdk.C3083c;

/* loaded from: classes3.dex */
public class SafeInfo {
    private String ANDROID_ID;
    private String BASE_INFO;
    private String BOARD;
    private String BOOTLOADER;
    private String BRAND;
    private String BluetoothMAC;
    private String CPU_ABI;
    private String CPU_ABI2;
    private String DEVICE;
    private String DEVICE_NUMBER;
    private String DISPLAY;
    private String FINGERPRINT;
    private String HARDWARE;
    private String HOST;

    /* renamed from: ID */
    private String f1879ID;
    private String IMEI;
    private String IMSI;
    private String MANUFACTURER;
    private String MODEL;
    private String PRODUCT;
    private String RadioVersion;
    private String SERIAL;
    private long SystemTime;
    private String SystemVersion;
    private String TAGS;
    private String TIME;
    private String TYPE;
    private String USER;
    private String WifiMAC;

    public String getSafeInfo(Context context) {
        this.SystemTime = System.currentTimeMillis();
        this.BOARD = C3079b.m3721a();
        this.BOOTLOADER = C3079b.m3720b();
        this.BRAND = C3079b.m3719c();
        this.CPU_ABI = C3079b.m3718d();
        this.CPU_ABI2 = C3079b.m3717e();
        this.DEVICE = C3079b.m3716f();
        this.DISPLAY = C3079b.m3715g();
        this.RadioVersion = C3079b.m3714h();
        this.FINGERPRINT = C3079b.m3713i();
        this.HARDWARE = C3079b.m3712j();
        this.HOST = C3079b.m3711k();
        this.f1879ID = C3079b.m3710l();
        this.MANUFACTURER = C3079b.m3709m();
        this.MODEL = C3079b.m3708n();
        this.SERIAL = C3079b.m3707o();
        this.PRODUCT = C3079b.m3706p();
        this.TAGS = C3079b.m3705q();
        this.TIME = C3079b.m3704r();
        this.TYPE = C3079b.m3703s();
        this.USER = C3079b.m3702t();
        this.SystemVersion = C3079b.m3701u();
        this.DEVICE_NUMBER = C3083c.m3696a(context);
        this.IMEI = C3083c.m3694b(context);
        this.IMSI = C3083c.m3693c(context);
        this.WifiMAC = C3083c.m3697a();
        this.ANDROID_ID = C3083c.m3692d(context);
        this.BluetoothMAC = C3083c.m3695b();
        this.BASE_INFO = C3083c.m3691e(context);
        return new GsonBuilder().disableHtmlEscaping().create().toJson(this);
    }
}
