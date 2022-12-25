package com.one.tomato.mvp.p080ui.papa.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaListVideoActivity;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NewPaPaAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.adapter.NewPaPaAdapter */
/* loaded from: classes3.dex */
public final class NewPaPaAdapter extends BaseRecyclerViewAdapter<PostList> {
    private boolean isPaPaTumbe;
    private int pageNumber = 1;

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPaPaAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.new_papa_list_item, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
    }

    public final void setPageNumber(int i) {
        this.pageNumber = i;
    }

    public final int getPageNumber() {
        return this.pageNumber;
    }

    public final void setIsPaPaTumbe(boolean z) {
        this.isPaPaTumbe = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final PostList postList) {
        float width;
        int i;
        String description;
        float height;
        super.convert(baseViewHolder, (BaseViewHolder) postList);
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_background) : null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.backround_image) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_like_number) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_ad_detail) : null;
        TextView textView5 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_ad) : null;
        TextView textView6 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_need_pay) : null;
        TextView textView7 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_original) : null;
        ImageView imageView2 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_tumbe) : null;
        if (imageView2 != null) {
            imageView2.setVisibility(8);
        }
        if (this.isPaPaTumbe) {
            width = DisplayMetricsUtils.getWidth() - DisplayMetricsUtils.dp2px(32.0f);
        } else {
            width = DisplayMetricsUtils.getWidth();
        }
        float f = 2;
        float f2 = width / f;
        int i2 = (int) f2;
        ImageBean createImageBean = PostUtils.INSTANCE.createImageBean(postList);
        RelativeLayout relativeLayout2 = relativeLayout;
        if (createImageBean instanceof ImageBean) {
            float width2 = createImageBean.getWidth();
            if (width2 < f2) {
                height = createImageBean.getHeight() * (f2 / width2);
            } else if (width2 > f2) {
                height = createImageBean.getHeight() / (width2 / f2);
            } else {
                i = createImageBean.getHeight();
            }
            i = (int) height;
        } else {
            i = i2;
        }
        if (i < i2) {
            i = (int) (f2 * 1.25d);
        }
        float f3 = i;
        int i3 = i;
        float f4 = 3;
        int height2 = f3 > (DisplayMetricsUtils.getHeight() * f) / f4 ? (int) ((DisplayMetricsUtils.getHeight() * f) / f4) : i3;
        if (imageView != null) {
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(-1, height2));
        }
        if (textView5 != null) {
            textView5.setVisibility(8);
        }
        if (textView4 != null) {
            textView4.setVisibility(8);
        }
        if (textView3 != null) {
            textView3.setVisibility(8);
        }
        if (textView2 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append('@');
            sb.append(postList != null ? postList.getName() : null);
            textView2.setText(sb.toString());
        }
        if (textView != null) {
            if (!TextUtils.isEmpty(postList != null ? postList.getTitle() : null)) {
                if (postList != null) {
                    description = postList.getTitle();
                    textView.setText(description);
                }
                description = null;
                textView.setText(description);
            } else {
                if (postList != null) {
                    description = postList.getDescription();
                    textView.setText(description);
                }
                description = null;
                textView.setText(description);
            }
        }
        if (this.isPaPaTumbe) {
            if (getData().indexOf(postList) == 0) {
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
                if (imageView2 != null) {
                    imageView2.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.papa_hot_tumbe_one));
                }
            }
            if (getData().indexOf(postList) == 1) {
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
                if (imageView2 != null) {
                    imageView2.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.papa_hot_tumbe_two));
                }
            }
            if (getData().indexOf(postList) == 2) {
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
                if (imageView2 != null) {
                    imageView2.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.papa_hot_tumbe_three));
                }
            }
        }
        final Boolean valueOf = postList != null ? Boolean.valueOf(postList.isAlreadyPaid()) : null;
        if (Intrinsics.areEqual(valueOf, true)) {
            if (textView6 != null) {
                textView6.setVisibility(0);
            }
            if (textView6 != null) {
                textView6.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner30_f0f7ee));
            }
            Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.post_alread_pay);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            }
            if (textView6 != null) {
                textView6.setCompoundDrawables(drawable, null, null, null);
            }
            if (textView6 != null) {
                textView6.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_2ADD93));
            }
            if (textView6 != null) {
                textView6.setText(AppUtil.getString(R.string.post_already_pay));
            }
        } else if (Intrinsics.areEqual(valueOf, false) && postList.getPrice() > 0) {
            if (textView6 != null) {
                textView6.setVisibility(0);
            }
            if (textView6 != null) {
                textView6.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner30_grey));
            }
            Drawable drawable2 = ContextCompat.getDrawable(this.mContext, R.drawable.post_need_pay);
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            }
            if (textView6 != null) {
                textView6.setCompoundDrawables(drawable2, null, null, null);
            }
            if (textView6 != null) {
                textView6.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_FC4C7B));
            }
            if (textView6 != null) {
                textView6.setText(FormatUtil.formatTomato2RMB(postList.getPrice()));
            }
        } else if (textView6 != null) {
            textView6.setVisibility(8);
        }
        if (postList == null || postList.getOriginalFlag() != 1) {
            if (textView7 != null) {
                textView7.setVisibility(8);
            }
        } else if (textView7 != null) {
            textView7.setVisibility(0);
        }
        if (textView6 != null) {
            textView6.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.adapter.NewPaPaAdapter$convert$1

                /* compiled from: NewPaPaAdapter.kt */
                /* renamed from: com.one.tomato.mvp.ui.papa.adapter.NewPaPaAdapter$convert$1$1 */
                /* loaded from: classes3.dex */
                static final class C25761 extends Lambda implements Functions<Unit> {
                    C25761() {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Functions
                    /* renamed from: invoke */
                    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
                        mo6822invoke();
                        return Unit.INSTANCE;
                    }

                    @Override // kotlin.jvm.functions.Functions
                    /* renamed from: invoke  reason: avoid collision after fix types in other method */
                    public final void mo6822invoke() {
                        postList.setAlreadyPaid(true);
                        NewPaPaAdapter.this.notifyItemChanged(NewPaPaAdapter.this.getData().indexOf(postList));
                    }
                }

                /* compiled from: NewPaPaAdapter.kt */
                /* renamed from: com.one.tomato.mvp.ui.papa.adapter.NewPaPaAdapter$convert$1$2 */
                /* loaded from: classes3.dex */
                static final class C25772 extends Lambda implements Functions<Unit> {
                    public static final C25772 INSTANCE = new C25772();

                    C25772() {
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
                    Context mContext;
                    if (Intrinsics.areEqual(valueOf, false)) {
                        PostUtils postUtils = PostUtils.INSTANCE;
                        mContext = ((BaseQuickAdapter) NewPaPaAdapter.this).mContext;
                        Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
                        postUtils.showImageNeedPayDialog(mContext, String.valueOf(postList.getPrice()), String.valueOf(postList.getId()), postList.getSubscribeSwitch() == 1 ? postList.getMemberId() : 0, new C25761(), C25772.INSTANCE);
                    }
                }
            });
        }
        Boolean valueOf2 = postList != null ? Boolean.valueOf(postList.isAd()) : null;
        if (Intrinsics.areEqual(valueOf2, true)) {
            if (textView3 != null) {
                textView3.setVisibility(8);
            }
            if (textView5 != null) {
                textView5.setVisibility(0);
            }
            if (textView4 != null) {
                textView4.setVisibility(0);
            }
            if (postList.getPage().getAdType() == 1) {
                if (textView4 != null) {
                    textView4.setText(this.mContext.getText(R.string.post_ad_view_detail));
                }
                if (textView5 != null) {
                    textView5.setText(R.string.common_ad);
                }
            } else if (postList.getPage().getAdType() == 2) {
                if ("1".equals(postList.getPage().getAdLinkType())) {
                    if (textView4 != null) {
                        textView4.setText(this.mContext.getText(R.string.post_ad_download));
                    }
                } else if ("2".equals(postList.getPage().getAdLinkType()) && textView4 != null) {
                    textView4.setText(this.mContext.getText(R.string.post_ad_view_detail));
                }
                if (textView5 != null) {
                    textView5.setText(R.string.common_ad);
                }
            } else {
                AdPage page = postList.getPage();
                Intrinsics.checkExpressionValueIsNotNull(page, "itemData.page");
                if (page.getAdType() == 3) {
                    if (textView4 != null) {
                        textView4.setText(R.string.post_ad_web_app_start);
                    }
                    if (textView5 != null) {
                        textView5.setText(R.string.common_web_app);
                    }
                }
            }
        } else if (Intrinsics.areEqual(valueOf2, false)) {
            if (textView3 != null) {
                textView3.setVisibility(0);
            }
            if (textView5 != null) {
                textView5.setVisibility(8);
            }
            if (textView4 != null) {
                textView4.setVisibility(8);
            }
            if (textView != null) {
                textView.setText(postList != null ? postList.getTitle() : null);
            }
            if (textView3 != null) {
                textView3.setText(String.valueOf((postList != null ? Integer.valueOf(postList.getGoodNum()) : null).intValue()));
            }
        }
        int color = ContextCompat.getColor(this.mContext, R.color.color_333333);
        if (relativeLayout2 != null) {
            relativeLayout2.setBackgroundColor(color);
        }
        String str = null;
        if (imageView != null) {
            imageView.setImageBitmap(null);
        }
        Context context = this.mContext;
        if (postList != null) {
            str = postList.getSecVideoCover();
        }
        ImageLoaderUtil.loadRecyclerThumbImageNoCrop(context, imageView, new ImageBean(str));
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        LogUtil.m3785e("yan", "点击跳转拍拍视频页面");
        List subList = this.mData.subList(i, getData().size());
        ArrayList<PostList> arrayList = new ArrayList<>();
        arrayList.addAll(subList);
        NewPaPaListVideoActivity.Companion companion = NewPaPaListVideoActivity.Companion;
        Context mContext = this.mContext;
        Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
        companion.startAct(mContext, arrayList, null, null, 0, getPageNumber(), i, true);
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseViewHolder holder) {
        Context context;
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        super.onViewRecycled((NewPaPaAdapter) holder);
        View view = holder.itemView;
        ImageView imageView = view != null ? (ImageView) view.findViewById(R.id.backround_image) : null;
        if (imageView == null || (context = this.mContext) == null) {
            return;
        }
        Glide.with(context).clear(imageView);
    }
}
