package com.one.tomato.adapter;

import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.MemberList;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.p085ui.mine.MemberListFragment;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* loaded from: classes3.dex */
public class MemberListAdapter extends BaseRecyclerViewAdapter<MemberList> {
    private String businessType;
    private MemberListFragment memberListFragment;

    public MemberListAdapter(MemberListFragment memberListFragment, int i, RecyclerView recyclerView, String str) {
        super(memberListFragment.getContext(), i, recyclerView);
        memberListFragment.getContext();
        this.memberListFragment = memberListFragment;
        this.businessType = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MemberList memberList) {
        super.convert(baseViewHolder, (BaseViewHolder) memberList);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_sex);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_desc);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_add_or_cancel);
        baseViewHolder.addOnClickListener(R.id.tv_add_or_cancel);
        ImageLoaderUtil.loadHeadImage(this.mContext, (ImageView) baseViewHolder.getView(R.id.iv_head), new ImageBean(memberList.getAvatar()));
        ((TextView) baseViewHolder.getView(R.id.tv_name)).setText(memberList.getName());
        if (TextUtils.isEmpty(memberList.getSignature())) {
            memberList.setSignature(AppUtil.getString(R.string.user_no_signature));
        }
        if ("1".equals(memberList.getSex())) {
            imageView.setImageResource(R.drawable.icon_man);
        } else {
            imageView.setImageResource(R.drawable.icon_woman);
        }
        textView.setText(memberList.getSignature());
        if ("circle_active_list".equals(this.businessType) && imageView.getVisibility() == 0) {
            imageView.setVisibility(8);
        }
        textView2.setVisibility(4);
        if ("fan_list".equals(this.businessType) || "focus_member_list".equals(this.businessType) || "circle_active_list".equals(this.businessType)) {
            textView2.setVisibility(0);
            if (memberList.getId() == DBUtil.getMemberId()) {
                textView2.setVisibility(4);
            }
            if (memberList.getFollow() == 1) {
                textView2.setText(R.string.common_focus_y);
                textView2.setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
                return;
            }
            textView2.setText(R.string.common_focus_n_add);
            textView2.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        NewMyHomePageActivity.Companion.startActivity(this.mContext, getItem(i).getId());
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
        MemberList item = getItem(i);
        if (view.getId() != R.id.tv_add_or_cancel) {
            return;
        }
        this.memberListFragment.focus(item, i);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
        this.memberListFragment.refresh();
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
        this.memberListFragment.loadMore();
    }
}
