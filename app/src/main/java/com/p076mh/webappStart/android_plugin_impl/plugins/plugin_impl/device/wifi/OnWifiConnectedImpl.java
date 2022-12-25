package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.beans.WxWifiInfo;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.WxAPIConfig;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.wifi.OnWifiConnectedImpl */
/* loaded from: classes3.dex */
public class OnWifiConnectedImpl extends BasePluginImpl<String> {
    private final Map map = new HashMap();

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, String str, Plugin.PluginCallback pluginCallback) throws Exception {
        WxAPIConfig.WiFi.isOnWifiConnectedCalled = true;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        iWebFragmentController.getContext().registerReceiver(new WifiReceiver(), intentFilter);
        responseSuccess(pluginCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.wifi.OnWifiConnectedImpl$WifiReceiver */
    /* loaded from: classes3.dex */
    public class WifiReceiver extends BroadcastReceiver {
        WifiReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.STATE_CHANGE")) {
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                if (networkInfo.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                    Logger.m4112i(((BasePluginImpl) OnWifiConnectedImpl.this).TAG, "wifi断开");
                } else if (!networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                } else {
                    WifiInfo connectionInfo = ((WifiManager) context.getSystemService(AopConstants.WIFI)).getConnectionInfo();
                    String str = ((BasePluginImpl) OnWifiConnectedImpl.this).TAG;
                    Logger.m4112i(str, "连接到网络 " + connectionInfo.getSSID());
                    WxWifiInfo wxWifiInfo = new WxWifiInfo();
                    wxWifiInfo.setSSID(wxWifiInfo.getSSID());
                    OnWifiConnectedImpl.this.map.clear();
                    OnWifiConnectedImpl.this.map.put("success", true);
                    OnWifiConnectedImpl.this.map.put("complete", true);
                    OnWifiConnectedImpl.this.map.put(AopConstants.WIFI, wxWifiInfo);
                }
            }
        }
    }
}
