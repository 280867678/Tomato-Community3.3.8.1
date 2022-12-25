package com.one.tomato.p085ui.tag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.PostTagAdapter;
import com.one.tomato.base.BaseFragment;
import com.one.tomato.entity.TagList;
import com.one.tomato.entity.event.TagSelectEvent;
import com.one.tomato.entity.p079db.Tag;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_tag_list)
/* renamed from: com.one.tomato.ui.tag.TagListFragment */
/* loaded from: classes3.dex */
public class TagListFragment extends BaseFragment {
    private TagList.TagCategoryListBean categoryListBean;
    @ViewInject(R.id.flowlayout_tag)
    private TagFlowLayout flowlayout_tag;
    private OnFragmentTagClickListener onTagClickListener;
    private ArrayList<Tag> selectTag = new ArrayList<>();
    private PostTagAdapter tagAdapter;

    /* renamed from: com.one.tomato.ui.tag.TagListFragment$OnFragmentTagClickListener */
    /* loaded from: classes3.dex */
    public interface OnFragmentTagClickListener {
        void onFragmentTagClick(Tag tag);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentTagClickListener) {
            this.onTagClickListener = (OnFragmentTagClickListener) context;
        }
    }

    @Override // com.one.tomato.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.categoryListBean = (TagList.TagCategoryListBean) arguments.getParcelable("intent_category");
            this.selectTag = arguments.getParcelableArrayList("intent_select_post");
            arguments.getInt("intent_num", 6);
        }
    }

    @Override // com.one.tomato.base.BaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        HashSet hashSet = new HashSet();
        ArrayList<Tag> arrayList = this.selectTag;
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator<Tag> it2 = this.selectTag.iterator();
            while (it2.hasNext()) {
                Tag next = it2.next();
                if (this.categoryListBean.getTagList().contains(next)) {
                    hashSet.add(Integer.valueOf(this.categoryListBean.getTagList().indexOf(next)));
                }
            }
        }
        this.tagAdapter = new PostTagAdapter(this.mContext, this.flowlayout_tag, this.categoryListBean.getTagList());
        this.tagAdapter.setTextSize(14);
        if (hashSet.size() > 0) {
            this.tagAdapter.setSetSelectPositions(hashSet);
        }
        this.flowlayout_tag.setAdapter(this.tagAdapter);
        this.flowlayout_tag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() { // from class: com.one.tomato.ui.tag.TagListFragment.1
            @Override // com.zhy.view.flowlayout.TagFlowLayout.OnTagClickListener
            public boolean onTagClick(View view, int i, FlowLayout flowLayout) {
                Tag tag = TagListFragment.this.categoryListBean.getTagList().get(i);
                if (TagListFragment.this.onTagClickListener != null) {
                    TagListFragment.this.onTagClickListener.onFragmentTagClick(tag);
                }
                TagSelectEvent tagSelectEvent = new TagSelectEvent();
                tagSelectEvent.tag = tag;
                tagSelectEvent.type = 1;
                EventBus.getDefault().post(tagSelectEvent);
                return false;
            }
        });
        return onCreateView;
    }

    public void onEventMainThread(TagSelectEvent tagSelectEvent) {
        if (this.categoryListBean == null || this.tagAdapter == null) {
            return;
        }
        Tag tag = tagSelectEvent.tag;
        int i = tagSelectEvent.type;
        for (int i2 = 0; i2 < this.categoryListBean.getTagList().size(); i2++) {
            if (this.categoryListBean.getTagList().contains(tag)) {
                int indexOf = this.categoryListBean.getTagList().indexOf(tag);
                if (i == 1) {
                    this.tagAdapter.addOrRemoveSelectPosition(true, indexOf);
                } else if (i == 2) {
                    this.tagAdapter.addOrRemoveSelectPosition(false, indexOf);
                }
            }
        }
    }
}
