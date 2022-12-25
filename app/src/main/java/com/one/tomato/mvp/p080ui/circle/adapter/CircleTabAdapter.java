package com.one.tomato.mvp.p080ui.circle.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.CircleDetail;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleTabContract$ItemClick;
import com.one.tomato.mvp.p080ui.circle.view.CircleSingleActivity;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.BetterHorScrollRecyclerView;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.Ranges;
import kotlin.ranges._Ranges;

/* compiled from: CircleTabAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.adapter.CircleTabAdapter */
/* loaded from: classes3.dex */
public final class CircleTabAdapter extends BaseRecyclerViewAdapter<CircleDiscoverListBean> {
    private final int[] background = {R.drawable.circle_background_blue_01, R.drawable.circle_background_blue_02, R.drawable.circle_background_green_01, R.drawable.circle_background_red_01, R.drawable.circle_background_yellow_01, R.drawable.circle_background_zise_01};
    private ICircleTabContract$ItemClick clickItem;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CircleTabAdapter(Context context, RecyclerView recyclerView, ICircleTabContract$ItemClick clickItem) {
        super(context, R.layout.circle_tab_item, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
        Intrinsics.checkParameterIsNotNull(clickItem, "clickItem");
        this.clickItem = clickItem;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    @SuppressLint({"ClickableViewAccessibility"})
    public void convert(BaseViewHolder baseViewHolder, final CircleDiscoverListBean circleDiscoverListBean) {
        int random;
        ArrayList<PostList> arrayList;
        String str;
        String str2;
        super.convert(baseViewHolder, (BaseViewHolder) circleDiscoverListBean);
        Integer num = null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.image_head) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_foucs_num) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_post_num) : null;
        TextView textView5 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_foucs) : null;
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = baseViewHolder != null ? (BetterHorScrollRecyclerView) baseViewHolder.getView(R.id.recycler_view) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_background) : null;
        random = _Ranges.random(new Ranges(0, 5), Random.Default);
        if (relativeLayout != null) {
            relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, this.background[random]));
        }
        ImageLoaderUtil.loadHeadImage(this.mContext, roundedImageView, new ImageBean(circleDiscoverListBean != null ? circleDiscoverListBean.groupImage : null));
        String str3 = "";
        if (textView != null) {
            if (circleDiscoverListBean == null || (str2 = circleDiscoverListBean.groupName) == null) {
                str2 = str3;
            }
            textView.setText(str2);
        }
        if (textView2 != null) {
            if (circleDiscoverListBean != null && (str = circleDiscoverListBean.brief) != null) {
                str3 = str;
            }
            textView2.setText(str3);
        }
        if (textView3 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(AppUtil.getString(R.string.common_focus_n));
            sb.append(' ');
            sb.append(circleDiscoverListBean != null ? Integer.valueOf(circleDiscoverListBean.followCount) : null);
            textView3.setText(sb.toString());
        }
        if (textView4 != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(AppUtil.getString(R.string.circle_post));
            sb2.append(' ');
            if (circleDiscoverListBean != null) {
                num = Integer.valueOf(circleDiscoverListBean.articleCount);
            }
            sb2.append(num);
            textView4.setText(sb2.toString());
        }
        if (circleDiscoverListBean == null || circleDiscoverListBean.followFlag != 1) {
            if (textView5 != null) {
                textView5.setText(AppUtil.getString(R.string.common_focus_n_add));
            }
            if (textView5 != null) {
                textView5.setTextColor(ContextCompat.getColor(this.mContext, R.color.text_dark));
            }
            if (textView5 != null) {
                textView5.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_white));
            }
        } else {
            if (textView5 != null) {
                textView5.setText(AppUtil.getString(R.string.common_focus_y));
            }
            if (textView5 != null) {
                textView5.setTextColor(ContextCompat.getColor(this.mContext, R.color.white));
            }
            if (textView5 != null) {
                textView5.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_stroke_corner5_white_divider));
            }
        }
        if (textView5 != null) {
            textView5.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.circle.adapter.CircleTabAdapter$convert$1
                /* JADX WARN: Code restructure failed: missing block: B:3:0x0004, code lost:
                    r0 = r3.this$0.clickItem;
                 */
                @Override // android.view.View.OnClickListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onClick(View view) {
                    ICircleTabContract$ItemClick iCircleTabContract$ItemClick;
                    CircleDiscoverListBean circleDiscoverListBean2 = circleDiscoverListBean;
                    if (circleDiscoverListBean2 == null || iCircleTabContract$ItemClick == null) {
                        return;
                    }
                    iCircleTabContract$ItemClick.clickItemFollow(circleDiscoverListBean2.groupId, CircleTabAdapter.this.getData().indexOf(circleDiscoverListBean));
                }
            });
        }
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
        }
        CircleItemAdapter circleItemAdapter = new CircleItemAdapter();
        if (circleDiscoverListBean != null && (arrayList = circleDiscoverListBean.articleList) != null) {
            circleItemAdapter.addData((Collection) arrayList);
            circleItemAdapter.addData((CircleItemAdapter) new PostList(-100));
        }
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.setAdapter(circleItemAdapter);
        }
        circleItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.circle.adapter.CircleTabAdapter$convert$3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
                Context context;
                Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
                if (i == adapter.getItemCount() - 1) {
                    CircleTabAdapter circleTabAdapter = CircleTabAdapter.this;
                    circleTabAdapter.onRecyclerItemClick(circleTabAdapter, null, circleTabAdapter.getData().indexOf(circleDiscoverListBean));
                    return;
                }
                Object item = adapter.getItem(i);
                if (item == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.PostList");
                }
                NewPostDetailViewPagerActivity.Companion companion = NewPostDetailViewPagerActivity.Companion;
                context = ((BaseQuickAdapter) CircleTabAdapter.this).mContext;
                companion.startActivity(context, ((PostList) item).getId(), false, false, false);
            }
        });
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        CircleDetail circleDetail = new CircleDetail();
        Object item = baseQuickAdapter != null ? baseQuickAdapter.getItem(i) : null;
        if (item instanceof CircleDiscoverListBean) {
            CircleDiscoverListBean circleDiscoverListBean = (CircleDiscoverListBean) item;
            circleDetail.setArticleCount(circleDiscoverListBean.articleCount);
            circleDetail.setLogo(circleDiscoverListBean.groupImage);
            circleDetail.setBrief(circleDiscoverListBean.brief);
            circleDetail.setCategoryName(circleDiscoverListBean.categoryName);
            circleDetail.setFollowCount(circleDiscoverListBean.followCount);
            circleDetail.setFollowFlag(circleDiscoverListBean.followFlag);
            circleDetail.setId(circleDiscoverListBean.groupId);
            circleDetail.setName(circleDiscoverListBean.groupName);
            circleDetail.setOfficial(circleDiscoverListBean.official);
        }
        CircleSingleActivity.startActivity(this.mContext, circleDetail);
    }
}
