package com.one.tomato.mvp.p080ui.start.view;

import android.content.Context;
import android.content.Intent;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.PagerSnapHelper;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.start.adapater.LauncherAdapater;
import com.one.tomato.utils.AppIdHistoryManger;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LauncherActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.start.view.LauncherActivity */
/* loaded from: classes3.dex */
public final class LauncherActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private LauncherAdapater adapter;

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
        return R.layout.activity_launcher;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: LauncherActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.start.view.LauncherActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivity(new Intent(context, LauncherActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getMContext(), 1, false));
        }
        new PagerSnapHelper().attachToRecyclerView((RecyclerView) _$_findCachedViewById(R$id.recycler_view));
        Context mContext = getMContext();
        RecyclerView recycler_view = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        Intrinsics.checkExpressionValueIsNotNull(recycler_view, "recycler_view");
        this.adapter = new LauncherAdapater(mContext, recycler_view);
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(this.adapter);
        }
        setListener();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        ArrayList arrayListOf;
        arrayListOf = CollectionsKt__CollectionsKt.arrayListOf(Integer.valueOf((int) R.drawable.icon_launcher_1), Integer.valueOf((int) R.drawable.icon_launcher_2), Integer.valueOf((int) R.drawable.icon_launcher_3), Integer.valueOf((int) R.drawable.icon_launcher_4));
        LauncherAdapater launcherAdapater = this.adapter;
        if (launcherAdapater != null) {
            launcherAdapater.setNewData(arrayListOf);
        }
        LauncherAdapater launcherAdapater2 = this.adapter;
        if (launcherAdapater2 != null) {
            launcherAdapater2.setEnableLoadMore(false);
        }
        AppIdHistoryManger.INSTANCE.deleteHistoryAppId(this);
    }

    public final void setListener() {
        LauncherActivity$setListener$clickJoinCallBack$1 launcherActivity$setListener$clickJoinCallBack$1 = new LauncherActivity$setListener$clickJoinCallBack$1(this);
        LauncherAdapater launcherAdapater = this.adapter;
        if (launcherAdapater != null) {
            launcherAdapater.setClickJoinCallBack(launcherActivity$setListener$clickJoinCallBack$1);
        }
    }
}
