package org.dom4j.p147io;

import org.dom4j.DocumentFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/* renamed from: org.dom4j.io.SAXModifyReader */
/* loaded from: classes4.dex */
class SAXModifyReader extends SAXReader {
    private boolean pruneElements;
    private XMLWriter xmlWriter;

    public SAXModifyReader() {
    }

    public SAXModifyReader(boolean z) {
        super(z);
    }

    public SAXModifyReader(DocumentFactory documentFactory) {
        super(documentFactory);
    }

    public SAXModifyReader(DocumentFactory documentFactory, boolean z) {
        super(documentFactory, z);
    }

    public SAXModifyReader(XMLReader xMLReader) {
        super(xMLReader);
    }

    public SAXModifyReader(XMLReader xMLReader, boolean z) {
        super(xMLReader, z);
    }

    public SAXModifyReader(String str) throws SAXException {
        super(str);
    }

    public SAXModifyReader(String str, boolean z) throws SAXException {
        super(str, z);
    }

    public void setXMLWriter(XMLWriter xMLWriter) {
        this.xmlWriter = xMLWriter;
    }

    public boolean isPruneElements() {
        return this.pruneElements;
    }

    public void setPruneElements(boolean z) {
        this.pruneElements = z;
    }

    @Override // org.dom4j.p147io.SAXReader
    protected SAXContentHandler createContentHandler(XMLReader xMLReader) {
        SAXModifyContentHandler sAXModifyContentHandler = new SAXModifyContentHandler(getDocumentFactory(), getDispatchHandler());
        sAXModifyContentHandler.setXMLWriter(this.xmlWriter);
        return sAXModifyContentHandler;
    }

    protected XMLWriter getXMLWriter() {
        return this.xmlWriter;
    }
}
