package com.one.tomato.mvp.p080ui.chess.view;

import android.support.p002v4.app.FragmentTransaction;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.p085ui.recharge.RechargeFragment;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessRechargeFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.view.ChessRechargeFragment */
/* loaded from: classes3.dex */
public final class ChessRechargeFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private RechargeFragment rechargeFragment;

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.fragment_chess_recharge;
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
        this.rechargeFragment = new RechargeFragment();
        RechargeFragment rechargeFragment = this.rechargeFragment;
        if (rechargeFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeFragment");
            throw null;
        }
        beginTransaction.add(R.id.framelayout, rechargeFragment);
        beginTransaction.commitAllowingStateLoss();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        RechargeFragment rechargeFragment = this.rechargeFragment;
        if (rechargeFragment != null) {
            rechargeFragment.onHiddenChanged(z);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeFragment");
            throw null;
        }
    }
}
