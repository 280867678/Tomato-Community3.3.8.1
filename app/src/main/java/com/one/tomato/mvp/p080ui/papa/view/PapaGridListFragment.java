package com.one.tomato.mvp.p080ui.papa.view;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.adapter.PapaGridListAdapter;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseGridLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment;
import com.one.tomato.utils.DBUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import java.util.Collection;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_papa_grid_list)
/* renamed from: com.one.tomato.mvp.ui.papa.view.PapaGridListFragment */
/* loaded from: classes3.dex */
public class PapaGridListFragment extends BaseRecyclerViewFragment {
    private String businessType;
    private int groupId;
    @ViewInject(R.id.ll_sort)
    private LinearLayout ll_sort;
    private PapaGridListAdapter papaGridListAdapter;
    private String path;
    private int personMemberId;
    private String searchKey;
    private String searchSort = AopConstants.TIME_KEY;
    private int spanCount;
    @ViewInject(R.id.text_hot)
    private TextView text_hot;
    @ViewInject(R.id.text_time)
    private TextView text_time;
    private View view;

    public static PapaGridListFragment getInstance(String str, String str2) {
        PapaGridListFragment papaGridListFragment = new PapaGridListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", str);
        bundle.putString("business", str2);
        papaGridListFragment.setArguments(bundle);
        return papaGridListFragment;
    }

    @Override // com.one.tomato.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.path = arguments.getString("path");
            this.businessType = arguments.getString("business");
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment, com.one.tomato.base.BaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = super.onCreateView(layoutInflater, viewGroup, bundle);
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseFragment
    public void onLazyLoad() {
        char c;
        super.onLazyLoad();
        String str = this.businessType;
        int hashCode = str.hashCode();
        if (hashCode == -1110540878) {
            if (str.equals("circle_all")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 166751952) {
            if (hashCode == 1502545125 && str.equals("papa_search")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("papa_mine")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            this.spanCount = 3;
        } else if (c == 1) {
            this.spanCount = 3;
        } else if (c == 2) {
            this.spanCount = 2;
            this.ll_sort.setVisibility(0);
            initSortView();
        }
        this.refreshLayout.autoRefresh(100);
        initAdapter();
    }

    private void initAdapter() {
        this.papaGridListAdapter = new PapaGridListAdapter(this, this.recyclerView, this.businessType);
        this.recyclerView.setLayoutManager(new BaseGridLayoutManager(this.mContext, this.spanCount));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.mContext, 1);
        dividerItemDecoration.setDrawable(this.mContext.getResources().getDrawable(R.drawable.common_shape_line_white));
        this.recyclerView.addItemDecoration(dividerItemDecoration);
        this.recyclerView.setAdapter(this.papaGridListAdapter);
    }

    public void setGroupId(int i) {
        this.groupId = i;
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void refresh() {
        super.refresh();
        getListFromServer(1);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void loadMore() {
        super.loadMore();
        getListFromServer(2);
    }

    private void initSortView() {
        this.text_time.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PapaGridListFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PapaGridListFragment papaGridListFragment = PapaGridListFragment.this;
                papaGridListFragment.switchSortView(AopConstants.TIME_KEY, papaGridListFragment.text_time, PapaGridListFragment.this.text_hot);
            }
        });
        this.text_hot.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PapaGridListFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PapaGridListFragment papaGridListFragment = PapaGridListFragment.this;
                papaGridListFragment.switchSortView("score", papaGridListFragment.text_hot, PapaGridListFragment.this.text_time);
            }
        });
    }

    public void searchFromServer(String str) {
        searchClear();
        SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.mo6481finishRefresh();
        }
        PapaGridListAdapter papaGridListAdapter = this.papaGridListAdapter;
        if (papaGridListAdapter != null) {
            papaGridListAdapter.loadMoreEnd();
        }
        this.searchKey = str;
        this.isLazyLoad = false;
    }

    private void searchClear() {
        PapaGridListAdapter papaGridListAdapter = this.papaGridListAdapter;
        if (papaGridListAdapter != null) {
            papaGridListAdapter.getData().clear();
            this.papaGridListAdapter.notifyDataSetChanged();
        }
        if (this.isViewCreated) {
            this.searchSort = AopConstants.TIME_KEY;
            this.text_time.setTextColor(this.mContext.getResources().getColor(R.color.colorAccent));
            this.text_time.setBackgroundResource(R.drawable.shape_post_search_sort);
            this.text_hot.setTextColor(this.mContext.getResources().getColor(R.color.text_light));
            this.text_hot.setBackgroundResource(R.drawable.common_shape_solid_corner5_grey);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchSortView(String str, TextView textView, TextView textView2) {
        if (this.searchSort.equals(str)) {
            return;
        }
        this.searchSort = str;
        textView.setTextColor(this.mContext.getResources().getColor(R.color.colorAccent));
        textView.setBackgroundResource(R.drawable.shape_post_search_sort);
        textView2.setTextColor(this.mContext.getResources().getColor(R.color.text_light));
        textView2.setBackgroundResource(R.drawable.common_shape_solid_corner5_grey);
        refresh();
    }

    private void getListFromServer(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), this.path);
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        String str = this.businessType;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1110540878) {
            if (hashCode != 166751952) {
                if (hashCode == 1502545125 && str.equals("papa_search")) {
                    c = 2;
                }
            } else if (str.equals("papa_mine")) {
                c = 1;
            }
        } else if (str.equals("circle_all")) {
            c = 0;
        }
        if (c == 0) {
            tomatoParams.addParameter("postType", 2);
            tomatoParams.addParameter("groupId", Integer.valueOf(this.groupId));
        } else if (c == 1) {
            tomatoParams.addParameter("postType", 4);
            tomatoParams.addParameter("personMemberId", Integer.valueOf(this.personMemberId));
        } else if (c == 2) {
            tomatoParams.addParameter("postType", 4);
            tomatoParams.addParameter("key", this.searchKey);
            tomatoParams.addParameter("sort", this.searchSort);
        }
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<PostList>>(this) { // from class: com.one.tomato.mvp.ui.papa.view.PapaGridListFragment.3
        }.getType(), i));
    }

    public void onRecyclerItemClick(int i) {
        ArrayList<PostList> arrayList = new ArrayList<>();
        while (i < this.papaGridListAdapter.getData().size()) {
            arrayList.add(this.papaGridListAdapter.getData().get(i));
            i++;
        }
        NewPostDetailViewPagerActivity.Companion.startActivity(this.mContext, arrayList, -1, -1, this.businessType, this.pageNo, false, (Bundle) null, 0, this.groupId, 0, (String) null, true);
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what != 1) {
            return;
        }
        updateData(message.arg1, (ArrayList) baseModel.obj);
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what == 1) {
            if (message.arg1 == 1) {
                this.refreshLayout.mo6481finishRefresh();
            } else {
                PapaGridListAdapter papaGridListAdapter = this.papaGridListAdapter;
                if (papaGridListAdapter != null) {
                    papaGridListAdapter.loadMoreFail();
                }
            }
            PapaGridListAdapter papaGridListAdapter2 = this.papaGridListAdapter;
            if (papaGridListAdapter2 != null && papaGridListAdapter2.getData().size() == 0) {
                this.papaGridListAdapter.setEmptyViewState(1, this.refreshLayout);
            }
        }
        return super.handleResponseError(message);
    }

    private void updateData(int i, ArrayList<PostList> arrayList) {
        PapaGridListAdapter papaGridListAdapter;
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                PapaGridListAdapter papaGridListAdapter2 = this.papaGridListAdapter;
                if (papaGridListAdapter2 != null) {
                    papaGridListAdapter2.setEmptyViewState(2, this.refreshLayout);
                }
            }
            if (i != 2 || (papaGridListAdapter = this.papaGridListAdapter) == null) {
                return;
            }
            papaGridListAdapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
            if (smartRefreshLayout != null) {
                smartRefreshLayout.mo6481finishRefresh();
            }
            this.pageNo = 2;
            PapaGridListAdapter papaGridListAdapter3 = this.papaGridListAdapter;
            if (papaGridListAdapter3 != null) {
                papaGridListAdapter3.setNewData(arrayList);
            }
        } else {
            this.pageNo++;
            PapaGridListAdapter papaGridListAdapter4 = this.papaGridListAdapter;
            if (papaGridListAdapter4 != null) {
                papaGridListAdapter4.addData((Collection) arrayList);
            }
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        PapaGridListAdapter papaGridListAdapter5 = this.papaGridListAdapter;
        if (papaGridListAdapter5 == null) {
            return;
        }
        if (z) {
            papaGridListAdapter5.loadMoreComplete();
        } else {
            papaGridListAdapter5.loadMoreEnd();
        }
    }
}
