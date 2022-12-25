package com.one.tomato.p085ui.recharge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.adapter.RechargeRecordAdapter;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.RechargeRecord;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.utils.DBUtil;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import java.util.Collection;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_recharge_record)
/* renamed from: com.one.tomato.ui.recharge.RechargeRecordActivity */
/* loaded from: classes3.dex */
public class RechargeRecordActivity extends BaseRecyclerViewActivity {
    private int businessType;
    private int curSelectItem = 0;
    private RechargeRecordAdapter recordAdapter;
    @ViewInject(R.id.tv_recharge_all)
    private TextView tv_recharge_all;
    @ViewInject(R.id.tv_recharge_in)
    private TextView tv_recharge_in;
    @ViewInject(R.id.tv_recharge_out)
    private TextView tv_recharge_out;

    public static void startActivity(Context context, int i) {
        Intent intent = new Intent();
        intent.setClass(context, RechargeRecordActivity.class);
        intent.putExtra("businessType", i);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.businessType = getIntent().getExtras().getInt("businessType");
        this.titleTV.setText(R.string.recharge_list_detail);
        this.refreshLayout.autoRefresh(100);
        this.recordAdapter = new RechargeRecordAdapter(this, this.recyclerView, this.refreshLayout);
        this.recyclerView.setAdapter(this.recordAdapter);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        super.refresh();
        getRecord(1);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        super.loadMore();
        getRecord(2);
    }

    @Event({R.id.tv_recharge_all, R.id.tv_recharge_in, R.id.tv_recharge_out})
    private void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_recharge_all) {
            this.tv_recharge_all.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            this.tv_recharge_all.setTextColor(getResources().getColor(R.color.white));
            this.tv_recharge_in.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_recharge_in.setTextColor(getResources().getColor(R.color.text_646464));
            this.tv_recharge_out.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_recharge_out.setTextColor(getResources().getColor(R.color.text_646464));
            this.curSelectItem = 0;
            this.refreshLayout.autoRefresh();
        } else if (id == R.id.tv_recharge_in) {
            this.tv_recharge_in.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            this.tv_recharge_in.setTextColor(getResources().getColor(R.color.white));
            this.tv_recharge_all.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_recharge_all.setTextColor(getResources().getColor(R.color.text_646464));
            this.tv_recharge_out.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_recharge_out.setTextColor(getResources().getColor(R.color.text_646464));
            this.curSelectItem = 2;
            this.refreshLayout.autoRefresh();
        } else if (id != R.id.tv_recharge_out) {
        } else {
            this.tv_recharge_out.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            this.tv_recharge_out.setTextColor(getResources().getColor(R.color.white));
            this.tv_recharge_all.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_recharge_all.setTextColor(getResources().getColor(R.color.text_646464));
            this.tv_recharge_in.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_recharge_in.setTextColor(getResources().getColor(R.color.text_646464));
            this.curSelectItem = 1;
            this.refreshLayout.autoRefresh();
        }
    }

    private void getRecord(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/payment/queryTomatoCoinDetail");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        int i2 = this.curSelectItem;
        if (i2 != 0) {
            tomatoParams.addParameter("type", Integer.valueOf(i2));
        }
        tomatoParams.addParameter("queryType", Integer.valueOf(this.businessType));
        tomatoParams.get(new TomatoCallback(this, 1, new TypeToken<ArrayList<RechargeRecord>>(this) { // from class: com.one.tomato.ui.recharge.RechargeRecordActivity.1
        }.getType(), i));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what != 1) {
            return;
        }
        updateData(message.arg1, (ArrayList) baseModel.obj);
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what == 1) {
            if (message.arg1 == 1) {
                this.refreshLayout.mo6481finishRefresh();
            } else {
                this.recordAdapter.loadMoreFail();
                this.recordAdapter.setEmptyViewState(1, this.refreshLayout);
            }
            if (this.recordAdapter.getData().size() == 0) {
                this.recordAdapter.setEmptyViewState(1, this.refreshLayout);
            }
        }
        return true;
    }

    private void updateData(int i, ArrayList<RechargeRecord> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.recordAdapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.recordAdapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.recordAdapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.recordAdapter.addData((Collection) arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.recordAdapter.loadMoreComplete();
        } else {
            this.recordAdapter.loadMoreEnd();
        }
    }
}
