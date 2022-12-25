package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.p135db.SearchKeywordEntity;
import com.tomatolive.library.p136ui.view.widget.tagview.TagContainerLayout;
import com.tomatolive.library.p136ui.view.widget.tagview.TagView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.headview.SearchHistoryHeadView */
/* loaded from: classes3.dex */
public class SearchHistoryHeadView extends LinearLayout {
    private TagContainerLayout tagHistory;
    private TagContainerLayout tagHot;

    public SearchHistoryHeadView(Context context) {
        this(context, null);
    }

    public SearchHistoryHeadView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SearchHistoryHeadView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R$layout.fq_layout_head_view_search_history, this);
        this.tagHot = (TagContainerLayout) findViewById(R$id.tag_hot);
        this.tagHistory = (TagContainerLayout) findViewById(R$id.tag_history);
    }

    public void setOnTagClickListener(TagView.OnTagClickListener onTagClickListener, TagView.OnTagClickListener onTagClickListener2) {
        if (onTagClickListener != null) {
            this.tagHot.setOnTagClickListener(onTagClickListener);
        }
        if (onTagClickListener2 != null) {
            this.tagHistory.setOnTagClickListener(onTagClickListener2);
        }
    }

    public void initHotTagList(List<LabelEntity> list) {
        if (list == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (LabelEntity labelEntity : list) {
            arrayList.add(labelEntity.keyword);
        }
        this.tagHot.setTags(arrayList);
    }

    public void initHistoryTagList(List<SearchKeywordEntity> list) {
        if (list == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (SearchKeywordEntity searchKeywordEntity : list) {
            arrayList.add(searchKeywordEntity.getKeyword());
        }
        this.tagHistory.setTags(arrayList);
    }
}
