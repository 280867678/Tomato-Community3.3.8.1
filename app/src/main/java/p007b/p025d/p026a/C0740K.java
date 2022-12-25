package p007b.p025d.p026a;

import android.os.Environment;
import com.sensorsdata.analytics.android.sdk.util.DateFormatUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import p007b.p012b.p013a.C0581a;
import p007b.p012b.p013a.C0583c;

/* renamed from: b.d.a.K */
/* loaded from: classes2.dex */
public class C0740K {

    /* renamed from: a */
    public static String f418a;

    /* renamed from: a */
    public static void m5253a(String str) {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String str2 = absolutePath + File.separator + "logan/cache";
        String str3 = absolutePath + File.separator + "logan/log";
        File file = new File(str2);
        File file2 = new File(str3);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file2.exists()) {
            file2.mkdirs();
        }
        C0583c.C0584a c0584a = new C0583c.C0584a();
        c0584a.m5512a(str2);
        c0584a.m5510b(str3);
        c0584a.m5509b("0123456789012345".getBytes());
        c0584a.m5511a("0123456789012345".getBytes());
        C0583c m5513a = c0584a.m5513a();
        C0581a.m5532a(m5513a);
        f418a = String.format("2|%s|%s", str, "v3.0.2.4");
        C0581a.m5532a(m5513a);
        C0581a.m5531a(new C0739J());
    }

    /* renamed from: b */
    public static void m5252b(String str) {
    }

    /* renamed from: c */
    public static void m5251c(String str) {
        String format = new SimpleDateFormat(DateFormatUtils.YYYY_MM_DD_HH_MM_SS_SSS).format(new Date());
        C0581a.m5529b(f418a + "|" + format + "|" + str, 1);
    }
}
