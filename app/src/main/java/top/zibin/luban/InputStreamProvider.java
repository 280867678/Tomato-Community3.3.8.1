package top.zibin.luban;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public interface InputStreamProvider {
    String getPath();

    InputStream open() throws IOException;
}
