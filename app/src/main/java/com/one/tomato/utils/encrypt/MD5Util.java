package com.one.tomato.utils.encrypt;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes3.dex */
public class MD5Util {
    public static String md5(@NonNull String str) {
        return TextUtils.isEmpty(str) ? "" : md5(str, "");
    }

    public static String md5(@NonNull String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            String str3 = "";
            for (byte b : MessageDigest.getInstance("MD5").digest((str + str2).getBytes())) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                str3 = str3 + hexString;
            }
            return str3;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String md5(@NonNull File file) {
        if (!file.isFile()) {
            return null;
        }
        byte[] bArr = new byte[1024];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                int read = fileInputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    messageDigest.update(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    return new BigInteger(1, messageDigest.digest()).toString(16);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
