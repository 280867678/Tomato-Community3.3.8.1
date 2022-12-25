package sj.keyboard.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.keyboard.view.R$dimen;
import com.keyboard.view.R$id;
import com.keyboard.view.R$layout;
import java.util.ArrayList;
import sj.keyboard.data.EmoticonPageEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.interfaces.EmoticonDisplayListener;

/* loaded from: classes4.dex */
public class EmoticonsAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected final int mDefalutItemHeight;
    protected EmoticonPageEntity mEmoticonPageEntity;
    protected LayoutInflater mInflater;
    protected int mItemHeight;
    protected int mItemHeightMax;
    protected int mItemHeightMin;
    protected EmoticonDisplayListener mOnDisPlayListener;
    protected EmoticonClickListener mOnEmoticonClickListener;
    protected final int DEF_HEIGHTMAXTATIO = 2;
    protected ArrayList<T> mData = new ArrayList<>();
    protected double mItemHeightMaxRatio = 2.0d;
    protected int mDelbtnPosition = -1;

    /* loaded from: classes4.dex */
    public static class ViewHolder {
        public ImageView iv_emoticon;
        public LinearLayout ly_root;
        public View rootView;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public EmoticonsAdapter(Context context, EmoticonPageEntity emoticonPageEntity, EmoticonClickListener emoticonClickListener) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mEmoticonPageEntity = emoticonPageEntity;
        this.mOnEmoticonClickListener = emoticonClickListener;
        int dimension = (int) context.getResources().getDimension(R$dimen.item_emoticon_size_default);
        this.mItemHeight = dimension;
        this.mDefalutItemHeight = dimension;
        this.mData.addAll(emoticonPageEntity.getEmoticonList());
        checkDelBtn(emoticonPageEntity);
    }

    private void checkDelBtn(EmoticonPageEntity emoticonPageEntity) {
        EmoticonPageEntity.DelBtnStatus delBtnStatus = emoticonPageEntity.getDelBtnStatus();
        if (EmoticonPageEntity.DelBtnStatus.GONE.equals(delBtnStatus)) {
            return;
        }
        if (EmoticonPageEntity.DelBtnStatus.FOLLOW.equals(delBtnStatus)) {
            this.mDelbtnPosition = getCount();
            this.mData.add(null);
        } else if (!EmoticonPageEntity.DelBtnStatus.LAST.equals(delBtnStatus)) {
        } else {
            int line = emoticonPageEntity.getLine() * emoticonPageEntity.getRow();
            while (getCount() < line) {
                this.mData.add(null);
            }
            this.mDelbtnPosition = getCount() - 1;
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        ArrayList<T> arrayList = this.mData;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        ArrayList<T> arrayList = this.mData;
        if (arrayList == null) {
            return null;
        }
        return arrayList.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = this.mInflater.inflate(R$layout.item_emoticon, (ViewGroup) null);
            viewHolder.rootView = view2;
            viewHolder.ly_root = (LinearLayout) view2.findViewById(R$id.ly_root);
            viewHolder.iv_emoticon = (ImageView) view2.findViewById(R$id.iv_emoticon);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        bindView(i, viewGroup, viewHolder);
        updateUI(viewHolder, viewGroup);
        return view2;
    }

    protected void bindView(int i, ViewGroup viewGroup, ViewHolder viewHolder) {
        EmoticonDisplayListener emoticonDisplayListener = this.mOnDisPlayListener;
        if (emoticonDisplayListener != null) {
            emoticonDisplayListener.onBindView(i, viewGroup, viewHolder, this.mData.get(i), i == this.mDelbtnPosition);
        }
    }

    protected boolean isDelBtn(int i) {
        return i == this.mDelbtnPosition;
    }

    protected void updateUI(ViewHolder viewHolder, ViewGroup viewGroup) {
        int i = this.mDefalutItemHeight;
        int i2 = this.mItemHeight;
        if (i != i2) {
            viewHolder.iv_emoticon.setLayoutParams(new LinearLayout.LayoutParams(-1, i2));
        }
        int i3 = this.mItemHeightMax;
        if (i3 == 0) {
            i3 = (int) (this.mItemHeight * this.mItemHeightMaxRatio);
        }
        this.mItemHeightMax = i3;
        int i4 = this.mItemHeightMin;
        if (i4 == 0) {
            i4 = this.mItemHeight;
        }
        this.mItemHeightMin = i4;
        viewHolder.ly_root.setLayoutParams(new LinearLayout.LayoutParams(-1, Math.max(Math.min(((View) viewGroup.getParent()).getMeasuredHeight() / this.mEmoticonPageEntity.getLine(), this.mItemHeightMax), this.mItemHeightMin)));
    }

    public void setOnDisPlayListener(EmoticonDisplayListener emoticonDisplayListener) {
        this.mOnDisPlayListener = emoticonDisplayListener;
    }

    public void setItemHeightMaxRatio(double d) {
        this.mItemHeightMaxRatio = d;
    }

    public void setItemHeightMax(int i) {
        this.mItemHeightMax = i;
    }

    public void setItemHeightMin(int i) {
        this.mItemHeightMin = i;
    }

    public void setItemHeight(int i) {
        this.mItemHeight = i;
    }

    public void setDelbtnPosition(int i) {
        this.mDelbtnPosition = i;
    }
}
