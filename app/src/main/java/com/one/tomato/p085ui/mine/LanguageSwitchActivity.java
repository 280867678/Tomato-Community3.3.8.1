package com.one.tomato.p085ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.p080ui.chess.view.ChessMainTabActivity;
import com.one.tomato.p085ui.MainTabActivity;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.one.tomato.utils.LanguageSwithUtils;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.widget.MineCustomItemLayout;
import java.util.Locale;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_language_switch)
/* renamed from: com.one.tomato.ui.mine.LanguageSwitchActivity */
/* loaded from: classes3.dex */
public class LanguageSwitchActivity extends BaseActivity {
    @ViewInject(R.id.lange_english)
    private MineCustomItemLayout english;
    @ViewInject(R.id.lange_fanti)
    private MineCustomItemLayout fanti;
    @ViewInject(R.id.lange_jianti)
    private MineCustomItemLayout jianti;
    private String langue = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(getResources().getString(R.string.string_language));
        this.langue = PreferencesUtil.getInstance().getString("language_country");
        if (!TextUtils.isEmpty(this.langue)) {
            String str = this.langue;
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 2155) {
                if (hashCode != 2691) {
                    if (hashCode == 2718 && str.equals("US")) {
                        c = 2;
                    }
                } else if (str.equals("TW")) {
                    c = 0;
                }
            } else if (str.equals("CN")) {
                c = 1;
            }
            if (c == 0) {
                this.fanti.getRightImg().setImageResource(R.drawable.icon_choose_lanuge);
            } else if (c == 1) {
                this.jianti.getRightImg().setImageResource(R.drawable.icon_choose_lanuge);
            } else if (c == 2) {
                this.english.getRightImg().setImageResource(R.drawable.icon_choose_lanuge);
            }
        }
        onClick();
    }

    private void onClick() {
        this.jianti.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.LanguageSwitchActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PreferencesUtil.getInstance().putString("language_country", "CN");
                PreferencesUtil.getInstance().putString("language_lan", "zh");
                if (LanguageSwithUtils.INSTANCE.isSameWithSetting(((BaseActivity) LanguageSwitchActivity.this).mContext)) {
                    return;
                }
                LanguageSwitchActivity.this.jianti.getRightImg().setImageResource(R.drawable.icon_choose_lanuge);
                LanguageSwitchActivity.this.fanti.getRightImg().setVisibility(8);
                LanguageSwitchActivity.this.english.getRightImg().setVisibility(8);
                LanguageSwitchActivity.this.changeLanguage("zh", "CN");
            }
        });
        this.fanti.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.LanguageSwitchActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PreferencesUtil.getInstance().putString("language_country", "TW");
                PreferencesUtil.getInstance().putString("language_lan", "zh");
                if (LanguageSwithUtils.INSTANCE.isSameWithSetting(((BaseActivity) LanguageSwitchActivity.this).mContext)) {
                    return;
                }
                LanguageSwitchActivity.this.jianti.getRightImg().setVisibility(8);
                LanguageSwitchActivity.this.fanti.getRightImg().setImageResource(R.drawable.icon_choose_lanuge);
                LanguageSwitchActivity.this.english.getRightImg().setVisibility(8);
                LanguageSwitchActivity.this.changeLanguage("zh", "TW");
            }
        });
        this.english.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.LanguageSwitchActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PreferencesUtil.getInstance().putString("language_country", "US");
                PreferencesUtil.getInstance().putString("language_lan", "en");
                if (LanguageSwithUtils.INSTANCE.isSameWithSetting(((BaseActivity) LanguageSwitchActivity.this).mContext)) {
                    return;
                }
                LanguageSwitchActivity.this.jianti.getRightImg().setVisibility(8);
                LanguageSwitchActivity.this.fanti.getRightImg().setVisibility(8);
                LanguageSwitchActivity.this.english.getRightImg().setImageResource(R.drawable.icon_choose_lanuge);
                LanguageSwitchActivity.this.changeLanguage("en", "US");
            }
        });
    }

    private void setLanLive() {
        String string = PreferencesUtil.getInstance().getString("language_country");
        if (!TextUtils.isEmpty(string)) {
            char c = 65535;
            int hashCode = string.hashCode();
            if (hashCode != 2155) {
                if (hashCode != 2691) {
                    if (hashCode == 2718 && string.equals("US")) {
                        c = 2;
                    }
                } else if (string.equals("TW")) {
                    c = 0;
                }
            } else if (string.equals("CN")) {
                c = 1;
            }
            if (c == 0) {
                TomatoLiveSDKUtils.getSingleton().updateLanguage(this, 3);
            } else if (c == 1) {
                TomatoLiveSDKUtils.getSingleton().updateLanguage(this, 2);
            } else if (c != 2) {
            } else {
                TomatoLiveSDKUtils.getSingleton().updateLanguage(this, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeLanguage(String str, String str2) {
        Intent intent;
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            PreferencesUtil.getInstance().putString("language_lan", "zh");
            PreferencesUtil.getInstance().putString("language_country", "TW");
        } else {
            Locale locale = new Locale(str, str2);
            LanguageSwithUtils.INSTANCE.changeAppLanguage(this, locale, true);
            LanguageSwithUtils.INSTANCE.changeAppLanguage(BaseApplication.getApplication(), locale, true);
        }
        setLanLive();
        if (BaseApplication.instance.isChess()) {
            intent = new Intent(BaseApplication.getApplication().getApplicationContext(), ChessMainTabActivity.class);
        } else {
            intent = new Intent(BaseApplication.getApplication().getApplicationContext(), MainTabActivity.class);
        }
        intent.addFlags(268468224);
        BaseApplication.getApplication().getApplicationContext().startActivity(intent);
    }
}
