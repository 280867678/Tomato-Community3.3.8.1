package com.tencent.liteav.beauty.p115b.p116a;

import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.beauty.NativeLoad;

/* renamed from: com.tencent.liteav.beauty.b.a.c */
/* loaded from: classes3.dex */
public class TXCTILSkinFilter extends TXCGPUFilter {
    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: c */
    public boolean mo2653c() {
        NativeLoad.getInstance();
        this.f2612a = NativeLoad.nativeLoadGLProgram(6);
        if (this.f2612a != 0 && mo1321a()) {
            this.f2618g = true;
        } else {
            this.f2618g = false;
        }
        mo2643d();
        return this.f2618g;
    }
}
