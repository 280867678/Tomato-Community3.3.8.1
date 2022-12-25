package p007b.p014c.p015a.p018c;

import android.net.Uri;
import com.koushikdutta.async.http.Multimap;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: b.c.a.c.e */
/* loaded from: classes2.dex */
public class C0688e implements Multimap.AbstractC2206a {
    @Override // com.koushikdutta.async.http.Multimap.AbstractC2206a
    public String decode(String str) {
        return Uri.decode(str);
    }
}
