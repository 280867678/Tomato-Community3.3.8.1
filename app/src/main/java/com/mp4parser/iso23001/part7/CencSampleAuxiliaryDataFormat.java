package com.mp4parser.iso23001.part7;

import com.coremedia.iso.Hex;
import java.math.BigInteger;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class CencSampleAuxiliaryDataFormat {

    /* renamed from: iv */
    public byte[] f1560iv = new byte[0];
    public Pair[] pairs = null;

    /* loaded from: classes3.dex */
    public interface Pair {
        int clear();

        long encrypted();
    }

    public int getSize() {
        int length = this.f1560iv.length;
        Pair[] pairArr = this.pairs;
        return (pairArr == null || pairArr.length <= 0) ? length : length + 2 + (pairArr.length * 6);
    }

    public Pair createPair(int i, long j) {
        if (i <= 127) {
            if (j <= 127) {
                return new ByteBytePair(i, j);
            }
            if (j <= 32767) {
                return new ByteShortPair(i, j);
            }
            if (j <= 2147483647L) {
                return new ByteIntPair(i, j);
            }
            return new ByteLongPair(i, j);
        } else if (i <= 32767) {
            if (j <= 127) {
                return new ShortBytePair(i, j);
            }
            if (j <= 32767) {
                return new ShortShortPair(i, j);
            }
            if (j <= 2147483647L) {
                return new ShortIntPair(i, j);
            }
            return new ShortLongPair(i, j);
        } else if (j <= 127) {
            return new IntBytePair(i, j);
        } else {
            if (j <= 32767) {
                return new IntShortPair(i, j);
            }
            if (j <= 2147483647L) {
                return new IntIntPair(i, j);
            }
            return new IntLongPair(i, j);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || CencSampleAuxiliaryDataFormat.class != obj.getClass()) {
            return false;
        }
        CencSampleAuxiliaryDataFormat cencSampleAuxiliaryDataFormat = (CencSampleAuxiliaryDataFormat) obj;
        if (!new BigInteger(this.f1560iv).equals(new BigInteger(cencSampleAuxiliaryDataFormat.f1560iv))) {
            return false;
        }
        Pair[] pairArr = this.pairs;
        Pair[] pairArr2 = cencSampleAuxiliaryDataFormat.pairs;
        return pairArr == null ? pairArr2 == null : Arrays.equals(pairArr, pairArr2);
    }

    public int hashCode() {
        byte[] bArr = this.f1560iv;
        int i = 0;
        int hashCode = (bArr != null ? Arrays.hashCode(bArr) : 0) * 31;
        Pair[] pairArr = this.pairs;
        if (pairArr != null) {
            i = Arrays.hashCode(pairArr);
        }
        return hashCode + i;
    }

    public String toString() {
        return "Entry{iv=" + Hex.encodeHex(this.f1560iv) + ", pairs=" + Arrays.toString(this.pairs) + '}';
    }

    /* loaded from: classes3.dex */
    private class ByteBytePair extends AbstractPair {
        private byte clear;
        private byte encrypted;

        public ByteBytePair(int i, long j) {
            super();
            this.clear = (byte) i;
            this.encrypted = (byte) j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class ByteShortPair extends AbstractPair {
        private byte clear;
        private short encrypted;

        public ByteShortPair(int i, long j) {
            super();
            this.clear = (byte) i;
            this.encrypted = (short) j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class ByteIntPair extends AbstractPair {
        private byte clear;
        private int encrypted;

        public ByteIntPair(int i, long j) {
            super();
            this.clear = (byte) i;
            this.encrypted = (int) j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class ByteLongPair extends AbstractPair {
        private byte clear;
        private long encrypted;

        public ByteLongPair(int i, long j) {
            super();
            this.clear = (byte) i;
            this.encrypted = j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class ShortBytePair extends AbstractPair {
        private short clear;
        private byte encrypted;

        public ShortBytePair(int i, long j) {
            super();
            this.clear = (short) i;
            this.encrypted = (byte) j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class ShortShortPair extends AbstractPair {
        private short clear;
        private short encrypted;

        public ShortShortPair(int i, long j) {
            super();
            this.clear = (short) i;
            this.encrypted = (short) j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class ShortIntPair extends AbstractPair {
        private short clear;
        private int encrypted;

        public ShortIntPair(int i, long j) {
            super();
            this.clear = (short) i;
            this.encrypted = (int) j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class ShortLongPair extends AbstractPair {
        private short clear;
        private long encrypted;

        public ShortLongPair(int i, long j) {
            super();
            this.clear = (short) i;
            this.encrypted = j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class IntBytePair extends AbstractPair {
        private int clear;
        private byte encrypted;

        public IntBytePair(int i, long j) {
            super();
            this.clear = i;
            this.encrypted = (byte) j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class IntShortPair extends AbstractPair {
        private int clear;
        private short encrypted;

        public IntShortPair(int i, long j) {
            super();
            this.clear = i;
            this.encrypted = (short) j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class IntIntPair extends AbstractPair {
        private int clear;
        private int encrypted;

        public IntIntPair(int i, long j) {
            super();
            this.clear = i;
            this.encrypted = (int) j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private class IntLongPair extends AbstractPair {
        private int clear;
        private long encrypted;

        public IntLongPair(int i, long j) {
            super();
            this.clear = i;
            this.encrypted = j;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public int clear() {
            return this.clear;
        }

        @Override // com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair
        public long encrypted() {
            return this.encrypted;
        }
    }

    /* loaded from: classes3.dex */
    private abstract class AbstractPair implements Pair {
        private AbstractPair() {
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Pair pair = (Pair) obj;
            return clear() == pair.clear() && encrypted() == pair.encrypted();
        }

        public String toString() {
            return "P(" + clear() + "|" + encrypted() + ")";
        }
    }
}
