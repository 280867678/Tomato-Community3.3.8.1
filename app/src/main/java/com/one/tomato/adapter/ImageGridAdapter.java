package com.one.tomato.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public class ImageGridAdapter extends CommonBaseAdapter<ImageBean> {
    private Context context;
    private boolean isReward;
    private int itemWidth = (int) ((DisplayMetricsUtils.getWidth() - DisplayMetricsUtils.dp2px(20.0f)) / 3.0f);

    public void setReward(boolean z) {
        this.isReward = z;
        notifyDataSetChanged();
    }

    public ImageGridAdapter(Context context, List<ImageBean> list, int i) {
        super(context, list, i);
        this.context = context;
    }

    @Override // com.one.tomato.adapter.CommonBaseAdapter
    public void convert(ViewHolder viewHolder, ImageBean imageBean, int i) {
        imageBean.setThumbWidth(this.itemWidth);
        imageBean.setThumbHeight(this.itemWidth);
        final ImageView imageView = (ImageView) viewHolder.getView(R.id.siv_pic);
        if (imageBean.isLocal()) {
            ImageLoaderUtil.loadNormalLocalImage(this.context, imageView, imageBean.getImage(), ImageLoaderUtil.getDefaultImageOptions(imageView));
        } else {
            View view = viewHolder.getView(R.id.relate_image_layer);
            view.setVisibility(8);
            if (this.isReward) {
                if (getCount() > 1 && i == 0) {
                    ImageLoaderUtil.loadRecyclerThumbImage(this.context, imageView, imageBean);
                    return;
                } else {
                    view.setVisibility(0);
                    ImageLoaderUtil.loadViewPagerOriginImageBlurs(this.context, imageView, null, imageBean, new Function1<Bitmap, Unit>(this) { // from class: com.one.tomato.adapter.ImageGridAdapter.1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke  reason: avoid collision after fix types in other method */
                        public Unit mo6794invoke(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                            return null;
                        }
                    });
                }
            } else {
                ImageLoaderUtil.loadRecyclerThumbImage(this.context, imageView, imageBean);
            }
        }
        TextView textView = (TextView) viewHolder.getView(R.id.tv_image_num);
        TextView textView2 = (TextView) viewHolder.getView(R.id.tv_long_image);
        if (imageBean.getImageType() == 3) {
            textView2.setVisibility(0);
            if (i == 8) {
                textView2.setVisibility(8);
            }
        } else {
            textView2.setVisibility(8);
        }
        if (this.mDatas.size() > 9 && i == 8) {
            textView.setVisibility(0);
            textView.setText(this.mDatas.size() + AppUtil.getString(R.string.circle_post_img_num));
            return;
        }
        textView.setVisibility(8);
    }
}
