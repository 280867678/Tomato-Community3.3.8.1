package com.one.tomato.mvp.p080ui.chess.view;

import android.content.Context;
import android.content.Intent;
import android.support.p002v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.tomatolive.library.p136ui.fragment.LiveHomeFragment;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessLiveActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.view.ChessLiveActivity */
/* loaded from: classes3.dex */
public final class ChessLiveActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
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
        return R.layout.activity_chess_live;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: ChessLiveActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.chess.view.ChessLiveActivity$Companion */
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
            intent.setClass(context, ChessLiveActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(beginTransaction, "supportFragmentManager.beginTransaction()");
        beginTransaction.add(R.id.framelayout, LiveHomeFragment.newInstance());
        beginTransaction.commitAllowingStateLoss();
        ((TextView) _$_findCachedViewById(R$id.back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessLiveActivity$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChessLiveActivity.this.onBackPressed();
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        TomatoLiveSDKUtils.getSingleton().updateServerUrl();
        TomatoLiveSDKUtils.getSingleton().initAnim();
    }
}
