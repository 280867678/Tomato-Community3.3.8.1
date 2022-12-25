package com.gen.p059mh.webapp_extensions.plugins;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import com.gen.p059mh.webapps.Plugin;
import com.p076mh.webappStart.util.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/* renamed from: com.gen.mh.webapp_extensions.plugins.ScreenShotPlugin */
/* loaded from: classes2.dex */
public class ScreenShotPlugin extends Plugin {
    public ScreenShotPlugin() {
        super("screenshot");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        boolean z = false;
        try {
            try {
                View decorView = getWebViewFragment().getActivity().getWindow().getDecorView();
                decorView.setDrawingCacheEnabled(true);
                decorView.buildDrawingCache();
                Bitmap drawingCache = decorView.getDrawingCache();
                File file = new File(Constants.GALLERY_PATH + File.separator + System.currentTimeMillis() + ".jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                boolean compress = drawingCache.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(file));
                    getWebViewFragment().getContext().sendBroadcast(intent);
                    z = compress;
                } catch (Exception e) {
                    e = e;
                    z = compress;
                    e.printStackTrace();
                    HashMap hashMap = new HashMap();
                    hashMap.put("success", Boolean.valueOf(z));
                    pluginCallback.response(hashMap);
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("success", Boolean.valueOf(z));
        pluginCallback.response(hashMap2);
    }
}
