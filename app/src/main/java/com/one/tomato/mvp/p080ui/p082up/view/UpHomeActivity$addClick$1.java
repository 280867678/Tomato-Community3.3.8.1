package com.one.tomato.mvp.p080ui.p082up.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.one.tomato.R$id;
import com.one.tomato.dialog.UpObserverPriceSetDialog;
import com.one.tomato.entity.UpStatusBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.p080ui.p082up.presenter.UpHomePresenter;
import com.one.tomato.mvp.p080ui.p082up.view.UpApplyAct;
import com.one.tomato.mvp.p080ui.p082up.view.UpPlanActivity;
import com.one.tomato.p085ui.income.IncomeActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UpHomeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.UpHomeActivity$addClick$1 */
/* loaded from: classes3.dex */
public final class UpHomeActivity$addClick$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ UpHomeActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UpHomeActivity$addClick$1(UpHomeActivity upHomeActivity) {
        super(0);
        this.this$0 = upHomeActivity;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: collision with other method in class */
    public final Unit mo6822invoke() {
        TextView textView = (TextView) this.this$0._$_findCachedViewById(R$id.up_plan);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpHomeActivity$addClick$1.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    UpPlanActivity.Companion companion = UpPlanActivity.Companion;
                    mContext = UpHomeActivity$addClick$1.this.this$0.getMContext();
                    companion.startAct(mContext);
                }
            });
        }
        Button button = (Button) this.this$0._$_findCachedViewById(R$id.button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpHomeActivity$addClick$1.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    int i;
                    if (UpHomeActivity$addClick$1.this.this$0.startLoginActivity()) {
                        return;
                    }
                    UpApplyAct.Companion companion = UpApplyAct.Companion;
                    mContext = UpHomeActivity$addClick$1.this.this$0.getMContext();
                    i = UpHomeActivity$addClick$1.this.this$0.applyType;
                    companion.startAct(mContext, i);
                }
            });
        }
        TextView textView2 = (TextView) this.this$0._$_findCachedViewById(R$id.text_go_withdraw);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpHomeActivity$addClick$1.3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    IncomeActivity.Companion companion = IncomeActivity.Companion;
                    mContext = UpHomeActivity$addClick$1.this.this$0.getMContext();
                    if (mContext != null) {
                        companion.startActivity(mContext, 2);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
        ImageView imageView = (ImageView) this.this$0._$_findCachedViewById(R$id.image_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpHomeActivity$addClick$1.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpHomeActivity$addClick$1.this.this$0.onBackPressed();
                }
            });
        }
        Button button2 = (Button) this.this$0._$_findCachedViewById(R$id.button_goin_up);
        if (button2 != null) {
            button2.setOnClickListener(View$OnClickListenerC26765.INSTANCE);
        }
        LinearLayout linearLayout = (LinearLayout) this.this$0._$_findCachedViewById(R$id.liner_my_obsever);
        if (linearLayout != null) {
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpHomeActivity$addClick$1.6

                /* compiled from: UpHomeActivity.kt */
                /* renamed from: com.one.tomato.mvp.ui.up.view.UpHomeActivity$addClick$1$6$1 */
                /* loaded from: classes3.dex */
                static final class C26781 extends Lambda implements Function1<Boolean, Unit> {
                    C26781() {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public /* bridge */ /* synthetic */ Unit mo6794invoke(Boolean bool) {
                        invoke(bool.booleanValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(boolean z) {
                        UpHomePresenter mPresenter;
                        mPresenter = UpHomeActivity$addClick$1.this.this$0.getMPresenter();
                        if (mPresenter != null) {
                            mPresenter.requestQueryAchievement();
                        }
                    }
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    UpStatusBean upStatusBean;
                    mContext = UpHomeActivity$addClick$1.this.this$0.getMContext();
                    UpObserverPriceSetDialog upObserverPriceSetDialog = new UpObserverPriceSetDialog(mContext);
                    upStatusBean = UpHomeActivity$addClick$1.this.this$0.info;
                    upObserverPriceSetDialog.setSubscribePrice(upStatusBean);
                    upObserverPriceSetDialog.saveInfoCallBack(new C26781());
                }
            });
            return Unit.INSTANCE;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: UpHomeActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.up.view.UpHomeActivity$addClick$1$5 */
    /* loaded from: classes3.dex */
    public static final class View$OnClickListenerC26765 implements View.OnClickListener {
        public static final View$OnClickListenerC26765 INSTANCE = new View$OnClickListenerC26765();

        View$OnClickListenerC26765() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            SystemParam systemParam = DBUtil.getSystemParam();
            AppUtil.startBrowseView(systemParam != null ? systemParam.getPotatoUrl() : null);
        }
    }
}
