package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.annotation.SuppressLint;
import android.support.p002v4.view.ViewCompat;
import android.util.Base64;
import android.util.Log;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import cn.bertsir.zbar.format_control.BarcodeFormat;
import cn.bertsir.zbar.format_control.BarcodeType;
import cn.bertsir.zbar.p036Qr.ScanResult;
import com.gen.p059mh.webapp_extensions.R$raw;
import com.gen.p059mh.webapp_extensions.rxpermission.RxPermissions;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.beans.ScanCodeParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.MainHandler;
import com.tomatolive.library.http.utils.EncryptUtil;
import io.reactivex.functions.Consumer;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ScanCodeImpl */
/* loaded from: classes3.dex */
public class ScanCodeImpl extends BasePluginImpl<ScanCodeParamsBean> {
    private final int REQUEST_CODE_SCAN = 100;
    private final Map map = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ScanCodeImpl$1 */
    /* loaded from: classes3.dex */
    public class RunnableC23391 implements Runnable {
        final /* synthetic */ ScanCodeParamsBean val$paramsBean;
        final /* synthetic */ Plugin.PluginCallback val$pluginCallback;
        final /* synthetic */ IWebFragmentController val$webViewFragment;

        RunnableC23391(IWebFragmentController iWebFragmentController, ScanCodeParamsBean scanCodeParamsBean, Plugin.PluginCallback pluginCallback) {
            this.val$webViewFragment = iWebFragmentController;
            this.val$paramsBean = scanCodeParamsBean;
            this.val$pluginCallback = pluginCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            new RxPermissions(this.val$webViewFragment.getActivity()).request("android.permission.CAMERA").subscribe(new Consumer<Boolean>() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ScanCodeImpl.1.1
                @Override // io.reactivex.functions.Consumer
                public void accept(Boolean bool) throws Exception {
                    if (bool.booleanValue()) {
                        Log.e(((BasePluginImpl) ScanCodeImpl.this).TAG, "允许了权限");
                        RunnableC23391 runnableC23391 = RunnableC23391.this;
                        QrManager.getInstance().init(ScanCodeImpl.this.configQr(runnableC23391.val$paramsBean)).startScan(RunnableC23391.this.val$webViewFragment.getActivity(), new QrManager.OnScanResultCallback() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ScanCodeImpl.1.1.1
                            @Override // cn.bertsir.zbar.QrManager.OnScanResultCallback
                            public void onScanSuccess(ScanResult scanResult) {
                                RunnableC23391 runnableC233912 = RunnableC23391.this;
                                ScanCodeImpl.this.scanSuccess(scanResult, runnableC233912.val$pluginCallback);
                            }

                            @Override // cn.bertsir.zbar.QrManager.OnScanResultCallback
                            public void onFailure(Exception exc) {
                                exc.printStackTrace();
                                RunnableC23391 runnableC233912 = RunnableC23391.this;
                                ScanCodeImpl.this.responseFailure(runnableC233912.val$pluginCallback);
                            }
                        });
                        return;
                    }
                    Log.e(((BasePluginImpl) ScanCodeImpl.this).TAG, "未授权权限，部分功能不能使用");
                    RunnableC23391 runnableC233912 = RunnableC23391.this;
                    ScanCodeImpl.this.responseFailure(runnableC233912.val$pluginCallback);
                }
            });
        }
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    @SuppressLint({"CheckResult"})
    public void action(IWebFragmentController iWebFragmentController, ScanCodeParamsBean scanCodeParamsBean, Plugin.PluginCallback pluginCallback) {
        MainHandler.getInstance().post(new RunnableC23391(iWebFragmentController, scanCodeParamsBean, pluginCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public QrConfig configQr(ScanCodeParamsBean scanCodeParamsBean) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(BarcodeFormat.I25);
        return new QrConfig.Builder().setDesText("(识别二维码)").setShowDes(false).setShowLight(true).setShowTitle(true).setShowAlbum(!scanCodeParamsBean.isOnlyFromCamera()).setCornerColor(-1).setLineColor(-1).setLineSpeed(3000).setScanType(BarcodeType.ALL).setScanViewType(1).setCustomBarcodeFormatList(arrayList).setPlaySound(true).setNeedCrop(false).setDingPath(R$raw.qrcode).setIsOnlyCenter(true).setTitleText("扫描二维码").setTitleBackgroudColor(-1).setTitleTextColor(ViewCompat.MEASURED_STATE_MASK).setShowZoom(false).setAutoZoom(false).setFingerZoom(false).setScreenOrientation(1).setDoubleEngine(false).setOpenAlbumText("选择要识别的图片").setLooperScan(false).setLooperWaitTime(5000).create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanSuccess(ScanResult scanResult, Plugin.PluginCallback pluginCallback) {
        String str = this.TAG;
        Logger.m4112i(str, "onScanSuccess: " + scanResult);
        try {
            this.map.clear();
            this.map.put("result", scanResult.getResult());
            this.map.put("scanType", scanResult.getScanType());
            this.map.put("charSet", EncryptUtil.CHARSET);
            this.map.put("path", "TODO");
            this.map.put("rawData", Base64.encodeToString(scanResult.getResult().getBytes(EncryptUtil.CHARSET), 0));
            this.map.put("success", true);
            this.map.put("complete", true);
            responseSuccess(pluginCallback, this.map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            responseFailure(pluginCallback);
        }
    }
}
