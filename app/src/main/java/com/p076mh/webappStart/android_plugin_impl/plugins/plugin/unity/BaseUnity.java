package com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity;

import android.util.Log;
import com.gen.p059mh.webapps.unity.Function;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.internal.LinkedTreeMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BaseUnity */
/* loaded from: classes3.dex */
public class BaseUnity extends Unity {
    protected final String TAG;

    public BaseUnity() {
        this.TAG = getClass().getSimpleName().length() < 15 ? getClass().getSimpleName() : getClass().getSimpleName().substring(0, 15);
    }

    public void releaseLock(Unity.MethodCallback methodCallback) {
        String str = this.TAG;
        Log.e(str, "call: " + methodCallback);
        methodCallback.run(null);
    }

    public void releaseLockWithCallBackSuccess(Unity.MethodCallback methodCallback, Map map) {
        map.put("success", true);
        map.put("complete", true);
        methodCallback.run(map);
    }

    public void releaseLockWithCallBackSuccess(Unity.MethodCallback methodCallback, Object obj) {
        methodCallback.run(obj);
    }

    public void releaseLockWithCallBackFailure(Unity.MethodCallback methodCallback) {
        HashMap hashMap = new HashMap();
        hashMap.put("success", false);
        hashMap.put("complete", true);
        methodCallback.run(hashMap);
    }

    public void releaseLockWithCallBack(Unity.MethodCallback methodCallback, Object... objArr) {
        responseFunctionWithCallBackGetSuccess(objArr);
        methodCallback.run(null);
    }

    public void responseFunctionCallBackSuccess(String str) {
        String str2 = this.TAG;
        Log.e(str2, "responseFunctionCallBackSuccess: " + str);
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("complete", true);
        event(str, null, hashMap);
    }

    public void responseFunctionWithCallBackGetSuccess(Object... objArr) {
        Logger.m4113i("responsePropertyGetSuccess start ");
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("complete", true);
        if (objArr.length == 0 || !(objArr[0] instanceof List)) {
            ((Function) objArr[0]).invoke(new Unity.MethodCallback() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BaseUnity.1
                @Override // com.gen.p059mh.webapps.unity.Unity.MethodCallback
                public void run(Object obj) {
                }
            }, hashMap);
            return;
        }
        List list = (List) objArr[0];
        if (list != null && !list.isEmpty()) {
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) list.get(0);
            if (linkedTreeMap.get("success") instanceof Function) {
                Function function = (Function) linkedTreeMap.get("success");
                Logger.m4112i("responsePropertyGetSuccess", function.getId());
                function.invoke(new Unity.MethodCallback() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BaseUnity.2
                    @Override // com.gen.p059mh.webapps.unity.Unity.MethodCallback
                    public void run(Object obj) {
                    }
                }, hashMap);
                return;
            }
            String str = this.TAG;
            Logger.m4114e(str, "responsePropertyGetSuccess: " + linkedTreeMap.get("success"));
            return;
        }
        Logger.m4114e(this.TAG, "responsePropertyGetSuccess: nothing  invoke");
    }

    @Deprecated
    public void responsePropertyGetSuccess(Map map, Object... objArr) {
        map.put("success", true);
        map.put("complete", true);
        Function function = (Function) ((LinkedTreeMap) ((List) objArr[0]).get(0)).get("success");
        Logger.m4112i("responsePropertyGetSuccess", function.getId());
        function.invoke(new Unity.MethodCallback() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BaseUnity.3
            @Override // com.gen.p059mh.webapps.unity.Unity.MethodCallback
            public void run(Object obj) {
            }
        }, map);
    }

    public <T> T getParamFromJs(String str, Object... objArr) {
        return (T) ((LinkedTreeMap) ((List) objArr[0]).get(0)).get(str);
    }
}
