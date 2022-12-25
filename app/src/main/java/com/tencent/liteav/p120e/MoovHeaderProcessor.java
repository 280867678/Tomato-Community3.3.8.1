package com.tencent.liteav.p120e;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.videoediter.ffmpeg.jni.TXFFQuickJointerJNI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/* renamed from: com.tencent.liteav.e.n */
/* loaded from: classes3.dex */
public class MoovHeaderProcessor {

    /* renamed from: a */
    private static MoovHeaderProcessor f3696a;

    /* renamed from: b */
    private final VideoOutputConfig f3697b = VideoOutputConfig.m2457a();

    /* renamed from: a */
    public static MoovHeaderProcessor m2096a() {
        if (f3696a == null) {
            f3696a = new MoovHeaderProcessor();
        }
        return f3696a;
    }

    private MoovHeaderProcessor() {
    }

    /* renamed from: b */
    public void m2095b() {
        long currentTimeMillis = System.currentTimeMillis();
        if (!this.f3697b.m2453b() && this.f3697b.m2450c()) {
            File file = new File(VideoOutputConfig.m2457a().f3371i);
            File file2 = new File(file.getParentFile(), "moov_tmp.mp4");
            if (file2.exists()) {
                file2.delete();
            }
            try {
                file2.createNewFile();
                TXFFQuickJointerJNI tXFFQuickJointerJNI = new TXFFQuickJointerJNI();
                tXFFQuickJointerJNI.setDstPath(file2.getAbsolutePath());
                ArrayList arrayList = new ArrayList();
                arrayList.add(file.getAbsolutePath());
                tXFFQuickJointerJNI.setSrcPaths(arrayList);
                boolean z = tXFFQuickJointerJNI.start() == 0;
                tXFFQuickJointerJNI.stop();
                tXFFQuickJointerJNI.destroy();
                if (!z) {
                    TXCLog.m2914e("MoovHeaderProcessor", "moov: change to moov type video file error!!");
                } else if (!file.delete()) {
                    TXCLog.m2914e("MoovHeaderProcessor", "moov: delete original file error!");
                } else {
                    boolean renameTo = file2.renameTo(file);
                    TXCLog.m2913i("MoovHeaderProcessor", "moov: rename file success = " + renameTo);
                    long currentTimeMillis2 = System.currentTimeMillis();
                    TXCLog.m2915d("MoovHeaderProcessor", "doProcessMoovHeader cost time:" + String.valueOf(currentTimeMillis2 - currentTimeMillis));
                }
            } catch (IOException e) {
                e.printStackTrace();
                TXCLog.m2914e("MoovHeaderProcessor", "moov: create moov tmp file error!");
            }
        }
    }
}
