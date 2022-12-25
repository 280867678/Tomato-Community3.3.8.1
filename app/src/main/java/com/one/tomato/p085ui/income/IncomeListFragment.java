package com.one.tomato.p085ui.income;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.IncomeRecodeListBean;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ViewUtil;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_income_recode_list)
/* renamed from: com.one.tomato.ui.income.IncomeListFragment */
/* loaded from: classes3.dex */
public class IncomeListFragment extends BaseRecyclerViewFragment {
    private BaseRecyclerViewAdapter<IncomeRecodeListBean> incomeAdapter;
    @ViewInject(R.id.tv_income_all)
    private TextView tv_income_all;
    @ViewInject(R.id.tv_income_in)
    private TextView tv_income_in;
    @ViewInject(R.id.tv_income_out)
    private TextView tv_income_out;
    private View view;
    private int curSelectItem = 0;
    private int accountType = 1;

    public static IncomeListFragment getInstance(int i) {
        IncomeListFragment incomeListFragment = new IncomeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("accountType", i);
        incomeListFragment.setArguments(bundle);
        return incomeListFragment;
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
        this.incomeAdapter = new BaseRecyclerViewAdapter<IncomeRecodeListBean>(this.mContext, R.layout.item_income_recode, this.recyclerView) { // from class: com.one.tomato.ui.income.IncomeListFragment.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, IncomeRecodeListBean incomeRecodeListBean) {
                super.convert(baseViewHolder, (BaseViewHolder) incomeRecodeListBean);
                ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_item_income_icon);
                TextView textView = (TextView) baseViewHolder.getView(R.id.tv_item_title_name);
                TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_item_gift_date);
                TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_item_income_count);
                TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_item_imcome_unit);
                TextView textView5 = (TextView) baseViewHolder.getView(R.id.tv_item_cost_unit);
                if (1 == incomeRecodeListBean.getType()) {
                    imageView.setImageResource(R.drawable.icon_income_item_cost);
                    textView4.setVisibility(8);
                    textView5.setVisibility(0);
                } else if (2 == incomeRecodeListBean.getType()) {
                    imageView.setImageResource(R.drawable.icon_income_item_receive);
                    textView4.setVisibility(0);
                    textView5.setVisibility(8);
                }
                textView.setText(incomeRecodeListBean.getContent());
                textView2.setText(incomeRecodeListBean.getCreateTime());
                if (IncomeListFragment.this.accountType == 3) {
                    textView3.setText(incomeRecodeListBean.getAmount());
                } else {
                    textView3.setText(FormatUtil.formatTomato2RMB(incomeRecodeListBean.getAmount()));
                }
                textView3.setTypeface(ViewUtil.getNumFontTypeface(this.mContext));
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                IncomeDetailActivity.startActivity(IncomeListFragment.this.getActivity(), getData().get(i), IncomeListFragment.this.accountType);
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                IncomeListFragment.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                IncomeListFragment.this.loadMore();
            }
        };
        this.recyclerView.setAdapter(this.incomeAdapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void loadMore() {
        getListFromServer(2, this.curSelectItem);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void refresh() {
        getListFromServer(1, this.curSelectItem);
    }

    public void getListFromServer(int i, int i2) {
        if (1 == i) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/withdraw/tomato/account/list");
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("memberType", Integer.valueOf(this.accountType));
        tomatoParams.addParameter("type", Integer.valueOf(i2));
        tomatoParams.post(new TomatoCallback(this, 2001, new TypeToken<ArrayList<IncomeRecodeListBean>>(this) { // from class: com.one.tomato.ui.income.IncomeListFragment.2
        }.getType(), i));
    }

    @Event({R.id.tv_income_all, R.id.tv_income_in, R.id.tv_income_out})
    private void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_income_all) {
            this.tv_income_all.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            this.tv_income_all.setTextColor(getResources().getColor(R.color.white));
            this.tv_income_in.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_income_in.setTextColor(getResources().getColor(R.color.text_646464));
            this.tv_income_out.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_income_out.setTextColor(getResources().getColor(R.color.text_646464));
            this.curSelectItem = 0;
            this.refreshLayout.autoRefresh();
        } else if (id == R.id.tv_income_in) {
            this.tv_income_in.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            this.tv_income_in.setTextColor(getResources().getColor(R.color.white));
            this.tv_income_all.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_income_all.setTextColor(getResources().getColor(R.color.text_646464));
            this.tv_income_out.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_income_out.setTextColor(getResources().getColor(R.color.text_646464));
            this.curSelectItem = 2;
            this.refreshLayout.autoRefresh();
        } else if (id != R.id.tv_income_out) {
        } else {
            this.tv_income_out.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            this.tv_income_out.setTextColor(getResources().getColor(R.color.white));
            this.tv_income_all.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_income_all.setTextColor(getResources().getColor(R.color.text_646464));
            this.tv_income_in.setBackgroundResource(R.drawable.common_shape_solid_corner30_white);
            this.tv_income_in.setTextColor(getResources().getColor(R.color.text_646464));
            this.curSelectItem = 1;
            this.refreshLayout.autoRefresh();
        }
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
            this.incomeAdapter.loadMoreFail();
        }
        if (this.incomeAdapter.getData().size() != 0) {
            return true;
        }
        this.incomeAdapter.setEmptyViewState(1, this.refreshLayout);
        return true;
    }

    private void updateData(int i, ArrayList<IncomeRecodeListBean> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.incomeAdapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.incomeAdapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.incomeAdapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.incomeAdapter.addData(arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.incomeAdapter.loadMoreComplete();
        } else {
            this.incomeAdapter.loadMoreEnd();
        }
    }
}
