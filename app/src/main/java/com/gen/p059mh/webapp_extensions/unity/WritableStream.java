package com.gen.p059mh.webapp_extensions.unity;

import android.util.Base64;
import com.gen.p059mh.webapps.unity.Unity;
import java.util.ArrayList;
import java.util.HashMap;

/* renamed from: com.gen.mh.webapp_extensions.unity.WritableStream */
/* loaded from: classes2.dex */
public class WritableStream extends Unity {
    Unity.Method write = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.WritableStream.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            int onWriteData = WritableStream.this.onWriteData(Base64.decode(((ArrayList) objArr[0]).get(0).toString(), 0));
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            hashMap.put("ret", Integer.valueOf(onWriteData));
            methodCallback.run(hashMap);
        }
    };
    Unity.Method finish = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.WritableStream.2
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            WritableStream.this.onFinish();
            methodCallback.run("success");
        }
    };

    public void onFinish() {
    }

    public int onWriteData(byte[] bArr) {
        return -1;
    }

    public WritableStream() {
        registerMethod("write", this.write);
        registerMethod("finish", this.finish);
    }
}
