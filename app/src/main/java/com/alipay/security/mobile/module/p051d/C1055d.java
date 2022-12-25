package com.alipay.security.mobile.module.p051d;

import com.alipay.security.mobile.module.p047a.C1037a;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.alipay.security.mobile.module.d.d */
/* loaded from: classes2.dex */
public final class C1055d {

    /* renamed from: a */
    private static String f1134a = "";

    /* renamed from: b */
    private static String f1135b = "";

    /* renamed from: c */
    private static String f1136c = "";

    /* renamed from: a */
    public static synchronized void m4206a(String str) {
        synchronized (C1055d.class) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            m4203a(arrayList);
        }
    }

    /* renamed from: a */
    public static synchronized void m4205a(String str, String str2, String str3) {
        synchronized (C1055d.class) {
            f1134a = str;
            f1135b = str2;
            f1136c = str3;
        }
    }

    /* renamed from: a */
    public static synchronized void m4204a(Throwable th) {
        String str;
        synchronized (C1055d.class) {
            ArrayList arrayList = new ArrayList();
            if (th != null) {
                StringWriter stringWriter = new StringWriter();
                th.printStackTrace(new PrintWriter(stringWriter));
                str = stringWriter.toString();
            } else {
                str = "";
            }
            arrayList.add(str);
            m4203a(arrayList);
        }
    }

    /* renamed from: a */
    private static synchronized void m4203a(List<String> list) {
        synchronized (C1055d.class) {
            if (!C1037a.m4303a(f1135b) && !C1037a.m4303a(f1136c)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(f1136c);
                Iterator<String> it2 = list.iterator();
                while (it2.hasNext()) {
                    stringBuffer.append(", " + it2.next());
                }
                stringBuffer.append("\n");
                try {
                    File file = new File(f1134a);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file2 = new File(f1134a, f1135b);
                    if (!file2.exists()) {
                        file2.createNewFile();
                    }
                    FileWriter fileWriter = ((long) stringBuffer.length()) + file2.length() <= 51200 ? new FileWriter(file2, true) : new FileWriter(file2);
                    fileWriter.write(stringBuffer.toString());
                    fileWriter.flush();
                    fileWriter.close();
                } catch (Exception unused) {
                }
            }
        }
    }
}
