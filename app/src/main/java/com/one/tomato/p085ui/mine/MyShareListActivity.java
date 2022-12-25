package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.SpreadList;
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
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_my_share_list)
/* renamed from: com.one.tomato.ui.mine.MyShareListActivity */
/* loaded from: classes3.dex */
public class MyShareListActivity extends BaseRecyclerViewActivity {
    private BaseRecyclerViewAdapter<SpreadList> memberAdapter;
    private int memberTotal;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MyShareListActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.my_spread_record);
        initAdapter();
        this.refreshLayout.autoRefresh(100);
    }

    private void initAdapter() {
        this.memberAdapter = new BaseRecyclerViewAdapter<SpreadList>(this, R.layout.item_my_spread, this.recyclerView) { // from class: com.one.tomato.ui.mine.MyShareListActivity.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, SpreadList spreadList) {
                super.convert(baseViewHolder, (BaseViewHolder) spreadList);
                ImageLoaderUtil.loadHeadImage(this.mContext, (ImageView) baseViewHolder.getView(R.id.iv_head), new ImageBean(spreadList.getAvatar()));
                ((TextView) baseViewHolder.getView(R.id.tv_nick)).setText(spreadList.getName());
                ((TextView) baseViewHolder.getView(R.id.tv_time)).setText(spreadList.getCreateTimeStr());
                ((TextView) baseViewHolder.getView(R.id.tv_desc)).setText(spreadList.getGiftVipDes());
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                MyShareListActivity.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                MyShareListActivity.this.loadMore();
            }
        };
        this.recyclerView.setAdapter(this.memberAdapter);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        super.refresh();
        getListFromServer(1);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        super.loadMore();
        getListFromServer(2);
    }

    private void getListFromServer(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/share/querySpreadDetailList");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<SpreadList>>(this) { // from class: com.one.tomato.ui.mine.MyShareListActivity.2
        }.getType(), i));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what != 1) {
            return;
        }
        ArrayList<SpreadList> arrayList = (ArrayList) baseModel.obj;
        BaseModel.Page page = baseModel.page;
        if (page != null) {
            this.memberTotal = page.getTotalCount();
        }
        updateData(message.arg1, arrayList);
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what == 1) {
            if (message.arg1 == 1) {
                this.refreshLayout.mo6481finishRefresh();
            } else {
                this.memberAdapter.loadMoreFail();
            }
            if (this.memberAdapter.getData().size() == 0) {
                this.tv_num.setVisibility(8);
                this.memberAdapter.setEmptyViewState(1, this.refreshLayout);
            }
        }
        return super.handleResponseError(message);
    }

    private void updateData(int i, ArrayList<SpreadList> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.tv_num.setVisibility(8);
                this.refreshLayout.mo6481finishRefresh();
                this.memberAdapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.memberAdapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.tv_num.setVisibility(0);
            this.tv_num.setText(AppUtil.getString(R.string.my_spread_invite_num, Integer.valueOf(this.memberTotal)));
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.memberAdapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.memberAdapter.addData(arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.memberAdapter.loadMoreComplete();
        } else {
            this.memberAdapter.loadMoreEnd();
        }
    }
}
