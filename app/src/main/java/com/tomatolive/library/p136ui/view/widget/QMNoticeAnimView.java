package com.tomatolive.library.p136ui.view.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ScreenUtils;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.widget.QMNoticeAnimView */
/* loaded from: classes4.dex */
public class QMNoticeAnimView extends RelativeLayout {
    private Disposable cdDisposable;
    private AnimatorSet giftAnimatorSet;
    private boolean isAnchor;
    private ImageView ivAvatar;
    private ImageView ivClose;
    private Context mContext;
    private OnQMInteractCallback onQMInteractCallback;
    private String putUserId;
    private String taskId;
    private TextView tvDone;
    private TextView tvGiftInfo;
    private TextView tvNickName;
    private TextView tvWorkContent;

    public QMNoticeAnimView(Context context) {
        this(context, null);
    }

    public QMNoticeAnimView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QMNoticeAnimView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        initView();
        initListener();
        initAnim();
    }

    private void initView() {
        RelativeLayout.inflate(this.mContext, R$layout.fq_view_qm_notice_anim, this);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.ivClose = (ImageView) findViewById(R$id.iv_close);
        this.tvNickName = (TextView) findViewById(R$id.tv_nick_name);
        this.tvGiftInfo = (TextView) findViewById(R$id.tv_content);
        this.tvWorkContent = (TextView) findViewById(R$id.tv_show_tip);
        this.tvDone = (TextView) findViewById(R$id.tv_done);
    }

    private void initAnim() {
        this.giftAnimatorSet = new AnimatorSet();
        this.giftAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.tomatolive.library.ui.view.widget.QMNoticeAnimView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (QMNoticeAnimView.this.isAnchor) {
                    QMNoticeAnimView.this.tvDone.setEnabled(false);
                    QMNoticeAnimView.this.startCountDown(4L);
                    return;
                }
                QMNoticeAnimView.this.setVisibility(8);
                if (QMNoticeAnimView.this.onQMInteractCallback == null) {
                    return;
                }
                QMNoticeAnimView.this.onQMInteractCallback.onNoticeAnimViewDismissListener();
            }
        });
    }

    private void initListener() {
        this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.-$$Lambda$QMNoticeAnimView$IiixOBFCso0kSAdHyUb-_qjD7zc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QMNoticeAnimView.this.lambda$initListener$0$QMNoticeAnimView(view);
            }
        });
        this.tvDone.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.-$$Lambda$QMNoticeAnimView$TlqX5-1mS2vtY1HrmJJ4zIqqes4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QMNoticeAnimView.this.lambda$initListener$1$QMNoticeAnimView(view);
            }
        });
        this.ivAvatar.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.-$$Lambda$QMNoticeAnimView$jLysls7C5Kcj7RuwDNRs_acvr0I
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QMNoticeAnimView.this.lambda$initListener$2$QMNoticeAnimView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$QMNoticeAnimView(View view) {
        onCancelAnim();
        setVisibility(8);
        OnQMInteractCallback onQMInteractCallback = this.onQMInteractCallback;
        if (onQMInteractCallback != null) {
            onQMInteractCallback.onNoticeAnimViewDismissListener();
        }
    }

    public /* synthetic */ void lambda$initListener$1$QMNoticeAnimView(View view) {
        setVisibility(8);
        OnQMInteractCallback onQMInteractCallback = this.onQMInteractCallback;
        if (onQMInteractCallback != null) {
            onQMInteractCallback.onTaskStatusUpdateListener(this.taskId, ConstantUtils.QM_TASK_STATUS_205);
        }
    }

    public /* synthetic */ void lambda$initListener$2$QMNoticeAnimView(View view) {
        OnQMInteractCallback onQMInteractCallback = this.onQMInteractCallback;
        if (onQMInteractCallback != null) {
            onQMInteractCallback.onUserCardListener(this.putUserId);
        }
    }

    public OnQMInteractCallback getOnQMInteractCallback() {
        return this.onQMInteractCallback;
    }

    public void setOnQMInteractCallback(OnQMInteractCallback onQMInteractCallback) {
        this.onQMInteractCallback = onQMInteractCallback;
    }

    public void showNoticeAnimView(boolean z, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.taskId = str7;
        this.isAnchor = z;
        this.putUserId = str6;
        this.tvDone.setVisibility(4);
        Context context = this.mContext;
        GlideUtils.loadAvatar(context, this.ivAvatar, str, 6, ContextCompat.getColor(context, R$color.fq_colorWhite));
        this.tvNickName.setText(StringUtils.formatStrLen(str2, 5));
        this.tvGiftInfo.setText(Html.fromHtml(this.mContext.getString(R$string.fq_qm_send_gift, str4, str3)));
        this.tvWorkContent.setText(str5);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "translationX", ScreenUtils.getScreenWidth(), 0.0f);
        ofFloat.setDuration(2000L);
        if (z) {
            this.giftAnimatorSet.play(ofFloat);
        } else {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f);
            ofFloat2.setStartDelay(5000L);
            ofFloat2.setDuration(800L);
            this.giftAnimatorSet.play(ofFloat).before(ofFloat2);
        }
        this.giftAnimatorSet.start();
        setVisibility(0);
        setAlpha(1.0f);
    }

    public void onCancelAnim() {
        AnimatorSet animatorSet = this.giftAnimatorSet;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.giftAnimatorSet.cancel();
        }
        Disposable disposable = this.cdDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.cdDisposable.dispose();
        this.cdDisposable = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCountDown(final long j) {
        Observable.interval(1L, TimeUnit.SECONDS).map(new Function() { // from class: com.tomatolive.library.ui.view.widget.-$$Lambda$QMNoticeAnimView$jPGXwlOyzFxz1umMf_vrZ_OL-ng
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                Long valueOf;
                valueOf = Long.valueOf(j - ((Long) obj).longValue());
                return valueOf;
            }
        }).take(j + 1).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.tomatolive.library.ui.view.widget.QMNoticeAnimView.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                QMNoticeAnimView.this.cdDisposable = disposable;
                QMNoticeAnimView.this.tvDone.setText(String.format(QMNoticeAnimView.this.mContext.getString(R$string.fq_qm_countdown), "5"));
                QMNoticeAnimView.this.tvDone.setVisibility(0);
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                if (l.longValue() == 0) {
                    QMNoticeAnimView.this.tvDone.setEnabled(true);
                    QMNoticeAnimView.this.tvDone.setText(R$string.fq_qm_countdown_suc);
                    return;
                }
                QMNoticeAnimView.this.tvDone.setText(String.format(QMNoticeAnimView.this.mContext.getString(R$string.fq_qm_countdown), String.valueOf(l)));
            }
        });
    }

    public void onRelease() {
        onCancelAnim();
        setVisibility(8);
    }
}
