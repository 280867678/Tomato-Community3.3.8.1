package com.p076mh.webappStart.util;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.p002v4.app.NotificationManagerCompat;
import com.p076mh.webappStart.util.dialog.SystemDialogFactory;

/* renamed from: com.mh.webappStart.util.NotificationUtil */
/* loaded from: classes3.dex */
public class NotificationUtil {

    /* renamed from: com.mh.webappStart.util.NotificationUtil$OnNextLitener */
    /* loaded from: classes3.dex */
    public interface OnNextLitener {
        void onNext();
    }

    @RequiresApi(api = 19)
    public static void openNotificationSetting(Context context, OnNextLitener onNextLitener) {
        if (!isNotificationEnabled(context)) {
            showSettingDialog(context);
        } else if (onNextLitener == null) {
        } else {
            onNextLitener.onNext();
        }
    }

    @RequiresApi(api = 19)
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String packageName = context.getApplicationContext().getPackageName();
        int i = applicationInfo.uid;
        try {
            Class<?> cls = Class.forName(AppOpsManager.class.getName());
            return ((Integer) cls.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class).invoke(appOpsManager, Integer.valueOf(((Integer) cls.getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(i), packageName)).intValue() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void gotoSet(Context context) {
        Intent intent = new Intent();
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (i >= 21) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static void showSettingDialog(final Context context) {
        SystemDialogFactory.getConfirmDialog(context, "检测到通知权限没有打开，可能会影响APP正常使用。前去设置一下？", new DialogInterface.OnClickListener() { // from class: com.mh.webappStart.util.NotificationUtil.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                NotificationUtil.gotoSet(context);
            }
        }, new DialogInterface.OnClickListener() { // from class: com.mh.webappStart.util.NotificationUtil.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }
}
