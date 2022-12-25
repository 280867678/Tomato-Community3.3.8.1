package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.p085ui.mine.MyBrowseListActivity;
import com.one.tomato.p085ui.mine.MyCommentListActivity;
import com.one.tomato.p085ui.mine.MyThumbPostActivity;
import com.one.tomato.widget.MineCustomItemLayout;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MyInteractionActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.MyInteractionActivity */
/* loaded from: classes3.dex */
public final class MyInteractionActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
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
        return R.layout.activity_interaction;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    /* compiled from: MyInteractionActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.MyInteractionActivity$Companion */
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
            intent.setClass(context, MyInteractionActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.my_interaction);
        }
        ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_browse)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MyInteractionActivity$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MyInteractionActivity.this.getMContext();
                MyBrowseListActivity.startActivity(mContext);
            }
        });
        ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_comment)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MyInteractionActivity$initView$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MyInteractionActivity.this.getMContext();
                MyCommentListActivity.startActivity(mContext);
            }
        });
        ((MineCustomItemLayout) _$_findCachedViewById(R$id.rl_thumb)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MyInteractionActivity$initView$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = MyInteractionActivity.this.getMContext();
                MyThumbPostActivity.startActivity(mContext);
            }
        });
    }
}
