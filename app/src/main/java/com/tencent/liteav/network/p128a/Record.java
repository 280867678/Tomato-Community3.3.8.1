package com.tencent.liteav.network.p128a;

/* renamed from: com.tencent.liteav.network.a.e */
/* loaded from: classes3.dex */
public final class Record {

    /* renamed from: a */
    public final String f4814a;

    /* renamed from: b */
    public final int f4815b;

    /* renamed from: c */
    public final int f4816c;

    /* renamed from: d */
    public final long f4817d;

    public Record(String str, int i, int i2, long j) {
        this.f4814a = str;
        this.f4815b = i;
        this.f4816c = i2 >= 600 ? i2 : 600;
        this.f4817d = j;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Record)) {
            return false;
        }
        Record record = (Record) obj;
        return this.f4814a.equals(record.f4814a) && this.f4815b == record.f4815b && this.f4816c == record.f4816c && this.f4817d == record.f4817d;
    }

    /* renamed from: a */
    public boolean m1166a() {
        return this.f4815b == 5;
    }
}
