package com.one.tomato.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import java.util.List;

/* loaded from: classes3.dex */
public class PostSearchTagAdapter extends TagAdapter {
    private FlowLayout flowLayout;
    private LayoutInflater inflater;
    private List<String> list;

    public PostSearchTagAdapter(Context context, FlowLayout flowLayout, List<String> list) {
        super(list);
        this.flowLayout = flowLayout;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override // com.zhy.view.flowlayout.TagAdapter
    /* renamed from: getItem  reason: collision with other method in class */
    public String mo6350getItem(int i) {
        return this.list.get(i);
    }

    @Override // com.zhy.view.flowlayout.TagAdapter
    public View getView(FlowLayout flowLayout, int i, Object obj) {
        TextView textView = (TextView) this.inflater.inflate(R.layout.item_post_search_tag_flow, (ViewGroup) this.flowLayout, false);
        textView.setText(this.list.get(i));
        return textView;
    }
}
