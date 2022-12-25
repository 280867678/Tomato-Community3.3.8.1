package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.TaskBoxEntity;
import com.tomatolive.library.p136ui.view.task.TaskBoxUtils;
import com.tomatolive.library.p136ui.view.widget.badgeView.QBadgeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.RxViewUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.tomatolive.library.ui.view.custom.TaskBoxView */
/* loaded from: classes3.dex */
public class TaskBoxView extends RelativeLayout {
    private RelativeLayout boxRoot;
    private Disposable disposable;
    private ImageView ivIcon;
    private OnRefreshTaskListener listener;
    private TextView mTaskCountdown;
    private TextView mTaskReceive;
    private QBadgeView qBadgeView;
    private AtomicInteger recieveCount;

    /* renamed from: com.tomatolive.library.ui.view.custom.TaskBoxView$OnRefreshTaskListener */
    /* loaded from: classes3.dex */
    public interface OnRefreshTaskListener {
        void onRefreshTask(TaskBoxEntity taskBoxEntity);

        void onShowDialog();

        void onTaskComplete(TaskBoxEntity taskBoxEntity);
    }

    public void setOnRefreshTaskListener(OnRefreshTaskListener onRefreshTaskListener) {
        this.listener = onRefreshTaskListener;
    }

    public TaskBoxView(Context context) {
        this(context, null);
    }

    public TaskBoxView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TaskBoxView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.recieveCount = new AtomicInteger(0);
        init(context);
    }

    private void init(Context context) {
        RelativeLayout.inflate(context, R$layout.fq_taskbox_layout, this);
        this.boxRoot = (RelativeLayout) findViewById(R$id.rl_task_root);
        this.ivIcon = (ImageView) findViewById(R$id.iv_taskbox);
        this.mTaskReceive = (TextView) findViewById(R$id.task_receive);
        this.mTaskCountdown = (TextView) findViewById(R$id.task_countdown);
        this.boxRoot.setVisibility(4);
        this.qBadgeView = new QBadgeView(context);
        this.qBadgeView.bindTarget(this.ivIcon).setBadgeTextColor(-1).setBadgePadding(1.0f, true).setBadgeGravity(8388661).setBadgeBackgroundColor(ContextCompat.getColor(getContext(), R$color.fq_colorRed)).setBadgeTextSize(10.0f, true).stroke(-1, 1.0f, true);
        initListener();
    }

    private void initListener() {
        RxViewUtils.getInstance().throttleFirst(this.boxRoot, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$TaskBoxView$C2w7FTiFo10M-irwD5EXrInoxQI
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                TaskBoxView.this.lambda$initListener$0$TaskBoxView(obj);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$TaskBoxView(Object obj) {
        OnRefreshTaskListener onRefreshTaskListener = this.listener;
        if (onRefreshTaskListener != null) {
            onRefreshTaskListener.onShowDialog();
        }
    }

    public void changeRedCount(boolean z) {
        if (z) {
            this.recieveCount.getAndIncrement();
        } else {
            this.recieveCount.getAndDecrement();
        }
        refreshRedCount();
    }

    private void refreshRedCount() {
        if (this.recieveCount.get() > 0) {
            this.qBadgeView.setVisibility(0);
            this.qBadgeView.setBadgeNumber(this.recieveCount.get());
            return;
        }
        this.qBadgeView.setVisibility(8);
    }

    public void checkToCountdown() {
        Disposable disposable;
        TaskBoxEntity taskingEntity = getTaskingEntity(TaskBoxUtils.getInstance().getData());
        if (this.recieveCount.get() != 0) {
            if (taskingEntity != null && ((disposable = this.disposable) == null || disposable.isDisposed())) {
                startTaskCountdown(taskingEntity);
            }
            this.mTaskCountdown.setVisibility(8);
            this.mTaskReceive.setVisibility(0);
        } else if (taskingEntity != null) {
            Disposable disposable2 = this.disposable;
            if (disposable2 == null || disposable2.isDisposed()) {
                startTaskCountdown(taskingEntity);
            }
            this.mTaskCountdown.setVisibility(0);
            this.mTaskReceive.setVisibility(8);
        } else {
            this.mTaskCountdown.setVisibility(8);
            this.mTaskReceive.setVisibility(8);
        }
    }

    public void refreshTaskButton() {
        this.boxRoot.setVisibility(0);
        for (TaskBoxEntity taskBoxEntity : TaskBoxUtils.getInstance().getData()) {
            if (taskBoxEntity.getStatus() == 1) {
                this.recieveCount.getAndIncrement();
                this.mTaskCountdown.setText(getContext().getString(AppUtils.isConsumptionPermissionUser() ? R$string.fq_receive_task : R$string.fq_receive_task_box));
            }
            if (taskBoxEntity.getStatus() == 0) {
                String stringForTime = DateUtils.stringForTime(NumberUtils.string2long(taskBoxEntity.getOpenTime(), 1L) * 1000);
                TextView textView = this.mTaskCountdown;
                if (!AppUtils.isConsumptionPermissionUser()) {
                    stringForTime = getContext().getString(R$string.fq_receive_task_box);
                }
                textView.setText(stringForTime);
            }
        }
        refreshRedCount();
        checkToCountdown();
    }

    @Nullable
    private TaskBoxEntity getTaskingEntity(List<TaskBoxEntity> list) {
        for (TaskBoxEntity taskBoxEntity : list) {
            if (taskBoxEntity.getStatus() == 0) {
                return taskBoxEntity;
            }
        }
        return null;
    }

    private void startTaskCountdown(final TaskBoxEntity taskBoxEntity) {
        final String openTime = taskBoxEntity.getOpenTime();
        if (!TaskBoxUtils.getInstance().isPushInBackground()) {
            TaskBoxUtils.getInstance().setOpenTime(openTime);
        }
        Observable.interval(1L, TimeUnit.SECONDS).map(new Function() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$TaskBoxView$p_PiB6S_I9oy6FWRfoGH21ezHUg
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                Long valueOf;
                valueOf = Long.valueOf(NumberUtils.string2long(openTime) - ((Long) obj).longValue());
                return valueOf;
            }
        }).take(Long.parseLong(openTime) + 1).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.tomatolive.library.ui.view.custom.TaskBoxView.1
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                TaskBoxView.this.disposable = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                String stringForTime = DateUtils.stringForTime(l.longValue() * 1000);
                TextView textView = TaskBoxView.this.mTaskCountdown;
                if (!AppUtils.isConsumptionPermissionUser()) {
                    stringForTime = TaskBoxView.this.getContext().getString(R$string.fq_receive_task_box);
                }
                textView.setText(stringForTime);
                if (TaskBoxView.this.listener != null) {
                    taskBoxEntity.setOpenTime(String.valueOf(l));
                    TaskBoxView.this.listener.onRefreshTask(taskBoxEntity);
                }
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                TaskBoxView.this.disposable.dispose();
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                TaskBoxView.this.disposable.dispose();
                if (TaskBoxView.this.listener != null) {
                    TaskBoxView.this.listener.onTaskComplete(taskBoxEntity);
                }
            }
        });
    }

    public void release() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        AtomicInteger atomicInteger = this.recieveCount;
        if (atomicInteger != null) {
            atomicInteger.set(0);
        }
    }
}
