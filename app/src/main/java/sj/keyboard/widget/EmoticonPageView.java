package sj.keyboard.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.RelativeLayout;
import com.keyboard.view.R$id;
import com.keyboard.view.R$layout;

/* loaded from: classes4.dex */
public class EmoticonPageView extends RelativeLayout {
    private GridView mGvEmotion;

    public GridView getEmoticonsGridView() {
        return this.mGvEmotion;
    }

    public EmoticonPageView(Context context) {
        this(context, null);
    }

    public EmoticonPageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mGvEmotion = (GridView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R$layout.item_emoticonpage, this).findViewById(R$id.gv_emotion);
        if (Build.VERSION.SDK_INT > 11) {
            this.mGvEmotion.setMotionEventSplittingEnabled(false);
        }
        this.mGvEmotion.setStretchMode(2);
        this.mGvEmotion.setCacheColorHint(0);
        this.mGvEmotion.setSelector(new ColorDrawable(0));
        this.mGvEmotion.setVerticalScrollBarEnabled(false);
    }

    public void setNumColumns(int i) {
        this.mGvEmotion.setNumColumns(i);
    }
}
