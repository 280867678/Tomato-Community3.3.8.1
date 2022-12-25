package com.gen.p059mh.webapps.server.worker;

import com.eclipsesource.p056v8.Releasable;
import com.gen.p059mh.webapps.utils.Logger;

/* renamed from: com.gen.mh.webapps.server.worker.Console */
/* loaded from: classes2.dex */
public class Console {
    public void log(Object obj) {
        Logger.m4112i("[INFO] ", obj.toString());
        if (obj instanceof Releasable) {
            ((Releasable) obj).release();
        }
    }

    public void error(Object obj) {
        Logger.m4114e("[ERROR] ", obj.toString());
        if (obj instanceof Releasable) {
            ((Releasable) obj).release();
        }
        throw new RuntimeException("");
    }

    public void error1(Object obj, Object obj2) {
        Logger.m4114e("[ERROR1] ", obj.toString());
        Logger.m4114e("[ERROR1] ", obj2.toString());
        if (obj instanceof Releasable) {
            ((Releasable) obj).release();
        }
        throw new RuntimeException("");
    }

    public void warn(Object obj) {
        Logger.m4108w("[WARN] ", obj.toString());
        if (obj instanceof Releasable) {
            ((Releasable) obj).release();
        }
    }
}
