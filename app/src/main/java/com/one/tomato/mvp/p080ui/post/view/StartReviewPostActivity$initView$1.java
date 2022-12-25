package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.one.tomato.dialog.ReviewPostChooseDialog;
import com.one.tomato.entity.PostList;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: StartReviewPostActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.StartReviewPostActivity$initView$1 */
/* loaded from: classes3.dex */
public final class StartReviewPostActivity$initView$1 implements View.OnClickListener {
    final /* synthetic */ StartReviewPostActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public StartReviewPostActivity$initView$1(StartReviewPostActivity startReviewPostActivity) {
        this.this$0 = startReviewPostActivity;
    }

    /* compiled from: StartReviewPostActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.StartReviewPostActivity$initView$1$1 */
    /* loaded from: classes3.dex */
    static final class C26701 extends Lambda implements Functions<Unit> {
        C26701() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Functions
        /* renamed from: invoke */
        public /* bridge */ /* synthetic */ Unit mo6822invoke() {
            mo6822invoke();
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Functions
        /* renamed from: invoke  reason: collision with other method in class */
        public final void mo6822invoke() {
            RecyclerView recyclerView;
            recyclerView = StartReviewPostActivity$initView$1.this.this$0.getRecyclerView();
            if (recyclerView != null) {
                recyclerView.post(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.StartReviewPostActivity.initView.1.1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        StartReviewPostActivity$initView$1.this.this$0.nextPost();
                    }
                });
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        ArrayList arrayList;
        int i;
        ArrayList arrayList2;
        int i2;
        arrayList = this.this$0.originData;
        int size = arrayList.size();
        i = this.this$0.currentIndex;
        if (size > i) {
            Context mContext = this.this$0.getMContext();
            if (mContext != null) {
                arrayList2 = this.this$0.originData;
                i2 = this.this$0.currentIndex;
                new ReviewPostChooseDialog(mContext, (PostList) arrayList2.get(i2)).addCallBackReviewCom(new C26701());
                return;
            }
            Intrinsics.throwNpe();
            throw null;
        }
    }
}
