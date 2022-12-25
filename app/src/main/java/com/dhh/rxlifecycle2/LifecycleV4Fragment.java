package com.dhh.rxlifecycle2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import io.reactivex.subjects.BehaviorSubject;

/* loaded from: classes2.dex */
public class LifecycleV4Fragment extends Fragment implements LifecycleManager {
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override // android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        this.lifecycleSubject.onNext(ActivityEvent.onCreate);
        super.onCreate(bundle);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onStart() {
        this.lifecycleSubject.onNext(ActivityEvent.onStart);
        super.onStart();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onResume() {
        this.lifecycleSubject.onNext(ActivityEvent.onResume);
        super.onResume();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onPause() {
        this.lifecycleSubject.onNext(ActivityEvent.onPause);
        super.onPause();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onStop() {
        this.lifecycleSubject.onNext(ActivityEvent.onStop);
        super.onStop();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDestroy() {
        this.lifecycleSubject.onNext(ActivityEvent.onDestory);
        super.onDestroy();
    }
}
