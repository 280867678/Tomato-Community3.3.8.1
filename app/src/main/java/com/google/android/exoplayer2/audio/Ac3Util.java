package com.google.android.exoplayer2.audio;

import android.support.p006v8.renderscript.ScriptIntrinsicBLAS;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.tencent.ugc.TXRecordCommon;
import com.tomatolive.library.utils.ConstantUtils;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Ac3Util {
    private static final int[] BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD = {1, 2, 3, 6};
    private static final int[] SAMPLE_RATE_BY_FSCOD = {TXRecordCommon.AUDIO_SAMPLERATE_48000, TXRecordCommon.AUDIO_SAMPLERATE_44100, TXRecordCommon.AUDIO_SAMPLERATE_32000};
    private static final int[] SAMPLE_RATE_BY_FSCOD2 = {24000, 22050, TXRecordCommon.AUDIO_SAMPLERATE_16000};
    private static final int[] CHANNEL_COUNT_BY_ACMOD = {2, 1, 2, 3, 3, 4, 4, 5};
    private static final int[] BITRATE_BY_HALF_FRMSIZECOD = {32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 384, 448, 512, 576, 640};
    private static final int[] SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1 = {69, 87, 104, ScriptIntrinsicBLAS.UPPER, 139, 174, 208, 243, ConstantUtils.PK_TYPE_INVITE, 348, 417, 487, 557, 696, 835, 975, 1114, 1253, 1393};

    public static int getAc3SyncframeAudioSampleCount() {
        return 1536;
    }

    /* loaded from: classes2.dex */
    public static final class SyncFrameInfo {
        public final int channelCount;
        public final int frameSize;
        public final String mimeType;
        public final int sampleCount;
        public final int sampleRate;

        private SyncFrameInfo(String str, int i, int i2, int i3, int i4, int i5) {
            this.mimeType = str;
            this.channelCount = i2;
            this.sampleRate = i3;
            this.frameSize = i4;
            this.sampleCount = i5;
        }
    }

    public static Format parseAc3AnnexFFormat(ParsableByteArray parsableByteArray, String str, String str2, DrmInitData drmInitData) {
        int i = SAMPLE_RATE_BY_FSCOD[(parsableByteArray.readUnsignedByte() & 192) >> 6];
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = CHANNEL_COUNT_BY_ACMOD[(readUnsignedByte & 56) >> 3];
        if ((readUnsignedByte & 4) != 0) {
            i2++;
        }
        return Format.createAudioSampleFormat(str, "audio/ac3", null, -1, -1, i2, i, null, drmInitData, 0, str2);
    }

    public static Format parseEAc3AnnexFFormat(ParsableByteArray parsableByteArray, String str, String str2, DrmInitData drmInitData) {
        parsableByteArray.skipBytes(2);
        int i = SAMPLE_RATE_BY_FSCOD[(parsableByteArray.readUnsignedByte() & 192) >> 6];
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = CHANNEL_COUNT_BY_ACMOD[(readUnsignedByte & 14) >> 1];
        if ((readUnsignedByte & 1) != 0) {
            i2++;
        }
        if (((parsableByteArray.readUnsignedByte() & 30) >> 1) > 0 && (2 & parsableByteArray.readUnsignedByte()) != 0) {
            i2 += 2;
        }
        return Format.createAudioSampleFormat(str, (parsableByteArray.bytesLeft() <= 0 || (parsableByteArray.readUnsignedByte() & 1) == 0) ? "audio/eac3" : "audio/eac3-joc", null, -1, -1, i2, i, null, drmInitData, 0, str2);
    }

    public static SyncFrameInfo parseAc3SyncframeInfo(ParsableBitArray parsableBitArray) {
        String str;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int readBits;
        int i6;
        int i7;
        int i8;
        int position = parsableBitArray.getPosition();
        parsableBitArray.skipBits(40);
        boolean z = parsableBitArray.readBits(5) == 16;
        parsableBitArray.setPosition(position);
        int i9 = -1;
        if (z) {
            parsableBitArray.skipBits(16);
            int readBits2 = parsableBitArray.readBits(2);
            if (readBits2 == 0) {
                i9 = 0;
            } else if (readBits2 == 1) {
                i9 = 1;
            } else if (readBits2 == 2) {
                i9 = 2;
            }
            parsableBitArray.skipBits(3);
            int readBits3 = (parsableBitArray.readBits(11) + 1) * 2;
            int readBits4 = parsableBitArray.readBits(2);
            if (readBits4 == 3) {
                i7 = SAMPLE_RATE_BY_FSCOD2[parsableBitArray.readBits(2)];
                readBits = 3;
                i6 = 6;
            } else {
                readBits = parsableBitArray.readBits(2);
                i6 = BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[readBits];
                i7 = SAMPLE_RATE_BY_FSCOD[readBits4];
            }
            int i10 = i6 * 256;
            int readBits5 = parsableBitArray.readBits(3);
            boolean readBit = parsableBitArray.readBit();
            int i11 = CHANNEL_COUNT_BY_ACMOD[readBits5] + (readBit ? 1 : 0);
            parsableBitArray.skipBits(10);
            if (parsableBitArray.readBit()) {
                parsableBitArray.skipBits(8);
            }
            if (readBits5 == 0) {
                parsableBitArray.skipBits(5);
                if (parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(8);
                }
            }
            if (i9 == 1 && parsableBitArray.readBit()) {
                parsableBitArray.skipBits(16);
            }
            if (parsableBitArray.readBit()) {
                if (readBits5 > 2) {
                    parsableBitArray.skipBits(2);
                }
                if ((readBits5 & 1) != 0 && readBits5 > 2) {
                    parsableBitArray.skipBits(6);
                }
                if ((readBits5 & 4) != 0) {
                    parsableBitArray.skipBits(6);
                }
                if (readBit && parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(5);
                }
                if (i9 == 0) {
                    if (parsableBitArray.readBit()) {
                        parsableBitArray.skipBits(6);
                    }
                    if (readBits5 == 0 && parsableBitArray.readBit()) {
                        parsableBitArray.skipBits(6);
                    }
                    if (parsableBitArray.readBit()) {
                        parsableBitArray.skipBits(6);
                    }
                    int readBits6 = parsableBitArray.readBits(2);
                    if (readBits6 == 1) {
                        parsableBitArray.skipBits(5);
                    } else if (readBits6 == 2) {
                        parsableBitArray.skipBits(12);
                    } else if (readBits6 == 3) {
                        int readBits7 = parsableBitArray.readBits(5);
                        if (parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(5);
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(4);
                                }
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(4);
                                }
                            }
                        }
                        if (parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(5);
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(7);
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(8);
                                }
                            }
                        }
                        parsableBitArray.skipBits((readBits7 + 2) * 8);
                        parsableBitArray.byteAlign();
                    }
                    if (readBits5 < 2) {
                        if (parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(14);
                        }
                        if (readBits5 == 0 && parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(14);
                        }
                    }
                    if (parsableBitArray.readBit()) {
                        if (readBits == 0) {
                            parsableBitArray.skipBits(5);
                        } else {
                            for (int i12 = 0; i12 < i6; i12++) {
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(5);
                                }
                            }
                        }
                    }
                }
            }
            if (parsableBitArray.readBit()) {
                parsableBitArray.skipBits(5);
                if (readBits5 == 2) {
                    parsableBitArray.skipBits(4);
                }
                if (readBits5 >= 6) {
                    parsableBitArray.skipBits(2);
                }
                if (parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(8);
                }
                if (readBits5 == 0 && parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(8);
                }
                i8 = 3;
                if (readBits4 < 3) {
                    parsableBitArray.skipBit();
                }
            } else {
                i8 = 3;
            }
            if (i9 == 0 && readBits != i8) {
                parsableBitArray.skipBit();
            }
            if (i9 == 2 && (readBits == i8 || parsableBitArray.readBit())) {
                parsableBitArray.skipBits(6);
            }
            str = (parsableBitArray.readBit() && parsableBitArray.readBits(6) == 1 && parsableBitArray.readBits(8) == 1) ? "audio/eac3-joc" : "audio/eac3";
            i4 = i9;
            i = readBits3;
            i2 = i7;
            i5 = i10;
            i3 = i11;
        } else {
            parsableBitArray.skipBits(32);
            int readBits8 = parsableBitArray.readBits(2);
            int ac3SyncframeSize = getAc3SyncframeSize(readBits8, parsableBitArray.readBits(6));
            parsableBitArray.skipBits(8);
            int readBits9 = parsableBitArray.readBits(3);
            if ((readBits9 & 1) != 0 && readBits9 != 1) {
                parsableBitArray.skipBits(2);
            }
            if ((readBits9 & 4) != 0) {
                parsableBitArray.skipBits(2);
            }
            if (readBits9 == 2) {
                parsableBitArray.skipBits(2);
            }
            str = "audio/ac3";
            i = ac3SyncframeSize;
            i2 = SAMPLE_RATE_BY_FSCOD[readBits8];
            i3 = CHANNEL_COUNT_BY_ACMOD[readBits9] + (parsableBitArray.readBit() ? 1 : 0);
            i4 = -1;
            i5 = 1536;
        }
        return new SyncFrameInfo(str, i4, i3, i2, i, i5);
    }

    public static int parseAc3SyncframeSize(byte[] bArr) {
        if (bArr.length < 5) {
            return -1;
        }
        return getAc3SyncframeSize((bArr[4] & 192) >> 6, bArr[4] & 63);
    }

    public static int parseEAc3SyncframeAudioSampleCount(ByteBuffer byteBuffer) {
        int i = 6;
        if (((byteBuffer.get(byteBuffer.position() + 4) & 192) >> 6) != 3) {
            i = BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[(byteBuffer.get(byteBuffer.position() + 4) & 48) >> 4];
        }
        return i * 256;
    }

    public static int findTrueHdSyncframeOffset(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        int limit = byteBuffer.limit() - 10;
        for (int i = position; i <= limit; i++) {
            if ((byteBuffer.getInt(i + 4) & (-16777217)) == -1167101192) {
                return i - position;
            }
        }
        return -1;
    }

    public static int parseTrueHdSyncframeAudioSampleCount(byte[] bArr) {
        boolean z = false;
        if (bArr[4] == -8 && bArr[5] == 114 && bArr[6] == 111 && (bArr[7] & 254) == 186) {
            if ((bArr[7] & 255) == 187) {
                z = true;
            }
            return 40 << ((bArr[z ? '\t' : '\b'] >> 4) & 7);
        }
        return 0;
    }

    public static int parseTrueHdSyncframeAudioSampleCount(ByteBuffer byteBuffer, int i) {
        return 40 << ((byteBuffer.get((byteBuffer.position() + i) + ((byteBuffer.get((byteBuffer.position() + i) + 7) & 255) == 187 ? 9 : 8)) >> 4) & 7);
    }

    private static int getAc3SyncframeSize(int i, int i2) {
        int i3 = i2 / 2;
        if (i >= 0) {
            int[] iArr = SAMPLE_RATE_BY_FSCOD;
            if (i >= iArr.length || i2 < 0) {
                return -1;
            }
            int[] iArr2 = SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1;
            if (i3 >= iArr2.length) {
                return -1;
            }
            int i4 = iArr[i];
            if (i4 == 44100) {
                return (iArr2[i3] + (i2 % 2)) * 2;
            }
            int i5 = BITRATE_BY_HALF_FRMSIZECOD[i3];
            return i4 == 32000 ? i5 * 6 : i5 * 4;
        }
        return -1;
    }
}
