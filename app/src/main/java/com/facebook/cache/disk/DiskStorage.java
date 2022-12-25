package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.WriterCallback;
import java.io.IOException;
import java.util.Collection;

/* loaded from: classes2.dex */
public interface DiskStorage {

    /* loaded from: classes2.dex */
    public interface Entry {
        String getId();

        long getSize();

        long getTimestamp();
    }

    /* loaded from: classes2.dex */
    public interface Inserter {
        boolean cleanUp();

        BinaryResource commit(Object obj) throws IOException;

        void writeData(WriterCallback writerCallback, Object obj) throws IOException;
    }

    /* renamed from: getEntries */
    Collection<Entry> mo5916getEntries() throws IOException;

    BinaryResource getResource(String str, Object obj) throws IOException;

    Inserter insert(String str, Object obj) throws IOException;

    boolean isExternal();

    void purgeUnexpectedResources();

    long remove(Entry entry) throws IOException;

    long remove(String str) throws IOException;
}
