package com.tencent.liteav.p125j;

import android.os.Environment;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/* renamed from: com.tencent.liteav.j.f */
/* loaded from: classes3.dex */
public class VideoUtil {
    /* renamed from: a */
    public static String m1420a(int i) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "txrtmp");
        if (!file.exists()) {
            file.mkdirs();
        }
        String valueOf = String.valueOf(System.currentTimeMillis() / 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String format = simpleDateFormat.format(new Date(Long.valueOf(valueOf + "000").longValue()));
        String str = null;
        if (i == 0) {
            str = String.format("TXVideo_%s_reverse.mp4", format);
        } else if (i == 1) {
            str = String.format("TXVideo_%s_process.mp4", format);
        }
        return file + "/" + str;
    }
}
