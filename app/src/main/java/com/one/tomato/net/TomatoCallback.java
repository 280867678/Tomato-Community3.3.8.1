package com.one.tomato.net;

import android.app.Activity;
import android.os.Message;
import android.text.TextUtils;
import com.broccoli.p150bh.R;
import com.google.gson.Gson;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.base.BaseFragment;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.mvp.base.okhttp.CallbackUtil;
import com.one.tomato.mvp.base.okhttp.ExceptionHandle;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.thirdpart.domain.DomainCallback;
import com.one.tomato.thirdpart.domain.DomainRequest;
import com.one.tomato.utils.AppInitUtil;
import com.one.tomato.utils.AppSecretUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.SaltUtils;
import com.one.tomato.utils.ToastUtil;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.lang.reflect.Type;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.app.RequestInterceptListener;
import org.xutils.http.request.UriRequest;

/* loaded from: classes3.dex */
public class TomatoCallback<T> implements Callback.CommonCallback<String>, RequestInterceptListener {
    public static final int MSG_ARG1_ACTIVATE_APP = 333334;
    public static final int MSG_ARG1_IMG_UPLOAD = 111111;
    public static final int MSG_ARG1_VIDEO_UPLOAD = 222222;
    public static final int MSG_ARG2_RECOMMEND_POST = 333333;
    private Activity activity;
    public Class<T> clazz;
    private String filterPath;
    private Message msg;
    private UriRequest myRequest;
    public int responseCode;
    public ResponseObserver responseObserver;
    private CustomAlertDialog tipDialog;
    private Type type;

    @Override // org.xutils.common.Callback.CommonCallback
    public void onFinished() {
    }

    public TomatoCallback(ResponseObserver responseObserver, int i) {
        this.responseCode = 0;
        this.clazz = null;
        this.type = null;
        this.activity = null;
        this.responseObserver = responseObserver;
        this.responseCode = i;
        this.msg = Message.obtain();
    }

    public TomatoCallback(ResponseObserver responseObserver, int i, Class<T> cls) {
        this.responseCode = 0;
        this.clazz = null;
        this.type = null;
        this.activity = null;
        this.responseObserver = responseObserver;
        this.responseCode = i;
        this.clazz = cls;
        this.msg = Message.obtain();
    }

    public TomatoCallback(ResponseObserver responseObserver, int i, Type type) {
        this.responseCode = 0;
        this.clazz = null;
        this.type = null;
        this.activity = null;
        this.responseObserver = responseObserver;
        this.responseCode = i;
        this.type = type;
        this.msg = Message.obtain();
    }

    public TomatoCallback(ResponseObserver responseObserver, int i, Type type, int... iArr) {
        this.responseCode = 0;
        this.clazz = null;
        this.type = null;
        this.activity = null;
        this.responseObserver = responseObserver;
        this.responseCode = i;
        this.type = type;
        this.msg = Message.obtain();
        if (iArr.length >= 1) {
            this.msg.arg1 = iArr[0];
        }
        if (iArr.length >= 2) {
            this.msg.arg2 = iArr[1];
        }
    }

    public TomatoCallback(ResponseObserver responseObserver, int i, Class<T> cls, int... iArr) {
        this.responseCode = 0;
        this.clazz = null;
        this.type = null;
        this.activity = null;
        this.responseObserver = responseObserver;
        this.responseCode = i;
        this.clazz = cls;
        this.msg = Message.obtain();
        if (iArr.length >= 1) {
            this.msg.arg1 = iArr[0];
        }
        if (iArr.length >= 2) {
            this.msg.arg2 = iArr[1];
        }
    }

    public String getTAG() {
        ResponseObserver responseObserver = this.responseObserver;
        if (responseObserver instanceof BaseActivity) {
            return ((BaseActivity) responseObserver).getTAG();
        }
        return responseObserver instanceof BaseFragment ? ((BaseFragment) responseObserver).getTAG() : "";
    }

    public TomatoCallback setFilterPath(String str) {
        this.filterPath = str;
        return this;
    }

    @Override // org.xutils.common.Callback.CommonCallback
    public void onSuccess(String str) {
        int i;
        if (!AppSecretUtil.severApiFilter(this.filterPath) && (i = this.msg.arg1) != 111111 && i != 222222) {
            str = AppSecretUtil.decodeResponse(str);
            String tag = getTAG();
            LogUtil.m3783i(tag, "解密后的：" + str);
        } else {
            String tag2 = getTAG();
            LogUtil.m3783i(tag2, "无需解密的：" + str);
        }
        BaseModel baseModel = new BaseModel();
        Gson gson = new Gson();
        Message message = this.msg;
        message.what = this.responseCode;
        message.obj = baseModel;
        try {
            JSONObject jSONObject = new JSONObject(str);
            int i2 = jSONObject.has("code") ? jSONObject.getInt("code") : 0;
            String string = jSONObject.has("message") ? jSONObject.getString("message") : "";
            String string2 = jSONObject.has(AopConstants.APP_PROPERTIES_KEY) ? jSONObject.getString(AopConstants.APP_PROPERTIES_KEY) : "";
            String string3 = jSONObject.has("url") ? jSONObject.getString("url") : "";
            BaseModel.Page page = jSONObject.has("page") ? (BaseModel.Page) gson.fromJson(jSONObject.getString("page"), (Class<Object>) BaseModel.Page.class) : null;
            baseModel.result = str;
            baseModel.code = i2;
            baseModel.message = string;
            baseModel.data = string2;
            baseModel.url = string3;
            baseModel.page = page;
            int i3 = baseModel.code;
            if (i3 == -3) {
                CallbackUtil.loginOut(false);
                this.responseObserver.handleResponseError(this.msg);
            } else if (i3 == 0) {
                try {
                    if (!TextUtils.isEmpty(baseModel.data)) {
                        if (this.clazz != null) {
                            baseModel.obj = gson.fromJson(baseModel.data, (Class<Object>) this.clazz);
                        } else if (this.type != null) {
                            baseModel.obj = gson.fromJson(baseModel.data, this.type);
                        }
                    }
                    this.responseObserver.handleResponse(this.msg);
                } catch (Exception unused) {
                    baseModel.message = AppUtil.getString(R.string.loading_data_error);
                    baseModel.data = null;
                    if (!this.responseObserver.handleResponseError(this.msg)) {
                        return;
                    }
                    ToastUtil.showCenterToast(baseModel.message + "", 0);
                }
            } else if (!this.responseObserver.handleResponseError(this.msg) || TextUtils.isEmpty(baseModel.message)) {
            } else {
                ToastUtil.showCenterToast(baseModel.message + "");
            }
        } catch (Exception unused2) {
            baseModel.obj = null;
            if (!this.responseObserver.handleResponseError(this.msg)) {
                return;
            }
            ToastUtil.showCenterToast((int) R.string.loading_data_error);
        }
    }

    @Override // org.xutils.common.Callback.CommonCallback
    public void onError(Throwable th, boolean z) {
        int i = this.msg.arg1;
        if (i == 111111) {
            DomainRequest.getInstance().switchDomainUrlByType("ttUpload");
        } else if (i == 222222) {
            DomainRequest.getInstance().switchDomainUrlByType("ttUpload");
        } else {
            DomainRequest.getInstance().switchDomainUrlByType("server");
        }
        ResponseThrowable handleException = ExceptionHandle.handleException(th);
        LogUtil.m3786e("code = " + handleException.getCode() + ",message = " + handleException.getMessage());
        handleHttpErrorCallback(handleException.getThrowableMessage());
    }

    @Override // org.xutils.common.Callback.CommonCallback
    public void onCancelled(Callback.CancelledException cancelledException) {
        this.msg.what = this.responseCode;
        hideWaitingDialog();
        this.responseObserver.handleRequestCancel(this.msg);
    }

    @Override // org.xutils.http.app.RequestInterceptListener
    public void beforeRequest(UriRequest uriRequest) throws Throwable {
        this.myRequest = uriRequest;
    }

    @Override // org.xutils.http.app.RequestInterceptListener
    public void afterRequest(UriRequest uriRequest) throws Throwable {
        uriRequest.getResponseHeaders();
        String responseHeader = uriRequest.getResponseHeader("code");
        this.myRequest = uriRequest;
        if ("301".equals(responseHeader)) {
            LogUtil.m3785e("TomatoCallback", "报301的请求地址：" + uriRequest.getRequestUri());
            CallbackUtil.loginOut(false);
        } else if ("302".equals(responseHeader)) {
            LogUtil.m3785e("TomatoCallback", "报302的请求地址：" + uriRequest.getRequestUri());
            CallbackUtil.loginOut(true);
        }
        DomainCallback.getInstance().setDomainVersion(uriRequest.getResponseHeader("domainVersion"));
        String responseHeader2 = uriRequest.getResponseHeader("keyApp");
        if (!TextUtils.isEmpty(responseHeader2)) {
            SaltUtils.INSTANCE.parseResponseKey(responseHeader2);
        }
        String responseHeader3 = uriRequest.getResponseHeader("refreshSalt");
        if (TextUtils.isEmpty(responseHeader3) || !responseHeader3.equals("1")) {
            return;
        }
        SaltUtils.INSTANCE.clearData();
        AppInitUtil.initAppInfoFromServerForSalt();
    }

    private boolean isShowHttpErrorToast() {
        Message message = this.msg;
        return (message.arg2 == 333333 || message.arg1 == 333334) ? false : true;
    }

    private void handleHttpErrorCallback(String str) {
        BaseModel baseModel = new BaseModel();
        Message message = this.msg;
        message.what = this.responseCode;
        message.obj = baseModel;
        hideWaitingDialog();
        this.responseObserver.handleHttpRequestError(this.msg);
    }

    private void hideWaitingDialog() {
        ResponseObserver responseObserver = this.responseObserver;
        if (responseObserver instanceof BaseActivity) {
            ((BaseActivity) responseObserver).hideWaitingDialog();
        } else if (!(responseObserver instanceof BaseFragment)) {
        } else {
            ((BaseFragment) responseObserver).hideWaitingDialog();
        }
    }
}
