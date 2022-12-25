package com.gen.p059mh.webapp_extensions.views;

import android.text.SpannableString;
import android.view.View;
import java.io.File;

/* renamed from: com.gen.mh.webapp_extensions.views.ICover */
/* loaded from: classes2.dex */
public interface ICover {

    /* renamed from: com.gen.mh.webapp_extensions.views.ICover$-CC  reason: invalid class name */
    /* loaded from: classes2.dex */
    public final /* synthetic */ class CC {
        public static void $default$loadFail(ICover iCover, String str) {
        }

        public static void $default$loadProcess(ICover iCover, SpannableString spannableString) {
        }

        public static void $default$onReady(ICover iCover) {
        }

        public static void $default$onUpdate(ICover iCover) {
        }

        public static void $default$setIconFile(ICover iCover, File file) {
        }

        public static void $default$setIconUrl(ICover iCover, String str) {
        }

        public static void $default$setTitle(ICover iCover, String str) {
        }

        public static void $default$setWAppIconShow(ICover iCover, boolean z) {
        }
    }

    void loadFail(String str);

    void loadProcess(SpannableString spannableString);

    void loadingRelease();

    void loadingStop();

    void onReady();

    void onRotateLandscape();

    void onUpdate();

    View provideView();

    void removeAllViews();

    void setCloseBtnShow(boolean z);

    void setIconFile(File file);

    void setIconUrl(String str);

    void setTitle(String str);

    void setWAppIconShow(boolean z);

    void startLoading();
}
