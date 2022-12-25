package com.one.tomato.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.AppIdHistory;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsJVM;

/* compiled from: AppIdHistoryManger.kt */
/* loaded from: classes3.dex */
public final class AppIdHistoryManger {
    public static final AppIdHistoryManger INSTANCE = new AppIdHistoryManger();

    private AppIdHistoryManger() {
    }

    public final void getAppidHistory() {
        ApiImplService.Companion.getApiImplService().getAppIdHistory().subscribeOn(Schedulers.computation()).subscribe(new ApiDisposableObserver<ArrayList<AppIdHistory>>() { // from class: com.one.tomato.utils.AppIdHistoryManger$getAppidHistory$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<AppIdHistory> arrayList) {
                if (arrayList == null || arrayList.isEmpty()) {
                    return;
                }
                PreferencesUtil.getInstance().putString("appid_manger", BaseApplication.getGson().toJson(arrayList));
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("获取appid历史记录失败____");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3787d("yan", sb.toString());
            }
        });
    }

    public final void deleteHistoryAppId(Activity context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        String string = PreferencesUtil.getInstance().getString("appid_manger");
        if (!TextUtils.isEmpty(string)) {
            ArrayList<AppIdHistory> arrayList = (ArrayList) BaseApplication.getGson().fromJson(string, new TypeToken<ArrayList<AppIdHistory>>() { // from class: com.one.tomato.utils.AppIdHistoryManger$deleteHistoryAppId$fromJson$1
            }.getType());
            if (!(arrayList instanceof ArrayList)) {
                return;
            }
            String packageName = context.getPackageName();
            for (AppIdHistory appIdHistory : arrayList) {
                if (!Intrinsics.areEqual(appIdHistory.getAppid(), packageName)) {
                    AppIdHistoryManger appIdHistoryManger = INSTANCE;
                    String appid = appIdHistory.getAppid();
                    Intrinsics.checkExpressionValueIsNotNull(appid, "it.appid");
                    if (appIdHistoryManger.isAvilible(context, appid)) {
                        AppIdHistoryManger appIdHistoryManger2 = INSTANCE;
                        String appid2 = appIdHistory.getAppid();
                        Intrinsics.checkExpressionValueIsNotNull(appid2, "it.appid");
                        appIdHistoryManger2.showDeleteDialog(context, appid2);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deletePackage(Activity activity, String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DELETE");
        intent.setData(Uri.parse("package:" + str));
        intent.addFlags(268435456);
        activity.startActivity(intent);
    }

    private final boolean isAvilible(Activity activity, String str) {
        boolean equals;
        BaseApplication baseApplication = BaseApplication.instance;
        Intrinsics.checkExpressionValueIsNotNull(baseApplication, "BaseApplication.instance");
        List<PackageInfo> pinfo = baseApplication.getPackageManager().getInstalledPackages(0);
        Intrinsics.checkExpressionValueIsNotNull(pinfo, "pinfo");
        int size = pinfo.size();
        for (int i = 0; i < size; i++) {
            PackageInfo packageInfo = pinfo.get(i);
            if ((packageInfo.applicationInfo.flags & 1) == 0) {
                String str2 = packageInfo.packageName;
                LogUtil.m3787d("pm", str2);
                equals = StringsJVM.equals(str2, str, true);
                if (equals) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [T, com.one.tomato.dialog.CustomAlertDialog] */
    private final void showDeleteDialog(final Activity activity, final String str) {
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = new CustomAlertDialog(activity);
        ((CustomAlertDialog) ref$ObjectRef.element).setTitle("提示");
        ((CustomAlertDialog) ref$ObjectRef.element).setMessage("检查到旧包存在，请删除老包避免影响新包使用！");
        ((CustomAlertDialog) ref$ObjectRef.element).setConfirmButton("删除", new View.OnClickListener() { // from class: com.one.tomato.utils.AppIdHistoryManger$showDeleteDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppIdHistoryManger.INSTANCE.deletePackage(activity, str);
                ((CustomAlertDialog) ref$ObjectRef.element).dismiss();
            }
        });
        ((CustomAlertDialog) ref$ObjectRef.element).setCancelButton("取消", new View.OnClickListener() { // from class: com.one.tomato.utils.AppIdHistoryManger$showDeleteDialog$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ((CustomAlertDialog) Ref$ObjectRef.this.element).dismiss();
            }
        });
        ((CustomAlertDialog) ref$ObjectRef.element).show();
    }
}
