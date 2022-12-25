package p007b.p014c.p015a.p018c;

import com.koushikdutta.async.http.Multimap;
import java.net.URLDecoder;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: b.c.a.c.f */
/* loaded from: classes2.dex */
public class C0689f implements Multimap.AbstractC2206a {
    @Override // com.koushikdutta.async.http.Multimap.AbstractC2206a
    public String decode(String str) {
        return URLDecoder.decode(str);
    }
}
