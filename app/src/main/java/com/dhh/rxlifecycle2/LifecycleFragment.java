package com.dhh.rxlifecycle2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import io.reactivex.subjects.BehaviorSubject;

/* loaded from: classes2.dex */
public class LifecycleFragment extends Fragment implements LifecycleManager {
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override // android.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        this.lifecycleSubject.onNext(ActivityEvent.onCreate);
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public void onStart() {
        this.lifecycleSubject.onNext(ActivityEvent.onStart);
        super.onStart();
    }

    @Override // android.app.Fragment
    public void onResume() {
        this.lifecycleSubject.onNext(ActivityEvent.onResume);
        super.onResume();
    }

    @Override // android.app.Fragment
    public void onPause() {
        this.lifecycleSubject.onNext(ActivityEvent.onPause);
        super.onPause();
    }

    @Override // android.app.Fragment
    public void onStop() {
        this.lifecycleSubject.onNext(ActivityEvent.onStop);
        super.onStop();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        this.lifecycleSubject.onNext(ActivityEvent.onDestory);
        super.onDestroy();
    }
}
