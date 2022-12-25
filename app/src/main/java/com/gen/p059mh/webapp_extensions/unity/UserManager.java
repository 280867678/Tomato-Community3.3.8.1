package com.gen.p059mh.webapp_extensions.unity;

import com.gen.p059mh.webapps.unity.Unity;
import java.util.HashMap;

/* renamed from: com.gen.mh.webapp_extensions.unity.UserManager */
/* loaded from: classes2.dex */
public class UserManager extends Unity {
    Unity.Method checkSession = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.UserManager.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            methodCallback.run(hashMap);
        }
    };
    Unity.Method login = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.UserManager.2
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            methodCallback.run(hashMap);
        }
    };
    Unity.Method getUserInfo = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.UserManager.3
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            methodCallback.run(hashMap);
        }
    };

    public UserManager() {
        registerMethod("checkSession", this.checkSession);
        registerMethod("login", this.login);
        registerMethod("getUserInfo", this.getUserInfo);
    }
}
