package com.one.tomato.mvp.p080ui.papa.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.PostPayEvent;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.p080ui.p082up.PostRewardPayUtils;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.post.VideoPlayCountUtils;
import java.util.LinkedHashMap;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewPaPaVideoPlayFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$7 */
/* loaded from: classes3.dex */
public final class NewPaPaVideoPlayFragment$initView$7 implements View.OnClickListener {
    final /* synthetic */ NewPaPaVideoPlayFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public NewPaPaVideoPlayFragment$initView$7(NewPaPaVideoPlayFragment newPaPaVideoPlayFragment) {
        this.this$0 = newPaPaVideoPlayFragment;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: NewPaPaVideoPlayFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$7$1 */
    /* loaded from: classes3.dex */
    public static final class C25791 extends Lambda implements Function1<String, Unit> {
        final /* synthetic */ Integer $needPay;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C25791(Integer num) {
            super(1);
            this.$needPay = num;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: NewPaPaVideoPlayFragment.kt */
        /* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$7$1$1 */
        /* loaded from: classes3.dex */
        public static final class C25801 extends Lambda implements Function1<String, Unit> {
            C25801() {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public /* bridge */ /* synthetic */ Unit mo6794invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final void invoke2(String str) {
                PostList postList;
                PostList postList2;
                LottieAnimationView lottieAnimationView = (LottieAnimationView) NewPaPaVideoPlayFragment$initView$7.this.this$0._$_findCachedViewById(R$id.image);
                if (lottieAnimationView != null) {
                    lottieAnimationView.setVisibility(8);
                }
                NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = NewPaPaVideoPlayFragment$initView$7.this.this$0;
                LottieAnimationView image = (LottieAnimationView) newPaPaVideoPlayFragment._$_findCachedViewById(R$id.image);
                Intrinsics.checkExpressionValueIsNotNull(image, "image");
                newPaPaVideoPlayFragment.cancelAnimation(image);
                TextView textView = (TextView) NewPaPaVideoPlayFragment$initView$7.this.this$0._$_findCachedViewById(R$id.tv_post_currency_play);
                if (textView != null) {
                    textView.setText(AppUtil.getString(R.string.post_pay_sucess));
                }
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_pay_sucess));
                PostRewardPayUtils postRewardPayUtils = PostRewardPayUtils.INSTANCE;
                postList = NewPaPaVideoPlayFragment$initView$7.this.this$0.postList;
                if (postList != null) {
                    postRewardPayUtils.setPayPost(postList.getId());
                    NewPaPaVideoPlayFragment$initView$7.this.this$0.payCompelete();
                    postList2 = NewPaPaVideoPlayFragment$initView$7.this.this$0.postList;
                    RxBus.getDefault().post(new PostPayEvent(true, postList2 != null ? postList2.getMemberId() : 0));
                    return;
                }
                Intrinsics.throwNpe();
                throw null;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: NewPaPaVideoPlayFragment.kt */
        /* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$7$1$2 */
        /* loaded from: classes3.dex */
        public static final class C25812 extends Lambda implements Function1<ResponseThrowable, Unit> {
            C25812() {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public /* bridge */ /* synthetic */ Unit mo6794invoke(ResponseThrowable responseThrowable) {
                invoke2(responseThrowable);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: avoid collision after fix types in other method */
            public final void invoke2(ResponseThrowable responseThrowable) {
                NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = NewPaPaVideoPlayFragment$initView$7.this.this$0;
                LottieAnimationView image = (LottieAnimationView) newPaPaVideoPlayFragment._$_findCachedViewById(R$id.image);
                Intrinsics.checkExpressionValueIsNotNull(image, "image");
                newPaPaVideoPlayFragment.cancelAnimation(image);
                LottieAnimationView lottieAnimationView = (LottieAnimationView) NewPaPaVideoPlayFragment$initView$7.this.this$0._$_findCachedViewById(R$id.image);
                if (lottieAnimationView != null) {
                    lottieAnimationView.setVisibility(8);
                }
                TextView textView = (TextView) NewPaPaVideoPlayFragment$initView$7.this.this$0._$_findCachedViewById(R$id.tv_post_currency_play);
                if (textView != null) {
                    textView.setText(AppUtil.getString(R.string.post_pay_error));
                }
            }
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
            Intrinsics.checkParameterIsNotNull(it2, "it");
            double parseDouble = Double.parseDouble(it2);
            Integer num = this.$needPay;
            if (parseDouble < (num != null ? num.intValue() : 0L)) {
                RechargeActivity.startActivity(NewPaPaVideoPlayFragment$initView$7.this.this$0.getContext());
                return;
            }
            LottieAnimationView lottieAnimationView = (LottieAnimationView) NewPaPaVideoPlayFragment$initView$7.this.this$0._$_findCachedViewById(R$id.image);
            if (lottieAnimationView != null) {
                lottieAnimationView.setVisibility(0);
            }
            NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = NewPaPaVideoPlayFragment$initView$7.this.this$0;
            LottieAnimationView image = (LottieAnimationView) newPaPaVideoPlayFragment._$_findCachedViewById(R$id.image);
            Intrinsics.checkExpressionValueIsNotNull(image, "image");
            newPaPaVideoPlayFragment.showAnimation(image);
            TextView textView = (TextView) NewPaPaVideoPlayFragment$initView$7.this.this$0._$_findCachedViewById(R$id.tv_post_currency_play);
            if (textView != null) {
                textView.setText(AppUtil.getString(R.string.post_dialog_paying));
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
            postList = NewPaPaVideoPlayFragment$initView$7.this.this$0.postList;
            linkedHashMap.put("articleId", postList != null ? Integer.valueOf(postList.getId()) : null);
            linkedHashMap.put("payType", 2);
            linkedHashMap.put("money", this.$needPay);
            VideoPlayCountUtils.getInstance().postRewardPay(NewPaPaVideoPlayFragment$initView$7.this.this$0.getContext(), linkedHashMap, new C25801(), new C25812());
        }
    }

    /* compiled from: NewPaPaVideoPlayFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$initView$7$2 */
    /* loaded from: classes3.dex */
    static final class C25822 extends Lambda implements Functions<Unit> {
        public static final C25822 INSTANCE = new C25822();

        C25822() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Functions
        /* renamed from: invoke  reason: avoid collision after fix types in other method */
        public final void mo6822invoke() {
        }

        @Override // kotlin.jvm.functions.Functions
        /* renamed from: invoke */
        public /* bridge */ /* synthetic */ Unit mo6822invoke() {
            mo6822invoke();
            return Unit.INSTANCE;
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        PostList postList;
        PostList postList2;
        Context mContext;
        postList = this.this$0.postList;
        if (postList == null || !postList.isAlreadyPaid()) {
            postList2 = this.this$0.postList;
            Integer valueOf = postList2 != null ? Integer.valueOf(postList2.getPrice()) : null;
            PostUtils postUtils = PostUtils.INSTANCE;
            mContext = this.this$0.getMContext();
            postUtils.requestBalance(mContext, new C25791(valueOf), C25822.INSTANCE);
            return;
        }
        ToastUtil.showCenterToast(AppUtil.getString(R.string.post_isareadly_pay));
    }
}
