package com.gen.p059mh.webapp_extensions.manager;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.google.gson.Gson;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.manager.WACacheManager */
/* loaded from: classes2.dex */
public class WACacheManager {
    static WACacheManager instance = new WACacheManager();
    private File cachePath = WebApplication.getInstance().getApplication().getCacheDir();
    SharedPreferences sharedPreferences = WebApplication.getInstance().getApplication().getSharedPreferences("cache_surface", 0);
    List<String> cacheList = (List) new Gson().fromJson(this.sharedPreferences.getString("cache_list", "[]"), (Class<Object>) List.class);

    private WACacheManager() {
    }

    public static WACacheManager getInstance() {
        return instance;
    }

    public void save(final String str, final byte[] bArr) {
        new Thread(new Runnable() { // from class: com.gen.mh.webapp_extensions.manager.-$$Lambda$WACacheManager$LPtWxFvGnTi7LqMaG5tJwi-NnvI
            @Override // java.lang.Runnable
            public final void run() {
                WACacheManager.this.lambda$save$0$WACacheManager(str, bArr);
            }
        }).start();
    }

    public /* synthetic */ void lambda$save$0$WACacheManager(String str, byte[] bArr) {
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream;
        if (!this.cachePath.exists() || !this.cachePath.isDirectory()) {
            this.cachePath.mkdir();
        }
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(new File(this.cachePath, str));
                    try {
                        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    bufferedOutputStream.write(bArr);
                    bufferedOutputStream.close();
                    fileOutputStream.close();
                } catch (Exception e2) {
                    e = e2;
                    bufferedOutputStream2 = bufferedOutputStream;
                    e.printStackTrace();
                    if (bufferedOutputStream2 != null) {
                        bufferedOutputStream2.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    this.cacheList.add(str);
                    this.sharedPreferences.edit().putString("cache_list", new Gson().toJson(this.cacheList)).apply();
                } catch (Throwable th2) {
                    th = th2;
                    bufferedOutputStream2 = bufferedOutputStream;
                    if (bufferedOutputStream2 != null) {
                        try {
                            bufferedOutputStream2.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            throw th;
                        }
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                fileOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.cacheList.add(str);
        this.sharedPreferences.edit().putString("cache_list", new Gson().toJson(this.cacheList)).apply();
    }

    public boolean hasHash(String str) {
        return this.cacheList.contains(str);
    }

    public Bitmap loadFromFile(String str) {
        File file = new File(this.cachePath, str);
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        return BitmapFactory.decodeFile(file.getPath());
    }
}
