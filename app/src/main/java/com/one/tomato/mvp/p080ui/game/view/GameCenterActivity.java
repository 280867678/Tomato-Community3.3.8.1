package com.one.tomato.mvp.p080ui.game.view;

import android.support.p002v4.app.FragmentTransaction;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameCenterActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameCenterActivity */
/* loaded from: classes3.dex */
public final class GameCenterActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    static {
        new Companion(null);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_game_center;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: GameCenterActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.game.view.GameCenterActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.game_center);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(beginTransaction, "supportFragmentManager.beginTransaction()");
        beginTransaction.add(R.id.framelayout, GameCenterFragment.Companion.getInstance("center"));
        beginTransaction.commitAllowingStateLoss();
    }
}
