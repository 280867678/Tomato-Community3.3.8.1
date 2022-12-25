package com.one.tomato.mvp.p080ui.level;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.CreditRule;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.pictureselector.FullyGridLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.RxUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.collections.MutableCollections;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CreditRuleActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.level.CreditRuleActivity */
/* loaded from: classes3.dex */
public final class CreditRuleActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private final ArrayList<String> fractionList = new ArrayList<>();
    private final ArrayList<String> punishList = new ArrayList<>();

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
        return R.layout.activity_credit_rule;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
    }

    /* compiled from: CreditRuleActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.level.CreditRuleActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivity(new Intent(context, CreditRuleActivity.class));
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.credit_rule_title);
        }
        String[] fractions = getResources().getStringArray(R.array.credit_rule_fraction_type);
        ArrayList<String> arrayList = this.fractionList;
        Intrinsics.checkExpressionValueIsNotNull(fractions, "fractions");
        MutableCollections.addAll(arrayList, fractions);
        String[] punishs = getResources().getStringArray(R.array.credit_rule_punish_type);
        ArrayList<String> arrayList2 = this.punishList;
        Intrinsics.checkExpressionValueIsNotNull(punishs, "punishs");
        MutableCollections.addAll(arrayList2, punishs);
        requestData();
    }

    private final void requestData() {
        showWaitingDialog();
        ApiImplService.Companion.getApiImplService().requestCreditRule().compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<CreditRule>() { // from class: com.one.tomato.mvp.ui.level.CreditRuleActivity$requestData$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(CreditRule creditRule) {
                ArrayList arrayList;
                ArrayList arrayList2;
                ArrayList arrayList3;
                ArrayList arrayList4;
                ArrayList arrayList5;
                ArrayList arrayList6;
                ArrayList arrayList7;
                ArrayList arrayList8;
                CreditRuleActivity.this.hideWaitingDialog();
                if (creditRule != null) {
                    creditRule.getListRule();
                }
                List<CreditRule.ListRuleBean> listRule = creditRule != null ? creditRule.getListRule() : null;
                if (listRule == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                int size = listRule.size();
                for (int i = 0; i < size; i++) {
                    CreditRule.ListRuleBean tempBean = (creditRule != null ? creditRule.getListRule() : null).get(i);
                    arrayList6 = CreditRuleActivity.this.fractionList;
                    Intrinsics.checkExpressionValueIsNotNull(tempBean, "tempBean");
                    arrayList6.add(tempBean.getName());
                    arrayList7 = CreditRuleActivity.this.fractionList;
                    arrayList7.add(tempBean.getSingleValue());
                    arrayList8 = CreditRuleActivity.this.fractionList;
                    arrayList8.add(tempBean.getMaxValue());
                }
                CreditRuleActivity creditRuleActivity = CreditRuleActivity.this;
                arrayList = creditRuleActivity.fractionList;
                creditRuleActivity.initFractionAdapter(arrayList);
                if (creditRule != null) {
                    creditRule.getListCfg();
                }
                List<CreditRule.ListCfgBean> listCfg = creditRule != null ? creditRule.getListCfg() : null;
                if (listCfg == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                int size2 = listCfg.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    CreditRule.ListCfgBean tempBean2 = (creditRule != null ? creditRule.getListCfg() : null).get(i2);
                    arrayList3 = CreditRuleActivity.this.punishList;
                    Intrinsics.checkExpressionValueIsNotNull(tempBean2, "tempBean");
                    arrayList3.add(tempBean2.getRange());
                    arrayList4 = CreditRuleActivity.this.punishList;
                    arrayList4.add(tempBean2.getPubCount());
                    arrayList5 = CreditRuleActivity.this.punishList;
                    arrayList5.add(tempBean2.getCommentCount());
                }
                CreditRuleActivity creditRuleActivity2 = CreditRuleActivity.this;
                arrayList2 = creditRuleActivity2.punishList;
                creditRuleActivity2.initPunishAdapter(arrayList2);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                CreditRuleActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initFractionAdapter(final ArrayList<String> arrayList) {
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_fraction);
        BaseRecyclerViewAdapter<String> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<String>(arrayList, this, R.layout.item_credit_rule, arrayList, recyclerView) { // from class: com.one.tomato.mvp.ui.level.CreditRuleActivity$initFractionAdapter$adapter$1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(this, r4, arrayList, recyclerView);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, String itemData) {
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                super.convert(holder, (BaseViewHolder) itemData);
                TextView tv_title = (TextView) holder.getView(R.id.tv_title);
                Intrinsics.checkExpressionValueIsNotNull(tv_title, "tv_title");
                tv_title.setText(itemData);
                if (Intrinsics.compare(this.mData.indexOf(itemData), 2) == 1) {
                    tv_title.setBackgroundColor(CreditRuleActivity.this.getResources().getColor(R.color.white));
                    tv_title.setTextColor(CreditRuleActivity.this.getResources().getColor(R.color.text_dark));
                    return;
                }
                tv_title.setBackgroundColor(CreditRuleActivity.this.getResources().getColor(R.color.app_bg_grey));
                tv_title.setTextColor(Color.parseColor("#9C9BAD"));
            }
        };
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(getMContext(), 3);
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getMContext());
        builder.setColor(getResources().getColor(R.color.divider));
        builder.setHorizontalSpan(R.dimen.dimen_1);
        builder.setVerticalSpan(R.dimen.dimen_1);
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_fraction)).addItemDecoration(builder.build());
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_fraction)).setLayoutManager(fullyGridLayoutManager);
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_fraction)).setAdapter(baseRecyclerViewAdapter);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initPunishAdapter(final ArrayList<String> arrayList) {
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_punish);
        BaseRecyclerViewAdapter<String> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<String>(arrayList, this, R.layout.item_credit_rule, arrayList, recyclerView) { // from class: com.one.tomato.mvp.ui.level.CreditRuleActivity$initPunishAdapter$adapter$1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(this, r4, arrayList, recyclerView);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, String itemData) {
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                super.convert(holder, (BaseViewHolder) itemData);
                TextView tv_title = (TextView) holder.getView(R.id.tv_title);
                Intrinsics.checkExpressionValueIsNotNull(tv_title, "tv_title");
                tv_title.setText(itemData);
                if (Intrinsics.compare(this.mData.indexOf(itemData), 2) == 1) {
                    tv_title.setBackgroundColor(CreditRuleActivity.this.getResources().getColor(R.color.white));
                    tv_title.setTextColor(CreditRuleActivity.this.getResources().getColor(R.color.text_dark));
                    return;
                }
                tv_title.setBackgroundColor(CreditRuleActivity.this.getResources().getColor(R.color.app_bg_grey));
                tv_title.setTextColor(Color.parseColor("#9C9BAD"));
            }
        };
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(getMContext(), 3);
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getMContext());
        builder.setColor(getResources().getColor(R.color.divider));
        builder.setHorizontalSpan(R.dimen.dimen_1);
        builder.setVerticalSpan(R.dimen.dimen_1);
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_punish)).addItemDecoration(builder.build());
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_punish)).setLayoutManager(fullyGridLayoutManager);
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_punish)).setAdapter(baseRecyclerViewAdapter);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
    }
}
