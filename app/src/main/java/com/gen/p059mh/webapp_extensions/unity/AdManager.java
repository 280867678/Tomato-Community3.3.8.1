package com.gen.p059mh.webapp_extensions.unity;

import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import java.util.HashMap;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.unity.AdManager */
/* loaded from: classes2.dex */
public class AdManager extends Unity {
    Unity.Method getAd = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.AdManager.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            HashMap hashMap = new HashMap();
            hashMap.put("width", 300);
            hashMap.put("height", 90);
            hashMap.put("type", "blank");
            methodCallback.run(hashMap);
        }
    };
    Unity.Method click = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.AdManager.2
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4112i("Clicked Ad ", ((List) objArr[0]).get(0));
            methodCallback.run(null);
        }
    };

    public AdManager() {
        registerMethod("getAd", this.getAd);
        registerMethod("click", this.click);
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
    }
}
