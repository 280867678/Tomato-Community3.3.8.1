package com.one.tomato.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.UpStatusBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpObserverPriceSetDialog.kt */
/* loaded from: classes3.dex */
public final class UpObserverPriceSetDialog extends CustomAlertDialog {
    private Button button;
    private CheckBox checkbox_subscribe;
    private EditText edit_month;
    private EditText edit_quarterly;
    private EditText edit_year;
    private Function1<? super Boolean, Unit> saveInfoCallBack;

    public UpObserverPriceSetDialog(Context context) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_up_observer_price_dialog, (ViewGroup) null);
        setContentView(inflate);
        setCanceledOnTouchOutside(true);
        bottomLayoutGone();
        setViewInit(inflate);
    }

    private final void setViewInit(View view) {
        CheckBox checkBox = null;
        this.checkbox_subscribe = view != null ? (CheckBox) view.findViewById(R.id.checkbox_subscribe) : null;
        this.edit_month = view != null ? (EditText) view.findViewById(R.id.edit_month) : null;
        this.edit_quarterly = view != null ? (EditText) view.findViewById(R.id.edit_quarterly) : null;
        this.edit_year = view != null ? (EditText) view.findViewById(R.id.edit_year) : null;
        this.button = view != null ? (Button) view.findViewById(R.id.button) : null;
        if (view != null) {
            checkBox = (CheckBox) view.findViewById(R.id.checkbox_subscribe);
        }
        this.checkbox_subscribe = checkBox;
        CheckBox checkBox2 = this.checkbox_subscribe;
        if (checkBox2 != null) {
            checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.one.tomato.dialog.UpObserverPriceSetDialog$setViewInit$1
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    Button button;
                    EditText editText;
                    EditText editText2;
                    EditText editText3;
                    button = UpObserverPriceSetDialog.this.button;
                    if (button != null) {
                        button.setEnabled(z);
                    }
                    if (!z) {
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
                        linkedHashMap.put("subscribeSwitch", 0);
                        UserInfo userInfo = DBUtil.getUserInfo();
                        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
                        linkedHashMap.put("originalFlag", Integer.valueOf(Intrinsics.areEqual(userInfo.getUpHostType(), "3") ? 1 : 0));
                        UpObserverPriceSetDialog.this.requestSaveInfo(linkedHashMap);
                    }
                    editText = UpObserverPriceSetDialog.this.edit_month;
                    if (editText != null) {
                        editText.setEnabled(z);
                    }
                    editText2 = UpObserverPriceSetDialog.this.edit_quarterly;
                    if (editText2 != null) {
                        editText2.setEnabled(z);
                    }
                    editText3 = UpObserverPriceSetDialog.this.edit_year;
                    if (editText3 != null) {
                        editText3.setEnabled(z);
                    }
                }
            });
        }
        Button button = this.button;
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.UpObserverPriceSetDialog$setViewInit$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    EditText editText;
                    EditText editText2;
                    EditText editText3;
                    Editable text;
                    Editable text2;
                    Editable text3;
                    editText = UpObserverPriceSetDialog.this.edit_month;
                    String obj = (editText == null || (text3 = editText.getText()) == null) ? null : text3.toString();
                    editText2 = UpObserverPriceSetDialog.this.edit_quarterly;
                    String obj2 = (editText2 == null || (text2 = editText2.getText()) == null) ? null : text2.toString();
                    editText3 = UpObserverPriceSetDialog.this.edit_year;
                    String obj3 = (editText3 == null || (text = editText3.getText()) == null) ? null : text.toString();
                    if (TextUtils.isEmpty(obj)) {
                        ToastUtil.showCenterToast("月票价格不能为空");
                    } else if (obj != null) {
                        int parseInt = Integer.parseInt(obj);
                        UserInfo userInfo = DBUtil.getUserInfo();
                        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
                        if (parseInt > userInfo.getMaxPriceMonth()) {
                            ToastUtil.showCenterToast("月票价格不能大于等级规定的最大价格");
                        } else if (parseInt < 1) {
                            ToastUtil.showCenterToast("月票价格不能小于1");
                        } else if (TextUtils.isEmpty(obj2)) {
                            ToastUtil.showCenterToast("季票价格不能为空");
                        } else if (obj2 != null) {
                            int parseInt2 = Integer.parseInt(obj2);
                            UserInfo userInfo2 = DBUtil.getUserInfo();
                            Intrinsics.checkExpressionValueIsNotNull(userInfo2, "DBUtil.getUserInfo()");
                            if (parseInt2 > userInfo2.getMaxPriceSeason()) {
                                ToastUtil.showCenterToast("季票价格不能大于等级规定的最大价格");
                            } else if (parseInt2 < 1) {
                                ToastUtil.showCenterToast("季票价格不能小于1");
                            } else if (TextUtils.isEmpty(obj3)) {
                                ToastUtil.showCenterToast("年票价格不能为空");
                            } else if (obj3 != null) {
                                int parseInt3 = Integer.parseInt(obj3);
                                UserInfo userInfo3 = DBUtil.getUserInfo();
                                Intrinsics.checkExpressionValueIsNotNull(userInfo3, "DBUtil.getUserInfo()");
                                if (parseInt3 > userInfo3.getMaxPriceYear()) {
                                    ToastUtil.showCenterToast("年票价格不能大于等级规定的最大价格");
                                } else if (parseInt3 < 1) {
                                    ToastUtil.showCenterToast("年票价格不能小于1");
                                } else {
                                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                                    linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
                                    linkedHashMap.put("subscribeSwitch", 1);
                                    linkedHashMap.put("priceMonth", Integer.valueOf(parseInt));
                                    linkedHashMap.put("priceSeason", Integer.valueOf(parseInt2));
                                    linkedHashMap.put("priceYear", Integer.valueOf(parseInt3));
                                    UserInfo userInfo4 = DBUtil.getUserInfo();
                                    Intrinsics.checkExpressionValueIsNotNull(userInfo4, "DBUtil.getUserInfo()");
                                    linkedHashMap.put("originalFlag", Integer.valueOf(Intrinsics.areEqual(userInfo4.getUpHostType(), "3") ? 1 : 0));
                                    UpObserverPriceSetDialog.this.requestSaveInfo(linkedHashMap);
                                }
                            } else {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestSaveInfo(Map<String, Object> map) {
        ApiImplService.Companion.getApiImplService().requestSaveSubscribeInfo(map).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.dialog.UpObserverPriceSetDialog$requestSaveInfo$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                Context context = UpObserverPriceSetDialog.this.context;
                if (context instanceof MvpBaseActivity) {
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).showWaitingDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.dialog.UpObserverPriceSetDialog$requestSaveInfo$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                Function1 function1;
                CheckBox checkBox;
                Context context = UpObserverPriceSetDialog.this.context;
                if (context instanceof MvpBaseActivity) {
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).hideWaitingDialog();
                }
                function1 = UpObserverPriceSetDialog.this.saveInfoCallBack;
                if (function1 != null) {
                    checkBox = UpObserverPriceSetDialog.this.checkbox_subscribe;
                    Unit unit = (Unit) function1.mo6794invoke(Boolean.valueOf(checkBox != null ? checkBox.isChecked() : false));
                }
                UpObserverPriceSetDialog.this.dismiss();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                Context context = UpObserverPriceSetDialog.this.context;
                if (context instanceof MvpBaseActivity) {
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) context).hideWaitingDialog();
                }
            }
        });
    }

    public final void setSubscribePrice(UpStatusBean upStatusBean) {
        if (upStatusBean != null && upStatusBean.getSubscribeSwitch() == 1) {
            CheckBox checkBox = this.checkbox_subscribe;
            if (checkBox != null) {
                checkBox.setChecked(true);
            }
            EditText editText = this.edit_month;
            if (editText != null) {
                editText.setText(String.valueOf(upStatusBean.getPriceMonth()));
            }
            EditText editText2 = this.edit_quarterly;
            if (editText2 != null) {
                editText2.setText(String.valueOf(upStatusBean.getPriceSeason()));
            }
            EditText editText3 = this.edit_year;
            if (editText3 != null) {
                editText3.setText(String.valueOf(upStatusBean.getPriceYear()));
            }
            Button button = this.button;
            if (button != null) {
                button.setEnabled(true);
            }
            EditText editText4 = this.edit_month;
            if (editText4 != null) {
                editText4.setEnabled(true);
            }
            EditText editText5 = this.edit_quarterly;
            if (editText5 != null) {
                editText5.setEnabled(true);
            }
            EditText editText6 = this.edit_year;
            if (editText6 == null) {
                return;
            }
            editText6.setEnabled(true);
            return;
        }
        Button button2 = this.button;
        if (button2 != null) {
            button2.setEnabled(false);
        }
        EditText editText7 = this.edit_month;
        if (editText7 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("1-");
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
            sb.append(userInfo.getMaxPriceMonth());
            editText7.setHint(sb.toString());
        }
        EditText editText8 = this.edit_quarterly;
        if (editText8 != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("1-");
            UserInfo userInfo2 = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo2, "DBUtil.getUserInfo()");
            sb2.append(userInfo2.getMaxPriceSeason());
            editText8.setHint(sb2.toString());
        }
        EditText editText9 = this.edit_year;
        if (editText9 != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("1-");
            UserInfo userInfo3 = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo3, "DBUtil.getUserInfo()");
            sb3.append(userInfo3.getMaxPriceYear());
            editText9.setHint(sb3.toString());
        }
        EditText editText10 = this.edit_month;
        if (editText10 != null) {
            editText10.setEnabled(false);
        }
        EditText editText11 = this.edit_quarterly;
        if (editText11 != null) {
            editText11.setEnabled(false);
        }
        EditText editText12 = this.edit_year;
        if (editText12 == null) {
            return;
        }
        editText12.setEnabled(false);
    }

    public final void saveInfoCallBack(Function1<? super Boolean, Unit> function1) {
        this.saveInfoCallBack = function1;
    }
}
