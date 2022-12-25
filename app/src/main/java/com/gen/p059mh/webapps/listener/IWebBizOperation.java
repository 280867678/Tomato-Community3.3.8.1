package com.gen.p059mh.webapps.listener;

import android.content.Intent;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.listener.IWebBizOperation */
/* loaded from: classes2.dex */
public interface IWebBizOperation {

    /* renamed from: com.gen.mh.webapps.listener.IWebBizOperation$-CC  reason: invalid class name */
    /* loaded from: classes2.dex */
    public final /* synthetic */ class CC {
        public static void $default$animationEnd(IWebBizOperation iWebBizOperation) {
        }

        public static void $default$checkPermissionAndStart(IWebBizOperation iWebBizOperation, Intent intent, int i, PhotoSwitchListener photoSwitchListener) {
        }

        public static void $default$doSwitchPhotoOrAlbum(IWebBizOperation iWebBizOperation, PhotoSwitchListener photoSwitchListener) {
        }

        public static void $default$loadEnd(IWebBizOperation iWebBizOperation) {
        }

        public static void $default$onRotateLandscape(IWebBizOperation iWebBizOperation) {
        }

        public static void $default$setMenuIcons(IWebBizOperation iWebBizOperation, List list) {
        }
    }

    void animationEnd();

    void checkPermissionAndStart(Intent intent, int i, PhotoSwitchListener photoSwitchListener);

    void doSwitchPhotoOrAlbum(PhotoSwitchListener photoSwitchListener);

    WebEngine getController();

    void loadEnd();

    void onRotateLandscape();

    void onScrollerChange(int i, int i2);

    void setMenuIcons(List<Map> list);
}
