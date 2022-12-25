package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ChoosePostActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.ChoosePostActivity$initView$2 */
/* loaded from: classes3.dex */
final class ChoosePostActivity$initView$2 extends Lambda implements Function1<Boolean, Unit> {
    final /* synthetic */ ChoosePostActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChoosePostActivity$initView$2(ChoosePostActivity choosePostActivity) {
        super(1);
        this.this$0 = choosePostActivity;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Boolean bool) {
        invoke(bool.booleanValue());
        return Unit.INSTANCE;
    }

    public final void invoke(boolean z) {
        Context mContext;
        Context mContext2;
        if (z) {
            TextView textView = (TextView) this.this$0._$_findCachedViewById(R$id.text_next);
            if (textView != null) {
                mContext2 = this.this$0.getMContext();
                if (mContext2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView.setTextColor(ContextCompat.getColor(mContext2, R.color.colorAccent));
            }
            TextView textView2 = (TextView) this.this$0._$_findCachedViewById(R$id.text_next);
            if (textView2 == null) {
                return;
            }
            textView2.setEnabled(true);
            return;
        }
        TextView textView3 = (TextView) this.this$0._$_findCachedViewById(R$id.text_next);
        if (textView3 != null) {
            mContext = this.this$0.getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView3.setTextColor(ContextCompat.getColor(mContext, R.color.text_light));
        }
        TextView textView4 = (TextView) this.this$0._$_findCachedViewById(R$id.text_next);
        if (textView4 == null) {
            return;
        }
        textView4.setEnabled(false);
    }
}
