package com.one.tomato.mvp.p080ui.download.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.VideoDownload;
import com.one.tomato.mvp.p080ui.download.view.VideoDownloadActivity;
import com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager;
import com.one.tomato.thirdpart.m3u8.download.utils.MUtils;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: VideoDownloadAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.download.adapter.VideoDownloadAdapter */
/* loaded from: classes3.dex */
public final class VideoDownloadAdapter extends BaseRecyclerViewAdapter<VideoDownload> {
    private Context context;
    private int model;
    private ArrayList<VideoDownload> selectList = new ArrayList<>();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoDownloadAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_video_download, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.context = context;
    }

    public final int getModel() {
        return this.model;
    }

    public final void setModel(int i) {
        this.model = i;
    }

    public final ArrayList<VideoDownload> getSelectList() {
        return this.selectList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, VideoDownload videoDownload) {
        super.convert(baseViewHolder, (BaseViewHolder) videoDownload);
        Integer num = null;
        Float f = null;
        String str = null;
        CheckBox checkBox = baseViewHolder != null ? (CheckBox) baseViewHolder.getView(R.id.checkbox) : null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.iv_cover) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_title) : null;
        ProgressBar progressBar = baseViewHolder != null ? (ProgressBar) baseViewHolder.getView(R.id.progressBar) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_status) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_speed) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_size) : null;
        TextView textView5 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_progress) : null;
        if (checkBox != null) {
            checkBox.setVisibility(8);
        }
        int i = this.model;
        if (i == 1) {
            if (checkBox != null) {
                checkBox.setVisibility(0);
            }
            if (checkBox != null) {
                if (videoDownload == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                checkBox.setChecked(videoDownload.isChecked());
            }
        } else if (i == 2) {
            if (checkBox != null) {
                checkBox.setVisibility(0);
            }
            if (checkBox != null) {
                checkBox.setChecked(true);
            }
        } else if (checkBox != null) {
            checkBox.setVisibility(8);
        }
        ImageLoaderUtil.loadRecyclerThumbImage(this.context, imageView, new ImageBean(videoDownload != null ? videoDownload.getCover() : null));
        if (textView != null) {
            textView.setText(videoDownload != null ? videoDownload.getTitle() : null);
        }
        Integer valueOf = videoDownload != null ? Integer.valueOf(videoDownload.getState()) : null;
        if (valueOf != null && valueOf.intValue() == 2) {
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
            if (textView4 != null) {
                textView4.setVisibility(8);
            }
            if (progressBar != null) {
                progressBar.setVisibility(0);
            }
            if (textView3 != null) {
                textView3.setVisibility(0);
            }
            if (textView5 != null) {
                textView5.setVisibility(0);
            }
            if (progressBar != null) {
                progressBar.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.layer_progress_video_download_ing));
            }
            if (progressBar != null) {
                if (videoDownload == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                progressBar.setProgress((int) (videoDownload.getProgress() * 100));
            }
            if (textView3 != null) {
                textView3.setText(videoDownload != null ? videoDownload.getFormatSpeed() : null);
            }
            if (textView5 == null) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] objArr = new Object[1];
            if (videoDownload != null) {
                f = Float.valueOf(videoDownload.getProgress());
            }
            objArr[0] = Float.valueOf(f.floatValue() * 100);
            String format = String.format("%.1f ", Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
            sb.append(format);
            sb.append("%");
            textView5.setText(sb.toString());
        } else if (valueOf != null && valueOf.intValue() == 3) {
            if (progressBar != null) {
                progressBar.setVisibility(8);
            }
            if (textView3 != null) {
                textView3.setVisibility(8);
            }
            if (textView5 != null) {
                textView5.setVisibility(8);
            }
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
            if (textView4 != null) {
                textView4.setVisibility(0);
            }
            if (textView2 != null) {
                textView2.setText(this.context.getResources().getString(R.string.video_down_complete));
            }
            if (textView4 == null) {
                return;
            }
            if (videoDownload != null) {
                str = videoDownload.getSize();
            }
            textView4.setText(str);
        } else {
            if (textView3 != null) {
                textView3.setVisibility(8);
            }
            if (textView4 != null) {
                textView4.setVisibility(8);
            }
            if (progressBar != null) {
                progressBar.setVisibility(0);
            }
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
            if (textView5 != null) {
                textView5.setVisibility(0);
            }
            if (progressBar != null) {
                progressBar.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.layer_progress_video_download_pause));
            }
            if (progressBar != null) {
                if (videoDownload == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                progressBar.setProgress((int) (videoDownload.getProgress() * 100));
            }
            if (textView5 != null) {
                StringBuilder sb2 = new StringBuilder();
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                Object[] objArr2 = new Object[1];
                if (videoDownload == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                objArr2[0] = Float.valueOf(videoDownload.getProgress() * 100);
                String format2 = String.format("%.1f ", Arrays.copyOf(objArr2, objArr2.length));
                Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
                sb2.append(format2);
                sb2.append("%");
                textView5.setText(sb2.toString());
            }
            if (videoDownload != null) {
                num = Integer.valueOf(videoDownload.getState());
            }
            int intValue = num.intValue();
            if (intValue == 4) {
                if (textView2 == null) {
                    return;
                }
                textView2.setText(this.context.getResources().getString(R.string.video_down_error));
            } else if (intValue != 5) {
                if (textView2 == null) {
                    return;
                }
                textView2.setText(this.context.getResources().getString(R.string.video_down_wait));
            } else if (textView2 == null) {
            } else {
                textView2.setText(this.context.getResources().getString(R.string.video_down_pause));
            }
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        VideoDownload item = getItem(i);
        int i2 = 0;
        String str = null;
        if (this.model != 0) {
            if (item != null) {
                if (!item.isChecked() && !this.selectList.contains(item)) {
                    this.selectList.add(item);
                    item.setChecked(true);
                    refreshNotifyItemChanged(i);
                    this.model = 1;
                    Context context = this.context;
                    if (context == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.download.view.VideoDownloadActivity");
                    }
                    ((VideoDownloadActivity) context).switchSelectTV(false);
                    if (this.selectList.size() != getData().size()) {
                        return;
                    }
                    this.model = 2;
                    Context context2 = this.context;
                    if (context2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.download.view.VideoDownloadActivity");
                    }
                    ((VideoDownloadActivity) context2).switchSelectTV(true);
                    return;
                }
                this.selectList.remove(item);
                item.setChecked(false);
                refreshNotifyItemChanged(i);
                this.model = 1;
                Context context3 = this.context;
                if (context3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.download.view.VideoDownloadActivity");
                }
                ((VideoDownloadActivity) context3).switchSelectTV(false);
                return;
            }
            Intrinsics.throwNpe();
            throw null;
        }
        Integer valueOf = item != null ? Integer.valueOf(item.getState()) : null;
        if (valueOf != null && valueOf.intValue() == 2) {
            M3U8DownloadManager m3U8DownloadManager = M3U8DownloadManager.getInstance();
            if (item != null) {
                str = item.getUrl();
            }
            m3U8DownloadManager.pause(str, false);
        } else if (valueOf == null || valueOf.intValue() != 3) {
            M3U8DownloadManager m3U8DownloadManager2 = M3U8DownloadManager.getInstance();
            String url = item != null ? item.getUrl() : null;
            String title = item != null ? item.getTitle() : null;
            if (item != null) {
                str = item.getPostId();
            }
            String valueOf2 = String.valueOf(str);
            if (item != null) {
                i2 = item.getVideoView();
            }
            m3U8DownloadManager2.download(url, title, valueOf2, i2);
        } else {
            if (MUtils.checkM3U8IsExist(item != null ? item.getUrl() : null, true)) {
                Context context4 = this.context;
                if (context4 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.download.view.VideoDownloadActivity");
                }
                ((VideoDownloadActivity) context4).startPlay(item);
                return;
            }
            M3U8DownloadManager m3U8DownloadManager3 = M3U8DownloadManager.getInstance();
            if (item != null) {
                str = item.getUrl();
            }
            m3U8DownloadManager3.download(str, item.getTitle(), item.getPostId().toString(), item.getVideoView());
        }
    }

    public final void clearSelect() {
        this.selectList.clear();
        for (VideoDownload item : getData()) {
            Intrinsics.checkExpressionValueIsNotNull(item, "item");
            item.setChecked(false);
        }
        notifyDataSetChanged();
    }

    public final void allSelect() {
        for (VideoDownload item : getData()) {
            if (!this.selectList.contains(item)) {
                Intrinsics.checkExpressionValueIsNotNull(item, "item");
                item.setChecked(true);
                this.selectList.add(item);
            }
        }
        notifyDataSetChanged();
    }
}
