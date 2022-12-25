package sj.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.keyboard.view.R$drawable;
import com.keyboard.view.R$id;
import com.keyboard.view.R$layout;
import java.util.ArrayList;
import java.util.Iterator;
import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.AutoHeightLayout;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsIndicatorView;
import sj.keyboard.widget.EmoticonsToolBarView;
import sj.keyboard.widget.FuncLayout;

/* loaded from: classes4.dex */
public class XhsEmoticonsKeyBoard extends AutoHeightLayout implements View.OnClickListener, EmoticonsFuncView.OnEmoticonsPageViewListener, EmoticonsToolBarView.OnToolBarItemClickListener, EmoticonsEditText.OnBackKeyClickListener, FuncLayout.OnFuncChangeListener {
    public static final int FUNC_TYPE_APPPS = -2;
    public static final int FUNC_TYPE_EMOTION = -1;
    protected ImageView mBtnFace;
    protected ImageView mBtnMultimedia;
    protected Button mBtnSend;
    protected Button mBtnVoice;
    protected ImageView mBtnVoiceOrText;
    protected boolean mDispatchKeyEventPreImeLock = false;
    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;
    protected EmoticonsEditText mEtChat;
    protected LayoutInflater mInflater;
    protected FuncLayout mLyKvml;
    protected RelativeLayout mRlInput;

    public XhsEmoticonsKeyBoard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        inflateKeyboardBar();
        initView();
        initFuncView();
    }

    protected void inflateKeyboardBar() {
        this.mInflater.inflate(R$layout.view_keyboard_xhs, this);
    }

    protected View inflateFunc() {
        return this.mInflater.inflate(R$layout.view_func_emoticon, (ViewGroup) null);
    }

    protected void initView() {
        this.mBtnVoiceOrText = (ImageView) findViewById(R$id.btn_voice_or_text);
        this.mBtnVoice = (Button) findViewById(R$id.btn_voice);
        this.mEtChat = (EmoticonsEditText) findViewById(R$id.et_chat);
        this.mBtnFace = (ImageView) findViewById(R$id.btn_face);
        this.mRlInput = (RelativeLayout) findViewById(R$id.rl_input);
        this.mBtnMultimedia = (ImageView) findViewById(R$id.btn_multimedia);
        this.mBtnSend = (Button) findViewById(R$id.btn_send);
        this.mLyKvml = (FuncLayout) findViewById(R$id.ly_kvml);
        this.mBtnVoiceOrText.setOnClickListener(this);
        this.mBtnFace.setOnClickListener(this);
        this.mBtnMultimedia.setOnClickListener(this);
        this.mEtChat.setOnBackKeyClickListener(this);
    }

    protected void initFuncView() {
        initEmoticonFuncView();
        initEditView();
    }

    protected void initEmoticonFuncView() {
        this.mLyKvml.addFuncView(-1, inflateFunc());
        this.mEmoticonsFuncView = (EmoticonsFuncView) findViewById(R$id.view_epv);
        this.mEmoticonsIndicatorView = (EmoticonsIndicatorView) findViewById(R$id.view_eiv);
        this.mEmoticonsToolBarView = (EmoticonsToolBarView) findViewById(R$id.view_etv);
        this.mEmoticonsFuncView.setOnIndicatorListener(this);
        this.mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
        this.mLyKvml.setOnFuncChangeListener(this);
    }

    protected void initEditView() {
        this.mEtChat.setOnTouchListener(new View.OnTouchListener() { // from class: sj.keyboard.XhsEmoticonsKeyBoard.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!XhsEmoticonsKeyBoard.this.mEtChat.isFocused()) {
                    XhsEmoticonsKeyBoard.this.mEtChat.setFocusable(true);
                    XhsEmoticonsKeyBoard.this.mEtChat.setFocusableInTouchMode(true);
                    return false;
                }
                return false;
            }
        });
        this.mEtChat.addTextChangedListener(new TextWatcher() { // from class: sj.keyboard.XhsEmoticonsKeyBoard.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    XhsEmoticonsKeyBoard.this.mBtnSend.setVisibility(0);
                    XhsEmoticonsKeyBoard.this.mBtnMultimedia.setVisibility(8);
                    XhsEmoticonsKeyBoard.this.mBtnSend.setBackgroundResource(R$drawable.btn_send_bg);
                    return;
                }
                XhsEmoticonsKeyBoard.this.mBtnMultimedia.setVisibility(0);
                XhsEmoticonsKeyBoard.this.mBtnSend.setVisibility(8);
            }
        });
    }

    public void setAdapter(PageSetAdapter pageSetAdapter) {
        ArrayList<PageSetEntity> pageSetEntityList;
        if (pageSetAdapter != null && (pageSetEntityList = pageSetAdapter.getPageSetEntityList()) != null) {
            Iterator<PageSetEntity> it2 = pageSetEntityList.iterator();
            while (it2.hasNext()) {
                this.mEmoticonsToolBarView.addToolItemView(it2.next());
            }
        }
        this.mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    public void addFuncView(View view) {
        this.mLyKvml.addFuncView(-2, view);
    }

    public void reset() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(this);
        this.mLyKvml.hideAllFuncView();
        this.mBtnFace.setImageResource(R$drawable.icon_face_nomal);
    }

    protected void showVoice() {
        this.mRlInput.setVisibility(8);
        this.mBtnVoice.setVisibility(0);
        reset();
    }

    protected void checkVoice() {
        if (this.mBtnVoice.isShown()) {
            this.mBtnVoiceOrText.setImageResource(R$drawable.btn_voice_or_text_keyboard);
        } else {
            this.mBtnVoiceOrText.setImageResource(R$drawable.btn_voice_or_text);
        }
    }

    protected void showText() {
        this.mRlInput.setVisibility(0);
        this.mBtnVoice.setVisibility(8);
    }

    protected void toggleFuncView(int i) {
        showText();
        this.mLyKvml.toggleFuncView(i, isSoftKeyboardPop(), this.mEtChat);
    }

    @Override // sj.keyboard.widget.FuncLayout.OnFuncChangeListener
    public void onFuncChange(int i) {
        if (-1 == i) {
            this.mBtnFace.setImageResource(R$drawable.icon_face_pop);
        } else {
            this.mBtnFace.setImageResource(R$drawable.icon_face_nomal);
        }
        checkVoice();
    }

    protected void setFuncViewHeight(int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLyKvml.getLayoutParams();
        layoutParams.height = i;
        this.mLyKvml.setLayoutParams(layoutParams);
    }

    @Override // sj.keyboard.widget.AutoHeightLayout
    public void onSoftKeyboardHeightChanged(int i) {
        this.mLyKvml.updateHeight(i);
    }

    @Override // sj.keyboard.widget.AutoHeightLayout, sj.keyboard.widget.SoftKeyboardSizeWatchLayout.OnResizeListener
    public void OnSoftPop(int i) {
        super.OnSoftPop(i);
        this.mLyKvml.setVisibility(true);
        this.mLyKvml.getClass();
        onFuncChange(Integer.MIN_VALUE);
    }

    @Override // sj.keyboard.widget.AutoHeightLayout, sj.keyboard.widget.SoftKeyboardSizeWatchLayout.OnResizeListener
    public void OnSoftClose() {
        super.OnSoftClose();
        if (this.mLyKvml.isOnlyShowSoftKeyboard()) {
            reset();
        } else {
            onFuncChange(this.mLyKvml.getCurrentFuncKey());
        }
    }

    public void addOnFuncKeyBoardListener(FuncLayout.OnFuncKeyBoardListener onFuncKeyBoardListener) {
        this.mLyKvml.addOnKeyBoardListener(onFuncKeyBoardListener);
    }

    @Override // sj.keyboard.widget.EmoticonsFuncView.OnEmoticonsPageViewListener
    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
        this.mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
    }

    @Override // sj.keyboard.widget.EmoticonsFuncView.OnEmoticonsPageViewListener
    public void playTo(int i, PageSetEntity pageSetEntity) {
        this.mEmoticonsIndicatorView.playTo(i, pageSetEntity);
    }

    @Override // sj.keyboard.widget.EmoticonsFuncView.OnEmoticonsPageViewListener
    public void playBy(int i, int i2, PageSetEntity pageSetEntity) {
        this.mEmoticonsIndicatorView.playBy(i, i2, pageSetEntity);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.btn_voice_or_text) {
            if (this.mRlInput.isShown()) {
                this.mBtnVoiceOrText.setImageResource(R$drawable.btn_voice_or_text_keyboard);
                showVoice();
                return;
            }
            showText();
            this.mBtnVoiceOrText.setImageResource(R$drawable.btn_voice_or_text);
            EmoticonsKeyboardUtils.openSoftKeyboard(this.mEtChat);
        } else if (id == R$id.btn_face) {
            toggleFuncView(-1);
        } else if (id != R$id.btn_multimedia) {
        } else {
            toggleFuncView(-2);
        }
    }

    @Override // sj.keyboard.widget.EmoticonsToolBarView.OnToolBarItemClickListener
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        this.mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }

    @Override // sj.keyboard.widget.EmoticonsEditText.OnBackKeyClickListener
    public void onBackKeyClick() {
        if (this.mLyKvml.isShown()) {
            this.mDispatchKeyEventPreImeLock = true;
            reset();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            if (this.mDispatchKeyEventPreImeLock) {
                this.mDispatchKeyEventPreImeLock = false;
                return true;
            } else if (this.mLyKvml.isShown()) {
                reset();
                return true;
            } else {
                return super.dispatchKeyEvent(keyEvent);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean requestFocus(int i, Rect rect) {
        if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            return false;
        }
        return super.requestFocus(i, rect);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            return;
        }
        super.requestChildFocus(view, view2);
    }

    public boolean dispatchKeyEventInFullScreen(KeyEvent keyEvent) {
        boolean isFocused;
        if (keyEvent == null) {
            return false;
        }
        if (keyEvent.getKeyCode() == 4 && EmoticonsKeyboardUtils.isFullScreen((Activity) getContext()) && this.mLyKvml.isShown()) {
            reset();
            return true;
        }
        if (keyEvent.getAction() == 0) {
            if (Build.VERSION.SDK_INT >= 21) {
                isFocused = this.mEtChat.getShowSoftInputOnFocus();
            } else {
                isFocused = this.mEtChat.isFocused();
            }
            if (isFocused) {
                this.mEtChat.onKeyDown(keyEvent.getKeyCode(), keyEvent);
            }
        }
        return false;
    }

    public EmoticonsEditText getEtChat() {
        return this.mEtChat;
    }

    public Button getBtnVoice() {
        return this.mBtnVoice;
    }

    public Button getBtnSend() {
        return this.mBtnSend;
    }

    public EmoticonsFuncView getEmoticonsFuncView() {
        return this.mEmoticonsFuncView;
    }

    public EmoticonsIndicatorView getEmoticonsIndicatorView() {
        return this.mEmoticonsIndicatorView;
    }

    public EmoticonsToolBarView getEmoticonsToolBarView() {
        return this.mEmoticonsToolBarView;
    }
}
