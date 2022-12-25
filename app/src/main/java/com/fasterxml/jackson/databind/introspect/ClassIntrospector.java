package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfig;

/* loaded from: classes2.dex */
public abstract class ClassIntrospector {

    /* loaded from: classes2.dex */
    public interface MixInResolver {
        /* renamed from: copy */
        MixInResolver mo6088copy();

        Class<?> findMixInClassFor(Class<?> cls);
    }

    public abstract ClassIntrospector copy();

    /* renamed from: forClassAnnotations */
    public abstract BeanDescription mo6073forClassAnnotations(MapperConfig<?> mapperConfig, JavaType javaType, MixInResolver mixInResolver);

    /* renamed from: forCreation */
    public abstract BeanDescription mo6074forCreation(DeserializationConfig deserializationConfig, JavaType javaType, MixInResolver mixInResolver);

    /* renamed from: forDeserialization */
    public abstract BeanDescription mo6075forDeserialization(DeserializationConfig deserializationConfig, JavaType javaType, MixInResolver mixInResolver);

    /* renamed from: forDeserializationWithBuilder */
    public abstract BeanDescription mo6076forDeserializationWithBuilder(DeserializationConfig deserializationConfig, JavaType javaType, MixInResolver mixInResolver);

    /* renamed from: forDirectClassAnnotations */
    public abstract BeanDescription mo6077forDirectClassAnnotations(MapperConfig<?> mapperConfig, JavaType javaType, MixInResolver mixInResolver);

    /* renamed from: forSerialization */
    public abstract BeanDescription mo6078forSerialization(SerializationConfig serializationConfig, JavaType javaType, MixInResolver mixInResolver);
}
