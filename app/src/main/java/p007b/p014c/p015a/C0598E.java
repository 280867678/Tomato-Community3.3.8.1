package p007b.p014c.p015a;

import java.util.ArrayList;
import p007b.p014c.p015a.C0599F;

/* renamed from: b.c.a.E */
/* loaded from: classes2.dex */
public class C0598E implements C0599F.AbstractC0601b<byte[]> {

    /* renamed from: a */
    public final /* synthetic */ C0599F f187a;

    public C0598E(C0599F c0599f) {
        this.f187a = c0599f;
    }

    @Override // p007b.p014c.p015a.C0599F.AbstractC0601b
    /* renamed from: a  reason: avoid collision after fix types in other method */
    public void mo5426a(byte[] bArr) {
        ArrayList arrayList;
        arrayList = this.f187a.f190l;
        arrayList.add(new String(bArr));
    }
}
