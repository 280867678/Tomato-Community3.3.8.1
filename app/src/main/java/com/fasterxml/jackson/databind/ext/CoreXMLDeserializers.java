package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/* loaded from: classes2.dex */
public class CoreXMLDeserializers extends Deserializers.Base {
    static final DatatypeFactory _dataTypeFactory;

    static {
        try {
            _dataTypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /* loaded from: classes2.dex */
    public static class Std extends FromStringDeserializer<Object> {
        private static final long serialVersionUID = 1;
        protected final int _kind;

        public Std(Class<?> cls, int i) {
            super(cls);
            this._kind = i;
        }

        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        /* renamed from: deserialize */
        public Object mo6063deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (this._kind == 2 && jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
                return _gregorianFromDate(deserializationContext, _parseDate(jsonParser, deserializationContext));
            }
            return super.mo6063deserialize(jsonParser, deserializationContext);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        /* renamed from: _deserialize */
        public Object mo6062_deserialize(String str, DeserializationContext deserializationContext) throws IOException {
            int i = this._kind;
            if (i != 1) {
                if (i == 2) {
                    try {
                        return _gregorianFromDate(deserializationContext, _parseDate(str, deserializationContext));
                    } catch (JsonMappingException unused) {
                        return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar(str);
                    }
                } else if (i == 3) {
                    return QName.valueOf(str);
                } else {
                    throw new IllegalStateException();
                }
            }
            return CoreXMLDeserializers._dataTypeFactory.newDuration(str);
        }

        protected XMLGregorianCalendar _gregorianFromDate(DeserializationContext deserializationContext, Date date) {
            if (date == null) {
                return null;
            }
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            TimeZone timeZone = deserializationContext.getTimeZone();
            if (timeZone != null) {
                gregorianCalendar.setTimeZone(timeZone);
            }
            return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        }
    }
}
