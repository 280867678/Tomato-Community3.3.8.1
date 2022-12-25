package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.adapter.CommonBaseAdapter;
import com.one.tomato.adapter.ViewHolder;
import com.one.tomato.base.BaseFragment;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.RecommendMemberList;
import com.one.tomato.entity.event.MemberFocusEvent;
import com.one.tomato.entity.event.PostFragmentChange;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserInfoManager;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.NoScrollGridView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.http.RequestParams;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_member_not_focus)
/* renamed from: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment */
/* loaded from: classes3.dex */
public class MemberNotFocusFragment extends BaseFragment {
    private Animation animation;
    @ViewInject(R.id.gridView)
    protected NoScrollGridView gridView;
    private boolean hidden;
    @ViewInject(R.id.iv_refresh)
    private ImageView iv_refresh;
    @ViewInject(R.id.ll_change)
    private LinearLayout ll_change;
    private MemberAdapter memberAdapter;
    @ViewInject(R.id.refreshLayout)
    protected SmartRefreshLayout refreshLayout;
    @ViewInject(R.id.scrollView)
    private ScrollView scrollView;
    private View view;
    protected int pageNo = 1;
    protected int pageSize = 6;
    private int curPosition = -1;

    @Override // com.one.tomato.base.BaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = super.onCreateView(layoutInflater, viewGroup, bundle);
        initRefreshLayout();
        setUserVisibleHint(true);
        return this.view;
    }

    @Override // android.support.p002v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        this.hidden = z;
        if (!z) {
            this.isLazyLoad = false;
            this.pageNo = 1;
            setUserVisibleHint(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        getRecommendListFromMember();
        initAdapter();
        initAnimation();
        this.ll_change.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MemberNotFocusFragment.this.iv_refresh.startAnimation(MemberNotFocusFragment.this.animation);
                MemberNotFocusFragment.this.getRecommendListFromMember();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseFragment
    public void onVisibleLoad() {
        super.onVisibleLoad();
        MemberAdapter memberAdapter = this.memberAdapter;
        if (memberAdapter == null || memberAdapter.getDatas().size() != 0) {
            return;
        }
        getRecommendListFromMember();
    }

    protected void initRefreshLayout() {
        this.refreshLayout = (SmartRefreshLayout) this.view.findViewById(R.id.refreshLayout);
        this.refreshLayout.setEnableRefresh(true);
        this.refreshLayout.mo6487setEnableLoadMore(false);
        this.refreshLayout.mo6486setEnableAutoLoadMore(false);
        this.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment.2
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.mo6485getLayout().postDelayed(new Runnable(this) { // from class: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                    }
                }, 500L);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.mo6485getLayout().postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        MemberNotFocusFragment.this.getFocusPostListFromServer();
                    }
                }, 500L);
            }
        });
    }

    private void initAnimation() {
        this.animation = AnimationUtils.loadAnimation(this.mContext, R.anim.rotation_linear);
        this.animation.setInterpolator(new LinearInterpolator());
    }

    private void initAdapter() {
        this.memberAdapter = new MemberAdapter(this.mContext, null, R.layout.item_focus_recommend_member);
        this.gridView.setAdapter((ListAdapter) this.memberAdapter);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                MemberNotFocusFragment memberNotFocusFragment = MemberNotFocusFragment.this;
                if (memberNotFocusFragment.isBlack(memberNotFocusFragment.memberAdapter.getItem(i).getBlackFlag())) {
                    return;
                }
                MemberNotFocusFragment.this.curPosition = i;
                NewMyHomePageActivity.Companion.startActivity(((BaseFragment) MemberNotFocusFragment.this).mContext, MemberNotFocusFragment.this.memberAdapter.getItem(i).getId());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getFocusPostListFromServer() {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/article/follow/list");
        tomatoParams.addParameter("pageNo", 1);
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, 1);
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<PostList>>(this) { // from class: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment.4
        }.getType()));
    }

    public void getRecommendListFromMember() {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/recommend/memberList");
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("ifMakeUp", "1");
        tomatoParams.post(new TomatoCallback(this, 2, new TypeToken<ArrayList<RecommendMemberList>>(this) { // from class: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment.5
        }.getType()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void focus(RecommendMemberList recommendMemberList) {
        if (recommendMemberList == null) {
            return;
        }
        int i = recommendMemberList.getFollowFlag() == 1 ? 0 : 1;
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberFollow/saveAndCancel");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("attentionMemberId", Integer.valueOf(recommendMemberList.getId()));
        tomatoParams.addParameter("flag", Integer.valueOf(i));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 3, (Class) null, i));
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i != 1) {
            if (i == 2) {
                this.iv_refresh.clearAnimation();
                updateData((ArrayList) baseModel.obj, baseModel);
                return;
            } else if (i != 3) {
                return;
            } else {
                hideWaitingDialog();
                int i2 = message.arg1;
                if (i2 == 1) {
                    UserInfoManager.setUserFollowCount(true);
                } else {
                    UserInfoManager.setUserFollowCount(false);
                }
                this.memberAdapter.getDatas().get(this.curPosition).setFollowFlag(i2);
                this.memberAdapter.notifyDataSetChanged();
                setMemberFocusEvent(i2, this.memberAdapter.getDatas().get(this.curPosition).getId());
                return;
            }
        }
        this.refreshLayout.mo6481finishRefresh();
        ArrayList arrayList = (ArrayList) baseModel.obj;
        if (arrayList != null && arrayList.size() > 0) {
            PostFragmentChange postFragmentChange = new PostFragmentChange();
            postFragmentChange.flag = 1;
            EventBus.getDefault().post(postFragmentChange);
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(baseModel.result);
            if (!jSONObject.has("isFollowFlag") || jSONObject.optInt("isFollowFlag") != 1) {
                return;
            }
            ToastUtil.showCenterToast((int) R.string.member_focus_no_publish);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
        } else if (i == 2) {
            this.iv_refresh.clearAnimation();
        }
        return super.handleResponseError(message);
    }

    private void updateData(ArrayList<RecommendMemberList> arrayList, BaseModel baseModel) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        this.pageNo++;
        if (baseModel.page.isLastPage()) {
            this.pageNo = 1;
        }
        this.memberAdapter.clear();
        this.memberAdapter.setList(arrayList);
        this.scrollView.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment.6
            @Override // java.lang.Runnable
            public void run() {
                MemberNotFocusFragment.this.scrollView.scrollTo(0, 0);
            }
        }, 100L);
    }

    public void onEventMainThread(MemberFocusEvent memberFocusEvent) {
        MemberAdapter memberAdapter;
        if (this.hidden || (memberAdapter = this.memberAdapter) == null || memberAdapter.getDatas().size() == 0) {
            return;
        }
        int i = memberFocusEvent.f1748id;
        int i2 = memberFocusEvent.followFlag;
        RecommendMemberList recommendMemberList = new RecommendMemberList(i, i2);
        if (!this.memberAdapter.getDatas().contains(recommendMemberList)) {
            return;
        }
        this.memberAdapter.getDatas().get(this.memberAdapter.getDatas().indexOf(recommendMemberList)).setFollowFlag(i2);
        this.memberAdapter.notifyDataSetChanged();
    }

    /* renamed from: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment$MemberAdapter */
    /* loaded from: classes3.dex */
    public class MemberAdapter extends CommonBaseAdapter<RecommendMemberList> {
        public MemberAdapter(Context context, List<RecommendMemberList> list, int i) {
            super(context, list, i);
        }

        @Override // com.one.tomato.adapter.CommonBaseAdapter
        public void convert(ViewHolder viewHolder, final RecommendMemberList recommendMemberList, final int i) {
            ImageLoaderUtil.loadHeadImage(this.mContext, (ImageView) viewHolder.getView(R.id.iv_head), new ImageBean(recommendMemberList.getLogo()));
            viewHolder.setText(R.id.tv_name, recommendMemberList.getName());
            TextView textView = (TextView) viewHolder.getView(R.id.tv_add_or_cancel);
            if (recommendMemberList.getFollowFlag() == 1) {
                textView.setText(R.string.common_focus_y);
                textView.setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
            } else {
                textView.setText(R.string.common_focus_n_add);
                textView.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.MemberNotFocusFragment.MemberAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (MemberNotFocusFragment.this.isBlack(recommendMemberList.getBlackFlag())) {
                        return;
                    }
                    MemberNotFocusFragment.this.curPosition = i;
                    MemberNotFocusFragment.this.focus(recommendMemberList);
                }
            });
        }
    }
}
