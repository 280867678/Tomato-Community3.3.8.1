package com.one.tomato.p085ui.publish;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.R$id;
import com.one.tomato.dialog.PublishPostDialog;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChoosePublishWayActivity.kt */
/* renamed from: com.one.tomato.ui.publish.ChoosePublishWayActivity */
/* loaded from: classes3.dex */
public final class ChoosePublishWayActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private PublishPostDialog publishPostDialog;

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
        return R.layout.activity_choose_publish_way;
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
            titleTV.setText(getString(R.string.publish_choose_way_title));
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_computer);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.publish.ChoosePublishWayActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    mContext = ChoosePublishWayActivity.this.getMContext();
                    ChoosePublishWayActivity.this.startActivity(new Intent(mContext, PCTipActivity.class));
                    ChoosePublishWayActivity.this.finish();
                }
            });
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_phone);
        if (relativeLayout2 != null) {
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.publish.ChoosePublishWayActivity$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    PublishPostDialog publishPostDialog;
                    PublishPostDialog publishPostDialog2;
                    ChoosePublishWayActivity choosePublishWayActivity = ChoosePublishWayActivity.this;
                    mContext = choosePublishWayActivity.getMContext();
                    if (mContext != null) {
                        choosePublishWayActivity.publishPostDialog = new PublishPostDialog(mContext);
                        publishPostDialog = ChoosePublishWayActivity.this.publishPostDialog;
                        if (publishPostDialog != null) {
                            publishPostDialog.setOwnerActivity(ChoosePublishWayActivity.this);
                        }
                        publishPostDialog2 = ChoosePublishWayActivity.this.publishPostDialog;
                        if (publishPostDialog2 == null) {
                            return;
                        }
                        publishPostDialog2.show();
                        return;
                    }
                    Intrinsics.throwNpe();
                    throw null;
                }
            });
        }
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        final String publishRuleUrl = systemParam.getPublishRuleUrl();
        if (!TextUtils.isEmpty(publishRuleUrl)) {
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_publish_notice);
            if (textView != null) {
                textView.setVisibility(0);
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_publish_notice);
            if (textView2 == null) {
                return;
            }
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.publish.ChoosePublishWayActivity$initView$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StringBuilder sb = new StringBuilder();
                    DomainServer domainServer = DomainServer.getInstance();
                    Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                    sb.append(domainServer.getH5Url());
                    sb.append(publishRuleUrl);
                    AppUtil.startBrowseView(sb.toString());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 188) {
            ArrayList<LocalMedia> arrayList = new ArrayList<>();
            arrayList.addAll(PictureSelector.obtainMultipleResult(intent));
            if (arrayList.size() <= 0) {
                return;
            }
            PublishPostDialog publishPostDialog = this.publishPostDialog;
            if (publishPostDialog != null) {
                publishPostDialog.setSelectData(arrayList);
            }
            finish();
        }
    }
}
