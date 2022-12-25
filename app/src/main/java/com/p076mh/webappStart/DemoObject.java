package com.p076mh.webappStart;

import android.support.p002v4.app.NotificationCompat;
import com.gen.p059mh.webapps.unity.Function;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import java.util.List;

/* renamed from: com.mh.webappStart.DemoObject */
/* loaded from: classes3.dex */
public class DemoObject extends Unity {
    Unity.Method testMethod = new Unity.Method() { // from class: com.mh.webappStart.DemoObject.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            Function function = (Function) ((List) objArr[0]).get(0);
            Logger.m4112i("testMethod", function.getId());
            function.invoke(new Unity.MethodCallback(this) { // from class: com.mh.webappStart.DemoObject.1.1
                @Override // com.gen.p059mh.webapps.unity.Unity.MethodCallback
                public void run(Object obj) {
                    Logger.m4112i("run response", obj);
                }
            }, "test");
            DemoObject.this.event(NotificationCompat.CATEGORY_EVENT, null, "test");
            methodCallback.run(new Unity.Method<String>(this) { // from class: com.mh.webappStart.DemoObject.1.2
                @Override // com.gen.p059mh.webapps.unity.Unity.Method
                public void call(Unity.MethodCallback<String> methodCallback2, Object... objArr2) {
                    Logger.m4112i(NotificationCompat.CATEGORY_CALL, objArr2.toString());
                    if (methodCallback2 != null) {
                        methodCallback2.run(null);
                    }
                }
            });
        }
    };

    public DemoObject() {
        registerMethod("test", this.testMethod);
    }
}
