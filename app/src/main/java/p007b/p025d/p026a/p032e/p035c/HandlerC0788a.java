package p007b.p025d.p026a.p032e.p035c;

import android.os.Handler;
import android.os.Looper;

/* renamed from: b.d.a.e.c.a */
/* loaded from: classes2.dex */
public class HandlerC0788a extends Handler {

    /* renamed from: a */
    public static volatile HandlerC0788a f620a;

    public HandlerC0788a() {
        super(Looper.getMainLooper());
    }

    /* renamed from: a */
    public static HandlerC0788a m5019a() {
        if (f620a == null) {
            synchronized (HandlerC0788a.class) {
                if (f620a == null) {
                    f620a = new HandlerC0788a();
                }
            }
        }
        return f620a;
    }
}
