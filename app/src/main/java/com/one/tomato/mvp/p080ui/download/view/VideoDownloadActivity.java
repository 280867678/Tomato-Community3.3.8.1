package com.one.tomato.mvp.p080ui.download.view;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.VideoDownload;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity;
import com.one.tomato.mvp.p080ui.download.adapter.VideoDownloadAdapter;
import com.one.tomato.mvp.p080ui.download.impl.IDownloadContact$IDownloadView;
import com.one.tomato.mvp.p080ui.download.presenter.DownloadPresenter;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager;
import com.one.tomato.thirdpart.m3u8.download.entity.M3U8Task;
import com.one.tomato.thirdpart.m3u8.download.listener.IM3U8DownloadListener;
import com.one.tomato.thirdpart.video.controller.VideoDownloadController;
import com.one.tomato.thirdpart.video.player.VideoDownloadView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.ViewUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VideoDownloadActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.download.view.VideoDownloadActivity */
/* loaded from: classes3.dex */
public final class VideoDownloadActivity extends MvpBaseRecyclerViewActivity<IDownloadContact$IDownloadView, DownloadPresenter, VideoDownloadAdapter, VideoDownload> implements IDownloadContact$IDownloadView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private IM3U8DownloadListener iM3U8DownloadListener = new IM3U8DownloadListener() { // from class: com.one.tomato.mvp.ui.download.view.VideoDownloadActivity$iM3U8DownloadListener$1
        @Override // com.one.tomato.thirdpart.m3u8.download.listener.IM3U8DownloadListener
        public void onDownloadPending(M3U8Task m3U8Task) {
            LogUtil.m3783i("Download", "onDownloadPending");
            VideoDownloadActivity.this.notifyDataChanged(m3U8Task);
        }

        @Override // com.one.tomato.thirdpart.m3u8.download.listener.IM3U8DownloadListener
        public void onDownloadPrepare(M3U8Task m3U8Task) {
            LogUtil.m3783i("Download", "onDownloadPrepare");
            VideoDownloadActivity.this.notifyDataChanged(m3U8Task);
        }

        @Override // com.one.tomato.thirdpart.m3u8.download.listener.IM3U8DownloadListener
        public void onDownloadProgress(M3U8Task m3U8Task) {
            LogUtil.m3783i("Download", "onDownloadProgress");
            VideoDownloadActivity.this.notifyDataChanged(m3U8Task);
        }

        @Override // com.one.tomato.thirdpart.m3u8.download.listener.IM3U8DownloadListener
        public void onDownloadSuccess(M3U8Task m3U8Task) {
            LogUtil.m3783i("Download", "onDownloadSuccess");
            VideoDownloadActivity.this.notifyDataChanged(m3U8Task);
            DownloadPresenter access$getMPresenter$p = VideoDownloadActivity.access$getMPresenter$p(VideoDownloadActivity.this);
            if (access$getMPresenter$p != null) {
                access$getMPresenter$p.findCachedSize();
            }
        }

        @Override // com.one.tomato.thirdpart.m3u8.download.listener.IM3U8DownloadListener
        public void onDownloadPause(M3U8Task m3U8Task) {
            LogUtil.m3783i("Download", "onDownloadPause");
            VideoDownloadActivity.this.notifyDataChanged(m3U8Task);
        }

        @Override // com.one.tomato.thirdpart.m3u8.download.listener.IM3U8DownloadListener
        public void onDownloadError(M3U8Task m3U8Task, Throwable th) {
            LogUtil.m3783i("Download", "onDownloadError");
            VideoDownloadActivity.this.notifyDataChanged(m3U8Task);
        }
    };
    private VideoDownloadController videoController;

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_video_download;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void loadMore() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void refresh() {
    }

    public static final /* synthetic */ DownloadPresenter access$getMPresenter$p(VideoDownloadActivity videoDownloadActivity) {
        return (DownloadPresenter) videoDownloadActivity.getMPresenter();
    }

    /* compiled from: VideoDownloadActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.download.view.VideoDownloadActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, VideoDownloadActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public DownloadPresenter mo6439createPresenter() {
        return new DownloadPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        super.initView();
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.common_download_offline);
        }
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setText(R.string.common_cancel);
        }
        TextView rightTV2 = getRightTV();
        if (rightTV2 != null) {
            rightTV2.setTextColor(getResources().getColor(R.color.tip_color));
        }
        ImageView rightIV = getRightIV();
        if (rightIV != null) {
            rightIV.setImageResource(R.drawable.icon_clear_history);
        }
        TextView rightTV3 = getRightTV();
        if (rightTV3 != null) {
            rightTV3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.download.view.VideoDownloadActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VideoDownloadActivity.this.clickRightTv();
                }
            });
        }
        ImageView rightIV2 = getRightIV();
        if (rightIV2 != null) {
            rightIV2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.download.view.VideoDownloadActivity$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    VideoDownloadActivity.this.clickRightIv();
                }
            });
        }
        ((TextView) _$_findCachedViewById(R$id.tv_is_select)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.download.view.VideoDownloadActivity$initView$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoDownloadActivity.this.clickSelectTv();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_delete)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.download.view.VideoDownloadActivity$initView$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoDownloadActivity.this.clickDeleteTv();
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        VideoDownloadAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setEmptyViewState(0, null);
        }
        DownloadPresenter downloadPresenter = (DownloadPresenter) getMPresenter();
        if (downloadPresenter != null) {
            downloadPresenter.findDataFromDB();
        }
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        VideoDownloadAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setEmptyViewState(2, null);
        }
        switchViewWithNoData();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    /* renamed from: createAdapter */
    public VideoDownloadAdapter mo6446createAdapter() {
        Context mContext = getMContext();
        if (mContext != null) {
            return new VideoDownloadAdapter(mContext, getRecyclerView());
        }
        Intrinsics.throwNpe();
        throw null;
    }

    @Override // com.one.tomato.mvp.p080ui.download.impl.IDownloadContact$IDownloadView
    public void setDataFromDB(ArrayList<VideoDownload> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            VideoDownloadAdapter adapter = getAdapter();
            if (adapter != null) {
                adapter.setEmptyViewState(2, null);
            }
            switchViewWithNoData();
            return;
        }
        VideoDownloadAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.setNewData(arrayList);
        }
        VideoDownloadAdapter adapter3 = getAdapter();
        if (adapter3 != null) {
            adapter3.setEnableLoadMore(false);
        }
        VideoDownloadAdapter adapter4 = getAdapter();
        if (adapter4 != null) {
            adapter4.loadMoreEnd();
        }
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setVisibility(8);
        }
        ImageView rightIV = getRightIV();
        if (rightIV != null) {
            rightIV.setVisibility(0);
        }
        initPlayer();
        M3U8DownloadManager.getInstance().downloadQueue(arrayList);
        M3U8DownloadManager.getInstance().setIM3U8DownloadListener(this.iM3U8DownloadListener);
        DownloadPresenter downloadPresenter = (DownloadPresenter) getMPresenter();
        if (downloadPresenter == null) {
            return;
        }
        downloadPresenter.findCachedSize();
    }

    @Override // com.one.tomato.mvp.p080ui.download.impl.IDownloadContact$IDownloadView
    public void setCachedSize(String[] array) {
        TextView textView;
        Intrinsics.checkParameterIsNotNull(array, "array");
        ConstraintLayout constraintLayout = (ConstraintLayout) _$_findCachedViewById(R$id.cl_action);
        if ((constraintLayout == null || constraintLayout.getVisibility() != 0) && (textView = (TextView) _$_findCachedViewById(R$id.tv_space)) != null) {
            textView.setVisibility(0);
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_space);
        if (textView2 != null) {
            ViewUtil.initTextViewWithSpannableString(textView2, new String[]{AppUtil.getString(R.string.video_down_size_used), array[0], AppUtil.getString(R.string.video_down_size_free), array[1]}, new String[]{String.valueOf(getResources().getColor(R.color.text_light)), String.valueOf(getResources().getColor(R.color.colorAccent)), String.valueOf(getResources().getColor(R.color.text_light)), String.valueOf(getResources().getColor(R.color.colorAccent))}, new String[]{"12", "12", "12", "12"});
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clickRightIv() {
        VideoDownloadAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setModel(1);
        }
        VideoDownloadAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
        switchViewWithSelect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clickRightTv() {
        VideoDownloadAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setModel(0);
        }
        VideoDownloadAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.clearSelect();
        }
        switchViewWithDefault();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clickSelectTv() {
        VideoDownloadAdapter adapter = getAdapter();
        if (adapter != null && adapter.getModel() == 2) {
            VideoDownloadAdapter adapter2 = getAdapter();
            if (adapter2 != null) {
                adapter2.setModel(1);
            }
            VideoDownloadAdapter adapter3 = getAdapter();
            if (adapter3 != null) {
                adapter3.clearSelect();
            }
            switchSelectTV(false);
            return;
        }
        VideoDownloadAdapter adapter4 = getAdapter();
        if (adapter4 != null) {
            adapter4.setModel(2);
        }
        VideoDownloadAdapter adapter5 = getAdapter();
        if (adapter5 != null) {
            adapter5.allSelect();
        }
        switchSelectTV(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clickDeleteTv() {
        List<VideoDownload> data;
        List<VideoDownload> data2;
        ArrayList<VideoDownload> selectList;
        VideoDownloadAdapter adapter = getAdapter();
        ArrayList<VideoDownload> selectList2 = adapter != null ? adapter.getSelectList() : null;
        if (selectList2 != null && selectList2.size() == 0) {
            ToastUtil.showCenterToast((int) R.string.video_delete_un_check);
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (selectList2 != null) {
            arrayList.addAll(selectList2);
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                VideoDownload item = (VideoDownload) it2.next();
                VideoDownloadAdapter adapter2 = getAdapter();
                if (adapter2 != null && (selectList = adapter2.getSelectList()) != null) {
                    selectList.remove(item);
                }
                VideoDownloadAdapter adapter3 = getAdapter();
                Integer valueOf = (adapter3 == null || (data2 = adapter3.getData()) == null) ? null : Integer.valueOf(data2.indexOf(item));
                VideoDownloadAdapter adapter4 = getAdapter();
                if (adapter4 != null) {
                    if (valueOf == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    adapter4.remove(valueOf.intValue());
                }
                Intrinsics.checkExpressionValueIsNotNull(item, "item");
                arrayList2.add(item.getUrl());
            }
            M3U8DownloadManager.getInstance().cancelAndDelete(arrayList2);
            ToastUtil.showCenterToast(AppUtil.getString(R.string.video_delete_success_size, Integer.valueOf(arrayList2.size())));
            VideoDownloadAdapter adapter5 = getAdapter();
            if (adapter5 != null) {
                adapter5.setModel(0);
            }
            VideoDownloadAdapter adapter6 = getAdapter();
            if (adapter6 != null && (data = adapter6.getData()) != null && data.size() == 0) {
                VideoDownloadAdapter adapter7 = getAdapter();
                if (adapter7 != null) {
                    adapter7.setEmptyViewState(2, null);
                }
                switchViewWithNoData();
            } else {
                VideoDownloadAdapter adapter8 = getAdapter();
                if (adapter8 != null) {
                    adapter8.notifyDataSetChanged();
                }
                switchViewWithDefault();
            }
            DownloadPresenter downloadPresenter = (DownloadPresenter) getMPresenter();
            if (downloadPresenter == null) {
                return;
            }
            downloadPresenter.findCachedSize();
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    public final void switchSelectTV(boolean z) {
        if (z) {
            TextView textView = (TextView) _$_findCachedViewById(R$id.tv_is_select);
            if (textView == null) {
                return;
            }
            textView.setText(AppUtil.getString(R.string.common_cancel_select_all));
            return;
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_is_select);
        if (textView2 == null) {
            return;
        }
        textView2.setText(AppUtil.getString(R.string.common_select_all));
    }

    private final void switchViewWithNoData() {
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setVisibility(8);
        }
        ImageView rightIV = getRightIV();
        if (rightIV != null) {
            rightIV.setVisibility(8);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_space);
        if (textView != null) {
            textView.setVisibility(8);
        }
        ConstraintLayout constraintLayout = (ConstraintLayout) _$_findCachedViewById(R$id.cl_action);
        if (constraintLayout != null) {
            constraintLayout.setVisibility(8);
        }
    }

    private final void switchViewWithDefault() {
        ImageView rightIV = getRightIV();
        if (rightIV != null) {
            rightIV.setVisibility(0);
        }
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setVisibility(8);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_space);
        if (textView != null) {
            textView.setVisibility(0);
        }
        ConstraintLayout constraintLayout = (ConstraintLayout) _$_findCachedViewById(R$id.cl_action);
        if (constraintLayout != null) {
            constraintLayout.setVisibility(8);
        }
    }

    private final void switchViewWithSelect() {
        ImageView rightIV = getRightIV();
        if (rightIV != null) {
            rightIV.setVisibility(8);
        }
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setVisibility(0);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_space);
        if (textView != null) {
            textView.setVisibility(8);
        }
        ConstraintLayout constraintLayout = (ConstraintLayout) _$_findCachedViewById(R$id.cl_action);
        if (constraintLayout != null) {
            constraintLayout.setVisibility(0);
        }
    }

    public final void notifyDataChanged(M3U8Task m3U8Task) {
        List<VideoDownload> data;
        Integer num = null;
        VideoDownload singleVideoBeanFormFile = M3U8DownloadManager.getInstance().getSingleVideoBeanFormFile(m3U8Task != null ? m3U8Task.getUrl() : null);
        if (singleVideoBeanFormFile != null) {
            if (m3U8Task != null) {
                singleVideoBeanFormFile.setState(m3U8Task.getState());
                singleVideoBeanFormFile.setSpeed(m3U8Task.getSpeed());
                singleVideoBeanFormFile.setProgress(m3U8Task.getProgress());
                singleVideoBeanFormFile.setSize(m3U8Task.getFormatTotalSize());
                VideoDownloadAdapter adapter = getAdapter();
                if (adapter != null && (data = adapter.getData()) != null) {
                    num = Integer.valueOf(data.indexOf(singleVideoBeanFormFile));
                }
                if (num == null) {
                    return;
                }
                num.intValue();
                VideoDownloadAdapter adapter2 = getAdapter();
                if (adapter2 == null) {
                    return;
                }
                adapter2.setData(num.intValue(), singleVideoBeanFormFile);
                return;
            }
            Intrinsics.throwNpe();
            throw null;
        }
    }

    private final void initPlayer() {
        Context mContext = getMContext();
        if (mContext != null) {
            this.videoController = new VideoDownloadController(mContext);
            VideoDownloadController videoDownloadController = this.videoController;
            if (videoDownloadController != null) {
                videoDownloadController.showTitle();
            }
            ((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)).setVideoController(this.videoController);
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    public final void startPlay(VideoDownload videoDownload) {
        if (isDestroyed() || ((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)) == null) {
            return;
        }
        onVideoRelease();
        VideoDownloadView ijkVideoView = (VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView);
        Intrinsics.checkExpressionValueIsNotNull(ijkVideoView, "ijkVideoView");
        ijkVideoView.setVisibility(0);
        StringBuilder sb = new StringBuilder();
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        sb.append(domainServer.getTtViewVideoView2());
        String str = null;
        sb.append(videoDownload != null ? videoDownload.getUrl() : null);
        String sb2 = sb.toString();
        LogUtil.m3786e("播放的视频Url：" + sb2);
        ((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)).setUrl(sb2);
        ((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)).setOffline(true);
        VideoDownloadController videoDownloadController = this.videoController;
        if (videoDownloadController != null) {
            if (videoDownload != null) {
                str = videoDownload.getTitle();
            }
            videoDownloadController.setTitle(str);
        }
        ((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)).start();
    }

    private final void onVideoResume() {
        if (((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)) != null) {
            VideoDownloadView ijkVideoView = (VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView);
            Intrinsics.checkExpressionValueIsNotNull(ijkVideoView, "ijkVideoView");
            if (ijkVideoView.getVisibility() != 0) {
                return;
            }
            ((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)).resume();
        }
    }

    private final void onVideoPause() {
        if (((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)) != null) {
            VideoDownloadView ijkVideoView = (VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView);
            Intrinsics.checkExpressionValueIsNotNull(ijkVideoView, "ijkVideoView");
            if (ijkVideoView.getVisibility() != 0) {
                return;
            }
            ((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)).pause();
        }
    }

    private final void onVideoRelease() {
        if (((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)) != null) {
            ((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)).release();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        onVideoResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        onVideoPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        onVideoRelease();
        M3U8DownloadManager.getInstance().setIM3U8DownloadListener(null);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)) != null && ((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)).onBackPressed()) {
            VideoDownloadView ijkVideoView = (VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView);
            Intrinsics.checkExpressionValueIsNotNull(ijkVideoView, "ijkVideoView");
            if (ijkVideoView.getVisibility() == 0) {
                return;
            }
        }
        if (((VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView)) != null) {
            VideoDownloadView ijkVideoView2 = (VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView);
            Intrinsics.checkExpressionValueIsNotNull(ijkVideoView2, "ijkVideoView");
            if (ijkVideoView2.getVisibility() == 0) {
                VideoDownloadView ijkVideoView3 = (VideoDownloadView) _$_findCachedViewById(R$id.ijkVideoView);
                Intrinsics.checkExpressionValueIsNotNull(ijkVideoView3, "ijkVideoView");
                ijkVideoView3.setVisibility(8);
                onVideoRelease();
                return;
            }
        }
        super.onBackPressed();
    }
}
