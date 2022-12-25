package com.one.tomato.mvp.p080ui.start.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.chess.view.ChessMainTabActivity;
import com.one.tomato.p085ui.MainTabActivity;
import com.one.tomato.utils.AppIdHistoryManger;
import com.one.tomato.utils.DBUtil;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WarnActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.start.view.WarnActivity */
/* loaded from: classes3.dex */
public final class WarnActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
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
        return R.layout.activity_warn;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
    }

    /* compiled from: WarnActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.start.view.WarnActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivity(new Intent(context, WarnActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        ((TextView) _$_findCachedViewById(R$id.tv_agree)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.start.view.WarnActivity$initData$1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WarnActivity.this.startNextActivity();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_disagree)).setOnClickListener(WarnActivity$initData$2.INSTANCE);
        AppIdHistoryManger.INSTANCE.deleteHistoryAppId(this);
        ((ImageView) _$_findCachedViewById(R$id.iv_bg)).setOnClickListener(WarnActivity$initData$3.INSTANCE);
    }

    public final void startNextActivity() {
        ArrayList<AdPage> adPage = DBUtil.getAdPage(C2516Ad.TYPE_START);
        if (adPage == null || adPage.isEmpty()) {
            BaseApplication application = BaseApplication.getApplication();
            Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
            if (application.isChess()) {
                ChessMainTabActivity.Companion companion = ChessMainTabActivity.Companion;
                companion.startActivity(this, companion.getTAB_ITEM_HOME());
            } else {
                MainTabActivity.startActivity(this, 0);
            }
        } else {
            AdActivity.Companion.startActivity(this);
        }
        finish();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().exitApp();
    }
}
