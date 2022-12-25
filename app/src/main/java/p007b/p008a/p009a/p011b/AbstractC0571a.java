package p007b.p008a.p009a.p011b;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import p007b.p008a.p009a.p010a.C0570a;

/* renamed from: b.a.a.b.a */
/* loaded from: classes2.dex */
public abstract class AbstractC0571a {

    /* renamed from: a */
    public C0574d f107a;

    /* renamed from: b */
    public Map<Class, C0577g> f108b = new HashMap();

    /* renamed from: a */
    public <T> C0577g<T> m5574a(Context context, Class cls) {
        if (this.f108b.get(cls) == null) {
            this.f108b.put(cls, new C0577g(context, cls));
        }
        return this.f108b.get(cls);
    }

    /* renamed from: a */
    public abstract void mo5148a();

    /* renamed from: a */
    public final void m5576a(int i) {
        C0574d.m5558a(i);
    }

    /* renamed from: a */
    public final void m5575a(Context context) {
        this.f107a = C0574d.m5557a(context);
    }

    /* renamed from: a */
    public void m5573a(C0576f c0576f) {
        if (c0576f == null) {
            c0576f = new C0576f();
        }
        m5571a(c0576f.m5544a());
        m5576a(c0576f.m5543b());
        m5575a(C0570a.m5580a().m5577b());
        mo5148a();
    }

    /* renamed from: a */
    public <T> void m5572a(Class<T> cls) {
        this.f107a.m5533a(cls);
    }

    /* renamed from: a */
    public final void m5571a(String str) {
        C0574d.m5556a(str);
    }
}
