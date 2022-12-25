package com.one.tomato.p085ui.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.dialog.DeleMsgDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.InteractiveSysBean;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_feedback_history)
/* renamed from: com.one.tomato.ui.feedback.FeedbackHistoryActivity */
/* loaded from: classes3.dex */
public class FeedbackHistoryActivity extends BaseRecyclerViewActivity {
    private BaseRecyclerViewAdapter<InteractiveSysBean.SystemNoticeListBean> adapter;
    private DeleMsgDialog deleMsgDialog = null;
    private int myPostion = 1000;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, FeedbackHistoryActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.refreshLayout.autoRefresh(100);
        initTitle();
        recyclerAdpterUpdate();
    }

    private void initTitle() {
        initTitleBar();
        this.titleTV.setText(R.string.feedback_history);
        this.deleMsgDialog = new DeleMsgDialog(this);
        this.deleMsgDialog.setMessgeListener(new DeleMsgDialog.onSetMessgeListener(this) { // from class: com.one.tomato.ui.feedback.FeedbackHistoryActivity.1
            @Override // com.one.tomato.dialog.DeleMsgDialog.onSetMessgeListener
            public void onsetTitle(TextView textView) {
                textView.setText(R.string.feedback_history_clear);
            }

            @Override // com.one.tomato.dialog.DeleMsgDialog.onSetMessgeListener
            public void onsetContent(TextView textView) {
                textView.setText(R.string.feedback_history_clear_tip);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.one.tomato.ui.feedback.FeedbackHistoryActivity$2 */
    /* loaded from: classes3.dex */
    public class C27672 extends BaseRecyclerViewAdapter<InteractiveSysBean.SystemNoticeListBean> {
        C27672(Context context, int i, RecyclerView recyclerView) {
            super(context, i, recyclerView);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, final InteractiveSysBean.SystemNoticeListBean systemNoticeListBean) {
            String string;
            String str;
            int i;
            int i2;
            String string2;
            super.convert(baseViewHolder, (BaseViewHolder) systemNoticeListBean);
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.inerac_icon_i);
            TextView textView = (TextView) baseViewHolder.getView(R.id.inerac_title_t);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.inerac_time_t);
            TextView textView3 = (TextView) baseViewHolder.getView(R.id.inerac_content_t);
            TextView textView4 = (TextView) baseViewHolder.getView(R.id.inerac_repaly_t);
            View view = baseViewHolder.getView(R.id.msg_item_gb);
            if (systemNoticeListBean.getType().equals("1")) {
                str = AppUtil.getString(R.string.feedback_history_title1);
                i = 8;
                i2 = R.drawable.icon_yijian_i;
            } else if (systemNoticeListBean.getType().equals("2")) {
                if (TextUtils.isEmpty(systemNoticeListBean.getReply())) {
                    string2 = AppUtil.getString(R.string.feedback_history_title2);
                } else {
                    string2 = AppUtil.getString(R.string.feedback_history_title3);
                }
                str = string2;
                i = 0;
                i2 = R.drawable.icon_tongzhi_i;
            } else {
                if (TextUtils.isEmpty(systemNoticeListBean.getReply())) {
                    string = AppUtil.getString(R.string.feedback_history_title4);
                } else {
                    string = AppUtil.getString(R.string.feedback_history_title5);
                }
                str = string;
                i = 0;
                i2 = R.drawable.icon_fankui_i;
            }
            textView4.setVisibility(i);
            if (systemNoticeListBean.getReply().equals("")) {
                textView4.setVisibility(8);
            } else {
                textView4.setVisibility(0);
            }
            textView4.setText(AppUtil.getString(R.string.feedback_history_reply, systemNoticeListBean.getReply()));
            textView.setText(str);
            textView3.setText(systemNoticeListBean.getNotice_content());
            textView2.setText(systemNoticeListBean.getCreate_time());
            imageView.setImageResource(i2);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.feedback.FeedbackHistoryActivity.2.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (systemNoticeListBean.getType().equals("1")) {
                        FeedbackSystemMsgDetailActivity.startActivity(((BaseQuickAdapter) C27672.this).mContext, systemNoticeListBean);
                    } else {
                        FeedbackHistoryDetailActivity.startActivity(((BaseQuickAdapter) C27672.this).mContext, systemNoticeListBean);
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.one.tomato.ui.feedback.FeedbackHistoryActivity.2.2
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                    FeedbackHistoryActivity.this.deleMsgDialog.show();
                    FeedbackHistoryActivity.this.deleMsgDialog.setYesOnclickListener(new DeleMsgDialog.onYesOnclickListener() { // from class: com.one.tomato.ui.feedback.FeedbackHistoryActivity.2.2.1
                        @Override // com.one.tomato.dialog.DeleMsgDialog.onYesOnclickListener
                        public void onYesClick() {
                            FeedbackHistoryActivity feedbackHistoryActivity = FeedbackHistoryActivity.this;
                            feedbackHistoryActivity.myPostion = feedbackHistoryActivity.adapter.getData().indexOf(systemNoticeListBean);
                            View$OnLongClickListenerC27692 view$OnLongClickListenerC27692 = View$OnLongClickListenerC27692.this;
                            FeedbackHistoryActivity feedbackHistoryActivity2 = FeedbackHistoryActivity.this;
                            String type = systemNoticeListBean.getType();
                            feedbackHistoryActivity2.requestDelete(type, systemNoticeListBean.getNotice_id() + "");
                            FeedbackHistoryActivity.this.deleMsgDialog.dismiss();
                        }
                    });
                    return false;
                }
            });
        }

        @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
        public void onEmptyRefresh(int i) {
            FeedbackHistoryActivity.this.refresh();
        }

        @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
        public void onLoadMore() {
            FeedbackHistoryActivity.this.loadMore();
        }
    }

    private void recyclerAdpterUpdate() {
        this.adapter = new C27672(this.mContext, R.layout.item_ineractive, this.recyclerView);
        this.recyclerView.setAdapter(this.adapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        super.refresh();
        requestData(1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        super.loadMore();
        requestData(2);
    }

    private void requestData(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/newFeedback/hisList");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 1, InteractiveSysBean.class, i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestDelete(String str, String str2) {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/newFeedback/delete");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("type", str);
        tomatoParams.addParameter(DatabaseFieldConfigLoader.FIELD_NAME_ID, str2);
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 2, InteractiveSysBean.class));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            updateData(message.arg1, ((InteractiveSysBean) baseModel.obj).getSystemNoticeList());
        } else if (i != 2) {
        } else {
            ToastUtil.showCenterToast(baseModel.message);
            this.adapter.remove(this.myPostion);
            this.adapter.notifyDataSetChanged();
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        int i = message.what;
        if (i == 1) {
            ToastUtil.showCenterToast((int) R.string.request_fail);
            return false;
        } else if (i != 2) {
            return false;
        } else {
            ToastUtil.showCenterToast((int) R.string.request_fail);
            return false;
        }
    }

    private void updateData(int i, ArrayList<InteractiveSysBean.SystemNoticeListBean> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.adapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.adapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.adapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.adapter.addData(arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.adapter.loadMoreComplete();
        } else {
            this.adapter.loadMoreEnd();
        }
    }
}
