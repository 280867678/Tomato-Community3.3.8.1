package cn.bertsir.zbar;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.p036Qr.ScanResult;
import com.gen.p059mh.webapp_extensions.rxpermission.RxPermissions;
import io.reactivex.functions.Consumer;

/* loaded from: classes2.dex */
public class QrManager {
    private static QrManager instance;
    private QrConfig options;
    public OnScanResultCallback resultCallback;

    /* loaded from: classes2.dex */
    public interface OnScanResultCallback {
        void onFailure(Exception exc);

        void onScanSuccess(ScanResult scanResult);
    }

    public static synchronized QrManager getInstance() {
        QrManager qrManager;
        synchronized (QrManager.class) {
            if (instance == null) {
                instance = new QrManager();
            }
            qrManager = instance;
        }
        return qrManager;
    }

    public OnScanResultCallback getResultCallback() {
        return this.resultCallback;
    }

    public QrManager init(QrConfig qrConfig) {
        this.options = qrConfig;
        return this;
    }

    public void startScan(final Activity activity, OnScanResultCallback onScanResultCallback) {
        if (this.options == null) {
            this.options = new QrConfig.Builder().create();
        }
        new RxPermissions(activity).request("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").subscribe(new Consumer<Boolean>() { // from class: cn.bertsir.zbar.QrManager.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) throws Exception {
                if (bool.booleanValue()) {
                    Intent intent = new Intent(activity, QRActivity.class);
                    intent.putExtra(QrConfig.EXTRA_THIS_CONFIG, QrManager.this.options);
                    activity.startActivity(intent);
                    return;
                }
                Toast.makeText(activity, "摄像头权限被拒绝！", 0).show();
                QrManager.getInstance().getResultCallback().onFailure(new Exception("camera storage permissionsDenied"));
            }
        });
        this.resultCallback = onScanResultCallback;
    }
}
