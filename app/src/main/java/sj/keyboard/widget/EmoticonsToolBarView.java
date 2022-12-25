package sj.keyboard.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.keyboard.view.R$color;
import com.keyboard.view.R$dimen;
import com.keyboard.view.R$drawable;
import com.keyboard.view.R$id;
import com.keyboard.view.R$layout;
import java.io.IOException;
import java.util.ArrayList;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.utils.imageloader.ImageLoader;

/* loaded from: classes4.dex */
public class EmoticonsToolBarView extends RelativeLayout {
    protected HorizontalScrollView hsv_toolbar;
    protected LinearLayout ly_tool;
    protected int mBtnWidth;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected OnToolBarItemClickListener mItemClickListeners;
    protected ArrayList<View> mToolBtnList;

    /* loaded from: classes4.dex */
    public interface OnToolBarItemClickListener {
        void onToolBarItemClick(PageSetEntity pageSetEntity);
    }

    public EmoticonsToolBarView(Context context) {
        this(context, null);
    }

    public EmoticonsToolBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mToolBtnList = new ArrayList<>();
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mInflater.inflate(R$layout.view_emoticonstoolbar, this);
        this.mContext = context;
        this.mBtnWidth = (int) context.getResources().getDimension(R$dimen.bar_tool_btn_width);
        this.hsv_toolbar = (HorizontalScrollView) findViewById(R$id.hsv_toolbar);
        this.ly_tool = (LinearLayout) findViewById(R$id.ly_tool);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        if (getChildCount() <= 3) {
            return;
        }
        throw new IllegalArgumentException("can host only two direct child");
    }

    public void addFixedToolItemView(View view, boolean z) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -1);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.hsv_toolbar.getLayoutParams();
        if (view.getId() <= 0) {
            view.setId(z ? R$id.id_toolbar_right : R$id.id_toolbar_left);
        }
        if (z) {
            layoutParams.addRule(11);
            layoutParams2.addRule(0, view.getId());
        } else {
            layoutParams.addRule(9);
            layoutParams2.addRule(1, view.getId());
        }
        addView(view, layoutParams);
        this.hsv_toolbar.setLayoutParams(layoutParams2);
    }

    protected View getCommonItemToolBtn() {
        LayoutInflater layoutInflater = this.mInflater;
        if (layoutInflater == null) {
            return null;
        }
        return layoutInflater.inflate(R$layout.item_toolbtn, (ViewGroup) null);
    }

    protected void initItemToolBtn(View view, int i, final PageSetEntity pageSetEntity, View.OnClickListener onClickListener) {
        ImageView imageView = (ImageView) view.findViewById(R$id.iv_icon);
        if (i > 0) {
            imageView.setImageResource(i);
        }
        imageView.setLayoutParams(new LinearLayout.LayoutParams(this.mBtnWidth, -1));
        if (pageSetEntity != null) {
            imageView.setTag(R$id.id_tag_pageset, pageSetEntity);
            try {
                ImageLoader.getInstance(this.mContext).displayImage(pageSetEntity.getIconUri(), imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (onClickListener == null) {
            onClickListener = new View.OnClickListener() { // from class: sj.keyboard.widget.EmoticonsToolBarView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    PageSetEntity pageSetEntity2;
                    OnToolBarItemClickListener onToolBarItemClickListener = EmoticonsToolBarView.this.mItemClickListeners;
                    if (onToolBarItemClickListener == null || (pageSetEntity2 = pageSetEntity) == null) {
                        return;
                    }
                    onToolBarItemClickListener.onToolBarItemClick(pageSetEntity2);
                }
            };
        }
        view.setOnClickListener(onClickListener);
    }

    protected View getToolBgBtn(View view) {
        return view.findViewById(R$id.iv_icon);
    }

    public void addFixedToolItemView(boolean z, int i, PageSetEntity pageSetEntity, View.OnClickListener onClickListener) {
        View commonItemToolBtn = getCommonItemToolBtn();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -1);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.hsv_toolbar.getLayoutParams();
        if (commonItemToolBtn.getId() <= 0) {
            commonItemToolBtn.setId(z ? R$id.id_toolbar_right : R$id.id_toolbar_left);
        }
        if (z) {
            layoutParams.addRule(11);
            layoutParams2.addRule(0, commonItemToolBtn.getId());
        } else {
            layoutParams.addRule(9);
            layoutParams2.addRule(1, commonItemToolBtn.getId());
        }
        addView(commonItemToolBtn, layoutParams);
        this.hsv_toolbar.setLayoutParams(layoutParams2);
        initItemToolBtn(commonItemToolBtn, i, pageSetEntity, onClickListener);
    }

    public void addToolItemView(PageSetEntity pageSetEntity) {
        addToolItemView(0, pageSetEntity, null);
    }

    public void addToolItemView(int i, View.OnClickListener onClickListener) {
        addToolItemView(i, null, onClickListener);
    }

    public void addToolItemView(int i, PageSetEntity pageSetEntity, View.OnClickListener onClickListener) {
        View commonItemToolBtn = getCommonItemToolBtn();
        initItemToolBtn(commonItemToolBtn, i, pageSetEntity, onClickListener);
        this.ly_tool.addView(commonItemToolBtn);
        this.mToolBtnList.add(getToolBgBtn(commonItemToolBtn));
    }

    public void setToolBtnSelect(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mToolBtnList.size(); i2++) {
            Object tag = this.mToolBtnList.get(i2).getTag(R$id.id_tag_pageset);
            if (tag != null && (tag instanceof PageSetEntity) && str.equals(((PageSetEntity) tag).getUuid())) {
                this.mToolBtnList.get(i2).setBackgroundColor(getResources().getColor(R$color.toolbar_btn_select));
                i = i2;
            } else {
                this.mToolBtnList.get(i2).setBackgroundResource(R$drawable.btn_toolbtn_bg);
            }
        }
        scrollToBtnPosition(i);
    }

    protected void scrollToBtnPosition(final int i) {
        if (i < this.ly_tool.getChildCount()) {
            this.hsv_toolbar.post(new Runnable() { // from class: sj.keyboard.widget.EmoticonsToolBarView.2
                @Override // java.lang.Runnable
                public void run() {
                    int scrollX = EmoticonsToolBarView.this.hsv_toolbar.getScrollX();
                    int left = EmoticonsToolBarView.this.ly_tool.getChildAt(i).getLeft();
                    if (left < scrollX) {
                        EmoticonsToolBarView.this.hsv_toolbar.scrollTo(left, 0);
                        return;
                    }
                    int width = left + EmoticonsToolBarView.this.ly_tool.getChildAt(i).getWidth();
                    int width2 = scrollX + EmoticonsToolBarView.this.hsv_toolbar.getWidth();
                    if (width <= width2) {
                        return;
                    }
                    EmoticonsToolBarView.this.hsv_toolbar.scrollTo(width - width2, 0);
                }
            });
        }
    }

    public void setBtnWidth(int i) {
        this.mBtnWidth = i;
    }

    public void setOnToolBarItemClickListener(OnToolBarItemClickListener onToolBarItemClickListener) {
        this.mItemClickListeners = onToolBarItemClickListener;
    }
}
