package com.tomatolive.library.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.p002v4.content.LocalBroadcastManager;
import com.tomatolive.library.utils.ConstantUtils;

/* loaded from: classes3.dex */
public class KickDialogService extends IntentService {
    public KickDialogService() {
        super("KickDialogService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(500L);
            Intent intent2 = new Intent(ConstantUtils.LIVE_KICK_OUT_ACTION);
            intent2.putExtra(ConstantUtils.RESULT_ITEM, intent.getStringExtra(ConstantUtils.RESULT_ITEM));
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
