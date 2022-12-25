package com.one.tomato.p085ui.messge.p086ui;

import android.os.Bundle;
import com.broccoli.p150bh.R;
import com.one.tomato.p085ui.messge.adapter.PostReviewMessageAdapter;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_post_review_message)
/* renamed from: com.one.tomato.ui.messge.ui.PostReviewMessageActivity */
/* loaded from: classes3.dex */
public class PostReviewMessageActivity extends BaseMessageUActivity {
    private PostReviewMessageAdapter adapter;
    private int type;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity, com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.type = getIntent().getIntExtra("post_or_weigui", 0);
        int i = this.type;
        if (i == 1) {
            this.titleTV.setText(AppUtil.getString(R.string.string_post_review));
        } else if (i == 2) {
            this.titleTV.setText(AppUtil.getString(R.string.string_weigui_and_chufa));
        } else if (i == 9) {
            this.titleTV.setText(AppUtil.getString(R.string.up_message_notify_title));
        } else if (i == 10) {
            this.titleTV.setText(AppUtil.getString(R.string.message_review_title));
        } else if (i == 3) {
            this.titleTV.setText(AppUtil.getString(R.string.message_reward_title));
        }
        this.rightTV.setVisibility(0);
        this.rightTV.setText(R.string.my_message_clear);
        this.rightTV.setTextColor(this.mContext.getResources().getColor(R.color.text_999ead));
        this.adapter = new PostReviewMessageAdapter(this, R.layout.item_post_revicew, this.recyclerView, this.refreshLayout);
        this.adapter.setType(this.type);
        initView();
    }

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity
    protected BaseRecyclerViewAdapter setAdapter() {
        return this.adapter;
    }

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity
    protected int getMsgType() {
        return this.type;
    }

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity, com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        super.refresh();
    }

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity, com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        super.loadMore();
    }
}
