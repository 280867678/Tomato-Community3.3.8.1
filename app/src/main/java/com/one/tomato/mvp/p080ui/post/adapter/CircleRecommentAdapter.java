package com.one.tomato.mvp.p080ui.post.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.mvp.p080ui.circle.view.CircleAllActivity;
import com.one.tomato.mvp.p080ui.post.item.PostCircleItem;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CircleRecommentAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.CircleRecommentAdapter */
/* loaded from: classes3.dex */
public final class CircleRecommentAdapter extends BaseQuickAdapter<CircleDiscoverListBean, BaseViewHolder> {
    private PostCircleItem.ItemClick itemClick;

    public CircleRecommentAdapter(PostCircleItem.ItemClick itemClick) {
        super((int) R.layout.circle_recomment_item);
        this.itemClick = itemClick;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final CircleDiscoverListBean circleDiscoverListBean) {
        ArrayList<PostList> arrayList;
        String str = null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.round_view) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_foucs_num) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_foucs) : null;
        RecyclerView recyclerView = baseViewHolder != null ? (RecyclerView) baseViewHolder.getView(R.id.post_recycler) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.read_content) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_click) : null;
        if (circleDiscoverListBean != null && circleDiscoverListBean.groupId == -100) {
            if (textView4 != null) {
                textView4.setVisibility(0);
            }
            if (relativeLayout != null) {
                relativeLayout.setVisibility(8);
            }
            if (textView4 == null) {
                return;
            }
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.adapter.CircleRecommentAdapter$convert$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context context;
                    CircleAllActivity.Companion companion = CircleAllActivity.Companion;
                    context = ((BaseQuickAdapter) CircleRecommentAdapter.this).mContext;
                    companion.startAct((Activity) context, false);
                }
            });
            return;
        }
        if (textView4 != null) {
            textView4.setVisibility(8);
        }
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
        }
        ImageLoaderUtil.loadHeadImage(this.mContext, roundedImageView, new ImageBean(circleDiscoverListBean != null ? circleDiscoverListBean.groupImage : null));
        if (textView != null) {
            if (circleDiscoverListBean != null) {
                str = circleDiscoverListBean.groupName;
            }
            textView.setText(str);
        }
        if (textView2 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(circleDiscoverListBean != null ? circleDiscoverListBean.followCount : 0);
            sb.append(' ');
            sb.append(AppUtil.getString(R.string.common_focus_n));
            textView2.setText(sb.toString());
        }
        if (circleDiscoverListBean == null || circleDiscoverListBean.followFlag != 1) {
            if (textView3 != null) {
                textView3.setText(AppUtil.getString(R.string.common_focus_n_add));
            }
            if (textView3 != null) {
                textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.white));
            }
            if (textView3 != null) {
                textView3.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_coloraccent80));
            }
        } else {
            if (textView3 != null) {
                textView3.setText(AppUtil.getString(R.string.common_focus_y));
            }
            if (textView3 != null) {
                textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.text_dark));
            }
            if (textView3 != null) {
                textView3.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner4_f5f5f7));
            }
        }
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.adapter.CircleRecommentAdapter$convert$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostCircleItem.ItemClick itemClick;
                    itemClick = CircleRecommentAdapter.this.itemClick;
                    if (itemClick != null) {
                        CircleDiscoverListBean circleDiscoverListBean2 = circleDiscoverListBean;
                        Integer valueOf = circleDiscoverListBean2 != null ? Integer.valueOf(circleDiscoverListBean2.groupId) : null;
                        if (valueOf != null) {
                            itemClick.clickItemFollow(valueOf.intValue(), CircleRecommentAdapter.this.getData().indexOf(circleDiscoverListBean));
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                }
            });
        }
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
        }
        BaseQuickAdapter<PostList, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<PostList, BaseViewHolder>(R.layout.circle_item_post_item) { // from class: com.one.tomato.mvp.ui.post.adapter.CircleRecommentAdapter$convert$adapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder2, PostList postList) {
                Integer num = null;
                RoundedImageView roundedImageView2 = baseViewHolder2 != null ? (RoundedImageView) baseViewHolder2.getView(R.id.image) : null;
                RelativeLayout relativeLayout2 = baseViewHolder2 != null ? (RelativeLayout) baseViewHolder2.getView(R.id.relate_bar) : null;
                ImageView imageView = baseViewHolder2 != null ? (ImageView) baseViewHolder2.getView(R.id.image_top_bar) : null;
                RelativeLayout relativeLayout3 = baseViewHolder2 != null ? (RelativeLayout) baseViewHolder2.getView(R.id.relate_text) : null;
                TextView textView5 = baseViewHolder2 != null ? (TextView) baseViewHolder2.getView(R.id.text_des) : null;
                TextView textView6 = baseViewHolder2 != null ? (TextView) baseViewHolder2.getView(R.id.text_title) : null;
                if (relativeLayout3 != null) {
                    relativeLayout3.setVisibility(8);
                }
                if (roundedImageView2 != null) {
                    roundedImageView2.setVisibility(8);
                }
                if (relativeLayout2 != null) {
                    relativeLayout2.setVisibility(8);
                }
                String str2 = "";
                if (textView6 != null) {
                    textView6.setText(TextUtils.isEmpty(postList != null ? postList.getTitle() : null) ? str2 : postList != null ? postList.getTitle() : null);
                }
                if (postList != null) {
                    num = Integer.valueOf(postList.getPostType());
                }
                if (num != null) {
                    boolean z = true;
                    if (num.intValue() == 1) {
                        if (roundedImageView2 != null) {
                            roundedImageView2.setVisibility(0);
                        }
                        if (relativeLayout2 != null) {
                            relativeLayout2.setVisibility(0);
                        }
                        if (imageView != null) {
                            imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.circle_image));
                        }
                        ArrayList<ImageBean> createImageBeanList = PostUtils.INSTANCE.createImageBeanList(postList);
                        if (createImageBeanList != null && !createImageBeanList.isEmpty()) {
                            z = false;
                        }
                        if (z) {
                            return;
                        }
                        ImageLoaderUtil.loadRecyclerThumbSamllImage(this.mContext, roundedImageView2, createImageBeanList.get(0));
                        return;
                    }
                }
                if (num != null && num.intValue() == 2) {
                    if (roundedImageView2 != null) {
                        roundedImageView2.setVisibility(0);
                    }
                    if (relativeLayout2 != null) {
                        relativeLayout2.setVisibility(0);
                    }
                    if (imageView != null) {
                        imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.circle_video));
                    }
                    ImageLoaderUtil.loadRecyclerThumbSamllImage(this.mContext, roundedImageView2, new ImageBean(postList.getSecVideoCover()));
                } else if (num == null || num.intValue() != 3) {
                } else {
                    if (relativeLayout3 != null) {
                        relativeLayout3.setVisibility(0);
                    }
                    if (textView5 == null) {
                        return;
                    }
                    if (!TextUtils.isEmpty(postList.getDescription())) {
                        str2 = postList.getDescription();
                    }
                    textView5.setText(str2);
                }
            }
        };
        if (recyclerView != null) {
            recyclerView.setAdapter(baseQuickAdapter);
        }
        if (circleDiscoverListBean != null && (arrayList = circleDiscoverListBean.articleList) != null) {
            ArrayList arrayList2 = new ArrayList();
            if (arrayList.size() >= 3) {
                List<PostList> subList = arrayList.subList(0, 3);
                Intrinsics.checkExpressionValueIsNotNull(subList, "it.subList(0, 3)");
                arrayList2.addAll(subList);
            } else if (arrayList.size() > 0) {
                List<PostList> subList2 = arrayList.subList(0, arrayList.size());
                Intrinsics.checkExpressionValueIsNotNull(subList2, "it.subList(0, it.size)");
                arrayList2.addAll(subList2);
            }
            baseQuickAdapter.addData(arrayList2);
        }
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.adapter.CircleRecommentAdapter$convert$4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter2, View view, int i) {
                Context context;
                Object item = baseQuickAdapter2.getItem(i);
                if (item == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.PostList");
                }
                NewPostDetailViewPagerActivity.Companion companion = NewPostDetailViewPagerActivity.Companion;
                context = ((BaseQuickAdapter) CircleRecommentAdapter.this).mContext;
                companion.startActivity(context, ((PostList) item).getId(), false, false, false);
            }
        });
    }
}
