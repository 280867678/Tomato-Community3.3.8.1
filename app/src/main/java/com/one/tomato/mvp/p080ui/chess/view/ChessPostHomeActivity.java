package com.one.tomato.mvp.p080ui.chess.view;

import android.content.Intent;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentTransaction;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.post.view.NewPostTabFragment;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessPostHomeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.view.ChessPostHomeActivity */
/* loaded from: classes3.dex */
public final class ChessPostHomeActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private NewPostTabFragment postTabFragment;

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_chess_post_home;
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
        this.postTabFragment = NewPostTabFragment.Companion.getInstance(1);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager != null ? supportFragmentManager.beginTransaction() : null;
        if (beginTransaction != null) {
            NewPostTabFragment newPostTabFragment = this.postTabFragment;
            if (newPostTabFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            beginTransaction.add(R.id.content_container, newPostTabFragment);
        }
        if (beginTransaction != null) {
            beginTransaction.commitAllowingStateLoss();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        NewPostTabFragment newPostTabFragment;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if ((i != 20 && i != 188) || (newPostTabFragment = this.postTabFragment) == null) {
                return;
            }
            newPostTabFragment.onActivityResult(i, i2, intent);
        }
    }
}
