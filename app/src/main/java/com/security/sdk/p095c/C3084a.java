package com.security.sdk.p095c;

import android.os.Build;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/* renamed from: com.security.sdk.c.a */
/* loaded from: classes3.dex */
public class C3084a {
    /* renamed from: a */
    public static String m3690a() {
        StringBuilder sb;
        int i;
        String[] strArr;
        try {
            strArr = Build.VERSION.SDK_INT >= 21 ? Build.SUPPORTED_ABIS : new String[]{Build.CPU_ABI, Build.CPU_ABI2};
            sb = new StringBuilder();
        } catch (Exception e) {
            e = e;
            sb = null;
        }
        try {
            for (String str : strArr) {
                sb.append(str);
                sb.append(',');
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return sb.toString();
        }
        return sb.toString();
    }

    /* renamed from: a */
    public static String m3689a(File file) {
        try {
            if (!file.isFile()) {
                return null;
            }
            byte[] bArr = new byte[1024];
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                FileInputStream fileInputStream = new FileInputStream(file);
                while (true) {
                    int read = fileInputStream.read(bArr, 0, 1024);
                    if (read == -1) {
                        fileInputStream.close();
                        return new BigInteger(1, messageDigest.digest()).toString(16);
                    }
                    messageDigest.update(bArr, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
