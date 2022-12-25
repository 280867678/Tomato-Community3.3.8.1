package p007b.p014c.p015a.p018c;

import p007b.p014c.p015a.p018c.C0686d;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: b.c.a.c.c */
/* loaded from: classes2.dex */
public class RunnableC0660c implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ C0686d.C0687a f269a;

    /* renamed from: b */
    public final /* synthetic */ Exception f270b;

    public RunnableC0660c(C0686d.C0687a c0687a, Exception exc) {
        this.f269a = c0687a;
        this.f270b = exc;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f269a.mo3859b(this.f270b);
    }
}
