package com.one.tomato.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.support.p002v4.content.FileProvider;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.p085ui.webview.HtmlShowActivity;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class AppUtil {
    private static Context mContext = BaseApplication.getApplication();
    public static Map<Integer, Long> fastClickMap = new HashMap();

    public static boolean isEmojiCharacter(char c) {
        return c == 0 || c == '\t' || c == '\n' || c == '\r' || (c >= ' ' && c <= 55295) || ((c >= 57344 && c <= 65533) || (c >= 0 && c <= 65535));
    }

    public static Resources getResources() {
        return mContext.getResources();
    }

    public static int getVersionCode() {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getVersionCodeStr() {
        return getVersionCode() + "001201";
    }

    public static String getVersionName() {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getPacketName() {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).packageName;
        } catch (Exception e) {
            e.printStackTrace();
            return "com.one.tomato";
        }
    }

    public static void installApk(String str) {
        Uri fromFile;
        File file = new File(str);
        if (file.exists()) {
            Intent intent = new Intent();
            intent.setFlags(268435456);
            intent.setAction("android.intent.action.VIEW");
            if (DeviceInfoUtil.isOverNougat()) {
                intent.addFlags(1);
                Context context = mContext;
                fromFile = FileProvider.getUriForFile(context, getPacketName() + ".apkProvider", file);
            } else {
                fromFile = Uri.fromFile(file);
            }
            intent.setDataAndType(fromFile, "application/vnd.android.package-archive");
            mContext.startActivity(intent);
            return;
        }
        ToastUtil.showCenterToast((int) R.string.common_file_not_exist);
    }

    public static boolean compareAPKVersion(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String[] split = str.split("\\.");
            String[] split2 = str2.split("\\.");
            int length = split.length;
            int length2 = split2.length;
            if (length2 > length) {
                String[] strArr = new String[length2];
                for (int i = 0; i < length; i++) {
                    strArr[i] = split[i];
                }
                for (int i2 = length; i2 < length2; i2++) {
                    strArr[i2] = "0";
                }
                split = strArr;
            } else if (length2 < length) {
                String[] strArr2 = new String[length];
                for (int i3 = 0; i3 < length2; i3++) {
                    strArr2[i3] = split2[i3];
                }
                while (length2 < length) {
                    strArr2[length2] = "0";
                    length2++;
                }
                split2 = strArr2;
            }
            for (int i4 = 0; i4 < length; i4++) {
                try {
                    int parseInt = Integer.parseInt(split2[i4]);
                    int parseInt2 = Integer.parseInt(split[i4]);
                    if (parseInt < parseInt2) {
                        return false;
                    }
                    if (parseInt > parseInt2) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void startBrowseView(String str) {
        try {
            Uri parse = Uri.parse(appendIdWithUrl(str));
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(parse);
            intent.setFlags(268435456);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startHtmlShowActivity(Context context, String str) {
        HtmlConfig htmlConfig = new HtmlConfig();
        htmlConfig.setDynamicTitle(true);
        htmlConfig.setUrl(appendIdWithUrl(str));
        HtmlShowActivity.startActivity(context, htmlConfig);
    }

    private static String appendIdWithUrl(String str) {
        if (str.contains("fqqwqw=1")) {
            str = str.replace("fqqwqw=1", "fquserid=" + DBUtil.getMemberId());
        }
        LogUtil.m3784i("跳转的url = " + str);
        return str;
    }

    public static void startActionView(int i, String str, Context context) {
        if (i == 1) {
            startBrowseView(str);
        } else if (i == 2) {
            startHtmlShowActivity(context, str);
        } else {
            startBrowseView(str);
        }
    }

    public static void startAppSetting() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + getPacketName()));
        intent.setFlags(268435456);
        mContext.startActivity(intent);
    }

    public static String filterString(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return "unKnow" + System.currentTimeMillis();
        }
        return Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]").matcher(str).replaceAll(str2).trim();
    }

    public static String hideMiddleMobile(String str) {
        if (TextUtils.isEmpty(str) || str.length() <= 6) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (i >= 3 && i <= 6) {
                sb.append('*');
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    public static String getMaskPhone(String str) {
        if (str.length() == 11) {
            return str.substring(0, 3) + "****" + str.substring(7, str.length());
        } else if (str.length() < 4) {
            return str;
        } else {
            return str.substring(0, 4) + "****";
        }
    }

    public static String hideBankAccount(String str) {
        if (TextUtils.isEmpty(str) || str.length() < 8) {
            return str;
        }
        String substring = str.substring(0, 4);
        String substring2 = str.substring(str.length() - 4);
        return substring + "****" + substring2;
    }

    public static String getString(int i, Object... objArr) {
        Resources resources = getResources();
        return resources != null ? resources.getString(i, objArr) : "";
    }

    public static String getString(int i) {
        Resources resources = getResources();
        return resources != null ? resources.getString(i) : "";
    }

    public static void copyShareText(String str, String str2) {
        ((ClipboardManager) mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("", str));
        ToastUtil.showCenterToast(str2);
    }

    public static String getClipData() {
        ClipData primaryClip;
        ClipData.Item itemAt;
        try {
            ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService("clipboard");
            if (clipboardManager == null || (primaryClip = clipboardManager.getPrimaryClip()) == null || (itemAt = primaryClip.getItemAt(0)) == null || TextUtils.isEmpty(itemAt.getText())) {
                return "";
            }
            String charSequence = itemAt.getText().toString();
            return TextUtils.isEmpty(charSequence) ? "" : charSequence;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void recyclerViewScroll(final RecyclerView recyclerView, final int i, final int i2, int i3) {
        recyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.utils.AppUtil.1
            @Override // java.lang.Runnable
            public void run() {
                ((BaseLinearLayoutManager) RecyclerView.this.getLayoutManager()).scrollToPositionWithOffset(i, i2);
            }
        }, i3);
    }

    public static boolean containsEmoji(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFastClick(int i, int i2) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = currentTimeMillis - (fastClickMap.containsKey(Integer.valueOf(i)) ? fastClickMap.get(Integer.valueOf(i)).longValue() : 0L) <= ((long) i2);
        fastClickMap.put(Integer.valueOf(i), Long.valueOf(currentTimeMillis));
        return z;
    }

    public static boolean checkApkExist(Context context, String str) {
        PackageInfo packageInfo;
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        return packageInfo != null;
    }

    public static boolean showPtInstallDialog(Context context) {
        if (!checkApkExist(context, "org.potato.messenger")) {
            final CustomAlertDialog customAlertDialog = new CustomAlertDialog(context);
            customAlertDialog.setTitle(R.string.common_notify);
            customAlertDialog.setMessage(R.string.recharge_pt_install_msg);
            customAlertDialog.setCancelButton(R.string.recharge_pt_install_cancel_btn);
            customAlertDialog.setConfirmButton(R.string.recharge_pt_install_confirm_btn, new View.OnClickListener() { // from class: com.one.tomato.utils.AppUtil.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CustomAlertDialog.this.dismiss();
                    AppUtil.startBrowseView(DBUtil.getSystemParam().getPotatoDownUrl());
                }
            });
            return true;
        }
        return false;
    }
}
