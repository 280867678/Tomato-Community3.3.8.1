package com.gen.p059mh.webapp_extensions.views.player;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.views.player.custom.HlwPlayerView;
import com.gen.p059mh.webapp_extensions.views.player.custom.PlayerStatesListener;
import com.gen.p059mh.webapp_extensions.views.player.custom.ResolutionUtils;
import com.gen.p059mh.webapp_extensions.views.player.custom.ResourceEntity;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.listener.NativeMethod;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.one.tomato.entity.p079db.UserChannelBean;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.views.player.HPlayer */
/* loaded from: classes2.dex */
public class HPlayer extends PlayerView implements PlayerStatesListener {
    String videoTitle;
    private int collectStatus = 0;
    boolean mOpenPreView = false;
    private NativeViewPlugin.NativeView.MethodHandler setCanPlay = new NativeViewPlugin.NativeView.MethodHandler(this) { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.1
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            Logger.m4112i("setCanPlay", list.get(0).toString());
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler setCollection = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.2
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (list != null) {
                HPlayer.this.collectStatus = ((Number) list.get(0)).intValue();
                HPlayer.this.player.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        HPlayer hPlayer = HPlayer.this;
                        IPlayer iPlayer = hPlayer.player;
                        boolean z = true;
                        if (hPlayer.collectStatus != 1) {
                            z = false;
                        }
                        iPlayer.setCollection(z);
                    }
                });
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler setAd = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.3
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            final String str;
            if (list != null) {
                final int i = 0;
                Map map = (Map) list.get(0);
                final String str2 = "";
                if (!map.containsKey("insertAD") || !map.containsKey("showAdTime")) {
                    str = str2;
                } else {
                    String decode = URLDecoder.decode((String) map.get("insertAD"));
                    str = decode;
                    i = ((Number) map.get("showAdTime")).intValue();
                }
                if (map.containsKey("pauseAd")) {
                    str2 = URLDecoder.decode((String) map.get("pauseAd"));
                }
                IPlayer iPlayer = HPlayer.this.player;
                if (iPlayer != null) {
                    iPlayer.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            String str3 = "http://" + HPlayer.this.getWebViewFragment().getHostStart();
                            if (!TextUtils.isEmpty(str2)) {
                                HPlayer.this.player.setAdsUrl(str3 + str2);
                            }
                            if (!TextUtils.isEmpty(str)) {
                                HPlayer.this.player.setOtherAdsUrl(str3 + str, i);
                            }
                        }
                    });
                }
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler getCurrentPosition = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.4
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            IPlayer iPlayer = HPlayer.this.player;
            int curPosition = iPlayer != null ? iPlayer.getCurPosition() / 1000 : 0;
            HashMap hashMap = new HashMap();
            hashMap.put(AopConstants.TIME_KEY, Integer.valueOf(curPosition));
            methodCallback.run(hashMap);
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler setThumbnailBg = new NativeViewPlugin.NativeView.MethodHandler(this) { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.5
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }
    };

    public boolean checkPlayerStateCanPlay() {
        return false;
    }

    public boolean isCanPlay() {
        return true;
    }

    public void jumpToVip() {
    }

    public boolean onCheckUserPermission(int i) {
        return false;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.custom.PlayerStatesListener
    public void onClarityChange(int i) {
    }

    public void onShareClick() {
    }

    public void setStatus(int i) {
    }

    public HPlayer(Context context) {
        super(context);
        registerMethod("setCanPlay", this.setCanPlay);
        registerMethod("setCollection", this.setCollection);
        registerMethod("setAd", this.setAd);
        registerMethod("getCurrentTime", this.getCurrentPosition);
        registerMethod("setThumbnailBg", this.setThumbnailBg);
    }

    @NativeMethod("getVersion")
    public static void getVersion(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        methodCallback.run("1.0.0");
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.PlayerView
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
                    }
                    this.resolution.add(new ResourceEntity((String) map.get("title"), (String) map.get("src"), map.get("key") == null ? 1 : ResolutionUtils.getResolutionByKey((String) map.get("key")), map.get("callback") == null ? false : ((Boolean) map.get("callback")).booleanValue()));
                }
                this.player.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.6
                    @Override // java.lang.Runnable
                    public void run() {
                        HPlayer hPlayer = HPlayer.this;
                        if (hPlayer.autoPlay) {
                            hPlayer.player.setResolutions(hPlayer.resolution, hPlayer.videoTitle, null);
                        }
                    }
                });
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
            return;
        }
        Toast.makeText(getContext(), "请使用播放器版本sdk", 0).show();
        Log.e("MethodHandler", "请使用播放器版本sdk");
        methodCallback.run(null);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.PlayerView
    public void initPlayer() {
        try {
            this.player = new HlwPlayerView(getContext());
            this.player.setStatusListener(this);
        } catch (NoClassDefFoundError unused) {
            Log.e("onInitialize", "请加入播放器版本sdk");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.PlayerView
    public void initParams(Object obj) {
        String str;
        super.initParams(obj);
        if (obj != null) {
            Map map = (Map) obj;
            if (map.containsKey("autoFullscreen")) {
                this.autoFull = ((Boolean) map.get("autoFullscreen")).booleanValue();
                getWebViewFragment().setRequestedOrientation(this.autoFull ? 4 : 1);
                this.defaultRequestOrientation = getWebViewFragment().getActivity().getRequestedOrientation();
            }
            if (map.containsKey("title")) {
                this.videoTitle = String.valueOf(map.get("title"));
            }
            if (map.containsKey("videoType")) {
                ((Number) map.get("videoType")).intValue();
            }
            if (map.containsKey("jumpSwitch")) {
                ((Boolean) map.get("jumpSwitch")).booleanValue();
            }
            if (map.containsKey("continueSwitch")) {
                ((Boolean) map.get("continueSwitch")).booleanValue();
            }
            if (map.containsKey("starter")) {
                str = String.valueOf(map.get("starter"));
                this.player.setHeaderVodUrl(str);
            }
        }
        str = "";
        this.player.setHeaderVodUrl(str);
    }

    public void onBackClick(boolean z, boolean z2) {
        if (z && z2) {
            this.player.toggleLock();
        } else if (z && !this.isLocal) {
            setFullscreen(!this._isFullscreen);
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put("type", "back");
            hashMap.put("success", true);
            sendEvent(hashMap, null);
        }
    }

    public void onPrepared(int i) {
        HashMap hashMap = new HashMap();
        hashMap.put("duration", Integer.valueOf(i / 1000));
        hashMap.put(AopConstants.TIME_KEY, 1);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("type", "thumbnails");
        hashMap2.put("value", hashMap);
        Logger.m4112i("onPrepared", new Gson().toJson(hashMap2));
        sendEvent(hashMap2, new JSResponseListener() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.7
            @Override // com.gen.p059mh.webapps.listener.JSResponseListener
            public void onResponse(Object obj) {
                boolean z = false;
                HPlayer hPlayer = HPlayer.this;
                if (((List) ((List) obj).get(0)).size() > 0) {
                    z = true;
                }
                hPlayer.mOpenPreView = z;
            }
        });
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.custom.PlayerStatesListener
    public String provideResource(int i) {
        ResourceEntity resourceByResolution = ResolutionUtils.getResourceByResolution(this.resolution, i);
        if (resourceByResolution == null || resourceByResolution.isCallback()) {
            return null;
        }
        return resourceByResolution.getUrl();
    }

    public void onRefresh(long j) {
        if (this.autoPlay) {
            this.player.setResolutions(this.resolution, this.videoTitle, null);
            this.player.setSeekOnStart(j);
        }
    }

    public void onAdsCLick() {
        HashMap hashMap = new HashMap();
        hashMap.put("type", "clickAD");
        HashMap hashMap2 = new HashMap();
        hashMap2.put("adType", 1);
        hashMap.put("value", hashMap2);
        sendEvent(hashMap, null);
    }

    public void onOtherAdsCLick() {
        HashMap hashMap = new HashMap();
        hashMap.put("type", "clickAD");
        HashMap hashMap2 = new HashMap();
        hashMap2.put("adType", 2);
        hashMap.put("value", hashMap2);
        sendEvent(hashMap, null);
    }

    public void onErrorClick() {
        HashMap hashMap = new HashMap();
        hashMap.put("type", "alert");
        hashMap.put("success", true);
        sendEvent(hashMap, null);
    }

    public void onCollectClick(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put("collectionState", Boolean.valueOf(z));
        HashMap hashMap2 = new HashMap();
        hashMap2.put("type", "collection");
        hashMap2.put("value", hashMap);
        sendEvent(hashMap2, null);
    }

    public void requestPreview(final int i, int i2) {
        HashMap hashMap = new HashMap();
        hashMap.put("duration", Integer.valueOf(i2 / 1000));
        hashMap.put(AopConstants.TIME_KEY, Integer.valueOf(i / 1000));
        HashMap hashMap2 = new HashMap();
        hashMap2.put("type", "thumbnails");
        hashMap2.put("value", hashMap);
        sendEvent(hashMap2, new JSResponseListener() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.8
            @Override // com.gen.p059mh.webapps.listener.JSResponseListener
            public void onResponse(Object obj) {
                final List list = (List) ((List) obj).get(0);
                HPlayer.this.player.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        IPlayer iPlayer = HPlayer.this.player;
                        iPlayer.setPreViewList("http://" + HPlayer.this.getWebViewFragment().getHostStart(), list, i);
                    }
                });
            }
        });
    }

    public void onSpeedClick() {
        HashMap hashMap = new HashMap();
        hashMap.put("type", "speed");
        sendEvent(hashMap, new JSResponseListener() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.9
            @Override // com.gen.p059mh.webapps.listener.JSResponseListener
            public void onResponse(Object obj) {
                if (((Boolean) ((List) obj).get(0)).booleanValue()) {
                    HPlayer.this.player.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.9.1
                        @Override // java.lang.Runnable
                        public void run() {
                            HPlayer.this.player.speedOnClickBack();
                        }
                    });
                }
            }
        });
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.custom.PlayerStatesListener
    public void onSpeedChange(int i) {
        String str = "1.0";
        if (i == 1) {
            str = "0.5";
        } else if (i != 2) {
            if (i == 3) {
                str = "1.5";
            } else if (i == 4) {
                str = "2.0";
            }
        }
        HashMap hashMap = new HashMap();
        hashMap.put("description", str);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("type", "rate");
        hashMap2.put("value", hashMap);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.custom.PlayerStatesListener
    public void onClarityClick(final ResourceEntity resourceEntity) {
        HashMap hashMap = new HashMap();
        hashMap.put("key", ResolutionUtils.getKeyByResource(resourceEntity.getResolution()));
        HashMap hashMap2 = new HashMap();
        hashMap2.put("type", "src");
        hashMap2.put("value", hashMap);
        sendEvent(hashMap2, resourceEntity.isCallback() ? new JSResponseListener() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.10
            @Override // com.gen.p059mh.webapps.listener.JSResponseListener
            public void onResponse(Object obj) {
                Logger.m4112i("onClarityClick", obj);
                List list = (List) obj;
                if (list.size() <= 0 || (list.get(0) instanceof Number) || !(list.get(0) instanceof String)) {
                    return;
                }
                HPlayer.this.player.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.player.HPlayer.10.1
                    @Override // java.lang.Runnable
                    public void run() {
                        C168410 c168410 = C168410.this;
                        HPlayer.this.player.clarityOnClickBack(resourceEntity.getResolution());
                    }
                });
            }
        } : null);
    }

    public void sendVideoScreen(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put("type", "orientation");
        hashMap.put("value", z ? "portrait" : "auto");
        sendEvent(hashMap, null);
    }

    public boolean isCanPreview() {
        return this.mOpenPreView;
    }
}
