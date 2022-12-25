package org.xutils.http.body;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public interface RequestBody {
    long getContentLength();

    String getContentType();

    void setContentType(String str);

    void writeTo(OutputStream outputStream) throws IOException;
}
