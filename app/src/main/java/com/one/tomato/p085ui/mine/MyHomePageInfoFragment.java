package com.one.tomato.p085ui.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.PostSearchTagAdapter;
import com.one.tomato.base.BaseFragment;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.tomatolive.library.utils.ConstantUtils;
import com.zhy.view.flowlayout.TagFlowLayout;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_home_page_info)
/* renamed from: com.one.tomato.ui.mine.MyHomePageInfoFragment */
/* loaded from: classes3.dex */
public class MyHomePageInfoFragment extends BaseFragment {
    private boolean isMySelf;
    @ViewInject(R.id.tag_layout)
    private TagFlowLayout tag_layout;
    @ViewInject(R.id.tv_account_clear)
    private TextView tv_account_clear;
    @ViewInject(R.id.tv_experience)
    private TextView tv_experience;
    @ViewInject(R.id.tv_location)
    private TextView tv_location;
    @ViewInject(R.id.tv_register_time)
    private TextView tv_register_time;
    @ViewInject(R.id.tv_role)
    private TextView tv_role;
    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;
    @ViewInject(R.id.tv_signature)
    private TextView tv_signature;
    private UserInfo userInfo;
    private int userInfoVersion;
    private View view;

    @Override // com.one.tomato.base.BaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = super.onCreateView(layoutInflater, viewGroup, bundle);
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        if (this.userInfo == null) {
            this.userInfo = new UserInfo();
        }
        this.isMySelf = this.userInfo.getMemberId() == DBUtil.getMemberId();
        if (this.isMySelf) {
            this.userInfoVersion = DBUtil.getUserInfo().getLocalVersion();
        }
        initInfo();
    }

    @Override // com.one.tomato.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (!this.isMySelf || this.userInfoVersion >= DBUtil.getUserInfo().getLocalVersion()) {
            return;
        }
        this.userInfoVersion = DBUtil.getUserInfo().getLocalVersion();
        initInfo();
    }

    private void initInfo() {
        String string;
        String upHostType = this.userInfo.getUpHostType();
        if (!TextUtils.isEmpty(upHostType)) {
            if (upHostType.equals("1")) {
                string = AppUtil.getString(R.string.my_up_y1);
            } else {
                string = upHostType.equals("2") ? AppUtil.getString(R.string.my_up_y2) : "";
            }
        } else {
            int userType = this.userInfo.getUserType();
            if (userType == 0 || userType == 1 || userType == 2) {
                string = AppUtil.getString(R.string.user_role_normal);
            } else if (userType == 3) {
                string = AppUtil.getString(R.string.user_role_vip);
            } else {
                string = AppUtil.getString(R.string.user_role_manager);
            }
        }
        if (this.userInfo.isMemberIsAnchor()) {
            string = string + ConstantUtils.PLACEHOLDER_STR_ONE + AppUtil.getString(R.string.user_anchor);
        }
        if (this.userInfo.getReviewType() == 1) {
            string = string + ConstantUtils.PLACEHOLDER_STR_ONE + AppUtil.getString(R.string.review_daren);
        }
        this.tv_role.setText(AppUtil.getString(R.string.home_page_role) + string);
        this.tv_experience.setText(AppUtil.getString(R.string.home_page_experience, Integer.valueOf(DBUtil.getLevelBean().getCurrentLevelValue())));
        this.tv_sex.setText(AppUtil.getString(R.string.home_page_sex) + "：" + this.userInfo.getSexDes());
        this.tv_location.setText(AppUtil.getString(R.string.home_page_location) + "：" + this.userInfo.getLocation());
        this.tv_register_time.setText(AppUtil.getString(R.string.home_page_register_time) + "：" + this.userInfo.getCreateTime());
        if (this.userInfo.getAccountStatus() != null && !this.userInfo.getAccountStatus().isEmpty()) {
            this.tv_account_clear.setVisibility(8);
            this.tag_layout.setVisibility(0);
            this.tag_layout.setAdapter(new PostSearchTagAdapter(this.mContext, this.tag_layout, this.userInfo.getAccountStatus()));
        } else {
            this.tv_account_clear.setVisibility(0);
            this.tag_layout.setVisibility(8);
        }
        this.tv_signature.setText(this.userInfo.getSignatureHint());
    }
}
