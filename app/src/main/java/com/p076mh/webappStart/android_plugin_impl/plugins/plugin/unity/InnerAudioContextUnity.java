package com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity;

import android.support.annotation.NonNull;
import android.util.Log;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.C2345Wx;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.ZZBMediaPlayer;
import com.p076mh.webappStart.util.TransferUtil;
import com.sensorsdata.analytics.android.sdk.AopConstants;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity */
/* loaded from: classes3.dex */
public class InnerAudioContextUnity extends BaseUnity {
    boolean isNeedResume = false;
    private final Unity.Method playAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_play();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method pauseAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.2
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_pause();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method seekAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.3
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            InnerAudioContextUnity innerAudioContextUnity = InnerAudioContextUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = innerAudioContextUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.seekTo(TransferUtil.getIntFromDouble(innerAudioContextUnity.getParamFromJs(AopConstants.TIME_KEY, objArr)));
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method stopAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.4
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_stop();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method destroyAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.5
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_destroy();
                InnerAudioContextUnity.this.zzbMediaPlayer = null;
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method setSrcMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.6
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            InnerAudioContextUnity innerAudioContextUnity = InnerAudioContextUnity.this;
            if (innerAudioContextUnity.zzbMediaPlayer != null) {
                String str = (String) innerAudioContextUnity.getParamFromJs("url", objArr);
                if (str.startsWith("/")) {
                    str = "http://" + ResourcesLoader.WORK_HOST + str;
                }
                InnerAudioContextUnity.this.zzbMediaPlayer.setSrc(str);
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method setStartTimeMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.7
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            InnerAudioContextUnity innerAudioContextUnity = InnerAudioContextUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = innerAudioContextUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.setStartTime(TransferUtil.getIntFromDouble(innerAudioContextUnity.getParamFromJs(AopConstants.TIME_KEY, objArr)));
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method getDurationMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.8
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                InnerAudioContextUnity.this.releaseLockWithCallBackSuccess(methodCallback, Integer.valueOf(zZBMediaPlayer.wx_getDuration()));
                return;
            }
            Logger.m4113i("zzbMediaPlayer already destroyed");
            InnerAudioContextUnity.this.releaseLockWithCallBackFailure(methodCallback);
        }
    };
    private final Unity.Method getCurrentMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.9
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                InnerAudioContextUnity.this.releaseLockWithCallBackSuccess(methodCallback, Integer.valueOf(zZBMediaPlayer.wx_getCurrent()));
                return;
            }
            Logger.m4113i("zzbMediaPlayer already destroyed");
            InnerAudioContextUnity.this.releaseLockWithCallBackFailure(methodCallback);
        }
    };
    private final Unity.Method getPausedMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.10
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                InnerAudioContextUnity.this.releaseLockWithCallBackSuccess(methodCallback, Boolean.valueOf(!zZBMediaPlayer.isPlaying()));
                return;
            }
            Logger.m4113i("zzbMediaPlayer already destroyed");
            InnerAudioContextUnity.this.releaseLockWithCallBackFailure(methodCallback);
        }
    };
    private final Unity.Method getBufferedMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.11
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                InnerAudioContextUnity.this.releaseLockWithCallBackSuccess(methodCallback, Float.valueOf(zZBMediaPlayer.wx_getBuffered() / 1000.0f));
                return;
            }
            Logger.m4113i("zzbMediaPlayer already destroyed");
            InnerAudioContextUnity.this.releaseLockWithCallBackFailure(methodCallback);
        }
    };
    private final Unity.Method setAutoPlayMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.12
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            InnerAudioContextUnity innerAudioContextUnity = InnerAudioContextUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = innerAudioContextUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.setAutoplay(((Boolean) innerAudioContextUnity.getParamFromJs("isAuto", objArr)).booleanValue());
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method setLoopMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.13
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            InnerAudioContextUnity innerAudioContextUnity = InnerAudioContextUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = innerAudioContextUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.setWxLoop(((Boolean) innerAudioContextUnity.getParamFromJs("isLoop", objArr)).booleanValue());
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method setVolumeMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.14
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            InnerAudioContextUnity innerAudioContextUnity = InnerAudioContextUnity.this;
            ZZBMediaPlayer zZBMediaPlayer = innerAudioContextUnity.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.setVolume(TransferUtil.getFloatFromDouble(innerAudioContextUnity.getParamFromJs("volume", objArr)));
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLock(methodCallback);
        }
    };
    private final Unity.Method onCanPlayAudioStartMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.15
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_onCanplay();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method offCanPlayAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.16
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_offCanplay();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method onPlayAudioStartMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.17
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_onPlay();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method offPlayAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.18
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_offPlay();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method onPauseAudioStartMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.19
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_onPause();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method offPauseAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.20
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_offPause();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method onStopAudioStartMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.21
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_onStop();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method offStopAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.22
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_offStop();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method onEndAudioStartMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.23
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_onEnded();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method offEndAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.24
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_offEnded();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method onUpdateAudioStartMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.25
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_onTimeUpdate();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method offUpdateAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.26
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_offTimeUpdate();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method onErrAudioStartMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.27
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_onError();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method offErrAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.28
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_offError();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method onWaitAudioStartMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.29
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_onWaiting();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method offWaitAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.30
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_offWaiting();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method onSeekAudioStartMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.31
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_onSeeking();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method offSeekAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.32
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_offSeeking();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method onSeekedAudioStartMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.33
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_onSeeked();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    private final Unity.Method offSeekedAudioMethod = new Unity.Method() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin.unity.InnerAudioContextUnity.34
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4113i(objArr.toString());
            ZZBMediaPlayer zZBMediaPlayer = InnerAudioContextUnity.this.zzbMediaPlayer;
            if (zZBMediaPlayer != null) {
                zZBMediaPlayer.wx_offSeeked();
            } else {
                Logger.m4113i("zzbMediaPlayer already destroyed");
            }
            InnerAudioContextUnity.this.releaseLockWithCallBack(methodCallback, objArr);
        }
    };
    protected ZZBMediaPlayer zzbMediaPlayer = getMediaPlayer();

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        String str = this.TAG;
        Log.e(str, "onInitialize: " + getWebViewFragment());
        this.zzbMediaPlayer.setWebViewFragment(getWebViewFragment());
        this.zzbMediaPlayer.setUnity(this);
        registerMethod();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void registerMethod() {
        registerMethod("playAudio", this.playAudioMethod);
        registerMethod("pauseAudio", this.pauseAudioMethod);
        registerMethod("seekAudio", this.seekAudioMethod);
        registerMethod("stopAudio", this.stopAudioMethod);
        registerMethod("destroyAudio", this.destroyAudioMethod);
        registerMethod("setSrc", this.setSrcMethod);
        registerMethod("setStartTime", this.setStartTimeMethod);
        registerMethod("getDuration", this.getDurationMethod);
        registerMethod("getCurrent", this.getCurrentMethod);
        registerMethod("getPaused", this.getPausedMethod);
        registerMethod("getBuffered", this.getBufferedMethod);
        registerMethod("setAutoPlay", this.setAutoPlayMethod);
        registerMethod("setLoop", this.setLoopMethod);
        registerMethod("setVolume", this.setVolumeMethod);
        registerMethod("onCanPlayAudioStart", this.onCanPlayAudioStartMethod);
        registerMethod("offCanPlayAudio", this.offCanPlayAudioMethod);
        registerMethod("onPlayAudioStart", this.onPlayAudioStartMethod);
        registerMethod("offPlayAudio", this.offPlayAudioMethod);
        registerMethod("onPauseAudioStart", this.onPauseAudioStartMethod);
        registerMethod("offPauseAudio", this.offPauseAudioMethod);
        registerMethod("onStopAudioStart", this.onStopAudioStartMethod);
        registerMethod("offStopAudio", this.offStopAudioMethod);
        registerMethod("onEndAudioStart", this.onEndAudioStartMethod);
        registerMethod("offEndAudio", this.offEndAudioMethod);
        registerMethod("onUpdateAudioStart", this.onUpdateAudioStartMethod);
        registerMethod("offUpdateAudio", this.offUpdateAudioMethod);
        registerMethod("onErrAudioStart", this.onErrAudioStartMethod);
        registerMethod("offErrAudio", this.offErrAudioMethod);
        registerMethod("onWaitAudioStart", this.onWaitAudioStartMethod);
        registerMethod("offWaitAudio", this.offWaitAudioMethod);
        registerMethod("onSeekAudioStart", this.onSeekAudioStartMethod);
        registerMethod("offSeekAudio", this.offSeekAudioMethod);
        registerMethod("onSeekedAudioStart", this.onSeekedAudioStartMethod);
        registerMethod("offSeekedAudio", this.offSeekedAudioMethod);
    }

    @NonNull
    protected ZZBMediaPlayer getMediaPlayer() {
        return C2345Wx.createInnerAudioContext(this);
    }

    public ZZBMediaPlayer getZzbMediaPlayer() {
        return this.zzbMediaPlayer;
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onShow() {
        super.onShow();
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer == null || !this.isNeedResume) {
            return;
        }
        this.isNeedResume = false;
        zZBMediaPlayer.wx_play();
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onHide() {
        super.onHide();
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            this.isNeedResume = zZBMediaPlayer.getmState() == CZMediaPlayer.State.STARTED;
            if (!this.isNeedResume) {
                return;
            }
            this.zzbMediaPlayer.wx_pause();
        }
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void unload() {
        super.unload();
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.wx_destroy();
        }
    }
}
