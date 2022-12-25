package com.gen.p059mh.webapp_extensions.plugins;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.p002v4.app.NotificationCompat;
import android.util.Log;
import com.gen.p059mh.webapp_extensions.matisse.Matisse;
import com.gen.p059mh.webapp_extensions.matisse.MimeType;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.MediaStoreCompat;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnCheckedListener;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnSelectedListener;
import com.gen.p059mh.webapp_extensions.rxpermission.RxPermissions;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.FileUtils;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.PickImagePlugin */
/* loaded from: classes2.dex */
public class PickImagePlugin extends Plugin {
    public static final String[] CAMERA = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    MediaStoreCompat mMediaStoreCompat;
    Plugin.PluginCallback pluginCallback;

    public PickImagePlugin() {
        super("pick.image");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4112i("input", "input:" + str);
        Map map = (Map) new Gson().fromJson(str, new TypeToken<HashMap<String, Integer>>(this) { // from class: com.gen.mh.webapp_extensions.plugins.PickImagePlugin.1
        }.getType());
        if (map != null) {
            this.pluginCallback = pluginCallback;
            int intValue = ((Integer) map.get("sourceType")).intValue();
            int i = 1;
            if (intValue == 1) {
                if (map.get("count") != null) {
                    i = ((Integer) map.get("count")).intValue();
                }
                gotoImageSelect(i, false);
                return;
            } else if (intValue == 2) {
                gotoCamera();
                return;
            } else if (intValue == 3) {
                gotoImageSelect(map.get("count") == null ? 1 : ((Integer) map.get("count")).intValue(), true);
                return;
            } else {
                pluginCallback.response(null);
                return;
            }
        }
        pluginCallback.response(null);
    }

    private void gotoImageSelect(final int i, final boolean z) {
        getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.plugins.PickImagePlugin.2
            @Override // java.lang.Runnable
            public void run() {
                new RxPermissions(PickImagePlugin.this.getWebViewFragment().getActivity()).request(PickImagePlugin.CAMERA).subscribe(new Observer<Boolean>() { // from class: com.gen.mh.webapp_extensions.plugins.PickImagePlugin.2.1
                    @Override // io.reactivex.Observer
                    public void onComplete() {
                    }

                    @Override // io.reactivex.Observer
                    public void onError(Throwable th) {
                    }

                    @Override // io.reactivex.Observer
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override // io.reactivex.Observer
                    public void onNext(Boolean bool) {
                        if (bool.booleanValue()) {
                            Matisse.from(PickImagePlugin.this.getWebViewFragment().getFragment()).choose(MimeType.ofImage(), false).countable(true).capture(z).spanCount(4).maxSelectable(i).restrictOrientation(1).thumbnailScale(0.85f).setOnSelectedListener(new OnSelectedListener(this) { // from class: com.gen.mh.webapp_extensions.plugins.PickImagePlugin.2.1.2
                                @Override // com.gen.p059mh.webapp_extensions.matisse.listener.OnSelectedListener
                                public void onSelected(@NonNull List<Uri> list, @NonNull List<String> list2) {
                                    Log.e("onSelected", "onSelected: pathList=" + list2);
                                }
                            }).originalEnable(false).showSingleMediaType(true).maxOriginalSize(10).autoHideToolbarOnSingleTap(true).setOnCheckedListener(new OnCheckedListener(this) { // from class: com.gen.mh.webapp_extensions.plugins.PickImagePlugin.2.1.1
                                @Override // com.gen.p059mh.webapp_extensions.matisse.listener.OnCheckedListener
                                public void onCheck(boolean z2) {
                                    Log.e("isChecked", "onCheck: isChecked=" + z2);
                                }
                            }).forResult(13158, PickImagePlugin.this.getWebViewFragment().getTempDir().getAbsolutePath());
                            return;
                        }
                        Logger.m4113i("permission is not allowed");
                        HashMap hashMap = new HashMap();
                        hashMap.put("success", false);
                        hashMap.put(NotificationCompat.CATEGORY_MESSAGE, "permission is not allowed");
                        PickImagePlugin.this.pluginCallback.response(hashMap);
                    }
                });
            }
        });
    }

    private void gotoCamera() {
        this.mMediaStoreCompat = new MediaStoreCompat(getWebViewFragment().getActivity(), getWebViewFragment().getFragment(), getWebViewFragment().getTempDir().getAbsolutePath());
        this.mMediaStoreCompat.dispatchCaptureIntent(getWebViewFragment().getContext(), 13158);
    }

    public void finishPick(final List<String> list) {
        if (list == null) {
            String currentPhotoPath = this.mMediaStoreCompat.getCurrentPhotoPath();
            ArrayList arrayList = new ArrayList();
            arrayList.add(currentPhotoPath);
            list = arrayList;
        }
        new Thread() { // from class: com.gen.mh.webapp_extensions.plugins.PickImagePlugin.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                List list2 = list;
                if (list2 == null || list2.size() == 0) {
                    PickImagePlugin.this.pluginCallback.response(null);
                    return;
                }
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                HashMap hashMap2 = new HashMap();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                for (String str : list) {
                    File file = new File(str);
                    if (file.exists()) {
                        String absolutePath = PickImagePlugin.this.getWebViewFragment().getTempDir().getAbsolutePath();
                        HashMap hashMap3 = new HashMap();
                        if (str.startsWith(absolutePath)) {
                            String str2 = "tmp://" + str.replace(absolutePath, "");
                            hashMap3.put("path", str2);
                            arrayList3.add(str2);
                        } else {
                            FileUtils.copyFile(str, absolutePath + File.separator + file.getName());
                            StringBuilder sb = new StringBuilder();
                            sb.append("tmp:///");
                            sb.append(file.getName());
                            String sb2 = sb.toString();
                            hashMap3.put("path", sb2);
                            arrayList3.add(sb2);
                        }
                        hashMap3.put("size", Long.valueOf(file.length()));
                        arrayList2.add(hashMap3);
                    }
                }
                hashMap2.put("tempFilePaths", arrayList3);
                if (arrayList2.size() > 0) {
                    hashMap2.put("tempFiles", arrayList2);
                }
                hashMap.put(AopConstants.APP_PROPERTIES_KEY, hashMap2);
                PickImagePlugin.this.pluginCallback.response(hashMap);
            }
        }.start();
    }
}
