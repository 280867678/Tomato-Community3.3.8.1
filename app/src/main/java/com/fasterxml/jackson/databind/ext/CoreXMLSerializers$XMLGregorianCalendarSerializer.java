package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Calendar;
import javax.xml.datatype.XMLGregorianCalendar;

/* loaded from: classes2.dex */
public class CoreXMLSerializers$XMLGregorianCalendarSerializer extends StdSerializer<XMLGregorianCalendar> implements ContextualSerializer {
    static final CoreXMLSerializers$XMLGregorianCalendarSerializer instance = new CoreXMLSerializers$XMLGregorianCalendarSerializer();
    final JsonSerializer<Object> _delegate;

    public CoreXMLSerializers$XMLGregorianCalendarSerializer() {
        this(CalendarSerializer.instance);
    }

    protected CoreXMLSerializers$XMLGregorianCalendarSerializer(JsonSerializer<?> jsonSerializer) {
        super(XMLGregorianCalendar.class);
        this._delegate = jsonSerializer;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public JsonSerializer<?> getDelegatee() {
        return this._delegate;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(SerializerProvider serializerProvider, XMLGregorianCalendar xMLGregorianCalendar) {
        return this._delegate.isEmpty(serializerProvider, _convert(xMLGregorianCalendar));
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(XMLGregorianCalendar xMLGregorianCalendar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        this._delegate.serialize(_convert(xMLGregorianCalendar), jsonGenerator, serializerProvider);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(XMLGregorianCalendar xMLGregorianCalendar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        this._delegate.serializeWithType(_convert(xMLGregorianCalendar), jsonGenerator, serializerProvider, typeSerializer);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        this._delegate.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, null);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<?> handlePrimaryContextualization = serializerProvider.handlePrimaryContextualization(this._delegate, beanProperty);
        return handlePrimaryContextualization != this._delegate ? new CoreXMLSerializers$XMLGregorianCalendarSerializer(handlePrimaryContextualization) : this;
    }

    protected Calendar _convert(XMLGregorianCalendar xMLGregorianCalendar) {
        if (xMLGregorianCalendar == null) {
            return null;
        }
        return xMLGregorianCalendar.toGregorianCalendar();
    }
}
