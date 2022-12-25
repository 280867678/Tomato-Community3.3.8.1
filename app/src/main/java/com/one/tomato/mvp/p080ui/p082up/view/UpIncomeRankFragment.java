package com.one.tomato.mvp.p080ui.p082up.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.content.ContextCompat;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.entity.UpRankListBean;
import com.one.tomato.entity.UpStatusBean;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView;
import com.one.tomato.mvp.p080ui.p082up.presenter.UpHomePresenter;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaTabAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Standard;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpIncomeRankFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.UpIncomeRankFragment */
/* loaded from: classes3.dex */
public final class UpIncomeRankFragment extends MvpBaseFragment<UpContarct$UpIView, UpHomePresenter> implements UpContarct$UpIView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private boolean autoLoad;
    private PaPaTabAdapter fragmentAdapter;
    private TabLayout tab_layout;
    private int topListType;
    private ArrayList<String> listStr = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.activity_up_income_rank;
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerRankList(ArrayList<UpRankListBean> arrayList) {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    /* compiled from: UpIncomeRankFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.up.view.UpIncomeRankFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final UpIncomeRankFragment getInstance(String businessType, int i, boolean z) {
            Intrinsics.checkParameterIsNotNull(businessType, "businessType");
            UpIncomeRankFragment upIncomeRankFragment = new UpIncomeRankFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("top_type", i);
            bundle.putBoolean("auto_load", z);
            bundle.putString("business", businessType);
            upIncomeRankFragment.setArguments(bundle);
            return upIncomeRankFragment;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public UpHomePresenter mo6441createPresenter() {
        return new UpHomePresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            arguments.getString("business");
        }
        Bundle arguments2 = getArguments();
        this.topListType = arguments2 != null ? arguments2.getInt("top_type") : 1;
        Bundle arguments3 = getArguments();
        this.autoLoad = arguments3 != null ? arguments3.getBoolean("auto_load") : false;
        this.listStr.add(AppUtil.getString(R.string.up_rank_day));
        this.listStr.add(AppUtil.getString(R.string.up_rank_week));
        this.listStr.add(AppUtil.getString(R.string.up_rank_moon));
        if (this.autoLoad) {
            setUserVisibleHint(true);
        }
    }

    public final void setTabLayout(TabLayout tabLayout) {
        this.tab_layout = tabLayout;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        initTab();
    }

    private final void initTab() {
        TabLayout.Tab tabAt;
        this.fragmentList.add(UpChildRankFragment.Companion.getInstance(1, this.topListType, this.autoLoad));
        this.fragmentList.add(UpChildRankFragment.Companion.getInstance(2, this.topListType, this.autoLoad));
        this.fragmentList.add(UpChildRankFragment.Companion.getInstance(3, this.topListType, this.autoLoad));
        this.fragmentAdapter = new PaPaTabAdapter(getChildFragmentManager(), this.fragmentList, this.listStr);
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager != null) {
            previewViewPager.setAdapter(this.fragmentAdapter);
        }
        PreviewViewPager previewViewPager2 = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager2 != null) {
            previewViewPager2.setOffscreenPageLimit(3);
        }
        TabLayout tabLayout = this.tab_layout;
        if (tabLayout != null) {
            tabLayout.setupWithViewPager((PreviewViewPager) _$_findCachedViewById(R$id.view_pager));
        }
        for (int i = 0; i < 3; i++) {
            TabLayout tabLayout2 = this.tab_layout;
            TabLayout.Tab tabAt2 = tabLayout2 != null ? tabLayout2.getTabAt(i) : null;
            TextView textView = new TextView(getMContext());
            textView.setWidth((int) DisplayMetricsUtils.dp2px(60.0f));
            textView.setHeight((int) DisplayMetricsUtils.dp2px(26.0f));
            textView.setText(this.listStr.get(i));
            TextPaint paint = textView.getPaint();
            Intrinsics.checkExpressionValueIsNotNull(paint, "textView.paint");
            paint.setFakeBoldText(true);
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            textView.setTextSize(14.0f);
            textView.setGravity(17);
            if (tabAt2 != null) {
                tabAt2.setCustomView(textView);
            }
        }
        TabLayout tabLayout3 = this.tab_layout;
        TextView textView2 = (TextView) ((tabLayout3 == null || (tabAt = tabLayout3.getTabAt(0)) == null) ? null : tabAt.getCustomView());
        if (textView2 != null) {
            Context mContext2 = getMContext();
            if (mContext2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView2.setBackground(ContextCompat.getDrawable(mContext2, R.drawable.up_shape_solid_rank_incom_30));
        }
        if (textView2 != null) {
            Context mContext3 = getMContext();
            if (mContext3 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView2.setTextColor(ContextCompat.getColor(mContext3, R.color.colorAccent));
        }
        PreviewViewPager previewViewPager3 = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager3 != null) {
            previewViewPager3.setCurrentItem(0);
        }
        TabLayout tabLayout4 = this.tab_layout;
        if (tabLayout4 != null) {
            tabLayout4.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.one.tomato.mvp.ui.up.view.UpIncomeRankFragment$initTab$1
                @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                public void onTabReselected(TabLayout.Tab tab) {
                }

                @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                public void onTabUnselected(TabLayout.Tab tab) {
                    Context mContext4;
                    TextView textView3 = (TextView) (tab != null ? tab.getCustomView() : null);
                    if (textView3 != null) {
                        textView3.setBackground(null);
                    }
                    if (textView3 != null) {
                        mContext4 = UpIncomeRankFragment.this.getMContext();
                        if (mContext4 != null) {
                            textView3.setTextColor(ContextCompat.getColor(mContext4, R.color.white));
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                }

                @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                public void onTabSelected(TabLayout.Tab tab) {
                    Context mContext4;
                    Context mContext5;
                    TextView textView3 = (TextView) (tab != null ? tab.getCustomView() : null);
                    if (textView3 != null) {
                        mContext5 = UpIncomeRankFragment.this.getMContext();
                        if (mContext5 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        textView3.setBackground(ContextCompat.getDrawable(mContext5, R.drawable.up_shape_solid_rank_incom_30));
                    }
                    if (textView3 != null) {
                        mContext4 = UpIncomeRankFragment.this.getMContext();
                        if (mContext4 != null) {
                            textView3.setTextColor(ContextCompat.getColor(mContext4, R.color.colorAccent));
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerQueryUpStatusInfo(UpStatusBean upStatusBean) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerApplyUpSuccess() {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerApplyError() {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerQueryAchiSucess(UpStatusBean upStatusBean) {
        throw new Standard("An operation is not implemented: not implemented");
    }
}
