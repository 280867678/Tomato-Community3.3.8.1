package com.tomatolive.library.p136ui.activity.noble;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.TimeUtils;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.MyNobilityEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.NobilityOpenEvent;
import com.tomatolive.library.p136ui.presenter.NobleRecommendPresenter;
import com.tomatolive.library.p136ui.view.dialog.NobilityGoldUseDescDialog;
import com.tomatolive.library.p136ui.view.dialog.RecommendDialog;
import com.tomatolive.library.p136ui.view.iview.IRecommendSearchView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.util.concurrent.atomic.AtomicInteger;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.activity.noble.NobleRecommendActivity */
/* loaded from: classes3.dex */
public class NobleRecommendActivity extends BaseActivity<NobleRecommendPresenter> implements IRecommendSearchView {
    public static final String KEY_RECOMMEND_END_TIME = "recommend_end_time";
    public static final String KEY_RECOMMEND_NUMBER = "recommend_number";
    private static final String RECOMMEND_ANONYMOUS = "1";
    private static final String RECOMMEND_PUBLIC = "0";
    private AnchorEntity anchorEntity;
    private LinearLayout llSearchBg;
    private EditText mEditTextSearchAnchor;
    private TextView mRecommendAnonymous;
    private TextView mRecommendCount;
    private TextView mRecommendPublic;
    private TextView mSearchAnchor;
    private AtomicInteger recommendCount = new AtomicInteger(0);
    private TextView tvExpire;
    private TextView tvLiving;
    private TextView tvSearchResult;

    @Override // com.tomatolive.library.p136ui.view.iview.IRecommendSearchView
    public void onDataFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NobleRecommendPresenter mo6636createPresenter() {
        return new NobleRecommendPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_nobility_recommended_top;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        Intent intent = getIntent();
        this.recommendCount.set(NumberUtils.string2int(intent.getStringExtra(KEY_RECOMMEND_NUMBER)));
        String stringExtra = intent.getStringExtra(KEY_RECOMMEND_END_TIME);
        setActivityTitle(R$string.fq_text_recommended_top);
        this.tvExpire = (TextView) findViewById(R$id.fq_noble_recommend_expire);
        this.tvExpire.setText(Html.fromHtml(getString(R$string.fq_text_expired_time, new Object[]{TimeUtils.millis2String(NumberUtils.string2long(stringExtra) * 1000)})));
        this.mRecommendCount = (TextView) findViewById(R$id.fq_noble_recommend_count);
        this.mRecommendCount.setText(Html.fromHtml(getString(R$string.fq_text_remain_recommend_times, new Object[]{String.valueOf(this.recommendCount)})));
        this.mRecommendAnonymous = (TextView) findViewById(R$id.fq_tv_recommend_anonymous);
        this.mRecommendPublic = (TextView) findViewById(R$id.fq_tv_recommend_public);
        this.mSearchAnchor = (TextView) findViewById(R$id.fq_noble_recommend_search_anchor);
        this.mEditTextSearchAnchor = (EditText) findViewById(R$id.fq_et_search);
        this.tvSearchResult = (TextView) findViewById(R$id.fq_text_search_item);
        this.tvLiving = (TextView) findViewById(R$id.tv_living);
        this.llSearchBg = (LinearLayout) findViewById(R$id.ll_search_bg);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        this.mRecommendAnonymous.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobleRecommendActivity$UBPEnO_2vdlfFfyqFgvSXtHbvp8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NobleRecommendActivity.this.lambda$initListener$0$NobleRecommendActivity(view);
            }
        });
        this.mRecommendPublic.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobleRecommendActivity$Ace6TEpGii_9VgxwM8-lxdJnYPI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NobleRecommendActivity.this.lambda$initListener$1$NobleRecommendActivity(view);
            }
        });
        this.mSearchAnchor.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobleRecommendActivity$8dgTcI7fDisUacjfcMPTMYcQmkc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NobleRecommendActivity.this.lambda$initListener$3$NobleRecommendActivity(view);
            }
        });
        findViewById(R$id.tv_watch_record).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobleRecommendActivity$FN68JNuDnCFcBGsx6XGh15rJo9U
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NobleRecommendActivity.this.lambda$initListener$4$NobleRecommendActivity(view);
            }
        });
        findViewById(R$id.iv_help).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobleRecommendActivity$x-qtLSvoFyjFABUAqkMD3rcMPg0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NobleRecommendActivity.this.lambda$initListener$5$NobleRecommendActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$NobleRecommendActivity(View view) {
        showDialog(RecommendDialog.RECOMMEND_ANONYMOUS);
    }

    public /* synthetic */ void lambda$initListener$1$NobleRecommendActivity(View view) {
        showDialog(RecommendDialog.RECOMMEND_PUBLIC);
    }

    public /* synthetic */ void lambda$initListener$3$NobleRecommendActivity(View view) {
        if (this.recommendCount.get() == 0) {
            RecommendDialog.newInstance(RecommendDialog.RECOMMEND_FAIL_2, getString(R$string.fq_text_recommend_count_fail_title_tips), getString(R$string.fq_text_recommend_count_fail_tips)).setOnSureListener(new RecommendDialog.OnRecommendListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobleRecommendActivity$1zL0v6dg0X1ONCu0jaZn2vmSS3A
                @Override // com.tomatolive.library.p136ui.view.dialog.RecommendDialog.OnRecommendListener
                public final void onRecommend() {
                    NobleRecommendActivity.this.lambda$null$2$NobleRecommendActivity();
                }
            }).show(getSupportFragmentManager());
        } else if (TextUtils.isEmpty(this.mEditTextSearchAnchor.getText().toString().trim())) {
            showToast(R$string.fq_text_input_live_no);
        } else {
            ((NobleRecommendPresenter) this.mPresenter).searchAnchor(this.mEditTextSearchAnchor.getText().toString());
        }
    }

    public /* synthetic */ void lambda$null$2$NobleRecommendActivity() {
        AppUtils.toNobilityOpenActivity(this.mContext, null);
    }

    public /* synthetic */ void lambda$initListener$4$NobleRecommendActivity(View view) {
        startActivity(NobilityRecommendHistoryActivity.class);
    }

    public /* synthetic */ void lambda$initListener$5$NobleRecommendActivity(View view) {
        NobilityGoldUseDescDialog.newInstance(NobilityGoldUseDescDialog.TYPE_RECOMMEND).show(getSupportFragmentManager());
    }

    private void showDialog(String str) {
        if (TextUtils.isEmpty(this.mEditTextSearchAnchor.getText().toString().trim())) {
            showToast(R$string.fq_text_input_live_no);
        } else if (this.anchorEntity == null) {
            showToast(R$string.fq_text_recommend_search_empty);
        } else if (this.recommendCount.get() == 0) {
            RecommendDialog.newInstance(RecommendDialog.RECOMMEND_FAIL_2, getString(R$string.fq_text_recommend_count_fail_title_tips), getString(R$string.fq_text_recommend_count_fail_tips)).setOnSureListener(new RecommendDialog.OnRecommendListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobleRecommendActivity$cNBk_KsvcNfJrBc6fgRbpGnJS6k
                @Override // com.tomatolive.library.p136ui.view.dialog.RecommendDialog.OnRecommendListener
                public final void onRecommend() {
                    NobleRecommendActivity.this.lambda$showDialog$6$NobleRecommendActivity();
                }
            }).show(getSupportFragmentManager());
        } else {
            final String str2 = str.equals(RecommendDialog.RECOMMEND_PUBLIC) ? "0" : "1";
            RecommendDialog.newInstance(str, this.anchorEntity.nickname).setOnSureListener(new RecommendDialog.OnRecommendListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobleRecommendActivity$g8ZUj1l2Fkz3foIxeM7S_WkYDcE
                @Override // com.tomatolive.library.p136ui.view.dialog.RecommendDialog.OnRecommendListener
                public final void onRecommend() {
                    NobleRecommendActivity.this.lambda$showDialog$7$NobleRecommendActivity(str2);
                }
            }).show(getSupportFragmentManager());
        }
    }

    public /* synthetic */ void lambda$showDialog$6$NobleRecommendActivity() {
        AppUtils.toNobilityOpenActivity(this.mContext, null);
    }

    public /* synthetic */ void lambda$showDialog$7$NobleRecommendActivity(String str) {
        ((NobleRecommendPresenter) this.mPresenter).recommend(str, this.anchorEntity.liveId);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRecommendSearchView
    public void onDataSuccess(AnchorEntity anchorEntity) {
        int i = 0;
        this.llSearchBg.setVisibility(0);
        if (TextUtils.isEmpty(anchorEntity.nickname)) {
            this.anchorEntity = null;
            this.tvSearchResult.setText(R$string.fq_text_recommend_search_empty);
            this.tvLiving.setVisibility(4);
            return;
        }
        this.anchorEntity = anchorEntity;
        this.tvSearchResult.setText(this.mContext.getString(R$string.fq_text_live_id, anchorEntity.expGrade, anchorEntity.nickname));
        TextView textView = this.tvLiving;
        if (!TextUtils.equals(anchorEntity.liveStatus, "1")) {
            i = 4;
        }
        textView.setVisibility(i);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRecommendSearchView
    public void onRecommendSuccess() {
        EventBus.getDefault().post(new NobilityOpenEvent());
        showToast(R$string.fq_text_recommend_success);
        this.recommendCount.decrementAndGet();
        this.mRecommendCount.setText(Html.fromHtml(getString(R$string.fq_text_remain_recommend_times, new Object[]{String.valueOf(this.recommendCount)})));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRecommendSearchView
    public void onRecommendFail(int i, String str) {
        String string;
        String str2;
        switch (i) {
            case ConstantUtils.NOBILITY_RECOMMEND_STATUS_ERROR_CODE /* 200111 */:
                string = getString(R$string.fq_text_recommend_count_fail_title_tips_2);
                str = getString(R$string.fq_text_recommend_is_not_living);
                str2 = RecommendDialog.RECOMMEND_FAIL;
                break;
            case ConstantUtils.NOBILITY_RECOMMEND_USED_ERROR_CODE /* 200112 */:
                string = getString(R$string.fq_text_recommend_count_fail_title_tips_3);
                str2 = RecommendDialog.RECOMMEND_FAIL_3;
                break;
            default:
                return;
        }
        RecommendDialog.newInstance(str2, string, str).show(getSupportFragmentManager());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRecommendSearchView
    public void onRecommendCount(MyNobilityEntity myNobilityEntity) {
        this.recommendCount.set(NumberUtils.string2int(myNobilityEntity.recommendNumber));
        this.mRecommendCount.setText(Html.fromHtml(getString(R$string.fq_text_remain_recommend_times, new Object[]{String.valueOf(this.recommendCount)})));
        this.tvExpire.setText(Html.fromHtml(getString(R$string.fq_text_expired_time, new Object[]{TimeUtils.millis2String(NumberUtils.string2long(myNobilityEntity.endTime) * 1000)})));
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (baseEvent instanceof NobilityOpenEvent) {
            ((NobleRecommendPresenter) this.mPresenter).getRecommendCount();
        }
    }
}
