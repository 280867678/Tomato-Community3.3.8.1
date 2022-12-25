package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.p080ui.post.view.PublishPostSpecificationActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: ReviewPostHeadView.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.ReviewPostHeadView */
/* loaded from: classes3.dex */
public final class ReviewPostHeadView extends RelativeLayout {
    private HashMap _$_findViewCache;

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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReviewPostHeadView(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        LayoutInflater.from(context).inflate(R.layout.review_head_view, this);
        initView();
    }

    private final void initView() {
        String str;
        int indexOf$default;
        int indexOf$default2;
        int lastIndexOf$default;
        String string = AppUtil.getString(R.string.review_notice_2);
        if (string instanceof String) {
            SpannableString spannableString = new SpannableString(string);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#5B92E1"));
            indexOf$default2 = StringsKt__StringsKt.indexOf$default((CharSequence) string, "《", 0, false, 6, (Object) null);
            lastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default(string, "》", 0, false, 6, null);
            spannableString.setSpan(foregroundColorSpan, indexOf$default2, lastIndexOf$default + 1, 18);
            TextView text_notice_2 = (TextView) _$_findCachedViewById(R$id.text_notice_2);
            Intrinsics.checkExpressionValueIsNotNull(text_notice_2, "text_notice_2");
            text_notice_2.setText(spannableString);
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_notice_2);
            if (textView != null) {
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.ReviewPostHeadView$initView$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        PublishPostSpecificationActivity.Companion companion = PublishPostSpecificationActivity.Companion;
                        Context context = ReviewPostHeadView.this.getContext();
                        Intrinsics.checkExpressionValueIsNotNull(context, "context");
                        companion.startAct(context);
                    }
                });
            }
        }
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "userInfo");
        if (userInfo.isOfficial()) {
            str = userInfo.getOfficialTime();
            Intrinsics.checkExpressionValueIsNotNull(str, "userInfo.officialTime");
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_post_type);
            if (textView2 != null) {
                textView2.setText(AppUtil.getString(R.string.review_offciler));
            }
        } else if (userInfo.getReviewType() == 0) {
            str = userInfo.getReviewTime();
            Intrinsics.checkExpressionValueIsNotNull(str, "userInfo.reviewTime");
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_post_type);
            if (textView3 != null) {
                textView3.setText(AppUtil.getString(R.string.review_type_0));
            }
        } else if (userInfo.getReviewType() == 1) {
            str = userInfo.getReviewTime();
            Intrinsics.checkExpressionValueIsNotNull(str, "userInfo.reviewTime");
            TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_post_type);
            if (textView4 != null) {
                textView4.setText(AppUtil.getString(R.string.review_type_1));
            }
        } else {
            str = "";
        }
        if (!TextUtils.isEmpty(str)) {
            if (str != null) {
                indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) str, ConstantUtils.PLACEHOLDER_STR_ONE, 0, false, 6, (Object) null);
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                str = str.substring(0, indexOf$default);
                Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            } else {
                str = null;
            }
        }
        TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_time);
        if (textView5 != null) {
            textView5.setText(AppUtil.getString(R.string.review_post_time) + str);
        }
    }
}
