package com.one.tomato.mvp.p080ui.promotion.p081ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SpreadRecordActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadRecordActivity */
/* loaded from: classes3.dex */
public final class SpreadRecordActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private TabFragmentAdapter fragmentAdapter;
    private int position;
    private final ArrayList<String> stringList = new ArrayList<>();
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

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
        return R.layout.activity_spread_record;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: SpreadRecordActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadRecordActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, int i) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, SpreadRecordActivity.class);
            intent.putExtra("position", i);
            context.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadRecordActivity$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpreadRecordActivity.this.onBackPressed();
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        Intent intent = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent, "intent");
        this.position = intent.getExtras().getInt("position");
        initTabs();
    }

    private final void initTabs() {
        this.stringList.add(AppUtil.getString(R.string.spread_income_record));
        this.stringList.add(AppUtil.getString(R.string.spread_withdraw_record));
        this.fragmentList.add(IncomeRecordFragment.Companion.getInstance());
        this.fragmentList.add(WithdrawRecordFragment.Companion.getInstance());
        this.fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), this.fragmentList, this.stringList);
        ((ViewPager) _$_findCachedViewById(R$id.viewpager)).setAdapter(this.fragmentAdapter);
        TabFragmentAdapter tabFragmentAdapter = this.fragmentAdapter;
        if (tabFragmentAdapter != null) {
            tabFragmentAdapter.notifyDataSetChanged();
        }
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setViewPager((ViewPager) _$_findCachedViewById(R$id.viewpager));
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setTextColor(getResources().getColor(R.color.text_light));
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setSelectedTextColor(getResources().getColor(R.color.colorAccent));
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setCustomIndicatorWith(true, 40);
        ((ViewPager) _$_findCachedViewById(R$id.viewpager)).setOffscreenPageLimit(this.fragmentList.size());
        ViewPager viewpager = (ViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager, "viewpager");
        viewpager.setCurrentItem(this.position);
    }
}
