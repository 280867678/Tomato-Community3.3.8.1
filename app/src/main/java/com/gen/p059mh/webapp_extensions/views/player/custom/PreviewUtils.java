package com.gen.p059mh.webapp_extensions.views.player.custom;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.common.use.util.PathUtils;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.MD5Utils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.ExecutionException;
import timber.log.Timber;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.PreviewUtils */
/* loaded from: classes2.dex */
public class PreviewUtils {
    public static String folder = PathUtils.getExternalStoragePath() + File.separator + "previewImg";
    public static String firstStr = "hlw_";

    static {
        String str = firstStr + "l_";
    }

    public static void startPreView(String str, List list, ImageView imageView) {
        try {
            String str2 = URLDecoder.decode(str + list.get(1), EncryptUtil.CHARSET).split("#")[0];
            File file = new File(folder);
            if (!file.exists()) {
                file.mkdirs();
            }
            String str3 = folder + File.separator + firstStr + MD5Utils.to32Str(list.get(1).toString()) + ".jpg";
            Logger.m4113i(str3);
            new C17401(str3, imageView, URLDecoder.decode(str + list.get(1), EncryptUtil.CHARSET).split("#")[1].split(SimpleComparison.EQUAL_TO_OPERATION)[1].split(","), str2).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: com.gen.mh.webapp_extensions.views.player.custom.PreviewUtils$1 */
    /* loaded from: classes2.dex */
    static class C17401 extends Thread {
        Bitmap bitmap = null;
        File file;
        final /* synthetic */ ImageView val$imageView;
        final /* synthetic */ String[] val$params;
        final /* synthetic */ String val$previewDir;
        final /* synthetic */ String val$url;

        C17401(String str, ImageView imageView, String[] strArr, String str2) {
            this.val$previewDir = str;
            this.val$imageView = imageView;
            this.val$params = strArr;
            this.val$url = str2;
            this.file = new File(this.val$previewDir);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                if (this.file.exists()) {
                    Logger.m4113i(this.file.getPath());
                    this.bitmap = Glide.with(this.val$imageView.getContext()).mo6717asBitmap().mo6726load(this.file).mo6653apply((BaseRequestOptions<?>) new RequestOptions().mo6661diskCacheStrategy(DiskCacheStrategy.NONE).mo6701skipMemoryCache(true)).into(Integer.valueOf(this.val$params[2]).intValue() * 5, Integer.valueOf(this.val$params[3]).intValue() * 5).get();
                } else {
                    Logger.m4113i(this.val$url);
                    this.bitmap = Glide.with(this.val$imageView.getContext()).mo6717asBitmap().mo6729load(this.val$url).into(Integer.valueOf(this.val$params[2]).intValue() * 5, Integer.valueOf(this.val$params[3]).intValue() * 5).get();
                    PreviewUtils.saveBitmap(this.bitmap, this.val$previewDir);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e2) {
                e2.printStackTrace();
            }
            this.val$imageView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.custom.PreviewUtils.1.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PreviewUtils.showImage(C17401.this.val$params, C17401.this.bitmap, C17401.this.val$imageView);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            });
        }
    }

    public static void showImage(String[] strArr, Bitmap bitmap, final ImageView imageView) {
        final Bitmap createBitmap = Bitmap.createBitmap(bitmap, Integer.valueOf(strArr[0]).intValue(), Integer.valueOf(strArr[1]).intValue(), Integer.valueOf(strArr[2]).intValue(), Integer.valueOf(strArr[3]).intValue());
        imageView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.custom.PreviewUtils.2
            @Override // java.lang.Runnable
            public void run() {
                imageView.setImageBitmap(createBitmap);
            }
        });
    }

    public static void saveBitmap(Bitmap bitmap, String str) {
        Timber.Tree tag = Timber.tag("previewUtil");
        tag.mo18i("save path==" + str, new Object[0]);
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
        try {
            File file = new File(str);
            file.createNewFile();
            bitmap.compress(compressFormat, 100, new FileOutputStream(file, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
