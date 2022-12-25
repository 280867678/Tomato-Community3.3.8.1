package com.one.tomato.mvp.p080ui.chess.view;

import android.content.Intent;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentTransaction;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaHomeFragment;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessPaPaHomeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.view.ChessPaPaHomeActivity */
/* loaded from: classes3.dex */
public final class ChessPaPaHomeActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private NewPaPaHomeFragment paPaHomeFragment;

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_chess_pa_pa_home;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        this.paPaHomeFragment = NewPaPaHomeFragment.Companion.getInstance(1);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager != null ? supportFragmentManager.beginTransaction() : null;
        if (beginTransaction != null) {
            NewPaPaHomeFragment newPaPaHomeFragment = this.paPaHomeFragment;
            if (newPaPaHomeFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            beginTransaction.add(R.id.content_container, newPaPaHomeFragment);
        }
        if (beginTransaction != null) {
            beginTransaction.commitAllowingStateLoss();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        NewPaPaHomeFragment newPaPaHomeFragment = this.paPaHomeFragment;
        if (newPaPaHomeFragment != null) {
            if (newPaPaHomeFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (!newPaPaHomeFragment.isVisible()) {
            } else {
                NewPaPaHomeFragment newPaPaHomeFragment2 = this.paPaHomeFragment;
                if (newPaPaHomeFragment2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (newPaPaHomeFragment2.isHidden()) {
                } else {
                    NewPaPaHomeFragment newPaPaHomeFragment3 = this.paPaHomeFragment;
                    if (newPaPaHomeFragment3 != null) {
                        newPaPaHomeFragment3.onActivityResult(i, i2, intent);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }
        }
    }
}
