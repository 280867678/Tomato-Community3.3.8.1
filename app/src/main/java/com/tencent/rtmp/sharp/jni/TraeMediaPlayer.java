package com.tencent.rtmp.sharp.jni;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes3.dex */
public class TraeMediaPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    public static final int TRAE_MEDIAPLAER_DATASOURCE_FILEPATH = 2;
    public static final int TRAE_MEDIAPLAER_DATASOURCE_RSID = 0;
    public static final int TRAE_MEDIAPLAER_DATASOURCE_URI = 1;
    public static final int TRAE_MEDIAPLAER_STOP = 100;
    private Context _context;
    private AbstractC3734a mCallback;
    private MediaPlayer mMediaPlay = null;
    private int _streamType = 0;
    private boolean _hasCall = false;
    private boolean _loop = false;
    private int _durationMS = -1;
    int _loopCount = 0;
    boolean _ringMode = false;
    private Timer _watchTimer = null;
    private TimerTask _watchTimertask = null;
    private int _prevVolume = -1;

    /* renamed from: com.tencent.rtmp.sharp.jni.TraeMediaPlayer$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3734a {
        /* renamed from: a */
        void mo286a();
    }

    public TraeMediaPlayer(Context context, AbstractC3734a abstractC3734a) {
        this._context = context;
        this.mCallback = abstractC3734a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:128:0x037f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean playRing(int i, int i2, Uri uri, String str, boolean z, int i3, boolean z2, boolean z3, int i4) {
        String str2;
        IllegalStateException illegalStateException;
        String str3;
        IllegalArgumentException illegalArgumentException;
        String str4;
        IOException iOException;
        Exception exc;
        String str5;
        int i5;
        Uri uri2 = uri;
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("TraeMediaPlay | playRing datasource:");
            sb.append(i);
            sb.append(" rsid:");
            sb.append(i2);
            sb.append(" uri:");
            sb.append(uri2);
            sb.append(" filepath:");
            sb.append(str);
            sb.append(" loop:");
            sb.append(z ? "Y" : "N");
            sb.append(" :loopCount");
            sb.append(i3);
            sb.append(" ringMode:");
            sb.append(z2 ? "Y" : "N");
            sb.append(" hasCall:");
            sb.append(z3);
            sb.append(" cst:");
            sb.append(i4);
            QLog.m376e("TRAE", 2, sb.toString());
        }
        if (!z && i3 <= 0) {
            if (QLog.isColorLevel()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("TraeMediaPlay | playRing err datasource:");
                sb2.append(i);
                sb2.append(" loop:");
                sb2.append(z ? "Y" : "N");
                sb2.append(" :loopCount");
                sb2.append(i3);
                QLog.m376e("TRAE", 2, sb2.toString());
            }
            return false;
        }
        try {
            try {
                try {
                    if (this.mMediaPlay != null) {
                        try {
                            if (this.mMediaPlay.isPlaying()) {
                                return false;
                            }
                            try {
                                this.mMediaPlay.release();
                            } catch (Exception unused) {
                            } catch (Throwable th) {
                                this.mMediaPlay = null;
                                throw th;
                            }
                            this.mMediaPlay = null;
                        } catch (IOException e) {
                            iOException = e;
                            str4 = ConstantUtils.PLACEHOLDER_STR_ONE;
                            if (QLog.isColorLevel()) {
                                QLog.m378d("TRAE", 2, "TraeMediaPlay | IOException: " + iOException.getLocalizedMessage() + str4 + iOException.getMessage());
                            }
                            try {
                                this.mMediaPlay.release();
                            } catch (Exception unused2) {
                            }
                            this.mMediaPlay = null;
                            return false;
                        } catch (IllegalArgumentException e2) {
                            illegalArgumentException = e2;
                            str3 = ConstantUtils.PLACEHOLDER_STR_ONE;
                            if (QLog.isColorLevel()) {
                                QLog.m378d("TRAE", 2, "TraeMediaPlay | IllegalArgumentException: " + illegalArgumentException.getLocalizedMessage() + str3 + illegalArgumentException.getMessage());
                            }
                            this.mMediaPlay.release();
                            this.mMediaPlay = null;
                            return false;
                        } catch (IllegalStateException e3) {
                            illegalStateException = e3;
                            str2 = ConstantUtils.PLACEHOLDER_STR_ONE;
                            if (QLog.isColorLevel()) {
                                QLog.m378d("TRAE", 2, "TraeMediaPlay | IllegalStateException: " + illegalStateException.getLocalizedMessage() + str2 + illegalStateException.getMessage());
                            }
                            this.mMediaPlay.release();
                            this.mMediaPlay = null;
                            return false;
                        }
                    }
                    if (this._watchTimer != null) {
                        this._watchTimer.cancel();
                        this._watchTimer = null;
                        this._watchTimertask = null;
                    }
                    AudioManager audioManager = (AudioManager) this._context.getSystemService("audio");
                    this.mMediaPlay = new MediaPlayer();
                    if (this.mMediaPlay == null) {
                        this.mMediaPlay.release();
                        this.mMediaPlay = null;
                        return false;
                    }
                    this.mMediaPlay.setOnCompletionListener(this);
                    this.mMediaPlay.setOnErrorListener(this);
                    if (i == 0) {
                        if (QLog.isColorLevel()) {
                            QLog.m376e("TRAE", 2, "TraeMediaPlay | rsid:" + i2);
                        }
                        AssetFileDescriptor openRawResourceFd = this._context.getResources().openRawResourceFd(i2);
                        if (openRawResourceFd == null) {
                            if (QLog.isColorLevel()) {
                                QLog.m376e("TRAE", 2, "TraeMediaPlay | afd == null rsid:" + i2);
                            }
                            this.mMediaPlay.release();
                            this.mMediaPlay = null;
                            return false;
                        }
                        this.mMediaPlay.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
                        openRawResourceFd.close();
                    } else if (i == 1) {
                        if (QLog.isColorLevel()) {
                            QLog.m376e("TRAE", 2, "TraeMediaPlay | uri:" + uri2);
                        }
                        this.mMediaPlay.setDataSource(this._context, uri2);
                    } else if (i == 2) {
                        if (QLog.isColorLevel()) {
                            QLog.m376e("TRAE", 2, "TraeMediaPlay | FilePath:" + str);
                        }
                        this.mMediaPlay.setDataSource(str);
                    } else {
                        if (QLog.isColorLevel()) {
                            QLog.m376e("TRAE", 2, "TraeMediaPlay | err datasource:" + i);
                        }
                        this.mMediaPlay.release();
                        this.mMediaPlay = null;
                    }
                    if (this.mMediaPlay == null) {
                        return false;
                    }
                    this._ringMode = z2;
                    if (this._ringMode) {
                        this._streamType = 2;
                        i5 = 1;
                    } else {
                        this._streamType = 0;
                        i5 = Build.VERSION.SDK_INT >= 11 ? 3 : 0;
                    }
                    this._hasCall = z3;
                    if (this._hasCall) {
                        this._streamType = i4;
                    }
                    this.mMediaPlay.setAudioStreamType(this._streamType);
                    this.mMediaPlay.prepare();
                    this.mMediaPlay.setLooping(z);
                    this.mMediaPlay.start();
                    this._loop = z;
                    if (this._loop) {
                        this._loopCount = 1;
                        this._durationMS = -1;
                    } else {
                        this._loopCount = i3;
                        this._durationMS = this._loopCount * this.mMediaPlay.getDuration();
                    }
                    this._loopCount--;
                    if (!this._hasCall) {
                        audioManager.setMode(i5);
                    }
                    if (this._durationMS > 0) {
                        this._watchTimer = new Timer();
                        this._watchTimertask = new TimerTask() { // from class: com.tencent.rtmp.sharp.jni.TraeMediaPlayer.1
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public void run() {
                                if (TraeMediaPlayer.this.mMediaPlay != null) {
                                    if (QLog.isColorLevel()) {
                                        QLog.m376e("TRAE", 2, "TraeMediaPlay | play timeout");
                                    }
                                    if (TraeMediaPlayer.this.mCallback == null) {
                                        return;
                                    }
                                    TraeMediaPlayer.this.mCallback.mo286a();
                                }
                            }
                        };
                        this._watchTimer.schedule(this._watchTimertask, this._durationMS + 1000);
                    }
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "TraeMediaPlay | DurationMS:" + this.mMediaPlay.getDuration() + " loop:" + z);
                    }
                    return true;
                } catch (Exception e4) {
                    e = e4;
                    exc = e;
                    str5 = uri2;
                    if (QLog.isColorLevel()) {
                        QLog.m378d("TRAE", 2, "TraeMediaPlay | Except: " + exc.getLocalizedMessage() + str5 + exc.getMessage());
                    }
                    this.mMediaPlay.release();
                    this.mMediaPlay = null;
                    return false;
                }
            } catch (SecurityException e5) {
                try {
                    if (QLog.isColorLevel()) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("TraeMediaPlay | SecurityException: ");
                        sb3.append(e5.getLocalizedMessage());
                        sb3.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                        sb3.append(e5.getMessage());
                        QLog.m378d("TRAE", 2, sb3.toString());
                    }
                } catch (Exception e6) {
                    e = e6;
                    uri2 = ConstantUtils.PLACEHOLDER_STR_ONE;
                    exc = e;
                    str5 = uri2;
                    if (QLog.isColorLevel()) {
                    }
                    this.mMediaPlay.release();
                    this.mMediaPlay = null;
                    return false;
                }
                this.mMediaPlay.release();
                this.mMediaPlay = null;
                return false;
            } catch (Exception e7) {
                exc = e7;
                str5 = ConstantUtils.PLACEHOLDER_STR_ONE;
                if (QLog.isColorLevel()) {
                }
                this.mMediaPlay.release();
                this.mMediaPlay = null;
                return false;
            }
        } catch (IOException e8) {
            str4 = ConstantUtils.PLACEHOLDER_STR_ONE;
            iOException = e8;
        } catch (IllegalArgumentException e9) {
            str3 = ConstantUtils.PLACEHOLDER_STR_ONE;
            illegalArgumentException = e9;
        } catch (IllegalStateException e10) {
            str2 = ConstantUtils.PLACEHOLDER_STR_ONE;
            illegalStateException = e10;
        }
    }

    public void stopRing() {
        if (QLog.isColorLevel()) {
            QLog.m378d("TRAE", 2, "TraeMediaPlay stopRing ");
        }
        MediaPlayer mediaPlayer = this.mMediaPlay;
        if (mediaPlayer == null) {
            return;
        }
        if (mediaPlayer.isPlaying()) {
            this.mMediaPlay.stop();
        }
        this.mMediaPlay.reset();
        try {
            if (this._watchTimer != null) {
                this._watchTimer.cancel();
                this._watchTimer = null;
                this._watchTimertask = null;
            }
            this.mMediaPlay.release();
        } catch (Exception unused) {
        }
        this.mMediaPlay = null;
        this._durationMS = -1;
    }

    public int getStreamType() {
        return this._streamType;
    }

    public int getDuration() {
        return this._durationMS;
    }

    public boolean hasCall() {
        return this._hasCall;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        AudioDeviceInterface.LogTraceEntry(" cb:" + this.mCallback + " loopCount:" + this._loopCount + " _loop:" + this._loop);
        if (this._loop) {
            if (!QLog.isColorLevel()) {
                return;
            }
            QLog.m378d("TRAE", 2, "loop play,continue...");
            return;
        }
        try {
            if (this._loopCount <= 0) {
                volumeUndo();
                if (this.mMediaPlay.isPlaying()) {
                    this.mMediaPlay.stop();
                }
                this.mMediaPlay.reset();
                this.mMediaPlay.release();
                this.mMediaPlay = null;
                if (this.mCallback != null) {
                    this.mCallback.mo286a();
                }
            } else {
                this.mMediaPlay.start();
                this._loopCount--;
            }
        } catch (Exception unused) {
        }
        AudioDeviceInterface.LogTraceExit();
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        AudioDeviceInterface.LogTraceEntry(" cb:" + this.mCallback + " arg1:" + i + " arg2:" + i2);
        try {
            this.mMediaPlay.release();
        } catch (Exception unused) {
        }
        this.mMediaPlay = null;
        AbstractC3734a abstractC3734a = this.mCallback;
        if (abstractC3734a != null) {
            abstractC3734a.mo286a();
        }
        AudioDeviceInterface.LogTraceExit();
        return false;
    }

    private void volumeDo() {
        if (this.mMediaPlay != null && this._ringMode && this._streamType != 2) {
            try {
                AudioManager audioManager = (AudioManager) this._context.getSystemService("audio");
                int streamVolume = audioManager.getStreamVolume(this._streamType);
                int streamMaxVolume = audioManager.getStreamMaxVolume(this._streamType);
                int streamVolume2 = audioManager.getStreamVolume(2);
                int streamMaxVolume2 = audioManager.getStreamMaxVolume(2);
                int i = (int) (((streamVolume2 * 1.0d) / streamMaxVolume2) * streamMaxVolume);
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "TraeMediaPlay volumeDo currV:" + streamVolume + " maxV:" + streamMaxVolume + " currRV:" + streamVolume2 + " maxRV:" + streamMaxVolume2 + " setV:" + i);
                }
                int i2 = i + 1;
                if (i2 >= streamMaxVolume) {
                    i2 = streamMaxVolume;
                }
                audioManager.setStreamVolume(this._streamType, i2, 0);
                this._prevVolume = streamVolume;
            } catch (Exception unused) {
            }
        }
    }

    private void volumeUndo() {
        if (this.mMediaPlay != null && this._ringMode && this._streamType != 2 && this._prevVolume != -1) {
            try {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "TraeMediaPlay volumeUndo _prevVolume:" + this._prevVolume);
                }
                ((AudioManager) this._context.getSystemService("audio")).setStreamVolume(this._streamType, this._prevVolume, 0);
            } catch (Exception unused) {
            }
        }
    }
}
