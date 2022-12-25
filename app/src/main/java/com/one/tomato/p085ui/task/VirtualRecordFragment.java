package com.one.tomato.p085ui.task;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.broccoli.p150bh.R;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.adapter.VirtualRecordAdapter;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.RecordBean;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment;
import com.one.tomato.utils.DBUtil;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import java.util.Collection;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.fragment_virtual_record)
/* renamed from: com.one.tomato.ui.task.VirtualRecordFragment */
/* loaded from: classes3.dex */
public class VirtualRecordFragment extends BaseRecyclerViewFragment {
    private VirtualRecordAdapter virtualRecordAdapter;
    private View view = null;
    private String mBusinessType = "";
    private String mUrl = "";

    public static VirtualRecordFragment getInstance(String str, String str2) {
        VirtualRecordFragment virtualRecordFragment = new VirtualRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", str);
        bundle.putString("business", str2);
        virtualRecordFragment.setArguments(bundle);
        return virtualRecordFragment;
    }

    @Override // com.one.tomato.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mUrl = arguments.getString("url");
            this.mBusinessType = arguments.getString("business");
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment, com.one.tomato.base.BaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = super.onCreateView(layoutInflater, viewGroup, bundle);
        if ("in".equals(this.mBusinessType)) {
            setUserVisibleHint(true);
        }
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        initView();
    }

    private void initView() {
        this.refreshLayout.autoRefresh(100);
        this.virtualRecordAdapter = new VirtualRecordAdapter(this, this.recyclerView, this.mBusinessType, this.refreshLayout);
        this.recyclerView.setAdapter(this.virtualRecordAdapter);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void refresh() {
        super.refresh();
        getValues(1);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void loadMore() {
        super.loadMore();
        getValues(2);
    }

    private void getValues(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), this.mUrl);
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        if ("in".equals(this.mBusinessType)) {
            tomatoParams.addParameter("type", 2);
        } else if ("out".equals(this.mBusinessType)) {
            tomatoParams.addParameter("type", 1);
        }
        tomatoParams.get(new TomatoCallback(this, 1, new TypeToken<ArrayList<RecordBean>>(this) { // from class: com.one.tomato.ui.task.VirtualRecordFragment.1
        }.getType(), i));
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what != 1) {
            return;
        }
        updateData(message.arg1, (ArrayList) baseModel.obj);
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what == 1) {
            if (message.arg1 == 1) {
                this.refreshLayout.mo6481finishRefresh();
            } else {
                this.virtualRecordAdapter.loadMoreFail();
                this.virtualRecordAdapter.setEmptyViewState(1, this.refreshLayout);
            }
            if (this.virtualRecordAdapter.getData().size() == 0) {
                this.virtualRecordAdapter.setEmptyViewState(1, this.refreshLayout);
            }
        }
        return true;
    }

    private void updateData(int i, ArrayList<RecordBean> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.virtualRecordAdapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.virtualRecordAdapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.virtualRecordAdapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.virtualRecordAdapter.addData((Collection) arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.virtualRecordAdapter.loadMoreComplete();
        } else {
            this.virtualRecordAdapter.loadMoreEnd();
        }
    }
}
