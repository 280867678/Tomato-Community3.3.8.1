package com.gen.p059mh.webapp_extensions.unity;

import com.gen.p059mh.webapps.unity.Unity;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.unity.ReadableStream */
/* loaded from: classes2.dex */
public class ReadableStream extends Unity {
    Unity.Method read = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.ReadableStream.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            methodCallback.run(ReadableStream.this.inputData());
        }
    };

    public void complete() {
    }

    public Map inputData() {
        return null;
    }

    public ReadableStream() {
        registerMethod("read", this.read);
    }
}
