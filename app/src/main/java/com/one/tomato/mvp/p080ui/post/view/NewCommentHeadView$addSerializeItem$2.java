package com.one.tomato.mvp.p080ui.post.view;

import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewCommentHeadView.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$addSerializeItem$2 */
/* loaded from: classes3.dex */
public final class NewCommentHeadView$addSerializeItem$2 extends Lambda implements Functions<FragmentManager> {
    final /* synthetic */ NewCommentHeadView this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewCommentHeadView$addSerializeItem$2(NewCommentHeadView newCommentHeadView) {
        super(0);
        this.this$0 = newCommentHeadView;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke */
    public final FragmentManager mo6822invoke() {
        Fragment fragment;
        fragment = this.this$0.fragment;
        if (fragment != null) {
            return fragment.getChildFragmentManager();
        }
        return null;
    }
}
