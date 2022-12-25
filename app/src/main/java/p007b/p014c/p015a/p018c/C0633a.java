package p007b.p014c.p015a.p018c;

import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;

/* renamed from: b.c.a.c.a */
/* loaded from: classes2.dex */
public class C0633a implements AbstractC0690g, Cloneable {

    /* renamed from: a */
    public final String f224a;

    /* renamed from: b */
    public final String f225b;

    public C0633a(String str, String str2) {
        if (str != null) {
            this.f224a = str;
            this.f225b = str2;
            return;
        }
        throw new IllegalArgumentException("Name may not be null");
    }

    public Object clone() {
        return super.clone();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractC0690g)) {
            return false;
        }
        C0633a c0633a = (C0633a) obj;
        return this.f224a.equals(c0633a.f224a) && TextUtils.equals(this.f225b, c0633a.f225b);
    }

    @Override // p007b.p014c.p015a.p018c.AbstractC0690g
    public String getName() {
        return this.f224a;
    }

    @Override // p007b.p014c.p015a.p018c.AbstractC0690g
    public String getValue() {
        return this.f225b;
    }

    public int hashCode() {
        return this.f224a.hashCode() ^ this.f225b.hashCode();
    }

    public String toString() {
        return this.f224a + SimpleComparison.EQUAL_TO_OPERATION + this.f225b;
    }
}
