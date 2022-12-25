package com.p089pm.liquidlink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.p005v7.app.AppCompatActivity;
import com.p089pm.liquidlink.LiquidLink;
import com.p089pm.liquidlink.listener.AppWakeUpAdapter;
import com.p089pm.liquidlink.model.AppData;

/* renamed from: com.pm.liquidlink.activity.LiquidLinkAppCompatActivity */
/* loaded from: classes3.dex */
public abstract class LiquidLinkAppCompatActivity extends AppCompatActivity {
    Intent liquidlinkIntent;
    AppWakeUpAdapter liquidlinkWakeUpAdapter;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.liquidlinkWakeUpAdapter = new AppWakeUpAdapter() { // from class: com.pm.liquidlink.activity.LiquidLinkAppCompatActivity.1
            @Override // com.p089pm.liquidlink.listener.AppWakeUpAdapter
            public void onWakeUp(AppData appData) {
                LiquidLinkAppCompatActivity.this.wakeup(appData);
            }
        };
        this.liquidlinkIntent = getIntent();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.liquidlinkIntent = null;
        this.liquidlinkWakeUpAdapter = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.liquidlinkIntent = intent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        LiquidLink.getWakeUpYYB(this.liquidlinkIntent, this.liquidlinkWakeUpAdapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        getIntent().removeExtra("liquidlink_intent");
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        intent.putExtra("liquidlink_intent", true);
        super.startActivityForResult(intent, i, bundle);
    }

    protected abstract void wakeup(AppData appData);
}
