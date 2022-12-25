package com.googlecode.mp4parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/* loaded from: classes3.dex */
public class FileDataSourceImpl implements DataSource {

    /* renamed from: fc */
    FileChannel f1340fc;
    String filename;

    public FileDataSourceImpl(File file) throws FileNotFoundException {
        this.f1340fc = new FileInputStream(file).getChannel();
        this.filename = file.getName();
    }

    public FileDataSourceImpl(String str) throws FileNotFoundException {
        File file = new File(str);
        this.f1340fc = new FileInputStream(file).getChannel();
        this.filename = file.getName();
    }

    public FileDataSourceImpl(FileChannel fileChannel) {
        this.f1340fc = fileChannel;
        this.filename = "unknown";
    }

    public FileDataSourceImpl(FileChannel fileChannel, String str) {
        this.f1340fc = fileChannel;
        this.filename = str;
    }

    @Override // com.googlecode.mp4parser.DataSource
    public int read(ByteBuffer byteBuffer) throws IOException {
        return this.f1340fc.read(byteBuffer);
    }

    @Override // com.googlecode.mp4parser.DataSource
    public long size() throws IOException {
        return this.f1340fc.size();
    }

    @Override // com.googlecode.mp4parser.DataSource
    public long position() throws IOException {
        return this.f1340fc.position();
    }

    @Override // com.googlecode.mp4parser.DataSource
    public void position(long j) throws IOException {
        this.f1340fc.position(j);
    }

    @Override // com.googlecode.mp4parser.DataSource
    public long transferTo(long j, long j2, WritableByteChannel writableByteChannel) throws IOException {
        return this.f1340fc.transferTo(j, j2, writableByteChannel);
    }

    @Override // com.googlecode.mp4parser.DataSource
    public ByteBuffer map(long j, long j2) throws IOException {
        return this.f1340fc.map(FileChannel.MapMode.READ_ONLY, j, j2);
    }

    @Override // com.googlecode.mp4parser.DataSource, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.f1340fc.close();
    }

    public String toString() {
        return this.filename;
    }
}
