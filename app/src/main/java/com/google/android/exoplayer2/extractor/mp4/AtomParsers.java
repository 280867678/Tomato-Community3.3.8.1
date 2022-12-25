package com.google.android.exoplayer2.extractor.mp4;

import android.util.Log;
import android.util.Pair;
import com.coremedia.iso.boxes.MetaBox;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.mp4.Atom;
import com.google.android.exoplayer2.extractor.mp4.FixedSampleSizeRechunker;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.AvcConfig;
import com.google.android.exoplayer2.video.HevcConfig;
import com.iceteck.silicompressorr.videocompression.MediaController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class AtomParsers {
    private static final int TYPE_vide = Util.getIntegerCodeForString("vide");
    private static final int TYPE_soun = Util.getIntegerCodeForString("soun");
    private static final int TYPE_text = Util.getIntegerCodeForString("text");
    private static final int TYPE_sbtl = Util.getIntegerCodeForString("sbtl");
    private static final int TYPE_subt = Util.getIntegerCodeForString("subt");
    private static final int TYPE_clcp = Util.getIntegerCodeForString("clcp");
    private static final int TYPE_meta = Util.getIntegerCodeForString(MetaBox.TYPE);

    /* loaded from: classes2.dex */
    private interface SampleSizeBox {
        int getSampleCount();

        boolean isFixedSampleSize();

        int readNextSampleSize();
    }

    /* loaded from: classes2.dex */
    public static final class UnhandledEditListException extends ParserException {
    }

    public static Track parseTrak(Atom.ContainerAtom containerAtom, Atom.LeafAtom leafAtom, long j, DrmInitData drmInitData, boolean z, boolean z2) throws ParserException {
        Atom.LeafAtom leafAtom2;
        long j2;
        long[] jArr;
        long[] jArr2;
        Atom.ContainerAtom containerAtomOfType = containerAtom.getContainerAtomOfType(Atom.TYPE_mdia);
        int parseHdlr = parseHdlr(containerAtomOfType.getLeafAtomOfType(Atom.TYPE_hdlr).data);
        if (parseHdlr == -1) {
            return null;
        }
        TkhdData parseTkhd = parseTkhd(containerAtom.getLeafAtomOfType(Atom.TYPE_tkhd).data);
        long j3 = -9223372036854775807L;
        if (j == -9223372036854775807L) {
            j2 = parseTkhd.duration;
            leafAtom2 = leafAtom;
        } else {
            leafAtom2 = leafAtom;
            j2 = j;
        }
        long parseMvhd = parseMvhd(leafAtom2.data);
        if (j2 != -9223372036854775807L) {
            j3 = Util.scaleLargeTimestamp(j2, 1000000L, parseMvhd);
        }
        long j4 = j3;
        Atom.ContainerAtom containerAtomOfType2 = containerAtomOfType.getContainerAtomOfType(Atom.TYPE_minf).getContainerAtomOfType(Atom.TYPE_stbl);
        Pair<Long, String> parseMdhd = parseMdhd(containerAtomOfType.getLeafAtomOfType(Atom.TYPE_mdhd).data);
        StsdData parseStsd = parseStsd(containerAtomOfType2.getLeafAtomOfType(Atom.TYPE_stsd).data, parseTkhd.f1315id, parseTkhd.rotationDegrees, (String) parseMdhd.second, drmInitData, z2);
        if (!z) {
            Pair<long[], long[]> parseEdts = parseEdts(containerAtom.getContainerAtomOfType(Atom.TYPE_edts));
            jArr2 = (long[]) parseEdts.second;
            jArr = (long[]) parseEdts.first;
        } else {
            jArr = null;
            jArr2 = null;
        }
        if (parseStsd.format != null) {
            return new Track(parseTkhd.f1315id, parseHdlr, ((Long) parseMdhd.first).longValue(), parseMvhd, j4, parseStsd.format, parseStsd.requiredSampleTransformation, parseStsd.trackEncryptionBoxes, parseStsd.nalUnitLengthFieldLength, jArr, jArr2);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x0341  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x034f  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0386 A[EDGE_INSN: B:135:0x0386->B:136:0x0386 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x039b  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x03a1  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x046a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:185:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0397  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0343  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static TrackSampleTable parseStbl(Track track, Atom.ContainerAtom containerAtom, GaplessInfoHolder gaplessInfoHolder) throws ParserException {
        SampleSizeBox stz2SampleSizeBox;
        boolean z;
        int i;
        int i2;
        int i3;
        String str;
        long[] jArr;
        long[] jArr2;
        long j;
        int[] iArr;
        int i4;
        int[] iArr2;
        int[] iArr3;
        long j2;
        long[] jArr3;
        int i5;
        long[] jArr4;
        String str2;
        boolean z2;
        int[] iArr4;
        long[] jArr5;
        int i6;
        long[] jArr6;
        int i7;
        int[] iArr5;
        String str3;
        String str4;
        int i8;
        int i9;
        int i10;
        int i11;
        Track track2 = track;
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_stsz);
        if (leafAtomOfType != null) {
            stz2SampleSizeBox = new StszSampleSizeBox(leafAtomOfType);
        } else {
            Atom.LeafAtom leafAtomOfType2 = containerAtom.getLeafAtomOfType(Atom.TYPE_stz2);
            if (leafAtomOfType2 == null) {
                throw new ParserException("Track has no sample table size information");
            }
            stz2SampleSizeBox = new Stz2SampleSizeBox(leafAtomOfType2);
        }
        int sampleCount = stz2SampleSizeBox.getSampleCount();
        if (sampleCount == 0) {
            return new TrackSampleTable(track, new long[0], new int[0], 0, new long[0], new int[0], -9223372036854775807L);
        }
        Atom.LeafAtom leafAtomOfType3 = containerAtom.getLeafAtomOfType(Atom.TYPE_stco);
        if (leafAtomOfType3 == null) {
            leafAtomOfType3 = containerAtom.getLeafAtomOfType(Atom.TYPE_co64);
            z = true;
        } else {
            z = false;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType3.data;
        ParsableByteArray parsableByteArray2 = containerAtom.getLeafAtomOfType(Atom.TYPE_stsc).data;
        ParsableByteArray parsableByteArray3 = containerAtom.getLeafAtomOfType(Atom.TYPE_stts).data;
        Atom.LeafAtom leafAtomOfType4 = containerAtom.getLeafAtomOfType(Atom.TYPE_stss);
        ParsableByteArray parsableByteArray4 = leafAtomOfType4 != null ? leafAtomOfType4.data : null;
        Atom.LeafAtom leafAtomOfType5 = containerAtom.getLeafAtomOfType(Atom.TYPE_ctts);
        ParsableByteArray parsableByteArray5 = leafAtomOfType5 != null ? leafAtomOfType5.data : null;
        ChunkIterator chunkIterator = new ChunkIterator(parsableByteArray2, parsableByteArray, z);
        parsableByteArray3.setPosition(12);
        int readUnsignedIntToInt = parsableByteArray3.readUnsignedIntToInt() - 1;
        int readUnsignedIntToInt2 = parsableByteArray3.readUnsignedIntToInt();
        int readUnsignedIntToInt3 = parsableByteArray3.readUnsignedIntToInt();
        if (parsableByteArray5 != null) {
            parsableByteArray5.setPosition(12);
            i = parsableByteArray5.readUnsignedIntToInt();
        } else {
            i = 0;
        }
        int i12 = -1;
        if (parsableByteArray4 != null) {
            parsableByteArray4.setPosition(12);
            i2 = parsableByteArray4.readUnsignedIntToInt();
            if (i2 > 0) {
                i12 = parsableByteArray4.readUnsignedIntToInt() - 1;
            } else {
                parsableByteArray4 = null;
            }
        } else {
            i2 = 0;
        }
        long j3 = 0;
        if (!(stz2SampleSizeBox.isFixedSampleSize() && "audio/raw".equals(track2.format.sampleMimeType) && readUnsignedIntToInt == 0 && i == 0 && i2 == 0)) {
            long[] jArr7 = new long[sampleCount];
            iArr = new int[sampleCount];
            int i13 = i2;
            jArr2 = new long[sampleCount];
            int[] iArr6 = new int[sampleCount];
            int i14 = i13;
            int i15 = readUnsignedIntToInt2;
            int i16 = readUnsignedIntToInt3;
            int i17 = i;
            int i18 = i12;
            long j4 = 0;
            int i19 = readUnsignedIntToInt;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            int i24 = 0;
            long j5 = 0;
            while (i21 < sampleCount) {
                while (i23 == 0) {
                    Assertions.checkState(chunkIterator.moveNext());
                    j5 = chunkIterator.offset;
                    i23 = chunkIterator.numSamples;
                    sampleCount = sampleCount;
                    i14 = i14;
                }
                int i25 = i14;
                int i26 = sampleCount;
                if (parsableByteArray5 != null) {
                    while (i22 == 0 && i17 > 0) {
                        i22 = parsableByteArray5.readUnsignedIntToInt();
                        i24 = parsableByteArray5.readInt();
                        i17--;
                    }
                    i22--;
                }
                int i27 = i24;
                jArr7[i21] = j5;
                iArr[i21] = stz2SampleSizeBox.readNextSampleSize();
                if (iArr[i21] > i20) {
                    i20 = iArr[i21];
                }
                SampleSizeBox sampleSizeBox = stz2SampleSizeBox;
                jArr2[i21] = i27 + j4;
                iArr6[i21] = parsableByteArray4 == null ? 1 : 0;
                if (i21 == i18) {
                    iArr6[i21] = 1;
                    i11 = i25 - 1;
                    if (i11 > 0) {
                        i18 = parsableByteArray4.readUnsignedIntToInt() - 1;
                    }
                    i9 = i20;
                    i10 = i27;
                } else {
                    i9 = i20;
                    i10 = i27;
                    i11 = i25;
                }
                j4 += i16;
                i15--;
                int i28 = i19;
                if (i15 != 0 || i28 <= 0) {
                    i19 = i28;
                } else {
                    int readUnsignedIntToInt4 = parsableByteArray3.readUnsignedIntToInt();
                    i16 = parsableByteArray3.readInt();
                    i19 = i28 - 1;
                    i15 = readUnsignedIntToInt4;
                }
                j5 += iArr[i21];
                i23--;
                i21++;
                i14 = i11;
                stz2SampleSizeBox = sampleSizeBox;
                i20 = i9;
                i24 = i10;
                sampleCount = i26;
            }
            int i29 = i14;
            i3 = sampleCount;
            int i30 = i19;
            j = j4 + i24;
            Assertions.checkArgument(i22 == 0);
            while (i17 > 0) {
                Assertions.checkArgument(parsableByteArray5.readUnsignedIntToInt() == 0);
                parsableByteArray5.readInt();
                i17--;
            }
            if (i29 == 0 && i15 == 0 && i23 == 0 && i30 == 0) {
                str = "AtomParsers";
                i8 = i20;
                track2 = track;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Inconsistent stbl box for track ");
                i8 = i20;
                track2 = track;
                sb.append(track2.f1316id);
                sb.append(": remainingSynchronizationSamples ");
                sb.append(i29);
                sb.append(", remainingSamplesAtTimestampDelta ");
                sb.append(i15);
                sb.append(", remainingSamplesInChunk ");
                sb.append(i23);
                sb.append(", remainingTimestampDeltaChanges ");
                sb.append(i30);
                str = "AtomParsers";
                Log.w(str, sb.toString());
            }
            jArr = jArr7;
            iArr2 = iArr6;
            i4 = i8;
        } else {
            i3 = sampleCount;
            str = "AtomParsers";
            int i31 = chunkIterator.length;
            long[] jArr8 = new long[i31];
            int[] iArr7 = new int[i31];
            while (chunkIterator.moveNext()) {
                int i32 = chunkIterator.index;
                jArr8[i32] = chunkIterator.offset;
                iArr7[i32] = chunkIterator.numSamples;
            }
            Format format = track2.format;
            FixedSampleSizeRechunker.Results rechunk = FixedSampleSizeRechunker.rechunk(Util.getPcmFrameSize(format.pcmEncoding, format.channelCount), jArr8, iArr7, readUnsignedIntToInt3);
            jArr = rechunk.offsets;
            int[] iArr8 = rechunk.sizes;
            int i33 = rechunk.maximumSize;
            jArr2 = rechunk.timestamps;
            int[] iArr9 = rechunk.flags;
            j = rechunk.duration;
            iArr = iArr8;
            i4 = i33;
            iArr2 = iArr9;
        }
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(j, 1000000L, track2.timescale);
        if (track2.editListDurations == null || gaplessInfoHolder.hasGaplessInfo()) {
            int[] iArr10 = iArr2;
            Util.scaleLargeTimestampsInPlace(jArr2, 1000000L, track2.timescale);
            return new TrackSampleTable(track, jArr, iArr, i4, jArr2, iArr10, scaleLargeTimestamp);
        }
        long[] jArr9 = track2.editListDurations;
        if (jArr9.length == 1 && track2.type == 1 && jArr2.length >= 2) {
            long j6 = track2.editListMediaTimes[0];
            long scaleLargeTimestamp2 = j6 + Util.scaleLargeTimestamp(jArr9[0], track2.timescale, track2.movieTimescale);
            if (canApplyEditWithGaplessInfo(jArr2, j, j6, scaleLargeTimestamp2)) {
                long j7 = j - scaleLargeTimestamp2;
                long scaleLargeTimestamp3 = Util.scaleLargeTimestamp(j6 - jArr2[0], track2.format.sampleRate, track2.timescale);
                j2 = j;
                long scaleLargeTimestamp4 = Util.scaleLargeTimestamp(j7, track2.format.sampleRate, track2.timescale);
                if ((scaleLargeTimestamp3 != 0 || scaleLargeTimestamp4 != 0) && scaleLargeTimestamp3 <= 2147483647L && scaleLargeTimestamp4 <= 2147483647L) {
                    gaplessInfoHolder.encoderDelay = (int) scaleLargeTimestamp3;
                    gaplessInfoHolder.encoderPadding = (int) scaleLargeTimestamp4;
                    Util.scaleLargeTimestampsInPlace(jArr2, 1000000L, track2.timescale);
                    return new TrackSampleTable(track, jArr, iArr, i4, jArr2, iArr2, scaleLargeTimestamp);
                }
                iArr3 = iArr2;
                jArr3 = track2.editListDurations;
                if (jArr3.length != 1 && jArr3[0] == 0) {
                    long j8 = track2.editListMediaTimes[0];
                    for (int i34 = 0; i34 < jArr2.length; i34++) {
                        jArr2[i34] = Util.scaleLargeTimestamp(jArr2[i34] - j8, 1000000L, track2.timescale);
                    }
                    return new TrackSampleTable(track, jArr, iArr, i4, jArr2, iArr3, Util.scaleLargeTimestamp(j2 - j8, 1000000L, track2.timescale));
                }
                boolean z3 = track2.type != 1;
                i5 = 0;
                boolean z4 = false;
                int i35 = 0;
                int i36 = 0;
                while (true) {
                    jArr4 = track2.editListDurations;
                    if (i5 < jArr4.length) {
                        break;
                    }
                    String str5 = str;
                    long[] jArr10 = jArr;
                    long j9 = track2.editListMediaTimes[i5];
                    if (j9 != -1) {
                        long scaleLargeTimestamp5 = Util.scaleLargeTimestamp(jArr4[i5], track2.timescale, track2.movieTimescale);
                        int binarySearchCeil = Util.binarySearchCeil(jArr2, j9, true, true);
                        int binarySearchCeil2 = Util.binarySearchCeil(jArr2, j9 + scaleLargeTimestamp5, z3, false);
                        i35 += binarySearchCeil2 - binarySearchCeil;
                        boolean z5 = i36 != binarySearchCeil;
                        i36 = binarySearchCeil2;
                        z4 = z5 | z4;
                    }
                    i5++;
                    jArr = jArr10;
                    str = str5;
                }
                str2 = str;
                long[] jArr11 = jArr;
                z2 = (i35 == i3) | z4;
                long[] jArr12 = !z2 ? new long[i35] : jArr11;
                iArr4 = !z2 ? new int[i35] : iArr;
                if (z2) {
                    i4 = 0;
                }
                int[] iArr11 = !z2 ? new int[i35] : iArr3;
                jArr5 = new long[i35];
                int i37 = i4;
                i6 = 0;
                int i38 = 0;
                while (true) {
                    jArr6 = track2.editListDurations;
                    if (i6 >= jArr6.length) {
                        long[] jArr13 = jArr5;
                        int i39 = i37;
                        long j10 = track2.editListMediaTimes[i6];
                        long j11 = jArr6[i6];
                        if (j10 != -1) {
                            i7 = i6;
                            int[] iArr12 = iArr4;
                            int binarySearchCeil3 = Util.binarySearchCeil(jArr2, j10, true, true);
                            int binarySearchCeil4 = Util.binarySearchCeil(jArr2, Util.scaleLargeTimestamp(j11, track2.timescale, track2.movieTimescale) + j10, z3, false);
                            if (z2) {
                                int i40 = binarySearchCeil4 - binarySearchCeil3;
                                System.arraycopy(jArr11, binarySearchCeil3, jArr12, i38, i40);
                                iArr5 = iArr12;
                                System.arraycopy(iArr, binarySearchCeil3, iArr5, i38, i40);
                                System.arraycopy(iArr3, binarySearchCeil3, iArr11, i38, i40);
                            } else {
                                iArr5 = iArr12;
                            }
                            if (binarySearchCeil3 >= binarySearchCeil4) {
                                str4 = str2;
                            } else if ((iArr11[i38] & 1) == 0) {
                                Log.w(str2, "Ignoring edit list: edit does not start with a sync sample.");
                                throw new UnhandledEditListException();
                            } else {
                                str4 = str2;
                            }
                            int i41 = i38;
                            int i42 = i39;
                            while (binarySearchCeil3 < binarySearchCeil4) {
                                int i43 = binarySearchCeil4;
                                String str6 = str4;
                                long j12 = j10;
                                jArr13[i41] = Util.scaleLargeTimestamp(j3, 1000000L, track2.movieTimescale) + Util.scaleLargeTimestamp(jArr2[binarySearchCeil3] - j10, 1000000L, track2.timescale);
                                if (z2 && iArr5[i41] > i42) {
                                    i42 = iArr[binarySearchCeil3];
                                }
                                i41++;
                                binarySearchCeil3++;
                                j10 = j12;
                                binarySearchCeil4 = i43;
                                str4 = str6;
                            }
                            str3 = str4;
                            i37 = i42;
                            i38 = i41;
                        } else {
                            i7 = i6;
                            iArr5 = iArr4;
                            str3 = str2;
                            i37 = i39;
                        }
                        j3 += j11;
                        i6 = i7 + 1;
                        iArr4 = iArr5;
                        jArr5 = jArr13;
                        str2 = str3;
                    } else {
                        return new TrackSampleTable(track, jArr12, iArr4, i37, jArr5, iArr11, Util.scaleLargeTimestamp(j3, 1000000L, track2.timescale));
                    }
                }
            }
        }
        iArr3 = iArr2;
        j2 = j;
        jArr3 = track2.editListDurations;
        if (jArr3.length != 1) {
        }
        if (track2.type != 1) {
        }
        i5 = 0;
        boolean z42 = false;
        int i352 = 0;
        int i362 = 0;
        while (true) {
            jArr4 = track2.editListDurations;
            if (i5 < jArr4.length) {
            }
            i5++;
            jArr = jArr10;
            str = str5;
        }
        str2 = str;
        long[] jArr112 = jArr;
        z2 = (i352 == i3) | z42;
        if (!z2) {
        }
        if (!z2) {
        }
        if (z2) {
        }
        if (!z2) {
        }
        jArr5 = new long[i352];
        int i372 = i4;
        i6 = 0;
        int i382 = 0;
        while (true) {
            jArr6 = track2.editListDurations;
            if (i6 >= jArr6.length) {
            }
            j3 += j11;
            i6 = i7 + 1;
            iArr4 = iArr5;
            jArr5 = jArr13;
            str2 = str3;
        }
    }

    public static Metadata parseUdta(Atom.LeafAtom leafAtom, boolean z) {
        if (z) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtom.data;
        parsableByteArray.setPosition(8);
        while (parsableByteArray.bytesLeft() >= 8) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == Atom.TYPE_meta) {
                parsableByteArray.setPosition(position);
                return parseMetaAtom(parsableByteArray, position + readInt);
            }
            parsableByteArray.skipBytes(readInt - 8);
        }
        return null;
    }

    private static Metadata parseMetaAtom(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(12);
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == Atom.TYPE_ilst) {
                parsableByteArray.setPosition(position);
                return parseIlst(parsableByteArray, position + readInt);
            }
            parsableByteArray.skipBytes(readInt - 8);
        }
        return null;
    }

    private static Metadata parseIlst(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray.getPosition() < i) {
            Metadata.Entry parseIlstElement = MetadataUtil.parseIlstElement(parsableByteArray);
            if (parseIlstElement != null) {
                arrayList.add(parseIlstElement);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    private static long parseMvhd(ParsableByteArray parsableByteArray) {
        int i = 8;
        parsableByteArray.setPosition(8);
        if (Atom.parseFullAtomVersion(parsableByteArray.readInt()) != 0) {
            i = 16;
        }
        parsableByteArray.skipBytes(i);
        return parsableByteArray.readUnsignedInt();
    }

    private static TkhdData parseTkhd(ParsableByteArray parsableByteArray) {
        boolean z;
        int i = 8;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 8 : 16);
        int readInt = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int position = parsableByteArray.getPosition();
        if (parseFullAtomVersion == 0) {
            i = 4;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= i) {
                z = true;
                break;
            } else if (parsableByteArray.data[position + i3] != -1) {
                z = false;
                break;
            } else {
                i3++;
            }
        }
        long j = -9223372036854775807L;
        if (z) {
            parsableByteArray.skipBytes(i);
        } else {
            long readUnsignedInt = parseFullAtomVersion == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
            if (readUnsignedInt != 0) {
                j = readUnsignedInt;
            }
        }
        parsableByteArray.skipBytes(16);
        int readInt2 = parsableByteArray.readInt();
        int readInt3 = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int readInt4 = parsableByteArray.readInt();
        int readInt5 = parsableByteArray.readInt();
        if (readInt2 == 0 && readInt3 == 65536 && readInt4 == -65536 && readInt5 == 0) {
            i2 = 90;
        } else if (readInt2 == 0 && readInt3 == -65536 && readInt4 == 65536 && readInt5 == 0) {
            i2 = 270;
        } else if (readInt2 == -65536 && readInt3 == 0 && readInt4 == 0 && readInt5 == -65536) {
            i2 = 180;
        }
        return new TkhdData(readInt, j, i2);
    }

    private static int parseHdlr(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(16);
        int readInt = parsableByteArray.readInt();
        if (readInt == TYPE_soun) {
            return 1;
        }
        if (readInt == TYPE_vide) {
            return 2;
        }
        if (readInt == TYPE_text || readInt == TYPE_sbtl || readInt == TYPE_subt || readInt == TYPE_clcp) {
            return 3;
        }
        return readInt == TYPE_meta ? 4 : -1;
    }

    private static Pair<Long, String> parseMdhd(ParsableByteArray parsableByteArray) {
        int i = 8;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 8 : 16);
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        if (parseFullAtomVersion == 0) {
            i = 4;
        }
        parsableByteArray.skipBytes(i);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        return Pair.create(Long.valueOf(readUnsignedInt), "" + ((char) (((readUnsignedShort >> 10) & 31) + 96)) + ((char) (((readUnsignedShort >> 5) & 31) + 96)) + ((char) ((readUnsignedShort & 31) + 96)));
    }

    private static StsdData parseStsd(ParsableByteArray parsableByteArray, int i, int i2, String str, DrmInitData drmInitData, boolean z) throws ParserException {
        parsableByteArray.setPosition(12);
        int readInt = parsableByteArray.readInt();
        StsdData stsdData = new StsdData(readInt);
        for (int i3 = 0; i3 < readInt; i3++) {
            int position = parsableByteArray.getPosition();
            int readInt2 = parsableByteArray.readInt();
            Assertions.checkArgument(readInt2 > 0, "childAtomSize should be positive");
            int readInt3 = parsableByteArray.readInt();
            if (readInt3 == Atom.TYPE_avc1 || readInt3 == Atom.TYPE_avc3 || readInt3 == Atom.TYPE_encv || readInt3 == Atom.TYPE_mp4v || readInt3 == Atom.TYPE_hvc1 || readInt3 == Atom.TYPE_hev1 || readInt3 == Atom.TYPE_s263 || readInt3 == Atom.TYPE_vp08 || readInt3 == Atom.TYPE_vp09) {
                parseVideoSampleEntry(parsableByteArray, readInt3, position, readInt2, i, i2, drmInitData, stsdData, i3);
            } else if (readInt3 == Atom.TYPE_mp4a || readInt3 == Atom.TYPE_enca || readInt3 == Atom.TYPE_ac_3 || readInt3 == Atom.TYPE_ec_3 || readInt3 == Atom.TYPE_dtsc || readInt3 == Atom.TYPE_dtse || readInt3 == Atom.TYPE_dtsh || readInt3 == Atom.TYPE_dtsl || readInt3 == Atom.TYPE_samr || readInt3 == Atom.TYPE_sawb || readInt3 == Atom.TYPE_lpcm || readInt3 == Atom.TYPE_sowt || readInt3 == Atom.TYPE__mp3 || readInt3 == Atom.TYPE_alac) {
                parseAudioSampleEntry(parsableByteArray, readInt3, position, readInt2, i, str, z, drmInitData, stsdData, i3);
            } else if (readInt3 == Atom.TYPE_TTML || readInt3 == Atom.TYPE_tx3g || readInt3 == Atom.TYPE_wvtt || readInt3 == Atom.TYPE_stpp || readInt3 == Atom.TYPE_c608) {
                parseTextSampleEntry(parsableByteArray, readInt3, position, readInt2, i, str, stsdData);
            } else if (readInt3 == Atom.TYPE_camm) {
                stsdData.format = Format.createSampleFormat(Integer.toString(i), "application/x-camera-motion", null, -1, null);
            }
            parsableByteArray.setPosition(position + readInt2);
        }
        return stsdData;
    }

    private static void parseTextSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, StsdData stsdData) throws ParserException {
        parsableByteArray.setPosition(i2 + 8 + 8);
        String str2 = "application/ttml+xml";
        List list = null;
        long j = Long.MAX_VALUE;
        if (i != Atom.TYPE_TTML) {
            if (i == Atom.TYPE_tx3g) {
                int i5 = (i3 - 8) - 8;
                byte[] bArr = new byte[i5];
                parsableByteArray.readBytes(bArr, 0, i5);
                list = Collections.singletonList(bArr);
                str2 = "application/x-quicktime-tx3g";
            } else if (i == Atom.TYPE_wvtt) {
                str2 = "application/x-mp4-vtt";
            } else if (i == Atom.TYPE_stpp) {
                j = 0;
            } else if (i == Atom.TYPE_c608) {
                stsdData.requiredSampleTransformation = 1;
                str2 = "application/x-mp4-cea-608";
            } else {
                throw new IllegalStateException();
            }
        }
        stsdData.format = Format.createTextSampleFormat(Integer.toString(i4), str2, null, -1, 0, str, -1, null, j, list);
    }

    private static void parseVideoSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, int i5, DrmInitData drmInitData, StsdData stsdData, int i6) throws ParserException {
        DrmInitData drmInitData2 = drmInitData;
        parsableByteArray.setPosition(i2 + 8 + 8);
        parsableByteArray.skipBytes(16);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(50);
        int position = parsableByteArray.getPosition();
        String str = null;
        int i7 = i;
        if (i7 == Atom.TYPE_encv) {
            Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray, i2, i3);
            if (parseSampleEntryEncryptionData != null) {
                i7 = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                drmInitData2 = drmInitData2 == null ? null : drmInitData2.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                stsdData.trackEncryptionBoxes[i6] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
            }
            parsableByteArray.setPosition(position);
        }
        DrmInitData drmInitData3 = drmInitData2;
        List<byte[]> list = null;
        byte[] bArr = null;
        boolean z = false;
        float f = 1.0f;
        int i8 = -1;
        while (position - i2 < i3) {
            parsableByteArray.setPosition(position);
            int position2 = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (readInt == 0 && parsableByteArray.getPosition() - i2 == i3) {
                break;
            }
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == Atom.TYPE_avcC) {
                Assertions.checkState(str == null);
                parsableByteArray.setPosition(position2 + 8);
                AvcConfig parse = AvcConfig.parse(parsableByteArray);
                list = parse.initializationData;
                stsdData.nalUnitLengthFieldLength = parse.nalUnitLengthFieldLength;
                if (!z) {
                    f = parse.pixelWidthAspectRatio;
                }
                str = MediaController.MIME_TYPE;
            } else if (readInt2 == Atom.TYPE_hvcC) {
                Assertions.checkState(str == null);
                parsableByteArray.setPosition(position2 + 8);
                HevcConfig parse2 = HevcConfig.parse(parsableByteArray);
                list = parse2.initializationData;
                stsdData.nalUnitLengthFieldLength = parse2.nalUnitLengthFieldLength;
                str = "video/hevc";
            } else if (readInt2 == Atom.TYPE_vpcC) {
                Assertions.checkState(str == null);
                str = i7 == Atom.TYPE_vp08 ? "video/x-vnd.on2.vp8" : "video/x-vnd.on2.vp9";
            } else if (readInt2 == Atom.TYPE_d263) {
                Assertions.checkState(str == null);
                str = "video/3gpp";
            } else if (readInt2 == Atom.TYPE_esds) {
                Assertions.checkState(str == null);
                Pair<String, byte[]> parseEsdsFromParent = parseEsdsFromParent(parsableByteArray, position2);
                str = (String) parseEsdsFromParent.first;
                list = Collections.singletonList(parseEsdsFromParent.second);
            } else if (readInt2 == Atom.TYPE_pasp) {
                f = parsePaspFromParent(parsableByteArray, position2);
                z = true;
            } else if (readInt2 == Atom.TYPE_sv3d) {
                bArr = parseProjFromParent(parsableByteArray, position2, readInt);
            } else if (readInt2 == Atom.TYPE_st3d) {
                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                parsableByteArray.skipBytes(3);
                if (readUnsignedByte == 0) {
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    if (readUnsignedByte2 == 0) {
                        i8 = 0;
                    } else if (readUnsignedByte2 == 1) {
                        i8 = 1;
                    } else if (readUnsignedByte2 == 2) {
                        i8 = 2;
                    } else if (readUnsignedByte2 == 3) {
                        i8 = 3;
                    }
                }
            }
            position += readInt;
        }
        if (str == null) {
            return;
        }
        stsdData.format = Format.createVideoSampleFormat(Integer.toString(i4), str, null, -1, -1, readUnsignedShort, readUnsignedShort2, -1.0f, list, i5, f, bArr, i8, null, drmInitData3);
    }

    private static Pair<long[], long[]> parseEdts(Atom.ContainerAtom containerAtom) {
        Atom.LeafAtom leafAtomOfType;
        if (containerAtom == null || (leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_elst)) == null) {
            return Pair.create(null, null);
        }
        ParsableByteArray parsableByteArray = leafAtomOfType.data;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        long[] jArr = new long[readUnsignedIntToInt];
        long[] jArr2 = new long[readUnsignedIntToInt];
        for (int i = 0; i < readUnsignedIntToInt; i++) {
            jArr[i] = parseFullAtomVersion == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
            jArr2[i] = parseFullAtomVersion == 1 ? parsableByteArray.readLong() : parsableByteArray.readInt();
            if (parsableByteArray.readShort() != 1) {
                throw new IllegalArgumentException("Unsupported media rate.");
            }
            parsableByteArray.skipBytes(2);
        }
        return Pair.create(jArr, jArr2);
    }

    private static float parsePaspFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8);
        return parsableByteArray.readUnsignedIntToInt() / parsableByteArray.readUnsignedIntToInt();
    }

    private static void parseAudioSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, boolean z, DrmInitData drmInitData, StsdData stsdData, int i5) throws ParserException {
        int i6;
        int readUnsignedShort;
        int readUnsignedFixedPoint1616;
        String str2;
        int i7;
        String str3;
        int i8;
        String str4;
        DrmInitData drmInitData2;
        int i9;
        int i10 = i2;
        DrmInitData drmInitData3 = drmInitData;
        parsableByteArray.setPosition(i10 + 8 + 8);
        if (z) {
            i6 = parsableByteArray.readUnsignedShort();
            parsableByteArray.skipBytes(6);
        } else {
            parsableByteArray.skipBytes(8);
            i6 = 0;
        }
        if (i6 == 0 || i6 == 1) {
            readUnsignedShort = parsableByteArray.readUnsignedShort();
            parsableByteArray.skipBytes(6);
            readUnsignedFixedPoint1616 = parsableByteArray.readUnsignedFixedPoint1616();
            if (i6 == 1) {
                parsableByteArray.skipBytes(16);
            }
        } else if (i6 != 2) {
            return;
        } else {
            parsableByteArray.skipBytes(16);
            int round = (int) Math.round(parsableByteArray.readDouble());
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            parsableByteArray.skipBytes(20);
            readUnsignedShort = readUnsignedIntToInt;
            readUnsignedFixedPoint1616 = round;
        }
        int position = parsableByteArray.getPosition();
        int i11 = i;
        if (i11 == Atom.TYPE_enca) {
            Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray, i10, i3);
            if (parseSampleEntryEncryptionData != null) {
                i11 = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                drmInitData3 = drmInitData3 == null ? null : drmInitData3.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                stsdData.trackEncryptionBoxes[i5] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
            }
            parsableByteArray.setPosition(position);
        }
        DrmInitData drmInitData4 = drmInitData3;
        String str5 = "audio/raw";
        if (i11 == Atom.TYPE_ac_3) {
            str2 = "audio/ac3";
        } else if (i11 == Atom.TYPE_ec_3) {
            str2 = "audio/eac3";
        } else if (i11 == Atom.TYPE_dtsc) {
            str2 = "audio/vnd.dts";
        } else if (i11 == Atom.TYPE_dtsh || i11 == Atom.TYPE_dtsl) {
            str2 = "audio/vnd.dts.hd";
        } else if (i11 == Atom.TYPE_dtse) {
            str2 = "audio/vnd.dts.hd;profile=lbr";
        } else if (i11 == Atom.TYPE_samr) {
            str2 = "audio/3gpp";
        } else if (i11 == Atom.TYPE_sawb) {
            str2 = "audio/amr-wb";
        } else if (i11 == Atom.TYPE_lpcm || i11 == Atom.TYPE_sowt) {
            str2 = str5;
        } else if (i11 == Atom.TYPE__mp3) {
            str2 = "audio/mpeg";
        } else {
            str2 = i11 == Atom.TYPE_alac ? "audio/alac" : null;
        }
        int i12 = readUnsignedFixedPoint1616;
        int i13 = position;
        int i14 = readUnsignedShort;
        byte[] bArr = null;
        String str6 = str2;
        while (i13 - i10 < i3) {
            parsableByteArray.setPosition(i13);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == Atom.TYPE_esds || (z && readInt2 == Atom.TYPE_wave)) {
                i7 = readInt;
                str3 = str6;
                i8 = i13;
                str4 = str5;
                drmInitData2 = drmInitData4;
                int findEsdsPosition = readInt2 == Atom.TYPE_esds ? i8 : findEsdsPosition(parsableByteArray, i8, i7);
                if (findEsdsPosition != -1) {
                    Pair<String, byte[]> parseEsdsFromParent = parseEsdsFromParent(parsableByteArray, findEsdsPosition);
                    str6 = (String) parseEsdsFromParent.first;
                    bArr = (byte[]) parseEsdsFromParent.second;
                    if ("audio/mp4a-latm".equals(str6)) {
                        Pair<Integer, Integer> parseAacAudioSpecificConfig = CodecSpecificDataUtil.parseAacAudioSpecificConfig(bArr);
                        i12 = ((Integer) parseAacAudioSpecificConfig.first).intValue();
                        i14 = ((Integer) parseAacAudioSpecificConfig.second).intValue();
                    }
                    i13 = i8 + i7;
                    i10 = i2;
                    drmInitData4 = drmInitData2;
                    str5 = str4;
                }
            } else {
                if (readInt2 == Atom.TYPE_dac3) {
                    parsableByteArray.setPosition(i13 + 8);
                    stsdData.format = Ac3Util.parseAc3AnnexFFormat(parsableByteArray, Integer.toString(i4), str, drmInitData4);
                } else if (readInt2 == Atom.TYPE_dec3) {
                    parsableByteArray.setPosition(i13 + 8);
                    stsdData.format = Ac3Util.parseEAc3AnnexFFormat(parsableByteArray, Integer.toString(i4), str, drmInitData4);
                } else {
                    if (readInt2 == Atom.TYPE_ddts) {
                        str3 = str6;
                        i9 = i13;
                        str4 = str5;
                        drmInitData2 = drmInitData4;
                        stsdData.format = Format.createAudioSampleFormat(Integer.toString(i4), str6, null, -1, -1, i14, i12, null, drmInitData2, 0, str);
                        i7 = readInt;
                    } else {
                        str3 = str6;
                        i9 = i13;
                        str4 = str5;
                        drmInitData2 = drmInitData4;
                        i7 = readInt;
                        if (readInt2 == Atom.TYPE_alac) {
                            byte[] bArr2 = new byte[i7];
                            i8 = i9;
                            parsableByteArray.setPosition(i8);
                            parsableByteArray.readBytes(bArr2, 0, i7);
                            bArr = bArr2;
                        }
                    }
                    i8 = i9;
                }
                i7 = readInt;
                str3 = str6;
                i8 = i13;
                str4 = str5;
                drmInitData2 = drmInitData4;
            }
            str6 = str3;
            i13 = i8 + i7;
            i10 = i2;
            drmInitData4 = drmInitData2;
            str5 = str4;
        }
        String str7 = str6;
        String str8 = str5;
        DrmInitData drmInitData5 = drmInitData4;
        int i15 = 2;
        if (stsdData.format != null || str7 == null) {
            return;
        }
        if (!str8.equals(str7)) {
            i15 = -1;
        }
        stsdData.format = Format.createAudioSampleFormat(Integer.toString(i4), str7, null, -1, -1, i14, i12, i15, bArr == null ? null : Collections.singletonList(bArr), drmInitData5, 0, str);
    }

    private static int findEsdsPosition(ParsableByteArray parsableByteArray, int i, int i2) {
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            if (parsableByteArray.readInt() == Atom.TYPE_esds) {
                return position;
            }
            position += readInt;
        }
        return -1;
    }

    private static Pair<String, byte[]> parseEsdsFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8 + 4);
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        parsableByteArray.skipBytes(2);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 128) != 0) {
            parsableByteArray.skipBytes(2);
        }
        if ((readUnsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort());
        }
        if ((readUnsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(2);
        }
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        String mimeTypeFromMp4ObjectType = MimeTypes.getMimeTypeFromMp4ObjectType(parsableByteArray.readUnsignedByte());
        if ("audio/mpeg".equals(mimeTypeFromMp4ObjectType) || "audio/vnd.dts".equals(mimeTypeFromMp4ObjectType) || "audio/vnd.dts.hd".equals(mimeTypeFromMp4ObjectType)) {
            return Pair.create(mimeTypeFromMp4ObjectType, null);
        }
        parsableByteArray.skipBytes(12);
        parsableByteArray.skipBytes(1);
        int parseExpandableClassSize = parseExpandableClassSize(parsableByteArray);
        byte[] bArr = new byte[parseExpandableClassSize];
        parsableByteArray.readBytes(bArr, 0, parseExpandableClassSize);
        return Pair.create(mimeTypeFromMp4ObjectType, bArr);
    }

    private static Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData(ParsableByteArray parsableByteArray, int i, int i2) {
        Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent;
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            if (parsableByteArray.readInt() == Atom.TYPE_sinf && (parseCommonEncryptionSinfFromParent = parseCommonEncryptionSinfFromParent(parsableByteArray, position, readInt)) != null) {
                return parseCommonEncryptionSinfFromParent;
            }
            position += readInt;
        }
        return null;
    }

    static Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent(ParsableByteArray parsableByteArray, int i, int i2) {
        int i3 = i + 8;
        String str = null;
        Integer num = null;
        int i4 = -1;
        int i5 = 0;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == Atom.TYPE_frma) {
                num = Integer.valueOf(parsableByteArray.readInt());
            } else if (readInt2 == Atom.TYPE_schm) {
                parsableByteArray.skipBytes(4);
                str = parsableByteArray.readString(4);
            } else if (readInt2 == Atom.TYPE_schi) {
                i4 = i3;
                i5 = readInt;
            }
            i3 += readInt;
        }
        if ("cenc".equals(str) || "cbc1".equals(str) || "cens".equals(str) || "cbcs".equals(str)) {
            boolean z = true;
            Assertions.checkArgument(num != null, "frma atom is mandatory");
            Assertions.checkArgument(i4 != -1, "schi atom is mandatory");
            TrackEncryptionBox parseSchiFromParent = parseSchiFromParent(parsableByteArray, i4, i5, str);
            if (parseSchiFromParent == null) {
                z = false;
            }
            Assertions.checkArgument(z, "tenc atom is mandatory");
            return Pair.create(num, parseSchiFromParent);
        }
        return null;
    }

    private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parsableByteArray, int i, int i2, String str) {
        int i3;
        int i4;
        int i5 = i + 8;
        while (true) {
            byte[] bArr = null;
            if (i5 - i < i2) {
                parsableByteArray.setPosition(i5);
                int readInt = parsableByteArray.readInt();
                if (parsableByteArray.readInt() == Atom.TYPE_tenc) {
                    int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
                    parsableByteArray.skipBytes(1);
                    if (parseFullAtomVersion == 0) {
                        parsableByteArray.skipBytes(1);
                        i4 = 0;
                        i3 = 0;
                    } else {
                        int readUnsignedByte = parsableByteArray.readUnsignedByte();
                        i3 = readUnsignedByte & 15;
                        i4 = (readUnsignedByte & 240) >> 4;
                    }
                    boolean z = parsableByteArray.readUnsignedByte() == 1;
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    byte[] bArr2 = new byte[16];
                    parsableByteArray.readBytes(bArr2, 0, bArr2.length);
                    if (z && readUnsignedByte2 == 0) {
                        int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                        bArr = new byte[readUnsignedByte3];
                        parsableByteArray.readBytes(bArr, 0, readUnsignedByte3);
                    }
                    return new TrackEncryptionBox(z, str, readUnsignedByte2, bArr2, i4, i3, bArr);
                }
                i5 += readInt;
            } else {
                return null;
            }
        }
    }

    private static byte[] parseProjFromParent(ParsableByteArray parsableByteArray, int i, int i2) {
        int i3 = i + 8;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == Atom.TYPE_proj) {
                return Arrays.copyOfRange(parsableByteArray.data, i3, readInt + i3);
            }
            i3 += readInt;
        }
        return null;
    }

    private static int parseExpandableClassSize(ParsableByteArray parsableByteArray) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = readUnsignedByte & 127;
        while ((readUnsignedByte & 128) == 128) {
            readUnsignedByte = parsableByteArray.readUnsignedByte();
            i = (i << 7) | (readUnsignedByte & 127);
        }
        return i;
    }

    private static boolean canApplyEditWithGaplessInfo(long[] jArr, long j, long j2, long j3) {
        int length = jArr.length - 1;
        return jArr[0] <= j2 && j2 < jArr[Util.constrainValue(3, 0, length)] && jArr[Util.constrainValue(jArr.length - 3, 0, length)] < j3 && j3 <= j;
    }

    /* loaded from: classes2.dex */
    private static final class ChunkIterator {
        private final ParsableByteArray chunkOffsets;
        private final boolean chunkOffsetsAreLongs;
        public int index;
        public final int length;
        private int nextSamplesPerChunkChangeIndex;
        public int numSamples;
        public long offset;
        private int remainingSamplesPerChunkChanges;
        private final ParsableByteArray stsc;

        public ChunkIterator(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z) {
            this.stsc = parsableByteArray;
            this.chunkOffsets = parsableByteArray2;
            this.chunkOffsetsAreLongs = z;
            parsableByteArray2.setPosition(12);
            this.length = parsableByteArray2.readUnsignedIntToInt();
            parsableByteArray.setPosition(12);
            this.remainingSamplesPerChunkChanges = parsableByteArray.readUnsignedIntToInt();
            Assertions.checkState(parsableByteArray.readInt() != 1 ? false : true, "first_chunk must be 1");
            this.index = -1;
        }

        public boolean moveNext() {
            int i = this.index + 1;
            this.index = i;
            if (i == this.length) {
                return false;
            }
            this.offset = this.chunkOffsetsAreLongs ? this.chunkOffsets.readUnsignedLongToLong() : this.chunkOffsets.readUnsignedInt();
            if (this.index == this.nextSamplesPerChunkChangeIndex) {
                this.numSamples = this.stsc.readUnsignedIntToInt();
                this.stsc.skipBytes(4);
                int i2 = this.remainingSamplesPerChunkChanges - 1;
                this.remainingSamplesPerChunkChanges = i2;
                this.nextSamplesPerChunkChangeIndex = i2 > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class TkhdData {
        private final long duration;

        /* renamed from: id */
        private final int f1315id;
        private final int rotationDegrees;

        public TkhdData(int i, long j, int i2) {
            this.f1315id = i;
            this.duration = j;
            this.rotationDegrees = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class StsdData {
        public Format format;
        public int nalUnitLengthFieldLength;
        public int requiredSampleTransformation = 0;
        public final TrackEncryptionBox[] trackEncryptionBoxes;

        public StsdData(int i) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[i];
        }
    }

    /* loaded from: classes2.dex */
    static final class StszSampleSizeBox implements SampleSizeBox {
        private final ParsableByteArray data;
        private final int fixedSampleSize;
        private final int sampleCount;

        public StszSampleSizeBox(Atom.LeafAtom leafAtom) {
            this.data = leafAtom.data;
            this.data.setPosition(12);
            this.fixedSampleSize = this.data.readUnsignedIntToInt();
            this.sampleCount = this.data.readUnsignedIntToInt();
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i = this.fixedSampleSize;
            return i == 0 ? this.data.readUnsignedIntToInt() : i;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public boolean isFixedSampleSize() {
            return this.fixedSampleSize != 0;
        }
    }

    /* loaded from: classes2.dex */
    static final class Stz2SampleSizeBox implements SampleSizeBox {
        private int currentByte;
        private final ParsableByteArray data;
        private final int fieldSize;
        private final int sampleCount;
        private int sampleIndex;

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public boolean isFixedSampleSize() {
            return false;
        }

        public Stz2SampleSizeBox(Atom.LeafAtom leafAtom) {
            this.data = leafAtom.data;
            this.data.setPosition(12);
            this.fieldSize = this.data.readUnsignedIntToInt() & 255;
            this.sampleCount = this.data.readUnsignedIntToInt();
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i = this.fieldSize;
            if (i == 8) {
                return this.data.readUnsignedByte();
            }
            if (i == 16) {
                return this.data.readUnsignedShort();
            }
            int i2 = this.sampleIndex;
            this.sampleIndex = i2 + 1;
            if (i2 % 2 == 0) {
                this.currentByte = this.data.readUnsignedByte();
                return (this.currentByte & 240) >> 4;
            }
            return this.currentByte & 15;
        }
    }
}
