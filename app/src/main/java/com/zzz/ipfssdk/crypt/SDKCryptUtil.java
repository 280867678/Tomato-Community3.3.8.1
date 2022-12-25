package com.zzz.ipfssdk.crypt;

import com.zzz.ipfssdk.LogUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import p007b.p025d.p026a.p032e.p033a.C0782a;

/* loaded from: classes4.dex */
public class SDKCryptUtil {
    public static String HexStringToString(String str) {
        if (str.length() % 2 != 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < str.length()) {
            stringBuffer.append("%");
            int i2 = i + 2;
            stringBuffer.append(str.subSequence(i, i2));
            i = i2;
        }
        try {
            return URLDecoder.decode(stringBuffer.toString(), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String nativeEncode(String str, String str2) {
        byte[] reportItemEncode = reportItemEncode(str2, str);
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "c加密后的长度:" + reportItemEncode.length);
        return new String(reportItemEncode);
    }

    public static native byte[] reportItemEncode(String str, String str2);

    public static String stringToHexString(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            for (byte b : str.getBytes("GBK")) {
                sb.append(Integer.toHexString(b & 255));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString().toUpperCase();
    }

    public static String tempDecode(String str) {
        return HexStringToString(C0782a.m5036a(str));
    }

    public static String tempEncode(String str) {
        return C0782a.m5035b(stringToHexString(str));
    }
}
