package com.tomatolive.library.p136ui.view.dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.model.p135db.MsgDetailListEntity;
import com.tomatolive.library.model.p135db.MsgListEntity;
import com.tomatolive.library.model.p135db.ShortcutItemEntity;
import com.tomatolive.library.p136ui.adapter.MsgDetailListAdapter;
import com.tomatolive.library.p136ui.adapter.MsgListAdapter;
import com.tomatolive.library.p136ui.view.dialog.ShortcutPopDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerListMsg;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.headview.PrivateMsgTipsView;
import com.tomatolive.library.p136ui.view.widget.Html5WebView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SoftKeyboardUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* renamed from: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog */
/* loaded from: classes3.dex */
public class PrivateMsgDialog extends BaseBottomDialogFragment {
    public static String CONTENT_TYPE_KEY = "CONTENT_TYPE_KEY";
    public static final int CONTENT_TYPE_MSG_DESC = 4;
    public static final int CONTENT_TYPE_MSG_DETAIL = 2;
    public static final int CONTENT_TYPE_MSG_LIST = 1;
    public static final int CONTENT_TYPE_SHORTCUT = 3;
    private static final int MAX_LENGTH_20 = 20;
    private static final int MAX_LENGTH_52 = 52;
    public static String TARGET_ID_KEY = "TARGET_ID_KEY";
    public static String TARGET_NAME_KEY = "TARGET_NAME_KEY";
    public static String TYPE_FORM_ANCHOR = "TYPE_FORM_ANCHOR";
    public static String TYPE_SOCKET_STATUS = "TYPE_SOCKET_STATUS";
    private ClipboardManager clipboardManager;
    private List<MsgDetailListEntity> curMsgDetailData;
    private MsgListEntity curMsgEntity;
    private EditText etMsgDetail;
    private EditText etShortCut;
    private FrameLayout flLeftIcon;
    private ImageView ivLeftIcon;
    private ImageView ivRightIcon;
    private ImageView ivShortcut;
    public SendPrivateMsgListener listener;
    private LinearLayout llMsgSendBg;
    private MsgDetailListAdapter msgDetailListAdapter;
    private LinearLayout msgDetailListRoot;
    private MsgListAdapter msgListAdapter;
    private List<MsgListEntity> msgListEntities;
    private LinearLayout msgShortcutRoot;
    private ViewStub myViewStab;
    private View rlReconnRoot;
    private RecyclerView rvMsgDetailList;
    private RecyclerView rvMsgList;
    private ShortcutPopDialog shortcutPopDialog;
    private boolean socketStatus;
    private String targetId;
    private TextView tvClick;
    private TextView tvLength;
    private TextView tvLoading;
    private TextView tvMsgSend;
    private TextView tvSave;
    private TextView tvTip;
    private TextView tvTitle;
    private Html5WebView webViewDesc;
    private int curContentType = 1;
    private List<MsgDetailListEntity> detailList = new ArrayList();
    private String targetName = "";
    private String targetAvatar = "";
    private boolean isFromAnchor = false;

    /* renamed from: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog$SendPrivateMsgListener */
    /* loaded from: classes3.dex */
    public interface SendPrivateMsgListener {
        void onReConnSocket();

        void sendPrivateMsg(MsgDetailListEntity msgDetailListEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public double getHeightScale() {
        return 0.5d;
    }

    public static PrivateMsgDialog newInstance(boolean z) {
        Bundle bundle = new Bundle();
        PrivateMsgDialog privateMsgDialog = new PrivateMsgDialog();
        bundle.putInt(CONTENT_TYPE_KEY, 1);
        bundle.putBoolean(TYPE_SOCKET_STATUS, z);
        privateMsgDialog.setArguments(bundle);
        return privateMsgDialog;
    }

    public static PrivateMsgDialog newInstance(boolean z, boolean z2) {
        Bundle bundle = new Bundle();
        PrivateMsgDialog privateMsgDialog = new PrivateMsgDialog();
        bundle.putBoolean(TYPE_FORM_ANCHOR, z);
        bundle.putInt(CONTENT_TYPE_KEY, 1);
        bundle.putBoolean(TYPE_SOCKET_STATUS, z2);
        privateMsgDialog.setArguments(bundle);
        return privateMsgDialog;
    }

    public static PrivateMsgDialog newInstance(String str, String str2, boolean z) {
        Bundle bundle = new Bundle();
        PrivateMsgDialog privateMsgDialog = new PrivateMsgDialog();
        bundle.putBoolean(TYPE_FORM_ANCHOR, true);
        bundle.putInt(CONTENT_TYPE_KEY, 2);
        bundle.putString(TARGET_ID_KEY, str2);
        bundle.putString(TARGET_NAME_KEY, str);
        bundle.putBoolean(TYPE_SOCKET_STATUS, z);
        privateMsgDialog.setArguments(bundle);
        return privateMsgDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.isFromAnchor = bundle.getBoolean(TYPE_FORM_ANCHOR);
        this.curContentType = bundle.getInt(CONTENT_TYPE_KEY, 1);
        this.targetId = bundle.getString(TARGET_ID_KEY);
        this.targetName = bundle.getString(TARGET_NAME_KEY);
        this.socketStatus = bundle.getBoolean(TYPE_SOCKET_STATUS);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_private_msg;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.flLeftIcon = (FrameLayout) view.findViewById(R$id.fl_left_icon);
        this.tvTitle = (TextView) view.findViewById(R$id.tv_title);
        this.ivLeftIcon = (ImageView) view.findViewById(R$id.iv_left_icon);
        this.ivRightIcon = (ImageView) view.findViewById(R$id.iv_right_icon);
        this.tvLoading = (TextView) view.findViewById(R$id.tv_loading);
        this.rvMsgList = (RecyclerView) view.findViewById(R$id.rv_msg_list);
        this.rvMsgDetailList = (RecyclerView) view.findViewById(R$id.rv_msg_detail_list);
        this.msgListAdapter = new MsgListAdapter(R$layout.fq_item_list_msg_center);
        this.msgDetailListAdapter = new MsgDetailListAdapter(this.detailList);
        this.msgDetailListRoot = (LinearLayout) view.findViewById(R$id.ll_msg_detail_root);
        this.llMsgSendBg = (LinearLayout) view.findViewById(R$id.ll_msg_send_bg);
        this.etMsgDetail = (EditText) view.findViewById(R$id.et_msg_detail);
        this.tvMsgSend = (TextView) view.findViewById(R$id.tv_msg_detail_send);
        this.ivShortcut = (ImageView) view.findViewById(R$id.iv_shortcut);
        this.msgShortcutRoot = (LinearLayout) view.findViewById(R$id.ll_msg_shortcut_root);
        this.tvSave = (TextView) view.findViewById(R$id.tv_save);
        this.tvLength = (TextView) view.findViewById(R$id.tv_length);
        this.etShortCut = (EditText) view.findViewById(R$id.et_msg_shortcut_detail);
        this.webViewDesc = (Html5WebView) view.findViewById(R$id.web_view);
        this.myViewStab = (ViewStub) view.findViewById(R$id.view_stub);
        this.webViewDesc.getSettings().setLoadWithOverviewMode(false);
        this.webViewDesc.getSettings().setUseWideViewPort(false);
        initAdapter();
        initPop();
    }

    private void initPop() {
        String[] stringArray;
        this.shortcutPopDialog = new ShortcutPopDialog(getContext());
        this.shortcutPopDialog.setOnShortcutListener(new ShortcutPopDialog.OnShortcutListener() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.1
            @Override // com.tomatolive.library.p136ui.view.dialog.ShortcutPopDialog.OnShortcutListener
            public void onSelect(ShortcutItemEntity shortcutItemEntity) {
                MsgDetailListEntity createMyMsg = PrivateMsgDialog.this.createMyMsg(shortcutItemEntity.content);
                SendPrivateMsgListener sendPrivateMsgListener = PrivateMsgDialog.this.listener;
                if (sendPrivateMsgListener != null) {
                    sendPrivateMsgListener.sendPrivateMsg(createMyMsg);
                }
            }

            @Override // com.tomatolive.library.p136ui.view.dialog.ShortcutPopDialog.OnShortcutListener
            public void createShortcut() {
                PrivateMsgDialog.this.curContentType = 3;
                PrivateMsgDialog.this.initContent();
            }
        }).setBackground(0).setPopupGravity(48);
        ArrayList arrayList = new ArrayList();
        for (String str : this.mContext.getResources().getStringArray(R$array.fq_private_msg_shortcut_tips)) {
            ShortcutItemEntity shortcutItemEntity = new ShortcutItemEntity();
            shortcutItemEntity.content = str;
            arrayList.add(shortcutItemEntity);
        }
        arrayList.addAll(DBUtils.getAllShortcut());
        this.shortcutPopDialog.setNewData(arrayList);
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setAutoMeasureEnabled(false);
        linearLayoutManager.setOrientation(1);
        this.rvMsgList.setLayoutManager(linearLayoutManager);
        this.rvMsgList.setHasFixedSize(true);
        this.msgListAdapter.bindToRecyclerView(this.rvMsgList);
        this.rvMsgList.setItemAnimator(null);
        this.msgListAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 49));
        this.rvMsgList.setAdapter(this.msgListAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this.mContext);
        linearLayoutManager2.setAutoMeasureEnabled(false);
        linearLayoutManager2.setOrientation(1);
        this.rvMsgDetailList.setLayoutManager(linearLayoutManager2);
        this.rvMsgDetailList.setHasFixedSize(true);
        this.rvMsgDetailList.addItemDecoration(new RVDividerListMsg(this.mContext, 17170445));
        this.msgDetailListAdapter.addHeaderView(new PrivateMsgTipsView(this.mContext, this.isFromAnchor));
        this.msgDetailListAdapter.bindToRecyclerView(this.rvMsgDetailList);
        this.rvMsgDetailList.setAdapter(this.msgDetailListAdapter);
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        initContent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initContent() {
        int i = this.curContentType;
        int i2 = 0;
        if (i == 1) {
            this.tvTitle.setText(R$string.fq_msg_center);
            this.ivLeftIcon.setImageResource(R$drawable.fq_ic_delete);
            this.ivRightIcon.setVisibility(0);
            this.tvSave.setVisibility(8);
            sendMsgListRequest();
        } else if (i == 2) {
            this.tvTitle.setText(this.targetName);
            this.ivLeftIcon.setImageResource(R$drawable.fq_ic_achieve_back_black);
            this.ivRightIcon.setVisibility(8);
            this.tvSave.setVisibility(8);
            ImageView imageView = this.ivShortcut;
            if (!this.isFromAnchor) {
                i2 = 8;
            }
            imageView.setVisibility(i2);
            sendMsgDetailRequest();
        } else if (i == 3) {
            this.tvTitle.setText(R$string.fq_create_shortcut);
            this.ivLeftIcon.setImageResource(R$drawable.fq_ic_achieve_back_black);
            this.ivRightIcon.setVisibility(8);
            this.tvSave.setVisibility(0);
            showContentView();
        } else if (i != 4) {
        } else {
            this.tvTitle.setText(R$string.fq_msg_center_desc);
            this.ivLeftIcon.setImageResource(R$drawable.fq_ic_achieve_back_black);
            this.ivRightIcon.setVisibility(8);
            this.tvSave.setVisibility(8);
            sendDescRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoadingView() {
        this.tvLoading.setVisibility(0);
        this.rvMsgList.setVisibility(4);
        this.msgDetailListRoot.setVisibility(4);
        this.msgShortcutRoot.setVisibility(4);
        this.webViewDesc.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showContentView() {
        int i = 4;
        this.tvLoading.setVisibility(4);
        this.rvMsgList.setVisibility(this.curContentType == 1 ? 0 : 4);
        this.msgDetailListRoot.setVisibility(this.curContentType == 2 ? 0 : 4);
        this.msgShortcutRoot.setVisibility(this.curContentType == 3 ? 0 : 4);
        Html5WebView html5WebView = this.webViewDesc;
        if (this.curContentType == 4) {
            i = 0;
        }
        html5WebView.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        this.flLeftIcon.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PrivateMsgDialog.this.curContentType != 1) {
                    if (PrivateMsgDialog.this.curContentType == 2 || PrivateMsgDialog.this.curContentType == 4) {
                        SoftKeyboardUtils.hideSoftKeyboard(PrivateMsgDialog.this.etMsgDetail);
                        PrivateMsgDialog.this.curContentType = 1;
                        PrivateMsgDialog.this.initContent();
                    } else if (PrivateMsgDialog.this.curContentType != 3) {
                    } else {
                        SoftKeyboardUtils.hideSoftKeyboard(PrivateMsgDialog.this.etShortCut);
                        PrivateMsgDialog.this.curContentType = 2;
                        PrivateMsgDialog.this.initContent();
                    }
                } else if (!DBUtils.isClearAllMsgList()) {
                } else {
                    SureCancelDialog.newInstance(PrivateMsgDialog.this.getString(R$string.fq_msg_list), PrivateMsgDialog.this.getString(R$string.fq_sure_clear_all_msg_record), PrivateMsgDialog.this.getString(R$string.fq_btn_cancel), PrivateMsgDialog.this.getString(R$string.fq_btn_confirm), R$color.fq_text_black, new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.2.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view3) {
                            DBUtils.deletePrivateMsgList();
                            PrivateMsgDialog.this.msgListEntities.clear();
                            PrivateMsgDialog.this.msgListAdapter.notifyDataSetChanged();
                        }
                    }).show(PrivateMsgDialog.this.getFragmentManager());
                }
            }
        });
        this.rvMsgDetailList.setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == 1) {
                    SoftKeyboardUtils.hideSoftKeyboard(PrivateMsgDialog.this.etMsgDetail);
                    return false;
                }
                return false;
            }
        });
        this.ivRightIcon.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$PrivateMsgDialog$7OMuB9ST4T4Oq8GX6SdwX655ldA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PrivateMsgDialog.this.lambda$initListener$0$PrivateMsgDialog(view2);
            }
        });
        this.msgListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$PrivateMsgDialog$oqdkBjt5PDwGes1fyHVPZCO4G-I
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                PrivateMsgDialog.this.lambda$initListener$1$PrivateMsgDialog(baseQuickAdapter, view2, i);
            }
        });
        this.msgDetailListAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                MsgDetailListEntity msgDetailListEntity = (MsgDetailListEntity) baseQuickAdapter.getItem(i);
                if (msgDetailListEntity != null) {
                    if (PrivateMsgDialog.this.clipboardManager == null) {
                        PrivateMsgDialog privateMsgDialog = PrivateMsgDialog.this;
                        privateMsgDialog.clipboardManager = (ClipboardManager) ((BaseRxDialogFragment) privateMsgDialog).mContext.getSystemService("clipboard");
                    }
                    PrivateMsgDialog.this.clipboardManager.setPrimaryClip(ClipData.newPlainText("text/plain", msgDetailListEntity.msg));
                    PrivateMsgDialog.this.showToast(R$string.fq_copy_success);
                    return false;
                }
                return false;
            }
        });
        this.tvMsgSend.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                PrivateMsgDialog.this.sendMsg();
            }
        });
        this.etMsgDetail.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.6
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 4) {
                    PrivateMsgDialog.this.sendMsg();
                    return true;
                }
                return true;
            }
        });
        this.ivShortcut.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PrivateMsgDialog.this.shortcutPopDialog == null || PrivateMsgDialog.this.shortcutPopDialog.isShowing()) {
                    return;
                }
                PrivateMsgDialog.this.shortcutPopDialog.showPopupWindow(PrivateMsgDialog.this.llMsgSendBg);
            }
        });
        this.tvSave.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$PrivateMsgDialog$-zj2iPunXVXQwvTd0QsZzJLlt4Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PrivateMsgDialog.this.lambda$initListener$2$PrivateMsgDialog(view2);
            }
        });
        this.etShortCut.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.8
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 6) {
                    PrivateMsgDialog.this.saveShortCutMsg();
                    return true;
                }
                return true;
            }
        });
        RxTextView.textChanges(this.etShortCut).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<CharSequence>() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.9
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(CharSequence charSequence) {
                int length = charSequence.toString().length();
                if (length > 20) {
                    return;
                }
                PrivateMsgDialog.this.tvLength.setText(length + "/20");
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$PrivateMsgDialog(View view) {
        this.curContentType = 4;
        initContent();
    }

    public /* synthetic */ void lambda$initListener$1$PrivateMsgDialog(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.curMsgEntity = (MsgListEntity) baseQuickAdapter.getItem(i);
        MsgListEntity msgListEntity = this.curMsgEntity;
        if (msgListEntity == null) {
            return;
        }
        this.targetId = msgListEntity.targetId;
        String str = "";
        this.targetName = msgListEntity.getLastMsgDetailListEntity() == null ? str : this.curMsgEntity.getLastMsgDetailListEntity().targetName;
        if (this.curMsgEntity.getLastMsgDetailListEntity() != null) {
            str = this.curMsgEntity.getLastMsgDetailListEntity().targetAvatar;
        }
        this.targetAvatar = str;
        this.curContentType = 2;
        initContent();
    }

    public /* synthetic */ void lambda$initListener$2$PrivateMsgDialog(View view) {
        saveShortCutMsg();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMsg() {
        String trim = this.etMsgDetail.getEditableText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            showToast(getString(R$string.fq_msg_not_allow_empty));
        } else if (trim.length() > 52) {
            showToast(getString(R$string.fq_private_msg_length));
        } else {
            MsgDetailListEntity createMyMsg = createMyMsg(trim);
            this.etMsgDetail.setText("");
            SendPrivateMsgListener sendPrivateMsgListener = this.listener;
            if (sendPrivateMsgListener == null) {
                return;
            }
            sendPrivateMsgListener.sendPrivateMsg(createMyMsg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveShortCutMsg() {
        String trim = this.etShortCut.getEditableText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            showToast(R$string.fq_create_shortcut_not_empty);
            return;
        }
        SoftKeyboardUtils.hideSoftKeyboard(this.etShortCut);
        ShortcutItemEntity shortcutItemEntity = new ShortcutItemEntity();
        shortcutItemEntity.userId = UserInfoManager.getInstance().getUserId();
        shortcutItemEntity.content = trim;
        shortcutItemEntity.createTime = String.valueOf(System.currentTimeMillis());
        DBUtils.saveOneShortcut(shortcutItemEntity);
        showToast(R$string.fq_achieve_save_success);
        ShortcutPopDialog shortcutPopDialog = this.shortcutPopDialog;
        if (shortcutPopDialog != null) {
            shortcutPopDialog.addShortcutMsg(shortcutItemEntity);
        }
        this.etShortCut.setText("");
        this.curContentType = 2;
        initContent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MsgDetailListEntity createMyMsg(String str) {
        MsgDetailListEntity msgDetailListEntity = new MsgDetailListEntity();
        msgDetailListEntity.userId = UserInfoManager.getInstance().getUserId();
        msgDetailListEntity.targetId = this.targetId;
        msgDetailListEntity.targetAvatar = this.targetAvatar;
        msgDetailListEntity.targetName = this.targetName;
        msgDetailListEntity.msg = str;
        msgDetailListEntity.type = 1;
        msgDetailListEntity.time = String.valueOf(System.currentTimeMillis());
        msgDetailListEntity.status = -1;
        msgDetailListEntity.messageId = UUID.randomUUID().toString().replaceAll("-", "") + System.currentTimeMillis();
        DBUtils.saveOnePrivateMsgDetail(msgDetailListEntity);
        MsgListEntity msgListEntity = new MsgListEntity();
        msgListEntity.userId = UserInfoManager.getInstance().getUserId();
        msgListEntity.appId = UserInfoManager.getInstance().getAppId();
        msgListEntity.targetId = this.targetId;
        msgListEntity.time = String.valueOf(System.currentTimeMillis());
        DBUtils.saveOrUpdateMsgList(msgListEntity);
        this.msgDetailListAdapter.addMsg(msgDetailListEntity);
        return msgDetailListEntity;
    }

    public void changeMsgStatus(String str, String str2) {
        MsgDetailListAdapter msgDetailListAdapter = this.msgDetailListAdapter;
        if (msgDetailListAdapter != null) {
            msgDetailListAdapter.changeMsgStatus(str, NumberUtils.string2int(str2));
        }
    }

    public void changeNetStatus(boolean z) {
        this.socketStatus = !z;
        if (z) {
            View view = this.rlReconnRoot;
            if (view == null) {
                return;
            }
            view.setVisibility(8);
            return;
        }
        if (this.rlReconnRoot == null) {
            this.rlReconnRoot = this.myViewStab.inflate();
            this.tvTip = (TextView) this.rlReconnRoot.findViewById(R$id.tv_reconn_tip);
            this.tvClick = (TextView) this.rlReconnRoot.findViewById(R$id.tv_click_reconn);
            this.tvClick.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    PrivateMsgDialog.this.tvClick.setVisibility(4);
                    PrivateMsgDialog.this.tvTip.setText(PrivateMsgDialog.this.getString(R$string.fq_conning_socket));
                    SendPrivateMsgListener sendPrivateMsgListener = PrivateMsgDialog.this.listener;
                    if (sendPrivateMsgListener != null) {
                        sendPrivateMsgListener.onReConnSocket();
                    }
                }
            });
        }
        this.rlReconnRoot.setVisibility(0);
        this.tvTip.setText(getString(R$string.fq_socket_conn_error));
        this.tvClick.setVisibility(0);
    }

    public void setSendPrivateMsgListener(SendPrivateMsgListener sendPrivateMsgListener) {
        this.listener = sendPrivateMsgListener;
    }

    public void dealMsg(MsgListEntity msgListEntity, MsgDetailListEntity msgDetailListEntity) {
        int i = this.curContentType;
        if (i != 1) {
            if (i != 2 || !TextUtils.equals(this.targetId, msgDetailListEntity.targetId)) {
                return;
            }
            this.msgDetailListAdapter.addMsg(msgDetailListEntity);
            return;
        }
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= this.msgListEntities.size()) {
                break;
            } else if (TextUtils.equals(this.msgListEntities.get(i2).targetId, msgListEntity.targetId)) {
                MsgListAdapter msgListAdapter = this.msgListAdapter;
                msgListAdapter.notifyItemChanged(i2 + msgListAdapter.getHeaderLayoutCount());
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            return;
        }
        this.msgListAdapter.addMsg(msgListEntity);
    }

    private void sendMsgListRequest() {
        Observable.just(DBUtils.getAllMsgList()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<List<MsgListEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.11
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                PrivateMsgDialog.this.showLoadingView();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<MsgListEntity> list) {
                PrivateMsgDialog.this.msgListEntities = list;
                PrivateMsgDialog.this.msgListAdapter.setNewData(PrivateMsgDialog.this.msgListEntities);
                PrivateMsgDialog.this.showContentView();
                PrivateMsgDialog privateMsgDialog = PrivateMsgDialog.this;
                privateMsgDialog.changeNetStatus(!privateMsgDialog.socketStatus);
            }
        });
    }

    private void sendMsgDetailRequest() {
        Observable.just(DBUtils.getAllPrivateMsgDetail(UserInfoManager.getInstance().getUserId(), this.targetId)).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<List<MsgDetailListEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.12
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                PrivateMsgDialog.this.showLoadingView();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<MsgDetailListEntity> list) {
                if (list == null) {
                    return;
                }
                PrivateMsgDialog.this.curMsgDetailData = list;
                PrivateMsgDialog.this.msgDetailListAdapter.setNewData(PrivateMsgDialog.this.curMsgDetailData);
                ((LinearLayoutManager) PrivateMsgDialog.this.rvMsgDetailList.getLayoutManager()).scrollToPositionWithOffset(PrivateMsgDialog.this.curMsgDetailData.size() - 1, 0);
                PrivateMsgDialog.this.showContentView();
                PrivateMsgDialog privateMsgDialog = PrivateMsgDialog.this;
                privateMsgDialog.changeNetStatus(!privateMsgDialog.socketStatus);
            }
        });
    }

    private void sendDescRequest() {
        ApiRetrofit.getInstance().getApiService().getAppParamConfigService(new RequestParams().getCodeParams(ConstantUtils.APP_PARAM_PRIVATE_MSG)).map(new ServerResultFunction<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.14
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.PrivateMsgDialog.13
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                PrivateMsgDialog.this.showLoadingView();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(PopularCardEntity popularCardEntity) {
                if (popularCardEntity == null) {
                    return;
                }
                PrivateMsgDialog.this.showContentView();
                PrivateMsgDialog.this.webViewDesc.loadDataWithBaseURL(null, popularCardEntity.value, "text/html", "UTF-8", null);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }
}
