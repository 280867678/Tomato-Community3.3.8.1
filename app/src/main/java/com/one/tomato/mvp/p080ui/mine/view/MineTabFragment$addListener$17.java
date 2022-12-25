package com.one.tomato.mvp.p080ui.mine.view;

import android.view.View;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MineTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.MineTabFragment$addListener$17 */
/* loaded from: classes3.dex */
final class MineTabFragment$addListener$17 implements View.OnClickListener {
    public static final MineTabFragment$addListener$17 INSTANCE = new MineTabFragment$addListener$17();

    MineTabFragment$addListener$17() {
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        AppUtil.startBrowseView(systemParam.getPotatoUrl());
    }
}
