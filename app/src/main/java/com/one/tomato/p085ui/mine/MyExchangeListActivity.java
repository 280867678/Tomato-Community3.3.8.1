package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ExchangeList;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ViewUtil;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import org.slf4j.Marker;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_my_exchange_list)
/* renamed from: com.one.tomato.ui.mine.MyExchangeListActivity */
/* loaded from: classes3.dex */
public class MyExchangeListActivity extends BaseRecyclerViewActivity {
    private BaseRecyclerViewAdapter<ExchangeList> adapter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MyExchangeListActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.exchange_history);
        this.refreshLayout.autoRefresh(100);
        initAdapter();
    }

    private void initAdapter() {
        this.adapter = new BaseRecyclerViewAdapter<ExchangeList>(this, R.layout.item_exchange, this.recyclerView) { // from class: com.one.tomato.ui.mine.MyExchangeListActivity.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ExchangeList exchangeList) {
                String string;
                super.convert(baseViewHolder, (BaseViewHolder) exchangeList);
                TextView textView = (TextView) baseViewHolder.getView(R.id.tv_result);
                ((TextView) baseViewHolder.getView(R.id.tv_code)).setText(AppUtil.getString(R.string.exchange_code) + ConstantUtils.PLACEHOLDER_STR_ONE + exchangeList.getRedeemCode());
                ((TextView) baseViewHolder.getView(R.id.tv_time)).setText(exchangeList.getCreateTime());
                int exchangeType = exchangeList.getExchangeType();
                if (exchangeType == 1) {
                    string = AppUtil.getString(R.string.common_potato_virtual);
                } else if (exchangeType == 2) {
                    string = AppUtil.getString(R.string.common_potato_currency);
                } else if (exchangeType == 3) {
                    string = AppUtil.getString(R.string.common_potato_expire);
                } else {
                    string = exchangeType != 4 ? "" : AppUtil.getString(R.string.exchange_vip_days);
                }
                ViewUtil.initTextViewWithSpannableString(textView, new String[]{Marker.ANY_NON_NULL_MARKER + exchangeList.getExchangeNum(), string}, new String[]{String.valueOf(MyExchangeListActivity.this.getResources().getColor(R.color.colorAccent)), String.valueOf(Color.parseColor("#999EAD"))}, new String[]{"16", "12"});
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                MyExchangeListActivity.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                MyExchangeListActivity.this.loadMore();
            }
        };
        this.recyclerView.setAdapter(this.adapter);
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
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/redeem/queryExchangeHistory");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<ExchangeList>>(this) { // from class: com.one.tomato.ui.mine.MyExchangeListActivity.2
        }.getType(), i));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what != 1) {
            return;
        }
        hideWaitingDialog();
        updateData(message.arg1, (ArrayList) baseModel.obj);
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what == 1) {
            if (message.arg1 != 1) {
                this.adapter.loadMoreFail();
            }
            if (this.adapter.getData().size() == 0) {
                this.adapter.setEmptyViewState(1, null);
            }
        }
        return true;
    }

    private void updateData(int i, ArrayList<ExchangeList> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.adapter.setEmptyViewState(2, null);
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
