package com.one.tomato.dialog;

import android.content.Context;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.ShieldEvent;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import com.one.tomato.mvp.p080ui.post.view.PostAndMemberReportActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import de.greenrobot.event.EventBus;

/* loaded from: classes3.dex */
public class PostActionDialog extends BottomSheetDialog implements View.OnClickListener, ResponseObserver {
    private Context context;
    private LinearLayout ll_first_menu;
    private LinearLayout ll_manage_menu;
    private LinearLayout ll_shield_menu;
    private NewPostItemOnClickCallBack newPostItemOnClickCallBack;
    private int position;
    private PostList postList;
    private TextView text_manage;
    private TextView tv_cancel;
    private TextView tv_collect;
    private TextView tv_down_black;
    private TextView tv_down_no_publish_post;
    private TextView tv_down_post;
    private TextView tv_focus;
    private TextView tv_report;
    private TextView tv_shield;
    private TextView tv_shield_detail;
    private TextView tv_shield_user;

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleHttpRequestError(Message message) {
        return false;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleRequestCancel(Message message) {
        return false;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        return true;
    }

    public PostActionDialog(Context context) {
        super(context);
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_post_action, (ViewGroup) null);
        setContentView(inflate);
        this.ll_first_menu = (LinearLayout) inflate.findViewById(R.id.ll_first_menu);
        this.tv_focus = (TextView) inflate.findViewById(R.id.tv_focus);
        this.tv_collect = (TextView) inflate.findViewById(R.id.tv_collect);
        this.tv_shield = (TextView) inflate.findViewById(R.id.tv_shield);
        this.tv_report = (TextView) inflate.findViewById(R.id.tv_report);
        this.ll_shield_menu = (LinearLayout) inflate.findViewById(R.id.ll_shield_menu);
        this.tv_shield_user = (TextView) inflate.findViewById(R.id.tv_shield_user);
        this.tv_shield_detail = (TextView) inflate.findViewById(R.id.tv_shield_detail);
        this.tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        this.text_manage = (TextView) inflate.findViewById(R.id.text_manage);
        this.ll_manage_menu = (LinearLayout) inflate.findViewById(R.id.ll_manage_menu);
        this.tv_down_post = (TextView) inflate.findViewById(R.id.tv_down_post);
        this.tv_down_no_publish_post = (TextView) inflate.findViewById(R.id.tv_down_no_publish_post);
        this.tv_down_black = (TextView) inflate.findViewById(R.id.tv_down_black);
        this.tv_focus.setOnClickListener(this);
        this.tv_collect.setOnClickListener(this);
        this.tv_shield.setOnClickListener(this);
        this.tv_report.setOnClickListener(this);
        this.tv_shield_user.setOnClickListener(this);
        this.tv_shield_detail.setOnClickListener(this);
        this.tv_cancel.setOnClickListener(this);
        this.text_manage.setOnClickListener(this);
        this.tv_down_post.setOnClickListener(this);
        this.tv_down_no_publish_post.setOnClickListener(this);
        this.tv_down_black.setOnClickListener(this);
    }

    public void initCallBack(@Nullable NewPostItemOnClickCallBack newPostItemOnClickCallBack) {
        this.newPostItemOnClickCallBack = newPostItemOnClickCallBack;
    }

    public void setPostList(int i, PostList postList) {
        this.position = i;
        this.postList = postList;
        this.ll_first_menu.setVisibility(0);
        this.ll_shield_menu.setVisibility(8);
        this.ll_manage_menu.setVisibility(8);
        if (postList.getMemberId() == DBUtil.getMemberId()) {
            this.tv_focus.setVisibility(8);
            this.tv_collect.setVisibility(0);
            this.tv_shield.setVisibility(8);
            this.tv_report.setVisibility(8);
        } else {
            this.tv_focus.setVisibility(0);
            this.tv_collect.setVisibility(0);
            this.tv_shield.setVisibility(0);
            this.tv_report.setVisibility(0);
        }
        if (postList.getIsAttention() == 1) {
            this.tv_focus.setText(R.string.post_focus_member_cancel);
        } else {
            this.tv_focus.setText(R.string.post_focus_member);
        }
        if (postList.getIsFavor() == 1) {
            this.tv_collect.setText(R.string.post_collect_cancel);
        } else {
            this.tv_collect.setText(R.string.post_collect);
        }
        if (postList.getSource() == 2 && DBUtil.getUserInfo().getRoleType() == 2) {
            this.text_manage.setVisibility(0);
        } else {
            this.text_manage.setVisibility(8);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_manage /* 2131298441 */:
                postManage();
                return;
            case R.id.tv_cancel /* 2131298712 */:
                dismiss();
                return;
            case R.id.tv_collect /* 2131298760 */:
                dismiss();
                NewPostItemOnClickCallBack newPostItemOnClickCallBack = this.newPostItemOnClickCallBack;
                if (newPostItemOnClickCallBack == null) {
                    return;
                }
                newPostItemOnClickCallBack.itemPostCollect(this.postList);
                return;
            case R.id.tv_down_black /* 2131298854 */:
                PostAndMemberReportActivity.startActivity(this.context, "down_post", this.postList.getId(), 3);
                dismiss();
                return;
            case R.id.tv_down_no_publish_post /* 2131298858 */:
                PostAndMemberReportActivity.startActivity(this.context, "down_post", this.postList.getId(), 2);
                dismiss();
                return;
            case R.id.tv_down_post /* 2131298859 */:
                PostAndMemberReportActivity.startActivity(this.context, "down_post", this.postList.getId(), 1);
                dismiss();
                return;
            case R.id.tv_focus /* 2131298912 */:
                dismiss();
                NewPostItemOnClickCallBack newPostItemOnClickCallBack2 = this.newPostItemOnClickCallBack;
                if (newPostItemOnClickCallBack2 == null) {
                    return;
                }
                newPostItemOnClickCallBack2.itemPostFoucs(this.postList);
                return;
            case R.id.tv_report /* 2131299319 */:
                dismiss();
                PostAndMemberReportActivity.startActivity(this.context, "post", this.postList.getId());
                return;
            case R.id.tv_shield /* 2131299370 */:
                this.ll_first_menu.setVisibility(8);
                this.ll_shield_menu.setVisibility(0);
                return;
            case R.id.tv_shield_detail /* 2131299371 */:
                dismiss();
                shield(2, this.position);
                return;
            case R.id.tv_shield_user /* 2131299372 */:
                dismiss();
                shield(1, this.position);
                return;
            default:
                return;
        }
    }

    private void shield(int i, int i2) {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/record/shield/add");
        tomatoParams.addParameter("createMemberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("type", Integer.valueOf(i));
        if (i == 1) {
            tomatoParams.addParameter("memberId", Integer.valueOf(this.postList.getMemberId()));
        } else if (i == 2) {
            tomatoParams.addParameter("articleId", Integer.valueOf(this.postList.getId()));
        }
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 1, (Class) null, i, i2));
    }

    private void postManage() {
        this.ll_first_menu.setVisibility(8);
        this.ll_manage_menu.setVisibility(0);
    }

    @Override // com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        ToastUtil.showCenterToast((int) R.string.post_shield_success);
        if (message.what == 1) {
            int i = message.arg1;
            int i2 = message.arg2;
            int id = this.postList.getId();
            ShieldEvent shieldEvent = new ShieldEvent();
            shieldEvent.type = i;
            shieldEvent.position = i2;
            shieldEvent.postId = id;
            EventBus.getDefault().post(shieldEvent);
        }
    }
}
