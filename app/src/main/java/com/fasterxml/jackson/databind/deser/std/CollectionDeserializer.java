package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@JacksonStdImpl
/* loaded from: classes2.dex */
public class CollectionDeserializer extends ContainerDeserializerBase<Collection<Object>> implements ContextualDeserializer {
    private static final long serialVersionUID = -1;
    protected final JsonDeserializer<Object> _delegateDeserializer;
    protected final JsonDeserializer<Object> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;
    protected final TypeDeserializer _valueTypeDeserializer;

    public CollectionDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, ValueInstantiator valueInstantiator) {
        this(javaType, jsonDeserializer, typeDeserializer, valueInstantiator, null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CollectionDeserializer(JavaType javaType, JsonDeserializer<Object> jsonDeserializer, TypeDeserializer typeDeserializer, ValueInstantiator valueInstantiator, JsonDeserializer<Object> jsonDeserializer2, NullValueProvider nullValueProvider, Boolean bool) {
        super(javaType, nullValueProvider, bool);
        this._valueDeserializer = jsonDeserializer;
        this._valueTypeDeserializer = typeDeserializer;
        this._valueInstantiator = valueInstantiator;
        this._delegateDeserializer = jsonDeserializer2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CollectionDeserializer(CollectionDeserializer collectionDeserializer) {
        super(collectionDeserializer);
        this._valueDeserializer = collectionDeserializer._valueDeserializer;
        this._valueTypeDeserializer = collectionDeserializer._valueTypeDeserializer;
        this._valueInstantiator = collectionDeserializer._valueInstantiator;
        this._delegateDeserializer = collectionDeserializer._delegateDeserializer;
    }

    /* renamed from: withResolved */
    protected CollectionDeserializer mo6014withResolved(JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, TypeDeserializer typeDeserializer, NullValueProvider nullValueProvider, Boolean bool) {
        return new CollectionDeserializer(this._containerType, jsonDeserializer2, typeDeserializer, this._valueInstantiator, jsonDeserializer, nullValueProvider, bool);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public boolean isCachable() {
        return this._valueDeserializer == null && this._valueTypeDeserializer == null && this._delegateDeserializer == null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008c  */
    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    /* renamed from: createContextual  reason: collision with other method in class */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CollectionDeserializer mo6018createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializer;
        JsonDeserializer<?> findConvertingContentDeserializer;
        JsonDeserializer<?> handleSecondaryContextualization;
        TypeDeserializer typeDeserializer;
        ValueInstantiator valueInstantiator = this._valueInstantiator;
        if (valueInstantiator != null) {
            if (valueInstantiator.canCreateUsingDelegate()) {
                JavaType delegateType = this._valueInstantiator.getDelegateType(deserializationContext.mo6006getConfig());
                if (delegateType == null) {
                    JavaType javaType = this._containerType;
                    deserializationContext.reportBadDefinition(javaType, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'", javaType, this._valueInstantiator.getClass().getName()));
                }
                jsonDeserializer = findDeserializer(deserializationContext, delegateType, beanProperty);
            } else if (this._valueInstantiator.canCreateUsingArrayDelegate()) {
                JavaType arrayDelegateType = this._valueInstantiator.getArrayDelegateType(deserializationContext.mo6006getConfig());
                if (arrayDelegateType == null) {
                    JavaType javaType2 = this._containerType;
                    deserializationContext.reportBadDefinition(javaType2, String.format("Invalid delegate-creator definition for %s: value instantiator (%s) returned true for 'canCreateUsingArrayDelegate()', but null for 'getArrayDelegateType()'", javaType2, this._valueInstantiator.getClass().getName()));
                }
                jsonDeserializer = findDeserializer(deserializationContext, arrayDelegateType, beanProperty);
            }
            JsonDeserializer<Object> jsonDeserializer2 = jsonDeserializer;
            Boolean findFormatFeature = findFormatFeature(deserializationContext, beanProperty, Collection.class, JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            findConvertingContentDeserializer = findConvertingContentDeserializer(deserializationContext, beanProperty, this._valueDeserializer);
            JavaType mo6160getContentType = this._containerType.mo6160getContentType();
            if (findConvertingContentDeserializer != null) {
                handleSecondaryContextualization = deserializationContext.findContextualValueDeserializer(mo6160getContentType, beanProperty);
            } else {
                handleSecondaryContextualization = deserializationContext.handleSecondaryContextualization(findConvertingContentDeserializer, beanProperty, mo6160getContentType);
            }
            JsonDeserializer<?> jsonDeserializer3 = handleSecondaryContextualization;
            typeDeserializer = this._valueTypeDeserializer;
            if (typeDeserializer != null) {
                typeDeserializer = typeDeserializer.forProperty(beanProperty);
            }
            TypeDeserializer typeDeserializer2 = typeDeserializer;
            NullValueProvider findContentNullProvider = findContentNullProvider(deserializationContext, beanProperty, jsonDeserializer3);
            return (findFormatFeature != this._unwrapSingle && findContentNullProvider == this._nullProvider && jsonDeserializer2 == this._delegateDeserializer && jsonDeserializer3 == this._valueDeserializer && typeDeserializer2 == this._valueTypeDeserializer) ? this : mo6014withResolved(jsonDeserializer2, jsonDeserializer3, typeDeserializer2, findContentNullProvider, findFormatFeature);
        }
        jsonDeserializer = null;
        JsonDeserializer<Object> jsonDeserializer22 = jsonDeserializer;
        Boolean findFormatFeature2 = findFormatFeature(deserializationContext, beanProperty, Collection.class, JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        findConvertingContentDeserializer = findConvertingContentDeserializer(deserializationContext, beanProperty, this._valueDeserializer);
        JavaType mo6160getContentType2 = this._containerType.mo6160getContentType();
        if (findConvertingContentDeserializer != null) {
        }
        JsonDeserializer<?> jsonDeserializer32 = handleSecondaryContextualization;
        typeDeserializer = this._valueTypeDeserializer;
        if (typeDeserializer != null) {
        }
        TypeDeserializer typeDeserializer22 = typeDeserializer;
        NullValueProvider findContentNullProvider2 = findContentNullProvider(deserializationContext, beanProperty, jsonDeserializer32);
        if (findFormatFeature2 != this._unwrapSingle) {
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
    public JsonDeserializer<Object> getContentDeserializer() {
        return this._valueDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    /* renamed from: deserialize  reason: collision with other method in class */
    public Collection<Object> mo6063deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonDeserializer<Object> jsonDeserializer = this._delegateDeserializer;
        if (jsonDeserializer != null) {
            return (Collection) this._valueInstantiator.createUsingDelegate(deserializationContext, jsonDeserializer.mo6063deserialize(jsonParser, deserializationContext));
        }
        if (jsonParser.hasToken(JsonToken.VALUE_STRING)) {
            String text = jsonParser.getText();
            if (text.length() == 0) {
                return (Collection) this._valueInstantiator.createFromString(deserializationContext, text);
            }
        }
        return deserialize(jsonParser, deserializationContext, createDefaultInstance(deserializationContext));
    }

    protected Collection<Object> createDefaultInstance(DeserializationContext deserializationContext) throws IOException {
        return (Collection) this._valueInstantiator.createUsingDefault(deserializationContext);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Collection<Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException {
        Object mo6059deserializeWithType;
        if (!jsonParser.isExpectedStartArrayToken()) {
            return handleNonArray(jsonParser, deserializationContext, collection);
        }
        jsonParser.setCurrentValue(collection);
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        if (jsonDeserializer.getObjectIdReader() != null) {
            return _deserializeWithObjectId(jsonParser, deserializationContext, collection);
        }
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        while (true) {
            JsonToken nextToken = jsonParser.nextToken();
            if (nextToken == JsonToken.END_ARRAY) {
                return collection;
            }
            try {
                if (nextToken == JsonToken.VALUE_NULL) {
                    if (!this._skipNullValues) {
                        mo6059deserializeWithType = this._nullProvider.mo6027getNullValue(deserializationContext);
                    }
                } else if (typeDeserializer == null) {
                    mo6059deserializeWithType = jsonDeserializer.mo6063deserialize(jsonParser, deserializationContext);
                } else {
                    mo6059deserializeWithType = jsonDeserializer.mo6059deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
                }
                collection.add(mo6059deserializeWithType);
            } catch (Exception e) {
                if (!(deserializationContext == null || deserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS))) {
                    ClassUtil.throwIfRTE(e);
                }
                throw JsonMappingException.wrapWithPath(e, collection, collection.size());
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    /* renamed from: deserializeWithType */
    public Object mo6059deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Collection<Object> handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException {
        Object mo6059deserializeWithType;
        Boolean bool = this._unwrapSingle;
        if (!(bool == Boolean.TRUE || (bool == null && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)))) {
            return (Collection) deserializationContext.handleUnexpectedToken(this._containerType.getRawClass(), jsonParser);
        }
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        try {
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
                if (this._skipNullValues) {
                    return collection;
                }
                mo6059deserializeWithType = this._nullProvider.mo6027getNullValue(deserializationContext);
            } else if (typeDeserializer == null) {
                mo6059deserializeWithType = jsonDeserializer.mo6063deserialize(jsonParser, deserializationContext);
            } else {
                mo6059deserializeWithType = jsonDeserializer.mo6059deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
            }
            collection.add(mo6059deserializeWithType);
            return collection;
        } catch (Exception e) {
            throw JsonMappingException.wrapWithPath(e, Object.class, collection.size());
        }
    }

    protected Collection<Object> _deserializeWithObjectId(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException {
        Object mo6059deserializeWithType;
        if (!jsonParser.isExpectedStartArrayToken()) {
            return handleNonArray(jsonParser, deserializationContext, collection);
        }
        jsonParser.setCurrentValue(collection);
        JsonDeserializer<Object> jsonDeserializer = this._valueDeserializer;
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        CollectionReferringAccumulator collectionReferringAccumulator = new CollectionReferringAccumulator(this._containerType.mo6160getContentType().getRawClass(), collection);
        while (true) {
            JsonToken nextToken = jsonParser.nextToken();
            if (nextToken == JsonToken.END_ARRAY) {
                return collection;
            }
            try {
            } catch (UnresolvedForwardReference e) {
                e.getRoid().appendReferring(collectionReferringAccumulator.handleUnresolvedReference(e));
            } catch (Exception e2) {
                if (!(deserializationContext == null || deserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS))) {
                    ClassUtil.throwIfRTE(e2);
                }
                throw JsonMappingException.wrapWithPath(e2, collection, collection.size());
            }
            if (nextToken == JsonToken.VALUE_NULL) {
                if (!this._skipNullValues) {
                    mo6059deserializeWithType = this._nullProvider.mo6027getNullValue(deserializationContext);
                }
            } else if (typeDeserializer == null) {
                mo6059deserializeWithType = jsonDeserializer.mo6063deserialize(jsonParser, deserializationContext);
            } else {
                mo6059deserializeWithType = jsonDeserializer.mo6059deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
            }
            collectionReferringAccumulator.add(mo6059deserializeWithType);
        }
    }

    /* loaded from: classes2.dex */
    public static class CollectionReferringAccumulator {
        private List<CollectionReferring> _accumulator = new ArrayList();
        private final Class<?> _elementType;
        private final Collection<Object> _result;

        public CollectionReferringAccumulator(Class<?> cls, Collection<Object> collection) {
            this._elementType = cls;
            this._result = collection;
        }

        public void add(Object obj) {
            if (this._accumulator.isEmpty()) {
                this._result.add(obj);
                return;
            }
            List<CollectionReferring> list = this._accumulator;
            list.get(list.size() - 1).next.add(obj);
        }

        public ReadableObjectId.Referring handleUnresolvedReference(UnresolvedForwardReference unresolvedForwardReference) {
            CollectionReferring collectionReferring = new CollectionReferring(this, unresolvedForwardReference, this._elementType);
            this._accumulator.add(collectionReferring);
            return collectionReferring;
        }

        public void resolveForwardReference(Object obj, Object obj2) throws IOException {
            Iterator<CollectionReferring> it2 = this._accumulator.iterator();
            Collection collection = this._result;
            while (it2.hasNext()) {
                CollectionReferring next = it2.next();
                if (next.hasId(obj)) {
                    it2.remove();
                    collection.add(obj2);
                    collection.addAll(next.next);
                    return;
                }
                collection = next.next;
            }
            throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + obj + "] that wasn't previously seen as unresolved.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class CollectionReferring extends ReadableObjectId.Referring {
        private final CollectionReferringAccumulator _parent;
        public final List<Object> next = new ArrayList();

        CollectionReferring(CollectionReferringAccumulator collectionReferringAccumulator, UnresolvedForwardReference unresolvedForwardReference, Class<?> cls) {
            super(unresolvedForwardReference, cls);
            this._parent = collectionReferringAccumulator;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.ReadableObjectId.Referring
        public void handleResolvedForwardReference(Object obj, Object obj2) throws IOException {
            this._parent.resolveForwardReference(obj, obj2);
        }
    }
}
