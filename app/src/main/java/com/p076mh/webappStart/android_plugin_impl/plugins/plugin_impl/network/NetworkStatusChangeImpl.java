package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.callback.JsCallBackKeys;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePairSharePluginImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.NetWorkUtil;
import com.p076mh.webappStart.util.bean.NetworkType;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.network.NetworkStatusChangeImpl */
/* loaded from: classes3.dex */
public class NetworkStatusChangeImpl extends BasePairSharePluginImpl {
    private final Map map = new HashMap();
    private NetStateChangeReceiver netStateChangeReceiver;

    public NetworkStatusChangeImpl(Boolean bool) {
        super(bool.booleanValue());
        String str = this.TAG;
        Logger.m4112i(str, "NetworkStatusChangeImpl: isOn = " + bool);
    }

    private void onNetworkStatusChange() {
        this.netStateChangeReceiver = new NetStateChangeReceiver(this.executor);
        WebApplication.getInstance().getApplication().registerReceiver(this.netStateChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private void offNetworkStatusChange() {
        if (this.netStateChangeReceiver != null) {
            WebApplication.getInstance().getApplication().unregisterReceiver(this.netStateChangeReceiver);
            this.netStateChangeReceiver = null;
            Logger.m4112i(this.TAG, "offNetworkStatusChange: 取消广播成功");
        }
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, Object obj, Plugin.PluginCallback pluginCallback) {
        if (this.isOn) {
            onNetworkStatusChange();
        } else {
            offNetworkStatusChange();
        }
        responseSuccess(pluginCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.network.NetworkStatusChangeImpl$NetStateChangeReceiver */
    /* loaded from: classes3.dex */
    public class NetStateChangeReceiver extends BroadcastReceiver {
        private Plugin.Executor eventSender;
        private NetworkType preNetworkType = null;

        public NetStateChangeReceiver(Plugin.Executor executor) {
            this.eventSender = executor;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Logger.m4112i(((BasePluginImpl) NetworkStatusChangeImpl.this).TAG, "NetStateChangeReceiver onReceive: ");
            NetworkType currentNetworkType = NetWorkUtil.getCurrentNetworkType();
            if (this.preNetworkType != currentNetworkType) {
                this.preNetworkType = currentNetworkType;
                NetworkStatusChangeImpl.this.map.clear();
                String str = ((BasePluginImpl) NetworkStatusChangeImpl.this).TAG;
                Logger.m4112i(str, "currentNetworkType: " + currentNetworkType);
                NetworkStatusChangeImpl.this.map.put("isConnected", Boolean.valueOf(currentNetworkType != NetworkType.NETWORK_NO));
                NetworkStatusChangeImpl.this.map.put(AopConstants.NETWORK_TYPE, currentNetworkType.toString());
                this.eventSender.executeEvent(JsCallBackKeys.ON_NETWORK_STATUS_CHANGE, NetworkStatusChangeImpl.this.map, null);
                Logger.m4112i(((BasePluginImpl) NetworkStatusChangeImpl.this).TAG, "eventSender success ");
            }
        }
    }
}
