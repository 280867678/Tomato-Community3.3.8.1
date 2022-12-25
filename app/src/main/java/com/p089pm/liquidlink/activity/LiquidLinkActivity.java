package com.p089pm.liquidlink.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.p089pm.liquidlink.LiquidLink;
import com.p089pm.liquidlink.listener.AppWakeUpAdapter;
import com.p089pm.liquidlink.model.AppData;

/* renamed from: com.pm.liquidlink.activity.LiquidLinkActivity */
/* loaded from: classes3.dex */
public abstract class LiquidLinkActivity extends Activity {
    Intent liquidlinkIntent;
    AppWakeUpAdapter liquidlinkWakeUpAdapter;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.liquidlinkWakeUpAdapter = new AppWakeUpAdapter() { // from class: com.pm.liquidlink.activity.LiquidLinkActivity.1
            @Override // com.p089pm.liquidlink.listener.AppWakeUpAdapter
            public void onWakeUp(AppData appData) {
                LiquidLinkActivity.this.wakeup(appData);
            }
        };
        this.liquidlinkIntent = getIntent();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.liquidlinkIntent = null;
        this.liquidlinkWakeUpAdapter = null;
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.liquidlinkIntent = intent;
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        LiquidLink.getWakeUpYYB(this.liquidlinkIntent, this.liquidlinkWakeUpAdapter);
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        getIntent().removeExtra("liquidlink_intent");
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override // android.app.Activity
    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        intent.putExtra("liquidlink_intent", true);
        super.startActivityForResult(intent, i, bundle);
    }

    protected abstract void wakeup(AppData appData);
}
