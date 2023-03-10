package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Iterator;

/* loaded from: classes2.dex */
public abstract class ObjectCodec extends TreeCodec implements Versioned {
    @Override // com.fasterxml.jackson.core.TreeCodec
    /* renamed from: createArrayNode */
    public abstract TreeNode mo5997createArrayNode();

    @Override // com.fasterxml.jackson.core.TreeCodec
    /* renamed from: createObjectNode */
    public abstract TreeNode mo5998createObjectNode();

    public abstract JsonFactory getFactory();

    @Override // com.fasterxml.jackson.core.TreeCodec
    public abstract <T extends TreeNode> T readTree(JsonParser jsonParser) throws IOException;

    public abstract <T> T readValue(JsonParser jsonParser, ResolvedType resolvedType) throws IOException;

    public abstract <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException;

    public abstract <T> T readValue(JsonParser jsonParser, Class<T> cls) throws IOException;

    /* renamed from: readValues */
    public abstract <T> Iterator<T> mo5993readValues(JsonParser jsonParser, ResolvedType resolvedType) throws IOException;

    /* renamed from: readValues */
    public abstract <T> Iterator<T> mo5994readValues(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException;

    /* renamed from: readValues */
    public abstract <T> Iterator<T> mo5995readValues(JsonParser jsonParser, Class<T> cls) throws IOException;

    @Override // com.fasterxml.jackson.core.TreeCodec
    public abstract JsonParser treeAsTokens(TreeNode treeNode);

    public abstract <T> T treeToValue(TreeNode treeNode, Class<T> cls) throws JsonProcessingException;

    public abstract Version version();

    @Override // com.fasterxml.jackson.core.TreeCodec
    public abstract void writeTree(JsonGenerator jsonGenerator, TreeNode treeNode) throws IOException;

    public abstract void writeValue(JsonGenerator jsonGenerator, Object obj) throws IOException;

    @Deprecated
    public JsonFactory getJsonFactory() {
        return getFactory();
    }
}
