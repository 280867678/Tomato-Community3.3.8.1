package com.one.tomato.mvp.p080ui.circle.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.CircleDetail;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.MemberList;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_circle_detail_new)
/* renamed from: com.one.tomato.mvp.ui.circle.view.CircleDetailNewActivity */
/* loaded from: classes3.dex */
public class CircleDetailNewActivity extends BaseActivity {
    private CircleDetail circleDetail;
    private int groupId;
    private int initFollowFlag;
    @ViewInject(R.id.iv_circle_icon)
    private ImageView iv_circle_icon;
    @ViewInject(R.id.ll_header_list)
    private LinearLayout ll_header_list;
    private List<MemberList> memberList = new ArrayList();
    @ViewInject(R.id.rl_last_active_header)
    private View rl_last_active_header;
    private MemberList selfBean;
    @ViewInject(R.id.tv_add_or_cancel)
    private TextView tv_add_or_cancel;
    @ViewInject(R.id.tv_circle_brief)
    private TextView tv_circle_brief;
    @ViewInject(R.id.tv_circle_category)
    private TextView tv_circle_category;
    @ViewInject(R.id.tv_circle_name)
    private TextView tv_circle_name;

    public static void startActivity(Context context, int i, int i2) {
        Intent intent = new Intent();
        intent.setClass(context, CircleDetailNewActivity.class);
        intent.putExtra("groupId", i);
        ((Activity) context).startActivityForResult(intent, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.groupId = getIntent().getExtras().getInt("groupId");
        this.titleTV.setText(R.string.circle_detail_title);
        initHeadAdapter();
        setListener();
        getDetail();
    }

    private void initHeadAdapter() {
        this.selfBean = new MemberList(DBUtil.getMemberId(), DBUtil.getUserInfo().getAvatar());
        new MemberList(-1, "");
    }

    private void setListener() {
        this.tv_add_or_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleDetailNewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CircleDetailNewActivity.this.circleDetail == null) {
                    return;
                }
                if (CircleDetailNewActivity.this.circleDetail.getFollowFlag() == 1) {
                    CircleDetailNewActivity.this.cancelCircleAttation();
                } else {
                    CircleDetailNewActivity.this.addCircleAttation();
                }
            }
        });
        this.rl_last_active_header.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleDetailNewActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CircleDetailNewActivity circleDetailNewActivity = CircleDetailNewActivity.this;
                CircleActiveListActivity.startActivity(circleDetailNewActivity, circleDetailNewActivity.circleDetail.getId(), CircleDetailNewActivity.this.circleDetail.getName());
            }
        });
    }

    private void getDetail() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/circle/detail");
        tomatoParams.addParameter("groupId", Integer.valueOf(this.groupId));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 1, CircleDetail.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addCircleAttation() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/follow/save");
        tomatoParams.addParameter("groupId", Integer.valueOf(this.groupId));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelCircleAttation() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/follow/delete");
        tomatoParams.addParameter("groupId", Integer.valueOf(this.groupId));
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
            this.initFollowFlag = this.circleDetail.getFollowFlag();
            updateData();
        } else if (i == 2) {
            this.circleDetail.setFollowFlag(1);
            changeFollowStatus(this.circleDetail.getFollowFlag());
            this.memberList.add(0, this.selfBean);
        } else if (i != 3) {
        } else {
            this.circleDetail.setFollowFlag(0);
            changeFollowStatus(this.circleDetail.getFollowFlag());
            if (!this.memberList.contains(this.selfBean)) {
                return;
            }
            this.memberList.remove(this.selfBean);
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what != 1) {
        }
        return super.handleResponseError(message);
    }

    private void updateData() {
        CircleDetail circleDetail = this.circleDetail;
        if (circleDetail == null) {
            return;
        }
        this.initFollowFlag = circleDetail.getFollowFlag();
        changeFollowStatus(this.circleDetail.getFollowFlag());
        this.memberList.addAll(this.circleDetail.getUserList());
        addHeaderImage(this.memberList);
        ImageLoaderUtil.loadCircleLogo(this.mContext, this.iv_circle_icon, new ImageBean(this.circleDetail.getLogo()));
        this.tv_circle_name.setText(this.circleDetail.getName());
        this.tv_circle_category.setText(this.circleDetail.getCategoryName());
        this.tv_circle_brief.setText(this.circleDetail.getBrief());
    }

    private void changeFollowStatus(int i) {
        if (i == 1) {
            this.tv_add_or_cancel.setText(R.string.common_focus_y);
            this.tv_add_or_cancel.setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
            return;
        }
        this.tv_add_or_cancel.setText(R.string.common_focus_n_add);
        this.tv_add_or_cancel.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        CircleDetail circleDetail = this.circleDetail;
        if (circleDetail != null && this.initFollowFlag != circleDetail.getFollowFlag()) {
            Intent intent = new Intent();
            intent.putExtra("groupId", this.groupId);
            intent.putExtra("followFlag", this.circleDetail.getFollowFlag());
            setResult(-1, intent);
        }
        super.onBackPressed();
    }

    private void addHeaderImage(List<MemberList> list) {
        if (list != null && list.size() > 4) {
            list = list.subList(0, 4);
        }
        if (list == null || list.size() <= 0) {
            this.rl_last_active_header.setVisibility(8);
            return;
        }
        this.rl_last_active_header.setVisibility(0);
        this.ll_header_list.removeAllViews();
        for (MemberList memberList : list) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.dimen_16), getResources().getDimensionPixelSize(R.dimen.dimen_16));
            layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.dimen_4);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoaderUtil.loadHeadImage(this, imageView, new ImageBean(memberList.getAvatar()));
            this.ll_header_list.addView(imageView);
        }
    }
}
