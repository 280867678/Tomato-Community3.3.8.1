package com.one.tomato.utils;

import android.support.p002v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.entity.AppInfoBean;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.foreground.ForegroundUtil;
import com.tomatolive.library.utils.DateUtils;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DataUploadUtil {
    public static void uploadUseTime() {
        long currentTimeMillis = System.currentTimeMillis();
        long appResumeTime = ForegroundUtil.getInstance().getAppResumeTime();
        if (appResumeTime == 0) {
            return;
        }
        final long j = (currentTimeMillis - appResumeTime) / 1000;
        if (j == 0) {
            return;
        }
        ApiImplService.Companion.getApiImplService().uploadUseTime(j, DBUtil.getMemberId(), DBUtil.getUserInfo().getUserType()).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i("用户使用时长数据上报成功，使用时长：" + j);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3786e("用户使用时长数据上报失败，使用时长：" + j);
            }
        });
    }

    public static void uploadQRToServer() {
        ApiImplService.Companion.getApiImplService().uploadQRToServer(DBUtil.getMemberId()).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i("保存二维码上报成功");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3786e("保存二维码上报失败");
            }
        });
    }

    public static void uploadAD(int i, int i2) {
        final String str = i2 == 1 ? "浏览" : "点击";
        ApiImplService.Companion.getApiImplService().uploadAD(i, DBUtil.getMemberId(), i2).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.3
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i(str + "广告上报成功");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3786e(str + "广告上报失败");
            }
        });
    }

    public static void uploadAppListInfo() {
        ArrayList arrayList = new ArrayList(128);
        List arrayList2 = new ArrayList(128);
        final List<AppInfoBean> paKageAll = DeviceInfoUtil.getPaKageAll(BaseApplication.getApplication().getPackageManager());
        try {
            String readSDCardData = FileUtil.readSDCardData(new File(FileUtil.getTextCacheDir(), "TerminalApp").getPath());
            if (!TextUtils.isEmpty(readSDCardData)) {
                try {
                    arrayList2 = (List) BaseApplication.getGson().fromJson(readSDCardData, new TypeToken<List<AppInfoBean>>() { // from class: com.one.tomato.utils.DataUploadUtil.5
                    }.getType());
                } catch (Exception e) {
                    e.printStackTrace();
                    arrayList2 = null;
                }
            }
            if (arrayList2 != null && arrayList2.size() > 0) {
                for (AppInfoBean appInfoBean : paKageAll) {
                    if (!readSDCardData.contains(appInfoBean.packageName)) {
                        arrayList.add(appInfoBean);
                    }
                }
            } else {
                arrayList.addAll(paKageAll);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (arrayList.size() > 0) {
            ApiImplService.Companion.getApiImplService().uploadAppListInfo(BaseApplication.getGson().toJson(arrayList, new TypeToken<List<AppInfoBean>>() { // from class: com.one.tomato.utils.DataUploadUtil.6
            }.getType())).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.7
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(Object obj) {
                    LogUtil.m3784i("app应用列表上报成功");
                    try {
                        FileUtil.writeSDCardData(new File(FileUtil.getTextCacheDir(), r2).getPath(), BaseApplication.getGson().toJson(paKageAll, new TypeToken<List<AppInfoBean>>(this) { // from class: com.one.tomato.utils.DataUploadUtil.7.1
                        }.getType()));
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    LogUtil.m3786e("app应用列表上报失败");
                }
            });
        }
    }

    public static void activateApp() {
        final long currentTimeMillis = System.currentTimeMillis() / DateUtils.ONE_HOUR_MILLIONS;
        long j = PreferencesUtil.getInstance().getLong("get_current_hour");
        if (j == 0 || currentTimeMillis - j >= 1) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("appVer", AppUtil.getVersionCode() + "");
            hashMap.put("equiCompany", DeviceInfoUtil.getDeviceBrand());
            hashMap.put("equiModel", DeviceInfoUtil.getDeviceTypeName());
            hashMap.put("os", DeviceInfoUtil.getPhoneOSVersion());
            hashMap.put("netInfo", NetWorkUtil.getNetWorkType());
            hashMap.put("equiId", DeviceInfoUtil.getUniqueDeviceID());
            hashMap.put("memberId", DBUtil.getMemberId() + "");
            hashMap.put("versionNo", AppUtil.getVersionCodeStr());
            hashMap.put("equiName", DeviceInfoUtil.getDeviceNick());
            ApiImplService.Companion.getApiImplService().activeApp(hashMap).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.8
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(Object obj) {
                    LogUtil.m3784i("激活app成功");
                    PreferencesUtil.getInstance().putLong("get_current_hour", currentTimeMillis);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    LogUtil.m3786e("激活app失败");
                }
            });
        }
    }

    public static void uploadPostBrowse(int i, int i2) {
        ApiImplService.Companion.getApiImplService().postUpdatePostBro(i, i2).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.9
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i("帖子浏览上报成功");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3786e("帖子浏览上报失败");
            }
        });
    }

    public static void uploadRecommendPostBrowse(int i, int i2) {
        ApiImplService.Companion.getApiImplService().postUpdatePostBro(i, i2).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.10
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i("帖子浏览上报成功");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3786e("帖子浏览上报失败");
            }
        });
    }

    public static void uploadVideoPlayHalf(int i) {
        ApiImplService.Companion.getApiImplService().uploadVideoPlayHalf(i, DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.11
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i("视频半播上报成功");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3786e("视频半播上报失败");
            }
        });
    }

    public static void uploadVideoPlayWhole(int i) {
        ApiImplService.Companion.getApiImplService().uploadVideoPlayWhole(i, DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.12
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i("视频完播上报成功");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3786e("视频完播上报失败");
            }
        });
    }

    public static void uploadPlayError(String str, int i, String str2, int i2) {
        String str3;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", str);
            jSONObject.put("postId", i);
            jSONObject.put("playFlag", str2);
            jSONObject.put(NotificationCompat.CATEGORY_PROGRESS, i2);
            str3 = jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str3 = "";
        }
        ApiImplService.Companion.getApiImplService().uploadPlayError("videoPlayError", str3).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.13
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i("视频播放失败上报成功");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3786e("视频播放失败上报失败");
            }
        });
    }

    public static void uploadPostShare(String str) {
        ApiImplService.Companion.getApiImplService().reportPostShare(str).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.utils.DataUploadUtil.14
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3784i("分享失败上报成功");
            }
        });
    }
}
