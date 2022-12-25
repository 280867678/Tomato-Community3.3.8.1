package com.zzhoujay.richtext.ext;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class ImageKit {
    public static boolean isGif(InputStream inputStream) {
        if (inputStream.markSupported()) {
            inputStream.mark(10);
            byte[] bArr = new byte[4];
            try {
                inputStream.read(bArr, 0, bArr.length);
                inputStream.reset();
                return "47494638".equals(bytesToHexString(bArr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isGif(byte[] bArr) {
        byte[] bArr2 = new byte[4];
        System.arraycopy(bArr, 0, bArr2, 0, 4);
        return "47494638".equals(bytesToHexString(bArr2));
    }

    public static boolean isGif(String str) {
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(str);
                try {
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                } catch (FileNotFoundException e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                boolean isGif = isGif(bufferedInputStream);
                try {
                    bufferedInputStream.close();
                    fileInputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return isGif;
            } catch (FileNotFoundException e3) {
                bufferedInputStream2 = bufferedInputStream;
                e = e3;
                e.printStackTrace();
                if (bufferedInputStream2 != null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        return false;
                    }
                }
                if (fileInputStream == null) {
                    return false;
                }
                fileInputStream.close();
                return false;
            } catch (Throwable th2) {
                bufferedInputStream2 = bufferedInputStream;
                th = th2;
                if (bufferedInputStream2 != null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                        throw th;
                    }
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e6) {
            e = e6;
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
        }
    }

    private static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }
}
