package com.one.tomato.mvp.p080ui.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.CreatorCenterBean;
import com.one.tomato.entity.CreatorCenterHomeBean;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.mvp.p080ui.p082up.view.UpRankHomeActivity;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CreatorRankAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.adapter.CreatorRankAdapter */
/* loaded from: classes3.dex */
public final class CreatorRankAdapter extends BaseRecyclerViewAdapter<CreatorCenterHomeBean> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreatorRankAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.creator_rank_layout, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final CreatorCenterHomeBean creatorCenterHomeBean) {
        super.convert(baseViewHolder, (BaseViewHolder) creatorCenterHomeBean);
        ArrayList<CreatorCenterBean.TodayRank> arrayList = null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_profit_hint) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_profit_more) : null;
        RecyclerView recyclerView = baseViewHolder != null ? (RecyclerView) baseViewHolder.getView(R.id.rv_profit) : null;
        String str = creatorCenterHomeBean != null ? creatorCenterHomeBean.topListType : null;
        if (str != null) {
            switch (str.hashCode()) {
                case 49:
                    if (str.equals("1") && textView != null) {
                        textView.setText(AppUtil.getString(R.string.up_rank_income));
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2") && textView != null) {
                        textView.setText(AppUtil.getString(R.string.up_rank_publish));
                        break;
                    }
                    break;
                case 51:
                    if (str.equals("3") && textView != null) {
                        textView.setText(AppUtil.getString(R.string.up_rank_hot));
                        break;
                    }
                    break;
                case 52:
                    if (str.equals("4") && textView != null) {
                        textView.setText(AppUtil.getString(R.string.up_rank_fans));
                        break;
                    }
                    break;
            }
        }
        final Context context = this.mContext;
        final RecyclerView recyclerView2 = recyclerView;
        final RecyclerView recyclerView3 = recyclerView;
        BaseRecyclerViewAdapter<CreatorCenterBean.TodayRank> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<CreatorCenterBean.TodayRank>(this, creatorCenterHomeBean, recyclerView2, context, R.layout.item_creator_profit_list, recyclerView3) { // from class: com.one.tomato.mvp.ui.mine.adapter.CreatorRankAdapter$convert$profitListAdapter$1
            final /* synthetic */ CreatorCenterHomeBean $itemDatas;

            /* JADX INFO: Access modifiers changed from: package-private */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(context, r5, recyclerView3);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder2, CreatorCenterBean.TodayRank todayRank) {
                String str2;
                String str3;
                String str4;
                super.convert(baseViewHolder2, (BaseViewHolder) todayRank);
                String str5 = null;
                ImageView imageView = baseViewHolder2 != null ? (ImageView) baseViewHolder2.getView(R.id.iv_icon) : null;
                ImageView imageView2 = baseViewHolder2 != null ? (ImageView) baseViewHolder2.getView(R.id.iv_head) : null;
                TextView textView3 = baseViewHolder2 != null ? (TextView) baseViewHolder2.getView(R.id.tv_name) : null;
                TextView textView4 = baseViewHolder2 != null ? (TextView) baseViewHolder2.getView(R.id.tv_num) : null;
                int indexOf = this.mData.indexOf(todayRank);
                if (indexOf != 0) {
                    if (indexOf != 1) {
                        if (indexOf == 2 && imageView != null) {
                            imageView.setImageResource(R.drawable.creator_profit_3);
                        }
                    } else if (imageView != null) {
                        imageView.setImageResource(R.drawable.creator_profit_2);
                    }
                } else if (imageView != null) {
                    imageView.setImageResource(R.drawable.creator_profit_1);
                }
                ImageLoaderUtil.loadHeadImage(this.mContext, imageView2, new ImageBean(todayRank != null ? todayRank.avatar : null));
                if (textView3 != null) {
                    textView3.setText(todayRank != null ? todayRank.memberName : null);
                }
                CreatorCenterHomeBean creatorCenterHomeBean2 = this.$itemDatas;
                String str6 = creatorCenterHomeBean2 != null ? creatorCenterHomeBean2.topListType : null;
                if (str6 == null) {
                    return;
                }
                switch (str6.hashCode()) {
                    case 49:
                        if (!str6.equals("1") || textView4 == null) {
                            return;
                        }
                        if (todayRank != null) {
                            str5 = todayRank.income;
                        }
                        textView4.setText(FormatUtil.formatTomato2RMB(str5));
                        return;
                    case 50:
                        if (!str6.equals("2")) {
                            return;
                        }
                        if (textView4 != null) {
                            StringBuilder sb = new StringBuilder();
                            if (todayRank == null || (str2 = todayRank.income) == null) {
                                str2 = "0";
                            }
                            sb.append(FormatUtil.formatNumOverTenThousand(str2));
                            sb.append(AppUtil.getString(R.string.up_creator_zp));
                            textView4.setText(sb.toString());
                        }
                        if (textView4 == null) {
                            return;
                        }
                        textView4.setCompoundDrawables(null, null, null, null);
                        return;
                    case 51:
                        if (!str6.equals("3")) {
                            return;
                        }
                        if (textView4 != null) {
                            if (todayRank == null || (str3 = todayRank.income) == null) {
                                str3 = "0";
                            }
                            textView4.setText(FormatUtil.formatNumOverTenThousand(str3));
                        }
                        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.creator_hot);
                        if (drawable != null) {
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        }
                        if (textView4 == null) {
                            return;
                        }
                        textView4.setCompoundDrawables(drawable, null, null, null);
                        return;
                    case 52:
                        if (!str6.equals("4")) {
                            return;
                        }
                        if (textView4 != null) {
                            StringBuilder sb2 = new StringBuilder();
                            if (todayRank == null || (str4 = todayRank.income) == null) {
                                str4 = "0";
                            }
                            sb2.append(FormatUtil.formatNumOverTenThousand(str4));
                            sb2.append(AppUtil.getString(R.string.up_creator_fans));
                            textView4.setText(sb2.toString());
                        }
                        if (textView4 == null) {
                            return;
                        }
                        textView4.setCompoundDrawables(null, null, null, null);
                        return;
                    default:
                        return;
                }
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                Intent intent = new Intent(this.mContext, UpRankHomeActivity.class);
                Context context2 = this.mContext;
                if (context2 != null) {
                    context2.startActivity(intent);
                }
            }
        };
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(this.mContext, 1, false);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(baseLinearLayoutManager);
        }
        if (recyclerView != null) {
            recyclerView.setAdapter(baseRecyclerViewAdapter);
        }
        if (creatorCenterHomeBean != null) {
            arrayList = creatorCenterHomeBean.top3List;
        }
        baseRecyclerViewAdapter.setNewData(arrayList);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.adapter.CreatorRankAdapter$convert$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context context2;
                    Context context3;
                    context2 = ((BaseQuickAdapter) CreatorRankAdapter.this).mContext;
                    Intent intent = new Intent(context2, UpRankHomeActivity.class);
                    context3 = ((BaseQuickAdapter) CreatorRankAdapter.this).mContext;
                    if (context3 != null) {
                        context3.startActivity(intent);
                    }
                }
            });
        }
    }
}
