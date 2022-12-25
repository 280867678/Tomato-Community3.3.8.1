package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.p136ui.view.widget.badgeView.QBadgeView;
import com.tomatolive.library.p136ui.view.widget.marqueenview.MarqueeTextView;
import com.tomatolive.library.p136ui.view.widget.progress.QMTaskProgressView;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.view.widget.QMNoticeWindow */
/* loaded from: classes4.dex */
public class QMNoticeWindow extends RelativeLayout {
    private View gitIconRoot;
    private boolean isShowRed;
    private ImageView ivGiftIcon;
    private Context mContext;
    private MarqueeTextView mTaskName;
    private QBadgeView qBadgeView;
    private QMTaskProgressView qmTaskProgressView;

    public QMNoticeWindow(Context context) {
        this(context, null);
    }

    public QMNoticeWindow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QMNoticeWindow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        initView();
    }

    private void initView() {
        RelativeLayout.inflate(this.mContext, R$layout.fq_qm_notice_window, this);
        this.mTaskName = (MarqueeTextView) findViewById(R$id.mv_task_name);
        this.ivGiftIcon = (ImageView) findViewById(R$id.iv_gift_icon);
        this.qmTaskProgressView = (QMTaskProgressView) findViewById(R$id.qm_task_progress);
        this.gitIconRoot = findViewById(R$id.fl_root_icon);
    }

    public void showRedPoint() {
        if (this.qBadgeView == null) {
            this.qBadgeView = new QBadgeView(this.mContext);
            this.qBadgeView.bindTarget(this.gitIconRoot).setBadgeTextColor(-1).setBadgePadding(1.0f, true).isNoNumber(true).setBadgeGravity(8388661).setBadgeBackgroundColor(ContextCompat.getColor(this.mContext, R$color.fq_qm_primary)).stroke(-1, 1.0f, true);
        }
        this.qBadgeView.showSinglePoint();
        this.isShowRed = true;
    }

    public void hideRedPoint() {
        if (this.isShowRed) {
            QBadgeView qBadgeView = this.qBadgeView;
            if (qBadgeView != null) {
                qBadgeView.hideSinglePoint();
            }
            this.isShowRed = false;
        }
    }

    public void show(QMInteractTaskEntity qMInteractTaskEntity) {
        if (TextUtils.equals("1", qMInteractTaskEntity.taskType)) {
            this.qmTaskProgressView.setVisibility(0);
            this.mTaskName.setVisibility(8);
            this.qmTaskProgressView.setMaxProgress(NumberUtils.string2int(qMInteractTaskEntity.giftNum));
            this.qmTaskProgressView.setProgressValue(NumberUtils.string2int(qMInteractTaskEntity.chargeGiftNum));
        } else {
            this.qmTaskProgressView.setVisibility(8);
            this.mTaskName.setVisibility(0);
            this.mTaskName.startWithText(qMInteractTaskEntity.taskName);
        }
        GlideUtils.loadImage(this.mContext, this.ivGiftIcon, qMInteractTaskEntity.giftUrl);
    }

    public void onRelease() {
        MarqueeTextView marqueeTextView = this.mTaskName;
        if (marqueeTextView != null) {
            marqueeTextView.stopScroll();
        }
    }

    public /* synthetic */ void lambda$setProgressValue$0$QMNoticeWindow(int i) {
        this.qmTaskProgressView.setProgressValue(i);
    }

    public void setProgressValue(final int i) {
        this.qmTaskProgressView.post(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.-$$Lambda$QMNoticeWindow$nZDTXgbAMM_KlSvyHTl2Ed2r4mM
            @Override // java.lang.Runnable
            public final void run() {
                QMNoticeWindow.this.lambda$setProgressValue$0$QMNoticeWindow(i);
            }
        });
    }
}
