package com.one.tomato.p085ui.income;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.constants.Constants;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.CashRecodeListBean;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.recharge.AlipayUtils;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.fragment_cash_recode_list)
/* renamed from: com.one.tomato.ui.income.CashListFragment */
/* loaded from: classes3.dex */
public class CashListFragment extends BaseRecyclerViewFragment {
    private int accountType = 1;
    private BaseRecyclerViewAdapter<CashRecodeListBean> cashAdapter;
    private View view;

    public static CashListFragment getInstance(int i) {
        CashListFragment cashListFragment = new CashListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("accountType", i);
        cashListFragment.setArguments(bundle);
        return cashListFragment;
    }

    @Override // com.one.tomato.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment, com.one.tomato.base.BaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = super.onCreateView(layoutInflater, viewGroup, bundle);
        setUserVisibleHint(true);
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        this.refreshLayout.autoRefresh(100);
        initAdapter();
        this.accountType = getArguments().getInt("accountType");
    }

    private void initAdapter() {
        this.cashAdapter = new BaseRecyclerViewAdapter<CashRecodeListBean>(this.mContext, R.layout.item_cash_recode, this.recyclerView) { // from class: com.one.tomato.ui.income.CashListFragment.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, CashRecodeListBean cashRecodeListBean) {
                super.convert(baseViewHolder, (BaseViewHolder) cashRecodeListBean);
                ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_item_way_icon);
                TextView textView = (TextView) baseViewHolder.getView(R.id.tv_item_cash_dis);
                TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_item_cash_account);
                TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_item_cash_date);
                TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_item_cash_amount);
                TextView textView5 = (TextView) baseViewHolder.getView(R.id.tv_item_status);
                String hideAlipayAccount = AlipayUtils.getInstance().hideAlipayAccount(cashRecodeListBean.getAlipayAccount());
                String hideAlipayName = AlipayUtils.getInstance().hideAlipayName(cashRecodeListBean.getAlipayName());
                String hideBankAccount = AppUtil.hideBankAccount(cashRecodeListBean.getBankAccount());
                String bankName = cashRecodeListBean.getBankName();
                String hideAlipayName2 = AlipayUtils.getInstance().hideAlipayName(cashRecodeListBean.getCardName());
                int withdrawType = cashRecodeListBean.getWithdrawType();
                if (withdrawType == 1) {
                    imageView.setImageResource(R.drawable.recharge_select_alipay);
                    textView.setText(AppUtil.getString(R.string.cash_prompt_to, AppUtil.getString(R.string.cash_mode_alipay)));
                    textView2.setText("[" + hideAlipayAccount + ConstantUtils.PLACEHOLDER_STR_ONE + hideAlipayName + "]");
                } else if (withdrawType == 2) {
                    imageView.setImageResource(R.drawable.recharge_select_union);
                    textView.setText(AppUtil.getString(R.string.cash_prompt_to, bankName));
                    textView2.setText("[" + hideBankAccount + ConstantUtils.PLACEHOLDER_STR_ONE + hideAlipayName2 + "]");
                }
                textView3.setText(cashRecodeListBean.getCreateTime());
                textView4.setText(String.valueOf(cashRecodeListBean.getAmount()));
                textView4.setTypeface(ViewUtil.getNumFontTypeface(this.mContext));
                textView5.setText(Constants.OrderStatus.getValueByKey(cashRecodeListBean.getStatus()));
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                CashProgressDetailActivity.startActivity(CashListFragment.this.getActivity(), getData().get(i));
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                setEmptyViewState(0, ((BaseRecyclerViewFragment) CashListFragment.this).refreshLayout);
                CashListFragment.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                CashListFragment.this.loadMore();
            }
        };
        this.recyclerView.setAdapter(this.cashAdapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void loadMore() {
        getListFromServer(2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void refresh() {
        getListFromServer(1);
    }

    public void getListFromServer(int i) {
        if (1 == i) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/withdraw/list");
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.addParameter("memberType", Integer.valueOf(this.accountType));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 2001, new TypeToken<ArrayList<CashRecodeListBean>>(this) { // from class: com.one.tomato.ui.income.CashListFragment.2
        }.getType(), i));
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what != 2001) {
            return;
        }
        updateData(message.arg1, (ArrayList) baseModel.obj);
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what != 2001) {
            return false;
        }
        if (message.arg1 == 1) {
            this.refreshLayout.mo6481finishRefresh();
        } else {
            this.cashAdapter.loadMoreFail();
            this.cashAdapter.setEmptyViewState(1, this.refreshLayout);
        }
        if (this.cashAdapter.getData().size() != 0) {
            return true;
        }
        this.cashAdapter.setEmptyViewState(1, this.refreshLayout);
        return true;
    }

    private void updateData(int i, ArrayList<CashRecodeListBean> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.cashAdapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.cashAdapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.cashAdapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.cashAdapter.addData(arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.cashAdapter.loadMoreComplete();
        } else {
            this.cashAdapter.loadMoreEnd();
        }
    }
}
