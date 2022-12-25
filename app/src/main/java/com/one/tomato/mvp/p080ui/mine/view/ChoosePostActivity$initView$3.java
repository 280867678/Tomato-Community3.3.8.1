package com.one.tomato.mvp.p080ui.mine.view;

import com.one.tomato.entity.PostList;
import java.util.ArrayList;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* compiled from: ChoosePostActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.ChoosePostActivity$initView$3 */
/* loaded from: classes3.dex */
final class ChoosePostActivity$initView$3 extends Lambda implements Functions<ArrayList<PostList>> {
    final /* synthetic */ ChoosePostActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChoosePostActivity$initView$3(ChoosePostActivity choosePostActivity) {
        super(0);
        this.this$0 = choosePostActivity;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final ArrayList<PostList> mo6822invoke() {
        ArrayList<PostList> arrayList;
        arrayList = this.this$0.selectListData;
        return arrayList;
    }
}
