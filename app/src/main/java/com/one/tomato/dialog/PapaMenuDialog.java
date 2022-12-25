package com.one.tomato.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.VideoDownload;
import com.one.tomato.mvp.p080ui.download.view.VideoDownloadActivity;
import com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.ToastUtil;

/* loaded from: classes3.dex */
public class PapaMenuDialog extends BottomSheetDialog {
    private PostList intentPostList;
    private ImageView iv_collect;
    private ImageView iv_download;
    private MenuListener listener;
    private LinearLayout ll_collect;
    private LinearLayout ll_copy;
    private LinearLayout ll_dcim;
    private LinearLayout ll_download;
    private LinearLayout ll_report;
    protected Context mContext;
    private TextView tv_cancel;
    private TextView tv_collect;
    private TextView tv_download;
    private VideoDownload videoDownload;

    /* loaded from: classes3.dex */
    public interface MenuListener {
        void collect(PostList postList);

        void copy(PostList postList);

        void download(PostList postList);

        void report(PostList postList);

        void saveDCIM(PostList postList);
    }

    public PapaMenuDialog(@NonNull final Context context) {
        super(context);
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_papa_menu, (ViewGroup) null);
        setContentView(inflate);
        this.ll_copy = (LinearLayout) inflate.findViewById(R.id.ll_copy);
        this.ll_report = (LinearLayout) inflate.findViewById(R.id.ll_report);
        this.ll_download = (LinearLayout) inflate.findViewById(R.id.ll_download);
        this.iv_download = (ImageView) inflate.findViewById(R.id.iv_download);
        this.tv_download = (TextView) inflate.findViewById(R.id.tv_download);
        this.ll_dcim = (LinearLayout) inflate.findViewById(R.id.ll_dcim);
        this.ll_collect = (LinearLayout) inflate.findViewById(R.id.ll_collect);
        this.iv_collect = (ImageView) inflate.findViewById(R.id.iv_collect);
        this.tv_collect = (TextView) inflate.findViewById(R.id.tv_collect);
        this.tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        this.ll_copy.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PapaMenuDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PapaMenuDialog.this.dismiss();
                if (PapaMenuDialog.this.listener != null) {
                    PapaMenuDialog.this.listener.copy(PapaMenuDialog.this.intentPostList);
                }
            }
        });
        this.ll_report.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PapaMenuDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PapaMenuDialog.this.dismiss();
                if (PapaMenuDialog.this.listener != null) {
                    PapaMenuDialog.this.listener.report(PapaMenuDialog.this.intentPostList);
                }
            }
        });
        this.ll_download.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PapaMenuDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PapaMenuDialog.this.dismiss();
                if (PapaMenuDialog.this.intentPostList.getSecVideoUrl().endsWith(".m3u8")) {
                    if (!M3U8DownloadManager.getInstance().isVideoDownloadExist(PapaMenuDialog.this.videoDownload.getUrl())) {
                        if (PapaMenuDialog.this.listener == null) {
                            return;
                        }
                        PapaMenuDialog.this.listener.download(PapaMenuDialog.this.intentPostList);
                        return;
                    }
                    LogUtil.m3783i("Download", "点击下载的时候，数据库已经存在该视频文件");
                    PapaMenuDialog.this.dismiss();
                    VideoDownloadActivity.Companion.startActivity(PapaMenuDialog.this.mContext);
                    return;
                }
                ToastUtil.showCenterToast((int) R.string.post_download_not_support);
            }
        });
        this.ll_dcim.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PapaMenuDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PapaMenuDialog.this.dismiss();
                if (PapaMenuDialog.this.listener != null) {
                    PapaMenuDialog.this.listener.saveDCIM(PapaMenuDialog.this.intentPostList);
                }
            }
        });
        this.ll_collect.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PapaMenuDialog.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PapaMenuDialog.this.dismiss();
                if (PapaMenuDialog.this.listener != null) {
                    PapaMenuDialog.this.listener.collect(PapaMenuDialog.this.intentPostList);
                }
            }
        });
        this.tv_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PapaMenuDialog.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PapaMenuDialog.this.dismiss();
            }
        });
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.one.tomato.dialog.PapaMenuDialog.7
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                if (PapaMenuDialog.this.intentPostList.getIsFavor() == 1) {
                    PapaMenuDialog.this.iv_collect.setSelected(true);
                    PapaMenuDialog.this.tv_collect.setText(R.string.post_collect_s);
                    PapaMenuDialog.this.tv_collect.setTextColor(PapaMenuDialog.this.mContext.getResources().getColor(R.color.colorAccent));
                } else {
                    PapaMenuDialog.this.iv_collect.setSelected(false);
                    PapaMenuDialog.this.tv_collect.setText(R.string.post_collect);
                    PapaMenuDialog.this.tv_collect.setTextColor(PapaMenuDialog.this.mContext.getResources().getColor(R.color.text_middle));
                }
                if (M3U8DownloadManager.getInstance().isVideoDownloadExist(PapaMenuDialog.this.videoDownload.getUrl())) {
                    PapaMenuDialog.this.iv_download.setImageResource(R.drawable.post_video_down_s);
                    PapaMenuDialog.this.tv_download.setText(R.string.common_download_queue);
                    PapaMenuDialog.this.tv_download.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    return;
                }
                PapaMenuDialog.this.iv_download.setImageResource(R.drawable.post_video_down_n);
                PapaMenuDialog.this.tv_download.setText(R.string.common_download_offline);
                PapaMenuDialog.this.tv_download.setTextColor(context.getResources().getColor(R.color.text_middle));
            }
        });
    }

    public void setMenuListener(MenuListener menuListener) {
        this.listener = menuListener;
    }

    public void setPostList(PostList postList) {
        this.intentPostList = postList;
        if (this.videoDownload == null || !postList.getSecVideoUrl().equals(this.videoDownload.getUrl())) {
            this.videoDownload = new VideoDownload(postList.getSecVideoUrl(), postList.getSecVideoCover(), postList.getTitle());
        }
    }
}
