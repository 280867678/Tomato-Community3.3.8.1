package com.one.tomato.dialog;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.p080ui.mine.view.NewHomePageFragment;
import com.one.tomato.mvp.p080ui.post.view.PostAndMemberReportActivity;

/* loaded from: classes3.dex */
public class HomePageActionDialog extends BottomSheetDialog implements View.OnClickListener {
    private ImageView iv_focus;
    private ImageView iv_shield;
    private LinearLayout ll_focus;
    private LinearLayout ll_report;
    private LinearLayout ll_shield;
    private NewHomePageFragment myHomePageActivity;
    private TextView tv_cancel;
    private TextView tv_focus;
    private TextView tv_shield;
    private UserInfo userInfo;

    public HomePageActionDialog(@NonNull NewHomePageFragment newHomePageFragment) {
        super(newHomePageFragment.getContext());
        this.myHomePageActivity = newHomePageFragment;
        View inflate = LayoutInflater.from(newHomePageFragment.getContext()).inflate(R.layout.dialog_home_page_action, (ViewGroup) null);
        setContentView(inflate);
        this.ll_report = (LinearLayout) inflate.findViewById(R.id.ll_report);
        this.ll_focus = (LinearLayout) inflate.findViewById(R.id.ll_focus);
        this.iv_focus = (ImageView) inflate.findViewById(R.id.iv_focus);
        this.tv_focus = (TextView) inflate.findViewById(R.id.tv_focus);
        this.ll_shield = (LinearLayout) inflate.findViewById(R.id.ll_shield);
        this.iv_shield = (ImageView) inflate.findViewById(R.id.iv_shield);
        this.tv_shield = (TextView) inflate.findViewById(R.id.tv_shield);
        this.tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        this.ll_report.setOnClickListener(this);
        this.ll_focus.setOnClickListener(this);
        this.ll_shield.setOnClickListener(this);
        this.tv_cancel.setOnClickListener(this);
    }

    public void setUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        this.userInfo = userInfo;
        if (userInfo.getFollowFlag() == 1) {
            this.iv_focus.setSelected(true);
            this.tv_focus.setTextColor(this.myHomePageActivity.getResources().getColor(R.color.colorAccent));
            this.tv_focus.setText(R.string.common_focus_y);
        } else {
            this.iv_focus.setSelected(false);
            this.tv_focus.setTextColor(this.myHomePageActivity.getResources().getColor(R.color.text_middle));
            this.tv_focus.setText(R.string.common_focus_n);
        }
        if (userInfo.getShieldFlag() == 1) {
            this.iv_shield.setSelected(true);
            this.tv_shield.setTextColor(this.myHomePageActivity.getResources().getColor(R.color.colorAccent));
            this.tv_shield.setText(R.string.post_shield_cancel);
            return;
        }
        this.iv_shield.setSelected(false);
        this.tv_shield.setTextColor(this.myHomePageActivity.getResources().getColor(R.color.text_middle));
        this.tv_shield.setText(R.string.post_shield);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_focus /* 2131297558 */:
                dismiss();
                this.myHomePageActivity.focus();
                return;
            case R.id.ll_report /* 2131297655 */:
                dismiss();
                PostAndMemberReportActivity.startActivity(this.myHomePageActivity.getContext(), "member", this.userInfo.getMemberId());
                return;
            case R.id.ll_shield /* 2131297668 */:
                dismiss();
                if (this.userInfo.getShieldFlag() == 1) {
                    this.myHomePageActivity.cancelShield();
                    return;
                } else {
                    this.myHomePageActivity.shield();
                    return;
                }
            case R.id.tv_cancel /* 2131298712 */:
                dismiss();
                return;
            default:
                return;
        }
    }
}
