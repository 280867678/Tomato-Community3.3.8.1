package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.p080ui.post.view.ReviewPostActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostReviewItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostReviewItem */
/* loaded from: classes3.dex */
public final class PostReviewItem extends RelativeLayout {
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

    public PostReviewItem(Context context, int i) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.post_review_item, this);
        if (i == 1) {
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_review);
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_no_data);
            if (imageView2 != null) {
                imageView2.setVisibility(8);
            }
        } else {
            ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_review);
            if (imageView3 != null) {
                imageView3.setVisibility(8);
            }
            ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.image_no_data);
            if (imageView4 != null) {
                imageView4.setVisibility(0);
            }
        }
        ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.image_review);
        if (imageView5 != null) {
            imageView5.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostReviewItem.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SystemParam systemParam = DBUtil.getSystemParam();
                    UserInfo userInfo = DBUtil.getUserInfo();
                    if ((userInfo != null && userInfo.isOfficial()) || (systemParam != null && systemParam.getReViewFlag() == 1)) {
                        ReviewPostActivity.Companion companion = ReviewPostActivity.Companion;
                        Context context2 = PostReviewItem.this.getContext();
                        Intrinsics.checkExpressionValueIsNotNull(context2, "getContext()");
                        companion.startAct(context2);
                    } else if ((systemParam != null && systemParam.getReViewFlag() == 0) || (systemParam != null && systemParam.getReViewFlag() == 2 && (userInfo == null || userInfo.getReviewType() != 1))) {
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.review_power_close));
                    } else {
                        ReviewPostActivity.Companion companion2 = ReviewPostActivity.Companion;
                        Context context3 = PostReviewItem.this.getContext();
                        Intrinsics.checkExpressionValueIsNotNull(context3, "getContext()");
                        companion2.startAct(context3);
                    }
                }
            });
        }
    }
}
