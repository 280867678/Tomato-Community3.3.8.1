package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.p135db.GiftBoxEntity;
import com.tomatolive.library.p136ui.view.widget.badgeView.QBadgeView;
import com.tomatolive.library.p136ui.view.widget.progress.AnimDownloadProgressButton;
import com.tomatolive.library.utils.AnimUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.RxViewUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.custom.GiftBoxView */
/* loaded from: classes3.dex */
public class GiftBoxView extends RelativeLayout {
    private RelativeLayout boxRoot;
    private Disposable chronographDisposable;
    private Disposable countDownDisposable;
    private Disposable expiredDisposable;
    private List<GiftBoxEntity> giftBoxEntityList;
    private ImageView ivIcon;
    private OnSendGiftBoxMsgListener listener;
    private State mState;
    private AnimDownloadProgressButton progressLoading;
    private QBadgeView qBadgeView;
    private TextView tvShowTip;

    /* renamed from: com.tomatolive.library.ui.view.custom.GiftBoxView$OnSendGiftBoxMsgListener */
    /* loaded from: classes3.dex */
    public interface OnSendGiftBoxMsgListener {
        void onSendGiftBoxMsg(GiftBoxEntity giftBoxEntity);

        void onShowDialog(GiftBoxEntity giftBoxEntity);
    }

    /* renamed from: com.tomatolive.library.ui.view.custom.GiftBoxView$State */
    /* loaded from: classes3.dex */
    public enum State {
        INIT,
        WAITING,
        OPENING,
        LOADING,
        EXPIRED
    }

    public GiftBoxView(Context context) {
        this(context, null);
    }

    public GiftBoxView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GiftBoxView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mState = State.INIT;
        init(context);
    }

    private void init(Context context) {
        RelativeLayout.inflate(context, R$layout.fq_giftbox_layout, this);
        this.ivIcon = (ImageView) findViewById(R$id.iv_box);
        this.boxRoot = (RelativeLayout) findViewById(R$id.rl_box_root);
        this.tvShowTip = (TextView) findViewById(R$id.tv_show_tip);
        this.progressLoading = (AnimDownloadProgressButton) findViewById(R$id.fq_loading_btn);
        this.boxRoot.setVisibility(8);
        this.qBadgeView = new QBadgeView(context);
        this.qBadgeView.bindTarget(this.ivIcon).setBadgeTextColor(-1).setBadgePadding(1.0f, true).setBadgeGravity(8388661).setBadgeBackgroundColor(ContextCompat.getColor(getContext(), R$color.fq_colorRed)).stroke(-1, 1.0f, true);
        this.progressLoading.setVisibility(4);
        this.giftBoxEntityList = new ArrayList();
        initListener();
    }

    private void initListener() {
        RxViewUtils.getInstance().throttleFirst(this.boxRoot, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$GiftBoxView$nhF59Ri1YbdqCUpmY-c8uE7OoWI
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                GiftBoxView.this.lambda$initListener$0$GiftBoxView(obj);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$GiftBoxView(Object obj) {
        int i;
        OnSendGiftBoxMsgListener onSendGiftBoxMsgListener;
        if (!AppUtils.isConsumptionPermissionUser(getContext()) || (i = C44133.f5850xa873243d[this.mState.ordinal()]) == 1 || i == 2) {
            return;
        }
        if (i != 3) {
            if ((i != 4 && i != 5) || (onSendGiftBoxMsgListener = this.listener) == null) {
                return;
            }
            onSendGiftBoxMsgListener.onShowDialog(this.giftBoxEntityList.get(0));
            return;
        }
        cancelExpiredDisposable();
        GiftBoxEntity remove = this.giftBoxEntityList.remove(0);
        DBUtils.saveOneGiftBox(remove);
        OnSendGiftBoxMsgListener onSendGiftBoxMsgListener2 = this.listener;
        if (onSendGiftBoxMsgListener2 != null) {
            onSendGiftBoxMsgListener2.onSendGiftBoxMsg(remove);
        }
        if (this.giftBoxEntityList.size() == 0) {
            showEmptyBox();
            return;
        }
        setBadgeCount();
        showLoading();
    }

    /* renamed from: com.tomatolive.library.ui.view.custom.GiftBoxView$3 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C44133 {

        /* renamed from: $SwitchMap$com$tomatolive$library$ui$view$custom$GiftBoxView$State */
        static final /* synthetic */ int[] f5850xa873243d = new int[State.values().length];

        static {
            try {
                f5850xa873243d[State.INIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f5850xa873243d[State.EXPIRED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f5850xa873243d[State.OPENING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f5850xa873243d[State.LOADING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f5850xa873243d[State.WAITING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyBox() {
        this.mState = State.INIT;
        cancelChronographDisposable();
        this.boxRoot.setVisibility(8);
    }

    public void showLoading() {
        this.mState = State.LOADING;
        this.ivIcon.setImageResource(R$drawable.fq_imgs_box_close);
        this.tvShowTip.setVisibility(4);
        this.progressLoading.setVisibility(0);
        this.progressLoading.setLoadingEndListener(new AnimDownloadProgressButton.LoadingEndListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$GiftBoxView$xovRGYXDzcFJVJhUgp54pk-67i8
            @Override // com.tomatolive.library.p136ui.view.widget.progress.AnimDownloadProgressButton.LoadingEndListener
            public final void onLoadingEnd() {
                GiftBoxView.this.lambda$showLoading$1$GiftBoxView();
            }
        });
        this.progressLoading.setProgressText("loading...", 100.0f);
    }

    public /* synthetic */ void lambda$showLoading$1$GiftBoxView() {
        this.progressLoading.setVisibility(8);
        this.progressLoading.setProgress(0.0f);
        showNextBox();
    }

    public void setOnSendGiftBoxMsgListener(OnSendGiftBoxMsgListener onSendGiftBoxMsgListener) {
        this.listener = onSendGiftBoxMsgListener;
    }

    public void startChronographTimer() {
        this.chronographDisposable = Observable.interval(1L, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new Consumer() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$GiftBoxView$rMkOLZjjdidcB5avXgT1Pf4FKxs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                GiftBoxView.this.lambda$startChronographTimer$2$GiftBoxView((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$startChronographTimer$2$GiftBoxView(Long l) throws Exception {
        synchronized (GiftBoxView.class) {
            for (int size = this.giftBoxEntityList.size() - 1; size >= 0; size--) {
                this.giftBoxEntityList.get(size).incrementTime++;
            }
        }
    }

    public void cancelChronographDisposable() {
        Disposable disposable = this.chronographDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.chronographDisposable.dispose();
        this.chronographDisposable = null;
    }

    public void cancelCountDownDisposable() {
        Disposable disposable = this.countDownDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.countDownDisposable.dispose();
        this.countDownDisposable = null;
    }

    public void cancelExpiredDisposable() {
        Disposable disposable = this.expiredDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.expiredDisposable.dispose();
        this.expiredDisposable = null;
    }

    public void cancelLoading() {
        AnimDownloadProgressButton animDownloadProgressButton = this.progressLoading;
        if (animDownloadProgressButton != null) {
            animDownloadProgressButton.cancelAnimaiton();
        }
    }

    public void showBoxList(List<GiftBoxEntity> list, String str) {
        if (list == null || list.size() == 0) {
            DBUtils.deleteGiftBoxList(str);
            this.boxRoot.setVisibility(8);
            return;
        }
        this.giftBoxEntityList.clear();
        List<String> giftBoxIdList = DBUtils.getGiftBoxIdList(str);
        ArrayList arrayList = new ArrayList();
        for (GiftBoxEntity giftBoxEntity : list) {
            if (!giftBoxIdList.contains(giftBoxEntity.giftBoxUniqueCode)) {
                giftBoxEntity.liveId = str;
                giftBoxEntity.userId = UserInfoManager.getInstance().getUserId();
                arrayList.add(giftBoxEntity);
            }
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            GiftBoxEntity giftBoxEntity2 = (GiftBoxEntity) arrayList.get(size);
            if (giftBoxEntity2.openTime == 0 && giftBoxEntity2.expirationTime == 0) {
                arrayList.remove(size);
            }
        }
        if (arrayList.size() == 0) {
            this.boxRoot.setVisibility(8);
            return;
        }
        this.giftBoxEntityList.addAll(arrayList);
        startChronographTimer();
        setBadgeCount();
        this.boxRoot.setVisibility(0);
        showLoading();
    }

    private void showAnim() {
        this.mState = State.LOADING;
        this.boxRoot.setVisibility(0);
        this.progressLoading.setVisibility(8);
        this.progressLoading.setProgress(0.0f);
        showNextBox();
    }

    public void addOneBox(GiftBoxEntity giftBoxEntity) {
        this.giftBoxEntityList.add(giftBoxEntity);
        setBadgeCount();
        if (this.mState == State.INIT) {
            startChronographTimer();
            showAnim();
        }
    }

    public void showNextBox() {
        if (this.giftBoxEntityList.size() > 0) {
            GiftBoxEntity giftBoxEntity = this.giftBoxEntityList.get(0);
            long j = giftBoxEntity.incrementTime;
            long j2 = giftBoxEntity.openTime;
            long j3 = giftBoxEntity.expirationTime;
            int i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
            if (i < 0) {
                this.mState = State.WAITING;
                this.ivIcon.setImageResource(R$drawable.fq_imgs_box_close);
                startWaitCountDown(j, j2, j3);
                return;
            }
            if (i >= 0) {
                long j4 = j - j2;
                if (j4 < j3) {
                    showOpenBoxAnim();
                    startExpiredCountDown(j3 - j4);
                    return;
                }
            }
            if (j < j2 + j3) {
                return;
            }
            this.mState = State.EXPIRED;
            this.ivIcon.setImageResource(R$drawable.fq_imgs_box_open);
            this.tvShowTip.setVisibility(0);
            this.tvShowTip.setText(AppUtils.isConsumptionPermissionUser() ? R$string.fq_receive_box : R$string.fq_receive_task_box);
            this.giftBoxEntityList.remove(0);
            setBadgeCount();
            this.progressLoading.setVisibility(8);
            this.progressLoading.setProgress(0.0f);
            showNextBox();
            return;
        }
        showEmptyBox();
    }

    private void startWaitCountDown(final long j, final long j2, final long j3) {
        this.tvShowTip.setVisibility(0);
        this.tvShowTip.setText(AppUtils.isConsumptionPermissionUser() ? DateUtils.secondToString(j2 - j) : getContext().getString(R$string.fq_receive_task_box));
        Observable.interval(1L, TimeUnit.SECONDS).take((j2 - j) + 1).map(new Function() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$GiftBoxView$gSzRCWKr4tASlFD6O4FYYiH6jqo
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                Long valueOf;
                valueOf = Long.valueOf(((j2 - j) - 1) - ((Long) obj).longValue());
                return valueOf;
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.ui.view.custom.GiftBoxView.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Long l) {
                if (!AppUtils.isConsumptionPermissionUser()) {
                    return;
                }
                GiftBoxView.this.tvShowTip.setText(DateUtils.secondToString(l.longValue()));
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onComplete() {
                GiftBoxView.this.showOpenBoxAnim();
                GiftBoxView.this.startExpiredCountDown(j3);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                GiftBoxView.this.countDownDisposable = disposable;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startExpiredCountDown(final long j) {
        Observable.interval(1L, TimeUnit.SECONDS).take(1 + j).map(new Function() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$GiftBoxView$j22IbXzW5CirYSAPSRbz5TSesXE
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                Long valueOf;
                valueOf = Long.valueOf(j - ((Long) obj).longValue());
                return valueOf;
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.ui.view.custom.GiftBoxView.2
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Long l) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onComplete() {
                GiftBoxEntity giftBoxEntity = (GiftBoxEntity) GiftBoxView.this.giftBoxEntityList.remove(0);
                GiftBoxView.this.setBadgeCount();
                if (GiftBoxView.this.giftBoxEntityList.size() == 0) {
                    GiftBoxView.this.showEmptyBox();
                } else {
                    GiftBoxView.this.showLoading();
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                GiftBoxView.this.expiredDisposable = disposable;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOpenBoxAnim() {
        this.mState = State.OPENING;
        this.ivIcon.setImageResource(R$drawable.fq_imgs_box_open);
        AnimUtils.playShakeAnim(this.boxRoot);
        this.tvShowTip.setVisibility(0);
        this.tvShowTip.setText(AppUtils.isConsumptionPermissionUser() ? R$string.fq_receive_box : R$string.fq_receive_task_box);
    }

    public void setBadgeCount() {
        int size = this.giftBoxEntityList.size();
        if (size == 1) {
            size = 0;
        }
        this.qBadgeView.setBadgeNumber(size);
    }

    public void clear() {
        this.boxRoot.setVisibility(8);
        this.giftBoxEntityList.clear();
        this.mState = State.INIT;
        cancelExpiredDisposable();
        cancelCountDownDisposable();
        cancelChronographDisposable();
        cancelLoading();
    }

    public void release() {
        clear();
    }
}
