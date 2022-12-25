package com.one.tomato.p085ui.messge.p086ui;

import android.os.Bundle;
import com.broccoli.p150bh.R;
import com.one.tomato.p085ui.messge.adapter.CommentReplyAdapter;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_feed_back_response)
/* renamed from: com.one.tomato.ui.messge.ui.CommentResponseActivity */
/* loaded from: classes3.dex */
public class CommentResponseActivity extends BaseMessageUActivity {
    private CommentReplyAdapter adapter;

    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity
    protected int getMsgType() {
        return 6;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.p085ui.messge.p086ui.BaseMessageUActivity, com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.string_comment_response);
        this.rightTV.setVisibility(0);
        this.rightTV.setText(R.string.my_message_clear);
        this.rightTV.setTextColor(this.mContext.getResources().getColor(R.color.text_999ead));
        this.adapter = new CommentReplyAdapter(this, 0, this.recyclerView, this);
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
