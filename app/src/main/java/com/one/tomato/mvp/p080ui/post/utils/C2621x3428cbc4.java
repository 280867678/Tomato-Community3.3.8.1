package com.one.tomato.mvp.p080ui.post.utils;

import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.p080ui.p082up.PostRewardPayUtils;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils$showImageNeedPayDialog$1;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.ToastUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: PostUtils.kt */
/* renamed from: com.one.tomato.mvp.ui.post.utils.PostUtils$showImageNeedPayDialog$1$3$$special$$inlined$let$lambda$1 */
/* loaded from: classes3.dex */
final class C2621x3428cbc4 extends Lambda implements Function1<String, Unit> {
    final /* synthetic */ TextView $tvPostCurrencyPlay;
    final /* synthetic */ PostUtils$showImageNeedPayDialog$1.View$OnClickListenerC26253 this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2621x3428cbc4(TextView textView, PostUtils$showImageNeedPayDialog$1.View$OnClickListenerC26253 view$OnClickListenerC26253) {
        super(1);
        this.$tvPostCurrencyPlay = textView;
        this.this$0 = view$OnClickListenerC26253;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(String str) {
        invoke2(str);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(String str) {
        PostUtils$showImageNeedPayDialog$1.this.$confimCallBack.mo6822invoke();
        TextView textView = this.$tvPostCurrencyPlay;
        if (textView != null) {
            textView.setText(AppUtil.getString(R.string.post_pay_sucess));
        }
        ToastUtil.showCenterToast(AppUtil.getString(R.string.post_pay_sucess));
        this.this$0.$dialog.dismiss();
        PostRewardPayUtils.INSTANCE.setPayPost(Integer.parseInt(PostUtils$showImageNeedPayDialog$1.this.$articleId));
    }
}
