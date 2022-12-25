package com.one.tomato.mvp.p080ui.search.view;

import android.widget.TextView;
import com.one.tomato.R$id;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostSearchActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.search.view.PostSearchActivity$requestFeedbackSearch$1 */
/* loaded from: classes3.dex */
public final class PostSearchActivity$requestFeedbackSearch$1 extends ApiDisposableObserver<Object> {
    final /* synthetic */ PostSearchActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public PostSearchActivity$requestFeedbackSearch$1(PostSearchActivity postSearchActivity) {
        this.this$0 = postSearchActivity;
    }

    @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
    public void onResult(Object obj) {
        this.this$0.hideWaitingDialog();
        TextView tv_feedback_success = (TextView) this.this$0._$_findCachedViewById(R$id.tv_feedback_success);
        Intrinsics.checkExpressionValueIsNotNull(tv_feedback_success, "tv_feedback_success");
        tv_feedback_success.setVisibility(0);
        ((TextView) this.this$0._$_findCachedViewById(R$id.tv_feedback)).postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.search.view.PostSearchActivity$requestFeedbackSearch$1$onResult$1
            @Override // java.lang.Runnable
            public final void run() {
                TextView tv_feedback_success2 = (TextView) PostSearchActivity$requestFeedbackSearch$1.this.this$0._$_findCachedViewById(R$id.tv_feedback_success);
                Intrinsics.checkExpressionValueIsNotNull(tv_feedback_success2, "tv_feedback_success");
                tv_feedback_success2.setVisibility(8);
            }
        }, 1500L);
    }

    @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
    public void onResultError(ResponseThrowable responseThrowable) {
        this.this$0.hideWaitingDialog();
    }
}
