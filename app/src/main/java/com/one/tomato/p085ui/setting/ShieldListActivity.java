package com.one.tomato.p085ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.ShieldList;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_shield_list)
/* renamed from: com.one.tomato.ui.setting.ShieldListActivity */
/* loaded from: classes3.dex */
public class ShieldListActivity extends BaseRecyclerViewActivity {
    private BaseRecyclerViewAdapter<ShieldList> mAdapter;
    private CustomAlertDialog tipDialog;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ShieldListActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.refreshLayout.autoRefresh(100);
        initTitleBar();
        this.titleTV.setText(R.string.shield_list);
        initAdapter();
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter<ShieldList>(this.mContext, R.layout.item_shield_list, this.recyclerView) { // from class: com.one.tomato.ui.setting.ShieldListActivity.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ShieldList shieldList) {
                super.convert(baseViewHolder, (BaseViewHolder) shieldList);
                TextView textView = (TextView) baseViewHolder.getView(R.id.tv_desc);
                TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_cancel);
                baseViewHolder.addOnClickListener(R.id.tv_cancel);
                ImageLoaderUtil.loadHeadImage(this.mContext, (ImageView) baseViewHolder.getView(R.id.iv_head), new ImageBean(shieldList.getAvatar()));
                ((TextView) baseViewHolder.getView(R.id.tv_name)).setText(shieldList.getName());
                if (TextUtils.isEmpty(shieldList.getSignature())) {
                    shieldList.setSignature(AppUtil.getString(R.string.user_no_signature));
                }
                textView.setText(shieldList.getSignature());
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
                ShieldList shieldList = (ShieldList) ShieldListActivity.this.mAdapter.getItem(i);
                if (view.getId() != R.id.tv_cancel) {
                    return;
                }
                ShieldListActivity.this.cancelShield(shieldList.getId(), i);
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                NewMyHomePageActivity.Companion.startActivity(this.mContext, ((ShieldList) ShieldListActivity.this.mAdapter.getItem(i)).getMemberId());
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                ShieldListActivity.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                ShieldListActivity.this.loadMore();
            }
        };
        this.recyclerView.setAdapter(this.mAdapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        super.refresh();
        getListFromServer(1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        super.loadMore();
        getListFromServer(2);
    }

    private void getListFromServer(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/record/shield/user/list");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<ShieldList>>(this) { // from class: com.one.tomato.ui.setting.ShieldListActivity.2
        }.getType(), i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelShield(final int i, final int i2) {
        CustomAlertDialog customAlertDialog = this.tipDialog;
        if (customAlertDialog == null) {
            this.tipDialog = new CustomAlertDialog(this);
            this.tipDialog.setTitle(R.string.common_notify);
            this.tipDialog.setMessage(R.string.shield_cancel_message);
            this.tipDialog.setBottomVerticalLineVisible(true);
            this.tipDialog.setCancelButtonTextColor(R.color.colorAccent);
            this.tipDialog.setCancelButton(R.string.feedback_silder);
        } else {
            customAlertDialog.show();
        }
        this.tipDialog.setConfirmButton(R.string.common_confirm, new View.OnClickListener() { // from class: com.one.tomato.ui.setting.ShieldListActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ShieldListActivity.this.tipDialog.dismiss();
                ShieldListActivity.this.showWaitingDialog();
                TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/record/shield/user/cancel");
                tomatoParams.addParameter(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(i));
                tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
                tomatoParams.post(new TomatoCallback((ResponseObserver) ShieldListActivity.this, 2, (Class) null, i2));
            }
        });
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            updateData(message.arg1, (ArrayList) baseModel.obj);
        } else if (i != 2) {
        } else {
            hideWaitingDialog();
            this.mAdapter.remove(message.arg1);
            if (this.mAdapter.getData().size() != 0) {
                return;
            }
            this.mAdapter.setEmptyViewState(2, this.refreshLayout);
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        int i = message.what;
        if (i == 1) {
            int i2 = message.arg1;
            if (i2 == 1) {
                this.refreshLayout.mo6481finishRefresh();
            } else if (i2 == 2) {
                this.mAdapter.loadMoreFail();
            }
            if (this.mAdapter.getData().size() == 0) {
                this.mAdapter.setEmptyViewState(1, this.refreshLayout);
            }
        } else if (i == 2) {
            hideWaitingDialog();
        }
        return true;
    }

    private void updateData(int i, ArrayList<ShieldList> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.size() == 0) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.mAdapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.mAdapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.mAdapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.mAdapter.addData(arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.mAdapter.loadMoreComplete();
        } else {
            this.mAdapter.loadMoreEnd();
        }
    }
}
