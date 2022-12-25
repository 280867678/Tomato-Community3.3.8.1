package com.tomatolive.library.utils;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/* loaded from: classes4.dex */
public class MD5Utils {
    private static char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private MD5Utils() {
    }

    public static String getMd5(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            return StringUtils.bytesToHex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMd5(String str) {
        return getMd5(str.getBytes());
    }

    public static String getMd5(File file) {
        FileInputStream fileInputStream;
        String str = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    MappedByteBuffer map = fileInputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.update(map);
                    str = StringUtils.bytesToHex(messageDigest.digest());
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    IOUtils.closeQuietly(fileInputStream);
                    return str;
                }
            } catch (Throwable th) {
                th = th;
                IOUtils.closeQuietly(fileInputStream);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            IOUtils.closeQuietly(fileInputStream);
            throw th;
        }
        IOUtils.closeQuietly(fileInputStream);
        return str;
    }

    public static String getMessageDigest(byte[] bArr) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            byte[] digest = messageDigest.digest();
            char[] cArr2 = new char[digest.length * 2];
            int i = 0;
            for (byte b : digest) {
                int i2 = i + 1;
                cArr2[i] = cArr[(b >>> 4) & 15];
                i = i2 + 1;
                cArr2[i2] = cArr[b & 15];
            }
            return new String(cArr2);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String byteHEX(byte b) {
        char[] cArr = Digit;
        return new String(new char[]{cArr[(b >>> 4) & 15], cArr[b & 15]});
    }

    private static byte[] md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            return messageDigest.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String toHex(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & 255) < 16) {
                sb.append("0");
            }
            sb.append(Long.toString(bArr[i] & 255, 16));
        }
        return sb.toString();
    }

    public static String hash(String str) {
        try {
            return TextUtils.isEmpty(toHex(md5(str))) ? str : new String(toHex(md5(str)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String encryptPassword(String str, String str2, String str3) {
        return hash(str + str2 + str3);
    }

    public static String encryptPassword(String str, String str2) {
        return hash(str + str2);
    }

    public static String encryptPassword(String str) {
        return hash(str);
    }

    public static String getDefaultPassword() {
        return hash("123456");
    }
}
