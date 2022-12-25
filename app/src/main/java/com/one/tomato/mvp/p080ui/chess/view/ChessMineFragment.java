package com.one.tomato.mvp.p080ui.chess.view;

import android.support.p002v4.app.FragmentTransaction;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.mine.view.MineTabFragment;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessMineFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.view.ChessMineFragment */
/* loaded from: classes3.dex */
public final class ChessMineFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private MineTabFragment mineTabFragment;

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.fragment_chess_mine;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(beginTransaction, "childFragmentManager.beginTransaction()");
        this.mineTabFragment = new MineTabFragment();
        MineTabFragment mineTabFragment = this.mineTabFragment;
        if (mineTabFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mineTabFragment");
            throw null;
        }
        beginTransaction.add(R.id.framelayout, mineTabFragment);
        beginTransaction.commitAllowingStateLoss();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        MineTabFragment mineTabFragment = this.mineTabFragment;
        if (mineTabFragment != null) {
            mineTabFragment.onHiddenChanged(z);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("mineTabFragment");
            throw null;
        }
    }
}
