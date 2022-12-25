package com.gen.p059mh.webapp_extensions.views.player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapp_extensions.R$string;
import com.gen.p059mh.webapp_extensions.adapter.ResolutionAdapter;
import com.gen.p059mh.webapp_extensions.adapter.SettingViewAdapter;
import com.gen.p059mh.webapp_extensions.utils.DeviceUtils;
import com.gen.p059mh.webapp_extensions.views.player.PlayerDialogFrameLayout;
import com.gen.p059mh.webapp_extensions.views.player.VideoScaleView;
import com.gen.p059mh.webapp_extensions.views.player.VideoSettingView;
import com.gen.p059mh.webapp_extensions.views.player.VideoSpeedPlayView;
import com.gen.p059mh.webapps.listener.BackListener;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.one.tomato.entity.p079db.UserChannelBean;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.views.player.PlayerView */
/* loaded from: classes2.dex */
public class PlayerView extends NativeViewPlugin.NativeView implements PlayerControllerCallBack, BackListener {
    protected boolean autoFull;
    protected String cover;
    protected boolean isLocal;
    protected int landHeight;
    protected int landWidth;
    protected FrameLayout.LayoutParams layoutParams;
    protected OrientationEventListener orientationEventListener;
    protected OrientationUtils orientationUtils;
    protected IPlayer player;
    protected PlayerDialogFrameLayout playerDialogFrameLayout;
    protected Rect rect;
    protected List resolution;
    protected boolean _isFullscreen = false;
    protected int oldPortrait = 0;
    protected boolean autoPlay = true;
    protected boolean portrait = true;
    protected boolean isNeedControl = true;
    protected int startResolution = 0;
    protected int defaultRequestOrientation = 0;
    protected long startTime = 0;

    /* renamed from: X */
    protected int f1296X = 0;

    /* renamed from: Y */
    protected int f1297Y = 0;
    protected int oldOrientation = 1;
    private boolean portraitLock = false;
    private boolean landscapeLock = false;
    private NativeViewPlugin.NativeView.MethodHandler setPlaySrc = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.1
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(final List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            Logger.m4113i("setPlaySrc" + list.toString());
            IPlayer iPlayer = PlayerView.this.player;
            if (iPlayer != null) {
                iPlayer.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (PlayerView.this.resolutionMap.get(list.get(0)) != null) {
                            ((Map) PlayerView.this.resolutionMap.get(list.get(0))).put("src", list.get(1));
                        }
                        PlayerView.this.player.setUp(String.valueOf(list.get(1)), true, "");
                        PlayerView.this.player.startPlayLogic();
                        PlayerView playerView = PlayerView.this;
                        playerView.player.setSeekOnStart(playerView.startTime);
                    }
                });
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                methodCallback.run(hashMap);
            }
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler volume = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.2
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            IPlayer iPlayer = PlayerView.this.player;
            if (iPlayer != null) {
                float volume = iPlayer.getVolume();
                HashMap hashMap = new HashMap();
                hashMap.put("result", Float.valueOf(volume));
                methodCallback.run(hashMap);
            }
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler isPlaying = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.3
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            IPlayer iPlayer = PlayerView.this.player;
            if (iPlayer != null) {
                boolean isPlaying = iPlayer.isPlaying();
                HashMap hashMap = new HashMap();
                hashMap.put("result", Boolean.valueOf(isPlaying));
                methodCallback.run(hashMap);
            }
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler setLogo = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.4
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(final List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            IPlayer iPlayer = PlayerView.this.player;
            if (iPlayer != null) {
                iPlayer.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        String valueOf = String.valueOf(list.get(0));
                        if (valueOf.startsWith("/wocker_div")) {
                            valueOf = "http://" + ResourcesLoader.WORK_HOST + valueOf;
                        }
                        PlayerView.this.player.setLogo(valueOf);
                    }
                });
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                methodCallback.run(hashMap);
            }
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler setLoop = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.5
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(final List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            Logger.m4113i("setPlaySrc" + list.toString());
            IPlayer iPlayer = PlayerView.this.player;
            if (iPlayer != null) {
                iPlayer.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PlayerView.this.player.setLoopPlay(Boolean.valueOf(String.valueOf(list.get(0))).booleanValue());
                    }
                });
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }
    };
    protected NativeViewPlugin.NativeView.MethodHandler setResolutions = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.6
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            Logger.m4113i("setResolutions" + list.toString());
            PlayerView.this.doSetResolutions(list, methodCallback);
        }
    };
    private Map<Object, Object> resolutionMap = new HashMap();
    private NativeViewPlugin.NativeView.MethodHandler play = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.9
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            IPlayer iPlayer = PlayerView.this.player;
            if (iPlayer != null) {
                iPlayer.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PlayerView.this.player.startPlayLogic();
                        PlayerView playerView = PlayerView.this;
                        playerView.player.setSeekOnStart(playerView.startTime);
                    }
                });
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                methodCallback.run(hashMap);
            }
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler pause = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.10
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            IPlayer iPlayer = PlayerView.this.player;
            if (iPlayer != null) {
                iPlayer.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.10.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PlayerView playerView = PlayerView.this;
                        playerView.startTime = playerView.player.getCurrentPositionWhenPlaying();
                        PlayerView.this.player.onVideoPause();
                    }
                });
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                methodCallback.run(hashMap);
            }
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler seek = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.11
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (PlayerView.this.player != null) {
                final long longValue = ((Number) list.get(0)).longValue();
                PlayerView.this.player.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.11.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PlayerView.this.player.seekTo(longValue * 1000);
                    }
                });
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                methodCallback.run(hashMap);
            }
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler setFullscreen = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.12
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            String str;
            if (PlayerView.this.player != null) {
                final boolean booleanValue = ((Boolean) list.get(0)).booleanValue();
                if (booleanValue && (str = (String) list.get(1)) != null && !"portrait".equals(str)) {
                    "landscape".equals(str);
                }
                Logger.m4113i(list.toString());
                PlayerView.this.player.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.12.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PlayerView.this.setFullscreen(booleanValue);
                    }
                });
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                methodCallback.run(hashMap);
            }
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler isFullscreen = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.13
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (PlayerView.this.player != null) {
                HashMap hashMap = new HashMap();
                hashMap.put("result", Boolean.valueOf(PlayerView.this._isFullscreen));
                methodCallback.run(hashMap);
            }
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler getCurrentTime = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.15
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            HashMap hashMap = new HashMap();
            hashMap.put(AopConstants.TIME_KEY, Integer.valueOf(PlayerView.this.player.getCurrentPositionWhenPlaying() / 1000));
            if (PlayerView.this.player == null) {
                hashMap = null;
            }
            methodCallback.run(hashMap);
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler getDuration = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.16
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            HashMap hashMap = new HashMap();
            hashMap.put("result", Integer.valueOf(PlayerView.this.player.getDuration() / 1000));
            if (PlayerView.this.player == null) {
                hashMap = null;
            }
            methodCallback.run(hashMap);
        }
    };

    public IPlayer getPlayer() {
        return this.player;
    }

    public PlayerView(Context context) {
        super(context);
        registerMethod("setResolutions", this.setResolutions);
        registerMethod("play", this.play);
        registerMethod("pause", this.pause);
        registerMethod("seek", this.seek);
        registerMethod("getCurrentTime", this.getCurrentTime);
        registerMethod("setLogo", this.setLogo);
        registerMethod("setPlaySrc", this.setPlaySrc);
        registerMethod("setFullscreen", this.setFullscreen);
        registerMethod("isFullscreen", this.isFullscreen);
        registerMethod("isPlaying", this.isPlaying);
        registerMethod("volume", this.volume);
        registerMethod("setLoop", this.setLoop);
        registerMethod("getDuration", this.getDuration);
    }

    public void doSetResolutions(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        if (this.player != null) {
            List list2 = (List) list.get(0);
            this.resolution = new ArrayList();
            this.startResolution = 0;
            if (list2.size() > 0) {
                for (int i = 0; i < list2.size(); i++) {
                    Map map = (Map) list2.get(i);
                    if (map.get(UserChannelBean.TYPE_DEFAULT) != null && ((Boolean) map.get(UserChannelBean.TYPE_DEFAULT)).booleanValue()) {
                        this.startResolution = i;
                        VideoResolutionView.SELECT_POSITION = this.startResolution;
                    }
                    this.resolution.add(map);
                }
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put("title", "默认");
                hashMap.put("src", "http://103.51.15.118:18060/mahua_adult2/3741/B408A2A01174F062DABA060EDE98002B/B408A2A01174F062DABA060EDE98002B_17c0790c75a343a589c5979e694a32fd.mp4/index.m3u8");
                this.resolution.add(hashMap);
            }
            VideoResolutionView.setMapList(this.resolution);
            this.player.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.7
                @Override // java.lang.Runnable
                public void run() {
                    PlayerView playerView = PlayerView.this;
                    Map map2 = (Map) playerView.resolution.get(playerView.startResolution);
                    if (map2.get("callback") != null && ((Boolean) map2.get("callback")).booleanValue()) {
                        PlayerView.this.requestResolution(map2);
                        return;
                    }
                    PlayerView.this.player.onVideoReset();
                    PlayerView.this.player.setUp((String) map2.get("src"), true, "");
                    PlayerView.this.player.setResolution((String) map2.get("title"));
                    PlayerView playerView2 = PlayerView.this;
                    if (!playerView2.autoPlay) {
                        return;
                    }
                    playerView2.player.startPlayLogic();
                }
            });
            HashMap hashMap2 = new HashMap();
            hashMap2.put("success", true);
            methodCallback.run(hashMap2);
            return;
        }
        Toast.makeText(getContext(), "请使用播放器版本sdk", 0).show();
        Log.e("MethodHandler", "请使用播放器版本sdk");
        methodCallback.run(null);
    }

    public void requestResolution(final Map map) {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        hashMap2.put("key", map.get("key"));
        hashMap2.put("title", map.get("title"));
        hashMap.put("type", "src");
        hashMap.put("value", hashMap2);
        Logger.m4113i("start request resolution");
        sendEvent(hashMap, new JSResponseListener() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.8
            @Override // com.gen.p059mh.webapps.listener.JSResponseListener
            public void onResponse(Object obj) {
                Logger.m4113i("response request resolution" + obj.toString());
                PlayerView.this.resolutionMap.put(obj, map);
            }
        });
    }

    public void startPlay(int i) {
        this.startResolution = i;
        Map map = (Map) this.resolution.get(i);
        if (map.get("callback") != null && ((Boolean) map.get("callback")).booleanValue()) {
            requestResolution(map);
            return;
        }
        this.player.onVideoReset();
        this.player.setUp((String) map.get("src"), true, "");
        this.player.setResolution((String) map.get("title"));
        this.player.startPlayLogic();
        this.player.setSeekOnStart(this.startTime);
    }

    public void playEnd() {
        sendEvent("", new JSResponseListener(this) { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.14
            @Override // com.gen.p059mh.webapps.listener.JSResponseListener
            public void onResponse(Object obj) {
            }
        });
    }

    private void initDialogFrameLayout() {
        this.playerDialogFrameLayout = new PlayerDialogFrameLayout(getContext());
        getWebViewFragment().getNativeLayer().addView(this.playerDialogFrameLayout);
        this.playerDialogFrameLayout.setCallback(new PlayerDialogFrameLayout.Callback() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.17
            @Override // com.gen.p059mh.webapp_extensions.views.player.PlayerDialogFrameLayout.Callback
            public void showControlWidget() {
                PlayerView.this.player.onClickUiToggle();
            }

            @Override // com.gen.p059mh.webapp_extensions.views.player.PlayerDialogFrameLayout.Callback
            public void onViewDismiss(int i) {
                if (i == 0) {
                    PlayerView.this.startResolutionUI();
                } else if (i == 1) {
                    PlayerView.this.startSpeedUI();
                } else if (i != 2) {
                } else {
                    PlayerView.this.startScaleModel();
                }
            }

            @Override // com.gen.p059mh.webapp_extensions.views.player.PlayerDialogFrameLayout.Callback
            public void onVisibleChange(int i) {
                Logger.m4113i("visible:" + i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startScaleModel() {
        if (this.playerDialogFrameLayout == null) {
            initDialogFrameLayout();
        }
        this.player.hideAllWidget();
        VideoScaleView videoScaleView = new VideoScaleView(getContext());
        videoScaleView.setPlayerDialogCallback(new VideoScaleView.VideoScalePlayCallback() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.18
            @Override // com.gen.p059mh.webapp_extensions.views.player.VideoScaleView.VideoScalePlayCallback
            public void scaleChange(int i, int i2, String str) {
                if (PlayerView.this.player != null) {
                    GSYVideoType.setShowType(i);
                    PlayerView.this.player.changeTextureViewShowType();
                }
                PlayerView.this.playerDialogFrameLayout.dismissView();
            }
        });
        this.playerDialogFrameLayout.addContentView(videoScaleView);
        this.playerDialogFrameLayout.setCanceledOnTouchOutside(true);
        this.playerDialogFrameLayout.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startResolutionUI() {
        if (this.playerDialogFrameLayout == null) {
            initDialogFrameLayout();
        }
        this.player.hideAllWidget();
        VideoResolutionView videoResolutionView = new VideoResolutionView(getContext());
        videoResolutionView.refresh(this.resolution);
        videoResolutionView.setClickListener(new ResolutionAdapter.ClickListener() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.19
            @Override // com.gen.p059mh.webapp_extensions.adapter.ResolutionAdapter.ClickListener
            public void onCancel() {
                PlayerView.this.playerDialogFrameLayout.dismissView();
            }

            @Override // com.gen.p059mh.webapp_extensions.adapter.ResolutionAdapter.ClickListener
            public void onSelected(int i) {
                int currentPositionWhenPlaying = PlayerView.this.player.getCurrentPositionWhenPlaying();
                PlayerView.this.startPlay(i);
                PlayerView.this.player.setSeekOnStart(currentPositionWhenPlaying);
                PlayerView.this.playerDialogFrameLayout.dismissView();
            }
        });
        this.playerDialogFrameLayout.addContentView(videoResolutionView);
        this.playerDialogFrameLayout.setCanceledOnTouchOutside(true);
        this.playerDialogFrameLayout.show();
    }

    public void startSettingUI() {
        if (this.player != null) {
            if (this.playerDialogFrameLayout == null) {
                initDialogFrameLayout();
            }
            this.player.hideAllWidget();
            VideoSettingView videoSettingView = new VideoSettingView(getContext());
            videoSettingView.refreshData(initSettingData());
            videoSettingView.setSettingClickCallBack(new VideoSettingView.SettingClickCallBack() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.20
                @Override // com.gen.p059mh.webapp_extensions.views.player.VideoSettingView.SettingClickCallBack
                public void onSettingCallBack(int i) {
                    PlayerView.this.playerDialogFrameLayout.dismissView(i);
                }
            });
            this.playerDialogFrameLayout.addContentView(videoSettingView);
            this.playerDialogFrameLayout.setCanceledOnTouchOutside(true);
            this.playerDialogFrameLayout.show();
        }
    }

    private List<SettingViewAdapter.SettingData> initSettingData() {
        ArrayList arrayList = new ArrayList();
        SettingViewAdapter.SettingData settingData = new SettingViewAdapter.SettingData();
        settingData.setImageRes(R$mipmap.icon_player_pop_clarity);
        settingData.setName(getContext().getString(R$string.resolutions) + " · ");
        settingData.setSelectIndex(VideoResolutionView.SELECT_POSITION);
        arrayList.add(settingData);
        SettingViewAdapter.SettingData settingData2 = new SettingViewAdapter.SettingData();
        settingData2.setImageRes(R$mipmap.icon_player_pop_speed);
        settingData2.setName(getContext().getString(R$string.speed) + " · ");
        settingData2.setSelectIndex(VideoSpeedPlayView.videoPlaySpeedType);
        arrayList.add(settingData2);
        SettingViewAdapter.SettingData settingData3 = new SettingViewAdapter.SettingData();
        settingData3.setImageRes(R$mipmap.icon_player_pop_scale);
        settingData3.setName(getContext().getString(R$string.scale) + " · ");
        settingData3.setSelectIndex(VideoSpeedPlayView.videoPlaySpeedType);
        arrayList.add(settingData3);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSpeedUI() {
        if (this.player != null) {
            if (this.playerDialogFrameLayout == null) {
                initDialogFrameLayout();
            }
            this.player.hideAllWidget();
            VideoSpeedPlayView videoSpeedPlayView = new VideoSpeedPlayView(getContext());
            videoSpeedPlayView.setPlayerDialogCallback(new VideoSpeedPlayView.VideoSpeedPlayCallback() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.21
                @Override // com.gen.p059mh.webapp_extensions.views.player.VideoSpeedPlayView.VideoSpeedPlayCallback
                public void speedPlay(float f, float f2, String str) {
                    PlayerView.this.player.onVideoSpeedPlay(f, f2, str);
                    PlayerView.this.playerDialogFrameLayout.dismissView();
                }
            });
            this.playerDialogFrameLayout.addContentView(videoSpeedPlayView);
            this.playerDialogFrameLayout.setCanceledOnTouchOutside(true);
            this.playerDialogFrameLayout.show();
        }
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    @RequiresApi(api = 17)
    @SuppressLint({"SourceLockedOrientationActivity"})
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        try {
            try {
                Logger.m4112i("initialize", obj.toString());
                initPlayer();
                getWebViewFragment().addListener(this);
                getWebViewFragment().setRequestedOrientation(1);
                this.defaultRequestOrientation = getWebViewFragment().getActivity().getRequestedOrientation();
                if (this.player == null) {
                    return;
                }
                initParams(obj);
                initLayout();
            } catch (NoClassDefFoundError e) {
                e.printStackTrace();
                Log.w("onInitialize", "请加入播放器版本sdk");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initParams(Object obj) {
        if (obj != null) {
            Map map = (Map) obj;
            if (map.get("autoplay") != null) {
                this.autoPlay = ((Boolean) map.get("autoplay")).booleanValue();
            }
            if (map.containsKey("portrait")) {
                this.portrait = ((Boolean) map.get("portrait")).booleanValue();
            }
            if (map.containsKey("localPlay")) {
                this.isLocal = ((Boolean) map.get("localPlay")).booleanValue();
            }
            if (map.get("cover") != null) {
                this.cover = String.valueOf(map.get("cover"));
            }
            if (map.get("control") != null) {
                this.isNeedControl = ((Boolean) map.get("control")).booleanValue();
                this.player.setControl(this.isNeedControl);
            }
            map.containsKey("collectSelectImg");
            map.containsKey("mainColor");
            this.startTime = map.get("startTime") == null ? 0L : ((Number) map.get("startTime")).longValue() * 1000;
        }
    }

    public void initPlayer() {
        this.player = new CommonPlayerView(getContext());
    }

    private void initLayout() {
        this.player.provideView().setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        addView(this.player.provideView());
        String str = this.cover;
        if (str != null) {
            this.player.setCover(str);
        }
        this.player.setPlayerControllerCallBack(this);
        this.orientationUtils = new OrientationUtils(getWebViewFragment().getActivity(), this.player.provideView());
        this.orientationUtils.setEnable(false);
        new DisplayMetrics();
        this.layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        this.landWidth = this.layoutParams.width;
        this.landHeight = this.layoutParams.height;
        this.rect = new Rect();
        this.rect.left = this.layoutParams.leftMargin;
        this.rect.right = this.layoutParams.rightMargin;
        this.rect.top = this.layoutParams.topMargin;
        this.rect.bottom = this.layoutParams.bottomMargin;
        this.f1296X = (int) getX();
        this.f1297Y = (int) getY();
        this.player.setIfCurrentIsFullscreen(false);
        this.player.startClick(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PlayerView playerView = PlayerView.this;
                playerView.setFullscreen(!playerView._isFullscreen);
            }
        });
        this.orientationEventListener = new OrientationEventListener(getWebViewFragment().getContext()) { // from class: com.gen.mh.webapp_extensions.views.player.PlayerView.23
            @Override // android.view.OrientationEventListener
            public void onOrientationChanged(int i) {
                if (i > 355 || (i > 0 && i < 5)) {
                    PlayerView playerView = PlayerView.this;
                    if ((playerView.oldOrientation != 1 || playerView.landscapeLock) && !PlayerView.this.portraitLock) {
                        Log.e("xxx", "正竖屏");
                        if (PlayerView.this.getWebViewFragment().getActivity().getRequestedOrientation() == 1 && PlayerView.this.player.getVerticalScreen()) {
                            PlayerView.this.landscapeLock = false;
                            return;
                        }
                        PlayerView playerView2 = PlayerView.this;
                        playerView2.oldOrientation = 1;
                        playerView2.setFullscreen(false);
                        PlayerView.this.landscapeLock = false;
                        return;
                    }
                }
                if ((i <= 85 || i >= 95) && (i <= 265 || i >= 275)) {
                    return;
                }
                PlayerView playerView3 = PlayerView.this;
                if ((playerView3.oldOrientation == 2 && !playerView3.portraitLock) || PlayerView.this.landscapeLock) {
                    return;
                }
                Log.e("xxx", "90度 270度 横屏");
                PlayerView playerView4 = PlayerView.this;
                playerView4.oldOrientation = 2;
                playerView4.setFullscreen(true);
                PlayerView.this.portraitLock = false;
            }
        };
        if (this.portrait || this.isLocal) {
            return;
        }
        this.orientationEventListener.enable();
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView, com.gen.p059mh.webapps.listener.WebappLifecycleObserver
    public void onWebPause() {
        super.onWebPause();
        IPlayer iPlayer = this.player;
        if (iPlayer != null) {
            iPlayer.onVideoPause();
        }
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView, com.gen.p059mh.webapps.listener.WebappLifecycleObserver
    public void onWebResume() {
        super.onWebResume();
        IPlayer iPlayer = this.player;
        if (iPlayer != null) {
            iPlayer.onVideoResume();
        }
    }

    @SuppressLint({"SourceLockedOrientationActivity"})
    public void setFullscreen(boolean z) {
        if (this._isFullscreen == z) {
            return;
        }
        this._isFullscreen = z;
        if (z) {
            this.portraitLock = true;
            this.landscapeLock = false;
            int requestedOrientation = getWebViewFragment().getActivity().getRequestedOrientation();
            if (requestedOrientation == 1) {
                this.oldPortrait = 1;
                if (this.player.getVerticalScreen()) {
                    getWebViewFragment().setRequestedOrientation(1);
                } else {
                    getWebViewFragment().setRequestedOrientation(0);
                }
            } else if (requestedOrientation == 0) {
                this.oldPortrait = 0;
            } else if (requestedOrientation == 4) {
                this.oldPortrait = 1;
                if (this.player.getVerticalScreen()) {
                    getWebViewFragment().setRequestedOrientation(1);
                } else {
                    getWebViewFragment().setRequestedOrientation(11);
                }
            }
            getWebViewFragment().getActivity().getWindow().setFlags(1024, 1024);
            DeviceUtils.setFullScreen(getWebViewFragment().getActivity());
            hideNavKey(getWebViewFragment().getActivity());
            FrameLayout.LayoutParams layoutParams = this.layoutParams;
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.setMargins(0, 0, 0, 0);
            setLayoutParams(this.layoutParams);
            setXY(0, 0);
            getWebViewFragment().closeButtonHidden(false);
            this.player.setIfCurrentIsFullscreen(true);
            this.player.onFullScreen();
            return;
        }
        this.landscapeLock = true;
        this.portraitLock = false;
        getWebViewFragment().setRequestedOrientation(1);
        getWebViewFragment().getActivity().getWindow().clearFlags(1024);
        DeviceUtils.cancelFullScreen(getWebViewFragment().getActivity());
        showNavKey(getWebViewFragment().getActivity());
        getWebViewFragment().setNavigationBarTextStyle();
        FrameLayout.LayoutParams layoutParams2 = this.layoutParams;
        layoutParams2.width = this.landWidth;
        layoutParams2.height = this.landHeight;
        Rect rect = this.rect;
        layoutParams2.setMargins(rect.left, rect.top, rect.right, rect.bottom);
        setLayoutParams(this.layoutParams);
        setXY(this.f1296X, this.f1297Y);
        getWebViewFragment().closeButtonHidden(true);
        this.player.setIfCurrentIsFullscreen(false);
        this.player.quiteFullScreen();
        if (this.oldOrientation != 2) {
            return;
        }
        this.oldOrientation = 1;
    }

    public static void hideNavKey(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(2562);
        } else {
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(514);
        }
    }

    public static void showNavKey(Activity activity) {
        int i = Build.VERSION.SDK_INT;
        if (i > 11 && i < 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(0);
        } else if (Build.VERSION.SDK_INT < 19) {
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(4);
        }
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    @SuppressLint({"SourceLockedOrientationActivity"})
    public void onDestroy() {
        super.onDestroy();
        this.orientationEventListener.disable();
        IPlayer iPlayer = this.player;
        if (iPlayer != null) {
            iPlayer.release();
        }
    }

    public void showSetting() {
        startSettingUI();
    }

    public void reTry() {
        if (this.player != null) {
            startPlay(this.startResolution);
        }
    }

    public void onBackClick(boolean z) {
        Log.e("on back is full", z ? "true" : "false");
        if (z) {
            setFullscreen(!this._isFullscreen);
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("type", "back");
        hashMap.put("success", true);
        sendEvent(hashMap, null);
    }

    @Override // com.gen.p059mh.webapps.listener.BackListener
    public boolean onBackPressed() {
        if (this.player == null) {
            return false;
        }
        PlayerDialogFrameLayout playerDialogFrameLayout = this.playerDialogFrameLayout;
        if (playerDialogFrameLayout != null && playerDialogFrameLayout.isShowing()) {
            this.playerDialogFrameLayout.dismissView();
            return true;
        } else if (!this.player.isIfCurrentIsFullscreen()) {
            return false;
        } else {
            this.player.getFullscreenButton().callOnClick();
            return true;
        }
    }
}
