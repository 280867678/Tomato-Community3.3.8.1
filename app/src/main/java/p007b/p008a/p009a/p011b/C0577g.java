package p007b.p008a.p009a.p011b;

import android.content.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: b.a.a.b.g */
/* loaded from: classes2.dex */
public class C0577g<T> {

    /* renamed from: a */
    public final C0580j f118a;

    public C0577g(Context context, Class cls) {
        this.f118a = new C0580j(context, cls);
    }

    /* renamed from: a */
    public List<T> m5542a() {
        return this.f118a.m5538a();
    }

    /* renamed from: a */
    public boolean m5541a(T t) {
        return this.f118a.m5537a((C0580j) t);
    }

    /* renamed from: a */
    public boolean m5540a(String str, Object obj) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, obj);
        return m5539a((Map<String, Object>) hashMap);
    }

    /* renamed from: a */
    public boolean m5539a(Map<String, Object> map) {
        return this.f118a.m5536a(map);
    }
}
