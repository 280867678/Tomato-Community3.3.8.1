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
public class PostDetailMenuDialog extends BottomSheetDialog {
    private PostList intentPostList;
    private ImageView iv_collect;
    private ImageView iv_download;
    private MenuListener listener;
    private LinearLayout ll_collect;
    private LinearLayout ll_dcim;
    private LinearLayout ll_download;
    private LinearLayout ll_share;
    protected Context mContext;
    private TextView tv_cancel;
    private TextView tv_collect;
    private TextView tv_download;
    private VideoDownload videoDownload;

    /* loaded from: classes3.dex */
    public interface MenuListener {
        void collect(PostList postList);

        void download(PostList postList);

        void saveDCIM(PostList postList);

        void share(PostList postList);
    }

    public PostDetailMenuDialog(@NonNull final Context context) {
        super(context);
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_post_detail_menu, (ViewGroup) null);
        setContentView(inflate);
        this.ll_collect = (LinearLayout) inflate.findViewById(R.id.ll_collect);
        this.iv_collect = (ImageView) inflate.findViewById(R.id.iv_collect);
        this.tv_collect = (TextView) inflate.findViewById(R.id.tv_collect);
        this.ll_download = (LinearLayout) inflate.findViewById(R.id.ll_download);
        this.iv_download = (ImageView) inflate.findViewById(R.id.iv_download);
        this.tv_download = (TextView) inflate.findViewById(R.id.tv_download);
        this.ll_dcim = (LinearLayout) inflate.findViewById(R.id.ll_dcim);
        this.ll_share = (LinearLayout) inflate.findViewById(R.id.ll_share);
        this.tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        this.ll_collect.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PostDetailMenuDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PostDetailMenuDialog.this.listener != null) {
                    PostDetailMenuDialog.this.listener.collect(PostDetailMenuDialog.this.intentPostList);
                }
                PostDetailMenuDialog.this.dismiss();
            }
        });
        this.ll_download.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PostDetailMenuDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PostDetailMenuDialog.this.intentPostList.getSecVideoUrl().endsWith(".m3u8")) {
                    if (!M3U8DownloadManager.getInstance().isVideoDownloadExist(PostDetailMenuDialog.this.videoDownload.getUrl())) {
                        if (PostDetailMenuDialog.this.listener != null) {
                            if (PostDetailMenuDialog.this.intentPostList == null) {
                                return;
                            }
                            PostDetailMenuDialog.this.listener.download(PostDetailMenuDialog.this.intentPostList);
                        }
                        PostDetailMenuDialog.this.dismiss();
                        return;
                    }
                    LogUtil.m3783i("Download", "点击下载的时候，数据库已经存在该视频文件");
                    PostDetailMenuDialog.this.dismiss();
                    VideoDownloadActivity.Companion.startActivity(PostDetailMenuDialog.this.mContext);
                    return;
                }
                ToastUtil.showCenterToast((int) R.string.post_download_not_support);
            }
        });
        this.ll_dcim.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PostDetailMenuDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PostDetailMenuDialog.this.isCanDown()) {
                    return;
                }
                if (PostDetailMenuDialog.this.listener != null && PostDetailMenuDialog.this.intentPostList != null) {
                    PostDetailMenuDialog.this.listener.saveDCIM(PostDetailMenuDialog.this.intentPostList);
                }
                PostDetailMenuDialog.this.dismiss();
            }
        });
        this.ll_share.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PostDetailMenuDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PostDetailMenuDialog.this.listener != null) {
                    PostDetailMenuDialog.this.listener.share(PostDetailMenuDialog.this.intentPostList);
                }
                PostDetailMenuDialog.this.dismiss();
            }
        });
        this.tv_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PostDetailMenuDialog.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PostDetailMenuDialog.this.dismiss();
            }
        });
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.one.tomato.dialog.PostDetailMenuDialog.6
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                if (PostDetailMenuDialog.this.intentPostList.getIsFavor() == 1) {
                    PostDetailMenuDialog.this.iv_collect.setSelected(true);
                    PostDetailMenuDialog.this.tv_collect.setText(R.string.post_collect_s);
                    PostDetailMenuDialog.this.tv_collect.setTextColor(PostDetailMenuDialog.this.mContext.getResources().getColor(R.color.colorAccent));
                } else {
                    PostDetailMenuDialog.this.iv_collect.setSelected(false);
                    PostDetailMenuDialog.this.tv_collect.setText(R.string.post_collect);
                    PostDetailMenuDialog.this.tv_collect.setTextColor(PostDetailMenuDialog.this.mContext.getResources().getColor(R.color.text_middle));
                }
                if (M3U8DownloadManager.getInstance().isVideoDownloadExist(PostDetailMenuDialog.this.videoDownload.getUrl())) {
                    PostDetailMenuDialog.this.iv_download.setImageResource(R.drawable.post_video_down_s);
                    PostDetailMenuDialog.this.tv_download.setText(R.string.common_download_queue);
                    PostDetailMenuDialog.this.tv_download.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    return;
                }
                PostDetailMenuDialog.this.iv_download.setImageResource(R.drawable.post_video_down_n);
                PostDetailMenuDialog.this.tv_download.setText(R.string.common_download_offline);
                PostDetailMenuDialog.this.tv_download.setTextColor(context.getResources().getColor(R.color.text_middle));
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

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCanDown() {
        if (this.intentPostList.getCanDownload() == 0) {
            ToastUtil.showCenterToast("发布者未开放下载！");
            return true;
        }
        return false;
    }
}
