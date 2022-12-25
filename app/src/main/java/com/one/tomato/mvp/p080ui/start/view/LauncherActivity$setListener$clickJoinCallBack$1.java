package com.one.tomato.mvp.p080ui.start.view;

import android.content.Context;
import com.one.tomato.p085ui.MainTabActivity;
import com.one.tomato.utils.PreferencesUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LauncherActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.start.view.LauncherActivity$setListener$clickJoinCallBack$1 */
/* loaded from: classes3.dex */
public final class LauncherActivity$setListener$clickJoinCallBack$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ LauncherActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LauncherActivity$setListener$clickJoinCallBack$1(LauncherActivity launcherActivity) {
        super(0);
        this.this$0 = launcherActivity;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
        mo6822invoke();
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: collision with other method in class */
    public final void mo6822invoke() {
        Context mContext;
        PreferencesUtil.getInstance().putBoolean("Launcher_333", false);
        mContext = this.this$0.getMContext();
        MainTabActivity.startActivity(mContext, 0);
    }
}
