package com.gen.p059mh.webapp_extensions.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.gen.p059mh.webapp_extensions.manager.WACacheManager;
import com.gen.p059mh.webapp_extensions.task.Task;
import com.gen.p059mh.webapps.utils.Request;
import java.net.MalformedURLException;
import java.net.URL;

/* renamed from: com.gen.mh.webapp_extensions.task.CacheTask */
/* loaded from: classes2.dex */
public class CacheTask extends Task {
    String src;

    public CacheTask(String str, String str2) {
        super(str);
        this.src = str2;
    }

    @Override // com.gen.p059mh.webapp_extensions.task.Task
    public void startTask() {
        Request request = new Request();
        try {
            request.setUrl(new URL(this.src));
            request.setRequestListener(new Request.RequestListener() { // from class: com.gen.mh.webapp_extensions.task.CacheTask.1
                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onFail(int i, String str) {
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onProgress(long j, long j2) {
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public boolean onReceiveResponse(Request.Response response) {
                    return true;
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onComplete(int i, byte[] bArr) {
                    WACacheManager.getInstance().save(CacheTask.this.key, bArr);
                    Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
                    Task.StateListener stateListener = CacheTask.this.onStateListener;
                    if (stateListener != null) {
                        stateListener.onComplete(decodeByteArray.getWidth(), decodeByteArray.getHeight());
                    }
                    decodeByteArray.recycle();
                    CacheTask.this.finish();
                }
            });
            request.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        finish();
    }
}
