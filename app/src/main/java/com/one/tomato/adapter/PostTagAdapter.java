package com.one.tomato.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.p079db.Tag;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes3.dex */
public class PostTagAdapter extends TagAdapter {
    private FlowLayout flowLayout;
    private LayoutInflater inflater;
    private boolean showDelete;
    private ArrayList<Tag> list = new ArrayList<>();
    private int textSize = 12;
    private Set<Integer> selectPosition = new HashSet();

    public PostTagAdapter(Context context, FlowLayout flowLayout, ArrayList<Tag> arrayList) {
        super(arrayList);
        this.flowLayout = flowLayout;
        if (arrayList != null) {
            this.list.addAll(arrayList);
        }
        this.inflater = LayoutInflater.from(context);
    }

    @Override // com.zhy.view.flowlayout.TagAdapter
    public int getCount() {
        return this.list.size();
    }

    @Override // com.zhy.view.flowlayout.TagAdapter
    /* renamed from: getItem */
    public Tag mo6350getItem(int i) {
        return this.list.get(i);
    }

    @Override // com.zhy.view.flowlayout.TagAdapter
    public View getView(FlowLayout flowLayout, int i, Object obj) {
        View inflate = this.inflater.inflate(R.layout.item_post_tag_flow, (ViewGroup) this.flowLayout, false);
        TextView textView = (TextView) inflate.findViewById(R.id.tv_content);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_clear);
        textView.setTextSize(2, this.textSize);
        if (obj instanceof Tag) {
            textView.setText(((Tag) obj).getTagName());
        }
        if (this.showDelete) {
            imageView.setVisibility(0);
        }
        if (this.selectPosition.contains(Integer.valueOf(i))) {
            textView.setBackgroundResource(R.drawable.common_shape_stroke_corner30_coloraccent);
            textView.setTextColor(inflate.getResources().getColor(R.color.colorAccent));
        } else {
            textView.setBackgroundResource(R.drawable.common_shape_stroke_corner30_divider);
            textView.setTextColor(inflate.getResources().getColor(R.color.text_middle));
        }
        return inflate;
    }

    public void setTextSize(int i) {
        this.textSize = i;
    }

    public void addOrRemoveSelectPosition(boolean z, int i) {
        if (z) {
            if (!this.selectPosition.contains(Integer.valueOf(i))) {
                this.selectPosition.add(Integer.valueOf(i));
            }
        } else if (this.selectPosition.contains(Integer.valueOf(i))) {
            this.selectPosition.remove(Integer.valueOf(i));
        }
        notifyDataChanged();
    }

    public void setSetSelectPositions(Set<Integer> set) {
        this.selectPosition.clear();
        this.selectPosition.addAll(set);
    }
}
