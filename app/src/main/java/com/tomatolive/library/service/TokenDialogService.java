package com.tomatolive.library.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.p002v4.content.LocalBroadcastManager;
import com.tomatolive.library.utils.ConstantUtils;

/* loaded from: classes3.dex */
public class TokenDialogService extends IntentService {
    public TokenDialogService() {
        super("TokenDialogService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(500L);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ConstantUtils.LIVE_TOKEN_INVALID_ACTION));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
