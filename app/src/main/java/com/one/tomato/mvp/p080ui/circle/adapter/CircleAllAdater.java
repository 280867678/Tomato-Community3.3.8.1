package com.one.tomato.mvp.p080ui.circle.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.CircleAllBean;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact$ItemClick;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.utils.ConstantUtils;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CircleAllAdater.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.adapter.CircleAllAdater */
/* loaded from: classes3.dex */
public final class CircleAllAdater extends BaseRecyclerViewAdapter<CircleAllBean> {
    private ICircleAllContact$ItemClick clickItem;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CircleAllAdater(Context context, RecyclerView recyclerView, ICircleAllContact$ItemClick clickItem) {
        super(context, R.layout.circle_all_item, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
        Intrinsics.checkParameterIsNotNull(clickItem, "clickItem");
        this.clickItem = clickItem;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final CircleAllBean circleAllBean) {
        String str;
        super.convert(baseViewHolder, (BaseViewHolder) circleAllBean);
        Long l = null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_no_foucs) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate) : null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.round_view) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_foucs_num) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_add_foucs) : null;
        if (textView != null) {
            textView.setVisibility(8);
        }
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        if (getData().indexOf(circleAllBean) == 0 && circleAllBean != null && circleAllBean.getFollowFlag() == 0) {
            if (textView == null) {
                return;
            }
            textView.setVisibility(0);
            return;
        }
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
        }
        ImageLoaderUtil.loadHeadImage(this.mContext, roundedImageView, new ImageBean(circleAllBean != null ? circleAllBean.getLogo() : null));
        if (textView2 != null) {
            if (circleAllBean == null || (str = circleAllBean.getName()) == null) {
                str = "";
            }
            textView2.setText(str);
        }
        if (circleAllBean != null) {
            l = Long.valueOf(circleAllBean.getFollowCount());
        }
        if (l instanceof Long) {
            long longValue = l.longValue();
            long j = (long) ConstantUtils.MAX_ITEM_NUM;
            if (longValue > j) {
                if (textView3 != null) {
                    textView3.setText((l.longValue() / j) + "w+" + AppUtil.getString(R.string.circle_user_foucs));
                }
            } else if (textView3 != null) {
                textView3.setText(l + '+' + AppUtil.getString(R.string.circle_user_foucs));
            }
        }
        if (circleAllBean == null || circleAllBean.getFollowFlag() != 1) {
            if (textView4 != null) {
                textView4.setText(AppUtil.getString(R.string.common_focus_n_add));
            }
            if (textView4 != null) {
                textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.white));
            }
            if (textView4 != null) {
                textView4.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_coloraccent80));
            }
        } else {
            if (textView4 != null) {
                textView4.setText(AppUtil.getString(R.string.common_focus_y));
            }
            if (textView4 != null) {
                textView4.setTextColor(ContextCompat.getColor(this.mContext, R.color.text_dark));
            }
            if (textView4 != null) {
                textView4.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner4_f5f5f7));
            }
        }
        if (textView4 == null) {
            return;
        }
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.circle.adapter.CircleAllAdater$convert$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ICircleAllContact$ItemClick iCircleAllContact$ItemClick;
                iCircleAllContact$ItemClick = CircleAllAdater.this.clickItem;
                if (iCircleAllContact$ItemClick != null) {
                    CircleAllBean circleAllBean2 = circleAllBean;
                    if (circleAllBean2 != null) {
                        iCircleAllContact$ItemClick.clickItemFollow(circleAllBean2.getGroupId(), CircleAllAdater.this.getData().indexOf(circleAllBean));
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }
        });
    }
}
