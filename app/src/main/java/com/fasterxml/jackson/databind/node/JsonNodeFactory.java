package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.databind.util.RawValue;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes2.dex */
public class JsonNodeFactory implements Serializable, JsonNodeCreator {
    private static final long serialVersionUID = 1;
    private final boolean _cfgBigDecimalExact;
    private static final JsonNodeFactory decimalsNormalized = new JsonNodeFactory(false);
    private static final JsonNodeFactory decimalsAsIs = new JsonNodeFactory(true);
    public static final JsonNodeFactory instance = decimalsNormalized;

    protected boolean _inIntRange(long j) {
        return ((long) ((int) j)) == j;
    }

    public JsonNodeFactory(boolean z) {
        this._cfgBigDecimalExact = z;
    }

    protected JsonNodeFactory() {
        this(false);
    }

    public static JsonNodeFactory withExactBigDecimals(boolean z) {
        return z ? decimalsAsIs : decimalsNormalized;
    }

    /* renamed from: booleanNode */
    public BooleanNode m6107booleanNode(boolean z) {
        return z ? BooleanNode.getTrue() : BooleanNode.getFalse();
    }

    /* renamed from: nullNode */
    public NullNode m6108nullNode() {
        return NullNode.getInstance();
    }

    /* renamed from: numberNode */
    public NumericNode m6109numberNode(byte b) {
        return IntNode.valueOf(b);
    }

    public ValueNode numberNode(Byte b) {
        return b == null ? m6108nullNode() : IntNode.valueOf(b.intValue());
    }

    /* renamed from: numberNode */
    public NumericNode m6114numberNode(short s) {
        return ShortNode.valueOf(s);
    }

    public ValueNode numberNode(Short sh) {
        return sh == null ? m6108nullNode() : ShortNode.valueOf(sh.shortValue());
    }

    /* renamed from: numberNode */
    public NumericNode m6112numberNode(int i) {
        return IntNode.valueOf(i);
    }

    public ValueNode numberNode(Integer num) {
        return num == null ? m6108nullNode() : IntNode.valueOf(num.intValue());
    }

    /* renamed from: numberNode */
    public NumericNode m6113numberNode(long j) {
        return LongNode.valueOf(j);
    }

    public ValueNode numberNode(Long l) {
        if (l == null) {
            return m6108nullNode();
        }
        return LongNode.valueOf(l.longValue());
    }

    public ValueNode numberNode(BigInteger bigInteger) {
        if (bigInteger == null) {
            return m6108nullNode();
        }
        return BigIntegerNode.valueOf(bigInteger);
    }

    /* renamed from: numberNode */
    public NumericNode m6111numberNode(float f) {
        return FloatNode.valueOf(f);
    }

    public ValueNode numberNode(Float f) {
        return f == null ? m6108nullNode() : FloatNode.valueOf(f.floatValue());
    }

    /* renamed from: numberNode */
    public NumericNode m6110numberNode(double d) {
        return DoubleNode.valueOf(d);
    }

    public ValueNode numberNode(Double d) {
        return d == null ? m6108nullNode() : DoubleNode.valueOf(d.doubleValue());
    }

    public ValueNode numberNode(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return m6108nullNode();
        }
        if (this._cfgBigDecimalExact) {
            return DecimalNode.valueOf(bigDecimal);
        }
        return bigDecimal.compareTo(BigDecimal.ZERO) == 0 ? DecimalNode.ZERO : DecimalNode.valueOf(bigDecimal.stripTrailingZeros());
    }

    /* renamed from: textNode */
    public TextNode m6115textNode(String str) {
        return TextNode.valueOf(str);
    }

    /* renamed from: binaryNode */
    public BinaryNode m6105binaryNode(byte[] bArr) {
        return BinaryNode.valueOf(bArr);
    }

    /* renamed from: binaryNode */
    public BinaryNode m6106binaryNode(byte[] bArr, int i, int i2) {
        return BinaryNode.valueOf(bArr, i, i2);
    }

    public ArrayNode arrayNode() {
        return new ArrayNode(this);
    }

    public ArrayNode arrayNode(int i) {
        return new ArrayNode(this, i);
    }

    public ObjectNode objectNode() {
        return new ObjectNode(this);
    }

    public ValueNode pojoNode(Object obj) {
        return new POJONode(obj);
    }

    public ValueNode rawValueNode(RawValue rawValue) {
        return new POJONode(rawValue);
    }
}
