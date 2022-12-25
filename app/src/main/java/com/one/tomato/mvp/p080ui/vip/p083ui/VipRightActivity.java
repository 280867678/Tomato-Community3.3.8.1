package com.one.tomato.mvp.p080ui.vip.p083ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.thirdpart.recyclerview.BaseGridLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.ViewUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.collections._ArraysJvm;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: VipRightActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.vip.ui.VipRightActivity */
/* loaded from: classes3.dex */
public final class VipRightActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;

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
        return R.layout.activity_vip_right;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: VipRightActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.vip.ui.VipRightActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, VipRightActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.vip_right);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        initVipRight();
        initVipExplain();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, java.util.ArrayList] */
    private final void initVipRight() {
        List asList;
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = new ArrayList();
        String[] stringArray = getResources().getStringArray(R.array.vip_right_compare_list);
        Intrinsics.checkExpressionValueIsNotNull(stringArray, "resources.getStringArray…y.vip_right_compare_list)");
        asList = _ArraysJvm.asList(stringArray);
        ((ArrayList) ref$ObjectRef.element).addAll(asList);
        final Context mContext = getMContext();
        final ArrayList arrayList = (ArrayList) ref$ObjectRef.element;
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        BaseRecyclerViewAdapter<String> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<String>(this, ref$ObjectRef, mContext, R.layout.item_credit_rule, arrayList, recyclerView) { // from class: com.one.tomato.mvp.ui.vip.ui.VipRightActivity$initVipRight$adapter$1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(mContext, r4, arrayList, recyclerView);
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
            }
        };
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getMContext());
        builder.setVerticalSpan(R.dimen.common_divider);
        builder.setHorizontalSpan(R.dimen.common_divider);
        builder.setColor(getResources().getColor(R.color.divider));
        builder.setShowLastLine(false);
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).addItemDecoration(builder.build());
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).setLayoutManager(new BaseGridLayoutManager(getMContext(), 3));
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).setAdapter(baseRecyclerViewAdapter);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
    }

    private final void initVipExplain() {
        ViewUtil.initTextViewWithSpannableString((TextView) _$_findCachedViewById(R$id.tv_vip_right_explain_list5), new String[]{AppUtil.getString(R.string.vip_right_explain_list5_1), AppUtil.getString(R.string.potato_tip5), "@", AppUtil.getString(R.string.vip_right_explain_list5_2)}, new String[]{String.valueOf(Color.parseColor("#646464")), String.valueOf(Color.parseColor("#149eff")), String.valueOf(Color.parseColor("#646464")), String.valueOf(Color.parseColor("#646464"))}, new String[]{"16", "16", "16", "16"});
        ((TextView) _$_findCachedViewById(R$id.tv_vip_right_explain_list5)).setOnClickListener(VipRightActivity$initVipExplain$1.INSTANCE);
    }
}
