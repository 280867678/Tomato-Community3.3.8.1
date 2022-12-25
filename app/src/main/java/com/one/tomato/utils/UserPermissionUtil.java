package com.one.tomato.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.CreditPermissionDialog;
import com.one.tomato.dialog.LevelPermissionDialog;
import com.one.tomato.entity.CircleDetail;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.mvp.p080ui.circle.view.CircleSingleActivity;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.mvp.p080ui.mine.view.MyInteractionActivity;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.p085ui.MainTabActivity;
import com.one.tomato.p085ui.feedback.FeedbackEnterActivity;
import com.one.tomato.p085ui.messge.p086ui.MyMessageNotifctionActivity;
import com.one.tomato.p085ui.mine.MyShareActivity;
import com.one.tomato.p085ui.mine.PersonInfoActivity;
import com.one.tomato.p085ui.setting.SettingActivity;
import com.one.tomato.p085ui.task.TaskCenterActivity;

/* loaded from: classes3.dex */
public class UserPermissionUtil {
    private static UserPermissionUtil instance;

    public static UserPermissionUtil getInstance() {
        if (instance == null) {
            instance = new UserPermissionUtil();
        }
        return instance;
    }

    public boolean isPermissionEnable(int i) {
        Activity currentActivity = AppManager.getAppManager().currentActivity();
        LevelBean levelBean = DBUtil.getLevelBean();
        int currentLevelIndex = levelBean.getCurrentLevelIndex();
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    if (DBUtil.getUserInfo().getVipType() > 0 && DBUtil.getUserInfo().getVipType() < 5) {
                        return true;
                    }
                    if (currentLevelIndex < 2) {
                        new LevelPermissionDialog(currentActivity, i);
                        return false;
                    } else if (levelBean.getCommentCount() == 0 || levelBean.getCommentCount_times() == levelBean.getCommentCount()) {
                        new CreditPermissionDialog(currentActivity, i, levelBean);
                        return false;
                    }
                } else if (i != 4 || (DBUtil.getUserInfo().getVipType() > 0 && DBUtil.getUserInfo().getVipType() < 5)) {
                    return true;
                } else {
                    if (currentLevelIndex < 2) {
                        new LevelPermissionDialog(currentActivity, i);
                        return false;
                    } else if (levelBean.getReplyCount() == 0 || levelBean.getReplyCount_times() == levelBean.getReplyCount()) {
                        new CreditPermissionDialog(currentActivity, i, levelBean);
                        return false;
                    }
                }
            } else if (DBUtil.getUserInfo().getVipType() <= 0 && currentLevelIndex < 2) {
                new LevelPermissionDialog(currentActivity, i);
                return false;
            }
        } else if (DBUtil.getUserInfo().getVipType() > 0) {
            return true;
        } else {
            if (currentLevelIndex < 2) {
                new LevelPermissionDialog(currentActivity, i);
                return false;
            } else if (levelBean.getPubCount() == 0 || levelBean.getPubCount_times() == levelBean.getPubCount()) {
                new CreditPermissionDialog(currentActivity, i, levelBean);
                return false;
            }
        }
        return true;
    }

    public void uerLevelShow(ImageView imageView, int i) {
        if (i >= 1 && i <= 5) {
            imageView.setImageResource(R.drawable.icon_user_level_5);
        } else if (i >= 6 && i <= 10) {
            imageView.setImageResource(R.drawable.icon_user_level_10);
        } else if (i >= 11 && i <= 15) {
            imageView.setImageResource(R.drawable.icon_user_level_15);
        } else {
            imageView.setImageResource(R.drawable.icon_user_level_20);
        }
    }

    public void uerLevelShow(LinearLayout linearLayout, ImageView imageView, TextView textView, int i) {
        Resources resources = linearLayout.getContext().getResources();
        if (i >= 1 && i <= 5) {
            imageView.setImageResource(R.drawable.icon_user_level_5);
            linearLayout.setBackgroundResource(R.drawable.shape_user_level_5);
            textView.setTextColor(resources.getColor(R.color.text_user_level_text5));
        } else if (i >= 6 && i <= 10) {
            imageView.setImageResource(R.drawable.icon_user_level_10);
            linearLayout.setBackgroundResource(R.drawable.shape_user_level_10);
            textView.setTextColor(resources.getColor(R.color.text_user_level_text10));
        } else if (i >= 11 && i <= 15) {
            imageView.setImageResource(R.drawable.icon_user_level_15);
            linearLayout.setBackgroundResource(R.drawable.shape_user_level_15);
            textView.setTextColor(resources.getColor(R.color.text_user_level_text15));
        } else {
            imageView.setImageResource(R.drawable.icon_user_level_20);
            linearLayout.setBackgroundResource(R.drawable.shape_user_level_20);
            textView.setTextColor(resources.getColor(R.color.text_user_level_text20));
        }
        textView.setText("Lv." + i);
    }

    public void userLevelNickShow(ImageView imageView, LevelBean levelBean) {
        switch (levelBean.getLevelNickIndex()) {
            case 1:
                imageView.setImageResource(R.drawable.level_nick1_s);
                return;
            case 2:
                imageView.setImageResource(R.drawable.level_nick2_s);
                return;
            case 3:
                imageView.setImageResource(R.drawable.level_nick3_s);
                return;
            case 4:
                imageView.setImageResource(R.drawable.level_nick4_s);
                return;
            case 5:
                imageView.setImageResource(R.drawable.level_nick5_s);
                return;
            case 6:
                imageView.setImageResource(R.drawable.level_nick6_s);
                return;
            default:
                return;
        }
    }

    public void userLevelNickShow(LinearLayout linearLayout, ImageView imageView, TextView textView, LevelBean levelBean) {
        switch (levelBean.getLevelNickIndex()) {
            case 1:
                linearLayout.setBackgroundResource(R.drawable.shape_user_level_nick1);
                imageView.setImageResource(R.drawable.level_nick1_s);
                textView.setTextColor(Color.parseColor("#FE6967"));
                return;
            case 2:
                linearLayout.setBackgroundResource(R.drawable.shape_user_level_nick2);
                imageView.setImageResource(R.drawable.level_nick2_s);
                textView.setTextColor(Color.parseColor("#D929E0"));
                return;
            case 3:
                linearLayout.setBackgroundResource(R.drawable.shape_user_level_nick3);
                imageView.setImageResource(R.drawable.level_nick3_s);
                textView.setTextColor(Color.parseColor("#4355D0"));
                return;
            case 4:
                linearLayout.setBackgroundResource(R.drawable.shape_user_level_nick4);
                imageView.setImageResource(R.drawable.level_nick4_s);
                textView.setTextColor(Color.parseColor("#FF9109"));
                return;
            case 5:
                linearLayout.setBackgroundResource(R.drawable.shape_user_level_nick5);
                imageView.setImageResource(R.drawable.level_nick5_s);
                textView.setTextColor(Color.parseColor("#A15E1E"));
                return;
            case 6:
                linearLayout.setBackgroundResource(R.drawable.shape_user_level_nick6);
                imageView.setImageResource(R.drawable.level_nick6_s);
                textView.setTextColor(Color.parseColor("#651202"));
                return;
            default:
                return;
        }
    }

    public void intentTask(Context context, String str, int i) {
        switch (i) {
            case 1:
                MainTabActivity.startActivity(context, 0);
                ((BaseActivity) context).finish();
                return;
            case 2:
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                NewPostDetailViewPagerActivity.Companion.startActivity(context, Integer.valueOf(str).intValue(), false, false, false);
                return;
            case 3:
                MainTabActivity.startActivity(context, 3);
                ((BaseActivity) context).finish();
                return;
            case 4:
                MainTabActivity.startActivity(context, 3);
                ((BaseActivity) context).finish();
                return;
            case 5:
                MainTabActivity.startActivity(context, 3);
                ((BaseActivity) context).finish();
                return;
            case 6:
                CircleDetail circleDetail = new CircleDetail();
                circleDetail.setId(Integer.valueOf(str).intValue());
                circleDetail.setName("");
                CircleSingleActivity.startActivity(context, circleDetail);
                return;
            case 7:
                MyInteractionActivity.Companion.startActivity(context);
                return;
            case 8:
                MyMessageNotifctionActivity.startActivity(context);
                return;
            case 9:
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                NewMyHomePageActivity.Companion.startActivity(context, Integer.valueOf(str).intValue());
                return;
            case 10:
                MainTabActivity.startActivity(context, 4);
                ((BaseActivity) context).finish();
                return;
            case 11:
                if (!DBUtil.getLoginInfo().isLogin()) {
                    LoginActivity.Companion.startActivity(context);
                    return;
                } else {
                    PersonInfoActivity.startActivity(context, "mine");
                    return;
                }
            case 12:
                TaskCenterActivity.startEarnActivity(context);
                return;
            case 13:
            default:
                return;
            case 14:
                FeedbackEnterActivity.startActivity(context);
                return;
            case 15:
                MyShareActivity.startActivity(context);
                return;
            case 16:
                SettingActivity.startActivity(context);
                return;
            case 17:
                MyShareActivity.startActivity(context);
                return;
        }
    }
}
