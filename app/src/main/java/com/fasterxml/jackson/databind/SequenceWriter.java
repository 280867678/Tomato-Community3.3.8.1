package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/* loaded from: classes2.dex */
public class SequenceWriter implements Versioned, Closeable, Flushable {
    protected final boolean _closeGenerator;
    protected boolean _closed;
    protected final SerializationConfig _config;
    protected final JsonGenerator _generator;
    protected boolean _openArray;

    public SequenceWriter(DefaultSerializerProvider defaultSerializerProvider, JsonGenerator jsonGenerator, boolean z, ObjectWriter.Prefetch prefetch) throws IOException {
        this._generator = jsonGenerator;
        this._closeGenerator = z;
        prefetch.getValueSerializer();
        prefetch.getTypeSerializer();
        this._config = defaultSerializerProvider.mo6006getConfig();
        this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
        this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE);
        PropertySerializerMap.emptyForRootValues();
    }

    public SequenceWriter init(boolean z) throws IOException {
        if (z) {
            this._generator.writeStartArray();
            this._openArray = true;
        }
        return this;
    }

    @Override // java.io.Flushable
    public void flush() throws IOException {
        if (!this._closed) {
            this._generator.flush();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this._closed) {
            this._closed = true;
            if (this._openArray) {
                this._openArray = false;
                this._generator.writeEndArray();
            }
            if (!this._closeGenerator) {
                return;
            }
            this._generator.close();
        }
    }
}
