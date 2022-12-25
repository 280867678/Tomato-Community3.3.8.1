package retrofit2.adapter.rxjava2;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;

/* loaded from: classes4.dex */
final class RxJava2CallAdapter<R> implements CallAdapter<R, Object> {
    private final boolean isAsync;
    private final boolean isBody;
    private final boolean isCompletable;
    private final boolean isFlowable;
    private final boolean isMaybe;
    private final boolean isResult;
    private final boolean isSingle;
    private final Type responseType;
    private final Scheduler scheduler;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RxJava2CallAdapter(Type type, Scheduler scheduler, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        this.responseType = type;
        this.scheduler = scheduler;
        this.isAsync = z;
        this.isResult = z2;
        this.isBody = z3;
        this.isFlowable = z4;
        this.isSingle = z5;
        this.isMaybe = z6;
        this.isCompletable = z7;
    }

    @Override // retrofit2.CallAdapter
    public Type responseType() {
        return this.responseType;
    }

    @Override // retrofit2.CallAdapter
    public Object adapt(Call<R> call) {
        Observable callExecuteObservable;
        Observable bodyObservable;
        if (this.isAsync) {
            callExecuteObservable = new CallEnqueueObservable(call);
        } else {
            callExecuteObservable = new CallExecuteObservable(call);
        }
        if (this.isResult) {
            bodyObservable = new ResultObservable(callExecuteObservable);
        } else {
            bodyObservable = this.isBody ? new BodyObservable(callExecuteObservable) : callExecuteObservable;
        }
        Scheduler scheduler = this.scheduler;
        if (scheduler != null) {
            bodyObservable = bodyObservable.subscribeOn(scheduler);
        }
        if (this.isFlowable) {
            return bodyObservable.toFlowable(BackpressureStrategy.LATEST);
        }
        if (this.isSingle) {
            return bodyObservable.singleOrError();
        }
        if (this.isMaybe) {
            return bodyObservable.singleElement();
        }
        return this.isCompletable ? bodyObservable.ignoreElements() : bodyObservable;
    }
}
