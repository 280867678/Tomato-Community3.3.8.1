package com.one.tomato.mvp.p080ui.feedback.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.p005v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.RechargeProblemOrder;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackRechargeContact$IFeedbackRechargeView;
import com.one.tomato.mvp.p080ui.feedback.presenter.FeedbackRechargePresenter;
import com.one.tomato.mvp.p080ui.feedback.view.FeedbackOrderActivity;
import com.one.tomato.thirdpart.pictureselector.FullyGridLayoutManager;
import com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.NetWorkUtil;
import com.one.tomato.utils.TTUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.ClearEditText;
import com.shizhefei.view.largeimage.LargeImageView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: FeedbackRechargeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity */
/* loaded from: classes3.dex */
public final class FeedbackRechargeActivity extends MvpBaseActivity<IFeedbackRechargeContact$IFeedbackRechargeView, FeedbackRechargePresenter> implements IFeedbackRechargeContact$IFeedbackRechargeView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private LocalMedia demo;
    private RechargeProblemOrder rechargeProblemOrder;
    private SelectGridImageAdapter selectGridImageAdapter;
    private boolean selectOrder;
    private SelectPicTypeUtil selectPicTypeUtil;
    private TTUtil ttUtil;
    private String recharge_type = "feedback_jc";
    private List<LocalMedia> selectList = new ArrayList();
    private boolean isFirstSelect = true;
    private final ArrayList<LocalMedia> uploadSuccessList = new ArrayList<>();
    private String imgUrl = "";

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
        return R.layout.activity_feedback_recharge;
    }

    public static final /* synthetic */ LocalMedia access$getDemo$p(FeedbackRechargeActivity feedbackRechargeActivity) {
        LocalMedia localMedia = feedbackRechargeActivity.demo;
        if (localMedia != null) {
            return localMedia;
        }
        Intrinsics.throwUninitializedPropertyAccessException("demo");
        throw null;
    }

    /* compiled from: FeedbackRechargeActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, FeedbackRechargeActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public FeedbackRechargePresenter mo6439createPresenter() {
        return new FeedbackRechargePresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.recharge_feedback_item1);
        }
        updateRechargeSelect();
        ((TextView) _$_findCachedViewById(R$id.tv_feedback_jc)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FeedbackRechargeActivity.this.recharge_type = "feedback_jc";
                FeedbackRechargeActivity.this.updateRechargeSelect();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_feedback_online)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$initView$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FeedbackRechargeActivity.this.recharge_type = "feedback_online";
                FeedbackRechargeActivity.this.updateRechargeSelect();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_select_order)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$initView$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                String str;
                FeedbackOrderActivity.Companion companion = FeedbackOrderActivity.Companion;
                FeedbackRechargeActivity feedbackRechargeActivity = FeedbackRechargeActivity.this;
                str = feedbackRechargeActivity.recharge_type;
                companion.startActivity(feedbackRechargeActivity, str);
            }
        });
        TextView tv_input_num = (TextView) _$_findCachedViewById(R$id.tv_input_num);
        Intrinsics.checkExpressionValueIsNotNull(tv_input_num, "tv_input_num");
        tv_input_num.setText(AppUtil.getString(R.string.update_report_text_num, 0));
        ((ClearEditText) _$_findCachedViewById(R$id.et_input)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$initView$4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                Intrinsics.checkParameterIsNotNull(s, "s");
                ClearEditText et_input = (ClearEditText) FeedbackRechargeActivity.this._$_findCachedViewById(R$id.et_input);
                Intrinsics.checkExpressionValueIsNotNull(et_input, "et_input");
                String obj = et_input.getText().toString();
                int length = obj.length() - 1;
                int i = 0;
                boolean z = false;
                while (i <= length) {
                    boolean z2 = obj.charAt(!z ? i : length) <= ' ';
                    if (!z) {
                        if (!z2) {
                            z = true;
                        } else {
                            i++;
                        }
                    } else if (!z2) {
                        break;
                    } else {
                        length--;
                    }
                }
                int length2 = obj.subSequence(i, length + 1).toString().length();
                TextView tv_input_num2 = (TextView) FeedbackRechargeActivity.this._$_findCachedViewById(R$id.tv_input_num);
                Intrinsics.checkExpressionValueIsNotNull(tv_input_num2, "tv_input_num");
                tv_input_num2.setText(AppUtil.getString(R.string.update_report_text_num, Integer.valueOf(length2)));
            }
        });
        ((LargeImageView) _$_findCachedViewById(R$id.iv_demo_full_screen)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$initView$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LargeImageView iv_demo_full_screen = (LargeImageView) FeedbackRechargeActivity.this._$_findCachedViewById(R$id.iv_demo_full_screen);
                Intrinsics.checkExpressionValueIsNotNull(iv_demo_full_screen, "iv_demo_full_screen");
                iv_demo_full_screen.setVisibility(8);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_feedback)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$initView$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FeedbackRechargeActivity.this.submitCheck();
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        this.demo = new LocalMedia();
        LocalMedia localMedia = this.demo;
        if (localMedia == null) {
            Intrinsics.throwUninitializedPropertyAccessException("demo");
            throw null;
        }
        localMedia.setPath("");
        LocalMedia localMedia2 = this.demo;
        if (localMedia2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("demo");
            throw null;
        }
        localMedia2.setDemo(true);
        LocalMedia localMedia3 = this.demo;
        if (localMedia3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("demo");
            throw null;
        }
        localMedia3.setDemoResId(R.drawable.recharge_feedback_img_demo);
        initGridImgAdapter();
        ((LargeImageView) _$_findCachedViewById(R$id.iv_demo_full_screen)).setImage(R.drawable.recharge_feedback_img_demo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateRechargeSelect() {
        String str = this.recharge_type;
        int hashCode = str.hashCode();
        if (hashCode != -1302586317) {
            if (hashCode == -1156401075 && str.equals("feedback_online")) {
                ((TextView) _$_findCachedViewById(R$id.tv_feedback_jc)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.feedback_recharge_type_n), (Drawable) null, (Drawable) null, (Drawable) null);
                ((TextView) _$_findCachedViewById(R$id.tv_feedback_online)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.feedback_recharge_type_s), (Drawable) null, (Drawable) null, (Drawable) null);
            }
        } else if (str.equals("feedback_jc")) {
            ((TextView) _$_findCachedViewById(R$id.tv_feedback_jc)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.feedback_recharge_type_s), (Drawable) null, (Drawable) null, (Drawable) null);
            ((TextView) _$_findCachedViewById(R$id.tv_feedback_online)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.feedback_recharge_type_n), (Drawable) null, (Drawable) null, (Drawable) null);
        }
        this.selectOrder = false;
        this.rechargeProblemOrder = null;
        ((TextView) _$_findCachedViewById(R$id.tv_select_order)).setText("");
    }

    private final void initGridImgAdapter() {
        this.selectPicTypeUtil = new SelectPicTypeUtil(this);
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(this, 3, 1, false);
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        Intrinsics.checkExpressionValueIsNotNull(recyclerView, "recyclerView");
        recyclerView.setLayoutManager(fullyGridLayoutManager);
        this.selectGridImageAdapter = new SelectGridImageAdapter(this);
        List<LocalMedia> list = this.selectList;
        LocalMedia localMedia = this.demo;
        if (localMedia == null) {
            Intrinsics.throwUninitializedPropertyAccessException("demo");
            throw null;
        }
        list.add(0, localMedia);
        SelectGridImageAdapter selectGridImageAdapter = this.selectGridImageAdapter;
        if (selectGridImageAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selectGridImageAdapter");
            throw null;
        }
        selectGridImageAdapter.setSelectMax(10);
        SelectGridImageAdapter selectGridImageAdapter2 = this.selectGridImageAdapter;
        if (selectGridImageAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selectGridImageAdapter");
            throw null;
        }
        selectGridImageAdapter2.setList(this.selectList);
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        Intrinsics.checkExpressionValueIsNotNull(recyclerView2, "recyclerView");
        SelectGridImageAdapter selectGridImageAdapter3 = this.selectGridImageAdapter;
        if (selectGridImageAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selectGridImageAdapter");
            throw null;
        }
        recyclerView2.setAdapter(selectGridImageAdapter3);
        SelectGridImageAdapter selectGridImageAdapter4 = this.selectGridImageAdapter;
        if (selectGridImageAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selectGridImageAdapter");
            throw null;
        }
        selectGridImageAdapter4.setOnItemClickListener(new SelectGridImageAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$initGridImgAdapter$1
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnItemClickListener
            public final void onItemClick(int i, View view) {
                List list2;
                List list3;
                List list4;
                list2 = FeedbackRechargeActivity.this.selectList;
                if (list2.size() > 0) {
                    list3 = FeedbackRechargeActivity.this.selectList;
                    if (PictureMimeType.pictureToVideo(((LocalMedia) list3.get(i)).getPictureType()) != 1) {
                        return;
                    }
                    if (i == 0) {
                        LargeImageView iv_demo_full_screen = (LargeImageView) FeedbackRechargeActivity.this._$_findCachedViewById(R$id.iv_demo_full_screen);
                        Intrinsics.checkExpressionValueIsNotNull(iv_demo_full_screen, "iv_demo_full_screen");
                        iv_demo_full_screen.setVisibility(0);
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    list4 = FeedbackRechargeActivity.this.selectList;
                    arrayList.addAll(list4);
                    arrayList.remove(FeedbackRechargeActivity.access$getDemo$p(FeedbackRechargeActivity.this));
                    PictureSelector.create(FeedbackRechargeActivity.this).themeStyle(2131821197).openExternalPreview(i - 1, arrayList);
                }
            }
        });
        SelectGridImageAdapter selectGridImageAdapter5 = this.selectGridImageAdapter;
        if (selectGridImageAdapter5 != null) {
            selectGridImageAdapter5.setOnAddPicClickListener(new SelectGridImageAdapter.OnAddPicClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$initGridImgAdapter$2
                @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnAddPicClickListener
                public final void onAddPicClick() {
                    boolean z;
                    z = FeedbackRechargeActivity.this.isFirstSelect;
                    if (z) {
                        FeedbackRechargeActivity.this.isFirstSelect = false;
                        FeedbackRechargeActivity.this.showSelectRechargeImgDialog();
                        return;
                    }
                    FeedbackRechargeActivity.this.selectRechargeImg();
                }
            });
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("selectGridImageAdapter");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void selectRechargeImg() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.selectList);
        LocalMedia localMedia = this.demo;
        if (localMedia == null) {
            Intrinsics.throwUninitializedPropertyAccessException("demo");
            throw null;
        }
        arrayList.remove(localMedia);
        SelectPicTypeUtil selectPicTypeUtil = this.selectPicTypeUtil;
        if (selectPicTypeUtil != null) {
            selectPicTypeUtil.selectCommonPhoto(9, false, false, false, arrayList);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("selectPicTypeUtil");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v0, types: [T, com.one.tomato.dialog.CustomAlertDialog] */
    public final void showSelectRechargeImgDialog() {
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = new CustomAlertDialog(this);
        ((CustomAlertDialog) ref$ObjectRef.element).bottomLayoutGone();
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_recharge_select_img_tip, (ViewGroup) null);
        ((CustomAlertDialog) ref$ObjectRef.element).setMiddleNeedPadding(false);
        ((CustomAlertDialog) ref$ObjectRef.element).setContentView(inflate);
        ((ImageView) inflate.findViewById(R.id.iv_scale)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$showSelectRechargeImgDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ((CustomAlertDialog) ref$ObjectRef.element).dismiss();
                LargeImageView iv_demo_full_screen = (LargeImageView) FeedbackRechargeActivity.this._$_findCachedViewById(R$id.iv_demo_full_screen);
                Intrinsics.checkExpressionValueIsNotNull(iv_demo_full_screen, "iv_demo_full_screen");
                iv_demo_full_screen.setVisibility(0);
            }
        });
        ((TextView) inflate.findViewById(R.id.tv_get)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$showSelectRechargeImgDialog$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ((CustomAlertDialog) ref$ObjectRef.element).dismiss();
                FeedbackRechargeActivity.this.selectRechargeImg();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void submitCheck() {
        if (!this.selectOrder) {
            ToastUtil.showCenterToast((int) R.string.recharge_feedback_order);
            return;
        }
        ClearEditText et_input_pay_member = (ClearEditText) _$_findCachedViewById(R$id.et_input_pay_member);
        Intrinsics.checkExpressionValueIsNotNull(et_input_pay_member, "et_input_pay_member");
        Editable text = et_input_pay_member.getText();
        Intrinsics.checkExpressionValueIsNotNull(text, "et_input_pay_member.text");
        boolean z = false;
        if (text.length() == 0) {
            ToastUtil.showCenterToast((int) R.string.recharge_feedback_pay_member);
            return;
        }
        ClearEditText et_input_pay_card = (ClearEditText) _$_findCachedViewById(R$id.et_input_pay_card);
        Intrinsics.checkExpressionValueIsNotNull(et_input_pay_card, "et_input_pay_card");
        Editable text2 = et_input_pay_card.getText();
        Intrinsics.checkExpressionValueIsNotNull(text2, "et_input_pay_card.text");
        if (text2.length() == 0) {
            ToastUtil.showCenterToast((int) R.string.recharge_feedback_pay_card);
            return;
        }
        ClearEditText et_input_pay_order = (ClearEditText) _$_findCachedViewById(R$id.et_input_pay_order);
        Intrinsics.checkExpressionValueIsNotNull(et_input_pay_order, "et_input_pay_order");
        Editable text3 = et_input_pay_order.getText();
        Intrinsics.checkExpressionValueIsNotNull(text3, "et_input_pay_order.text");
        if (text3.length() == 0) {
            ToastUtil.showCenterToast((int) R.string.recharge_feedback_pay_order);
            return;
        }
        ClearEditText et_input = (ClearEditText) _$_findCachedViewById(R$id.et_input);
        Intrinsics.checkExpressionValueIsNotNull(et_input, "et_input");
        Editable text4 = et_input.getText();
        Intrinsics.checkExpressionValueIsNotNull(text4, "et_input.text");
        if (text4.length() == 0) {
            z = true;
        }
        if (!z) {
            ClearEditText et_input2 = (ClearEditText) _$_findCachedViewById(R$id.et_input);
            Intrinsics.checkExpressionValueIsNotNull(et_input2, "et_input");
            if (et_input2.getText().length() >= 10) {
                ClearEditText et_input3 = (ClearEditText) _$_findCachedViewById(R$id.et_input);
                Intrinsics.checkExpressionValueIsNotNull(et_input3, "et_input");
                if (et_input3.getText().length() <= 200) {
                    if (this.selectList.size() == 1) {
                        ToastUtil.showCenterToast((int) R.string.recharge_feedback_img_tip);
                        return;
                    } else {
                        uploadImg();
                        return;
                    }
                }
            }
        }
        ToastUtil.showCenterToast((int) R.string.recharge_feedback_issues_intro);
    }

    private final void uploadImg() {
        this.ttUtil = new TTUtil(1, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeActivity$uploadImg$1
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
                FeedbackRechargeActivity.this.showWaitingDialog();
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia media) {
                ArrayList arrayList;
                ArrayList arrayList2;
                List list;
                ArrayList arrayList3;
                ArrayList arrayList4;
                TTUtil tTUtil;
                TTUtil tTUtil2;
                ArrayList arrayList5;
                Intrinsics.checkParameterIsNotNull(media, "media");
                arrayList = FeedbackRechargeActivity.this.uploadSuccessList;
                arrayList.add(media);
                arrayList2 = FeedbackRechargeActivity.this.uploadSuccessList;
                int size = arrayList2.size();
                list = FeedbackRechargeActivity.this.selectList;
                if (size == list.size() - 1) {
                    StringBuilder sb = new StringBuilder();
                    arrayList3 = FeedbackRechargeActivity.this.uploadSuccessList;
                    int size2 = arrayList3.size();
                    for (int i = 0; i < size2; i++) {
                        arrayList4 = FeedbackRechargeActivity.this.uploadSuccessList;
                        Object obj = arrayList4.get(i);
                        Intrinsics.checkExpressionValueIsNotNull(obj, "uploadSuccessList[i]");
                        LocalMedia localMedia = (LocalMedia) obj;
                        tTUtil = FeedbackRechargeActivity.this.ttUtil;
                        if (tTUtil == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        String ceph = tTUtil.getCeph(localMedia);
                        sb.append("/");
                        tTUtil2 = FeedbackRechargeActivity.this.ttUtil;
                        if (tTUtil2 != null) {
                            sb.append(tTUtil2.getBucketName());
                            sb.append("/");
                            sb.append(ceph);
                            arrayList5 = FeedbackRechargeActivity.this.uploadSuccessList;
                            if (i < arrayList5.size() - 1) {
                                sb.append(";");
                            }
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                    FeedbackRechargeActivity feedbackRechargeActivity = FeedbackRechargeActivity.this;
                    String sb2 = sb.toString();
                    Intrinsics.checkExpressionValueIsNotNull(sb2, "urlBuilder.toString()");
                    feedbackRechargeActivity.imgUrl = sb2;
                    FeedbackRechargeActivity.this.submit();
                }
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                FeedbackRechargeActivity.this.hideWaitingDialog();
                ToastUtil.showCenterToast((int) R.string.common_upload_img_fail);
            }
        });
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.selectList);
        LocalMedia localMedia = this.demo;
        if (localMedia == null) {
            Intrinsics.throwUninitializedPropertyAccessException("demo");
            throw null;
        }
        arrayList.remove(localMedia);
        TTUtil tTUtil = this.ttUtil;
        if (tTUtil != null) {
            tTUtil.getStsToken(arrayList);
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void submit() {
        String money;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("brand", DeviceInfoUtil.getDeviceBrand());
        hashMap.put("model", DeviceInfoUtil.getDeviceTypeName());
        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, DeviceInfoUtil.getPhoneOSVersion());
        hashMap.put("netStatus", NetWorkUtil.getNetWorkType());
        hashMap.put("memberId", String.valueOf(DBUtil.getMemberId()));
        ClearEditText et_input = (ClearEditText) _$_findCachedViewById(R$id.et_input);
        Intrinsics.checkExpressionValueIsNotNull(et_input, "et_input");
        String obj = et_input.getText().toString();
        int length = obj.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = obj.charAt(!z ? i : length) <= ' ';
            if (!z) {
                if (!z2) {
                    z = true;
                } else {
                    i++;
                }
            } else if (!z2) {
                break;
            } else {
                length--;
            }
        }
        hashMap.put("description", obj.subSequence(i, length + 1).toString());
        hashMap.put("imageUrl", this.imgUrl);
        RechargeProblemOrder rechargeProblemOrder = this.rechargeProblemOrder;
        String id = rechargeProblemOrder != null ? rechargeProblemOrder.getId() : null;
        if (id == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        hashMap.put("orderId", id);
        RechargeProblemOrder rechargeProblemOrder2 = this.rechargeProblemOrder;
        hashMap.put("orderAmount", String.valueOf((rechargeProblemOrder2 == null || (money = rechargeProblemOrder2.getMoney()) == null) ? null : Integer.valueOf((int) (Float.parseFloat(money) * 100))));
        RechargeProblemOrder rechargeProblemOrder3 = this.rechargeProblemOrder;
        String date = rechargeProblemOrder3 != null ? rechargeProblemOrder3.getDate() : null;
        if (date == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        hashMap.put("orderTime", date);
        ClearEditText et_input_pay_member = (ClearEditText) _$_findCachedViewById(R$id.et_input_pay_member);
        Intrinsics.checkExpressionValueIsNotNull(et_input_pay_member, "et_input_pay_member");
        hashMap.put("payerName", et_input_pay_member.getText().toString());
        ClearEditText et_input_pay_card = (ClearEditText) _$_findCachedViewById(R$id.et_input_pay_card);
        Intrinsics.checkExpressionValueIsNotNull(et_input_pay_card, "et_input_pay_card");
        hashMap.put("payerAccountNo", et_input_pay_card.getText().toString());
        ClearEditText et_input_pay_order = (ClearEditText) _$_findCachedViewById(R$id.et_input_pay_order);
        Intrinsics.checkExpressionValueIsNotNull(et_input_pay_order, "et_input_pay_order");
        hashMap.put("payFlowOrderId", et_input_pay_order.getText().toString());
        if (this.recharge_type.equals("feedback_jc")) {
            hashMap.put("feedbackType", "3");
        } else if (this.recharge_type.equals("feedback_online")) {
            hashMap.put("feedbackType", "4");
        }
        FeedbackRechargePresenter mPresenter = getMPresenter();
        if (mPresenter == null) {
            return;
        }
        mPresenter.submit(hashMap);
    }

    @Override // com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackRechargeContact$IFeedbackRechargeView
    public void handleSubmit() {
        hideWaitingDialog();
        ToastUtil.showCenterToast((int) R.string.recharge_feedback_success);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            String str = null;
            if (i == 1) {
                Serializable serializableExtra = intent != null ? intent.getSerializableExtra("orderBean") : null;
                if (serializableExtra == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.RechargeProblemOrder");
                }
                this.rechargeProblemOrder = (RechargeProblemOrder) serializableExtra;
                TextView tv_select_order = (TextView) _$_findCachedViewById(R$id.tv_select_order);
                Intrinsics.checkExpressionValueIsNotNull(tv_select_order, "tv_select_order");
                RechargeProblemOrder rechargeProblemOrder = this.rechargeProblemOrder;
                if (rechargeProblemOrder != null) {
                    str = rechargeProblemOrder.getId();
                }
                tv_select_order.setText(str);
                this.selectOrder = true;
            } else if (i != 188) {
            } else {
                List<LocalMedia> obtainMultipleResult = PictureSelector.obtainMultipleResult(intent);
                Intrinsics.checkExpressionValueIsNotNull(obtainMultipleResult, "PictureSelector.obtainMultipleResult(data)");
                this.selectList = obtainMultipleResult;
                List<LocalMedia> list = this.selectList;
                LocalMedia localMedia = this.demo;
                if (localMedia == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("demo");
                    throw null;
                }
                list.add(0, localMedia);
                SelectGridImageAdapter selectGridImageAdapter = this.selectGridImageAdapter;
                if (selectGridImageAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("selectGridImageAdapter");
                    throw null;
                }
                selectGridImageAdapter.setList(this.selectList);
                SelectGridImageAdapter selectGridImageAdapter2 = this.selectGridImageAdapter;
                if (selectGridImageAdapter2 != null) {
                    selectGridImageAdapter2.notifyDataSetChanged();
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("selectGridImageAdapter");
                    throw null;
                }
            }
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        LargeImageView iv_demo_full_screen = (LargeImageView) _$_findCachedViewById(R$id.iv_demo_full_screen);
        Intrinsics.checkExpressionValueIsNotNull(iv_demo_full_screen, "iv_demo_full_screen");
        if (iv_demo_full_screen.getVisibility() == 0) {
            LargeImageView iv_demo_full_screen2 = (LargeImageView) _$_findCachedViewById(R$id.iv_demo_full_screen);
            Intrinsics.checkExpressionValueIsNotNull(iv_demo_full_screen2, "iv_demo_full_screen");
            iv_demo_full_screen2.setVisibility(8);
            return;
        }
        super.onBackPressed();
    }
}
