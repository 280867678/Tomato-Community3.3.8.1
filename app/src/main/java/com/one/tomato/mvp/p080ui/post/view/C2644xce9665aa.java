package com.one.tomato.mvp.p080ui.post.view;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostTabListAdapter;
import com.one.tomato.thirdpart.recyclerview.BetterHorScrollRecyclerView;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$BooleanRef;
import org.jetbrains.anko.Async;
import org.jetbrains.anko.AsyncKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewPostTabListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$setScrollPostion2$1$$special$$inlined$let$lambda$1 */
/* loaded from: classes3.dex */
public final class C2644xce9665aa extends Lambda implements Function1<Async<NewPostTabListFragment>, Unit> {
    final /* synthetic */ List $it;
    final /* synthetic */ PostList $postList1$inlined;
    final /* synthetic */ NewPostTabListFragment$setScrollPostion2$1 this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2644xce9665aa(List list, NewPostTabListFragment$setScrollPostion2$1 newPostTabListFragment$setScrollPostion2$1, PostList postList) {
        super(1);
        this.$it = list;
        this.this$0 = newPostTabListFragment$setScrollPostion2$1;
        this.$postList1$inlined = postList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: NewPostTabListFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$setScrollPostion2$1$$special$$inlined$let$lambda$1$1 */
    /* loaded from: classes3.dex */
    public static final class C26451 extends Lambda implements Function1<NewPostTabListFragment, Unit> {
        final /* synthetic */ Ref$BooleanRef $isHave;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C26451(Ref$BooleanRef ref$BooleanRef) {
            super(1);
            this.$isHave = ref$BooleanRef;
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public /* bridge */ /* synthetic */ Unit mo6794invoke(NewPostTabListFragment newPostTabListFragment) {
            invoke2(newPostTabListFragment);
            return Unit.INSTANCE;
        }

        /* JADX WARN: Code restructure failed: missing block: B:3:0x000c, code lost:
            r4 = r3.this$0.this$0.this$0.getAdapter();
         */
        /* renamed from: invoke  reason: avoid collision after fix types in other method */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void invoke2(NewPostTabListFragment it2) {
            NewPostTabListAdapter adapter;
            int i;
            NewPostTabListAdapter adapter2;
            List<PostList> data;
            NewPostTabListAdapter adapter3;
            List<PostList> data2;
            NewPostTabListAdapter adapter4;
            Intrinsics.checkParameterIsNotNull(it2, "it");
            if (!this.$isHave.element && adapter4 != null) {
                adapter4.addData((NewPostTabListAdapter) C2644xce9665aa.this.$postList1$inlined);
            }
            adapter = C2644xce9665aa.this.this$0.this$0.getAdapter();
            Integer valueOf = (adapter == null || (data2 = adapter.getData()) == null) ? null : Integer.valueOf(data2.indexOf(C2644xce9665aa.this.$postList1$inlined));
            if (valueOf instanceof Integer) {
                if (Intrinsics.areEqual(C2644xce9665aa.this.this$0.this$0.getBusinessType(), "recommend") || Intrinsics.areEqual(C2644xce9665aa.this.this$0.this$0.getBusinessType(), "new_post")) {
                    int intValue = valueOf.intValue();
                    adapter3 = C2644xce9665aa.this.this$0.this$0.getAdapter();
                    if (adapter3 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    valueOf = Integer.valueOf(intValue + adapter3.getHeaderLayoutCount());
                }
                C2644xce9665aa.this.this$0.this$0.postion = valueOf.intValue();
                BetterHorScrollRecyclerView recyclerViewBetterHorScroll = C2644xce9665aa.this.this$0.this$0.getRecyclerViewBetterHorScroll();
                if (recyclerViewBetterHorScroll != null) {
                    recyclerViewBetterHorScroll.scrollToPosition(valueOf.intValue());
                }
            }
            i = C2644xce9665aa.this.this$0.this$0.postion;
            adapter2 = C2644xce9665aa.this.this$0.this$0.getAdapter();
            if (i > ((adapter2 == null || (data = adapter2.getData()) == null) ? 0 : data.size()) - 3) {
                C2644xce9665aa.this.this$0.this$0.loadMore();
            }
        }
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Async<NewPostTabListFragment> async) {
        invoke2(async);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(Async<NewPostTabListFragment> receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        ref$BooleanRef.element = false;
        for (PostList pt : this.$it) {
            Intrinsics.checkExpressionValueIsNotNull(pt, "pt");
            if (pt.getId() == this.$postList1$inlined.getId()) {
                ref$BooleanRef.element = true;
            }
        }
        AsyncKt.uiThread(receiver, new C26451(ref$BooleanRef));
    }
}
