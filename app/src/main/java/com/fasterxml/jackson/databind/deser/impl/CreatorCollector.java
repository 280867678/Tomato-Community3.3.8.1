package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.one.tomato.entity.p079db.UserChannelBean;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class CreatorCollector {
    protected static final String[] TYPE_DESCS = {UserChannelBean.TYPE_DEFAULT, "from-String", "from-int", "from-long", "from-double", "from-boolean", "delegate", "property-based"};
    protected SettableBeanProperty[] _arrayDelegateArgs;
    protected final BeanDescription _beanDesc;
    protected final boolean _canFixAccess;
    protected SettableBeanProperty[] _delegateArgs;
    protected final boolean _forceAccess;
    protected SettableBeanProperty[] _propertyBasedArgs;
    protected final AnnotatedWithParams[] _creators = new AnnotatedWithParams[9];
    protected int _explicitCreators = 0;
    protected boolean _hasNonDefaultCreator = false;

    public CreatorCollector(BeanDescription beanDescription, MapperConfig<?> mapperConfig) {
        this._beanDesc = beanDescription;
        this._canFixAccess = mapperConfig.canOverrideAccessModifiers();
        this._forceAccess = mapperConfig.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS);
    }

    public ValueInstantiator constructValueInstantiator(DeserializationContext deserializationContext) throws JsonMappingException {
        DeserializationConfig mo6006getConfig = deserializationContext.mo6006getConfig();
        JavaType _computeDelegateType = _computeDelegateType(deserializationContext, this._creators[6], this._delegateArgs);
        JavaType _computeDelegateType2 = _computeDelegateType(deserializationContext, this._creators[8], this._arrayDelegateArgs);
        JavaType type = this._beanDesc.getType();
        AnnotatedWithParams tryToOptimize = StdTypeConstructor.tryToOptimize(this._creators[0]);
        StdValueInstantiator stdValueInstantiator = new StdValueInstantiator(mo6006getConfig, type);
        AnnotatedWithParams[] annotatedWithParamsArr = this._creators;
        stdValueInstantiator.configureFromObjectSettings(tryToOptimize, annotatedWithParamsArr[6], _computeDelegateType, this._delegateArgs, annotatedWithParamsArr[7], this._propertyBasedArgs);
        stdValueInstantiator.configureFromArraySettings(this._creators[8], _computeDelegateType2, this._arrayDelegateArgs);
        stdValueInstantiator.configureFromStringCreator(this._creators[1]);
        stdValueInstantiator.configureFromIntCreator(this._creators[2]);
        stdValueInstantiator.configureFromLongCreator(this._creators[3]);
        stdValueInstantiator.configureFromDoubleCreator(this._creators[4]);
        stdValueInstantiator.configureFromBooleanCreator(this._creators[5]);
        return stdValueInstantiator;
    }

    public void setDefaultCreator(AnnotatedWithParams annotatedWithParams) {
        AnnotatedWithParams[] annotatedWithParamsArr = this._creators;
        _fixAccess(annotatedWithParams);
        annotatedWithParamsArr[0] = annotatedWithParams;
    }

    public void addStringCreator(AnnotatedWithParams annotatedWithParams, boolean z) {
        verifyNonDup(annotatedWithParams, 1, z);
    }

    public void addIntCreator(AnnotatedWithParams annotatedWithParams, boolean z) {
        verifyNonDup(annotatedWithParams, 2, z);
    }

    public void addLongCreator(AnnotatedWithParams annotatedWithParams, boolean z) {
        verifyNonDup(annotatedWithParams, 3, z);
    }

    public void addDoubleCreator(AnnotatedWithParams annotatedWithParams, boolean z) {
        verifyNonDup(annotatedWithParams, 4, z);
    }

    public void addBooleanCreator(AnnotatedWithParams annotatedWithParams, boolean z) {
        verifyNonDup(annotatedWithParams, 5, z);
    }

    public void addDelegatingCreator(AnnotatedWithParams annotatedWithParams, boolean z, SettableBeanProperty[] settableBeanPropertyArr, int i) {
        if (annotatedWithParams.getParameterType(i).isCollectionLikeType()) {
            if (!verifyNonDup(annotatedWithParams, 8, z)) {
                return;
            }
            this._arrayDelegateArgs = settableBeanPropertyArr;
        } else if (!verifyNonDup(annotatedWithParams, 6, z)) {
        } else {
            this._delegateArgs = settableBeanPropertyArr;
        }
    }

    public void addPropertyCreator(AnnotatedWithParams annotatedWithParams, boolean z, SettableBeanProperty[] settableBeanPropertyArr) {
        Integer num;
        if (verifyNonDup(annotatedWithParams, 7, z)) {
            if (settableBeanPropertyArr.length > 1) {
                HashMap hashMap = new HashMap();
                int length = settableBeanPropertyArr.length;
                for (int i = 0; i < length; i++) {
                    String name = settableBeanPropertyArr[i].getName();
                    if ((!name.isEmpty() || settableBeanPropertyArr[i].getInjectableValueId() == null) && (num = (Integer) hashMap.put(name, Integer.valueOf(i))) != null) {
                        throw new IllegalArgumentException(String.format("Duplicate creator property \"%s\" (index %s vs %d) for type %s ", name, num, Integer.valueOf(i), ClassUtil.nameOf(this._beanDesc.getBeanClass())));
                    }
                }
            }
            this._propertyBasedArgs = settableBeanPropertyArr;
        }
    }

    public boolean hasDefaultCreator() {
        return this._creators[0] != null;
    }

    public boolean hasDelegatingCreator() {
        return this._creators[6] != null;
    }

    public boolean hasPropertyBasedCreator() {
        return this._creators[7] != null;
    }

    private JavaType _computeDelegateType(DeserializationContext deserializationContext, AnnotatedWithParams annotatedWithParams, SettableBeanProperty[] settableBeanPropertyArr) throws JsonMappingException {
        if (!this._hasNonDefaultCreator || annotatedWithParams == null) {
            return null;
        }
        int i = 0;
        if (settableBeanPropertyArr != null) {
            int length = settableBeanPropertyArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                } else if (settableBeanPropertyArr[i2] == null) {
                    i = i2;
                    break;
                } else {
                    i2++;
                }
            }
        }
        DeserializationConfig mo6006getConfig = deserializationContext.mo6006getConfig();
        JavaType parameterType = annotatedWithParams.getParameterType(i);
        AnnotationIntrospector annotationIntrospector = mo6006getConfig.getAnnotationIntrospector();
        if (annotationIntrospector == null) {
            return parameterType;
        }
        AnnotatedParameter parameter = annotatedWithParams.getParameter(i);
        Object findDeserializer = annotationIntrospector.findDeserializer(parameter);
        if (findDeserializer != null) {
            return parameterType.mo6170withValueHandler(deserializationContext.deserializerInstance(parameter, findDeserializer));
        }
        return annotationIntrospector.refineDeserializationType(mo6006getConfig, parameter, parameterType);
    }

    private <T extends AnnotatedMember> T _fixAccess(T t) {
        if (t != null && this._canFixAccess) {
            ClassUtil.checkAndFixAccess((Member) t.mo6089getAnnotated(), this._forceAccess);
        }
        return t;
    }

    protected boolean verifyNonDup(AnnotatedWithParams annotatedWithParams, int i, boolean z) {
        boolean z2;
        int i2 = 1 << i;
        this._hasNonDefaultCreator = true;
        AnnotatedWithParams annotatedWithParams2 = this._creators[i];
        if (annotatedWithParams2 != null) {
            if ((this._explicitCreators & i2) == 0) {
                z2 = !z;
            } else if (!z) {
                return false;
            } else {
                z2 = true;
            }
            if (z2 && annotatedWithParams2.getClass() == annotatedWithParams.getClass()) {
                Class<?> rawParameterType = annotatedWithParams2.getRawParameterType(0);
                Class<?> rawParameterType2 = annotatedWithParams.getRawParameterType(0);
                if (rawParameterType == rawParameterType2) {
                    if (_isEnumValueOf(annotatedWithParams)) {
                        return false;
                    }
                    if (!_isEnumValueOf(annotatedWithParams2)) {
                        Object[] objArr = new Object[4];
                        objArr[0] = TYPE_DESCS[i];
                        objArr[1] = z ? "explicitly marked" : "implicitly discovered";
                        objArr[2] = annotatedWithParams2;
                        objArr[3] = annotatedWithParams;
                        throw new IllegalArgumentException(String.format("Conflicting %s creators: already had %s creator %s, encountered another: %s", objArr));
                    }
                } else if (rawParameterType2.isAssignableFrom(rawParameterType)) {
                    return false;
                }
            }
        }
        if (z) {
            this._explicitCreators |= i2;
        }
        AnnotatedWithParams[] annotatedWithParamsArr = this._creators;
        _fixAccess(annotatedWithParams);
        annotatedWithParamsArr[i] = annotatedWithParams;
        return true;
    }

    protected boolean _isEnumValueOf(AnnotatedWithParams annotatedWithParams) {
        return annotatedWithParams.getDeclaringClass().isEnum() && "valueOf".equals(annotatedWithParams.getName());
    }

    /* loaded from: classes2.dex */
    protected static final class StdTypeConstructor extends AnnotatedWithParams implements Serializable {
        public static final int TYPE_ARRAY_LIST = 1;
        public static final int TYPE_HASH_MAP = 2;
        public static final int TYPE_LINKED_HASH_MAP = 3;
        private static final long serialVersionUID = 1;
        private final AnnotatedWithParams _base;
        private final int _type;

        @Override // com.fasterxml.jackson.databind.introspect.Annotated
        public boolean equals(Object obj) {
            return obj == this;
        }

        public StdTypeConstructor(AnnotatedWithParams annotatedWithParams, int i) {
            super(annotatedWithParams, null);
            this._base = annotatedWithParams;
            this._type = i;
        }

        public static AnnotatedWithParams tryToOptimize(AnnotatedWithParams annotatedWithParams) {
            if (annotatedWithParams != null) {
                Class<?> declaringClass = annotatedWithParams.getDeclaringClass();
                if (declaringClass == List.class || declaringClass == ArrayList.class) {
                    return new StdTypeConstructor(annotatedWithParams, 1);
                }
                if (declaringClass == LinkedHashMap.class) {
                    return new StdTypeConstructor(annotatedWithParams, 3);
                }
                if (declaringClass == HashMap.class) {
                    return new StdTypeConstructor(annotatedWithParams, 2);
                }
            }
            return annotatedWithParams;
        }

        protected final Object _construct() {
            int i = this._type;
            if (i != 1) {
                if (i == 2) {
                    return new HashMap();
                }
                if (i == 3) {
                    return new LinkedHashMap();
                }
                throw new IllegalStateException("Unknown type " + this._type);
            }
            return new ArrayList();
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
        public int getParameterCount() {
            return this._base.getParameterCount();
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
        public Class<?> getRawParameterType(int i) {
            return this._base.getRawParameterType(i);
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
        public JavaType getParameterType(int i) {
            return this._base.getParameterType(i);
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
        @Deprecated
        public Type getGenericParameterType(int i) {
            return this._base.getGenericParameterType(i);
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
        public Object call() throws Exception {
            return _construct();
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
        public Object call(Object[] objArr) throws Exception {
            return _construct();
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
        public Object call1(Object obj) throws Exception {
            return _construct();
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
        public Class<?> getDeclaringClass() {
            return this._base.getDeclaringClass();
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
        /* renamed from: getMember */
        public Member mo6070getMember() {
            return this._base.mo6070getMember();
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
        public void setValue(Object obj, Object obj2) throws UnsupportedOperationException, IllegalArgumentException {
            throw new UnsupportedOperationException();
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
        public Object getValue(Object obj) throws UnsupportedOperationException, IllegalArgumentException {
            throw new UnsupportedOperationException();
        }

        @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
        /* renamed from: withAnnotations */
        public Annotated mo6072withAnnotations(AnnotationMap annotationMap) {
            throw new UnsupportedOperationException();
        }

        @Override // com.fasterxml.jackson.databind.introspect.Annotated
        /* renamed from: getAnnotated */
        public AnnotatedElement mo6089getAnnotated() {
            return this._base.mo6089getAnnotated();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.introspect.Annotated
        public int getModifiers() {
            return this._base.mo6070getMember().getModifiers();
        }

        @Override // com.fasterxml.jackson.databind.introspect.Annotated
        public String getName() {
            return this._base.getName();
        }

        @Override // com.fasterxml.jackson.databind.introspect.Annotated
        public JavaType getType() {
            return this._base.getType();
        }

        @Override // com.fasterxml.jackson.databind.introspect.Annotated
        public Class<?> getRawType() {
            return this._base.getRawType();
        }

        @Override // com.fasterxml.jackson.databind.introspect.Annotated
        public int hashCode() {
            return this._base.hashCode();
        }

        @Override // com.fasterxml.jackson.databind.introspect.Annotated
        public String toString() {
            return this._base.toString();
        }
    }
}
