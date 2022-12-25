package org.dom4j.p147io;

import javax.xml.transform.sax.SAXResult;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.ext.LexicalHandler;

/* renamed from: org.dom4j.io.DOMDocumentResult */
/* loaded from: classes4.dex */
public class DOMDocumentResult extends SAXResult {
    private DOMSAXContentHandler contentHandler;

    public DOMDocumentResult() {
        this(new DOMSAXContentHandler());
    }

    public DOMDocumentResult(DOMSAXContentHandler dOMSAXContentHandler) {
        this.contentHandler = dOMSAXContentHandler;
        super.setHandler(this.contentHandler);
        super.setLexicalHandler(this.contentHandler);
    }

    public Document getDocument() {
        return this.contentHandler.getDocument();
    }

    @Override // javax.xml.transform.sax.SAXResult
    public void setHandler(ContentHandler contentHandler) {
        if (contentHandler instanceof DOMSAXContentHandler) {
            this.contentHandler = (DOMSAXContentHandler) contentHandler;
            super.setHandler(this.contentHandler);
        }
    }

    @Override // javax.xml.transform.sax.SAXResult
    public void setLexicalHandler(LexicalHandler lexicalHandler) {
        if (lexicalHandler instanceof DOMSAXContentHandler) {
            this.contentHandler = (DOMSAXContentHandler) lexicalHandler;
            super.setLexicalHandler(this.contentHandler);
        }
    }
}
