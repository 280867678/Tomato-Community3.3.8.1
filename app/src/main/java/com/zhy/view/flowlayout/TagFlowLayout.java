package com.zhy.view.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.zhy.view.flowlayout.TagAdapter;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public class TagFlowLayout extends FlowLayout implements TagAdapter.OnDataChangedListener {
    private OnSelectListener mOnSelectListener;
    private OnTagClickListener mOnTagClickListener;
    private int mSelectedMax;
    private Set<Integer> mSelectedView;
    private TagAdapter mTagAdapter;

    /* loaded from: classes4.dex */
    public interface OnSelectListener {
        void onSelected(Set<Integer> set);
    }

    /* loaded from: classes4.dex */
    public interface OnTagClickListener {
        boolean onTagClick(View view, int i, FlowLayout flowLayout);
    }

    public TagFlowLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSelectedMax = -1;
        this.mSelectedView = new HashSet();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.TagFlowLayout);
        this.mSelectedMax = obtainStyledAttributes.getInt(R$styleable.TagFlowLayout_max_select, -1);
        obtainStyledAttributes.recycle();
    }

    public TagFlowLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.zhy.view.flowlayout.FlowLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            TagView tagView = (TagView) getChildAt(i3);
            if (tagView.getVisibility() != 8 && tagView.getTagView().getVisibility() == 8) {
                tagView.setVisibility(8);
            }
        }
        super.onMeasure(i, i2);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.mOnSelectListener = onSelectListener;
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    public void setAdapter(TagAdapter tagAdapter) {
        this.mTagAdapter = tagAdapter;
        this.mTagAdapter.setOnDataChangedListener(this);
        this.mSelectedView.clear();
        changeAdapter();
    }

    private void changeAdapter() {
        removeAllViews();
        TagAdapter tagAdapter = this.mTagAdapter;
        HashSet<Integer> preCheckedList = tagAdapter.getPreCheckedList();
        for (final int i = 0; i < tagAdapter.getCount(); i++) {
            View view = tagAdapter.getView(this, i, tagAdapter.mo6350getItem(i));
            final TagView tagView = new TagView(getContext());
            view.setDuplicateParentStateEnabled(true);
            if (view.getLayoutParams() != null) {
                tagView.setLayoutParams(view.getLayoutParams());
            } else {
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
                marginLayoutParams.setMargins(dip2px(getContext(), 5.0f), dip2px(getContext(), 5.0f), dip2px(getContext(), 5.0f), dip2px(getContext(), 5.0f));
                tagView.setLayoutParams(marginLayoutParams);
            }
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            tagView.addView(view);
            addView(tagView);
            if (preCheckedList.contains(Integer.valueOf(i))) {
                setChildChecked(i, tagView);
            }
            if (this.mTagAdapter.setSelected(i, tagAdapter.mo6350getItem(i))) {
                setChildChecked(i, tagView);
            }
            view.setClickable(false);
            tagView.setOnClickListener(new View.OnClickListener() { // from class: com.zhy.view.flowlayout.TagFlowLayout.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    TagFlowLayout.this.doSelect(tagView, i);
                    if (TagFlowLayout.this.mOnTagClickListener != null) {
                        TagFlowLayout.this.mOnTagClickListener.onTagClick(tagView, i, TagFlowLayout.this);
                    }
                }
            });
        }
        this.mSelectedView.addAll(preCheckedList);
    }

    public void setMaxSelectCount(int i) {
        if (this.mSelectedView.size() > i) {
            Log.w("TagFlowLayout", "you has already select more than " + i + " views , so it will be clear .");
            this.mSelectedView.clear();
        }
        this.mSelectedMax = i;
    }

    public Set<Integer> getSelectedList() {
        return new HashSet(this.mSelectedView);
    }

    private void setChildChecked(int i, TagView tagView) {
        tagView.setChecked(true);
        this.mTagAdapter.onSelected(i, tagView.getTagView());
    }

    private void setChildUnChecked(int i, TagView tagView) {
        tagView.setChecked(false);
        this.mTagAdapter.unSelected(i, tagView.getTagView());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSelect(TagView tagView, int i) {
        if (!tagView.isChecked()) {
            if (this.mSelectedMax == 1 && this.mSelectedView.size() == 1) {
                Integer next = this.mSelectedView.iterator().next();
                setChildUnChecked(next.intValue(), (TagView) getChildAt(next.intValue()));
                setChildChecked(i, tagView);
                this.mSelectedView.remove(next);
                this.mSelectedView.add(Integer.valueOf(i));
            } else if (this.mSelectedMax > 0 && this.mSelectedView.size() >= this.mSelectedMax) {
                return;
            } else {
                setChildChecked(i, tagView);
                this.mSelectedView.add(Integer.valueOf(i));
            }
        } else {
            setChildUnChecked(i, tagView);
            this.mSelectedView.remove(Integer.valueOf(i));
        }
        OnSelectListener onSelectListener = this.mOnSelectListener;
        if (onSelectListener != null) {
            onSelectListener.onSelected(new HashSet(this.mSelectedView));
        }
    }

    public TagAdapter getAdapter() {
        return this.mTagAdapter;
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("key_default", super.onSaveInstanceState());
        String str = "";
        if (this.mSelectedView.size() > 0) {
            for (Integer num : this.mSelectedView) {
                str = str + num.intValue() + "|";
            }
            str = str.substring(0, str.length() - 1);
        }
        bundle.putString("key_choose_pos", str);
        return bundle;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            String string = bundle.getString("key_choose_pos");
            if (!TextUtils.isEmpty(string)) {
                for (String str : string.split("\\|")) {
                    int parseInt = Integer.parseInt(str);
                    this.mSelectedView.add(Integer.valueOf(parseInt));
                    TagView tagView = (TagView) getChildAt(parseInt);
                    if (tagView != null) {
                        setChildChecked(parseInt, tagView);
                    }
                }
            }
            super.onRestoreInstanceState(bundle.getParcelable("key_default"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override // com.zhy.view.flowlayout.TagAdapter.OnDataChangedListener
    public void onChanged() {
        this.mSelectedView.clear();
        changeAdapter();
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
