package com.taobao.gcanvas.bridges.p102rn.bridge;

import com.taobao.gcanvas.bridges.spec.bridge.IJSCallbackArray;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.taobao.gcanvas.bridges.rn.bridge.WAEJSCallbackArray */
/* loaded from: classes3.dex */
public class WAEJSCallbackArray implements IJSCallbackArray {
    private List mArray = new ArrayList();

    public List getArray() {
        return this.mArray;
    }
}
