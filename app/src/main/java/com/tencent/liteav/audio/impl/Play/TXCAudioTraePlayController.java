package com.tencent.liteav.audio.impl.Play;

import android.content.Context;
import com.tencent.liteav.audio.TXCAudioRecorder;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.TXIAudioPlayListener;
import com.tencent.liteav.audio.impl.TXCTraeJNI;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;
import com.tencent.ugc.TXRecordCommon;

/* renamed from: com.tencent.liteav.audio.impl.Play.c */
/* loaded from: classes3.dex */
public class TXCAudioTraePlayController extends TXCAudioBasePlayController implements TXIAudioPlayListener {

    /* renamed from: a */
    private static final String f2077a = "AudioCenter:" + TXCAudioTraePlayController.class.getSimpleName();

    /* renamed from: b */
    private TXSAudioPacket f2078b;

    public TXCAudioTraePlayController(Context context) {
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
    public static void m3426a(Context context, int i) {
        TXCTraeJNI.setContext(context);
        TXCTraeJNI.nativeSetAudioMode(i);
    }

    @Override // com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController
    public int startPlay() {
        TXCLog.m2913i(f2077a, "start play audio!");
        if (!this.mIsPlaying) {
            super.startPlay();
            TXCLog.m2913i(f2077a, "finish start play audio!");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
        }
        TXCLog.m2914e(f2077a, "repeat start play audio, ignore it!");
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_REPEAT_OPTION;
    }

    @Override // com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController
    public int stopPlay() {
        TXCLog.m2913i(f2077a, "stop play audio!");
        if (this.mIsPlaying) {
            super.stopPlay();
            long j = this.mJitterBuffer;
            if (j != 0) {
                nativeDestoryJitterBuffer(j);
                this.mJitterBuffer = 0L;
            }
            TXCTraeJNI.traeStopPlay();
            this.f2078b = null;
            TXCLog.m2913i(f2077a, "finish stop play audio!");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
        }
        TXCLog.m2914e(f2077a, "repeat stop play audio, ignore it!");
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_REPEAT_OPTION;
    }

    @Override // com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController
    public int playData(TXSAudioPacket tXSAudioPacket) {
        if (tXSAudioPacket == null) {
            return TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
        }
        int i = TXEAudioTypeDef.f2324k;
        int i2 = tXSAudioPacket.packetType;
        if (i != i2 && TXEAudioTypeDef.f2325l != i2 && TXEAudioTypeDef.f2326m != i2) {
            String str = f2077a;
            TXCLog.m2914e(str, "soft dec, not support audio type , packet type : " + tXSAudioPacket.packetType);
            int i3 = TXEAudioDef.TXE_AUDIO_PLAY_ERR_AUDIO_TYPE_NOT_SUPPORT;
            onPlayError(i3, "解码器不支持当前音频格式，包类型:" + tXSAudioPacket.packetType);
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_AUDIO_TYPE_NOT_SUPPORT;
        }
        if (TXEAudioTypeDef.f2324k == tXSAudioPacket.packetType) {
            String str2 = f2077a;
            TXCLog.m2913i(str2, "soft dec, recv aac seq " + tXSAudioPacket.audioData);
        }
        if (!this.mIsPlaying) {
            onPlayError(TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE, "播放器还没有启动");
            TXCLog.m2911w(f2077a, "sotf dec, invalid state. player not started yet!");
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
        }
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
                TXCLog.m2914e(f2077a, "soft dec, create jitterbuffer failed!!");
            }
            String str3 = f2077a;
            TXCLog.m2914e(str3, "soft dec, create jitterbuffer with id " + this.mJitterBuffer);
        }
        long j2 = this.mJitterBuffer;
        if (j2 != 0) {
            nativeAddData(j2, tXSAudioPacket.audioData, tXSAudioPacket.packetType, tXSAudioPacket.timestamp);
            int i4 = TXEAudioTypeDef.f2324k;
            int i5 = tXSAudioPacket.packetType;
            if (i4 == i5) {
                this.f2078b = new TXSAudioPacket();
                this.f2078b.sampleRate = nativeGetSamplerate(this.mJitterBuffer);
                this.f2078b.channelsPerSample = nativeGetChannel(this.mJitterBuffer);
                TXSAudioPacket tXSAudioPacket2 = this.f2078b;
                tXSAudioPacket2.bitsPerChannel = TXCAudioRecorder.f2044c;
                tXSAudioPacket2.packetType = TXEAudioTypeDef.f2324k;
                TXSAudioPacket tXSAudioPacket3 = new TXSAudioPacket();
                tXSAudioPacket3.sampleRate = TXRecordCommon.AUDIO_SAMPLERATE_48000;
                tXSAudioPacket3.channelsPerSample = 2;
                tXSAudioPacket3.bitsPerChannel = 16;
                tXSAudioPacket3.packetType = TXEAudioTypeDef.f2324k;
                onPlayAudioInfoChanged(this.f2078b, tXSAudioPacket3);
            } else if (TXEAudioTypeDef.f2326m == i5 && this.f2078b == null) {
                this.f2078b = new TXSAudioPacket();
                this.f2078b.sampleRate = nativeGetSamplerate(this.mJitterBuffer);
                this.f2078b.channelsPerSample = nativeGetChannel(this.mJitterBuffer);
                TXSAudioPacket tXSAudioPacket4 = this.f2078b;
                tXSAudioPacket4.bitsPerChannel = TXCAudioRecorder.f2044c;
                tXSAudioPacket4.packetType = TXEAudioTypeDef.f2324k;
                TXSAudioPacket tXSAudioPacket5 = new TXSAudioPacket();
                tXSAudioPacket5.sampleRate = TXRecordCommon.AUDIO_SAMPLERATE_48000;
                tXSAudioPacket5.channelsPerSample = 2;
                tXSAudioPacket5.bitsPerChannel = 16;
                tXSAudioPacket5.packetType = TXEAudioTypeDef.f2324k;
                onPlayAudioInfoChanged(this.f2078b, tXSAudioPacket5);
            }
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
        }
        onPlayError(TXEAudioDef.TXE_AUDIO_PLAY_ERR_NOT_CREATE_JIT, "jitterbuf 还未创建");
        TXCLog.m2914e(f2077a, "soft dec, jitterbuffer not created yet!!");
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_NOT_CREATE_JIT;
    }

    @Override // com.tencent.liteav.audio.impl.Play.TXCAudioBasePlayController, com.tencent.liteav.audio.TXIAudioPlayListener
    public void onPlayAudioInfoChanged(TXSAudioPacket tXSAudioPacket, TXSAudioPacket tXSAudioPacket2) {
        if (this.f2078b == null) {
            this.f2078b = tXSAudioPacket;
        }
        TXIAudioPlayListener tXIAudioPlayListener = this.mListener;
        if (tXIAudioPlayListener != null) {
            tXIAudioPlayListener.onPlayAudioInfoChanged(tXSAudioPacket, tXSAudioPacket2);
        }
        TXCTraeJNI.traeStartPlay(this.mContext);
    }
}
