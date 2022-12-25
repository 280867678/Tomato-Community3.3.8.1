package com.opensource.svgaplayer.proto;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import okio.ByteString;

/* loaded from: classes3.dex */
public final class Transform extends Message<Transform, Builder> {
    public static final ProtoAdapter<Transform> ADAPTER = new ProtoAdapter_Transform();
    public static final Float DEFAULT_A;
    public static final Float DEFAULT_B;
    public static final Float DEFAULT_C;
    public static final Float DEFAULT_D;
    public static final Float DEFAULT_TX;
    public static final Float DEFAULT_TY;
    private static final long serialVersionUID = 0;
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 1)

    /* renamed from: a */
    public final Float f1785a;
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 2)

    /* renamed from: b */
    public final Float f1786b;
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 3)

    /* renamed from: c */
    public final Float f1787c;
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 4)

    /* renamed from: d */
    public final Float f1788d;
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 5)

    /* renamed from: tx */
    public final Float f1789tx;
    @WireField(adapter = "com.squareup.wire.ProtoAdapter#FLOAT", tag = 6)

    /* renamed from: ty */
    public final Float f1790ty;

    static {
        Float valueOf = Float.valueOf(0.0f);
        DEFAULT_A = valueOf;
        DEFAULT_B = valueOf;
        DEFAULT_C = valueOf;
        DEFAULT_D = valueOf;
        DEFAULT_TX = valueOf;
        DEFAULT_TY = valueOf;
    }

    public Transform(Float f, Float f2, Float f3, Float f4, Float f5, Float f6) {
        this(f, f2, f3, f4, f5, f6, ByteString.EMPTY);
    }

    public Transform(Float f, Float f2, Float f3, Float f4, Float f5, Float f6, ByteString byteString) {
        super(ADAPTER, byteString);
        this.f1785a = f;
        this.f1786b = f2;
        this.f1787c = f3;
        this.f1788d = f4;
        this.f1789tx = f5;
        this.f1790ty = f6;
    }

    @Override // com.squareup.wire.Message
    /* renamed from: newBuilder */
    public Message.Builder<Transform, Builder> newBuilder2() {
        Builder builder = new Builder();
        builder.f1791a = this.f1785a;
        builder.f1792b = this.f1786b;
        builder.f1793c = this.f1787c;
        builder.f1794d = this.f1788d;
        builder.f1795tx = this.f1789tx;
        builder.f1796ty = this.f1790ty;
        builder.addUnknownFields(unknownFields());
        return builder;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Transform)) {
            return false;
        }
        Transform transform = (Transform) obj;
        return unknownFields().equals(transform.unknownFields()) && Internal.equals(this.f1785a, transform.f1785a) && Internal.equals(this.f1786b, transform.f1786b) && Internal.equals(this.f1787c, transform.f1787c) && Internal.equals(this.f1788d, transform.f1788d) && Internal.equals(this.f1789tx, transform.f1789tx) && Internal.equals(this.f1790ty, transform.f1790ty);
    }

    public int hashCode() {
        int i = this.hashCode;
        if (i == 0) {
            int hashCode = unknownFields().hashCode() * 37;
            Float f = this.f1785a;
            int i2 = 0;
            int hashCode2 = (hashCode + (f != null ? f.hashCode() : 0)) * 37;
            Float f2 = this.f1786b;
            int hashCode3 = (hashCode2 + (f2 != null ? f2.hashCode() : 0)) * 37;
            Float f3 = this.f1787c;
            int hashCode4 = (hashCode3 + (f3 != null ? f3.hashCode() : 0)) * 37;
            Float f4 = this.f1788d;
            int hashCode5 = (hashCode4 + (f4 != null ? f4.hashCode() : 0)) * 37;
            Float f5 = this.f1789tx;
            int hashCode6 = (hashCode5 + (f5 != null ? f5.hashCode() : 0)) * 37;
            Float f6 = this.f1790ty;
            if (f6 != null) {
                i2 = f6.hashCode();
            }
            int i3 = hashCode6 + i2;
            this.hashCode = i3;
            return i3;
        }
        return i;
    }

    @Override // com.squareup.wire.Message
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.f1785a != null) {
            sb.append(", a=");
            sb.append(this.f1785a);
        }
        if (this.f1786b != null) {
            sb.append(", b=");
            sb.append(this.f1786b);
        }
        if (this.f1787c != null) {
            sb.append(", c=");
            sb.append(this.f1787c);
        }
        if (this.f1788d != null) {
            sb.append(", d=");
            sb.append(this.f1788d);
        }
        if (this.f1789tx != null) {
            sb.append(", tx=");
            sb.append(this.f1789tx);
        }
        if (this.f1790ty != null) {
            sb.append(", ty=");
            sb.append(this.f1790ty);
        }
        StringBuilder replace = sb.replace(0, 2, "Transform{");
        replace.append('}');
        return replace.toString();
    }

    /* loaded from: classes3.dex */
    public static final class Builder extends Message.Builder<Transform, Builder> {

        /* renamed from: a */
        public Float f1791a;

        /* renamed from: b */
        public Float f1792b;

        /* renamed from: c */
        public Float f1793c;

        /* renamed from: d */
        public Float f1794d;

        /* renamed from: tx */
        public Float f1795tx;

        /* renamed from: ty */
        public Float f1796ty;

        /* renamed from: a */
        public Builder m3770a(Float f) {
            this.f1791a = f;
            return this;
        }

        /* renamed from: b */
        public Builder m3769b(Float f) {
            this.f1792b = f;
            return this;
        }

        /* renamed from: c */
        public Builder m3768c(Float f) {
            this.f1793c = f;
            return this;
        }

        /* renamed from: d */
        public Builder m3767d(Float f) {
            this.f1794d = f;
            return this;
        }

        /* renamed from: tx */
        public Builder m3766tx(Float f) {
            this.f1795tx = f;
            return this;
        }

        /* renamed from: ty */
        public Builder m3765ty(Float f) {
            this.f1796ty = f;
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.squareup.wire.Message.Builder
        /* renamed from: build */
        public Transform mo6479build() {
            return new Transform(this.f1791a, this.f1792b, this.f1793c, this.f1794d, this.f1795tx, this.f1796ty, super.buildUnknownFields());
        }
    }

    /* loaded from: classes3.dex */
    private static final class ProtoAdapter_Transform extends ProtoAdapter<Transform> {
        ProtoAdapter_Transform() {
            super(FieldEncoding.LENGTH_DELIMITED, Transform.class);
        }

        @Override // com.squareup.wire.ProtoAdapter
        public int encodedSize(Transform transform) {
            Float f = transform.f1785a;
            int i = 0;
            int encodedSizeWithTag = f != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(1, f) : 0;
            Float f2 = transform.f1786b;
            int encodedSizeWithTag2 = encodedSizeWithTag + (f2 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(2, f2) : 0);
            Float f3 = transform.f1787c;
            int encodedSizeWithTag3 = encodedSizeWithTag2 + (f3 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(3, f3) : 0);
            Float f4 = transform.f1788d;
            int encodedSizeWithTag4 = encodedSizeWithTag3 + (f4 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(4, f4) : 0);
            Float f5 = transform.f1789tx;
            int encodedSizeWithTag5 = encodedSizeWithTag4 + (f5 != null ? ProtoAdapter.FLOAT.encodedSizeWithTag(5, f5) : 0);
            Float f6 = transform.f1790ty;
            if (f6 != null) {
                i = ProtoAdapter.FLOAT.encodedSizeWithTag(6, f6);
            }
            return encodedSizeWithTag5 + i + transform.unknownFields().size();
        }

        @Override // com.squareup.wire.ProtoAdapter
        public void encode(ProtoWriter protoWriter, Transform transform) throws IOException {
            Float f = transform.f1785a;
            if (f != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 1, f);
            }
            Float f2 = transform.f1786b;
            if (f2 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 2, f2);
            }
            Float f3 = transform.f1787c;
            if (f3 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 3, f3);
            }
            Float f4 = transform.f1788d;
            if (f4 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 4, f4);
            }
            Float f5 = transform.f1789tx;
            if (f5 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 5, f5);
            }
            Float f6 = transform.f1790ty;
            if (f6 != null) {
                ProtoAdapter.FLOAT.encodeWithTag(protoWriter, 6, f6);
            }
            protoWriter.writeBytes(transform.unknownFields());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.squareup.wire.ProtoAdapter
        /* renamed from: decode */
        public Transform mo6533decode(ProtoReader protoReader) throws IOException {
            Builder builder = new Builder();
            long beginMessage = protoReader.beginMessage();
            while (true) {
                int nextTag = protoReader.nextTag();
                if (nextTag != -1) {
                    switch (nextTag) {
                        case 1:
                            builder.m3770a(ProtoAdapter.FLOAT.mo6533decode(protoReader));
                            break;
                        case 2:
                            builder.m3769b(ProtoAdapter.FLOAT.mo6533decode(protoReader));
                            break;
                        case 3:
                            builder.m3768c(ProtoAdapter.FLOAT.mo6533decode(protoReader));
                            break;
                        case 4:
                            builder.m3767d(ProtoAdapter.FLOAT.mo6533decode(protoReader));
                            break;
                        case 5:
                            builder.m3766tx(ProtoAdapter.FLOAT.mo6533decode(protoReader));
                            break;
                        case 6:
                            builder.m3765ty(ProtoAdapter.FLOAT.mo6533decode(protoReader));
                            break;
                        default:
                            FieldEncoding peekFieldEncoding = protoReader.peekFieldEncoding();
                            builder.addUnknownField(nextTag, peekFieldEncoding, peekFieldEncoding.rawProtoAdapter().mo6533decode(protoReader));
                            break;
                    }
                } else {
                    protoReader.endMessage(beginMessage);
                    return builder.mo6479build();
                }
            }
        }

        @Override // com.squareup.wire.ProtoAdapter
        public Transform redact(Transform transform) {
            Message.Builder<Transform, Builder> newBuilder2 = transform.newBuilder2();
            newBuilder2.clearUnknownFields();
            return newBuilder2.mo6479build();
        }
    }
}
