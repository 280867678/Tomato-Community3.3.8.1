package com.tomatolive.library.model;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import com.blankj.utilcode.util.TimeUtils;
import com.tomatolive.library.download.GiftDownLoadManager;
import com.tomatolive.library.utils.StringUtils;
import java.io.Serializable;

/* loaded from: classes3.dex */
public abstract class IncomeEntity implements Serializable {
    public String anchorIncomePrice = "0";
    public String price = "0";

    public abstract String getCount(boolean z);

    public abstract Spanned getFirstLine(Context context, boolean z);

    public abstract int getIconImg();

    public abstract String getImgUrl();

    public String getNobleExtraText(Context context, boolean z) {
        return "";
    }

    public String getPropExtraText(Context context, boolean z) {
        return "";
    }

    public abstract String getRecordTime();

    public boolean hasNobleExtraText(boolean z) {
        return false;
    }

    public Spanned getHtmlSpanned(String str) {
        return Html.fromHtml(str);
    }

    public Spanned getHtmlSpanned(Context context, int i, Object... objArr) {
        return Html.fromHtml(context.getString(i, objArr));
    }

    public String getLocalIcon(String str, int i) {
        return i == 1 ? GiftDownLoadManager.getInstance().getGiftItemImgUrl(str) : "";
    }

    public String formatNickName(String str) {
        return StringUtils.formatStrLen(str, 5);
    }

    public String formatRecordTime(Context context, boolean z) {
        return TimeUtils.millis2String(Long.parseLong(getRecordTime()) * 1000);
    }
}
