package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.content.Intent;
import android.support.p002v4.app.FragmentTransaction;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.utils.LogUtil;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewMyHomePageActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.NewMyHomePageActivity */
/* loaded from: classes3.dex */
public final class NewMyHomePageActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private FragmentTransaction beginTransaction;
    private NewHomePageFragment newHomePageFragemnt;
    public static final Companion Companion = new Companion(null);
    private static final String MEMBERID = MEMBERID;
    private static final String MEMBERID = MEMBERID;

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_new_my_home_page;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    /* compiled from: NewMyHomePageActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.NewMyHomePageActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getMEMBERID() {
            return NewMyHomePageActivity.MEMBERID;
        }

        public final void startActivity(Context context, int i) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, NewMyHomePageActivity.class);
            intent.putExtra(getMEMBERID(), i);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        this.newHomePageFragemnt = new NewHomePageFragment();
        this.beginTransaction = getSupportFragmentManager().beginTransaction();
        FragmentTransaction fragmentTransaction = this.beginTransaction;
        if (fragmentTransaction != null) {
            NewHomePageFragment newHomePageFragment = this.newHomePageFragemnt;
            if (newHomePageFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            fragmentTransaction.add(R.id.content, newHomePageFragment);
        }
        FragmentTransaction fragmentTransaction2 = this.beginTransaction;
        if (fragmentTransaction2 != null) {
            fragmentTransaction2.commitAllowingStateLoss();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        NewHomePageFragment newHomePageFragment = this.newHomePageFragemnt;
        if (newHomePageFragment != null) {
            try {
                FragmentTransaction fragmentTransaction = this.beginTransaction;
                if (fragmentTransaction == null) {
                    return;
                }
                if (newHomePageFragment != null) {
                    fragmentTransaction.remove(newHomePageFragment);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            } catch (Exception e) {
                LogUtil.m3787d("yan", "個人主頁移除fragment報錯--------" + e.getMessage());
                Unit unit = Unit.INSTANCE;
            }
        }
    }
}
