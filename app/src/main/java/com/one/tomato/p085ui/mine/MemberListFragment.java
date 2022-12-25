package com.one.tomato.p085ui.mine;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.adapter.MemberListAdapter;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.MemberList;
import com.one.tomato.entity.event.MemberFocusEvent;
import com.one.tomato.mvp.p080ui.circle.view.CircleActiveListActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.UserInfoManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_member_list)
/* renamed from: com.one.tomato.ui.mine.MemberListFragment */
/* loaded from: classes3.dex */
public class MemberListFragment extends BaseRecyclerViewFragment {
    private String businessType;
    private int groupId;
    private MemberListAdapter memberAdapter;
    private ArrayList<MemberList> memberInfoLists;
    private int memberTotal;
    private String path;
    private String searchKey;
    private int totalResId;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;
    private View view;

    public static MemberListFragment getInstance(String str, String str2) {
        return getInstance(str, str2, -1);
    }

    public static MemberListFragment getInstance(String str, String str2, int i) {
        MemberListFragment memberListFragment = new MemberListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", str);
        bundle.putString("business", str2);
        if (i != -1) {
            bundle.putInt("groupId", i);
        }
        memberListFragment.setArguments(bundle);
        return memberListFragment;
    }

    @Override // com.one.tomato.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.path = arguments.getString("path");
            this.businessType = arguments.getString("business");
            if (arguments.containsKey("groupId")) {
                this.groupId = arguments.getInt("groupId", -1);
            }
            if (!arguments.containsKey(CircleActiveListActivity.INTENT_ARGS_CIRCLE_ACTIVE_INFO)) {
                return;
            }
            this.memberInfoLists = (ArrayList) arguments.getSerializable(CircleActiveListActivity.INTENT_ARGS_CIRCLE_ACTIVE_INFO);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment, com.one.tomato.base.BaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = super.onCreateView(layoutInflater, viewGroup, bundle);
        if (!"search_member_list".equals(this.businessType)) {
            setUserVisibleHint(true);
        }
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseFragment
    public void onLazyLoad() {
        char c;
        super.onLazyLoad();
        String str = this.businessType;
        int hashCode = str.hashCode();
        if (hashCode == 806801994) {
            if (str.equals("fan_list")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 1047043644) {
            if (hashCode == 1281802568 && str.equals("circle_active_list")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("focus_member_list")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            this.totalResId = R.string.mine_fans_member;
        } else if (c == 1) {
            this.totalResId = R.string.mine_focus_member;
        } else if (c == 2) {
            this.totalResId = R.string.circle_active_lable;
            this.tv_num.setVisibility(0);
            this.tv_num.setText(this.totalResId);
        }
        ArrayList<MemberList> arrayList = this.memberInfoLists;
        if (arrayList != null && arrayList.size() > 0) {
            initAdapter();
            this.memberAdapter.setNewData(this.memberInfoLists);
            this.memberAdapter.setEnableLoadMore(false);
            this.memberAdapter.loadMoreEnd();
            return;
        }
        this.refreshLayout.autoRefresh(100);
        initAdapter();
    }

    private void initAdapter() {
        if (this.memberAdapter == null) {
            this.memberAdapter = new MemberListAdapter(this, R.layout.item_member, this.recyclerView, this.businessType);
            this.recyclerView.setAdapter(this.memberAdapter);
        }
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

    public void searchFromServer(String str) {
        SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.mo6481finishRefresh();
        }
        MemberListAdapter memberListAdapter = this.memberAdapter;
        if (memberListAdapter != null) {
            memberListAdapter.loadMoreEnd();
        }
        this.searchKey = str;
        this.isLazyLoad = false;
    }

    private void getListFromServer(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), this.path);
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        if ("search_member_list".equals(this.businessType)) {
            tomatoParams.addParameter("key", this.searchKey);
        }
        if ("circle_active_list".equals(this.businessType)) {
            tomatoParams.addParameter("groupId", Integer.valueOf(this.groupId));
        } else {
            tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
            tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        }
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<MemberList>>(this) { // from class: com.one.tomato.ui.mine.MemberListFragment.1
        }.getType(), i));
    }

    public void focus(MemberList memberList, int i) {
        showWaitingDialog();
        int i2 = memberList.getFollow() == 1 ? 0 : 1;
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberFollow/saveAndCancel");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("attentionMemberId", Integer.valueOf(memberList.getId()));
        tomatoParams.addParameter("flag", Integer.valueOf(i2));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 2, (Class) null, i2, i));
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            ArrayList<MemberList> arrayList = (ArrayList) baseModel.obj;
            BaseModel.Page page = baseModel.page;
            if (page != null) {
                this.memberTotal = page.getTotalCount();
            }
            updateData(message.arg1, arrayList);
        } else if (i != 2) {
        } else {
            hideWaitingDialog();
            int i2 = message.arg1;
            int i3 = message.arg2;
            MemberList item = this.memberAdapter.getItem(i3);
            if (i2 == 1) {
                UserInfoManager.setUserFollowCount(true);
                item.setFollow(1);
                if ("focus_member_list".equals(this.businessType)) {
                    this.memberTotal++;
                }
            } else {
                UserInfoManager.setUserFollowCount(false);
                item.setFollow(0);
                if ("focus_member_list".equals(this.businessType)) {
                    this.memberTotal--;
                }
            }
            this.memberAdapter.refreshNotifyItemChanged(i3);
            setMemberFocusEvent(i2, item.getId());
            if (!"focus_member_list".equals(this.businessType)) {
                return;
            }
            if (this.memberTotal < 0) {
                this.memberTotal = 0;
            }
            this.tv_num.setVisibility(0);
            this.tv_num.setText(AppUtil.getString(this.totalResId, Integer.valueOf(this.memberTotal)));
        }
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            if (message.arg1 == 1) {
                this.refreshLayout.mo6481finishRefresh();
            } else {
                this.memberAdapter.loadMoreFail();
            }
            if (this.memberAdapter.getData().size() == 0) {
                this.tv_num.setVisibility(8);
                this.memberAdapter.setEmptyViewState(1, this.refreshLayout);
            }
        } else if (i == 2) {
            hideWaitingDialog();
        }
        return super.handleResponseError(message);
    }

    private void updateData(int i, ArrayList<MemberList> arrayList) {
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
        if ("focus_member_list".equals(this.businessType)) {
            Iterator<MemberList> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                it2.next().setFollow(1);
            }
        }
        if (i == 1) {
            if ("fan_list".equals(this.businessType) || "focus_member_list".equals(this.businessType)) {
                this.tv_num.setVisibility(0);
                this.tv_num.setText(AppUtil.getString(this.totalResId, Integer.valueOf(this.memberTotal)));
            }
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.memberAdapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.memberAdapter.addData((Collection) arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if ("circle_active_list".equals(this.businessType)) {
            this.memberAdapter.setEnableLoadMore(false);
            this.memberAdapter.loadMoreEnd();
        } else if (z) {
            this.memberAdapter.loadMoreComplete();
        } else {
            this.memberAdapter.loadMoreEnd();
        }
    }

    public void onEventMainThread(MemberFocusEvent memberFocusEvent) {
        int i = memberFocusEvent.f1748id;
        int i2 = memberFocusEvent.followFlag;
        int indexOf = this.memberAdapter.getData().indexOf(new MemberList(i, i2));
        this.memberAdapter.getItem(indexOf).setFollow(i2);
        this.memberAdapter.refreshNotifyItemChanged(indexOf);
    }
}
