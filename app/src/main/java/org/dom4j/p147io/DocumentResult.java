package org.dom4j.p147io;

import javax.xml.transform.sax.SAXResult;
import org.dom4j.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.ext.LexicalHandler;

/* renamed from: org.dom4j.io.DocumentResult */
/* loaded from: classes4.dex */
public class DocumentResult extends SAXResult {
    private SAXContentHandler contentHandler;

    public DocumentResult() {
        this(new SAXContentHandler());
    }

    public DocumentResult(SAXContentHandler sAXContentHandler) {
        this.contentHandler = sAXContentHandler;
        super.setHandler(this.contentHandler);
        super.setLexicalHandler(this.contentHandler);
    }

    public Document getDocument() {
        return this.contentHandler.getDocument();
    }

    @Override // javax.xml.transform.sax.SAXResult
    public void setHandler(ContentHandler contentHandler) {
        if (contentHandler instanceof SAXContentHandler) {
            this.contentHandler = (SAXContentHandler) contentHandler;
            super.setHandler(this.contentHandler);
        }
    }

    @Override // javax.xml.transform.sax.SAXResult
    public void setLexicalHandler(LexicalHandler lexicalHandler) {
        if (lexicalHandler instanceof SAXContentHandler) {
            this.contentHandler = (SAXContentHandler) lexicalHandler;
            super.setLexicalHandler(this.contentHandler);
        }
    }
}
