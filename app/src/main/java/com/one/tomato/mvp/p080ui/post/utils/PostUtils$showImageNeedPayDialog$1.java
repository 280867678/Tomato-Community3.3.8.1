package com.one.tomato.mvp.p080ui.post.utils;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.mvp.p080ui.p082up.PostRewardPayUtils;
import com.one.tomato.mvp.p080ui.p082up.view.UpSubscribeActivity;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.RexUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.post.VideoPlayCountUtils;
import java.util.Arrays;
import java.util.LinkedHashMap;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostUtils.kt */
/* renamed from: com.one.tomato.mvp.ui.post.utils.PostUtils$showImageNeedPayDialog$1 */
/* loaded from: classes3.dex */
public final class PostUtils$showImageNeedPayDialog$1 extends Lambda implements Function1<String, Unit> {
    final /* synthetic */ String $articleId;
    final /* synthetic */ Functions $cancleCallBack;
    final /* synthetic */ Functions $confimCallBack;
    final /* synthetic */ Context $mContext;
    final /* synthetic */ String $needPay;
    final /* synthetic */ int $personId;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostUtils$showImageNeedPayDialog$1(Context context, int i, Functions functions, String str, String str2, Functions functions2) {
        super(1);
        this.$mContext = context;
        this.$personId = i;
        this.$cancleCallBack = functions;
        this.$articleId = str;
        this.$needPay = str2;
        this.$confimCallBack = functions2;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(String str) {
        invoke2(str);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(String tomatoBalance) {
        Intrinsics.checkParameterIsNotNull(tomatoBalance, "tomatoBalance");
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(this.$mContext, false);
        customAlertDialog.setCancleButtonBackgroundDrable(ContextCompat.getDrawable(this.$mContext, R.drawable.common_shape_solid_corner5_disable));
        customAlertDialog.setConfirmButtonBackgroundDrable(ContextCompat.getDrawable(this.$mContext, R.drawable.common_shape_solid_corner5_coloraccent80));
        customAlertDialog.setConfirmButtonTextColor(R.color.white);
        customAlertDialog.setCancelButtonTextColor(R.color.white);
        if (this.$personId == 0) {
            customAlertDialog.setCancelButton(AppUtil.getString(R.string.post_need_pay_cancle), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.utils.PostUtils$showImageNeedPayDialog$1.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    customAlertDialog.cancel();
                    PostUtils$showImageNeedPayDialog$1.this.$cancleCallBack.mo6822invoke();
                }
            });
        } else {
            customAlertDialog.setCancelButton(AppUtil.getString(R.string.post_need_subscribe_title), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.utils.PostUtils$showImageNeedPayDialog$1.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpSubscribeActivity.Companion companion = UpSubscribeActivity.Companion;
                    PostUtils$showImageNeedPayDialog$1 postUtils$showImageNeedPayDialog$1 = PostUtils$showImageNeedPayDialog$1.this;
                    companion.startAct(postUtils$showImageNeedPayDialog$1.$mContext, postUtils$showImageNeedPayDialog$1.$personId);
                    customAlertDialog.cancel();
                }
            });
        }
        customAlertDialog.setConfirmButton(AppUtil.getString(R.string.post_need_pay_confim), new View$OnClickListenerC26253(customAlertDialog, tomatoBalance));
        customAlertDialog.setTitle(AppUtil.getString(R.string.post_need_pay_title));
        customAlertDialog.setTitleLineVisible(false);
        customAlertDialog.setTitleBackgroundDrawable(ContextCompat.getDrawable(this.$mContext, R.drawable.common_shape_solid_corner12_white));
        customAlertDialog.setBottomHorizontalLineVisible(false);
        customAlertDialog.setBottomVerticalLineVisible(false);
        String string = AppUtil.getString(R.string.post_need_pay_messge);
        Intrinsics.checkExpressionValueIsNotNull(string, "string");
        Object[] objArr = {FormatUtil.formatTomato2RMB(this.$needPay), FormatUtil.formatTomato2RMB(tomatoBalance)};
        String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
        customAlertDialog.setMessage(format);
        if (RexUtils.isNumber(this.$needPay)) {
            String str = this.$needPay;
            if (str == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (Double.parseDouble(tomatoBalance) < Double.parseDouble(str)) {
                String string2 = AppUtil.getString(R.string.post_video_consume_tip7);
                TextView confirmButton = customAlertDialog.getConfirmButton();
                if (confirmButton != null) {
                    confirmButton.setText(string2);
                }
            }
        }
        customAlertDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: PostUtils.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.utils.PostUtils$showImageNeedPayDialog$1$3 */
    /* loaded from: classes3.dex */
    public static final class View$OnClickListenerC26253 implements View.OnClickListener {
        final /* synthetic */ CustomAlertDialog $dialog;
        final /* synthetic */ String $tomatoBalance;

        View$OnClickListenerC26253(CustomAlertDialog customAlertDialog, String str) {
            this.$dialog = customAlertDialog;
            this.$tomatoBalance = str;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (TextUtils.isEmpty(PostUtils$showImageNeedPayDialog$1.this.$articleId)) {
                return;
            }
            PostRewardPayUtils postRewardPayUtils = PostRewardPayUtils.INSTANCE;
            String str = PostUtils$showImageNeedPayDialog$1.this.$articleId;
            if (str == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (postRewardPayUtils.isAreadlyPay(Integer.parseInt(str))) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_isareadly_pay));
                this.$dialog.dismiss();
            } else {
                double parseDouble = Double.parseDouble(this.$tomatoBalance);
                String str2 = PostUtils$showImageNeedPayDialog$1.this.$needPay;
                if (parseDouble < (str2 != null ? Double.parseDouble(str2) : 0.0d)) {
                    RechargeActivity.startActivity(PostUtils$showImageNeedPayDialog$1.this.$mContext);
                    return;
                }
                RelativeLayout relatePostNeedPay = this.$dialog.getRelate_post_need_pay();
                Intrinsics.checkExpressionValueIsNotNull(relatePostNeedPay, "relatePostNeedPay");
                relatePostNeedPay.setBackground(ContextCompat.getDrawable(PostUtils$showImageNeedPayDialog$1.this.$mContext, R.drawable.common_shape_solid_corner5_coloraccent80));
                TextView tv_post_currency_play = this.$dialog.getTv_post_currency_play();
                this.$dialog.setLoadingVisiby(true);
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
                linkedHashMap.put("articleId", PostUtils$showImageNeedPayDialog$1.this.$articleId);
                linkedHashMap.put("payType", 2);
                linkedHashMap.put("money", PostUtils$showImageNeedPayDialog$1.this.$needPay);
                VideoPlayCountUtils.getInstance().postRewardPay(PostUtils$showImageNeedPayDialog$1.this.$mContext, linkedHashMap, new C2621x3428cbc4(tv_post_currency_play, this), new C2622x3428cbc5(this));
            }
        }
    }
}
