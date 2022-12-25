package com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity;

import android.support.annotation.NonNull;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.C2345Wx;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.ZZBMediaPlayer;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BackgroundAudioManagerUnity */
/* loaded from: classes3.dex */
public class BackgroundAudioManagerUnity extends InnerAudioContextUnity {
    private static BackgroundAudioManagerUnity instance = new BackgroundAudioManagerUnity();
    private final Unity.Method setTitleMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BackgroundAudioManagerUnity.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            BackgroundAudioManagerUnity backgroundAudioManagerUnity = BackgroundAudioManagerUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = backgroundAudioManagerUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.setTitle((String) backgroundAudioManagerUnity.getParamFromJs("title", objArr));
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            BackgroundAudioManagerUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method setEpnameMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BackgroundAudioManagerUnity.2
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            BackgroundAudioManagerUnity backgroundAudioManagerUnity = BackgroundAudioManagerUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = backgroundAudioManagerUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.setEpname((String) backgroundAudioManagerUnity.getParamFromJs("epname", objArr));
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            BackgroundAudioManagerUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method setSingerMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BackgroundAudioManagerUnity.3
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            BackgroundAudioManagerUnity backgroundAudioManagerUnity = BackgroundAudioManagerUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = backgroundAudioManagerUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.setSinger((String) backgroundAudioManagerUnity.getParamFromJs("singer", objArr));
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            BackgroundAudioManagerUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method setCoverMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BackgroundAudioManagerUnity.4
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            BackgroundAudioManagerUnity backgroundAudioManagerUnity = BackgroundAudioManagerUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = backgroundAudioManagerUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.setCoverImgUrl((String) backgroundAudioManagerUnity.getParamFromJs("url", objArr));
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            BackgroundAudioManagerUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method setWebUrlMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BackgroundAudioManagerUnity.5
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            BackgroundAudioManagerUnity backgroundAudioManagerUnity = BackgroundAudioManagerUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = backgroundAudioManagerUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.setWebUrl((String) backgroundAudioManagerUnity.getParamFromJs("url", objArr));
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            BackgroundAudioManagerUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method setProtocolMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.BackgroundAudioManagerUnity.6
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            BackgroundAudioManagerUnity backgroundAudioManagerUnity = BackgroundAudioManagerUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = backgroundAudioManagerUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.setProtocol((String) backgroundAudioManagerUnity.getParamFromJs("protocol", objArr));
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            BackgroundAudioManagerUnity.this.releaseLock(methodCallback);
        }
    };

    private BackgroundAudioManagerUnity() {
    }

    public static BackgroundAudioManagerUnity getInstance() {
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity
    public void registerMethod() {
        super.registerMethod();
        registerMethod("setTitle", this.setTitleMethod);
        registerMethod("setEpname", this.setEpnameMethod);
        registerMethod("setSinger", this.setSingerMethod);
        registerMethod("setCover", this.setCoverMethod);
        registerMethod("setWebUrl", this.setWebUrlMethod);
        registerMethod("setProtocol", this.setProtocolMethod);
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity
    @NonNull
    protected ZZBMediaPlayer getMediaPlayer() {
        return C2345Wx.getBackgroundAudioManager();
    }
}
