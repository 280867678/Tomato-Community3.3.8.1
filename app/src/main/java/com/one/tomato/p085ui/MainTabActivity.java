package com.one.tomato.p085ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.event.PostTabStatusChange;
import com.one.tomato.entity.event.VideoTabStatusChange;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.p080ui.game.view.GameTypeScrollTabFragment;
import com.one.tomato.mvp.p080ui.mine.view.MineTabFragment;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaHomeFragment;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.post.view.NewPostTabFragment;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.one.tomato.utils.AppInitUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.TimerTaskUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UpdateManager;
import com.tomatolive.library.p136ui.fragment.LiveHomeFragment;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main_tab)
/* renamed from: com.one.tomato.ui.MainTabActivity */
/* loaded from: classes3.dex */
public class MainTabActivity extends BaseActivity implements ResponseObserver {
    private GameTypeScrollTabFragment gameSpreadFragment;
    @ViewInject(R.id.iv_find)
    private ImageView iv_find;
    @ViewInject(R.id.iv_game)
    private ImageView iv_game;
    @ViewInject(R.id.iv_live)
    private ImageView iv_live;
    @ViewInject(R.id.iv_mine)
    private ImageView iv_mine;
    @ViewInject(R.id.iv_tomato)
    private ImageView iv_tomato;
    private Fragment liveTabFragment;
    @ViewInject(R.id.main_red_point)
    private View main_red_point;
    private MineTabFragment mineTabFragment;
    private NewPaPaHomeFragment paPaHomeFragment;
    private NewPostTabFragment postTabFragment;
    private Animation tabAnimation;
    @ViewInject(R.id.tv_find)
    private TextView tv_find;
    @ViewInject(R.id.tv_game)
    private TextView tv_game;
    @ViewInject(R.id.tv_live)
    private TextView tv_live;
    @ViewInject(R.id.tv_mine)
    private TextView tv_mine;
    @ViewInject(R.id.tv_tomato)
    private TextView tv_tomato;
    private int tabItem = 0;
    private int checkedTab = -1;
    private boolean exitApp = false;
    private boolean isRefreshLive = false;

    public static void startActivity(Context context, int i) {
        Intent intent = new Intent();
        intent.setClass(context, MainTabActivity.class);
        intent.putExtra("tabItem", i);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        UpdateManager.getUpdateManager().checkAppUpdate(this, false);
        if (getIntent() != null && getIntent().getExtras() != null) {
            this.tabItem = getIntent().getExtras().getInt("tabItem");
        }
        registerReceiver();
        queryMyMessage();
        selectTab(this.tabItem);
    }

    @Event({R.id.ll_tomato, R.id.rl_game, R.id.ll_live, R.id.ll_find, R.id.ll_mine})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_find /* 2131297556 */:
                selectTab(3);
                return;
            case R.id.ll_live /* 2131297587 */:
                selectTab(2);
                return;
            case R.id.ll_mine /* 2131297599 */:
                this.main_red_point.setVisibility(8);
                selectTab(4);
                return;
            case R.id.ll_tomato /* 2131297689 */:
                selectTab(0);
                return;
            case R.id.rl_game /* 2131298065 */:
                selectTab(1);
                return;
            default:
                return;
        }
    }

    public void selectTab(int i) {
        int i2 = this.checkedTab;
        if (i2 == i) {
            if (i2 == 0) {
                PostTabStatusChange postTabStatusChange = new PostTabStatusChange();
                postTabStatusChange.refresh = true;
                RxBus.getDefault().post(postTabStatusChange);
            }
            if (this.checkedTab == 3) {
                VideoTabStatusChange videoTabStatusChange = new VideoTabStatusChange();
                videoTabStatusChange.refresh = true;
                RxBus.getDefault().post(videoTabStatusChange);
            }
            changeTabStatus(i);
            return;
        }
        hideAllFragment();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        changeTabStatus(i);
        if (i == 0) {
            NewPostTabFragment newPostTabFragment = this.postTabFragment;
            if (newPostTabFragment == null) {
                this.postTabFragment = new NewPostTabFragment();
                beginTransaction.add(R.id.fl_content, this.postTabFragment);
            } else {
                beginTransaction.show(newPostTabFragment);
            }
        } else if (i == 1) {
            GameTypeScrollTabFragment gameTypeScrollTabFragment = this.gameSpreadFragment;
            if (gameTypeScrollTabFragment == null) {
                this.gameSpreadFragment = new GameTypeScrollTabFragment();
                beginTransaction.add(R.id.fl_content, this.gameSpreadFragment);
            } else {
                beginTransaction.show(gameTypeScrollTabFragment);
            }
        } else if (i == 2) {
            TomatoLiveSDKUtils.getSingleton().updateServerUrl();
            TomatoLiveSDKUtils.getSingleton().initAnim();
            Fragment fragment = this.liveTabFragment;
            if (fragment == null) {
                this.liveTabFragment = LiveHomeFragment.newInstance();
                beginTransaction.add(R.id.fl_content, this.liveTabFragment);
            } else {
                beginTransaction.show(fragment);
            }
        } else if (i == 3) {
            NewPaPaHomeFragment newPaPaHomeFragment = this.paPaHomeFragment;
            if (newPaPaHomeFragment == null) {
                this.paPaHomeFragment = new NewPaPaHomeFragment();
                beginTransaction.add(R.id.fl_content, this.paPaHomeFragment);
            } else {
                beginTransaction.show(newPaPaHomeFragment);
            }
        } else if (i == 4) {
            MineTabFragment mineTabFragment = this.mineTabFragment;
            if (mineTabFragment == null) {
                this.mineTabFragment = new MineTabFragment();
                beginTransaction.add(R.id.fl_content, this.mineTabFragment);
            } else {
                beginTransaction.show(mineTabFragment);
            }
        }
        beginTransaction.commitAllowingStateLoss();
        this.checkedTab = i;
        if (!PreferencesUtil.getInstance().getBoolean("need_upgrade") || UpdateManager.cancelUpdate) {
            return;
        }
        UpdateManager.getUpdateManager().checkAppUpdate(this, false);
    }

    private void changeTabStatus(int i) {
        clearTabStatus();
        if (this.tabAnimation == null) {
            this.tabAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.main_tab);
        }
        if (i == 0) {
            this.iv_tomato.setSelected(true);
            this.iv_tomato.startAnimation(this.tabAnimation);
            this.tv_tomato.setTextColor(getResources().getColor(R.color.colorHomeTabText));
        } else if (i == 1) {
            this.iv_game.setSelected(true);
            this.iv_game.startAnimation(this.tabAnimation);
            this.tv_game.setTextColor(getResources().getColor(R.color.colorHomeTabText));
        } else if (i == 2) {
            this.iv_live.setSelected(true);
            this.iv_live.startAnimation(this.tabAnimation);
            this.tv_live.setTextColor(getResources().getColor(R.color.colorHomeTabText));
        } else if (i == 3) {
            this.iv_find.setSelected(true);
            this.iv_find.startAnimation(this.tabAnimation);
            this.tv_find.setTextColor(getResources().getColor(R.color.colorHomeTabText));
        } else if (i != 4) {
        } else {
            this.iv_mine.setSelected(true);
            this.iv_mine.startAnimation(this.tabAnimation);
            this.tv_mine.setTextColor(getResources().getColor(R.color.colorHomeTabText));
        }
    }

    private void clearTabStatus() {
        this.iv_tomato.setSelected(false);
        this.tv_tomato.setTextColor(getResources().getColor(R.color.main_tab_text_color));
        this.iv_game.setSelected(false);
        this.tv_game.setTextColor(getResources().getColor(R.color.main_tab_text_color));
        this.iv_live.setSelected(false);
        this.tv_live.setTextColor(getResources().getColor(R.color.main_tab_text_color));
        this.iv_find.setSelected(false);
        this.tv_find.setTextColor(getResources().getColor(R.color.main_tab_text_color));
        this.iv_mine.setSelected(false);
        this.tv_mine.setTextColor(getResources().getColor(R.color.main_tab_text_color));
    }

    private void hideAllFragment() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        NewPostTabFragment newPostTabFragment = this.postTabFragment;
        if (newPostTabFragment != null && newPostTabFragment.isVisible()) {
            beginTransaction.hide(this.postTabFragment);
        }
        GameTypeScrollTabFragment gameTypeScrollTabFragment = this.gameSpreadFragment;
        if (gameTypeScrollTabFragment != null && gameTypeScrollTabFragment.isVisible()) {
            beginTransaction.hide(this.gameSpreadFragment);
        }
        Fragment fragment = this.liveTabFragment;
        if (fragment != null && fragment.isVisible()) {
            beginTransaction.hide(this.liveTabFragment);
        }
        NewPaPaHomeFragment newPaPaHomeFragment = this.paPaHomeFragment;
        if (newPaPaHomeFragment != null && newPaPaHomeFragment.isVisible()) {
            beginTransaction.hide(this.paPaHomeFragment);
        }
        MineTabFragment mineTabFragment = this.mineTabFragment;
        if (mineTabFragment != null && mineTabFragment.isVisible()) {
            beginTransaction.hide(this.mineTabFragment);
        }
        beginTransaction.commitAllowingStateLoss();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i != 20) {
                if (i == 30) {
                    NewPaPaHomeFragment newPaPaHomeFragment = this.paPaHomeFragment;
                    if (newPaPaHomeFragment == null || !newPaPaHomeFragment.isVisible() || this.paPaHomeFragment.isHidden()) {
                        return;
                    }
                    this.paPaHomeFragment.onActivityResult(i, i2, intent);
                    return;
                } else if (i != 188) {
                    return;
                }
            }
            this.postTabFragment.onActivityResult(i, i2, intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        Fragment fragment;
        super.onResume();
        if (DBUtil.getMemberId() == 0) {
            AppInitUtil.initAppInfoFromServer(false);
        }
        if (!this.isRefreshLive || (fragment = this.liveTabFragment) == null || fragment.isHidden()) {
            return;
        }
        if (this.checkedTab == 2) {
            selectTab(2);
        }
        this.isRefreshLive = false;
    }

    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        TimerTaskUtil.getInstance().onStop();
        unregisterReceiver();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (PostUtils.INSTANCE.notifyBackObserver(true)) {
            return;
        }
        if (this.exitApp) {
            AppManager.getAppManager().exitApp();
            return;
        }
        ToastUtil.showCenterToast((int) R.string.app_exit);
        this.exitApp = true;
        DataUploadUtil.uploadUseTime();
        TimerTaskUtil.getInstance().onStart(2, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.ui.MainTabActivity.1
            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void position(int i) {
            }

            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void stop() {
                MainTabActivity.this.exitApp = false;
            }
        });
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        this.isRefreshLive = true;
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.LoginResponseObserver
    public void onLoginCancel() {
        super.onLoginCancel();
        this.isRefreshLive = true;
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what != 90) {
            return;
        }
        String str = baseModel.data;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            int i = jSONObject.getInt("1");
            int i2 = jSONObject.getInt("2");
            int i3 = jSONObject.getInt("4");
            int i4 = jSONObject.getInt("6");
            int i5 = jSONObject.getInt("8");
            int i6 = jSONObject.getInt("9");
            int i7 = i + i2 + i3 + i4 + i5 + i6 + jSONObject.getInt("10") + jSONObject.getInt("3");
            if (i7 == 0) {
                return;
            }
            BaseApplication.getApplication();
            BaseApplication.setIsMyMessageHave(i7);
            this.main_red_point.setVisibility(0);
        } catch (Exception unused) {
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        return false;
    }

    private void queryMyMessage() {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/messageCenter/queryUnReadMessageCount");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 90));
        LogUtil.m3785e("TAG", "查询消息数量 ：" + tomatoParams.getUri());
    }
}
