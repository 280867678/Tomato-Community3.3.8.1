package com.one.tomato.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.SubscribeUpList;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* loaded from: classes3.dex */
public class SubscribeUpListAdapter extends BaseRecyclerViewAdapter<SubscribeUpList> {
    public SubscribeUpListAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_subscribe_up, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, SubscribeUpList subscribeUpList) {
        super.convert(baseViewHolder, (BaseViewHolder) subscribeUpList);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_up_type);
        ImageLoaderUtil.loadHeadImage(this.mContext, (ImageView) baseViewHolder.getView(R.id.iv_head), new ImageBean(subscribeUpList.avatar));
        ((TextView) baseViewHolder.getView(R.id.tv_name)).setText(subscribeUpList.name);
        ((TextView) baseViewHolder.getView(R.id.tv_expire_time)).setText(subscribeUpList.expireTime);
        int i = subscribeUpList.type;
        if (i == 1) {
            imageView.setImageResource(R.drawable.up_valid_month);
        } else if (i == 2) {
            imageView.setImageResource(R.drawable.up_valid_season);
        } else if (i != 3) {
        } else {
            imageView.setImageResource(R.drawable.up_valid_year);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        NewMyHomePageActivity.Companion.startActivity(this.mContext, ((SubscribeUpList) this.mData.get(i)).memberId);
    }
}
