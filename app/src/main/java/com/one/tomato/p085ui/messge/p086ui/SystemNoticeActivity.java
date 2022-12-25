package com.one.tomato.p085ui.messge.p086ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.p085ui.messge.bean.PostReviewBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_feed_back_response)
/* renamed from: com.one.tomato.ui.messge.ui.SystemNoticeActivity */
/* loaded from: classes3.dex */
public class SystemNoticeActivity extends BaseMessageUActivity {
    private BaseRecyclerViewAdapter<PostReviewBean.DataBean> adapter;

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity
    protected int getMsgType() {
        return 8;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity, com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.string_system_gonggao);
        this.rightTV.setVisibility(0);
        this.rightTV.setText(R.string.my_message_clear);
        this.rightTV.setTextColor(this.mContext.getResources().getColor(R.color.text_999ead));
        getAdapter();
        initView();
    }

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity
    protected BaseRecyclerViewAdapter setAdapter() {
        return this.adapter;
    }

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity, com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        super.refresh();
    }

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity, com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        super.loadMore();
    }

    private void getAdapter() {
        this.adapter = new BaseRecyclerViewAdapter<PostReviewBean.DataBean>(this, R.layout.item_sysyteam_notice, this.recyclerView) { // from class: com.one.tomato.ui.messge.ui.SystemNoticeActivity.1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                SystemNoticeActivity.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                SystemNoticeActivity.this.loadMore();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, PostReviewBean.DataBean dataBean) {
                super.convert(baseViewHolder, (BaseViewHolder) dataBean);
                ((TextView) baseViewHolder.getView(R.id.text_title)).setText(dataBean.getMsgTitle());
                ((TextView) baseViewHolder.getView(R.id.text_time)).setText(dataBean.getCreateTime());
                ((TextView) baseViewHolder.getView(R.id.text_content)).setText(dataBean.getMsgContent());
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                Intent intent = new Intent(this.mContext, SystemNoticeDetailAct.class);
                intent.putExtra("system_notice", getData().get(i));
                this.mContext.startActivity(intent);
            }
        };
    }
}
