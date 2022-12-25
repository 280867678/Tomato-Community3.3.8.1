package com.one.tomato.p085ui.messge.p086ui;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import com.broccoli.p150bh.R;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.p085ui.messge.bean.PostReviewBean;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import java.util.Collection;

/* renamed from: com.one.tomato.ui.messge.ui.BaseMessageUActivity */
/* loaded from: classes3.dex */
public abstract class BaseMessageUActivity extends BaseRecyclerViewActivity {
    private BaseRecyclerViewAdapter adapter;
    private int msgType;

    protected abstract int getMsgType();

    protected abstract BaseRecyclerViewAdapter setAdapter();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initView() {
        this.adapter = setAdapter();
        this.msgType = getMsgType();
        this.recyclerView.setAdapter(this.adapter);
        this.refreshLayout.autoRefresh(100);
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.messge.ui.BaseMessageUActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (BaseMessageUActivity.this.adapter.getData().isEmpty()) {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.string_no_data_clear));
                    return;
                }
                final CustomAlertDialog customAlertDialog = new CustomAlertDialog(BaseMessageUActivity.this);
                customAlertDialog.setMessage(R.string.string_clear_data_message);
                customAlertDialog.setCancelButton(R.string.common_cancel, new View.OnClickListener(this) { // from class: com.one.tomato.ui.messge.ui.BaseMessageUActivity.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        customAlertDialog.dismiss();
                    }
                });
                customAlertDialog.setConfirmButton(R.string.common_confirm, new View.OnClickListener() { // from class: com.one.tomato.ui.messge.ui.BaseMessageUActivity.1.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        customAlertDialog.dismiss();
                        BaseMessageUActivity.this.clearData();
                    }
                });
            }
        });
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        super.refresh();
        postData(this.msgType, 1);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        super.loadMore();
        postData(this.msgType, 2);
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
            String str = baseModel.data;
            if (TextUtils.isEmpty(str)) {
                return;
            }
            try {
                updateData(message.arg1, (ArrayList) BaseApplication.getGson().fromJson(str, new TypeToken<ArrayList<PostReviewBean.DataBean>>(this) { // from class: com.one.tomato.ui.messge.ui.BaseMessageUActivity.2
                }.getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (i != 2) {
        } else {
            if (baseModel.code == 0) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.string_clear_suceess));
                this.adapter.getData().clear();
                this.adapter.notifyDataSetChanged();
                this.adapter.setEmptyViewState(2, this.refreshLayout);
                this.pageNo = 1;
                return;
            }
            ToastUtil.showCenterToast(AppUtil.getString(R.string.string_clear_fail));
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        int i = message.what;
        if (i == 1) {
            if (message.arg1 == 1) {
                this.refreshLayout.mo6481finishRefresh();
            } else {
                this.adapter.loadMoreFail();
                this.adapter.setEmptyViewState(1, this.refreshLayout);
            }
            if (this.adapter.getData().size() == 0) {
                this.adapter.setEmptyViewState(1, this.refreshLayout);
            }
        } else if (i == 2) {
            ToastUtil.showCenterToast(AppUtil.getString(R.string.string_clear_fail));
        }
        return super.handleResponseError(message);
    }

    private void updateData(int i, ArrayList<PostReviewBean.DataBean> arrayList) {
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
            this.adapter.addData((Collection) arrayList);
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

    private void postData(int i, int i2) {
        if (i2 == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/messageCenter/queryMessageList");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("msgGroup", Integer.valueOf(i));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, 10);
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<PostReviewBean.DataBean>>(this) { // from class: com.one.tomato.ui.messge.ui.BaseMessageUActivity.3
        }.getType(), i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearData() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/messageCenter/clearMessage");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("msgGroup", Integer.valueOf(this.msgType));
        tomatoParams.post(new TomatoCallback(this, 2));
    }
}
