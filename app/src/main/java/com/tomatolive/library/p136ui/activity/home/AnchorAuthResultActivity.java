package com.tomatolive.library.p136ui.activity.home;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveHelperAppConfigEntity;
import com.tomatolive.library.p136ui.activity.live.PrepareLiveActivity;
import com.tomatolive.library.p136ui.presenter.AnchorAuthResultPresenter;
import com.tomatolive.library.p136ui.view.dialog.CommonRuleTipsDialog;
import com.tomatolive.library.p136ui.view.iview.IAnchorAuthResultView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.activity.home.AnchorAuthResultActivity */
/* loaded from: classes3.dex */
public class AnchorAuthResultActivity extends BaseActivity<AnchorAuthResultPresenter> implements IAnchorAuthResultView {
    private String androidPackageName;
    private List<LiveHelperAppConfigEntity.ChannelConfigEntity> configChannelEntityList;
    private ImageView ivAuthLogo;
    private ImageView ivContactLogo;
    private ImageView ivContactLogo2;
    private ImageView ivContactLogo3;
    private LinearLayout llContactBg;
    private LinearLayout llContactItemBg1;
    private LinearLayout llContactItemBg2;
    private LinearLayout llContactItemBg3;
    private String startLiveAppDownloadUrl;
    private TextView tvContactTitle;
    private TextView tvContactTitle2;
    private TextView tvContactTitle3;
    private TextView tvDownloadTips;
    private TextView tvReAuth;
    private TextView tvResultInfo;
    private TextView tvResultTips;
    private TextView tvStartLive;
    private int type;
    private final int TYPE_QQ_INDEX = 0;
    private final int TYPE_WECHAT_INDEX = 1;
    private final int TYPE_POTATO_INDEX = 2;

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorAuthResultView
    public void onCustomerServiceFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public AnchorAuthResultPresenter mo6636createPresenter() {
        return new AnchorAuthResultPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_identy_result;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_anchor_identy);
        this.ivAuthLogo = (ImageView) findViewById(R$id.iv_auth_logo);
        this.tvResultInfo = (TextView) findViewById(R$id.tv_result_info);
        this.tvResultTips = (TextView) findViewById(R$id.tv_fail_info);
        this.tvStartLive = (TextView) findViewById(R$id.tv_start_live);
        this.tvReAuth = (TextView) findViewById(R$id.tv_re_auth);
        this.llContactBg = (LinearLayout) findViewById(R$id.ll_contact_bg);
        this.tvDownloadTips = (TextView) findViewById(R$id.tv_download_tips);
        this.llContactItemBg1 = (LinearLayout) findViewById(R$id.ll_contact_item_bg_1);
        this.ivContactLogo = (ImageView) findViewById(R$id.iv_contact_logo);
        this.tvContactTitle = (TextView) findViewById(R$id.tv_contact_title);
        this.llContactItemBg2 = (LinearLayout) findViewById(R$id.ll_contact_item_bg_2);
        this.ivContactLogo2 = (ImageView) findViewById(R$id.iv_contact_logo_2);
        this.tvContactTitle2 = (TextView) findViewById(R$id.tv_contact_title_2);
        this.llContactItemBg3 = (LinearLayout) findViewById(R$id.ll_contact_item_bg_3);
        this.ivContactLogo3 = (ImageView) findViewById(R$id.iv_contact_logo_3);
        this.tvContactTitle3 = (TextView) findViewById(R$id.tv_contact_title_3);
        this.type = getIntent().getIntExtra(ConstantUtils.AUTH_TYPE, 0);
        showCheckResult();
        if (this.type == 0) {
            ((AnchorAuthResultPresenter) this.mPresenter).onAnchorAuth();
        }
        if (this.type != 1) {
            ((AnchorAuthResultPresenter) this.mPresenter).onCustomerService();
            CommonRuleTipsDialog.newInstance(ConstantUtils.APP_PARAM_AUTH_PROMPT, getString(R$string.fq_contact_warm_tips), true, 0.32d).show(getSupportFragmentManager());
        }
        this.tvStartLive.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$AnchorAuthResultActivity$BShZj6EyLFF2xQqbJBjwuPRlFSs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnchorAuthResultActivity.this.lambda$initView$0$AnchorAuthResultActivity(view);
            }
        });
        this.tvReAuth.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$AnchorAuthResultActivity$zBsl4qf2PFZCT_wm0_eVTdA_BPM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnchorAuthResultActivity.this.lambda$initView$1$AnchorAuthResultActivity(view);
            }
        });
        this.llContactItemBg1.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.AnchorAuthResultActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AnchorAuthResultActivity.this.toJumpContactUrl(0);
            }
        });
        this.llContactItemBg2.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.AnchorAuthResultActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AnchorAuthResultActivity.this.toJumpContactUrl(1);
            }
        });
        this.llContactItemBg3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.AnchorAuthResultActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AnchorAuthResultActivity.this.toJumpContactUrl(2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$AnchorAuthResultActivity(View view) {
        if (AppUtils.isEnableLiveHelperJump()) {
            AppUtils.toLiveHelperApp(this.mContext, this.androidPackageName, this.startLiveAppDownloadUrl, getSupportFragmentManager());
            return;
        }
        startActivity(PrepareLiveActivity.class);
        finish();
    }

    public /* synthetic */ void lambda$initView$1$AnchorAuthResultActivity(View view) {
        startActivity(AnchorAuthActivity.class);
        finish();
    }

    private void showCheckResult() {
        int i = this.type;
        int i2 = 4;
        if (i == -1) {
            this.ivAuthLogo.setImageResource(R$drawable.fq_identy_failure);
            this.tvResultInfo.setText(R$string.fq_complete_identy);
            this.tvResultTips.setVisibility(0);
            this.tvResultTips.setText(R$string.fq_identity_information_does_not_match);
            this.tvReAuth.setVisibility(0);
            this.llContactBg.setVisibility(0);
            this.tvStartLive.setVisibility(4);
            this.tvDownloadTips.setVisibility(4);
        } else if (i == 0) {
            this.ivAuthLogo.setImageResource(R$drawable.fq_anchor_identy);
            this.tvResultInfo.setText(R$string.fq_complete_identy);
            this.tvResultTips.setVisibility(0);
            this.tvResultTips.setText(R$string.fq_wait_identy);
            this.tvReAuth.setVisibility(4);
            this.llContactBg.setVisibility(0);
            this.tvStartLive.setVisibility(4);
            this.tvDownloadTips.setVisibility(4);
        } else if (i != 1) {
        } else {
            this.ivAuthLogo.setImageResource(R$drawable.fq_identy_succ);
            this.tvResultInfo.setText(R$string.fq_already_identy);
            this.tvResultTips.setVisibility(0);
            this.tvResultTips.setText(R$string.fq_anchor_auth_success_tips);
            this.tvReAuth.setVisibility(4);
            this.llContactBg.setVisibility(4);
            this.tvStartLive.setVisibility(0);
            TextView textView = this.tvDownloadTips;
            if (AppUtils.isEnableLiveHelperJump()) {
                i2 = 0;
            }
            textView.setVisibility(i2);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorAuthResultView
    public void onAnchorAuthSuccess(AnchorEntity anchorEntity) {
        this.type = anchorEntity.isChecked;
        showCheckResult();
        int i = this.type;
        if (i == 1 || i == -1) {
            return;
        }
        starTimer();
    }

    private void starTimer() {
        Observable.timer(5L, TimeUnit.SECONDS).compose(bindToLifecycle()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.tomatolive.library.ui.activity.home.AnchorAuthResultActivity.4
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                ((AnchorAuthResultPresenter) ((BaseActivity) AnchorAuthResultActivity.this).mPresenter).onAnchorAuth();
            }
        });
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorAuthResultView
    public void onLiveListFail() {
        starTimer();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorAuthResultView
    public void onCustomerServiceSuccess(LiveHelperAppConfigEntity liveHelperAppConfigEntity) {
        if (liveHelperAppConfigEntity == null) {
            this.llContactBg.setVisibility(4);
            return;
        }
        this.startLiveAppDownloadUrl = liveHelperAppConfigEntity.startLiveAppDownloadUrl;
        this.androidPackageName = liveHelperAppConfigEntity.androidPackageName;
        this.configChannelEntityList = liveHelperAppConfigEntity.customerChannelConfigs;
        List<LiveHelperAppConfigEntity.ChannelConfigEntity> list = this.configChannelEntityList;
        if (list == null || list.isEmpty()) {
            this.llContactBg.setVisibility(4);
            return;
        }
        this.llContactBg.setVisibility(0);
        if (this.configChannelEntityList.size() >= 3) {
            this.llContactItemBg1.setVisibility(0);
            this.tvContactTitle.setText(getChannelName(0));
            this.ivContactLogo.setImageResource(getLogoResId(0));
            this.llContactItemBg2.setVisibility(0);
            this.tvContactTitle2.setText(getChannelName(1));
            this.ivContactLogo2.setImageResource(getLogoResId(1));
            this.llContactItemBg3.setVisibility(0);
            this.tvContactTitle3.setText(getChannelName(2));
            this.ivContactLogo3.setImageResource(getLogoResId(2));
        } else if (this.configChannelEntityList.size() >= 2) {
            this.llContactItemBg1.setVisibility(0);
            this.tvContactTitle.setText(getChannelName(0));
            this.ivContactLogo.setImageResource(getLogoResId(0));
            this.llContactItemBg2.setVisibility(0);
            this.tvContactTitle2.setText(getChannelName(1));
            this.ivContactLogo2.setImageResource(getLogoResId(1));
            this.llContactItemBg3.setVisibility(8);
        } else {
            this.llContactItemBg1.setVisibility(0);
            this.tvContactTitle.setText(getChannelName(0));
            this.ivContactLogo.setImageResource(getLogoResId(0));
            this.llContactItemBg2.setVisibility(8);
            this.llContactItemBg3.setVisibility(8);
        }
    }

    private LiveHelperAppConfigEntity.ChannelConfigEntity getChannelConfigEntity(int i) {
        List<LiveHelperAppConfigEntity.ChannelConfigEntity> list = this.configChannelEntityList;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.configChannelEntityList.get(i);
    }

    private String getChannelName(int i) {
        LiveHelperAppConfigEntity.ChannelConfigEntity channelConfigEntity = getChannelConfigEntity(i);
        return channelConfigEntity == null ? "" : channelConfigEntity.channelName;
    }

    @DrawableRes
    private int getLogoResId(int i) {
        LiveHelperAppConfigEntity.ChannelConfigEntity channelConfigEntity = getChannelConfigEntity(i);
        if (channelConfigEntity == null) {
            return R$drawable.fq_ic_placeholder_avatar;
        }
        String str = channelConfigEntity.channelType;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -982438873) {
            if (hashCode != -792723642) {
                if (hashCode == 3616 && str.equals("qq")) {
                    c = 1;
                }
            } else if (str.equals("weChat")) {
                c = 0;
            }
        } else if (str.equals("potato")) {
            c = 2;
        }
        if (c == 0) {
            return R$drawable.fq_ic_contact_logo_wechat;
        }
        if (c == 1) {
            return R$drawable.fq_ic_contact_logo_qq;
        }
        if (c == 2) {
            return R$drawable.fq_ic_contact_logo_potato;
        }
        return R$drawable.fq_ic_placeholder_avatar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toJumpContactUrl(int i) {
        LiveHelperAppConfigEntity.ChannelConfigEntity channelConfigEntity = getChannelConfigEntity(i);
        if (channelConfigEntity == null) {
            return;
        }
        AppUtils.onSysWebView(this.mContext, channelConfigEntity.channelUrl);
    }
}
