package com.one.tomato.mvp.p080ui.papa.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ViewUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewPaPaVideoPlayFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$setPostNeedPay$1 */
/* loaded from: classes3.dex */
public final class NewPaPaVideoPlayFragment$setPostNeedPay$1 extends Lambda implements Function1<String, Unit> {
    final /* synthetic */ NewPaPaVideoPlayFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPaPaVideoPlayFragment$setPostNeedPay$1(NewPaPaVideoPlayFragment newPaPaVideoPlayFragment) {
        super(1);
        this.this$0 = newPaPaVideoPlayFragment;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(String str) {
        invoke2(str);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(String it2) {
        PostList postList;
        Context mContext;
        Context mContext2;
        PostList postList2;
        PostList postList3;
        Intrinsics.checkParameterIsNotNull(it2, "it");
        View _$_findCachedViewById = this.this$0._$_findCachedViewById(R$id.rl_wifi_and_virtual);
        if (_$_findCachedViewById != null) {
            _$_findCachedViewById.setVisibility(0);
        }
        RelativeLayout rl_wifi_tip = (RelativeLayout) this.this$0._$_findCachedViewById(R$id.rl_wifi_tip);
        Intrinsics.checkExpressionValueIsNotNull(rl_wifi_tip, "rl_wifi_tip");
        rl_wifi_tip.setVisibility(8);
        RelativeLayout rl_virtual_currency_tip = (RelativeLayout) this.this$0._$_findCachedViewById(R$id.rl_virtual_currency_tip);
        Intrinsics.checkExpressionValueIsNotNull(rl_virtual_currency_tip, "rl_virtual_currency_tip");
        rl_virtual_currency_tip.setVisibility(0);
        TextView textView = (TextView) this.this$0._$_findCachedViewById(R$id.tv_virtual_play);
        if (textView != null) {
            textView.setVisibility(8);
        }
        RelativeLayout relativeLayout = (RelativeLayout) this.this$0._$_findCachedViewById(R$id.relate_post_need_pay);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
        }
        TextView textView2 = (TextView) this.this$0._$_findCachedViewById(R$id.tv_virtual_currency);
        if (textView2 != null) {
            textView2.setVisibility(0);
        }
        LinearLayout linearLayout = (LinearLayout) this.this$0._$_findCachedViewById(R$id.liner_pay);
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        postList = this.this$0.postList;
        if (postList != null && postList.getSubscribeSwitch() == 1) {
            TextView textView3 = (TextView) this.this$0._$_findCachedViewById(R$id.tv_post_currency_subscribe);
            if (textView3 != null) {
                textView3.setVisibility(0);
            }
            TextView textView4 = (TextView) this.this$0._$_findCachedViewById(R$id.tv_post_currency_subscribe);
            if (textView4 != null) {
                textView4.setText(AppUtil.getString(R.string.post_need_subscribe_title));
            }
        } else {
            TextView textView5 = (TextView) this.this$0._$_findCachedViewById(R$id.tv_post_currency_subscribe);
            if (textView5 != null) {
                textView5.setVisibility(8);
            }
        }
        TextView textView6 = (TextView) this.this$0._$_findCachedViewById(R$id.tv_virtual_currency_tip);
        if (textView6 != null) {
            Object[] objArr = new Object[1];
            postList3 = this.this$0.postList;
            objArr[0] = FormatUtil.formatTomato2RMB(postList3 != null ? postList3.getPrice() : 0.0d);
            textView6.setText(AppUtil.getString(R.string.post_need_pay_num, objArr));
        }
        TextView textView7 = (TextView) this.this$0._$_findCachedViewById(R$id.tv_virtual_currency);
        String[] strArr = {String.valueOf(AppUtil.getString(R.string.income_current_coin_lable)), String.valueOf(FormatUtil.formatTomato2RMB(it2))};
        String[] strArr2 = new String[2];
        mContext = this.this$0.getMContext();
        if (mContext != null) {
            strArr2[0] = String.valueOf(mContext.getResources().getColor(R.color.text_969696));
            mContext2 = this.this$0.getMContext();
            if (mContext2 != null) {
                strArr2[1] = String.valueOf(mContext2.getResources().getColor(R.color.white));
                ViewUtil.initTextViewWithSpannableString(textView7, strArr, strArr2, new String[]{"14", "14"});
                double parseDouble = Double.parseDouble(it2);
                postList2 = this.this$0.postList;
                Integer valueOf = postList2 != null ? Integer.valueOf(postList2.getPrice()) : null;
                if (valueOf == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (Double.compare(parseDouble, valueOf.intValue()) == 1) {
                    TextView textView8 = (TextView) this.this$0._$_findCachedViewById(R$id.tv_post_currency_play);
                    if (textView8 == null) {
                        return;
                    }
                    textView8.setText(AppUtil.getString(R.string.post_tot_pay_post));
                    return;
                } else {
                    TextView textView9 = (TextView) this.this$0._$_findCachedViewById(R$id.tv_post_currency_play);
                    if (textView9 == null) {
                        return;
                    }
                    textView9.setText(AppUtil.getString(R.string.post_video_consume_tip7));
                    return;
                }
            }
            Intrinsics.throwNpe();
            throw null;
        }
        Intrinsics.throwNpe();
        throw null;
    }
}
