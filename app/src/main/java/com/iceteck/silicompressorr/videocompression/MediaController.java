package com.iceteck.silicompressorr.videocompression;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint({"NewApi"})
/* loaded from: classes3.dex */
public class MediaController {
    private static final int DEFAULT_VIDEO_BITRATE = 450000;
    private static final int DEFAULT_VIDEO_HEIGHT = 360;
    private static final int DEFAULT_VIDEO_WIDTH = 640;
    private static volatile MediaController Instance = null;
    public static final String MIME_TYPE = "video/avc";
    private static final int PROCESSOR_TYPE_INTEL = 2;
    private static final int PROCESSOR_TYPE_MTK = 3;
    private static final int PROCESSOR_TYPE_OTHER = 0;
    private static final int PROCESSOR_TYPE_QCOM = 1;
    private static final int PROCESSOR_TYPE_SEC = 4;
    private static final int PROCESSOR_TYPE_TI = 5;
    public static File cachedFile;
    public String path;
    private boolean videoConvertFirstWrite = true;

    public static native int convertVideoFrame(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i, int i2, int i3, int i4, int i5);

    private static boolean isRecognizedFormat(int i) {
        if (i == 39 || i == 2130706688) {
            return true;
        }
        switch (i) {
            case 19:
            case 20:
            case 21:
                return true;
            default:
                return false;
        }
    }

    public static MediaController getInstance() {
        MediaController mediaController = Instance;
        if (mediaController == null) {
            synchronized (MediaController.class) {
                mediaController = Instance;
                if (mediaController == null) {
                    mediaController = new MediaController();
                    Instance = mediaController;
                }
            }
        }
        return mediaController;
    }

    @SuppressLint({"NewApi"})
    public static int selectColorFormat(MediaCodecInfo mediaCodecInfo, String str) {
        int i;
        MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = capabilitiesForType.colorFormats;
            if (i2 >= iArr.length) {
                return i3;
            }
            i = iArr[i2];
            if (isRecognizedFormat(i)) {
                if (!mediaCodecInfo.getName().equals("OMX.SEC.AVC.Encoder") || i != 19) {
                    break;
                }
                i3 = i;
            }
            i2++;
        }
        return i;
    }

    private void didWriteData(boolean z, boolean z2) {
        if (this.videoConvertFirstWrite) {
            this.videoConvertFirstWrite = false;
        }
    }

    /* loaded from: classes3.dex */
    public static class VideoConvertRunnable implements Runnable {
        private File destDirectory;
        private String videoPath;

        private VideoConvertRunnable(String str, File file) {
            this.videoPath = str;
            this.destDirectory = file;
        }

        public static void runConversion(final String str, final File file) {
            new Thread(new Runnable() { // from class: com.iceteck.silicompressorr.videocompression.MediaController.VideoConvertRunnable.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Thread thread = new Thread(new VideoConvertRunnable(str, file), "VideoConvertRunnable");
                        thread.start();
                        thread.join();
                    } catch (Exception e) {
                        Log.e("tmessages", e.getMessage());
                    }
                }
            }).start();
        }

        @Override // java.lang.Runnable
        public void run() {
            MediaController.getInstance().convertVideo(this.videoPath, this.destDirectory);
        }
    }

    public static MediaCodecInfo selectCodec(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        MediaCodecInfo mediaCodecInfo = null;
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                MediaCodecInfo mediaCodecInfo2 = mediaCodecInfo;
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str)) {
                        if (!codecInfoAt.getName().equals("OMX.SEC.avc.enc") || codecInfoAt.getName().equals("OMX.SEC.AVC.Encoder")) {
                            return codecInfoAt;
                        }
                        mediaCodecInfo2 = codecInfoAt;
                    }
                }
                mediaCodecInfo = mediaCodecInfo2;
            }
        }
        return mediaCodecInfo;
    }

    public void scheduleVideoConvert(String str, File file) {
        startVideoConvertFromQueue(str, file);
    }

    private void startVideoConvertFromQueue(String str, File file) {
        VideoConvertRunnable.runConversion(str, file);
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0089, code lost:
        if (r10 == (-1)) goto L13;
     */
    @TargetApi(16)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private long readAndWriteTrack(MediaExtractor mediaExtractor, MP4Builder mP4Builder, MediaCodec.BufferInfo bufferInfo, long j, long j2, File file, boolean z) throws Exception {
        long j3;
        boolean z2;
        int selectTrack = selectTrack(mediaExtractor, z);
        long j4 = -1;
        if (selectTrack >= 0) {
            mediaExtractor.selectTrack(selectTrack);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(selectTrack);
            int addTrack = mP4Builder.addTrack(trackFormat, z);
            int integer = trackFormat.getInteger("max-input-size");
            long j5 = 0;
            int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
            if (i > 0) {
                mediaExtractor.seekTo(j, 0);
            } else {
                mediaExtractor.seekTo(0L, 0);
            }
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(integer);
            long j6 = -1;
            boolean z3 = false;
            while (!z3) {
                int sampleTrackIndex = mediaExtractor.getSampleTrackIndex();
                if (sampleTrackIndex == selectTrack) {
                    bufferInfo.size = mediaExtractor.readSampleData(allocateDirect, 0);
                    if (bufferInfo.size < 0) {
                        bufferInfo.size = 0;
                        j3 = j5;
                    } else {
                        bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
                        long j7 = (i <= 0 || j6 != j4) ? j6 : bufferInfo.presentationTimeUs;
                        j3 = 0;
                        if (j2 < 0 || bufferInfo.presentationTimeUs < j2) {
                            bufferInfo.offset = 0;
                            bufferInfo.flags = mediaExtractor.getSampleFlags();
                            mP4Builder.writeSampleData(addTrack, allocateDirect, bufferInfo, z);
                            mediaExtractor.advance();
                            j6 = j7;
                            z2 = false;
                        } else {
                            j6 = j7;
                        }
                    }
                    z2 = true;
                } else {
                    j3 = j5;
                }
                if (z2) {
                    z3 = true;
                }
                j5 = j3;
                j4 = -1;
            }
            mediaExtractor.unselectTrack(selectTrack);
            return j6;
        }
        return -1L;
    }

    @TargetApi(16)
    private int selectTrack(MediaExtractor mediaExtractor, boolean z) {
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            String string = mediaExtractor.getTrackFormat(i).getString("mime");
            if (z) {
                if (string.startsWith("audio/")) {
                    return i;
                }
            } else if (string.startsWith("video/")) {
                return i;
            }
        }
        return -5;
    }

    public boolean convertVideo(String str, File file) {
        return convertVideo(str, file, 0, 0, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:155:0x03dd, code lost:
        r0 = r5;
        r16 = r15;
        r15 = r18;
        r5 = r40;
        r4 = r41;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:142:0x03ba A[Catch: all -> 0x0138, Exception -> 0x0687, TryCatch #14 {all -> 0x0138, blocks: (B:363:0x015f, B:365:0x0169, B:367:0x0176, B:369:0x017a, B:371:0x0183, B:375:0x01c0, B:90:0x022c, B:92:0x0230, B:100:0x02be, B:328:0x02db, B:331:0x02e4, B:317:0x030f, B:120:0x0336, B:122:0x0344, B:128:0x036c, B:130:0x0372, B:132:0x0378, B:134:0x037e, B:135:0x0385, B:137:0x038c, B:138:0x039d, B:139:0x0381, B:142:0x03ba, B:144:0x03c3, B:239:0x040e, B:241:0x0414, B:245:0x042b, B:247:0x0432, B:252:0x0440, B:260:0x0455, B:262:0x045b, B:349:0x024c, B:351:0x0259, B:358:0x0268, B:360:0x0271, B:378:0x0192, B:381:0x019d, B:384:0x01a8, B:387:0x01b3, B:391:0x01f1, B:392:0x01f9, B:400:0x0125), top: B:31:0x0103 }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x03d2  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x05de A[Catch: Exception -> 0x0676, all -> 0x0726, TryCatch #3 {all -> 0x0726, blocks: (B:36:0x0146, B:84:0x014c, B:88:0x020f, B:94:0x0283, B:97:0x02a5, B:101:0x02cc, B:104:0x02d2, B:109:0x02f9, B:111:0x0307, B:117:0x0322, B:158:0x03f4, B:164:0x0518, B:177:0x052f, B:179:0x054e, B:181:0x0554, B:184:0x0569, B:196:0x056e, B:199:0x057f, B:201:0x0585, B:202:0x0596, B:204:0x059e, B:207:0x05ba, B:187:0x05d8, B:189:0x05de, B:191:0x05ea, B:192:0x05ef, B:194:0x05f7, B:210:0x06b4, B:211:0x06bf, B:213:0x06c4, B:215:0x06c9, B:217:0x06ce, B:219:0x06d6, B:41:0x06e7, B:220:0x05cd, B:224:0x0575, B:226:0x055c, B:228:0x0560, B:232:0x0622, B:233:0x063b, B:249:0x043a, B:254:0x0449, B:256:0x044e, B:265:0x0463, B:269:0x0480, B:271:0x0484, B:273:0x048a, B:275:0x0490, B:278:0x0496, B:279:0x04c4, B:282:0x04d0, B:283:0x04dc, B:285:0x04eb, B:288:0x04f4, B:291:0x04b6, B:300:0x063c, B:301:0x065b, B:302:0x0443, B:304:0x065c, B:305:0x0675, B:114:0x031d), top: B:35:0x0146 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x056e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x06c4 A[Catch: Exception -> 0x06f4, all -> 0x0726, TryCatch #3 {all -> 0x0726, blocks: (B:36:0x0146, B:84:0x014c, B:88:0x020f, B:94:0x0283, B:97:0x02a5, B:101:0x02cc, B:104:0x02d2, B:109:0x02f9, B:111:0x0307, B:117:0x0322, B:158:0x03f4, B:164:0x0518, B:177:0x052f, B:179:0x054e, B:181:0x0554, B:184:0x0569, B:196:0x056e, B:199:0x057f, B:201:0x0585, B:202:0x0596, B:204:0x059e, B:207:0x05ba, B:187:0x05d8, B:189:0x05de, B:191:0x05ea, B:192:0x05ef, B:194:0x05f7, B:210:0x06b4, B:211:0x06bf, B:213:0x06c4, B:215:0x06c9, B:217:0x06ce, B:219:0x06d6, B:41:0x06e7, B:220:0x05cd, B:224:0x0575, B:226:0x055c, B:228:0x0560, B:232:0x0622, B:233:0x063b, B:249:0x043a, B:254:0x0449, B:256:0x044e, B:265:0x0463, B:269:0x0480, B:271:0x0484, B:273:0x048a, B:275:0x0490, B:278:0x0496, B:279:0x04c4, B:282:0x04d0, B:283:0x04dc, B:285:0x04eb, B:288:0x04f4, B:291:0x04b6, B:300:0x063c, B:301:0x065b, B:302:0x0443, B:304:0x065c, B:305:0x0675, B:114:0x031d), top: B:35:0x0146 }] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x06c9 A[Catch: Exception -> 0x06f4, all -> 0x0726, TryCatch #3 {all -> 0x0726, blocks: (B:36:0x0146, B:84:0x014c, B:88:0x020f, B:94:0x0283, B:97:0x02a5, B:101:0x02cc, B:104:0x02d2, B:109:0x02f9, B:111:0x0307, B:117:0x0322, B:158:0x03f4, B:164:0x0518, B:177:0x052f, B:179:0x054e, B:181:0x0554, B:184:0x0569, B:196:0x056e, B:199:0x057f, B:201:0x0585, B:202:0x0596, B:204:0x059e, B:207:0x05ba, B:187:0x05d8, B:189:0x05de, B:191:0x05ea, B:192:0x05ef, B:194:0x05f7, B:210:0x06b4, B:211:0x06bf, B:213:0x06c4, B:215:0x06c9, B:217:0x06ce, B:219:0x06d6, B:41:0x06e7, B:220:0x05cd, B:224:0x0575, B:226:0x055c, B:228:0x0560, B:232:0x0622, B:233:0x063b, B:249:0x043a, B:254:0x0449, B:256:0x044e, B:265:0x0463, B:269:0x0480, B:271:0x0484, B:273:0x048a, B:275:0x0490, B:278:0x0496, B:279:0x04c4, B:282:0x04d0, B:283:0x04dc, B:285:0x04eb, B:288:0x04f4, B:291:0x04b6, B:300:0x063c, B:301:0x065b, B:302:0x0443, B:304:0x065c, B:305:0x0675, B:114:0x031d), top: B:35:0x0146 }] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x06ce A[Catch: Exception -> 0x06f4, all -> 0x0726, TryCatch #3 {all -> 0x0726, blocks: (B:36:0x0146, B:84:0x014c, B:88:0x020f, B:94:0x0283, B:97:0x02a5, B:101:0x02cc, B:104:0x02d2, B:109:0x02f9, B:111:0x0307, B:117:0x0322, B:158:0x03f4, B:164:0x0518, B:177:0x052f, B:179:0x054e, B:181:0x0554, B:184:0x0569, B:196:0x056e, B:199:0x057f, B:201:0x0585, B:202:0x0596, B:204:0x059e, B:207:0x05ba, B:187:0x05d8, B:189:0x05de, B:191:0x05ea, B:192:0x05ef, B:194:0x05f7, B:210:0x06b4, B:211:0x06bf, B:213:0x06c4, B:215:0x06c9, B:217:0x06ce, B:219:0x06d6, B:41:0x06e7, B:220:0x05cd, B:224:0x0575, B:226:0x055c, B:228:0x0560, B:232:0x0622, B:233:0x063b, B:249:0x043a, B:254:0x0449, B:256:0x044e, B:265:0x0463, B:269:0x0480, B:271:0x0484, B:273:0x048a, B:275:0x0490, B:278:0x0496, B:279:0x04c4, B:282:0x04d0, B:283:0x04dc, B:285:0x04eb, B:288:0x04f4, B:291:0x04b6, B:300:0x063c, B:301:0x065b, B:302:0x0443, B:304:0x065c, B:305:0x0675, B:114:0x031d), top: B:35:0x0146 }] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x06d6 A[Catch: Exception -> 0x06f4, all -> 0x0726, TryCatch #3 {all -> 0x0726, blocks: (B:36:0x0146, B:84:0x014c, B:88:0x020f, B:94:0x0283, B:97:0x02a5, B:101:0x02cc, B:104:0x02d2, B:109:0x02f9, B:111:0x0307, B:117:0x0322, B:158:0x03f4, B:164:0x0518, B:177:0x052f, B:179:0x054e, B:181:0x0554, B:184:0x0569, B:196:0x056e, B:199:0x057f, B:201:0x0585, B:202:0x0596, B:204:0x059e, B:207:0x05ba, B:187:0x05d8, B:189:0x05de, B:191:0x05ea, B:192:0x05ef, B:194:0x05f7, B:210:0x06b4, B:211:0x06bf, B:213:0x06c4, B:215:0x06c9, B:217:0x06ce, B:219:0x06d6, B:41:0x06e7, B:220:0x05cd, B:224:0x0575, B:226:0x055c, B:228:0x0560, B:232:0x0622, B:233:0x063b, B:249:0x043a, B:254:0x0449, B:256:0x044e, B:265:0x0463, B:269:0x0480, B:271:0x0484, B:273:0x048a, B:275:0x0490, B:278:0x0496, B:279:0x04c4, B:282:0x04d0, B:283:0x04dc, B:285:0x04eb, B:288:0x04f4, B:291:0x04b6, B:300:0x063c, B:301:0x065b, B:302:0x0443, B:304:0x065c, B:305:0x0675, B:114:0x031d), top: B:35:0x0146 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x04f1  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x04f3  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x06e7 A[Catch: Exception -> 0x06f4, all -> 0x0726, TRY_LEAVE, TryCatch #3 {all -> 0x0726, blocks: (B:36:0x0146, B:84:0x014c, B:88:0x020f, B:94:0x0283, B:97:0x02a5, B:101:0x02cc, B:104:0x02d2, B:109:0x02f9, B:111:0x0307, B:117:0x0322, B:158:0x03f4, B:164:0x0518, B:177:0x052f, B:179:0x054e, B:181:0x0554, B:184:0x0569, B:196:0x056e, B:199:0x057f, B:201:0x0585, B:202:0x0596, B:204:0x059e, B:207:0x05ba, B:187:0x05d8, B:189:0x05de, B:191:0x05ea, B:192:0x05ef, B:194:0x05f7, B:210:0x06b4, B:211:0x06bf, B:213:0x06c4, B:215:0x06c9, B:217:0x06ce, B:219:0x06d6, B:41:0x06e7, B:220:0x05cd, B:224:0x0575, B:226:0x055c, B:228:0x0560, B:232:0x0622, B:233:0x063b, B:249:0x043a, B:254:0x0449, B:256:0x044e, B:265:0x0463, B:269:0x0480, B:271:0x0484, B:273:0x048a, B:275:0x0490, B:278:0x0496, B:279:0x04c4, B:282:0x04d0, B:283:0x04dc, B:285:0x04eb, B:288:0x04f4, B:291:0x04b6, B:300:0x063c, B:301:0x065b, B:302:0x0443, B:304:0x065c, B:305:0x0675, B:114:0x031d), top: B:35:0x0146 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x07eb  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x07f0  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x06fb  */
    /* JADX WARN: Type inference failed for: r0v34, types: [com.iceteck.silicompressorr.videocompression.MP4Builder] */
    /* JADX WARN: Type inference failed for: r10v2, types: [com.iceteck.silicompressorr.videocompression.Mp4Movie] */
    /* JADX WARN: Type inference failed for: r13v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v15, types: [com.iceteck.silicompressorr.videocompression.MP4Builder] */
    /* JADX WARN: Type inference failed for: r13v16, types: [com.iceteck.silicompressorr.videocompression.MP4Builder] */
    /* JADX WARN: Type inference failed for: r13v17 */
    /* JADX WARN: Type inference failed for: r13v18 */
    /* JADX WARN: Type inference failed for: r13v19 */
    /* JADX WARN: Type inference failed for: r15v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r45v1, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r45v43 */
    /* JADX WARN: Type inference failed for: r45v44 */
    /* JADX WARN: Type inference failed for: r50v0, types: [int] */
    /* JADX WARN: Type inference failed for: r50v10 */
    /* JADX WARN: Type inference failed for: r50v11 */
    /* JADX WARN: Type inference failed for: r50v12 */
    /* JADX WARN: Type inference failed for: r50v13 */
    /* JADX WARN: Type inference failed for: r50v14 */
    /* JADX WARN: Type inference failed for: r50v7 */
    /* JADX WARN: Type inference failed for: r50v8 */
    /* JADX WARN: Type inference failed for: r50v9 */
    @TargetApi(16)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean convertVideo(String str, File file, int i, int i2, int i3) {
        int i4;
        ?? file2;
        File file3;
        String str2;
        MediaExtractor mediaExtractor;
        MP4Builder mP4Builder;
        MediaExtractor mediaExtractor2;
        Throwable th;
        MP4Builder mP4Builder2;
        File file4;
        ?? r45;
        boolean z;
        MediaCodec.BufferInfo bufferInfo;
        MP4Builder createMovie;
        String str3;
        MediaCodec.BufferInfo bufferInfo2;
        boolean z2;
        long j;
        MediaCodec.BufferInfo bufferInfo3;
        String str4;
        int i5;
        OutputSurface outputSurface;
        MediaCodec mediaCodec;
        InputSurface inputSurface;
        MediaCodec mediaCodec2;
        String str5;
        boolean z3;
        int i6;
        OutputSurface outputSurface2;
        String str6;
        char c;
        int i7;
        char c2;
        int i8;
        String str7;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        MediaFormat trackFormat;
        MediaFormat createVideoFormat;
        int i15;
        boolean z4;
        boolean z5;
        ByteBuffer[] byteBufferArr;
        ByteBuffer[] byteBufferArr2;
        int i16;
        ByteBuffer[] byteBufferArr3;
        ByteBuffer outputBuffer;
        ByteBuffer[] byteBufferArr4;
        int i17;
        int i18;
        boolean z6;
        int i19;
        boolean z7;
        ByteBuffer[] byteBufferArr5;
        int i20;
        ByteBuffer byteBuffer;
        ByteBuffer byteBuffer2;
        boolean z8;
        boolean z9;
        String str8;
        boolean z10;
        boolean z11;
        ByteBuffer inputBuffer;
        MediaController mediaController = this;
        mediaController.path = str;
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(mediaController.path);
        String extractMetadata = mediaMetadataRetriever.extractMetadata(19);
        String extractMetadata2 = mediaMetadataRetriever.extractMetadata(18);
        String extractMetadata3 = mediaMetadataRetriever.extractMetadata(24);
        int i21 = i > 0 ? i : DEFAULT_VIDEO_WIDTH;
        int i22 = i2 > 0 ? i2 : DEFAULT_VIDEO_HEIGHT;
        int intValue = Integer.valueOf(extractMetadata3).intValue();
        int intValue2 = Integer.valueOf(extractMetadata).intValue();
        int intValue3 = Integer.valueOf(extractMetadata2).intValue();
        int i23 = i3 > 0 ? i3 : DEFAULT_VIDEO_BITRATE;
        MP4Builder file5 = new File(file, "VIDEO_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".mp4");
        if (Build.VERSION.SDK_INT >= 18 || i22 <= i21 || i21 == intValue2 || i22 == intValue3) {
            if (Build.VERSION.SDK_INT > 20) {
                if (intValue == 90) {
                    intValue = 0;
                } else if (intValue == 180) {
                    intValue = 0;
                    i4 = 180;
                    int i24 = i22;
                    i22 = i21;
                    i21 = i24;
                    file2 = new File(mediaController.path);
                    if (file2.canRead()) {
                        mediaController.didWriteData(true, true);
                        return false;
                    }
                    mediaController.videoConvertFirstWrite = true;
                    long currentTimeMillis = System.currentTimeMillis();
                    if (i22 != 0 && i21 != 0) {
                        try {
                            bufferInfo = new MediaCodec.BufferInfo();
                            ?? mp4Movie = new Mp4Movie();
                            mp4Movie.setCacheFile(file5);
                            mp4Movie.setRotation(intValue);
                            mp4Movie.setSize(i22, i21);
                            createMovie = new MP4Builder().createMovie(mp4Movie);
                            try {
                                mediaExtractor2 = new MediaExtractor();
                                try {
                                    mediaExtractor2.setDataSource(file2.toString());
                                    try {
                                        try {
                                        } catch (Throwable th2) {
                                            th = th2;
                                            mP4Builder2 = file5;
                                            if (mediaExtractor2 != null) {
                                            }
                                            if (mP4Builder2 != null) {
                                            }
                                            Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                            throw th;
                                        }
                                    } catch (Exception e) {
                                        e = e;
                                        str3 = str;
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    i = file5;
                                    str3 = file2;
                                    file5 = createMovie;
                                } catch (Throwable th3) {
                                    th = th3;
                                    file5 = createMovie;
                                }
                            } catch (Exception e3) {
                                e = e3;
                                file3 = file5;
                                str2 = file2;
                                mP4Builder = createMovie;
                                mediaExtractor = null;
                            } catch (Throwable th4) {
                                th = th4;
                                file5 = createMovie;
                                mediaExtractor2 = null;
                                th = th;
                                mP4Builder2 = file5;
                                if (mediaExtractor2 != null) {
                                }
                                if (mP4Builder2 != null) {
                                }
                                Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                throw th;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            file3 = file5;
                            str2 = file2;
                            mediaExtractor = null;
                            mP4Builder = null;
                        } catch (Throwable th5) {
                            th = th5;
                            file5 = null;
                        }
                        try {
                            if (i22 != intValue2) {
                                bufferInfo2 = bufferInfo;
                                i = file5;
                                str = file2;
                                file5 = createMovie;
                                z2 = false;
                            } else if (i21 == intValue3) {
                                i = file5;
                                str = file2;
                                file5 = createMovie;
                                long readAndWriteTrack = readAndWriteTrack(mediaExtractor2, createMovie, bufferInfo, -1L, -1L, file5, false);
                                str3 = str;
                                j = readAndWriteTrack != -1 ? readAndWriteTrack : -1L;
                                bufferInfo3 = bufferInfo;
                                z = false;
                                if (!z) {
                                    readAndWriteTrack(mediaExtractor2, file5, bufferInfo3, j, -1L, i, true);
                                }
                                mediaExtractor2.release();
                                if (file5 != 0) {
                                    try {
                                        file5.finishMovie(false);
                                    } catch (Exception e5) {
                                        Log.e("tmessages", e5.getMessage());
                                    }
                                }
                                Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                r45 = str3;
                                file4 = i;
                                didWriteData(true, z);
                                cachedFile = file4;
                                Log.e("ViratPath", this.path + "");
                                Log.e("ViratPath", file4.getPath() + "");
                                Log.e("ViratPath", r45.getPath() + "");
                                return true;
                            } else {
                                bufferInfo2 = bufferInfo;
                                i = file5;
                                str = file2;
                                z2 = false;
                                file5 = createMovie;
                            }
                            int selectTrack = mediaController.selectTrack(mediaExtractor2, z2);
                            if (selectTrack >= 0) {
                                try {
                                    String lowerCase = Build.MANUFACTURER.toLowerCase();
                                    if (Build.VERSION.SDK_INT < 18) {
                                        try {
                                            MediaCodecInfo selectCodec = selectCodec(MIME_TYPE);
                                            int selectColorFormat = selectColorFormat(selectCodec, MIME_TYPE);
                                            if (selectColorFormat == 0) {
                                                throw new RuntimeException("no supported color format");
                                            }
                                            String name = selectCodec.getName();
                                            if (name.contains("OMX.qcom.")) {
                                                if (Build.VERSION.SDK_INT != 16 || (!lowerCase.equals("lge") && !lowerCase.equals("nokia"))) {
                                                    c = 1;
                                                    i7 = 0;
                                                } else {
                                                    c = 1;
                                                    i7 = 1;
                                                }
                                            } else {
                                                if (name.contains("OMX.Intel.")) {
                                                    c = 2;
                                                } else if (name.equals("OMX.MTK.VIDEO.ENCODER.AVC")) {
                                                    c = 3;
                                                } else if (name.equals("OMX.SEC.AVC.Encoder")) {
                                                    c = 4;
                                                    i7 = 1;
                                                } else {
                                                    c = name.equals("OMX.TI.DUCATI1.VIDEO.H264E") ? (char) 5 : (char) 0;
                                                }
                                                i7 = 0;
                                            }
                                            Log.e("tmessages", "codec = " + selectCodec.getName() + " manufacturer = " + lowerCase + "device = " + Build.MODEL);
                                            c2 = c;
                                            i8 = selectColorFormat;
                                        } catch (Exception e6) {
                                            e = e6;
                                            str6 = str;
                                            bufferInfo3 = bufferInfo2;
                                            outputSurface = null;
                                            mediaCodec = null;
                                            inputSurface = null;
                                            str7 = str6;
                                            mediaCodec2 = null;
                                            str3 = str7;
                                            i5 = selectTrack;
                                            try {
                                                Log.e("tmessages", e.getMessage());
                                                outputSurface2 = outputSurface;
                                                i6 = i5;
                                                z3 = true;
                                                str5 = str3;
                                                mediaExtractor2.unselectTrack(i6);
                                                if (outputSurface2 != null) {
                                                }
                                                if (inputSurface != null) {
                                                }
                                                if (mediaCodec2 != null) {
                                                }
                                                if (mediaCodec != null) {
                                                }
                                                z = z3;
                                                j = -1;
                                                file5 = file5;
                                                str3 = str5;
                                                i = i;
                                                if (!z) {
                                                }
                                                mediaExtractor2.release();
                                                if (file5 != 0) {
                                                }
                                                Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                                r45 = str3;
                                                file4 = i;
                                            } catch (Exception e7) {
                                                e = e7;
                                                mediaExtractor = mediaExtractor2;
                                                mP4Builder = file5;
                                                str2 = str3;
                                                file3 = i;
                                                try {
                                                    Log.e("tmessages", e.getMessage());
                                                    if (mediaExtractor != null) {
                                                        mediaExtractor.release();
                                                    }
                                                    if (mP4Builder != null) {
                                                        try {
                                                            mP4Builder.finishMovie(false);
                                                        } catch (Exception e8) {
                                                            Log.e("tmessages", e8.getMessage());
                                                        }
                                                    }
                                                    Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                                    z = true;
                                                    r45 = str2;
                                                    file4 = file3;
                                                    didWriteData(true, z);
                                                    cachedFile = file4;
                                                    Log.e("ViratPath", this.path + "");
                                                    Log.e("ViratPath", file4.getPath() + "");
                                                    Log.e("ViratPath", r45.getPath() + "");
                                                    return true;
                                                } catch (Throwable th6) {
                                                    th = th6;
                                                    mediaExtractor2 = mediaExtractor;
                                                    mP4Builder2 = mP4Builder;
                                                    if (mediaExtractor2 != null) {
                                                        mediaExtractor2.release();
                                                    }
                                                    if (mP4Builder2 != null) {
                                                        try {
                                                            mP4Builder2.finishMovie(false);
                                                        } catch (Exception e9) {
                                                            Log.e("tmessages", e9.getMessage());
                                                        }
                                                    }
                                                    Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                                    throw th;
                                                }
                                            }
                                            didWriteData(true, z);
                                            cachedFile = file4;
                                            Log.e("ViratPath", this.path + "");
                                            Log.e("ViratPath", file4.getPath() + "");
                                            Log.e("ViratPath", r45.getPath() + "");
                                            return true;
                                        }
                                    } else {
                                        c2 = 0;
                                        i8 = 2130708361;
                                        i7 = 0;
                                    }
                                    Log.e("tmessages", "colorFormat = " + i8);
                                    int i25 = i22 * i21;
                                    int i26 = (i25 * 3) / 2;
                                    if (c2 != 0) {
                                        i9 = i7;
                                        if (c2 == 1) {
                                            if (!lowerCase.toLowerCase().equals("lge")) {
                                                i10 = ((i25 + 2047) & (-2048)) - i25;
                                                i11 = i26 + i10;
                                                i12 = i10;
                                                i13 = i11;
                                            }
                                            i13 = i26;
                                            i12 = 0;
                                        } else {
                                            if (c2 != 5 && c2 == 3 && lowerCase.equals("baidu")) {
                                                i10 = (((16 - (i21 % 16)) + i21) - i21) * i22;
                                                i11 = i26 + ((i10 * 5) / 4);
                                                i12 = i10;
                                                i13 = i11;
                                            }
                                            i13 = i26;
                                            i12 = 0;
                                        }
                                    } else if (i21 % 16 != 0) {
                                        int i27 = (((16 - (i21 % 16)) + i21) - i21) * i22;
                                        i11 = i26 + ((i27 * 5) / 4);
                                        i12 = i27;
                                        i9 = i7;
                                        i13 = i11;
                                    } else {
                                        i9 = i7;
                                        i13 = i26;
                                        i12 = 0;
                                    }
                                    mediaExtractor2.selectTrack(selectTrack);
                                    i14 = i13;
                                    mediaExtractor2.seekTo(0L, 0);
                                    trackFormat = mediaExtractor2.getTrackFormat(selectTrack);
                                    createVideoFormat = MediaFormat.createVideoFormat(MIME_TYPE, i22, i21);
                                    createVideoFormat.setInteger("color-format", i8);
                                    if (i23 == 0) {
                                        i23 = 921600;
                                    }
                                    createVideoFormat.setInteger("bitrate", i23);
                                    createVideoFormat.setInteger("frame-rate", 25);
                                    createVideoFormat.setInteger("i-frame-interval", 10);
                                    if (Build.VERSION.SDK_INT < 18) {
                                        createVideoFormat.setInteger("stride", i22 + 32);
                                        createVideoFormat.setInteger("slice-height", i21);
                                    }
                                    mediaCodec = MediaCodec.createEncoderByType(MIME_TYPE);
                                } catch (Exception e10) {
                                    e = e10;
                                    str4 = str;
                                    bufferInfo3 = bufferInfo2;
                                    i5 = selectTrack;
                                    outputSurface = null;
                                    mediaCodec = null;
                                }
                                try {
                                    mediaCodec.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
                                    if (Build.VERSION.SDK_INT >= 18) {
                                        try {
                                            inputSurface = new InputSurface(mediaCodec.createInputSurface());
                                        } catch (Exception e11) {
                                            e = e11;
                                            str6 = str;
                                            bufferInfo3 = bufferInfo2;
                                            outputSurface = null;
                                            inputSurface = null;
                                            str7 = str6;
                                            mediaCodec2 = null;
                                            str3 = str7;
                                            i5 = selectTrack;
                                            Log.e("tmessages", e.getMessage());
                                            outputSurface2 = outputSurface;
                                            i6 = i5;
                                            z3 = true;
                                            str5 = str3;
                                            mediaExtractor2.unselectTrack(i6);
                                            if (outputSurface2 != null) {
                                            }
                                            if (inputSurface != null) {
                                            }
                                            if (mediaCodec2 != null) {
                                            }
                                            if (mediaCodec != null) {
                                            }
                                            z = z3;
                                            j = -1;
                                            file5 = file5;
                                            str3 = str5;
                                            i = i;
                                            if (!z) {
                                            }
                                            mediaExtractor2.release();
                                            if (file5 != 0) {
                                            }
                                            Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                            r45 = str3;
                                            file4 = i;
                                            didWriteData(true, z);
                                            cachedFile = file4;
                                            Log.e("ViratPath", this.path + "");
                                            Log.e("ViratPath", file4.getPath() + "");
                                            Log.e("ViratPath", r45.getPath() + "");
                                            return true;
                                        }
                                        try {
                                            inputSurface.makeCurrent();
                                        } catch (Exception e12) {
                                            e = e12;
                                            str7 = str;
                                            bufferInfo3 = bufferInfo2;
                                            outputSurface = null;
                                            mediaCodec2 = null;
                                            str3 = str7;
                                            i5 = selectTrack;
                                            Log.e("tmessages", e.getMessage());
                                            outputSurface2 = outputSurface;
                                            i6 = i5;
                                            z3 = true;
                                            str5 = str3;
                                            mediaExtractor2.unselectTrack(i6);
                                            if (outputSurface2 != null) {
                                            }
                                            if (inputSurface != null) {
                                            }
                                            if (mediaCodec2 != null) {
                                            }
                                            if (mediaCodec != null) {
                                            }
                                            z = z3;
                                            j = -1;
                                            file5 = file5;
                                            str3 = str5;
                                            i = i;
                                            if (!z) {
                                            }
                                            mediaExtractor2.release();
                                            if (file5 != 0) {
                                            }
                                            Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                            r45 = str3;
                                            file4 = i;
                                            didWriteData(true, z);
                                            cachedFile = file4;
                                            Log.e("ViratPath", this.path + "");
                                            Log.e("ViratPath", file4.getPath() + "");
                                            Log.e("ViratPath", r45.getPath() + "");
                                            return true;
                                        }
                                    } else {
                                        inputSurface = null;
                                    }
                                } catch (Exception e13) {
                                    e = e13;
                                    str4 = str;
                                    bufferInfo3 = bufferInfo2;
                                    i5 = selectTrack;
                                    outputSurface = null;
                                    inputSurface = null;
                                    str3 = str4;
                                    mediaCodec2 = null;
                                    Log.e("tmessages", e.getMessage());
                                    outputSurface2 = outputSurface;
                                    i6 = i5;
                                    z3 = true;
                                    str5 = str3;
                                    mediaExtractor2.unselectTrack(i6);
                                    if (outputSurface2 != null) {
                                    }
                                    if (inputSurface != null) {
                                    }
                                    if (mediaCodec2 != null) {
                                    }
                                    if (mediaCodec != null) {
                                    }
                                    z = z3;
                                    j = -1;
                                    file5 = file5;
                                    str3 = str5;
                                    i = i;
                                    if (!z) {
                                    }
                                    mediaExtractor2.release();
                                    if (file5 != 0) {
                                    }
                                    Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                    r45 = str3;
                                    file4 = i;
                                    didWriteData(true, z);
                                    cachedFile = file4;
                                    Log.e("ViratPath", this.path + "");
                                    Log.e("ViratPath", file4.getPath() + "");
                                    Log.e("ViratPath", r45.getPath() + "");
                                    return true;
                                }
                                try {
                                    mediaCodec.start();
                                    mediaCodec2 = MediaCodec.createDecoderByType(trackFormat.getString("mime"));
                                    try {
                                        i15 = i12;
                                        if (Build.VERSION.SDK_INT >= 18) {
                                            try {
                                                outputSurface = new OutputSurface();
                                            } catch (Exception e14) {
                                                e = e14;
                                                str3 = str;
                                                bufferInfo3 = bufferInfo2;
                                                outputSurface = null;
                                                i5 = selectTrack;
                                                Log.e("tmessages", e.getMessage());
                                                outputSurface2 = outputSurface;
                                                i6 = i5;
                                                z3 = true;
                                                str5 = str3;
                                                mediaExtractor2.unselectTrack(i6);
                                                if (outputSurface2 != null) {
                                                }
                                                if (inputSurface != null) {
                                                }
                                                if (mediaCodec2 != null) {
                                                }
                                                if (mediaCodec != null) {
                                                }
                                                z = z3;
                                                j = -1;
                                                file5 = file5;
                                                str3 = str5;
                                                i = i;
                                                if (!z) {
                                                }
                                                mediaExtractor2.release();
                                                if (file5 != 0) {
                                                }
                                                Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                                r45 = str3;
                                                file4 = i;
                                                didWriteData(true, z);
                                                cachedFile = file4;
                                                Log.e("ViratPath", this.path + "");
                                                Log.e("ViratPath", file4.getPath() + "");
                                                Log.e("ViratPath", r45.getPath() + "");
                                                return true;
                                            }
                                        } else {
                                            outputSurface = new OutputSurface(i22, i21, i4);
                                        }
                                    } catch (Exception e15) {
                                        e = e15;
                                        str3 = str;
                                        bufferInfo3 = bufferInfo2;
                                        i5 = selectTrack;
                                        outputSurface = null;
                                    }
                                } catch (Exception e16) {
                                    e = e16;
                                    str3 = str;
                                    bufferInfo3 = bufferInfo2;
                                    i5 = selectTrack;
                                    outputSurface = null;
                                    mediaCodec2 = null;
                                    Log.e("tmessages", e.getMessage());
                                    outputSurface2 = outputSurface;
                                    i6 = i5;
                                    z3 = true;
                                    str5 = str3;
                                    mediaExtractor2.unselectTrack(i6);
                                    if (outputSurface2 != null) {
                                    }
                                    if (inputSurface != null) {
                                    }
                                    if (mediaCodec2 != null) {
                                    }
                                    if (mediaCodec != null) {
                                    }
                                    z = z3;
                                    j = -1;
                                    file5 = file5;
                                    str3 = str5;
                                    i = i;
                                    if (!z) {
                                    }
                                    mediaExtractor2.release();
                                    if (file5 != 0) {
                                    }
                                    Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                    r45 = str3;
                                    file4 = i;
                                    didWriteData(true, z);
                                    cachedFile = file4;
                                    Log.e("ViratPath", this.path + "");
                                    Log.e("ViratPath", file4.getPath() + "");
                                    Log.e("ViratPath", r45.getPath() + "");
                                    return true;
                                }
                                try {
                                    int i28 = i8;
                                    mediaCodec2.configure(trackFormat, outputSurface.getSurface(), (MediaCrypto) null, 0);
                                    mediaCodec2.start();
                                    if (Build.VERSION.SDK_INT < 21) {
                                        ByteBuffer[] inputBuffers = mediaCodec2.getInputBuffers();
                                        ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();
                                        if (Build.VERSION.SDK_INT < 18) {
                                            byteBufferArr3 = mediaCodec.getInputBuffers();
                                            byteBufferArr2 = outputBuffers;
                                            z5 = false;
                                            i16 = -5;
                                        } else {
                                            byteBufferArr2 = outputBuffers;
                                            z5 = false;
                                            i16 = -5;
                                            byteBufferArr3 = null;
                                        }
                                        byteBufferArr = inputBuffers;
                                        z4 = false;
                                    } else {
                                        z4 = false;
                                        z5 = false;
                                        byteBufferArr = null;
                                        byteBufferArr2 = null;
                                        i16 = -5;
                                        byteBufferArr3 = null;
                                    }
                                    while (!z4) {
                                        ByteBuffer[] byteBufferArr6 = byteBufferArr2;
                                        int i29 = i21;
                                        int i30 = i22;
                                        if (!z5) {
                                            int sampleTrackIndex = mediaExtractor2.getSampleTrackIndex();
                                            if (sampleTrackIndex == selectTrack) {
                                                int dequeueInputBuffer = mediaCodec2.dequeueInputBuffer(2500L);
                                                if (dequeueInputBuffer >= 0) {
                                                    if (Build.VERSION.SDK_INT < 21) {
                                                        inputBuffer = byteBufferArr[dequeueInputBuffer];
                                                    } else {
                                                        inputBuffer = mediaCodec2.getInputBuffer(dequeueInputBuffer);
                                                    }
                                                    int readSampleData = mediaExtractor2.readSampleData(inputBuffer, 0);
                                                    if (readSampleData < 0) {
                                                        mediaCodec2.queueInputBuffer(dequeueInputBuffer, 0, 0, 0L, 4);
                                                        z5 = true;
                                                    } else {
                                                        mediaCodec2.queueInputBuffer(dequeueInputBuffer, 0, readSampleData, mediaExtractor2.getSampleTime(), 0);
                                                        mediaExtractor2.advance();
                                                    }
                                                }
                                            } else if (sampleTrackIndex == -1) {
                                                z10 = z5;
                                                z11 = true;
                                                if (!z11) {
                                                    z5 = z10;
                                                    int dequeueInputBuffer2 = mediaCodec2.dequeueInputBuffer(2500L);
                                                    if (dequeueInputBuffer2 >= 0) {
                                                        mediaCodec2.queueInputBuffer(dequeueInputBuffer2, 0, 0, 0L, 4);
                                                        z5 = true;
                                                    }
                                                } else {
                                                    z5 = z10;
                                                }
                                            }
                                            z10 = z5;
                                            z11 = false;
                                            if (!z11) {
                                            }
                                        }
                                        boolean z12 = z4;
                                        int i31 = i16;
                                        boolean z13 = true;
                                        boolean z14 = true;
                                        while (true) {
                                            if (z14 || z13) {
                                                boolean z15 = z13;
                                                boolean z16 = z5;
                                                boolean z17 = z14;
                                                bufferInfo3 = bufferInfo2;
                                                i5 = selectTrack;
                                                try {
                                                    int dequeueOutputBuffer = mediaCodec.dequeueOutputBuffer(bufferInfo3, 2500L);
                                                    if (dequeueOutputBuffer == -1) {
                                                        byteBufferArr4 = byteBufferArr;
                                                        i19 = i31;
                                                        i18 = i29;
                                                        i20 = -1;
                                                        z6 = z12;
                                                        byteBufferArr5 = byteBufferArr6;
                                                        i17 = i30;
                                                        z7 = false;
                                                    } else {
                                                        if (dequeueOutputBuffer == -3) {
                                                            if (Build.VERSION.SDK_INT < 21) {
                                                                byteBufferArr6 = mediaCodec.getOutputBuffers();
                                                            }
                                                        } else if (dequeueOutputBuffer == -2) {
                                                            MediaFormat outputFormat = mediaCodec.getOutputFormat();
                                                            if (i31 == -5) {
                                                                i31 = file5.addTrack(outputFormat, false);
                                                            }
                                                        } else if (dequeueOutputBuffer < 0) {
                                                            throw new RuntimeException("unexpected result from encoder.dequeueOutputBuffer: " + dequeueOutputBuffer);
                                                        } else {
                                                            if (Build.VERSION.SDK_INT < 21) {
                                                                outputBuffer = byteBufferArr6[dequeueOutputBuffer];
                                                            } else {
                                                                outputBuffer = mediaCodec.getOutputBuffer(dequeueOutputBuffer);
                                                            }
                                                            if (outputBuffer == null) {
                                                                throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null");
                                                            }
                                                            if (bufferInfo3.size > 1) {
                                                                if ((bufferInfo3.flags & 2) == 0) {
                                                                    if (file5.writeSampleData(i31, outputBuffer, bufferInfo3, false)) {
                                                                        mediaController.didWriteData(false, false);
                                                                    }
                                                                } else if (i31 == -5) {
                                                                    byte[] bArr = new byte[bufferInfo3.size];
                                                                    outputBuffer.limit(bufferInfo3.offset + bufferInfo3.size);
                                                                    outputBuffer.position(bufferInfo3.offset);
                                                                    outputBuffer.get(bArr);
                                                                    byte b = 1;
                                                                    int i32 = bufferInfo3.size - 1;
                                                                    while (i32 >= 0 && i32 > 3) {
                                                                        if (bArr[i32] == b && bArr[i32 - 1] == 0 && bArr[i32 - 2] == 0) {
                                                                            int i33 = i32 - 3;
                                                                            if (bArr[i33] == 0) {
                                                                                byteBuffer = ByteBuffer.allocate(i33);
                                                                                byteBuffer2 = ByteBuffer.allocate(bufferInfo3.size - i33);
                                                                                byteBufferArr4 = byteBufferArr;
                                                                                byteBuffer.put(bArr, 0, i33).position(0);
                                                                                byteBuffer2.put(bArr, i33, bufferInfo3.size - i33).position(0);
                                                                                break;
                                                                            }
                                                                        }
                                                                        i32--;
                                                                        b = 1;
                                                                        byteBufferArr = byteBufferArr;
                                                                    }
                                                                    byteBufferArr4 = byteBufferArr;
                                                                    byteBuffer = null;
                                                                    byteBuffer2 = null;
                                                                    i17 = i30;
                                                                    i18 = i29;
                                                                    MediaFormat createVideoFormat2 = MediaFormat.createVideoFormat(MIME_TYPE, i17, i18);
                                                                    if (byteBuffer != null && byteBuffer2 != null) {
                                                                        createVideoFormat2.setByteBuffer("csd-0", byteBuffer);
                                                                        createVideoFormat2.setByteBuffer("csd-1", byteBuffer2);
                                                                    }
                                                                    i31 = file5.addTrack(createVideoFormat2, false);
                                                                    z6 = (bufferInfo3.flags & 4) == 0;
                                                                    mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                                                                    i19 = i31;
                                                                    z7 = z15;
                                                                    byteBufferArr5 = byteBufferArr6;
                                                                    i20 = -1;
                                                                }
                                                            }
                                                            byteBufferArr4 = byteBufferArr;
                                                            i17 = i30;
                                                            i18 = i29;
                                                            if ((bufferInfo3.flags & 4) == 0) {
                                                            }
                                                            mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                                                            i19 = i31;
                                                            z7 = z15;
                                                            byteBufferArr5 = byteBufferArr6;
                                                            i20 = -1;
                                                        }
                                                        byteBufferArr4 = byteBufferArr;
                                                        i19 = i31;
                                                        i18 = i29;
                                                        i20 = -1;
                                                        z6 = z12;
                                                        byteBufferArr5 = byteBufferArr6;
                                                        i17 = i30;
                                                        z7 = z15;
                                                    }
                                                    if (dequeueOutputBuffer != i20) {
                                                        i31 = i19;
                                                        z13 = z7;
                                                        byteBufferArr6 = byteBufferArr5;
                                                        z5 = z16;
                                                        mediaController = this;
                                                        i29 = i18;
                                                        i30 = i17;
                                                        z12 = z6;
                                                        selectTrack = i5;
                                                        byteBufferArr = byteBufferArr4;
                                                        bufferInfo2 = bufferInfo3;
                                                        z14 = z17;
                                                    } else {
                                                        int i34 = i18;
                                                        int dequeueOutputBuffer2 = mediaCodec2.dequeueOutputBuffer(bufferInfo3, 2500L);
                                                        if (dequeueOutputBuffer2 == i20) {
                                                            str8 = str;
                                                        } else {
                                                            if (dequeueOutputBuffer2 != -3) {
                                                                if (dequeueOutputBuffer2 == -2) {
                                                                    Log.e("tmessages", "newFormat = " + mediaCodec2.getOutputFormat());
                                                                } else if (dequeueOutputBuffer2 < 0) {
                                                                    throw new RuntimeException("unexpected result from decoder.dequeueOutputBuffer: " + dequeueOutputBuffer2);
                                                                } else {
                                                                    if (Build.VERSION.SDK_INT < 18 ? !(bufferInfo3.size != 0 || bufferInfo3.presentationTimeUs != 0) : bufferInfo3.size == 0) {
                                                                        z8 = false;
                                                                        mediaCodec2.releaseOutputBuffer(dequeueOutputBuffer2, z8);
                                                                        if (z8) {
                                                                            try {
                                                                                outputSurface.awaitNewImage();
                                                                                z9 = false;
                                                                            } catch (Exception e17) {
                                                                                Log.e("tmessages", e17.getMessage());
                                                                                z9 = true;
                                                                            }
                                                                            if (!z9) {
                                                                                if (Build.VERSION.SDK_INT >= 18) {
                                                                                    outputSurface.drawImage(false);
                                                                                    inputSurface.setPresentationTime(bufferInfo3.presentationTimeUs * 1000);
                                                                                    inputSurface.swapBuffers();
                                                                                } else {
                                                                                    int dequeueInputBuffer3 = mediaCodec.dequeueInputBuffer(2500L);
                                                                                    if (dequeueInputBuffer3 >= 0) {
                                                                                        outputSurface.drawImage(true);
                                                                                        ByteBuffer frame = outputSurface.getFrame();
                                                                                        ByteBuffer byteBuffer3 = byteBufferArr3[dequeueInputBuffer3];
                                                                                        byteBuffer3.clear();
                                                                                        str3 = str;
                                                                                        try {
                                                                                            convertVideoFrame(frame, byteBuffer3, i28, i17, i34, i15, i9);
                                                                                            mediaCodec.queueInputBuffer(dequeueInputBuffer3, 0, i14, bufferInfo3.presentationTimeUs, 0);
                                                                                            str8 = str3;
                                                                                        } catch (Exception e18) {
                                                                                            e = e18;
                                                                                            Log.e("tmessages", e.getMessage());
                                                                                            outputSurface2 = outputSurface;
                                                                                            i6 = i5;
                                                                                            z3 = true;
                                                                                            str5 = str3;
                                                                                            mediaExtractor2.unselectTrack(i6);
                                                                                            if (outputSurface2 != null) {
                                                                                            }
                                                                                            if (inputSurface != null) {
                                                                                            }
                                                                                            if (mediaCodec2 != null) {
                                                                                            }
                                                                                            if (mediaCodec != null) {
                                                                                            }
                                                                                            z = z3;
                                                                                            j = -1;
                                                                                            file5 = file5;
                                                                                            str3 = str5;
                                                                                            i = i;
                                                                                            if (!z) {
                                                                                            }
                                                                                            mediaExtractor2.release();
                                                                                            if (file5 != 0) {
                                                                                            }
                                                                                            Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                                                                            r45 = str3;
                                                                                            file4 = i;
                                                                                            didWriteData(true, z);
                                                                                            cachedFile = file4;
                                                                                            Log.e("ViratPath", this.path + "");
                                                                                            Log.e("ViratPath", file4.getPath() + "");
                                                                                            Log.e("ViratPath", r45.getPath() + "");
                                                                                            return true;
                                                                                        }
                                                                                    } else {
                                                                                        str8 = str;
                                                                                        Log.e("tmessages", "input buffer not available");
                                                                                    }
                                                                                    if ((bufferInfo3.flags & 4) != 0) {
                                                                                        Log.e("tmessages", "decoder stream end");
                                                                                        if (Build.VERSION.SDK_INT >= 18) {
                                                                                            mediaCodec.signalEndOfInputStream();
                                                                                        } else {
                                                                                            int dequeueInputBuffer4 = mediaCodec.dequeueInputBuffer(2500L);
                                                                                            if (dequeueInputBuffer4 >= 0) {
                                                                                                mediaCodec.queueInputBuffer(dequeueInputBuffer4, 0, 1, bufferInfo3.presentationTimeUs, 4);
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    i31 = i19;
                                                                                    selectTrack = i5;
                                                                                    z13 = z7;
                                                                                    byteBufferArr6 = byteBufferArr5;
                                                                                    z5 = z16;
                                                                                    str = str8;
                                                                                    mediaController = this;
                                                                                    i29 = i34;
                                                                                    bufferInfo2 = bufferInfo3;
                                                                                    i30 = i17;
                                                                                    z12 = z6;
                                                                                    z14 = z17;
                                                                                    byteBufferArr = byteBufferArr4;
                                                                                }
                                                                            }
                                                                        }
                                                                        str8 = str;
                                                                        if ((bufferInfo3.flags & 4) != 0) {
                                                                        }
                                                                        i31 = i19;
                                                                        selectTrack = i5;
                                                                        z13 = z7;
                                                                        byteBufferArr6 = byteBufferArr5;
                                                                        z5 = z16;
                                                                        str = str8;
                                                                        mediaController = this;
                                                                        i29 = i34;
                                                                        bufferInfo2 = bufferInfo3;
                                                                        i30 = i17;
                                                                        z12 = z6;
                                                                        z14 = z17;
                                                                        byteBufferArr = byteBufferArr4;
                                                                    }
                                                                    z8 = true;
                                                                    mediaCodec2.releaseOutputBuffer(dequeueOutputBuffer2, z8);
                                                                    if (z8) {
                                                                    }
                                                                    str8 = str;
                                                                    if ((bufferInfo3.flags & 4) != 0) {
                                                                    }
                                                                    i31 = i19;
                                                                    selectTrack = i5;
                                                                    z13 = z7;
                                                                    byteBufferArr6 = byteBufferArr5;
                                                                    z5 = z16;
                                                                    str = str8;
                                                                    mediaController = this;
                                                                    i29 = i34;
                                                                    bufferInfo2 = bufferInfo3;
                                                                    i30 = i17;
                                                                    z12 = z6;
                                                                    z14 = z17;
                                                                    byteBufferArr = byteBufferArr4;
                                                                }
                                                            }
                                                            str8 = str;
                                                            i31 = i19;
                                                            selectTrack = i5;
                                                            z13 = z7;
                                                            byteBufferArr6 = byteBufferArr5;
                                                            z5 = z16;
                                                            str = str8;
                                                            mediaController = this;
                                                            i29 = i34;
                                                            bufferInfo2 = bufferInfo3;
                                                            i30 = i17;
                                                            z12 = z6;
                                                            z14 = z17;
                                                            byteBufferArr = byteBufferArr4;
                                                        }
                                                        z17 = false;
                                                        i31 = i19;
                                                        selectTrack = i5;
                                                        z13 = z7;
                                                        byteBufferArr6 = byteBufferArr5;
                                                        z5 = z16;
                                                        str = str8;
                                                        mediaController = this;
                                                        i29 = i34;
                                                        bufferInfo2 = bufferInfo3;
                                                        i30 = i17;
                                                        z12 = z6;
                                                        z14 = z17;
                                                        byteBufferArr = byteBufferArr4;
                                                    }
                                                } catch (Exception e19) {
                                                    e = e19;
                                                    str3 = str;
                                                }
                                            }
                                        }
                                    }
                                    str5 = str;
                                    bufferInfo3 = bufferInfo2;
                                    outputSurface2 = outputSurface;
                                    i6 = selectTrack;
                                    z3 = false;
                                } catch (Exception e20) {
                                    e = e20;
                                    str3 = str;
                                    bufferInfo3 = bufferInfo2;
                                    i5 = selectTrack;
                                    Log.e("tmessages", e.getMessage());
                                    outputSurface2 = outputSurface;
                                    i6 = i5;
                                    z3 = true;
                                    str5 = str3;
                                    mediaExtractor2.unselectTrack(i6);
                                    if (outputSurface2 != null) {
                                    }
                                    if (inputSurface != null) {
                                    }
                                    if (mediaCodec2 != null) {
                                    }
                                    if (mediaCodec != null) {
                                    }
                                    z = z3;
                                    j = -1;
                                    file5 = file5;
                                    str3 = str5;
                                    i = i;
                                    if (!z) {
                                    }
                                    mediaExtractor2.release();
                                    if (file5 != 0) {
                                    }
                                    Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                                    r45 = str3;
                                    file4 = i;
                                    didWriteData(true, z);
                                    cachedFile = file4;
                                    Log.e("ViratPath", this.path + "");
                                    Log.e("ViratPath", file4.getPath() + "");
                                    Log.e("ViratPath", r45.getPath() + "");
                                    return true;
                                }
                                mediaExtractor2.unselectTrack(i6);
                                if (outputSurface2 != null) {
                                    outputSurface2.release();
                                }
                                if (inputSurface != null) {
                                    inputSurface.release();
                                }
                                if (mediaCodec2 != null) {
                                    mediaCodec2.stop();
                                    mediaCodec2.release();
                                }
                                if (mediaCodec != null) {
                                    mediaCodec.stop();
                                    mediaCodec.release();
                                }
                            } else {
                                str5 = str;
                                bufferInfo3 = bufferInfo2;
                                z3 = false;
                            }
                            z = z3;
                            j = -1;
                            file5 = file5;
                            str3 = str5;
                            i = i;
                            if (!z) {
                            }
                            mediaExtractor2.release();
                            if (file5 != 0) {
                            }
                            Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                            r45 = str3;
                            file4 = i;
                            didWriteData(true, z);
                            cachedFile = file4;
                            Log.e("ViratPath", this.path + "");
                            Log.e("ViratPath", file4.getPath() + "");
                            Log.e("ViratPath", r45.getPath() + "");
                            return true;
                        } catch (Throwable th7) {
                            th = th7;
                            th = th;
                            mP4Builder2 = file5;
                            if (mediaExtractor2 != null) {
                            }
                            if (mP4Builder2 != null) {
                            }
                            Log.i("tmessages", "time = " + (System.currentTimeMillis() - currentTimeMillis));
                            throw th;
                        }
                    }
                    mediaController.didWriteData(true, true);
                    return false;
                } else if (intValue == 270) {
                    intValue = 0;
                    i4 = 90;
                    file2 = new File(mediaController.path);
                    if (file2.canRead()) {
                    }
                }
            }
            i4 = 0;
            int i242 = i22;
            i22 = i21;
            i21 = i242;
            file2 = new File(mediaController.path);
            if (file2.canRead()) {
            }
        } else {
            intValue = 90;
        }
        i4 = 270;
        file2 = new File(mediaController.path);
        if (file2.canRead()) {
        }
    }

    public static void copyFile(File file, File file2) throws IOException {
        FileChannel channel = new FileInputStream(file).getChannel();
        FileChannel channel2 = new FileOutputStream(file2).getChannel();
        try {
            channel.transferTo(1L, channel.size(), channel2);
        } finally {
            if (channel != null) {
                channel.close();
            }
            if (channel2 != null) {
                channel2.close();
            }
        }
    }
}
