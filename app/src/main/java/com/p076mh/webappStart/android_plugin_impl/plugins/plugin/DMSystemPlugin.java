package com.p076mh.webappStart.android_plugin_impl.plugins.plugin;

import com.gen.p059mh.webapps.Plugin;
import com.p076mh.webappStart.android_plugin_impl.beans.ChooseVideoParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.ClipboardDataParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.DownloadFileParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.DownloadTaskParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.KeepScreenOnParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.MakePhoneCallParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.ScanCodeParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.ScreenBrightnessParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.ShowLoadingParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.StartDeviceAccelerometerParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.StartDeviceMotionListeningParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.ToastParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.VibrateParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.ChooseVideoImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.GetBatteryForLowVersionImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.GetClipboardDataImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.GetScreenBrightnessImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.MakePhoneCallImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.OnMemoryWarningImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.ScanCodeImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.SetClipboardDataImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.SetKeepScreenOnImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.SetScreenBrightnessImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.UserCaptureScreenImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.VibrateImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.accelerometer.DeviceAccelerometerListeningImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.accelerometer.DeviceAccelerometerSwitchImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.compass.DeviceCompassListeningImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.compass.DeviceCompassSwitchImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.motion.DeviceMotionListeningImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.device.motion.DeviceMotionSwitchImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadFileRequestImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTaskActionImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.network.GetNetworkTypeImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.network.NetworkStatusChangeImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.p078ui.LoadingImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.p078ui.ToastImpl;
import com.p076mh.webappStart.util.GsonUtil;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin.DMSystemPlugin */
/* loaded from: classes3.dex */
public class DMSystemPlugin extends BasePlugin {
    public DMSystemPlugin() {
        super("system.tools");
    }

    private void getBattery(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(GetBatteryForLowVersionImpl.class).action(getWebViewFragment(), str, pluginCallback);
    }

    private void getClipboard(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(GetClipboardDataImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ClipboardDataParamsBean.class), pluginCallback);
    }

    private void setClipboard(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(SetClipboardDataImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ClipboardDataParamsBean.class), pluginCallback);
    }

    private void getNetwork(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(GetNetworkTypeImpl.class).action(getWebViewFragment(), str, pluginCallback);
    }

    private void keepScreenOn(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(SetKeepScreenOnImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) KeepScreenOnParamsBean.class), pluginCallback);
    }

    private void phoneCall(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(MakePhoneCallImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) MakePhoneCallParamsBean.class), pluginCallback);
    }

    private void setBright(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(SetScreenBrightnessImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ScreenBrightnessParamsBean.class), pluginCallback);
    }

    private void vibrate(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(VibrateImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) VibrateParamsBean.class), pluginCallback);
    }

    private void getBright(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(GetScreenBrightnessImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ScreenBrightnessParamsBean.class), pluginCallback);
    }

    private void scanCode(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(ScanCodeImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ScanCodeParamsBean.class), pluginCallback);
    }

    private void startDevice(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceMotionSwitchImpl.class, true).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) StartDeviceMotionListeningParamsBean.class), pluginCallback);
    }

    private void stopDevice(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceMotionSwitchImpl.class, false).action(getWebViewFragment(), null, pluginCallback);
    }

    private void onDevice(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceMotionListeningImpl.class, true).action(getWebViewFragment(), str, pluginCallback);
    }

    private void offDevice(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceMotionListeningImpl.class, false).action(getWebViewFragment(), str, pluginCallback);
    }

    private void onCapture(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(UserCaptureScreenImpl.class, true).action(getWebViewFragment(), str, pluginCallback);
    }

    private void offCapture(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(UserCaptureScreenImpl.class, false).action(getWebViewFragment(), str, pluginCallback);
    }

    private void onNetworkChange(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(NetworkStatusChangeImpl.class, true).action(getWebViewFragment(), str, pluginCallback);
    }

    private void offNetworkChange(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(NetworkStatusChangeImpl.class, false).action(getWebViewFragment(), str, pluginCallback);
    }

    private void chooseVideo(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(ChooseVideoImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ChooseVideoParamsBean.class), pluginCallback);
    }

    private void showLoading(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(LoadingImpl.class, true).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ShowLoadingParamsBean.class), pluginCallback);
    }

    private void hideLoading(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(LoadingImpl.class, false).action(getWebViewFragment(), null, pluginCallback);
    }

    private void showToast1(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(ToastImpl.class, true).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) ToastParamsBean.class), pluginCallback);
    }

    private void hideToast1(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(ToastImpl.class, false).action(getWebViewFragment(), null, pluginCallback);
    }

    private void memoryWarning(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(OnMemoryWarningImpl.class).action(getWebViewFragment(), null, pluginCallback);
    }

    private void startCompass(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceCompassSwitchImpl.class, true).action(getWebViewFragment(), null, pluginCallback);
    }

    private void stopCompass(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceCompassSwitchImpl.class, false).action(getWebViewFragment(), null, pluginCallback);
    }

    private void onCompass(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceCompassListeningImpl.class, true).action(getWebViewFragment(), null, pluginCallback);
    }

    private void offCompass(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceCompassListeningImpl.class, false).action(getWebViewFragment(), null, pluginCallback);
    }

    private void startAccelerometer(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceAccelerometerSwitchImpl.class, true).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) StartDeviceAccelerometerParamsBean.class), pluginCallback);
    }

    private void stopAccelerometer(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceAccelerometerSwitchImpl.class, false).action(getWebViewFragment(), null, pluginCallback);
    }

    private void onAccelerometer(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceAccelerometerListeningImpl.class, true).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) StartDeviceAccelerometerParamsBean.class), pluginCallback);
    }

    private void offAccelerometer(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePairSharePluginImpl(DeviceAccelerometerListeningImpl.class, false).action(getWebViewFragment(), null, pluginCallback);
    }

    private void downloadFile(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(DownloadFileRequestImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) DownloadFileParamsBean.class), pluginCallback);
    }

    private void abort(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(DownloadTaskActionImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) DownloadTaskParamsBean.class), pluginCallback);
    }

    private void onHeadersReceived(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(DownloadTaskActionImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) DownloadTaskParamsBean.class), pluginCallback);
    }

    private void offHeadersReceived(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(DownloadTaskActionImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) DownloadTaskParamsBean.class), pluginCallback);
    }

    private void onProgressUpdate(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(DownloadTaskActionImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) DownloadTaskParamsBean.class), pluginCallback);
    }

    private void offProgressUpdate(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(DownloadTaskActionImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) DownloadTaskParamsBean.class), pluginCallback);
    }
}
