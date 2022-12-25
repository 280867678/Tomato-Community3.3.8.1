package p007b.p012b.p013a;

import java.text.SimpleDateFormat;
import java.util.Date;

/* renamed from: b.b.a.k */
/* loaded from: classes2.dex */
public class C0592k {

    /* renamed from: a */
    public static final SimpleDateFormat f176a = new SimpleDateFormat("yyyy-MM-dd");

    /* renamed from: a */
    public static long m5495a() {
        try {
            return f176a.parse(f176a.format(new Date(System.currentTimeMillis()))).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
