package com.squareup.wire;

import java.io.IOException;
import java.net.ProtocolException;

/* loaded from: classes3.dex */
public enum FieldEncoding {
    VARINT(0),
    FIXED64(1),
    LENGTH_DELIMITED(2),
    FIXED32(5);
    
    final int value;

    FieldEncoding(int i) {
        this.value = i;
    }

    static FieldEncoding get(int i) throws IOException {
        if (i != 0) {
            if (i == 1) {
                return FIXED64;
            }
            if (i == 2) {
                return LENGTH_DELIMITED;
            }
            if (i == 5) {
                return FIXED32;
            }
            throw new ProtocolException("Unexpected FieldEncoding: " + i);
        }
        return VARINT;
    }

    /* renamed from: com.squareup.wire.FieldEncoding$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C31811 {
        static final /* synthetic */ int[] $SwitchMap$com$squareup$wire$FieldEncoding = new int[FieldEncoding.values().length];

        static {
            try {
                $SwitchMap$com$squareup$wire$FieldEncoding[FieldEncoding.VARINT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$squareup$wire$FieldEncoding[FieldEncoding.FIXED32.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$squareup$wire$FieldEncoding[FieldEncoding.FIXED64.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$squareup$wire$FieldEncoding[FieldEncoding.LENGTH_DELIMITED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public ProtoAdapter<?> rawProtoAdapter() {
        int i = C31811.$SwitchMap$com$squareup$wire$FieldEncoding[ordinal()];
        if (i != 1) {
            if (i == 2) {
                return ProtoAdapter.FIXED32;
            }
            if (i == 3) {
                return ProtoAdapter.FIXED64;
            }
            if (i == 4) {
                return ProtoAdapter.BYTES;
            }
            throw new AssertionError();
        }
        return ProtoAdapter.UINT64;
    }
}
