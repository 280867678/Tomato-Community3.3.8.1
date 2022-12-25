package com.one.tomato.mvp.p080ui.post.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.broccoli.p150bh.R;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.controller.MediaPlayerControl;
import com.dueeeke.videoplayer.player.BaseIjkVideoView;
import com.dueeeke.videoplayer.util.WindowUtil;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.p082up.view.UpSubscribeActivity;
import com.one.tomato.mvp.p080ui.post.impl.PostDetailSeekBarCallBack;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.domain.DomainRequest;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.BaseToTVideoController;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.MarqueeTextView;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPostVideoController.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.NewPostVideoController */
/* loaded from: classes3.dex */
public final class NewPostVideoController extends BaseToTVideoController implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private HashMap _$_findViewCache;
    private Activity activity;
    private PostUtils.BackObserver backObserver;
    private Function1<? super Integer, Unit> clickFullScreamCallBack;
    private Functions<Unit> clickMenu;
    private int getCurrentPostion;
    private boolean isFull;
    private boolean mIsDragging;
    private final boolean mIsLive;
    private Function3<? super Long, ? super Long, ? super Integer, Unit> onProgressListener;
    private Function1<? super PostList, Unit> payComplete;
    private PostDetailSeekBarCallBack postDetailSeekBarCallBack;
    private PostList postList;
    private String videoPlayType;
    private final Animation mShowAnim = AnimationUtils.loadAnimation(getContext(), R.anim.post_video_alph_show);
    private final Animation mHideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.post_video_alph_hide);

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

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    protected int getLayoutId() {
        return R.layout.new_post_list_video_controller;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPostVideoController(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.activity = (Activity) context;
        setOnclick();
    }

    public final Function3<Long, Long, Integer, Unit> getOnProgressListener() {
        return this.onProgressListener;
    }

    public final void setOnProgressListener(Function3<? super Long, ? super Long, ? super Integer, Unit> function3) {
        this.onProgressListener = function3;
    }

    public final PostUtils.BackObserver getBackObserver() {
        return this.backObserver;
    }

    public final void setBackObserver(PostUtils.BackObserver backObserver) {
        this.backObserver = backObserver;
    }

    public final String getVideoPlayType() {
        return this.videoPlayType;
    }

    public final void setVideoPlayType(String str) {
        this.videoPlayType = str;
    }

    public final void setPost(PostList postList, boolean z) {
        String format;
        TextView textView;
        this.postList = postList;
        if (postList != null && !postList.isAlreadyPaid() && postList.getPrice() > 0 && postList.getMemberId() != DBUtil.getMemberId() && !z) {
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_need_pay);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            String string = AppUtil.getString(R.string.post_video_pay_text_prompt);
            if (PostUtils.INSTANCE.isPost10Video(postList.getVideoTime())) {
                Intrinsics.checkExpressionValueIsNotNull(string, "string");
                Object[] objArr = {60};
                format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            } else {
                Intrinsics.checkExpressionValueIsNotNull(string, "string");
                Object[] objArr2 = {15};
                format = String.format(string, Arrays.copyOf(objArr2, objArr2.length));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_try);
            if (textView2 != null) {
                textView2.setText(format);
            }
            if (postList.getSubscribeSwitch() != 1 || (textView = (TextView) _$_findCachedViewById(R$id.text_try_subscribe)) == null) {
                return;
            }
            textView.setVisibility(0);
            return;
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_need_pay);
        if (relativeLayout2 == null) {
            return;
        }
        relativeLayout2.setVisibility(8);
    }

    public final Functions<Unit> getClickMenu() {
        return this.clickMenu;
    }

    public final void setClickMenu(Functions<Unit> functions) {
        this.clickMenu = functions;
    }

    public final Function1<Integer, Unit> getClickFullScreamCallBack() {
        return this.clickFullScreamCallBack;
    }

    public final void setClickFullScreamCallBack(Function1<? super Integer, Unit> function1) {
        this.clickFullScreamCallBack = function1;
    }

    public final Function1<PostList, Unit> getPayComplete() {
        return this.payComplete;
    }

    public final void setPayComplete(Function1<? super PostList, Unit> function1) {
        this.payComplete = function1;
    }

    public final void setOnclick() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.fullscreen);
        if (imageView != null) {
            imageView.setOnClickListener(this);
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.back);
        if (imageView2 != null) {
            imageView2.setOnClickListener(this);
        }
        ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.lock);
        if (imageView3 != null) {
            imageView3.setOnClickListener(this);
        }
        ImageView imageView4 = (ImageView) _$_findCachedViewById(R$id.start_play);
        if (imageView4 != null) {
            imageView4.setOnClickListener(this);
        }
        ImageView imageView5 = (ImageView) _$_findCachedViewById(R$id.thumb);
        if (imageView5 != null) {
            imageView5.setOnClickListener(this);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.iv_replay);
        if (textView != null) {
            textView.setOnClickListener(this);
        }
        SeekBar seekBar = (SeekBar) _$_findCachedViewById(R$id.seekBar);
        if (seekBar != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        ImageView imageView6 = (ImageView) _$_findCachedViewById(R$id.iv_menu);
        if (imageView6 != null) {
            imageView6.setOnClickListener(this);
        }
        ImageView imageView7 = (ImageView) _$_findCachedViewById(R$id.iv_compelte_back);
        if (imageView7 != null) {
            imageView7.setOnClickListener(this);
        }
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.complete_container);
        if (linearLayout != null) {
            linearLayout.setOnClickListener(this);
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_try_pay);
        if (textView2 != null) {
            textView2.setOnClickListener(this);
        }
        ((TextView) _$_findCachedViewById(R$id.text_try_subscribe)).setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        PostList postList;
        MediaPlayerControl mediaPlayerControl;
        String str = null;
        Integer valueOf = view != null ? Integer.valueOf(view.getId()) : null;
        int i = 0;
        if (valueOf != null && valueOf.intValue() == R.id.fullscreen) {
            PostUtils postUtils = PostUtils.INSTANCE;
            PostList postList2 = this.postList;
            if (postList2 != null) {
                str = postList2.getSize();
            }
            if (!postUtils.isWidthVideo(str)) {
                Function1<? super Integer, Unit> function1 = this.clickFullScreamCallBack;
                if (function1 == null) {
                    return;
                }
                PostList postList3 = this.postList;
                if (postList3 != null) {
                    i = postList3.getId();
                }
                function1.mo6794invoke(Integer.valueOf(i));
                return;
            }
            changeFullScreem(2);
        } else if (valueOf != null && valueOf.intValue() == R.id.back) {
            changeFullScreem(1);
        } else if (valueOf != null && valueOf.intValue() == R.id.lock) {
            doLockUnlock();
        } else if ((valueOf != null && valueOf.intValue() == R.id.start_play) || (valueOf != null && valueOf.intValue() == R.id.thumb)) {
            if (this.mMediaPlayer == null) {
                return;
            }
            doPauseResume();
        } else if ((valueOf != null && valueOf.intValue() == R.id.iv_replay) || (valueOf != null && valueOf.intValue() == R.id.iv_refresh)) {
            MediaPlayerControl mediaPlayerControl2 = this.mMediaPlayer;
            if (mediaPlayerControl2 == null) {
                return;
            }
            mediaPlayerControl2.replay(true);
        } else if (valueOf != null && valueOf.intValue() == R.id.iv_compelte_back) {
            doStartStopFullScreen(1);
        } else if (valueOf != null && valueOf.intValue() == R.id.iv_menu) {
            Functions<Unit> functions = this.clickMenu;
            if (functions == null) {
                return;
            }
            functions.mo6822invoke();
        } else if (valueOf != null && valueOf.intValue() == R.id.complete_container) {
            if (this.mCurrentPlayState != 5 || (mediaPlayerControl = this.mMediaPlayer) == null) {
                return;
            }
            mediaPlayerControl.replay(true);
        } else if (valueOf != null && valueOf.intValue() == R.id.text_try_pay) {
            MediaPlayerControl mediaPlayerControl3 = this.mMediaPlayer;
            if (mediaPlayerControl3 != null) {
                mediaPlayerControl3.pause();
            }
            PostUtils postUtils2 = PostUtils.INSTANCE;
            Context context = getContext();
            Intrinsics.checkExpressionValueIsNotNull(context, "context");
            PostList postList4 = this.postList;
            String valueOf2 = postList4 != null ? String.valueOf(postList4.getPrice()) : null;
            PostList postList5 = this.postList;
            if (postList5 != null) {
                str = String.valueOf(postList5.getId());
            }
            String str2 = str;
            PostList postList6 = this.postList;
            postUtils2.showImageNeedPayDialog(context, valueOf2, str2, (postList6 == null || postList6.getSubscribeSwitch() != 1 || (postList = this.postList) == null) ? 0 : postList.getMemberId(), new NewPostVideoController$onClick$1(this), NewPostVideoController$onClick$2.INSTANCE);
        } else if (valueOf == null || valueOf.intValue() != R.id.text_try_subscribe) {
        } else {
            UpSubscribeActivity.Companion companion = UpSubscribeActivity.Companion;
            Context context2 = getContext();
            PostList postList7 = this.postList;
            if (postList7 != null) {
                i = postList7.getMemberId();
            }
            companion.startAct(context2, i);
        }
    }

    private final void doStartStopFullScreen(int i) {
        if (i == 1) {
            Activity scanForActivity = WindowUtil.scanForActivity(this.activity);
            Intrinsics.checkExpressionValueIsNotNull(scanForActivity, "WindowUtil.scanForActivity(activity)");
            if (scanForActivity.getRequestedOrientation() == 0) {
                this.isFull = true;
                ImageView fullscreen = (ImageView) _$_findCachedViewById(R$id.fullscreen);
                Intrinsics.checkExpressionValueIsNotNull(fullscreen, "fullscreen");
                fullscreen.setSelected(false);
                Activity scanForActivity2 = WindowUtil.scanForActivity(this.activity);
                Intrinsics.checkExpressionValueIsNotNull(scanForActivity2, "WindowUtil.scanForActivity(activity)");
                scanForActivity2.setRequestedOrientation(1);
                return;
            }
            Activity scanForActivity3 = WindowUtil.scanForActivity(this.activity);
            Intrinsics.checkExpressionValueIsNotNull(scanForActivity3, "WindowUtil.scanForActivity(activity)");
            if (1 != scanForActivity3.getRequestedOrientation()) {
                return;
            }
            BaseVideoController.FullScreamCallBack fullScreamCallBack = this.fullScreamCallBack;
            if (fullScreamCallBack != null) {
                fullScreamCallBack.fullCallBack(false);
            }
            this.mMediaPlayer.stopFullScreen();
            ImageView fullscreen2 = (ImageView) _$_findCachedViewById(R$id.fullscreen);
            Intrinsics.checkExpressionValueIsNotNull(fullscreen2, "fullscreen");
            fullscreen2.setSelected(false);
            this.isFull = false;
            PostUtils.INSTANCE.deleteBackObserver(this.backObserver);
            this.backObserver = null;
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_compelte_back);
            if (imageView == null) {
                return;
            }
            imageView.setVisibility(8);
            return;
        }
        registerBackPressed();
        BaseVideoController.FullScreamCallBack fullScreamCallBack2 = this.fullScreamCallBack;
        if (fullScreamCallBack2 != null) {
            fullScreamCallBack2.fullCallBack(true);
        }
        this.mMediaPlayer.startFullScreen();
        if (this.isFull) {
            this.isFull = false;
            ImageView fullscreen3 = (ImageView) _$_findCachedViewById(R$id.fullscreen);
            Intrinsics.checkExpressionValueIsNotNull(fullscreen3, "fullscreen");
            fullscreen3.setSelected(true);
            Activity scanForActivity4 = WindowUtil.scanForActivity(this.activity);
            Intrinsics.checkExpressionValueIsNotNull(scanForActivity4, "WindowUtil.scanForActivity(activity)");
            scanForActivity4.setRequestedOrientation(0);
            return;
        }
        this.isFull = true;
        ImageView fullscreen4 = (ImageView) _$_findCachedViewById(R$id.fullscreen);
        Intrinsics.checkExpressionValueIsNotNull(fullscreen4, "fullscreen");
        fullscreen4.setSelected(false);
        Activity scanForActivity5 = WindowUtil.scanForActivity(this.activity);
        Intrinsics.checkExpressionValueIsNotNull(scanForActivity5, "WindowUtil.scanForActivity(activity)");
        scanForActivity5.setRequestedOrientation(1);
    }

    public final void changeFullScreem(int i) {
        if (i == 1) {
            Activity scanForActivity = WindowUtil.scanForActivity(this.activity);
            Intrinsics.checkExpressionValueIsNotNull(scanForActivity, "WindowUtil.scanForActivity(activity)");
            if (scanForActivity.getRequestedOrientation() == 0) {
                this.isFull = true;
                ImageView fullscreen = (ImageView) _$_findCachedViewById(R$id.fullscreen);
                Intrinsics.checkExpressionValueIsNotNull(fullscreen, "fullscreen");
                fullscreen.setSelected(false);
                Activity scanForActivity2 = WindowUtil.scanForActivity(this.activity);
                Intrinsics.checkExpressionValueIsNotNull(scanForActivity2, "WindowUtil.scanForActivity(activity)");
                scanForActivity2.setRequestedOrientation(1);
                return;
            }
            Activity scanForActivity3 = WindowUtil.scanForActivity(this.activity);
            Intrinsics.checkExpressionValueIsNotNull(scanForActivity3, "WindowUtil.scanForActivity(activity)");
            if (1 != scanForActivity3.getRequestedOrientation()) {
                return;
            }
            BaseVideoController.FullScreamCallBack fullScreamCallBack = this.fullScreamCallBack;
            if (fullScreamCallBack != null) {
                fullScreamCallBack.fullCallBack(false);
            }
            this.mMediaPlayer.stopFullScreen();
            ImageView fullscreen2 = (ImageView) _$_findCachedViewById(R$id.fullscreen);
            Intrinsics.checkExpressionValueIsNotNull(fullscreen2, "fullscreen");
            fullscreen2.setSelected(false);
            this.isFull = false;
            PostUtils.INSTANCE.deleteBackObserver(this.backObserver);
            this.backObserver = null;
            ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_compelte_back);
            if (imageView == null) {
                return;
            }
            imageView.setVisibility(8);
            return;
        }
        registerBackPressed();
        BaseVideoController.FullScreamCallBack fullScreamCallBack2 = this.fullScreamCallBack;
        if (fullScreamCallBack2 != null) {
            fullScreamCallBack2.fullCallBack(true);
        }
        this.mMediaPlayer.startFullScreen();
        if (this.isFull) {
            this.isFull = false;
            ImageView fullscreen3 = (ImageView) _$_findCachedViewById(R$id.fullscreen);
            Intrinsics.checkExpressionValueIsNotNull(fullscreen3, "fullscreen");
            fullscreen3.setSelected(true);
            Activity scanForActivity4 = WindowUtil.scanForActivity(this.activity);
            Intrinsics.checkExpressionValueIsNotNull(scanForActivity4, "WindowUtil.scanForActivity(activity)");
            scanForActivity4.setRequestedOrientation(1);
            return;
        }
        this.isFull = true;
        ImageView fullscreen4 = (ImageView) _$_findCachedViewById(R$id.fullscreen);
        Intrinsics.checkExpressionValueIsNotNull(fullscreen4, "fullscreen");
        fullscreen4.setSelected(false);
        Activity scanForActivity5 = WindowUtil.scanForActivity(this.activity);
        Intrinsics.checkExpressionValueIsNotNull(scanForActivity5, "WindowUtil.scanForActivity(activity)");
        scanForActivity5.setRequestedOrientation(0);
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void setPlayerState(int i) {
        super.setPlayerState(i);
        if (i == 10) {
            PostDetailSeekBarCallBack postDetailSeekBarCallBack = this.postDetailSeekBarCallBack;
            if (postDetailSeekBarCallBack != null) {
                postDetailSeekBarCallBack.getPlayState(10);
            }
            LogUtil.m3784i("PLAYER_NORMAL");
            if (this.mIsLocked) {
                return;
            }
            setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            this.mIsGestureEnabled = false;
            ImageView fullscreen = (ImageView) _$_findCachedViewById(R$id.fullscreen);
            Intrinsics.checkExpressionValueIsNotNull(fullscreen, "fullscreen");
            fullscreen.setSelected(false);
            ((ImageView) _$_findCachedViewById(R$id.back)).setVisibility(8);
            ((ImageView) _$_findCachedViewById(R$id.lock)).setVisibility(8);
            ((MarqueeTextView) _$_findCachedViewById(R$id.title)).setVisibility(4);
            ((TextView) _$_findCachedViewById(R$id.sys_time)).setVisibility(8);
            ((LinearLayout) _$_findCachedViewById(R$id.top_container)).setVisibility(8);
        } else if (i != 11) {
        } else {
            PostDetailSeekBarCallBack postDetailSeekBarCallBack2 = this.postDetailSeekBarCallBack;
            if (postDetailSeekBarCallBack2 != null) {
                postDetailSeekBarCallBack2.getPlayState(11);
            }
            LogUtil.m3784i("PLAYER_FULL_SCREEN");
            if (this.mIsLocked) {
                return;
            }
            this.mIsGestureEnabled = true;
            ((ImageView) _$_findCachedViewById(R$id.back)).setVisibility(0);
            ((MarqueeTextView) _$_findCachedViewById(R$id.title)).setVisibility(0);
            ((TextView) _$_findCachedViewById(R$id.sys_time)).setVisibility(8);
            if (this.mShowing) {
                ((ImageView) _$_findCachedViewById(R$id.lock)).setVisibility(0);
                ((LinearLayout) _$_findCachedViewById(R$id.top_container)).setVisibility(0);
                return;
            }
            ((ImageView) _$_findCachedViewById(R$id.lock)).setVisibility(8);
        }
    }

    @Override // com.one.tomato.utils.BaseToTVideoController, com.dueeeke.videoplayer.controller.BaseVideoController
    public void setPlayState(int i) {
        super.setPlayState(i);
        switch (i) {
            case -1:
                PostDetailSeekBarCallBack postDetailSeekBarCallBack = this.postDetailSeekBarCallBack;
                if (postDetailSeekBarCallBack != null) {
                    postDetailSeekBarCallBack.getPlayState(-1);
                }
                LogUtil.m3784i("STATE_ERROR");
                ImageView start_play = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play, "start_play");
                start_play.setVisibility(8);
                LinearLayout bottom_container = (LinearLayout) _$_findCachedViewById(R$id.bottom_container);
                Intrinsics.checkExpressionValueIsNotNull(bottom_container, "bottom_container");
                bottom_container.setVisibility(8);
                ProgressBar loading = (ProgressBar) _$_findCachedViewById(R$id.loading);
                Intrinsics.checkExpressionValueIsNotNull(loading, "loading");
                loading.setVisibility(8);
                ImageView thumb = (ImageView) _$_findCachedViewById(R$id.thumb);
                Intrinsics.checkExpressionValueIsNotNull(thumb, "thumb");
                thumb.setVisibility(8);
                ((ProgressBar) _$_findCachedViewById(R$id.bottom_progress)).setVisibility(8);
                ((LinearLayout) _$_findCachedViewById(R$id.top_container)).setVisibility(8);
                DomainRequest.getInstance().switchDomainUrlByType("ttViewVideoNew");
                return;
            case 0:
                PostDetailSeekBarCallBack postDetailSeekBarCallBack2 = this.postDetailSeekBarCallBack;
                if (postDetailSeekBarCallBack2 != null) {
                    postDetailSeekBarCallBack2.getPlayState(0);
                }
                LogUtil.m3784i("STATE_IDLE");
                hide();
                this.mIsLocked = false;
                ImageView lock = (ImageView) _$_findCachedViewById(R$id.lock);
                Intrinsics.checkExpressionValueIsNotNull(lock, "lock");
                lock.setSelected(false);
                MediaPlayerControl mediaPlayerControl = this.mMediaPlayer;
                if (mediaPlayerControl != null) {
                    mediaPlayerControl.setLock(false);
                }
                ProgressBar bottom_progress = (ProgressBar) _$_findCachedViewById(R$id.bottom_progress);
                Intrinsics.checkExpressionValueIsNotNull(bottom_progress, "bottom_progress");
                bottom_progress.setProgress(0);
                ProgressBar bottom_progress2 = (ProgressBar) _$_findCachedViewById(R$id.bottom_progress);
                Intrinsics.checkExpressionValueIsNotNull(bottom_progress2, "bottom_progress");
                bottom_progress2.setSecondaryProgress(0);
                SeekBar seekBar = (SeekBar) _$_findCachedViewById(R$id.seekBar);
                Intrinsics.checkExpressionValueIsNotNull(seekBar, "seekBar");
                seekBar.setProgress(0);
                SeekBar seekBar2 = (SeekBar) _$_findCachedViewById(R$id.seekBar);
                Intrinsics.checkExpressionValueIsNotNull(seekBar2, "seekBar");
                seekBar2.setSecondaryProgress(0);
                LinearLayout complete_container = (LinearLayout) _$_findCachedViewById(R$id.complete_container);
                Intrinsics.checkExpressionValueIsNotNull(complete_container, "complete_container");
                complete_container.setVisibility(8);
                ImageView iv_compelte_back = (ImageView) _$_findCachedViewById(R$id.iv_compelte_back);
                Intrinsics.checkExpressionValueIsNotNull(iv_compelte_back, "iv_compelte_back");
                iv_compelte_back.setVisibility(8);
                ProgressBar bottom_progress3 = (ProgressBar) _$_findCachedViewById(R$id.bottom_progress);
                Intrinsics.checkExpressionValueIsNotNull(bottom_progress3, "bottom_progress");
                bottom_progress3.setVisibility(8);
                ProgressBar loading2 = (ProgressBar) _$_findCachedViewById(R$id.loading);
                Intrinsics.checkExpressionValueIsNotNull(loading2, "loading");
                loading2.setVisibility(8);
                ImageView start_play2 = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play2, "start_play");
                start_play2.setSelected(false);
                ImageView start_play3 = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play3, "start_play");
                start_play3.setVisibility(0);
                ImageView thumb2 = (ImageView) _$_findCachedViewById(R$id.thumb);
                Intrinsics.checkExpressionValueIsNotNull(thumb2, "thumb");
                thumb2.setVisibility(0);
                return;
            case 1:
                PostDetailSeekBarCallBack postDetailSeekBarCallBack3 = this.postDetailSeekBarCallBack;
                if (postDetailSeekBarCallBack3 != null) {
                    postDetailSeekBarCallBack3.getPlayState(1);
                }
                LogUtil.m3784i("STATE_PREPARING");
                ((LinearLayout) _$_findCachedViewById(R$id.complete_container)).setVisibility(8);
                ImageView iv_compelte_back2 = (ImageView) _$_findCachedViewById(R$id.iv_compelte_back);
                Intrinsics.checkExpressionValueIsNotNull(iv_compelte_back2, "iv_compelte_back");
                iv_compelte_back2.setVisibility(8);
                ((ImageView) _$_findCachedViewById(R$id.start_play)).setVisibility(8);
                LinearLayout bottom_container2 = (LinearLayout) _$_findCachedViewById(R$id.bottom_container);
                Intrinsics.checkExpressionValueIsNotNull(bottom_container2, "bottom_container");
                bottom_container2.setVisibility(8);
                ((ProgressBar) _$_findCachedViewById(R$id.loading)).setVisibility(0);
                ImageView thumb3 = (ImageView) _$_findCachedViewById(R$id.thumb);
                Intrinsics.checkExpressionValueIsNotNull(thumb3, "thumb");
                thumb3.setVisibility(0);
                return;
            case 2:
                PostDetailSeekBarCallBack postDetailSeekBarCallBack4 = this.postDetailSeekBarCallBack;
                if (postDetailSeekBarCallBack4 != null) {
                    postDetailSeekBarCallBack4.getPlayState(2);
                }
                LogUtil.m3784i("STATE_PREPARED");
                if (!this.mIsLive) {
                    ProgressBar bottom_progress4 = (ProgressBar) _$_findCachedViewById(R$id.bottom_progress);
                    Intrinsics.checkExpressionValueIsNotNull(bottom_progress4, "bottom_progress");
                    bottom_progress4.setVisibility(0);
                }
                ImageView start_play4 = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play4, "start_play");
                start_play4.setVisibility(8);
                LinearLayout bottom_container3 = (LinearLayout) _$_findCachedViewById(R$id.bottom_container);
                Intrinsics.checkExpressionValueIsNotNull(bottom_container3, "bottom_container");
                bottom_container3.setVisibility(8);
                ImageView thumb4 = (ImageView) _$_findCachedViewById(R$id.thumb);
                Intrinsics.checkExpressionValueIsNotNull(thumb4, "thumb");
                thumb4.setVisibility(0);
                return;
            case 3:
                postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.controller.NewPostVideoController$setPlayState$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PostDetailSeekBarCallBack postDetailSeekBarCallBack5;
                        Runnable runnable;
                        MediaPlayerControl mediaPlayerControl2;
                        MediaPlayerControl mediaPlayerControl3;
                        postDetailSeekBarCallBack5 = NewPostVideoController.this.postDetailSeekBarCallBack;
                        if (postDetailSeekBarCallBack5 != null) {
                            postDetailSeekBarCallBack5.getPlayState(3);
                        }
                        boolean globalVisibleRect = NewPostVideoController.this.getGlobalVisibleRect(new Rect());
                        if (!NewPostVideoController.this.isShown() && globalVisibleRect) {
                            mediaPlayerControl2 = ((BaseVideoController) NewPostVideoController.this).mMediaPlayer;
                            if (mediaPlayerControl2 == null) {
                                return;
                            }
                            mediaPlayerControl3 = ((BaseVideoController) NewPostVideoController.this).mMediaPlayer;
                            mediaPlayerControl3.pause();
                            return;
                        }
                        LogUtil.m3784i("STATE_PLAYING");
                        NewPostVideoController newPostVideoController = NewPostVideoController.this;
                        runnable = ((BaseVideoController) newPostVideoController).mShowProgress;
                        newPostVideoController.post(runnable);
                        ImageView start_play5 = (ImageView) NewPostVideoController.this._$_findCachedViewById(R$id.start_play);
                        Intrinsics.checkExpressionValueIsNotNull(start_play5, "start_play");
                        start_play5.setSelected(true);
                        ProgressBar loading3 = (ProgressBar) NewPostVideoController.this._$_findCachedViewById(R$id.loading);
                        Intrinsics.checkExpressionValueIsNotNull(loading3, "loading");
                        loading3.setVisibility(8);
                        LinearLayout complete_container2 = (LinearLayout) NewPostVideoController.this._$_findCachedViewById(R$id.complete_container);
                        Intrinsics.checkExpressionValueIsNotNull(complete_container2, "complete_container");
                        complete_container2.setVisibility(8);
                        ImageView iv_compelte_back3 = (ImageView) NewPostVideoController.this._$_findCachedViewById(R$id.iv_compelte_back);
                        Intrinsics.checkExpressionValueIsNotNull(iv_compelte_back3, "iv_compelte_back");
                        iv_compelte_back3.setVisibility(8);
                        ImageView thumb5 = (ImageView) NewPostVideoController.this._$_findCachedViewById(R$id.thumb);
                        Intrinsics.checkExpressionValueIsNotNull(thumb5, "thumb");
                        thumb5.setVisibility(8);
                        ImageView start_play6 = (ImageView) NewPostVideoController.this._$_findCachedViewById(R$id.start_play);
                        Intrinsics.checkExpressionValueIsNotNull(start_play6, "start_play");
                        start_play6.setVisibility(8);
                        LinearLayout bottom_container4 = (LinearLayout) NewPostVideoController.this._$_findCachedViewById(R$id.bottom_container);
                        Intrinsics.checkExpressionValueIsNotNull(bottom_container4, "bottom_container");
                        bottom_container4.setVisibility(8);
                    }
                }, 100L);
                return;
            case 4:
                PostDetailSeekBarCallBack postDetailSeekBarCallBack5 = this.postDetailSeekBarCallBack;
                if (postDetailSeekBarCallBack5 != null) {
                    postDetailSeekBarCallBack5.getPlayState(4);
                }
                LogUtil.m3784i("STATE_PAUSED");
                ImageView start_play5 = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play5, "start_play");
                start_play5.setSelected(false);
                ImageView start_play6 = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play6, "start_play");
                start_play6.setVisibility(0);
                LinearLayout bottom_container4 = (LinearLayout) _$_findCachedViewById(R$id.bottom_container);
                Intrinsics.checkExpressionValueIsNotNull(bottom_container4, "bottom_container");
                bottom_container4.setVisibility(0);
                return;
            case 5:
                PostDetailSeekBarCallBack postDetailSeekBarCallBack6 = this.postDetailSeekBarCallBack;
                if (postDetailSeekBarCallBack6 != null) {
                    postDetailSeekBarCallBack6.getPlayState(5);
                }
                LogUtil.m3784i("STATE_PLAYBACK_COMPLETED");
                hide();
                removeCallbacks(this.mShowProgress);
                ProgressBar loading3 = (ProgressBar) _$_findCachedViewById(R$id.loading);
                Intrinsics.checkExpressionValueIsNotNull(loading3, "loading");
                loading3.setVisibility(8);
                ImageView start_play7 = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play7, "start_play");
                start_play7.setVisibility(8);
                LinearLayout bottom_container5 = (LinearLayout) _$_findCachedViewById(R$id.bottom_container);
                Intrinsics.checkExpressionValueIsNotNull(bottom_container5, "bottom_container");
                bottom_container5.setVisibility(8);
                ImageView thumb5 = (ImageView) _$_findCachedViewById(R$id.thumb);
                Intrinsics.checkExpressionValueIsNotNull(thumb5, "thumb");
                thumb5.setVisibility(8);
                LinearLayout complete_container2 = (LinearLayout) _$_findCachedViewById(R$id.complete_container);
                Intrinsics.checkExpressionValueIsNotNull(complete_container2, "complete_container");
                complete_container2.setVisibility(0);
                MediaPlayerControl mediaPlayerControl2 = this.mMediaPlayer;
                if (mediaPlayerControl2 != null && mediaPlayerControl2.isFullScreen()) {
                    ImageView iv_compelte_back3 = (ImageView) _$_findCachedViewById(R$id.iv_compelte_back);
                    Intrinsics.checkExpressionValueIsNotNull(iv_compelte_back3, "iv_compelte_back");
                    iv_compelte_back3.setVisibility(0);
                } else {
                    ImageView iv_compelte_back4 = (ImageView) _$_findCachedViewById(R$id.iv_compelte_back);
                    Intrinsics.checkExpressionValueIsNotNull(iv_compelte_back4, "iv_compelte_back");
                    iv_compelte_back4.setVisibility(8);
                }
                ((ProgressBar) _$_findCachedViewById(R$id.bottom_progress)).setProgress(0);
                ProgressBar bottom_progress5 = (ProgressBar) _$_findCachedViewById(R$id.bottom_progress);
                Intrinsics.checkExpressionValueIsNotNull(bottom_progress5, "bottom_progress");
                bottom_progress5.setSecondaryProgress(0);
                this.mIsLocked = false;
                MediaPlayerControl mediaPlayerControl3 = this.mMediaPlayer;
                if (mediaPlayerControl3 == null) {
                    return;
                }
                mediaPlayerControl3.setLock(false);
                return;
            case 6:
                PostDetailSeekBarCallBack postDetailSeekBarCallBack7 = this.postDetailSeekBarCallBack;
                if (postDetailSeekBarCallBack7 != null) {
                    postDetailSeekBarCallBack7.getPlayState(6);
                }
                LogUtil.m3784i("STATE_BUFFERING");
                ImageView start_play8 = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play8, "start_play");
                start_play8.setVisibility(8);
                LinearLayout bottom_container6 = (LinearLayout) _$_findCachedViewById(R$id.bottom_container);
                Intrinsics.checkExpressionValueIsNotNull(bottom_container6, "bottom_container");
                bottom_container6.setVisibility(8);
                ((ProgressBar) _$_findCachedViewById(R$id.loading)).setVisibility(0);
                ((ImageView) _$_findCachedViewById(R$id.thumb)).setVisibility(8);
                ImageView start_play9 = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play9, "start_play");
                MediaPlayerControl mMediaPlayer = this.mMediaPlayer;
                Intrinsics.checkExpressionValueIsNotNull(mMediaPlayer, "mMediaPlayer");
                start_play9.setSelected(mMediaPlayer.isPlaying());
                return;
            case 7:
                PostDetailSeekBarCallBack postDetailSeekBarCallBack8 = this.postDetailSeekBarCallBack;
                if (postDetailSeekBarCallBack8 != null) {
                    postDetailSeekBarCallBack8.getPlayState(7);
                }
                LogUtil.m3784i("STATE_BUFFERED");
                ProgressBar loading4 = (ProgressBar) _$_findCachedViewById(R$id.loading);
                Intrinsics.checkExpressionValueIsNotNull(loading4, "loading");
                loading4.setVisibility(8);
                ((ImageView) _$_findCachedViewById(R$id.start_play)).setVisibility(8);
                LinearLayout bottom_container7 = (LinearLayout) _$_findCachedViewById(R$id.bottom_container);
                Intrinsics.checkExpressionValueIsNotNull(bottom_container7, "bottom_container");
                bottom_container7.setVisibility(8);
                ((ImageView) _$_findCachedViewById(R$id.thumb)).setVisibility(8);
                ImageView start_play10 = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play10, "start_play");
                MediaPlayerControl mMediaPlayer2 = this.mMediaPlayer;
                Intrinsics.checkExpressionValueIsNotNull(mMediaPlayer2, "mMediaPlayer");
                start_play10.setSelected(mMediaPlayer2.isPlaying());
                return;
            default:
                return;
        }
    }

    protected final void doLockUnlock() {
        if (this.mIsLocked) {
            this.mIsLocked = false;
            this.mShowing = false;
            this.mIsGestureEnabled = true;
            show();
            ImageView lock = (ImageView) _$_findCachedViewById(R$id.lock);
            Intrinsics.checkExpressionValueIsNotNull(lock, "lock");
            lock.setSelected(false);
            Toast.makeText(getContext(), (int) R.string.dkplayer_unlocked, 0).show();
        } else {
            hide();
            this.mIsLocked = true;
            this.mIsGestureEnabled = false;
            ImageView lock2 = (ImageView) _$_findCachedViewById(R$id.lock);
            Intrinsics.checkExpressionValueIsNotNull(lock2, "lock");
            lock2.setSelected(true);
            Toast.makeText(getContext(), (int) R.string.dkplayer_locked, 0).show();
        }
        MediaPlayerControl mediaPlayerControl = this.mMediaPlayer;
        if (mediaPlayerControl != null) {
            mediaPlayerControl.setLock(this.mIsLocked);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (!z) {
            return;
        }
        MediaPlayerControl mediaPlayerControl = this.mMediaPlayer;
        long duration = mediaPlayerControl != null ? mediaPlayerControl.getDuration() : 0L;
        long max = (i * duration) / (seekBar != null ? seekBar.getMax() : 0);
        if (((TextView) _$_findCachedViewById(R$id.curr_time)) != null) {
            this.getCurrentPostion = (int) max;
        }
        TextView curr_time = (TextView) _$_findCachedViewById(R$id.curr_time);
        Intrinsics.checkExpressionValueIsNotNull(curr_time, "curr_time");
        curr_time.setText(stringForTime(this.getCurrentPostion));
        long j = max / 1000;
        Function3<? super Long, ? super Long, ? super Integer, Unit> function3 = this.onProgressListener;
        if (function3 == null) {
            return;
        }
        function3.invoke(Long.valueOf(max), Long.valueOf(duration), Integer.valueOf((int) j));
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.mIsDragging = true;
        removeCallbacks(this.mShowProgress);
        removeCallbacks(this.mFadeOut);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        MediaPlayerControl mediaPlayerControl = this.mMediaPlayer;
        this.mMediaPlayer.seekTo(((mediaPlayerControl != null ? mediaPlayerControl.getDuration() : 0L) * (seekBar != null ? seekBar.getProgress() : 0)) / (seekBar != null ? seekBar.getMax() : 0));
        this.mIsDragging = false;
        post(this.mShowProgress);
        show();
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void hide() {
        if (this.mShowing) {
            LogUtil.m3784i("hide");
            if (this.mMediaPlayer.isFullScreen()) {
                ImageView lock = (ImageView) _$_findCachedViewById(R$id.lock);
                Intrinsics.checkExpressionValueIsNotNull(lock, "lock");
                lock.setVisibility(8);
                if (!this.mIsLocked) {
                    hideAllViews();
                }
            } else {
                LinearLayout bottom_container = (LinearLayout) _$_findCachedViewById(R$id.bottom_container);
                Intrinsics.checkExpressionValueIsNotNull(bottom_container, "bottom_container");
                bottom_container.setVisibility(8);
                ((LinearLayout) _$_findCachedViewById(R$id.bottom_container)).startAnimation(this.mHideAnim);
                ImageView start_play = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play, "start_play");
                start_play.setVisibility(8);
                ((ImageView) _$_findCachedViewById(R$id.start_play)).startAnimation(this.mHideAnim);
            }
            this.mShowing = false;
            PostDetailSeekBarCallBack postDetailSeekBarCallBack = this.postDetailSeekBarCallBack;
            if (postDetailSeekBarCallBack == null) {
                return;
            }
            postDetailSeekBarCallBack.isShowOrHide(this.mShowing);
        }
    }

    private final void show(int i) {
        if (((TextView) _$_findCachedViewById(R$id.sys_time)) != null) {
            ((TextView) _$_findCachedViewById(R$id.sys_time)).setText(getCurrentSystemTime());
        }
        if (!this.mShowing) {
            LogUtil.m3784i("show");
            MediaPlayerControl mMediaPlayer = this.mMediaPlayer;
            Intrinsics.checkExpressionValueIsNotNull(mMediaPlayer, "mMediaPlayer");
            if (mMediaPlayer.isFullScreen()) {
                ImageView lock = (ImageView) _$_findCachedViewById(R$id.lock);
                Intrinsics.checkExpressionValueIsNotNull(lock, "lock");
                lock.setVisibility(0);
                if (!this.mIsLocked) {
                    showAllViews();
                }
            } else {
                ((LinearLayout) _$_findCachedViewById(R$id.bottom_container)).setVisibility(0);
                ((LinearLayout) _$_findCachedViewById(R$id.bottom_container)).startAnimation(this.mShowAnim);
                ImageView start_play = (ImageView) _$_findCachedViewById(R$id.start_play);
                Intrinsics.checkExpressionValueIsNotNull(start_play, "start_play");
                start_play.setVisibility(0);
                ((ImageView) _$_findCachedViewById(R$id.start_play)).startAnimation(this.mShowAnim);
            }
            this.mShowing = true;
            PostDetailSeekBarCallBack postDetailSeekBarCallBack = this.postDetailSeekBarCallBack;
            if (postDetailSeekBarCallBack != null) {
                postDetailSeekBarCallBack.isShowOrHide(this.mShowing);
            }
        }
        removeCallbacks(this.mFadeOut);
        if (i != 0) {
            postDelayed(this.mFadeOut, i);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void show() {
        show(this.mDefaultTimeout);
    }

    private final void hideAllViews() {
        ((LinearLayout) _$_findCachedViewById(R$id.top_container)).setVisibility(8);
        ((LinearLayout) _$_findCachedViewById(R$id.top_container)).startAnimation(this.mHideAnim);
        ((LinearLayout) _$_findCachedViewById(R$id.bottom_container)).setVisibility(8);
        ((LinearLayout) _$_findCachedViewById(R$id.bottom_container)).startAnimation(this.mHideAnim);
        ImageView start_play = (ImageView) _$_findCachedViewById(R$id.start_play);
        Intrinsics.checkExpressionValueIsNotNull(start_play, "start_play");
        start_play.setVisibility(8);
        ((ImageView) _$_findCachedViewById(R$id.start_play)).startAnimation(this.mHideAnim);
    }

    private final void showAllViews() {
        ((LinearLayout) _$_findCachedViewById(R$id.bottom_container)).setVisibility(0);
        ((LinearLayout) _$_findCachedViewById(R$id.bottom_container)).startAnimation(this.mShowAnim);
        ((LinearLayout) _$_findCachedViewById(R$id.top_container)).setVisibility(0);
        ((LinearLayout) _$_findCachedViewById(R$id.top_container)).startAnimation(this.mShowAnim);
        ImageView start_play = (ImageView) _$_findCachedViewById(R$id.start_play);
        Intrinsics.checkExpressionValueIsNotNull(start_play, "start_play");
        start_play.setVisibility(0);
        ((ImageView) _$_findCachedViewById(R$id.start_play)).startAnimation(this.mShowAnim);
    }

    public final void setonProgressListener(Function3<? super Long, ? super Long, ? super Integer, Unit> function3) {
        this.onProgressListener = function3;
    }

    public final void setPostDetailSeekBarCallBack(PostDetailSeekBarCallBack postDetailSeekBarCallBack) {
        this.postDetailSeekBarCallBack = postDetailSeekBarCallBack;
        PostDetailSeekBarCallBack postDetailSeekBarCallBack2 = this.postDetailSeekBarCallBack;
        if (postDetailSeekBarCallBack2 != null) {
            postDetailSeekBarCallBack2.getFrameVideoController((FrameLayout) _$_findCachedViewById(R$id.fragment_video_controller));
        }
    }

    public final void clickMenuCallBack(Functions<Unit> functions) {
        this.clickMenu = functions;
    }

    public final void clickFullScremCallBack(Function1<? super Integer, Unit> function1) {
        this.clickFullScreamCallBack = function1;
    }

    public final void payCompleteCallBack(Function1<? super PostList, Unit> function1) {
        this.payComplete = function1;
    }

    public final boolean isPlayCommplete() {
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.complete_container);
        return linearLayout != null && linearLayout.getVisibility() == 0;
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    protected int setProgress() {
        int i;
        MediaPlayerControl mMediaPlayer = this.mMediaPlayer;
        int i2 = 0;
        if (mMediaPlayer == null || this.mIsDragging || this.mIsLive) {
            return 0;
        }
        Intrinsics.checkExpressionValueIsNotNull(mMediaPlayer, "mMediaPlayer");
        long currentPosition = mMediaPlayer.getCurrentPosition();
        MediaPlayerControl mMediaPlayer2 = this.mMediaPlayer;
        Intrinsics.checkExpressionValueIsNotNull(mMediaPlayer2, "mMediaPlayer");
        long duration = mMediaPlayer2.getDuration();
        MediaPlayerControl mediaPlayerControl = this.mMediaPlayer;
        if (mediaPlayerControl == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.dueeeke.videoplayer.player.BaseIjkVideoView");
        }
        if (((BaseIjkVideoView) mediaPlayerControl).getCurrentPlayState() == 3) {
            Function3<? super Long, ? super Long, ? super Integer, Unit> function3 = this.onProgressListener;
        }
        if (((SeekBar) _$_findCachedViewById(R$id.seekBar)) != null) {
            if (duration > 0) {
                SeekBar seekBar = (SeekBar) _$_findCachedViewById(R$id.seekBar);
                Intrinsics.checkExpressionValueIsNotNull(seekBar, "seekBar");
                seekBar.setEnabled(true);
                SeekBar seekBar2 = (SeekBar) _$_findCachedViewById(R$id.seekBar);
                Intrinsics.checkExpressionValueIsNotNull(seekBar2, "seekBar");
                i2 = (int) (((currentPosition * 1.0d) / duration) * seekBar2.getMax());
                SeekBar seekBar3 = (SeekBar) _$_findCachedViewById(R$id.seekBar);
                Intrinsics.checkExpressionValueIsNotNull(seekBar3, "seekBar");
                seekBar3.setProgress(i2);
                ProgressBar bottom_progress = (ProgressBar) _$_findCachedViewById(R$id.bottom_progress);
                Intrinsics.checkExpressionValueIsNotNull(bottom_progress, "bottom_progress");
                bottom_progress.setProgress(i2);
            } else {
                SeekBar seekBar4 = (SeekBar) _$_findCachedViewById(R$id.seekBar);
                Intrinsics.checkExpressionValueIsNotNull(seekBar4, "seekBar");
                seekBar4.setEnabled(false);
            }
            MediaPlayerControl mMediaPlayer3 = this.mMediaPlayer;
            Intrinsics.checkExpressionValueIsNotNull(mMediaPlayer3, "mMediaPlayer");
            int bufferedPercentage = mMediaPlayer3.getBufferedPercentage();
            if (bufferedPercentage >= 95) {
                SeekBar seekBar5 = (SeekBar) _$_findCachedViewById(R$id.seekBar);
                Intrinsics.checkExpressionValueIsNotNull(seekBar5, "seekBar");
                SeekBar seekBar6 = (SeekBar) _$_findCachedViewById(R$id.seekBar);
                Intrinsics.checkExpressionValueIsNotNull(seekBar6, "seekBar");
                seekBar5.setSecondaryProgress(seekBar6.getMax());
                SeekBar seekBar7 = (SeekBar) _$_findCachedViewById(R$id.seekBar);
                Intrinsics.checkExpressionValueIsNotNull(seekBar7, "seekBar");
                i = seekBar7.getMax();
                ProgressBar bottom_progress2 = (ProgressBar) _$_findCachedViewById(R$id.bottom_progress);
                Intrinsics.checkExpressionValueIsNotNull(bottom_progress2, "bottom_progress");
                ProgressBar bottom_progress3 = (ProgressBar) _$_findCachedViewById(R$id.bottom_progress);
                Intrinsics.checkExpressionValueIsNotNull(bottom_progress3, "bottom_progress");
                bottom_progress2.setSecondaryProgress(bottom_progress3.getMax());
            } else {
                i = bufferedPercentage * 10;
                SeekBar seekBar8 = (SeekBar) _$_findCachedViewById(R$id.seekBar);
                Intrinsics.checkExpressionValueIsNotNull(seekBar8, "seekBar");
                seekBar8.setSecondaryProgress(i);
                ProgressBar bottom_progress4 = (ProgressBar) _$_findCachedViewById(R$id.bottom_progress);
                Intrinsics.checkExpressionValueIsNotNull(bottom_progress4, "bottom_progress");
                bottom_progress4.setSecondaryProgress(i);
            }
        } else {
            i = 0;
        }
        if (((TextView) _$_findCachedViewById(R$id.total_time)) != null) {
            TextView total_time = (TextView) _$_findCachedViewById(R$id.total_time);
            Intrinsics.checkExpressionValueIsNotNull(total_time, "total_time");
            total_time.setText(stringForTime((int) duration));
        }
        if (((TextView) _$_findCachedViewById(R$id.curr_time)) != null) {
            TextView curr_time = (TextView) _$_findCachedViewById(R$id.curr_time);
            Intrinsics.checkExpressionValueIsNotNull(curr_time, "curr_time");
            curr_time.setText(stringForTime((int) currentPosition));
        }
        long j = currentPosition / 1000;
        Function3<? super Long, ? super Long, ? super Integer, Unit> function32 = this.onProgressListener;
        if (function32 != null) {
            function32.invoke(Long.valueOf(currentPosition), Long.valueOf(duration), Integer.valueOf((int) j));
        }
        PostDetailSeekBarCallBack postDetailSeekBarCallBack = this.postDetailSeekBarCallBack;
        if (postDetailSeekBarCallBack != null) {
            String stringForTime = stringForTime((int) duration);
            Intrinsics.checkExpressionValueIsNotNull(stringForTime, "stringForTime(duration.toInt())");
            String stringForTime2 = stringForTime((int) currentPosition);
            Intrinsics.checkExpressionValueIsNotNull(stringForTime2, "stringForTime(position.toInt())");
            postDetailSeekBarCallBack.getTimeProgess(stringForTime, stringForTime2, i2, i);
        }
        return (int) currentPosition;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.GestureVideoController
    public void slideToChangePosition(float f) {
        super.slideToChangePosition(f);
        if (this.mIsLive) {
            this.mNeedSeek = false;
        } else {
            super.slideToChangePosition(f);
        }
    }

    public final ImageView getThumb() {
        ImageView thumb = (ImageView) _$_findCachedViewById(R$id.thumb);
        Intrinsics.checkExpressionValueIsNotNull(thumb, "thumb");
        return thumb;
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public boolean onBackPressed() {
        if (this.mIsLocked) {
            show();
            Toast.makeText(getContext(), (int) R.string.dkplayer_lock_tip, 0).show();
            return true;
        }
        MediaPlayerControl mMediaPlayer = this.mMediaPlayer;
        Intrinsics.checkExpressionValueIsNotNull(mMediaPlayer, "mMediaPlayer");
        if (mMediaPlayer.isFullScreen()) {
            BaseVideoController.FullScreamCallBack fullScreamCallBack = this.fullScreamCallBack;
            if (fullScreamCallBack != null) {
                fullScreamCallBack.fullCallBack(false);
            }
            this.mMediaPlayer.stopFullScreen();
            ImageView fullscreen = (ImageView) _$_findCachedViewById(R$id.fullscreen);
            Intrinsics.checkExpressionValueIsNotNull(fullscreen, "fullscreen");
            fullscreen.setSelected(false);
            Activity scanForActivity = WindowUtil.scanForActivity(this.activity);
            Intrinsics.checkExpressionValueIsNotNull(scanForActivity, "WindowUtil.scanForActivity(activity)");
            if (scanForActivity.getRequestedOrientation() == 0) {
                Activity scanForActivity2 = WindowUtil.scanForActivity(this.activity);
                Intrinsics.checkExpressionValueIsNotNull(scanForActivity2, "WindowUtil.scanForActivity(activity)");
                scanForActivity2.setRequestedOrientation(1);
            }
            return true;
        }
        return super.onBackPressed();
    }

    public final void backPressed() {
        if (this.mIsLocked) {
            show();
            Toast.makeText(getContext(), (int) R.string.dkplayer_lock_tip, 0).show();
            return;
        }
        changeFullScreem(1);
    }

    private final void registerBackPressed() {
        if (this.backObserver == null) {
            this.backObserver = new PostUtils.BackObserver() { // from class: com.one.tomato.mvp.ui.post.controller.NewPostVideoController$registerBackPressed$1
                @Override // com.one.tomato.mvp.p080ui.post.utils.PostUtils.BackObserver
                public void update(Boolean bool) {
                    if (Intrinsics.areEqual(bool, true)) {
                        NewPostVideoController.this.backPressed();
                    }
                }
            };
            PostUtils.INSTANCE.addBackObserver(this.backObserver);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void showStatusView() {
        ToastUtil.showCenterToast(AppUtil.getString(R.string.post_video_play_mobile));
    }

    @Override // com.one.tomato.utils.BaseToTVideoController
    public Map<String, Object> getVideoErrorInfo() {
        HashMap hashMap = new HashMap();
        hashMap.put("postion", Integer.valueOf(this.getCurrentPostion));
        PostList postList = this.postList;
        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(postList != null ? postList.getId() : 0));
        String str = this.videoPlayType;
        if (str == null) {
            str = "";
        }
        hashMap.put("type", str);
        return hashMap;
    }

    @Override // com.one.tomato.utils.BaseToTVideoController
    public boolean isReplay() {
        setPlayState(5);
        setKeepScreenOn(false);
        MediaPlayerControl mediaPlayerControl = this.mMediaPlayer;
        if (mediaPlayerControl != null) {
            ((BaseIjkVideoView) mediaPlayerControl).onPlayStopped();
            return false;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.dueeeke.videoplayer.player.BaseIjkVideoView");
    }
}
