package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.custom.UserNickNameGradeView;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.view.dialog.UserSuperAvatarDialog */
/* loaded from: classes3.dex */
public class UserSuperAvatarDialog extends BaseDialogFragment {
    private static final String AUDIENCEEXPGRADE = "audienceExpGrade";
    private static final String AVATAR = "avatar";
    private static final String GUARD_TYPE = "targetGuardType";
    private static final String MANAGER = "manager";
    private static final String NAME = "name";
    private static final String ROLE = "role";
    private static final String SEX = "sex";
    private static final String TIPS = "tips";
    private static final String USER_ID = "id";

    public static UserSuperAvatarDialog newInstance(String str, String str2, String str3, String str4, String str5, String str6, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("id", str);
        bundle.putString(AVATAR, str2);
        bundle.putString("name", str3);
        bundle.putString(SEX, str4);
        bundle.putString(TIPS, str5);
        bundle.putString(AUDIENCEEXPGRADE, str6);
        bundle.putInt(GUARD_TYPE, i);
        UserSuperAvatarDialog userSuperAvatarDialog = new UserSuperAvatarDialog();
        userSuperAvatarDialog.setArguments(bundle);
        return userSuperAvatarDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_live_super_avatar;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        ImageView imageView = (ImageView) view.findViewById(R$id.iv_avatar_bg);
        ImageView imageView2 = (ImageView) view.findViewById(R$id.iv_guard_type);
        GlideUtils.loadAvatar(getActivity(), (ImageView) view.findViewById(R$id.iv_avatar), getArgumentsString(AVATAR), 6, ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
        ((TextView) view.findViewById(R$id.tv_id)).setText(getString(R$string.fq_live_room_id, getArgumentsString("id")));
        ((TextView) view.findViewById(R$id.tv_tips)).setText(R$string.fq_avatar_dialog_sign_tips);
        ((UserNickNameGradeView) view.findViewById(R$id.user_nickname)).initData(getArgumentsString("name"), R$color.fq_text_white_color, getArgumentsString(SEX), getArgumentsString(AUDIENCEEXPGRADE));
        if (AppUtils.isGuardUser(getArgumentsInt(GUARD_TYPE))) {
            imageView.setImageResource(AppUtils.isYearGuard(getArgumentsInt(GUARD_TYPE)) ? R$drawable.fq_ic_guard_year_avatar_bg_big : R$drawable.fq_ic_guard_month_avatar_bg_big);
            imageView2.setImageResource(AppUtils.isYearGuard(getArgumentsInt(GUARD_TYPE)) ? R$drawable.fq_ic_live_msg_year_guard : R$drawable.fq_ic_live_msg_mouth_guard);
        }
    }
}
