package com.zzhoujay.richtext.callback;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public interface BitmapStream extends Closeable {
    InputStream getInputStream() throws IOException;
}
