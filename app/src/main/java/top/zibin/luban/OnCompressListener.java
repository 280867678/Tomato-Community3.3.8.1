package top.zibin.luban;

import java.io.File;

/* loaded from: classes4.dex */
public interface OnCompressListener {
    void onError(Throwable th);

    void onStart();

    void onSuccess(File file);
}
