package com.one.tomato.thirdpart.emotion;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.p002v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.thirdpart.emotion.EmotionEditText;
import com.one.tomato.utils.DBUtil;
import java.util.ArrayList;
import java.util.Iterator;
import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.AutoHeightLayout;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsIndicatorView;
import sj.keyboard.widget.EmoticonsToolBarView;
import sj.keyboard.widget.FuncLayout;

/* loaded from: classes3.dex */
public class PapaKeyboard extends AutoHeightLayout implements EmoticonsFuncView.OnEmoticonsPageViewListener, EmoticonsToolBarView.OnToolBarItemClickListener, EmotionEditText.OnBackKeyClickListener {
    public static final int FUNC_TYPE_APPPS = -2;
    public static final int FUNC_TYPE_EMOTION = -1;
    protected EmotionEditText et_input;
    protected ImageView iv_emotion;
    protected LinearLayout ll_comment;
    protected boolean mDispatchKeyEventPreImeLock = false;
    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;
    protected LayoutInflater mInflater;
    protected FuncLayout mLyKvml;

    public PapaKeyboard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        inflateKeyboardBar();
        initView();
        initFuncView();
    }

    protected void inflateKeyboardBar() {
        this.mInflater.inflate(R.layout.layout_keyboard_papa, this);
    }

    protected View inflateFunc() {
        return this.mInflater.inflate(R.layout.view_func_emoticon, (ViewGroup) null);
    }

    protected void initView() {
        this.ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
        this.iv_emotion = (ImageView) findViewById(R.id.iv_emotion);
        this.et_input = (EmotionEditText) findViewById(R.id.et_input);
        this.mLyKvml = (FuncLayout) findViewById(R.id.ly_kvml);
        this.et_input.setOnBackKeyClickListener(this);
    }

    protected void initFuncView() {
        initEmoticonFuncView();
        initEditView();
    }

    protected void initEmoticonFuncView() {
        this.mLyKvml.addFuncView(-1, inflateFunc());
        this.mEmoticonsFuncView = (EmoticonsFuncView) findViewById(R.id.view_epv);
        this.mEmoticonsIndicatorView = (EmoticonsIndicatorView) findViewById(R.id.view_eiv);
        this.mEmoticonsToolBarView = (EmoticonsToolBarView) findViewById(R.id.view_etv);
        this.mEmoticonsFuncView.setOnIndicatorListener(this);
        this.mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
    }

    protected void initEditView() {
        this.iv_emotion.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.thirdpart.emotion.PapaKeyboard.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!DBUtil.getLoginInfo().isLogin()) {
                    LoginActivity.Companion.startActivity(((AutoHeightLayout) PapaKeyboard.this).mContext);
                } else {
                    PapaKeyboard.this.toggleFuncView(-1);
                }
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
        this.ll_comment.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        setFocusable(false);
    }

    public void toggleFuncView(int i) {
        this.mLyKvml.toggleFuncView(i, isSoftKeyboardPop(), this.et_input);
        this.ll_comment.setBackgroundColor(-1);
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
        this.ll_comment.setBackgroundColor(-1);
    }

    @Override // sj.keyboard.widget.AutoHeightLayout, sj.keyboard.widget.SoftKeyboardSizeWatchLayout.OnResizeListener
    public void OnSoftClose() {
        super.OnSoftClose();
        if (this.mLyKvml.isOnlyShowSoftKeyboard()) {
            reset();
        }
        this.et_input.setFocusable(false);
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

    @Override // sj.keyboard.widget.EmoticonsToolBarView.OnToolBarItemClickListener
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        this.mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }

    @Override // com.one.tomato.thirdpart.emotion.EmotionEditText.OnBackKeyClickListener
    public void onBackKeyClick() {
        if (this.mLyKvml.isShown()) {
            this.mDispatchKeyEventPreImeLock = true;
            reset();
        }
        this.ll_comment.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            this.ll_comment.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
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

    public LinearLayout getLl_comment() {
        return this.ll_comment;
    }

    public ImageView getIv_emotion() {
        return this.iv_emotion;
    }

    public EmotionEditText getEtChat() {
        return this.et_input;
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
