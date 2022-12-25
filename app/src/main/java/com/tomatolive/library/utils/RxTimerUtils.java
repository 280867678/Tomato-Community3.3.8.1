package com.tomatolive.library.utils;

import android.content.Context;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class RxTimerUtils {

    /* loaded from: classes4.dex */
    public interface RxTimerAction {
        void action(long j);
    }

    private RxTimerUtils() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class SingletonHolder {
        private static final RxTimerUtils INSTANCE = new RxTimerUtils();

        private SingletonHolder() {
        }
    }

    public static RxTimerUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void timer(Context context, long j, TimeUnit timeUnit, final RxTimerAction rxTimerAction) {
        if (context instanceof RxAppCompatActivity) {
            Observable.timer(j, timeUnit).observeOn(AndroidSchedulers.mainThread()).compose(((RxAppCompatActivity) context).bindUntilEvent(ActivityEvent.DESTROY)).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.utils.RxTimerUtils.1
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(Long l) {
                    RxTimerAction rxTimerAction2 = rxTimerAction;
                    if (rxTimerAction2 != null) {
                        rxTimerAction2.action(l.longValue());
                    }
                }
            });
        } else {
            Observable.timer(j, timeUnit).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.utils.RxTimerUtils.2
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(Long l) {
                    RxTimerAction rxTimerAction2 = rxTimerAction;
                    if (rxTimerAction2 != null) {
                        rxTimerAction2.action(l.longValue());
                    }
                }
            });
        }
    }

    public void timerBindDestroy(LifecycleProvider<ActivityEvent> lifecycleProvider, long j, TimeUnit timeUnit, final RxTimerAction rxTimerAction) {
        Observable.timer(j, timeUnit).observeOn(AndroidSchedulers.mainThread()).compose(lifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY)).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.utils.RxTimerUtils.3
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Long l) {
                RxTimerAction rxTimerAction2 = rxTimerAction;
                if (rxTimerAction2 != null) {
                    rxTimerAction2.action(l.longValue());
                }
            }
        });
    }

    public void timerBindDestroyFragment(LifecycleProvider<FragmentEvent> lifecycleProvider, long j, TimeUnit timeUnit, final RxTimerAction rxTimerAction) {
        Observable.timer(j, timeUnit).observeOn(AndroidSchedulers.mainThread()).compose(lifecycleProvider.bindUntilEvent(FragmentEvent.DESTROY)).subscribe(new SimpleRxObserver<Long>() { // from class: com.tomatolive.library.utils.RxTimerUtils.4
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Long l) {
                RxTimerAction rxTimerAction2 = rxTimerAction;
                if (rxTimerAction2 != null) {
                    rxTimerAction2.action(l.longValue());
                }
            }
        });
    }
}
