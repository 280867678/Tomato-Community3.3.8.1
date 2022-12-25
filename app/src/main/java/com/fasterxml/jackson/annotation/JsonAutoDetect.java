package com.fasterxml.jackson.annotation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes2.dex */
public @interface JsonAutoDetect {
    Visibility creatorVisibility() default Visibility.DEFAULT;

    Visibility fieldVisibility() default Visibility.DEFAULT;

    Visibility getterVisibility() default Visibility.DEFAULT;

    Visibility isGetterVisibility() default Visibility.DEFAULT;

    Visibility setterVisibility() default Visibility.DEFAULT;

    /* loaded from: classes2.dex */
    public enum Visibility {
        ANY,
        NON_PRIVATE,
        PROTECTED_AND_PUBLIC,
        PUBLIC_ONLY,
        NONE,
        DEFAULT;

        public boolean isVisible(Member member) {
            int i = C13641.f1270x23d16a11[ordinal()];
            if (i != 1) {
                if (i == 2) {
                    return false;
                }
                if (i == 3) {
                    return !Modifier.isPrivate(member.getModifiers());
                }
                if (i != 4) {
                    if (i != 5) {
                        return false;
                    }
                } else if (Modifier.isProtected(member.getModifiers())) {
                    return true;
                }
                return Modifier.isPublic(member.getModifiers());
            }
            return true;
        }
    }

    /* loaded from: classes2.dex */
    public static class Value implements JacksonAnnotationValue<JsonAutoDetect>, Serializable {
        protected static final Value DEFAULT;
        private static final Visibility DEFAULT_FIELD_VISIBILITY;
        protected static final Value NO_OVERRIDES;
        private static final long serialVersionUID = 1;
        protected final Visibility _creatorVisibility;
        protected final Visibility _fieldVisibility;
        protected final Visibility _getterVisibility;
        protected final Visibility _isGetterVisibility;
        protected final Visibility _setterVisibility;

        static {
            Visibility visibility = Visibility.PUBLIC_ONLY;
            DEFAULT_FIELD_VISIBILITY = visibility;
            DEFAULT = new Value(DEFAULT_FIELD_VISIBILITY, visibility, visibility, Visibility.ANY, Visibility.PUBLIC_ONLY);
            Visibility visibility2 = Visibility.DEFAULT;
            NO_OVERRIDES = new Value(visibility2, visibility2, visibility2, visibility2, visibility2);
        }

        private Value(Visibility visibility, Visibility visibility2, Visibility visibility3, Visibility visibility4, Visibility visibility5) {
            this._fieldVisibility = visibility;
            this._getterVisibility = visibility2;
            this._isGetterVisibility = visibility3;
            this._setterVisibility = visibility4;
            this._creatorVisibility = visibility5;
        }

        public static Value defaultVisibility() {
            return DEFAULT;
        }

        public static Value noOverrides() {
            return NO_OVERRIDES;
        }

        public static Value from(JsonAutoDetect jsonAutoDetect) {
            return construct(jsonAutoDetect.fieldVisibility(), jsonAutoDetect.getterVisibility(), jsonAutoDetect.isGetterVisibility(), jsonAutoDetect.setterVisibility(), jsonAutoDetect.creatorVisibility());
        }

        public static Value construct(PropertyAccessor propertyAccessor, Visibility visibility) {
            Visibility visibility2;
            Visibility visibility3;
            Visibility visibility4;
            Visibility visibility5 = Visibility.DEFAULT;
            switch (C13641.$SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor[propertyAccessor.ordinal()]) {
                case 1:
                    visibility3 = visibility;
                    visibility2 = visibility5;
                    visibility = visibility2;
                    visibility4 = visibility;
                    break;
                case 2:
                    visibility2 = visibility5;
                    visibility4 = visibility2;
                    visibility3 = visibility4;
                    visibility5 = visibility;
                    visibility = visibility3;
                    break;
                case 3:
                    visibility2 = visibility5;
                    visibility4 = visibility2;
                    visibility3 = visibility4;
                    break;
                case 4:
                    visibility2 = visibility;
                    visibility = visibility5;
                    visibility4 = visibility;
                    visibility3 = visibility4;
                    break;
                case 5:
                default:
                    visibility2 = visibility5;
                    visibility = visibility2;
                    visibility4 = visibility;
                    visibility3 = visibility4;
                    break;
                case 6:
                    visibility4 = visibility;
                    visibility2 = visibility5;
                    visibility = visibility2;
                    visibility3 = visibility;
                    break;
                case 7:
                    visibility2 = visibility;
                    visibility5 = visibility2;
                    visibility4 = visibility5;
                    visibility3 = visibility4;
                    break;
            }
            return construct(visibility5, visibility, visibility2, visibility4, visibility3);
        }

        public static Value construct(Visibility visibility, Visibility visibility2, Visibility visibility3, Visibility visibility4, Visibility visibility5) {
            Value _predefined = _predefined(visibility, visibility2, visibility3, visibility4, visibility5);
            return _predefined == null ? new Value(visibility, visibility2, visibility3, visibility4, visibility5) : _predefined;
        }

        public Value withFieldVisibility(Visibility visibility) {
            return construct(visibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
        }

        public Value withGetterVisibility(Visibility visibility) {
            return construct(this._fieldVisibility, visibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
        }

        public Value withIsGetterVisibility(Visibility visibility) {
            return construct(this._fieldVisibility, this._getterVisibility, visibility, this._setterVisibility, this._creatorVisibility);
        }

        public Value withSetterVisibility(Visibility visibility) {
            return construct(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, visibility, this._creatorVisibility);
        }

        public Value withCreatorVisibility(Visibility visibility) {
            return construct(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, visibility);
        }

        public static Value merge(Value value, Value value2) {
            return value == null ? value2 : value.withOverrides(value2);
        }

        public Value withOverrides(Value value) {
            if (value == null || value == NO_OVERRIDES || value == this || _equals(this, value)) {
                return this;
            }
            Visibility visibility = value._fieldVisibility;
            if (visibility == Visibility.DEFAULT) {
                visibility = this._fieldVisibility;
            }
            Visibility visibility2 = value._getterVisibility;
            if (visibility2 == Visibility.DEFAULT) {
                visibility2 = this._getterVisibility;
            }
            Visibility visibility3 = value._isGetterVisibility;
            if (visibility3 == Visibility.DEFAULT) {
                visibility3 = this._isGetterVisibility;
            }
            Visibility visibility4 = value._setterVisibility;
            if (visibility4 == Visibility.DEFAULT) {
                visibility4 = this._setterVisibility;
            }
            Visibility visibility5 = value._creatorVisibility;
            if (visibility5 == Visibility.DEFAULT) {
                visibility5 = this._creatorVisibility;
            }
            return construct(visibility, visibility2, visibility3, visibility4, visibility5);
        }

        public Class<JsonAutoDetect> valueFor() {
            return JsonAutoDetect.class;
        }

        public Visibility getFieldVisibility() {
            return this._fieldVisibility;
        }

        public Visibility getGetterVisibility() {
            return this._getterVisibility;
        }

        public Visibility getIsGetterVisibility() {
            return this._isGetterVisibility;
        }

        public Visibility getSetterVisibility() {
            return this._setterVisibility;
        }

        public Visibility getCreatorVisibility() {
            return this._creatorVisibility;
        }

        protected Object readResolve() {
            Value _predefined = _predefined(this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
            return _predefined == null ? this : _predefined;
        }

        public String toString() {
            return String.format("JsonAutoDetect.Value(fields=%s,getters=%s,isGetters=%s,setters=%s,creators=%s)", this._fieldVisibility, this._getterVisibility, this._isGetterVisibility, this._setterVisibility, this._creatorVisibility);
        }

        public int hashCode() {
            return ((this._fieldVisibility.ordinal() + 1) ^ (((this._getterVisibility.ordinal() * 3) - (this._isGetterVisibility.ordinal() * 7)) + (this._setterVisibility.ordinal() * 11))) ^ (this._creatorVisibility.ordinal() * 13);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return obj != null && obj.getClass() == Value.class && _equals(this, (Value) obj);
        }

        private static Value _predefined(Visibility visibility, Visibility visibility2, Visibility visibility3, Visibility visibility4, Visibility visibility5) {
            if (visibility == DEFAULT_FIELD_VISIBILITY) {
                Value value = DEFAULT;
                if (visibility2 != value._getterVisibility || visibility3 != value._isGetterVisibility || visibility4 != value._setterVisibility || visibility5 != value._creatorVisibility) {
                    return null;
                }
                return value;
            }
            Visibility visibility6 = Visibility.DEFAULT;
            if (visibility != visibility6 || visibility2 != visibility6 || visibility3 != visibility6 || visibility4 != visibility6 || visibility5 != visibility6) {
                return null;
            }
            return NO_OVERRIDES;
        }

        private static boolean _equals(Value value, Value value2) {
            return value._fieldVisibility == value2._fieldVisibility && value._getterVisibility == value2._getterVisibility && value._isGetterVisibility == value2._isGetterVisibility && value._setterVisibility == value2._setterVisibility && value._creatorVisibility == value2._creatorVisibility;
        }
    }

    /* renamed from: com.fasterxml.jackson.annotation.JsonAutoDetect$1 */
    /* loaded from: classes2.dex */
    static /* synthetic */ class C13641 {

        /* renamed from: $SwitchMap$com$fasterxml$jackson$annotation$JsonAutoDetect$Visibility */
        static final /* synthetic */ int[] f1270x23d16a11;
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor = new int[PropertyAccessor.values().length];

        static {
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor[PropertyAccessor.CREATOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor[PropertyAccessor.FIELD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor[PropertyAccessor.GETTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor[PropertyAccessor.IS_GETTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor[PropertyAccessor.NONE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor[PropertyAccessor.SETTER.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor[PropertyAccessor.ALL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            f1270x23d16a11 = new int[Visibility.values().length];
            try {
                f1270x23d16a11[Visibility.ANY.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f1270x23d16a11[Visibility.NONE.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f1270x23d16a11[Visibility.NON_PRIVATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f1270x23d16a11[Visibility.PROTECTED_AND_PUBLIC.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f1270x23d16a11[Visibility.PUBLIC_ONLY.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }
}
