package com.one.tomato.mvp.p080ui.post.item;

import android.view.View;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
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
/* compiled from: PostVideoLookPayItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostVideoLookPayItem$onClick$3 */
/* loaded from: classes3.dex */
public final class PostVideoLookPayItem$onClick$3 implements View.OnClickListener {
    final /* synthetic */ PostVideoLookPayItem this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public PostVideoLookPayItem$onClick$3(PostVideoLookPayItem postVideoLookPayItem) {
        this.this$0 = postVideoLookPayItem;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: PostVideoLookPayItem.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.item.PostVideoLookPayItem$onClick$3$1 */
    /* loaded from: classes3.dex */
    public static final class C26171 extends Lambda implements Function1<String, Unit> {
        C26171() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: PostVideoLookPayItem.kt */
        /* renamed from: com.one.tomato.mvp.ui.post.item.PostVideoLookPayItem$onClick$3$1$1 */
        /* loaded from: classes3.dex */
        public static final class C26181 extends Lambda implements Function1<String, Unit> {
            C26181() {
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
                LottieAnimationView lottieAnimationView = (LottieAnimationView) PostVideoLookPayItem$onClick$3.this.this$0._$_findCachedViewById(R$id.image);
                if (lottieAnimationView != null) {
                    lottieAnimationView.setVisibility(8);
                }
                PostVideoLookPayItem postVideoLookPayItem = PostVideoLookPayItem$onClick$3.this.this$0;
                LottieAnimationView image = (LottieAnimationView) postVideoLookPayItem._$_findCachedViewById(R$id.image);
                Intrinsics.checkExpressionValueIsNotNull(image, "image");
                postVideoLookPayItem.cancelAnimation(image);
                PostRewardPayUtils postRewardPayUtils = PostRewardPayUtils.INSTANCE;
                PostList postList = PostVideoLookPayItem$onClick$3.this.this$0.getPostList();
                if (postList == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                postRewardPayUtils.setPayPost(postList.getId());
                TextView textView = (TextView) PostVideoLookPayItem$onClick$3.this.this$0._$_findCachedViewById(R$id.tv_post_currency_play);
                if (textView != null) {
                    textView.setText(AppUtil.getString(R.string.post_pay_sucess));
                }
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_pay_sucess));
                Functions<Unit> paySucess = PostVideoLookPayItem$onClick$3.this.this$0.getPaySucess();
                if (paySucess == null) {
                    return;
                }
                paySucess.mo6822invoke();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: PostVideoLookPayItem.kt */
        /* renamed from: com.one.tomato.mvp.ui.post.item.PostVideoLookPayItem$onClick$3$1$2 */
        /* loaded from: classes3.dex */
        public static final class C26192 extends Lambda implements Function1<ResponseThrowable, Unit> {
            C26192() {
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
                LottieAnimationView lottieAnimationView = (LottieAnimationView) PostVideoLookPayItem$onClick$3.this.this$0._$_findCachedViewById(R$id.image);
                if (lottieAnimationView != null) {
                    lottieAnimationView.setVisibility(8);
                }
                PostVideoLookPayItem postVideoLookPayItem = PostVideoLookPayItem$onClick$3.this.this$0;
                LottieAnimationView image = (LottieAnimationView) postVideoLookPayItem._$_findCachedViewById(R$id.image);
                Intrinsics.checkExpressionValueIsNotNull(image, "image");
                postVideoLookPayItem.cancelAnimation(image);
                TextView textView = (TextView) PostVideoLookPayItem$onClick$3.this.this$0._$_findCachedViewById(R$id.tv_post_currency_play);
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
            String str;
            String str2;
            Intrinsics.checkParameterIsNotNull(it2, "it");
            double parseDouble = Double.parseDouble(it2);
            str = PostVideoLookPayItem$onClick$3.this.this$0.needPay;
            if (parseDouble < (str != null ? Long.parseLong(str) : 0L)) {
                RechargeActivity.startActivity(PostVideoLookPayItem$onClick$3.this.this$0.getContext());
                return;
            }
            LottieAnimationView lottieAnimationView = (LottieAnimationView) PostVideoLookPayItem$onClick$3.this.this$0._$_findCachedViewById(R$id.image);
            if (lottieAnimationView != null) {
                lottieAnimationView.setVisibility(0);
            }
            PostVideoLookPayItem postVideoLookPayItem = PostVideoLookPayItem$onClick$3.this.this$0;
            LottieAnimationView image = (LottieAnimationView) postVideoLookPayItem._$_findCachedViewById(R$id.image);
            Intrinsics.checkExpressionValueIsNotNull(image, "image");
            postVideoLookPayItem.showAnimation(image);
            TextView textView = (TextView) PostVideoLookPayItem$onClick$3.this.this$0._$_findCachedViewById(R$id.tv_post_currency_play);
            if (textView != null) {
                textView.setText(AppUtil.getString(R.string.post_dialog_paying));
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
            PostList postList = PostVideoLookPayItem$onClick$3.this.this$0.getPostList();
            linkedHashMap.put("articleId", postList != null ? Integer.valueOf(postList.getId()) : null);
            linkedHashMap.put("payType", 2);
            str2 = PostVideoLookPayItem$onClick$3.this.this$0.needPay;
            linkedHashMap.put("money", str2);
            VideoPlayCountUtils.getInstance().postRewardPay(PostVideoLookPayItem$onClick$3.this.this$0.getContext(), linkedHashMap, new C26181(), new C26192());
        }
    }

    /* compiled from: PostVideoLookPayItem.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.item.PostVideoLookPayItem$onClick$3$2 */
    /* loaded from: classes3.dex */
    static final class C26202 extends Lambda implements Functions<Unit> {
        public static final C26202 INSTANCE = new C26202();

        C26202() {
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
        PostUtils.INSTANCE.requestBalance(this.this$0.getContext(), new C26171(), C26202.INSTANCE);
    }
}
