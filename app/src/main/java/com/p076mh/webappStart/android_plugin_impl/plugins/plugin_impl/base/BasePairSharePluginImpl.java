package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl */
/* loaded from: classes3.dex */
public abstract class BasePairSharePluginImpl extends BasePluginImpl {
    protected boolean isOn;

    public BasePairSharePluginImpl() {
        this.isOn = false;
    }

    public BasePairSharePluginImpl(boolean z) {
        this.isOn = false;
        this.isOn = z;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean z) {
        this.isOn = z;
    }
}
