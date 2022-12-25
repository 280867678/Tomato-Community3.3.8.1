package com.taobao.gcanvas.bridges.p102rn.bridge;

import com.taobao.gcanvas.bridges.spec.bridge.IJSCallbackDataFactory;
import com.taobao.gcanvas.bridges.spec.bridge.IJSCallbackMap;

/* renamed from: com.taobao.gcanvas.bridges.rn.bridge.WAEJSCallbackDataFactory */
/* loaded from: classes3.dex */
public class WAEJSCallbackDataFactory implements IJSCallbackDataFactory {
    @Override // com.taobao.gcanvas.bridges.spec.bridge.IJSCallbackDataFactory
    public IJSCallbackMap createJSCallbackMap() {
        return new WAEJSCallbackMap();
    }
}
