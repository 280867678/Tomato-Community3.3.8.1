package com.gen.p059mh.webapp_extensions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p005v7.app.AppCompatActivity;
import com.gen.p059mh.webapp_extensions.IActivityConfig;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.gen.p059mh.webapp_extensions.fragments.WebAppFragment;
import com.gen.p059mh.webapp_extensions.listener.ErrorEventListener;
import com.gen.p059mh.webapp_extensions.utils.DeviceUtils;
import com.gen.p059mh.webapp_extensions.utils.Tool;
import com.gen.p059mh.webapps.listener.IErrorInfo;
import com.gen.p059mh.webapps.listener.OnBackPressedListener;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.WACrypto;
import com.gyf.immersionbar.ImmersionBar;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.activities.WebAppActivity */
/* loaded from: classes2.dex */
public class WebAppActivity extends AppCompatActivity implements IActivityConfig {
    public static final String AEM = "is_use_aem";
    public static final String APP_ID_KEY = "appID";
    public static final String CLOSE_BUTTON_HIDDEN = "close_button_hidden";
    public static final String CONTAINER_ID = "container_id";
    public static final String DEFAULT_ID_KEY = "defaultID";
    public static final String DEFAULT_PATH_KEY = "defaultsPath";
    public static final String FRAGMENT_CLASS_KEY = "FragmentClass";
    public static final String INIT_PARAMS = "initParams";
    public static final String IS_ON_LINE = "is_on_line";
    public static final String ON_LINE_CONFIG = "on_line_config";
    public static final String ON_LINE_COVER = "on_line_cover";
    public static final String ON_LINE_CRYPTO = "on_line_crypto";
    public static final String WINDOW_FEATURE = "window_feature";
    public static final String WORK_PATH_KEY = "workPath";

    /* renamed from: e */
    ErrorEventListener f1289e = new ErrorEventListener(this) { // from class: com.gen.mh.webapp_extensions.activities.WebAppActivity.2
        @Override // com.gen.p059mh.webapp_extensions.listener.ErrorEventListener
        public void onErrorEvent(IErrorInfo iErrorInfo) {
            Logger.m4115e("error list" + iErrorInfo.getMessage());
        }
    };
    WebAppFragment fragment;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        Map map;
        requestWindowFeature(1);
        Intent intent = getIntent();
        if (intent.hasExtra(ON_LINE_CONFIG) && (map = (Map) intent.getSerializableExtra(ON_LINE_CONFIG)) != null && map.containsKey("window")) {
            Map map2 = (Map) map.get("window");
            if (map2.containsKey("pageOrientation")) {
                Tool.checkOrientation(map2.get("pageOrientation").toString(), this);
            }
        }
        super.onCreate(bundle);
        Logger.m4112i("ScreenHeight", "WebAppActivity height = " + DeviceUtils.getScreenHeight(this));
        Logger.m4112i("ScreenWidth", "WebAppActivity Width = " + DeviceUtils.getScreenWidth(this));
        setContentView(R$layout.web_sdk_app_activity);
        if (intent != null && intent.hasExtra(FRAGMENT_CLASS_KEY)) {
            try {
                this.fragment = (WebAppFragment) Class.forName(intent.getStringExtra(FRAGMENT_CLASS_KEY)).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.fragment == null) {
            this.fragment = new MainFragment();
        }
        this.fragment.setOnBackPressedListener(new OnBackPressedListener() { // from class: com.gen.mh.webapp_extensions.activities.WebAppActivity.1
            @Override // com.gen.p059mh.webapps.listener.OnBackPressedListener
            public void onPressed() {
                WebAppActivity.super.onBackPressed();
            }
        });
        if (intent != null) {
            if (intent.hasExtra(IS_ON_LINE)) {
                this.fragment.setOnline(intent.getBooleanExtra(IS_ON_LINE, false));
            }
            if (intent.hasExtra(ON_LINE_CRYPTO)) {
                this.fragment.setOnLineCrypto((WACrypto) intent.getSerializableExtra(ON_LINE_CRYPTO));
            }
            if (intent.hasExtra(ON_LINE_CONFIG)) {
                this.fragment.setOnlineConfig((Map) intent.getSerializableExtra(ON_LINE_CONFIG));
            }
            if (intent.hasExtra(ON_LINE_COVER)) {
                this.fragment.setOnlineCover((Map) intent.getSerializableExtra(ON_LINE_COVER));
            }
            if (intent.hasExtra(AEM)) {
                this.fragment.setAem(intent.getBooleanExtra(AEM, false));
            }
            Bundle bundle2 = new Bundle();
            bundle2.putInt(CONTAINER_ID, R$id.main_container);
            if (intent.hasExtra("webapp_opener_id")) {
                bundle2.putInt("webapp_opener_id", intent.getIntExtra("webapp_opener_id", -1));
            }
            this.fragment.setArguments(bundle2);
            if (intent.hasExtra(DEFAULT_PATH_KEY)) {
                this.fragment.setDefaultsPath(intent.getStringExtra(DEFAULT_PATH_KEY));
                this.fragment.setLocalDefaults(true);
            }
            if (intent.hasExtra(WORK_PATH_KEY)) {
                this.fragment.setWorkPath(intent.getStringExtra(WORK_PATH_KEY));
            }
            if (intent.hasExtra(APP_ID_KEY)) {
                this.fragment.setAppID(intent.getStringExtra(APP_ID_KEY));
            }
            if (intent.hasExtra(DEFAULT_ID_KEY)) {
                this.fragment.setDefaultsID(intent.getStringExtra(DEFAULT_ID_KEY));
            }
            if (intent.hasExtra(INIT_PARAMS)) {
                this.fragment.setInitParams((HashMap) intent.getSerializableExtra(INIT_PARAMS));
            }
            if (intent.hasExtra(CLOSE_BUTTON_HIDDEN)) {
                this.fragment.setCloseButtonHidden(intent.getBooleanExtra(CLOSE_BUTTON_HIDDEN, false));
            }
            getWindow().clearFlags(1024);
            ImmersionBar with = ImmersionBar.with(this);
            with.statusBarDarkFont(true);
            with.init();
            if (intent.hasExtra(WINDOW_FEATURE)) {
                checkConfig(intent.getIntExtra(WINDOW_FEATURE, 0));
            }
        }
        getSupportFragmentManager().beginTransaction().add(R$id.main_container, this.fragment).commitAllowingStateLoss();
    }

    public void addEventListener() {
        WebAppFragment webAppFragment = this.fragment;
        if (webAppFragment != null) {
            webAppFragment.addEventListener(this.f1289e);
        }
    }

    public void removeEventListener() {
        WebAppFragment webAppFragment = this.fragment;
        if (webAppFragment != null) {
            webAppFragment.removeEventListener(this.f1289e);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.IActivityConfig
    public void checkConfig(int i) {
        if (i == 0) {
            return;
        }
        if ((i & 1) != 0) {
            getWindow().setFlags(1024, 1024);
        } else if ((i & 2) != 0) {
            getWindow().clearFlags(1024);
        }
        if ((i & 4) != 0) {
            setRequestedOrientation(1);
        } else if ((i & 8) != 0) {
            setRequestedOrientation(0);
        }
        if ((i & 16) != 0) {
            ImmersionBar with = ImmersionBar.with(this);
            with.statusBarDarkFont(false);
            with.init();
        } else if ((i & 32) == 0) {
        } else {
            ImmersionBar with2 = ImmersionBar.with(this);
            with2.statusBarDarkFont(true);
            with2.init();
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        Tool.getTopFragment(getSupportFragmentManager()).onBackPressed();
    }
}
