package com.one.tomato.mvp.p080ui.circle.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.p002v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.CircleDetail;
import com.one.tomato.entity.CircleList;
import com.one.tomato.entity.CircleNoticeBean;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PublishInfo;
import com.one.tomato.entity.event.CircleFocusEvent;
import com.one.tomato.entity.event.PublishTypeEvent;
import com.one.tomato.mvp.p080ui.papa.view.PapaGridListFragment;
import com.one.tomato.mvp.p080ui.post.view.MyHomePagePostFragment;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.post.PublishUtil;
import com.one.tomato.widget.NoHorScrollViewPager;
import com.one.tomato.widget.PagerSlidingTabStrip;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_circle_single)
/* renamed from: com.one.tomato.mvp.ui.circle.view.CircleSingleActivity */
/* loaded from: classes3.dex */
public class CircleSingleActivity extends BaseActivity {
    @ViewInject(R.id.appbar_layout)
    private AppBarLayout appbar_layout;
    @ViewInject(R.id.back)
    private ImageView back;
    private CircleDetail circleDetail;
    private CircleList circleList;
    private CircleNoticeBean circleNoticeBean;
    private TabFragmentAdapter fragmentAdapter;
    private int initFollowFlag;
    @ViewInject(R.id.iv_circle_header)
    private ImageView iv_circle_header;
    @ViewInject(R.id.iv_circle_post_publish)
    private ImageView iv_circle_post_publish;
    @ViewInject(R.id.iv_circle_single_top_bg)
    private ImageView iv_circle_single_top_bg;
    @ViewInject(R.id.iv_head_small)
    private ImageView iv_head_small;
    @ViewInject(R.id.ll_circle_notication_post)
    private View ll_circle_notication_post;
    @ViewInject(R.id.right_txt)
    private TextView right_txt;
    @ViewInject(R.id.rl_title)
    private View rl_title;
    @ViewInject(R.id.tab_layout)
    private PagerSlidingTabStrip tab_layout;
    @ViewInject(R.id.tv_circle_descript)
    private TextView tv_circle_descript;
    @ViewInject(R.id.tv_circle_fans_count)
    private TextView tv_circle_fans_count;
    @ViewInject(R.id.tv_circle_item_attention)
    private TextView tv_circle_item_attention;
    @ViewInject(R.id.tv_circle_name)
    private TextView tv_circle_name;
    @ViewInject(R.id.tv_circle_post_count)
    private TextView tv_circle_post_count;
    @ViewInject(R.id.tv_circle_post_notication)
    private TextView tv_circle_post_notication;
    @ViewInject(R.id.tv_offical_flag)
    private TextView tv_offical_flag;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.view_line)
    private View view_line;
    @ViewInject(R.id.viewpager)
    private NoHorScrollViewPager viewpager;
    private List<String> stringList = new ArrayList();
    private List<Fragment> fragmentList = new ArrayList();

    public static void startActivity(Context context, CircleDetail circleDetail) {
        Intent intent = new Intent();
        intent.setClass(context, CircleSingleActivity.class);
        intent.putExtra("circleInfo", circleDetail);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.circleDetail = (CircleDetail) getIntent().getExtras().getSerializable("circleInfo");
        init();
        initTabs();
        initCircleDetail();
        getDetail();
        setListener();
        updatePublishStatus(PublishUtil.getInstance().getPublishType());
    }

    private void init() {
        this.tv_circle_fans_count.setTypeface(ViewUtil.getNumFontTypeface(this));
        this.tv_circle_post_count.setTypeface(ViewUtil.getNumFontTypeface(this));
        initTopBackground();
    }

    private void initTopBackground() {
        int identifier = getResources().getIdentifier("circle_single_bg_" + ((System.currentTimeMillis() % 4) + 1), "drawable", getPackageName());
        this.iv_circle_single_top_bg.getLayoutParams();
        this.iv_circle_single_top_bg.setImageDrawable(getResources().getDrawable(identifier));
    }

    private void initTabs() {
        this.fragmentList.clear();
        this.stringList.clear();
        if (this.fragmentAdapter == null) {
            this.fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), this.fragmentList, this.stringList);
            this.viewpager.setAdapter(this.fragmentAdapter);
        }
        this.stringList.add(AppUtil.getString(R.string.home_page_must_hot));
        this.stringList.add(AppUtil.getString(R.string.home_page_must_new));
        this.stringList.add(AppUtil.getString(R.string.home_page_vedio));
        for (int i = 0; i < this.stringList.size(); i++) {
            if (i == 0) {
                MyHomePagePostFragment companion = MyHomePagePostFragment.Companion.getInstance(-1, C2516Ad.TYPE_CIRCLE, true);
                companion.setGroupId(this.circleDetail.getId());
                this.fragmentList.add(companion);
            } else if (i == 2) {
                PapaGridListFragment papaGridListFragment = PapaGridListFragment.getInstance("/app/article/group/list", "circle_all");
                papaGridListFragment.setGroupId(this.circleDetail.getId());
                this.fragmentList.add(papaGridListFragment);
            } else if (i == 1) {
                MyHomePagePostFragment companion2 = MyHomePagePostFragment.Companion.getInstance(-1, "circle_hot", true);
                companion2.setGroupId(this.circleDetail.getId());
                this.fragmentList.add(companion2);
            }
        }
        this.fragmentAdapter.notifyDataSetChanged();
        this.tab_layout.setViewPager(this.viewpager);
        PagerSlidingTabUtil.setAllTabsValue(this, this.tab_layout, true);
        this.viewpager.setOffscreenPageLimit(this.fragmentList.size());
    }

    private void setListener() {
        this.appbar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleSingleActivity.1
            @Override // android.support.design.widget.AppBarLayout.OnOffsetChangedListener, android.support.design.widget.AppBarLayout.BaseOnOffsetChangedListener
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                double totalScrollRange = appBarLayout.getTotalScrollRange();
                if (i == (-totalScrollRange)) {
                    CircleSingleActivity.this.back.setImageResource(R.drawable.icon_back_black);
                    CircleSingleActivity.this.view_line.setVisibility(0);
                    CircleSingleActivity.this.rl_title.setBackgroundColor(CircleSingleActivity.this.getResources().getColor(R.color.white));
                    CircleSingleActivity.this.iv_head_small.setVisibility(0);
                    CircleSingleActivity.this.tv_title.setVisibility(0);
                    CircleSingleActivity.this.right_txt.setVisibility(0);
                    CircleSingleActivity.this.right_txt.setTextColor(CircleSingleActivity.this.getResources().getColor(R.color.text_999ead));
                    return;
                }
                int argb = Color.argb((int) (((-i) / totalScrollRange) * 255.0d), 255, 255, 255);
                if (i == 0) {
                    CircleSingleActivity.this.rl_title.setBackgroundColor(CircleSingleActivity.this.getResources().getColor(R.color.transparent));
                } else {
                    CircleSingleActivity.this.rl_title.setBackgroundColor(argb);
                }
                CircleSingleActivity.this.back.setImageResource(R.drawable.icon_back_white);
                CircleSingleActivity.this.view_line.setVisibility(8);
                CircleSingleActivity.this.iv_head_small.setVisibility(8);
                CircleSingleActivity.this.tv_title.setVisibility(8);
                CircleSingleActivity.this.right_txt.setVisibility(0);
                CircleSingleActivity.this.right_txt.setTextColor(CircleSingleActivity.this.getResources().getColor(R.color.white));
            }
        });
    }

    @Event({R.id.back, R.id.right_txt, R.id.tv_circle_item_attention, R.id.iv_circle_post_publish, R.id.iv_circle_header, R.id.tv_circle_name, R.id.tv_circle_descript, R.id.ll_circle_top_post, R.id.ll_circle_notication_post})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.back /* 2131296336 */:
                onBackPressed();
                return;
            case R.id.iv_circle_header /* 2131297136 */:
            case R.id.right_txt /* 2131298013 */:
            case R.id.tv_circle_descript /* 2131298743 */:
            case R.id.tv_circle_name /* 2131298746 */:
                CircleDetailNewActivity.startActivity(this, this.circleDetail.getId(), 1);
                return;
            case R.id.iv_circle_post_publish /* 2131297138 */:
                startPublishActivity();
                return;
            case R.id.ll_circle_notication_post /* 2131297519 */:
                if (this.circleNoticeBean == null) {
                    this.circleNoticeBean = new CircleNoticeBean();
                    this.circleNoticeBean.setData(this.circleDetail.getNoticeContent());
                    this.circleNoticeBean.setGroupId(this.circleDetail.getId());
                    this.circleNoticeBean.setDate(this.circleDetail.getNoticeTime());
                    this.circleNoticeBean.setGroupName(this.circleDetail.getName());
                }
                CircleNoticeDetailActivity.startActivity(this, this.circleNoticeBean);
                return;
            case R.id.ll_circle_top_post /* 2131297520 */:
                if (this.circleDetail.getTopArticle() == null) {
                    return;
                }
                NewPostDetailViewPagerActivity.Companion.startActivity(this, this.circleDetail.getTopArticle().getId(), false, false, false);
                return;
            case R.id.tv_circle_item_attention /* 2131298745 */:
                CircleDetail circleDetail = this.circleDetail;
                if (circleDetail == null) {
                    return;
                }
                if (circleDetail.getFollowFlag() == 1) {
                    cancelCircleAttation();
                    return;
                } else {
                    addCircleAttation();
                    return;
                }
            default:
                return;
        }
    }

    private synchronized void initCircleDetail() {
        if (this.circleDetail == null) {
            return;
        }
        this.right_txt.setText(R.string.circle_detail_title);
        this.initFollowFlag = this.circleDetail.getFollowFlag();
        this.tv_circle_fans_count.setText(String.valueOf(this.circleDetail.getFollowCount()));
        this.tv_circle_post_count.setText(String.valueOf(this.circleDetail.getArticleCount()));
        changeFollowStatus(this.circleDetail.getFollowFlag());
        ImageLoaderUtil.loadCircleLogo(this.mContext, this.iv_head_small, new ImageBean(this.circleDetail.getLogo()));
        ImageLoaderUtil.loadCircleLogo(this.mContext, this.iv_circle_header, new ImageBean(this.circleDetail.getLogo()));
        if (this.circleDetail.getOfficial() == 1) {
            this.tv_offical_flag.setVisibility(0);
        } else {
            this.tv_offical_flag.setVisibility(8);
        }
        String name = this.circleDetail.getName();
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        if (name.length() > 10) {
            name = name.substring(0, 10) + "...";
        }
        this.tv_title.setText(this.circleDetail.getName());
        this.tv_circle_name.setText(name);
        if (!TextUtils.isEmpty(this.circleDetail.getBrief())) {
            this.tv_circle_descript.setText(this.circleDetail.getBrief());
        }
        if (!TextUtils.isEmpty(this.circleDetail.getNoticeTitle())) {
            this.ll_circle_notication_post.setVisibility(0);
            this.tv_circle_post_notication.setText(this.circleDetail.getNoticeTitle());
        } else {
            this.ll_circle_notication_post.setVisibility(8);
        }
        if (this.circleDetail.getOfficial() == 1) {
            this.iv_circle_post_publish.setVisibility(8);
        } else {
            this.iv_circle_post_publish.setVisibility(0);
        }
    }

    private void changeFollowStatus(int i) {
        if (i == 1) {
            this.tv_circle_item_attention.setText(AppUtil.getString(R.string.common_focus_y));
            this.tv_circle_item_attention.setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
            return;
        }
        this.tv_circle_item_attention.setText(AppUtil.getString(R.string.common_focus_n_add));
        this.tv_circle_item_attention.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (1 == i && i2 == -1) {
            this.circleDetail.setFollowFlag(intent.getExtras().getInt("followFlag"));
            changeFollowStatus(this.circleDetail.getFollowFlag());
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        CircleDetail circleDetail = this.circleDetail;
        if (circleDetail != null && this.initFollowFlag != circleDetail.getFollowFlag()) {
            CircleFocusEvent circleFocusEvent = new CircleFocusEvent();
            circleFocusEvent.circleList = new CircleList(this.circleDetail.getId(), this.circleDetail.getName(), this.circleDetail.getLogo(), this.circleDetail.getBrief(), this.circleDetail.getFollowFlag());
            EventBus.getDefault().post(circleFocusEvent);
        }
        super.onBackPressed();
    }

    private void startPublishActivity() {
        PublishInfo publishInfo;
        PublishUtil.getInstance().setContext(this);
        if (this.circleList != null) {
            publishInfo = new PublishInfo();
            publishInfo.setCircleList(this.circleList);
        } else {
            publishInfo = null;
        }
        PublishUtil.getInstance().startPublishActivity(publishInfo);
    }

    public void onEventMainThread(PublishTypeEvent publishTypeEvent) {
        updatePublishStatus(publishTypeEvent.type);
    }

    private void updatePublishStatus(int i) {
        if (i >= 0) {
            if (i == 0) {
                ImageLoaderUtil.loadNormalDrawableImg(this.mContext, this.iv_circle_post_publish, R.drawable.post_publish);
            } else if (i == 1) {
                ImageLoaderUtil.loadNormalDrawableGif(this.mContext, this.iv_circle_post_publish, R.drawable.publish_ing);
            } else if (i == 2) {
                ImageLoaderUtil.loadNormalDrawableGif(this.mContext, this.iv_circle_post_publish, R.drawable.publish_success);
            } else if (i == 3) {
                ImageLoaderUtil.loadNormalDrawableImg(this.mContext, this.iv_circle_post_publish, R.drawable.post_publish_fail);
            } else if (i != 4) {
            } else {
                ImageLoaderUtil.loadNormalDrawableImg(this.mContext, this.iv_circle_post_publish, R.drawable.post_publish_fail);
            }
        }
    }

    private void getDetail() {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/circle/detail");
        tomatoParams.addParameter("groupId", Integer.valueOf(this.circleDetail.getId()));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 1, CircleDetail.class));
    }

    private void addCircleAttation() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/follow/save");
        tomatoParams.addParameter("groupId", Integer.valueOf(this.circleDetail.getId()));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    private void cancelCircleAttation() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/follow/delete");
        tomatoParams.addParameter("groupId", Integer.valueOf(this.circleDetail.getId()));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 3));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            this.circleDetail = (CircleDetail) baseModel.obj;
            this.circleList = new CircleList(this.circleDetail.getId(), this.circleDetail.getName());
            initCircleDetail();
        } else if (i == 2) {
            this.circleDetail.setFollowFlag(1);
            changeFollowStatus(this.circleDetail.getFollowFlag());
            ToastUtil.showCenterToast((int) R.string.common_focus_success);
        } else if (i != 3) {
        } else {
            this.circleDetail.setFollowFlag(0);
            changeFollowStatus(this.circleDetail.getFollowFlag());
            ToastUtil.showCenterToast((int) R.string.common_cancel_focus_success);
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what != 1) {
        }
        return true;
    }
}
