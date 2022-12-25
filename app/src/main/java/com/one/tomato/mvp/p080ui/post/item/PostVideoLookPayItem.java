package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.p082up.view.UpSubscribeActivity;
import com.one.tomato.mvp.p080ui.vip.p083ui.VipActivity;
import com.one.tomato.p085ui.mine.MyShareActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ViewUtil;
import java.util.HashMap;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostVideoLookPayItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostVideoLookPayItem */
/* loaded from: classes3.dex */
public final class PostVideoLookPayItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private String needPay;
    private Functions<Unit> payComplete;
    private Functions<Unit> paySucess;
    private int personMemberId;
    private PostList postList;

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    public PostVideoLookPayItem(Context context, PostList postList) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.include_wifi_and_virtual, this);
        onClick();
        this.postList = postList;
    }

    public final PostList getPostList() {
        return this.postList;
    }

    public final void setPostList(PostList postList) {
        this.postList = postList;
    }

    public final Functions<Unit> getPayComplete() {
        return this.payComplete;
    }

    public final void setPayComplete(Functions<Unit> functions) {
        this.payComplete = functions;
    }

    public final Functions<Unit> getPaySucess() {
        return this.paySucess;
    }

    public final void setPaySucess(Functions<Unit> functions) {
        this.paySucess = functions;
    }

    public final void onClick() {
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_virtual_play);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostVideoLookPayItem$onClick$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MyShareActivity.startActivity(PostVideoLookPayItem.this.getContext());
                }
            });
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_currency_play);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostVideoLookPayItem$onClick$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VipActivity.Companion companion = VipActivity.Companion;
                    Context context = PostVideoLookPayItem.this.getContext();
                    Intrinsics.checkExpressionValueIsNotNull(context, "context");
                    companion.startActivity(context);
                }
            });
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_post_currency_play);
        if (textView3 != null) {
            textView3.setOnClickListener(new PostVideoLookPayItem$onClick$3(this));
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.tv_post_currency_subscribe);
        if (textView4 != null) {
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostVideoLookPayItem$onClick$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i;
                    UpSubscribeActivity.Companion companion = UpSubscribeActivity.Companion;
                    Context context = PostVideoLookPayItem.this.getContext();
                    i = PostVideoLookPayItem.this.personMemberId;
                    companion.startAct(context, i);
                }
            });
        }
    }

    public final void cancelAnimation(LottieAnimationView iv_loading) {
        Intrinsics.checkParameterIsNotNull(iv_loading, "iv_loading");
        if (iv_loading.getAnimation() == null || !iv_loading.isAnimating()) {
            return;
        }
        iv_loading.cancelAnimation();
    }

    public final void showAnimation(LottieAnimationView iv_loading) {
        Intrinsics.checkParameterIsNotNull(iv_loading, "iv_loading");
        if (iv_loading.getAnimation() != null && iv_loading.isAnimating()) {
            iv_loading.cancelAnimation();
        }
        iv_loading.setAnimation("loading_more.json");
    }

    public final void setCallBackComplete(Functions<Unit> functions) {
        this.payComplete = functions;
    }

    public final void setPayCallBackComplete(Functions<Unit> functions) {
        this.paySucess = functions;
    }

    public final void setPostListData(PostList postList) {
        this.postList = postList;
    }

    public final void setLookTimes() {
        RelativeLayout rl_wifi_tip = (RelativeLayout) _$_findCachedViewById(R$id.rl_wifi_tip);
        Intrinsics.checkExpressionValueIsNotNull(rl_wifi_tip, "rl_wifi_tip");
        rl_wifi_tip.setVisibility(8);
        RelativeLayout rl_virtual_currency_tip = (RelativeLayout) _$_findCachedViewById(R$id.rl_virtual_currency_tip);
        Intrinsics.checkExpressionValueIsNotNull(rl_virtual_currency_tip, "rl_virtual_currency_tip");
        rl_virtual_currency_tip.setVisibility(0);
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_virtual_play);
        if (textView != null) {
            textView.setVisibility(0);
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_post_need_pay);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.liner_pay);
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_virtual_currency);
        if (textView2 != null) {
            textView2.setText(AppUtil.getString(R.string.post_open_vip_tip));
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_virtual_play);
        if (textView3 != null) {
            textView3.setText(AppUtil.getString(R.string.post_goto_promote));
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.tv_currency_play);
        if (textView4 != null) {
            textView4.setText(AppUtil.getString(R.string.post_goto_open_vip));
        }
    }

    public final void setNeedPostPay(String balance, String str, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(balance, "balance");
        this.needPay = str;
        this.personMemberId = i2;
        RelativeLayout rl_wifi_tip = (RelativeLayout) _$_findCachedViewById(R$id.rl_wifi_tip);
        Intrinsics.checkExpressionValueIsNotNull(rl_wifi_tip, "rl_wifi_tip");
        rl_wifi_tip.setVisibility(8);
        RelativeLayout rl_virtual_currency_tip = (RelativeLayout) _$_findCachedViewById(R$id.rl_virtual_currency_tip);
        Intrinsics.checkExpressionValueIsNotNull(rl_virtual_currency_tip, "rl_virtual_currency_tip");
        rl_virtual_currency_tip.setVisibility(0);
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_virtual_play);
        if (textView != null) {
            textView.setVisibility(8);
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_post_need_pay);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_virtual_currency);
        if (textView2 != null) {
            textView2.setVisibility(0);
        }
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.liner_pay);
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        if (i == 1) {
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_post_currency_subscribe);
            if (textView3 != null) {
                textView3.setVisibility(0);
            }
            TextView textView4 = (TextView) _$_findCachedViewById(R$id.tv_post_currency_subscribe);
            if (textView4 != null) {
                textView4.setText(AppUtil.getString(R.string.post_need_subscribe_title));
            }
        } else {
            TextView textView5 = (TextView) _$_findCachedViewById(R$id.tv_post_currency_subscribe);
            if (textView5 != null) {
                textView5.setVisibility(8);
            }
        }
        TextView textView6 = (TextView) _$_findCachedViewById(R$id.tv_virtual_currency_tip);
        if (textView6 != null) {
            textView6.setText(AppUtil.getString(R.string.post_need_pay_num, FormatUtil.formatTomato2RMB(str)));
        }
        String[] strArr = {String.valueOf(AppUtil.getString(R.string.income_current_coin_lable)), String.valueOf(FormatUtil.formatTomato2RMB(balance))};
        Context context = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "context");
        Context context2 = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context2, "context");
        ViewUtil.initTextViewWithSpannableString((TextView) _$_findCachedViewById(R$id.tv_virtual_currency), strArr, new String[]{String.valueOf(context.getResources().getColor(R.color.text_969696)), String.valueOf(context2.getResources().getColor(R.color.white))}, new String[]{"14", "14"});
        try {
            if (Double.parseDouble(balance) < (str != null ? Long.parseLong(str) : 0L)) {
                TextView textView7 = (TextView) _$_findCachedViewById(R$id.tv_post_currency_play);
                if (textView7 != null) {
                    textView7.setText(AppUtil.getString(R.string.post_video_consume_tip7));
                }
            } else {
                TextView textView8 = (TextView) _$_findCachedViewById(R$id.tv_post_currency_play);
                if (textView8 != null) {
                    textView8.setText(AppUtil.getString(R.string.post_tot_pay_post));
                }
            }
        } catch (Exception unused) {
            TextView textView9 = (TextView) _$_findCachedViewById(R$id.tv_post_currency_play);
            if (textView9 != null) {
                textView9.setText(AppUtil.getString(R.string.post_tot_pay_post));
            }
        }
        LottieAnimationView lottieAnimationView = (LottieAnimationView) _$_findCachedViewById(R$id.image);
        if (lottieAnimationView != null) {
            lottieAnimationView.setVisibility(8);
        }
    }
}
