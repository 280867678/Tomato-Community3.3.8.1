package kotlin.coroutines.intrinsics;

import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: IntrinsicsJvm.kt */
/* renamed from: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2 */
/* loaded from: classes4.dex */
public final class C5247xa50de661 extends ContinuationImpl {
    final /* synthetic */ Continuation $completion;
    final /* synthetic */ CoroutineContext $context;
    final /* synthetic */ Function1 $this_createCoroutineUnintercepted$inlined;
    private int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C5247xa50de661(Continuation continuation, CoroutineContext coroutineContext, Continuation continuation2, CoroutineContext coroutineContext2, Function1 function1) {
        super(continuation2, coroutineContext2);
        this.$completion = continuation;
        this.$context = coroutineContext;
        this.$this_createCoroutineUnintercepted$inlined = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    protected Object invokeSuspend(Object obj) {
        int i = this.label;
        if (i != 0) {
            if (i == 1) {
                this.label = 2;
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            throw new IllegalStateException("This coroutine had already completed".toString());
        }
        this.label = 1;
        ResultKt.throwOnFailure(obj);
        Function1 function1 = this.$this_createCoroutineUnintercepted$inlined;
        if (function1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
        }
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1);
        return function1.mo6794invoke(this);
    }
}
