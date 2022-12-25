package com.taobao.gcanvas.bridges.p102rn.bridge;

import com.taobao.gcanvas.bridges.spec.bridge.IJSCallbackMap;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.taobao.gcanvas.bridges.rn.bridge.WAEJSCallbackMap */
/* loaded from: classes3.dex */
public class WAEJSCallbackMap implements IJSCallbackMap {
    private Map mMap = new HashMap();

    public Map getMap() {
        return this.mMap;
    }

    @Override // com.taobao.gcanvas.bridges.spec.bridge.IJSCallbackMap
    public void putInt(String str, int i) {
        this.mMap.put(str, Integer.valueOf(i));
    }

    @Override // com.taobao.gcanvas.bridges.spec.bridge.IJSCallbackMap
    public void putString(String str, String str2) {
        this.mMap.put(str, str2);
    }
}
