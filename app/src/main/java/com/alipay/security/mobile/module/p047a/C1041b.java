package com.alipay.security.mobile.module.p047a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* renamed from: com.alipay.security.mobile.module.a.b */
/* loaded from: classes2.dex */
public final class C1041b {
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003f, code lost:
        if (r4 == null) goto L16;
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String m4284a(String str, String str2) {
        BufferedReader bufferedReader;
        File file;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader2 = null;
        try {
            file = new File(str, str2);
        } catch (IOException unused) {
            bufferedReader = null;
        } catch (Throwable th) {
            th = th;
        }
        if (!file.exists()) {
            return null;
        }
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                }
            } catch (IOException unused2) {
            } catch (Throwable th2) {
                th = th2;
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (Throwable unused3) {
                    }
                }
                throw th;
            }
            try {
                break;
            } catch (Throwable unused4) {
                return sb.toString();
            }
        }
        bufferedReader.close();
    }
}
