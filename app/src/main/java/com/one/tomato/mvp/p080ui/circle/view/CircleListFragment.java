package com.one.tomato.mvp.p080ui.circle.view;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.constants.PreferencesConstant;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.CircleDetail;
import com.one.tomato.entity.CircleList;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.event.CircleFocusEvent;
import com.one.tomato.entity.event.SelectCircleEvent;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.http.RequestParams;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_circle_list)
/* renamed from: com.one.tomato.mvp.ui.circle.view.CircleListFragment */
/* loaded from: classes3.dex */
public class CircleListFragment extends BaseRecyclerViewFragment {
    private String businessType;
    private int categoryId;
    private BaseRecyclerViewAdapter<CircleList> circleAdapter;
    private boolean isSelectCircle;
    private int memberTotal;
    private boolean needRefresh;
    private RelativeLayout.LayoutParams params50;
    private RelativeLayout.LayoutParams params70;
    private String path;
    private String searchKey;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;
    private View view;
    private ArrayList<CircleList> historyList = new ArrayList<>();
    private int curPosition = -1;

    public static CircleListFragment getInstance(String str, int i, String str2) {
        CircleListFragment circleListFragment = new CircleListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", str);
        bundle.putInt("categoryId", i);
        bundle.putString("business", str2);
        circleListFragment.setArguments(bundle);
        return circleListFragment;
    }

    @Override // com.one.tomato.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.path = arguments.getString("path");
            this.categoryId = arguments.getInt("categoryId");
            this.businessType = arguments.getString("business");
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment, com.one.tomato.base.BaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = super.onCreateView(layoutInflater, viewGroup, bundle);
        if ("circleSearchHistory".equals(this.businessType) || "circleFilter0".equals(this.businessType)) {
            setUserVisibleHint(true);
        }
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        if (this.params70 == null) {
            this.params70 = new RelativeLayout.LayoutParams((int) DisplayMetricsUtils.dp2px(70.0f), (int) DisplayMetricsUtils.dp2px(70.0f));
        }
        if (this.params50 == null) {
            this.params50 = new RelativeLayout.LayoutParams((int) DisplayMetricsUtils.dp2px(50.0f), (int) DisplayMetricsUtils.dp2px(50.0f));
        }
        initAdapter();
        if ("circleSearchHistory".equals(this.businessType)) {
            this.refreshLayout.setEnableRefresh(false);
        } else {
            this.refreshLayout.autoRefresh(100);
        }
        if ("circleSearchResult".equals(this.businessType) || "circleSearchHistory".equals(this.businessType) || "circleFilter0".equals(this.businessType) || "circleFilter".equals(this.businessType)) {
            ArrayList<CircleList> arrayList = (ArrayList) BaseApplication.getGson().fromJson(PreferencesUtil.getInstance().getString(PreferencesConstant.HISTORY_CIRCLE), new TypeToken<ArrayList<CircleList>>(this) { // from class: com.one.tomato.mvp.ui.circle.view.CircleListFragment.1
            }.getType());
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            this.historyList.clear();
            this.historyList.addAll(arrayList);
            if (!"circleSearchHistory".equals(this.businessType)) {
                return;
            }
            setCircleSearchHistory(arrayList);
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z) {
            if ("focus_circle_list".equals(this.businessType)) {
                resetRefresh();
            } else if (!"circleSearchResult".equals(this.businessType) && !"circleSearchHistory".equals(this.businessType) && !"circleFilter0".equals(this.businessType) && !"circleFilter".equals(this.businessType)) {
            } else {
                ArrayList<CircleList> arrayList = (ArrayList) BaseApplication.getGson().fromJson(PreferencesUtil.getInstance().getString(PreferencesConstant.HISTORY_CIRCLE), new TypeToken<ArrayList<CircleList>>(this) { // from class: com.one.tomato.mvp.ui.circle.view.CircleListFragment.2
                }.getType());
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                this.historyList.clear();
                this.historyList.addAll(arrayList);
                if (!"circleSearchHistory".equals(this.businessType)) {
                    return;
                }
                setCircleSearchHistory(arrayList);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseFragment
    public void onVisibleLoad() {
        super.onVisibleLoad();
        if (this.needRefresh) {
            resetRefresh();
            this.needRefresh = false;
        }
        BaseRecyclerViewAdapter<CircleList> baseRecyclerViewAdapter = this.circleAdapter;
        if (baseRecyclerViewAdapter != null && baseRecyclerViewAdapter.getData().size() == 0 && this.circleAdapter.getEmptyViewState() == 1) {
            refresh();
        }
    }

    @Override // com.one.tomato.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (!this.needRefresh || !this.isVisibleToUser) {
            return;
        }
        resetRefresh();
        this.needRefresh = false;
    }

    private void resetRefresh() {
        this.isLazyLoad = false;
        setUserVisibleHint(true);
    }

    protected void initAdapter() {
        BaseRecyclerViewAdapter<CircleList> baseRecyclerViewAdapter;
        if (this.circleAdapter != null) {
            return;
        }
        this.circleAdapter = new BaseRecyclerViewAdapter<CircleList>(this.mContext, R.layout.item_circle_list, this.recyclerView) { // from class: com.one.tomato.mvp.ui.circle.view.CircleListFragment.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, CircleList circleList) {
                super.convert(baseViewHolder, (BaseViewHolder) circleList);
                ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_head);
                TextView textView = (TextView) baseViewHolder.getView(R.id.tv_name);
                TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_desc);
                TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_add_or_cancel);
                ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_delete);
                if ("focus_circle_list".equals(CircleListFragment.this.businessType) || "circleSearchResult".equals(CircleListFragment.this.businessType)) {
                    imageView.setLayoutParams(CircleListFragment.this.params70);
                    if ("circleSearchResult".equals(CircleListFragment.this.businessType)) {
                        textView3.setVisibility(8);
                    } else {
                        textView3.setVisibility(0);
                    }
                } else if ("circleSearchHistory".equals(CircleListFragment.this.businessType) || "circleFilter0".equals(CircleListFragment.this.businessType) || "circleFilter".equals(CircleListFragment.this.businessType)) {
                    imageView.setLayoutParams(CircleListFragment.this.params50);
                    if ("circleSearchHistory".equals(CircleListFragment.this.businessType)) {
                        imageView2.setVisibility(0);
                    } else {
                        imageView2.setVisibility(8);
                    }
                }
                ImageLoaderUtil.loadCircleLogo(this.mContext, imageView, new ImageBean(circleList.getLogo()));
                textView.setText(circleList.getName());
                textView2.setText(circleList.getBrief());
                if (circleList.getFollowFlag() == 1) {
                    textView3.setText(R.string.common_focus_y);
                    textView3.setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
                } else {
                    textView3.setText(R.string.common_focus_n_add);
                    textView3.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
                }
                baseViewHolder.addOnClickListener(R.id.tv_add_or_cancel).addOnClickListener(R.id.iv_delete);
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                CircleList circleList = (CircleList) CircleListFragment.this.circleAdapter.getItem(i);
                if ("circleSearchResult".equals(CircleListFragment.this.businessType) || "circleSearchHistory".equals(CircleListFragment.this.businessType) || "circleFilter0".equals(CircleListFragment.this.businessType) || "circleFilter".equals(CircleListFragment.this.businessType)) {
                    if (CircleListFragment.this.historyList.contains(circleList)) {
                        CircleListFragment.this.historyList.remove(CircleListFragment.this.historyList.indexOf(circleList));
                    }
                    CircleListFragment.this.historyList.add(0, circleList);
                    if (CircleListFragment.this.historyList.size() > 10) {
                        CircleListFragment.this.historyList.remove(10);
                    }
                    PreferencesUtil.getInstance().putString(PreferencesConstant.HISTORY_CIRCLE, BaseApplication.getGson().toJson(CircleListFragment.this.historyList));
                }
                if (CircleListFragment.this.isSelectCircle) {
                    SelectCircleEvent selectCircleEvent = new SelectCircleEvent();
                    selectCircleEvent.circleList = circleList;
                    EventBus.getDefault().post(selectCircleEvent);
                    CircleListFragment.this.getActivity().finish();
                } else if (CircleListFragment.this.isBlack(circleList.getBlackFlag())) {
                } else {
                    CircleListFragment.this.curPosition = i;
                    CircleDetail circleDetail = new CircleDetail();
                    circleDetail.setName(circleList.getName());
                    circleDetail.setId(circleList.getId());
                    CircleSingleActivity.startActivity(this.mContext, circleDetail);
                }
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
                CircleListFragment circleListFragment = CircleListFragment.this;
                if (circleListFragment.isBlack(((CircleList) circleListFragment.circleAdapter.getItem(i)).getBlackFlag())) {
                    return;
                }
                CircleListFragment.this.curPosition = i;
                int id = view.getId();
                if (id != R.id.iv_delete) {
                    if (id != R.id.tv_add_or_cancel) {
                        return;
                    }
                    if (((CircleList) CircleListFragment.this.circleAdapter.getItem(CircleListFragment.this.curPosition)).getFollowFlag() == 1) {
                        CircleListFragment.this.cancelCircleAttation();
                        return;
                    } else {
                        CircleListFragment.this.addCircleAttation();
                        return;
                    }
                }
                CircleListFragment.this.circleAdapter.remove(CircleListFragment.this.curPosition);
                if (CircleListFragment.this.isEmpty()) {
                    CircleListFragment.this.circleAdapter.setEmptyViewState(2, ((BaseRecyclerViewFragment) CircleListFragment.this).refreshLayout);
                    PreferencesUtil.getInstance().putString(PreferencesConstant.HISTORY_CIRCLE, "");
                    return;
                }
                PreferencesUtil.getInstance().putString(PreferencesConstant.HISTORY_CIRCLE, BaseApplication.getGson().toJson(CircleListFragment.this.circleAdapter.getData()));
                CircleListFragment.this.circleAdapter.notifyDataSetChanged();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                CircleListFragment.this.circleAdapter.setEmptyViewState(0, ((BaseRecyclerViewFragment) CircleListFragment.this).refreshLayout);
                CircleListFragment.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                CircleListFragment.this.loadMore();
            }
        };
        if ("circleSearchHistory".equals(this.businessType) && (baseRecyclerViewAdapter = this.circleAdapter) != null) {
            baseRecyclerViewAdapter.setEnableLoadMore(false);
        }
        this.recyclerView.setAdapter(this.circleAdapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void loadMore() {
        char c;
        String str = this.businessType;
        switch (str.hashCode()) {
            case -1097362219:
                if (str.equals("circleSearchResult")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -473598776:
                if (str.equals("circleFilter0")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -403481146:
                if (str.equals("focus_circle_list")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1508743272:
                if (str.equals("circleFilter")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            getAllCircleFromServer(2);
        } else if (c != 1 && c != 2 && c != 3) {
        } else {
            getAllCircleFromServer(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void refresh() {
        char c;
        String str = this.businessType;
        switch (str.hashCode()) {
            case -1097362219:
                if (str.equals("circleSearchResult")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -473598776:
                if (str.equals("circleFilter0")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -403481146:
                if (str.equals("focus_circle_list")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1508743272:
                if (str.equals("circleFilter")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            getAllCircleFromServer(1);
        } else if (c != 1 && c != 2 && c != 3) {
        } else {
            getAllCircleFromServer(1);
        }
    }

    public void getAllCircleFromServer(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), this.path);
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        if ("circleFilter0".equals(this.businessType) || "circleFilter".equals(this.businessType)) {
            tomatoParams.addParameter("categoryId", Integer.valueOf(this.categoryId));
        }
        if ("circleSearchResult".equals(this.businessType)) {
            tomatoParams.addParameter("key", this.searchKey);
            BaseRecyclerViewAdapter<CircleList> baseRecyclerViewAdapter = this.circleAdapter;
            if (baseRecyclerViewAdapter != null) {
                baseRecyclerViewAdapter.getData().clear();
                this.circleAdapter.notifyDataSetChanged();
            }
        }
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<CircleList>>(this) { // from class: com.one.tomato.mvp.ui.circle.view.CircleListFragment.4
        }.getType(), i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addCircleAttation() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/follow/save");
        tomatoParams.addParameter("groupId", Integer.valueOf(this.circleAdapter.getItem(this.curPosition).getId()));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelCircleAttation() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/follow/delete");
        tomatoParams.addParameter("groupId", Integer.valueOf(this.circleAdapter.getItem(this.curPosition).getId()));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 3));
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        ArrayList arrayList;
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            ArrayList<CircleList> arrayList2 = (ArrayList) baseModel.obj;
            this.memberTotal = baseModel.page.getTotalCount();
            if ("focus_circle_list".equals(this.businessType) && message.arg1 == 1 && (arrayList2 == null || arrayList2.size() == 0)) {
                this.refreshLayout.mo6481finishRefresh();
                this.circleAdapter.setEmptyViewState(2, this.refreshLayout);
                return;
            }
            updateData(message.arg1, arrayList2);
            return;
        }
        if (i == 2) {
            hideWaitingDialog();
            this.circleAdapter.getItem(this.curPosition).setFollowFlag(1);
            this.circleAdapter.notifyItemChanged(this.curPosition);
            if ("focus_circle_list".equals(this.businessType)) {
                this.memberTotal++;
                if (this.memberTotal < 0) {
                    this.memberTotal = 0;
                }
                this.tv_num.setVisibility(0);
                this.tv_num.setText(AppUtil.getString(R.string.mine_focus_circle, Integer.valueOf(this.memberTotal)));
            }
            sendFocusChangeEvent();
        } else if (i == 3) {
            this.circleAdapter.getItem(this.curPosition).setFollowFlag(0);
            this.circleAdapter.notifyItemChanged(this.curPosition);
            if ("focus_circle_list".equals(this.businessType)) {
                this.memberTotal--;
                if (this.memberTotal < 0) {
                    this.memberTotal = 0;
                }
                this.tv_num.setVisibility(0);
                this.tv_num.setText(AppUtil.getString(R.string.mine_focus_circle, Integer.valueOf(this.memberTotal)));
            }
            sendFocusChangeEvent();
        } else if (i == 4 && (arrayList = (ArrayList) baseModel.obj) != null && arrayList.size() != 0) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                CircleList circleList = new CircleList(((Integer) arrayList.get(i2)).intValue());
                if (this.historyList.contains(circleList)) {
                    this.historyList.remove(circleList);
                }
            }
            this.circleAdapter.setNewData(this.historyList);
            this.circleAdapter.loadMoreEnd();
            if (isEmpty()) {
                this.circleAdapter.setEmptyViewState(2, this.refreshLayout);
                PreferencesUtil.getInstance().putString(PreferencesConstant.HISTORY_CIRCLE, "");
                return;
            }
            PreferencesUtil.getInstance().putString(PreferencesConstant.HISTORY_CIRCLE, BaseApplication.getGson().toJson(this.circleAdapter.getData()));
        }
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what != 1) {
            return false;
        }
        if (message.arg1 == 1) {
            this.refreshLayout.mo6481finishRefresh();
        } else {
            this.circleAdapter.loadMoreFail();
        }
        if (this.circleAdapter.getData().size() != 0) {
            return true;
        }
        this.tv_num.setVisibility(8);
        this.circleAdapter.setEmptyViewState(1, this.refreshLayout);
        return true;
    }

    private void updateData(int i, ArrayList<CircleList> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.circleAdapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.circleAdapter.loadMoreEnd();
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        if (this.isSelectCircle && ("circleFilter0".equals(this.businessType) || "circleFilter".equals(this.businessType))) {
            arrayList2.addAll(filterOfficeList(arrayList));
            if (arrayList2.isEmpty()) {
                if (i == 1) {
                    this.refreshLayout.mo6481finishRefresh();
                    this.circleAdapter.setEmptyViewState(2, this.refreshLayout);
                }
                if (i != 2) {
                    return;
                }
                this.circleAdapter.loadMoreEnd();
                return;
            }
        } else {
            arrayList2.addAll(arrayList);
        }
        boolean z = false;
        if (i == 1) {
            if ("focus_circle_list".equals(this.businessType)) {
                this.tv_num.setVisibility(0);
                this.tv_num.setText(AppUtil.getString(R.string.mine_focus_circle, Integer.valueOf(this.memberTotal)));
            }
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.circleAdapter.setNewData(arrayList2);
        } else {
            this.pageNo++;
            this.circleAdapter.addData(arrayList2);
        }
        if (arrayList2.size() >= this.pageSize) {
            z = true;
        }
        if (z) {
            this.circleAdapter.loadMoreComplete();
        } else {
            this.circleAdapter.loadMoreEnd();
        }
    }

    public void setCircleSearchHistory(ArrayList<CircleList> arrayList) {
        this.historyList.clear();
        this.historyList.addAll(arrayList);
        if (this.circleAdapter == null) {
            return;
        }
        if (arrayList == null || arrayList.isEmpty()) {
            this.circleAdapter.setEmptyViewState(2, AppUtil.getString(R.string.circle_search_recent_no_data), this.refreshLayout);
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        if (this.isSelectCircle) {
            arrayList2.addAll(filterOfficeList(arrayList));
        } else {
            arrayList2.addAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            this.circleAdapter.setEmptyViewState(2, AppUtil.getString(R.string.circle_search_recent_no_data), this.refreshLayout);
            return;
        }
        this.circleAdapter.setNewData(arrayList2);
        this.circleAdapter.loadMoreEnd();
        checkHistoryFromServer();
    }

    private ArrayList<CircleList> filterOfficeList(ArrayList<CircleList> arrayList) {
        if (arrayList == null) {
            return new ArrayList<>();
        }
        ArrayList<CircleList> arrayList2 = new ArrayList<>();
        Iterator<CircleList> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            CircleList next = it2.next();
            if (next.getOfficial() == 0) {
                arrayList2.add(next);
            }
        }
        return arrayList2;
    }

    private void checkHistoryFromServer() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.historyList.size(); i++) {
            sb.append(this.historyList.get(i).getId());
            if (i < this.historyList.size() - 1) {
                sb.append(",");
            }
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/circle/checkCircle");
        tomatoParams.addParameter("groupIds", sb.toString());
        tomatoParams.post(new TomatoCallback(this, 4, new TypeToken<ArrayList<Integer>>(this) { // from class: com.one.tomato.mvp.ui.circle.view.CircleListFragment.5
        }.getType()));
    }

    public boolean isEmpty() {
        return this.circleAdapter.getData().isEmpty();
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        this.needRefresh = true;
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.LoginOutResponseObserver
    public void onLoginOutSuccess() {
        super.onLoginOutSuccess();
        this.needRefresh = true;
    }

    private void sendFocusChangeEvent() {
        CircleFocusEvent circleFocusEvent = new CircleFocusEvent();
        circleFocusEvent.circleList = this.circleAdapter.getItem(this.curPosition);
        EventBus.getDefault().post(circleFocusEvent);
    }

    public void onEventMainThread(CircleFocusEvent circleFocusEvent) {
        BaseRecyclerViewAdapter<CircleList> baseRecyclerViewAdapter = this.circleAdapter;
        if (baseRecyclerViewAdapter == null || baseRecyclerViewAdapter.getData().size() == 0) {
            return;
        }
        circleFocusEvent.circleList.getId();
        int followFlag = circleFocusEvent.circleList.getFollowFlag();
        if (!this.circleAdapter.getData().contains(circleFocusEvent.circleList)) {
            return;
        }
        int indexOf = this.circleAdapter.getData().indexOf(circleFocusEvent.circleList);
        this.circleAdapter.getItem(indexOf).setFollowFlag(followFlag);
        this.circleAdapter.refreshNotifyItemChanged(indexOf);
    }
}
