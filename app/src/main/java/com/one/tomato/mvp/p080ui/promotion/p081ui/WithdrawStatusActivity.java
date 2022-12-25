package com.one.tomato.mvp.p080ui.promotion.p081ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PromotionOrderRecord;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.utils.AppUtil;
import java.io.Serializable;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WithdrawStatusActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.WithdrawStatusActivity */
/* loaded from: classes3.dex */
public final class WithdrawStatusActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private PromotionOrderRecord.C2524Record record;
    private int type;

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_withdraw_status;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: WithdrawStatusActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.promotion.ui.WithdrawStatusActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, int i, PromotionOrderRecord.C2524Record record) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(record, "record");
            Intent intent = new Intent();
            intent.setClass(context, WithdrawStatusActivity.class);
            intent.putExtra("type", i);
            intent.putExtra("record", record);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        Intent intent = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent, "intent");
        this.type = intent.getExtras().getInt("type");
        Intent intent2 = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent2, "intent");
        Serializable serializable = intent2.getExtras().getSerializable("record");
        if (serializable == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.PromotionOrderRecord.Record");
        }
        this.record = (PromotionOrderRecord.C2524Record) serializable;
        initTitleBar();
        int i = this.type;
        if (i == 1) {
            TextView titleTV = getTitleTV();
            if (titleTV != null) {
                titleTV.setText(R.string.withdraw_status_apply);
            }
            TextView tv_complete = (TextView) _$_findCachedViewById(R$id.tv_complete);
            Intrinsics.checkExpressionValueIsNotNull(tv_complete, "tv_complete");
            tv_complete.setVisibility(0);
        } else if (i == 2) {
            TextView titleTV2 = getTitleTV();
            if (titleTV2 != null) {
                titleTV2.setText(R.string.withdraw_detail_title);
            }
            LinearLayout ll_cash_top_lable = (LinearLayout) _$_findCachedViewById(R$id.ll_cash_top_lable);
            Intrinsics.checkExpressionValueIsNotNull(ll_cash_top_lable, "ll_cash_top_lable");
            ll_cash_top_lable.setVisibility(0);
        }
        ((TextView) _$_findCachedViewById(R$id.tv_complete)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawStatusActivity$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WithdrawStatusActivity.this.onBackPressed();
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_cash_amount);
        PromotionOrderRecord.C2524Record c2524Record = this.record;
        if (c2524Record == null) {
            Intrinsics.throwUninitializedPropertyAccessException("record");
            throw null;
        }
        textView.setText(c2524Record.coinAmount);
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_apply_time);
        PromotionOrderRecord.C2524Record c2524Record2 = this.record;
        if (c2524Record2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("record");
            throw null;
        }
        textView2.setText(c2524Record2.orderTime);
        PromotionOrderRecord.C2524Record c2524Record3 = this.record;
        if (c2524Record3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("record");
            throw null;
        }
        int i = c2524Record3.orderStatus;
        if (i == 1) {
            _$_findCachedViewById(R$id.divider_receive_top).setBackgroundColor(getResources().getColor(R.color.blue_149eff));
            ((ImageView) _$_findCachedViewById(R$id.iv_receive)).setImageResource(R.drawable.progress_compeled);
            ((TextView) _$_findCachedViewById(R$id.tv_receive)).setText(R.string.withdraw_status_success);
            ((TextView) _$_findCachedViewById(R$id.tv_receive)).setTextColor(getResources().getColor(R.color.blue_149eff));
            TextView tv_receive_time = (TextView) _$_findCachedViewById(R$id.tv_receive_time);
            Intrinsics.checkExpressionValueIsNotNull(tv_receive_time, "tv_receive_time");
            tv_receive_time.setVisibility(0);
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_receive_time);
            PromotionOrderRecord.C2524Record c2524Record4 = this.record;
            if (c2524Record4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("record");
                throw null;
            }
            textView3.setText(c2524Record4.endTime);
        } else if (i == 2) {
            _$_findCachedViewById(R$id.divider_receive_top).setBackgroundColor(getResources().getColor(R.color.blue_149eff));
            ((ImageView) _$_findCachedViewById(R$id.iv_receive)).setImageResource(R.drawable.progress_fail);
            ((TextView) _$_findCachedViewById(R$id.tv_receive)).setText(R.string.withdraw_status_fail);
            TextView tv_error = (TextView) _$_findCachedViewById(R$id.tv_error);
            Intrinsics.checkExpressionValueIsNotNull(tv_error, "tv_error");
            tv_error.setVisibility(0);
            TextView textView4 = (TextView) _$_findCachedViewById(R$id.tv_error);
            PromotionOrderRecord.C2524Record c2524Record5 = this.record;
            if (c2524Record5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("record");
                throw null;
            }
            textView4.setText(c2524Record5.orderStatusDesc);
        }
        PromotionOrderRecord.C2524Record c2524Record6 = this.record;
        if (c2524Record6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("record");
            throw null;
        }
        if (TextUtils.isEmpty(c2524Record6.orderId)) {
            TextView tv_order_code_lable = (TextView) _$_findCachedViewById(R$id.tv_order_code_lable);
            Intrinsics.checkExpressionValueIsNotNull(tv_order_code_lable, "tv_order_code_lable");
            tv_order_code_lable.setVisibility(4);
            TextView tv_order_code = (TextView) _$_findCachedViewById(R$id.tv_order_code);
            Intrinsics.checkExpressionValueIsNotNull(tv_order_code, "tv_order_code");
            tv_order_code.setVisibility(4);
        } else {
            TextView textView5 = (TextView) _$_findCachedViewById(R$id.tv_order_code);
            PromotionOrderRecord.C2524Record c2524Record7 = this.record;
            if (c2524Record7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("record");
                throw null;
            }
            textView5.setText(c2524Record7.orderId);
        }
        TextView textView6 = (TextView) _$_findCachedViewById(R$id.tv_order_account);
        Object[] objArr = new Object[1];
        PromotionOrderRecord.C2524Record c2524Record8 = this.record;
        if (c2524Record8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("record");
            throw null;
        }
        objArr[0] = c2524Record8.walletAccount;
        textView6.setText(AppUtil.getString(R.string.withdraw_order_account_xiaobai, objArr));
        TextView textView7 = (TextView) _$_findCachedViewById(R$id.tv_withdraw_order_money);
        PromotionOrderRecord.C2524Record c2524Record9 = this.record;
        if (c2524Record9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("record");
            throw null;
        }
        textView7.setText(c2524Record9.orderAmount);
        TextView textView8 = (TextView) _$_findCachedViewById(R$id.tv_withdraw_order_ecny);
        PromotionOrderRecord.C2524Record c2524Record10 = this.record;
        if (c2524Record10 != null) {
            textView8.setText(c2524Record10.coinAmount);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("record");
            throw null;
        }
    }
}
