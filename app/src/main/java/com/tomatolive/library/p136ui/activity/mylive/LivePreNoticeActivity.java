package com.tomatolive.library.p136ui.activity.mylive;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.LivePreNoticeEntity;
import com.tomatolive.library.p136ui.presenter.LivePreNoticePresenter;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.iview.ILivePreNoticeView;
import com.tomatolive.library.utils.EmojiFilter;
import com.tomatolive.library.utils.live.SimpleRxObserver;

/* renamed from: com.tomatolive.library.ui.activity.mylive.LivePreNoticeActivity */
/* loaded from: classes3.dex */
public class LivePreNoticeActivity extends BaseActivity<LivePreNoticePresenter> implements ILivePreNoticeView {
    private EditText etContent;
    private TextView tvCurrentPre;
    private TextView tvNewPre;
    private TextView tvSave;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public LivePreNoticePresenter mo6636createPresenter() {
        return new LivePreNoticePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_live_pre_notice;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_my_live_pre_notice);
        this.etContent = (EditText) findViewById(R$id.et_content);
        this.tvCurrentPre = (TextView) findViewById(R$id.tv_current_pre);
        this.tvNewPre = (TextView) findViewById(R$id.tv_new_pre);
        this.tvSave = (TextView) findViewById(R$id.tv_save);
        ((LivePreNoticePresenter) this.mPresenter).getDataList();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        RxTextView.textChanges(this.etContent).map($$Lambda$LivePreNoticeActivity$Ug5s0bIA1uy6ACXtAovkEEE0.INSTANCE).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.activity.mylive.LivePreNoticeActivity.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                LivePreNoticeActivity.this.tvSave.setEnabled(bool.booleanValue());
            }
        });
        this.tvSave.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$LivePreNoticeActivity$tWTks0CUWoSRHd6bMdjfOn68dGU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LivePreNoticeActivity.this.lambda$initListener$2$LivePreNoticeActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$2$LivePreNoticeActivity(View view) {
        final String trim = this.etContent.getText().toString().trim();
        if (EmojiFilter.containsEmoji(trim)) {
            showToast(R$string.fq_no_emoji_search);
        } else if (trim.length() < 5) {
            showToast(R$string.fq_my_live_pre_notice_chat_short_tips);
        } else if (trim.length() > 25) {
            showToast(R$string.fq_my_live_pre_notice_chat_long_tips);
        } else {
            SureCancelDialog.newInstance(getString(R$string.fq_my_live_sure_submit_pre_notice), new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$LivePreNoticeActivity$PCWu8l1nG4gGI1Kg0_cmqe8XLbA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LivePreNoticeActivity.this.lambda$null$1$LivePreNoticeActivity(trim, view2);
                }
            }).show(getSupportFragmentManager());
        }
    }

    public /* synthetic */ void lambda$null$1$LivePreNoticeActivity(String str, View view) {
        ((LivePreNoticePresenter) this.mPresenter).onSaveContent(str);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ILivePreNoticeView
    public void onDataSuccess(LivePreNoticeEntity livePreNoticeEntity) {
        if (livePreNoticeEntity == null) {
            return;
        }
        if (!TextUtils.isEmpty(livePreNoticeEntity.useLiveHerald)) {
            this.tvCurrentPre.setText(getString(R$string.fq_my_live_current_pre_notice_tips, new Object[]{livePreNoticeEntity.useLiveHerald}));
        } else {
            this.tvCurrentPre.setText(R$string.fq_my_live_no_current_pre_notice_tips);
        }
        if (TextUtils.isEmpty(livePreNoticeEntity.auditLiveHerald)) {
            return;
        }
        this.tvNewPre.setText(getString(R$string.fq_my_live_new_pre_notice_tips, new Object[]{livePreNoticeEntity.auditLiveHerald}));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ILivePreNoticeView
    public void onSaveSuccess() {
        showToast(R$string.fq_submit_suc);
        this.etContent.setText("");
        ((LivePreNoticePresenter) this.mPresenter).getDataList();
    }
}
