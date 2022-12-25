package com.tencent.liteav.audio.impl.Play;

import android.content.Context;
import com.tencent.liteav.audio.TXCAudioRecorder;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.TXIAudioPlayListener;
import com.tencent.liteav.audio.impl.TXCAudioUtil;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;
import com.tencent.ugc.TXRecordCommon;
import java.lang.ref.WeakReference;

/* renamed from: com.tencent.liteav.audio.impl.Play.b */
/* loaded from: classes3.dex */
public class TXCAudioSysPlayController extends TXCAudioBasePlayController {

    /* renamed from: a */
    public static final String f2074a = "AudioCenter:" + TXCAudioSysPlayController.class.getSimpleName();

    /* renamed from: b */
    private TXCAudioHWDecoder f2075b;

    /* renamed from: c */
    private TXSAudioPacket f2076c;

    public TXCAudioSysPlayController(Context context) {
        super(context);
    }

    protected void finalize() {
        long j = this.mJitterBuffer;
        if (j != 0) {
            nativeDestoryJitterBuffer(j);
            this.mJitterBuffer = 0L;
        }
    }

    /* renamed from: a */
    public static void m3429a(Context context, int i) {
        TXCMultAudioTrackPlayer.m3425a().m3424a(context, i);
    }

    @Override // com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController
    public long getCacheDuration() {
        return this.mIsHWAcceleration ? this.f2075b.m3436a() + nativeGetCacheDuration(this.mJitterBuffer) : nativeGetCacheDuration(this.mJitterBuffer);
    }

    @Override // com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController
    public int startPlay() {
        TXCLog.m2913i(f2074a, "start play audio!");
        if (!this.mIsPlaying) {
            if (this.mIsHWAcceleration) {
                this.f2075b = new TXCAudioHWDecoder();
                this.f2075b.m3433a(new WeakReference<>(this));
            }
            this.mIsPlaying = true;
            TXCLog.m2913i(f2074a, "finish start play audio!");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
        }
        TXCLog.m2914e(f2074a, "repeat start play audio, ignore it!");
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_REPEAT_OPTION;
    }

    @Override // com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController
    public int stopPlay() {
        TXCLog.m2913i(f2074a, "stop play audio!");
        if (this.mIsPlaying) {
            super.stopPlay();
            long j = this.mJitterBuffer;
            if (j != 0) {
                nativeDestoryJitterBuffer(j);
                this.mJitterBuffer = 0L;
            }
            if (TXCAudioBasePlayController.nativeIsTracksEmpty()) {
                TXCMultAudioTrackPlayer.m3425a().m3419c();
            }
            TXCAudioHWDecoder tXCAudioHWDecoder = this.f2075b;
            if (tXCAudioHWDecoder != null) {
                tXCAudioHWDecoder.m3432b();
                this.f2075b = null;
            }
            this.f2076c = null;
            TXCLog.m2913i(f2074a, "finish stop play audio!");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
        }
        TXCLog.m2914e(f2074a, "repeat stop play audio, ignore it!");
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_REPEAT_OPTION;
    }

    @Override // com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController
    public int playData(TXSAudioPacket tXSAudioPacket) {
        if (this.mIsHWAcceleration) {
            return m3427b(tXSAudioPacket);
        }
        return m3428a(tXSAudioPacket);
    }

    /* renamed from: a */
    private int m3428a(TXSAudioPacket tXSAudioPacket) {
        if (tXSAudioPacket == null) {
            return TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
        }
        int i = TXEAudioTypeDef.f2324k;
        int i2 = tXSAudioPacket.packetType;
        if (i != i2 && TXEAudioTypeDef.f2325l != i2 && TXEAudioTypeDef.f2326m != i2) {
            String str = f2074a;
            TXCLog.m2914e(str, "soft dec, not support audio type , packet type : " + tXSAudioPacket.packetType);
            int i3 = TXEAudioDef.TXE_AUDIO_PLAY_ERR_AUDIO_TYPE_NOT_SUPPORT;
            onPlayError(i3, "解码器不支持当前音频格式，包类型:" + tXSAudioPacket.packetType);
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_AUDIO_TYPE_NOT_SUPPORT;
        } else if (!this.mIsPlaying) {
            onPlayError(TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE, "播放器还没有启动");
            TXCLog.m2911w(f2074a, "sotf dec, invalid state. player not started yet!");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
        } else {
            if (this.mJitterBuffer == 0) {
                this.mJitterBuffer = nativeCreateJitterBuffer(false, this);
                long j = this.mJitterBuffer;
                if (j != 0) {
                    nativeSetSmoothMode(j, this.mSmoothMode);
                    nativeSetSmoothAdjust(this.mJitterBuffer, TXCConfigCenter.m2988a().m2979a("Audio", "SmoothAdjust"));
                    nativeSetCacheTime(this.mJitterBuffer, this.mCacheTime);
                    nativeSetMute(this.mJitterBuffer, this.mIsMute);
                    nativeEnableRealTimePlay(this.mJitterBuffer, this.mIsRealTimePlay);
                    nativeEnableAutoAdjustCache(this.mJitterBuffer, this.mIsAutoAdjustCache);
                    nativeSetAutoAdjustMaxCache(this.mJitterBuffer, this.mAutoAdjustMaxCache);
                    nativeSetAutoAdjustMinCache(this.mJitterBuffer, this.mAutoAdjustMinCache);
                    nativeSetSmoothSpeed(this.mJitterBuffer, TXCConfigCenter.m2988a().m2972b("Audio", "SmoothSpeed"));
                    nativeSetJitterCycle(this.mJitterBuffer, TXCConfigCenter.m2988a().m2979a("Audio", "LIVE_JitterCycle"));
                    nativeSetRealtimeJitterCycle(this.mJitterBuffer, TXCConfigCenter.m2988a().m2979a("Audio", "RTC_JitterCycle"));
                    nativeSetLoadingThreshold(this.mJitterBuffer, TXCConfigCenter.m2988a().m2979a("Audio", "LoadingThreshold"));
                    nativeSetRTCPlayHungryTimeThreshold(this.mJitterBuffer, (int) TXCConfigCenter.m2988a().m2979a("Audio", "RtcPlayHungryTimeThreshold"));
                } else {
                    TXCLog.m2914e(f2074a, "soft dec, create jitterbuffer failed!!");
                }
                String str2 = f2074a;
                TXCLog.m2914e(str2, "soft dec, create jitterbuffer with id " + this.mJitterBuffer);
            }
            if (TXEAudioTypeDef.f2324k == tXSAudioPacket.packetType) {
                String str3 = f2074a;
                TXCLog.m2913i(str3, "soft dec, recv aac seq " + TXCAudioUtil.m3349a(tXSAudioPacket.audioData));
            }
            long j2 = this.mJitterBuffer;
            if (j2 != 0) {
                nativeAddData(j2, tXSAudioPacket.audioData, tXSAudioPacket.packetType, tXSAudioPacket.timestamp);
                int i4 = TXEAudioTypeDef.f2324k;
                int i5 = tXSAudioPacket.packetType;
                if (i4 == i5) {
                    this.f2076c = new TXSAudioPacket();
                    this.f2076c.sampleRate = nativeGetSamplerate(this.mJitterBuffer);
                    this.f2076c.channelsPerSample = nativeGetChannel(this.mJitterBuffer);
                    TXSAudioPacket tXSAudioPacket2 = this.f2076c;
                    tXSAudioPacket2.bitsPerChannel = TXCAudioRecorder.f2044c;
                    tXSAudioPacket2.packetType = TXEAudioTypeDef.f2324k;
                    TXSAudioPacket tXSAudioPacket3 = new TXSAudioPacket();
                    tXSAudioPacket3.sampleRate = TXRecordCommon.AUDIO_SAMPLERATE_48000;
                    tXSAudioPacket3.channelsPerSample = 2;
                    tXSAudioPacket3.bitsPerChannel = 16;
                    tXSAudioPacket3.packetType = TXEAudioTypeDef.f2324k;
                    onPlayAudioInfoChanged(this.f2076c, tXSAudioPacket3);
                } else if (TXEAudioTypeDef.f2326m == i5 && this.f2076c == null) {
                    this.f2076c = new TXSAudioPacket();
                    this.f2076c.sampleRate = nativeGetSamplerate(this.mJitterBuffer);
                    this.f2076c.channelsPerSample = nativeGetChannel(this.mJitterBuffer);
                    TXSAudioPacket tXSAudioPacket4 = this.f2076c;
                    tXSAudioPacket4.bitsPerChannel = TXCAudioRecorder.f2044c;
                    tXSAudioPacket4.packetType = TXEAudioTypeDef.f2324k;
                    TXSAudioPacket tXSAudioPacket5 = new TXSAudioPacket();
                    tXSAudioPacket5.sampleRate = TXRecordCommon.AUDIO_SAMPLERATE_48000;
                    tXSAudioPacket5.channelsPerSample = 2;
                    tXSAudioPacket5.bitsPerChannel = 16;
                    tXSAudioPacket5.packetType = TXEAudioTypeDef.f2324k;
                    onPlayAudioInfoChanged(this.f2076c, tXSAudioPacket5);
                }
                return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
            }
            onPlayError(TXEAudioDef.TXE_AUDIO_PLAY_ERR_NOT_CREATE_JIT, "jitterbuf 还未创建");
            TXCLog.m2914e(f2074a, "soft dec, jitterbuffer not created yet!!");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_NOT_CREATE_JIT;
        }
    }

    /* renamed from: b */
    private int m3427b(TXSAudioPacket tXSAudioPacket) {
        if (tXSAudioPacket == null) {
            return TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
        }
        int i = TXEAudioTypeDef.f2324k;
        int i2 = tXSAudioPacket.packetType;
        if (i != i2 && TXEAudioTypeDef.f2325l != i2 && TXEAudioTypeDef.f2326m != i2) {
            String str = f2074a;
            TXCLog.m2914e(str, "hw dec, not support audio type , packet type : " + tXSAudioPacket.packetType);
            int i3 = TXEAudioDef.TXE_AUDIO_PLAY_ERR_AUDIO_TYPE_NOT_SUPPORT;
            onPlayError(i3, "解码器不支持当前音频格式，包类型:" + tXSAudioPacket.packetType);
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_AUDIO_TYPE_NOT_SUPPORT;
        } else if (!this.mIsPlaying) {
            onPlayError(TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE, "播放器还没有启动");
            TXCLog.m2911w(f2074a, "hw dec, invalid state. player not started yet!");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
        } else {
            if (this.mJitterBuffer == 0) {
                this.mJitterBuffer = nativeCreateJitterBuffer(true, this);
                long j = this.mJitterBuffer;
                if (j != 0) {
                    nativeSetSmoothMode(j, this.mSmoothMode);
                    nativeSetSmoothAdjust(this.mJitterBuffer, TXCConfigCenter.m2988a().m2979a("Audio", "SmoothAdjust"));
                    nativeSetCacheTime(this.mJitterBuffer, this.mCacheTime);
                    nativeSetMute(this.mJitterBuffer, this.mIsMute);
                    nativeEnableRealTimePlay(this.mJitterBuffer, this.mIsRealTimePlay);
                    nativeEnableAutoAdjustCache(this.mJitterBuffer, this.mIsAutoAdjustCache);
                    nativeSetAutoAdjustMaxCache(this.mJitterBuffer, this.mAutoAdjustMaxCache);
                    nativeSetAutoAdjustMinCache(this.mJitterBuffer, this.mAutoAdjustMinCache);
                    nativeSetSmoothSpeed(this.mJitterBuffer, TXCConfigCenter.m2988a().m2972b("Audio", "SmoothSpeed"));
                    nativeSetJitterCycle(this.mJitterBuffer, TXCConfigCenter.m2988a().m2979a("Audio", "LIVE_JitterCycle"));
                    nativeSetRealtimeJitterCycle(this.mJitterBuffer, TXCConfigCenter.m2988a().m2979a("Audio", "RTC_JitterCycle"));
                    nativeSetLoadingThreshold(this.mJitterBuffer, TXCConfigCenter.m2988a().m2979a("Audio", "LoadingThreshold"));
                    nativeSetRTCPlayHungryTimeThreshold(this.mJitterBuffer, (int) TXCConfigCenter.m2988a().m2979a("Audio", "RtcPlayHungryTimeThreshold"));
                } else {
                    TXCLog.m2914e(f2074a, "hw dec, create jitterbuffer failed!!");
                }
                String str2 = f2074a;
                TXCLog.m2914e(str2, "hw dec, create jitterbuffer with id " + this.mJitterBuffer);
            }
            if (TXEAudioTypeDef.f2324k == tXSAudioPacket.packetType) {
                String str3 = f2074a;
                TXCLog.m2913i(str3, "soft dec, recv aac seq " + TXCAudioUtil.m3349a(tXSAudioPacket.audioData));
            }
            TXCAudioHWDecoder tXCAudioHWDecoder = this.f2075b;
            if (tXCAudioHWDecoder != null) {
                tXCAudioHWDecoder.m3435a(tXSAudioPacket);
            }
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
        }
    }

    @Override // com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController, com.tencent.liteav.audio.TXIAudioPlayListener
    public void onPlayAudioInfoChanged(TXSAudioPacket tXSAudioPacket, TXSAudioPacket tXSAudioPacket2) {
        if (this.f2076c == null) {
            this.f2076c = tXSAudioPacket;
        }
        TXIAudioPlayListener tXIAudioPlayListener = this.mListener;
        if (tXIAudioPlayListener != null) {
            tXIAudioPlayListener.onPlayAudioInfoChanged(tXSAudioPacket, tXSAudioPacket2);
        }
        if (!TXCAudioBasePlayController.nativeIsTracksEmpty()) {
            TXCMultAudioTrackPlayer.m3425a().m3421b();
        }
    }

    @Override // com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController, com.tencent.liteav.audio.TXIAudioPlayListener
    public void onPlayPcmData(byte[] bArr, long j) {
        super.onPlayPcmData(bArr, j);
        long j2 = this.mJitterBuffer;
        if (j2 == 0 || !this.mIsHWAcceleration) {
            return;
        }
        nativeAddData(j2, bArr, TXEAudioTypeDef.f2327n, j);
    }
}
