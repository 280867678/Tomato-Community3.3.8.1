package com.king.zxing;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes3.dex */
public class CaptureFragment extends Fragment implements OnCaptureCallback {
    private View ivTorch;
    private CaptureHelper mCaptureHelper;
    private View mRootView;
    private SurfaceView surfaceView;
    private ViewfinderView viewfinderView;

    public boolean isContentView(@LayoutRes int i) {
        return true;
    }

    @Override // com.king.zxing.OnCaptureCallback
    public boolean onResultCallback(String str) {
        return false;
    }

    @Override // android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (isContentView(getLayoutId())) {
            this.mRootView = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        }
        initUI();
        return this.mRootView;
    }

    public void initUI() {
        this.surfaceView = (SurfaceView) this.mRootView.findViewById(getSurfaceViewId());
        int viewfinderViewId = getViewfinderViewId();
        if (viewfinderViewId != 0) {
            this.viewfinderView = (ViewfinderView) this.mRootView.findViewById(viewfinderViewId);
        }
        int ivTorchId = getIvTorchId();
        if (ivTorchId != 0) {
            this.ivTorch = this.mRootView.findViewById(ivTorchId);
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

    @Override // android.support.p002v4.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mCaptureHelper.onCreate();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        this.mCaptureHelper.onResume();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.mCaptureHelper.onPause();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mCaptureHelper.onDestroy();
    }
}
