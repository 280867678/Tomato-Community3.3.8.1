package com.one.tomato.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import java.io.File;
import java.io.FileOutputStream;

/* loaded from: classes3.dex */
public class JavaAndJsMethod {
    private Activity activity;

    public JavaAndJsMethod(Activity activity, WebView webView) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void login() {
        LoginActivity.Companion.startActivity(this.activity);
    }

    @JavascriptInterface
    public void finish() {
        Activity activity = this.activity;
        if (activity == null || activity.isDestroyed()) {
            return;
        }
        this.activity.finish();
    }

    @JavascriptInterface
    public void takeScreenShot() {
        View decorView = this.activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap createBitmap = Bitmap.createBitmap(decorView.getDrawingCache());
        if (createBitmap != null) {
            try {
                File file = new File(FileUtil.getSDDCIMDir().getPath() + File.separator + AppUtil.getString(R.string.common_new_year_activity) + ".png");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(file));
                this.activity.sendBroadcast(intent);
                ToastUtil.showCenterToast((int) R.string.my_take_shot_success);
            } catch (Exception unused) {
                ToastUtil.showCenterToast((int) R.string.my_take_shot_fail);
            }
        }
    }

    @JavascriptInterface
    public void jumpToPotato() {
        AppUtil.startBrowseView(DBUtil.getSystemParam().getPotatoUrl());
    }

    @JavascriptInterface
    public void startActionView(int i, String str) {
        if (i == 1) {
            AppUtil.startBrowseView(str);
        } else if (i == 2) {
            AppUtil.startHtmlShowActivity(this.activity, str);
        } else {
            AppUtil.startBrowseView(str);
        }
    }
}
