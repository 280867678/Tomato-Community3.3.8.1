package com.one.tomato.adapter;

import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.RecordBean;
import com.one.tomato.p085ui.task.VirtualRecordFragment;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.ViewUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tomatolive.library.utils.ConstantUtils;
import org.slf4j.Marker;

/* loaded from: classes3.dex */
public class VirtualRecordAdapter extends BaseRecyclerViewAdapter<RecordBean> {
    private VirtualRecordFragment context;
    private String mBusinessType;
    private RefreshLayout refreshLayout;

    public VirtualRecordAdapter(VirtualRecordFragment virtualRecordFragment, RecyclerView recyclerView, String str, RefreshLayout refreshLayout) {
        super(virtualRecordFragment.getContext(), R.layout.item_virtual_record, recyclerView);
        this.context = virtualRecordFragment;
        this.mBusinessType = str;
        this.refreshLayout = refreshLayout;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, RecordBean recordBean) {
        super.convert(baseViewHolder, (BaseViewHolder) recordBean);
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.rl_root);
        View view = baseViewHolder.getView(R.id.divider);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_num);
        ((TextView) baseViewHolder.getView(R.id.tv_title)).setText(recordBean.getContent());
        ((TextView) baseViewHolder.getView(R.id.tv_time)).setText(recordBean.getUpdateTime());
        String str = this.mBusinessType.equals("in") ? Marker.ANY_NON_NULL_MARKER : "-";
        ViewUtil.initTextViewWithSpannableString(textView, new String[]{str + recordBean.getAmount() + ConstantUtils.PLACEHOLDER_STR_ONE, this.context.getResources().getString(R.string.common_potato_virtual)}, new String[]{String.valueOf(this.context.getResources().getColor(R.color.colorAccent)), String.valueOf(this.context.getResources().getColor(R.color.text_light))}, new String[]{"16", "12"});
        int indexOf = this.mData.indexOf(recordBean);
        if (this.mData.size() == 0) {
            view.setVisibility(8);
            relativeLayout.setBackgroundResource(R.drawable.shape_task_virtual_record_bg1);
        } else if (indexOf == 0) {
            view.setVisibility(8);
            relativeLayout.setBackgroundResource(R.drawable.shape_task_virtual_record_bg2);
        } else if (indexOf == this.mData.size()) {
            view.setVisibility(8);
            relativeLayout.setBackgroundResource(R.drawable.shape_task_virtual_record_bg3);
        } else {
            view.setVisibility(0);
            relativeLayout.setBackgroundResource(0);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
        setEmptyViewState(0, this.refreshLayout);
        this.context.refresh();
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
        this.context.loadMore();
    }
}
