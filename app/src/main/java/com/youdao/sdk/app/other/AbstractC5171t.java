package com.youdao.sdk.app.other;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.youdao.sdk.app.other.C5165h;
import com.youdao.sdk.common.YouDaoLog;
import java.net.NetworkInterface;
import java.net.SocketException;

/* renamed from: com.youdao.sdk.app.other.t */
/* loaded from: classes4.dex */
public abstract class AbstractC5171t extends AbstractC5164g {
    protected String appKey;
    protected Context mContext;
    protected String mKeywords;
    protected WifiManager mWifiManager;

    public AbstractC5171t(Context context) {
        this.mContext = context;
        TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        this.mWifiManager = (WifiManager) context.getSystemService(AopConstants.WIFI);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setAppKey(String str) {
        addParam("appKey", str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setKeywords(String str) {
        addParam("q", str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setTimezone(String str) {
        addParam("z", str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setOrientation(String str) {
        addParam("o", str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setDensity(float f) {
        addParam("sc_a", "" + f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setMccCode(String str) {
        addParam("mcc", str == null ? "" : str.substring(0, mncPortionLength(str)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setMncCode(String str) {
        addParam("mnc", str == null ? "" : str.substring(mncPortionLength(str)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setIsoCountryCode(String str) {
        addParam("iso", str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setCarrierName(String str) {
        addParam("cn", str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setNetworkType(C5165h.EnumC5166a enumC5166a) {
        addParam("ct", enumC5166a);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setDetailNetworkType(int i) {
        addParam("dct", String.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setImei(String str) {
        addParam("imei", str);
    }

    private void addParam(String str, C5165h.EnumC5166a enumC5166a) {
        addParam(str, enumC5166a.toString());
    }

    private int mncPortionLength(String str) {
        return Math.min(3, str.length());
    }

    public void setWifi() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null) {
            return;
        }
        WifiInfo wifiInfo = null;
        try {
            wifiInfo = wifiManager.getConnectionInfo();
        } catch (Exception e) {
            YouDaoLog.m168d("Unable to fectch connection wifi info", e);
        }
        if (wifiInfo == null) {
            return;
        }
        String str = "";
        StringBuilder sb = new StringBuilder(str);
        String macAddress = wifiInfo.getMacAddress();
        if (TextUtils.isEmpty(macAddress) || "02:00:00:00:00:00".equals(macAddress)) {
            macAddress = getMacAddress();
        }
        sb.append(macAddress);
        sb.append(",");
        if (wifiInfo.getSSID() != null) {
            str = wifiInfo.getSSID();
        }
        sb.append(str);
        addParam(AopConstants.WIFI, sb.toString());
    }

    public static String getMacAddress() {
        byte[] hardwareAddress;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            NetworkInterface byName = NetworkInterface.getByName("eth1");
            if (byName == null) {
                byName = NetworkInterface.getByName("wlan0");
            }
            if (byName != null && (hardwareAddress = byName.getHardwareAddress()) != null && hardwareAddress.length != 0) {
                int length = hardwareAddress.length;
                for (int i = 0; i < length; i++) {
                    stringBuffer.append(String.format("%02X:", Byte.valueOf(hardwareAddress[i])));
                }
                if (stringBuffer.length() > 0) {
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                }
                return stringBuffer.toString();
            }
            return "02:00:00:00:00:00";
        } catch (SocketException e) {
            YouDaoLog.m166e("Exception encounted", e);
            return "02:00:00:00:00:00";
        }
    }

    public void setPackage(String str) {
        addParam("pkn", str);
    }
}
