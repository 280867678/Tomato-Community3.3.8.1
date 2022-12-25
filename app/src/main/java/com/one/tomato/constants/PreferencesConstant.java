package com.one.tomato.constants;

import com.one.tomato.utils.PreferencesUtil;

/* loaded from: classes3.dex */
public class PreferencesConstant {
    public static final String HISTORY_POST = "post_" + PreferencesUtil.getInstance().getString("deviceNo");
    public static final String HISTORY_CIRCLE = "circle_" + PreferencesUtil.getInstance().getString("deviceNo");
}
