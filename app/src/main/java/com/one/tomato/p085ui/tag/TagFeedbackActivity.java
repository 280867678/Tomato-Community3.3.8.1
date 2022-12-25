package com.one.tomato.p085ui.tag;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.TagFeedback;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_tag_feedback)
/* renamed from: com.one.tomato.ui.tag.TagFeedbackActivity */
/* loaded from: classes3.dex */
public class TagFeedbackActivity extends BaseRecyclerViewActivity {
    private BaseRecyclerViewAdapter<TagFeedback> adapter;
    private int defaultId = 0;
    private TagFeedback itemBean;
    @ViewInject(R.id.tv_commit)
    private TextView tv_commit;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.tag_feedback_title);
        this.rightTV.setText(R.string.tag_feedback_item_add);
        this.rightTV.setVisibility(0);
        this.rightTV.setTextColor(getResources().getColor(R.color.text_middle));
        initAdapter();
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.tag.TagFeedbackActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TagFeedbackActivity.this.addItem();
            }
        });
        this.tv_commit.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.tag.TagFeedbackActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TagFeedbackActivity.this.commit();
            }
        });
    }

    private void initAdapter() {
        this.adapter = new BaseRecyclerViewAdapter<TagFeedback>(this, R.layout.item_add_tag_feedback, this.recyclerView) { // from class: com.one.tomato.ui.tag.TagFeedbackActivity.3
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, final TagFeedback tagFeedback) {
                super.convert(baseViewHolder, (BaseViewHolder) tagFeedback);
                final TextView textView = (TextView) baseViewHolder.getView(R.id.tv_length_cur);
                EditText editText = (EditText) baseViewHolder.getView(R.id.et_input);
                textView.setText(tagFeedback.getLength() + "");
                if (TextUtils.isEmpty(tagFeedback.getName())) {
                    editText.setText("");
                }
                baseViewHolder.addOnClickListener(R.id.tv_delete);
                editText.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.tag.TagFeedbackActivity.3.1
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                        String trim = editable.toString().trim();
                        int length = trim.length();
                        tagFeedback.setName(trim);
                        TextView textView2 = textView;
                        textView2.setText(length + "");
                        tagFeedback.setName(trim);
                        tagFeedback.setLength(length);
                        TagFeedbackActivity.this.checkIsCommit();
                    }
                });
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (view.getId() != R.id.tv_delete) {
                    return;
                }
                TagFeedbackActivity.this.deleteItem(i);
            }
        };
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setEnableLoadMore(false);
        addItem();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addItem() {
        if (this.adapter.getData().size() >= 5) {
            return;
        }
        this.itemBean = new TagFeedback(this.defaultId, "");
        this.defaultId++;
        this.adapter.addData(0, (int) this.itemBean);
        AppUtil.recyclerViewScroll(this.recyclerView, 0, 0, 500);
        checkIsCommit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteItem(int i) {
        this.adapter.remove(i);
        checkIsCommit();
        if (this.adapter.getData().isEmpty()) {
            addItem();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkIsCommit() {
        boolean z;
        int i = 0;
        while (true) {
            if (i >= this.adapter.getData().size()) {
                z = false;
                break;
            } else if (!TextUtils.isEmpty(this.adapter.getData().get(i).getName())) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            this.tv_commit.setEnabled(true);
            this.tv_commit.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            return;
        }
        this.tv_commit.setEnabled(false);
        this.tv_commit.setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commit() {
        hideKeyBoard(this);
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/tag/missing/send");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.adapter.getData().size(); i++) {
            if (!TextUtils.isEmpty(this.adapter.getData().get(i).getName())) {
                sb.append(this.adapter.getData().get(i).getName());
                sb.append(",");
            }
        }
        if (sb.toString().endsWith(",")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        tomatoParams.addParameter("tags", sb.toString());
        tomatoParams.post(new TomatoCallback(this, 1));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        ToastUtil.showCenterToast((int) R.string.tag_feedback_success);
        finish();
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        return super.handleResponseError(message);
    }
}
