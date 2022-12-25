package com.one.tomato.mvp.p080ui.p082up.view;

import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpEditNoticeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.UpEditNoticeActivity */
/* loaded from: classes3.dex */
public final class UpEditNoticeActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;

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
        return R.layout.activity_up_eidt_notice;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        ImmersionBarUtil.init(this);
        initTitleBar();
        TextView textView = (TextView) _$_findCachedViewById(R$id.right_txt);
        if (textView != null) {
            textView.setVisibility(0);
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.right_txt);
        if (textView2 != null) {
            textView2.setText(AppUtil.getString(R.string.common_save));
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.right_txt);
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpEditNoticeActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Editable text;
                    EditText editText = (EditText) UpEditNoticeActivity.this._$_findCachedViewById(R$id.edit);
                    String obj = (editText == null || (text = editText.getText()) == null) ? null : text.toString();
                    if (obj != null) {
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
                        linkedHashMap.put("notice", obj);
                        UserInfo userInfo = DBUtil.getUserInfo();
                        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
                        linkedHashMap.put("originalFlag", Integer.valueOf(Intrinsics.areEqual(userInfo.getUpHostType(), "3") ? 3 : 0));
                        UpEditNoticeActivity.this.requestSaveInfo(linkedHashMap);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestSaveInfo(Map<String, Object> map) {
        ApiImplService.Companion.getApiImplService().requestSaveSubscribeInfo(map).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.up.view.UpEditNoticeActivity$requestSaveInfo$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                UpEditNoticeActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.up.view.UpEditNoticeActivity$requestSaveInfo$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                String str;
                Editable text;
                UpEditNoticeActivity.this.hideWaitingDialog();
                Intent intent = new Intent();
                EditText editText = (EditText) UpEditNoticeActivity.this._$_findCachedViewById(R$id.edit);
                if (editText == null || (text = editText.getText()) == null || (str = text.toString()) == null) {
                    str = "";
                }
                intent.putExtra("text", str);
                UpEditNoticeActivity.this.setResult(-1, intent);
                UpEditNoticeActivity.this.finish();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                UpEditNoticeActivity.this.hideWaitingDialog();
            }
        });
    }
}
