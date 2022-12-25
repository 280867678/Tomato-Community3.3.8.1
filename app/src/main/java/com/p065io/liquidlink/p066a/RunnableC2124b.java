package com.p065io.liquidlink.p066a;

import android.content.ClipData;
import android.os.Build;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.io.liquidlink.a.b */
/* loaded from: classes3.dex */
public class RunnableC2124b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C2123a f1374a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC2124b(C2123a c2123a) {
        this.f1374a = c2123a;
    }

    @Override // java.lang.Runnable
    public void run() {
        ClipData m4093c;
        ClipData.Item itemAt;
        C2123a c2123a;
        ClipData newPlainText;
        m4093c = this.f1374a.m4093c();
        if (m4093c == null || m4093c.getItemCount() <= 0 || (itemAt = m4093c.getItemAt(0)) == null) {
            return;
        }
        if (this.f1374a.m4095a(Build.VERSION.SDK_INT >= 16 ? itemAt.getHtmlText() : null, itemAt.getText() == null ? null : itemAt.getText().toString()).m4085c() == 0) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            c2123a = this.f1374a;
            newPlainText = ClipData.newHtmlText(null, null, null);
        } else {
            c2123a = this.f1374a;
            newPlainText = ClipData.newPlainText(null, null);
        }
        c2123a.m4099a(newPlainText);
    }
}
