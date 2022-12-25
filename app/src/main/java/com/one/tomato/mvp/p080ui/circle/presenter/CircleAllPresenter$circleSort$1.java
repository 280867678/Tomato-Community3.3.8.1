package com.one.tomato.mvp.p080ui.circle.presenter;

import com.one.tomato.entity.CircleCategory;
import com.one.tomato.entity.p079db.CircleAllBean;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact$ICircleAllView;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.anko.Async;
import org.jetbrains.anko.AsyncKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CircleAllPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.presenter.CircleAllPresenter$circleSort$1 */
/* loaded from: classes3.dex */
public final class CircleAllPresenter$circleSort$1 extends Lambda implements Function1<Async<CircleAllPresenter>, Unit> {
    final /* synthetic */ ArrayList $circleBean;
    final /* synthetic */ CircleAllPresenter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CircleAllPresenter$circleSort$1(CircleAllPresenter circleAllPresenter, ArrayList arrayList) {
        super(1);
        this.this$0 = circleAllPresenter;
        this.$circleBean = arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: CircleAllPresenter.kt */
    /* renamed from: com.one.tomato.mvp.ui.circle.presenter.CircleAllPresenter$circleSort$1$3 */
    /* loaded from: classes3.dex */
    public static final class C25533 extends Lambda implements Function1<CircleAllPresenter, Unit> {
        final /* synthetic */ ArrayList $newList1;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C25533(ArrayList arrayList) {
            super(1);
            this.$newList1 = arrayList;
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public /* bridge */ /* synthetic */ Unit mo6794invoke(CircleAllPresenter circleAllPresenter) {
            invoke2(circleAllPresenter);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke  reason: avoid collision after fix types in other method */
        public final void invoke2(CircleAllPresenter it2) {
            ICircleAllContact$ICircleAllView mView;
            Intrinsics.checkParameterIsNotNull(it2, "it");
            mView = CircleAllPresenter$circleSort$1.this.this$0.getMView();
            if (mView != null) {
                mView.handlerCircleAll(this.$newList1);
            }
        }
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Async<CircleAllPresenter> async) {
        invoke2(async);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(Async<CircleAllPresenter> receiver) {
        ArrayList<CircleCategory> arrayList;
        ArrayList<CircleCategory> arrayList2;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        ArrayList arrayList3 = new ArrayList();
        if (this.$circleBean instanceof ArrayList) {
            arrayList = this.this$0.categoryList;
            if (arrayList != null) {
                for (CircleCategory circleCategory : arrayList) {
                    ArrayList arrayList4 = new ArrayList();
                    for (CircleAllBean circleAllBean : this.$circleBean) {
                        if (circleCategory.getId() == -10 && circleAllBean.getFollowFlag() == 1 && !arrayList3.contains(circleAllBean)) {
                            arrayList4.add(circleAllBean);
                        }
                    }
                    arrayList3.addAll(arrayList4);
                }
            }
            arrayList2 = this.this$0.categoryList;
            if (arrayList2 != null) {
                for (CircleCategory circleCategory2 : arrayList2) {
                    ArrayList arrayList5 = new ArrayList();
                    for (CircleAllBean circleAllBean2 : this.$circleBean) {
                        if (circleCategory2.getId() == circleAllBean2.getCategoryId() && !arrayList3.contains(circleAllBean2)) {
                            arrayList5.add(circleAllBean2);
                        }
                    }
                    arrayList3.addAll(arrayList5);
                }
            }
        }
        AsyncKt.uiThread(receiver, new C25533(arrayList3));
    }
}
