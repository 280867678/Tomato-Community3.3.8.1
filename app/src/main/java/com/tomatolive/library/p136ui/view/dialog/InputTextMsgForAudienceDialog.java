package com.tomatolive.library.p136ui.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.p002v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import com.blankj.utilcode.util.SPUtils;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.widget.SmoothInputLayout;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.EmojiFilter;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SystemUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.InputTextMsgForAudienceDialog */
/* loaded from: classes3.dex */
public class InputTextMsgForAudienceDialog extends Dialog implements View.OnTouchListener, SmoothInputLayout.OnKeyboardChangeListener {
    private EditText etMsg;
    private ImageButton ibGuard;
    private ImageButton ibNobility;
    private ImageButton ibNormal;
    private ImageButton ibTrumpet;
    private List<ImageButton> imageButtons;
    private boolean isShowBoard;
    private boolean isShowTrumpet;
    private boolean isTrumpetClick;
    private SmoothInputLayout lytContent;
    private Context mContext;
    private OnTextSendListener mOnTextSendListener;
    private int myGuardType;
    private String myRole;
    private int myUserGrade;
    private View outSideView;
    private String tempBanMsg;
    private TextView tvInputType;
    private TextView tvTrumpet;
    private TextView tvTrumpetCount;
    private TextView tvTrumpetRule;
    private int inputType = 0;
    private int banType = ConstantUtils.NO_BAN_TYPE;
    private int gradeSet10CharacterLimit = 15;
    private int mNowh = -1;
    private View.OnClickListener myClickListener = new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.InputTextMsgForAudienceDialog.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.getId() == R$id.ib_nobility) {
                if (InputTextMsgForAudienceDialog.this.mOnTextSendListener == null) {
                    return;
                }
                InputTextMsgForAudienceDialog.this.mOnTextSendListener.selectTypeDialog(view, 2);
            } else if (view.getId() == R$id.ib_normal) {
                InputTextMsgForAudienceDialog.this.selectNormal();
            } else if (view.getId() == R$id.ib_guard) {
                if (InputTextMsgForAudienceDialog.this.mOnTextSendListener == null) {
                    return;
                }
                InputTextMsgForAudienceDialog.this.mOnTextSendListener.selectTypeDialog(view, 1);
            } else if (view.getId() != R$id.ib_trumpet || InputTextMsgForAudienceDialog.this.mOnTextSendListener == null) {
            } else {
                InputTextMsgForAudienceDialog.this.mOnTextSendListener.selectTypeDialog(view, 3);
            }
        }
    };

    /* renamed from: com.tomatolive.library.ui.view.dialog.InputTextMsgForAudienceDialog$OnTextSendListener */
    /* loaded from: classes3.dex */
    public interface OnTextSendListener {
        void onTextSend(String str, int i);

        void selectTypeDialog(View view, int i);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.SmoothInputLayout.OnKeyboardChangeListener
    public void onKeyboardChanged(boolean z) {
    }

    public InputTextMsgForAudienceDialog(Context context, OnTextSendListener onTextSendListener) {
        super(context);
        this.mContext = context;
        this.mOnTextSendListener = onTextSendListener;
        setContentView(R$layout.fq_dialog_input_audience_send_msg);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().setLayout(-1, -1);
        getWindow().setSoftInputMode(20);
        initView();
        initListener();
        getLastType();
        initGlobalListener();
    }

    private void initGlobalListener() {
        this.mNowh = SystemUtils.getContentViewInvisibleHeight(this.mNowh, (Activity) this.mContext);
        ((FrameLayout) ((Activity) this.mContext).findViewById(16908290)).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$InputTextMsgForAudienceDialog$8-DnmqocBgL1OQfEA1ApD6MXUtw
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                InputTextMsgForAudienceDialog.this.lambda$initGlobalListener$0$InputTextMsgForAudienceDialog();
            }
        });
    }

    public /* synthetic */ void lambda$initGlobalListener$0$InputTextMsgForAudienceDialog() {
        int contentViewInvisibleHeight = SystemUtils.getContentViewInvisibleHeight(this.mNowh, (Activity) this.mContext);
        if (this.mNowh != contentViewInvisibleHeight) {
            this.mNowh = contentViewInvisibleHeight;
            int i = this.mNowh;
            if (i > 0) {
                this.lytContent.updateHeight(i);
                keyBoardShow();
                return;
            }
            this.lytContent.setKeyboardBoolean(false);
            keyBoardHide();
        }
    }

    private void getLastType() {
        setTrumpetCount(false, 0);
        this.tvTrumpetRule.setText(Html.fromHtml(this.mContext.getString(R$string.trumpet_count_rule)));
        this.inputType = SPUtils.getInstance().getInt(ConstantUtils.INPUT_TYPE, 0);
        if (this.inputType == 3) {
            this.inputType = 0;
        }
        initSelectTypeNoTrumpet();
    }

    public void setTrumpetCount(boolean z, int i) {
        this.tvTrumpetCount.setText(Html.fromHtml(this.mContext.getString(z ? R$string.trumpet_count_status_normal : R$string.trumpet_count_status_freeze, String.valueOf(i))));
    }

    private void initSelectTypeNoTrumpet() {
        ImageButton imageButton;
        int i = this.inputType;
        if (i == 0) {
            this.tvInputType.setText(R$string.fq_normal_danmu);
            imageButton = this.ibNormal;
        } else if (i == 1) {
            imageButton = this.ibGuard;
            this.tvInputType.setText(R$string.fq_guard_danmu);
        } else if (i != 2) {
            imageButton = null;
        } else {
            imageButton = this.ibNobility;
            this.tvInputType.setText(R$string.fq_nobility_danmu);
        }
        selectIcon(imageButton);
        int i2 = this.banType;
        if (i2 != 2320) {
            switch (i2) {
                case ConstantUtils.NO_BAN_TYPE /* 2311 */:
                    setEditTextHintAndLength();
                    return;
                case ConstantUtils.NORMAL_BAN_TYPE /* 2312 */:
                    setBandOnePost(this.tempBanMsg);
                    return;
                case ConstantUtils.ALL_BAN_TYPE /* 2313 */:
                    setBanedAllPost();
                    return;
                default:
                    return;
            }
        }
        setBandPostBySuperManager();
    }

    private void initView() {
        this.outSideView = findViewById(R$id.sil_v_list);
        this.outSideView.setOnTouchListener(this);
        this.tvTrumpet = (TextView) findViewById(R$id.tv_trumpet);
        this.lytContent = (SmoothInputLayout) findViewById(R$id.sil_lyt_content);
        this.lytContent.setOutSideView(this.outSideView);
        this.tvTrumpetRule = (TextView) findViewById(R$id.tv_trumpet_rule);
        this.ibTrumpet = (ImageButton) findViewById(R$id.ib_trumpet);
        this.ibGuard = (ImageButton) findViewById(R$id.ib_guard);
        this.ibNormal = (ImageButton) findViewById(R$id.ib_normal);
        this.ibNobility = (ImageButton) findViewById(R$id.ib_nobility);
        this.tvTrumpetCount = (TextView) findViewById(R$id.tv_trumpet_count);
        this.imageButtons = new ArrayList();
        this.imageButtons.add(this.ibNormal);
        this.imageButtons.add(this.ibGuard);
        this.imageButtons.add(this.ibNobility);
        this.imageButtons.add(this.ibTrumpet);
        this.tvInputType = (TextView) findViewById(R$id.tv_input_type);
        this.etMsg = (EditText) findViewById(R$id.et_input_message);
        this.etMsg.setFocusable(true);
        this.etMsg.setFocusableInTouchMode(true);
        this.etMsg.requestFocus();
    }

    private void selectTypeNoTrumpet() {
        this.lytContent.setNoSaveHeight(false);
        this.tvTrumpet.setVisibility(8);
        this.isTrumpetClick = false;
        showSoftKeyBoard();
        initSelectTypeNoTrumpet();
    }

    public void selectNormal() {
        this.inputType = 0;
        selectTypeNoTrumpet();
    }

    public void selectNobility() {
        this.inputType = 2;
        selectTypeNoTrumpet();
    }

    public void selectGuard() {
        this.inputType = 1;
        selectTypeNoTrumpet();
    }

    public void selectTrumpet(View view) {
        int i = this.banType;
        if (i != 2320) {
            switch (i) {
                case ConstantUtils.NO_BAN_TYPE /* 2311 */:
                    break;
                default:
                    return;
                case ConstantUtils.NORMAL_BAN_TYPE /* 2312 */:
                case ConstantUtils.ALL_BAN_TYPE /* 2313 */:
                    this.etMsg.setText("");
                    break;
            }
            this.inputType = 3;
            setEditTextHintAndLength();
        }
        this.tvTrumpet.setVisibility(0);
        this.isTrumpetClick = true;
        this.tvInputType.setText(R$string.fq_trumpet_danmu);
        this.inputType = 3;
        if (!this.isShowTrumpet) {
            this.lytContent.showInputPane(false);
        }
        if (view instanceof ImageButton) {
            selectIcon((ImageButton) view);
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R$id.sil_v_list) {
            this.lytContent.closeKeyboard(true);
            this.lytContent.closeInputPane();
            dismiss();
            return false;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002f, code lost:
        if (r0 != 3) goto L12;
     */
    @Override // android.app.Dialog
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void show() {
        if (this.isShowTrumpet) {
            selectTrumpet(this.ibTrumpet);
            this.isShowTrumpet = false;
            this.isTrumpetClick = true;
            this.inputType = 3;
            this.lytContent.settShowInputPane(true);
            this.lytContent.setNoSaveHeight(true);
            getWindow().setSoftInputMode(2);
        } else {
            int i = this.inputType;
            if (i != 0) {
                if (i == 1) {
                    if (!AppUtils.isGuardUser(this.myGuardType)) {
                        this.inputType = 0;
                    }
                    this.tvTrumpet.setVisibility(8);
                    initSelectTypeNoTrumpet();
                } else if (i == 2) {
                    if (!AppUtils.highThanBoJue(UserInfoManager.getInstance().getNobilityType())) {
                        this.inputType = 0;
                    }
                    this.tvTrumpet.setVisibility(8);
                    initSelectTypeNoTrumpet();
                }
                this.lytContent.settShowInputPane(false);
                this.lytContent.setNoSaveHeight(false);
                getWindow().setSoftInputMode(20);
            }
            this.inputType = 0;
            this.tvTrumpet.setVisibility(8);
            initSelectTypeNoTrumpet();
            this.lytContent.settShowInputPane(false);
            this.lytContent.setNoSaveHeight(false);
            getWindow().setSoftInputMode(20);
        }
        super.show();
    }

    private void selectIcon(ImageButton imageButton) {
        Iterator<ImageButton> it2 = this.imageButtons.iterator();
        while (it2.hasNext()) {
            ImageButton next = it2.next();
            next.setSelected(next == imageButton);
        }
    }

    private void initListener() {
        this.lytContent.setOnKeyboardChangeListener(this);
        this.ibGuard.setOnClickListener(this.myClickListener);
        this.ibNobility.setOnClickListener(this.myClickListener);
        this.ibNormal.setOnClickListener(this.myClickListener);
        this.ibTrumpet.setOnClickListener(this.myClickListener);
        this.etMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$InputTextMsgForAudienceDialog$llEPTdd5J5MC7P5oj9zv3YcgPv0
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return InputTextMsgForAudienceDialog.this.lambda$initListener$1$InputTextMsgForAudienceDialog(textView, i, keyEvent);
            }
        });
        this.etMsg.addTextChangedListener(new TextWatcher() { // from class: com.tomatolive.library.ui.view.dialog.InputTextMsgForAudienceDialog.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (EmojiFilter.containsEmoji(editable.toString())) {
                    InputTextMsgForAudienceDialog.this.etMsg.setText(EmojiFilter.filterEmoji(editable.toString()));
                    InputTextMsgForAudienceDialog.this.etMsg.setSelection(InputTextMsgForAudienceDialog.this.etMsg.getText().length());
                }
            }
        });
    }

    public /* synthetic */ boolean lambda$initListener$1$InputTextMsgForAudienceDialog(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 4) {
            if (i != 4) {
                return false;
            }
            dismiss();
            return false;
        }
        String trim = this.etMsg.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            return true;
        }
        OnTextSendListener onTextSendListener = this.mOnTextSendListener;
        if (onTextSendListener == null) {
            return false;
        }
        onTextSendListener.onTextSend(trim, this.inputType);
        this.etMsg.setText("");
        dismiss();
        return false;
    }

    private void setEditTextHintAndLength() {
        String string;
        if (this.etMsg != null) {
            int i = 53;
            if (AppUtils.isAudience(this.myRole) && !AppUtils.isNobilityUser() && !AppUtils.isGuardUser(this.myGuardType) && this.myUserGrade < this.gradeSet10CharacterLimit) {
                i = 10;
            }
            int i2 = this.inputType;
            String str = "";
            if (i2 != 0) {
                if (i2 == 1) {
                    string = this.mContext.getString(R$string.fq_send_danmu_tips);
                    this.etMsg.setText(str);
                } else if (i2 == 2) {
                    string = this.mContext.getString(R$string.fq_send_danmu_nobility_tips);
                    this.etMsg.setText(str);
                } else if (i2 == 3) {
                    i = 20;
                }
                str = string;
                i = 15;
            } else {
                this.etMsg.setText(str);
                str = this.mContext.getString(R$string.fq_text_input_hint);
            }
            this.etMsg.setHint(str);
            this.etMsg.setFocusableInTouchMode(true);
            this.etMsg.setFocusable(true);
            this.etMsg.requestFocus();
            this.etMsg.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
            this.etMsg.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_colorBlack));
        }
    }

    private void cancelBanInternal() {
        setEditTextHintAndLength();
    }

    private void closeSoftKeyBoard() {
        this.lytContent.closeKeyboard(true);
    }

    private void showSoftKeyBoard() {
        this.lytContent.showKeyboard();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        if (this.lytContent.isInputPaneOpen()) {
            this.lytContent.closeInputPane();
        }
        this.isShowBoard = false;
        closeSoftKeyBoard();
        SPUtils.getInstance().put(ConstantUtils.INPUT_TYPE, this.inputType);
        super.dismiss();
    }

    public void onDestroy() {
        List<ImageButton> list = this.imageButtons;
        if (list != null) {
            list.clear();
            this.imageButtons = null;
        }
        if (this.mOnTextSendListener != null) {
            this.mOnTextSendListener = null;
        }
        EditText editText = this.etMsg;
        if (editText != null) {
            editText.clearFocus();
            this.etMsg.setOnEditorActionListener(null);
        }
    }

    public void setMyGuardType(int i) {
        this.myGuardType = i;
    }

    public void keyBoardHide() {
        if (this.isTrumpetClick || !this.isShowBoard) {
            return;
        }
        dismiss();
    }

    public void keyBoardShow() {
        this.isShowBoard = true;
        if (this.inputType == 3) {
            this.isTrumpetClick = false;
        }
    }

    public void show(boolean z) {
        this.isShowTrumpet = z;
        show();
    }

    public void setSpeakWordLimit(int i) {
        this.gradeSet10CharacterLimit = i;
    }

    public void setMyRole(String str) {
        this.myRole = str;
    }

    public void setMyUserGrade(String str) {
        this.myUserGrade = NumberUtils.string2int(str);
    }

    public void cancelBandPost() {
        this.banType = ConstantUtils.NO_BAN_TYPE;
        cancelBanInternal();
    }

    public void setBandOnePost(String str) {
        this.banType = ConstantUtils.NORMAL_BAN_TYPE;
        this.tempBanMsg = str;
        String format = String.format(this.mContext.getString(R$string.fq_banned_time_to), str);
        if (this.inputType != 3) {
            setBanMsgInternal(format);
        }
    }

    public void setBandPostBySuperManager() {
        this.banType = ConstantUtils.SUPER_BAN_TYPE;
        setBanMsgInternal(this.mContext.getString(R$string.fq_super_manager));
    }

    public void setBanedAllPost() {
        this.banType = ConstantUtils.ALL_BAN_TYPE;
        if (this.inputType != 3) {
            setBanMsgInternal(this.mContext.getString(R$string.fq_text_input_banned_hint));
        }
    }

    private void setBanMsgInternal(String str) {
        EditText editText = this.etMsg;
        if (editText != null) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(53)});
            this.etMsg.setText(str);
            this.etMsg.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_colorPrimary));
            this.etMsg.setFocusable(false);
            this.etMsg.setFocusableInTouchMode(false);
        }
    }
}
