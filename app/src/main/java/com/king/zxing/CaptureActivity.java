package com.king.zxing;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.p005v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

/* loaded from: classes3.dex */
public class CaptureActivity extends AppCompatActivity implements OnCaptureCallback {
    private View ivTorch;
    private CaptureHelper mCaptureHelper;
    private SurfaceView surfaceView;
    private ViewfinderView viewfinderView;

    public boolean isContentView(@LayoutRes int i) {
        return true;
    }

    @Override // com.king.zxing.OnCaptureCallback
    public boolean onResultCallback(String str) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        int layoutId = getLayoutId();
        if (isContentView(layoutId)) {
            setContentView(layoutId);
        }
        initUI();
        this.mCaptureHelper.onCreate();
    }

    public void initUI() {
        this.surfaceView = (SurfaceView) findViewById(getSurfaceViewId());
        int viewfinderViewId = getViewfinderViewId();
        if (viewfinderViewId != 0) {
            this.viewfinderView = (ViewfinderView) findViewById(viewfinderViewId);
        }
        int ivTorchId = getIvTorchId();
        if (ivTorchId != 0) {
            this.ivTorch = findViewById(ivTorchId);
            this.ivTorch.setVisibility(4);
        }
        initCaptureHelper();
    }

    public void initCaptureHelper() {
        this.mCaptureHelper = new CaptureHelper(this, this.surfaceView, this.viewfinderView, this.ivTorch);
        this.mCaptureHelper.setOnCaptureCallback(this);
    }

    public int getLayoutId() {
        return R$layout.zxl_capture;
    }

    public int getViewfinderViewId() {
        return R$id.viewfinderView;
    }

    public int getSurfaceViewId() {
        return R$id.surfaceView;
    }

    public int getIvTorchId() {
        return R$id.ivTorch;
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.mCaptureHelper.onResume();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.mCaptureHelper.onPause();
    }

    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mCaptureHelper.onDestroy();
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mCaptureHelper.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }
}
