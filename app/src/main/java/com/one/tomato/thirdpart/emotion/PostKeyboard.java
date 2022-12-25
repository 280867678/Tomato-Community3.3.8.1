package com.one.tomato.thirdpart.emotion;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.p005v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
public class PostKeyboard extends AutoHeightLayout implements EmoticonsFuncView.OnEmoticonsPageViewListener, EmoticonsToolBarView.OnToolBarItemClickListener, EmotionEditText.OnBackKeyClickListener {
    public static final int FUNC_TYPE_APPPS = -2;
    public static final int FUNC_TYPE_EMOTION = -1;
    protected EmotionEditText et_input;
    protected ImageView iv_choose_img;
    protected ImageView iv_comment_tip;
    protected ImageView iv_emotion;
    protected LinearLayout linear_bottom_post_detail;
    protected boolean mDispatchKeyEventPreImeLock = false;
    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;
    protected LayoutInflater mInflater;
    protected FuncLayout mLyKvml;
    protected RecyclerView recyclerView_upload;
    protected RelativeLayout rl_comment;
    protected TextView tv_comment_bottom_num;

    public PostKeyboard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        inflateKeyboardBar();
        initView();
        initFuncView();
    }

    protected void inflateKeyboardBar() {
        this.mInflater.inflate(R.layout.layout_keyboard_post, this);
    }

    protected View inflateFunc() {
        return this.mInflater.inflate(R.layout.view_func_emoticon, (ViewGroup) null);
    }

    protected void initView() {
        this.recyclerView_upload = (RecyclerView) findViewById(R.id.recyclerView_upload);
        this.iv_emotion = (ImageView) findViewById(R.id.iv_emotion);
        this.et_input = (EmotionEditText) findViewById(R.id.et_input);
        this.iv_choose_img = (ImageView) findViewById(R.id.iv_choose_img);
        this.rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
        this.iv_comment_tip = (ImageView) findViewById(R.id.iv_comment_tip);
        this.tv_comment_bottom_num = (TextView) findViewById(R.id.tv_comment_bottom_num);
        this.mLyKvml = (FuncLayout) findViewById(R.id.ly_kvml);
        this.linear_bottom_post_detail = (LinearLayout) findViewById(R.id.linear_bottom_post_detail);
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
        this.iv_emotion.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.thirdpart.emotion.PostKeyboard.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!DBUtil.getLoginInfo().isLogin()) {
                    LoginActivity.Companion.startActivity(((AutoHeightLayout) PostKeyboard.this).mContext);
                } else {
                    PostKeyboard.this.toggleFuncView(-1);
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
    }

    protected void toggleFuncView(int i) {
        this.mLyKvml.toggleFuncView(i, isSoftKeyboardPop(), this.et_input);
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
    }

    @Override // sj.keyboard.widget.AutoHeightLayout, sj.keyboard.widget.SoftKeyboardSizeWatchLayout.OnResizeListener
    public void OnSoftClose() {
        super.OnSoftClose();
        if (this.mLyKvml.isOnlyShowSoftKeyboard()) {
            reset();
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
                isFocused = this.et_input.getShowSoftInputOnFocus();
            } else {
                isFocused = this.et_input.isFocused();
            }
            if (isFocused) {
                this.et_input.onKeyDown(keyEvent.getKeyCode(), keyEvent);
            }
        }
        return false;
    }

    public RecyclerView getRecyclerView_upload() {
        return this.recyclerView_upload;
    }

    public void setRecyclerView_upload(RecyclerView recyclerView) {
        this.recyclerView_upload = recyclerView;
    }

    public ImageView getIv_emotion() {
        return this.iv_emotion;
    }

    public EmotionEditText getEtChat() {
        return this.et_input;
    }

    public ImageView getIv_choose_img() {
        return this.iv_choose_img;
    }

    public RelativeLayout getRl_comment() {
        return this.rl_comment;
    }

    public ImageView getIv_comment_tip() {
        return this.iv_comment_tip;
    }

    public TextView getTv_comment_bottom_num() {
        return this.tv_comment_bottom_num;
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

    public LinearLayout getLinear_bottom_post_detail() {
        return this.linear_bottom_post_detail;
    }
}
