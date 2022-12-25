package com.taobao.gcanvas.bridges.spec.module;

/* loaded from: classes3.dex */
public interface IGBridgeModule<JSCallback> {

    /* loaded from: classes3.dex */
    public enum ContextType {
        _2D(0),
        _3D(1);
        
        private int value;

        ContextType(int i) {
            this.value = i;
        }

        public int value() {
            return this.value;
        }
    }
}
