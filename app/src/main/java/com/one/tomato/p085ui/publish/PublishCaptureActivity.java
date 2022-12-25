package com.one.tomato.p085ui.publish;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.king.zxing.CaptureHelper;
import com.king.zxing.OnCaptureCallback;
import com.king.zxing.ViewfinderView;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PublishCaptureActivity.kt */
/* renamed from: com.one.tomato.ui.publish.PublishCaptureActivity */
/* loaded from: classes3.dex */
public final class PublishCaptureActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> implements OnCaptureCallback {
    private HashMap _$_findViewCache;
    private View ivTorch;
    private CaptureHelper mCaptureHelper;
    private SurfaceView surfaceView;
    private ViewfinderView viewfinderView;

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_publish_capture;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public final int getIvTorchId() {
        return R.id.ivTorch;
    }

    public final int getSurfaceViewId() {
        return R.id.surfaceView;
    }

    public final int getViewfinderViewId() {
        return R.id.viewfinderView;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initUI();
        CaptureHelper captureHelper = this.mCaptureHelper;
        if (captureHelper != null) {
            captureHelper.onCreate();
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.publish.PublishCaptureActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PublishCaptureActivity.this.onBackPressed();
                }
            });
        }
    }

    public final void initUI() {
        this.surfaceView = (SurfaceView) findViewById(getSurfaceViewId());
        int viewfinderViewId = getViewfinderViewId();
        if (viewfinderViewId != 0) {
            this.viewfinderView = (ViewfinderView) findViewById(viewfinderViewId);
        }
        int ivTorchId = getIvTorchId();
        if (ivTorchId != 0) {
            this.ivTorch = findViewById(ivTorchId);
            View view = this.ivTorch;
            if (view != null) {
                view.setVisibility(4);
            }
        }
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        String creator_center_url = systemParam.getCreator_center_url();
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_url);
        if (textView != null) {
            if (creator_center_url == null) {
                creator_center_url = "";
            }
            textView.setText(creator_center_url);
        }
        initCaptureHelper();
    }

    public final void initCaptureHelper() {
        this.mCaptureHelper = new CaptureHelper(this, this.surfaceView, this.viewfinderView, this.ivTorch);
        CaptureHelper captureHelper = this.mCaptureHelper;
        if (captureHelper != null) {
            captureHelper.setOnCaptureCallback(this);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CaptureHelper captureHelper = this.mCaptureHelper;
        if (captureHelper != null) {
            captureHelper.onResume();
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        CaptureHelper captureHelper = this.mCaptureHelper;
        if (captureHelper != null) {
            captureHelper.onPause();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        CaptureHelper captureHelper = this.mCaptureHelper;
        if (captureHelper != null) {
            captureHelper.onDestroy();
        }
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        CaptureHelper captureHelper = this.mCaptureHelper;
        if (captureHelper != null) {
            captureHelper.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override // com.king.zxing.OnCaptureCallback
    public boolean onResultCallback(String result) {
        Intrinsics.checkParameterIsNotNull(result, "result");
        LogUtil.m3784i("扫描结果-------" + result);
        request(result);
        return true;
    }

    private final void request(String str) {
        ApiImplService.Companion.getApiImplService().requestLoginCode(str).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.ui.publish.PublishCaptureActivity$request$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                Context mContext;
                mContext = PublishCaptureActivity.this.getMContext();
                PublishCaptureActivity.this.startActivity(new Intent(mContext, ScannerResultActivity.class));
                PublishCaptureActivity.this.finish();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3784i("扫描结果-------");
            }
        });
    }
}
