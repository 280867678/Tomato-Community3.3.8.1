package org.dom4j.p147io;

import com.tomatolive.library.utils.ConstantUtils;
import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.QName;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* renamed from: org.dom4j.io.XPP3Reader */
/* loaded from: classes4.dex */
public class XPP3Reader {
    private DispatchHandler dispatchHandler;
    private DocumentFactory factory;
    private XmlPullParserFactory xppFactory;
    private XmlPullParser xppParser;

    public XPP3Reader() {
    }

    public XPP3Reader(DocumentFactory documentFactory) {
        this.factory = documentFactory;
    }

    public Document read(File file) throws DocumentException, IOException, XmlPullParserException {
        return read(new BufferedReader(new FileReader(file)), file.getAbsolutePath());
    }

    public Document read(URL url) throws DocumentException, IOException, XmlPullParserException {
        return read(createReader(url.openStream()), url.toExternalForm());
    }

    public Document read(String str) throws DocumentException, IOException, XmlPullParserException {
        if (str.indexOf(58) >= 0) {
            return read(new URL(str));
        }
        return read(new File(str));
    }

    public Document read(InputStream inputStream) throws DocumentException, IOException, XmlPullParserException {
        return read(createReader(inputStream));
    }

    public Document read(Reader reader) throws DocumentException, IOException, XmlPullParserException {
        getXPPParser().setInput(reader);
        return parseDocument();
    }

    public Document read(char[] cArr) throws DocumentException, IOException, XmlPullParserException {
        getXPPParser().setInput(new CharArrayReader(cArr));
        return parseDocument();
    }

    public Document read(InputStream inputStream, String str) throws DocumentException, IOException, XmlPullParserException {
        return read(createReader(inputStream), str);
    }

    public Document read(Reader reader, String str) throws DocumentException, IOException, XmlPullParserException {
        Document read = read(reader);
        read.setName(str);
        return read;
    }

    public XmlPullParser getXPPParser() throws XmlPullParserException {
        if (this.xppParser == null) {
            this.xppParser = getXPPFactory().newPullParser();
        }
        return this.xppParser;
    }

    public XmlPullParserFactory getXPPFactory() throws XmlPullParserException {
        if (this.xppFactory == null) {
            this.xppFactory = XmlPullParserFactory.newInstance();
        }
        this.xppFactory.setNamespaceAware(true);
        return this.xppFactory;
    }

    public void setXPPFactory(XmlPullParserFactory xmlPullParserFactory) {
        this.xppFactory = xmlPullParserFactory;
    }

    public DocumentFactory getDocumentFactory() {
        if (this.factory == null) {
            this.factory = DocumentFactory.getInstance();
        }
        return this.factory;
    }

    public void setDocumentFactory(DocumentFactory documentFactory) {
        this.factory = documentFactory;
    }

    public void addHandler(String str, ElementHandler elementHandler) {
        getDispatchHandler().addHandler(str, elementHandler);
    }

    public void removeHandler(String str) {
        getDispatchHandler().removeHandler(str);
    }

    public void setDefaultHandler(ElementHandler elementHandler) {
        getDispatchHandler().setDefaultHandler(elementHandler);
    }

    protected Document parseDocument() throws DocumentException, IOException, XmlPullParserException {
        QName createQName;
        QName createQName2;
        DocumentFactory documentFactory = getDocumentFactory();
        Document createDocument = documentFactory.createDocument();
        XmlPullParser xPPParser = getXPPParser();
        xPPParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        Element element = null;
        while (true) {
            switch (xPPParser.nextToken()) {
                case 1:
                    return createDocument;
                case 2:
                    if (xPPParser.getPrefix() == null) {
                        createQName = documentFactory.createQName(xPPParser.getName(), xPPParser.getNamespace());
                    } else {
                        createQName = documentFactory.createQName(xPPParser.getName(), xPPParser.getPrefix(), xPPParser.getNamespace());
                    }
                    Element createElement = documentFactory.createElement(createQName);
                    int namespaceCount = xPPParser.getNamespaceCount(xPPParser.getDepth());
                    for (int namespaceCount2 = xPPParser.getNamespaceCount(xPPParser.getDepth() - 1); namespaceCount2 < namespaceCount; namespaceCount2++) {
                        if (xPPParser.getNamespacePrefix(namespaceCount2) != null) {
                            createElement.addNamespace(xPPParser.getNamespacePrefix(namespaceCount2), xPPParser.getNamespaceUri(namespaceCount2));
                        }
                    }
                    for (int i = 0; i < xPPParser.getAttributeCount(); i++) {
                        if (xPPParser.getAttributePrefix(i) == null) {
                            createQName2 = documentFactory.createQName(xPPParser.getAttributeName(i));
                        } else {
                            createQName2 = documentFactory.createQName(xPPParser.getAttributeName(i), xPPParser.getAttributePrefix(i), xPPParser.getAttributeNamespace(i));
                        }
                        createElement.addAttribute(createQName2, xPPParser.getAttributeValue(i));
                    }
                    if (element != null) {
                        element.add(createElement);
                    } else {
                        createDocument.add(createElement);
                    }
                    element = createElement;
                    break;
                case 3:
                    if (element != null) {
                        element = element.getParent();
                        break;
                    } else {
                        break;
                    }
                case 4:
                    String text = xPPParser.getText();
                    if (element != null) {
                        element.addText(text);
                        break;
                    } else {
                        throw new DocumentException("Cannot have text content outside of the root document");
                    }
                case 5:
                    if (element != null) {
                        element.addCDATA(xPPParser.getText());
                        break;
                    } else {
                        throw new DocumentException("Cannot have text content outside of the root document");
                    }
                case 8:
                    String text2 = xPPParser.getText();
                    int indexOf = text2.indexOf(ConstantUtils.PLACEHOLDER_STR_ONE);
                    if (indexOf >= 0) {
                        createDocument.addProcessingInstruction(text2.substring(0, indexOf), text2.substring(indexOf + 1));
                        break;
                    } else {
                        createDocument.addProcessingInstruction(text2, "");
                        break;
                    }
                case 9:
                    if (element != null) {
                        element.addComment(xPPParser.getText());
                        break;
                    } else {
                        createDocument.addComment(xPPParser.getText());
                        break;
                    }
            }
        }
    }

    protected DispatchHandler getDispatchHandler() {
        if (this.dispatchHandler == null) {
            this.dispatchHandler = new DispatchHandler();
        }
        return this.dispatchHandler;
    }

    protected void setDispatchHandler(DispatchHandler dispatchHandler) {
        this.dispatchHandler = dispatchHandler;
    }

    protected Reader createReader(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
