package com.fasterxml.jackson.core.p058io;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;

/* renamed from: com.fasterxml.jackson.core.io.JsonStringEncoder */
/* loaded from: classes2.dex */
public final class JsonStringEncoder {
    protected ByteArrayBuilder _bytes;
    protected final char[] _qbuf = new char[6];
    protected TextBuffer _text;

    /* renamed from: HC */
    private static final char[] f1276HC = CharTypes.copyHexChars();

    /* renamed from: HB */
    private static final byte[] f1275HB = CharTypes.copyHexBytes();

    public JsonStringEncoder() {
        char[] cArr = this._qbuf;
        cArr[0] = '\\';
        cArr[2] = '0';
        cArr[3] = '0';
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0029, code lost:
        r8 = r1 + 1;
        r1 = r12.charAt(r1);
        r9 = r2[r1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0031, code lost:
        if (r9 >= 0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0033, code lost:
        r1 = _appendNumeric(r1, r11._qbuf);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0040, code lost:
        r9 = r7 + r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0043, code lost:
        if (r9 <= r6.length) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0045, code lost:
        r9 = r6.length - r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0047, code lost:
        if (r9 <= 0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0049, code lost:
        java.lang.System.arraycopy(r11._qbuf, 0, r6, r7, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004e, code lost:
        r6 = r0.finishCurrentSegment();
        r1 = r1 - r9;
        java.lang.System.arraycopy(r11._qbuf, r9, r6, 0, r1);
        r7 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005a, code lost:
        java.lang.System.arraycopy(r11._qbuf, 0, r6, r7, r1);
        r7 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003a, code lost:
        r1 = _appendNamed(r9, r11._qbuf);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public char[] quoteAsString(String str) {
        int i;
        TextBuffer textBuffer = this._text;
        if (textBuffer == null) {
            textBuffer = new TextBuffer(null);
            this._text = textBuffer;
        }
        char[] emptyAndGetCurrentSegment = textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = CharTypes.get7BitOutputEscapes();
        int length = iArr.length;
        int length2 = str.length();
        char[] cArr = emptyAndGetCurrentSegment;
        int i2 = 0;
        int i3 = 0;
        loop0: while (true) {
            if (i2 >= length2) {
                break;
            }
            while (true) {
                char charAt = str.charAt(i2);
                if (charAt < length && iArr[charAt] != 0) {
                    break;
                }
                if (i3 >= cArr.length) {
                    cArr = textBuffer.finishCurrentSegment();
                    i3 = 0;
                }
                int i4 = i3 + 1;
                cArr[i3] = charAt;
                i2++;
                if (i2 >= length2) {
                    i3 = i4;
                    break loop0;
                }
                i3 = i4;
            }
            i2 = i;
        }
        textBuffer.setCurrentLength(i3);
        return textBuffer.contentsAsArray();
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x010a, code lost:
        return r11._bytes.completeAndCoalesce(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0042, code lost:
        if (r6 < r5.length) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0044, code lost:
        r5 = r0.finishCurrentSegment();
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0049, code lost:
        r8 = r3 + 1;
        r3 = r12.charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x004f, code lost:
        if (r3 > 127) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0051, code lost:
        r6 = _appendByte(r3, r7[r3], r0, r6);
        r5 = r0.getCurrentSegment();
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x005f, code lost:
        if (r3 > 2047) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0061, code lost:
        r5[r6] = (byte) ((r3 >> 6) | 192);
        r3 = (r3 & '?') | 128;
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00f5, code lost:
        if (r6 < r5.length) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00f7, code lost:
        r5 = r0.finishCurrentSegment();
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00fc, code lost:
        r5[r6] = (byte) r3;
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0074, code lost:
        if (r3 < 55296) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0079, code lost:
        if (r3 <= 57343) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x007f, code lost:
        if (r3 > 56319) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0081, code lost:
        if (r8 >= r2) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0083, code lost:
        r7 = r8 + 1;
        r3 = _convert(r3, r12.charAt(r8));
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0090, code lost:
        if (r3 > 1114111) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0092, code lost:
        r8 = r6 + 1;
        r5[r6] = (byte) ((r3 >> 18) | 240);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x009c, code lost:
        if (r8 < r5.length) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x009e, code lost:
        r5 = r0.finishCurrentSegment();
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a3, code lost:
        r6 = r8 + 1;
        r5[r8] = (byte) (((r3 >> 12) & 63) | 128);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00af, code lost:
        if (r6 < r5.length) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00b1, code lost:
        r5 = r0.finishCurrentSegment();
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00b6, code lost:
        r5[r6] = (byte) (((r3 >> 6) & 63) | 128);
        r3 = (r3 & 63) | 128;
        r6 = r6 + 1;
        r8 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00c8, code lost:
        _illegal(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00cb, code lost:
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00cc, code lost:
        _illegal(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00cf, code lost:
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00d0, code lost:
        _illegal(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d3, code lost:
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d4, code lost:
        r7 = r6 + 1;
        r5[r6] = (byte) ((r3 >> '\f') | 224);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00de, code lost:
        if (r7 < r5.length) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00e0, code lost:
        r5 = r0.finishCurrentSegment();
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00e5, code lost:
        r6 = r7 + 1;
        r5[r7] = (byte) (((r3 >> 6) & 63) | 128);
        r3 = (r3 & '?') | 128;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public byte[] quoteAsUTF8(String str) {
        int i;
        ByteArrayBuilder byteArrayBuilder = this._bytes;
        if (byteArrayBuilder == null) {
            byteArrayBuilder = new ByteArrayBuilder((BufferRecycler) null);
            this._bytes = byteArrayBuilder;
        }
        int length = str.length();
        byte[] resetAndGetFirstSegment = byteArrayBuilder.resetAndGetFirstSegment();
        int i2 = 0;
        int i3 = 0;
        loop0: while (true) {
            if (i2 >= length) {
                break;
            }
            int[] iArr = CharTypes.get7BitOutputEscapes();
            while (true) {
                char charAt = str.charAt(i2);
                if (charAt > 127 || iArr[charAt] != 0) {
                    break;
                }
                if (i3 >= resetAndGetFirstSegment.length) {
                    resetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
                    i3 = 0;
                }
                int i4 = i3 + 1;
                resetAndGetFirstSegment[i3] = (byte) charAt;
                i2++;
                if (i2 >= length) {
                    i3 = i4;
                    break loop0;
                }
                i3 = i4;
            }
            i2 = i;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00f8, code lost:
        return r12._bytes.completeAndCoalesce(r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public byte[] encodeAsUTF8(String str) {
        int i;
        char c;
        ByteArrayBuilder byteArrayBuilder = this._bytes;
        if (byteArrayBuilder == null) {
            byteArrayBuilder = new ByteArrayBuilder((BufferRecycler) null);
            this._bytes = byteArrayBuilder;
        }
        int length = str.length();
        byte[] resetAndGetFirstSegment = byteArrayBuilder.resetAndGetFirstSegment();
        int length2 = resetAndGetFirstSegment.length;
        byte[] bArr = resetAndGetFirstSegment;
        int i2 = 0;
        int i3 = 0;
        loop0: while (true) {
            if (i2 >= length) {
                break;
            }
            int i4 = i2 + 1;
            char c2 = str.charAt(i2);
            while (c2 <= 127) {
                if (i3 >= length2) {
                    byte[] finishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
                    i3 = 0;
                    bArr = finishCurrentSegment;
                    length2 = finishCurrentSegment.length;
                }
                int i5 = i3 + 1;
                bArr[i3] = (byte) c2;
                if (i4 >= length) {
                    i3 = i5;
                    break loop0;
                }
                char charAt = str.charAt(i4);
                i4++;
                c2 = charAt;
                i3 = i5;
            }
            if (i3 >= length2) {
                bArr = byteArrayBuilder.finishCurrentSegment();
                length2 = bArr.length;
                i3 = 0;
            }
            if (c2 < 2048) {
                bArr[i3] = (byte) ((c2 >> 6) | 192);
                i = i3 + 1;
                c = c2;
            } else if (c2 < 55296 || c2 > 57343) {
                int i6 = i3 + 1;
                bArr[i3] = (byte) ((c2 >> '\f') | 224);
                if (i6 >= length2) {
                    bArr = byteArrayBuilder.finishCurrentSegment();
                    length2 = bArr.length;
                    i6 = 0;
                }
                i = i6 + 1;
                bArr[i6] = (byte) (((c2 >> 6) & 63) | 128);
                c = c2;
            } else if (c2 > 56319) {
                _illegal(c2);
                throw null;
            } else if (i4 >= length) {
                _illegal(c2);
                throw null;
            } else {
                int i7 = i4 + 1;
                int _convert = _convert(c2, str.charAt(i4));
                if (_convert > 1114111) {
                    _illegal(_convert);
                    throw null;
                }
                int i8 = i3 + 1;
                bArr[i3] = (byte) ((_convert >> 18) | 240);
                if (i8 >= length2) {
                    bArr = byteArrayBuilder.finishCurrentSegment();
                    length2 = bArr.length;
                    i8 = 0;
                }
                int i9 = i8 + 1;
                bArr[i8] = (byte) (((_convert >> 12) & 63) | 128);
                if (i9 >= length2) {
                    byte[] finishCurrentSegment2 = byteArrayBuilder.finishCurrentSegment();
                    i9 = 0;
                    bArr = finishCurrentSegment2;
                    length2 = finishCurrentSegment2.length;
                }
                bArr[i9] = (byte) (((_convert >> 6) & 63) | 128);
                i = i9 + 1;
                i4 = i7;
                c = _convert;
            }
            if (i >= length2) {
                byte[] finishCurrentSegment3 = byteArrayBuilder.finishCurrentSegment();
                i = 0;
                bArr = finishCurrentSegment3;
                length2 = finishCurrentSegment3.length;
            }
            bArr[i] = (byte) ((c & '?') | 128);
            i2 = i4;
            i3 = i + 1;
        }
    }

    private int _appendNumeric(int i, char[] cArr) {
        cArr[1] = 'u';
        char[] cArr2 = f1276HC;
        cArr[4] = cArr2[i >> 4];
        cArr[5] = cArr2[i & 15];
        return 6;
    }

    private int _appendNamed(int i, char[] cArr) {
        cArr[1] = (char) i;
        return 2;
    }

    private int _appendByte(int i, int i2, ByteArrayBuilder byteArrayBuilder, int i3) {
        byteArrayBuilder.setCurrentSegmentLength(i3);
        byteArrayBuilder.append(92);
        if (i2 < 0) {
            byteArrayBuilder.append(117);
            if (i > 255) {
                int i4 = i >> 8;
                byteArrayBuilder.append(f1275HB[i4 >> 4]);
                byteArrayBuilder.append(f1275HB[i4 & 15]);
                i &= 255;
            } else {
                byteArrayBuilder.append(48);
                byteArrayBuilder.append(48);
            }
            byteArrayBuilder.append(f1275HB[i >> 4]);
            byteArrayBuilder.append(f1275HB[i & 15]);
        } else {
            byteArrayBuilder.append((byte) i2);
        }
        return byteArrayBuilder.getCurrentSegmentLength();
    }

    private static int _convert(int i, int i2) {
        if (i2 < 56320 || i2 > 57343) {
            throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(i) + ", second 0x" + Integer.toHexString(i2) + "; illegal combination");
        }
        return ((i - 55296) << 10) + 65536 + (i2 - 56320);
    }

    private static void _illegal(int i) {
        throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(i));
    }
}
