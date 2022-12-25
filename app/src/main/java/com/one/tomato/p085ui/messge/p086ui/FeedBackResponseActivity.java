package com.one.tomato.p085ui.messge.p086ui;

import android.os.Bundle;
import com.broccoli.p150bh.R;
import com.one.tomato.p085ui.messge.adapter.FeedBackReplyAdapter;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_feed_back_response)
/* renamed from: com.one.tomato.ui.messge.ui.FeedBackResponseActivity */
/* loaded from: classes3.dex */
public class FeedBackResponseActivity extends BaseMessageUActivity {
    private FeedBackReplyAdapter adapter;

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity
    protected int getMsgType() {
        return 4;
    }

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity, com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.feedback_history_title2);
        this.adapter = new FeedBackReplyAdapter(this, 0, this.recyclerView, this);
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
}
