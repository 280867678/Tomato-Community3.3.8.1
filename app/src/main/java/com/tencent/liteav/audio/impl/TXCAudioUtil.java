package com.tencent.liteav.audio.impl;

import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.impl.Play.TXCMultAudioTrackPlayer;
import com.tencent.liteav.audio.impl.Record.TXCAudioSysRecord;
import com.tencent.ugc.TXRecordCommon;

/* renamed from: com.tencent.liteav.audio.impl.b */
/* loaded from: classes3.dex */
public class TXCAudioUtil {

    /* renamed from: a */
    private static int[] f2152a = {96000, 88200, 64000, TXRecordCommon.AUDIO_SAMPLERATE_48000, TXRecordCommon.AUDIO_SAMPLERATE_44100, TXRecordCommon.AUDIO_SAMPLERATE_32000, 24000, 22050, TXRecordCommon.AUDIO_SAMPLERATE_16000, 12000, 11025, 8000, 7350};

    /* renamed from: a */
    public static String m3349a(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append("0x");
            sb.append(hexString);
        }
        return sb.toString();
    }

    /* renamed from: a */
    public static int m3350a(int i) {
        int[] iArr = f2152a;
        if (i >= iArr.length || i < 0) {
            return 0;
        }
        return iArr[i];
    }

    /* renamed from: b */
    public static int m3348b(int i) {
        if (i == TXEAudioDef.TXE_AEC_TRAE) {
            if (TXCMultAudioTrackPlayer.m3425a().m3417d()) {
                return TXEAudioDef.TXE_AUDIO_RECORD_ERR_CUR_PLAYER_INVALID;
            }
        } else if (TXCTraeJNI.nativeTraeIsPlaying()) {
            return TXEAudioDef.TXE_AUDIO_RECORD_ERR_CUR_PLAYER_INVALID;
        }
        return TXEAudioDef.TXE_AUDIO_COMMON_ERR_OK;
    }

    /* renamed from: c */
    public static int m3347c(int i) {
        if (i == TXEAudioDef.TXE_AEC_TRAE) {
            if (TXCAudioSysRecord.m3382a().m3376c()) {
                return TXEAudioDef.TXE_AUDIO_RECORD_ERR_CUR_RECORDER_INVALID;
            }
        } else if (TXCTraeJNI.nativeTraeIsRecording()) {
            return TXEAudioDef.TXE_AUDIO_RECORD_ERR_CUR_RECORDER_INVALID;
        }
        return TXEAudioDef.TXE_AUDIO_COMMON_ERR_OK;
    }
}
