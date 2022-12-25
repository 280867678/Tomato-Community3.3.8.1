package p007b.p025d.p026a.p032e.p033a;

import android.util.Base64;
import java.io.UnsupportedEncodingException;

/* renamed from: b.d.a.e.a.a */
/* loaded from: classes2.dex */
public class C0782a {
    /* renamed from: a */
    public static String m5036a(String str) {
        try {
            return new String(Base64.decode(str.getBytes("UTF-8"), 0));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /* renamed from: b */
    public static String m5035b(String str) {
        try {
            return Base64.encodeToString(str.getBytes("UTF-8"), 0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
