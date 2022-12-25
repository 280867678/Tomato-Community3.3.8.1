package com.tencent.rtmp.sharp.jni;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;
import com.tencent.rtmp.sharp.jni.TraeAudioSession;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@TargetApi(16)
/* loaded from: classes3.dex */
public class AudioDeviceInterface {
    private static boolean _dumpEnable = false;
    private static boolean _logEnable = true;
    private ByteBuffer _playBuffer;
    private ByteBuffer _recBuffer;
    private byte[] _tempBufPlay;
    private byte[] _tempBufRec;
    private AudioTrack _audioTrack = null;
    private AudioRecord _audioRecord = null;
    private int _streamType = 0;
    private int _playSamplerate = 8000;
    private int _channelOutType = 4;
    private int _audioSource = 0;
    private int _sceneModeKey = 0;
    private int _sessionId = 0;
    private Context _context = null;
    private int _modePolicy = -1;
    private int _audioSourcePolicy = -1;
    private int _audioStreamTypePolicy = -1;
    private AudioManager _audioManager = null;
    private final ReentrantLock _playLock = new ReentrantLock();
    private final ReentrantLock _recLock = new ReentrantLock();
    private boolean _doPlayInit = true;
    private boolean _doRecInit = true;
    private boolean _isRecording = false;
    private boolean _isPlaying = false;
    private int _bufferedRecSamples = 0;
    private int _bufferedPlaySamples = 0;
    private int _playPosition = 0;
    private File _rec_dump = null;
    private File _play_dump = null;
    private FileOutputStream _rec_out = null;
    private FileOutputStream _play_out = null;
    private TraeAudioSession _as = null;
    private String _connectedDev = TraeAudioManager.DEVICE_NONE;
    private boolean _audioRouteChanged = false;
    private ReentrantLock _prelock = new ReentrantLock();
    private Condition _precon = this._prelock.newCondition();
    private boolean _preDone = false;
    private boolean usingJava = true;
    private int switchState = 0;
    private TraeAudioSession _init_as = null;

    @TargetApi(16)
    private int getAudioSessionId(AudioRecord audioRecord) {
        return 0;
    }

    public AudioDeviceInterface() {
        try {
            this._playBuffer = ByteBuffer.allocateDirect(1920);
            this._recBuffer = ByteBuffer.allocateDirect(1920);
        } catch (Exception e) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, e.getMessage());
            }
        }
        this._tempBufPlay = new byte[1920];
        this._tempBufRec = new byte[1920];
        int i = Build.VERSION.SDK_INT;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "AudioDeviceInterface apiLevel:" + i);
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, " SDK_INT:" + Build.VERSION.SDK_INT);
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "manufacture:" + Build.MANUFACTURER);
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "MODEL:" + Build.MODEL);
        }
    }

    public void setContext(Context context) {
        this._context = context;
    }

    private int getLowlatencySamplerate() {
        Context context = this._context;
        if (context == null || Build.VERSION.SDK_INT < 9) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "getLowlatencySamplerate err, _context:" + this._context + " api:" + Build.VERSION.SDK_INT);
            }
            return 0;
        }
        boolean hasSystemFeature = context.getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("LOW_LATENCY:");
            sb.append(hasSystemFeature ? "Y" : "N");
            QLog.m371w("TRAE", 2, sb.toString());
        }
        if (Build.VERSION.SDK_INT < 17) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "API Level too low not support PROPERTY_OUTPUT_SAMPLE_RATE");
            }
            return 0;
        }
        if (QLog.isColorLevel()) {
            QLog.m376e("TRAE", 2, "getLowlatencySamplerate not support right now!");
        }
        return 0;
    }

    private int getLowlatencyFramesPerBuffer() {
        Context context = this._context;
        if (context == null || Build.VERSION.SDK_INT < 9) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "getLowlatencySamplerate err, _context:" + this._context + " api:" + Build.VERSION.SDK_INT);
            }
            return 0;
        }
        boolean hasSystemFeature = context.getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("LOW_LATENCY:");
            sb.append(hasSystemFeature ? "Y" : "N");
            QLog.m371w("TRAE", 2, sb.toString());
        }
        if (Build.VERSION.SDK_INT < 17 && QLog.isColorLevel()) {
            QLog.m376e("TRAE", 2, "API Level too low not support PROPERTY_OUTPUT_SAMPLE_RATE");
        }
        return 0;
    }

    private int InitSetting(int i, int i2, int i3, int i4) {
        this._audioSourcePolicy = i;
        this._audioStreamTypePolicy = i2;
        this._modePolicy = i3;
        this._sceneModeKey = i4;
        int i5 = this._sceneModeKey;
        if (i5 == 1 || i5 == 2 || i5 == 3) {
            TraeAudioManager.IsMusicScene = true;
        } else {
            TraeAudioManager.IsMusicScene = false;
        }
        TraeAudioManager.IsUpdateSceneFlag = true;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "InitSetting: _audioSourcePolicy:" + this._audioSourcePolicy + " _audioStreamTypePolicy:" + this._audioStreamTypePolicy + " _modePolicy:" + this._modePolicy + " sceneModeKey:" + i4);
        }
        return 0;
    }

    private int InitRecording(int i, int i2) {
        int i3;
        int i4;
        int[] iArr;
        AudioRecord audioRecord;
        int i5;
        AudioRecord audioRecord2;
        int i6 = 2;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "InitRecording entry:" + i);
        }
        if (this._isRecording || this._audioRecord != null || i2 > 2) {
            if (!QLog.isColorLevel()) {
                return -1;
            }
            QLog.m376e("TRAE", 2, "InitRecording _isRecording:" + this._isRecording);
            return -1;
        }
        int i7 = i2 == 2 ? 12 : 16;
        int minBufferSize = AudioRecord.getMinBufferSize(i, i7, 2);
        int i8 = (((i * 20) * i2) * 2) / 1000;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "InitRecording: min rec buf size is " + minBufferSize + " sr:" + getLowlatencySamplerate() + " fp" + getLowlatencyFramesPerBuffer() + " 20msFZ:" + i8);
        }
        this._bufferedRecSamples = (i * 5) / 200;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "  rough rec delay set to " + this._bufferedRecSamples);
        }
        AudioRecord audioRecord3 = this._audioRecord;
        AudioRecord audioRecord4 = null;
        if (audioRecord3 != null) {
            audioRecord3.release();
            this._audioRecord = null;
        }
        int[] iArr2 = {0, 1, 5, 0};
        iArr2[0] = TraeAudioManager.getAudioSource(this._audioSourcePolicy);
        int i9 = minBufferSize;
        int i10 = 0;
        while (i10 < iArr2.length && this._audioRecord == null) {
            this._audioSource = iArr2[i10];
            int i11 = 1;
            while (true) {
                if (i11 > i6) {
                    i4 = i10;
                    iArr = iArr2;
                    audioRecord = audioRecord4;
                    break;
                }
                int i12 = minBufferSize * i11;
                if (i12 >= i8 * 4 || i11 >= i6) {
                    try {
                        i5 = i11;
                        i4 = i10;
                        iArr = iArr2;
                    } catch (Exception e) {
                        e = e;
                        i5 = i11;
                        i4 = i10;
                        iArr = iArr2;
                        audioRecord2 = audioRecord4;
                    }
                    try {
                        this._audioRecord = new AudioRecord(this._audioSource, i, i7, 2, i12);
                        if (this._audioRecord.getState() == 1) {
                            audioRecord = null;
                            i9 = i12;
                            break;
                        }
                        if (QLog.isColorLevel()) {
                            QLog.m371w("TRAE", 2, "InitRecording:  rec not initialized,try agine,  minbufsize:" + i12 + " sr:" + i + " as:" + this._audioSource);
                        }
                        this._audioRecord.release();
                        audioRecord2 = null;
                        this._audioRecord = null;
                    } catch (Exception e2) {
                        e = e2;
                        audioRecord2 = null;
                        if (QLog.isColorLevel()) {
                            QLog.m371w("TRAE", 2, e.getMessage() + " _audioRecord:" + this._audioRecord);
                        }
                        AudioRecord audioRecord5 = this._audioRecord;
                        if (audioRecord5 != null) {
                            audioRecord5.release();
                        }
                        this._audioRecord = audioRecord2;
                        audioRecord4 = audioRecord2;
                        i10 = i4;
                        iArr2 = iArr;
                        i6 = 2;
                        i11 = i5 + 1;
                        i9 = i12;
                    }
                } else {
                    i5 = i11;
                    i4 = i10;
                    iArr = iArr2;
                    audioRecord2 = audioRecord4;
                }
                audioRecord4 = audioRecord2;
                i10 = i4;
                iArr2 = iArr;
                i6 = 2;
                i11 = i5 + 1;
                i9 = i12;
            }
            i10 = i4 + 1;
            audioRecord4 = audioRecord;
            iArr2 = iArr;
            i6 = 2;
        }
        if (this._audioRecord == null) {
            if (!QLog.isColorLevel()) {
                return -1;
            }
            QLog.m371w("TRAE", 2, "InitRecording fail!!!");
            return -1;
        }
        if (QLog.isColorLevel()) {
            i3 = 2;
            QLog.m371w("TRAE", 2, " [Config] InitRecording: audioSession:" + this._sessionId + " audioSource:" + this._audioSource + " rec sample rate set to " + i + " recBufSize:" + i9);
        } else {
            i3 = 2;
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", i3, "InitRecording exit");
        }
        return this._bufferedRecSamples;
    }

    private int InitPlayback(int i, int i2) {
        AudioManager audioManager;
        int i3;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "InitPlayback entry: sampleRate " + i);
        }
        if (this._isPlaying || this._audioTrack != null || i2 > 2) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "InitPlayback _isPlaying:" + this._isPlaying);
            }
            return -1;
        }
        if (this._audioManager == null) {
            try {
                this._audioManager = (AudioManager) this._context.getSystemService("audio");
            } catch (Exception e) {
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, e.getMessage());
                }
                return -1;
            }
        }
        if (i2 == 2) {
            this._channelOutType = 12;
        } else {
            this._channelOutType = 4;
        }
        this._playSamplerate = i;
        int minBufferSize = AudioTrack.getMinBufferSize(i, this._channelOutType, 2);
        if (this._channelOutType == 12) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "InitPlayback, _channelOutType stero");
            } else if (this._channelOutType == 4 && QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "InitPlayback, _channelOutType Mono");
            }
        }
        int i4 = (((i * 20) * 1) * 2) / 1000;
        if (this._channelOutType == 12) {
            i4 *= 2;
        }
        int i5 = i4;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "InitPlayback: minPlayBufSize:" + minBufferSize + " 20msFz:" + i5);
        }
        this._bufferedPlaySamples = 0;
        AudioTrack audioTrack = this._audioTrack;
        if (audioTrack != null) {
            audioTrack.release();
            this._audioTrack = null;
        }
        int[] iArr = {0, 0, 3, 1};
        this._streamType = TraeAudioManager.getAudioStreamType(this._audioStreamTypePolicy);
        if (this._audioRouteChanged) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "_audioRouteChanged:" + this._audioRouteChanged + " _streamType:" + this._streamType);
            }
            if (this._audioManager.getMode() == 0 && this._connectedDev.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                this._streamType = 3;
            } else {
                this._streamType = 0;
            }
            this._audioRouteChanged = false;
        }
        iArr[0] = this._streamType;
        int i6 = minBufferSize;
        for (int i7 = 0; i7 < iArr.length && this._audioTrack == null; i7++) {
            this._streamType = iArr[i7];
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "InitPlayback: min play buf size is " + minBufferSize + " hw_sr:" + AudioTrack.getNativeOutputSampleRate(this._streamType));
            }
            int i8 = 1;
            while (true) {
                if (i8 > 2) {
                    break;
                }
                int i9 = minBufferSize * i8;
                if (i9 >= i5 * 4 || i8 >= 2) {
                    try {
                    } catch (Exception e2) {
                        e = e2;
                        i3 = i9;
                    }
                    try {
                        this._audioTrack = new AudioTrack(this._streamType, this._playSamplerate, this._channelOutType, 2, i9, 1);
                        if (this._audioTrack.getState() == 1) {
                            i6 = i9;
                            break;
                        }
                        if (QLog.isColorLevel()) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("InitPlayback: play not initialized playBufSize:");
                            i3 = i9;
                            sb.append(i3);
                            sb.append(" sr:");
                            sb.append(this._playSamplerate);
                            QLog.m371w("TRAE", 2, sb.toString());
                        } else {
                            i3 = i9;
                        }
                        this._audioTrack.release();
                        this._audioTrack = null;
                    } catch (Exception e3) {
                        e = e3;
                        i3 = i9;
                        if (QLog.isColorLevel()) {
                            QLog.m371w("TRAE", 2, e.getMessage() + " _audioTrack:" + this._audioTrack);
                        }
                        AudioTrack audioTrack2 = this._audioTrack;
                        if (audioTrack2 != null) {
                            audioTrack2.release();
                        }
                        this._audioTrack = null;
                        i8++;
                        i6 = i3;
                    }
                } else {
                    i3 = i9;
                }
                i8++;
                i6 = i3;
            }
        }
        if (this._audioTrack == null) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "InitPlayback fail!!!");
            }
            return -1;
        }
        TraeAudioSession traeAudioSession = this._as;
        if (traeAudioSession != null && (audioManager = this._audioManager) != null) {
            traeAudioSession.voiceCallAudioParamChanged(audioManager.getMode(), this._streamType);
        }
        this._playPosition = this._audioTrack.getPlaybackHeadPosition();
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "InitPlayback exit: streamType:" + this._streamType + " samplerate:" + this._playSamplerate + " _playPosition:" + this._playPosition + " playBufSize:" + i6);
        }
        TraeAudioManager.forceVolumeControlStream(this._audioManager, this._connectedDev.equals(TraeAudioManager.DEVICE_BLUETOOTHHEADSET) ? 6 : this._audioTrack.getStreamType());
        return 0;
    }

    private String getDumpFilePath(String str, int i) {
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "manufacture:" + Build.MANUFACTURER);
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "MODEL:" + Build.MODEL);
        }
        String str2 = Environment.getExternalStorageDirectory().getPath() + "/MF-" + Build.MANUFACTURER + "-M-" + Build.MODEL + "-as-" + TraeAudioManager.getAudioSource(this._audioSourcePolicy) + "-st-" + this._streamType + "-m-" + i + "-" + str;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "dump:" + str2);
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "dump replace:" + str2.replace(ConstantUtils.PLACEHOLDER_STR_ONE, "_"));
        }
        return str2.replace(ConstantUtils.PLACEHOLDER_STR_ONE, "_");
    }

    private int StartRecording() {
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "StartRecording entry");
        }
        int i = -1;
        if (this._isRecording) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "StartRecording _isRecording:" + this._isRecording);
            }
            return -1;
        }
        AudioRecord audioRecord = this._audioRecord;
        if (audioRecord == null) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "StartRecording _audioRecord:" + this._audioRecord);
            }
            return -1;
        }
        try {
            audioRecord.startRecording();
            if (_dumpEnable) {
                AudioManager audioManager = this._audioManager;
                if (audioManager != null) {
                    i = audioManager.getMode();
                }
                this._rec_dump = new File(getDumpFilePath("jnirecord.pcm", i));
                try {
                    this._rec_out = new FileOutputStream(this._rec_dump);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            this._isRecording = true;
            if (!QLog.isColorLevel()) {
                return 0;
            }
            QLog.m371w("TRAE", 2, "StartRecording ok");
            return 0;
        } catch (IllegalStateException e2) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "StartRecording fail");
            }
            e2.printStackTrace();
            return -1;
        }
    }

    private int StartPlayback() {
        int i = -1;
        if (this._isPlaying) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "StartPlayback _isPlaying");
            }
            return -1;
        }
        AudioTrack audioTrack = this._audioTrack;
        if (audioTrack == null) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "StartPlayback _audioTrack:" + this._audioTrack);
            }
            return -1;
        }
        try {
            audioTrack.play();
            if (_dumpEnable) {
                AudioManager audioManager = this._audioManager;
                if (audioManager != null) {
                    i = audioManager.getMode();
                }
                this._play_dump = new File(getDumpFilePath("jniplay.pcm", i));
                try {
                    this._play_out = new FileOutputStream(this._play_dump);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            this._isPlaying = true;
            if (!QLog.isColorLevel()) {
                return 0;
            }
            QLog.m371w("TRAE", 2, "StartPlayback ok");
            return 0;
        } catch (IllegalStateException e2) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "StartPlayback fail");
            }
            e2.printStackTrace();
            return -1;
        }
    }

    private int StopRecording() {
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "StopRecording entry");
        }
        if (this._audioRecord == null) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "UnintRecord:" + this._audioRecord);
            }
            return -1;
        }
        this._recLock.lock();
        try {
            if (this._audioRecord.getRecordingState() == 3) {
                try {
                    if (QLog.isColorLevel()) {
                        QLog.m371w("TRAE", 2, "StopRecording stop... state:" + this._audioRecord.getRecordingState());
                    }
                    this._audioRecord.stop();
                } catch (IllegalStateException e) {
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "StopRecording  err");
                    }
                    e.printStackTrace();
                    this._recLock.unlock();
                    return -1;
                }
            }
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "StopRecording releaseing... state:" + this._audioRecord.getRecordingState());
            }
            this._audioRecord.release();
            this._audioRecord = null;
            this._isRecording = false;
            this._recLock.unlock();
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "StopRecording exit ok");
            }
            return 0;
        } catch (Throwable th) {
            this._recLock.unlock();
            throw th;
        }
    }

    private int StopPlayback() {
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "StopPlayback entry _isPlaying:" + this._isPlaying);
        }
        if (this._audioTrack == null) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "StopPlayback _isPlaying:" + this._isPlaying + ConstantUtils.PLACEHOLDER_STR_ONE + this._audioTrack);
            }
            return -1;
        }
        this._playLock.lock();
        try {
            if (this._audioTrack.getPlayState() == 3) {
                try {
                    if (QLog.isColorLevel()) {
                        QLog.m371w("TRAE", 2, "StopPlayback stoping...");
                    }
                    this._audioTrack.stop();
                    if (QLog.isColorLevel()) {
                        QLog.m371w("TRAE", 2, "StopPlayback flushing... state:" + this._audioTrack.getPlayState());
                    }
                    this._audioTrack.flush();
                } catch (IllegalStateException e) {
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "StopPlayback err");
                    }
                    e.printStackTrace();
                    this._playLock.unlock();
                    return -1;
                }
            }
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "StopPlayback releaseing... state:" + this._audioTrack.getPlayState());
            }
            this._audioTrack.release();
            this._audioTrack = null;
            this._isPlaying = false;
            this._playLock.unlock();
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "StopPlayback exit ok");
            }
            return 0;
        } catch (Throwable th) {
            this._playLock.unlock();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x039d A[Catch: all -> 0x0393, TRY_LEAVE, TryCatch #3 {all -> 0x0393, blocks: (B:13:0x0049, B:17:0x0054, B:21:0x005a, B:22:0x007f, B:26:0x0060, B:28:0x0066, B:29:0x0081, B:31:0x0085, B:34:0x0089, B:37:0x0092, B:38:0x0095, B:41:0x00d8, B:71:0x00e1, B:73:0x00f0, B:74:0x0116, B:142:0x011f, B:144:0x0125, B:145:0x012b, B:147:0x013b, B:148:0x0156, B:150:0x0161, B:151:0x017c, B:77:0x018b, B:79:0x01b2, B:81:0x01b5, B:83:0x01b8, B:85:0x01bc, B:87:0x01c6, B:95:0x029b, B:97:0x01f9, B:98:0x0214, B:100:0x021a, B:101:0x022f, B:103:0x0238, B:105:0x023e, B:106:0x025f, B:111:0x02a5, B:114:0x026c, B:116:0x0273, B:117:0x028f, B:119:0x0293, B:120:0x0298, B:126:0x02ad, B:133:0x02b1, B:136:0x02d8, B:138:0x02d2, B:128:0x02db, B:130:0x02e1, B:153:0x017f, B:155:0x0185, B:43:0x0307, B:45:0x030f, B:48:0x031c, B:50:0x0322, B:54:0x0349, B:56:0x034f, B:57:0x036e, B:59:0x037f, B:60:0x0381, B:66:0x0397, B:68:0x039d, B:157:0x009c, B:159:0x00a0, B:161:0x00a4, B:162:0x00b1, B:164:0x00b9, B:166:0x00c4, B:167:0x00c9, B:170:0x00d6, B:172:0x00c7), top: B:12:0x0049 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int PlayAudio(int i) {
        boolean z;
        Object obj;
        Object obj2;
        int i2 = i;
        int i3 = 0;
        int i4 = 2;
        if ((!this._isPlaying) | (this._audioTrack == null)) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "PlayAudio: _isPlaying " + this._isPlaying + ConstantUtils.PLACEHOLDER_STR_ONE + this._audioTrack);
            }
            return -1;
        }
        this._playLock.lock();
        try {
            try {
            } finally {
                this._playLock.unlock();
            }
        } catch (Exception e) {
            e = e;
            i2 = 0;
        }
        if (this._audioTrack != null) {
            if (this._doPlayInit) {
                try {
                    Process.setThreadPriority(-19);
                } catch (Exception e2) {
                    if (QLog.isColorLevel()) {
                        QLog.m371w("TRAE", 2, "Set play thread priority failed: " + e2.getMessage());
                    }
                }
                this._doPlayInit = false;
            }
            if (_dumpEnable && this._play_out != null) {
                try {
                    this._play_out.write(this._tempBufPlay, 0, 0);
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            if (!this._audioRouteChanged) {
                z = false;
            } else {
                if (this._audioManager == null && this._context != null) {
                    this._audioManager = (AudioManager) this._context.getSystemService("audio");
                }
                if (this._audioManager.getMode() == 0 && this._connectedDev.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                    this._streamType = 3;
                } else {
                    this._streamType = 0;
                }
                z = this._streamType != this._audioTrack.getStreamType();
                this._audioRouteChanged = false;
            }
            this._playBuffer.get(this._tempBufPlay);
            if (z) {
                try {
                    this._playBuffer.rewind();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    if (QLog.isColorLevel()) {
                        QLog.m371w("TRAE", 2, " track resting: _streamType:" + this._streamType + " at.st:" + this._audioTrack.getStreamType());
                    }
                    Object obj3 = null;
                    if (this._audioTrack.getPlayState() == 3) {
                        try {
                            if (QLog.isColorLevel()) {
                                QLog.m371w("TRAE", 2, "StopPlayback stoping...");
                            }
                            this._audioTrack.stop();
                            this._audioTrack.flush();
                            if (QLog.isColorLevel()) {
                                QLog.m371w("TRAE", 2, "StopPlayback flushing... state:" + this._audioTrack.getPlayState());
                            }
                            this._audioTrack.release();
                            if (QLog.isColorLevel()) {
                                QLog.m371w("TRAE", 2, "StopPlayback releaseing... state:" + this._audioTrack.getPlayState());
                            }
                            this._audioTrack = null;
                        } catch (IllegalStateException unused) {
                            if (QLog.isColorLevel()) {
                                QLog.m376e("TRAE", 2, "StopPlayback err");
                            }
                        }
                    }
                    int minBufferSize = AudioTrack.getMinBufferSize(this._playSamplerate, this._channelOutType, 2);
                    int[] iArr = {0, 0, 3, 1};
                    iArr[0] = this._streamType;
                    int i5 = (((this._playSamplerate * 20) * 1) * 2) / 1000;
                    if (this._channelOutType == 12) {
                        i5 *= 2;
                    }
                    int i6 = i5;
                    while (i3 < iArr.length && this._audioTrack == null) {
                        this._streamType = iArr[i3];
                        if (QLog.isColorLevel()) {
                            QLog.m371w("TRAE", i4, "InitPlayback: min play buf size is " + minBufferSize + " hw_sr:" + AudioTrack.getNativeOutputSampleRate(this._streamType));
                        }
                        int i7 = 1;
                        while (true) {
                            if (i7 > i4) {
                                obj = obj3;
                                break;
                            }
                            int i8 = minBufferSize * i7;
                            if (i8 >= i6 * 4 || i7 >= i4) {
                                try {
                                    this._audioTrack = new AudioTrack(this._streamType, this._playSamplerate, this._channelOutType, 2, i8, 1);
                                    if (QLog.isColorLevel()) {
                                        QLog.m371w("TRAE", 2, " _audioTrack:" + this._audioTrack);
                                    }
                                    if (this._audioTrack.getState() == 1) {
                                        obj = null;
                                        break;
                                    }
                                    if (QLog.isColorLevel()) {
                                        QLog.m371w("TRAE", 2, "InitPlayback: play not initialized playBufSize:" + i8 + " sr:" + this._playSamplerate);
                                    }
                                    this._audioTrack.release();
                                    this._audioTrack = null;
                                    obj2 = null;
                                } catch (Exception e4) {
                                    if (QLog.isColorLevel()) {
                                        QLog.m371w("TRAE", 2, e4.getMessage() + " _audioTrack:" + this._audioTrack);
                                    }
                                    if (this._audioTrack != null) {
                                        this._audioTrack.release();
                                    }
                                    obj2 = null;
                                    this._audioTrack = null;
                                }
                            } else {
                                obj2 = null;
                            }
                            i7++;
                            obj3 = obj2;
                            i4 = 2;
                        }
                        i3++;
                        obj3 = obj;
                        i4 = 2;
                    }
                    if (this._audioTrack != null) {
                        try {
                            this._audioTrack.play();
                            this._as.voiceCallAudioParamChanged(this._audioManager.getMode(), this._streamType);
                            TraeAudioManager.forceVolumeControlStream(this._audioManager, this._connectedDev.equals(TraeAudioManager.DEVICE_BLUETOOTHHEADSET) ? 6 : this._audioTrack.getStreamType());
                        } catch (Exception unused2) {
                        }
                    }
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "  track reset used:" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms");
                    }
                } catch (Exception e5) {
                    e = e5;
                    if (QLog.isColorLevel()) {
                    }
                    return i2;
                }
            } else {
                int write = this._audioTrack.write(this._tempBufPlay, 0, i2);
                try {
                    this._playBuffer.rewind();
                    if (write < 0) {
                        if (QLog.isColorLevel()) {
                            QLog.m376e("TRAE", 2, "Could not write data from sc (write = " + write + ", length = " + i2 + ")");
                        }
                        return -1;
                    }
                    if (write != i2 && QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "Could not write all data from sc (write = " + write + ", length = " + i2 + ")");
                    }
                    this._bufferedPlaySamples += write >> 1;
                    int playbackHeadPosition = this._audioTrack.getPlaybackHeadPosition();
                    if (playbackHeadPosition < this._playPosition) {
                        this._playPosition = 0;
                    }
                    this._bufferedPlaySamples -= playbackHeadPosition - this._playPosition;
                    this._playPosition = playbackHeadPosition;
                    boolean z2 = this._isRecording;
                    i2 = write;
                } catch (Exception e6) {
                    e = e6;
                    i2 = write;
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "PlayAudio Exception: " + e.getMessage());
                    }
                    return i2;
                }
            }
            return i2;
        }
        return -2;
    }

    private int OpenslesNeedResetAudioTrack(boolean z) {
        try {
        } catch (Exception e) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "PlayAudio Exception: " + e.getMessage());
            }
        }
        if (!TraeAudioManager.isCloseSystemAPM(this._modePolicy)) {
            return -1;
        }
        if (this._audioRouteChanged || z) {
            if (this._audioManager == null && this._context != null) {
                this._audioManager = (AudioManager) this._context.getSystemService("audio");
            }
            if (this._audioManager == null) {
                return 0;
            }
            if (this._audioManager.getMode() == 0 && this._connectedDev.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                this._audioStreamTypePolicy = 3;
            } else {
                this._audioStreamTypePolicy = 0;
            }
            this._audioRouteChanged = false;
        }
        return this._audioStreamTypePolicy;
    }

    private int RecordAudio(int i) {
        int i2;
        if (!this._isRecording) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "RecordAudio: _isRecording " + this._isRecording);
            }
            return -1;
        }
        this._recLock.lock();
        try {
            try {
            } catch (Exception e) {
                e = e;
                i2 = 0;
            }
            if (this._audioRecord != null) {
                if (this._doRecInit) {
                    try {
                        Process.setThreadPriority(-19);
                    } catch (Exception e2) {
                        if (QLog.isColorLevel()) {
                            QLog.m371w("TRAE", 2, "Set rec thread priority failed: " + e2.getMessage());
                        }
                    }
                    this._doRecInit = false;
                }
                this._recBuffer.rewind();
                i2 = this._audioRecord.read(this._tempBufRec, 0, i);
                try {
                } catch (Exception e3) {
                    e = e3;
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "RecordAudio Exception: " + e.getMessage());
                    }
                    return i2;
                }
                if (i2 < 0) {
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "Could not read data from sc (read = " + i2 + ", length = " + i + ")");
                    }
                } else {
                    this._recBuffer.put(this._tempBufRec, 0, i2);
                    if (_dumpEnable && this._rec_out != null) {
                        try {
                            this._rec_out.write(this._tempBufRec, 0, i2);
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (i2 != i) {
                        if (QLog.isColorLevel()) {
                            QLog.m376e("TRAE", 2, "Could not read all data from sc (read = " + i2 + ", length = " + i + ")");
                        }
                    }
                    return i2;
                }
                return -1;
            }
            return -2;
        } finally {
            this._recLock.unlock();
        }
    }

    private int SetPlayoutVolume(int i) {
        Context context;
        if (this._audioManager == null && (context = this._context) != null) {
            this._audioManager = (AudioManager) context.getSystemService("audio");
        }
        AudioManager audioManager = this._audioManager;
        if (audioManager != null) {
            audioManager.setStreamVolume(0, i, 0);
            return 0;
        }
        return -1;
    }

    private int GetPlayoutVolume() {
        Context context;
        if (this._audioManager == null && (context = this._context) != null) {
            this._audioManager = (AudioManager) context.getSystemService("audio");
        }
        AudioManager audioManager = this._audioManager;
        if (audioManager != null) {
            return audioManager.getStreamVolume(0);
        }
        return -1;
    }

    public static String getTraceInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int length = stackTrace.length;
        stringBuffer.append("");
        stringBuffer.append(stackTrace[2].getClassName());
        stringBuffer.append(".");
        stringBuffer.append(stackTrace[2].getMethodName());
        stringBuffer.append(": ");
        stringBuffer.append(stackTrace[2].getLineNumber());
        return stringBuffer.toString();
    }

    public static final void LogTraceEntry(String str) {
        if (!_logEnable) {
            return;
        }
        String str2 = getTraceInfo() + " entry:" + str;
        if (!QLog.isColorLevel()) {
            return;
        }
        QLog.m371w("TRAE", 2, str2);
    }

    public static final void LogTraceExit() {
        if (!_logEnable) {
            return;
        }
        String str = getTraceInfo() + " exit";
        if (!QLog.isColorLevel()) {
            return;
        }
        QLog.m371w("TRAE", 2, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onOutputChanage(String str) {
        int i;
        String str2;
        String str3;
        String str4;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, " onOutputChanage:" + str);
        }
        setAudioRouteSwitchState(str);
        if (!TraeAudioManager.isCloseSystemAPM(this._modePolicy) || (i = this._sceneModeKey) == 1 || i == 2 || i == 3) {
            return;
        }
        this._connectedDev = str;
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append(" onOutputChanage:");
            sb.append(str);
            if (this._audioManager == null) {
                str3 = " am==null";
            } else {
                str3 = " mode:" + this._audioManager.getMode();
            }
            sb.append(str3);
            sb.append(" st:");
            sb.append(this._streamType);
            if (this._audioTrack == null) {
                str4 = "_audioTrack==null";
            } else {
                str4 = " at.st:" + this._audioTrack.getStreamType();
            }
            sb.append(str4);
            QLog.m371w("TRAE", 2, sb.toString());
        }
        try {
            if (this._audioManager == null) {
                this._audioManager = (AudioManager) this._context.getSystemService("audio");
            }
            if (QLog.isColorLevel()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(" curr mode:");
                sb2.append(str);
                if (this._audioManager == null) {
                    str2 = "am==null";
                } else {
                    str2 = " mode:" + this._audioManager.getMode();
                }
                sb2.append(str2);
                QLog.m371w("TRAE", 2, sb2.toString());
            }
            if (this._audioManager != null && this._connectedDev.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                this._audioManager.setMode(0);
            }
        } catch (Exception e) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, e.getMessage());
            }
        }
        this._audioRouteChanged = true;
    }

    public int call_preprocess() {
        LogTraceEntry("");
        this.switchState = 0;
        this._streamType = TraeAudioManager.getAudioStreamType(this._audioStreamTypePolicy);
        if (this._as == null) {
            QLog.m376e("TRAE", 2, "TraeAudioSession-LeakCheck init: call_preprocess");
        }
        this._as = new TraeAudioSession(this._context, new TraeAudioSession.AbstractC3731a() { // from class: com.tencent.rtmp.sharp.jni.AudioDeviceInterface.1
            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo300a(int i) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo299a(int i, int i2) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo297a(int i, String str, boolean z) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo296a(int i, boolean z) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo295a(int i, String[] strArr, String str, String str2, String str3) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo294a(String str, long j) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo293a(String str, String str2) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: b */
            public void mo289b(int i, String str) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: b */
            public void mo288b(boolean z) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: c */
            public void mo287c(int i, String str) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo292a(boolean z) {
                if (!z) {
                    try {
                        AudioDeviceInterface.this._prelock.lock();
                        AudioDeviceInterface.this._preDone = true;
                        if (QLog.isColorLevel()) {
                            QLog.m376e("TRAE", 2, "onVoicecallPreprocessRes signalAll");
                        }
                        AudioDeviceInterface.this._precon.signalAll();
                        AudioDeviceInterface.this._prelock.unlock();
                    } catch (Exception unused) {
                    }
                }
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo291a(String[] strArr, String str, String str2, String str3) {
                if (AudioDeviceInterface.this.usingJava) {
                    AudioDeviceInterface.this.onOutputChanage(str);
                }
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo298a(int i, String str) {
                if (i == 0) {
                    AudioDeviceInterface.this.onOutputChanage(str);
                }
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: b */
            public void mo290b(int i) {
                try {
                    AudioDeviceInterface.this._prelock.lock();
                    AudioDeviceInterface.this._preDone = true;
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "onVoicecallPreprocessRes signalAll");
                    }
                    AudioDeviceInterface.this._precon.signalAll();
                    AudioDeviceInterface.this._prelock.unlock();
                } catch (Exception unused) {
                }
            }
        });
        this._preDone = false;
        if (this._as != null) {
            this._prelock.lock();
            try {
                this._as.voiceCallPreprocess(this._modePolicy, this._streamType);
                this._as.connectHighestPriorityDevice();
                int i = 7;
                while (true) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    }
                    try {
                        if (this._preDone) {
                            break;
                        }
                        this._precon.await(1L, TimeUnit.SECONDS);
                        if (QLog.isColorLevel()) {
                            QLog.m376e("TRAE", 2, "call_preprocess waiting...  as:" + this._as);
                        }
                        i = i2;
                    } catch (InterruptedException unused) {
                    }
                }
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "call_preprocess done!");
                }
                this._as.getConnectedDevice();
            } finally {
                this._prelock.unlock();
            }
        }
        LogTraceExit();
        return 0;
    }

    public int call_postprocess() {
        LogTraceEntry("");
        this.switchState = 0;
        TraeAudioSession traeAudioSession = this._as;
        if (traeAudioSession != null) {
            traeAudioSession.voiceCallPostprocess();
            this._as.release();
            this._as = null;
            QLog.m376e("TRAE", 2, "TraeAudioSession-LeakCheck release: call_postprocess");
        }
        TraeAudioManager.IsUpdateSceneFlag = false;
        LogTraceExit();
        return 0;
    }

    public int call_preprocess_media() {
        LogTraceEntry("");
        this.switchState = 0;
        if (this._as == null) {
            QLog.m376e("TRAE", 2, "TraeAudioSession-LeakCheck init: call_preprocess_media");
        }
        this._as = new TraeAudioSession(this._context, new TraeAudioSession.AbstractC3731a() { // from class: com.tencent.rtmp.sharp.jni.AudioDeviceInterface.2
            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo300a(int i) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo299a(int i, int i2) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo297a(int i, String str, boolean z) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo296a(int i, boolean z) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo295a(int i, String[] strArr, String str, String str2, String str3) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo294a(String str, long j) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo293a(String str, String str2) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo292a(boolean z) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: b */
            public void mo290b(int i) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: b */
            public void mo289b(int i, String str) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: b */
            public void mo288b(boolean z) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: c */
            public void mo287c(int i, String str) {
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo291a(String[] strArr, String str, String str2, String str3) {
                if (AudioDeviceInterface.this.usingJava) {
                    AudioDeviceInterface.this.onOutputChanage(str);
                }
            }

            @Override // com.tencent.rtmp.sharp.jni.TraeAudioSession.AbstractC3731a
            /* renamed from: a */
            public void mo298a(int i, String str) {
                if (i == 0) {
                    AudioDeviceInterface.this.onOutputChanage(str);
                }
            }
        });
        if (this._as != null) {
            try {
                if (this._audioManager == null) {
                    this._audioManager = (AudioManager) this._context.getSystemService("audio");
                }
                if (this._audioManager != null) {
                    if (this._audioManager.getMode() == 2) {
                        int i = 5;
                        while (true) {
                            int i2 = i - 1;
                            if (i <= 0 || this._audioManager.getMode() != 2) {
                                break;
                            }
                            if (QLog.isColorLevel()) {
                                QLog.m376e("TRAE", 2, "media call_preprocess waiting...  mode:" + this._audioManager.getMode());
                            }
                            Thread.sleep(500L);
                            i = i2;
                        }
                    }
                    if (this._audioManager.getMode() != 0) {
                        this._audioManager.setMode(0);
                    }
                }
                this._as.connectHighestPriorityDevice();
                this._as.getConnectedDevice();
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "call_preprocess done!");
                }
            } catch (InterruptedException unused) {
            }
        }
        LogTraceExit();
        return 0;
    }

    public int call_postprocess_media() {
        LogTraceEntry("");
        this.switchState = 0;
        TraeAudioSession traeAudioSession = this._as;
        if (traeAudioSession != null) {
            traeAudioSession.release();
            this._as = null;
            QLog.m376e("TRAE", 2, "TraeAudioSession-LeakCheck release: call_postprocess_media");
        }
        TraeAudioManager.IsUpdateSceneFlag = false;
        LogTraceExit();
        return 0;
    }

    public void setJavaInterface(int i) {
        if (i == 0) {
            this.usingJava = false;
        } else {
            this.usingJava = true;
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "setJavaInterface flg:" + i);
        }
    }

    private void setAudioRouteSwitchState(String str) {
        if (str.equals(TraeAudioManager.DEVICE_EARPHONE)) {
            this.switchState = 1;
        } else if (str.equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
            this.switchState = 2;
        } else if (str.equals(TraeAudioManager.DEVICE_WIREDHEADSET)) {
            this.switchState = 3;
        } else if (str.equals(TraeAudioManager.DEVICE_BLUETOOTHHEADSET)) {
            this.switchState = 4;
        } else {
            this.switchState = 0;
        }
    }

    public int getAudioRouteSwitchState() {
        return this.switchState;
    }

    private void initTRAEAudioManager() {
        Context context = this._context;
        if (context != null) {
            TraeAudioManager.init(context);
            if (this._init_as == null) {
                QLog.m376e("TRAE", 2, "TraeAudioSession-LeakCheck init: initTRAEAudioManager");
                this._init_as = new TraeAudioSession(this._context, null);
            }
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "initTRAEAudioManager , TraeAudioSession startService");
            }
            this._init_as.startService(TraeAudioManager.MUSIC_CONFIG);
        }
    }

    private int getAndroidSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    private void uninitTRAEAudioManager() {
        if (this._context != null) {
            if (this._init_as != null) {
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "uninitTRAEAudioManager , stopService");
                }
                this._init_as.stopService();
                this._init_as.release();
                this._init_as = null;
                QLog.m376e("TRAE", 2, "TraeAudioSession-LeakCheck release: uninitTRAEAudioManager");
            }
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "uninitTRAEAudioManager , stopService");
            }
            TraeAudioManager.uninit();
        } else if (!QLog.isColorLevel()) {
        } else {
            QLog.m371w("TRAE", 2, "uninitTRAEAudioManager , context null");
        }
    }
}
