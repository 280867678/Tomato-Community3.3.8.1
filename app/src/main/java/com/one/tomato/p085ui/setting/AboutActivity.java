package com.one.tomato.p085ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_about)
/* renamed from: com.one.tomato.ui.setting.AboutActivity */
/* loaded from: classes3.dex */
public class AboutActivity extends BaseActivity {
    @ViewInject(R.id.tv_offical)
    private TextView tv_offical;
    @ViewInject(R.id.tv_version)
    private TextView tv_version;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AboutActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.about_app);
        TextView textView = this.tv_version;
        textView.setText(AppUtil.getString(R.string.about_app_version) + AppUtil.getVersionName());
        TextView textView2 = this.tv_offical;
        textView2.setText(AppUtil.getString(R.string.common_offcial_url) + DomainServer.getInstance().getWebsiteUrl());
    }
}
