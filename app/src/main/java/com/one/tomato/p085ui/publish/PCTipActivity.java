package com.one.tomato.p085ui.publish;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.permissions.RxPermissions;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PCTipActivity.kt */
/* renamed from: com.one.tomato.ui.publish.PCTipActivity */
/* loaded from: classes3.dex */
public final class PCTipActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
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
        return R.layout.activity_pctip;
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
        ImmersionBarUtil.init(this);
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(getString(R.string.publish_pc_title));
        }
        Button button = (Button) _$_findCachedViewById(R$id.button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.publish.PCTipActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PCTipActivity.this.requestPe();
                }
            });
        }
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        String creator_center_url = systemParam.getCreator_center_url();
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_url);
        if (textView != null) {
            if (creator_center_url == null) {
                creator_center_url = "";
            }
            textView.setText(creator_center_url);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestPe() {
        new RxPermissions(this).request("android.permission.CAMERA").subscribe(new Consumer<Boolean>() { // from class: com.one.tomato.ui.publish.PCTipActivity$requestPe$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Boolean bool) {
                Context mContext;
                if (!(bool instanceof Boolean) || !bool.booleanValue()) {
                    return;
                }
                mContext = PCTipActivity.this.getMContext();
                PCTipActivity.this.startActivity(new Intent(mContext, PublishCaptureActivity.class));
                PCTipActivity.this.finish();
            }
        });
    }
}
