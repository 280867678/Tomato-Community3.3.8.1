package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.BitArray;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class HpackDraft07 {
    private static final Header[] STATIC_HEADER_TABLE = {new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, ConstantUtils.QM_TASK_STATUS_204), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", ""), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
    private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX = nameToFirstIndex();

    /* loaded from: classes3.dex */
    static final class Reader {
        private int maxHeaderTableByteCount;
        private int maxHeaderTableByteCountSetting;
        private final BufferedSource source;
        private final List<Header> emittedHeaders = new ArrayList();
        Header[] headerTable = new Header[8];
        int nextHeaderIndex = this.headerTable.length - 1;
        int headerCount = 0;
        BitArray referencedHeaders = new BitArray.FixedCapacity();
        BitArray emittedReferencedHeaders = new BitArray.FixedCapacity();
        int headerTableByteCount = 0;

        /* JADX INFO: Access modifiers changed from: package-private */
        public Reader(int i, Source source) {
            this.maxHeaderTableByteCountSetting = i;
            this.maxHeaderTableByteCount = i;
            this.source = Okio.buffer(source);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void maxHeaderTableByteCountSetting(int i) {
            this.maxHeaderTableByteCountSetting = i;
            this.maxHeaderTableByteCount = this.maxHeaderTableByteCountSetting;
            adjustHeaderTableByteCount();
        }

        private void adjustHeaderTableByteCount() {
            int i = this.maxHeaderTableByteCount;
            int i2 = this.headerTableByteCount;
            if (i < i2) {
                if (i == 0) {
                    clearHeaderTable();
                } else {
                    evictToRecoverBytes(i2 - i);
                }
            }
        }

        private void clearHeaderTable() {
            clearReferenceSet();
            Arrays.fill(this.headerTable, (Object) null);
            this.nextHeaderIndex = this.headerTable.length - 1;
            this.headerCount = 0;
            this.headerTableByteCount = 0;
        }

        private int evictToRecoverBytes(int i) {
            int i2 = 0;
            if (i > 0) {
                int length = this.headerTable.length;
                while (true) {
                    length--;
                    if (length < this.nextHeaderIndex || i <= 0) {
                        break;
                    }
                    Header[] headerArr = this.headerTable;
                    i -= headerArr[length].hpackSize;
                    this.headerTableByteCount -= headerArr[length].hpackSize;
                    this.headerCount--;
                    i2++;
                }
                this.referencedHeaders.shiftLeft(i2);
                this.emittedReferencedHeaders.shiftLeft(i2);
                Header[] headerArr2 = this.headerTable;
                int i3 = this.nextHeaderIndex;
                System.arraycopy(headerArr2, i3 + 1, headerArr2, i3 + 1 + i2, this.headerCount);
                this.nextHeaderIndex += i2;
            }
            return i2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void readHeaders() throws IOException {
            while (!this.source.exhausted()) {
                int readByte = this.source.readByte() & 255;
                if (readByte == 128) {
                    throw new IOException("index == 0");
                }
                if ((readByte & 128) == 128) {
                    readIndexedHeader(readInt(readByte, 127) - 1);
                } else if (readByte == 64) {
                    readLiteralHeaderWithIncrementalIndexingNewName();
                } else if ((readByte & 64) == 64) {
                    readLiteralHeaderWithIncrementalIndexingIndexedName(readInt(readByte, 63) - 1);
                } else if ((readByte & 32) == 32) {
                    if ((readByte & 16) != 16) {
                        this.maxHeaderTableByteCount = readInt(readByte, 15);
                        int i = this.maxHeaderTableByteCount;
                        if (i < 0 || i > this.maxHeaderTableByteCountSetting) {
                            throw new IOException("Invalid header table byte count " + this.maxHeaderTableByteCount);
                        }
                        adjustHeaderTableByteCount();
                    } else if ((readByte & 15) != 0) {
                        throw new IOException("Invalid header table state change " + readByte);
                    } else {
                        clearReferenceSet();
                    }
                } else if (readByte == 16 || readByte == 0) {
                    readLiteralHeaderWithoutIndexingNewName();
                } else {
                    readLiteralHeaderWithoutIndexingIndexedName(readInt(readByte, 15) - 1);
                }
            }
        }

        private void clearReferenceSet() {
            this.referencedHeaders.clear();
            this.emittedReferencedHeaders.clear();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void emitReferenceSet() {
            int length = this.headerTable.length;
            while (true) {
                length--;
                if (length != this.nextHeaderIndex) {
                    if (this.referencedHeaders.get(length) && !this.emittedReferencedHeaders.get(length)) {
                        this.emittedHeaders.add(this.headerTable[length]);
                    }
                } else {
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public List<Header> getAndReset() {
            ArrayList arrayList = new ArrayList(this.emittedHeaders);
            this.emittedHeaders.clear();
            this.emittedReferencedHeaders.clear();
            return arrayList;
        }

        private void readIndexedHeader(int i) throws IOException {
            if (isStaticHeader(i)) {
                int i2 = i - this.headerCount;
                if (i2 <= HpackDraft07.STATIC_HEADER_TABLE.length - 1) {
                    Header header = HpackDraft07.STATIC_HEADER_TABLE[i2];
                    if (this.maxHeaderTableByteCount == 0) {
                        this.emittedHeaders.add(header);
                        return;
                    } else {
                        insertIntoHeaderTable(-1, header);
                        return;
                    }
                }
                throw new IOException("Header index too large " + (i2 + 1));
            }
            int headerTableIndex = headerTableIndex(i);
            if (!this.referencedHeaders.get(headerTableIndex)) {
                this.emittedHeaders.add(this.headerTable[headerTableIndex]);
                this.emittedReferencedHeaders.set(headerTableIndex);
            }
            this.referencedHeaders.toggle(headerTableIndex);
        }

        private int headerTableIndex(int i) {
            return this.nextHeaderIndex + 1 + i;
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int i) throws IOException {
            this.emittedHeaders.add(new Header(getName(i), readByteString()));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            ByteString readByteString = readByteString();
            HpackDraft07.checkLowercase(readByteString);
            this.emittedHeaders.add(new Header(readByteString, readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int i) throws IOException {
            insertIntoHeaderTable(-1, new Header(getName(i), readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            ByteString readByteString = readByteString();
            HpackDraft07.checkLowercase(readByteString);
            insertIntoHeaderTable(-1, new Header(readByteString, readByteString()));
        }

        private ByteString getName(int i) {
            if (isStaticHeader(i)) {
                return HpackDraft07.STATIC_HEADER_TABLE[i - this.headerCount].name;
            }
            return this.headerTable[headerTableIndex(i)].name;
        }

        private boolean isStaticHeader(int i) {
            return i >= this.headerCount;
        }

        private void insertIntoHeaderTable(int i, Header header) {
            int i2 = header.hpackSize;
            if (i != -1) {
                i2 -= this.headerTable[headerTableIndex(i)].hpackSize;
            }
            int i3 = this.maxHeaderTableByteCount;
            if (i2 > i3) {
                clearHeaderTable();
                this.emittedHeaders.add(header);
                return;
            }
            int evictToRecoverBytes = evictToRecoverBytes((this.headerTableByteCount + i2) - i3);
            if (i == -1) {
                int i4 = this.headerCount + 1;
                Header[] headerArr = this.headerTable;
                if (i4 > headerArr.length) {
                    Header[] headerArr2 = new Header[headerArr.length * 2];
                    System.arraycopy(headerArr, 0, headerArr2, headerArr.length, headerArr.length);
                    if (headerArr2.length == 64) {
                        this.referencedHeaders = ((BitArray.FixedCapacity) this.referencedHeaders).toVariableCapacity();
                        this.emittedReferencedHeaders = ((BitArray.FixedCapacity) this.emittedReferencedHeaders).toVariableCapacity();
                    }
                    this.referencedHeaders.shiftLeft(this.headerTable.length);
                    this.emittedReferencedHeaders.shiftLeft(this.headerTable.length);
                    this.nextHeaderIndex = this.headerTable.length - 1;
                    this.headerTable = headerArr2;
                }
                int i5 = this.nextHeaderIndex;
                this.nextHeaderIndex = i5 - 1;
                this.referencedHeaders.set(i5);
                this.headerTable[i5] = header;
                this.headerCount++;
            } else {
                int headerTableIndex = i + headerTableIndex(i) + evictToRecoverBytes;
                this.referencedHeaders.set(headerTableIndex);
                this.headerTable[headerTableIndex] = header;
            }
            this.headerTableByteCount += i2;
        }

        private int readByte() throws IOException {
            return this.source.readByte() & 255;
        }

        int readInt(int i, int i2) throws IOException {
            int i3 = i & i2;
            if (i3 < i2) {
                return i3;
            }
            int i4 = 0;
            while (true) {
                int readByte = readByte();
                if ((readByte & 128) == 0) {
                    return i2 + (readByte << i4);
                }
                i2 += (readByte & 127) << i4;
                i4 += 7;
            }
        }

        ByteString readByteString() throws IOException {
            int readByte = readByte();
            boolean z = (readByte & 128) == 128;
            int readInt = readInt(readByte, 127);
            if (z) {
                return ByteString.m70of(Huffman.get().decode(this.source.readByteArray(readInt)));
            }
            return this.source.readByteString(readInt);
        }
    }

    private static Map<ByteString, Integer> nameToFirstIndex() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(STATIC_HEADER_TABLE.length);
        int i = 0;
        while (true) {
            Header[] headerArr = STATIC_HEADER_TABLE;
            if (i < headerArr.length) {
                if (!linkedHashMap.containsKey(headerArr[i].name)) {
                    linkedHashMap.put(STATIC_HEADER_TABLE[i].name, Integer.valueOf(i));
                }
                i++;
            } else {
                return Collections.unmodifiableMap(linkedHashMap);
            }
        }
    }

    /* loaded from: classes3.dex */
    static final class Writer {
        private final Buffer out;

        /* JADX INFO: Access modifiers changed from: package-private */
        public Writer(Buffer buffer) {
            this.out = buffer;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void writeHeaders(List<Header> list) throws IOException {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ByteString asciiLowercase = list.get(i).name.toAsciiLowercase();
                Integer num = (Integer) HpackDraft07.NAME_TO_FIRST_INDEX.get(asciiLowercase);
                if (num != null) {
                    writeInt(num.intValue() + 1, 15, 0);
                    writeByteString(list.get(i).value);
                } else {
                    this.out.mo6807writeByte(0);
                    writeByteString(asciiLowercase);
                    writeByteString(list.get(i).value);
                }
            }
        }

        void writeInt(int i, int i2, int i3) throws IOException {
            if (i < i2) {
                this.out.mo6807writeByte(i | i3);
                return;
            }
            this.out.mo6807writeByte(i3 | i2);
            int i4 = i - i2;
            while (i4 >= 128) {
                this.out.mo6807writeByte(128 | (i4 & 127));
                i4 >>>= 7;
            }
            this.out.mo6807writeByte(i4);
        }

        void writeByteString(ByteString byteString) throws IOException {
            writeInt(byteString.size(), 127, 0);
            this.out.mo6804write(byteString);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ByteString checkLowercase(ByteString byteString) throws IOException {
        int size = byteString.size();
        for (int i = 0; i < size; i++) {
            byte b = byteString.getByte(i);
            if (b >= 65 && b <= 90) {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + byteString.utf8());
            }
        }
        return byteString;
    }
}
