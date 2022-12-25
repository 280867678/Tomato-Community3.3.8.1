package com.p076mh.webappStart.android_plugin_impl.plugins.plugin;

import android.app.Activity;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.one.tomato.entity.C2516Ad;
import com.p076mh.webappStart.android_plugin_impl.beans.base.BasePluginParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.GsonUtil;
import com.p076mh.webappStart.util.ReflectManger;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin.BasePlugin */
/* loaded from: classes3.dex */
public class BasePlugin extends Plugin {
    protected final String TAG = getClass().getSimpleName();
    protected String callType;
    protected Activity mActivity;

    /* JADX INFO: Access modifiers changed from: protected */
    public BasePlugin(String str) {
        super(str);
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        String str2 = this.TAG;
        Logger.m4112i(str2, "process1: " + str);
        this.mActivity = getWebViewFragment().getActivity();
        processAfterEscape(str, pluginCallback);
    }

    public void processAfterEscape(String str, Plugin.PluginCallback pluginCallback) {
        this.callType = ((BasePluginParamsBean) GsonUtil.getInstance().fromJson(str, (Class<Object>) BasePluginParamsBean.class)).getCall();
        try {
            ReflectManger.invokeMethod(getClass(), this, this.callType, new Class[]{String.class, Plugin.PluginCallback.class}, new Object[]{str, pluginCallback});
        } catch (Exception e) {
            e.printStackTrace();
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            hashMap.put("complete", true);
            pluginCallback.response(hashMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BasePluginImpl getBasePluginImpl(Class cls) throws InstantiationException, IllegalAccessException {
        BasePluginImpl basePluginImpl = CachedImplsMapManager.getInstance().getImplsContainer().get(this.callType);
        if (basePluginImpl == null) {
            BasePluginImpl basePluginImpl2 = (BasePluginImpl) cls.newInstance();
            basePluginImpl2.setExecutor(this.executor);
            CachedImplsMapManager.getInstance().getImplsContainer().put(this.callType, basePluginImpl2);
            return basePluginImpl2;
        }
        return basePluginImpl;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BasePluginImpl getBasePairSharePluginImpl(Class cls, boolean z) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String str;
        if (this.callType.startsWith("on")) {
            str = "on_off_" + this.callType.substring(2);
        } else if (this.callType.startsWith("off")) {
            str = "on_off_" + this.callType.substring(3);
        } else if (this.callType.startsWith(C2516Ad.TYPE_START)) {
            str = "start_stop_" + this.callType.substring(5);
        } else if (this.callType.startsWith("stop")) {
            str = "start_stop_" + this.callType.substring(4);
        } else if (this.callType.startsWith("show")) {
            str = "show_hide_" + this.callType.substring(4);
        } else if (this.callType.startsWith("hide")) {
            str = "show_hide_" + this.callType.substring(4);
        } else {
            throw new IllegalArgumentException("illegal OnOff calltype:" + this.callType);
        }
        BasePluginImpl basePluginImpl = CachedImplsMapManager.getInstance().getImplsContainer().get(str);
        if (basePluginImpl == null) {
            basePluginImpl = (BasePluginImpl) cls.getConstructor(Boolean.class).newInstance(Boolean.valueOf(z));
            CachedImplsMapManager.getInstance().getImplsContainer().put(str, basePluginImpl);
        } else {
            ((BasePairSharePluginImpl) basePluginImpl).setOn(z);
        }
        basePluginImpl.setExecutor(getExecutor());
        return basePluginImpl;
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void unload() {
        super.unload();
        if (this.mActivity != null) {
            this.mActivity = null;
        }
    }
}
