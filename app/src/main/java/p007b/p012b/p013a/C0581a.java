package p007b.p012b.p013a;

/* renamed from: b.b.a.a */
/* loaded from: classes2.dex */
public class C0581a {

    /* renamed from: a */
    public static AbstractC0590i f123a;

    /* renamed from: b */
    public static C0585d f124b;

    /* renamed from: c */
    public static boolean f125c;

    /* renamed from: a */
    public static void m5532a(C0583c c0583c) {
        f124b = C0585d.m5507a(c0583c);
    }

    /* renamed from: a */
    public static void m5531a(AbstractC0590i abstractC0590i) {
        f123a = abstractC0590i;
    }

    /* renamed from: a */
    public static void m5530a(String str, int i) {
        AbstractC0590i abstractC0590i = f123a;
        if (abstractC0590i != null) {
            abstractC0590i.mo5254a(str, i);
        }
    }

    /* renamed from: b */
    public static void m5529b(String str, int i) {
        C0585d c0585d = f124b;
        if (c0585d != null) {
            c0585d.m5506a(str, i);
            return;
        }
        throw new RuntimeException("Please initialize Logan first");
    }
}
